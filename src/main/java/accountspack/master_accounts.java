package accountspack;

import com.selrom.db.DataUtil;
import java.awt.Font;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import menupack.menu_form;
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class master_accounts extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    int hmany = 2;

    final void button_short() {
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        updatebutton.setText("<html><b>Update</b><br>(Alt+U)</h6><html>");

        titlelablel.setText("<html><u>Account Group Master</u></html>");
        setTitle("Account Group Master");
        this.setSize(1022, 536);
        java.net.URL imgUrl = getClass().getResource("/images/icon.png");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            this.setFrameIcon(icon);
        }
        menupack.menu_form me = new menu_form();
        hmany = me.getHmany();
    }

    final void load_list_table() {
        s2.addColumn("Group");
        s2.addColumn("Under");
        s2.addColumn("Debit");
        s2.addColumn("Credit");
        jTable1.setModel(s2);
        DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
        dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
        jTable1.getColumnModel().getColumn(2).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(3).setCellRenderer(dtcr1);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(300);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(110);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    void clear() {
        h1.setText("");
        h3.setText("");
        h4.setText("");
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        updatebutton.setVisible(false);
        deletebutton.setVisible(false);
        savebutton.setVisible(true);
        h1.setEnabled(true);
        get_display();
        h1.requestFocus();
    }

    void get_display() {
        try {
            ResultSet r;
            String query = "select head,under,debit,credit from account_master order by head";
            r = util.doQuery(query);
            while (r.next()) {
                s2.addRow(new Object[]{r.getString(1), r.getString(2), (long) r.getDouble(3) + ".00", (long) r.getDouble(4) + ".00"});
            }
            totl.setText(" Total Records: " + jTable1.getRowCount());
        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
    }

    void save() {
        try {
            if (h1.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Account Group ?", "Group", JOptionPane.ERROR_MESSAGE);
                h1.requestFocus();
                return;
            }
            boolean selva = false;
            ResultSet r;
            String query = "select head from account_master where head='" + h1.getText() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                JOptionPane.showMessageDialog(this, "Already Exist!", "Invalid", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (h3.getText().equals("")) {
                h3.setText("" + 0);
            }
            if (h4.getText().equals("")) {
                h4.setText("" + 0);
            }
            query = "insert into account_master values ('" + h1.getText() + "','" + h2.getSelectedItem() + "','" + h3.getText() + "','" + h4.getText() + "' )";
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
            if (h1.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Account Group ?", "Group", JOptionPane.ERROR_MESSAGE);
                h1.requestFocus();
                return;
            }
            boolean selva = false;
            ResultSet r;
            String query = "select head from account_master where head='" + h1.getText() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (h3.getText().equals("")) {
                h3.setText("" + 0);
            }
            if (h4.getText().equals("")) {
                h4.setText("" + 0);
            }
            query = "update account_master set under='" + h2.getSelectedItem() + "',debit='" + h3.getText() + "',credit='" + h4.getText() + "' where head='" + h1.getText() + "' ";
            int count = util.doManipulation(query);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Updated Successfully", "Updated", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void delete() {
        try {
            if (h1.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Account Group ?", "Group", JOptionPane.ERROR_MESSAGE);
                h1.requestFocus();
                return;
            }
            boolean selva = false;
            ResultSet r;
            String query = "select head from account_master where head='" + h1.getText() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int as = JOptionPane.showConfirmDialog(this, "Want to Delete ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            query = "delete from account_master where head='" + h1.getText() + "' ";
            int count = util.doManipulation(query);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Deleted Successfully", "Deleted", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void view() {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean selva = false;
            String head = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
            String query = "select head,under,debit,credit from account_master where head='" + head + "' ";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                h1.setText(r.getString(1));
                h2.setSelectedItem(r.getString(2));
                String debit2 = String.format("%." + hmany + "f", r.getDouble(3));
                String credit2 = String.format("%." + hmany + "f", r.getDouble(4));
                h3.setText(debit2);
                h4.setText(credit2);
                selva = true;
            }
            if (selva == true) {
                savebutton.setVisible(false);
                updatebutton.setVisible(true);
                deletebutton.setVisible(true);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public master_accounts(DataUtil util) {
        initComponents();
        button_short();
        this.util = util;
        load_list_table();
        get_display();
        updatebutton.setVisible(false);
        deletebutton.setVisible(false);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closebutton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        titlelablel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        clearbutton = new javax.swing.JButton();
        h1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        h2 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        h3 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        h4 = new javax.swing.JTextField();
        updatebutton = new javax.swing.JButton();
        deletebutton = new javax.swing.JButton();
        savebutton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        totl = new javax.swing.JLabel();

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
        closebutton.setBounds(850, 440, 140, 50);

        jTable1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setRowHeight(25);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(450, 52, 540, 390);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Account Group Master (Ledger)");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 400, 30);

        jPanel1.setLayout(null);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Opening Balance:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(0, 80, 120, 30);

        clearbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        clearbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/clear45.png"))); // NOI18N
        clearbutton.setMnemonic('c');
        clearbutton.setText("Clear");
        clearbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbuttonActionPerformed(evt);
            }
        });
        jPanel1.add(clearbutton);
        clearbutton.setBounds(280, 160, 140, 50);

        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h1);
        h1.setBounds(40, 0, 380, 30);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Group");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(0, 0, 40, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Incomes", "Expenses", "Current Assets", "Fixed Assets", "Loans (Liability)", "Current Liabilities", "Capital Account", "Suspense Account", "Bank Accounts" }));
        jPanel1.add(h2);
        h2.setBounds(40, 30, 380, 30);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Under");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(0, 30, 40, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h3);
        h3.setBounds(40, 110, 160, 30);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Debit");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(0, 110, 40, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h4);
        h4.setBounds(260, 110, 160, 30);

        updatebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        updatebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/upload45.png"))); // NOI18N
        updatebutton.setMnemonic('u');
        updatebutton.setText("Update");
        updatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebuttonActionPerformed(evt);
            }
        });
        jPanel1.add(updatebutton);
        updatebutton.setBounds(0, 160, 140, 50);

        deletebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        deletebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete45.png"))); // NOI18N
        deletebutton.setMnemonic('d');
        deletebutton.setText("Delete");
        deletebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebuttonActionPerformed(evt);
            }
        });
        jPanel1.add(deletebutton);
        deletebutton.setBounds(140, 160, 140, 50);

        savebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        savebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save45.png"))); // NOI18N
        savebutton.setMnemonic('s');
        savebutton.setText("Save");
        savebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebuttonActionPerformed(evt);
            }
        });
        jPanel1.add(savebutton);
        savebutton.setBounds(140, 160, 140, 50);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Credit");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(210, 110, 50, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(20, 50, 420, 210);

        totl.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(totl);
        totl.setBounds(450, 440, 400, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        save();
    }//GEN-LAST:event_savebuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebuttonActionPerformed
        delete();
    }//GEN-LAST:event_deletebuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        clear();
    }//GEN-LAST:event_clearbuttonActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        view();
    }//GEN-LAST:event_jTable1MouseClicked

    private void updatebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatebuttonActionPerformed
        update();
    }//GEN-LAST:event_updatebuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton deletebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JComboBox<String> h2;
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JLabel totl;
    private javax.swing.JButton updatebutton;
    // End of variables declaration//GEN-END:variables
}
