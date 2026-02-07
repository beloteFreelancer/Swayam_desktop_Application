package ActivationPack;

import Utils.ColorConstants;
import menupack.CloudAuthenticationService;
import menupack.login;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import com.selrom.db.CloudDatabase;
import com.selrom.db.Database;
import Utils.TokenDecrypter;

public final class activation_home extends javax.swing.JFrame {

    public activation_home() {
        initComponents();
        get_defaults();
    }

    void get_defaults() {
        setSize(450, 200); // Reduced height since username is hidden
        setLocationRelativeTo(null);
        setResizable(false);
        try {
            javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
            if (icon != null) {
                setIconImage(icon.getImage());
            }
        } catch (Exception e) {
        }
    }

    private void initComponents() {
        mainPanel = new javax.swing.JPanel();
        headerPanel = new javax.swing.JPanel();
        lblHeader = new javax.swing.JLabel();

        lblLicenseKey = new javax.swing.JLabel();
        txtLicenseKey = new javax.swing.JTextField();

        // txtUserName is hidden/removed from UI as per requirements

        btnActivate = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255), 3));
        mainPanel.setLayout(null);

        headerPanel.setBackground(new java.awt.Color(0, 51, 255));
        headerPanel.setLayout(null);

        lblHeader.setFont(new java.awt.Font("Tahoma", 0, 18));
        lblHeader.setForeground(new java.awt.Color(255, 255, 255));
        lblHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeader.setText("Software Activation");
        headerPanel.add(lblHeader);
        lblHeader.setBounds(0, 0, 450, 40);

        mainPanel.add(headerPanel);
        headerPanel.setBounds(0, 0, 450, 40);

        lblLicenseKey.setFont(new java.awt.Font("Arial", 0, 14));
        lblLicenseKey.setText("Activation Token:");
        mainPanel.add(lblLicenseKey);
        lblLicenseKey.setBounds(30, 70, 120, 30);

        txtLicenseKey.setFont(new java.awt.Font("Arial", 0, 14));
        mainPanel.add(txtLicenseKey);
        txtLicenseKey.setBounds(150, 70, 250, 30);

        btnActivate.setBackground(new java.awt.Color(0, 153, 51));
        btnActivate.setFont(new java.awt.Font("Tahoma", 0, 14));
        btnActivate.setForeground(new java.awt.Color(255, 255, 255));
        btnActivate.setText("Activate");
        btnActivate.addActionListener(e -> performActivation());
        mainPanel.add(btnActivate);
        btnActivate.setBounds(150, 130, 100, 35); // Adjusted Y pos

        btnExit.setBackground(new java.awt.Color(200, 50, 50));
        btnExit.setFont(new java.awt.Font("Tahoma", 0, 14));
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("Exit");
        btnExit.addActionListener(e -> System.exit(0));
        mainPanel.add(btnExit);
        btnExit.setBounds(270, 130, 100, 35); // Adjusted Y pos

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE) // Adjusted
                                                                                                             // height
        );

        pack();
    }

    private void createLicenseOwnerDialog() {
        javax.swing.JPanel p = new javax.swing.JPanel(new java.awt.GridLayout(2, 2, 10, 10));
        javax.swing.JTextField userF = new javax.swing.JTextField();
        javax.swing.JPasswordField passF = new javax.swing.JPasswordField();
        p.add(new javax.swing.JLabel("License Owner Username:"));
        p.add(userF);
        p.add(new javax.swing.JLabel("Password:"));
        p.add(passF);

        int result = JOptionPane.showConfirmDialog(this, p, "Create License Owner Account",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String u = userF.getText().trim();
            String pwd = new String(passF.getPassword()).trim();
            if (u.isEmpty() || pwd.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty");
                createLicenseOwnerDialog(); // Retry
                return;
            }

            try {
                CloudAuthenticationService cloudAuth = new CloudAuthenticationService();
                cloudAuth.createUser(u, pwd, "License Owner"); // Create as License Owner

                JOptionPane.showMessageDialog(this, "License Owner Account Created!");
                this.dispose();
                new login().setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error creating License Owner: " + e.getMessage());
            }
        } else {
            System.exit(0);
        }
    }

    // Helper to get existing License Owner username
    private String getExistingLicenseOwner(Connection con) throws SQLException {
        String query = "SELECT user FROM users WHERE utype='License Owner' LIMIT 1";
        try (PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("user");
            }
        }
        return null;
    }

    // Helper to delete existing License Owner
    private void deleteLicenseOwner(Connection con) throws SQLException {
        String query = "DELETE FROM users WHERE utype='License Owner'";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.executeUpdate();
        }
    }

    private void performActivation() {
        String token = txtLicenseKey.getText().trim();

        if (!token.startsWith("Swayam-ACT-")) {
            JOptionPane.showMessageDialog(this, "Invalid Activation Token!", "Activation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // 1. Decrypt token locally to get License ID (cid)
            String encryptedPart = token.replace("Swayam-ACT-", "");
            Map<String, String> tokenData = TokenDecrypter.decryptToken(encryptedPart);
            String cid = tokenData.get("cid");
            String licenseUname = tokenData.get("password"); // Username is in the 'password' field of the token map

            if (cid == null || cid.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Token Structure.", "Activation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Check Internet
            try {
                java.net.URL url = new java.net.URL("http://google.com");
                java.net.URLConnection connection = url.openConnection();
                connection.connect();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Connection Failed. Internet Required.", "Activation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection cloudCon = CloudDatabase.getConnection()) {
                // 3. Verify Token Status in Cloud
                String checkQuery = "SELECT token_used, cid FROM setting_user WHERE log=?";
                boolean exists = false;
                boolean alreadyUsed = false;

                try (PreparedStatement ps = cloudCon.prepareStatement(checkQuery)) {
                    ps.setString(1, token);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            exists = true;
                            alreadyUsed = rs.getBoolean("token_used");
                            String dbCid = rs.getString("cid");

                            if (!dbCid.equals(cid)) {
                                JOptionPane.showMessageDialog(this, "Token content mismatch.", "Activation Error",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                    }
                }

                if (!exists) {
                    JOptionPane.showMessageDialog(this, "Invalid Activation Token (Not Found).", "Activation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (alreadyUsed) {
                    JOptionPane.showMessageDialog(this, "This Activation Token has already been used.",
                            "Activation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 4. Mark Token as USED in Cloud
                String updateCloud = "UPDATE setting_user SET token_used=true WHERE log=?";
                try (PreparedStatement ps = cloudCon.prepareStatement(updateCloud)) {
                    ps.setString(1, token);
                    ps.executeUpdate();
                }

                // 5. Update Local app_sync_status
                try (Connection localCon = Database.getInstance().getConnection()) {
                    String updateLocal = "UPDATE app_sync_status SET license_uname=?, license_cid=?, last_cloud_sync=NOW()";
                    try (PreparedStatement psLocal = localCon.prepareStatement(updateLocal)) {
                        psLocal.setString(1, licenseUname);
                        psLocal.setString(2, cid);
                        int updated = psLocal.executeUpdate();
                        if (updated == 0) {
                            try (PreparedStatement psInsert = localCon.prepareStatement(
                                    "INSERT INTO app_sync_status (license_uname, license_cid, last_cloud_sync) VALUES (?, ?, NOW())")) {
                                psInsert.setString(1, licenseUname);
                                psInsert.setString(2, cid);
                                psInsert.executeUpdate();
                            }
                        }
                    }
                }

                // 6. Check for existing License Owner account in Cloud DB
                String ownerUser = getExistingLicenseOwner(cloudCon);

                if (ownerUser != null) {
                    // License Owner Exists - Show options
                    Object[] options = { "Activate for current user",
                            "Delete current user and activate license for new user" };

                    int choice = JOptionPane.showOptionDialog(this,
                            "A License Owner account ('" + ownerUser + "') is already stored in the database.\n"
                                    + "How do you want to proceed?",
                            "License Owner Account Exists",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);

                    if (choice == JOptionPane.YES_OPTION) {
                        // Activate for current user
                        JOptionPane.showMessageDialog(this, "Activation Successful!", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                        new login().setVisible(true);
                    } else if (choice == JOptionPane.NO_OPTION) {
                        // Delete current user and create new
                        deleteLicenseOwner(Database.getInstance().getConnection());
                        JOptionPane.showMessageDialog(this, "Previous License Owner account deleted.",
                                "Account Deleted", JOptionPane.INFORMATION_MESSAGE);
                        createLicenseOwnerDialog();
                    } else {
                        // User closed dialog, do nothing
                    }

                } else {
                    // No License Owner Exists - Create new
                    JOptionPane.showMessageDialog(this, "Activation Successful! Please create a License Owner account.",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    createLicenseOwnerDialog();
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Activation Error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ex) {
        }

        java.awt.EventQueue.invokeLater(() -> {
            new activation_home().setVisible(true);
        });
    }

    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblLicenseKey;
    private javax.swing.JTextField txtLicenseKey;
    private javax.swing.JButton btnActivate;
    private javax.swing.JButton btnExit;
}
