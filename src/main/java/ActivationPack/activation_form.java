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
import Utils.PasswordUtils;

/**
 *
 * @author Selrom Software
 */
public final class activation_form extends javax.swing.JFrame {
    DataUtil util = null;
    Connection c;
    boolean active = false;
    final String secretKey = "!@#$%^&*()_+;.,|";
    AutoCompleteSupport support;

    // New fields for SuperAdmin
    private javax.swing.JTextField superAdminUsername;
    private javax.swing.JPasswordField superAdminPassword;

    void get_defaults() {
        setSize(600, 740);
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
            System.out.println(e.toString());
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
                    "select date_format(dat,'%d/%m/%Y'),tim,license,ctype,cid,bname,category,cname,mobile,phone,email,country,state,remarks,ref,rdetails from customers  where sno=?");
            t.setObject(1, h1.getText());
            r = t.executeQuery();
            while (r.next()) {
                h2.setText(r.getString(1));
                h3.setText(r.getString(2));
                h18.setText(r.getString(3));

                h19.setSelectedItem(r.getString(4));
                h20.setText(r.getString(5));
                h7.setText(r.getString(6));
                h8.setSelectedItem(r.getString(7));
                h9.setText(r.getString(8));
                h10.setText(r.getString(9));
                h11.setText(r.getString(10));
                h12.setText(r.getString(11));
                h13.setText(r.getString(12));
                h14.setText(r.getString(13));
                h15.setText(r.getString(14));

                h16.setSelectedItem(r.getString(15));
                h17.setText(r.getString(16));
                active = true;
            }
            if (active == true) {
                h19.setEnabled(false);
                h20.setEnabled(false);
                h7.setEnabled(false);
                h18.setEnabled(false);
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
        if (h15.getText().equals("")) {
            h15.setText(".");
        }
        if (h17.getText().equals("")) {
            h17.setText(".");
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
            if (h18.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter License Key ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h18.requestFocus();
                return;
            }
            if (h18.getText().length() < 36) {
                JOptionPane.showMessageDialog(this, "Invalid License Key ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h18.requestFocus();
                return;
            }
            get_default_values();
            String key1 = h18.getText();
            String cid = h20.getText();
            PreparedStatement t;
            String key_status = "Activated";
            t = c.prepareStatement("select status from call_logs where key1=?");
            t.setObject(1, key1);
            ResultSet r = t.executeQuery();
            while (r.next()) {
                key_status = r.getString(1);
            }
            // customer type is old
            if (h19.getSelectedIndex() == 1) {
                boolean cust_type = false;
                t = c.prepareStatement("select cid from customers where cid=?");
                t.setObject(1, cid);
                r = t.executeQuery();
                while (r.next()) {
                    cust_type = true;
                }
                if (cust_type == false) {
                    JOptionPane.showMessageDialog(this, "Invalid Customers Details!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } // Existing Customer Ends
            else if (h19.getSelectedIndex() == 0) {
                boolean ch = false;
                int billno = 3114;
                t = c.prepareStatement("select max(cid) from customers");
                r = t.executeQuery();
                while (r.next()) {
                    ch = true;
                    billno = r.getInt(1);
                }
                if (ch == true) {
                    billno = billno + 1;
                }
                h20.setText(billno + "");
            } // New Customer Ends

            if (!key_status.equals("Available")) {
                JOptionPane.showMessageDialog(this, "Please Contact Customer Care!", "Invalid License Key",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int aa = JOptionPane.showConfirmDialog(this,
                    "<html>Want to Activate this Key: <b>'" + h18.getText() + "' ? </b></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (aa == JOptionPane.NO_OPTION) {
                return;
            }
            boolean selva = false;
            int sno = 3725;
            t = c.prepareStatement("select max(sno) from customers");
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
            String version = "Full Version";
            String hard = get_hard_disc_Id();
            String mother = get_mother_board_id();
            String bname = h7.getText();
            cid = h20.getText();
            c.setAutoCommit(false);
            t = c.prepareStatement("insert into customers values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            t.setObject(1, h1.getText());
            t.setObject(2, date);
            t.setObject(3, time);
            t.setObject(4, h5.getText());
            t.setObject(5, h4.getText());
            t.setObject(6, h6.getText());
            t.setObject(7, h18.getText());
            t.setObject(8, h19.getSelectedItem());
            t.setObject(9, h20.getText());
            t.setObject(10, h7.getText());
            t.setObject(11, h8.getSelectedItem());
            t.setObject(12, h9.getText());
            t.setObject(13, h10.getText());
            t.setObject(14, h11.getText());
            t.setObject(15, h12.getText());
            t.setObject(16, h13.getText());
            t.setObject(17, h14.getText());
            t.setObject(18, h15.getText());
            t.setObject(19, h16.getSelectedItem());
            t.setObject(20, h17.getText());
            int as = t.executeUpdate();
            t = c.prepareStatement("update call_logs set status='Activated', eno='" + eno + "', cid='" + cid
                    + "', last='" + last + "' where key1='" + key1 + "' ");
            int vb = t.executeUpdate();
            if (as > 0 && vb > 0) {
                status = AES.encrypt(status, secretKey);
                date = AES.encrypt(date, secretKey);
                version = AES.encrypt(version, secretKey);
                hard = AES.encrypt(hard, secretKey);
                System.out.println("Mother Board ID before encryption: " + get_mother_board_id());
                mother = AES.encrypt(get_mother_board_id(), secretKey);
                System.out.println("Mother Board ID after encryption: " + mother);
                cid = AES.encrypt(cid, secretKey);
                bname = AES.encrypt(bname, secretKey);
                key1 = AES.encrypt(key1, secretKey);
                eno = AES.encrypt(eno, secretKey);

                boolean selvakumar = false;
                String query1 = "";
                String query = "select status from setting_user";
                ResultSet r1 = util.doQuery(query);
                while (r1.next()) {
                    selvakumar = true;
                }
                if (selvakumar == false) {
                    query1 = "insert into setting_user values ('" + status + "','" + date + "','" + version + "','"
                            + hard + "','" + mother + "','" + cid + "','" + bname + "','" + key1 + "','" + eno + "',"
                            + "NULL,NULL,NULL,NULL,'" + g.format(new Date()) + "')";
                } else if (selvakumar == true) {
                    query1 = "update setting_user set status='" + status + "',dat='" + date + "',vname='" + version
                            + "',hname='" + hard + "',mname='" + mother + "',cid='" + cid + "',cname='" + bname
                            + "',uname='" + key1 + "',eno='" + eno + "' ";
                }
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
                    String licenseUsername = superAdminUsername.getText();
                    String licensePassword = new String(superAdminPassword.getPassword());
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
            String remarks = h15.getText();
            String ref = h16.getSelectedItem().toString();
            String rname = h17.getText();

            t = c.prepareStatement("update customers set category='" + category + "',cname='" + cname + "',mobile='"
                    + mobile + "',phone='" + phone + "',email='" + email + "',country='" + country + "',state='" + state
                    + "',remarks='" + remarks + "',ref='" + ref + "',rdetails='" + rname + "' where sno='" + eno
                    + "'  ");
            int a = t.executeUpdate();
            if (a > 0) {
                JOptionPane.showMessageDialog(this, "Updated Successfully", "Updated", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "2:Please Try Again...");
            }
        } catch (HeadlessException | SQLException e) {
            System.out.print(e);
        }
    }

    void customer_type() {
        if (h19.getSelectedIndex() == 0) {
            h20.setText("--");
            h20.setEnabled(false);
        } else {
            h20.setText("");
            h20.setEnabled(true);
        }
    }

    void get_customers_details() {
        try {
            PreparedStatement t;
            ResultSet r;
            boolean selva = false;
            if (h20.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Customer Id ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h20.requestFocus();
                return;
            }
            t = c.prepareStatement(
                    "select bname,category,cname,mobile,phone,email,country,state,ref,rdetails from customers where cid=? ");
            t.setObject(1, h20.getText());
            r = t.executeQuery();
            while (r.next()) {
                h7.setText(r.getString(1));
                h8.setSelectedItem(r.getString(2));
                h9.setText(r.getString(3));
                h10.setText(r.getString(4));
                h11.setText(r.getString(5));
                h12.setText(r.getString(6));
                h13.setText(r.getString(7));
                h14.setText(r.getString(8));
                h16.setSelectedItem(r.getString(9));
                h17.setText(r.getString(10));
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        }
    }

    final void get_sug() {
        try {
            int count = 0;
            PreparedStatement t;
            t = c.prepareStatement("select distinct category from customers");
            ResultSet set = t.executeQuery();
            while (set.next()) {
                count = count + 1;
            }
            t = c.prepareStatement("select distinct category from customers");
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

    public activation_form() {
        initComponents();
        util = new DataUtil();
        get_defaults();
        get_connection();
        get_sug();
        customer_type();
        get_details();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel superAdminLabel = new javax.swing.JLabel();
        javax.swing.JLabel superAdminPassLabel = new javax.swing.JLabel();
        superAdminUsername = new javax.swing.JTextField();
        superAdminPassword = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        h3 = new javax.swing.JTextField();
        h18 = new javax.swing.JTextField();
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
        h15 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        h14 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        h17 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        savebutton = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        h19 = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        h20 = new javax.swing.JTextField();
        h16 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        updatebutton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 102), 3));
        jPanel1.setLayout(null);
        // Increase the height of jPanel1
        jPanel1.setBounds(0, 0, 707, 850); // Increased height from 750 to 850

        // License Owner Username Label
        superAdminLabel.setFont(new java.awt.Font("Arial", 0, 14));
        superAdminLabel.setText("License Owner Username");
        jPanel1.add(superAdminLabel);
        superAdminLabel.setBounds(20, 520, 200, 30);
        // License Owner Username Field
        superAdminUsername.setFont(new java.awt.Font("Arial", 0, 14));
        jPanel1.add(superAdminUsername);
        superAdminUsername.setBounds(250, 520, 200, 30);

        // License Owner Password Label
        superAdminPassLabel.setFont(new java.awt.Font("Arial", 0, 14));
        superAdminPassLabel.setText("License Owner Password");
        jPanel1.add(superAdminPassLabel);
        superAdminPassLabel.setBounds(20, 560, 200, 30);
        // License Owner Password Field
        superAdminPassword.setFont(new java.awt.Font("Arial", 0, 14));
        jPanel1.add(superAdminPassword);
        superAdminPassword.setBounds(250, 560, 200, 30);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Activation Key");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 600, 120, 40);

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

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText(" Customer Id");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(360, 160, 100, 30);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Category");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(20, 220, 100, 30);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Contact Person");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(20, 250, 100, 30);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Mobile No");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(20, 280, 100, 30);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText(" Alt.Contact Nos");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(230, 280, 110, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Email");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(20, 310, 100, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("State");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(20, 370, 100, 30);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Cust_Remarks");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(20, 400, 110, 30);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Reference");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(20, 450, 100, 30);

        h3.setEditable(false);
        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h3);
        h3.setBounds(460, 50, 120, 30);

        h18.setBackground(new java.awt.Color(255, 204, 204));
        h18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        h18.setForeground(new java.awt.Color(0, 0, 204));
        h18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        h18.setBorder(null);
        jPanel1.add(h18);
        h18.setBounds(120, 600, 460, 40);

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
        h8.setBounds(120, 220, 460, 30);

        h7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h7);
        h7.setBounds(120, 190, 460, 30);

        h9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h9);
        h9.setBounds(120, 250, 460, 30);

        h10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h10);
        h10.setBounds(120, 280, 110, 30);

        h11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h11);
        h11.setBounds(340, 280, 240, 30);

        h12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h12);
        h12.setBounds(120, 310, 460, 30);

        h13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h13);
        h13.setBounds(120, 340, 460, 30);

