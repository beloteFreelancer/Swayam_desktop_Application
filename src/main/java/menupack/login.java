package menupack;

import ActivationPack.AES;
import ActivationPack.trial_version_expired;
import com.selrom.db.DataUtil;
import com.selrom.db.Database;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.apache.poi.hpsf.Variant;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.PointerByReference;
import java.util.ArrayList;
import java.util.List;

public final class login extends javax.swing.JFrame {
    DataUtil util = null;
    menu_form me = null;
    String drive = "C";
    String folder = "Retail_POS_Files";
    final String secretKey = "!@#$%^&*()_+;.,|";
    int days_left = 0;
    String what_version = ".";

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

    void get_defaults() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
    }

    void get_login() {
        try {
            if (h1.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter User Name ?", "User Name", JOptionPane.ERROR_MESSAGE);
                h1.requestFocus();
                return;
            }
            if (h2.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Password ?", "Password", JOptionPane.ERROR_MESSAGE);
                h2.requestFocus();
                return;
            }
            boolean selva = false;
            String utype = "";

            String authenticatedUtype = authenticate(h1.getText(), h2.getText());
            System.out.println("Authenticated User Type: " + authenticatedUtype);
            if (authenticatedUtype != null) {
                selva = true;
                utype = authenticatedUtype;
                System.out.println("User Type: " + utype);
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "Invalid User Name or Password!", "Invalid",
                        JOptionPane.ERROR_MESSAGE);
                h1.requestFocus();
            } else {
                // Initialize menu_form only when login is successful
                if (me == null) {
                    me = new menu_form();
                }

                Properties prop = new Properties();
                prop.setProperty("utype", utype);
                prop.setProperty("user", h1.getText());
                prop.store(new FileOutputStream(drive + ":/" + folder + "/Config_Files/userlog.properties"), null);
                this.dispose();
                me.get_user_details(utype, h1.getText());
                me.getUsername();
                me.getUserType();
                me.setVisible(true);
            }
        } catch (HeadlessException | IOException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String get_hard_disc_Id() {
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
            // System.out.println("Hard Disc Serial No: "+serial);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return serial;
    }

    public static String getMotherboardIdFixed() {
        try {
            File vbs = File.createTempFile("mbserial", ".vbs");
            vbs.deleteOnExit();

            String script = """
                    Set objWMIService = GetObject("winmgmts:\\\\.\\root\\cimv2")
                    Set colItems = objWMIService.ExecQuery("SELECT * FROM Win32_BaseBoard")
                    For Each objItem in colItems
                        WScript.Echo objItem.SerialNumber
                    Next
                    """;

            try (var fw = new java.io.FileWriter(vbs)) {
                fw.write(script);
            }

            Process p = Runtime.getRuntime().exec("cscript //nologo \"" + vbs.getAbsolutePath() + "\"");
            var reader = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line.trim());
            }

            return sb.toString().trim();

        } catch (Exception e) {
            return "Error: " + e.getMessage();

        }
    }

    public static String get_mother_board_id() {
        String result = "";
        try {
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
            // System.out.println("Mother Board ID: "+result);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return result;
    }

    /*
     * public static String get_mother_board_id() {
     * String result = "";
     * try {
     * File file = File.createTempFile("GetMBSerial", ".vbs");
     * file.deleteOnExit();
     * try (FileWriter fw = new FileWriter(file)) {
     * String vbs =
     * "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
     * + "Set colItems = objWMIService.ExecQuery _ \n"
     * + "   (\"Select * from Win32_ComputerSystemProduct\") \n"
     * + "For"
     * + "3 Each objItem in colItems \n"
     * + "    Wscript.Echo objItem.IdentifyingNumber \n"
     * + "Next \n";
     * 
     * fw.write(vbs);
     * }
     * Process gWMI = Runtime.getRuntime().exec("cscript //NoLogo " +
     * file.getPath());
     * try (BufferedReader input = new BufferedReader(new
     * InputStreamReader(gWMI.getInputStream()))) {
     * String line;
     * while ((line = input.readLine()) != null) {
     * result += line;
     * }
     * }
     * // System.out.println("Mother Board ID: "+result);
     * } catch (IOException e) {
     * System.out.println(e.toString());
     * }
     * return result;
     * }
     */
    void get_license() {
        try {
            String ip;
            try (FileInputStream m = new FileInputStream(drive + ":/" + folder + "/Config_Files/Connect.txt")) {
                Properties p = new Properties(null);
                p.load(m);
                ip = p.getProperty("server_ip");
            }
            if (ip.equalsIgnoreCase("localhost")) {
                boolean fresh = false;
                String status = "Not", date = "", version = "", hard = "", mother = "", key1 = "";
                String query = "select status,dat,vname,hname,mname,uname from setting_user";
                ResultSet r = util.doQuery(query);
                while (r.next()) {
                    fresh = true;
                    status = r.getString(1);
                    date = r.getString(2);
                    version = r.getString(3);
                    hard = r.getString(4);
                    mother = r.getString(5);
                    key1 = r.getString(6);
                }
                if (fresh == false) {
                    this.dispose();
                    new ActivationPack.activation_home().setVisible(true);
                    return;
                }
                String mother1 = get_mother_board_id();
                String hard1 = get_hard_disc_Id();
                status = AES.decrypt(status, secretKey);
                date = AES.decrypt(date, secretKey);
                version = AES.decrypt(version, secretKey);
                hard = AES.decrypt(hard, secretKey);
                mother = AES.decrypt(mother, secretKey);
                if (!status.equals("Activated")) {
                    this.dispose();
                    new ActivationPack.activation_home().setVisible(true);
                    return;
                }

                if (status.equals("Activated") && version.equals("Trial Version")) {
                    if (!mother1.equals(mother) && !hard1.equals(hard)) {
                        this.dispose();
                        new ActivationPack.activation_home().setVisible(true);
                        return;
                    }
                    what_version = "Trial Version";
                    Date d = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String end_date = format.format(d);
                    Date d1 = format.parse(date);
                    Date d2 = format.parse(end_date);
                    long diff = d2.getTime() - d1.getTime();
                    long diffDays = diff / (24 * 60 * 60 * 1000);
                    if (diffDays > 15) {
                        this.dispose();
                        new trial_version_expired().setVisible(true);
                        return;
                    } // days greaterthan 15
                    else {
                        days_left = 15 - (int) diffDays;
                        new login().setVisible(true);
                    }
                    return;
                } // trial version ends here

                if (status.equals("Activated") && version.equals("Full Version")) {
                    what_version = "Full Version";
                    key1 = AES.decrypt(key1, secretKey);

                    String fmother = "", fhard = "", fkey = "";
                    String filePath = drive + ":/" + folder + "/Drafts/Drafts/Drafts_Logs.drafts";
                    System.out.println("File Path: " + filePath);
                    System.out.println("Motherboard ID: " + mother1);
                    System.out.println("Hard Disk ID: " + hard1);

                    File draftsFile = new File(filePath);
                    System.err.println("Drafts File Exists: " + draftsFile.exists());

                    if (draftsFile.exists()) {
                        try (FileInputStream m = new FileInputStream(filePath)) {
                            Properties p = new Properties(null);
                            p.load(m);
                            fmother = p.getProperty("last_moth");
                            fhard = p.getProperty("last_hard");
                            fkey = p.getProperty("last_ke");
                        }
                        fmother = AES.decrypt(fmother, secretKey);
                        fhard = AES.decrypt(fhard, secretKey);
                        fkey = AES.decrypt(fkey, secretKey);
                    }
                    System.out.println("File Motherboard ID: " + fmother);
                    System.out.println("File Hard Disk ID: " + fhard);
                    System.out.println("File Key ID: " + fkey);
                    System.out.println("Database Motherboard ID: " + mother);
                    System.out.println("Database Hard Disk ID: " + hard);
                    System.out.println("Database Key ID: " + key1);
                    System.out.println("Motherboard : " + mother1);
                    System.out.println("Hard Disk : " + hard1);
                    System.out.println("Motherboard Match: " + mother1.equals(mother));
                    System.out.println("Hard Disk Match: " + hard1.equals(hard));

                    if (mother1.equals(mother) && hard1.equals(hard)) {
                        if (draftsFile.exists() && fmother.equals(mother) && fhard.equals(hard) && fkey.equals(key1)) {
                            new login().setVisible(true);
                        } else if (!draftsFile.exists()) {
                            new login().setVisible(true);
                        } else {
                            this.dispose();
                            new trial_version_expired().setVisible(true);
                        }
                    } // motherbaord & harddisc is equan /matching
                } // full version ends here
            } // Local Host Ends
            else { // IP Not Local Host starts
                new login().setVisible(true);
            } // IP not Local Host

        } catch (IOException | ClassNotFoundException | SQLException | ParseException e) {
            System.out.println(e);
        }
    }

    public login() {
        initComponents();
        util = new DataUtil();
        get_defaults();
        setLocationRelativeTo(getRootPane());
        setResizable(false);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 102), 2));
        jPanel1.setLayout(null);

        h1.setBackground(new java.awt.Color(242, 231, 231));
        h1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        h1.setForeground(new java.awt.Color(51, 51, 51));
        h1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        h1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));
        h1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h1ActionPerformed(evt);
            }
        });
        jPanel1.add(h1);
        h1.setBounds(40, 70, 380, 30);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setText("Password");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(40, 110, 140, 30);

        h2.setBackground(new java.awt.Color(242, 231, 231));
        h2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        h2.setForeground(new java.awt.Color(51, 51, 51));
        h2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        h2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));
        h2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h2ActionPerformed(evt);
            }
        });
        jPanel1.add(h2);
        h2.setBounds(40, 140, 380, 30);

        closebutton.setBackground(new java.awt.Color(204, 0, 59));
        closebutton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        closebutton.setForeground(new java.awt.Color(204, 204, 204));
        closebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/login50.png"))); // NOI18N
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

        closebutton1.setBackground(new java.awt.Color(204, 0, 59));
        closebutton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        closebutton1.setForeground(new java.awt.Color(204, 204, 204));
        closebutton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/exit45.png"))); // NOI18N
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

        jPanel2.setBackground(new java.awt.Color(204, 0, 102));
        jPanel2.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("   Secure Login...");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(0, 0, 460, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 460, 40);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
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
                                javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        get_login();
    }// GEN-LAST:event_closebuttonActionPerformed

    private void closebutton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebutton1ActionPerformed
        this.dispose();
    }// GEN-LAST:event_closebutton1ActionPerformed

    private void h1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h1ActionPerformed
        h2.requestFocus();
    }// GEN-LAST:event_h1ActionPerformed

    private void h2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h2ActionPerformed
        get_login();
    }// GEN-LAST:event_h2ActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex)

        {
            System.out.println("Look and Feel: " + ex);
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        UIManager.put("nimbusFocus", new Color(153, 0, 153, 255));
        UIManager.put("nimbusSelectionBackground", new Color(153, 0, 153, 255));
        UIManager.put("nimbusBase", new Color(153, 0, 153, 225));
        UIManager.put("control", new Color(255, 255, 255, 255));

        java.awt.EventQueue.invokeLater(() -> {

            System.out.println("Starting Login...");
            // new login();
            new login().get_license();

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
