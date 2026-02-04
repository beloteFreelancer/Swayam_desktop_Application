package custpack;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.selrom.db.DataUtil;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class cust_master extends javax.swing.JInternalFrame {

    DataUtil util = null;
    AutoCompleteSupport support, support1, support2, support3, support4;
    String state_name = "", state_code = "";

    void button_short() {
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        viewbutton.setText("<html><b>View</b><br>(Alt+V)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        updatebutton.setText("<html><b>Update</b><br>(Alt+U)</h6><html>");
        titlelablel.setText("<html><u>Customer Master</u></html>");

        setTitle("Customer Master");
        this.setSize(666, 617);
        java.net.URL imgUrl = getClass().getResource("/images/icon.png");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            this.setFrameIcon(icon);
        }
    }

    void get_cid() {
        try {
            int sno = 1;
            String query = "select max(cid) from cust";
            ResultSet r = util.doQuery(query);
            boolean selva = false;
            while (r.next()) {
                selva = true;
                sno = r.getInt(1);
            }
            if (selva == true) {
                sno = sno + 1;
            }
            h1.setText("" + sno);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_state_name_code() {
        try {
            String query = "select state,scode from setting_bill";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                state_name = r.getString(1);
                state_code = r.getString(2);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug1() {
        try {
            int count = 0;
            String query = "select distinct cname from cust";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct cname from cust";
            set = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support = AutoCompleteSupport.install(h4, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug2() {
        try {
            int count = 0;
            String query = "select distinct city from cust";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct city from cust";
            set = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support1 = AutoCompleteSupport.install(h9, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug3() {
        try {
            int count = 0;
            String query = "select distinct sname from cust";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct sname from cust";
            set = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support2 = AutoCompleteSupport.install(h12, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug4() {
        try {
            int count = 0;
            String query = "select distinct scode from cust";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct scode from cust";
            set = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support3 = AutoCompleteSupport.install(h13, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug5() {
        try {
            int count = 0;
            String query = "select distinct mobile from cust";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct mobile from cust";
            set = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support4 = AutoCompleteSupport.install(h5, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_validate() {
        if (h6.getText().equals("")) {
            h6.setText(".");
        }
        if (h7.getText().equals("")) {
            h7.setText(".");
        }
        if (h8.getText().equals("")) {
            h8.setText(".");
        }
        if (h9.getSelectedItem() == null || h9.getSelectedItem() == "") {
            h9.setSelectedItem(".");
        }
        if (h10.getText().equals("")) {
            h10.setText(".");
        }
        if (h11.getText().equals("")) {
            h11.setText(".");
        }
        if (h12.getSelectedItem() == null || h12.getSelectedItem() == "") {
            h12.setSelectedItem(".");
        }
        if (h13.getSelectedItem() == null || h13.getSelectedItem() == "") {
            h13.setSelectedItem(".");
        }
        if (h14.getText().equals("")) {
            h14.setText("" + 0);
        }
        if (h15.getText().equals("")) {
            h15.setText("" + 0);
        }
        if (h16.getText().equals("")) {
            h16.setText(".");
        }
        if (h17.getText().equals("")) {
            h17.setText(".");
        }
    }

    void save() {
        try {
            if (h4.getSelectedItem() == null || h4.getSelectedItem() == "") {
                JOptionPane.showMessageDialog(this, "Enter Customer Name ?", "Customer Name", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }
            if (h5.getSelectedItem() == null || h5.getSelectedItem() == "") {
                JOptionPane.showMessageDialog(this, "Enter Mobile No ?", "Mobile No", JOptionPane.ERROR_MESSAGE);
                h5.requestFocus();
                return;
            }
            if (h5.getSelectedItem().toString().length() != 10) {
                JOptionPane.showMessageDialog(this, "Invalid Mobile No!", "Invalid", JOptionPane.ERROR_MESSAGE);
                h5.requestFocus();
                return;
            }

            get_cid();
            get_validate();
            get_state_name_code();
            if (h3.getText().equals("")) {
                h3.setText(h1.getText());
            }
            String cid = h1.getText();
            String ctype = h2.getSelectedItem().toString();
            String cardno = h3.getText();
            String cname = h4.getSelectedItem().toString();
            String mobile = h5.getSelectedItem().toString();
            String add1 = h6.getText();
            String add2 = h7.getText();
            String add3 = h8.getText();
            String area = h9.getSelectedItem().toString();
            String phone = h10.getText();
            String gstno = h11.getText();
            String sname = h12.getSelectedItem().toString();
            String scode = h13.getSelectedItem().toString();
            String climit = h14.getText();
            String duedays = h15.getText();
            String remarks = h16.getText();
            String email = h17.getText();
            if (sname.equals(".")) {
                sname = state_name;
            }
            if (scode.equals(".")) {
                scode = state_code;
            }
            boolean selva = false;
            String query = "select distinct cname from cust where cname='" + cname + "'";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                selva = true;
            }
            if (selva == true) {
                JOptionPane.showMessageDialog(this, "Already Exist!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            query = "insert into cust values ('" + cid + "','" + ctype + "','" + cardno + "','" + cname + "','" + add1 + "','" + add2 + "','" + add3 + "','" + area + "','" + mobile + "','" + phone + "','" + gstno + "','" + sname + "','" + scode + "','" + climit + "','" + duedays + "','" + remarks + "','" + email + "')";
            int count = util.doManipulation(query);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Saved Successfully", "Saved", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void update() {
        try {
            if (h4.getSelectedItem() == null || h4.getSelectedItem() == "") {
                JOptionPane.showMessageDialog(this, "Enter Customer Name ?", "Customer Name", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }
            if (h5.getSelectedItem() == null || h5.getSelectedItem() == "") {
                JOptionPane.showMessageDialog(this, "Enter Mobile No ?", "Mobile No", JOptionPane.ERROR_MESSAGE);
                h5.requestFocus();
                return;
            }
            if (h5.getSelectedItem().toString().length() != 10) {
                JOptionPane.showMessageDialog(this, "Invalid Mobile No!", "Invalid", JOptionPane.ERROR_MESSAGE);
                h5.requestFocus();
                return;
            }

            get_validate();
            get_state_name_code();
            if (h3.getText().equals("")) {
                h3.setText(h1.getText());
            }
            String cid = h1.getText();
            String ctype = h2.getSelectedItem().toString();
            String cardno = h3.getText();
            String cname = h4.getSelectedItem().toString();
            String mobile = h5.getSelectedItem().toString();
            String add1 = h6.getText();
            String add2 = h7.getText();
            String add3 = h8.getText();
            String area = h9.getSelectedItem().toString();
            String phone = h10.getText();
            String gstno = h11.getText();
            String sname = h12.getSelectedItem().toString();
            String scode = h13.getSelectedItem().toString();
            String climit = h14.getText();
            String duedays = h15.getText();
            String remarks = h16.getText();
            String email = h17.getText();
            if (sname.equals(".")) {
                sname = state_name;
            }
            if (scode.equals(".")) {
                scode = state_code;
            }
            boolean selva = false;
            String query = "select distinct cname from cust where cid='" + cid + "'";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "Customer Details Not Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            query = "update cust set  cardno='" + cardno + "',ctype='" + ctype + "',cname='" + cname + "',add1='" + add1 + "',add2='" + add2 + "',add3='" + add3 + "',city='" + area + "',mobile='" + mobile + "',phone='" + phone + "',gstno='" + gstno + "',sname='" + sname + "',scode='" + scode + "',climit='" + climit + "',duedays='" + duedays + "',remarks='" + remarks + "',email='" + email + "' where cid='" + cid + "' ";

            int count = util.doManipulation(query);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Updated Successfully", "Saved", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void clear() {
        try {
            support.uninstall();
            support1.uninstall();
            support2.uninstall();
            support3.uninstall();
            support4.uninstall();
            updatebutton.setVisible(false);
            deletebutton.setVisible(false);
            savebutton.setVisible(true);
            viewbutton.setVisible(true);
            get_sug1();
            get_sug2();
            get_sug3();
            get_sug4();
            get_sug5();

            h6.setText("");
            h7.setText("");
            h8.setText("");
            h5.setSelectedItem("");
            h10.setText("");
            h11.setText("");
            h15.setText("");
            h16.setText("");
            h4.setEnabled(true);
            h2.requestFocus();
            h17.setText("");
            h2.setSelectedIndex(0);
            h3.setText("");
            h14.setText("");
            h1.setText("--");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void view(String cid) {
        try {
            String query = "select distinct cid,ctype,cardno,cname,mobile,add1,add2,add3,city,phone,gstno,sname,scode,climit,duedays,remarks,email from cust where cid='" + cid + "' ";
            ResultSet set1 = util.doQuery(query);
            boolean selva = false;
            while (set1.next()) {
                h1.setText(set1.getString(1));
                h2.setSelectedItem(set1.getString(2));
                h3.setText(set1.getString(3));
                h4.setSelectedItem(set1.getString(4));
                h5.setSelectedItem(set1.getString(5));
                h6.setText(set1.getString(6));
                h7.setText(set1.getString(7));
                h8.setText(set1.getString(8));
                h9.setSelectedItem(set1.getString(9));
                h10.setText(set1.getString(10));
                h11.setText(set1.getString(11));
                h12.setSelectedItem(set1.getString(12));
                h13.setSelectedItem(set1.getString(13));
                h14.setText(set1.getString(14));
                h15.setText(set1.getString(15));
                h16.setText(set1.getString(16));
                h17.setText(set1.getString(17));
                selva = true;
            }
            if (selva == true) {
                savebutton.setVisible(false);
                viewbutton.setVisible(false);
                updatebutton.setVisible(true);
                deletebutton.setVisible(true);
            } else if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void delete() {
        try {
            int as = JOptionPane.showConfirmDialog(this, "Want to Delete ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            boolean selva = false;
            String cid = h1.getText();
            String query = "select distinct cname from cust where cid='" + cid + "'";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            query = "delete from cust where cid='" + h1.getText() + "'";
            int count = util.doManipulation(query);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Deleted Successfully", "Deleted", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public cust_master(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();
        get_sug1();
        get_sug2();
        get_sug3();
        get_sug4();
        get_sug5();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        h16 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        h8 = new javax.swing.JTextField();
        h13 = new javax.swing.JComboBox<>();
        savebutton = new javax.swing.JButton();
        viewbutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        deletebutton = new javax.swing.JButton();
        updatebutton = new javax.swing.JButton();
        h9 = new javax.swing.JComboBox<>();
        h6 = new javax.swing.JTextField();
        h7 = new javax.swing.JTextField();
        h4 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        h10 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        h12 = new javax.swing.JComboBox<>();
        h11 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        h15 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        h1 = new javax.swing.JTextField();
        h2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        h14 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        h3 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        h17 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        h5 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Customer Master");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 180, 30);

        h16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h16);
        h16.setBounds(150, 470, 470, 30);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Credit Limit");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(30, 440, 120, 30);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("GSTIN");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(30, 380, 120, 30);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText(" State Code");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(440, 410, 80, 30);

        h8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h8);
        h8.setBounds(150, 260, 470, 30);

        h13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h13);
        h13.setBounds(520, 410, 100, 30);

        savebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        savebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save45.png"))); // NOI18N
        savebutton.setMnemonic('s');
        savebutton.setText("Save");
        savebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(savebutton);
        savebutton.setBounds(100, 520, 130, 50);

        viewbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        viewbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/view45.png"))); // NOI18N
        viewbutton.setMnemonic('v');
        viewbutton.setText("View");
        viewbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(viewbutton);
        viewbutton.setBounds(230, 520, 130, 50);

        clearbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        clearbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/clear45.png"))); // NOI18N
        clearbutton.setMnemonic('c');
        clearbutton.setText("Clear");
        clearbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(clearbutton);
        clearbutton.setBounds(360, 520, 130, 50);

        closebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        closebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close45.png"))); // NOI18N
        closebutton.setMnemonic('o');
        closebutton.setText("Close");
        closebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(closebutton);
        closebutton.setBounds(490, 520, 130, 50);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Address");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(30, 200, 120, 30);

        deletebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        deletebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete45.png"))); // NOI18N
        deletebutton.setMnemonic('d');
        deletebutton.setText("Delete");
        deletebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(deletebutton);
        deletebutton.setBounds(230, 520, 130, 50);

        updatebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        updatebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/load45.jpg.png"))); // NOI18N
        updatebutton.setMnemonic('u');
        updatebutton.setText("Update");
        updatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(updatebutton);
        updatebutton.setBounds(100, 520, 130, 50);

        h9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h9);
        h9.setBounds(150, 290, 470, 30);

        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h6);
        h6.setBounds(150, 200, 470, 30);

        h7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h7);
        h7.setBounds(150, 230, 470, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h4);
        h4.setBounds(150, 110, 430, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("City /Area");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(30, 290, 120, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Mobile No");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(30, 170, 120, 30);

        h10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h10);
        h10.setBounds(150, 320, 470, 30);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Email");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(30, 350, 120, 30);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Remarks");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(30, 470, 120, 30);

        h12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h12);
        h12.setBounds(150, 410, 290, 30);

        h11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h11);
        h11.setBounds(150, 380, 470, 30);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("State");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(30, 410, 120, 30);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText(" Due Days");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(440, 440, 80, 30);

        h15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h15);
        h15.setBounds(520, 440, 100, 30);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Customer Name");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(30, 110, 120, 30);

        h1.setEditable(false);
        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setText("--");
        h1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h1FocusGained(evt);
            }
        });
        getContentPane().add(h1);
        h1.setBounds(150, 50, 470, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "General", "Credit Customer" }));
        getContentPane().add(h2);
        h2.setBounds(150, 80, 470, 30);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Customer Id");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(30, 50, 120, 30);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Customer Card No");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(30, 140, 120, 30);

        h14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h14);
        h14.setBounds(150, 440, 290, 30);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Customer Type");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(30, 80, 120, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h3);
        h3.setBounds(150, 140, 470, 30);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("Alt.Contact Nos");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(30, 320, 120, 30);

        h17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h17);
        h17.setBounds(150, 350, 470, 30);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search22.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(580, 170, 40, 30);

        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h5);
        h5.setBounds(150, 170, 430, 30);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search22.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(580, 110, 40, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_closebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed

        save();
        // TODO add your handling code here:
    }//GEN-LAST:event_savebuttonActionPerformed

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewbuttonActionPerformed
        Object cid = JOptionPane.showInputDialog(this, "Enter Customer Id ?", "Customer Id", JOptionPane.PLAIN_MESSAGE);
        if ("".equals(cid) || cid == null) {
            JOptionPane.showMessageDialog(this, "Invalid Customer Id!", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        view(cid.toString());

    }//GEN-LAST:event_viewbuttonActionPerformed

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebuttonActionPerformed
        delete();
    }//GEN-LAST:event_deletebuttonActionPerformed

    private void updatebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatebuttonActionPerformed
        update();
        // TODO add your handling code here:
    }//GEN-LAST:event_updatebuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        clear();
        // TODO add your handling code here:
    }//GEN-LAST:event_clearbuttonActionPerformed

    private void h1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h1FocusGained
        h2.requestFocus();
// TODO add your handling code here:
    }//GEN-LAST:event_h1FocusGained

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            if (h5.getSelectedItem() == null || h5.getSelectedItem() == "") {
                JOptionPane.showMessageDialog(this, "Enter Mobile No ?", "Mobile No", JOptionPane.ERROR_MESSAGE);
                h5.requestFocus();
                return;
            }
            boolean selva = false;
            String cid = "";
            String query = "select cid from cust where mobile='" + h5.getSelectedItem() + "' order by cid limit 1";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                cid = r.getString(1);
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            } else {
                view(cid);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            if (h4.getSelectedItem() == null || h4.getSelectedItem() == "") {
                JOptionPane.showMessageDialog(this, "Enter Name ?", "Name", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }
            boolean selva = false;
            String cid = "";
            String query = "select cid from cust where cname='" + h4.getSelectedItem() + "' order by cid limit 1";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                cid = r.getString(1);
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            } else {
                view(cid);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton deletebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h10;
    private javax.swing.JTextField h11;
    private javax.swing.JComboBox<String> h12;
    private javax.swing.JComboBox<String> h13;
    private javax.swing.JTextField h14;
    private javax.swing.JTextField h15;
    private javax.swing.JTextField h16;
    private javax.swing.JTextField h17;
    private javax.swing.JComboBox<String> h2;
    private javax.swing.JTextField h3;
    private javax.swing.JComboBox<String> h4;
    private javax.swing.JComboBox<String> h5;
    private javax.swing.JTextField h6;
    private javax.swing.JTextField h7;
    private javax.swing.JTextField h8;
    private javax.swing.JComboBox<String> h9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JButton updatebutton;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