        h15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h15);
        h15.setBounds(120, 400, 460, 30);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Country ");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(20, 340, 100, 30);

        h14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h14);
        h14.setBounds(120, 370, 460, 30);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("Ref.Details");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(20, 480, 100, 30);

        h17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h17);
        h17.setBounds(120, 480, 460, 30);

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
        jButton1.setBounds(500, 680, 70, 40);

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
        jButton3.setBounds(430, 680, 70, 40);

        jPanel2.setBackground(new java.awt.Color(204, 0, 102));
        jPanel2.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(" Activation Form");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(0, 0, 610, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 610, 40);

        savebutton.setBackground(new java.awt.Color(204, 0, 59));
        savebutton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        savebutton.setForeground(new java.awt.Color(153, 153, 255));
        savebutton.setText("Activate Full Version");
        savebutton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        savebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebuttonActionPerformed(evt);
            }
        });
        jPanel1.add(savebutton);
        savebutton.setBounds(100, 680, 180, 40);

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("Business Name");
        jPanel1.add(jLabel18);
        jLabel18.setBounds(20, 190, 100, 30);

        h19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h19.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "New Customer", "Existing Customer" }));
        h19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h19ActionPerformed(evt);
            }
        });
        jPanel1.add(h19);
        h19.setBounds(120, 160, 240, 30);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText("Customer Type");
        jPanel1.add(jLabel19);
        jLabel19.setBounds(20, 160, 100, 30);

        h20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(h20);
        h20.setBounds(451, 160, 100, 30);

        h16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Customer Reference", "Dealer /Reseller",
                "Online Search", "Employee Reference", "Other Sources" }));
        jPanel1.add(h16);
        h16.setBounds(120, 450, 460, 30);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search22.png"))); // NOI18N
        jPanel1.add(jButton2);
        jButton2.setBounds(550, 160, 30, 30);

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
        updatebutton.setBounds(280, 680, 150, 40);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE));

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
        h15.setText("");
        h16.setSelectedIndex(0);
        h17.setText("");
        h18.setText("");
        support.uninstall();
        get_sug();
        h19.setSelectedIndex(0);
        h20.setText("");
        h19.requestFocus();

    }// GEN-LAST:event_jButton3ActionPerformed

    private void h19ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h19ActionPerformed
        customer_type();
    }// GEN-LAST:event_h19ActionPerformed

    private void updatebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_updatebuttonActionPerformed
        update();
    }// GEN-LAST:event_updatebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_savebuttonActionPerformed
        save();
    }// GEN-LAST:event_savebuttonActionPerformed

    private void h1FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h1FocusGained
        h19.requestFocus();
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
            java.util.logging.Logger.getLogger(activation_form.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        UIManager.put("nimbusFocus", new Color(153, 0, 153, 255));
        UIManager.put("nimbusSelectionBackground", new Color(153, 0, 153, 255));
        UIManager.put("nimbusBase", new Color(153, 0, 153, 225));
        UIManager.put("control", new Color(255, 255, 255, 255));
        java.awt.EventQueue.invokeLater(() -> {
            new activation_form().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h10;
    private javax.swing.JTextField h11;
    private javax.swing.JTextField h12;
    private javax.swing.JTextField h13;
    private javax.swing.JTextField h14;
    private javax.swing.JTextField h15;
    private javax.swing.JComboBox<String> h16;
    private javax.swing.JTextField h17;
    private javax.swing.JTextField h18;
    private javax.swing.JComboBox<String> h19;
    private javax.swing.JTextField h2;
    private javax.swing.JTextField h20;
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h4;
    private javax.swing.JTextField h5;
    private javax.swing.JTextField h6;
    private javax.swing.JTextField h7;
    private javax.swing.JComboBox<String> h8;
    private javax.swing.JTextField h9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton savebutton;
    private javax.swing.JButton updatebutton;
    // End of variables declaration//GEN-END:variables
}
