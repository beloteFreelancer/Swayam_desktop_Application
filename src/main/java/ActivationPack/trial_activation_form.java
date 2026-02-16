package ActivationPack;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.selrom.db.DataUtil;
import com.selrom.db.Database;

import java.sql.Connection;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.UIManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.JOptionPane;
import menupack.login;
import menupack.menu_form;

/**
 *
 * @author Selrom Software
 */
public final class trial_activation_form extends javax.swing.JFrame {
    DataUtil util = null;
    Connection c;
    boolean active = false;
    final String secretKey = "!@#$%^&*()_+;.,|";
    AutoCompleteSupport support;

    void get_defaults() {
        setSize(600, 572);
        setLocationRelativeTo(getRootPane());
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
        Date d = new Date();
        SimpleDateFormat g = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
        String date = g.format(d);
        String time = g1.format(d);
        h2.setText(date);
        h3.setText(time);
    }

    void get_connection() {
        try {
            String password = "VMfO8TllTQoc6Llw";
            String username = "3gKZsiyCbqqdp7s.root";
            String url = "jdbc:mysql://gateway01.ap-southeast-1.prod.aws.tidbcloud.com:4000/pos_license?createDatabaseIfNotExist=true";
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            c = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
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

    void get_details() {
        try {
            String status = "", hard = "", mother = "", entryno = "";

            String query = "select status,hname,mname,eno from setting_user";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                status = r.getString(1);
                hard = r.getString(2);
                mother = r.getString(3);
                entryno = r.getString(4);
            }
            status = AES.decrypt(status, secretKey);
            hard = AES.decrypt(hard, secretKey);
            mother = AES.decrypt(mother, secretKey);
            String hard1 = get_hard_disc_Id();
            String mother1 = get_mother_board_id();
            entryno = AES.decrypt(entryno, secretKey);
            if (status.equalsIgnoreCase("Activated")) {
                if (hard1.equals(hard) && mother1.equals(mother)) {
                    h1.setText(entryno);
                    get_activated_details_from_web_server();
                }
            } // activated ends
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }
    }

    void get_activated_details_from_web_server() {
        try {
            PreparedStatement t;
            ResultSet r;
            t = c.prepareStatement(
                    "select date_format(dat,'%d/%m/%Y'),tim,bname,category,cname,mobile,phone,email,country,state from customers1  where sno=?");
            t.setObject(1, h1.getText());
            r = t.executeQuery();
            while (r.next()) {
                h2.setText(r.getString(1));
                h3.setText(r.getString(2));
                h7.setText(r.getString(3));
                h8.setSelectedItem(r.getString(4));
                h9.setText(r.getString(5));
                h10.setText(r.getString(6));
                h11.setText(r.getString(7));
                h12.setText(r.getString(8));
                h13.setText(r.getString(9));
                h14.setText(r.getString(10));
                active = true;
            }
            if (active == true) {
                h7.setEnabled(false);
                savebutton.setVisible(false);
                updatebutton.setVisible(true);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    void get_default_values() {
        if (h9.getText().equals("")) {
            h9.setText(".");
        }
        if (h11.getText().equals("")) {
            h11.setText(".");
        }
        if (h12.getText().equals("")) {
            h12.setText(".");
        }
        if (h13.getText().equals("")) {
            h13.setText(".");
        }
        if (h14.getText().equals("")) {
            h14.setText(".");
        }
        if (h8.getSelectedItem() == null || h8.getSelectedItem() == "") {
            h8.setSelectedItem(".");
        }
    }

    public boolean isUsernameTaken(String username) {
        try {
            Connection conn = Database.getInstance().getConnection();
            try (PreparedStatement ps = conn
                    .prepareStatement("SELECT count(*) FROM users WHERE LOWER(user)=LOWER(?)")) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0)
                        return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    void save() {
        try {
            if (h7.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Business Name ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h7.requestFocus();
                return;
            }
            if (h10.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Mobile No ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h10.requestFocus();
                return;
            }
            get_default_values();

            PreparedStatement t;
            int aa = JOptionPane.showConfirmDialog(this, "<html>Want to Activate 'Trial Version' ? </b></html>",
                    "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (aa == JOptionPane.NO_OPTION) {
                return;
            }
            boolean selva = false;
            int sno = 3912;
            ResultSet r;
            t = c.prepareStatement("select max(sno) from customers1");
            r = t.executeQuery();
            while (r.next()) {
                selva = true;
                sno = r.getInt(1);
            }
            if (selva == true) {
                sno = sno + 1;
            }
            h1.setText("" + sno);
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            SimpleDateFormat g2 = new SimpleDateFormat("dd-MM-yyyy");
            String date = g.format(d);
            String time = g1.format(d);
            String last = g2.format(d) + "  " + g1.format(d);
            String eno = h1.getText();
            String status = "Activated";
            String version = "Trial Version";
            String hard = get_hard_disc_Id();
            String mother = get_mother_board_id();
            String bname = h7.getText();
            c.setAutoCommit(false);
            t = c.prepareStatement("insert into customers1 values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            t.setObject(1, h1.getText());
            t.setObject(2, date);
            t.setObject(3, time);
            t.setObject(4, h5.getText());
            t.setObject(5, h4.getText());
            t.setObject(6, h6.getText());
            t.setObject(7, h7.getText());
            t.setObject(8, h8.getSelectedItem());
            t.setObject(9, h9.getText());
            t.setObject(10, h10.getText());
            t.setObject(11, h11.getText());
            t.setObject(12, h12.getText());
            t.setObject(13, h13.getText());
            t.setObject(14, h14.getText());
            int as = t.executeUpdate();
            if (as > 0) {
                status = AES.encrypt(status, secretKey);
                date = AES.encrypt(date, secretKey);
                version = AES.encrypt(version, secretKey);
                hard = AES.encrypt(hard, secretKey);
                mother = AES.encrypt(mother, secretKey);
                bname = AES.encrypt(bname, secretKey);
                eno = AES.encrypt(eno, secretKey);
                String key1 = "....,,,,,$$$$%%%%%", cid = ".";

                String query1 = "delete from setting_user";
                int b = util.doManipulation(query1);

                query1 = "insert into setting_user values ('" + status + "','" + date + "','" + version + "','"
                        + hard + "','" + mother + "','" + cid + "','" + bname + "','" + key1 + "','" + eno + "',"
                        + "NULL,NULL,NULL,NULL,'" + g.format(new Date()) + "')";
                int a = util.doManipulation(query1);

                if (a > 0) {
                    c.commit();
                    // write folder
                    menu_form me = new menu_form();
                    String drive = me.getDrive();
                    String folder = me.getFoler();
                    Properties prop = new Properties();
                    prop.setProperty("last_sta", status);
                    prop.setProperty("last_hard", hard);
                    prop.setProperty("last_moth", mother);
                    prop.setProperty("last_ke", key1);

                    // Create directories if they don't exist
                    // Fix path: if drive is empty, use folder as absolute; else use drive + ":/" +
                    // ...
                    String filePath;
                    if (drive != null && !drive.isEmpty()) {
                        filePath = drive + File.separator + "Drafts" + File.separator + "Drafts" + File.separator
                                + "Drafts_Logs.drafts";
                    } else {
                        filePath = folder + File.separator + "Drafts" + File.separator + "Drafts" + File.separator
                                + "Drafts_Logs.drafts";
                    }
                    File file = new File(filePath);
                    File parentDir = file.getParentFile();
                    if (parentDir != null && !parentDir.exists()) {
                        parentDir.mkdirs();
                    }
                    prop.store(new FileOutputStream(file), null);
                    // write folder ends

                    // Insert record in users table using license username and password
                    String licenseUsername = licenseOwnerUsername.getText();
                    String licensePassword = new String(licenseOwnerPassword.getPassword());
                    if (!licenseUsername.isEmpty() && !licensePassword.isEmpty()) {
                        String hashedPassword = Utils.PasswordUtils.hashPassword(licensePassword);

                        // Store the plain password in Utils for later use

                        // Check if user exists
                        Connection conn = Database.getInstance().getConnection();
                        boolean exists = isUsernameTaken(licenseUsername);

                        // If creating License Owner, ensure no conflict (handled by isUsernameTaken

                        String last1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").format(new Date());

                        if (exists) {
                            String update = "UPDATE users SET pass=?, utype=?, last=? WHERE user=?";
                            try (PreparedStatement ps = conn.prepareStatement(update)) {
                                ps.setString(1, hashedPassword);
                                ps.setString(2, "License Owner");
                                ps.setString(3, last1);
                                ps.setString(4, licenseUsername);
                                ps.executeUpdate();
                            } catch (SQLException e) {
                                System.err.println("Error updating License Owner user: " + e.getMessage());
                                // JOptionPane.showMessageDialog(this, "<html><h3>Error updating License Owner
                                // user: " + e.getMessage() + "</h3></html>",
                            }
                        } else {
                            String insert = "INSERT INTO users VALUES ('License Owner','" +
                                    licenseUsername + "','" + hashedPassword + "','" + last1 + "')";
                            try {

                                int result = util.doManipulation(insert);
                                System.err.println(
                                        "Inserted License Owner user: " + licenseUsername + " with result: " + result);
                                if (result > 0)
                                    JOptionPane.showMessageDialog(this,
                                            "<html><h3>License Owner User Created Successfully</h3></html>",
                                            "User Created", JOptionPane.PLAIN_MESSAGE);
                            } catch (SQLException e) {
                                System.err.println("Error inserting License Owner user: " + e.getMessage());
                                // JOptionPane.showMessageDialog(this, "<html><h3>Error creating License Owner
                                // user: " + e.getMessage() + "</h3></html>",
                            }
                        }
                    }

                    JOptionPane.showMessageDialog(this, "<html><h3>License Activated Successfully</h3></html>",
                            "Activated", JOptionPane.PLAIN_MESSAGE);
                    this.dispose();
                    new login().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Please Try Again...");
                }

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    void update() {
        try {
            get_default_values();
            int as = JOptionPane.showConfirmDialog(this, "Want to Update Details ?", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            if (h10.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Mobile No ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h10.requestFocus();
                return;
            }
            PreparedStatement t;
            String eno = h1.getText();
            String category = h8.getSelectedItem().toString();
            String cname = h9.getText();
            String mobile = h10.getText();
            String phone = h11.getText();
            String email = h12.getText();
            String country = h13.getText();
            String state = h14.getText();
            t = c.prepareStatement("update customers1 set category='" + category + "',cname='" + cname + "',mobile='"
                    + mobile + "',phone='" + phone + "',email='" + email + "',country='" + country + "',state='" + state
                    + "' where sno='" + eno + "'  ");
            int a = t.executeUpdate();
            if (a > 0) {
                JOptionPane.showMessageDialog(this, "Updated Successfully", "Updated", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please Try Again...");
            }
        } catch (HeadlessException | SQLException e) {
            System.out.print(e);
        }
    }

    final void get_sug() {
        try {
            int count = 0;
            PreparedStatement t;
            t = c.prepareStatement("select distinct category from customers1");
            ResultSet set = t.executeQuery();
            while (set.next()) {
                count = count + 1;
            }
            t = c.prepareStatement("select distinct category from customers1");
            set = t.executeQuery();
            String f[] = new String[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support = AutoCompleteSupport.install(h8, GlazedLists.eventListOf(f));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public trial_activation_form() {
        initComponents();
        util = new DataUtil();
        get_defaults();
        get_connection();
        get_sug();
        get_details();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        javax.swing.JLabel licenseOwnerLabel = new javax.swing.JLabel();
        javax.swing.JLabel licenseOwnerPassLabel = new javax.swing.JLabel();
        licenseOwnerUsername = new javax.swing.JTextField();
        licenseOwnerPassword = new javax.swing.JPasswordField();

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        h3 = new javax.swing.JTextField();
        h2 = new javax.swing.JTextField();
        h1 = new javax.swing.JTextField();
        h4 = new javax.swing.JTextField();
        h5 = new javax.swing.JTextField();
        h6 = new javax.swing.JTextField();
        h8 = new javax.swing.JComboBox<>();
        h7 = new javax.swing.JTextField();
        h9 = new javax.swing.JTextField();
        h10 = new javax.swing.JTextField();
        h11 = new javax.swing.JTextField();
        h12 = new javax.swing.JTextField();
        h13 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        h14 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        savebutton = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        updatebutton = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 102), 3));
        jPanel1.setLayout(null);
        // License Owner Username Label
        licenseOwnerLabel.setFont(new java.awt.Font("Arial", 0, 14));
        licenseOwnerLabel.setText("License Owner Username");
        jPanel1.add(licenseOwnerLabel);
        licenseOwnerLabel.setBounds(20, 390, 200, 30);
        // License Owner Username Field
        licenseOwnerUsername.setFont(new java.awt.Font("Arial", 0, 14));
        jPanel1.add(licenseOwnerUsername);
        licenseOwnerUsername.setBounds(250, 390, 200, 30);

        // License Owner Password Label
        licenseOwnerPassLabel.setFont(new java.awt.Font("Arial", 0, 14));
        licenseOwnerPassLabel.setText("License Owner Password");
        jPanel1.add(licenseOwnerPassLabel);
        licenseOwnerPassLabel.setBounds(20, 430, 200, 30);
        // License Owner Password Field
        licenseOwnerPassword.setFont(new java.awt.Font("Arial", 0, 14));
        jPanel1.add(licenseOwnerPassword);
        licenseOwnerPassword.setBounds(250, 430, 200, 30);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Entry No");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(20, 50, 100, 30);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Product");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 80, 100, 30);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Product Details");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(20, 110, 100, 30);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Category");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(20, 200, 100, 30);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Contact Person");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(20, 230, 100, 30);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Mobile No");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(20, 260, 100, 30);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText(" Alt.Contact Nos");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(230, 260, 110, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Email");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(20, 290, 100, 30);

        h3.setEditable(false);
        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h3);
        h3.setBounds(460, 50, 120, 30);

        h2.setEditable(false);
        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h2);
        h2.setBounds(360, 50, 100, 30);

        h1.setEditable(false);
        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setText("--");
        h1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h1FocusGained(evt);
            }
        });
        jPanel1.add(h1);
        h1.setBounds(120, 50, 240, 30);

        h4.setEditable(false);
        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h4.setText("Swayam Bill BOOK");
        jPanel1.add(h4);
        h4.setBounds(120, 80, 240, 30);

        h5.setEditable(false);
        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h5.setText("Version 1.0");
        jPanel1.add(h5);
        h5.setBounds(360, 80, 220, 30);

        h6.setEditable(false);
        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h6.setText("General Retail & Wholesale Software");
        jPanel1.add(h6);
        h6.setBounds(120, 110, 460, 30);

        h8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        jPanel1.add(h8);
        h8.setBounds(120, 200, 460, 30);

        h7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h7);
        h7.setBounds(120, 170, 460, 30);

        h9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h9);
        h9.setBounds(120, 230, 460, 30);

        h10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h10);
        h10.setBounds(120, 260, 110, 30);

        h11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h11);
        h11.setBounds(340, 260, 240, 30);

