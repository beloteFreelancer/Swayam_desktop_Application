package emailpack;

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
public final class email_setting extends javax.swing.JInternalFrame {

    DataUtil util = null;

    final void button_short() {
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        titlelablel.setText("<html><u>Email Setting</u></html>");
        setTitle("Email Setting");
        this.setSize(616, 331);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
    }

    void get_display() {
        try {
            ResultSet r;
            String query = "select user,pass,backup_option,statement_option,email1 from setting_email";
            r = util.doQuery(query);
            while (r.next()) {
                h1.setText(r.getString(1));
                h2.setText(r.getString(2));
                h3.setSelectedItem(r.getString(3));
                h4.setSelectedItem(r.getString(4));
                h5.setText(r.getString(5));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
    }

    public email_setting(DataUtil util) {
        initComponents();
        button_short();
        this.util = util;
        get_display();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closebutton = new javax.swing.JButton();
        titlelablel = new javax.swing.JLabel();
        h1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        savebutton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        h2 = new javax.swing.JPasswordField();
        h5 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        h3 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        h4 = new javax.swing.JComboBox<>();

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
        closebutton.setBounds(430, 240, 150, 50);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Email Setting");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 200, 30);

        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h1);
        h1.setBounds(130, 50, 450, 30);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Daily Statements Email");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(20, 160, 160, 30);

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
        savebutton.setBounds(280, 240, 150, 50);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Gmail Username");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(20, 50, 110, 30);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Receiver Email");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(20, 190, 150, 30);

        h2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(130, 80, 450, 30);

        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h5);
        h5.setBounds(170, 190, 410, 30);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Gmail Password");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(20, 80, 110, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No", "Yes" }));
        getContentPane().add(h3);
        h3.setBounds(170, 130, 410, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Google Backup Option");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(20, 130, 150, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No", "Yes" }));
        getContentPane().add(h4);
        h4.setBounds(170, 160, 410, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        if (h1.getText().equals("")) {
            h1.setText(".");
        }
        if (h2.getText().equals("")) {
            h2.setText(".");
        }
        if (h5.getText().equals("")) {
            h5.setText(".");
        }
        try {
            boolean selva = false;
            String query = "select user from setting_email";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                query = "update setting_email set user='" + h1.getText() + "',pass='" + h2.getText() + "',backup_option='" + h3.getSelectedItem() + "',statement_option='" + h4.getSelectedItem() + "',email1='" + h5.getText() + "'";
            } else {
                query = "insert into setting_email values ('" + h1.getText() + "','" + h2.getText() + "','" + h3.getSelectedItem() + "','" + h4.getSelectedItem() + "','" + h5.getText() + "')";
            }
            int a = util.doManipulation(query);
            if (a > 0) {
                JOptionPane.showMessageDialog(this, "Saved /Updated Successfully", "Saved", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_savebuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JPasswordField h2;
    private javax.swing.JComboBox<String> h3;
    private javax.swing.JComboBox<String> h4;
    private javax.swing.JTextField h5;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    // End of variables declaration//GEN-END:variables
}
