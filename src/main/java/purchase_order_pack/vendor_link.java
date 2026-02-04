package purchase_order_pack;

import com.selrom.db.DataUtil;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public final class vendor_link extends javax.swing.JInternalFrame {

    DataUtil util = null;

    public class sample2 extends DefaultTableModel {

        @Override
        public void addColumn(Object columnName) {
            super.addColumn(columnName);
        }

        @Override
        public void addRow(Object[] rowData) {
            super.addRow(rowData);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3 || column == 4 || column == 5 || column == 6 || column == 7 || column == 9;
        }
    }

    sample2 s2 = new sample2();
    sample2 s3 = new sample2();
    sample2 s4 = new sample2();

    final void button_short() {
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        viewbutton.setText("<html><b>View</b><br>(Alt+V)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        removebutton.setText("<html><b>Remove</b><br>(Alt+M)</h6><html>");

        titlelablel.setText("<html><u>Supplier & Products Link</u></html>");
    }

    public final void load_items_table() {
        s2.addColumn("It.Code");
        s2.addColumn("Name");
        s2.addColumn("Category");
        s2.addColumn("Manufacturer");
        s2.addColumn("Tax %");
        s2.addColumn("Re-Order Qty");
        jTable1.setModel(s2);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(400);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(230);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(234);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    void load_supplier_table() {
        try {
            s3.addColumn("Name");
            s3.addColumn("City");
            jTable2.setModel(s3);
            jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(500);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(347);
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable2.getTableHeader().setFont(new Font(Ta, Bold, size));
        } catch (Exception e) {
        }
    }

    void load_iname_table() {
        try {
            s4.addColumn("It.Code");
            s4.addColumn("Item Name");
            s4.addColumn("Category");
            s4.addColumn("Manufacturer");
            jTable3.setModel(s4);
            jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(317);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(220);
            jTable3.getColumnModel().getColumn(3).setPreferredWidth(270);
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable3.getTableHeader().setFont(new Font(Ta, Bold, size));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void add_item() {
        try {
            if (h8.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Item Particulars ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h8.requestFocus();
                return;
            }
            if (h9.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Item Particulars ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h8.requestFocus();
                return;
            }
            String manu = "", cat = "", taxp = "", minstock = "";
            String query = "select cat,manu,taxp,minstock from item where ino='" + h8.getText() + "'";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                cat = r.getString(1);
                manu = r.getString(2);
                taxp = r.getString(3);
                minstock = r.getString(4);
            }
            s2.addRow(new Object[] { h8.getText(), h9.getText(), cat, manu, taxp, minstock });
            totl.setText("Total Items: " + jTable1.getRowCount());
            h8.setText("");
            h9.setText("");
            h8.requestFocus();
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void form_clear() {
        h8.setText("");
        h9.setText("");
        h3.setText("");
        h3.requestFocus();
        totl.setText("");
        deletebutton.setVisible(false);
        viewbutton.setVisible(true);
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
    }

    void save() {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found to Save!", "No Records",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (h3.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Supplier Name ?", "Supplier Name",
                        JOptionPane.ERROR_MESSAGE);
                h3.requestFocus();
                return;
            }
            int aa = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (aa == JOptionPane.NO_OPTION) {
                return;
            }
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);

            ArrayList query_list = new ArrayList();
            boolean selva = false;
            String query = "select cname from vendor_link where cname='" + h3.getText() + "'";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva = true) {
                query_list.add("delete from vendor_link where cname='" + h3.getText() + "'");
            }

            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String ino = jTable1.getValueAt(i, 0).toString();
                String iname = jTable1.getValueAt(i, 1).toString();
                String cat = jTable1.getValueAt(i, 2).toString();
                String manu = jTable1.getValueAt(i, 3).toString();
                String taxp = jTable1.getValueAt(i, 4).toString();
                String minstock = jTable1.getValueAt(i, 5).toString();
                query_list.add("insert into vendor_link values ('" + h3.getText() + "','" + ino + "','" + iname + "','"
                        + cat + "','" + manu + "','" + taxp + "','" + minstock + "','" + last + "')");
            }
            int count = util.doManipulation_Batch(query_list);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Saved Successfully</h1></html>", "Saved",
                        JOptionPane.PLAIN_MESSAGE);
                form_clear();
            } else {
                JOptionPane.showMessageDialog(this, "Check Product Entries and then Try Again!", "Invalid Products",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void view() {
        try {
            if (h3.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Supplier Name ?", "Supplier Name",
                        JOptionPane.ERROR_MESSAGE);
                h3.requestFocus();
                return;
            }
            if (s2.getRowCount() > 0) {
                s2.getDataVector().removeAllElements();
                s2.fireTableDataChanged();
            }
            String query = "select distinct cname from vendor_link where cname='" + h3.getText() + "'";
            ResultSet r = util.doQuery(query);
            boolean selva = false;
            while (r.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            } else {
                query = "select ino,iname,cat,manu,taxp,minstock from vendor_link where cname='" + h3.getText() + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    s2.addRow(new Object[] { r.getString(1), r.getString(2), r.getString(3), r.getString(4),
                            r.getString(5), r.getString(6) });
                }
                totl.setText(" Total Items: " + jTable1.getRowCount());
                viewbutton.setVisible(false);
                deletebutton.setVisible(true);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void delete() {
        try {
            if (h3.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Supplier Name ?", "Supplier Name",
                        JOptionPane.ERROR_MESSAGE);
                h3.requestFocus();
                return;
            }
            int as = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Delete ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            String query = "delete from vendor_link where cname='" + h3.getText() + "' ";
            int a = util.doManipulation(query);
            if (a > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Deleted Successfully</h1></html>", "Deleted",
                        JOptionPane.PLAIN_MESSAGE);
                form_clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public vendor_link(DataUtil util) {
        initComponents();
        this.setTitle("Supplier & Products Link");
        this.setSize(1258, 650);
        java.net.URL iconUrl = ClassLoader.getSystemResource("/images/icon.png");
        if (iconUrl != null) {
            ImageIcon icon = new ImageIcon(iconUrl);
            this.setFrameIcon(icon);
        }
        this.util = util;
        button_short();
        load_items_table();
        h3.requestFocus();
        load_supplier_table();
        load_iname_table();
        deletebutton.setVisible(false);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cname_list = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        iname_list = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        titlelablel = new javax.swing.JLabel();
        savebutton = new javax.swing.JButton();
        viewbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        h3 = new javax.swing.JTextField();
        h9 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        totl = new javax.swing.JLabel();
        h8 = new javax.swing.JTextField();
        clearbutton = new javax.swing.JButton();
        removebutton = new javax.swing.JButton();
        deletebutton = new javax.swing.JButton();
        netl = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();

        cname_list.setUndecorated(true);
        cname_list.getContentPane().setLayout(null);

        jScrollPane2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jScrollPane2FocusLost(evt);
            }
        });

        jTable2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }));
        jTable2.setRowHeight(25);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        cname_list.getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(0, 0, 850, 400);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete22.png"))); // NOI18N
        jButton3.setMnemonic('o');
        jButton3.setText("Close");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        cname_list.getContentPane().add(jButton3);
        jButton3.setBounds(710, 400, 140, 30);

        iname_list.setUndecorated(true);
        iname_list.getContentPane().setLayout(null);

        jScrollPane3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jScrollPane3FocusLost(evt);
            }
        });

        jTable3.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }));
        jTable3.setRowHeight(25);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable3KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        iname_list.getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(0, 0, 910, 350);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete22.png"))); // NOI18N
        jButton4.setMnemonic('o');
        jButton4.setText("Close");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        iname_list.getContentPane().add(jButton4);
        jButton4.setBounds(770, 350, 140, 30);

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Supplier & Products Link");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 310, 30);

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
        savebutton.setBounds(480, 560, 150, 50);

        viewbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        viewbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/view45.png"))); // NOI18N
        viewbutton.setMnemonic('v');
        viewbutton.setText("View");
        viewbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(viewbutton);
        viewbutton.setBounds(780, 560, 150, 50);

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
        closebutton.setBounds(1080, 560, 150, 50);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Supplier");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(10, 50, 70, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h3KeyPressed(evt);
            }
        });
        getContentPane().add(h3);
        h3.setBounds(80, 50, 600, 30);

        h9.setEditable(false);
        h9.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        getContentPane().add(h9);
        h9.setBounds(240, 110, 440, 30);

        jTable1.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
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
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 150, 1220, 370);

        totl.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(totl);
        totl.setBounds(10, 520, 1220, 40);

        h8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h8ActionPerformed(evt);
            }
        });
        h8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h8KeyPressed(evt);
            }
        });
        getContentPane().add(h8);
        h8.setBounds(80, 110, 160, 30);

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
        clearbutton.setBounds(930, 560, 150, 50);

        removebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        removebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/remove45.png"))); // NOI18N
        removebutton.setMnemonic('m');
        removebutton.setText("Remove");
        removebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(removebutton);
        removebutton.setBounds(630, 560, 150, 50);

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
        deletebutton.setBounds(320, 560, 160, 50);

        netl.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        netl.setForeground(new java.awt.Color(255, 0, 0));
        netl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(netl);
        netl.setBounds(680, 20, 300, 50);

        jLabel31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel31.setText("Item Code");
        getContentPane().add(jLabel31);
        jLabel31.setBounds(10, 110, 80, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }// GEN-LAST:event_closebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_savebuttonActionPerformed
        save();

    }// GEN-LAST:event_savebuttonActionPerformed

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_viewbuttonActionPerformed
        view();

    }// GEN-LAST:event_viewbuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbuttonActionPerformed
        form_clear();
    }// GEN-LAST:event_clearbuttonActionPerformed

    private void removebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_removebuttonActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int[] row_indexes = jTable1.getSelectedRows();
        for (int i = 0; i < row_indexes.length; i++) {
            s2.removeRow(jTable1.getSelectedRow());
        }
        totl.setText(" Total Items: " + jTable1.getRowCount());
        h8.requestFocus();
    }// GEN-LAST:event_removebuttonActionPerformed

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deletebuttonActionPerformed
        delete();
    }// GEN-LAST:event_deletebuttonActionPerformed

    private void h8ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h8ActionPerformed

    }// GEN-LAST:event_h8ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
        cname_list.dispose();
    }// GEN-LAST:event_jButton3ActionPerformed

    private void h3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_h3KeyPressed
        cname_list.requestFocus();
        jTable2.requestFocus();
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                h8.requestFocus();
                break;
            case KeyEvent.VK_ESCAPE:
                h3.requestFocus();
                cname_list.dispose();
                break;
            case KeyEvent.VK_DOWN:
                if (s3.getRowCount() > 0) {
                    s3.getDataVector().removeAllElements();
                    s3.fireTableDataChanged();
                }
                try {
                    cname_list.requestFocus();
                    Point l = h3.getLocationOnScreen();
                    cname_list.setLocation(l.x, l.y + h3.getHeight());
                    cname_list.setSize(857, 438);
                    cname_list.setVisible(true);
                    String query = "select distinct cname,city from vendor where cname like '" + h3.getText()
                            + "%' order by cname limit 300";
                    ResultSet r = util.doQuery(query);
                    while (r.next()) {
                        s3.addRow(new Object[] { r.getString(1), r.getString(2) });
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                break;
        }

    }// GEN-LAST:event_h3KeyPressed

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTable2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable2.getRowCount() > 0) {
                h3.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
            }
            cname_list.dispose();
            h8.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cname_list.dispose();
            h3.requestFocus();
        }

    }// GEN-LAST:event_jTable2KeyPressed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable2MouseClicked

        if (jTable2.getRowCount() > 0) {
            h3.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
        }
        h8.requestFocus();
        cname_list.dispose();
    }// GEN-LAST:event_jTable2MouseClicked

    private void jScrollPane2FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jScrollPane2FocusLost
        cname_list.dispose();
    }// GEN-LAST:event_jScrollPane2FocusLost

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable3MouseClicked
        if (jTable3.getRowCount() > 0) {
            h8.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
            h9.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 1).toString());
        }
        h8.requestFocus();
        iname_list.dispose();
    }// GEN-LAST:event_jTable3MouseClicked

    private void jTable3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTable3KeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable3.getRowCount() > 0) {
                h8.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
                h9.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 1).toString());
            }
            h8.requestFocus();
            iname_list.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            iname_list.dispose();
            h8.requestFocus();
        }
    }// GEN-LAST:event_jTable3KeyPressed

    private void jScrollPane3FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jScrollPane3FocusLost
        iname_list.dispose();
    }// GEN-LAST:event_jScrollPane3FocusLost

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
        iname_list.dispose();
    }// GEN-LAST:event_jButton4ActionPerformed

    private void h8KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_h8KeyPressed

        iname_list.requestFocus();
        jTable3.requestFocus();
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                add_item();
                break;
            case KeyEvent.VK_ESCAPE:
                h8.requestFocus();
                iname_list.dispose();
                break;
            case KeyEvent.VK_DOWN:
                if (s4.getRowCount() > 0) {
                    s4.getDataVector().removeAllElements();
                    s4.fireTableDataChanged();
                }
                try {
                    iname_list.requestFocus();
                    Point l = h8.getLocationOnScreen();
                    iname_list.setLocation(l.x, l.y + h8.getHeight());
                    iname_list.setSize(916, 382);
                    iname_list.setVisible(true);
                    String query = "select distinct ino,iname,cat,manu from item where iname like '" + h8.getText()
                            + "%' order by ino limit 500";
                    ResultSet r = util.doQuery(query);
                    while (r.next()) {
                        s4.addRow(new Object[] { r.getString(1), r.getString(2), r.getString(3), r.getString(4) });
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                break;
        }

    }// GEN-LAST:event_h8KeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JDialog cname_list;
    private javax.swing.JButton deletebutton;
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h8;
    private javax.swing.JTextField h9;
    private javax.swing.JDialog iname_list;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JLabel netl;
    private javax.swing.JButton removebutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JLabel totl;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
