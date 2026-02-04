package custpack;

import ca.odell.glazedlists.swing.AutoCompleteSupport;
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
 * mysoft.java@gmail.com
 */
public final class cust_balance_report extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    ResultSet r;
    AutoCompleteSupport support;
    int hmany = 2;

    void button_short() {
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Excel</b><br>(Alt+I)</h6><html>");
        smsbutton.setText("<html><b>SMS</b><br>(Alt+M)</h6><html>");
        generatebutton.setText("<html><b>Generate</b>  (Alt+G)</h6><html>");
        titlelablel.setText("<html><u>Customer Overall Balance Report</u></html>");

        setTitle("Customer Overall Balance Report");
        this.setSize(1021, 648);
        java.net.URL imgUrl = getClass().getResource("/images/icon.png");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            this.setFrameIcon(icon);
        }
        menu_form me = new menu_form();
        hmany = me.getHmany();
    }

    public void load_list_table() {
        s2.addColumn("Cust_Id");
        s2.addColumn("Name");
        s2.addColumn("Area /City");
        s2.addColumn("Mobile No");
        s2.addColumn("Net Balance");
        jTable1.setModel(s2);
        DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
        dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
        jTable1.getColumnModel().getColumn(4).setCellRenderer(dtcr1);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        jTable1.getColumnModel().getColumn(0).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(300);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(250);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(144);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(150);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    void load_report() {
        try {
            boolean selva = false;
            String query;
            if (all.isSelected()) {
                query = "select a.cid,a.cname,city,mobile,sum(tot-paid) from cust_bal a,cust b where a.cid=b.cid group by a.cid having sum(tot-paid)>0 order by city";
            } else {
                query = "select a.cid,a.cname,city,mobile,sum(tot-paid) from cust_bal a,cust b where a.cid=b.cid and city='" + h3.getSelectedItem() + "' group by a.cid having sum(tot-paid)>0 order by a.cid";
            }
            r = util.doQuery(query);
            while (r.next()) {
                double tot = r.getDouble(5);
                String tot2 = String.format("%." + hmany + "f", tot);
                s2.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), r.getString(4), tot2});
                selva = true;
            }

            double tot = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                tot = tot + Double.parseDouble(jTable1.getValueAt(i, 4).toString());
            }
            String tot2 = String.format("%." + hmany + "f", tot);
            totl.setText(" Total Records: " + jTable1.getRowCount() + "   Amount: " + tot2);
            if (selva == true) {
                generatebutton.setEnabled(false);
                h3.setEnabled(false);
                all.setEnabled(false);
            }
        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_cid() {
        try {
            h3.removeAllItems();
            String query = "select distinct city from cust";
            r = util.doQuery(query);
            while (r.next()) {
                h3.addItem(r.getString(1));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void send_sms() {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "Sorry, No Records Were Found!", "Oops", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int as = JOptionPane.showConfirmDialog(this, "Want to Send SMS ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            String smsuser = "", smspass = "", sender = "", smsfoot1 = "", smsfoot2 = "";
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            String query = "select user,pass,sender,smsfoot1,smsfoot2 from setting_sms";
            r = util.doQuery(query);
            while (r.next()) {
                smsuser = r.getString(1);
                smspass = r.getString(2);
                sender = r.getString(3);
                smsfoot1 = r.getString(4);
                smsfoot2 = r.getString(5);
            }
            String cname1 = "\n\nFor Queries,\n" + smsfoot1 + "\n" + smsfoot2;

            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String cname = jTable1.getValueAt(i, 1).toString();
                String amount = jTable1.getValueAt(i, 4).toString();
                String phone = jTable1.getValueAt(i, 3).toString();

                if (phone.length() == 10) {
                    String message = "Dear " + cname + ", Your Total Balance: " + amount + " as on " + g.format(d) + ". Kindly make Payment early, Ignore if Already Paid.\n\n" + cname1;
                    new smspack.SMS_Sender_Single().getData(smsuser, smspass, sender, phone, message);
                }
            }//row counts ends
            JOptionPane.showMessageDialog(this, "<html><h1>Send Successfully</h1></html>", "Send", JOptionPane.PLAIN_MESSAGE);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public cust_balance_report(DataUtil util) {
        initComponents();

        this.util = util;
        button_short();
        load_list_table();
        get_cid();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        generatebutton = new javax.swing.JButton();
        excelbutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        h3 = new javax.swing.JComboBox();
        all = new javax.swing.JCheckBox();
        smsbutton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        totl = new javax.swing.JLabel();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Customer Overall Balance Report");
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
        generatebutton.setBounds(630, 50, 180, 30);

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
        excelbutton.setBounds(490, 560, 130, 50);

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
        jLabel5.setText("Area");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(10, 50, 40, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        h3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                h3ItemStateChanged(evt);
            }
        });
        getContentPane().add(h3);
        h3.setBounds(50, 50, 500, 30);

        all.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        all.setText("Select All");
        all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allActionPerformed(evt);
            }
        });
        getContentPane().add(all);
        all.setBounds(550, 50, 90, 30);

        smsbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        smsbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sms45.png"))); // NOI18N
        smsbutton.setMnemonic('m');
        smsbutton.setText("SMS");
        smsbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smsbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(smsbutton);
        smsbutton.setBounds(620, 560, 130, 50);

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
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(2, 80, 1000, 480);

        totl.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(totl);
        totl.setBounds(0, 560, 490, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generatebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatebuttonActionPerformed
        load_report();

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
            String file_name = "CustomerOverallBalanceReport.xlsx";
            File file = new File(folder + "/Drafts/" + file_name);

            ExcelUtil.exportTableToExcel(jTable1, file);

            Runtime.getRuntime();
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
        h3.setEnabled(true);
        all.setEnabled(true);
        all.setSelected(false);
        generatebutton.setEnabled(true);
        h3.setSelectedItem("");

    }//GEN-LAST:event_clearbuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void h3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_h3ItemStateChanged

    }//GEN-LAST:event_h3ItemStateChanged

    private void allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allActionPerformed
        if (all.isSelected()) {
            h3.setEnabled(false);
        } else {
            h3.setEnabled(true);
        }

    }//GEN-LAST:event_allActionPerformed

    private void smsbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smsbuttonActionPerformed
        send_sms();
    }//GEN-LAST:event_smsbuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox all;
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton excelbutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JComboBox h3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton smsbutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JLabel totl;
    // End of variables declaration//GEN-END:variables
}
