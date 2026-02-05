package stockpack;

import Utils.ColorConstants;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.selrom.db.DataUtil;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class stock_report_max_stock extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    ResultSet r;
    AutoCompleteSupport support;

    final void button_short() {
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Excel</b><br>(Alt+I)</h6><html>");
        generatebutton.setText("<html><b>Generate</b>  (Alt+G)</h6><html>");
        titlelablel.setText("<html><u>Maximum Stock Report (Excess Stock Report)</u></html>");
        setTitle("Maximum Stock Report (Excess Stock Report)");
        this.setSize(1021, 648);
        javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            setFrameIcon(icon);
        }
    }

    final void load_list_table() {
        s2.addColumn("It.Code");
        s2.addColumn("It.Name");
        s2.addColumn("Stock-In-Hand");
        s2.addColumn("Maximum Stock Qty");
        s2.addColumn("Excess Qty");
        jTable1.setModel(s2);
        DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
        dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
        jTable1.getColumnModel().getColumn(2).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(3).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(4).setCellRenderer(dtcr1);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(394);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(160);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(160);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(170);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    void load_report() {
        try {
            boolean selva = false;
            String query;
            if (all.isSelected()) {
                query = "select distinct a.ino,a.iname,maxstock,sum(quan) from stock a,item b where a.ino=b.ino and a.iname=b.iname group by a.ino having sum(quan)> maxstock order by a.iname";
            } else {
                query = "select distinct a.ino,a.iname,maxstock,sum(quan) from stock a,item b where a.cat='"
                        + h3.getSelectedItem()
                        + "' and a.ino=b.ino and a.iname=b.iname group by a.ino having sum(quan)> maxstock order by a.iname";
            }
            r = util.doQuery(query);
            while (r.next()) {
                long minstock = r.getInt(3);
                long stock = r.getInt(4);
                long diff = stock - minstock;
                s2.addRow(new Object[] { r.getString(1), r.getString(2), stock, minstock, diff });
                selva = true;
            }
            totl.setText(" Total Records: " + jTable1.getRowCount());
            if (selva == true) {
                generatebutton.setEnabled(false);
                h3.setEnabled(false);
                all.setEnabled(false);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    final void get_cid() {
        try {
            int count = 0;
            String query = "select distinct cat from stock";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select distinct cat from stock";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support = AutoCompleteSupport.install(
                    h3, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public stock_report_max_stock(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();
        load_list_table();
        get_cid();
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
        jLabel5 = new javax.swing.JLabel();
        h3 = new javax.swing.JComboBox();
        all = new javax.swing.JCheckBox();
        totl = new javax.swing.JLabel();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Maximum Stock Report (Excess Stock Report)");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 480, 30);

        jTable1.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
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
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable1FocusGained(evt);
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
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
        generatebutton.setBounds(560, 50, 180, 30);

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

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Category");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(10, 50, 70, 30);

        h3.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        h3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        h3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                h3ItemStateChanged(evt);
            }
        });
        getContentPane().add(h3);
        h3.setBounds(70, 50, 410, 30);

        all.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        all.setText("Select All");
        all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allActionPerformed(evt);
            }
        });
        getContentPane().add(all);
        all.setBounds(480, 50, 83, 30);

        totl.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(totl);
        totl.setBounds(0, 560, 620, 30);

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
            String drive = mp.getDrive();
            String folder = mp.getFoler();
            String file_name = "List";
            try (FileWriter f = new FileWriter(new File(folder + "/Drafts/" + file_name + ".xls"))) {
                f.write("Maximum Stock Report (Excess Stock Report):\n");
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
                f.write("" + totl.getText());
            }
            Runtime rt = Runtime.getRuntime();
            Utils.AppConfig.openFile(folder + "/Drafts/" + file_name + ".xls");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_excelbuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbuttonActionPerformed
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        h3.setEnabled(true);
        all.setEnabled(true);
        all.setSelected(false);
        generatebutton.setEnabled(true);
        h3.setSelectedItem("");

    }// GEN-LAST:event_clearbuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }// GEN-LAST:event_closebuttonActionPerformed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTable1FocusGained

    }// GEN-LAST:event_jTable1FocusGained

    private void h3ItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_h3ItemStateChanged

    }// GEN-LAST:event_h3ItemStateChanged

    private void allActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_allActionPerformed
        if (all.isSelected()) {
            h3.setEnabled(false);
        } else {
            h3.setEnabled(true);
        }

        // TODO add your handling code here:
    }// GEN-LAST:event_allActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox all;
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton excelbutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JComboBox h3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JLabel totl;
    // End of variables declaration//GEN-END:variables
}
