package stockpack;

import com.selrom.db.DataUtil;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import menupack.menu_form;
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class stock_alter extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    sample2 s4 = new sample2();
    ResultSet r;
    String username = "", utype = "User";
    boolean selvagates = false;

    final void button_short() {
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        removebutton.setText("<html><b>Remove</b><br>(Alt+M)</h6><html>");
        viewbutton.setText("<html><b>View</b><br>(Alt+V)</h6><html>");
        titlelablel.setText("<html><u>Stock Alter</u></html>");

        setTitle("Stock Alter");
        this.setSize(1021, 648);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
        Date d = new Date();
        SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
        h2.setText(g.format(d));
        menupack.menu_form me = new menu_form();
        username = me.getUsername();
        utype = me.getUserType();
    }

    final void load_list_table() {
        s2.addColumn("It.Code");
        s2.addColumn("It.Name");
        s2.addColumn("Qty");
        s2.addColumn("Entry");
        s2.addColumn("Stock_Type");
        s2.addColumn("Category");
        s2.addColumn("Barcode");
        s2.addColumn("Prate");
        s2.addColumn("MRP");
        s2.addColumn("RPrice");
        s2.addColumn("WPrice");

        jTable1.setModel(s2);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(450);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(170);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(124);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(10).setPreferredWidth(0);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
    }

    void load_iname_table() {
        try {
            s4.addColumn("It.Code");
            s4.addColumn("Barcode");
            s4.addColumn("Item Name");
            s4.addColumn("Category");
            jTable3.setModel(s4);
            jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(314);
            jTable3.getColumnModel().getColumn(3).setPreferredWidth(270);
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable3.getTableHeader().setFont(new Font(Ta, Bold, size));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void get_item_details_using_item_no() {
        try {
            String query = "select ino,iname from item where barcode='" + h4.getText() + "' order by ino limit 1";
            r = util.doQuery(query);
            while (r.next()) {
                h4.setText(r.getString(1));
                h5.setText(r.getString(2));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void add_item() {
        try {
            if (h4.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Item Code ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }
            if (h5.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Item Code ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }
            if (h6.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Qty ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h6.requestFocus();
                return;
            }
            String entry = "purchase", stock_type = "new_stock";
            String query = "select entry from stock where ino='" + h4.getText() + "' order by ino limit 1";
            r = util.doQuery(query);
            while (r.next()) {
                entry = r.getString(1);
                stock_type = "old_stock";
            }
            String cat = ".", mrp = "" + 0, rprice = "" + 0, wprice = "" + 0, prate = "" + 0, barcode = ".";
            query = "select cat,mrp,rprice,wprice,prate,barcode from item where ino='" + h4.getText() + "' order by ino limit 1";
            r = util.doQuery(query);
            while (r.next()) {
                cat = r.getString(1);
                mrp = r.getString(2);
                rprice = r.getString(3);
                wprice = r.getString(4);
                prate = r.getString(5);
                barcode = r.getString(6);
            }
            s2.addRow(new Object[]{h4.getText(), h5.getText(), h6.getText(), entry, stock_type, cat, barcode, prate, mrp, rprice, wprice});
            clear_filds();
            calculate();
            h4.requestFocus();
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void clear_filds() {
        h4.setText("");
        h5.setText("");
        h6.setText("");
    }

    void calculate() {
        h7.setText(jTable1.getRowCount() + "");
        double nquan = 0;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            nquan = nquan + Double.parseDouble(jTable1.getValueAt(i, 2).toString());
        }
        h8.setText("" + nquan);
    }

    void get_sno() {
        try {
            int sno = 1;
            String query = "select max(sno) from stock_alter";
            r = util.doQuery(query);
            boolean selva = false;
            while (r.next()) {
                selva = true;
                sno = r.getInt(1);
            }
            if (selva == true) {
                sno = sno + 1;
            }
            h1.setText("" + sno);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void save() {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found to Save!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (utype.equalsIgnoreCase("User")) {
                JOptionPane.showMessageDialog(this, "Login as 'Administrator' to Alter!", "Permission Restricted", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int aa = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (aa == JOptionPane.NO_OPTION) {
                return;
            }
            get_sno();
            String sno = h1.getText();
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText());
            String date = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));

            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);

            String type = h3.getSelectedItem().toString();
            String items = h7.getText();
            String quans = h8.getText();

            ArrayList query_list = new ArrayList();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String ino = jTable1.getValueAt(i, 0).toString();
                String iname = jTable1.getValueAt(i, 1).toString();
                String quan = jTable1.getValueAt(i, 2).toString();
                String entry = jTable1.getValueAt(i, 3).toString();
                String stock_type = jTable1.getValueAt(i, 4).toString();

                String cat = jTable1.getValueAt(i, 5).toString();
                String barcode = jTable1.getValueAt(i, 6).toString();
                String prate = jTable1.getValueAt(i, 7).toString();
                String mrp = jTable1.getValueAt(i, 8).toString();
                String rprice = jTable1.getValueAt(i, 9).toString();
                String wprice = jTable1.getValueAt(i, 10).toString();
                query_list.add("insert into stock_alter values ('" + sno + "','" + date + "','" + type + "','" + ino + "','" + iname + "','" + quan + "','" + entry + "','" + stock_type + "','" + items + "','" + quans + "','" + username + "','" + last + "')");

                if (stock_type.equalsIgnoreCase("new_stock") && type.equalsIgnoreCase("Add")) {
                    query_list.add("insert into stock values ('" + barcode + "','" + ino + "','" + iname + "','" + mrp + "','" + rprice + "','" + wprice + "','" + prate + "','" + quan + "','" + cat + "','" + entry + "')");
                }//new stock ends here
                else if (stock_type.equalsIgnoreCase("old_stock") && type.equalsIgnoreCase("Add")) {
                    query_list.add("update stock set quan=quan+" + quan + " where ino='" + ino + "' and iname='" + iname + "' and entry='" + entry + "' ");
                } else if (stock_type.equalsIgnoreCase("old_stock") && type.equalsIgnoreCase("Less")) {
                    query_list.add("update stock set quan=quan-" + quan + " where ino='" + ino + "' and iname='" + iname + "' and entry='" + entry + "' ");
                }
            }
            int a = util.doManipulation_Batch(query_list);
            if (a > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Saved Successfully</h1></html>", "Saved", JOptionPane.PLAIN_MESSAGE);
                form_clear();
            } else {
                JOptionPane.showMessageDialog(this, "Try Again...", "Oops", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    void view(String sno) {
        try {
            String query = "select sno from stock_alter where sno='" + sno + "'";
            ResultSet set1 = util.doQuery(query);
            boolean selva = false;
            while (set1.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            } else {
                savebutton.setVisible(false);
                viewbutton.setVisible(false);

                selvagates = true;
                if (s2.getRowCount() > 0) {
                    s2.getDataVector().removeAllElements();
                    s2.fireTableDataChanged();
                }
                query = "select sno,date_format(dat,'%d/%m/%Y'),etype,items,quans from stock_alter where sno='" + sno + "'";
                set1 = util.doQuery(query);
                while (set1.next()) {
                    h1.setText(set1.getString(1));
                    h2.setText(set1.getString(2));
                    h3.setSelectedItem(set1.getString(3));
                    h7.setText(set1.getString(4));
                    h8.setText(set1.getString(5));
                }
                query = "select ino,iname,quan,entry,stock_type from stock_alter where sno='" + sno + "'";
                set1 = util.doQuery(query);
                while (set1.next()) {
                    s2.addRow(new Object[]{set1.getString(1), set1.getString(2), set1.getString(3), set1.getString(4), set1.getString(5)});
                }
            }//if selva true ends
        } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void form_clear() {
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        h1.setText("--");
        h7.setText("");
        h8.setText("");
        h3.setSelectedIndex(0);
        savebutton.setVisible(true);
        viewbutton.setVisible(true);
    }

    public stock_alter(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();
        load_list_table();
        load_iname_table();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        iname_list = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        titlelablel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        cnamel = new javax.swing.JLabel();
        viewbutton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        h1 = new javax.swing.JTextField();
        h2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        h3 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        h4 = new javax.swing.JTextField();
        h5 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        h6 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        savebutton = new javax.swing.JButton();
        removebutton = new javax.swing.JButton();
        h7 = new javax.swing.JTextField();
        h8 = new javax.swing.JTextField();

        iname_list.setUndecorated(true);
        iname_list.getContentPane().setLayout(null);

        jScrollPane3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jScrollPane3FocusLost(evt);
            }
        });

        jTable3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable3.setRowHeight(25);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable3KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        iname_list.getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(0, 0, 840, 400);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete22.png"))); // NOI18N
        jButton4.setMnemonic('o');
        jButton4.setText("Close");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        iname_list.getContentPane().add(jButton4);
        jButton4.setBounds(700, 400, 140, 30);

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Stock Alter");
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
        jScrollPane1.setBounds(0, 162, 1000, 360);

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

        cnamel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cnamel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(cnamel);
        cnamel.setBounds(430, 0, 560, 30);

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

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Type");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(10, 70, 60, 30);

        h1.setEditable(false);
        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setText("--");
        h1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h1FocusGained(evt);
            }
        });
        getContentPane().add(h1);
        h1.setBounds(70, 40, 180, 30);

        h2.setEditable(false);
        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(330, 40, 120, 30);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Entry No");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(10, 40, 60, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Add", "Less" }));
        h3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h3KeyPressed(evt);
            }
        });
        getContentPane().add(h3);
        h3.setBounds(70, 70, 380, 30);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Qty");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(790, 120, 30, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h4ActionPerformed(evt);
            }
        });
        h4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h4KeyPressed(evt);
            }
        });
        getContentPane().add(h4);
        h4.setBounds(70, 120, 180, 30);

        h5.setEditable(false);
        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h5);
        h5.setBounds(250, 120, 530, 30);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText(" Qty");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(200, 530, 40, 30);

        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h6ActionPerformed(evt);
            }
        });
        h6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h6KeyPressed(evt);
            }
        });
        getContentPane().add(h6);
        h6.setBounds(820, 120, 180, 30);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText(" Entry Date");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(250, 40, 80, 30);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("It.Code");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(10, 120, 60, 30);

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText(" Total Items");
        getContentPane().add(jLabel18);
        jLabel18.setBounds(0, 530, 110, 30);

        savebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        savebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save45.png"))); // NOI18N
        savebutton.setMnemonic('s');
        savebutton.setText("Save");
        savebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(savebutton);
        savebutton.setBounds(360, 560, 130, 50);

        removebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        removebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/remove45.png"))); // NOI18N
        removebutton.setMnemonic('m');
        removebutton.setText("Remove");
        removebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(removebutton);
        removebutton.setBounds(620, 560, 130, 50);

        h7.setEditable(false);
        h7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h7);
        h7.setBounds(80, 530, 120, 30);

        h8.setEditable(false);
        h8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h8);
        h8.setBounds(230, 530, 120, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

    }//GEN-LAST:event_jTable1MouseClicked

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        form_clear();

    }//GEN-LAST:event_clearbuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained

    }//GEN-LAST:event_jTable1FocusGained

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewbuttonActionPerformed
        String grn = JOptionPane.showInputDialog(this, "Enter Entry No ?", "EntryNo", JOptionPane.PLAIN_MESSAGE);
        if ("".equals(grn) || grn == null) {
            JOptionPane.showMessageDialog(this, "Invalid Entry No!", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        view(grn);
    }//GEN-LAST:event_viewbuttonActionPerformed

    private void h1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h1FocusGained
        h3.requestFocus();
    }//GEN-LAST:event_h1FocusGained

    private void h4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h4ActionPerformed
        if (h4.getText().equals("") && s2.getRowCount() > 0) {
            savebutton.requestFocus();
        } else {
            if (h5.getText().equals("")) {
                get_item_details_using_item_no();
            }
            h6.requestFocus();
        }
    }//GEN-LAST:event_h4ActionPerformed

    private void h4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_h4KeyPressed

        iname_list.requestFocus();
        jTable3.requestFocus();
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                h4.requestFocus();
                iname_list.dispose();
                break;
            case KeyEvent.VK_DOWN:
                if (s4.getRowCount() > 0) {
                    s4.getDataVector().removeAllElements();
                    s4.fireTableDataChanged();
                }
                try {
                    iname_list.requestFocus();
                    Point l = jLabel17.getLocationOnScreen();
                    iname_list.setLocation(l.x, l.y + jLabel17.getHeight());
                    iname_list.setSize(840, 432);
                    iname_list.setVisible(true);
                    String query = "select ino,barcode,iname,cat from item where iname like '" + h4.getText() + "%' order by ino limit 500";
                    r = util.doQuery(query);
                    while (r.next()) {
                        s4.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), r.getString(4)});
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                break;
        }

    }//GEN-LAST:event_h4KeyPressed

    private void h6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h6ActionPerformed
        add_item();
    }//GEN-LAST:event_h6ActionPerformed

    private void h6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_h6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_h6KeyPressed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        if (jTable3.getRowCount() > 0) {
            h4.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
        }
        get_item_details_using_item_no();
        h6.requestFocus();
        iname_list.dispose();
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTable3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable3KeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable3.getRowCount() > 0) {
                h4.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
            }
            get_item_details_using_item_no();
            h6.requestFocus();
            iname_list.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            iname_list.dispose();
            h4.requestFocus();
        }
    }//GEN-LAST:event_jTable3KeyPressed

    private void jScrollPane3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane3FocusLost
        iname_list.dispose();
    }//GEN-LAST:event_jScrollPane3FocusLost

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        iname_list.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        save();
    }//GEN-LAST:event_savebuttonActionPerformed

    private void removebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removebuttonActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int[] row_indexes = jTable1.getSelectedRows();
        for (int i = 0; i < row_indexes.length; i++) {
            s2.removeRow(jTable1.getSelectedRow());
        }
        calculate();

    }//GEN-LAST:event_removebuttonActionPerformed

    private void h3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_h3KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            h4.requestFocus();
        }
    }//GEN-LAST:event_h3KeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JLabel cnamel;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h2;
    private javax.swing.JComboBox<String> h3;
    private javax.swing.JTextField h4;
    private javax.swing.JTextField h5;
    private javax.swing.JTextField h6;
    private javax.swing.JTextField h7;
    private javax.swing.JTextField h8;
    private javax.swing.JDialog iname_list;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    private javax.swing.JButton removebutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
