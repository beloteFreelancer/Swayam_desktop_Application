package loyaltypack;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.selrom.db.DataUtil;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class cust_points_withdraw extends javax.swing.JInternalFrame {

    DataUtil util = null;
    String username = "", utype = "";
    sample2 s3 = new sample2();
    sample2 s2 = new sample2();
    ResultSet r;
    AutoCompleteSupport support, support1;

    void button_short() {
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");

        titlelablel.setText("<html><u>Customer Points Withdrawal</u></html>");
        setTitle("Customer Points Withdrawal ");
        this.setSize(539, 459);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
    }

    void get_defaults() {
        try {
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            h2.setText(g.format(d));
            h3.setText(g1.format(d));
            menupack.menu_form me = new menupack.menu_form();
            username = me.getUsername();
            utype = me.getUserType();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    final void get_mobileno() {
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
            support = AutoCompleteSupport.install(h5, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    final void get_cid() {
        try {
            int count = 0;
            String query = "select cid from cust_points";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select cid from cust_points";
            set = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support1 = AutoCompleteSupport.install(h4, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_billno() {
        try {
            int sno = 1;
            String query = "select max(sno) from cust_points1";
            r = util.doQuery(query);
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

    void save() {
        try {
            if (h4.getSelectedItem() == null || h4.getSelectedItem() == "") {
                JOptionPane.showMessageDialog(this, "Enter Customer Id ?", "Customer Id", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }
            if (h5.getSelectedItem() == null || h5.getSelectedItem() == "") {
                JOptionPane.showMessageDialog(this, "Enter Customer Mobile No ?", "Mobile No", JOptionPane.ERROR_MESSAGE);
                h5.requestFocus();
                return;
            }
            if (h7.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Invalid Customer Details ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }
            if (h10.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Invalid Total Points!", "Invalid", JOptionPane.ERROR_MESSAGE);
                h10.requestFocus();
                return;
            }
            if (h6.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Points to Withdraw ?", "Points", JOptionPane.ERROR_MESSAGE);
                h6.requestFocus();
                return;
            }

            if (h7.getText().equals("")) {
                h7.setText(".");
            }
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText());
            String date = (new SimpleDateFormat("yyyy/MM/dd").format(nm));

            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);
            get_billno();
            String sno = h1.getText();
            String time = h3.getText();

            String cid = h4.getSelectedItem().toString();
            String mobile = h5.getSelectedItem().toString();
            String points = h6.getText();
            String remarks = h7.getText();

            boolean selva = false;
            String query = "select sno from cust_points1 where sno='" + sno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                JOptionPane.showMessageDialog(this, "Entry No Already Exist!", "Already Exist", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }
            if (utype.equalsIgnoreCase("User")) {
                JOptionPane.showMessageDialog(this, "Login as 'Administrator' to Withdraw Points!", "Permission Restricted", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int aa = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (aa == JOptionPane.NO_OPTION) {
                return;
            }
            ArrayList query_batch = new ArrayList();
            query_batch.add("insert into cust_points1 values ('" + sno + "','" + date + "','" + time + "','" + cid + "','" + mobile + "','" + points + "','" + remarks + "','" + username + "','" + last + "')");
            query_batch.add("update cust_points set points=points-" + points + " where cid='" + cid + "'");
            int count = util.doManipulation_Batch(query_batch);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Saved Successfully</h1></html>", "Saved", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    void clear() {
        try {
            h1.setText("--");
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            h2.setText(g.format(d));
            h3.setText(g1.format(d));
            h4.setSelectedItem("");
            h5.setSelectedItem("");
            h6.setText("");
            h7.setText("");
            h8.setText("");
            h9.setText("");
            h10.setText("");
            savebutton.setVisible(true);
            h4.setEnabled(true);
            h4.requestFocus();
            if (s2.getRowCount() > 0) {
                s2.getDataVector().removeAllElements();
                s2.fireTableDataChanged();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void get_customer_details() {
        try {
            h8.setText("");
            h9.setText("");
            h10.setText("");
            h5.setSelectedItem("");
            String query = "select cname,city,mobile from cust where cid='" + h4.getSelectedItem() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                h8.setText(r.getString(1));
                h9.setText(r.getString(2));
                h5.setSelectedItem(r.getString(3));
            }
            query = "select points from cust_points where cid='" + h4.getSelectedItem() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                h10.setText("" + (int) r.getDouble(1));
            }
        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_customer_details_using_mobile() {
        try {
            h8.setText("");
            h9.setText("");
            h10.setText("");
            h4.setSelectedItem("");
            String query = "select cname,city,cid from cust where mobile='" + h5.getSelectedItem() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                h8.setText(r.getString(1));
                h9.setText(r.getString(2));
                h4.setSelectedItem(r.getString(3));
            }
            query = "select points from cust_points where cid='" + h4.getSelectedItem() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                h10.setText("" + (int) r.getDouble(1));
            }
        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public cust_points_withdraw(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();
        get_mobileno();
        get_cid();
        h4.requestFocus();
        get_defaults();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        closebutton = new javax.swing.JButton();
        savebutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        h7 = new javax.swing.JTextField();
        h1 = new javax.swing.JTextField();
        h2 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        h3 = new javax.swing.JTextField();
        jCalendarButton1 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        h6 = new javax.swing.JTextField();
        h5 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        h4 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        h10 = new javax.swing.JTextField();
        h8 = new javax.swing.JTextField();
        h9 = new javax.swing.JTextField();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Customer Points Withdrawal ");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 330, 30);

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
        closebutton.setBounds(370, 350, 130, 50);

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
        savebutton.setBounds(110, 350, 130, 50);

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
        clearbutton.setBounds(240, 350, 130, 50);

        jLabel23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel23.setText("Cust_Id");
        getContentPane().add(jLabel23);
        jLabel23.setBounds(20, 100, 80, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Date");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(20, 70, 80, 30);

        h7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h7);
        h7.setBounds(100, 300, 400, 30);

        h1.setEditable(false);
        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setText("--");
        h1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h1FocusGained(evt);
            }
        });
        getContentPane().add(h1);
        h1.setBounds(100, 40, 400, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(100, 70, 240, 30);

        jLabel27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel27.setText("Entry No");
        getContentPane().add(jLabel27);
        jLabel27.setBounds(20, 40, 80, 30);

        h3.setEditable(false);
        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h3);
        h3.setBounds(380, 70, 120, 30);

        jCalendarButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
        jCalendarButton1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton1PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton1);
        jCalendarButton1.setBounds(340, 70, 40, 30);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText("Mobile No");
        getContentPane().add(jLabel19);
        jLabel19.setBounds(20, 130, 80, 30);

        jLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel20.setText("Withdraw");
        getContentPane().add(jLabel20);
        jLabel20.setBounds(20, 270, 80, 30);

        h6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        h6.setForeground(new java.awt.Color(255, 0, 0));
        h6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(h6);
        h6.setBounds(100, 270, 400, 30);

        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h5);
        h5.setBounds(100, 130, 280, 30);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search22.png"))); // NOI18N
        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(380, 130, 120, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h4);
        h4.setBounds(100, 100, 280, 30);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search22.png"))); // NOI18N
        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(380, 100, 120, 30);

        jLabel30.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel30.setText("Total Points");
        getContentPane().add(jLabel30);
        jLabel30.setBounds(20, 220, 80, 30);

        jLabel31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel31.setText("Remarks");
        getContentPane().add(jLabel31);
        jLabel31.setBounds(20, 300, 80, 30);

        jLabel32.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel32.setText("Name");
        getContentPane().add(jLabel32);
        jLabel32.setBounds(20, 160, 80, 30);

        jLabel33.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel33.setText("Area /City");
        getContentPane().add(jLabel33);
        jLabel33.setBounds(20, 190, 80, 30);

        h10.setEditable(false);
        h10.setBackground(new java.awt.Color(255, 255, 204));
        h10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        h10.setForeground(new java.awt.Color(0, 153, 0));
        h10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(h10);
        h10.setBounds(100, 220, 400, 30);

        h8.setEditable(false);
        h8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h8);
        h8.setBounds(100, 160, 400, 30);

        h9.setEditable(false);
        h9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h9);
        h9.setBounds(100, 190, 400, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        save();
    }//GEN-LAST:event_savebuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        clear();
    }//GEN-LAST:event_clearbuttonActionPerformed

    private void h1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h1FocusGained

        h4.requestFocus();
    }//GEN-LAST:event_h1FocusGained

    private void jCalendarButton1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendarButton1PropertyChange

        try {
            if (evt.getNewValue() instanceof Date) {
                String ses = evt.getNewValue().toString();
                Date nm = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(ses);
                String date = (new SimpleDateFormat("dd/MM/yyyy").format(nm));
                h2.setText(date);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jCalendarButton1PropertyChange

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        get_customer_details();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        get_customer_details_using_mobile();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h10;
    private javax.swing.JTextField h2;
    private javax.swing.JTextField h3;
    private javax.swing.JComboBox<String> h4;
    private javax.swing.JComboBox<String> h5;
    private javax.swing.JTextField h6;
    private javax.swing.JTextField h7;
    private javax.swing.JTextField h8;
    private javax.swing.JTextField h9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    // End of variables declaration//GEN-END:variables
}
