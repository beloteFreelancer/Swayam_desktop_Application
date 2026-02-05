package tax_reports;

import Utils.ColorConstants;
import Utils.ExcelUtilGST;
import com.selrom.db.DataUtil;
import java.awt.Font;
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
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public final class tax_hsn_sales extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    ResultSet r;
    int hmany = 2;

    void button_short() {
        excelbutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Excel</b><br>(Alt+I)</h6><html>");
        generatebutton.setText("<html><b>Generate</b>  (Alt+G)</h6><html>");
        titlelablel.setText("<html><u>GSTR - HSN Wise Sales</u></html>");

        setTitle("GSTR - HSN Wise Sales");
        this.setSize(1021, 648);
        javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            setFrameIcon(icon);
        }
    }

    public void load_list_table() {
        s2.addColumn("HSN");
        s2.addColumn("Description");
        s2.addColumn("UQC");
        s2.addColumn("Total Quantity");
        s2.addColumn("Total Value");
        s2.addColumn("Taxable Value");
        s2.addColumn("Integrated Tax Amount");
        s2.addColumn("Central Tax Amount");
        s2.addColumn("State/UT Tax Amount");
        s2.addColumn("Cess Amount");
        jTable1.setModel(s2);
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
        dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
        jTable1.getColumnModel().getColumn(3).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(4).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(5).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(6).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(7).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(8).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(9).setCellRenderer(dtcr1);

        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(280);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(130);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    void load_report(String dfrom, String dto) {
        try {
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(dfrom);
            String start = new SimpleDateFormat("yyyy/MM/dd").format(startDate);
            Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(dto);
            String end = new SimpleDateFormat("yyyy/MM/dd").format(endDate);

            boolean selva = false;
            String query;
            ResultSet r;

            // Query with proper aggregation (fixes SQL error)
            query = "SELECT hsn, iname, MAX(udes), SUM(quan), SUM(total), SUM(sub), MAX(tax_type), SUM(taxamt) "
                    + "FROM sales_items "
                    + "WHERE dat BETWEEN '" + start + "' AND '" + end + "' AND entry='purchase' "
                    + "GROUP BY hsn, iname "
                    + "ORDER BY hsn, iname";

            r = util.doQuery(query);
            if (r != null) {
                while (r.next()) {
                    String hsn = r.getString(1);
                    String iname = r.getString(2);
                    String udes = r.getString(3);
                    double quan = r.getDouble(4);
                    double total = r.getDouble(5);
                    double value = r.getDouble(6);
                    String ttype = r.getString(7);
                    double tax1 = r.getDouble(8);

                    if ("Inclusive Model-II".equalsIgnoreCase(ttype)) {
                        value -= tax1;
                    }

                    String totalStr = String.format("%." + hmany + "f", total);
                    String valueStr = String.format("%." + hmany + "f", value);
                    int quantity = (int) quan;

                    s2.addRow(new Object[] { hsn, iname, udes, quantity, totalStr, valueStr, "", "", "", "" });
                }
            } else {
                System.out.println("Query returned null ResultSet (main query).");
            }

            // Calculate tax columns
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String hsn1 = jTable1.getValueAt(i, 0).toString();
                String iname1 = jTable1.getValueAt(i, 1).toString();
                double cgst = 0, igst = 0;

                query = "SELECT SUM(taxamt), tax "
                        + "FROM sales_items "
                        + "WHERE dat BETWEEN '" + start + "' AND '" + end + "' "
                        + "AND hsn='" + hsn1 + "' AND iname='" + iname1 + "' AND entry='purchase' "
                        + "GROUP BY tax";

                r = util.doQuery(query);
                if (r != null) {
                    while (r.next()) {
                        double taxamt = r.getDouble(1);
                        String tax = r.getString(2);
                        if ("Local".equalsIgnoreCase(tax)) {
                            cgst += taxamt / 2;
                        } else {
                            igst += taxamt;
                        }
                    }
                }

                String cgstStr = String.format("%." + hmany + "f", cgst);
                String igstStr = String.format("%." + hmany + "f", igst);

                jTable1.setValueAt(igstStr, i, 6);
                jTable1.setValueAt(cgstStr, i, 7);
                jTable1.setValueAt(cgstStr, i, 8);
            }

            // Calculate totals
            double ntotal = 0, nvalue = 0, nigst = 0, ncgst = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                ntotal += Double.parseDouble(jTable1.getValueAt(i, 4).toString());
                nvalue += Double.parseDouble(jTable1.getValueAt(i, 5).toString());
                nigst += Double.parseDouble(jTable1.getValueAt(i, 6).toString());
                ncgst += Double.parseDouble(jTable1.getValueAt(i, 7).toString());
            }

            // Format totals and add to table
            String ntotalStr = String.format("%." + hmany + "f", ntotal);
            String nvalueStr = String.format("%." + hmany + "f", nvalue);
            String nigstStr = String.format("%." + hmany + "f", nigst);
            String ncgstStr = String.format("%." + hmany + "f", ncgst);

            s2.addRow(new Object[] { "", "", "", "", "", "", "", "", "", "" });
            s2.addRow(new Object[] { "", "Total", "", "", ntotalStr, nvalueStr, nigstStr, ncgstStr, ncgstStr, "" });

            // Optional UI disable logic
            if (selva) {
                h1.setEnabled(false);
                h2.setEnabled(false);
                jCalendarButton1.setEnabled(false);
                jCalendarButton2.setEnabled(false);
                generatebutton.setEnabled(false);
            }

        } catch (ClassNotFoundException | NumberFormatException | SQLException | ParseException e) {
            e.printStackTrace(); // or log properly
            System.out.println("Error in load_report: " + e.getMessage());
        }
    }

    public tax_hsn_sales(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();
        load_list_table();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        generatebutton = new javax.swing.JButton();
        excelbutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jCalendarButton2 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jCalendarButton1 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        h2 = new javax.swing.JTextField();
        h1 = new javax.swing.JTextField();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("GSTR - HSN Wise Sales");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 400, 30);

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
        jTable1.setRowHeight(24);
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
        jScrollPane1.setBounds(0, 82, 1000, 480);

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
        generatebutton.setBounds(370, 50, 210, 30);

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
        excelbutton.setBounds(620, 560, 130, 50);

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
        clearbutton.setBounds(750, 560, 130, 50);

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
        closebutton.setBounds(880, 560, 120, 50);

        jCalendarButton2.setIcon(ColorConstants.loadIcon("/icons/cal40.png")); // NOI18N
        jCalendarButton2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton2PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton2);
        jCalendarButton2.setBounds(170, 50, 40, 30);

        jCalendarButton1.setIcon(ColorConstants.loadIcon("/icons/cal40.png")); // NOI18N
        jCalendarButton1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton1PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton1);
        jCalendarButton1.setBounds(330, 50, 40, 30);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText(" To");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(210, 50, 30, 30);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Date from");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 50, 70, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(240, 50, 90, 30);

        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h1);
        h1.setBounds(80, 50, 90, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable1MouseClicked

    }// GEN-LAST:event_jTable1MouseClicked

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
        } else {
            new ExcelUtilGST(h1.getText(), h2.getText()).GenerateHsnSalesWorkbook();
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

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }// GEN-LAST:event_closebuttonActionPerformed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTable1FocusGained

    }// GEN-LAST:event_jTable1FocusGained

    private void jCalendarButton2PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jCalendarButton2PropertyChange
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

    }// GEN-LAST:event_jCalendarButton2PropertyChange

    private void jCalendarButton1PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jCalendarButton1PropertyChange
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

        // TODO add your handling code here:
    }// GEN-LAST:event_jCalendarButton1PropertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton excelbutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h2;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton1;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel titlelablel;
    // End of variables declaration//GEN-END:variables
}
