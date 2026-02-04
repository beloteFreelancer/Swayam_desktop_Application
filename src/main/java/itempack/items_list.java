package itempack;

import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.selrom.db.DataUtil;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import menupack.menu_form;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class items_list extends javax.swing.JInternalFrame {

    DataUtil util = null;
    ResultSet r;
    AutoCompleteSupport support;

    final void button_short() {
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Excel</b><br>(Alt+I)</h6><html>");
        viewbutton.setText("<html><b>View</b><br>(Alt+V)</h6><html>");
        titlelablel.setText("<html><u>Items List</u></html>");
        allbutton.setText("<html><b>View All</b><br>(Alt+A)</h6><html>");
        updatebutton.setText("<html><b>Update</b><br>(Alt+U)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        setTitle("Items List");
        this.setSize(1237, 648);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
    }

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
            return column != 0;
        }
    }
    sample2 s2 = new sample2();

    final void load_list_table() {
        s2.addColumn("It.Code");
        s2.addColumn("Barcode");
        s2.addColumn("It.Name");
        s2.addColumn("Print Name");
        s2.addColumn("Purchase Price");
        s2.addColumn("Tax %");
        s2.addColumn("MRP");
        s2.addColumn("Retail Price");
        s2.addColumn("Wholesale Price");
        s2.addColumn("Category");
        s2.addColumn("Manufacturer");
        s2.addColumn("HSN Code");
        s2.addColumn("Unit_Desc");
        s2.addColumn("Re-Order Qty");
        s2.addColumn("Max.Stock Qty");
        s2.addColumn("Location");
        s2.addColumn("Dis %");

        jTable1.setModel(s2);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(294);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(280);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(10).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(11).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(12).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(13).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(14).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(15).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(16).setPreferredWidth(100);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    void load_report(String query) {
        try {
            if (s2.getRowCount() > 0) {
                s2.getDataVector().removeAllElements();
                s2.fireTableDataChanged();
            }
            r = util.doQuery(query);
            while (r.next()) {
                s2.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6), r.getString(7), r.getString(8), r.getString(9), r.getString(10), r.getString(11), r.getString(12), r.getString(13), r.getString(14), r.getString(15), r.getString(16), r.getString(17)});
            }
            totl.setText(" Total Records: " + jTable1.getRowCount());
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_default_values() {
        try {
            h3.removeAllItems();
            h4.removeAllItems();
            h6.removeAllItems();
            String query = "select distinct cat from item";
            r = util.doQuery(query);
            while (r.next()) {
                h3.addItem(r.getString(1));
            }
            query = "select distinct manu from item";
            r = util.doQuery(query);
            while (r.next()) {
                h4.addItem(r.getString(1));
            }
            query = "select distinct rack from item";
            r = util.doQuery(query);
            while (r.next()) {
                h6.addItem(r.getString(1));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public items_list(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();
        load_list_table();
        get_default_values();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        generatebutton = new javax.swing.JButton();
        excelbutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        h3 = new javax.swing.JComboBox();
        cnamel = new javax.swing.JLabel();
        totl = new javax.swing.JLabel();
        viewbutton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        generatebutton2 = new javax.swing.JButton();
        generatebutton4 = new javax.swing.JButton();
        generatebutton5 = new javax.swing.JButton();
        h5 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        h4 = new javax.swing.JComboBox<>();
        h6 = new javax.swing.JComboBox<>();
        allbutton = new javax.swing.JButton();
        deletebutton = new javax.swing.JButton();
        updatebutton = new javax.swing.JButton();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Items List");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 400, 30);

        generatebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/searchh.png"))); // NOI18N
        generatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton);
        generatebutton.setBounds(510, 80, 40, 30);

        excelbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        excelbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/excel45.png"))); // NOI18N
        excelbutton.setMnemonic('i');
        excelbutton.setText("Excel");
        excelbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excelbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(excelbutton);
        excelbutton.setBounds(840, 560, 130, 50);

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
        clearbutton.setBounds(970, 560, 130, 50);

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
        closebutton.setBounds(1100, 560, 120, 50);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Item Location");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(670, 80, 90, 30);

        h3.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        h3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        h3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                h3ItemStateChanged(evt);
            }
        });
        getContentPane().add(h3);
        h3.setBounds(70, 50, 440, 30);

        cnamel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cnamel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(cnamel);
        cnamel.setBounds(430, 0, 560, 30);

        totl.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(totl);
        totl.setBounds(0, 530, 1220, 30);

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
        viewbutton.setBounds(450, 560, 130, 50);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Barcode");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(10, 80, 60, 30);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Category");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(10, 50, 60, 30);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Manufacturer");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(670, 50, 90, 30);

        generatebutton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/searchh.png"))); // NOI18N
        generatebutton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebutton2ActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton2);
        generatebutton2.setBounds(1180, 80, 40, 30);

        generatebutton4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/searchh.png"))); // NOI18N
        generatebutton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebutton4ActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton4);
        generatebutton4.setBounds(510, 50, 40, 30);

        generatebutton5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/searchh.png"))); // NOI18N
        generatebutton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebutton5ActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton5);
        generatebutton5.setBounds(1180, 50, 40, 30);

        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h5);
        h5.setBounds(70, 80, 440, 30);

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
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(2, 120, 1220, 410);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h4);
        h4.setBounds(760, 50, 420, 30);

        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h6);
        h6.setBounds(760, 80, 420, 30);

        allbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        allbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search45.png"))); // NOI18N
        allbutton.setMnemonic('v');
        allbutton.setText("View All");
        allbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(allbutton);
        allbutton.setBounds(300, 560, 150, 50);

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
        deletebutton.setBounds(710, 560, 130, 50);

        updatebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        updatebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/upload45.png"))); // NOI18N
        updatebutton.setMnemonic('u');
        updatebutton.setText("Update");
        updatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(updatebutton);
        updatebutton.setBounds(580, 560, 130, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generatebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatebuttonActionPerformed
        String query = "select ino,barcode,iname,iname1,prate,taxp,mrp,rprice,wprice,cat,manu,hsn,udes,minstock,maxstock,rack,disp from item where barcode='" + h5.getText() + "' order by ino";
        load_report(query);
    }//GEN-LAST:event_generatebuttonActionPerformed

    private void excelbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excelbuttonActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Sorry, No Records Were Found!", "Oops", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            menupack.menu_form mp = new menu_form();
            String drive = mp.getDrive();
            String folder = mp.getFoler();

            String file_name = "List";

            try (FileWriter f = new FileWriter(new File(folder + "/Drafts/" + file_name + ".xls"))) {
                f.write("Items List:\n");
                for (int i1 = 0; i1 < jTable1.getColumnCount(); i1++) {
                    String kg = jTable1.getColumnName(i1);
                    f.write(kg);
                    f.write("\t");
                }
                f.write("\n");
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    for (int j = 0; j < jTable1.getColumnCount(); j++) {
                        String val = jTable1.getValueAt(i, j).toString();
                        f.write(val);
                        f.write("\t");
                    }
                    f.write("\n");
                }
                f.write("" + totl.getText());
            }
            Runtime rt = Runtime.getRuntime();
            Utils.AppConfig.openFile(folder + "/Drafts/" + file_name + ".xls");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }//GEN-LAST:event_excelbuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        h3.setEnabled(true);
        generatebutton.setEnabled(true);
        h3.setSelectedItem("");
        cnamel.setText("");
        totl.setText("");

    }//GEN-LAST:event_clearbuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void h3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_h3ItemStateChanged

    }//GEN-LAST:event_h3ItemStateChanged

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewbuttonActionPerformed
        item_master oe = new item_master(util);
        JDesktopPane desktop_pane = getDesktopPane();
        desktop_pane.add(oe);
        String sno = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
        oe.view(sno);
        oe.setVisible(true);

        Dimension desktopSize = desktop_pane.getSize();
        Dimension jInternalFrameSize = oe.getSize();
        oe.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);
    }//GEN-LAST:event_viewbuttonActionPerformed

    private void generatebutton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatebutton2ActionPerformed
        String query = "select ino,barcode,iname,iname1,prate,taxp,mrp,rprice,wprice,cat,manu,hsn,udes,minstock,maxstock,rack,disp from item where rack='" + h6.getSelectedItem() + "' order by ino";
        load_report(query);
    }//GEN-LAST:event_generatebutton2ActionPerformed

    private void generatebutton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatebutton4ActionPerformed
        String query = "select ino,barcode,iname,iname1,prate,taxp,mrp,rprice,wprice,cat,manu,hsn,udes,minstock,maxstock,rack,disp from item where cat='" + h3.getSelectedItem() + "' order by ino";
        load_report(query);
    }//GEN-LAST:event_generatebutton4ActionPerformed

    private void generatebutton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatebutton5ActionPerformed
        String query = "select ino,barcode,iname,iname1,prate,taxp,mrp,rprice,wprice,cat,manu,hsn,udes,minstock,maxstock,rack,disp from item where manu='" + h4.getSelectedItem() + "' order by ino";
        load_report(query);
    }//GEN-LAST:event_generatebutton5ActionPerformed

    private void allbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allbuttonActionPerformed
        String query = "select ino,barcode,iname,iname1,prate,taxp,mrp,rprice,wprice,cat,manu,hsn,udes,minstock,maxstock,rack,disp from item order by ino";
        load_report(query);
    }//GEN-LAST:event_allbuttonActionPerformed

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebuttonActionPerformed
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "Invalid", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int as = JOptionPane.showConfirmDialog(this, "Want to Delete ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            ArrayList query_batch = new ArrayList();
            String ino = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
            query_batch.add("delete from item where ino='" + ino + "' ");
            query_batch.add("delete from stock where ino='" + ino + "' ");
            int count = util.doManipulation_Batch(query_batch);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Deleted Successfully", "Deleted", JOptionPane.PLAIN_MESSAGE);
                s2.removeRow(jTable1.getSelectedRow());
                totl.setText(" Total Records: " + jTable1.getRowCount());
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_deletebuttonActionPerformed

    private void updatebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatebuttonActionPerformed
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "Invalid", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int as = JOptionPane.showConfirmDialog(this, "Want to Update ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            ArrayList query_batch = new ArrayList();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String ino = jTable1.getValueAt(i, 0).toString();
                String barcode = jTable1.getValueAt(i, 1).toString();
                String iname = jTable1.getValueAt(i, 2).toString();
                String iname1 = jTable1.getValueAt(i, 3).toString();
                String prate = jTable1.getValueAt(i, 4).toString();
                String taxp = jTable1.getValueAt(i, 5).toString();
                String mrp = jTable1.getValueAt(i, 6).toString();
                String rprice = jTable1.getValueAt(i, 7).toString();
                String wprice = jTable1.getValueAt(i, 8).toString();
                String cat = jTable1.getValueAt(i, 9).toString();
                String manu = jTable1.getValueAt(i, 10).toString();
                String hsn = jTable1.getValueAt(i, 11).toString();
                String udes = jTable1.getValueAt(i, 12).toString();
                String minstock = jTable1.getValueAt(i, 13).toString();
                String maxstock = jTable1.getValueAt(i, 14).toString();
                String rack = jTable1.getValueAt(i, 15).toString();
                String disp = jTable1.getValueAt(i, 16).toString();
                query_batch.add("update item set barcode='" + barcode + "',iname='" + iname + "',iname1='" + iname1 + "',prate='" + prate + "',taxp='" + taxp + "',mrp='" + mrp + "',rprice='" + rprice + "',wprice='" + wprice + "',cat='" + cat + "',manu='" + manu + "',hsn='" + hsn + "',udes='" + udes + "',minstock='" + minstock + "',maxstock='" + maxstock + "',rack='" + rack + "',disp='" + disp + "' where ino='" + ino + "'");
                query_batch.add("update stock set barcode='" + barcode + "',iname='" + iname + "',mrp='" + mrp + "',rprice='" + rprice + "',wprice='" + wprice + "',prate='" + prate + "',cat='" + cat + "' where ino='" + ino + "' ");
            }
            int a = util.doManipulation_Batch(query_batch);
            if (a > 0) {
                JOptionPane.showMessageDialog(this, "Updated Successfully", "Updated", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Check Entries, Try Again...");
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_updatebuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton allbutton;
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JLabel cnamel;
    private javax.swing.JButton deletebutton;
    private javax.swing.JButton excelbutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JButton generatebutton2;
    private javax.swing.JButton generatebutton4;
    private javax.swing.JButton generatebutton5;
    private javax.swing.JComboBox h3;
    private javax.swing.JComboBox<String> h4;
    private javax.swing.JTextField h5;
    private javax.swing.JComboBox<String> h6;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JLabel totl;
    private javax.swing.JButton updatebutton;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
