package accountspack;

import com.selrom.db.DataUtil;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import menupack.menu_form;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class voucher_trasfer extends javax.swing.JInternalFrame {

    DataUtil util = null;
    String user, utype;
    int hmany = 2;

    final void button_short() {
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        viewbutton.setText("<html><b>View</b><br>(Alt+V)</h6><html>");
        alterbutton.setText("<html><b>Alter /Update</b><br>(Alt+U)</h6><html>");

        prebutton.setText("<html><b>Last Entry</b><br>(Alt+R)</h6><html>");
        nextbutton.setText("<html><b>Next Entry</b><br>(Alt+N)</h6><html>");

        titlelablel.setText("<html><u>Transfer Entry (Contra)</u></html>");
        setTitle("Transfer Entry (Contra)");
        this.setSize(608, 377);
        java.net.URL imgUrl = getClass().getResource("/images/icon.png");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            this.setFrameIcon(icon);
        }
        Date d = new Date();
        SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
        h2.setText(g.format(d));
        menupack.menu_form me = new menu_form();
        user = me.getUsername();
        utype = me.getUserType();
        hmany = me.getHmany();
    }

    void get_billno() {
        try {
            int sno = 1;
            String query = "select max(billno) from account_transfer";
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

    void save() {
        try {
            if (h4.getSelectedItem() == null || h4.getSelectedItem() == "") {
                JOptionPane.showMessageDialog(this, "Select Bank Account ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }
            if (h5.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Amount ?", "Amount", JOptionPane.ERROR_MESSAGE);
                h5.requestFocus();
                return;
            }
            if (h6.getText().equals("")) {
                h6.setText(".");
            }
            int as = JOptionPane.showConfirmDialog(this, "Want to Save ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            get_billno();
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText());
            String date = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);

            String billno = h1.getText();
            String category = h3.getSelectedItem().toString();
            String account = h4.getSelectedItem().toString();
            String amount = h5.getText();
            String remarks = h6.getText();

            boolean selva = false;
            String query = "select billno from account_transfer where billno='" + billno + "'";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                selva = true;
            }
            if (selva == true) {
                JOptionPane.showMessageDialog(this, "Already Exist!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            query = "insert into account_transfer values ('" + billno + "','" + date + "','" + category + "','" + account + "','" + amount + "','" + remarks + "','" + user + "','" + last + "')";
            int count = util.doManipulation(query);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Saved Successfully", "Saved", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    void update() {
        try {
            if (h4.getSelectedItem() == null || h4.getSelectedItem() == "") {
                JOptionPane.showMessageDialog(this, "Select Bank Account ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }
            if (h5.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Amount ?", "Amount", JOptionPane.ERROR_MESSAGE);
                h5.requestFocus();
                return;
            }
            if (h6.getText().equals("")) {
                h6.setText(".");
            }
            int as = JOptionPane.showConfirmDialog(this, "Want to Update ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText());
            String date = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);

            String billno = h1.getText();
            String category = h3.getSelectedItem().toString();
            String account = h4.getSelectedItem().toString();
            String amount = h5.getText();
            String remarks = h6.getText();

            boolean selva = false;
            String query = "select billno from account_transfer where billno='" + billno + "'";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "Invalid", JOptionPane.ERROR_MESSAGE);
                return;
            }
            query = "update account_transfer set dat='" + date + "',category='" + category + "',account='" + account + "',amount='" + amount + "',remarks='" + remarks + "',user='" + user + "',last='" + last + "' where billno='" + billno + "' ";
            int count = util.doManipulation(query);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Updated Successfully", "Updated", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    void clear() {
        try {
            h1.setText("--");
            deletebutton.setVisible(false);
            savebutton.setVisible(true);
            viewbutton.setVisible(true);
            alterbutton.setVisible(false);
            h2.setText("");
            h5.setText("");
            h6.setText("");
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            h2.setText(g.format(d));
            h1.setEnabled(true);
            h3.requestFocus();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void view(String billno) {
        try {
            String category = "";
            String query = "select billno,date_format(dat,'%d/%m/%Y'),category,account,amount,remarks from account_transfer where billno='" + billno + "' ";
            ResultSet r = util.doQuery(query);
            boolean selva = false;
            while (r.next()) {
                h1.setText(r.getString(1));
                h2.setText(r.getString(2));
                h3.setSelectedItem(r.getString(3));
                h4.setSelectedItem(r.getString(4));
                double amount = r.getDouble(5);
                String amount2 = String.format("%." + hmany + "f", amount);
                h5.setText(amount2);
                h6.setText(r.getString(6));
                selva = true;
                category = r.getString(3);
            }
            if (category.equals("Cash Ac to Bank Ac")) {
                h3.setSelectedIndex(0);
            } else {
                h3.setSelectedIndex(1);
            }
            if (selva == true) {
                savebutton.setVisible(false);
                viewbutton.setVisible(false);
                deletebutton.setVisible(true);
                alterbutton.setVisible(true);
                h1.setEnabled(false);
                h3.requestFocus();
            } else if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void delete(String billno) {
        try {
            if (utype.equalsIgnoreCase("User")) {
                JOptionPane.showMessageDialog(this, "Login as 'Administrator' to Delete!", "Permission Restricted", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int as = JOptionPane.showConfirmDialog(this, "Want to Delete ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            boolean selva = false;
            String query = "select billno from account_transfer where billno='" + billno + "'";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            query = "delete from account_transfer where billno='" + billno + "'";
            int count = util.doManipulation(query);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Deleted Successfully", "Deleted", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_group() {
        try {
            h4.removeAllItems();
            String query = "select concat(ano,' / ',aname) from bank";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                h4.addItem(set.getString(1));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public voucher_trasfer(DataUtil util) {
        initComponents();
        button_short();
        this.util = util;
        deletebutton.setVisible(false);
        alterbutton.setVisible(false);
        get_group();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closebutton = new javax.swing.JButton();
        titlelablel = new javax.swing.JLabel();
        clearbutton = new javax.swing.JButton();
        savebutton = new javax.swing.JButton();
        deletebutton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        viewbutton = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jCalendarButton2 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        h4 = new javax.swing.JComboBox<>();
        h6 = new javax.swing.JTextField();
        h5 = new javax.swing.JTextField();
        h2 = new javax.swing.JTextField();
        h1 = new javax.swing.JTextField();
        prebutton = new javax.swing.JButton();
        nextbutton = new javax.swing.JButton();
        alterbutton = new javax.swing.JButton();
        h3 = new javax.swing.JComboBox<>();

        setClosable(true);
        getContentPane().setLayout(null);

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
        closebutton.setBounds(420, 270, 150, 50);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Transfer Entry (Contra)");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 250, 30);

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
        clearbutton.setBounds(270, 270, 150, 50);

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
        savebutton.setBounds(120, 220, 150, 50);

        deletebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        deletebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete45.png"))); // NOI18N
        deletebutton.setMnemonic('d');
        deletebutton.setText("Delete");
        deletebutton.setToolTipText("Use (Alt+D)  or Click Here to Delete");
        deletebutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deletebutton.setPreferredSize(new java.awt.Dimension(123, 50));
        deletebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(deletebutton);
        deletebutton.setBounds(120, 270, 150, 50);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Bank Account");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(20, 110, 100, 30);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Amount");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(20, 140, 100, 30);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Entry No");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(20, 50, 100, 30);

        viewbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        viewbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/view45.png"))); // NOI18N
        viewbutton.setMnemonic('v');
        viewbutton.setText("View");
        viewbutton.setToolTipText("Use (Alt+V)  or Click Here to View");
        viewbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        viewbutton.setPreferredSize(new java.awt.Dimension(127, 50));
        viewbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(viewbutton);
        viewbutton.setBounds(120, 270, 150, 50);

        jLabel47.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 102, 0));
        getContentPane().add(jLabel47);
        jLabel47.setBounds(470, 620, 450, 30);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Category");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(20, 80, 100, 30);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Remarks");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(20, 170, 100, 30);

        jCalendarButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
        jCalendarButton2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton2PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton2);
        jCalendarButton2.setBounds(530, 50, 40, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h4);
        h4.setBounds(120, 110, 450, 30);

        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h6);
        h6.setBounds(120, 170, 450, 30);

        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h5);
        h5.setBounds(120, 140, 450, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(410, 50, 120, 30);

        h1.setEditable(false);
        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setText("--");
        h1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h1FocusGained(evt);
            }
        });
        getContentPane().add(h1);
        h1.setBounds(120, 50, 290, 30);

        prebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        prebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pre45.png"))); // NOI18N
        prebutton.setMnemonic('r');
        prebutton.setText("Last Entry");
        prebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(prebutton);
        prebutton.setBounds(270, 220, 150, 50);

        nextbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        nextbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/next45.png"))); // NOI18N
        nextbutton.setMnemonic('u');
        nextbutton.setText("Next Entry");
        nextbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(nextbutton);
        nextbutton.setBounds(420, 220, 150, 50);

        alterbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        alterbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/upload45.png"))); // NOI18N
        alterbutton.setMnemonic('u');
        alterbutton.setText("Update");
        alterbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alterbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(alterbutton);
        alterbutton.setBounds(120, 220, 150, 50);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash A\\c to Bank A\\c", "Bank A\\c to Cash A\\c" }));
        getContentPane().add(h3);
        h3.setBounds(120, 80, 450, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        save();
    }//GEN-LAST:event_savebuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        clear();
    }//GEN-LAST:event_clearbuttonActionPerformed

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewbuttonActionPerformed
        String ino = JOptionPane.showInputDialog(this, "Enter Voucher No ?", "Voucher No", JOptionPane.PLAIN_MESSAGE);
        if (ino == null || "".equals(ino)) {
            JOptionPane.showMessageDialog(this, "Invalid Voucher No!", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        view(ino);
    }//GEN-LAST:event_viewbuttonActionPerformed

    private void jCalendarButton2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendarButton2PropertyChange

        try {
            if (evt.getNewValue() instanceof Date) {
                String ses = evt.getNewValue().toString();
                Date nm = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(ses);
                String date = (new SimpleDateFormat("dd/MM/yyyy").format(nm));
                h2.setText(date);
                h2.requestFocus();
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_jCalendarButton2PropertyChange

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebuttonActionPerformed
        delete(h1.getText());
    }//GEN-LAST:event_deletebuttonActionPerformed

    private void h1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h1FocusGained
        h3.requestFocus();
    }//GEN-LAST:event_h1FocusGained

    private void prebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prebuttonActionPerformed

        try {
            String billno = h1.getText();
            String query;
            if (billno.equalsIgnoreCase("--")) {
                query = "select max(billno) from account_transfer";
            } else {
                query = "select billno from account_transfer where billno < '" + billno + "' order by billno desc limit 1";
            }

            ResultSet set1 = util.doQuery(query);
            boolean selva = false;
            String search_billno = "";
            while (set1.next()) {
                selva = true;
                search_billno = set1.getString(1);
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            view(search_billno);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_prebuttonActionPerformed

    private void nextbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextbuttonActionPerformed
        try {

            String billno = h1.getText();
            if (billno.equalsIgnoreCase("--")) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String query = "select distinct billno from account_transfer where billno > '" + billno + "' order by billno limit 1";
            ResultSet set1 = util.doQuery(query);
            boolean selva = false;
            String search_billno = "";
            while (set1.next()) {
                selva = true;
                search_billno = set1.getString(1);
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            view(search_billno);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_nextbuttonActionPerformed

    private void alterbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alterbuttonActionPerformed
        update();
    }//GEN-LAST:event_alterbuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton alterbutton;
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton deletebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h2;
    private javax.swing.JComboBox<String> h3;
    private javax.swing.JComboBox<String> h4;
    private javax.swing.JTextField h5;
    private javax.swing.JTextField h6;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton2;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton nextbutton;
    private javax.swing.JButton prebutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
