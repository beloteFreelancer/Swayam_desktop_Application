package purchasepack;

import com.selrom.db.DataUtil;
import com.selrom.utils.ExcelUtil;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import menupack.menu_form;
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public class preturn_report extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    ResultSet r;
    DecimalFormat df2 = new DecimalFormat("#.##");

    final void button_short() {
        excelbutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Excel</b><br>(Alt+I)</h6><html>");
        viewbutton.setText("<html><b>View</b><br>(Alt+V)</h6><html>");
        generatebutton.setText("<html><b>Generate</b>  (Alt+G)</h6><html>");
        titlelablel.setText("<html><u>Purchase Return Report</u></html>");
    }

    public final void load_list_table() {
        s2.addColumn("Entry No");
        s2.addColumn("Entry Date");
        s2.addColumn("Supplier");
        s2.addColumn("Bill No");
        s2.addColumn("Bill Date");
        s2.addColumn("Total Items");
        s2.addColumn("Total Qty");
        s2.addColumn("Sub Total");
        s2.addColumn("Dis Amt");
        s2.addColumn("Gross Amt");
        s2.addColumn("Tax Amt");
        s2.addColumn("Fright");
        s2.addColumn("Other Charges");
        s2.addColumn("Net Amt");
        s2.addColumn("Pay Mode");
        s2.addColumn("User_Name");
        s2.addColumn("Last Modified at");
        jTable1.setModel(s2);
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
        jTable1.getColumnModel().getColumn(14).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(15).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(16).setCellRenderer(dtcr1);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(270);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(10).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(11).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(12).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(13).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(14).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(15).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(16).setPreferredWidth(180);

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
            double sub, disamt, net, gross, taxamt, frieght, other;

            String query, pby = h3.getSelectedItem().toString();
            PreparedStatement ps;
            Connection conn = util.getConnection();

            if (all.isSelected()) {
                query = "select distinct grn,date_format(dat,'%d/%m/%Y'),cname,billno,date_format(bdate,'%d/%m/%Y'),items,quans,sub,dis,gross,tax,fright,other,net,pby,user,last from preturn where bdate between ? and ? order by bdate,grn";
                ps = conn.prepareStatement(query);
                ps.setString(1, lk);
                ps.setString(2, lk1);
            } else {
                query = "select distinct grn,date_format(dat,'%d/%m/%Y'),cname,billno,date_format(bdate,'%d/%m/%Y'),items,quans,sub,dis,gross,tax,fright,other,net,pby,user,last from preturn where bdate between ? and ? and pby=? order by bdate,grn";
                ps = conn.prepareStatement(query);
                ps.setString(1, lk);
                ps.setString(2, lk1);
                ps.setString(3, pby);
            }
            r = ps.executeQuery();
            while (r.next()) {
                sub = r.getDouble(8);
                disamt = r.getDouble(9);
                gross = r.getDouble(10);
                taxamt = r.getDouble(11);
                frieght = r.getDouble(12);
                other = r.getDouble(13);
                net = r.getDouble(14);

                sub = Double.parseDouble(df2.format(sub));
                disamt = Double.parseDouble(df2.format(disamt));
                gross = Double.parseDouble(df2.format(gross));
                taxamt = Double.parseDouble(df2.format(taxamt));
                frieght = Double.parseDouble(df2.format(frieght));
                other = Double.parseDouble(df2.format(other));
                net = Double.parseDouble(df2.format(net));

                String sub1 = "" + sub;
                String[] split = sub1.split("\\.");
                int what = split[1].length();
                if (what < 2) {
                    sub1 = sub1 + "0";
                }

                String disamt1 = "" + disamt;
                String[] split1 = disamt1.split("\\.");
                int what1 = split1[1].length();
                if (what1 < 2) {
                    disamt1 = disamt1 + "0";
                }

                String gross1 = "" + gross;
                String[] split2 = gross1.split("\\.");
                int what2 = split2[1].length();
                if (what2 < 2) {
                    gross1 = gross1 + "0";
                }

                String taxamt1 = "" + taxamt;
                String[] split3 = taxamt1.split("\\.");
                int what3 = split3[1].length();
                if (what3 < 2) {
                    taxamt1 = taxamt1 + "0";
                }

                String net1 = "" + net;
                String[] split4 = net1.split("\\.");
                int what4 = split4[1].length();
                if (what4 < 2) {
                    net1 = net1 + "0";
                }

                String frieght1 = "" + frieght;
                String[] split6 = frieght1.split("\\.");
                int what6 = split6[1].length();
                if (what6 < 2) {
                    frieght1 = frieght1 + "0";
                }

                String other1 = "" + other;
                String[] split7 = other1.split("\\.");
                int what7 = split7[1].length();
                if (what7 < 2) {
                    other1 = other1 + "0";
                }

                s2.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6), r.getString(7), sub1, disamt1, gross1, taxamt1, frieght1, other1, net1, r.getString(15), r.getString(16), r.getString(17)});
                selva = true;
            }
            sub = 0;
            disamt = 0;
            net = 0;
            gross = 0;
            taxamt = 0;
            frieght = 0;
            other = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                sub = sub + Double.parseDouble(jTable1.getValueAt(i, 7).toString());
                disamt = disamt + Double.parseDouble(jTable1.getValueAt(i, 8).toString());
                gross = gross + Double.parseDouble(jTable1.getValueAt(i, 9).toString());
                taxamt = taxamt + Double.parseDouble(jTable1.getValueAt(i, 10).toString());
                frieght = frieght + Double.parseDouble(jTable1.getValueAt(i, 11).toString());
                other = other + Double.parseDouble(jTable1.getValueAt(i, 12).toString());
                net = net + Double.parseDouble(jTable1.getValueAt(i, 13).toString());
            }
            sub = Double.parseDouble(df2.format(sub));
            disamt = Double.parseDouble(df2.format(disamt));
            gross = Double.parseDouble(df2.format(gross));
            taxamt = Double.parseDouble(df2.format(taxamt));
            frieght = Double.parseDouble(df2.format(frieght));
            other = Double.parseDouble(df2.format(other));
            net = Double.parseDouble(df2.format(net));

            String sub1 = "" + sub;
            String[] split = sub1.split("\\.");
            int what = split[1].length();
            if (what < 2) {
                sub1 = sub1 + "0";
            }

            String disamt1 = "" + disamt;
            String[] split1 = disamt1.split("\\.");
            int what1 = split1[1].length();
            if (what1 < 2) {
                disamt1 = disamt1 + "0";
            }

            String gross1 = "" + gross;
            String[] split2 = gross1.split("\\.");
            int what2 = split2[1].length();
            if (what2 < 2) {
                gross1 = gross1 + "0";
            }

            String taxamt1 = "" + taxamt;
            String[] split3 = taxamt1.split("\\.");
            int what3 = split3[1].length();
            if (what3 < 2) {
                taxamt1 = taxamt1 + "0";
            }

            String net1 = "" + net;
            String[] split4 = net1.split("\\.");
            int what4 = split4[1].length();
            if (what4 < 2) {
                net1 = net1 + "0";
            }
            String frieght1 = "" + frieght;
            String[] split6 = frieght1.split("\\.");
            int what6 = split6[1].length();
            if (what6 < 2) {
                frieght1 = frieght1 + "0";
            }

            String other1 = "" + other;
            String[] split7 = other1.split("\\.");
            int what7 = split7[1].length();
            if (what7 < 2) {
                other1 = other1 + "0";
            }

            if (selva == true) {
                s2.addRow(new Object[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""});
                s2.addRow(new Object[]{"", "", "TOTAL:" + (jTable1.getRowCount() - 1), "", "", "", "", "" + sub1, disamt1, gross1, taxamt1, frieght1, other1, net1, "", "", ""});

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

    public preturn_report(DataUtil util) {
        initComponents();
        setTitle("Purchase Report");
        this.setSize(1017, 650);
        java.net.URL imgUrl = getClass().getResource("/images/icon.png");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            this.setFrameIcon(icon);
        }
        this.util = util;
        load_list_table();
        button_short();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        generatebutton = new javax.swing.JButton();
        viewbutton = new javax.swing.JButton();
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
        titlelablel.setText("Purchase Return Report");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 280, 30);

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

        viewbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        viewbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/view45.png"))); // NOI18N
        viewbutton.setMnemonic('v');
        viewbutton.setText("View");
        viewbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(viewbutton);
        viewbutton.setBounds(610, 560, 130, 50);

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
        jLabel11.setText(" Pay Mode");
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
        excelbutton.setBounds(480, 560, 130, 50);

        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h1);
        h1.setBounds(80, 40, 110, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Credit", "Cash", "Card", "Cheque", "Others" }));
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

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewbuttonActionPerformed
        preturn_entry oe = new preturn_entry(util);
        JDesktopPane desktop_pane = getDesktopPane();
        desktop_pane.add(oe);
        String sno = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
        oe.view(sno);
        oe.setVisible(true);

        Dimension desktopSize = desktop_pane.getSize();
        Dimension jInternalFrameSize = oe.getSize();
        oe.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);

    }//GEN-LAST:event_viewbuttonActionPerformed

    private void excelbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excelbuttonActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Sorry, No Records Were Found!", "Oops", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            menupack.menu_form mp = new menu_form();
            String drive = mp.getDrive();
            String folder = mp.getFoler();

            String file_name = "Purchase_Return_Report.xlsx";
            File file = new File(folder + "/Drafts/" + file_name);

            ExcelUtil.exportTableToExcel(jTable1, file);

            Runtime rt = Runtime.getRuntime();
            Utils.AppConfig.openFile(file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
