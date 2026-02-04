package MISPack;

import com.selrom.db.DataUtil;
import com.selrom.utils.ExcelUtil;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import menupack.menu_form;
import menupack.sample2;
import smspack.manual_sms;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class top_customers_for_item extends javax.swing.JInternalFrame {

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
        smsbutton.setText("<html><b>SMS</b><br>(Alt+S)</h6><html>");
        removebutton.setText("<html><b>Remove</b><br>(Alt+M)</h6><html>");
        downloadbutton.setText("<html><b>Down.Text</b><br>(Alt+W)</h6><html>");
        generatebutton.setText("<html><b>Generate</b>  (Alt+G)</h6><html>");
        titlelablel.setText("<html><u>Customers Search Item Wise</u></html>");
        setTitle("Customers Search Item Wise");
        this.setSize(1017, 650);
        java.net.URL imgUrl = getClass().getResource("/images/icon.png");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            this.setFrameIcon(icon);
        }
        menu_form me = new menu_form();
        hmany = me.getHmany();
    }

    void load_list_table() {
        try {
            s2.addColumn("Cust_Id");
            s2.addColumn("Cust Name");
            s2.addColumn("Mobile No");
            s2.addColumn("Total Sales Qty");
            jTable1.setModel(s2);
            jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(494);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(200);
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void load_report(String dfrom, String dto) {
        try {
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(h1.getText());
            String lk = (new SimpleDateFormat("yyyy-MM-dd").format(nm));
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText());
            String lk1 = (new SimpleDateFormat("yyyy-MM-dd").format(nm1));
            boolean selva = false;
            String query;

            Connection conn = util.getConnection();
            PreparedStatement ps;

            if (all.isSelected()) {
                query = "select a.cid,cname,mobile,sum(quan) from sales_items a,sales b where a.dat between ? and ? and a.ino=? and a.billno=b.billno and a.cid=b.cid group by a.cid, cname, mobile order by sum(quan) desc";
                ps = conn.prepareStatement(query);
                ps.setString(1, lk);
                ps.setString(2, lk1);
                ps.setString(3, h3.getText());
            } else {
                query = "select a.cid,cname,mobile,sum(quan) from sales_items a,sales b where a.dat between ? and ? and a.ino=? and a.billno=b.billno and a.cid=b.cid group by a.cid, cname, mobile having sum(quan) >= ? order by sum(quan) desc";
                ps = conn.prepareStatement(query);
                ps.setString(1, lk);
                ps.setString(2, lk1);
                ps.setString(3, h3.getText());
                ps.setString(4, h5.getText());
            }

            r = ps.executeQuery();
            while (r.next()) {
                String quan2 = String.format("%." + hmany + "f", r.getDouble(4));
                String[] split3 = quan2.split("\\.");
                int what3 = Integer.parseInt(split3[1]);
                if (what3 <= 0) {
                    quan2 = split3[0];
                }
                s2.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), quan2});
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
                h5.setEnabled(false);
                all.setEnabled(false);
            }
        } catch (SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_customer_name() {
        try {
            h4.setText("");
            String query = "select iname from item where ino=?";
            Connection conn = util.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, h3.getText());

            r = ps.executeQuery();
            while (r.next()) {
                h4.setText(r.getString(1));
            }
        } catch (SQLException e) {
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

    public top_customers_for_item(DataUtil util) {
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
        jLabel14 = new javax.swing.JLabel();
        h5 = new javax.swing.JTextField();
        all = new javax.swing.JCheckBox();
        smsbutton = new javax.swing.JButton();
        removebutton = new javax.swing.JButton();
        downloadbutton = new javax.swing.JButton();

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
        titlelablel.setText("Customers Search Item Wise");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 320, 30);

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
        generatebutton.setBounds(770, 70, 230, 30);

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
        jLabel11.setText(" Min.Qty");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(350, 70, 60, 30);

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
        jScrollPane1.setBounds(0, 100, 1000, 460);

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
        h4.setBounds(511, 40, 490, 30);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText(" It.Code");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(350, 40, 60, 30);

        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h5ActionPerformed(evt);
            }
        });
        h5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h5KeyPressed(evt);
            }
        });
        getContentPane().add(h5);
        h5.setBounds(410, 70, 270, 30);

        all.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        all.setText("Select All");
        all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allActionPerformed(evt);
            }
        });
        getContentPane().add(all);
        all.setBounds(680, 70, 90, 30);

        smsbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        smsbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sms45.png"))); // NOI18N
        smsbutton.setMnemonic('s');
        smsbutton.setText("SMS");
        smsbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smsbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(smsbutton);
        smsbutton.setBounds(190, 560, 130, 50);

        removebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        removebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/remove45.png"))); // NOI18N
        removebutton.setMnemonic('m');
        removebutton.setText("Remove");
        removebutton.setToolTipText("Alt+M");
        removebutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        removebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(removebutton);
        removebutton.setBounds(470, 560, 140, 50);

        downloadbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        downloadbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/upload45.png"))); // NOI18N
        downloadbutton.setMnemonic('w');
        downloadbutton.setText("Down.Text");
        downloadbutton.setToolTipText("");
        downloadbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        downloadbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(downloadbutton);
        downloadbutton.setBounds(320, 560, 149, 50);

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
            String folder = mp.getFoler();
            String file_name = "ItemWiseCustomers.xlsx";
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
        h5.setEnabled(true);
        all.setEnabled(true);
        all.setSelected(false);
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
                    String query = "select ino,iname,cat from item where iname like ? order by ino limit 500";
                    Connection conn = util.getConnection();
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, h3.getText() + "%");
                    r = ps.executeQuery();
                    while (r.next()) {
                        s3.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3)});
                    }
                } catch (SQLException e) {
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

    private void h5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_h5ActionPerformed

    private void h5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_h5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_h5KeyPressed

    private void allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allActionPerformed
        if (all.isSelected()) {
            h5.setEnabled(false);
        } else {
            h5.setEnabled(true);
        }
    }//GEN-LAST:event_allActionPerformed

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
            mobile.add(jTable1.getValueAt(i, 2));
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

    private void removebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removebuttonActionPerformed

        int[] rows = jTable1.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            s2.removeRow(rows[i] - i);
        }
    }//GEN-LAST:event_removebuttonActionPerformed

    private void downloadbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadbuttonActionPerformed
        try {
            menupack.menu_form me = new menu_form();
            String drive = "";
            String folder = Utils.AppConfig.getAppPath();

            ExcelUtil.exportTableToExcel(jTable1, new File(folder + "/Drafts/SMS_List.xlsx"));

            Runtime rt = Runtime.getRuntime();
            Utils.AppConfig.openFile(folder + "/Drafts/SMS_List.xlsx");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_downloadbuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox all;
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton downloadbutton;
    private javax.swing.JButton excelbutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h2;
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h4;
    private javax.swing.JTextField h5;
    private javax.swing.JDialog iname_list;
    private javax.swing.JButton jButton4;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton1;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton2;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    private javax.swing.JButton removebutton;
    private javax.swing.JButton smsbutton;
    private javax.swing.JLabel titlelablel;
    // End of variables declaration//GEN-END:variables
}
