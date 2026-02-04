package accountspack;

import com.selrom.db.DataUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class Group_select extends javax.swing.JInternalFrame {

    DataUtil util = null;

    final void button_short() {
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        generatebutton.setText("<html><b>Generate</b>  (Alt+G)</h6><html>");
        setTitle("Group Selection");
        this.setSize(477, 149);
        java.net.URL imgUrl = getClass().getResource("/images/icon.png");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            this.setFrameIcon(icon);
        }
    }

    void get_account_group() {
        try {
            h3.removeAllItems();
            String query = "select head from account_master";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                h3.addItem(set.getString(1));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Group_select(DataUtil util) {
        initComponents();

        this.util = util;
        button_short();
        get_account_group();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        generatebutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        h3 = new javax.swing.JComboBox<>();

        setClosable(true);
        getContentPane().setLayout(null);

        generatebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/selectall45.png"))); // NOI18N
        generatebutton.setMnemonic('g');
        generatebutton.setText("Select");
        generatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton);
        generatebutton.setBounds(130, 60, 160, 40);

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
        closebutton.setBounds(290, 60, 150, 40);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Group");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(10, 20, 70, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h3);
        h3.setBounds(60, 20, 380, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void generatebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatebuttonActionPerformed
        new Group_Summary().Report(util, h3.getSelectedItem().toString());
        this.dispose();

    }//GEN-LAST:event_generatebuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closebutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JComboBox<String> h3;
    private javax.swing.JLabel jLabel12;
    // End of variables declaration//GEN-END:variables
}