        h12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h12);
        h12.setBounds(120, 290, 460, 30);

        h13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h13);
        h13.setBounds(120, 320, 460, 30);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Country ");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(20, 320, 100, 30);

        h14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h14);
        h14.setBounds(120, 350, 460, 30);

        jButton1.setBackground(new java.awt.Color(204, 0, 59));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(153, 153, 255));
        jButton1.setText("Exit");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(480, 490, 100, 40);

        jButton3.setBackground(new java.awt.Color(204, 0, 59));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(153, 153, 255));
        jButton3.setText("Clear");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(380, 490, 100, 40);

        jPanel2.setBackground(new java.awt.Color(204, 0, 102));
        jPanel2.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(" Trial Version Activation");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(0, 0, 610, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 610, 40);

        savebutton.setBackground(new java.awt.Color(204, 0, 59));
        savebutton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        savebutton.setForeground(new java.awt.Color(153, 153, 255));
        savebutton.setText("Activate Trial Version");
        savebutton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        savebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebuttonActionPerformed(evt);
            }
        });
        jPanel1.add(savebutton);
        savebutton.setBounds(190, 490, 190, 40);

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("Business Name");
        jPanel1.add(jLabel18);
        jLabel18.setBounds(20, 170, 100, 30);

        updatebutton.setBackground(new java.awt.Color(204, 0, 59));
        updatebutton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        updatebutton.setForeground(new java.awt.Color(153, 153, 255));
        updatebutton.setText("Update Details");
        updatebutton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        updatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebuttonActionPerformed(evt);
            }
        });
        jPanel1.add(updatebutton);
        updatebutton.setBounds(190, 490, 190, 40);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("State");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(20, 350, 100, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }// GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
        h7.setText("");
        h9.setText("");
        h10.setText("");
        h11.setText("");
        h12.setText("");
        h13.setText("");
        h14.setText("");
        support.uninstall();
        get_sug();
        h7.requestFocus();

    }// GEN-LAST:event_jButton3ActionPerformed

    private void updatebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_updatebuttonActionPerformed
        update();
    }// GEN-LAST:event_updatebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_savebuttonActionPerformed
        save();
    }// GEN-LAST:event_savebuttonActionPerformed

    private void h1FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h1FocusGained
        h7.requestFocus();
    }// GEN-LAST:event_h1FocusGained

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(trial_activation_form.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        UIManager.put("nimbusFocus", new Color(153, 0, 153, 255));
        UIManager.put("nimbusSelectionBackground", new Color(153, 0, 153, 255));
        UIManager.put("nimbusBase", new Color(153, 0, 153, 225));
        UIManager.put("control", new Color(255, 255, 255, 255));
        java.awt.EventQueue.invokeLater(() -> {
            new trial_activation_form().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h10;
    private javax.swing.JTextField h11;
    private javax.swing.JTextField h12;
    private javax.swing.JTextField h13;
    private javax.swing.JTextField h14;
    private javax.swing.JTextField h2;
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h4;
    private javax.swing.JTextField h5;
    private javax.swing.JTextField h6;
    private javax.swing.JTextField h7;
    private javax.swing.JComboBox<String> h8;
    private javax.swing.JTextField h9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton savebutton;
    private javax.swing.JButton updatebutton;
    private javax.swing.JTextField licenseOwnerUsername;
    private javax.swing.JPasswordField licenseOwnerPassword;
    // End of variables declaration//GEN-END:variables
}
