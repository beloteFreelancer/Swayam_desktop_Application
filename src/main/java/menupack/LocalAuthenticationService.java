package menupack;

import ActivationPack.AES;
import Utils.TokenDecrypter;
import com.selrom.db.DataUtil;
import com.selrom.db.Database;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class LocalAuthenticationService {

    private final String secretKey = "!@#$%^&*()_+;.,|";
    private DataUtil util = new DataUtil();
    private String configPath;

    public LocalAuthenticationService(String configPath) {
        this.configPath = configPath;
    }

    public LocalAuthenticationService() {
        this.configPath = Utils.AppConfig.getAppPath() + "/Config_Files/";
    }

    public String getLicenseCid() throws SQLException, ClassNotFoundException {
        Connection con = Database.getInstance().getConnection();
        // Try to get from app_sync_status first
        try {
            String query = "SELECT license_cid FROM app_sync_status ORDER BY id DESC LIMIT 1";
            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String cid = rs.getString(1);
                    if (cid != null && !cid.isEmpty()) return cid;
                }
            }
        } catch (Exception e) {
            // Table might not exist if migration failed, but we assume it exists.
        }

        // Fallback: Check setting_user if populated
        String query2 = "SELECT cid FROM setting_user LIMIT 1";
        try (PreparedStatement ps = con.prepareStatement(query2);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString(1);
            }
        }

        return null;
    }

    public String getLicenseUname() throws SQLException, ClassNotFoundException {
        Connection con = Database.getInstance().getConnection();
        try {
            String query = "SELECT license_uname FROM app_sync_status ORDER BY id DESC LIMIT 1";
            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String u = rs.getString(1);
                    if (u != null && !u.isEmpty()) return u;
                }
            }
        } catch (Exception e) {}

        String query2 = "SELECT uname FROM setting_user LIMIT 1";
        try (PreparedStatement ps = con.prepareStatement(query2);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString(1);
            }
        }
        return null;
    }

    public void syncLicense(LicenseDTO dto) throws SQLException, ClassNotFoundException {
        Connection con = Database.getInstance().getConnection();
        boolean originalAutoCommit = con.getAutoCommit();

        try {
            con.setAutoCommit(false);

            // Clear existing local license to prevent conflict/stale data
            String deleteQuery = "DELETE FROM setting_user";
            try (PreparedStatement deletePs = con.prepareStatement(deleteQuery)) {
                deletePs.executeUpdate();
            }

            String insertQuery = "INSERT INTO setting_user (status, dat, vname, hname, mname, cid, cname, uname, eno, vdate, log, pass, user_valid_date) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
                ps.setString(1, dto.getEncStatus());
                ps.setDate(2, dto.getDate() != null ? new java.sql.Date(dto.getDate().getTime()) : null);
                ps.setString(3, dto.getEncVersion());
                ps.setString(4, dto.getEncHard());
                ps.setString(5, dto.getEncMother());
                ps.setString(6, dto.getCid());
                ps.setString(7, dto.getCname());
                ps.setString(8, dto.getUname());
                ps.setString(9, dto.getEno());
                ps.setDate(10, dto.getVdate() != null ? new java.sql.Date(dto.getVdate().getTime()) : null);
                ps.setString(11, dto.getLicenseToken());
                ps.setString(12, dto.getEncPass());
                ps.setDate(13, dto.getUserValidDate() != null ? new java.sql.Date(dto.getUserValidDate().getTime()) : null);

                ps.executeUpdate();
            }

            // Sync Data: Update Timer
            updateOfflineSyncTime();

            // Ensure app_sync_status has the license info
            String licenseCid = dto.getCid();
            String licenseUname = dto.getUname();

            if (licenseCid != null && !licenseCid.isEmpty()) {
                String update = "UPDATE app_sync_status SET license_cid=?, license_uname=? WHERE (license_cid IS NULL OR license_cid='') OR (license_uname IS NULL OR license_uname='')";
                try (PreparedStatement ps = con.prepareStatement(update)) {
                    ps.setString(1, licenseCid);
                    ps.setString(2, licenseUname);
                    ps.executeUpdate();
                }
            }

            con.commit();
        } catch (Exception e) {
            con.rollback();
            if (e instanceof SQLException) throw (SQLException) e;
            throw new RuntimeException(e);
        } finally {
            con.setAutoCommit(originalAutoCommit);
        }
    }

    private void updateOfflineSyncTime() throws Exception {
        // Path 1: Scenario A - Updates Timer: Sets last_cloud_sync to NOW.
        Connection con = Database.getInstance().getConnection();
        String update = "UPDATE app_sync_status SET last_cloud_sync=NOW()";
        try (PreparedStatement ps = con.prepareStatement(update)) {
            int rows = ps.executeUpdate();
            if (rows == 0) {
                // Insert if missing
                String insert = "INSERT INTO app_sync_status (last_cloud_sync) VALUES (NOW())";
                try (PreparedStatement ps2 = con.prepareStatement(insert)) {
                    ps2.executeUpdate();
                }
            }
        }
    }

    private void checkOfflineValidity() throws Exception {
        // Path 2: Offline Mode
        Connection con = Database.getInstance().getConnection();
        String query = "SELECT last_cloud_sync FROM app_sync_status";

        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                Timestamp lastSync = rs.getTimestamp("last_cloud_sync");
                if (lastSync == null) {
                    throw new Exception("Offline login invalid. Never synced.");
                }

                long diff = System.currentTimeMillis() - lastSync.getTime();
                long hours = diff / (60 * 60 * 1000);

                // Scenario G: Offline Time Limit Exceeded (> 24 Hours)
                if (hours > 24) {
                    throw new Exception("Offline limit (24 hours) exceeded. Connect to internet to sync.");
                }
            } else {
                throw new Exception("Application not activated locally.");
            }
        }
    }

    public void syncUser(String user, String pass, String utype) throws SQLException, ClassNotFoundException {
        Connection con = Database.getInstance().getConnection();

        String check = "SELECT count(*) FROM users WHERE user=?";
        boolean exists = false;
        try (PreparedStatement ps = con.prepareStatement(check)) {
            ps.setString(1, user);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) exists = true;
            }
        }

        String hashedPass = Utils.PasswordUtils.hashPassword(pass);

        if (exists) {
            String update = "UPDATE users SET pass=?, utype=? WHERE user=?";
            try (PreparedStatement ps = con.prepareStatement(update)) {
                ps.setString(1, hashedPass);
                ps.setString(2, utype);
                ps.setString(3, user);
                ps.executeUpdate();
            }
        } else {
            String insert = "INSERT INTO users (user, pass, utype, last) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insert)) {
                ps.setString(1, user);
                ps.setString(2, hashedPass);
                ps.setString(3, utype);
                ps.setString(4, "0");
                ps.executeUpdate();
            }
        }
    }
    public String authenticate(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = Database.getInstance().getConnection();
        String query = "select utype, pass from users where LOWER(`user`)=LOWER(?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("pass");
                    if (Utils.PasswordUtils.verifyPassword(password, storedHash)) {
                        return rs.getString("utype");
                    }
                }
            }
        }
        return null;
    }

    // --- TERMINAL AUTHORIZATION METHODS (LOCAL) ---

    public int getLocalAuthorizedTerminalCount() {
        try {
            Connection con = Database.getInstance().getConnection();
            String query = "SELECT count(*) FROM authorized_terminals";
            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {}
        return 0;
    }

    public boolean isTerminalAuthorized(String hddId, String mbId) {
        String hwid = hddId + ":" + mbId;
        try {
            Connection con = Database.getInstance().getConnection();
            String query = "SELECT count(*) FROM authorized_terminals WHERE hwid=?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, hwid);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) return true;
                }
            }
        } catch (Exception e) {}
        return false;
    }

    public void authorizeTerminal(String hddId, String mbId, String label) {
        String hwid = hddId + ":" + mbId;
        try {
            Connection con = Database.getInstance().getConnection();
            if (!isTerminalAuthorized(hddId, mbId)) {
                try (PreparedStatement ps = con.prepareStatement("INSERT INTO authorized_terminals (hwid, label, first_seen) VALUES (?, ?, NOW())")) {
                    ps.setString(1, hwid);
                    ps.setString(2, label);
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logAccessRequest(String hddId, String mbId, String label) {
        String hwid = hddId + ":" + mbId;
        try {
            Connection con = Database.getInstance().getConnection();

            // Check if request already exists
            try (PreparedStatement ps = con.prepareStatement("SELECT count(*) FROM access_requests WHERE hwid=?")) {
                ps.setString(1, hwid);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) return;
                }
            }

            // Log request
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO access_requests (hwid, label, request_time) VALUES (?, ?, NOW())")) {
                ps.setString(1, hwid);
                ps.setString(2, label);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validateLicense(boolean isLicenseOwner) throws Exception {
        // Checks offline validity first (24 hr limit)
        checkOfflineValidity();

        Connection con = Database.getInstance().getConnection();
        String query = "SELECT status, dat, vname, hname, mname, cid, cname, uname, eno, vdate, log, pass, user_valid_date "
                + "FROM setting_user";

        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (!rs.next()) {
                throw new Exception("No offline license found. Connect to internet first.");
            }

            // Load data to DTO for validation
            LicenseDTO dto = new LicenseDTO();
            dto.setEncStatus(rs.getString("status"));
            dto.setEncHard(rs.getString("hname"));
            dto.setEncMother(rs.getString("mname"));
            dto.setLicenseToken(rs.getString("log"));
            dto.setEncPass(rs.getString("pass"));
            dto.setUserValidDate(rs.getDate("user_valid_date"));
            dto.setVdate(rs.getDate("vdate"));
            dto.setEno(rs.getString("eno"));

            // Check Status
            String status = AES.decrypt(dto.getEncStatus(), secretKey);
            if (status == null || (!"Activated".equalsIgnoreCase(status) && !"Trial".equalsIgnoreCase(status))) {
                throw new Exception("License Status is " + (status == null ? "Invalid" : status) + ". Please contact support.");
            }

            Date today = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date todayZero = sdf.parse(sdf.format(today));

            // Check License Expiry
            if (dto.getVdate() != null) {
                if (todayZero.after(dto.getVdate())) {
                    throw new Exception("Your License has expired.");
                }
            }

            // Check User Valid Date
            if (dto.getUserValidDate() != null) {
                if (todayZero.after(dto.getUserValidDate())) {
                    throw new Exception("Your User Account has expired.");
                }
            }

            // NEW CHECK: Max Terminals (Offline)
            String enoStr = dto.getEno();
            if (enoStr != null && !enoStr.isEmpty()) {
                try {
                    int max = Integer.parseInt(enoStr);
                    int count = getLocalAuthorizedTerminalCount();

                    if (count > max) {
                        throw new Exception("License Limit Exceeded (Offline). Max Terminals: " + max + ", Active: " + count);
                    }
                } catch (NumberFormatException e) {
                    // ignore parsing error
                }
            }
        }
    }

    public void validateLicenseDTO(LicenseDTO dto, String userType) throws Exception {
        // Check Status
        String status = AES.decrypt(dto.getEncStatus(), secretKey);
        if (status == null || (!"Activated".equalsIgnoreCase(status) && !"Trial".equalsIgnoreCase(status))) {
            throw new Exception("License Status is " + (status == null ? "Invalid" : status) + ". Please contact support.");
        }

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date todayZero = sdf.parse(sdf.format(today));

        if (dto.getVdate() != null) {
            if (todayZero.after(dto.getVdate())) {
                throw new Exception("Your License has expired.");
            }
        }

        if (dto.getUserValidDate() != null) {
            if (todayZero.after(dto.getUserValidDate())) {
                throw new Exception("Your User Account has expired.");
            }
        }
    }
}
