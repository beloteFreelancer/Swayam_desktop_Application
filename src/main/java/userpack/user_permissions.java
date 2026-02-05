package userpack;

import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.selrom.db.DataUtil;
import java.awt.Font;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import menupack.sample2;
import Utils.ColorConstants;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public final class user_permissions extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    ResultSet r;
    DecimalFormat df2 = new DecimalFormat("#.##");
    AutoCompleteSupport support;

    final void button_short() {
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        titlelablel.setText("<html><u>User Permissions</u></html>");
        setTitle("User Permissions");
        this.setSize(491, 648);
        javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            this.setFrameIcon(icon);
        }
    }

    public final void load_list_table() {
        s2.addColumn("Feature");
        s2.addColumn("Permission");
        jTable1.setModel(s2);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(364);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    // UPDATED: Loads from PermissionManager to match menu_form logic exactly
    void load_report() {
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }

        // Use the Master List from PermissionManager
        for (String feature : PermissionManager.getAllFeatures()) {
            s2.addRow(new Object[] { feature, "No" });
        }
    }

    final void get_user_name() {
        try {
            h3.removeAllItems();
            String query = "select `user` from users where utype='User' ";
            r = util.doQuery(query);
            while (r.next()) {
                h3.addItem(r.getString(1));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // UPDATED: Optimized to fetch all permissions at once
    void get_permission_list() {
        try {
            // 1. Reset all permissions to "No" initially
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                jTable1.setValueAt("No", i, 1);
            }

            // 2. Fetch all enabled permissions for this user
            String query = "select fname, option1 from users_permissions where `user`='" + h3.getSelectedItem() + "'";
            r = util.doQuery(query);

            while (r.next()) {
                String dbFname = r.getString(1);
                String dbOption = r.getString(2);

                // 3. Find the matching row in our table and update it
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    String tableFname = jTable1.getValueAt(i, 0).toString();
                    if (tableFname.equalsIgnoreCase(dbFname)) {
                        jTable1.setValueAt(dbOption, i, 1);
                        break;
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void save() {
        try {
            int as = JOptionPane.showConfirmDialog(this, "Want to Save ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            ArrayList query_batch = new ArrayList();
            query_batch.add("delete from users_permissions where `user`='" + h3.getSelectedItem() + "'");
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String fname = jTable1.getValueAt(i, 0).toString();
                String option = jTable1.getValueAt(i, 1).toString();
                query_batch.add("insert into users_permissions values ('" + h3.getSelectedItem() + "','" + fname + "','"
                        + option + "')");
            }
            int count = util.doManipulation_Batch(query_batch);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Saved Successfully</h1></html>", "Saved",
                        JOptionPane.PLAIN_MESSAGE);
                clear();
                load_report();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void clear() {
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        h3.setEnabled(true);
        generatebutton.setEnabled(true);
    }

    public user_permissions(DataUtil util) {
        initComponents();

        this.util = util;
        button_short();
        load_list_table();
        get_user_name();
        load_report();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        generatebutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        h3 = new javax.swing.JComboBox();
        savebutton = new javax.swing.JButton();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("User Permissions");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 230, 30);

        jTable1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }));
        jTable1.setRowHeight(25);
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable1FocusGained(evt);
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 82, 470, 480);

        generatebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton.setIcon(ColorConstants.loadIcon("/icons/select22.png")); // NOI18N
        generatebutton.setMnemonic('t');
        generatebutton.setText("Select");
        generatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton);
        generatebutton.setBounds(340, 50, 130, 30);

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
        clearbutton.setBounds(220, 560, 130, 50);

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
        closebutton.setBounds(350, 560, 120, 50);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("User");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(10, 50, 90, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        h3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                h3ItemStateChanged(evt);
            }
        });
        getContentPane().add(h3);
        h3.setBounds(50, 50, 290, 30);

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
        savebutton.setBounds(90, 560, 130, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable1MouseClicked
        String value = jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString();
        if (value.equals("Yes")) {
            jTable1.setValueAt("No", jTable1.getSelectedRow(), 1);
        } else {
            jTable1.setValueAt("Yes", jTable1.getSelectedRow(), 1);
        }

    }// GEN-LAST:event_jTable1MouseClicked

    private void generatebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_generatebuttonActionPerformed
        h3.setEnabled(false);
        generatebutton.setEnabled(false);
        get_permission_list();

    }// GEN-LAST:event_generatebuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbuttonActionPerformed
        clear();
        load_report();

    }// GEN-LAST:event_clearbuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }// GEN-LAST:event_closebuttonActionPerformed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTable1FocusGained

    }// GEN-LAST:event_jTable1FocusGained

    private void h3ItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_h3ItemStateChanged

    }// GEN-LAST:event_h3ItemStateChanged

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_savebuttonActionPerformed
        save();
    }// GEN-LAST:event_savebuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JComboBox h3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    // End of variables declaration//GEN-END:variables
}
