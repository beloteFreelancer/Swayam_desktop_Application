package menupack;

import ActivationPack.AES;
import ActivationPack.trial_version_expired;
import Utils.TokenDecrypter;
import com.selrom.db.DataUtil;
import com.selrom.db.Database;
import Utils.ColorConstants;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

public final class login extends javax.swing.JFrame {

    DataUtil util = null;
    menu_form me = new menu_form();
    String drive = me.getDrive();
    String folder = me.getFoler();
    final String secretKey = "!@#$%^&*()_+;.,|";
    int days_left = 0;
    String what_version = ".";
    String key_what = ".";

    public login() {
        util = new DataUtil();
        initComponents();
        get_defaults();
        setLocationRelativeTo(getRootPane());
        setResizable(false);
    }

    void get_defaults() {
        try {
            javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
            if (icon != null) {
                setIconImage(icon.getImage());
            }
        } catch (Exception e) {
        }
    }

    private void performLogin() {
        String user = h1.getText().trim();
        String pass = new String(h2.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Username & Password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        closebutton.setEnabled(false);
        closebutton.setText("Auth...");

        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                CloudAuthenticationService cloudAuth = new CloudAuthenticationService();
                LocalAuthenticationService localAuth = new LocalAuthenticationService();
                String hddId = get_hard_disc_Id();
                String mbId = get_mother_board_id();
                String machineName = "Unknown PC";
                try {
                    machineName = java.net.InetAddress.getLocalHost().getHostName();
                } catch (Exception e) {
                }

                boolean online = isOnline();
                String utype = null;

                // 1. Try Local Authentication
                String localUtype = localAuth.authenticate(user, pass);

                if (online) {
                    try {
                        if (localUtype != null) {
                            // User found Locally
                            if ("License Owner".equalsIgnoreCase(localUtype)) {
                                // License Owner must validate against Cloud
                                String cloudUtype = cloudAuth.authenticate(user, pass, hddId, mbId);
                                if (cloudUtype == null) {
                                    throw new Exception("Login failed. Password does not match Cloud.");
                                }
                                utype = cloudUtype;
                                localAuth.syncUser(user, pass, utype);
                            } else {
                                // Regular User: Local is authority for credentials.
                                // But must validate License & Terminals against Cloud Limits.
                                utype = localUtype;
                            }

                            // --- COMMON ONLINE LICENSE CHECK ---
                            String licenseCid = localAuth.getLicenseCid();

                            /*
                             * // License checks disabled - commented out per request (DNT check license)
                             * if (licenseCid != null) {
                             * LicenseDTO license = cloudAuth.fetchLicense(hddId, mbId, licenseCid);
                             * try {
                             * localAuth.syncLicense(license);
                             * } catch (Exception ex) {
                             * System.err.println("License Sync Failed: " + ex.getMessage());
                             * }
                             * localAuth.validateLicenseDTO(license, utype);
                             * 
                             * // --- TERMINAL AUTHORIZATION (LOCAL DB) ---
                             * int maxTerminals = 0;
                             * try {
                             * if (license.getEno() != null) maxTerminals =
                             * Integer.parseInt(license.getEno());
                             * } catch (NumberFormatException e) {}
                             * 
                             * boolean isAuthorized = localAuth.isTerminalAuthorized(hddId, mbId);
                             * 
                             * if (!isAuthorized) {
                             * if ("License Owner".equalsIgnoreCase(utype)) {
                             * // Owner automatically authorizes the terminal they log in to
                             * localAuth.authorizeTerminal(hddId, mbId, machineName);
                             * } else {
                             * // Regular user cannot authorize new terminals
                             * localAuth.logAccessRequest(hddId, mbId, machineName);
                             * throw new
                             * Exception("This terminal is not authorized. Request sent to Admin.");
                             * }
                             * }
                             * 
                             * // Check Max Terminal Limit (Local Count vs Cloud Limit)
                             * int currentCount = localAuth.getLocalAuthorizedTerminalCount();
                             * if (currentCount > maxTerminals) {
                             * throw new Exception("License Limit Exceeded. Max Terminals: " + maxTerminals
                             * + ", Active: " + currentCount);
                             * }
                             * }
                             */

                        } else {
                            // User NOT found Locally. Try Cloud (License Owner only).
                            String cloudUtype = cloudAuth.authenticate(user, pass, hddId, mbId);
                            if (cloudUtype != null) {
                                utype = cloudUtype;
                                localAuth.syncUser(user, pass, utype);

                                // Sync License
                                // We need License ID. Since this is "Cloud Auth" path for Owner not found
                                // locally,
                                // we assume they must have activated this machine previously or we can't sync
                                // license by ID easily
                                // without the ID being stored locally.
                                // If they are "License Owner" logging in on a FRESH machine that has NEVER
                                // activated:
                                // Previous logic used 'user' (name) to fetch license.
                                // New logic requires 'cid'.
                                // If 'cid' is not in local DB, we can't fetch the license by ID.
                                // SOLUTION: The user MUST activate the software using the Token (which has the
                                // ID) before logging in.

                                String licenseCid = localAuth.getLicenseCid();

                                if (licenseCid != null) {
                                    LicenseDTO license = cloudAuth.fetchLicense(hddId, mbId, licenseCid);
                                    try {
                                        localAuth.syncLicense(license);
                                    } catch (Exception ex) {
                                        System.err.println("License Sync Failed: " + ex.getMessage());
                                    }
                                    localAuth.validateLicenseDTO(license, utype);
                                } else {
                                    // Fallback: If no CID found, user must activate first.
                                    throw new Exception("License ID not found. Please Activate the software first.");
                                }

                                // New Owner on new machine -> Auto Authorize
                                localAuth.authorizeTerminal(hddId, mbId, machineName);
                            } else {
                                throw new Exception("Login failed. Invalid Username or Password.");
                            }
                        }
                    } catch (Exception e) {
                        // Handle explicit failures or Fallback if network issue
                        String msg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
                        if (msg.contains("authorized") ||
                                msg.contains("license limit") ||
                                msg.contains("login failed") ||
                                msg.contains("license not found") ||
                                msg.contains("expired") ||
                                msg.contains("license status")) {
                            throw e;
                        }
                        System.err.println("Cloud Auth Failed (Fallback): " + e.getMessage());
                        // Proceed to offline fallback if localUtype exists
                        if (localUtype == null)
                            throw e;
                        utype = localUtype;
                    }
                }

                // --- OFFLINE / FALLBACK FLOW ---

                if (utype == null) {
                    if (localUtype != null) {
                        utype = localUtype;
                    } else {
                        // Legacy
                        if (checkLegacyLogin(user, pass)) {
                            utype = getLegacyUserType(user, pass);
                        } else {
                            throw new Exception("Login failed. Invalid Username or Password.");
                        }
                    }
                }

                // Offline Validation (License & Terminal)
                // boolean isOwner = "License Owner".equalsIgnoreCase(utype);
                // localAuth.validateLicense(isOwner);
                //
                // if (isOwner) {
                // return utype;
                // } else {
                // if (!localAuth.isTerminalAuthorized(hddId, mbId)) {
                // throw new Exception("This terminal is not authorized locally.");
                // }
                // return utype;
                // }
                // License and terminal checks disabled - allow authenticated users to proceed
                return utype;
            }

            @Override
            protected void done() {
                try {
                    String utype = get();
                    onLoginSuccess(utype, user);
                } catch (Exception ex) {
                    String msg = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
                    JOptionPane.showMessageDialog(login.this, msg, "Login Failed", JOptionPane.ERROR_MESSAGE);
                    resetUI();
                }
            }
        }.execute();
    }

    private boolean checkLegacyLogin(String user, String pass) {
        try {
            String query = "select utype from users where user='" + user + "' and pass='" + pass + "'";
            ResultSet r = util.doQuery(query);
            return r.next();
        } catch (Exception e) {
            return false;
        }
    }

    private String getLegacyUserType(String user, String pass) {
        try {
            String query = "select utype from users where user='" + user + "' and pass='" + pass + "'";
            ResultSet r = util.doQuery(query);
            if (r.next())
                return r.getString(1);
        } catch (Exception e) {
        }
        return "User";
    }

    private void resetUI() {
        closebutton.setEnabled(true);
        closebutton.setText("Login");
        h2.setText("");
        h2.requestFocus();
    }

    private void onLoginSuccess(String utype, String user) {
        // try {
        // Properties prop = new Properties();
        // prop.setProperty("utype", utype);
        // prop.setProperty("user", user);
        // File configDir = new File(drive + ":/" + folder + "/Config_Files");
        // if (!configDir.exists()) configDir.mkdirs();
        // prop.store(new FileOutputStream(new File(configDir, "userlog.properties")),
        // null);
        // } catch (Exception e) {}

        UserSession.setSession(user, utype);
        this.dispose();
        me.get_user_details(utype, user);
        me.setVisible(true);
    }

    private boolean isOnline() {
        if (CloudAuthenticationService.SIMULATION_MODE)
            return true; // Simulate Online
        try {
            URL url = new URL("http://google.com");
            URLConnection con = url.openConnection();
            con.setConnectTimeout(3000);
            con.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String get_hard_disc_Id() {
        String serial = "";
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
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
            } else {
                try {
                    serial = java.net.InetAddress.getLocalHost().getHostName();
                } catch (Exception e) {
                    serial = "UNKNOWN_HDD_ID";
                }
            }
        } catch (IOException e) {
        }
        return serial;
    }

    public static String get_mother_board_id() {
        String result = "";
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                File file = File.createTempFile("GetMBSerial", ".vbs");
                file.deleteOnExit();
                try (FileWriter fw = new FileWriter(file)) {
                    String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
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
            } else {
                try {
                    result = java.net.InetAddress.getLocalHost().getHostAddress();
                } catch (Exception e) {
                    result = "UNKNOWN_MB_ID";
                }
            }
        } catch (IOException e) {
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        h1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        h2 = new javax.swing.JPasswordField();
        closebutton = new javax.swing.JButton();
        closebutton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnActivate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(ColorConstants.PRIMARY_BLUE, 2));
        jPanel1.setLayout(null);

        h1.setBackground(ColorConstants.INPUT_BACKGROUND);
        h1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        h1.setForeground(ColorConstants.TEXT_PRIMARY);
        h1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        h1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, ColorConstants.INPUT_BORDER));
        h1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h1ActionPerformed(evt);
            }
        });
        jPanel1.add(h1);
        h1.setBounds(40, 70, 380, 30);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(ColorConstants.TEXT_SECONDARY);
        jLabel1.setText("Password");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(40, 110, 140, 30);

        h2.setBackground(ColorConstants.INPUT_BACKGROUND);
        h2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        h2.setForeground(ColorConstants.TEXT_PRIMARY);
        h2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        h2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, ColorConstants.INPUT_BORDER));
        h2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h2ActionPerformed(evt);
            }
        });
        jPanel1.add(h2);
        h2.setBounds(40, 140, 380, 30);

        closebutton.setBackground(ColorConstants.BUTTON_PRIMARY);
        closebutton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        closebutton.setForeground(ColorConstants.TEXT_LIGHT);
        closebutton.setIcon(ColorConstants.loadIcon("/icons/login50.png")); // NOI18N
        closebutton.setMnemonic('l');
        closebutton.setText("Login");
        closebutton.setBorder(null);
        closebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebuttonActionPerformed(evt);
            }
        });
        jPanel1.add(closebutton);
        closebutton.setBounds(40, 200, 190, 40);

        closebutton1.setBackground(ColorConstants.BUTTON_SECONDARY);
        closebutton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        closebutton1.setForeground(ColorConstants.TEXT_LIGHT);
        closebutton1.setIcon(ColorConstants.loadIcon("/icons/exit45.png")); // NOI18N
        closebutton1.setMnemonic('c');
        closebutton1.setText("Cancel");
        closebutton1.setBorder(null);
        closebutton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebutton1ActionPerformed(evt);
            }
        });
        jPanel1.add(closebutton1);
        closebutton1.setBounds(230, 200, 190, 40);

        btnActivate.setBackground(ColorConstants.SECONDARY_ORANGE);
        btnActivate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnActivate.setForeground(ColorConstants.TEXT_LIGHT);
        btnActivate.setText("Activate License");
        btnActivate.setBorder(null);
        btnActivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivateActionPerformed(evt);
            }
        });
        jPanel1.add(btnActivate);
        btnActivate.setBounds(40, 260, 380, 40);

        jPanel2.setBackground(ColorConstants.PANEL_HEADER_BACKGROUND);
        jPanel2.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("   Secure Login...");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(0, 0, 460, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 460, 40);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(ColorConstants.TEXT_SECONDARY);
        jLabel4.setText("User Name");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(40, 40, 140, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 460,
                                javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        performLogin();
    }// GEN-LAST:event_closebuttonActionPerformed

    private void btnActivateActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        java.awt.EventQueue.invokeLater(() -> {
            new ActivationPack.activation_home().setVisible(true);
        });
    }

    private void closebutton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebutton1ActionPerformed
        this.dispose();
    }// GEN-LAST:event_closebutton1ActionPerformed

    private void h1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h1ActionPerformed
        h2.requestFocus();
    }// GEN-LAST:event_h1ActionPerformed

    private void h2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h2ActionPerformed
        performLogin();
    }// GEN-LAST:event_h2ActionPerformed

    public static void login_main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        UIManager.put("nimbusFocus", ColorConstants.PRIMARY_BLUE);
        UIManager.put("nimbusSelectionBackground", ColorConstants.PRIMARY_BLUE);
        UIManager.put("nimbusBase", ColorConstants.PRIMARY_BLUE);
        UIManager.put("control", ColorConstants.BACKGROUND_LIGHT);
        java.awt.EventQueue.invokeLater(() -> {
            new login().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActivate;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton closebutton1;
    private javax.swing.JTextField h1;
    private javax.swing.JPasswordField h2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
