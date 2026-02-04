package tax_reports;

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
 * mysoft.java@gmail.com
 */
public final class tax_summary extends javax.swing.JInternalFrame {

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
        titlelablel.setText("<html><u>GSTR -Summary</u></html>");
    }

    public void load_list_table() {
        s2.addColumn("Particulars");
        s2.addColumn("5% Tax Amount");
        s2.addColumn("12% Tax Amount");
        s2.addColumn("18% Tax Amount");
        s2.addColumn("28% Tax Amount");
        s2.addColumn("Cess");
        s2.addColumn("Total Tax Amount");
        jTable1.setModel(s2);
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
        dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
        jTable1.getColumnModel().getColumn(1).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(2).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(3).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(4).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(5).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(6).setCellRenderer(dtcr1);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(219);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(135);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(135);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(135);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(135);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(135);
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
            String query;

            double s5 = 0, s12 = 0, s18 = 0, s28 = 0, stot = 0;
            double sr5 = 0, sr12 = 0, sr18 = 0, sr28 = 0, srtot = 0;
            query = "select Sum(CASE WHEN t.taxp = '5' THEN t.taxamt ELSE NULL END) AS Tax5,Sum(CASE WHEN t.taxp = '12' THEN t.taxamt ELSE NULL END) AS Tax12,Sum(CASE WHEN t.taxp = '18' THEN t.taxamt ELSE NULL END) AS Tax18,Sum(CASE WHEN t.taxp = '28' THEN t.taxamt ELSE NULL END) AS Tax28 from sales_items t where dat between '" + lk + "' and '" + lk1 + "' and entry='purchase' ";
            r = util.doQuery(query);
            while (r.next()) {
                s5 = r.getDouble(1);
                s12 = r.getDouble(2);
                s18 = r.getDouble(3);
                s28 = r.getDouble(4);
            }
            stot = s5 + s12 + s18 + s28;

            String s51 = String.format("%." + hmany + "f", s5);
            String s121 = String.format("%." + hmany + "f", s12);
            String s181 = String.format("%." + hmany + "f", s18);
            String s281 = String.format("%." + hmany + "f", s28);
            String stot1 = String.format("%." + hmany + "f", stot);
            s2.addRow(new Object[]{"Sales", s51, s121, s181, s281, 0, stot1});

            query = "select Sum(CASE WHEN t.taxp = '5' THEN t.taxamt ELSE NULL END) AS Tax5,Sum(CASE WHEN t.taxp = '12' THEN t.taxamt ELSE NULL END) AS Tax12,Sum(CASE WHEN t.taxp = '18' THEN t.taxamt ELSE NULL END) AS Tax18,Sum(CASE WHEN t.taxp = '28' THEN t.taxamt ELSE NULL END) AS Tax28 from sreturn_items t where dat between '" + lk + "' and '" + lk1 + "' and entry='purchase' ";
            r = util.doQuery(query);
            while (r.next()) {
                sr5 = r.getDouble(1);
                sr12 = r.getDouble(2);
                sr18 = r.getDouble(3);
                sr28 = r.getDouble(4);
            }
            srtot = sr5 + sr12 + sr18 + sr28;

            String sr51 = String.format("%." + hmany + "f", sr5);
            String sr121 = String.format("%." + hmany + "f", sr12);
            String sr181 = String.format("%." + hmany + "f", sr18);
            String sr281 = String.format("%." + hmany + "f", sr28);
            String srtot1 = String.format("%." + hmany + "f", srtot);
            s2.addRow(new Object[]{"(-) Sales Return", sr51, sr121, sr181, sr281, 0, srtot1});
            double output = stot - srtot;
            String output1 = String.format("%." + hmany + "f", output);

            s2.addRow(new Object[]{"Output Tax: ", "", "", "", "", "", "" + output1});
            s2.addRow(new Object[]{"", "", "", "", "", "", ""});

            double p5 = 0, p12 = 0, p18 = 0, p28 = 0, ptot = 0;
            double pr5 = 0, pr12 = 0, pr18 = 0, pr28 = 0, prtot = 0;
            query = "select Sum(CASE WHEN t.taxp = '5' THEN t.taxamt ELSE NULL END) AS Tax5,Sum(CASE WHEN t.taxp = '12' THEN t.taxamt ELSE NULL END) AS Tax12,Sum(CASE WHEN t.taxp = '18' THEN t.taxamt ELSE NULL END) AS Tax18,Sum(CASE WHEN t.taxp = '28' THEN t.taxamt ELSE NULL END) AS Tax28 from purchase_items t where dat between '" + lk + "' and '" + lk1 + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                p5 = r.getDouble(1);
                p12 = r.getDouble(2);
                p18 = r.getDouble(3);
                p28 = r.getDouble(4);
            }
            ptot = p5 + p12 + p18 + p28;
            String p51 = String.format("%." + hmany + "f", p5);
            String p121 = String.format("%." + hmany + "f", p12);
            String p181 = String.format("%." + hmany + "f", p18);
            String p281 = String.format("%." + hmany + "f", p28);
            String ptot1 = String.format("%." + hmany + "f", ptot);
            s2.addRow(new Object[]{"Purchase", p51, p121, p181, p281, 0, ptot1});

            query = "select Sum(CASE WHEN t.taxp = '5' THEN t.taxamt ELSE NULL END) AS Tax5,Sum(CASE WHEN t.taxp = '12' THEN t.taxamt ELSE NULL END) AS Tax12,Sum(CASE WHEN t.taxp = '18' THEN t.taxamt ELSE NULL END) AS Tax18,Sum(CASE WHEN t.taxp = '28' THEN t.taxamt ELSE NULL END) AS Tax28 from preturn_items t where dat between '" + lk + "' and '" + lk1 + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                pr5 = r.getDouble(1);
                pr12 = r.getDouble(2);
                pr18 = r.getDouble(3);
                pr28 = r.getDouble(4);
            }
            prtot = pr5 + pr12 + pr18 + pr28;
            String pr51 = String.format("%." + hmany + "f", pr5);
            String pr121 = String.format("%." + hmany + "f", pr12);
            String pr181 = String.format("%." + hmany + "f", pr18);
            String pr281 = String.format("%." + hmany + "f", pr28);
            String prtot1 = String.format("%." + hmany + "f", prtot);
            s2.addRow(new Object[]{"(-) Purchase Return", pr51, pr121, pr181, pr281, 0, prtot1});

            double input = ptot - prtot;
            String input1 = String.format("%." + hmany + "f", input);
            s2.addRow(new Object[]{"Input Tax: ", "", "", "", "", "", "" + input1});
            s2.addRow(new Object[]{"", "", "", "", "", "", ""});

            double tax = output - input;
            String tax1 = String.format("%." + hmany + "f", tax);
            s2.addRow(new Object[]{"GST SUMMARY", "", "", "", "", "", ""});
            s2.addRow(new Object[]{"Output Tax", "" + output1, "", "", "", "", ""});
            s2.addRow(new Object[]{"Input Tax", "" + input1, "", "", "", "", ""});
            s2.addRow(new Object[]{"", "", "", "", "", "", ""});
            s2.addRow(new Object[]{"Tax Payable", "" + tax1, "", "", "", "", ""});
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

    public tax_summary(DataUtil util) {
        initComponents();
        setTitle(titlelablel.getText());
        this.setSize(1021, 648);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
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
        excelWorkbookButton = new javax.swing.JButton();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("GSTR -Summary");
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
        generatebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/generate30.png"))); // NOI18N
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

        jCalendarButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
        jCalendarButton2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton2PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton2);
        jCalendarButton2.setBounds(170, 50, 40, 30);

        jCalendarButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
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

        excelWorkbookButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        excelWorkbookButton.setText("Excel Workbook");
        excelWorkbookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excelWorkbookButtonActionPerformed(evt);
            }
        });
        getContentPane().add(excelWorkbookButton);
        excelWorkbookButton.setBounds(500, 560, 120, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

    }//GEN-LAST:event_jTable1MouseClicked

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
        } else {
            new ExcelUtilGST(h1.getText(), h2.getText()).GenerateSummaryWorkbook();
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
        generatebutton.setEnabled(true);

    }//GEN-LAST:event_clearbuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained

    }//GEN-LAST:event_jTable1FocusGained

    private void jCalendarButton2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendarButton2PropertyChange
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

    }//GEN-LAST:event_jCalendarButton2PropertyChange

    private void jCalendarButton1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendarButton1PropertyChange
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
    }//GEN-LAST:event_jCalendarButton1PropertyChange

    private void excelWorkbookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excelWorkbookButtonActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Sorry, No Records Were Found!", "Oops", JOptionPane.ERROR_MESSAGE);
        } else {
            new ExcelUtilGST(h1.getText(), h2.getText()).GenerateGSTWorkbookComplete();
        }
    }//GEN-LAST:event_excelWorkbookButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton excelWorkbookButton;
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
