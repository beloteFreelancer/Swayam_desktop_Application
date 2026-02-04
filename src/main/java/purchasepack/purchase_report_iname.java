package purchasepack;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
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
public class purchase_report_iname extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    ResultSet r;
    AutoCompleteSupport support;
    int hmany = 2;

    final void button_short() {
        excelbutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Excel</b><br>(Alt+I)</h6><html>");
        viewbutton.setText("<html><b>View</b><br>(Alt+V)</h6><html>");
        generatebutton.setText("<html><b>Generate</b>  (Alt+G)</h6><html>");
        titlelablel.setText("<html><u>Item Wise Purchase Details</u></html>");
        menu_form me = new menu_form();
        hmany = me.getHmany();
        setTitle(titlelablel.getText());
        this.setSize(1021, 648);
        java.net.URL imgUrl = getClass().getResource("/images/icon.png");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            this.setFrameIcon(icon);
        }
    }

    final void load_list_table() {
        s2.addColumn("GRN No");
        s2.addColumn("Supplier");
        s2.addColumn("Bill No");
        s2.addColumn("Bill Date");
        s2.addColumn("Qty");
        s2.addColumn("Rate");
        jTable1.setModel(s2);
        DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
        dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
        jTable1.getColumnModel().getColumn(4).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(5).setCellRenderer(dtcr1);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(384);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(120);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    final void get_cid() {
        try {
            int count = 0;
            String query = "select distinct iname from purchase_items";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select distinct iname from purchase_items";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support = AutoCompleteSupport.install(
                    h3, GlazedLists.eventListOf(f));
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_cname() {
        try {
            cnamel.setText("");
            String query = "select distinct ino from purchase_items where iname='" + h3.getSelectedItem() + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                cnamel.setText("It.Code: " + r.getString(1));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    void load_report(String dfrom, String dto, String iname) {
        try {
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(dfrom);
            String lk = (new SimpleDateFormat("yyyy/MM/dd").format(nm));
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(dto);
            String lk1 = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));
            double rate;
            boolean selva = false;
            String query;
            query = "select distinct grn,cname,billno,date_format(bdate,'%d/%m/%Y'),quan,price from purchase_items where bdate between ? and ? and iname=? order by bdate,grn";
            Connection conn = util.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, lk);
            ps.setString(2, lk1);
            ps.setString(3, iname);
            r = ps.executeQuery();
            while (r.next()) {
                rate = r.getDouble(6);
                String rate1 = String.format("%." + hmany + "f", rate);
                s2.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), rate1});
                selva = true;
            }

            double quan = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                quan = quan + Double.parseDouble(jTable1.getValueAt(i, 4).toString());
            }
            String quan2 = String.format("%." + hmany + "f", quan);
            if (selva == true) {
                s2.addRow(new Object[]{"", "", "", "", "", ""});
                s2.addRow(new Object[]{"", "", "", "TOTAL:" + (jTable1.getRowCount() - 1), "" + quan2, ""});

                h1.setEnabled(false);
                h2.setEnabled(false);
                jCalendarButton1.setEnabled(false);
                jCalendarButton2.setEnabled(false);
                generatebutton.setEnabled(false);
                h3.setEnabled(false);
            }

        } catch (NumberFormatException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public purchase_report_iname(DataUtil util) {
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
        viewbutton = new javax.swing.JButton();
        cnamel = new javax.swing.JLabel();
        h3 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Item Wise Purchase Details");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 300, 30);

        jTable1.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
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
        generatebutton.setBounds(810, 50, 190, 30);

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
        viewbutton.setBounds(490, 560, 130, 50);

        cnamel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cnamel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(cnamel);
        cnamel.setBounds(430, 0, 560, 30);

        h3.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        h3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        h3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                h3ItemStateChanged(evt);
            }
        });
        getContentPane().add(h3);
        h3.setBounds(426, 50, 380, 30);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText(" It.Name");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(370, 50, 60, 30);

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
        load_report(h1.getText(), h2.getText(), h3.getSelectedItem().toString());

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
            String file_name = "Item_Wise_Purchase_Details.xlsx";
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
        generatebutton.setEnabled(true);
        h3.setEnabled(true);
        h3.setSelectedItem("");
        cnamel.setText("");

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

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewbuttonActionPerformed
        try {
            purchase_entry oe = new purchase_entry(util);
            JDesktopPane desktop_pane = getDesktopPane();
            desktop_pane.add(oe);
            String sno = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
            oe.view(sno);
            oe.setVisible(true);

            Dimension desktopSize = desktop_pane.getSize();
            Dimension jInternalFrameSize = oe.getSize();
            oe.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }//GEN-LAST:event_viewbuttonActionPerformed

    private void h3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_h3ItemStateChanged
        get_cname();
        // TODO add your handling code here:
    }//GEN-LAST:event_h3ItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JLabel cnamel;
    private javax.swing.JButton excelbutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h2;
    private javax.swing.JComboBox h3;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton1;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
