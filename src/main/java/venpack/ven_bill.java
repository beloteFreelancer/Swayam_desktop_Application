package venpack;

import Utils.ColorConstants;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.selrom.db.DataUtil;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultCaret;
import menupack.menu_form;
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public final class ven_bill extends javax.swing.JInternalFrame {

    DataUtil util = null;
    String username = "", utype = "";
    sample2 s3 = new sample2();
    sample2 s2 = new sample2();
    ResultSet r;
    int hmany = 2;
    AutoCompleteSupport support, support1;

    void button_short() {
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        viewbutton.setText("<html><b>View</b><br>(Alt+V)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        prebutton.setText("<html><b>Last Entry</b><br>(Alt+R)</h6><html>");
        nextbutton.setText("<html><b>Next Entry</b><br>(Alt+N)</h6><html>");
        titlelablel.setText("<html><u>Supplier Bill Register (Opening Balance)</u></html>");

        setTitle("Supplier Bill Register (Opening Balance)");
        this.setSize(979, 617);
        javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            setFrameIcon(icon);
        }
        menu_form me = new menu_form();
        hmany = me.getHmany();
    }

    void get_defaults() {
        try {
            menupack.menu_form me = new menupack.menu_form();
            username = me.getUsername();
            utype = me.getUserType();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void get_billno() {
        try {
            int sno = 1;
            String query = "select max(sno) from ven_bill";
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

    void get_sug() {
        try {
            int count = 0;
            String query = "select distinct billno from ven_bal";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct billno from ven_bal";
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

    void get_sug1() {
        try {
            int count = 0;
            String query = "select distinct cname from vendor";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct cname from vendor";
            set = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support1 = AutoCompleteSupport.install(h2, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void save() {
        try {
            if (h2.getSelectedItem() == null || h2.getSelectedItem() == "") {
                JOptionPane.showMessageDialog(this, "Enter Supplier Name ?", "Supplier Name",
                        JOptionPane.ERROR_MESSAGE);
                h2.requestFocus();
                return;
            }
            if (h3.getSelectedItem() == null || h3.getSelectedItem() == "") {
                JOptionPane.showMessageDialog(this, "Enter Bill No ?", "Bill No", JOptionPane.ERROR_MESSAGE);
                h3.requestFocus();
                return;
            }
            if (hh.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Invalid Supplier Details ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h2.requestFocus();
                return;
            }
            if (h6.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Paid Amount ?", "Amount", JOptionPane.ERROR_MESSAGE);
                h6.requestFocus();
                return;
            }
            if (h7.getText().equals("")) {
                h7.setText(".");
            }

            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);
            if (h4.getText().equals("")) {
                h4.setText(g.format(d));
            }
            if (h5.getText().equals("")) {
                h5.setText(g.format(d));
            }
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(h4.getText());
            String date = (new SimpleDateFormat("yyyy-MM-dd").format(nm));
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(h5.getText());
            String ddate = (new SimpleDateFormat("yyyy-MM-dd").format(nm1));
            get_billno();
            String sno = h1.getText();
            String cname = h2.getSelectedItem().toString();
            String billno = h3.getSelectedItem().toString();
            String amount = h6.getText();
            String remarks = h7.getText();

            boolean selva = false;
            String query = "select sno from ven_bill where sno='" + sno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                JOptionPane.showMessageDialog(this, "Entry No Already Exist!", "Already Exist",
                        JOptionPane.ERROR_MESSAGE);
                h2.requestFocus();
                return;
            }
            boolean selva1 = false;
            query = "select billno from ven_bill where cname='" + cname + "' and billno='" + billno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva1 = true;
            }
            if (selva1 == true) {
                JOptionPane.showMessageDialog(this,
                        "This Bill No: '" + billno + "'  is Already Exist for '" + cname + "' ", "Already Exist",
                        JOptionPane.ERROR_MESSAGE);
                h3.requestFocus();
                return;
            }
            int aa = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (aa == JOptionPane.NO_OPTION) {
                return;
            }
            ArrayList query_list = new ArrayList();
            double paid = 0;
            query_list.add("insert into ven_bill values ('" + sno + "','" + cname + "','" + billno + "','" + date
                    + "','" + ddate + "','" + amount + "','" + remarks + "','" + username + "','" + last + "') ");
            query_list.add("insert into ven_bal values ('" + billno + "','" + date + "','" + ddate + "','" + cname
                    + "','" + amount + "','" + paid + "','" + last + "') ");
            int a = util.doManipulation_Batch(query_list);
            if (a > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Saved Successfully</h1></html>", "Saved",
                        JOptionPane.PLAIN_MESSAGE);
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
            h4.setText("");
            h5.setText("");
            h7.setText("");
            hh.setText("");
            h6.setText("");
            h2.setEnabled(true);
            support.uninstall();
            support1.uninstall();
            get_sug();
            h3.setSelectedItem("");
            h2.setSelectedItem("");
            get_sug1();
            deletebutton.setVisible(false);
            savebutton.setVisible(true);
            viewbutton.setVisible(true);
            h2.requestFocus();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void view(String sno) {
        try {
            String query = "select sno,cname,billno,date_format(dat,'%d/%m/%Y'),date_format(ddate,'%d/%m/%Y'),amount,remarks from ven_bill where sno='"
                    + sno + "' ";
            ResultSet r1 = util.doQuery(query);
            boolean selva = false;
            while (r1.next()) {
                h1.setText(r1.getString(1));
                h2.setSelectedItem(r1.getString(2));
                h3.setSelectedItem(r1.getString(3));
                h4.setText(r1.getString(4));
                h5.setText(r1.getString(5));
                h7.setText(r1.getString(7));
                double amount = r1.getDouble(6);
                String amount2 = String.format("%." + hmany + "f", amount);
                h6.setText("" + amount2);
                selva = true;
            }
            if (selva == true) {
                get_patient_details(h2.getSelectedItem().toString());
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
                JOptionPane.showMessageDialog(this, "Login as 'Administrator' to Delete!", "Permission Restricted",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int as = JOptionPane.showConfirmDialog(this, "Want to Delete ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            boolean selva = false;
            String query = "select sno from ven_bill where sno='" + sno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ArrayList query_batch = new ArrayList();
            String cname = h2.getSelectedItem().toString();
            String billno = h3.getSelectedItem().toString();
            query_batch.add("delete from ven_bill where sno='" + sno + "' and cname='" + cname + "'");
            query_batch.add("delete from ven_bal where billno='" + billno + "' and cname='" + cname + "'");
            int count = util.doManipulation_Batch(query_batch);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Deleted Successfully", "Deleted", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_patient_details(String cname) {
        try {
            DefaultCaret caret = (DefaultCaret) hh.getCaret();
            caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
            hh.setText("");
            String query = "select cname,add1,add2,add3,city,mobile from vendor where cname='" + cname + "'";
            r = util.doQuery(query);
            while (r.next()) {
                hh.append("\n Name        : " + r.getString(1));
                hh.append("\n Address     : " + r.getString(2));
                hh.append("\n             : " + r.getString(3));
                hh.append("\n             : " + r.getString(4));
                hh.append("\n Area/City   : " + r.getString(5));
                hh.append("\n Mobile No   : " + r.getString(6));
            }

            double bal = 0;
            query = "select sum(tot-paid) from ven_bal where cname='" + cname + "'";
            r = util.doQuery(query);
            while (r.next()) {
                bal = r.getDouble(1);
            }
            String bal2 = String.format("%." + hmany + "f", bal);
            hh.append("\n");
            hh.append("\n Net Balance : " + bal2);

            boolean selva = false;
            query = "select distinct cname from ven_bal where cname='" + cname + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                hh.append("\n");
                hh.append("\n DUE DETAILS:");
                hh.append("\n ------------------------------------------");
                hh.append("\n Date               Bill No      Due Amount");
                hh.append("\n ------------------------------------------");
            }

            double total = 0;
            query = "select date_format(dat,'%d/%m/%Y'),billno,tot-paid from ven_bal where cname='" + cname
                    + "' and tot-paid >0 ";
            r = util.doQuery(query);
            while (r.next()) {
                String date = r.getString(1);
                String billno = r.getString(2);
                double amount = r.getDouble(3);
                total = total + amount;

                String amount1 = String.format("%." + hmany + "f", amount);
                int bb = billno.length();
                int qq3 = 15 - bb;
                if (qq3 <= 0) {
                } else {
                    for (int e = 0; e < qq3; e++) {
                        billno = " " + billno;
                    }
                }

                int bb1 = amount1.length();
                int qq4 = 15 - bb1;
                if (qq4 <= 0) {
                } else {
                    for (int e = 0; e < qq4; e++) {
                        amount1 = " " + amount1;
                    }
                }
                hh.append("\n " + date + " " + billno + " " + amount1);

            }
            String total1 = String.format("%." + hmany + "f", total);
            int bb1 = total1.length();
            int qq4 = 15 - bb1;
            if (qq4 <= 0) {
            } else {
                for (int e = 0; e < qq4; e++) {
                    total1 = " " + total1;
                }
            }
            if (selva == true) {
                hh.append("\n                     ======================");
                hh.append("\n                     TOTAL: " + total1);
                hh.append("\n                     ======================");
            }
        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ven_bill(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();
        h4.requestFocus();
        deletebutton.setVisible(false);
        get_defaults();
        get_sug();
        get_sug1();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        closebutton = new javax.swing.JButton();
        savebutton = new javax.swing.JButton();
        viewbutton = new javax.swing.JButton();
        deletebutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        h7 = new javax.swing.JTextField();
        h1 = new javax.swing.JTextField();
        h4 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        h5 = new javax.swing.JTextField();
        prebutton = new javax.swing.JButton();
        nextbutton = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jCalendarButton1 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        hh = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        h3 = new javax.swing.JComboBox();
        h6 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jCalendarButton2 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        h2 = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Supplier Bill Register (Opening Balance)");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 410, 30);

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
        closebutton.setBounds(320, 340, 130, 50);

        savebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        savebutton.setIcon(ColorConstants.loadIcon("/icons/save45.png")); // NOI18N
        savebutton.setMnemonic('s');
        savebutton.setText("Save");
        savebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(savebutton);
        savebutton.setBounds(60, 290, 130, 50);

        viewbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        viewbutton.setIcon(ColorConstants.loadIcon("/icons/view45.png")); // NOI18N
        viewbutton.setMnemonic('v');
        viewbutton.setText("View");
        viewbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(viewbutton);
        viewbutton.setBounds(60, 340, 130, 50);

        deletebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        deletebutton.setIcon(ColorConstants.loadIcon("/icons/delete45.png")); // NOI18N
        deletebutton.setMnemonic('d');
        deletebutton.setText("Delete");
        deletebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(deletebutton);
        deletebutton.setBounds(60, 290, 130, 50);

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
        clearbutton.setBounds(190, 340, 130, 50);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Due Date");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(20, 180, 80, 30);

        h7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h7);
        h7.setBounds(100, 240, 350, 30);

        h1.setEditable(false);
        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setText("--");
        h1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h1FocusGained(evt);
            }
        });
        getContentPane().add(h1);
        h1.setBounds(100, 40, 350, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h4);
        h4.setBounds(100, 150, 310, 30);

        jLabel27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel27.setText("Supplier");
        getContentPane().add(jLabel27);
        jLabel27.setBounds(20, 70, 80, 30);

        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h5);
        h5.setBounds(100, 180, 310, 30);

        prebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        prebutton.setIcon(ColorConstants.loadIcon("/icons/pre45.png")); // NOI18N
        prebutton.setMnemonic('r');
        prebutton.setText("Previous");
        prebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(prebutton);
        prebutton.setBounds(190, 290, 130, 50);

        nextbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        nextbutton.setIcon(ColorConstants.loadIcon("/icons/next45.png")); // NOI18N
        nextbutton.setMnemonic('n');
        nextbutton.setText("Next");
        nextbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(nextbutton);
        nextbutton.setBounds(320, 290, 130, 50);

        jLabel29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel29.setText("Remarks");
        getContentPane().add(jLabel29);
        jLabel29.setBounds(20, 240, 80, 30);

        jCalendarButton1.setIcon(ColorConstants.loadIcon("/icons/cal40.png")); // NOI18N
        jCalendarButton1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton1PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton1);
        jCalendarButton1.setBounds(410, 180, 40, 30);

        hh.setEditable(false);
        hh.setColumns(20);
        hh.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        hh.setForeground(new java.awt.Color(255, 0, 51));
        hh.setRows(5);
        jScrollPane1.setViewportView(hh);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(470, 40, 480, 540);

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setText("Bill No");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(20, 120, 80, 30);

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText("Amount");
        getContentPane().add(jLabel22);
        jLabel22.setBounds(20, 210, 80, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        getContentPane().add(h3);
        h3.setBounds(100, 120, 350, 30);

        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h6FocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                h6FocusLost(evt);
            }
        });
        h6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h6ActionPerformed(evt);
            }
        });
        getContentPane().add(h6);
        h6.setBounds(100, 210, 350, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Bill Date");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(20, 150, 80, 30);

        jCalendarButton2.setIcon(ColorConstants.loadIcon("/icons/cal40.png")); // NOI18N
        jCalendarButton2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton2PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton2);
        jCalendarButton2.setBounds(410, 150, 40, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        h2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                h2ItemStateChanged(evt);
            }
        });
        getContentPane().add(h2);
        h2.setBounds(100, 70, 350, 30);

        jLabel28.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel28.setText("Entry No");
        getContentPane().add(jLabel28);
        jLabel28.setBounds(20, 40, 80, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }// GEN-LAST:event_closebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_savebuttonActionPerformed
        save();
    }// GEN-LAST:event_savebuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbuttonActionPerformed
        clear();
    }// GEN-LAST:event_clearbuttonActionPerformed

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_viewbuttonActionPerformed
        String sno = JOptionPane.showInputDialog(this, "Enter Entry No ?", "Entry No", JOptionPane.PLAIN_MESSAGE);
        view(sno);
    }// GEN-LAST:event_viewbuttonActionPerformed

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deletebuttonActionPerformed
        String sno = h1.getText();
        delete(sno);
    }// GEN-LAST:event_deletebuttonActionPerformed

    private void h1FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h1FocusGained

        h2.requestFocus();
    }// GEN-LAST:event_h1FocusGained

    private void prebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_prebuttonActionPerformed

        try {
            String query;
            String billno = h1.getText();
            if (billno.equalsIgnoreCase("--")) {
                query = "select max(sno) from ven_bill";
            } else {
                query = "select sno from ven_bill where sno < '" + billno + "' order by sno desc limit 1";
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
    }// GEN-LAST:event_prebuttonActionPerformed

    private void nextbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_nextbuttonActionPerformed

        try {
            String query;
            String billno = h1.getText();
            if (billno.equalsIgnoreCase("--")) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            query = "select sno from ven_bill where sno > '" + billno + "' order by sno limit 1";
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
    }// GEN-LAST:event_nextbuttonActionPerformed

    private void jCalendarButton1PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jCalendarButton1PropertyChange

        try {
            if (evt.getNewValue() instanceof Date) {
                String ses = evt.getNewValue().toString();
                Date nm = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(ses);
                String date = (new SimpleDateFormat("dd/MM/yyyy").format(nm));
                h4.setText(date);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        // TODO add your handling code here:
    }// GEN-LAST:event_jCalendarButton1PropertyChange

    private void h6FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h6FocusLost

    }// GEN-LAST:event_h6FocusLost

    private void h6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h6ActionPerformed

    }// GEN-LAST:event_h6ActionPerformed

    private void h6FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h6FocusGained
    }// GEN-LAST:event_h6FocusGained

    private void jCalendarButton2PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jCalendarButton2PropertyChange
        try {
            if (evt.getNewValue() instanceof Date) {
                String ses = evt.getNewValue().toString();
                Date nm = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(ses);
                String date = (new SimpleDateFormat("dd/MM/yyyy").format(nm));
                h5.setText(date);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } // TODO add your handling code here:
    }// GEN-LAST:event_jCalendarButton2PropertyChange

    private void h2ItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_h2ItemStateChanged
        get_patient_details(h2.getSelectedItem().toString());
    }// GEN-LAST:event_h2ItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton deletebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JComboBox h2;
    private javax.swing.JComboBox h3;
    private javax.swing.JTextField h4;
    private javax.swing.JTextField h5;
    private javax.swing.JTextField h6;
    private javax.swing.JTextField h7;
    private javax.swing.JTextArea hh;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton1;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton2;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton nextbutton;
    private javax.swing.JButton prebutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
