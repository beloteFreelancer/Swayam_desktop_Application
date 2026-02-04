package custpack;

import com.selrom.db.DataUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import menupack.menu_form;
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class cust_dues_report_print_select extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    ResultSet r;
    int hmany = 2;

    void button_short() {
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        generatebutton.setText("<html><b>Generate</b>  (Alt+G)</h6><html>");

        setTitle("Select Option");
        this.setSize(591, 162);
        java.net.URL imgUrl = getClass().getResource("/images/icon.png");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            this.setFrameIcon(icon);
        }
        menu_form me = new menu_form();
        hmany = me.getHmany();
    }

    void get_cid() {
        try {
            h3.removeAllItems();
            String query = "select distinct city from cust";
            r = util.doQuery(query);
            while (r.next()) {
                h3.addItem(r.getString(1));
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public cust_dues_report_print_select(DataUtil util) {
        initComponents();

        this.util = util;
        button_short();
        get_cid();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        generatebutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        h3 = new javax.swing.JComboBox();
        all = new javax.swing.JCheckBox();

        setClosable(true);
        getContentPane().setLayout(null);

        generatebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/select22.png"))); // NOI18N
        generatebutton.setMnemonic('s');
        generatebutton.setText("Select");
        generatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton);
        generatebutton.setBounds(310, 70, 130, 30);

        closebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        closebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete22.png"))); // NOI18N
        closebutton.setMnemonic('o');
        closebutton.setText("Close");
        closebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(closebutton);
        closebutton.setBounds(440, 70, 120, 31);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Area");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(10, 30, 40, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        h3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                h3ItemStateChanged(evt);
            }
        });
        getContentPane().add(h3);
        h3.setBounds(50, 30, 510, 30);

        all.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        all.setText("Select All");
        all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allActionPerformed(evt);
            }
        });
        getContentPane().add(all);
        all.setBounds(210, 70, 90, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generatebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatebuttonActionPerformed
        try {
            String option = "No";
            if (all.isSelected()) {
                option = "Yes";
            }
            String area = h3.getSelectedItem().toString();
            new cust_dues_report_print().Report(util, option, area);
            dispose();
        } catch (Exception e) {
            System.out.println(e);
        }

    }//GEN-LAST:event_generatebuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void h3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_h3ItemStateChanged

    }//GEN-LAST:event_h3ItemStateChanged

    private void allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allActionPerformed
        if (all.isSelected()) {
            h3.setEnabled(false);
        } else {
            h3.setEnabled(true);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_allActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox all;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JComboBox h3;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
