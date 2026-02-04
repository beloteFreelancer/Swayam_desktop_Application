package ActivationPack;

import Utils.TokenDecrypter;
import com.selrom.db.DataUtil;
import com.selrom.db.Database;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Handles business logic for License Activation.
 */
public class LicenseManager {

    private final String secretKey = "!@#$%^&*()_+;.,|";
    private DataUtil util = new DataUtil();

    public Map<String, String> verifyLicenseToken(String licenseKey, String inputPassword) throws Exception {
        Map<String, String> tokenData = TokenDecrypter.decryptToken(licenseKey);
        String tokenPassword = tokenData.get("password");
        String tokenExpiry = tokenData.get("tokenExpiry");

        if (!inputPassword.equals(tokenPassword)) {
            throw new Exception("Password does not match the license key.");
        }

        SimpleDateFormat sdfFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expiryDate = null;
        try {
            expiryDate = sdfFull.parse(tokenExpiry);
        } catch (Exception e) {
            expiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(tokenExpiry);
        }

        if (new Date().after(expiryDate)) {
            throw new Exception("This license key has expired.");
        }

        return tokenData;
    }

    public List<String> getExistingAdmins() {
        List<String> admins = new ArrayList<>();
        try {
            Connection conn = Database.getInstance().getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT user FROM users WHERE utype='Administrator' OR utype='Super Admin' OR utype='License Owner'")) {
                while (rs.next()) {
                    admins.add(rs.getString("user"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admins;
    }

    public List<String> getDistinctRoles() {
        List<String> roles = new ArrayList<>();
        try {
            Connection conn = Database.getInstance().getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT DISTINCT utype FROM users")) {
                while (rs.next()) {
                    String r = rs.getString("utype");
                    if (r != null && !r.trim().isEmpty()) {
                        roles.add(r);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }

    public java.util.Map<String, String> getUserDetails(String username) {
        java.util.Map<String, String> details = new java.util.HashMap<>();
        try {
            Connection conn = Database.getInstance().getConnection();
            String query = "SELECT pass, utype FROM users WHERE user=?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        details.put("password", rs.getString("pass"));
                        details.put("type", rs.getString("utype"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return details;
    }

    // New method to check if a user is available (Globally unique check implied by local constraint + Cloud sync later)
    // Enforces: License Owner user must be unique. No other user can have License Owner name.
    public boolean isUsernameTaken(String username) {
        try {
            Connection conn = Database.getInstance().getConnection();
            try (PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM users WHERE LOWER(user)=LOWER(?)")) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateUserRole(String username, String newRole) throws SQLException, ClassNotFoundException {
         Connection conn = Database.getInstance().getConnection();
         String update = "UPDATE users SET utype=? WHERE user=?";
         try (PreparedStatement ps = conn.prepareStatement(update)) {
             ps.setString(1, newRole);
             ps.setString(2, username);
             ps.executeUpdate();
         }
    }

    public void createOrUpdateUser(String username, String password, String type) throws SQLException, ClassNotFoundException, Exception {
        Connection conn = Database.getInstance().getConnection();
        String hashedPassword = Utils.PasswordUtils.hashPassword(password);

        // Check if user exists
        boolean exists = isUsernameTaken(username);

        // If creating License Owner, ensure no conflict (handled by isUsernameTaken returning true if exists)
        // If exists, we update. If not, insert.
        // Uniqueness Logic:
        // If we are creating/updating a user to License Owner role, we must ensure that IF the user already exists, it is being updated.
        // If it doesn't exist, fine.
        // But we must also ensure that we don't accidentally overwrite a different user if not intended (handled by UI flow)

        String last = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").format(new Date());

        if (exists) {
            String update = "UPDATE users SET pass=?, utype=?, last=? WHERE user=?";
            try (PreparedStatement ps = conn.prepareStatement(update)) {
                ps.setString(1, hashedPassword);
                ps.setString(2, type);
                ps.setString(3, last);
                ps.setString(4, username);
                ps.executeUpdate();
            }
        } else {
            String insert = "INSERT INTO users (user, pass, utype, last) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insert)) {
                ps.setString(1, username);
                ps.setString(2, hashedPassword);
                ps.setString(3, type);
                ps.setString(4, last);
                ps.executeUpdate();
            }
        }
    }

    public void activateLicense(String licenseKey, String password, String businessName, String associatedUser) throws Exception {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String hardDiskId = get_hard_disc_Id();
        String motherBoardId = get_mother_board_id();

        String encStatus = AES.encrypt("Activated", secretKey);
        String encVersion = AES.encrypt("Full Version", secretKey);
        String encHard = AES.encrypt(hardDiskId, secretKey);
        String encMother = AES.encrypt(motherBoardId, secretKey);
        String encPass = AES.encrypt(password, secretKey);

        Connection conn = Database.getInstance().getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM setting_user");
        }

        String insertQuery = "INSERT INTO setting_user (status, dat, vname, hname, mname, cid, cname, uname, eno, vdate, log, pass) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, encStatus);
            pstmt.setString(2, currentDate);
            pstmt.setString(3, encVersion);
            pstmt.setString(4, encHard);
            pstmt.setString(5, encMother);
            pstmt.setString(6, password); // cid
            pstmt.setString(7, businessName);
            pstmt.setString(8, associatedUser); // uname
            pstmt.setString(9, "0");
            pstmt.setString(10, currentDate);
            pstmt.setString(11, licenseKey);
            pstmt.setString(12, encPass);
            pstmt.executeUpdate();
        }
    }

    // Hardware ID helpers
    public String get_hard_disc_Id() {
        String serial = "";
        try {
            String line;
            Process process = Runtime.getRuntime().exec("cmd /c vol C:");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                while ((line = in.readLine()) != null) {
                    if (line.toLowerCase().contains("serial number")) {
                        String[] strings = line.split(" ");
                        serial = strings[strings.length - 1];
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serial;
    }

    public String get_mother_board_id() {
        String result = "";
        try {
            File file = File.createTempFile("GetMBSerial", ".vbs");
            file.deleteOnExit();
            try (FileWriter fw = new FileWriter(file)) {
                String vbs
                        = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                        + "Set colItems = objWMIService.ExecQuery _ \n"
                        + "   (\"Select * from Win32_ComputerSystemProduct\") \n"
                        + "For Each objItem in colItems \n"
                        + "    Wscript.Echo objItem.IdentifyingNumber \n"
                        + "Next \n";

                fw.write(vbs);
            }
            Process gWMI = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            try (BufferedReader input = new BufferedReader(new InputStreamReader(gWMI.getInputStream()))) {
                String line;
                while ((line = input.readLine()) != null) {
                    result += line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getEncryptedHardwareInfo() {
         String h = get_hard_disc_Id();
         String m = get_mother_board_id();
         return AES.encrypt(h, secretKey) + "|" + AES.encrypt(m, secretKey);
         // Helper if needed for bulk ops, but mostly we need individual fields for Cloud Sync
    }

    public LicenseDataPackage prepareCloudSyncData(String licenseKey, String password, String businessName, String associatedUser) throws Exception {
         LicenseDataPackage pkg = new LicenseDataPackage();
         String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
         String hardDiskId = get_hard_disc_Id();
         String motherBoardId = get_mother_board_id();

         pkg.encStatus = AES.encrypt("Activated", secretKey);
         pkg.date = new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);
         pkg.encVersion = AES.encrypt("Full Version", secretKey);
         pkg.encHard = AES.encrypt(hardDiskId, secretKey);
         pkg.encMother = AES.encrypt(motherBoardId, secretKey);
         pkg.cid = password;
         pkg.cname = businessName;
         pkg.uname = associatedUser;
         pkg.eno = "0";
         pkg.vdate = pkg.date;
         pkg.licenseToken = licenseKey;
         pkg.encPass = AES.encrypt(password, secretKey);

         return pkg;
    }

    public menupack.LicenseDTO getLicenseDTOForSync(String licenseKey, String password, String businessName, String associatedUser) throws Exception {
         menupack.LicenseDTO dto = new menupack.LicenseDTO();
         String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

         // Decrypt token to get plan expiry
         Map<String, String> tokenData = TokenDecrypter.decryptToken(licenseKey);
         String planExpiry = tokenData.get("planExpiry");
         Date userValidDate = null;
         if (planExpiry != null) {
             try {
                 userValidDate = new SimpleDateFormat("yyyy-MM-dd").parse(planExpiry);
             } catch (Exception e) {
                 System.out.println("Error parsing plan expiry for user_valid_date: " + e.getMessage());
             }
         }

         dto.setEncStatus(AES.encrypt("Activated", secretKey));
         dto.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(currentDate));
         dto.setEncVersion(AES.encrypt("Full Version", secretKey));
         dto.setEncHard(AES.encrypt(get_hard_disc_Id(), secretKey));
         dto.setEncMother(AES.encrypt(get_mother_board_id(), secretKey));
         dto.setCid(password);
         dto.setCname(businessName);
         dto.setUname(associatedUser);
         dto.setEno("0");
         dto.setVdate(dto.getDate());
         dto.setLicenseToken(licenseKey);
         dto.setEncPass(AES.encrypt(password, secretKey));
         dto.setUserValidDate(userValidDate);

         return dto;
    }

    // Helper class just in case
    public class LicenseDataPackage {
        public String encStatus;
        public Date date;
        public String encVersion;
        public String encHard;
        public String encMother;
        public String cid;
        public String cname;
        public String uname;
        public String eno;
        public Date vdate;
        public String licenseToken;
        public String encPass;
    }
}
