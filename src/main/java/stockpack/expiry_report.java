package stockpack;

import com.selrom.db.DataUtil;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import menupack.menu_form;
import menupack.sample2;

public class expiry_report extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    ResultSet r;
    int hmany = 2;

    public expiry_report(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();
        load_list_table();
    }

    final void button_short() {
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Excel</b><br>(Alt+I)</h6><html>");
        generatebutton.setText("<html><b>Generate</b>  (Alt+G)</h6><html>");
        titlelablel.setText("<html><u>Expiry Report</u></html>");
        menu_form me = new menu_form();
        hmany = me.getHmany();
        setTitle("Expiry Report");
        this.setSize(1021, 648);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
    }

    public final void load_list_table() {
        s2.addColumn("It.Code");
        s2.addColumn("It.Name");
        s2.addColumn("MRP");
        s2.addColumn("Pur. Price");
        s2.addColumn("Sel. Price");
        s2.addColumn("Mfg. Date");
        s2.addColumn("Exp. Date");
        s2.addColumn("Cur. Stock");
        jTable1.setModel(s2);

        DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
        dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);

        jTable1.getColumnModel().getColumn(2).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(3).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(4).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(7).setCellRenderer(dtcr1);

        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(294);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(100);

        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    void load_report() {
        try {
            if (s2.getRowCount() > 0) {
                s2.getDataVector().removeAllElements();
                s2.fireTableDataChanged();
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            String date1 = format.format(jDateChooser1.getDate());
            String date2 = format.format(jDateChooser2.getDate());

            String query = "select a.ino, a.iname, a.mrp, b.prate, a.rprice, a.mfg_date, a.exp_date, b.quan from item a, stock b where a.ino=b.ino and a.exp_date between ? and ? order by a.exp_date asc";

            java.sql.PreparedStatement pstmt = com.selrom.db.Database.getInstance().getConnection().prepareStatement(query);
            pstmt.setString(1, date1);
            pstmt.setString(2, date2);

            r = pstmt.executeQuery();

            SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat targetDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            while(r.next())
            {
                String mfgDateStr = r.getString(6);
                String expDateStr = r.getString(7);

                String formattedMfgDate = "";
                if (mfgDateStr != null && !mfgDateStr.isEmpty()) {
                    formattedMfgDate = targetDateFormat.format(dbDateFormat.parse(mfgDateStr));
                }

                String formattedExpDate = "";
                if (expDateStr != null && !expDateStr.isEmpty()) {
                    formattedExpDate = targetDateFormat.format(dbDateFormat.parse(expDateStr));
                }

                s2.addRow(new Object[]{r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),formattedMfgDate,formattedExpDate,r.getString(8)});
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        generatebutton = new javax.swing.JButton();
        excelbutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cnamel = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Stock Report");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 400, 30);

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
        jScrollPane1.setBounds(0, 82, 1000, 480);

        generatebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/generate30.png"))); // NOI18N
        generatebutton.setMnemonic('g');
        generatebutton.setText("Generate");
        generatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton);
        generatebutton.setBounds(510, 50, 180, 30);

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
        excelbutton.setBounds(620, 560, 130, 50);

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
        clearbutton.setBounds(750, 560, 130, 50);

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
        closebutton.setBounds(880, 560, 120, 50);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("From");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(10, 50, 40, 30);

        cnamel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cnamel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(cnamel);
        cnamel.setBounds(430, 0, 560, 30);

        jDateChooser1.setDateFormatString("dd/MM/yyyy");
        jDateChooser1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(jDateChooser1);
        jDateChooser1.setBounds(50, 50, 190, 30);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("To");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(260, 50, 30, 30);

        jDateChooser2.setDateFormatString("dd/MM/yyyy");
        jDateChooser2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(jDateChooser2);
        jDateChooser2.setBounds(290, 50, 200, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

    }//GEN-LAST:event_jTable1MouseClicked

    private void generatebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatebuttonActionPerformed
        if (jDateChooser1.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select a from date.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (jDateChooser2.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select a to date.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        load_report();
    }//GEN-LAST:event_generatebuttonActionPerformed

    private void excelbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excelbuttonActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Sorry, No Records Were Found!", "Oops", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            menu_form mp = new menu_form();
            String drive = mp.getDrive();
            String folder = mp.getFoler();
            String file_name = "List";

            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet("Stock Report");

            // Create a date cell style
            CellStyle dateCellStyle = wb.createCellStyle();
            CreationHelper createHelper = wb.getCreationHelper();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < jTable1.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(jTable1.getColumnName(i));
            }

            // Create data rows
            SimpleDateFormat tableDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < jTable1.getColumnCount(); j++) {
                    Cell cell = row.createCell(j);
                    Object obj = jTable1.getValueAt(i, j);
                    if (obj == null) {
                        cell.setCellValue("");
                    } else {
                        String val = obj.toString();
                        if (j == 5 || j == 6) { // Mfg. Date and Exp. Date columns
                            try {
                                Date date = tableDateFormat.parse(val);
                                cell.setCellValue(date);
                                cell.setCellStyle(dateCellStyle);
                            } catch (ParseException e) {
                                cell.setCellValue(val); // Fallback to string if parsing fails
                            }
                        } else {
                            cell.setCellValue(val);
                        }
                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < jTable1.getColumnCount(); i++) {
                sheet.autoSizeColumn(i);
            }

            String filePath = folder + "/Drafts/" + file_name + ".xls";
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                wb.write(fileOut);
            }
            wb.close();

            Runtime rt = Runtime.getRuntime();
            Utils.AppConfig.openFile(filePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }//GEN-LAST:event_excelbuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);
        generatebutton.setEnabled(true);
    }//GEN-LAST:event_clearbuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained

    }//GEN-LAST:event_jTable1FocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JLabel cnamel;
    private javax.swing.JButton excelbutton;
    private javax.swing.JButton generatebutton;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel titlelablel;
    // End of variables declaration//GEN-END:variables
}
