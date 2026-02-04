package loyaltypack;

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
public final class points_setting extends javax.swing.JInternalFrame {

    DataUtil util = null;

    final void button_short() {
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        titlelablel.setText("<html><u>Loyalty Points Setting</u></html>");
        setTitle("Loyalty Points Setting");
        this.setSize(423, 262);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
    }

    void get_display() {
        try {
            ResultSet r;
            String query = "select points_option,hmuch,pfor from setting_points";
            r = util.doQuery(query);
            while (r.next()) {
                h1.setSelectedItem(r.getString(1));
                h2.setText(r.getString(2));
                h3.setText(r.getString(3));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
    }

    public points_setting(DataUtil util) {
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
        savebutton = new javax.swing.JButton();
        h1 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        h3 = new javax.swing.JTextField();
        h2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

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
        closebutton.setBounds(260, 150, 130, 50);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Loyalty Points Setting");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 220, 30);

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
        savebutton.setBounds(130, 150, 130, 50);

        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No", "Yes" }));
        getContentPane().add(h1);
        h1.setBounds(130, 50, 260, 30);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Points for");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(20, 110, 110, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h3);
        h3.setBounds(130, 110, 260, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(130, 80, 260, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Points Option");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(20, 50, 110, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("How much Points");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(20, 80, 110, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        if (h2.getText().equals("")) {
            h2.setText(".");
        }
        if (h3.getText().equals("")) {
            h3.setText(".");
        }
        try {
            boolean selva = false;
            String query = "select points_option from setting_points";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == false) {
                query = "insert into setting_points values ('" + h1.getSelectedItem() + "','" + h2.getText() + "','" + h3.getText() + "')";
            } else {
                query = "update setting_points set points_option='" + h1.getSelectedItem() + "', hmuch='" + h2.getText() + "',pfor='" + h3.getText() + "'";
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
    private javax.swing.JComboBox<String> h1;
    private javax.swing.JTextField h2;
    private javax.swing.JTextField h3;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    // End of variables declaration//GEN-END:variables
}
