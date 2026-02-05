package barcodepack;

import Utils.CompanySettingUtil;
import Utils.ItemUtil;
import Utils.ColorConstants;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.selrom.db.DataUtil;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import menupack.menu_form;
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public class barcode_print extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    ResultSet r;
    AutoCompleteSupport support, support1, support2, support3;
    menu_form me = new menu_form();
    String drive = "";
    String folder = Utils.AppConfig.getAppPath();
    int hmany = me.getHmany();

    final void button_short() {
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        savebutton.setText("<html><b>Save & Print</b><br>(Alt+S)</h6><html>");
        titlelablel.setText("<html><u>Barcode Printing</u></html>");
        setTitle("Barcode Printing");
        this.setSize(1021, 648);
        javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            this.setFrameIcon(icon);
        }
    }

    final void load_list_table() {
        s2.addColumn("Barcode");
        s2.addColumn("It.Code");
        s2.addColumn("It.Name");
        s2.addColumn("MRP");
        s2.addColumn("Retail Price");
        s2.addColumn("Wholesale Price");
        s2.addColumn("Print Name");
        jTable1.setModel(s2);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(314);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(140);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(140);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(140);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(200);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    final void get_grn_no() {
        try {
            int count = 0;
            String query = "select grn from purchase";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select grn from purchase";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support = AutoCompleteSupport.install(h1, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    final void get_barcode_no() {
        try {
            int count = 0;
            String query = "select distinct barcode from item";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select distinct barcode from item";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support1 = AutoCompleteSupport.install(h2, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    final void get_item_code() {
        try {
            int count = 0;
            String query = "select distinct ino from item";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select distinct ino from item";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support2 = AutoCompleteSupport.install(h3, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    final void get_item_name() {
        try {
            int count = 0;
            String query = "select distinct iname from item";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select distinct iname from item";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support3 = AutoCompleteSupport.install(h4, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_details_using_grn_no() {
        try {
            String query;
            ArrayList barcode = new ArrayList();
            ArrayList ino = new ArrayList();
            ArrayList iname = new ArrayList();
            ArrayList mrp = new ArrayList();
            ArrayList rprice = new ArrayList();
            ArrayList wprice = new ArrayList();
            ArrayList tname = new ArrayList();
            query = "select barcode,ino,iname,mrp,rprice,wprice,quan,iname1 from purchase_items where grn='"
                    + h1.getSelectedItem() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                String barcode1 = r.getString(1);
                String ino1 = r.getString(2);
                String iname1 = r.getString(3);
                double mrp1 = r.getDouble(4);
                double rprice1 = r.getDouble(5);
                double wprice1 = r.getDouble(6);
                double quan = r.getDouble(7);
                String tname1 = r.getString(8);
                int quan1 = (int) quan;

                String mrp2 = String.format("%." + hmany + "f", mrp1);
                String rprice2 = String.format("%." + hmany + "f", rprice1);
                String wprice2 = String.format("%." + hmany + "f", wprice1);

                for (int j = 0; j < quan1; j++) {
                    barcode.add(barcode1);
                    ino.add(ino1);
                    iname.add(iname1);
                    mrp.add(mrp2);
                    rprice.add(rprice2);
                    wprice.add(wprice2);
                    tname.add(tname1);
                }
            }

            for (int i = 0; i < ino.size(); i++) {
                s2.addRow(new Object[] { barcode.get(i), ino.get(i), iname.get(i), mrp.get(i), rprice.get(i),
                        wprice.get(i), tname.get(i) });
            }
            totl.setText(" Total Records: " + jTable1.getRowCount());
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void load_default_data(String query, String quan) {
        try {
            ArrayList barcode = new ArrayList();
            ArrayList ino = new ArrayList();
            ArrayList iname = new ArrayList();
            ArrayList mrp = new ArrayList();
            ArrayList rprice = new ArrayList();
            ArrayList wprice = new ArrayList();
            ArrayList tname = new ArrayList();
            r = util.doQuery(query);
            while (r.next()) {
                String barcode1 = r.getString(1);
                String ino1 = r.getString(2);
                String iname1 = r.getString(3);
                double mrp1 = r.getDouble(4);
                double rprice1 = r.getDouble(5);
                double wprice1 = r.getDouble(6);
                String tname1 = r.getString(7);

                String mrp2 = String.format("%." + hmany + "f", mrp1);
                String rprice2 = String.format("%." + hmany + "f", rprice1);
                String wprice2 = String.format("%." + hmany + "f", wprice1);
                int quan1 = Integer.parseInt(quan);
                for (int j = 0; j < quan1; j++) {
                    barcode.add(barcode1);
                    ino.add(ino1);
                    iname.add(iname1);
                    mrp.add(mrp2);
                    rprice.add(rprice2);
                    wprice.add(wprice2);
                    tname.add(tname1);
                }
            }
            for (int i = 0; i < ino.size(); i++) {
                s2.addRow(new Object[] { barcode.get(i), ino.get(i), iname.get(i), mrp.get(i), rprice.get(i),
                        wprice.get(i), tname.get(i) });
            }
            totl.setText(" Total Records: " + jTable1.getRowCount());
        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    void save() {
        try {

            ArrayList query_batch = new ArrayList();
            List<Integer> items = new ArrayList<>();
            query_batch.add("delete from barcode");
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String barcode = jTable1.getValueAt(i, 0).toString();
                int ino = Integer.parseInt(jTable1.getValueAt(i, 1).toString());
                String iname = jTable1.getValueAt(i, 2).toString();
                String mrp = jTable1.getValueAt(i, 3).toString();
                String rprice = jTable1.getValueAt(i, 4).toString();
                String wprice = jTable1.getValueAt(i, 5).toString();
                String iname1 = jTable1.getValueAt(i, 6).toString();

                items.add(ino);

                // Add query to batch list (ALWAYS sanitize or use PreparedStatement in real
                // apps)
                String query = String.format(
                        "INSERT INTO barcode (barcode, ino, iname, mrp, retail_price, wholesale_price, iname1) "
                                + "VALUES ('%s', %d, '%s', '%s', '%s', '%s', '%s')",
                        barcode, ino, iname, mrp, rprice, wprice, iname1);

                query_batch.add(query);
            }

            int count = util.doManipulation_Batch(query_batch);
            if (count > 0) {
                printBarode(items);
                clear();
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void printBarode(List<Integer> itemNo) {
        barcodepack.jasper.JasperBarcodePrinter.print(ItemUtil.getItemsByItemNos(itemNo));
    }

    void clear() {
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        totl.setText("");
        h1.setSelectedItem("");
        h2.setSelectedItem("");
        h3.setSelectedItem("");
        h4.setSelectedItem("");
    }

    public barcode_print(DataUtil util) {
        initComponents();

        this.util = util;
        button_short();
        load_list_table();
        get_grn_no();
        get_barcode_no();
        get_item_code();
        get_item_name();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        generatebutton = new javax.swing.JButton();
        savebutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        h1 = new javax.swing.JComboBox();
        totl = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        h2 = new javax.swing.JComboBox();
        generatebutton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        h3 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        h4 = new javax.swing.JComboBox();
        generatebutton2 = new javax.swing.JButton();
        generatebutton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Barcode Printing");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 400, 30);

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
        jTable1.setRowHeight(25);
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
        jScrollPane1.setBounds(0, 132, 1000, 430);

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
        generatebutton.setBounds(260, 50, 140, 30);

        savebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        savebutton.setIcon(ColorConstants.loadIcon("/icons/print45.png")); // NOI18N
        savebutton.setMnemonic('s');
        savebutton.setText("Save");
        savebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(savebutton);
        savebutton.setBounds(410, 560, 180, 50);

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
        jLabel5.setText("Barcode");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(410, 50, 60, 30);

        h1.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        h1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        h1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                h1ItemStateChanged(evt);
            }
        });
        getContentPane().add(h1);
        h1.setBounds(70, 50, 190, 30);

        totl.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(totl);
        totl.setBounds(0, 560, 410, 30);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("It.Name");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(410, 80, 60, 30);

        h2.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        h2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        h2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                h2ItemStateChanged(evt);
            }
        });
        getContentPane().add(h2);
        h2.setBounds(470, 50, 390, 30);

        generatebutton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton1.setIcon(ColorConstants.loadIcon("/icons/generate30.png")); // NOI18N
        generatebutton1.setMnemonic('g');
        generatebutton1.setText("Generate");
        generatebutton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebutton1ActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton1);
        generatebutton1.setBounds(860, 50, 140, 30);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("GRN No");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(10, 50, 60, 30);

        h3.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        h3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        h3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                h3ItemStateChanged(evt);
            }
        });
        getContentPane().add(h3);
        h3.setBounds(70, 80, 190, 30);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("It.Code");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(10, 80, 60, 30);

        h4.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        h4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        h4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                h4ItemStateChanged(evt);
            }
        });
        getContentPane().add(h4);
        h4.setBounds(470, 80, 390, 30);

        generatebutton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton2.setIcon(ColorConstants.loadIcon("/icons/generate30.png")); // NOI18N
        generatebutton2.setMnemonic('g');
        generatebutton2.setText("Generate");
        generatebutton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebutton2ActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton2);
        generatebutton2.setBounds(260, 80, 140, 30);

        generatebutton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton3.setIcon(ColorConstants.loadIcon("/icons/generate30.png")); // NOI18N
        generatebutton3.setMnemonic('g');
        generatebutton3.setText("Generate");
        generatebutton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebutton3ActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton3);
        generatebutton3.setBounds(860, 80, 140, 30);

        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setIcon(ColorConstants.loadIcon("/icons/setting45.png")); // NOI18N
        jButton1.setText("Settings");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(590, 560, 160, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable1MouseClicked

    }// GEN-LAST:event_jTable1MouseClicked

    private void generatebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_generatebuttonActionPerformed
        get_details_using_grn_no();

    }// GEN-LAST:event_generatebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_savebuttonActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Sorry, No Records Were Found!", "Oops", JOptionPane.ERROR_MESSAGE);
            return;
        }
        save();

    }// GEN-LAST:event_savebuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbuttonActionPerformed
        clear();

    }// GEN-LAST:event_clearbuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }// GEN-LAST:event_closebuttonActionPerformed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTable1FocusGained

    }// GEN-LAST:event_jTable1FocusGained

    private void h1ItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_h1ItemStateChanged

    }// GEN-LAST:event_h1ItemStateChanged

    private void h2ItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_h2ItemStateChanged
        // TODO add your handling code here:
    }// GEN-LAST:event_h2ItemStateChanged

    private void generatebutton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_generatebutton1ActionPerformed
        try {
            String quan = JOptionPane.showInputDialog(this, "How Many Lables ?", "Nos", JOptionPane.PLAIN_MESSAGE);
            if (quan == null || "".equals(quan)) {
                JOptionPane.showMessageDialog(this, "Invalid Quantity!", "Invalid", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String query = "select barcode,ino,iname,mrp,rprice,wprice,iname1 from item where barcode='"
                    + h2.getSelectedItem() + "' order by barcode limit 1";
            load_default_data(query, quan);
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_generatebutton1ActionPerformed

    private void h3ItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_h3ItemStateChanged
        // TODO add your handling code here:
    }// GEN-LAST:event_h3ItemStateChanged

    private void h4ItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_h4ItemStateChanged
        // TODO add your handling code here:
    }// GEN-LAST:event_h4ItemStateChanged

    private void generatebutton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_generatebutton2ActionPerformed
        try {
            String quan = JOptionPane.showInputDialog(this, "How Many Lables ?", "Nos", JOptionPane.PLAIN_MESSAGE);
            if (quan == null || "".equals(quan)) {
                JOptionPane.showMessageDialog(this, "Invalid Quantity!", "Invalid", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String query = "select barcode,ino,iname,mrp,rprice,wprice,iname1 from item where ino='"
                    + h3.getSelectedItem() + "' order by barcode";
            load_default_data(query, quan);
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_generatebutton2ActionPerformed

    private void generatebutton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_generatebutton3ActionPerformed
        try {
            try {
                String selectedName = h4.getSelectedItem() != null ? h4.getSelectedItem().toString() : "";
                String query = "SELECT ino, iname, barcode,"
                        + (CompanySettingUtil.getInstance().isDisplayBatch() ? "batch" : "size")
                        + " FROM item WHERE iname = '" + selectedName + "'";
                ResultSet set = util.doQuery(query);

                List<Object[]> rows = new ArrayList<>();
                while (set.next()) {
                    rows.add(new Object[] {
                            set.getString("ino"),
                            set.getString("iname"),
                            set.getString("barcode"),
                            set.getString(CompanySettingUtil.getInstance().isDisplayBatch() ? "batch" : "size")
                    });
                }

                if (rows.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No Records Were Found!", "Invalid", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (rows.size() == 1) {
                    // Only one record found, directly view it
                    loadAndPrint(rows.get(0)[0].toString());
                } else {
                    // Multiple records found, show selection dialog
                    String selectedIno = showSelectionDialog(rows);
                    if (selectedIno != null) {
                        loadAndPrint(selectedIno);
                    } else {
                        // User cancelled or no selection
                        JOptionPane.showMessageDialog(this, "No item selected.", "Cancelled",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
            }

        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_generatebutton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        Container parent = getTopLevelAncestor();
        new barcodepack.jasper.BarcodeTemplateManager((parent instanceof Window) ? (Window) parent : null)
                .setVisible(true);
    }// GEN-LAST:event_jButton1ActionPerformed

    private String showSelectionDialog(List<Object[]> data) {
        // Column names for the table
        String[] columns = { "Item No", "Item Name", "Barcode",
                CompanySettingUtil.getInstance().isDisplayBatch() ? "Batch" : "Size" };

        // Convert List<Object[]> to Object[][]
        Object[][] tableData = data.toArray(new Object[0][]);

        // Create JTable
        JTable table = new JTable(tableData, columns);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        // Show dialog
        int option = JOptionPane.showConfirmDialog(this, scrollPane, "Select Item", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                // Return the ino of selected row
                return table.getValueAt(selectedRow, 0).toString();
            }
        }
        return null; // no selection or cancelled
    }

    private void loadAndPrint(String ino) {
        String quan = JOptionPane.showInputDialog(this, "How Many Lables ?", "Nos", JOptionPane.PLAIN_MESSAGE);
        if (quan == null || "".equals(quan)) {
            JOptionPane.showMessageDialog(this, "Invalid Quantity!", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String query = "select barcode,ino,iname,mrp,rprice,wprice,iname1 from item where ino='" + ino
                + "' order by iname limit 1";
        load_default_data(query, quan);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JButton generatebutton1;
    private javax.swing.JButton generatebutton2;
    private javax.swing.JButton generatebutton3;
    private javax.swing.JComboBox h1;
    private javax.swing.JComboBox h2;
    private javax.swing.JComboBox h3;
    private javax.swing.JComboBox h4;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JLabel totl;
    // End of variables declaration//GEN-END:variables
}
