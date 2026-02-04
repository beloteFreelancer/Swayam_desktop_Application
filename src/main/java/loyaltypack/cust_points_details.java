package loyaltypack;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.selrom.db.DataUtil;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import menupack.menu_form;
import menupack.sample2;
import smspack.manual_sms;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class cust_points_details extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    ResultSet r;
    AutoCompleteSupport support;

    void button_short() {
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Excel</b><br>(Alt+I)</h6><html>");
        smsbutton.setText("<html><b>SMS</b><br>(Alt+M)</h6><html>");
        titlelablel.setText("<html><u>Customer Points Details</u></html>");

        setTitle("Customer Points Details");
        this.setSize(1021, 648);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
    }

    public void load_list_table() {
        s2.addColumn("Cust_Id");
        s2.addColumn("Name");
        s2.addColumn("Area /City");
        s2.addColumn("Mobile No");
        s2.addColumn("Total Points");
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

    void load_report_using_points() {
        if (h2.getText().equals("")) {
            h2.setText("" + 0);
        }
        String query = "select a.cid,a.cname,city,mobile,points from cust a,cust_points b where a.cid=b.cid and points " + h1.getSelectedItem() + " " + h2.getText() + " order by points";
        load_report(query);
    }

    void load_report_using_mobileno() {
        if (h3.getSelectedItem() == null || h3.getSelectedItem() == "") {
            JOptionPane.showMessageDialog(this, "Enter Mobile No ?", "Mobile", JOptionPane.ERROR_MESSAGE);
            h3.requestFocus();
            return;
        }
        String query = "select a.cid,a.cname,city,mobile,points from cust a,cust_points b where a.cid=b.cid and mobile=" + h3.getSelectedItem() + " order by points";
        load_report(query);
    }

    void load_report_all() {
        String query = "select a.cid,a.cname,city,mobile,points from cust a,cust_points b where a.cid=b.cid order by points";
        load_report(query);
    }

    void load_report(String query) {
        try {
            if (s2.getRowCount() > 0) {
                s2.getDataVector().removeAllElements();
                s2.fireTableDataChanged();
            }
            totl.setText("");

            r = util.doQuery(query);
            while (r.next()) {
                s2.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), r.getString(4), (int) r.getDouble(5)});
            }
            totl.setText(" Total Records: " + jTable1.getRowCount());
        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    final void get_mobileno() {
        try {
            int count = 0;
            String query = "select distinct mobile from cust";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct mobile from cust";
            set = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support = AutoCompleteSupport.install(h3, GlazedLists.eventListOf(f));

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public cust_points_details(DataUtil util) {
        initComponents();

        this.util = util;
        button_short();
        load_list_table();
        get_mobileno();
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
        h1 = new javax.swing.JComboBox();
        smsbutton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        totl = new javax.swing.JLabel();
        h2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        h3 = new javax.swing.JComboBox<>();
        generatebutton1 = new javax.swing.JButton();
        generatebutton2 = new javax.swing.JButton();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Customer Points Details");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 370, 30);

        generatebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/generate30.png"))); // NOI18N
        generatebutton.setText("Generate");
        generatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton);
        generatebutton.setBounds(220, 50, 170, 30);

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
        jLabel5.setText("Mobile No");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(420, 50, 70, 30);

        h1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        h1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { ">", "<", "=" }));
        h1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                h1ItemStateChanged(evt);
            }
        });
        getContentPane().add(h1);
        h1.setBounds(50, 50, 50, 30);

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
        totl.setBounds(0, 560, 380, 40);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(100, 50, 120, 30);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Points");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(10, 50, 40, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h3);
        h3.setBounds(490, 50, 160, 30);

        generatebutton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/generate30.png"))); // NOI18N
        generatebutton1.setText("Generate");
        generatebutton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebutton1ActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton1);
        generatebutton1.setBounds(650, 50, 130, 30);

        generatebutton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/select22.png"))); // NOI18N
        generatebutton2.setText("Select All");
        generatebutton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebutton2ActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton2);
        generatebutton2.setBounds(820, 50, 180, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generatebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatebuttonActionPerformed
        load_report_using_points();

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
            String file_name = "List";

            try (FileWriter f = new FileWriter(new File(folder + "/Drafts/" + file_name + ".xls"))) {
                f.write("Customer Points Details:\n");
                for (int i1 = 0; i1 < jTable1.getColumnCount(); i1++) {
                    String kg = jTable1.getColumnName(i1);
                    f.write(kg);
                    f.write("\t");
                }
                f.write("\n");
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    for (int j = 0; j < jTable1.getColumnCount(); j++) {
                        String val = jTable1.getValueAt(i, j).toString();
                        f.write(val);
                        f.write("\t");
                    }
                    f.write("\n");
                }
            }
            Runtime rt = Runtime.getRuntime();
            Utils.AppConfig.openFile(folder + "/Drafts/" + file_name + ".xls");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }//GEN-LAST:event_excelbuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        h1.setSelectedIndex(0);
        h2.setText("");
        h3.setSelectedItem("");
    }//GEN-LAST:event_clearbuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void h1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_h1ItemStateChanged

    }//GEN-LAST:event_h1ItemStateChanged

    private void smsbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smsbuttonActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Sorry, No Records Were Found!", "Oops", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ArrayList cid = new ArrayList();
        ArrayList cname = new ArrayList();
        ArrayList mobile = new ArrayList();
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            cid.add(jTable1.getValueAt(i, 0));
            cname.add(jTable1.getValueAt(i, 1));
            mobile.add(jTable1.getValueAt(i, 3));
        }
        manual_sms oe = new manual_sms(util);
        JDesktopPane desktop_pane = getDesktopPane();
        desktop_pane.add(oe);
        oe.load_list_from_outside(cid, cname, mobile);
        oe.setVisible(true);
        this.dispose();
        Dimension desktopSize = desktop_pane.getSize();
        Dimension jInternalFrameSize = oe.getSize();
        oe.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);

    }//GEN-LAST:event_smsbuttonActionPerformed

    private void generatebutton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatebutton1ActionPerformed
        load_report_using_mobileno();

    }//GEN-LAST:event_generatebutton1ActionPerformed

    private void generatebutton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatebutton2ActionPerformed
        load_report_all();
    }//GEN-LAST:event_generatebutton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton excelbutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JButton generatebutton1;
    private javax.swing.JButton generatebutton2;
    private javax.swing.JComboBox h1;
    private javax.swing.JTextField h2;
    private javax.swing.JComboBox<String> h3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton smsbutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JLabel totl;
    // End of variables declaration//GEN-END:variables
}
