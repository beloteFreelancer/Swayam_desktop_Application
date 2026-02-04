package salespack;

import com.selrom.db.DataUtil;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public class sales_report_price_type extends javax.swing.JInternalFrame {

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
        titlelablel.setText("<html><u>Price Type Wise Sales Report</u></html>");
        setTitle("Price Type Wise Sales Report");
        this.setSize(1017, 650);
        java.net.URL imgUrl = getClass().getResource("/images/icon.png");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            this.setFrameIcon(icon);
        }
        menu_form me = new menu_form();
        hmany = me.getHmany();
    }

    public final void load_list_table() {
        s2.addColumn("Bill No");
        s2.addColumn("Date");
        s2.addColumn("Time");
        s2.addColumn("Location");
        s2.addColumn("Terminal");
        s2.addColumn("Cashier");
        s2.addColumn("Total Items");
        s2.addColumn("Total Qty");
        s2.addColumn("Sub Total");
        s2.addColumn("Dis%");
        s2.addColumn("Dis Amt");
        s2.addColumn("Gross Amt");
        s2.addColumn("Tax Amt");
        s2.addColumn("Other Charges");
        s2.addColumn("Net Amt");
        s2.addColumn("Pay Mode");
        s2.addColumn("Tender");
        s2.addColumn("Return");
        s2.addColumn("Price Type");
        s2.addColumn("Cust_Id");
        s2.addColumn("Cust_Name");
        s2.addColumn("Mobile No");
        s2.addColumn("Last Modified at");
        jTable1.setModel(s2);
        DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
        dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
        jTable1.getColumnModel().getColumn(6).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(7).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(8).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(9).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(10).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(11).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(12).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(13).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(14).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(15).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(16).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(17).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(18).setCellRenderer(dtcr1);

        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(10).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(11).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(12).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(13).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(14).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(15).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(16).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(17).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(18).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(19).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(20).setPreferredWidth(250);
        jTable1.getColumnModel().getColumn(21).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(22).setPreferredWidth(180);

        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    void load_report(String dfrom, String dto) {
        try {
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(dfrom);
            String lk = (new SimpleDateFormat("yyyy/MM/dd").format(nm));
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(dto);
            String lk1 = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));
            boolean selva = false;
            double sub, disamt, net, gross, taxamt, other, paid, bal;

            String query;
            if (all.isSelected()) {
                query = "select billno,date_format(dat,'%d/%m/%Y'),tim,location,terminal,cashier,items,quans,sub,disp,disamt,gross,taxamt,addamt,net,pby,paid,bal,price_type,cid,cname,mobile,last from sales where dat between '" + lk + "' and '" + lk1 + "' order by price_type,dat,billno";
            } else {
                query = "select billno,date_format(dat,'%d/%m/%Y'),tim,location,terminal,cashier,items,quans,sub,disp,disamt,gross,taxamt,addamt,net,pby,paid,bal,price_type,cid,cname,mobile,last from sales where dat between '" + lk + "' and '" + lk1 + "' and price_type='" + h3.getSelectedItem().toString() + "'  order by dat,billno";
            }
            r = util.doQuery(query);
            while (r.next()) {
                sub = r.getDouble(9);
                disamt = r.getDouble(11);
                gross = r.getDouble(12);
                taxamt = r.getDouble(13);
                other = r.getDouble(14);
                net = r.getDouble(15);
                paid = r.getDouble(17);
                bal = r.getDouble(18);

                String sub1 = String.format("%." + hmany + "f", sub);
                String disamt1 = String.format("%." + hmany + "f", disamt);
                String gross1 = String.format("%." + hmany + "f", gross);
                String taxamt1 = String.format("%." + hmany + "f", taxamt);
                String other1 = String.format("%." + hmany + "f", other);
                String net1 = String.format("%." + hmany + "f", net);
                String paid1 = String.format("%." + hmany + "f", paid);
                String bal1 = String.format("%." + hmany + "f", bal);

                s2.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6), r.getString(7), r.getString(8), sub1, r.getString(10), disamt1, gross1, taxamt1, other1, net1, r.getString(16), paid1, bal1, r.getString(19), "  " + r.getString(20), r.getString(21), r.getString(22), r.getString(23)});
                selva = true;
            }
            sub = 0;
            disamt = 0;
            net = 0;
            gross = 0;
            taxamt = 0;
            other = 0;
            paid = 0;
            bal = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                sub = sub + Double.parseDouble(jTable1.getValueAt(i, 8).toString());
                disamt = disamt + Double.parseDouble(jTable1.getValueAt(i, 10).toString());
                gross = gross + Double.parseDouble(jTable1.getValueAt(i, 11).toString());
                taxamt = taxamt + Double.parseDouble(jTable1.getValueAt(i, 12).toString());
                other = other + Double.parseDouble(jTable1.getValueAt(i, 13).toString());
                net = net + Double.parseDouble(jTable1.getValueAt(i, 14).toString());

                paid = paid + Double.parseDouble(jTable1.getValueAt(i, 16).toString());
                bal = bal + Double.parseDouble(jTable1.getValueAt(i, 17).toString());
            }
            String sub1 = String.format("%." + hmany + "f", sub);
            String disamt1 = String.format("%." + hmany + "f", disamt);
            String gross1 = String.format("%." + hmany + "f", gross);
            String taxamt1 = String.format("%." + hmany + "f", taxamt);
            String other1 = String.format("%." + hmany + "f", other);
            String net1 = String.format("%." + hmany + "f", net);
            String paid1 = String.format("%." + hmany + "f", paid);
            String bal1 = String.format("%." + hmany + "f", bal);

            if (selva == true) {
                s2.addRow(new Object[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""});
                s2.addRow(new Object[]{"", "", "TOTAL:" + (jTable1.getRowCount() - 1), "", "", "", "", "", "" + sub1, "", disamt1, gross1, taxamt1, other1, net1, "", paid1, bal1, "", "", "", "", ""});

                h1.setEnabled(false);
                h2.setEnabled(false);
                jCalendarButton1.setEnabled(false);
                jCalendarButton2.setEnabled(false);
                generatebutton.setEnabled(false);
                h3.setEnabled(false);
                all.setEnabled(false);
            }
        } catch (ClassNotFoundException | NumberFormatException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public sales_report_price_type(DataUtil util) {
        initComponents();

        this.util = util;
        load_list_table();
        button_short();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        generatebutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        excelbutton = new javax.swing.JButton();
        h1 = new javax.swing.JTextField();
        h3 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        h2 = new javax.swing.JTextField();
        jCalendarButton1 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jCalendarButton2 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel13 = new javax.swing.JLabel();
        all = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Price Type Wise Sales Report");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 370, 30);

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
        generatebutton.setBounds(780, 40, 220, 30);

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
        clearbutton.setBounds(740, 560, 130, 50);

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
        closebutton.setBounds(870, 560, 130, 50);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Price Type");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(400, 40, 70, 30);

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
        excelbutton.setBounds(610, 560, 130, 50);

        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h1);
        h1.setBounds(80, 40, 110, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Retail", "Wholesale" }));
        getContentPane().add(h3);
        h3.setBounds(470, 40, 220, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Date from");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(10, 40, 70, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(250, 40, 110, 30);

        jCalendarButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
        jCalendarButton1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton1PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton1);
        jCalendarButton1.setBounds(190, 40, 30, 30);

        jCalendarButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
        jCalendarButton2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton2PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton2);
        jCalendarButton2.setBounds(360, 40, 30, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("To");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(230, 40, 20, 30);

        all.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        all.setText("Select All");
        all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allActionPerformed(evt);
            }
        });
        getContentPane().add(all);
        all.setBounds(690, 40, 90, 30);

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
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 70, 1000, 490);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_closebuttonActionPerformed

    private void generatebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatebuttonActionPerformed
        Date d = new Date();
        SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");

        if (h1.getText().equals("")) {
            h1.setText(g.format(d));
        }
        if (h2.getText().equals("")) {
            h2.setText(g.format(d));
        }
        load_report(h1.getText(), h2.getText());

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
            String file_name = "List.xlsx";
            File file = new File(folder + "/Drafts/" + file_name);

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Price Type Sales Report");

                // Title and Date Range
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("Price Type Wise Sales Report");

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
                // Create date cell style
                CellStyle dateCellStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 4);
                    for (int j = 0; j < jTable1.getColumnCount(); j++) {
                        Cell cell = row.createCell(j);
                        Object value = jTable1.getValueAt(i, j);

                        if (value != null) {
                            if (j == 1) { // Date column
                                try {
                                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(value.toString());
                                    cell.setCellValue(date);
                                    cell.setCellStyle(dateCellStyle);
                                } catch (ParseException e) {
                                    cell.setCellValue(value.toString());
                                }
                            } else if (value instanceof Number) {
                                cell.setCellValue(((Number) value).doubleValue());
                            } else {
                                String val = value.toString();
                                try {
                                    // Try to parse double if it looks like a number and not the bill no or phone
                                    if (j != 0 && j != 21) {
                                         double d = Double.parseDouble(val);
                                         cell.setCellValue(d);
                                    } else {
                                         cell.setCellValue(val);
                                    }
                                } catch (NumberFormatException e) {
                                    cell.setCellValue(val);
                                }
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
            JOptionPane.showMessageDialog(this, "Error exporting to Excel: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_excelbuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        h1.setEnabled(true);
        h2.setEnabled(true);
        jCalendarButton1.setEnabled(true);
        jCalendarButton2.setEnabled(true);
        h1.setText("");
        h2.setText("");
        h3.setEnabled(true);
        all.setEnabled(true);
        h3.setSelectedIndex(0);
        all.setSelected(false);
        generatebutton.setEnabled(true);

    }//GEN-LAST:event_clearbuttonActionPerformed

    private void jCalendarButton1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendarButton1PropertyChange
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
    }//GEN-LAST:event_jCalendarButton1PropertyChange

    private void jCalendarButton2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendarButton2PropertyChange
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
    }//GEN-LAST:event_jCalendarButton2PropertyChange

    private void allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allActionPerformed

        if (all.isSelected()) {
            h3.setEnabled(false);
        } else {
            h3.setEnabled(true);
            h3.setSelectedIndex(0);
        }
    }//GEN-LAST:event_allActionPerformed

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
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel titlelablel;
    // End of variables declaration//GEN-END:variables
}
