package menupack;

import ActivationPack.AES;
import com.selrom.db.CloudDatabase;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CloudAuthenticationService {
    // Secret Key matching Login/LocalAuth. Ideally this should be centralized in a Config class.
    private final String secretKey = "!@#$%^&*()_+;.,|";

    // Simulation Flag for Testing/Demo
    public static boolean SIMULATION_MODE = true;

    public String authenticate(String username, String password, String hddId, String mbId) throws SQLException, ClassNotFoundException {
        if (SIMULATION_MODE) {
            if ("owner".equalsIgnoreCase(username) && "123".equals(password)) return "License Owner";
            if ("user".equalsIgnoreCase(username) && "123".equals(password)) return "User";
            return null;
        }

        Connection con = CloudDatabase.getConnection();

        String query = "SELECT utype, pass FROM users WHERE LOWER(`user`)=LOWER(?)";
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

    public void createUser(String username, String password, String type) throws SQLException, ClassNotFoundException {
        if (SIMULATION_MODE) return;

        try (Connection con = CloudDatabase.getConnection()) {
            String hashedPassword = Utils.PasswordUtils.hashPassword(password);

            // Check existence
            try (PreparedStatement ps = con.prepareStatement("SELECT count(*) FROM users WHERE user=?")) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) throw new SQLException("User already exists.");
                }
            }

            String insert = "INSERT INTO users (user, pass, utype) VALUES (?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insert)) {
                ps.setString(1, username);
                ps.setString(2, hashedPassword);
                ps.setString(3, type);
                ps.executeUpdate();
            }
        }
    }

    public LicenseDTO fetchLicense(String machineId, String motherboardId, String licenseCid) throws Exception {
        if (SIMULATION_MODE) {
            LicenseDTO dto = new LicenseDTO();
            dto.setEncStatus("ACTIVE"); // Encrypted usually
            dto.setCname("Simulation Corp");
            dto.setUname("simuser");
            dto.setCid(licenseCid);
            dto.setEno("5"); // 5 Terminals
            dto.setVdate(new Date(System.currentTimeMillis() + 864000000L)); // +10 days
            dto.setUserValidDate(new Date(System.currentTimeMillis() + 864000000L));
            dto.setEncHard(AES.encrypt(machineId, secretKey));
            dto.setEncMother(AES.encrypt(motherboardId, secretKey));
            return dto;
        }

        Connection con = CloudDatabase.getConnection();

        // Retrieve license based on License ID (CID)
        String query = "SELECT status, dat, vname, cid, cname, uname, eno, vdate, log, pass, user_valid_date "
                + "FROM setting_user WHERE cid=?";

        // Ensure we trim any potential whitespace from the ID
        String searchCid = (licenseCid != null) ? licenseCid.trim() : "";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, searchCid);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Create DTO with Cloud Data
                    LicenseDTO dto = new LicenseDTO();
                    dto.setEncStatus(rs.getString("status"));
                    dto.setEncVersion(rs.getString("vname"));
                    dto.setLicenseToken(rs.getString("log"));
                    dto.setEncPass(rs.getString("pass"));
                    dto.setCid(rs.getString("cid"));
                    dto.setCname(rs.getString("cname"));
                    dto.setUname(rs.getString("uname"));
                    dto.setEno(rs.getString("eno"));

                    dto.setVdate(rs.getDate("vdate"));
                    dto.setUserValidDate(rs.getDate("user_valid_date"));
                    dto.setDate(rs.getDate("dat"));

                    // Re-bind license to THIS machine for Local Storage.
                    dto.setEncHard(AES.encrypt(machineId, secretKey));
                    dto.setEncMother(AES.encrypt(motherboardId, secretKey));

                    return dto;
                }
            }
        }

        throw new Exception("License not found for ID: " + searchCid);
    }
}
