package venpack;

import Utils.ColorConstants;
import com.selrom.db.DataUtil;
import com.selrom.utils.ExcelUtil;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import menupack.menu_form;
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public final class ven_dues_overdue extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    int hmany = 2;

    void button_short() {
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Excel</b><br>(Alt+I)</h6><html>");
        generatebutton.setText("<html><b>Generate</b>  (Alt+G)</h6><html>");
        titlelablel.setText("<html><u>Supplier Over Dues Report</u></html>");

        setTitle("Supplier Over Dues Report");
        this.setSize(1021, 648);
        javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            setFrameIcon(icon);
        }
        menu_form me = new menu_form();
        hmany = me.getHmany();
    }

    public void load_list_table() {
        s2.addColumn("Bill No");
        s2.addColumn("Bill Date");
        s2.addColumn("Due Date");
        s2.addColumn("Days Diff");
        s2.addColumn("Name");
        s2.addColumn("Due Amount");
        s2.addColumn("Mobile No");
        jTable1.setModel(s2);
        DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
        dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
        jTable1.getColumnModel().getColumn(5).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(6).setCellRenderer(dtcr1);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(374);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(110);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    void load_report() {
        try {

            boolean selva = false;
            String query;
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("yyyy-MM-dd");
            String today = g.format(d);
            query = "select billno,date_format(dat,'%d/%m/%Y'),date_format(ddate,'%d/%m/%Y'),datediff(ddate,'" + today
                    + "'),a.cname,tot-paid,mobile from ven_bal a,vendor b where ddate < '" + today
                    + "' and tot-paid>0 and a.cname=b.cname order by ddate,billno";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                double tot = r.getDouble(6);
                String tot2 = String.format("%." + hmany + "f", tot);
                s2.addRow(new Object[] { r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5),
                        tot2, r.getString(7) });
                selva = true;
            }

            double tot = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                tot = tot + Double.parseDouble(jTable1.getValueAt(i, 5).toString());
            }
            String tot2 = String.format("%." + hmany + "f", tot);
            totl.setText(" Total Records: " + jTable1.getRowCount() + "  Due Amount: " + tot2);
            if (selva == true) {
                generatebutton.setEnabled(false);
            }
        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ven_dues_overdue(DataUtil util) {
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
        totl = new javax.swing.JLabel();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Supplier Over Dues Report");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 390, 30);

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
        jScrollPane1.setBounds(0, 52, 1000, 510);

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
        generatebutton.setBounds(820, 20, 180, 30);

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

        totl.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(totl);
        totl.setBounds(0, 560, 620, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable1MouseClicked

    }// GEN-LAST:event_jTable1MouseClicked

    private void generatebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_generatebuttonActionPerformed
        load_report();

    }// GEN-LAST:event_generatebuttonActionPerformed

    private void excelbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_excelbuttonActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Sorry, No Records Were Found!", "Oops", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            menupack.menu_form mp = new menu_form();
            String folder = mp.getFoler();
            String file_name = "SupplierOverDueReport.xlsx";
            File file = new File(folder + "/Drafts/" + file_name);

            ExcelUtil.exportTableToExcel(jTable1, file);

            Runtime.getRuntime();
            Utils.AppConfig.openFile(file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_excelbuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbuttonActionPerformed
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        generatebutton.setEnabled(true);
        totl.setText("");

    }// GEN-LAST:event_clearbuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }// GEN-LAST:event_closebuttonActionPerformed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTable1FocusGained

    }// GEN-LAST:event_jTable1FocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton excelbutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JLabel totl;
    // End of variables declaration//GEN-END:variables
}
