package menupack;

import com.selrom.db.DataUtil;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.Window;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class setting_bill extends javax.swing.JInternalFrame {

    DataUtil util = null;

    final void button_short() {
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        titlelablel.setText("<html><u>Setting</u></html>");
    }

    void save() {
        try {

            if (h1.getText().equals("")) {
                h1.setText(".");
            }
            if (h2.getText().equals("")) {
                h2.setText(".");
            }
            if (h3.getText().equals("")) {
                h3.setText(".");
            }
            if (h4.getText().equals("")) {
                h4.setText(".");
            }
            if (h5.getText().equals("")) {
                h5.setText(".");
            }
            if (h6.getText().equals("")) {
                h6.setText(".");
            }
            if (h7.getText().equals("")) {
                h7.setText(".");
            }
            if (h9.getText().equals("")) {
                h9.setText(".");
            }
            if (h11.getText().equals("")) {
                h11.setText(".");
            }
            if (h12.getText().equals("")) {
                h12.setText(".");
            }
            if (h13.getText().equals("")) {
                h13.setText(".");
            }
            if (h14.getText().equals("")) {
                h14.setText(".");
            }
            if (h15.getText().equals("")) {
                h15.setText(".");
            }

            if (h16.getText().equals("")) {
                h16.setText("" + 0);
            }
            if (h17.getText().equals("")) {
                h17.setText("" + 0);
            }
            if (h18.getText().equals("")) {
                h18.setText("" + 11);
            }
            if (h19.getText().equals("")) {
                h19.setText("PRN");
            }

            if (h26.getText().equals("")) {
                h26.setText("" + 0);
            }
            if (h27.getText().equals("")) {
                h27.setText("" + 0);
            }
            if (h22.getText().equals("")) {
                h22.setText(".");
            }
            if (h40.getText().equals("")) {
                h40.setText(".");
            }
            if (h41.getText().equals("")) {
                h41.setText(".");
            }
            if (upiIdField.getText().equals("")) {
                h41.setText(".");
            }
            int as = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            String cname = h1.getText();
            String add1 = h2.getText();
            String add2 = h3.getText();
            String add3 = h4.getText();
            String add4 = h5.getText();
            String state = h6.getText();
            String scode = h7.getText();
            String ttype = h8.getSelectedItem().toString();
            String letter = h9.getText();
            String bformat = h10.getSelectedItem().toString();
            String bhead = h11.getText();
            String sms1 = h12.getText();
            String sms2 = h13.getText();
            String sms3 = h14.getText();
            String sms4 = h15.getText();
            String lmargin = h16.getText();
            String maxdis = h17.getText();
            String lines = h18.getText();
            String port = h19.getText();
            String rprice = h20.getSelectedItem().toString();
            String wprice = h21.getSelectedItem().toString();
            String stock_bill = h23.getSelectedItem().toString();
            String print_name = h24.getSelectedItem().toString();
            String less_prate = h25.getSelectedItem().toString();
            String rdis = h26.getText();
            String wdis = h27.getText();
            String entry_mode = h28.getSelectedItem().toString();
            String bformat1 = h29.getSelectedItem().toString();
            String pur_rate_edit = h30.getSelectedItem().toString();
            String cust_details = h31.getSelectedItem().toString();
            String dsales = h32.getSelectedItem().toString();

            String ehead = h22.getText();
            String eformat1 = h33.getSelectedItem().toString();
            String eformat2 = h34.getSelectedItem().toString();
            String dplaces = dpl.getSelectedItem().toString();
            String round = roundl.getSelectedItem().toString();
            String cur_name = h40.getText();
            String cur_symbol = h41.getText();
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd-MM-yyy hh:mm:ss a");
            String last = g.format(d);
            String upiId = upiIdField.getText();
            String batch = batchCombo.getSelectedItem() != null ? batchCombo.getSelectedItem().toString() : "Batch";
            int exp = expCheckbox.isSelected() ? 1 : 0;
            int mfg = mfgCheckBox.isSelected() ? 1 : 0;
            String weighingButtonValue = weighingButton.isSelected() ? "Yes" : "No";
            String logoPath = hLogo.getText().replace("\\", "\\\\");

            boolean selva = false;
            String query = "select distinct cname from setting_bill";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                selva = true;
            }

            java.sql.Connection conn = util.getConnection();
            PreparedStatement ps;
            int count = 0;

            if (selva == false) {
                String insertQuery = "INSERT INTO setting_bill (cname, add1, add2, add3, add4, state, scode, ttype, letter, bformat, bhead, sms1, sms2, sms3, sms4, lmargin, maxdis, lines1, port, rprice, wprice, stock_bill, print_name, less_prate, last, rdis, wdis, entry_mode, bformat1, pur_rate_edit, cust_details, dsales, ehead, eformat1, eformat2, hmany, round, cur_name, cur_symbol, upi_id, batch, exp, mfg, weighing_button, logo_path) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(insertQuery);
                ps.setString(1, cname);
                ps.setString(2, add1);
                ps.setString(3, add2);
                ps.setString(4, add3);
                ps.setString(5, add4);
                ps.setString(6, state);
                ps.setString(7, scode);
                ps.setString(8, ttype);
                ps.setString(9, letter);
                ps.setString(10, bformat);
                ps.setString(11, bhead);
                ps.setString(12, sms1);
                ps.setString(13, sms2);
                ps.setString(14, sms3);
                ps.setString(15, sms4);
                ps.setDouble(16, Double.parseDouble(lmargin));
                ps.setDouble(17, Double.parseDouble(maxdis));
                ps.setInt(18, Integer.parseInt(lines));
                ps.setString(19, port);
                ps.setString(20, rprice);
                ps.setString(21, wprice);
                ps.setString(22, stock_bill);
                ps.setString(23, print_name);
                ps.setString(24, less_prate);
                ps.setString(25, last);
                ps.setDouble(26, Double.parseDouble(rdis));
                ps.setDouble(27, Double.parseDouble(wdis));
                ps.setString(28, entry_mode);
                ps.setString(29, bformat1);
                ps.setString(30, pur_rate_edit);
                ps.setString(31, cust_details);
                ps.setString(32, dsales);
                ps.setString(33, ehead);
                ps.setString(34, eformat1);
                ps.setString(35, eformat2);
                ps.setInt(36, Integer.parseInt(dplaces));
                ps.setString(37, round);
                ps.setString(38, cur_name);
                ps.setString(39, cur_symbol);
                ps.setString(40, upiId);
                ps.setString(41, batch);
                ps.setInt(42, exp);
                ps.setInt(43, mfg);
                ps.setString(44, weighingButtonValue);
                ps.setString(45, logoPath);
            } else {
                String updateQuery = "UPDATE setting_bill SET cname=?, add1=?, add2=?, add3=?, add4=?, state=?, scode=?, ttype=?, letter=?, bformat=?, bhead=?, sms1=?, sms2=?, sms3=?, sms4=?, lmargin=?, maxdis=?, lines1=?, port=?, rprice=?, wprice=?, stock_bill=?, print_name=?, less_prate=?, rdis=?, wdis=?, entry_mode=?, bformat1=?, pur_rate_edit=?, cust_details=?, dsales=?, ehead=?, eformat1=?, eformat2=?, hmany=?, round=?, cur_name=?, cur_symbol=?, upi_id=?, batch=?, exp=?, mfg=?, weighing_button=?, logo_path=?";
                ps = conn.prepareStatement(updateQuery);
                ps.setString(1, cname);
                ps.setString(2, add1);
                ps.setString(3, add2);
                ps.setString(4, add3);
                ps.setString(5, add4);
                ps.setString(6, state);
                ps.setString(7, scode);
                ps.setString(8, ttype);
                ps.setString(9, letter);
                ps.setString(10, bformat);
                ps.setString(11, bhead);
                ps.setString(12, sms1);
                ps.setString(13, sms2);
                ps.setString(14, sms3);
                ps.setString(15, sms4);
                ps.setDouble(16, Double.parseDouble(lmargin));
                ps.setDouble(17, Double.parseDouble(maxdis));
                ps.setInt(18, Integer.parseInt(lines));
                ps.setString(19, port);
                ps.setString(20, rprice);
                ps.setString(21, wprice);
                ps.setString(22, stock_bill);
                ps.setString(23, print_name);
                ps.setString(24, less_prate);
                ps.setDouble(25, Double.parseDouble(rdis));
                ps.setDouble(26, Double.parseDouble(wdis));
                ps.setString(27, entry_mode);
                ps.setString(28, bformat1);
                ps.setString(29, pur_rate_edit);
                ps.setString(30, cust_details);
                ps.setString(31, dsales);
                ps.setString(32, ehead);
                ps.setString(33, eformat1);
                ps.setString(34, eformat2);
                ps.setInt(35, Integer.parseInt(dplaces));
                ps.setString(36, round);
                ps.setString(37, cur_name);
                ps.setString(38, cur_symbol);
                ps.setString(39, upiId);
                ps.setString(40, batch);
                ps.setInt(41, exp);
                ps.setInt(42, mfg);
                ps.setString(43, weighingButtonValue);
                ps.setString(44, logoPath);
            }

            count = ps.executeUpdate();

            if (count > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Saved Successfully</h1></html>", "Saved", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    void clear() {
        h1.setText("");
        h2.setText("");
        h3.setText("");
        h4.setText("");
        h5.setText("");
        h6.setText("");
        h7.setText("");
        h11.setText("");
        h12.setText("");
        h13.setText("");
        h14.setText("");
        h15.setText("");
        h16.setText("");
        h17.setText("");
        h18.setText("");
        h19.setText("");
        h26.setText("");
        h27.setText("");
        h22.setText("");
        h40.setText("");
        h41.setText("");
        upiIdField.setText("");
        batchCombo.setSelectedItem("Batch");
        expCheckbox.setSelected(false);
        mfgCheckBox.setSelected(false);
        weighingButton.setSelected(false);
        hLogo.setText("");
    }

    void view() {
        try {
            String query = "select distinct cname,add1,add2,add3,add4,state,scode,ttype,letter,bformat,bhead,sms1,sms2,sms3,sms4,lmargin,maxdis,lines1,port,rprice,wprice,stock_bill,print_name,less_prate,rdis,wdis,entry_mode,bformat1,pur_rate_edit,cust_details,dsales,ehead,eformat1,eformat2,hmany,round,cur_name,cur_symbol, upi_id, batch, exp, mfg, weighing_button, logo_path from setting_bill";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                h1.setText(r.getString(1));
                h2.setText(r.getString(2));
                h3.setText(r.getString(3));
                h4.setText(r.getString(4));
                h5.setText(r.getString(5));
                h6.setText(r.getString(6));
                h7.setText(r.getString(7));
                h8.setSelectedItem(r.getString(8));
                h9.setText(r.getString(9));
                h10.setSelectedItem(r.getString(10));
                h11.setText(r.getString(11));
                h12.setText(r.getString(12));
                h13.setText(r.getString(13));
                h14.setText(r.getString(14));
                h15.setText(r.getString(15));
                h16.setText(r.getString(16));
                h17.setText(r.getString(17));
                h18.setText(r.getString(18));
                h19.setText(r.getString(19));

                h20.setSelectedItem(r.getString(20));
                h21.setSelectedItem(r.getString(21));
                h23.setSelectedItem(r.getString(22));
                h24.setSelectedItem(r.getString(23));
                h25.setSelectedItem(r.getString(24));

                h26.setText(r.getString(25));
                h27.setText(r.getString(26));
                h28.setSelectedItem(r.getString(27));
                h29.setSelectedItem(r.getString(28));
                h30.setSelectedItem(r.getString(29));
                h31.setSelectedItem(r.getString(30));
                h32.setSelectedItem(r.getString(31));
                h22.setText(r.getString(32));
                h33.setSelectedItem(r.getString(33));
                h34.setSelectedItem(r.getString(34));
                dpl.setSelectedItem(r.getString(35));
                roundl.setSelectedItem(r.getString(36));
                h40.setText(r.getString(37));
                h41.setText(r.getString(38));
                upiIdField.setText(r.getString(39));
                batchCombo.setSelectedItem(r.getString(40));
                expCheckbox.setSelected(r.getBoolean("exp"));
                mfgCheckBox.setSelected(r.getBoolean("mfg"));
                weighingButton.setSelected("Yes".equalsIgnoreCase(r.getString("weighing_button")));
                hLogo.setText(r.getString("logo_path"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public setting_bill(DataUtil util) {
        initComponents();
        setTitle("Setting");
        this.setSize(866, 700);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
        this.util = util;
        button_short();
        view();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        savebutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        h15 = new javax.swing.JTextField();
        h1 = new javax.swing.JTextField();
        h2 = new javax.swing.JTextField();
        h3 = new javax.swing.JTextField();
        h4 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        h5 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        h8 = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        h19 = new javax.swing.JTextField();
        h12 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        h13 = new javax.swing.JTextField();
        h14 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        h9 = new javax.swing.JTextField();
        h16 = new javax.swing.JTextField();
        h18 = new javax.swing.JTextField();
        h17 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        h10 = new javax.swing.JComboBox<>();
        h25 = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        h20 = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        h21 = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        h24 = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        h23 = new javax.swing.JComboBox<>();
        h7 = new javax.swing.JTextField();
        h6 = new javax.swing.JTextField();
        h26 = new javax.swing.JTextField();
        h27 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        h28 = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        h29 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        h30 = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        h31 = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        h32 = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        h11 = new javax.swing.JTextField();
        h22 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        h33 = new javax.swing.JComboBox<>();
        h34 = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        dpl = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        roundl = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        h41 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        h40 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        upiIdField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        batchCombo = new javax.swing.JComboBox<>();
        mfgCheckBox = new javax.swing.JCheckBox();
        expCheckbox = new javax.swing.JCheckBox();
        weighingButton = new javax.swing.JCheckBox();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Setting");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 260, 30);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Company Name");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 40, 120, 30);

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
        savebutton.setBounds(440, 610, 130, 50);

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
        clearbutton.setBounds(570, 610, 130, 50);

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
        closebutton.setBounds(700, 610, 130, 50);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Estimate Head");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(500, 250, 110, 30);

        h15.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        getContentPane().add(h15);
        h15.setBounds(490, 340, 340, 30);

        h1.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        getContentPane().add(h1);
        h1.setBounds(130, 40, 700, 30);

        h2.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(130, 70, 700, 30);

        h3.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        getContentPane().add(h3);
        h3.setBounds(130, 100, 360, 30);

        h4.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        getContentPane().add(h4);
        h4.setBounds(490, 100, 340, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("GSTIN No");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(20, 130, 110, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Entry Mode");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(20, 460, 110, 30);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("State");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(20, 160, 110, 30);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Currency");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(500, 130, 110, 30);

        h5.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        getContentPane().add(h5);
        h5.setBounds(130, 130, 360, 30);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Tax Type");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(20, 190, 110, 30);

        h8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Inclusive of Tax", "Exclusive of Tax", "Inclusive Model-II", "No Tax" }));
        getContentPane().add(h8);
        h8.setBounds(130, 190, 200, 30);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("Estimate Format");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(20, 280, 110, 30);

        h19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h19);
        h19.setBounds(770, 370, 60, 30);

        h12.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        getContentPane().add(h12);
        h12.setBounds(130, 310, 360, 30);

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("Bill Head");
        getContentPane().add(jLabel18);
        jLabel18.setBounds(20, 250, 110, 30);

        h13.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        getContentPane().add(h13);
        h13.setBounds(490, 310, 340, 30);

        h14.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        getContentPane().add(h14);
        h14.setBounds(130, 340, 360, 30);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText("Bill Messages");
        getContentPane().add(jLabel19);
        jLabel19.setBounds(20, 310, 110, 30);

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setText("Low Margin");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(20, 370, 110, 30);

        h9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h9);
        h9.setBounds(410, 190, 80, 30);

        h16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h16);
        h16.setBounds(130, 370, 200, 30);

        h18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h18);
        h18.setBounds(610, 370, 80, 30);

        h17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h17);
        h17.setBounds(400, 370, 90, 30);

        jLabel23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel23.setText("Lines Count");
        getContentPane().add(jLabel23);
        jLabel23.setBounds(500, 370, 110, 30);

        jLabel24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel24.setText("Bill < Pur.Rate");
        getContentPane().add(jLabel24);
        jLabel24.setBounds(500, 490, 110, 30);

        h10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        String[] formats = new String[] { "Sales 3-Inch (Thermal)", "Sales 3-Inch MRP (Thermal)", "Sales 3-Inch Short (Thermal)", "Sales 3-Inch NoGST (Thermal)", "Sales 4-Inch (Thermal)", "Sales 4-Inch MRP (Thermal)", "Sales 2-Inch MRP (Thermal)", "Sales A4", "Sales A5", "Dot Matrix USB" };
        h10.setModel(new javax.swing.DefaultComboBoxModel<>(formats));
        getContentPane().add(h10);
        h10.setBounds(130, 220, 360, 30);

        h25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h25.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No", "Yes" }));
        getContentPane().add(h25);
        h25.setBounds(610, 490, 220, 30);

        jLabel25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel25.setText("Rate Edit on Pur.");
        getContentPane().add(jLabel25);
        jLabel25.setBounds(20, 430, 110, 30);

        h20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h20.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MRP Price", "MRP- Discount %", "Purchase Price + Margin %", "Manual" }));
        getContentPane().add(h20);
        h20.setBounds(130, 400, 200, 30);

        jLabel26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel26.setText("Customer Details");
        getContentPane().add(jLabel26);
        jLabel26.setBounds(500, 430, 110, 30);

        h21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h21.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MRP Price", "MRP- Discount %", "Purchase Price + Margin %", "Manual" }));
        getContentPane().add(h21);
        h21.setBounds(610, 400, 160, 30);

        jLabel29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel29.setText("Print Name");
        getContentPane().add(jLabel29);
        jLabel29.setBounds(20, 490, 110, 30);

        h24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h24.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Same as Item Name", "Different Name" }));
        getContentPane().add(h24);
        h24.setBounds(130, 490, 360, 30);

        jLabel30.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel30.setText("Bill Without Stock");
        getContentPane().add(jLabel30);
        jLabel30.setBounds(500, 460, 110, 30);

        h23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h23.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No", "Yes" }));
        getContentPane().add(h23);
        h23.setBounds(610, 460, 220, 30);

        h7.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        getContentPane().add(h7);
        h7.setBounds(410, 160, 80, 30);

        h6.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        getContentPane().add(h6);
        h6.setBounds(130, 160, 200, 30);

        h26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h26);
        h26.setBounds(330, 400, 160, 30);

        h27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h27);
        h27.setBounds(770, 400, 60, 30);

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText(" Amount Round");
        getContentPane().add(jLabel22);
        jLabel22.setBounds(330, 460, 100, 30);

        h28.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h28.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Auto", "Manual" }));
        getContentPane().add(h28);
        h28.setBounds(130, 460, 200, 30);

        jLabel28.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel28.setText("Default Sales");
        getContentPane().add(jLabel28);
        jLabel28.setBounds(500, 190, 110, 30);

        h29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h29.setModel(new javax.swing.DefaultComboBoxModel<>(formats));
        getContentPane().add(h29);
        h29.setBounds(610, 220, 220, 30);

        jLabel27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel27.setText("Address");
        getContentPane().add(jLabel27);
        jLabel27.setBounds(20, 70, 110, 30);

        jLabel31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel31.setText("Retail Price");
        getContentPane().add(jLabel31);
        jLabel31.setBounds(20, 400, 110, 30);

        h30.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h30.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No", "Yes" }));
        getContentPane().add(h30);
        h30.setBounds(130, 430, 200, 30);

        jLabel32.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel32.setText("Wholesale Price");
        getContentPane().add(jLabel32);
        jLabel32.setBounds(500, 400, 110, 30);

        h31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h31.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));
        getContentPane().add(h31);
        h31.setBounds(610, 430, 220, 30);

        jLabel33.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel33.setText("Port Name");
        getContentPane().add(jLabel33);
        jLabel33.setBounds(700, 370, 70, 30);

        jLabel34.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel34.setText(" Bill Prefix");
        getContentPane().add(jLabel34);
        jLabel34.setBounds(330, 190, 80, 30);

        h32.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h32.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Retail", "Wholesale" }));
        getContentPane().add(h32);
        h32.setBounds(610, 190, 220, 30);

        jLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel20.setText("Wholesale Est.");
        getContentPane().add(jLabel20);
        jLabel20.setBounds(500, 280, 110, 30);

        h11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h11);
        h11.setBounds(130, 250, 360, 30);

        h22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h22);
        h22.setBounds(610, 250, 220, 30);

        jLabel35.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel35.setText("Retail Bill Format");
        getContentPane().add(jLabel35);
        jLabel35.setBounds(20, 220, 110, 30);

        h33.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h33.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thermal", "Thermal MRP", "Thermal Short Bill", "Thermal 4-Inch", "Thermal 4-Inch MRP", "A4", "A5" }));
        getContentPane().add(h33);
        h33.setBounds(130, 280, 360, 30);

        h34.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h34.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thermal", "Thermal MRP", "Thermal Short Bill", "Thermal 4-Inch", "Thermal 4-Inch MRP", "A4", "A5" }));
        getContentPane().add(h34);
        h34.setBounds(610, 280, 220, 30);

        jLabel36.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel36.setText("Wholesale Bill");
        getContentPane().add(jLabel36);
        jLabel36.setBounds(500, 220, 110, 30);

        jLabel37.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel37.setText(" Max.Dis%");
        getContentPane().add(jLabel37);
        jLabel37.setBounds(330, 370, 70, 30);

        dpl.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        dpl.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "3", "4" }));
        getContentPane().add(dpl);
        dpl.setBounds(430, 430, 60, 30);

        jLabel38.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel38.setText(" Decimal Places");
        getContentPane().add(jLabel38);
        jLabel38.setBounds(330, 430, 110, 30);

        roundl.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        roundl.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));
        getContentPane().add(roundl);
        roundl.setBounds(430, 460, 60, 30);

        jLabel39.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel39.setText("Currency Symbol");
        getContentPane().add(jLabel39);
        jLabel39.setBounds(500, 160, 110, 30);

        h41.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        getContentPane().add(h41);
        h41.setBounds(610, 160, 220, 30);

        jLabel40.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel40.setText(" State Code");
        getContentPane().add(jLabel40);
        jLabel40.setBounds(330, 160, 80, 30);

        h40.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        getContentPane().add(h40);
        h40.setBounds(610, 130, 220, 30);

        jLabel41.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel41.setText("UPI Id");
        getContentPane().add(jLabel41);
        jLabel41.setBounds(20, 520, 110, 30);

        upiIdField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upiIdFieldActionPerformed(evt);
            }
        });
        getContentPane().add(upiIdField);
        upiIdField.setBounds(130, 520, 360, 30);

        jButton1.setBackground(new java.awt.Color(204, 204, 255));
        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 0, 51));
        jButton1.setText("Barcode Settings");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(500, 520, 160, 30);


        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Batch/Size");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 550, 110, 30);

        batchCombo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        batchCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Batch", "Size" }));
        getContentPane().add(batchCombo);
        batchCombo.setBounds(130, 550, 72, 30);

        mfgCheckBox.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        mfgCheckBox.setText("Mfg Date");
        getContentPane().add(mfgCheckBox);
        mfgCheckBox.setBounds(220, 550, 80, 30);

        expCheckbox.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        expCheckbox.setText("Exp Date");
        expCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expCheckboxActionPerformed(evt);
            }
        });
        getContentPane().add(expCheckbox);
        expCheckbox.setBounds(340, 550, 80, 30);

        weighingButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        weighingButton.setText("Weighing Button");
        weighingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weighingButtonActionPerformed(evt);
            }
        });
        getContentPane().add(weighingButton);
        weighingButton.setBounds(20, 580, 150, 30);

        jLabel42 = new javax.swing.JLabel();
        jLabel42.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel42.setText("Logo Path");
        getContentPane().add(jLabel42);
        jLabel42.setBounds(20, 610, 110, 30);

        hLogo = new javax.swing.JTextField();
        hLogo.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        getContentPane().add(hLogo);
        hLogo.setBounds(130, 610, 250, 30);

        btnBrowseLogo = new javax.swing.JButton();
        btnBrowseLogo.setText("...");
        btnBrowseLogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "jpeg"));
                int result = fileChooser.showOpenDialog(setting_bill.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    hLogo.setText(selectedFile.getAbsolutePath());
                }
            }
        });
        getContentPane().add(btnBrowseLogo);
        btnBrowseLogo.setBounds(390, 610, 40, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_closebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        save();

    }//GEN-LAST:event_savebuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        clear();
    }//GEN-LAST:event_clearbuttonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Container parent = getTopLevelAncestor();
        new barcodepack.jasper.BarcodeTemplateManager((parent instanceof Window) ? (Window) parent : null).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void upiIdFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upiIdFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_upiIdFieldActionPerformed

    private void expCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expCheckboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_expCheckboxActionPerformed

    private void weighingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weighingButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_weighingButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> batchCombo;
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JComboBox<String> dpl;
    private javax.swing.JCheckBox expCheckbox;
    private javax.swing.JTextField h1;
    private javax.swing.JComboBox<String> h10;
    private javax.swing.JTextField h11;
    private javax.swing.JTextField h12;
    private javax.swing.JTextField h13;
    private javax.swing.JTextField h14;
    private javax.swing.JTextField h15;
    private javax.swing.JTextField h16;
    private javax.swing.JTextField h17;
    private javax.swing.JTextField h18;
    private javax.swing.JTextField h19;
    private javax.swing.JTextField h2;
    private javax.swing.JComboBox<String> h20;
    private javax.swing.JComboBox<String> h21;
    private javax.swing.JTextField h22;
    private javax.swing.JComboBox<String> h23;
    private javax.swing.JComboBox<String> h24;
    private javax.swing.JComboBox<String> h25;
    private javax.swing.JTextField h26;
    private javax.swing.JTextField h27;
    private javax.swing.JComboBox<String> h28;
    private javax.swing.JComboBox<String> h29;
    private javax.swing.JTextField h3;
    private javax.swing.JComboBox<String> h30;
    private javax.swing.JComboBox<String> h31;
    private javax.swing.JComboBox<String> h32;
    private javax.swing.JComboBox<String> h33;
    private javax.swing.JComboBox<String> h34;
    private javax.swing.JTextField h4;
    private javax.swing.JTextField h40;
    private javax.swing.JTextField h41;
    private javax.swing.JTextField h5;
    private javax.swing.JTextField h6;
    private javax.swing.JTextField h7;
    private javax.swing.JComboBox<String> h8;
    private javax.swing.JTextField h9;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JCheckBox mfgCheckBox;
    private javax.swing.JComboBox<String> roundl;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JTextField upiIdField;
    private javax.swing.JCheckBox weighingButton;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JTextField hLogo;
    private javax.swing.JButton btnBrowseLogo;
    // End of variables declaration//GEN-END:variables
}
