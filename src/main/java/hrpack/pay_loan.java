package hrpack;

import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.selrom.db.DataUtil;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public final class pay_loan extends javax.swing.JInternalFrame {

    DataUtil util = null;
    String username = "", utype = "";
    sample2 s3 = new sample2();
    sample2 s2 = new sample2();
    ResultSet r;
    int hmany = 2;
    AutoCompleteSupport support;

    void button_short() {
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        viewbutton.setText("<html><b>View</b><br>(Alt+V)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        prebutton.setText("<html><b>Last Entry</b><br>(Alt+R)</h6><html>");
        nextbutton.setText("<html><b>Next Entry</b><br>(Alt+N)</h6><html>");
        titlelablel.setText("<html><u>Loan Register</u></html>");

        setTitle("Loan Register");
        this.setSize(496, 409);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
        menu_form me = new menu_form();
        hmany = me.getHmany();
    }

    void load_patient_table() {
        try {
            s3.addColumn("Staff ID");
            s3.addColumn("Name");
            s3.addColumn("Designation");
            jTable2.setModel(s3);
            jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(300);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(150);
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable2.getTableHeader().setFont(new Font(Ta, Bold, size));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void get_defaults() {
        try {
            menupack.menu_form me = new menupack.menu_form();
            username = me.getUsername();
            utype = me.getUserType();
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            h2.setText(g.format(d));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void get_billno() {
        try {
            int sno = 1;
            String query = "select max(sno) from pay_loan";
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
            if (h3.getText().equals("") || h4.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Staff Id ?", "Staff Id", JOptionPane.ERROR_MESSAGE);
                h3.requestFocus();
                return;
            }
            if (h4.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Invalid Staff Details ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }
            if (h5.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Loan Amount ?", "Amount", JOptionPane.ERROR_MESSAGE);
                h5.requestFocus();
                return;
            }
            if (h6.getText().equals("")) {
                h6.setText(".");
            }

            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);
            if (h2.getText().equals("")) {
                h2.setText(g.format(d));
            }
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText());
            String date = (new SimpleDateFormat("yyyy/MM/dd").format(nm));
            get_billno();
            String sno = h1.getText();
            String cid = h3.getText();
            String cname = h4.getText();
            String amount = h5.getText();
            String remarks = h6.getText();
            double paid = 0;
            String status = "Active";
            boolean selva = false;
            String query = "select sno from pay_loan where sno='" + sno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                JOptionPane.showMessageDialog(this, "Entry No Already Exist!", "Already Exist", JOptionPane.ERROR_MESSAGE);
                h1.requestFocus();
                return;
            }
            boolean selva1 = false;
            query = "select sno from pay_loan where cid='" + cid + "' and status='" + status + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva1 = true;
            }
            if (selva1 == true) {
                JOptionPane.showMessageDialog(this, "Loan is Already Active for This Staff!", "Already Exist", JOptionPane.ERROR_MESSAGE);
                h1.requestFocus();
                return;
            }
            int aa = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (aa == JOptionPane.NO_OPTION) {
                return;
            }
            query = "insert into pay_loan values ('" + sno + "','" + date + "','" + cid + "','" + cname + "','" + amount + "','" + paid + "','" + remarks + "','" + status + "','" + last + "')";
            int a = util.doManipulation(query);
            if (a > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Saved Successfully</h1></html>", "Saved", JOptionPane.PLAIN_MESSAGE);
                clear();
            } else {
                JOptionPane.showMessageDialog(this, "Please try again...");
            }
        } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    void clear() {
        try {
            h1.setText("--");
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            h2.setText(g.format(d));
            h3.setText("");
            h4.setText("");
            h6.setText("");
            h5.setText("");
            h3.setEnabled(true);
            lastl.setText("");
            support.uninstall();
            deletebutton.setVisible(false);
            savebutton.setVisible(true);
            viewbutton.setVisible(true);
            h3.requestFocus();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void view(String sno) {
        try {
            String query = "select sno,date_format(dat,'%d/%m/%Y'),cid,cname,tot,remarks from pay_loan where sno='" + sno + "' ";
            r = util.doQuery(query);
            boolean selva = false;
            while (r.next()) {
                h1.setText(r.getString(1));
                h2.setText(r.getString(2));
                h3.setText(r.getString(3));
                h4.setText(r.getString(4));
                h6.setText(r.getString(6));
                double amount = r.getDouble(5);
                String amount2 = String.format("%." + hmany + "f", amount);
                h5.setText("" + amount2);
                selva = true;
            }
            if (selva == true) {
                savebutton.setVisible(false);
                viewbutton.setVisible(false);
                deletebutton.setVisible(true);
            } else if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void delete(String sno) {
        try {
            if (utype.equalsIgnoreCase("User")) {
                JOptionPane.showMessageDialog(this, "Login as 'Administrator' to Delete!", "Permission Restricted", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int as = JOptionPane.showConfirmDialog(this, "Want to Delete ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            boolean selva = false;
            String query = "select sno from pay_loan where sno='" + sno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            query = "delete from pay_loan where sno='" + sno + "'";
            int count = util.doManipulation(query);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Deleted Successfully", "Deleted", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_details() {
        try {
            String amount = "", date = "";
            String query = "select tot,date_format(dat,'%d/%m/%Y') from pay_loan where cid='" + h3.getText() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                amount = r.getString(1);
                date = r.getString(2);
            }
            lastl.setText(" Previous Loan Amount: " + amount + "    Date: " + date);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public pay_loan(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();
        h3.requestFocus();
        deletebutton.setVisible(false);
        load_patient_table();
        get_defaults();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cname_list = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton10 = new javax.swing.JButton();
        titlelablel = new javax.swing.JLabel();
        closebutton = new javax.swing.JButton();
        savebutton = new javax.swing.JButton();
        viewbutton = new javax.swing.JButton();
        deletebutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        h6 = new javax.swing.JTextField();
        h1 = new javax.swing.JTextField();
        h2 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        prebutton = new javax.swing.JButton();
        nextbutton = new javax.swing.JButton();
        lastl = new javax.swing.JLabel();
        h4 = new javax.swing.JTextField();
        h3 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        h5 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jCalendarButton2 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel30 = new javax.swing.JLabel();

        cname_list.setUndecorated(true);
        cname_list.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cname_listFocusLost(evt);
            }
        });
        cname_list.getContentPane().setLayout(null);

        jScrollPane4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jScrollPane4FocusLost(evt);
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
        jTable2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTable2FocusLost(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(jTable2);

        cname_list.getContentPane().add(jScrollPane4);
        jScrollPane4.setBounds(0, 0, 540, 270);

        jButton10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close45.png"))); // NOI18N
        jButton10.setMnemonic('o');
        jButton10.setText("Close Search");
        jButton10.setToolTipText("Alt+O");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        cname_list.getContentPane().add(jButton10);
        jButton10.setBounds(360, 270, 180, 40);

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Loan Register");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 240, 30);

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
        closebutton.setBounds(330, 280, 130, 50);

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
        savebutton.setBounds(70, 230, 130, 50);

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
        viewbutton.setBounds(70, 280, 130, 50);

        deletebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        deletebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete45.png"))); // NOI18N
        deletebutton.setMnemonic('d');
        deletebutton.setText("Delete");
        deletebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(deletebutton);
        deletebutton.setBounds(70, 230, 130, 50);

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
        clearbutton.setBounds(200, 280, 130, 50);

        jLabel23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel23.setText("Staff ID");
        getContentPane().add(jLabel23);
        jLabel23.setBounds(20, 70, 90, 30);

        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h6);
        h6.setBounds(110, 170, 350, 30);

        h1.setEditable(false);
        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setText("--");
        h1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h1FocusGained(evt);
            }
        });
        getContentPane().add(h1);
        h1.setBounds(110, 40, 140, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(300, 40, 120, 30);

        jLabel27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel27.setText("Entry No");
        getContentPane().add(jLabel27);
        jLabel27.setBounds(20, 40, 90, 30);

        prebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        prebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pre45.png"))); // NOI18N
        prebutton.setMnemonic('r');
        prebutton.setText("Previous");
        prebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(prebutton);
        prebutton.setBounds(200, 230, 130, 50);

        nextbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        nextbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/next45.png"))); // NOI18N
        nextbutton.setMnemonic('n');
        nextbutton.setText("Next");
        nextbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(nextbutton);
        nextbutton.setBounds(330, 230, 130, 50);

        lastl.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        lastl.setForeground(new java.awt.Color(255, 102, 0));
        lastl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lastl);
        lastl.setBounds(20, 340, 430, 40);

        h4.setEditable(false);
        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h4FocusGained(evt);
            }
        });
        getContentPane().add(h4);
        h4.setBounds(110, 100, 350, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                h3FocusLost(evt);
            }
        });
        h3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h3KeyPressed(evt);
            }
        });
        getContentPane().add(h3);
        h3.setBounds(110, 70, 350, 30);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText("Name");
        getContentPane().add(jLabel19);
        jLabel19.setBounds(20, 100, 90, 30);

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText("Loan Amount");
        getContentPane().add(jLabel22);
        jLabel22.setBounds(20, 140, 90, 30);

        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h5FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                h5FocusLost(evt);
            }
        });
        h5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h5ActionPerformed(evt);
            }
        });
        getContentPane().add(h5);
        h5.setBounds(110, 140, 350, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText(" Date");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(260, 40, 40, 30);

        jCalendarButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
        jCalendarButton2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton2PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton2);
        jCalendarButton2.setBounds(420, 40, 40, 30);

        jLabel30.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel30.setText("Remarks");
        getContentPane().add(jLabel30);
        jLabel30.setBounds(20, 170, 90, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        save();
    }//GEN-LAST:event_savebuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        clear();
    }//GEN-LAST:event_clearbuttonActionPerformed

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewbuttonActionPerformed
        String sno = JOptionPane.showInputDialog(this, "Enter Entry No ?", "Entry No", JOptionPane.PLAIN_MESSAGE);
        view(sno);
    }//GEN-LAST:event_viewbuttonActionPerformed

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebuttonActionPerformed
        String sno = h1.getText();
        delete(sno);
    }//GEN-LAST:event_deletebuttonActionPerformed

    private void h1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h1FocusGained

        h3.requestFocus();
    }//GEN-LAST:event_h1FocusGained

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

        if (jTable2.getRowCount() > 0) {
            String cid = jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString();
            h3.setText(cid);
            h4.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 1).toString());
            get_details();
        }
        cname_list.dispose();

    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable2FocusLost
        cname_list.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2FocusLost

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable2.getRowCount() > 0) {
                String cid = jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString();
                h3.setText(cid);
                h4.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 1).toString());
                get_details();
            }
            h5.requestFocus();
            cname_list.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cname_list.dispose();
            h3.requestFocus();
        }
    }//GEN-LAST:event_jTable2KeyPressed

    private void jScrollPane4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane4FocusLost

        cname_list.dispose();
    }//GEN-LAST:event_jScrollPane4FocusLost

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

        cname_list.setVisible(false);
        cname_list.dispose();
        h5.requestFocus();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void cname_listFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cname_listFocusLost

        cname_list.dispose();
    }//GEN-LAST:event_cname_listFocusLost

    private void prebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prebuttonActionPerformed

        try {
            String query;
            String billno = h1.getText();
            if (billno.equalsIgnoreCase("--")) {
                query = "select max(sno) from pay_loan";
            } else {
                query = "select sno from pay_loan where sno < '" + billno + "' order by sno desc limit 1";
            }
            r = util.doQuery(query);
            boolean selva = false;
            String search_billno = "";
            while (r.next()) {
                selva = true;
                search_billno = r.getString(1);
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            view(search_billno);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_prebuttonActionPerformed

    private void nextbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextbuttonActionPerformed

        try {
            String query;
            String billno = h1.getText();
            if (billno.equalsIgnoreCase("--")) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            query = "select sno from pay_loan where sno > '" + billno + "' order by sno limit 1";
            r = util.doQuery(query);
            boolean selva = false;
            String search_billno = "";
            while (r.next()) {
                selva = true;
                search_billno = r.getString(1);
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            view(search_billno);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_nextbuttonActionPerformed

    private void h3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_h3KeyPressed

        cname_list.requestFocus();
        jTable2.requestFocus();
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            h3.requestFocus();
            cname_list.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (s3.getRowCount() > 0) {
                s3.getDataVector().removeAllElements();
                s3.fireTableDataChanged();
            }
            try {
                cname_list.requestFocus();
                jTable2.requestFocus();
                Point l = jLabel23.getLocationOnScreen();
                cname_list.setLocation(l.x, l.y + jLabel23.getHeight());
                cname_list.setSize(543, 310);
                cname_list.setVisible(true);
                String query = "select sid,sname,desig from staff_entry where sname like '" + h3.getText() + "%' order by sname limit 500";
                r = util.doQuery(query);
                while (r.next()) {
                    s3.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3)});
                }
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_h3KeyPressed

    private void h3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h3FocusLost
        h3.setText(h3.getText().toUpperCase());
    }//GEN-LAST:event_h3FocusLost

    private void h4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h4FocusGained
        // h3.setEnabled(false);
    }//GEN-LAST:event_h4FocusGained

    private void h5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h5FocusLost

    }//GEN-LAST:event_h5FocusLost

    private void h5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h5ActionPerformed

    }//GEN-LAST:event_h5ActionPerformed

    private void h5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h5FocusGained
    }//GEN-LAST:event_h5FocusGained

    private void jCalendarButton2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendarButton2PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jCalendarButton2PropertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JDialog cname_list;
    private javax.swing.JButton deletebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h2;
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h4;
    private javax.swing.JTextField h5;
    private javax.swing.JTextField h6;
    private javax.swing.JButton jButton10;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton2;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lastl;
    private javax.swing.JButton nextbutton;
    private javax.swing.JButton prebutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
