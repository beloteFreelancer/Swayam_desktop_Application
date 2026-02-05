package salespack;

import Utils.ColorConstants;
import com.selrom.db.DataUtil;
import com.selrom.utils.ExcelUtil;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import menupack.menu_form;
import menupack.sample2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public final class sales_summary_item extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    ResultSet r;
    int hmany = 2;

    final void button_short() {
        excelbutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Excel</b><br>(Alt+I)</h6><html>");
        generatebutton.setText("<html><b>Generate</b>  (Alt+G)</h6><html>");
        titlelablel.setText("<html><u>Item Wise Sales Summary</u></html>");
        setTitle("Item Wise Sales Summary");
        this.setSize(1017, 650);
        javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            setFrameIcon(icon);
        }
        menu_form me = new menu_form();
        hmany = me.getHmany();
    }

    public final void load_list_table() {
        s2.addColumn("It.Code");
        s2.addColumn("It.Name");
        s2.addColumn("Qty");
        s2.addColumn("Amount");
        jTable1.setModel(s2);
        DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
        dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
        jTable1.getColumnModel().getColumn(2).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(3).setCellRenderer(dtcr1);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(464);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(200);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    void load_report(String dfrom, String dto) {
        try {
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(dfrom);
            String lk = (new SimpleDateFormat("yyyy-MM-dd").format(nm));
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(dto);
            String lk1 = (new SimpleDateFormat("yyyy-MM-dd").format(nm1));
            boolean selva = false;
            double sub;
            String query;

            Connection conn = util.getConnection();
            PreparedStatement ps;

            if (all.isSelected()) {
                query = "select ino,iname,sum(quan),sum(amount) from sales_items where dat between ? and ? group by ino, iname order by ino";
                ps = conn.prepareStatement(query);
                ps.setString(1, lk);
                ps.setString(2, lk1);
            } else {
                query = "select distinct a.ino,a.iname,sum(quan),sum(amount) from sales_items a,item b where dat between ? and ? and cat=? and a.ino=b.ino group by a.ino, a.iname order by a.ino";
                ps = conn.prepareStatement(query);
                ps.setString(1, lk);
                ps.setString(2, lk1);
                Object selectedItem = h3.getSelectedItem();
                ps.setString(3, selectedItem != null ? selectedItem.toString() : "");
            }

            r = ps.executeQuery();
            while (r.next()) {
                sub = r.getDouble(4);
                String sub2 = String.format("%." + hmany + "f", sub);
                s2.addRow(new Object[] { r.getString(1), r.getString(2), r.getString(3), sub2 });
                selva = true;
            }
            sub = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                sub = sub + Double.parseDouble(jTable1.getValueAt(i, 3).toString());
            }
            String sub2 = String.format("%." + hmany + "f", sub);
            if (selva == true) {
                s2.addRow(new Object[] { "", "", "", "" });
                s2.addRow(new Object[] { "", "TOTAL:" + (jTable1.getRowCount() - 1), "", sub2 });

                h1.setEnabled(false);
                h2.setEnabled(false);
                jCalendarButton1.setEnabled(false);
                jCalendarButton2.setEnabled(false);
                generatebutton.setEnabled(false);
                h3.setEnabled(false);
                all.setEnabled(false);

            }
        } catch (NumberFormatException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_category() {
        try {
            h3.removeAllItems();
            String query = "select distinct cat from item";
            r = util.doQuery(query);
            while (r.next()) {
                h3.addItem(r.getString(1));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public sales_summary_item(DataUtil util) {
        initComponents();

        this.util = util;
        load_list_table();
        button_short();
        get_category();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        generatebutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        excelbutton = new javax.swing.JButton();
        h1 = new javax.swing.JTextField();
        h2 = new javax.swing.JTextField();
        jCalendarButton1 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jCalendarButton2 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        h3 = new javax.swing.JComboBox<>();
        all = new javax.swing.JCheckBox();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Item Wise Sales Summary");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 290, 30);

        generatebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton.setIcon(ColorConstants.loadIcon("/icons/generate30.png")); // NOI18N
        generatebutton.setMnemonic('g');
        generatebutton.setText("Generate");
        generatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton);
        generatebutton.setBounds(810, 40, 190, 30);

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
        clearbutton.setBounds(740, 560, 130, 50);

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
        closebutton.setBounds(870, 560, 130, 50);

        excelbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        excelbutton.setIcon(ColorConstants.loadIcon("/icons/excel45.png")); // NOI18N
        excelbutton.setMnemonic('i');
        excelbutton.setText("Excel");
        excelbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excelbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(excelbutton);
        excelbutton.setBounds(610, 560, 130, 50);

        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h1);
        h1.setBounds(80, 40, 90, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(230, 40, 90, 30);

        jCalendarButton1.setIcon(ColorConstants.loadIcon("/icons/cal40.png")); // NOI18N
        jCalendarButton1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton1PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton1);
        jCalendarButton1.setBounds(170, 40, 30, 30);

        jCalendarButton2.setIcon(ColorConstants.loadIcon("/icons/cal40.png")); // NOI18N
        jCalendarButton2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton2PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton2);
        jCalendarButton2.setBounds(320, 40, 30, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("To");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(210, 40, 20, 30);

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
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 70, 1000, 490);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Date from");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(10, 40, 70, 30);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Category");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(360, 40, 70, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h3);
        h3.setBounds(420, 40, 300, 30);

        all.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        all.setText("Select All");
        all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allActionPerformed(evt);
            }
        });
        getContentPane().add(all);
        all.setBounds(720, 40, 90, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }// GEN-LAST:event_closebuttonActionPerformed

    private void generatebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_generatebuttonActionPerformed
        Date d = new Date();
        SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");

        if (h1.getText().equals("")) {
            h1.setText(g.format(d));
        }
        if (h2.getText().equals("")) {
            h2.setText(g.format(d));
        }
        load_report(h1.getText(), h2.getText());

    }// GEN-LAST:event_generatebuttonActionPerformed

    private void excelbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_excelbuttonActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Sorry, No Records Were Found!", "Oops", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            menupack.menu_form mp = new menu_form();
            String folder = mp.getFoler();
            String file_name = "ItemWiseSalesSummary.xlsx";
            File file = new File(folder + "/Drafts/" + file_name);

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Item Sales Summary");

                // Title and Date Range
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("Item Wise Sales Summary");

                Row dateRow = sheet.createRow(1);
                Cell dateCell = dateRow.createCell(0);
                dateCell.setCellValue("Date from: " + h1.getText() + "  To: " + h2.getText());

                // Header Row
                Row headerRow = sheet.createRow(3);
                for (int i = 0; i < jTable1.getColumnCount(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(jTable1.getColumnName(i));
                }

                // Data Rows
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 4);
                    for (int j = 0; j < jTable1.getColumnCount(); j++) {
                        Cell cell = row.createCell(j);
                        Object value = jTable1.getValueAt(i, j);
                        if (value != null) {
                            String val = value.toString();
                            try {
                                double d = Double.parseDouble(val);
                                cell.setCellValue(d);
                            } catch (NumberFormatException e) {
                                cell.setCellValue(val);
                            }
                        }
                    }
                }

                // Auto-size columns
                for (int i = 0; i < jTable1.getColumnCount(); i++) {
                    sheet.autoSizeColumn(i);
                }

                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                }
            }

            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this, "Error exporting to Excel: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }// GEN-LAST:event_excelbuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbuttonActionPerformed
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        h1.setEnabled(true);
        h2.setEnabled(true);
        jCalendarButton1.setEnabled(true);
        jCalendarButton2.setEnabled(true);
        generatebutton.setEnabled(true);
        h3.setEnabled(true);
        all.setEnabled(true);
        all.setSelected(false);
        h1.setText("");
        h2.setText("");
        h3.setSelectedIndex(0);
        generatebutton.setEnabled(true);
    }// GEN-LAST:event_clearbuttonActionPerformed

    private void jCalendarButton1PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jCalendarButton1PropertyChange
        try {
            if (evt.getNewValue() instanceof Date) {
                String ses = evt.getNewValue().toString();
                Date nm = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(ses);
                String date = (new SimpleDateFormat("dd/MM/yyyy").format(nm));
                h1.setText(date);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jCalendarButton1PropertyChange

    private void jCalendarButton2PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jCalendarButton2PropertyChange
        try {
            if (evt.getNewValue() instanceof Date) {
                String ses = evt.getNewValue().toString();
                Date nm = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(ses);
                String date = (new SimpleDateFormat("dd/MM/yyyy").format(nm));
                h2.setText(date);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jCalendarButton2PropertyChange

    private void allActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_allActionPerformed

        if (all.isSelected()) {
            h3.setEnabled(false);
        } else {
            h3.setEnabled(true);
            h3.setSelectedIndex(0);
        }
    }// GEN-LAST:event_allActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox all;
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton excelbutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h2;
    private javax.swing.JComboBox<String> h3;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton1;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton2;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel titlelablel;
    // End of variables declaration//GEN-END:variables
}
