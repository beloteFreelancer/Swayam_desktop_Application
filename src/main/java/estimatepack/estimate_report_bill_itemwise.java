package estimatepack;

import com.selrom.db.DataUtil;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
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
public final class estimate_report_bill_itemwise extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    sample2 s3 = new sample2();
    ResultSet r;

    final void button_short() {
        excelbutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Excel</b><br>(Alt+I)</h6><html>");
        generatebutton.setText("<html><b>Generate</b>  (Alt+G)</h6><html>");
        titlelablel.setText("<html><u>Bill Wise Items Estimate</u></html>");
        setTitle("Bill Wise Items Estimate");
        this.setSize(1017, 650);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
    }

    void load_list_table() {
        try {
            s2.addColumn("Bill No");
            s2.addColumn("Date");
            s2.addColumn("Qty");
            s2.addColumn("Rate");
            s2.addColumn("Amount");
            s2.addColumn("Cust_Id");
            s2.addColumn("Cust Name");
            s2.addColumn("Mobile No");
            jTable1.setModel(s2);
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
            DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
            dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
            jTable1.getColumnModel().getColumn(0).setCellRenderer(dtcr);
            jTable1.getColumnModel().getColumn(1).setCellRenderer(dtcr);
            jTable1.getColumnModel().getColumn(2).setCellRenderer(dtcr1);
            jTable1.getColumnModel().getColumn(3).setCellRenderer(dtcr1);
            jTable1.getColumnModel().getColumn(4).setCellRenderer(dtcr1);
            jTable1.getColumnModel().getColumn(5).setCellRenderer(dtcr);
            jTable1.getColumnModel().getColumn(6).setCellRenderer(dtcr);
            jTable1.getColumnModel().getColumn(7).setCellRenderer(dtcr);

            jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(120);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(90);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(110);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(280);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(150);
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void load_report(String dfrom, String dto) {
        try {
            PreparedStatement t;
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(h1.getText());
            String lk = (new SimpleDateFormat("yyyy/MM/dd").format(nm));
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText());
            String lk1 = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));
            boolean selva = false;
            String query;
            query = "select distinct a.billno,date_format(a.dat,'%d/%m/%Y'),quan,price,amount,a.cid,cname,mobile from estimate a,estimate_items b where a.dat between '" + lk + "' and '" + lk1 + "' and ino='" + h3.getText() + "' and a.billno=b.billno order by a.dat,a.billno";
            r = util.doQuery(query);
            while (r.next()) {
                s2.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6), r.getString(7), r.getString(8)});
                selva = true;
            }

            if (selva == true) {
                h1.setEnabled(false);
                h2.setEnabled(false);
                jCalendarButton1.setEnabled(false);
                jCalendarButton2.setEnabled(false);
                generatebutton.setEnabled(false);
                h3.setEnabled(false);
                h4.setEnabled(false);
            }
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_customer_name() {
        try {
            h4.setText("");
            String query = "select iname from item where ino='" + h3.getText() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                h4.setText(r.getString(1));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void load_iname_table() {
        try {
            s3.addColumn("It.Code");
            s3.addColumn("Item Name");
            s3.addColumn("Category");
            jTable3.setModel(s3);
            jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(150);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(400);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(300);
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable3.getTableHeader().setFont(new Font(Ta, Bold, size));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public estimate_report_bill_itemwise(DataUtil util) {
        initComponents();

        this.util = util;
        load_list_table();
        button_short();
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

        iname_list.setUndecorated(true);
        iname_list.getContentPane().setLayout(null);

        jScrollPane3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jScrollPane3FocusLost(evt);
            }
        });

        jTable3.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
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
        jScrollPane3.setBounds(0, 0, 760, 450);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete22.png"))); // NOI18N
        jButton4.setMnemonic('o');
        jButton4.setText("Close");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        iname_list.getContentPane().add(jButton4);
        jButton4.setBounds(620, 460, 140, 30);

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Bill Wise Items Estimate");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 420, 30);

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
        jLabel11.setText(" It.Code");
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
        h4.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
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
                f.write("Bill Wise Item Sales:\n");
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

    private void h3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_h3KeyPressed

        iname_list.requestFocus();
        jTable3.requestFocus();
        switch (evt.getKeyCode()) {

            case KeyEvent.VK_ESCAPE:
                h3.requestFocus();
                iname_list.dispose();
                break;
            case KeyEvent.VK_DOWN:
                if (s3.getRowCount() > 0) {
                    s3.getDataVector().removeAllElements();
                    s3.fireTableDataChanged();
                }
                try {
                    iname_list.requestFocus();
                    Point l = jLabel12.getLocationOnScreen();
                    iname_list.setLocation(l.x, l.y + jLabel12.getHeight());
                    iname_list.setSize(800, 528);
                    iname_list.setVisible(true);
                    String query = "select ino,iname,cat from item where iname like '" + h3.getText() + "%' order by ino limit 500";
                    r = util.doQuery(query);
                    while (r.next()) {
                        s3.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3)});
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                break;
        }
    }//GEN-LAST:event_h3KeyPressed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        if (jTable3.getRowCount() > 0) {
            h3.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
            h4.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 1).toString());

        }
        h3.requestFocus();
        iname_list.dispose();
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTable3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable3KeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable3.getRowCount() > 0) {
                h3.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
                h4.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 1).toString());

            }
            h3.requestFocus();
            iname_list.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            iname_list.dispose();
            h3.requestFocus();
        }
    }//GEN-LAST:event_jTable3KeyPressed

    private void jScrollPane3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane3FocusLost
        iname_list.dispose();
    }//GEN-LAST:event_jScrollPane3FocusLost

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        iname_list.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton excelbutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h2;
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h4;
    private javax.swing.JDialog iname_list;
    private javax.swing.JButton jButton4;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton1;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton2;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    private javax.swing.JLabel titlelablel;
    // End of variables declaration//GEN-END:variables
}
