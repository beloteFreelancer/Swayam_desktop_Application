package custpack;

import com.selrom.db.DataUtil;
import com.selrom.utils.ExcelUtil;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import menupack.menu_form;
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public class cust_bills_bulk_upload extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    ResultSet r;
    String username;

    final void button_short() {
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Download Format</b><br>(Alt+I)</h6><html>");
        browsebutton.setText("<html><b>Browse File</b><br>(Alt+B)</h6><html>");
        removebutton.setText("<html><b>Remove Rows</b><br>(Alt+M)</h6><html>");
        loadbutton.setText("<html><b>Load List</b><br>(Alt+A)</h6><html>");
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        titlelablel.setText("<html><u>Customer Bills Bulk Upload (Opening Balance)</u></html>");

        setTitle("Customer Bills Bulk Upload (Opening Balance)");
        this.setSize(1021, 648);
        java.net.URL imgUrl = getClass().getResource("/images/icon.png");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            this.setFrameIcon(icon);
        }
        menu_form me = new menu_form();
        username = me.getUsername();
    }

    public final void load_list_table() {
        s2.addColumn("Cust_Id");
        s2.addColumn("Cust_Name");
        s2.addColumn("Bill No");
        s2.addColumn("Bill Date");
        s2.addColumn("Due Date");
        s2.addColumn("Amount");
        s2.addColumn("Remarks");
        jTable1.setModel(s2);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(250);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(124);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(200);
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
                return;
            }
            String FILEPATH = selectedFile.getPath();
            File file = new File(FILEPATH);

            int as = JOptionPane.showConfirmDialog(this, "Want to Load this file:  '" + FILEPATH + "'  ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }

            // Legacy JXL is deprecated but keeping for import as requested,
            // Assuming the user is uploading .xls files.
            // If .xlsx is needed, we would need to switch to POI here as well.
            // For now, I'm keeping the logic as is for IMPORT, but fixing EXPORT.

            Workbook workbook = Workbook.getWorkbook(file);
            Sheet sheet = workbook.getSheet(0);

            ArrayList list = new ArrayList();
            for (int j = 1; j < sheet.getRows(); j++) {
                for (int i = 0; i < sheet.getColumns(); i++) {
                    Cell cell = sheet.getCell(i, j);
                    list.add(cell.getContents());
                }
            }
            int rows = list.size() / 7;
            for (int i = 0; i < rows; i++) {
                s2.addRow(new Object[]{"", "", "", "", "", "", ""});
            }
            int val = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                for (int j = 0; j < jTable1.getColumnCount(); j++) {
                    jTable1.setValueAt(list.get(val), i, j);
                    val++;
                }
            }
            totl.setText(" Total Records: " + jTable1.getRowCount());
        } catch (IOException | IndexOutOfBoundsException | BiffException e) {
            System.out.println(e.getMessage());
        }
    }

    void load_items() {
        try {
            String query;
            query = "select cid,cname,billno,date_format(dat,'%d/%m/%Y'),date_format(ddate,'%d/%m/%Y'),amount,remarks from cust_bill order by sno";
            r = util.doQuery(query);
            while (r.next()) {
                s2.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6), r.getString(7)});
            }
            totl.setText(" Total Records: " + jTable1.getRowCount());
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void clear() {
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        totl.setText("");
    }

    void save() {
        try {
            int as = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }

            String query;
            boolean selva = false;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String cid = jTable1.getValueAt(i, 0).toString();
                String cname = jTable1.getValueAt(i, 1).toString();
                String billno = jTable1.getValueAt(i, 2).toString();
                query = "select billno from cust_bill where cid='" + cid + "' and cname='" + cname + "' and billno='" + billno + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    selva = true;
                }
                if (selva == true) {
                    JOptionPane.showMessageDialog(this, "This Bill No:" + billno + " is Already Exist for " + cname + "", "Already Exist", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }//checking

//Serial Number
            int sno = 1;
            query = "select max(sno) from cust_bill";
            r = util.doQuery(query);
            boolean selva1 = false;
            while (r.next()) {
                selva1 = true;
                sno = r.getInt(1);
            }
            if (selva1 == true) {
                sno = sno + 1;
            }
//Serial Number Ends

            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);
            double paid = 0;
            ArrayList query_list = new ArrayList();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String cid = jTable1.getValueAt(i, 0).toString();
                String cname = jTable1.getValueAt(i, 1).toString();
                String billno = jTable1.getValueAt(i, 2).toString();
                String bill_date = jTable1.getValueAt(i, 3).toString();
                String due_date = jTable1.getValueAt(i, 4).toString();
                String amount = jTable1.getValueAt(i, 5).toString();
                String remarks = jTable1.getValueAt(i, 6).toString();

                Date nm = new SimpleDateFormat("dd.MM.yyyy").parse(bill_date);
                String date = (new SimpleDateFormat("yyyy-MM-dd").format(nm));
                Date nm1 = new SimpleDateFormat("dd.MM.yyyy").parse(due_date);
                String ddate = (new SimpleDateFormat("yyyy-MM-dd").format(nm1));

                query_list.add("insert into cust_bill values ('" + sno + "','" + cid + "','" + cname + "','" + billno + "','" + date + "','" + ddate + "','" + amount + "','" + remarks + "','" + username + "','" + last + "')");
                query_list.add("insert into cust_bal values ('" + billno + "','" + date + "','" + ddate + "','" + cid + "','" + cname + "','" + amount + "','" + paid + "','" + last + "' ) ");
                sno = sno + 1;
            }
            int aa = util.doManipulation_Batch(query_list);
            if (aa > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Saved Successfully</h1></html>", "Saved", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException | ParseException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public cust_bills_bulk_upload(DataUtil util) {
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
        closebutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        loadbutton = new javax.swing.JButton();
        removebutton = new javax.swing.JButton();
        excelbutton = new javax.swing.JButton();
        savebutton = new javax.swing.JButton();
        browsebutton = new javax.swing.JButton();
        totl = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Customer Bills Bulk Upload (Opening Balance)");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 470, 30);

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
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable1FocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 42, 1000, 490);

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

        totl.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(totl);
        totl.setBounds(0, 530, 1000, 30);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Info: Date format  'DD.MM.YYYY'   ");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(560, 10, 440, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained

    }//GEN-LAST:event_jTable1FocusGained

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        clear();

    }//GEN-LAST:event_clearbuttonActionPerformed

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

    private void excelbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excelbuttonActionPerformed

        try {
            menupack.menu_form mp = new menu_form();
            String folder = mp.getFoler();

            String file_name = "Customer_Bills_Upload.xlsx";
            File file = new File(folder + "/Drafts/" + file_name);

            // Create a dummy table for the format if jTable1 is empty
            if (jTable1.getRowCount() == 0) {
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Cust_Id");
                model.addColumn("Cust_Name");
                model.addColumn("Bill No");
                model.addColumn("Bill Date");
                model.addColumn("Due Date");
                model.addColumn("Amount");
                model.addColumn("Remarks");
                JTable dummyTable = new JTable(model);
                ExcelUtil.exportTableToExcel(dummyTable, file);
            } else {
                ExcelUtil.exportTableToExcel(jTable1, file);
            }

            Runtime rt = Runtime.getRuntime();
            Utils.AppConfig.openFile(file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }//GEN-LAST:event_excelbuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        save();
    }//GEN-LAST:event_savebuttonActionPerformed

    private void browsebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browsebuttonActionPerformed
        load_excel();
    }//GEN-LAST:event_browsebuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browsebutton;
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton excelbutton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton loadbutton;
    private javax.swing.JButton removebutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JLabel totl;
    // End of variables declaration//GEN-END:variables
}
