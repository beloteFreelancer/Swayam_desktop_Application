package smspack;

import Utils.ColorConstants;
import com.selrom.db.DataUtil;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public final class sms_setting extends javax.swing.JInternalFrame {

    DataUtil util = null;

    final void button_short() {
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        titlelablel.setText("<html><u>SMS Setting</u></html>");
        setTitle("SMS Setting");
        this.setSize(435, 481);
        javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            setFrameIcon(icon);
        }
    }

    void get_display() {
        try {
            ResultSet r;
            String query = "select sender,user,pass,sms_alert,alter_sms,statement_sms,mobile1,mobile2,smsfoot1,smsfoot2 from setting_sms";
            r = util.doQuery(query);
            while (r.next()) {
                h1.setText(r.getString(1));
                h2.setText(r.getString(2));
                h3.setText(r.getString(3));
                h4.setSelectedItem(r.getString(4));
                h5.setSelectedItem(r.getString(5));
                h6.setSelectedItem(r.getString(6));
                h7.setText(r.getString(7));
                h8.setText(r.getString(8));
                // Replace any "BBS" references with "Swayam" in SMS footers
                String smsfoot1 = r.getString(9);
                String smsfoot2 = r.getString(10);
                if (smsfoot1 != null) {
                    smsfoot1 = smsfoot1.replace("BBS", "Swayam").replace("bbs", "Swayam");
                }
                if (smsfoot2 != null) {
                    smsfoot2 = smsfoot2.replace("BBS", "Swayam").replace("bbs", "Swayam");
                }
                h9.setText(smsfoot1);
                h10.setText(smsfoot2);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
    }

    public sms_setting(DataUtil util) {
        initComponents();
        button_short();
        this.util = util;
        get_display();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closebutton = new javax.swing.JButton();
        titlelablel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        h1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        h3 = new javax.swing.JPasswordField();
        savebutton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        h4 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        h6 = new javax.swing.JComboBox<>();
        h5 = new javax.swing.JComboBox<>();
        h8 = new javax.swing.JTextField();
        h7 = new javax.swing.JTextField();
        h2 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        h10 = new javax.swing.JTextField();
        h9 = new javax.swing.JTextField();

        setClosable(true);
        getContentPane().setLayout(null);

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
        closebutton.setBounds(250, 380, 150, 50);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("SMS Setting");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 200, 30);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("SMS Footer");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(20, 300, 80, 30);

        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h1);
        h1.setBounds(100, 40, 300, 30);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("User Name");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(20, 70, 80, 30);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Daily Statements");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 190, 140, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        getContentPane().add(h3);
        h3.setBounds(100, 100, 300, 30);

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
        savebutton.setBounds(100, 380, 150, 50);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Management No-2");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 250, 140, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No", "Yes" }));
        getContentPane().add(h4);
        h4.setBounds(160, 130, 240, 30);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Password");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(20, 100, 80, 30);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Management No-1");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(20, 220, 140, 30);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Automatic SMS Alters");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(20, 130, 140, 30);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Delete & Alter SMS");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(20, 160, 140, 30);

        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No", "Yes" }));
        getContentPane().add(h6);
        h6.setBounds(160, 190, 240, 30);

        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No", "Yes" }));
        getContentPane().add(h5);
        h5.setBounds(160, 160, 240, 30);

        h8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h8);
        h8.setBounds(160, 250, 240, 30);

        h7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h7);
        h7.setBounds(160, 220, 240, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(100, 70, 300, 30);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Sender Id");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(20, 40, 80, 30);

        h10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h10);
        h10.setBounds(100, 330, 300, 30);

        h9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h9);
        h9.setBounds(100, 300, 300, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_savebuttonActionPerformed
        if (h1.getText().equals("")) {
            h1.setText(".");
        }
        if (h2.getText().equals("")) {
            h2.setText(".");
        }
        if (h3.getText().equals("")) {
            h3.setText(".");
        }
        if (h7.getText().equals("")) {
            h7.setText(".");
        }
        if (h8.getText().equals("")) {
            h8.setText(".");
        }
        if (h9.getText().equals("")) {
            h9.setText(".");
        }
        if (h10.getText().equals("")) {
            h10.setText(".");
        }
        try {
            boolean selva = false;
            String query = "select sender from setting_sms";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == false) {
                query = "insert into setting_sms values ('" + h1.getText() + "','" + h2.getText() + "','" + h3.getText()
                        + "','" + h4.getSelectedItem() + "','" + h5.getSelectedItem() + "','" + h6.getSelectedItem()
                        + "','" + h7.getText() + "','" + h8.getText() + "','" + h9.getText() + "','" + h10.getText()
                        + "')";
            } else {
                query = "update setting_sms set sender='" + h1.getText() + "', user='" + h2.getText() + "',pass='"
                        + h3.getText() + "',sms_alert='" + h4.getSelectedItem() + "',alter_sms='" + h5.getSelectedItem()
                        + "',statement_sms='" + h6.getSelectedItem() + "',mobile1='" + h7.getText() + "',mobile2='"
                        + h8.getText() + "',smsfoot1='" + h9.getText() + "',smsfoot2='" + h10.getText() + "' ";
            }
            int a = util.doManipulation(query);
            if (a > 0) {
                JOptionPane.showMessageDialog(this, "Saved /Updated Successfully", "Saved", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_savebuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }// GEN-LAST:event_closebuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h10;
    private javax.swing.JTextField h2;
    private javax.swing.JPasswordField h3;
    private javax.swing.JComboBox<String> h4;
    private javax.swing.JComboBox<String> h5;
    private javax.swing.JComboBox<String> h6;
    private javax.swing.JTextField h7;
    private javax.swing.JTextField h8;
    private javax.swing.JTextField h9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    // End of variables declaration//GEN-END:variables
}
