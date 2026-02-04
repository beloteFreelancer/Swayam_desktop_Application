package custpack;

import ca.odell.glazedlists.GlazedLists;
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
 * mysoft.java@gmail.com
 */
public final class cust_bill extends javax.swing.JInternalFrame {

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
        titlelablel.setText("<html><u>Customer Bill Register (Opening Balance)</u></html>");

        setTitle("Customer Bill Register (Opening Balance)");
        this.setSize(979, 617);
        java.net.URL imgUrl = getClass().getResource("/images/icon.png");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            this.setFrameIcon(icon);
        }
        menu_form me = new menu_form();
        hmany = me.getHmany();
    }

    void load_patient_table() {
        try {
            s3.addColumn("Customer Id");
            s3.addColumn("Customer Name");
            s3.addColumn("Area");
            jTable2.setModel(s3);
            jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(150);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(550);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(184);
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void get_billno() {
        try {
            int sno = 1;
            String query = "select max(sno) from cust_bill";
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
            String query = "select distinct billno from cust_bal";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct billno from cust_bal";
            set = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support = AutoCompleteSupport.install(h4, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void save() {
        try {
            if (h2.getText().equals("") || h3.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Customer Id ?", "Customer Id", JOptionPane.ERROR_MESSAGE);
                h2.requestFocus();
                return;
            }
            if (hh.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Invalid Customer Details ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h2.requestFocus();
                return;
            }
            if (h7.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Paid Amount ?", "Amount", JOptionPane.ERROR_MESSAGE);
                h7.requestFocus();
                return;
            }
            if (h8.getText().equals("")) {
                h8.setText(".");
            }

            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);
            if (h5.getText().equals("")) {
                h5.setText(g.format(d));
            }
            if (h6.getText().equals("")) {
                h6.setText(g.format(d));
            }
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(h5.getText());
            String date = (new SimpleDateFormat("yyyy-MM-dd").format(nm));
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(h6.getText());
            String ddate = (new SimpleDateFormat("yyyy-MM-dd").format(nm1));
            get_billno();
            String sno = h1.getText();
            String cid = h2.getText();
            String cname = h3.getText();
            String billno = h4.getSelectedItem().toString();
            String amount = h7.getText();
            String remarks = h8.getText();

            boolean selva = false;
            String query = "select sno from cust_bill where sno='" + sno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                JOptionPane.showMessageDialog(this, "Entry No Already Exist!", "Already Exist", JOptionPane.ERROR_MESSAGE);
                h2.requestFocus();
                return;
            }
            boolean selva1 = false;
            query = "select billno from cust_bill where cid='" + cid + "' and cname='" + cname + "' and billno='" + billno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva1 = true;
            }
            if (selva1 == true) {
                JOptionPane.showMessageDialog(this, "This Bill No: '" + billno + "'  is Already Exist for '" + cname + "' ", "Already Exist", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }
            int aa = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (aa == JOptionPane.NO_OPTION) {
                return;
            }
            ArrayList query_list = new ArrayList();
            double paid = 0;
            query_list.add("insert into cust_bill values ('" + sno + "','" + cid + "','" + cname + "','" + billno + "','" + date + "','" + ddate + "','" + amount + "','" + remarks + "','" + username + "','" + last + "') ");
            query_list.add("insert into cust_bal values ('" + billno + "','" + date + "','" + ddate + "','" + cid + "','" + cname + "','" + amount + "','" + paid + "','" + last + "') ");
            int a = util.doManipulation_Batch(query_list);
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
            h5.setText("");
            h6.setText("");
            h2.setText("");
            h3.setText("");
            h8.setText("");
            hh.setText("");
            h7.setText("");
            h2.setEnabled(true);
            support.uninstall();
            get_sug();
            h4.setSelectedItem("");
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
            String query = "select sno,cid,cname,billno,date_format(dat,'%d/%m/%Y'),date_format(ddate,'%d/%m/%Y'),amount,remarks from cust_bill where sno='" + sno + "' ";
            r = util.doQuery(query);
            boolean selva = false;
            while (r.next()) {
                h1.setText(r.getString(1));
                h2.setText(r.getString(2));
                h3.setText(r.getString(3));
                h4.setSelectedItem(r.getString(4));
                h5.setText(r.getString(5));
                h6.setText(r.getString(6));
                h8.setText(r.getString(8));
                double amount = r.getDouble(7);
                String amount2 = String.format("%." + hmany + "f", amount);
                h7.setText("" + amount2);
                selva = true;
            }
            if (selva == true) {
                get_patient_details(h2.getText());
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
            String query = "select distinct sno from cust_bill where sno='" + sno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ArrayList query_batch = new ArrayList();
            String cid = h2.getText();
            String cname = h3.getText();
            String billno = h4.getSelectedItem().toString();
            query_batch.add("delete from cust_bill where sno='" + sno + "' and cid='" + cid + "' and cname='" + cname + "'");
            query_batch.add("delete from cust_bal where billno='" + billno + "' and cid='" + cid + "' and cname='" + cname + "'");
            int count = util.doManipulation_Batch(query_batch);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Deleted Successfully", "Deleted", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_patient_details(String cid) {
        try {
            DefaultCaret caret = (DefaultCaret) hh.getCaret();
            caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
            hh.setText("");
            String query = "select cid,cname,add1,add2,add3,city,mobile,climit,cname from cust where cid='" + cid + "'";
            r = util.doQuery(query);
            while (r.next()) {
                hh.append("\n Cust_Id     : " + r.getString(1));
                hh.append("\n Name        : " + r.getString(2));
                hh.append("\n Address     : " + r.getString(3));
                hh.append("\n             : " + r.getString(4));
                hh.append("\n             : " + r.getString(5));
                hh.append("\n Area/City   : " + r.getString(6));
                hh.append("\n Mobile No   : " + r.getString(7));
                double climit = r.getDouble(8);
                String climit2 = String.format("%." + hmany + "f", climit);
                hh.append("\n Credit Limit: " + climit2);
                h3.setText(r.getString(9));
            }

            double bal = 0;
            query = "select sum(tot-paid) from cust_bal where cid='" + cid + "'";
            r = util.doQuery(query);
            while (r.next()) {
                bal = r.getDouble(1);
            }
            String bal2 = String.format("%." + hmany + "f", bal);
            hh.append("\n");
            hh.append("\n Net Balance : " + bal2);
            boolean selva = false;
            query = "select distinct cid from cust_bal where cid='" + cid + "' group by cid having sum(tot-paid)>0";
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
            query = "select date_format(dat,'%d/%m/%Y'),billno,tot-paid from cust_bal where cid='" + cid + "' and tot-paid >0 ";
            r = util.doQuery(query);
            while (r.next()) {
                String date = r.getString(1);
                String billno = r.getString(2);
                double amount = r.getDouble(3);
                total = total + amount;
                String amount2 = String.format("%." + hmany + "f", amount);
                String amount1 = amount2;

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

    public cust_bill(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();
        h5.requestFocus();
        deletebutton.setVisible(false);
        load_patient_table();
        get_defaults();
        get_sug();

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
        jLabel12 = new javax.swing.JLabel();
        h8 = new javax.swing.JTextField();
        h1 = new javax.swing.JTextField();
        h5 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        h6 = new javax.swing.JTextField();
        prebutton = new javax.swing.JButton();
        nextbutton = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jCalendarButton1 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        h3 = new javax.swing.JTextField();
        h2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        hh = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        h4 = new javax.swing.JComboBox();
        h7 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jCalendarButton2 = new net.sourceforge.jcalendarbutton.JCalendarButton();

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
        jScrollPane4.setBounds(0, 0, 890, 270);

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
        jButton10.setBounds(710, 270, 180, 40);

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Customer Bill Register (Opening Balance)");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 400, 30);

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
        closebutton.setBounds(320, 360, 130, 50);

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
        savebutton.setBounds(60, 310, 130, 50);

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
        viewbutton.setBounds(60, 360, 130, 50);

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
        deletebutton.setBounds(60, 310, 130, 50);

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
        clearbutton.setBounds(190, 360, 130, 50);

        jLabel23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel23.setText("Cust_Id");
        getContentPane().add(jLabel23);
        jLabel23.setBounds(20, 70, 80, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Due Date");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(20, 200, 80, 30);

        h8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h8);
        h8.setBounds(100, 260, 350, 30);

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

        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h5);
        h5.setBounds(100, 170, 310, 30);

        jLabel27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel27.setText("Entry No");
        getContentPane().add(jLabel27);
        jLabel27.setBounds(20, 40, 80, 30);

        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h6);
        h6.setBounds(100, 200, 310, 30);

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
        prebutton.setBounds(190, 310, 130, 50);

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
        nextbutton.setBounds(320, 310, 130, 50);

        jLabel29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel29.setText("Remarks");
        getContentPane().add(jLabel29);
        jLabel29.setBounds(20, 260, 80, 30);

        jCalendarButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
        jCalendarButton1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton1PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton1);
        jCalendarButton1.setBounds(410, 200, 40, 30);

        h3.setEditable(false);
        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h3FocusGained(evt);
            }
        });
        getContentPane().add(h3);
        h3.setBounds(100, 100, 350, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                h2FocusLost(evt);
            }
        });
        h2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h2KeyPressed(evt);
            }
        });
        getContentPane().add(h2);
        h2.setBounds(100, 70, 350, 30);

        hh.setEditable(false);
        hh.setColumns(20);
        hh.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        hh.setForeground(new java.awt.Color(255, 0, 51));
        hh.setRows(5);
        jScrollPane1.setViewportView(hh);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(470, 40, 480, 540);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText("Cust_Name");
        getContentPane().add(jLabel19);
        jLabel19.setBounds(20, 100, 80, 30);

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setText("Bill No");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(20, 140, 80, 30);

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText("Amount");
        getContentPane().add(jLabel22);
        jLabel22.setBounds(20, 230, 80, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        getContentPane().add(h4);
        h4.setBounds(100, 140, 350, 30);

        h7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h7FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                h7FocusLost(evt);
            }
        });
        h7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h7ActionPerformed(evt);
            }
        });
        getContentPane().add(h7);
        h7.setBounds(100, 230, 350, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Bill Date");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(20, 170, 80, 30);

        jCalendarButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
        jCalendarButton2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton2PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton2);
        jCalendarButton2.setBounds(410, 170, 40, 30);

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

        h2.requestFocus();
    }//GEN-LAST:event_h1FocusGained

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

        if (jTable2.getRowCount() > 0) {
            String cid = jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString();
            h2.setText(cid);
            get_patient_details(cid);
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
                h2.setText(cid);
                get_patient_details(cid);
            }
            cname_list.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cname_list.dispose();
            h2.requestFocus();
        }
    }//GEN-LAST:event_jTable2KeyPressed

    private void jScrollPane4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane4FocusLost

        cname_list.dispose();
    }//GEN-LAST:event_jScrollPane4FocusLost

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

        cname_list.setVisible(false);
        cname_list.dispose();
        h6.requestFocus();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void cname_listFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cname_listFocusLost

        cname_list.dispose();
    }//GEN-LAST:event_cname_listFocusLost

    private void prebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prebuttonActionPerformed

        try {
            String query;
            String billno = h1.getText();
            if (billno.equalsIgnoreCase("--")) {
                query = "select max(sno) from cust_bill";
            } else {
                query = "select sno from cust_bill where sno < '" + billno + "' order by sno desc limit 1";
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
            query = "select sno from cust_bill where sno > '" + billno + "' order by sno limit 1";
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

    private void jCalendarButton1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendarButton1PropertyChange

        try {
            if (evt.getNewValue() instanceof Date) {
                String ses = evt.getNewValue().toString();
                Date nm = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(ses);
                String date = (new SimpleDateFormat("dd/MM/yyyy").format(nm));
                h5.setText(date);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jCalendarButton1PropertyChange

    private void h2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_h2KeyPressed

        cname_list.requestFocus();
        jTable2.requestFocus();
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            h2.requestFocus();
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
                cname_list.setSize(890, 310);
                cname_list.setVisible(true);
                String query = "select cid,cname,city from cust where cname like '" + h2.getText() + "%' order by cname limit 500";
                r = util.doQuery(query);
                while (r.next()) {
                    s3.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3)});
                }
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_h2KeyPressed

    private void h2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h2FocusLost
        h2.setText(h2.getText().toUpperCase());
        if (hh.getText().equals("")) {
            get_patient_details(h2.getText());
        }
    }//GEN-LAST:event_h2FocusLost

    private void h3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h3FocusGained
        h2.setEnabled(false);
    }//GEN-LAST:event_h3FocusGained

    private void h7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h7FocusLost

    }//GEN-LAST:event_h7FocusLost

    private void h7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h7ActionPerformed

    }//GEN-LAST:event_h7ActionPerformed

    private void h7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h7FocusGained
    }//GEN-LAST:event_h7FocusGained

    private void jCalendarButton2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendarButton2PropertyChange
        try {
            if (evt.getNewValue() instanceof Date) {
                String ses = evt.getNewValue().toString();
                Date nm = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(ses);
                String date = (new SimpleDateFormat("dd/MM/yyyy").format(nm));
                h6.setText(date);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jCalendarButton2PropertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JDialog cname_list;
    private javax.swing.JButton deletebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h2;
    private javax.swing.JTextField h3;
    private javax.swing.JComboBox h4;
    private javax.swing.JTextField h5;
    private javax.swing.JTextField h6;
    private javax.swing.JTextField h7;
    private javax.swing.JTextField h8;
    private javax.swing.JTextArea hh;
    private javax.swing.JButton jButton10;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton1;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton2;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable2;
    private javax.swing.JButton nextbutton;
    private javax.swing.JButton prebutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
