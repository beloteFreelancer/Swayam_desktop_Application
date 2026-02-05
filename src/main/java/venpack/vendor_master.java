package venpack;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.selrom.db.DataUtil;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import Utils.ColorConstants;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public final class vendor_master extends javax.swing.JInternalFrame {

    DataUtil util = null;
    AutoCompleteSupport support, support1, support2, support3;

    void button_short() {
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        viewbutton.setText("<html><b>View</b><br>(Alt+V)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        updatebutton.setText("<html><b>Update</b><br>(Alt+U)</h6><html>");

        titlelablel.setText("<html><u>Supplier Master</u></html>");
        setTitle("Supplier Master");
        this.setSize(630, 534);
        javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            this.setFrameIcon(icon);
        }
    }

    void get_sug1() {
        try {
            int count = 0;
            String query = "select distinct cname from vendor";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct cname from vendor";
            set = util.doQuery(query);
            String f[] = new String[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support = AutoCompleteSupport.install(
                    h1, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug2() {
        try {
            int count = 0;
            String query = "select distinct city from vendor";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct city from vendor";
            set = util.doQuery(query);
            String f[] = new String[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support1 = AutoCompleteSupport.install(
                    h5, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug3() {
        try {
            int count = 0;
            String query = "select distinct sname from vendor";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct sname from vendor";
            set = util.doQuery(query);
            String f[] = new String[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support2 = AutoCompleteSupport.install(
                    h9, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug4() {
        try {
            int count = 0;
            String query = "select distinct scode from vendor";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct scode from vendor";
            set = util.doQuery(query);
            String f[] = new String[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support3 = AutoCompleteSupport.install(
                    h10, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void save() {
        try {
            if (h1.getSelectedItem() == null || h1.getSelectedItem() == "") {
                JOptionPane.showMessageDialog(this, "Enter Supplier Name ?", "Supplier Name",
                        JOptionPane.ERROR_MESSAGE);
                h1.requestFocus();
                return;
            }
            if (h2.getText().equals("")) {
                h2.setText(".");
            }
            if (h3.getText().equals("")) {
                h3.setText(".");
            }
            if (h4.getText().equals("")) {
                h4.setText(".");
            }
            if (h5.getSelectedItem() == null || h5.getSelectedItem() == "") {
                h5.setSelectedItem(".");
            }
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
            if (h10.getSelectedItem() == null || h10.getSelectedItem() == "") {
                h10.setSelectedItem(".");
            }
            if (h11.getText().equals("")) {
                h11.setText("" + 0);
            }
            if (h12.getText().equals("")) {
                h12.setText(".");
            }
            if (h13.getText().equals("")) {
                h13.setText(".");
            }
            String cname = h1.getSelectedItem().toString();
            String add1 = h2.getText();
            String add2 = h3.getText();
            String add3 = h4.getText();
            String area = h5.getSelectedItem().toString();
            String mobile = h6.getText();
            String phone = h7.getText();
            String gstno = h8.getText();
            String sname = h9.getSelectedItem().toString();
            String scode = h10.getSelectedItem().toString();
            String duedays = h11.getText();
            String remarks = h12.getText();
            String email = h13.getText();
            boolean selva = false;
            String query = "select distinct cname from vendor where cname='" + cname + "'";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                selva = true;
            }
            if (selva == true) {
                JOptionPane.showMessageDialog(this, "Already Exist!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            query = "insert into vendor values ('" + cname + "','" + add1 + "','" + add2 + "','" + add3 + "','" + area
                    + "','" + mobile + "','" + phone + "','" + gstno + "','" + sname + "','" + scode + "','" + duedays
                    + "','" + remarks + "','" + email + "')";
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
            if (h1.getSelectedItem() == null || h1.getSelectedItem() == "") {
                JOptionPane.showMessageDialog(this, "Enter Supplier Name ?", "Supplier Name",
                        JOptionPane.ERROR_MESSAGE);
                h1.requestFocus();
                return;
            }
            if (h2.getText().equals("")) {
                h2.setText(".");
            }
            if (h3.getText().equals("")) {
                h3.setText(".");
            }
            if (h4.getText().equals("")) {
                h4.setText(".");
            }
            if (h5.getSelectedItem() == null || h5.getSelectedItem() == "") {
                h5.setSelectedItem(".");
            }
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
            if (h10.getSelectedItem() == null || h10.getSelectedItem() == "") {
                h10.setSelectedItem(".");
            }
            if (h11.getText().equals("")) {
                h11.setText("" + 0);
            }
            if (h12.getText().equals("")) {
                h12.setText(".");
            }
            if (h13.getText().equals("")) {
                h13.setText(".");
            }

            String cname = h1.getSelectedItem().toString();
            String add1 = h2.getText();
            String add2 = h3.getText();
            String add3 = h4.getText();
            String area = h5.getSelectedItem().toString();
            String mobile = h6.getText();
            String phone = h7.getText();
            String gstno = h8.getText();
            String sname = h9.getSelectedItem().toString();
            String scode = h10.getSelectedItem().toString();
            String duedays = h11.getText();
            String remarks = h12.getText();
            String email = h13.getText();
            boolean selva = false;
            String query = "select distinct cname from vendor where cname='" + cname + "'";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "Supplier Details Not Found!", "No Records",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            query = "update vendor set add1='" + add1 + "',add2='" + add2 + "',add3='" + add3 + "',city='" + area
                    + "',mobile='" + mobile + "',phone='" + phone + "',gstno='" + gstno + "',sname='" + sname
                    + "',scode='" + scode + "',duedays='" + duedays + "',remarks='" + remarks + "',email='" + email
                    + "' where cname='" + cname + "' ";
            int count = util.doManipulation(query);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Updated Successfully", "Updated", JOptionPane.PLAIN_MESSAGE);
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
            updatebutton.setVisible(false);
            deletebutton.setVisible(false);
            savebutton.setVisible(true);
            viewbutton.setVisible(true);
            get_sug1();
            get_sug2();
            get_sug3();
            get_sug4();

            h2.setText("");
            h3.setText("");
            h4.setText("");
            h6.setText("");
            h7.setText("");
            h8.setText("");
            h11.setText("");
            h12.setText("");
            h1.setEnabled(true);
            h1.requestFocus();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void view() {
        try {
            String query = "select distinct cname,add1,add2,add3,city,mobile,phone,gstno,sname,scode,duedays,remarks from vendor where cname='"
                    + h1.getSelectedItem() + "' ";
            ResultSet set1 = util.doQuery(query);
            boolean selva = false;
            while (set1.next()) {
                h1.setSelectedItem(set1.getString(1));
                h2.setText(set1.getString(2));
                h3.setText(set1.getString(3));
                h4.setText(set1.getString(4));
                h5.setSelectedItem(set1.getString(5));
                h6.setText(set1.getString(6));
                h7.setText(set1.getString(7));
                h8.setText(set1.getString(8));
                h9.setSelectedItem(set1.getString(9));
                h10.setSelectedItem(set1.getString(10));
                h11.setText(set1.getString(11));
                h12.setText(set1.getString(12));
                selva = true;
            }
            if (selva == true) {
                savebutton.setVisible(false);
                viewbutton.setVisible(false);
                updatebutton.setVisible(true);
                deletebutton.setVisible(true);
                h1.setEnabled(false);
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
            String query = "select distinct cname from vendor where cname='" + h1.getSelectedItem() + "'";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            query = "delete from vendor where cname='" + h1.getSelectedItem() + "'";
            int count = util.doManipulation(query);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Deleted Successfully", "Deleted", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public vendor_master(DataUtil util) {
        initComponents();

        this.util = util;
        button_short();
        get_sug1();
        get_sug2();
        get_sug3();
        get_sug4();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        h12 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        h4 = new javax.swing.JTextField();
        h10 = new javax.swing.JComboBox<>();
        savebutton = new javax.swing.JButton();
        viewbutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        deletebutton = new javax.swing.JButton();
        updatebutton = new javax.swing.JButton();
        h5 = new javax.swing.JComboBox<>();
        h2 = new javax.swing.JTextField();
        h3 = new javax.swing.JTextField();
        h1 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        h6 = new javax.swing.JTextField();
        h7 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        h9 = new javax.swing.JComboBox<>();
        h8 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        h11 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        h13 = new javax.swing.JTextField();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Supplier Master");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 150, 30);

        h12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h12);
        h12.setBounds(120, 380, 470, 30);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("GSTIN");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(20, 290, 100, 30);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText(" State Code");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(410, 320, 80, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h4);
        h4.setBounds(120, 140, 470, 30);

        h10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h10);
        h10.setBounds(490, 320, 100, 30);

        savebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        savebutton.setIcon(ColorConstants.loadIcon("/icons/save45.png")); // NOI18N
        savebutton.setMnemonic('s');
        savebutton.setText("Save");
        savebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(savebutton);
        savebutton.setBounds(70, 430, 130, 50);

        viewbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        viewbutton.setIcon(ColorConstants.loadIcon("/icons/view45.png")); // NOI18N
        viewbutton.setMnemonic('v');
        viewbutton.setText("View");
        viewbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(viewbutton);
        viewbutton.setBounds(200, 430, 130, 50);

        clearbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        clearbutton.setIcon(ColorConstants.loadIcon("/icons/clear45.png")); // NOI18N
        clearbutton.setMnemonic('c');
        clearbutton.setText("Clear");
        clearbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(clearbutton);
        clearbutton.setBounds(330, 430, 130, 50);

        closebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        closebutton.setIcon(ColorConstants.loadIcon("/icons/close45.png")); // NOI18N
        closebutton.setMnemonic('o');
        closebutton.setText("Close");
        closebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(closebutton);
        closebutton.setBounds(460, 430, 130, 50);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Address");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(20, 80, 100, 30);

        deletebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        deletebutton.setIcon(ColorConstants.loadIcon("/icons/delete45.png")); // NOI18N
        deletebutton.setMnemonic('d');
        deletebutton.setText("Delete");
        deletebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(deletebutton);
        deletebutton.setBounds(200, 430, 130, 50);

        updatebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        updatebutton.setIcon(ColorConstants.loadIcon("/icons/load45.jpg.png")); // NOI18N
        updatebutton.setMnemonic('u');
        updatebutton.setText("Update");
        updatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(updatebutton);
        updatebutton.setBounds(70, 430, 130, 50);

        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h5);
        h5.setBounds(120, 170, 470, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(120, 80, 470, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h3);
        h3.setBounds(120, 110, 470, 30);

        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h1);
        h1.setBounds(120, 50, 470, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("City /Area");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(20, 170, 100, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Mobile No");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(20, 200, 100, 30);

        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h6);
        h6.setBounds(120, 200, 470, 30);

        h7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h7);
        h7.setBounds(120, 230, 470, 30);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Email");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(20, 260, 100, 30);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Remarks");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(20, 380, 100, 30);

        h9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h9);
        h9.setBounds(120, 320, 290, 30);

        h8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h8);
        h8.setBounds(120, 290, 470, 30);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("State");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(20, 320, 100, 30);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Due Days");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(20, 350, 100, 30);

        h11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h11);
        h11.setBounds(120, 350, 470, 30);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Supplier Name");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 50, 100, 30);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Alt.Contact Nos");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(20, 230, 100, 30);

        h13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h13);
        h13.setBounds(120, 260, 470, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }// GEN-LAST:event_closebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_savebuttonActionPerformed

        save();
        // TODO add your handling code here:
    }// GEN-LAST:event_savebuttonActionPerformed

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_viewbuttonActionPerformed
        if (h1.getSelectedItem() == null || h1.getSelectedItem() == "") {
            JOptionPane.showMessageDialog(this, "Enter Supplier Name ?", "Supplier Name", JOptionPane.ERROR_MESSAGE);
            h1.requestFocus();
            return;
        }
        view();

    }// GEN-LAST:event_viewbuttonActionPerformed

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deletebuttonActionPerformed
        delete();
    }// GEN-LAST:event_deletebuttonActionPerformed

    private void updatebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_updatebuttonActionPerformed
        update();
        // TODO add your handling code here:
    }// GEN-LAST:event_updatebuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbuttonActionPerformed
        clear();
        // TODO add your handling code here:
    }// GEN-LAST:event_clearbuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton deletebutton;
    private javax.swing.JComboBox<String> h1;
    private javax.swing.JComboBox<String> h10;
    private javax.swing.JTextField h11;
    private javax.swing.JTextField h12;
    private javax.swing.JTextField h13;
    private javax.swing.JTextField h2;
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h4;
    private javax.swing.JComboBox<String> h5;
    private javax.swing.JTextField h6;
    private javax.swing.JTextField h7;
    private javax.swing.JTextField h8;
    private javax.swing.JComboBox<String> h9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JButton updatebutton;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
