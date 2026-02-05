package tax_reports;

import Utils.ColorConstants;
import Utils.ExcelUtilGST;
import com.selrom.db.DataUtil;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public class tax_purchase extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    ResultSet r;

    final void button_short() {
        excelbutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Excel</b><br>(Alt+I)</h6><html>");
        generatebutton.setText("<html><b>Generate</b>  (Alt+G)</h6><html>");
        titlelablel.setText("<html><u>GSTR -Purchase</u></html>");
    }

    final void load_list_table() {
        s2.addColumn("Serial No");
        s2.addColumn("Name of Seller");
        s2.addColumn("Seller GSTIN/UIN");
        s2.addColumn("Invoice No");
        s2.addColumn("Invoice Date");
        s2.addColumn("Rate of Tax");
        s2.addColumn("Taxable Value");
        s2.addColumn("IGST Amount");
        s2.addColumn("CGST Amount");
        s2.addColumn("SGST/UTGST Amount");
        s2.addColumn("Cess");
        s2.addColumn("Total Tax");
        s2.addColumn("Total Amount");
        s2.addColumn("GRN_No");
        jTable1.setModel(s2);
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
        dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
        jTable1.getColumnModel().getColumn(5).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(6).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(7).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(8).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(9).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(10).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(11).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(12).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(13).setCellRenderer(dtcr1);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(90);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(280);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(160);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(140);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(160);
        jTable1.getColumnModel().getColumn(10).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(11).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(12).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(13).setPreferredWidth(150);
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
            DecimalFormat df2 = new DecimalFormat("#.##");
            boolean selva = false;
            String query;

            ArrayList cname = new ArrayList();
            ArrayList billno = new ArrayList();
            ArrayList billdate = new ArrayList();
            ArrayList tin = new ArrayList();
            ArrayList grn = new ArrayList();
            ArrayList ttype = new ArrayList();
            query = "SELECT DISTINCT cname, billno, DATE_FORMAT(bdate,'%d/%m/%Y') AS bdate_str, bdate, grn, ttype "
                    + "FROM purchase_items "
                    + "WHERE bdate BETWEEN '" + lk + "' AND '" + lk1 + "' "
                    + "ORDER BY bdate, grn";
            r = util.doQuery(query);
            if (r != null) {
                while (r.next()) {
                    cname.add(r.getString(1));
                    billno.add(r.getString(2));
                    billdate.add(r.getString(3));
                    tin.add("");
                    grn.add(r.getString(4));
                    ttype.add(r.getString(5));
                    selva = true;
                }
            }
            ArrayList cname1 = new ArrayList();
            ArrayList tin1 = new ArrayList();
            ArrayList billno1 = new ArrayList();
            ArrayList date1 = new ArrayList();
            ArrayList taxp1 = new ArrayList();
            ArrayList value1 = new ArrayList();
            ArrayList taxamt1 = new ArrayList();
            ArrayList total1 = new ArrayList();
            ArrayList grn1 = new ArrayList();
            ArrayList ttype1 = new ArrayList();
            for (int i = 0; i < billno.size(); i++) {
                int taxp = 0;
                double value = 0, taxamt, total;
                query = "select sum(sub) from purchase_items where bdate between '" + lk + "' and '" + lk1
                        + "' and taxp='" + taxp + "' and billno='" + billno.get(i).toString() + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    value = r.getDouble(1);
                }
                if (value > 0) {
                    cname1.add(cname.get(i));
                    tin1.add(tin.get(i));
                    billno1.add(billno.get(i));
                    date1.add(billdate.get(i));
                    taxp1.add("" + 0);
                    value1.add("" + value);
                    taxamt1.add("" + 0);
                    total1.add("" + value);
                    grn1.add(grn.get(i));
                    ttype1.add(ttype.get(i));
                }

                // 5 Percentage Tax
                taxp = 5;
                value = 0;
                taxamt = 0;
                total = 0;
                query = "select sum(sub),sum(taxamt),sum(total) from purchase_items where bdate between '" + lk
                        + "' and '" + lk1 + "' and taxp='" + taxp + "' and billno='" + billno.get(i).toString() + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    value = r.getDouble(1);
                    taxamt = r.getDouble(2);
                    total = r.getDouble(3);
                }
                if (value > 0) {
                    cname1.add(cname.get(i));
                    tin1.add(tin.get(i));
                    billno1.add(billno.get(i));
                    date1.add(billdate.get(i));
                    taxp1.add("" + 5);
                    value1.add("" + value);
                    taxamt1.add("" + taxamt);
                    total1.add("" + total);
                    grn1.add(grn.get(i));
                    ttype1.add(ttype.get(i));
                }

                // 5 Percentage Ends
                // 12 Percentage Tax
                taxp = 12;
                value = 0;
                taxamt = 0;
                total = 0;
                query = "select sum(sub),sum(taxamt),sum(total) from purchase_items where bdate between '" + lk
                        + "' and '" + lk1 + "' and taxp='" + taxp + "' and billno='" + billno.get(i).toString() + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    value = r.getDouble(1);
                    taxamt = r.getDouble(2);
                    total = r.getDouble(3);
                }
                if (value > 0) {
                    cname1.add(cname.get(i));
                    tin1.add(tin.get(i));
                    billno1.add(billno.get(i));
                    date1.add(billdate.get(i));
                    taxp1.add("" + 12);
                    value1.add("" + value);
                    taxamt1.add("" + taxamt);
                    total1.add("" + total);
                    grn1.add(grn.get(i));
                    ttype1.add(ttype.get(i));
                }

                // 12 Percentage Ends
                // 18 Percentage Tax
                taxp = 18;
                value = 0;
                taxamt = 0;
                total = 0;
                query = "select sum(sub),sum(taxamt),sum(total) from purchase_items where bdate between '" + lk
                        + "' and '" + lk1 + "' and taxp='" + taxp + "' and billno='" + billno.get(i).toString() + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    value = r.getDouble(1);
                    taxamt = r.getDouble(2);
                    total = r.getDouble(3);
                }
                if (value > 0) {
                    cname1.add(cname.get(i));
                    tin1.add(tin.get(i));
                    billno1.add(billno.get(i));
                    date1.add(billdate.get(i));
                    taxp1.add("" + 18);
                    value1.add("" + value);
                    taxamt1.add("" + taxamt);
                    total1.add("" + total);
                    grn1.add(grn.get(i));
                    ttype1.add(ttype.get(i));
                }

                // 18 Percentage Ends
                // 28 Percentage Tax
                taxp = 28;
                value = 0;
                taxamt = 0;
                total = 0;
                query = "select sum(sub),sum(taxamt),sum(total) from purchase_items where bdate between '" + lk
                        + "' and '" + lk1 + "' and taxp='" + taxp + "' and billno='" + billno.get(i).toString() + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    value = r.getDouble(1);
                    taxamt = r.getDouble(2);
                    total = r.getDouble(3);
                }

                if (value > 0) {
                    cname1.add(cname.get(i));
                    tin1.add(tin.get(i));
                    billno1.add(billno.get(i));
                    date1.add(billdate.get(i));
                    taxp1.add("" + 28);
                    value1.add("" + value);
                    taxamt1.add("" + taxamt);
                    total1.add("" + total);
                    grn1.add(grn.get(i));
                    ttype1.add(ttype.get(i));
                }

                // 28 Percentage Ends
            } // bill no row counts ends

            int serial = 1;
            for (int i = 0; i < billno1.size(); i++) {
                double value2 = Double.parseDouble(value1.get(i).toString());
                double taxamt2 = Double.parseDouble(taxamt1.get(i).toString());
                double total2 = Double.parseDouble(total1.get(i).toString());
                double cgst = taxamt2 / 2;
                double igst = taxamt2;

                value2 = Double.parseDouble(df2.format(value2));
                taxamt2 = Double.parseDouble(df2.format(taxamt2));
                total2 = Double.parseDouble(df2.format(total2));
                cgst = Double.parseDouble(df2.format(cgst));
                igst = Double.parseDouble(df2.format(igst));

                String value3 = "" + value2;
                String[] splitter = value3.split("\\.");
                int what = splitter[1].length();
                if (what < 2) {
                    value3 = value3 + "0";
                }

                String taxamt3 = "" + taxamt2;
                String[] splitter1 = taxamt3.split("\\.");
                int what1 = splitter1[1].length();
                if (what1 < 2) {
                    taxamt3 = taxamt3 + "0";
                }

                String total3 = "" + total2;
                String[] splitter2 = total3.split("\\.");
                int what2 = splitter2[1].length();
                if (what2 < 2) {
                    total3 = total3 + "0";
                }

                String cgst3 = "" + cgst;
                String[] splitter3 = cgst3.split("\\.");
                int what3 = splitter3[1].length();
                if (what3 < 2) {
                    cgst3 = cgst3 + "0";
                }
                String igst3 = "" + igst;
                String[] splitter4 = igst3.split("\\.");
                int what4 = splitter4[1].length();
                if (what4 < 2) {
                    igst3 = igst3 + "0";
                }

                if (ttype1.get(i).toString().equalsIgnoreCase("Local")) {
                    igst3 = 0 + "";
                } else {
                    cgst3 = "" + 0;
                }

                s2.addRow(new Object[] { serial, cname1.get(i), tin1.get(i), billno1.get(i), date1.get(i), taxp1.get(i),
                        value3, igst3, cgst3, cgst3, "", taxamt3, total3, grn1.get(i) });
                serial = serial + 1;
            } // table add value ends

            double nvalue = 0, ntotal = 0, ncgst = 0, nigst = 0, ntax = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                nvalue = nvalue + Double.parseDouble(jTable1.getValueAt(i, 6).toString());
                nigst = nigst + Double.parseDouble(jTable1.getValueAt(i, 7).toString());
                ncgst = ncgst + Double.parseDouble(jTable1.getValueAt(i, 8).toString());
                ntax = ntax + Double.parseDouble(jTable1.getValueAt(i, 11).toString());
                ntotal = ntotal + Double.parseDouble(jTable1.getValueAt(i, 12).toString());

                String pname = jTable1.getValueAt(i, 1).toString();
                if (pname.equalsIgnoreCase(".")) {
                    jTable1.setValueAt("Cash Bill", i, 1);
                }

                double igst = Double.parseDouble(jTable1.getValueAt(i, 7).toString());
                if (igst <= 0) {
                    jTable1.setValueAt("", i, 7);
                }

                double cgst = Double.parseDouble(jTable1.getValueAt(i, 8).toString());
                if (cgst <= 0) {
                    jTable1.setValueAt("", i, 8);
                    jTable1.setValueAt("", i, 9);
                }

                double tax = Double.parseDouble(jTable1.getValueAt(i, 11).toString());
                if (tax <= 0) {
                    jTable1.setValueAt("", i, 11);
                }
            }

            nvalue = Double.parseDouble(df2.format(nvalue));
            ntax = Double.parseDouble(df2.format(ntax));
            ntotal = Double.parseDouble(df2.format(ntotal));
            ncgst = Double.parseDouble(df2.format(ncgst));
            nigst = Double.parseDouble(df2.format(nigst));

            String value3 = "" + nvalue;
            String[] splitter = value3.split("\\.");
            int what = splitter[1].length();
            if (what < 2) {
                value3 = value3 + "0";
            }

            String taxamt3 = "" + ntax;
            String[] splitter1 = taxamt3.split("\\.");
            int what1 = splitter1[1].length();
            if (what1 < 2) {
                taxamt3 = taxamt3 + "0";
            }

            String total3 = "" + ntotal;
            String[] splitter2 = total3.split("\\.");
            int what2 = splitter2[1].length();
            if (what2 < 2) {
                total3 = total3 + "0";
            }

            String cgst3 = "" + ncgst;
            String[] splitter3 = cgst3.split("\\.");
            int what3 = splitter3[1].length();
            if (what3 < 2) {
                cgst3 = cgst3 + "0";
            }

            String igst3 = "" + nigst;
            String[] splitter4 = igst3.split("\\.");
            int what4 = splitter4[1].length();
            if (what4 < 2) {
                igst3 = igst3 + "0";
            }

            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String cname4 = jTable1.getValueAt(i, 1).toString();
                query = "select distinct gstno from vendor where cname='" + cname4 + "' ";
                r = util.doQuery(query);
                while (r.next()) {
                    jTable1.setValueAt(r.getString(1), i, 2);
                }
            }

            s2.addRow(new Object[] { "", "", "", "", "", "", "", "", "", "", "", "", "", "" });
            s2.addRow(new Object[] { "", "TOTAL:" + (jTable1.getRowCount() - 1), "", "", "", "", "" + value3, igst3,
                    cgst3, cgst3, "", taxamt3, total3, "" });

            if (selva == true) {
                h1.setEnabled(false);
                h2.setEnabled(false);
                jCalendarButton1.setEnabled(false);
                jCalendarButton2.setEnabled(false);
                generatebutton.setEnabled(false);
            }

        } catch (ClassNotFoundException | NumberFormatException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public tax_purchase(DataUtil util) {
        initComponents();
        setTitle(titlelablel.getText());
        this.setSize(1021, 648);
        javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            setFrameIcon(icon);
        }
        this.util = util; // ✅ ASSIGN HERE
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
        titlelablel.setText("GSTR -Purchase");
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
        if (jTable1.getRowCount() > 1) {

            new ExcelUtilGST(h1.getText(), h2.getText()).GenerateGstrPurchaseWorkbook();

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
