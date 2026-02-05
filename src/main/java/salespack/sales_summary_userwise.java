package salespack;

import Utils.ColorConstants;
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
import java.util.ArrayList;
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
 *         mysoft.java@gmail.com
 */
public class sales_summary_userwise extends javax.swing.JInternalFrame {

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
        titlelablel.setText("<html><u>Cashier Wise Sales Summary</u></html>");
        setTitle("Cashier Wise Sales Summary");
        this.setSize(1017, 650);
        javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            setFrameIcon(icon);
        }
        menu_form me = new menu_form();
        hmany = me.getHmany();
    }

    public final void load_list_table() {
        s2.addColumn("Cashier");
        s2.addColumn("Total Bills");
        s2.addColumn("Cash");
        s2.addColumn("Card");
        s2.addColumn("Credit");
        s2.addColumn("UPI");
        s2.addColumn("Others");
        s2.addColumn("Total");
        jTable1.setModel(s2);
        DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
        dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
        jTable1.getColumnModel().getColumn(1).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(2).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(3).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(4).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(5).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(6).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(7).setCellRenderer(dtcr1);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(234);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(130);
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

            ArrayList cashier1 = new ArrayList();
            String query = "select distinct cashier from sales where dat between '" + lk + "' and '" + lk1 + "'";
            r = util.doQuery(query);
            while (r.next()) {
                cashier1.add(r.getString(1));
            }

            double ncash = 0, ncard = 0, ncredit = 0, nupi = 0, nnet = 0, nothers = 0;
            for (int i = 0; i < cashier1.size(); i++) {
                int billno = 0, rbillno = 0;
                double cash = 0, card = 0, credit = 0, upi = 0, others = 0, net = 0, bal = 0;
                double rcash = 0, rcard = 0, rcredit = 0, rupi = 0, rothers = 0, rnet = 0, rbal = 0;
                query = "select count(billno),sum(cash),sum(card),sum(credit),sum(upi),sum(others),sum(net),sum(bal) from sales where dat between '"
                        + lk + "' and '" + lk1 + "' and cashier='" + cashier1.get(i) + "' ";
                r = util.doQuery(query);
                while (r.next()) {
                    billno = r.getInt(1);
                    cash = r.getDouble(2);
                    card = r.getDouble(3);
                    credit = r.getDouble(4);
                    upi = r.getDouble(5);
                    others = r.getDouble(6);
                    net = r.getDouble(7);
                    bal = r.getDouble(8);
                }

                query = "select count(billno),sum(cash),sum(card),sum(credit),sum(upi),sum(others),sum(net),sum(bal) from sreturn where dat between '"
                        + lk + "' and '" + lk1 + "' and cashier='" + cashier1.get(i) + "' ";
                r = util.doQuery(query);
                while (r.next()) {
                    rbillno = r.getInt(1);
                    rcash = r.getDouble(2);
                    rcard = r.getDouble(3);
                    rcredit = r.getDouble(4);
                    rupi = r.getDouble(5);
                    rothers = r.getDouble(6);
                    rnet = r.getDouble(7);
                    rbal = r.getDouble(8);
                }
                int tbills = billno - rbillno;
                double tcash = cash - rcash;
                double tcard = card - rcard;
                double tcredit = credit - rcredit;
                double tupi = upi - rupi;
                double tothers = others - rothers;
                double tnet = net - rnet;

                ncash = ncash + tcash;
                ncard = ncard + tcard;
                ncredit = ncredit + tcredit;
                nupi = nupi + tupi;
                nothers = nothers + tothers;
                nnet = nnet + tnet;

                String cash2 = String.format("%." + hmany + "f", cash);
                String card2 = String.format("%." + hmany + "f", card);
                String credit2 = String.format("%." + hmany + "f", credit);
                String upi2 = String.format("%." + hmany + "f", upi);
                String others2 = String.format("%." + hmany + "f", others);
                String net2 = String.format("%." + hmany + "f", net);

                s2.addRow(new Object[] { cashier1.get(i) + " - Sales", billno, cash2, card2, credit2, upi2, others2,
                        net2 });
                if (rnet > 0) {
                    String rcash2 = String.format("%." + hmany + "f", rcash);
                    String rcard2 = String.format("%." + hmany + "f", rcard);
                    String rcredit2 = String.format("%." + hmany + "f", rcredit);
                    String rupi2 = String.format("%." + hmany + "f", rupi);
                    String rothers2 = String.format("%." + hmany + "f", rothers);
                    String rnet2 = String.format("%." + hmany + "f", rnet);

                    String tcash2 = String.format("%." + hmany + "f", tcash);
                    String tcard2 = String.format("%." + hmany + "f", tcard);
                    String tcredit2 = String.format("%." + hmany + "f", tcredit);
                    String tupi2 = String.format("%." + hmany + "f", tupi);
                    String tothers2 = String.format("%." + hmany + "f", tothers);
                    String tnet2 = String.format("%." + hmany + "f", tnet);

                    s2.addRow(
                            new Object[] { "Sales Return", rbillno, rcash2, rcard2, rcredit2, rupi2, rothers2, rnet2 });
                    s2.addRow(new Object[] { "TOTAL for " + cashier1.get(i), tbills, tcash2, tcard2, tcredit2, tupi2,
                            tothers2, tnet2 });
                }
                cash = 0;
                card = 0;
                credit = 0;
                upi = 0;
                others = 0;
                net = 0;
                bal = 0;
                rcash = 0;
                rcard = 0;
                rcredit = 0;
                rupi = 0;
                rothers = 0;
                rnet = 0;
                rbal = 0;
                s2.addRow(new Object[] { "", "", "", "", "", "", "", "" });
            } // cashier array list ends

            String cash2 = String.format("%." + hmany + "f", ncash);
            String card2 = String.format("%." + hmany + "f", ncard);
            String credit2 = String.format("%." + hmany + "f", ncredit);
            String upi2 = String.format("%." + hmany + "f", nupi);
            String others2 = String.format("%." + hmany + "f", nothers);
            String net2 = String.format("%." + hmany + "f", nnet);
            s2.addRow(new Object[] { "", "", "", "", "", "", "", "" });
            s2.addRow(new Object[] { "TOTAL:" + (jTable1.getRowCount() - 1), "", cash2, card2, credit2, upi2, others2,
                    net2 });

            h1.setEnabled(false);
            h2.setEnabled(false);
            jCalendarButton1.setEnabled(false);
            jCalendarButton2.setEnabled(false);
            generatebutton.setEnabled(false);
        } catch (ClassNotFoundException | NumberFormatException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public sales_summary_userwise(DataUtil util) {
        initComponents();

        this.util = util;
        load_list_table();
        button_short();

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
        jLabel12 = new javax.swing.JLabel();
        h2 = new javax.swing.JTextField();
        jCalendarButton1 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jCalendarButton2 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Cashier Wise Sales Summary");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 310, 30);

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
        generatebutton.setBounds(400, 40, 220, 30);

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
        h1.setBounds(80, 40, 110, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Date from");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(10, 40, 70, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(250, 40, 110, 30);

        jCalendarButton1.setIcon(ColorConstants.loadIcon("/icons/cal40.png")); // NOI18N
        jCalendarButton1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton1PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton1);
        jCalendarButton1.setBounds(190, 40, 30, 30);

        jCalendarButton2.setIcon(ColorConstants.loadIcon("/icons/cal40.png")); // NOI18N
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
            String drive = mp.getDrive();
            String folder = mp.getFoler();
            String file_name = "List.xlsx";
            File file = new File(folder + "/Drafts/" + file_name);

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Cashier Sales Summary");

                // Title and Date Range
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("Cashier Wise Sales Summary");

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
        h1.setText("");
        h2.setText("");
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton excelbutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h2;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton1;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton2;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel titlelablel;
    // End of variables declaration//GEN-END:variables
}
