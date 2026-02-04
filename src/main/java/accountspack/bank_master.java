package accountspack;

import com.selrom.db.DataUtil;
import java.awt.Font;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class bank_master extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();

    final void button_short() {
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");

        titlelablel.setText("<html><u>Bank Master</u></html>");
        setTitle("Bank Master");
        this.setSize(1142, 536);
        java.net.URL imgUrl = getClass().getResource("/images/icon.png");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            this.setFrameIcon(icon);
        }
    }

    final void load_list_table() {
        s2.addColumn("A\\c No");
        s2.addColumn("Name");
        s2.addColumn("Bank");
        s2.addColumn("Branch");
        s2.addColumn("IFSC Code");
        s2.addColumn("Remarks");
        jTable1.setModel(s2);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(170);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(250);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(250);

        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    void clear() {
        h1.setText("");
        h2.setText("");
        h3.setText("");
        h4.setText("");
        h5.setText("");
        h6.setText("");
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        h1.setEnabled(true);
        get_display();
        h1.requestFocus();
    }

    void get_display() {
        try {
            ResultSet r;
            String query = "select ano,aname,bank,branch,ifsc,remarks from bank order by aname";
            r = util.doQuery(query);
            while (r.next()) {
                s2.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6)});
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
    }

    void view(String ano) {
        try {
            ResultSet r;
            String query = "select ano,aname,bank,branch,ifsc,remarks from bank where ano='" + ano + "'";
            r = util.doQuery(query);
            while (r.next()) {
                h1.setText(r.getString(1));
                h2.setText(r.getString(2));
                h3.setText(r.getString(3));
                h4.setText(r.getString(4));
                h5.setText(r.getString(5));
                h6.setText(r.getString(6));
                h1.setEnabled(false);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
    }

    public bank_master(DataUtil util) {
        initComponents();
        button_short();
        this.util = util;
        load_list_table();
        get_display();
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
        h6 = new javax.swing.JTextField();
        h1 = new javax.swing.JTextField();
        h2 = new javax.swing.JTextField();
        h3 = new javax.swing.JTextField();
        h4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        h5 = new javax.swing.JTextField();
        savebutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        deletebutton = new javax.swing.JButton();

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
        closebutton.setBounds(960, 440, 150, 50);

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
        jScrollPane1.setBounds(500, 50, 610, 390);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Bank Master");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 400, 30);

        jPanel1.setLayout(null);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Branch");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(0, 90, 80, 30);

        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h6);
        h6.setBounds(80, 150, 380, 30);

        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h1);
        h1.setBounds(80, 0, 380, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h2);
        h2.setBounds(80, 30, 380, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h3);
        h3.setBounds(80, 60, 380, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h4);
        h4.setBounds(80, 90, 380, 30);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("A\\c No");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(0, 0, 80, 30);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Remarks");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(0, 150, 80, 30);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Name");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(0, 30, 80, 30);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Bank");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(0, 60, 80, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("IFSC Code");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(0, 120, 80, 30);

        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h5);
        h5.setBounds(80, 120, 380, 30);

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
        savebutton.setBounds(160, 190, 150, 50);

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
        clearbutton.setBounds(310, 190, 150, 50);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(20, 50, 460, 240);

        deletebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        deletebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete45.png"))); // NOI18N
        deletebutton.setMnemonic('d');
        deletebutton.setText("Delete");
        deletebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(deletebutton);
        deletebutton.setBounds(810, 440, 150, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        try {
            ResultSet r;
            boolean selva = false;

            if (h1.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Account Number", "Account No", JOptionPane.ERROR_MESSAGE);
                h1.requestFocus();
                return;
            }
            if (h5.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter IFSC Code", "IFSC Code", JOptionPane.ERROR_MESSAGE);
                h5.requestFocus();
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
            if (h6.getText().equals("")) {
                h6.setText(".");
            }
            String ano = h1.getText();
            String aname = h2.getText();
            String bank = h3.getText();
            String branch = h4.getText();
            String ifsc = h5.getText();
            String remarks = h6.getText();

            String query = "select ano from bank where ano='" + h1.getText() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            int count;
            if (selva == true) {
                query = "update bank set aname='" + aname + "',bank='" + bank + "',branch='" + branch + "',ifsc='" + ifsc + "',remarks='" + remarks + "' where ano='" + ano + "'";
                count = util.doManipulation(query);
            } else {
                query = "insert into bank values ('" + ano + "','" + aname + "','" + bank + "','" + branch + "','" + ifsc + "','" + remarks + "' )";
                count = util.doManipulation(query);
            }
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Saved /Updated Successfully", "Saved", JOptionPane.PLAIN_MESSAGE);
                clear();
            }

        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

    }//GEN-LAST:event_savebuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebuttonActionPerformed
        try {

            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int as = JOptionPane.showConfirmDialog(this, "Want to Delete", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            String query = "delete from bank where ano='" + jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "'";
            int count = util.doManipulation(query);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Deleted Successfully", "Deleted", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_deletebuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        clear();
    }//GEN-LAST:event_clearbuttonActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        view(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());

    }//GEN-LAST:event_jTable1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton deletebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h2;
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h4;
    private javax.swing.JTextField h5;
    private javax.swing.JTextField h6;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    // End of variables declaration//GEN-END:variables
}
