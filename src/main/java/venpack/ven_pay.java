package venpack;

import Utils.ColorConstants;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.selrom.db.DataUtil;
import java.awt.Font;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.text.DefaultCaret;
import menupack.menu_form;
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public final class ven_pay extends javax.swing.JInternalFrame {

    DataUtil util = null;
    String username = "", utype = "";
    AutoCompleteSupport support;
    sample2 s2 = new sample2();
    ResultSet r;
    int hmany = 2;

    void button_short() {
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        viewbutton.setText("<html><b>View</b><br>(Alt+V)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        prebutton.setText("<html><b>Last Entry</b><br>(Alt+R)</h6><html>");
        nextbutton.setText("<html><b>Next Entry</b><br>(Alt+N)</h6><html>");

        titlelablel.setText("<html><u>Supplier Payment Entry</u></html>");

        setTitle("Supplier Payment Entry");
        this.setSize(979, 617);
        ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            this.setFrameIcon(icon);
        }
        menu_form me = new menu_form();
        hmany = me.getHmany();
    }

    void load_bills_table() {
        try {
            s2.addColumn("Bill No");
            s2.addColumn("Paid Aamount");
            jTable1.setModel(s2);
            jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(224);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(200);
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void get_defaults() {
        try {
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            h2.setText(g.format(d));
            h3.setText(g1.format(d));
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
            String query = "select max(sno) from ven_pay";
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
            if (hh.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Invalid Supplier Details ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }
            if (jTable1.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid Details ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h10.requestFocus();
                return;
            }

            if (h6.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Paid Amount ?", "Amount", JOptionPane.ERROR_MESSAGE);
                h10.requestFocus();
                return;
            }

            if (Double.parseDouble(h6.getText()) <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid Amount ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h10.requestFocus();
                return;
            }
            if (h9.getText().equals("")) {
                h9.setText(".");
            }
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText());
            String date = (new SimpleDateFormat("yyyy/MM/dd").format(nm));

            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);
            get_billno();
            String sno = h1.getText();
            String cname = h4.getSelectedItem().toString();
            String time = h3.getText();
            String net = h6.getText();
            String pby = h7.getSelectedItem().toString();
            String remarks = h9.getText();

            boolean selva = false;
            String query = "select distinct sno from ven_pay where sno='" + sno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                JOptionPane.showMessageDialog(this, "Entry No Already Exist!", "Already Exist",
                        JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }
            int aa = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (aa == JOptionPane.NO_OPTION) {
                return;
            }
            ArrayList query_batch = new ArrayList();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String billno = jTable1.getValueAt(i, 0).toString();
                String amount = jTable1.getValueAt(i, 1).toString();

                query_batch.add("insert into ven_pay values ('" + sno + "','" + date + "','" + time + "','" + cname
                        + "','" + billno + "','" + amount + "','" + net + "','" + pby + "','" + remarks + "','"
                        + username + "','" + last + "' )");
                query_batch.add("update ven_bal set paid=paid+" + amount + " where billno='" + billno + "' and cname='"
                        + cname + "' ");
            }
            int count = util.doManipulation_Batch(query_batch);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Saved Successfully</h1></html>", "Saved",
                        JOptionPane.PLAIN_MESSAGE);
                clear();
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
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            h2.setText(g.format(d));
            h3.setText(g1.format(d));
            h4.setSelectedItem("");
            h6.setText("");
            h7.setSelectedIndex(0);
            h9.setText("");
            hh.setText("");
            h10.removeAllItems();
            h11.setText("");
            h10.setEnabled(true);
            h11.setEnabled(true);
            deletebutton.setVisible(false);
            savebutton.setVisible(true);
            viewbutton.setVisible(true);
            jButton1.setEnabled(true);
            jButton2.setEnabled(true);
            h4.setEnabled(true);
            h4.requestFocus();
            if (s2.getRowCount() > 0) {
                s2.getDataVector().removeAllElements();
                s2.fireTableDataChanged();
            }
            support.uninstall();
            get_sug();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void view(String sno) {
        try {
            String query = "select distinct sno,date_format(dat,'%d/%m/%Y'),tim,cname,net,pby,remarks from ven_pay where sno='"
                    + sno + "' ";
            ResultSet r1 = util.doQuery(query);
            boolean selva = false;
            while (r1.next()) {
                h1.setText(r1.getString(1));
                h2.setText(r1.getString(2));
                h3.setText(r1.getString(3));
                h4.setSelectedItem(r1.getString(4));
                double amount = r1.getDouble(5);
                String amount2 = String.format("%." + hmany + "f", amount);
                h6.setText(amount2);
                h7.setSelectedItem(r1.getString(6));
                h9.setText(r1.getString(7));
                selva = true;
            }

            if (selva == true) {
                if (s2.getRowCount() > 0) {
                    s2.getDataVector().removeAllElements();
                    s2.fireTableDataChanged();
                }
                query = "select billno,amount from ven_pay where sno='" + sno + "'";
                r1 = util.doQuery(query);
                while (r1.next()) {
                    double amount = r1.getDouble(2);
                    String amount2 = String.format("%." + hmany + "f", amount);
                    s2.addRow(new Object[] { r1.getString(1), amount2 });
                }
            }
            if (selva == true) {
                savebutton.setVisible(false);
                viewbutton.setVisible(false);
                deletebutton.setVisible(true);
                h4.setEnabled(false);
                h10.setEnabled(false);
                h11.setEnabled(false);
                jButton1.setEnabled(false);
                jButton2.setEnabled(false);
            } else if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.print(e.getMessage());
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
            String query = "select distinct sno from ven_pay where sno='" + sno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ArrayList query_batch = new ArrayList();
            String cname = h4.getSelectedItem().toString();
            query_batch.add("delete from ven_pay where sno='" + sno + "'");
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String billno = jTable1.getValueAt(i, 0).toString();
                String amount = jTable1.getValueAt(i, 1).toString();
                query_batch.add("update ven_bal set paid=paid-" + amount + " where billno='" + billno + "' and cname='"
                        + cname + "' ");
            }
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
                h10.removeAllItems();
                query = "select billno from ven_bal where cname='" + cname + "' and tot-paid>0";
                r = util.doQuery(query);
                while (r.next()) {
                    h10.addItem(r.getString(1));
                }

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

    void add_item() {
        try {
            if (h10.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Select Bill No ?", "Bill No", JOptionPane.ERROR_MESSAGE);
                h10.requestFocus();
                return;
            }
            if (h11.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Paid Amount ?", "Amount", JOptionPane.ERROR_MESSAGE);
                h11.requestFocus();
                return;
            }

            boolean selva = false;
            String query = "select distinct cname from ven_bal where cname='" + h4.getSelectedItem() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "Invalid Supplier Details!", "Invalid", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }

            if (ball.getText().equals("")) {
                get_bal();
            }
            if (Double.parseDouble(h11.getText()) <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid Paid Amount ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h11.requestFocus();
                return;
            }

            if (Double.parseDouble(h11.getText()) > Double.parseDouble(ball.getText())) {
                JOptionPane.showMessageDialog(this, "Paid Amount is Greaterthan Balance Amount ?",
                        "Invalid Paid Amount", JOptionPane.ERROR_MESSAGE);
                h11.requestFocus();
                return;
            }
            double amount = Double.parseDouble(h11.getText());
            String amount2 = String.format("%." + hmany + "f", amount);
            s2.addRow(new Object[] { h10.getSelectedItem(), amount2 });
            h10.removeItem(h10.getSelectedItem());
            h11.setText("");
            ball.setText("");
            calculate();
        } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void calculate() {
        double amount = 0;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            amount = amount + Double.parseDouble(jTable1.getValueAt(i, 1).toString());
        }
        String amount2 = String.format("%." + hmany + "f", amount);
        h6.setText(amount2);
    }

    void get_bal() {
        try {
            String query = "select tot-paid from ven_bal where cname='" + h4.getSelectedItem() + "' and billno='"
                    + h10.getSelectedItem() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                ball.setText(r.getString(1));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug() {
        try {
            int count = 0;
            String query = "select distinct cname from ven_bal  group by cname having sum(tot-paid)>0";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct cname from ven_bal group by cname having sum(tot-paid)>0";
            set = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support = AutoCompleteSupport.install(
                    h4, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ven_pay(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();

        h4.requestFocus();
        deletebutton.setVisible(false);
        get_defaults();
        load_bills_table();
        ball.setVisible(false);
        get_sug();
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
        jLabel17 = new javax.swing.JLabel();
        h7 = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        h9 = new javax.swing.JTextField();
        h1 = new javax.swing.JTextField();
        h2 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        h3 = new javax.swing.JTextField();
        prebutton = new javax.swing.JButton();
        nextbutton = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jCalendarButton1 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        hh = new javax.swing.JTextArea();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        h10 = new javax.swing.JComboBox();
        h11 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        ball = new javax.swing.JTextField();
        h6 = new javax.swing.JTextField();
        h4 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Supplier Payment Entry");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 330, 30);

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
        closebutton.setBounds(320, 530, 130, 50);

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
        savebutton.setBounds(60, 480, 130, 50);

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
        viewbutton.setBounds(60, 530, 130, 50);

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
        deletebutton.setBounds(60, 530, 130, 50);

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
        clearbutton.setBounds(190, 530, 130, 50);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("Pay Mode");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(20, 410, 80, 30);

        h7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cash", "Bank", "Others" }));
        getContentPane().add(h7);
        h7.setBounds(100, 410, 350, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Supplier");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(20, 100, 80, 30);

        h9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h9);
        h9.setBounds(100, 440, 350, 30);

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

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(100, 70, 140, 30);

        jLabel27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel27.setText("Entry No");
        getContentPane().add(jLabel27);
        jLabel27.setBounds(20, 40, 80, 30);

        h3.setEditable(false);
        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h3);
        h3.setBounds(280, 70, 170, 30);

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
        prebutton.setBounds(190, 480, 130, 50);

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
        nextbutton.setBounds(320, 480, 130, 50);

        jLabel29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel29.setText("Remarks");
        getContentPane().add(jLabel29);
        jLabel29.setBounds(20, 440, 80, 30);

        jCalendarButton1.setIcon(ColorConstants.loadIcon("/icons/cal40.png")); // NOI18N
        jCalendarButton1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton1PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton1);
        jCalendarButton1.setBounds(240, 70, 40, 30);

        hh.setEditable(false);
        hh.setColumns(20);
        hh.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        hh.setForeground(new java.awt.Color(255, 0, 51));
        hh.setRows(5);
        jScrollPane1.setViewportView(hh);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(470, 40, 480, 540);

        jLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel20.setText("Net Amount");
        getContentPane().add(jLabel20);
        jLabel20.setBounds(20, 380, 80, 30);

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setText("Bill No");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(20, 170, 80, 30);

        jTable1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
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
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(20, 240, 430, 130);

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText("Paid Amount");
        getContentPane().add(jLabel22);
        jLabel22.setBounds(20, 200, 90, 30);

        h10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        getContentPane().add(h10);
        h10.setBounds(100, 170, 350, 30);

        h11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h11ActionPerformed(evt);
            }
        });
        h11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h11FocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                h11FocusLost(evt);
            }
        });
        getContentPane().add(h11);
        h11.setBounds(100, 200, 250, 30);

        jButton1.setIcon(ColorConstants.loadIcon("/icons/add22.png")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(350, 200, 50, 30);

        jButton2.setIcon(ColorConstants.loadIcon("/icons/delete22.png")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(400, 200, 50, 30);

        ball.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ball.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ballActionPerformed(evt);
            }
        });
        ball.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ballFocusLost(evt);
            }
        });
        getContentPane().add(ball);
        ball.setBounds(520, 10, 250, 30);

        h6.setEditable(false);
        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h6);
        h6.setBounds(100, 380, 350, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        h4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                h4ItemStateChanged(evt);
            }
        });
        getContentPane().add(h4);
        h4.setBounds(100, 100, 350, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Date");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(20, 70, 80, 30);

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

        h4.requestFocus();
    }// GEN-LAST:event_h1FocusGained

    private void prebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_prebuttonActionPerformed

        try {
            String query;
            String billno = h1.getText();
            if (billno.equalsIgnoreCase("--")) {
                query = "select max(sno) from ven_pay";
            } else {
                query = "select sno from ven_pay where sno < '" + billno + "' order by sno desc limit 1";
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
            query = "select sno from ven_pay where sno > '" + billno + "' order by sno limit 1";
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
                h2.setText(date);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        // TODO add your handling code here:
    }// GEN-LAST:event_jCalendarButton1PropertyChange

    private void h11FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h11FocusLost
        h7.requestFocus();
    }// GEN-LAST:event_h11FocusLost

    private void h11ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h11ActionPerformed
        add_item();
    }// GEN-LAST:event_h11ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        add_item();
    }// GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
        s2.removeRow(jTable1.getSelectedRow());
        calculate();
    }// GEN-LAST:event_jButton2ActionPerformed

    private void ballActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ballActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_ballActionPerformed

    private void ballFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_ballFocusLost
        // TODO add your handling code here:
    }// GEN-LAST:event_ballFocusLost

    private void h11FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h11FocusGained
        if (h10.getSelectedItem() != null) {
            get_bal();
        }
    }// GEN-LAST:event_h11FocusGained

    private void h4ItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_h4ItemStateChanged

        get_patient_details(h4.getSelectedItem().toString());
    }// GEN-LAST:event_h4ItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ball;
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton deletebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JComboBox h10;
    private javax.swing.JTextField h11;
    private javax.swing.JTextField h2;
    private javax.swing.JTextField h3;
    private javax.swing.JComboBox<String> h4;
    private javax.swing.JTextField h6;
    private javax.swing.JComboBox h7;
    private javax.swing.JTextField h9;
    private javax.swing.JTextArea hh;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton nextbutton;
    private javax.swing.JButton prebutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
