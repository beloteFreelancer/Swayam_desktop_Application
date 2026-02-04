package itempack;

import com.selrom.db.DataUtil;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import menupack.menu_form;
import menupack.sample2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public class items_import_excel extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    ResultSet r;

    final void button_short() {
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Download Format</b><br>(Alt+I)</h6><html>");
        browsebutton.setText("<html><b>Browse File</b><br>(Alt+B)</h6><html>");
        removebutton.setText("<html><b>Remove Rows</b><br>(Alt+M)</h6><html>");
        loadbutton.setText("<html><b>Load Items</b><br>(Alt+A)</h6><html>");
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        titlelablel.setText("<html><u>Items Bulk Upload</u></html>");

        setTitle("Items Bulk Upload");
        this.setSize(1021, 648);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
    }

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
        s2.addColumn("Opening Stock");
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
        jTable1.getColumnModel().getColumn(12).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(13).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(14).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(15).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(16).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(17).setPreferredWidth(110);

        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    void load_excel() {
        try {
            if (s2.getRowCount() > 0) {
                JOptionPane.showMessageDialog(this, "Data Already There in Table!", "Data Exist", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JFileChooser jj = new JFileChooser();
            jj.showOpenDialog(this);
            File selectedFile = jj.getSelectedFile();
            if (selectedFile == null) {
                return; // User cancelled the dialog
            }
            String FILEPATH = selectedFile.getPath();

            int as = JOptionPane.showConfirmDialog(this, "Want to Load this file:  '" + FILEPATH + "'  ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }

            try (Workbook workbook = WorkbookFactory.create(selectedFile)) {
                Sheet sheet = workbook.getSheetAt(0);
                DataFormatter dataFormatter = new DataFormatter();
                ArrayList<Object[]> rowsData = new ArrayList<>();

                boolean firstRow = true;
                for (Row row : sheet) {
                    if (firstRow) {
                        firstRow = false;
                        continue; // Skip header row
                    }

                    Object[] rowData = new Object[jTable1.getColumnCount()];
                    boolean rowHasData = false;
                    for (int i = 0; i < jTable1.getColumnCount(); i++) {
                        Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        String cellValue = (cell == null) ? "" : dataFormatter.formatCellValue(cell);
                        rowData[i] = cellValue;
                        if (cellValue != null && !cellValue.trim().isEmpty()) {
                            rowHasData = true;
                        }
                    }

                    if (rowHasData) {
                        rowsData.add(rowData);
                    }
                }

                for (Object[] rowData : rowsData) {
                    s2.addRow(rowData);
                }
            }

            update_fields();
            totl.setText(" Total Records: " + jTable1.getRowCount());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading Excel file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void update_fields() {
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            Object ino = jTable1.getValueAt(i, 0);
            Object barcode = jTable1.getValueAt(i, 1);
            Object iname = jTable1.getValueAt(i, 2);
            Object iname1 = jTable1.getValueAt(i, 3);
            Object prate = jTable1.getValueAt(i, 4);
            Object taxp = jTable1.getValueAt(i, 5);
            Object mrp = jTable1.getValueAt(i, 6);
            Object rprice = jTable1.getValueAt(i, 7);
            Object wprice = jTable1.getValueAt(i, 8);
            Object cat = jTable1.getValueAt(i, 9);
            Object manu = jTable1.getValueAt(i, 10);
            Object hsn = jTable1.getValueAt(i, 11);
            Object udes = jTable1.getValueAt(i, 12);
            Object minstock = jTable1.getValueAt(i, 13);
            Object maxstock = jTable1.getValueAt(i, 14);
            Object rack = jTable1.getValueAt(i, 15);
            Object disp = jTable1.getValueAt(i, 16);
            Object ostock = jTable1.getValueAt(i, 17);

            if (barcode == null || barcode == "") {
                jTable1.setValueAt(ino, i, 1);
            }
            if (iname1 == null || iname1 == "") {
                jTable1.setValueAt(iname, i, 3);
            }
            if (prate == null || prate == "") {
                jTable1.setValueAt(0, i, 4);
            }
            if (taxp == null || taxp == "") {
                jTable1.setValueAt(0, i, 5);
            }
            if (mrp == null || mrp == "") {
                jTable1.setValueAt(0, i, 6);
            }
            if (rprice == null || rprice == "") {
                jTable1.setValueAt(0, i, 7);
            }
            if (wprice == null || wprice == "") {
                jTable1.setValueAt(0, i, 8);
            }
            if (cat == null || cat == "") {
                jTable1.setValueAt(".", i, 9);
            }
            if (manu == null || manu == "") {
                jTable1.setValueAt(".", i, 10);
            }
            if (hsn == null || hsn == "") {
                jTable1.setValueAt(".", i, 11);
            }
            if (udes == null || udes == "") {
                jTable1.setValueAt(".", i, 12);
            }
            if (minstock == null || minstock == "") {
                jTable1.setValueAt(0, i, 13);
            }
            if (maxstock == null || maxstock == "") {
                jTable1.setValueAt(0, i, 14);
            }
            if (rack == null || rack == "") {
                jTable1.setValueAt(".", i, 15);
            }
            if (disp == null || disp == "") {
                jTable1.setValueAt(0, i, 16);
            }
            if (ostock == null || ostock == "") {
                jTable1.setValueAt(0, i, 17);
            }
        }//for loop ends
    }

    void load_items() {
        try {
            String query = "select ino,barcode,iname,iname1,prate,taxp,mrp,rprice,wprice,cat,manu,hsn,udes,minstock,maxstock,rack,disp,ostock from item order by ino";
            r = util.doQuery(query);
            while (r.next()) {
                s2.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6), r.getString(7), r.getString(8), r.getString(9), r.getString(10), r.getString(11), r.getString(12), r.getString(13), r.getString(14), r.getString(15), r.getString(16), r.getString(17), r.getString(18)});
            }
            totl.setText(" Total Records: " + jTable1.getRowCount());
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void save() {
        Connection conn = null;
        PreparedStatement psItem = null;
        PreparedStatement psStock = null;
        try {
            int as = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }

            conn = util.getConnection();
            conn.setAutoCommit(false);

            String itemQuery = "INSERT INTO item (ino, barcode, iname, iname1, prate, taxp, mrp, rprice, wprice, cat, manu, hsn, udes, minstock, maxstock, rack, disp, ostock) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            psItem = conn.prepareStatement(itemQuery);

            String stockQuery = "INSERT INTO stock (barcode, ino, iname, mrp, rprice, wprice, prate, ostock, cat, entry_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            psStock = conn.prepareStatement(stockQuery);

            String entry = "purchase";

            for (int i = 0; i < jTable1.getRowCount(); i++) {
                psItem.setString(1, jTable1.getValueAt(i, 0).toString());
                psItem.setString(2, jTable1.getValueAt(i, 1).toString());
                psItem.setString(3, jTable1.getValueAt(i, 2).toString());
                psItem.setString(4, jTable1.getValueAt(i, 3).toString());
                psItem.setDouble(5, Double.parseDouble(jTable1.getValueAt(i, 4).toString()));
                psItem.setDouble(6, Double.parseDouble(jTable1.getValueAt(i, 5).toString()));
                psItem.setDouble(7, Double.parseDouble(jTable1.getValueAt(i, 6).toString()));
                psItem.setDouble(8, Double.parseDouble(jTable1.getValueAt(i, 7).toString()));
                psItem.setDouble(9, Double.parseDouble(jTable1.getValueAt(i, 8).toString()));
                psItem.setString(10, jTable1.getValueAt(i, 9).toString());
                psItem.setString(11, jTable1.getValueAt(i, 10).toString());
                psItem.setString(12, jTable1.getValueAt(i, 11).toString());
                psItem.setString(13, jTable1.getValueAt(i, 12).toString());
                psItem.setDouble(14, Double.parseDouble(jTable1.getValueAt(i, 13).toString()));
                psItem.setDouble(15, Double.parseDouble(jTable1.getValueAt(i, 14).toString()));
                psItem.setString(16, jTable1.getValueAt(i, 15).toString());
                psItem.setDouble(17, Double.parseDouble(jTable1.getValueAt(i, 16).toString()));
                psItem.setDouble(18, Double.parseDouble(jTable1.getValueAt(i, 17).toString()));
                psItem.addBatch();

                psStock.setString(1, jTable1.getValueAt(i, 1).toString());
                psStock.setString(2, jTable1.getValueAt(i, 0).toString());
                psStock.setString(3, jTable1.getValueAt(i, 2).toString());
                psStock.setDouble(4, Double.parseDouble(jTable1.getValueAt(i, 6).toString()));
                psStock.setDouble(5, Double.parseDouble(jTable1.getValueAt(i, 7).toString()));
                psStock.setDouble(6, Double.parseDouble(jTable1.getValueAt(i, 8).toString()));
                psStock.setDouble(7, Double.parseDouble(jTable1.getValueAt(i, 4).toString()));
                psStock.setDouble(8, Double.parseDouble(jTable1.getValueAt(i, 17).toString()));
                psStock.setString(9, jTable1.getValueAt(i, 9).toString());
                psStock.setString(10, entry);
                psStock.addBatch();
            }

            psItem.executeBatch();
            psStock.executeBatch();
            conn.commit();

            JOptionPane.showMessageDialog(this, "<html><h1>Saved Successfully</h1></html>", "Saved", JOptionPane.PLAIN_MESSAGE);
            clear();

        } catch (HeadlessException | SQLException | NumberFormatException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (psItem != null) {
                    psItem.close();
                }
                if (psStock != null) {
                    psStock.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    void clear() {
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        totl.setText("");
    }

    public items_import_excel(DataUtil util) {
        initComponents();

        this.util = util;
        button_short();
        load_list_table();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        excelbutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        totl = new javax.swing.JLabel();
        browsebutton = new javax.swing.JButton();
        loadbutton = new javax.swing.JButton();
        removebutton = new javax.swing.JButton();
        savebutton = new javax.swing.JButton();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Items Bulk Upload");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 400, 30);

        jTable1.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
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
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable1FocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 52, 1000, 480);

        excelbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        excelbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/excel45.png"))); // NOI18N
        excelbutton.setMnemonic('i');
        excelbutton.setText("Download Format");
        excelbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excelbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(excelbutton);
        excelbutton.setBounds(300, 560, 140, 50);

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
        clearbutton.setBounds(720, 560, 140, 50);

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
        closebutton.setBounds(860, 560, 140, 50);

        totl.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(totl);
        totl.setBounds(0, 530, 1000, 30);

        browsebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        browsebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/browse45.png"))); // NOI18N
        browsebutton.setMnemonic('b');
        browsebutton.setText("Browse File");
        browsebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browsebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(browsebutton);
        browsebutton.setBounds(0, 560, 160, 50);

        loadbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        loadbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/load45.jpg.png"))); // NOI18N
        loadbutton.setMnemonic('a');
        loadbutton.setText("Load Items");
        loadbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(loadbutton);
        loadbutton.setBounds(580, 560, 140, 50);

        removebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        removebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/remove45.png"))); // NOI18N
        removebutton.setMnemonic('m');
        removebutton.setText("Remove Rows");
        removebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(removebutton);
        removebutton.setBounds(440, 560, 140, 50);

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
        savebutton.setBounds(160, 560, 140, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

    }//GEN-LAST:event_jTable1MouseClicked

    private void excelbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excelbuttonActionPerformed

        try {
            menupack.menu_form mp = new menu_form();
            String drive = mp.getDrive();
            String folder = mp.getFoler();

            String file_name = "Items_List_Upload";

            try (FileWriter f = new FileWriter(new File(folder + "/Drafts/" + file_name + ".xls"))) {
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
            }
            Runtime rt = Runtime.getRuntime();
            Utils.AppConfig.openFile(folder + "/Drafts/" + file_name + ".xls");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }//GEN-LAST:event_excelbuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        clear();

    }//GEN-LAST:event_clearbuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained

    }//GEN-LAST:event_jTable1FocusGained

    private void browsebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browsebuttonActionPerformed
        load_excel();
    }//GEN-LAST:event_browsebuttonActionPerformed

    private void loadbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadbuttonActionPerformed
        if (s2.getRowCount() > 0) {
            JOptionPane.showMessageDialog(this, "Data Already There in Table!", "Data Exist", JOptionPane.ERROR_MESSAGE);
            return;
        }
        load_items();
    }//GEN-LAST:event_loadbuttonActionPerformed

    private void removebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removebuttonActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int[] row_indexes = jTable1.getSelectedRows();
        for (int i = 0; i < row_indexes.length; i++) {
            s2.removeRow(jTable1.getSelectedRow());
        }
        totl.setText(" Total Records: " + jTable1.getRowCount());
    }//GEN-LAST:event_removebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        save();
    }//GEN-LAST:event_savebuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browsebutton;
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton excelbutton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton loadbutton;
    private javax.swing.JButton removebutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JLabel totl;
    // End of variables declaration//GEN-END:variables
}
