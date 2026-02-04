package hrpack;

import com.selrom.db.DataUtil;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import menupack.menu_form;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public class pay_bill_generation extends javax.swing.JInternalFrame {

    DataUtil util = null;
    ResultSet r;
    int hmany = 2;
    String utype = new menu_form().getUserType();

    public class sample2 extends DefaultTableModel {

        @Override
        public void addColumn(Object columnName) {
            super.addColumn(columnName);
        }

        @Override
        public void addRow(Object[] rowData) {
            super.addRow(rowData);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 5 || column == 6;
        }
    }

    sample2 s2 = new sample2();

    final void button_short() {
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Excel</b><br>(Alt+I)</h6><html>");
        viewbutton.setText("<html><b>View</b><br>(Alt+V)</h6><html>");
        generatebutton.setText("<html><b>Generate List</b>  (Alt+G)</h6><html>");
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        netbutton.setText("<html><b>Calculate Net Pay</b>  (Alt+P)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        smsbutton.setText("<html><b>Send SMS</b><br>(Alt+M)</h6><html>");
        prebutton.setText("<html><b>Last Entry</b><br>(Alt+R)</h6><html>");
        nextbutton.setText("<html><b>Next Entry</b><br>(Alt+N)</h6><html>");
        titlelablel.setText("<html><u>Payroll Generation</u></html>");
        setTitle("Payroll Generation");
        this.setSize(1017, 650);
        java.net.URL iconUrl = ClassLoader.getSystemResource("/images/icon.png");
        if (iconUrl != null) {
            ImageIcon icon = new ImageIcon(iconUrl);
            this.setFrameIcon(icon);
        }
        menu_form me = new menu_form();
        hmany = me.getHmany();
        Date d = new Date();
        SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
        h2.setText(g.format(d));
    }

    void get_billno() {
        try {
            int sno = 1;
            String query = "select max(sno) from pay_bill";
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

    public final void load_list_table() {
        s2.addColumn("Staff Id");
        s2.addColumn("Name");
        s2.addColumn("Salary/Day");
        s2.addColumn("Days");
        s2.addColumn("Gross Pay");
        s2.addColumn("Advance Amount");
        s2.addColumn("Loan Deduction");
        s2.addColumn("Net Pay");
        s2.addColumn("Mobile No");
        s2.addColumn("Loan No");
        s2.addColumn("Balance");
        jTable1.setModel(s2);
        DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
        dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
        jTable1.getColumnModel().getColumn(2).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(3).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(4).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(5).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(6).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(7).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(8).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(9).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(10).setCellRenderer(dtcr1);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(250);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(90);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(10).setPreferredWidth(100);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
        jTable1.setShowGrid(true);
    }

    void load_report(String dfrom, String dto) {
        try {
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(dfrom);
            String lk = (new SimpleDateFormat("yyyy/MM/dd").format(nm));
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(dto);
            String lk1 = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));
            String query = "select sid,sname,salary,mobile from staff_entry where status='Working' order by desig,sname";
            r = util.doQuery(query);
            while (r.next()) {
                String salary = String.format("%." + hmany + "f", r.getDouble(3));
                s2.addRow(new Object[] { r.getString(1), r.getString(2), salary, "", "", "", "", "", r.getString(4),
                        ".", 0 });
            }
            // attendance
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String cid = jTable1.getValueAt(i, 0).toString();
                String cname = jTable1.getValueAt(i, 1).toString();
                double salary = Double.parseDouble(jTable1.getValueAt(i, 2).toString());
                int days = 0;
                query = "select sum(value) from atten_entry where dat between '" + lk + "' and '" + lk1 + "' and sid='"
                        + cid + "' and sname='" + cname + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    days = r.getInt(1);
                    jTable1.setValueAt(days, i, 3);
                }
                double basic = salary * days;
                String basic2 = String.format("%." + hmany + "f", basic);
                jTable1.setValueAt(basic2, i, 4);
            } // attendance ends

            // advance table
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String cid = jTable1.getValueAt(i, 0).toString();
                String cname = jTable1.getValueAt(i, 1).toString();
                query = "select sum(amount) from pay_advance where cid='" + cid + "' and cname='" + cname
                        + "' and dat between '" + lk + "' and '" + lk1 + "' ";
                r = util.doQuery(query);
                while (r.next()) {
                    String amount = String.format("%." + hmany + "f", r.getDouble(1));
                    jTable1.setValueAt(amount, i, 5);
                }
            } // advance ends

            // loan table
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String cid = jTable1.getValueAt(i, 0).toString();
                String cname = jTable1.getValueAt(i, 1).toString();
                query = "select sno,tot-paid from pay_loan where cid='" + cid + "' and cname='" + cname
                        + "' and status='Active' ";
                r = util.doQuery(query);
                while (r.next()) {
                    jTable1.setValueAt(r.getString(1), i, 9);
                    jTable1.setValueAt(r.getString(2), i, 10);
                }
            } // Loans ends

            h3.setEnabled(false);
            h4.setEnabled(false);
            jCalendarButton1.setEnabled(false);
            jCalendarButton2.setEnabled(false);
            generatebutton.setEnabled(false);
        } catch (ClassNotFoundException | NumberFormatException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_net_pay() {
        try {
            double nbasic = 0, nlpay = 0, nlopay = 0, tnet = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                Object leave = jTable1.getValueAt(i, 5);
                Object loan = jTable1.getValueAt(i, 6);
                if (leave == null || leave == "") {
                    jTable1.setValueAt(0, i, 5);
                }
                if (loan == null || loan == "") {
                    jTable1.setValueAt(0, i, 6);
                }
                double basic = Double.parseDouble(jTable1.getValueAt(i, 4).toString());
                double lpay = Double.parseDouble(jTable1.getValueAt(i, 5).toString());
                double lopay = Double.parseDouble(jTable1.getValueAt(i, 6).toString());
                double deduc = lpay + lopay;
                if (deduc > basic) {
                    JOptionPane.showMessageDialog(this, "Deductions are Greaterthan Gross Pay!", "Invalid Deductions",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double net = basic - deduc;
                String net2 = String.format("%." + hmany + "f", net);
                jTable1.setValueAt(net2, i, 7);
                nbasic = nbasic + basic;
                nlpay = nlpay + lpay;
                nlopay = nlopay + lopay;
                tnet = tnet + net;
            }
            calculation();
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    void calculation() {
        try {
            double nbasic = 0, nlpay = 0, nlopay = 0, tnet = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                double basic = Double.parseDouble(jTable1.getValueAt(i, 4).toString());
                double lpay = Double.parseDouble(jTable1.getValueAt(i, 5).toString());
                double lopay = Double.parseDouble(jTable1.getValueAt(i, 6).toString());
                double net = basic - lpay - lopay;
                nbasic = nbasic + basic;
                nlpay = nlpay + lpay;
                nlopay = nlopay + lopay;
                tnet = tnet + net;
            }
            String nbasic2 = String.format("%." + hmany + "f", nbasic);
            String nlpay2 = String.format("%." + hmany + "f", nlpay);
            String nlopay2 = String.format("%." + hmany + "f", nlopay);
            String tnet2 = String.format("%." + hmany + "f", tnet);
            s2.addRow(new Object[] { "", "", "", "", "", "", "", "", "", "", "" });
            s2.addRow(new Object[] { "", "Total: " + (jTable1.getRowCount() - 1), "", "", "" + nbasic2, "" + nlpay2,
                    "" + nlopay2, "" + tnet2, "", "", "" });
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    void save() {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "Oops", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (int i = 0; i < jTable1.getRowCount() - 2; i++) {
                Object np = jTable1.getValueAt(i, 7);
                if (np == null || np == "") {
                    JOptionPane.showMessageDialog(this, "Invalid Net Pay!", "Invalid", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            int a = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (a == JOptionPane.NO_OPTION) {
                return;
            }
            get_billno();
            boolean selva = false;
            String sno = h1.getText();
            Date n = new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText());
            String date = (new SimpleDateFormat("yyyy/MM/dd").format(n));
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(h3.getText());
            String lk = (new SimpleDateFormat("yyyy/MM/dd").format(nm));
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(h4.getText());
            String lk1 = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);
            String query = "select sno from pay_bill where sno='" + sno + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                JOptionPane.showMessageDialog(this, "Already Exist!", "Oops", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ArrayList list = new ArrayList();
            for (int i = 0; i < jTable1.getRowCount() - 2; i++) {
                String cid = jTable1.getValueAt(i, 0).toString();
                String cname = jTable1.getValueAt(i, 1).toString();
                String salary = jTable1.getValueAt(i, 2).toString();
                String days = jTable1.getValueAt(i, 3).toString();
                String gross = jTable1.getValueAt(i, 4).toString();
                String lpay = jTable1.getValueAt(i, 5).toString();
                double lopay = Double.parseDouble(jTable1.getValueAt(i, 6).toString());
                String net = jTable1.getValueAt(i, 7).toString();
                String mobile = jTable1.getValueAt(i, 8).toString();
                String lno = jTable1.getValueAt(i, 9).toString();
                String balance = jTable1.getValueAt(i, 10).toString();

                list.add("insert into pay_bill values ('" + sno + "','" + lk + "','" + lk1 + "','" + cid + "','" + cname
                        + "','" + salary + "','" + days + "','" + gross + "','" + lpay + "','" + lopay + "','" + net
                        + "','" + mobile + "','" + lno + "','" + balance + "','" + last + "','" + date + "')");
                list.add("update pay_loan set paid=paid+" + lopay + " where sno='" + lno + "' and cid='" + cid
                        + "' and cname='" + cname + "' ");
            } // table row counts
            int as = util.doManipulation_Batch(list);
            if (as > 0) {
                int aa = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Send SMS ?</h1></html>",
                        "Saved Successfully", JOptionPane.YES_NO_OPTION);
                if (aa == JOptionPane.YES_OPTION) {
                    send_sms();
                }
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException | ParseException e) {
            System.out.println(e);
        }
    }

    void send_sms() {

    }

    void clear() {
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        generatebutton.setEnabled(true);
        h3.setText("");
        h4.setText("");
        h1.setText("--");
        h3.setEnabled(true);
        h4.setEnabled(true);
        jCalendarButton1.setEnabled(true);
        jCalendarButton2.setEnabled(true);
        savebutton.setVisible(true);
        viewbutton.setVisible(true);
        deletebutton.setVisible(false);
        smsbutton.setVisible(false);
        Date d = new Date();
        SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
        h2.setText(g.format(d));
    }

    void view(String sno) {
        try {
            String query = "select sno,date_format(dfrom,'%d/%m/%Y'),date_format(dto,'%d/%m/%Y'),date_format(dat,'%d/%m/%Y') from pay_bill where sno='"
                    + sno + "' ";
            r = util.doQuery(query);
            boolean selva = false;
            while (r.next()) {
                h1.setText(r.getString(1));
                h3.setText(r.getString(2));
                h4.setText(r.getString(3));
                h2.setText(r.getString(4));
                selva = true;
            }
            if (selva == true) {
                savebutton.setVisible(false);
                viewbutton.setVisible(false);
                deletebutton.setVisible(true);
                smsbutton.setVisible(true);

                query = "select cid,cname,salary,days,gross,lpay,lopay,net,mobile,lno,balance from pay_bill where sno='"
                        + sno + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    String salary = String.format("%." + hmany + "f", r.getDouble(3));
                    String gross = String.format("%." + hmany + "f", r.getDouble(5));
                    String lpay = String.format("%." + hmany + "f", r.getDouble(6));
                    String lopay = String.format("%." + hmany + "f", r.getDouble(7));
                    String net = String.format("%." + hmany + "f", r.getDouble(8));
                    s2.addRow(new Object[] { r.getString(1), r.getString(2), salary, r.getString(4), gross, lpay, lopay,
                            net, r.getString(9), r.getString(10), r.getString(11) });
                }
                calculation();
            } else if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    void delete() {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "Oops", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (int i = 0; i < jTable1.getRowCount() - 2; i++) {
                Object np = jTable1.getValueAt(i, 7);
                if (np == null || np == "") {
                    JOptionPane.showMessageDialog(this, "Invalid Net Pay!", "Invalid", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (utype.equalsIgnoreCase("User")) {
                JOptionPane.showMessageDialog(this, "Login as 'Administrator' to Delete!", "Permission Restricted",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int a = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Delete ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (a == JOptionPane.NO_OPTION) {
                return;
            }
            String sno = h1.getText();
            ArrayList list = new ArrayList();
            for (int i = 0; i < jTable1.getRowCount() - 2; i++) {
                String cid = jTable1.getValueAt(i, 0).toString();
                String cname = jTable1.getValueAt(i, 1).toString();
                double lopay = Double.parseDouble(jTable1.getValueAt(i, 6).toString());
                String lno = jTable1.getValueAt(i, 9).toString();
                list.add("update pay_loan set paid=paid-" + lopay + " where sno='" + lno + "' and cid='" + cid
                        + "' and cname='" + cname + "' ");
            } // table row counts
            list.add("delete from pay_bill where sno='" + sno + "'");
            int as = util.doManipulation_Batch(list);
            if (as > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Deleted Successfully</h1></html>", "Deleted",
                        JOptionPane.PLAIN_MESSAGE);
                clear();
            } else {
                JOptionPane.showMessageDialog(this, "Please Try again...");
            }
        } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println();
        }
    }

    public pay_bill_generation(DataUtil util) {
        initComponents();

        this.util = util;
        load_list_table();
        button_short();
        smsbutton.setVisible(false);
        deletebutton.setVisible(false);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        generatebutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        excelbutton = new javax.swing.JButton();
        h3 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        h4 = new javax.swing.JTextField();
        jCalendarButton1 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jCalendarButton2 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        viewbutton = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        h1 = new javax.swing.JTextField();
        netbutton = new javax.swing.JButton();
        savebutton = new javax.swing.JButton();
        smsbutton = new javax.swing.JButton();
        deletebutton = new javax.swing.JButton();
        prebutton = new javax.swing.JButton();
        nextbutton = new javax.swing.JButton();
        h2 = new javax.swing.JTextField();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Payroll Generation");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 200, 30);

        generatebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/generate30.png"))); // NOI18N
        generatebutton.setMnemonic('g');
        generatebutton.setText("Generate List");
        generatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton);
        generatebutton.setBounds(400, 80, 210, 30);

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
        clearbutton.setBounds(760, 560, 120, 50);

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
        excelbutton.setBounds(640, 560, 120, 50);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h3);
        h3.setBounds(80, 80, 110, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Entry No");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(10, 40, 70, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h4);
        h4.setBounds(250, 80, 110, 30);

        jCalendarButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
        jCalendarButton1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton1PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton1);
        jCalendarButton1.setBounds(190, 80, 30, 30);

        jCalendarButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
        jCalendarButton2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton2PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton2);
        jCalendarButton2.setBounds(360, 80, 30, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("To");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(230, 80, 20, 30);

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
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 110, 1000, 440);

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
        viewbutton.setBounds(510, 560, 130, 50);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Date from");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(10, 80, 70, 30);

        h1.setEditable(false);
        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setText("--");
        h1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h1FocusGained(evt);
            }
        });
        getContentPane().add(h1);
        h1.setBounds(80, 40, 170, 30);

        netbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        netbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/apply45.png"))); // NOI18N
        netbutton.setMnemonic('p');
        netbutton.setText("Calculate Net Pay");
        netbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                netbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(netbutton);
        netbutton.setBounds(610, 80, 260, 30);

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
        savebutton.setBounds(120, 560, 130, 50);

        smsbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        smsbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sms45.png"))); // NOI18N
        smsbutton.setMnemonic('m');
        smsbutton.setText("SMS");
        smsbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smsbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(smsbutton);
        smsbutton.setBounds(0, 560, 120, 50);

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
        deletebutton.setBounds(120, 560, 130, 50);

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
        prebutton.setBounds(250, 560, 130, 50);

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
        nextbutton.setBounds(380, 560, 130, 50);

        h2.setEditable(false);
        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(250, 40, 140, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }// GEN-LAST:event_closebuttonActionPerformed

    private void generatebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_generatebuttonActionPerformed
        Date d = new Date();
        SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");

        if (h3.getText().equals("")) {
            h3.setText(g.format(d));
        }
        if (h4.getText().equals("")) {
            h4.setText(g.format(d));
        }
        load_report(h3.getText(), h4.getText());

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
                f.write("Sales Report:\n");
                f.write("Date from: " + h3.getText() + "  To: " + h4.getText() + "\n");
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
    }// GEN-LAST:event_excelbuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbuttonActionPerformed
        clear();
    }// GEN-LAST:event_clearbuttonActionPerformed

    private void jCalendarButton1PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jCalendarButton1PropertyChange
        try {
            if (evt.getNewValue() instanceof Date) {
                String ses = evt.getNewValue().toString();
                Date nm = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(ses);
                String date = (new SimpleDateFormat("dd/MM/yyyy").format(nm));
                h3.setText(date);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jCalendarButton1PropertyChange

    private void jCalendarButton2PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jCalendarButton2PropertyChange
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
    }// GEN-LAST:event_jCalendarButton2PropertyChange

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_viewbuttonActionPerformed
        String sno = JOptionPane.showInputDialog(this, "Enter Entry No ?", "Entry No", JOptionPane.PLAIN_MESSAGE);
        view(sno);
    }// GEN-LAST:event_viewbuttonActionPerformed

    private void netbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_netbuttonActionPerformed
        get_net_pay();
    }// GEN-LAST:event_netbuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_savebuttonActionPerformed
        save(); // TODO add your handling code here:
    }// GEN-LAST:event_savebuttonActionPerformed

    private void smsbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_smsbuttonActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_smsbuttonActionPerformed

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deletebuttonActionPerformed
        delete(); // TODO add your handling code here:
    }// GEN-LAST:event_deletebuttonActionPerformed

    private void prebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_prebuttonActionPerformed

        try {
            String query;
            String billno = h1.getText();
            if (billno.equalsIgnoreCase("--")) {
                query = "select distinct max(sno) from pay_bill";
            } else {
                query = "select  distinct sno from pay_bill where sno < '" + billno + "' order by sno desc limit 1";
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
            query = "select  distinct sno from pay_bill where sno > '" + billno + "' order by sno limit 1";
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

    private void h1FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h1FocusGained
        h3.requestFocus();
    }// GEN-LAST:event_h1FocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton deletebutton;
    private javax.swing.JButton excelbutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h2;
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h4;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton1;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton2;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton netbutton;
    private javax.swing.JButton nextbutton;
    private javax.swing.JButton prebutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JButton smsbutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
