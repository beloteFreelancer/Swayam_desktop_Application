package estimatepack;

import com.selrom.db.DataUtil;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import menupack.menu_form;
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class estimate_report_cust extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    sample2 s3 = new sample2();
    ResultSet r;
    int hmany = 2;

    final void button_short() {
        excelbutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Excel</b><br>(Alt+I)</h6><html>");
        generatebutton.setText("<html><b>Generate</b>  (Alt+G)</h6><html>");
        titlelablel.setText("<html><u>Customer Wise Estimate Report</u></html>");
        setTitle("Customer Wise Estimate Report");
        this.setSize(1017, 650);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
        menu_form me = new menu_form();
        hmany = me.getHmany();
    }

    public final void load_list_table() {
        s2.addColumn("Bill No");
        s2.addColumn("Date");
        s2.addColumn("Time");
        s2.addColumn("Location");
        s2.addColumn("Terminal");
        s2.addColumn("Cashier");
        s2.addColumn("Total Items");
        s2.addColumn("Total Qty");
        s2.addColumn("Sub Total");
        s2.addColumn("Dis%");
        s2.addColumn("Dis Amt");
        s2.addColumn("Gross Amt");
        s2.addColumn("Tax Amt");
        s2.addColumn("Other Charges");
        s2.addColumn("Net Amt");
        s2.addColumn("Pay Mode");
        s2.addColumn("Tender");
        s2.addColumn("Return");
        s2.addColumn("Price Type");
        s2.addColumn("Cust_Id");
        s2.addColumn("Cust_Name");
        s2.addColumn("Mobile No");
        s2.addColumn("Last Modified at");
        jTable1.setModel(s2);
        DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
        dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
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
        jTable1.getColumnModel().getColumn(17).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(18).setCellRenderer(dtcr1);

        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(10).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(11).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(12).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(13).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(14).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(15).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(16).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(17).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(18).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(19).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(20).setPreferredWidth(250);
        jTable1.getColumnModel().getColumn(21).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(22).setPreferredWidth(180);

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
            query = "select billno,date_format(dat,'%d/%m/%Y'),tim,location,terminal,cashier,items,quans,sub,disp,disamt,gross,taxamt,addamt,net,pby,paid,bal,price_type,cid,cname,mobile,last from estimate where dat between '" + lk + "' and '" + lk1 + "' and cid='" + h3.getText() + "'  order by dat,billno";
            r = util.doQuery(query);
            while (r.next()) {
                String sub = String.format("%." + hmany + "f", r.getDouble(9));
                String disamt = String.format("%." + hmany + "f", r.getDouble(11));
                String gross = String.format("%." + hmany + "f", r.getDouble(12));
                String taxamt = String.format("%." + hmany + "f", r.getDouble(13));
                String other = String.format("%." + hmany + "f", r.getDouble(14));
                String net = String.format("%." + hmany + "f", r.getDouble(15));
                String paid = String.format("%." + hmany + "f", r.getDouble(17));
                String bal = String.format("%." + hmany + "f", r.getDouble(18));

                s2.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6), r.getString(7), r.getString(8), sub, r.getString(10), disamt, gross, taxamt, other, net, r.getString(16), paid, bal, r.getString(19), "  " + r.getString(20), r.getString(21), r.getString(22), r.getString(23)});
                selva = true;
            }
            double sub = 0, disamt = 0, net = 0, gross = 0, taxamt = 0, other = 0, paid = 0, bal = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                sub = sub + Double.parseDouble(jTable1.getValueAt(i, 8).toString());
                disamt = disamt + Double.parseDouble(jTable1.getValueAt(i, 10).toString());
                gross = gross + Double.parseDouble(jTable1.getValueAt(i, 11).toString());
                taxamt = taxamt + Double.parseDouble(jTable1.getValueAt(i, 12).toString());
                other = other + Double.parseDouble(jTable1.getValueAt(i, 13).toString());
                net = net + Double.parseDouble(jTable1.getValueAt(i, 14).toString());

                paid = paid + Double.parseDouble(jTable1.getValueAt(i, 16).toString());
                bal = bal + Double.parseDouble(jTable1.getValueAt(i, 17).toString());
            }

            String sub1 = String.format("%." + hmany + "f", sub);
            String disamt1 = String.format("%." + hmany + "f", disamt);
            String gross1 = String.format("%." + hmany + "f", gross);
            String taxamt1 = String.format("%." + hmany + "f", taxamt);
            String net1 = String.format("%." + hmany + "f", net);
            String other1 = String.format("%." + hmany + "f", other);
            String paid1 = String.format("%." + hmany + "f", paid);
            String bal1 = String.format("%." + hmany + "f", bal);

            if (selva == true) {
                s2.addRow(new Object[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""});
                s2.addRow(new Object[]{"", "", "TOTAL:" + (jTable1.getRowCount() - 1), "", "", "", "", "", "" + sub1, "", disamt1, gross1, taxamt1, other1, net1, "", paid1, bal1, "", "", "", "", ""});

                h1.setEnabled(false);
                h2.setEnabled(false);
                jCalendarButton1.setEnabled(false);
                jCalendarButton2.setEnabled(false);
                generatebutton.setEnabled(false);
                h3.setEnabled(false);
            }
        } catch (ClassNotFoundException | NumberFormatException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_customer_name() {
        try {
            h4.setText("");
            String query = "select cname from cust where cid='" + h3.getText() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                h4.setText(r.getString(1));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void load_customer_table() {
        try {
            s3.addColumn("Cust_Id");
            s3.addColumn("Type");
            s3.addColumn("Name");
            s3.addColumn("Customer_Card_No");
            s3.addColumn("Mobile No");
            s3.addColumn("Area");
            s3.addColumn("");
            jTable2.setModel(s3);
            jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(280);
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(180);
            jTable2.getColumnModel().getColumn(4).setPreferredWidth(120);
            jTable2.getColumnModel().getColumn(5).setPreferredWidth(204);
            jTable2.getColumnModel().getColumn(6).setPreferredWidth(100);
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable2.getTableHeader().setFont(new Font(Ta, Bold, size));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public estimate_report_cust(DataUtil util) {
        initComponents();

        this.util = util;
        load_list_table();
        button_short();
        load_customer_table();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cname_list = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        titlelablel = new javax.swing.JLabel();
        generatebutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        excelbutton = new javax.swing.JButton();
        h1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        h2 = new javax.swing.JTextField();
        jCalendarButton1 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jCalendarButton2 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        h3 = new javax.swing.JTextField();
        h4 = new javax.swing.JTextField();

        cname_list.setUndecorated(true);
        cname_list.getContentPane().setLayout(null);

        jScrollPane2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jScrollPane2FocusLost(evt);
            }
        });

        jTable2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.setRowHeight(25);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        cname_list.getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(0, 0, 1040, 480);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete22.png"))); // NOI18N
        jButton3.setMnemonic('o');
        jButton3.setText("Close");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        cname_list.getContentPane().add(jButton3);
        jButton3.setBounds(900, 480, 140, 30);

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Customer Wise Estimate Report");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 330, 30);

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
        generatebutton.setBounds(830, 40, 170, 30);

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
        jLabel11.setText(" Cust_Id");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(350, 40, 70, 30);

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
        excelbutton.setBounds(610, 560, 130, 50);

        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h1);
        h1.setBounds(80, 40, 90, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Date from");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(10, 40, 70, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(230, 40, 90, 30);

        jCalendarButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
        jCalendarButton1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton1PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton1);
        jCalendarButton1.setBounds(170, 40, 30, 30);

        jCalendarButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
        jCalendarButton2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton2PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton2);
        jCalendarButton2.setBounds(320, 40, 30, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("To");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(210, 40, 20, 30);

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

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h3ActionPerformed(evt);
            }
        });
        h3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h3KeyPressed(evt);
            }
        });
        getContentPane().add(h3);
        h3.setBounds(410, 40, 100, 30);

        h4.setEditable(false);
        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h4);
        h4.setBounds(511, 40, 320, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
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
                f.write("Customer Wise Estimate Report:\n");
                f.write("Date from: " + h1.getText() + "  To: " + h2.getText() + "\n");
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
        h1.setEnabled(true);
        h2.setEnabled(true);
        jCalendarButton1.setEnabled(true);
        jCalendarButton2.setEnabled(true);
        h1.setText("");
        h2.setText("");
        h3.setEnabled(true);
        h3.setText("");
        h4.setEnabled(true);
        h4.setText("");
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

    private void h3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h3ActionPerformed

        get_customer_name();
    }//GEN-LAST:event_h3ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

        if (jTable2.getRowCount() > 0) {
            h3.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
            h4.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 2).toString());
        }
        h3.requestFocus();
        cname_list.dispose();
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable2.getRowCount() > 0) {
                h3.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
                h4.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 2).toString());
            }
            h3.requestFocus();
            cname_list.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cname_list.dispose();
            h3.requestFocus();
        }
    }//GEN-LAST:event_jTable2KeyPressed

    private void jScrollPane2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane2FocusLost
        cname_list.dispose();
    }//GEN-LAST:event_jScrollPane2FocusLost

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        cname_list.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void h3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_h3KeyPressed

        cname_list.requestFocus();
        jTable2.requestFocus();
        switch (evt.getKeyCode()) {

            case KeyEvent.VK_ESCAPE:
                h3.requestFocus();
                cname_list.dispose();
                break;
            case KeyEvent.VK_DOWN:
                if (s3.getRowCount() > 0) {
                    s3.getDataVector().removeAllElements();
                    s3.fireTableDataChanged();
                }
                try {
                    cname_list.requestFocus();
                    Point l = jLabel12.getLocationOnScreen();
                    cname_list.setLocation(l.x, l.y + jLabel12.getHeight());
                    cname_list.setSize(1063, 528);
                    cname_list.setVisible(true);
                    String query;
                    query = "select cid,ctype,cname,cardno,mobile,city,scode from cust where cname like '" + h3.getText() + "%' order by cname limit 400";
                    r = util.doQuery(query);
                    while (r.next()) {
                        s3.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6), r.getString(7)});
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                break;
        }
    }//GEN-LAST:event_h3KeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JDialog cname_list;
    private javax.swing.JButton excelbutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h2;
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h4;
    private javax.swing.JButton jButton3;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton1;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton2;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel titlelablel;
    // End of variables declaration//GEN-END:variables
}
