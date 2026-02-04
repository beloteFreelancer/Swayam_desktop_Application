package pospack;

import Utils.ColorConstants;
import Utils.CompanySettingUtil;
import Utils.POSWeighing;
import Utils.UPIPaymentDialog;
import com.selrom.db.DataUtil;
import itempack.item_master;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import menupack.menu_form;
import smspack.SMS_Sender_Single;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public final class sales extends javax.swing.JInternalFrame {

    DataUtil util = null;
    DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
    DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
    boolean selvagates = false, customer_selection = false;
    String username = "", utype = "", drive = "", folder = "", version = "", retail_format = "", wholesale_format = "";
    String scode = "", sname = "", ttype = "", stock_bill, less_prate = "", tax = "Local", entry_mode = "Auto",
            dsales = "Retail", cust_details = "";
    double max_rdis = 0, max_wdis = 0;
    int hmany = 2, hmany1 = 3;
    String sms_alert = "", sender = "", smsuser = "", smspass = "", smsfoot1 = "", smsfoot2 = "", alter_sms = "",
            management_no1 = "", management_no2 = "";
    String points_option = "No", round_option = "Yes";
    String weighing_button_visible = "Yes";
    double hmuch_points = 0, points_for = 0;
    // int sno_num=1;
    String mainUnit = "";
    String subUnit = "";
    String unitConv = "";
    double itemRate = -1;

    final void get_defaults() {
        try {
            String query = "select scode,ttype,stock_bill,less_prate,maxdis,wdis,entry_mode,state,bformat,bformat1,dsales,cust_details,hmany,round,weighing_button from setting_bill";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                scode = r.getString(1);
                ttype = r.getString(2);
                stock_bill = r.getString(3);
                less_prate = r.getString(4);
                max_rdis = r.getDouble(5);
                max_wdis = r.getDouble(5);
                entry_mode = r.getString(7);
                sname = r.getString(8);
                retail_format = r.getString(9);
                wholesale_format = r.getString(10);
                dsales = r.getString(11);
                cust_details = r.getString(12);
                hmany = r.getInt(13);
                round_option = r.getString(14);
                weighing_button_visible = r.getString(15);
            }

            if (weighing_button_visible.equalsIgnoreCase("Yes")) {
                weighingButton.setVisible(true);
            } else {
                weighingButton.setVisible(false);
            }

            drive = "";
            folder = Utils.AppConfig.getAppPath();
            // version and utype are now passed as constructor parameters
            h17.requestFocusInWindow();

            query = "select sms_alert,sender,user,pass,smsfoot1,smsfoot2,alter_sms,mobile1,mobile2 from setting_sms";
            r = util.doQuery(query);
            while (r.next()) {
                sms_alert = r.getString(1);
                sender = r.getString(2);
                smsuser = r.getString(3);
                smspass = r.getString(4);
                smsfoot1 = r.getString(5);
                smsfoot2 = r.getString(6);
                alter_sms = r.getString(7);
                management_no1 = r.getString(8);
                management_no2 = r.getString(9);
            }

            query = "select points_option,hmuch,pfor from setting_points";
            r = util.doQuery(query);
            while (r.next()) {
                points_option = r.getString(1);
                hmuch_points = r.getInt(2);
                points_for = r.getInt(3);
            }
            if (dsales.equals("Wholesale")) {
                pricel.setText("Wholesale");
                pricel.setForeground(Color.red);
                jLabel28.setForeground(Color.red);
                h16.setSelectedItem("Credit");
                h24.requestFocus();
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

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
            return column == 3 || column == 4 || column == 6 || column == 8;
        }
    }

    sample2 s2 = new sample2();
    sample2 s3 = new sample2();
    sample2 s4 = new sample2();
    sample2 s5 = new sample2();

    final void button_short() {
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        viewbutton.setText("<html><b>View</b><br>(Alt+V)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        prebutton.setText("<html><b>Last Entry</b><br>(Alt+R)</h6><html>");
        nextbutton.setText("<html><b>Next Entry</b><br>(Alt+N)</h6><html>");
        applybutton.setText("<html><b>Apply</b><br>(Alt+A)</h6><html>");
        removebutton.setText("<html><b>Remove</b><br>(Alt+M)</h6><html>");
        draftbutton.setText("<html><b>Draft</b><br>(Alt+F)</h6><html>");
        loadbutton.setText("<html><b>Load Draft</b><br>(Alt+K)</h6><html>");
        printbutton.setText("<html><b>Re-Print</b><br>(Alt+P)</h6><html>");
        alterbutton.setText("<html><b>Alter</b><br>(Alt+U)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");

        tenderbutton.setText("<html><b>Tender</b><br>(Alt+T)</h6><html>");
        holdbutton.setText("<html><b>Hold</b><br>(Alt+H)</h6><html>");
        retrivebutton.setText("<html><b>Retrive</b><br>(Alt+J)</h6><html>");
        retailbutton.setText("<html>Retail Price  (Alt+Z)</h6><html>");
        wholesalebutton.setText("<html>Wholesale Price  (Alt+X)</h6><html>");

        get_date_time();

        this.setSize(1307, 663);
        java.net.URL iconUrl = ClassLoader.getSystemResource("/images/icon.png");
        if (iconUrl != null) {
            ImageIcon icon = new ImageIcon(iconUrl);
            this.setFrameIcon(icon);
        }
    }

    void get_date_time() {
        Date d = new Date();
        SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
        h2.setText(g.format(d));
        h3.setText(g1.format(d));
    }

    void display_terminal() {
        try {
            try (FileInputStream m = new FileInputStream(folder + "/Config_Files/terminal.properties")) {
                Properties p = new Properties(null);
                p.load(m);
                h4.setText(p.getProperty("location"));
                h5.setText(p.getProperty("terminal"));
                username = new menu_form().getUsername();
                h6.setText(username);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public final void load_items_table() {
        s2.addColumn("#");
        s2.addColumn("Item Code");
        s2.addColumn("Description");
        s2.addColumn("Qty");
        s2.addColumn("MRP");
        s2.addColumn("Net Rate");
        s2.addColumn("Price");
        s2.addColumn("Amount");
        s2.addColumn("Dis %");
        s2.addColumn("Dis Amt");
        s2.addColumn("Sub Total");
        s2.addColumn("Tax %");
        s2.addColumn("Tax Amount");
        s2.addColumn("Total");
        s2.addColumn("Unit Des");
        s2.addColumn("Bar Code");
        s2.addColumn("HSN");
        s2.addColumn("Tax");
        s2.addColumn("Entry");
        s2.addColumn("Cost_Rate");
        s2.addColumn("Profit");
        s2.addColumn("Print Name");
        s2.addColumn("Pur.Rate");
        s2.addColumn("RPrice");
        s2.addColumn("WPrice");
        s2.addColumn("Remarks");

        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
        jTable1.setModel(s2);
        jTable1.getColumnModel().getColumn(3).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(4).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(5).setCellRenderer(dtcr1);
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
        jTable1.getColumnModel().getColumn(19).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(20).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(21).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(22).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(23).setCellRenderer(dtcr1);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(115);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(355);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(145);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(90);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(10).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(11).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(12).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(13).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(14).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(15).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(16).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(17).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(18).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(19).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(20).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(21).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(22).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(23).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(24).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(25).setPreferredWidth(120);
        String Ta = "Arial";
        int Bold = 1, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
    }

    void get_billno() {
        try {
            int sno = 1;
            String query = "select max(billno) from sales";
            ResultSet r = util.doQuery(query);
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

    void get_item_details_using_barocde() {
        try {
            boolean selva = false;
            double rrate = 0, wrate = 0, price = 0;
            String iname = null, ino = null;
            String query = "select iname,rprice,wprice,ino from item where barcode='" + h17.getText() + "'";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                iname = r.getString(1);
                rrate = r.getDouble(2);
                wrate = r.getDouble(3);
                ino = r.getString(4);
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "<html><h1>Invalid Products Details!</h1></hmtl>", "Invalid",
                        JOptionPane.ERROR_MESSAGE);
                fields_clear();
                h17.requestFocus();
            } else {
                if (pricel.getText().equalsIgnoreCase("Retail")) {
                    price = rrate;
                } else if (pricel.getText().equalsIgnoreCase("Wholesale")) {
                    price = wrate;
                }
                String price2 = String.format("%." + hmany + "f", price);
                h18.setText(iname);
                h19.setText(price2);
                inol.setText(ino);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_stock() {
        try {
            double stock = 0;
            String query = "select sum(quan) from stock where barcode='" + h17.getText() + "'";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                stock = r.getDouble(1);
            }

            String stock2 = String.format("%." + hmany + "f", stock);
            String[] split3 = stock2.split("\\.");
            int what3 = Integer.parseInt(split3[1]);
            if (what3 <= 0) {
                stock2 = split3[0];
            }
            stockl.setText("Stock-in-Hand: " + stock2);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    void fields_clear() {
        h17.setText("");
        h18.setText("");
        h19.setText("");
        h20.setText("");
        inol.setText("");
        stockl.setText("");
        h29.setText("");
        unitCombo.setModel(new DefaultComboBoxModel<>());
    }

    void add_item(String barcode, String ino, String iname, double price, double quan, String remarks, String unit) {
        try {
            double netPrice = price;
            String hsn = ".", udes = "", iname1 = "";
            double disp = 0, prate = 0, mrp = 0, rprice = 0, wprice = 0;
            int taxp = 0;
            boolean selva = false;
            String query = "select hsn,udes,disp,taxp,iname1,prate,mrp,rprice,wprice from item where ino='" + ino
                    + "' and barcode='" + barcode + "' and iname='" + iname + "' ";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                hsn = r.getString(1);
                udes = r.getString(2);
                disp = r.getDouble(3);
                taxp = r.getInt(4);
                iname1 = r.getString(5);
                prate = r.getDouble(6);
                mrp = r.getDouble(7);
                rprice = r.getDouble(8);
                wprice = r.getDouble(9);
                selva = true;
            }
            if (unit != null && !unit.isBlank()) {
                udes = unit;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "Invalid Product Details!", "Invalid", JOptionPane.ERROR_MESSAGE);
                fields_clear();
                h17.requestFocus();
                return;
            }
            double stock = 0;
            String entry = "purchase";
            query = "select quan,entry from stock where barcode='" + barcode + "' and ino='" + ino + "' and iname='"
                    + iname + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                stock = r.getDouble(1);
                entry = r.getString(2);
            }

            double amount, disamt, sub, taxamt = 0, total;
            if (ttype.equalsIgnoreCase("Inclusive of Tax")) {
                double devide = 100 + taxp;
                devide = price * (100 / devide);
                taxamt = price - devide;
                price = price - taxamt;
                amount = price * quan;
                disamt = (disp * amount) / 100;
                sub = amount - disamt;
                taxamt = taxamt * quan;
                total = sub + taxamt;
            } else if (ttype.equalsIgnoreCase("Inclusive Model-II")) {
                amount = price * quan;
                disamt = (disp / 100) * amount;
                sub = amount - disamt;
                total = sub;
            } else if (ttype.equalsIgnoreCase("Exclusive of Tax")) {
                amount = price * quan;
                disamt = (disp * amount) / 100;
                sub = amount - disamt;
                taxamt = (taxp * sub) / 100;
                total = sub + taxamt;
            } else {
                amount = price * quan;
                disamt = (disp * amount) / 100;
                sub = amount - disamt;
                taxamt = 0;
                total = sub;
            }

            String price2 = String.format("%." + hmany + "f", price);
            String mrp2 = String.format("%." + hmany + "f", mrp);
            String amount2 = String.format("%." + hmany + "f", amount);
            String quan2 = String.format("%." + hmany1 + "f", quan);
            String netRate = String.format("%." + hmany1 + "f", netPrice);
            String[] split3 = quan2.split("\\.");
            int what3 = Integer.parseInt(split3[1]);
            if (what3 <= 0) {
                quan2 = split3[0];
            }

            boolean selvakumar = false;
            double old_quan = 0;
            int old_row = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String old_ino = jTable1.getValueAt(i, 1).toString();
                String old_barcode = jTable1.getValueAt(i, 15).toString();
                String old_entry = jTable1.getValueAt(i, 18).toString();

                if (ino.equalsIgnoreCase(old_ino) && barcode.equalsIgnoreCase(old_barcode)
                        && entry.equalsIgnoreCase(old_entry)) {
                    selvakumar = true;
                    old_row = i;
                    old_quan = Double.parseDouble(jTable1.getValueAt(i, 3).toString());
                }
            } // row counts ends for checking

            double nquan = quan + old_quan;
            if (nquan > stock && "No".equalsIgnoreCase(stock_bill)) {
                JOptionPane.showMessageDialog(this, "Stock-in-Hand: " + stock, "Invalid Stock",
                        JOptionPane.ERROR_MESSAGE);
                fields_clear();
                h17.requestFocus();
                return;
            }

            if (selvakumar == false) {
                s2.addRow(new Object[] { jTable1.getRowCount() + 1, ino, iname, quan2, mrp2, netRate, price2, amount2,
                        disp, disamt, sub, taxp, taxamt, total, udes, barcode, hsn, tax, entry, 0, 0, iname1, prate,
                        rprice, wprice, remarks });
                serial_num();
            } else {
                double net_quan = old_quan + quan;
                String quann2 = String.format("%." + hmany1 + "f", net_quan);
                String[] split4 = quann2.split("\\.");
                int what4 = Integer.parseInt(split4[1]);
                if (what4 <= 0) {
                    quann2 = split4[0];
                }
                s2.setValueAt(quann2, old_row, 3);
            }

            infol.setText("<html>" + iname + "&nbsp &nbsp &nbsp &nbsp <b>MRP: " + mrp2 + "</b> &nbsp &nbsp &nbsp "
                    + quan2 + "x" + netRate + "=" + Double.parseDouble(quan2) * netPrice + "</html>");
            // row selected
            Rectangle rect = jTable1.getCellRect(jTable1.getRowCount() - 1, 0, true);
            jTable1.scrollRectToVisible(rect);
            jTable1.setRowSelectionInterval(jTable1.getRowCount() - 1, jTable1.getRowCount() - 1);
            // row selected ends

            apply_all_changes();
            fields_clear();
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void apply_all_changes() {
        try {
            double nsub = 0, ndis = 0, ngross = 0, ntax = 0, nquan = 0, ncost_rate = 0;
            int items = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                double quan = Double.parseDouble(jTable1.getValueAt(i, 3).toString());
                double price = Double.parseDouble(jTable1.getValueAt(i, 6).toString());
                double disp = Double.parseDouble(jTable1.getValueAt(i, 8).toString());
                double taxp = Double.parseDouble(jTable1.getValueAt(i, 11).toString());
                double prate = Double.parseDouble(jTable1.getValueAt(i, 22).toString());

                double amount = price * quan;
                double disamt = (disp * amount) / 100;
                double sub = amount - disamt;

                double taxamt = 0, total = 0;

                if (ttype.equalsIgnoreCase("No Tax")) {
                    total = sub;
                    taxamt = 0;
                } else if (ttype.equalsIgnoreCase("Inclusive Model-II")) {
                    double devide = 100 + taxp;
                    devide = price * (100 / devide);
                    taxamt = price - devide;
                    taxamt = taxamt * quan;
                    total = sub;
                } else if (ttype.equalsIgnoreCase("Inclusive of Tax") || ttype.equalsIgnoreCase("Exclusive of Tax")) {
                    taxamt = (taxp * sub) / 100;
                    total = sub + taxamt;
                }

                double cost_rate = prate * quan;
                double profit = sub - cost_rate;

                String price2 = String.format("%." + hmany + "f", price);
                String amount2 = String.format("%." + hmany + "f", amount);
                String disamt2 = String.format("%." + hmany + "f", disamt);
                String sub2 = String.format("%." + hmany + "f", sub);
                String taxamt2 = String.format("%." + hmany + "f", taxamt);
                String total2 = String.format("%." + hmany + "f", total);

                String cost_rate2 = String.format("%." + hmany + "f", cost_rate);
                String profit2 = String.format("%." + hmany + "f", profit);

                jTable1.setValueAt(price2, i, 6);
                jTable1.setValueAt(amount2, i, 7);
                jTable1.setValueAt(disamt2, i, 9);
                jTable1.setValueAt(sub2, i, 10);
                jTable1.setValueAt(taxamt2, i, 12);
                jTable1.setValueAt(total2, i, 13);

                jTable1.setValueAt(cost_rate2, i, 19);
                jTable1.setValueAt(profit2, i, 20);

                nquan = nquan + quan;
                nsub = nsub + amount;
                ndis = ndis + disamt;
                ngross = ngross + sub;
                ntax = ntax + taxamt;
                items = items + 1;
                ncost_rate = ncost_rate + cost_rate;
            } // table row counts ends

            String nsub2 = String.format("%." + hmany + "f", nsub);
            String ndis2 = String.format("%." + hmany + "f", ndis);
            String ngross2 = String.format("%." + hmany + "f", ngross);
            String ntax2 = String.format("%." + hmany + "f", ntax);
            String ncost_rate2 = String.format("%." + hmany + "f", ncost_rate);

            h7.setText("" + items);
            h8.setText("" + nquan);
            h9.setText("" + nsub2);
            h11.setText("" + ndis2);
            h12.setText("" + ngross2);
            h13.setText("" + ntax2);
            pratel.setText("" + ncost_rate2);
            final_calculate();
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    void final_calculate() {
        try {
            if (h10.getText().equals("")) {
                h10.setText("" + 0.00);
            }
            if (h11.getText().equals("")) {
                h11.setText("" + 0.00);
            }
            if (h14.getText().equals("")) {
                h14.setText("" + 0.00);
            }

            double sub = Double.parseDouble(h9.getText());
            double disamt = Double.parseDouble(h11.getText());
            double gross = sub - disamt;

            double taxamt = Double.parseDouble(h13.getText());
            double other = Double.parseDouble(h14.getText());
            double gt = gross + taxamt + other;

            if (ttype.equalsIgnoreCase("Inclusive Model-II")) {
                gt = gross + other;
            }
            String gross2 = String.format("%." + hmany + "f", gross);
            h12.setText("" + gross2);

            String grant = String.format("%." + hmany + "f", gt);

            if (round_option.equals("Yes")) {
                // round off starts
                String[] grant1 = grant.split("\\.");
                String grant2 = grant1[0];
                String grant3 = grant1[1];

                String less1 = 0 + "." + grant3;
                double less = Double.parseDouble(less1);

                int rup = Integer.parseInt(grant2);

                String wpoint = grant3.substring(0, 1);
                int point = Integer.parseInt(wpoint);
                double round;
                if (point >= 5) {
                    rup = rup + 1;
                    round = 1 - less;
                } else {
                    round = -less;
                }
                if (round == -0) {
                    round = -round;
                }
                String round2 = String.format("%." + hmany + "f", round);

                h15.setText("" + round2);
                netl.setText(rup + ".00");
                // round off ends
            } // round option ends
            else {
                h15.setText("" + 0);
                netl.setText(grant);
            } // no round option
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_paid_bal_details() {
        if (h25.getText().equals("")) {
            h25.setText("0.00");
        }
        if (h26.getText().equals("")) {
            h26.setText("0.00");
        }
        if (h27.getText().equals("")) {
            h27.setText("0.00");
        }
        double cash = Double.parseDouble(h25.getText());
        double card = Double.parseDouble(h26.getText());
        double others = Double.parseDouble(h27.getText());
        double total = cash + card + others;

        String cash2 = String.format("%." + hmany + "f", cash);
        String card2 = String.format("%." + hmany + "f", card);
        String others2 = String.format("%." + hmany + "f", others);
        String total2 = String.format("%." + hmany + "f", total);

        h25.setText(cash2);
        h26.setText(card2);
        h27.setText(others2);
        h28.setText(total2);
        paidl.setText(total2);

    }

    void get_bal_details() {
        if (paidl.getText().equals("")) {
            paidl.setText("0.00");
        }
        double tot = Double.parseDouble(netl.getText());
        double paid = Double.parseDouble(paidl.getText());
        double bal = paid - tot;
        String bal2 = String.format("%." + hmany + "f", bal);
        String paid2 = String.format("%." + hmany + "f", paid);
        paidl.setText(paid2);
        if (paid < tot) {
            JOptionPane.showMessageDialog(this, "<html><h1>Paid Amount is Lessthan Bill Amount!</h1></html>", "Invalid",
                    JOptionPane.ERROR_MESSAGE);
            paidl.setText("0.00");
            ball.setText("0.00");
        } else {
            ball.setText(bal2);
            if (cust_details.equals("Yes")) {
                h22.requestFocus();
            } else {
                save();
            }
        }
    }

    void get_pay_mode() {
        if (h16.getSelectedItem().equals("Cash")) {
            paidl.requestFocus();
        } else if (h16.getSelectedItem().equals("Card")) {
            paidl.setText(netl.getText());
            ball.setText("0.00");
            h22.requestFocus();
        } else if (h16.getSelectedItem().equals("Credit")) {
            paidl.setText("0.00");
            ball.setText("0.00");
            h22.requestFocus();
        } else if (h16.getSelectedItem().equals("Others")) {
            paidl.setText(netl.getText());
            ball.setText("0.00");
            h22.requestFocus();
        } else if (h16.getSelectedItem().equals("UPI")) {
            SwingUtilities.invokeLater(() -> {
                Frame frame = JOptionPane.getFrameForComponent(this);
                double tot = 0;
                double paid = 0;
                double bal = 0;
                double net = 0;
                try {
                    net = Double.parseDouble(netl.getText());
                    paid = Double.parseDouble(paidl.getText());
                    bal = Double.parseDouble(ball.getText());
                    tot = net - paid - bal;
                    if (tot < 0) {
                        tot = 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                final double fPaid = paid;
                final double fNet = net;

                UPIPaymentDialog dialog = new UPIPaymentDialog(frame, tot, (t) -> {
                    double totalPaid = fPaid + t;
                    paidl.setText(String.format("%.2f", totalPaid));

                    double newBal = fNet - totalPaid;
                    if (newBal < 0) {
                        newBal = 0;
                        double advance = totalPaid - fNet;
                        JOptionPane.showMessageDialog(this,
                                "Customer paid ₹" + String.format("%.2f", advance) + " extra.");
                    }
                    ball.setText(String.format("%.2f", newBal));

                    h22.requestFocus();
                });
            });
            return;
        } else {
            multi_pay_mode.requestFocus();
            Point l = stockl.getLocationOnScreen();
            multi_pay_mode.setLocation(l.x, l.y + stockl.getHeight());
            multi_pay_mode.setSize(306, 301);
            multi_pay_mode.setVisible(true);
            h25.requestFocus();
        }
    }

    void save() {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found to Save!", "No Records",
                        JOptionPane.ERROR_MESSAGE);
                h17.requestFocus();
                return;
            }
            apply_all_changes();

            if (!selvagates) {
                get_date_time();
                get_billno();
            }

            String billno = h1.getText();
            boolean billExists = false;
            ResultSet r = util.doQuery("SELECT billno FROM sales WHERE billno='" + billno + "'");
            while (r.next()) {
                billExists = true;
            }
            if (billExists) {
                JOptionPane.showMessageDialog(this, "Already Exist!\nUse 'Alter' Option to Alter...", "Already Exist",
                        JOptionPane.ERROR_MESSAGE);
                savebutton.requestFocus();
                return;
            }

            // Default empty values
            h22.setText(h22.getText().isEmpty() ? "." : h22.getText());
            h23.setText(h23.getText().isEmpty() ? "." : h23.getText());
            h24.setText(h24.getText().isEmpty() ? "." : h24.getText());
            paidl.setText(paidl.getText().isEmpty() ? "0.00" : paidl.getText());
            ball.setText(ball.getText().isEmpty() ? "0.00" : ball.getText());
            pointsl.setText(pointsl.getText().isEmpty() ? "0" : pointsl.getText());
            tot_pointsl.setText(tot_pointsl.getText().isEmpty() ? "0" : tot_pointsl.getText());

            String cid = h21.getText();
            double net = Double.parseDouble(netl.getText());

            // Validate credit customers
            if (h16.getSelectedItem().equals("Credit")) {
                String ctype = "Retail";
                boolean customerExists = false;
                r = util.doQuery("SELECT ctype FROM cust WHERE cid='" + cid + "'");
                while (r.next()) {
                    ctype = r.getString(1);
                    customerExists = true;
                }
                if (ctype.equals("Retail") || !customerExists) {
                    JOptionPane.showMessageDialog(this,
                            "<html>For Credit Bill First Create Customer Master<br>, Create as <b> 'Credit Customer'</b></html>",
                            "Invalid Credit Customer!", JOptionPane.ERROR_MESSAGE);
                    h24.requestFocus();
                    return;
                }
            }

            int due_days = 0;
            if (h16.getSelectedItem().equals("Credit")) {
                double climit = 0, old_bal = 0;
                r = util.doQuery("SELECT climit,duedays FROM cust WHERE cid='" + cid + "'");
                while (r.next()) {
                    climit = r.getDouble(1);
                    due_days = r.getInt(2);
                }
                r = util.doQuery("SELECT SUM(tot-paid) FROM cust_bal WHERE cid='" + cid + "'");
                while (r.next()) {
                    old_bal = r.getDouble(1);
                }
                double allowed_credit = net + old_bal;
                if (allowed_credit > climit) {
                    JOptionPane.showMessageDialog(this,
                            "<html><b>Credit Limit: " + climit + "<br>Old Balance: " + old_bal + "</b></html>",
                            "Credit Limit Exceeds", JOptionPane.ERROR_MESSAGE);
                    h16.requestFocus();
                    return;
                }
            }

            // Customer & points info
            String cname = h24.getText();
            String cardno = h22.getText();
            String mobile = h23.getText();

            // Auto-detect existing customer if not selected
            if (!customer_selection) {
                try {
                    boolean found = false;
                    // Try to find by Mobile
                    if (mobile.length() >= 10) {
                        ResultSet rs = util.doQuery("SELECT cid, cname FROM cust WHERE mobile='" + mobile + "'");
                        if (rs.next()) {
                            cid = rs.getString("cid");
                            cname = rs.getString("cname"); // Sync name from DB
                            h21.setText(cid);
                            h24.setText(cname);
                            customer_selection = true;
                            found = true;
                        }
                    }
                    // Try to find by Name if not found by Mobile
                    if (!found && cname.length() > 0 && !cname.equals(".")) {
                        ResultSet rs = util.doQuery("SELECT cid, mobile FROM cust WHERE cname='" + cname + "'");
                        if (rs.next()) {
                            cid = rs.getString("cid");
                            String dbMobile = rs.getString("mobile");
                            // Only overwrite mobile if the entered one is invalid/empty
                            if (mobile.length() < 10 && dbMobile != null && dbMobile.length() >= 10) {
                                mobile = dbMobile;
                                h23.setText(mobile);
                            }
                            h21.setText(cid);
                            customer_selection = true;
                            found = true;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            boolean customerCardExists = false;
            int newcid = 0;
            boolean createNewCustomer = !customer_selection
                    && (mobile.length() >= 10 || (cname.length() > 0 && !cname.equals(".")));

            if (createNewCustomer) {
                if (!cardno.equals(".")) {
                    r = util.doQuery("SELECT cid FROM cust WHERE cardno='" + cardno + "'");
                    while (r.next()) {
                        customerCardExists = true;
                    }

                    if (customerCardExists) {
                        JOptionPane.showMessageDialog(this, "Customer Card No Already Exist!", "Already Exist",
                                JOptionPane.ERROR_MESSAGE);
                        h22.requestFocus();
                        return;
                    }
                }

                r = util.doQuery("SELECT MAX(cid) FROM cust");
                while (r.next()) {
                    newcid = r.getInt(1);
                }
                newcid++;
                cid = String.valueOf(newcid);
                h21.setText(cid); // Update UI with new CID
            }

            double old_points = 0;
            boolean points_exist = false;
            if (points_option.equalsIgnoreCase("Yes") && mobile.length() == 10) {
                r = util.doQuery("SELECT points FROM cust_points WHERE cid='" + cid + "'");
                while (r.next()) {
                    old_points = r.getDouble(1);
                    points_exist = true;
                }
                double today_points = (net / points_for) * hmuch_points;
                double total_points = today_points + old_points;
                pointsl.setText(String.format("%." + hmany + "f", today_points));
                tot_pointsl.setText(String.format("%." + hmany + "f", total_points));
            }

            // Confirm save
            int confirm = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.NO_OPTION) {
                return;
            }

            save_draft();

            String date = new SimpleDateFormat("yyyy/MM/dd")
                    .format(new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText()));
            String time = h3.getText();
            String location = h4.getText();
            String terminal = h5.getText();
            String cashier = h6.getText();
            String items = h7.getText();
            String quans = h8.getText();
            String sub = h9.getText();
            String disp = h10.getText();
            String disamt = h11.getText();
            String gross = h12.getText();
            String taxamt = h13.getText();
            String addamt = h14.getText();
            String round = h15.getText();
            String pby = h16.getSelectedItem().toString();
            double paidDouble = Double.parseDouble(paidl.getText().replace(",", ""));
            String paid = String.format("%." + hmany + "f", paidDouble);
            paidl.setText(paid);
            double bal = Double.parseDouble(ball.getText());
            String cash = h25.getText();
            String card = h26.getText();
            String others = h27.getText();
            String credit = "0";
            String upi = "0";
            String today_points = pointsl.getText();
            String total_points = tot_pointsl.getText();
            String cost_rate = pratel.getText();
            String price_type = pricel.getText();
            String last = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").format(new Date());

            if (pby.equalsIgnoreCase("Cash")) {
                cash = String.valueOf(net);
                card = others = credit = upi = "0";
            } else if (pby.equalsIgnoreCase("Card")) {
                card = "" + net;
                cash = others = credit = upi = "0";
            } else if (pby.equalsIgnoreCase("Credit")) {
                credit = "" + net;
                cash = card = others = upi = "0";
            } else if (pby.equalsIgnoreCase("UPI")) {
                upi = "" + net;
                cash = card = credit = others = "0";
            } else if (pby.equalsIgnoreCase("Others")) {
                others = "" + net;
                cash = card = credit = upi = "0";
            }

            Connection conn = util.getConnection();
            conn.setAutoCommit(false);
            int count = 0;

            try {
                // Insert into sales
                String salesQuery = "INSERT INTO sales (billno, dat, tim, location, cashier, terminal, items, quans, sub, disp, disamt, gross, taxamt, addamt, round, net, pby, paid, bal, cash, card, credit, others, upi, price_type, tax_type, tax, cid, cardno, cname, mobile, user, last, today_points, total_points, cost_rate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement psSales = conn.prepareStatement(salesQuery);
                psSales.setString(1, billno);
                psSales.setString(2, date);
                psSales.setString(3, time);
                psSales.setString(4, location);
                psSales.setString(5, cashier);
                psSales.setString(6, terminal);
                psSales.setString(7, items);
                psSales.setString(8, quans);
                psSales.setString(9, sub);
                psSales.setString(10, disp);
                psSales.setString(11, disamt);
                psSales.setString(12, gross);
                psSales.setString(13, taxamt);
                psSales.setString(14, addamt);
                psSales.setString(15, round);
                psSales.setDouble(16, net);
                psSales.setString(17, pby);
                psSales.setString(18, paid);
                psSales.setDouble(19, bal);
                psSales.setString(20, cash);
                psSales.setString(21, card);
                psSales.setString(22, credit);
                psSales.setString(23, others);
                psSales.setString(24, upi);
                psSales.setString(25, price_type);
                psSales.setString(26, ttype);
                psSales.setString(27, tax);
                psSales.setString(28, cid);
                psSales.setString(29, cardno);
                psSales.setString(30, cname);
                psSales.setString(31, mobile);
                psSales.setString(32, username);
                psSales.setString(33, last);
                psSales.setString(34, today_points);
                psSales.setString(35, total_points);
                psSales.setString(36, cost_rate);
                psSales.executeUpdate();

                String salesItemsQuery = "INSERT INTO sales_items (billno, dat, cid, serial, ino, iname, quan, mrp, price, amount, disp, disamt, sub, taxp, taxamt, total, udes, barcode, hsn, tax, entry, cost_rate, profit, iname1, price_type, tax_type, item_type, prate, rprice, wprice, remarks) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement psItems = conn.prepareStatement(salesItemsQuery);

                String stockUpdateQuery = "UPDATE stock SET quan = quan - ? WHERE barcode=? AND ino=? AND iname=? AND entry=?";
                PreparedStatement psStock = conn.prepareStatement(stockUpdateQuery);

                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    String serial = jTable1.getValueAt(i, 0).toString();
                    String ino = jTable1.getValueAt(i, 1).toString();
                    String iname = jTable1.getValueAt(i, 2).toString();
                    double quan = Double.parseDouble(jTable1.getValueAt(i, 3).toString());
                    String mrp = jTable1.getValueAt(i, 4).toString();
                    String price = jTable1.getValueAt(i, 6).toString();
                    String amount = jTable1.getValueAt(i, 7).toString();
                    String disp1 = jTable1.getValueAt(i, 8).toString();
                    String disamt1 = jTable1.getValueAt(i, 9).toString();
                    String sub1 = jTable1.getValueAt(i, 10).toString();
                    String taxp1 = jTable1.getValueAt(i, 11).toString();
                    String taxamt1 = jTable1.getValueAt(i, 12).toString();
                    String total = jTable1.getValueAt(i, 13).toString();
                    String udes = jTable1.getValueAt(i, 14).toString();
                    String barcode = jTable1.getValueAt(i, 15).toString();
                    String hsn = jTable1.getValueAt(i, 16).toString();
                    String entry = jTable1.getValueAt(i, 18).toString();
                    String item_cost_rate = jTable1.getValueAt(i, 19).toString();
                    String profit = jTable1.getValueAt(i, 20).toString();
                    String iname1 = jTable1.getValueAt(i, 21).toString();
                    String prate = jTable1.getValueAt(i, 22).toString();
                    String rprice = jTable1.getValueAt(i, 23).toString();
                    String wprice = jTable1.getValueAt(i, 24).toString();
                    String remarks = jTable1.getValueAt(i, 25).toString();

                    // Fetch subunit & subconv
                    ResultSet rs_unit = util.doQuery("SELECT subunit, subconv, udes FROM item WHERE ino='" + ino + "'");
                    String subunit = "";
                    double subconv = 1;
                    String main_unit = "";
                    while (rs_unit.next()) {
                        subunit = rs_unit.getString("subunit");
                        subconv = rs_unit.getDouble("subconv");
                        main_unit = rs_unit.getString("udes");
                    }

                    // Convert subunit to main unit if necessary
                    double main_quan = udes.equalsIgnoreCase(main_unit) || subconv == 0 ? quan : quan / subconv;

                    psItems.setString(1, billno);
                    psItems.setString(2, date);
                    psItems.setString(3, cid);
                    psItems.setString(4, serial);
                    psItems.setString(5, ino);
                    psItems.setString(6, iname);
                    psItems.setDouble(7, quan);
                    psItems.setString(8, mrp);
                    psItems.setString(9, price);
                    psItems.setString(10, amount);
                    psItems.setString(11, disp1);
                    psItems.setString(12, disamt1);
                    psItems.setString(13, sub1);
                    psItems.setString(14, taxp1);
                    psItems.setString(15, taxamt1);
                    psItems.setString(16, total);
                    psItems.setString(17, udes);
                    psItems.setString(18, barcode);
                    psItems.setString(19, hsn);
                    psItems.setString(20, tax);
                    psItems.setString(21, entry);
                    psItems.setString(22, item_cost_rate);
                    psItems.setString(23, profit);
                    psItems.setString(24, iname1);
                    psItems.setString(25, price_type);
                    psItems.setString(26, ttype);
                    psItems.setString(27, "Old");
                    psItems.setString(28, prate);
                    psItems.setString(29, rprice);
                    psItems.setString(30, wprice);
                    psItems.setString(31, remarks);
                    psItems.executeUpdate();

                    // Update stock
                    psStock.setDouble(1, main_quan);
                    psStock.setString(2, barcode);
                    psStock.setString(3, ino);
                    psStock.setString(4, iname);
                    psStock.setString(5, entry);
                    psStock.executeUpdate();
                }

                String rno = "SALE-" + billno;
                String type = "";
                String account = "Sales Account", under = "Primary Account", part = "Sales Account";
                String debit = "0";
                String credit_perrac = "" + net;

                if (pby.equalsIgnoreCase("Cash")) {
                    type = "cash";
                    String ano = "-";
                    String peraccQuery = "INSERT INTO peracc (billno, dat, part, debit, credit, account, under, type1, ano) VALUES (?,?,?,?,?,?,?,?,?)";
                    PreparedStatement psPeracc = conn.prepareStatement(peraccQuery);
                    psPeracc.setString(1, rno); // billno column
                    psPeracc.setString(2, date);
                    psPeracc.setString(3, part);
                    psPeracc.setString(4, debit);
                    psPeracc.setString(5, credit_perrac);
                    psPeracc.setString(6, account);
                    psPeracc.setString(7, under);
                    psPeracc.setString(8, type);
                    psPeracc.setString(9, ano);
                    psPeracc.executeUpdate();
                } else if (pby.equalsIgnoreCase("Card")) {
                    type = "bank";
                    String ano = ".";
                    String peraccQuery = "INSERT INTO peracc (billno, dat, part, debit, credit, account, under, type1, ano) VALUES (?,?,?,?,?,?,?,?,?)";
                    PreparedStatement psPeracc = conn.prepareStatement(peraccQuery);
                    psPeracc.setString(1, rno); // billno column
                    psPeracc.setString(2, date);
                    psPeracc.setString(3, part);
                    psPeracc.setString(4, debit);
                    psPeracc.setString(5, credit_perrac);
                    psPeracc.setString(6, account);
                    psPeracc.setString(7, under);
                    psPeracc.setString(8, type);
                    psPeracc.setString(9, ano);
                    psPeracc.executeUpdate();
                }

                if (pby.equalsIgnoreCase("Credit")) {
                    int paid1 = 0;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    Calendar c = Calendar.getInstance();
                    c.setTime(sdf.parse(date));
                    c.add(Calendar.DAY_OF_MONTH, due_days);
                    String ddate = sdf.format(c.getTime());

                    String custBalQuery = "INSERT INTO cust_bal (billno, dat, ddate, cid, cname, tot, paid, last) VALUES (?,?,?,?,?,?,?,?)";
                    PreparedStatement psCustBal = conn.prepareStatement(custBalQuery);
                    psCustBal.setString(1, billno);
                    psCustBal.setString(2, date);
                    psCustBal.setString(3, ddate);
                    psCustBal.setString(4, cid);
                    psCustBal.setString(5, cname);
                    psCustBal.setDouble(6, net);
                    psCustBal.setInt(7, paid1);
                    psCustBal.setString(8, last);
                    psCustBal.executeUpdate();
                }

                // Customer save
                if (createNewCustomer) {
                    String ctype = "General", add1 = ".", add2 = ".", add3 = ".", city = ".", phone = ".", gstno = ".",
                            climit = "" + 0, due_days1 = "" + 0, remarks = ".", email = ".";
                    if (cardno.equals(".")) {
                        cardno = "" + newcid;
                    }
                    String custQuery = "INSERT INTO cust (cid, ctype, cardno, cname, add1, add2, add3, city, mobile, phone, gstno, sname, scode, climit, duedays, remarks, email) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement psCust = conn.prepareStatement(custQuery);
                    psCust.setInt(1, newcid);
                    psCust.setString(2, ctype);
                    psCust.setString(3, cardno);
                    psCust.setString(4, cname);
                    psCust.setString(5, add1);
                    psCust.setString(6, add2);
                    psCust.setString(7, add3);
                    psCust.setString(8, city);
                    psCust.setString(9, mobile);
                    psCust.setString(10, phone);
                    psCust.setString(11, gstno);
                    psCust.setString(12, sname);
                    psCust.setString(13, scode);
                    psCust.setString(14, climit);
                    psCust.setString(15, due_days1);
                    psCust.setString(16, remarks);
                    psCust.setString(17, email);
                    psCust.executeUpdate();
                }

                // Points system
                if (points_option.equalsIgnoreCase("Yes") && mobile.length() == 10) {
                    if (points_exist == false) {
                        String pointsQuery = "INSERT INTO cust_points (cid, points) VALUES (?,?)";
                        PreparedStatement psPoints = conn.prepareStatement(pointsQuery);
                        psPoints.setString(1, cid);
                        psPoints.setString(2, today_points);
                        psPoints.executeUpdate();
                    } else {
                        String pointsQuery = "UPDATE cust_points SET points=? WHERE cid=?";
                        PreparedStatement psPoints = conn.prepareStatement(pointsQuery);
                        psPoints.setString(1, total_points);
                        psPoints.setString(2, cid);
                        psPoints.executeUpdate();
                    }
                }

                conn.commit();
                count = 1; // Success

                int bb = JOptionPane.showConfirmDialog(this, "<html><h1>You Want to Print Bill ?</h1></html>",
                        "Saved Successfully", JOptionPane.YES_OPTION);
                if (bb == JOptionPane.YES_OPTION) {
                    String billformat = price_type.equals("Wholesale") ? wholesale_format : retail_format;
                    new print_class().get_print(util, billno, drive, folder, billformat);
                }
                send_sms();
                form_clear();

            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                get_billno();
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.err.println("Error in save(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    //// void get_num_sno()
    //// {
    //// boolean siva=false;
    //// int kl=0;
    //// try
    //// {
    //// String query=("select max(sno) from num");
    //// ResultSet r=util.doQuery(query);
    //// while(r.next())
    //// {
    //// siva=true;
    //// kl=r.getInt(1);
    //// }
    //// if(siva==true)
    //// {
    //// sno_num=kl;
    //// sno_num=sno_num+1;
    //// }
    //// else
    //// {
    //// sno_num=1;
    //// }
    //
    // }catch(Exception e){e.printStackTrace();}
    // }
    void form_clear() {
        try {
            fields_clear();
            get_date_time();
            display_terminal();
            h1.setText("--");
            h7.setText("");
            h8.setText("");
            h9.setText("");
            h10.setText("");
            h11.setText("");
            h12.setText("");
            h13.setText("");
            h14.setText("");
            h15.setText("");
            h16.setSelectedIndex(0);
            h21.setText("--");
            creditLabel.setText(null);
            h22.setText("");
            h23.setText("");
            h24.setText("");
            infol.setText("");
            pointsl.setText("");
            tot_pointsl.setText("");
            pratel.setText("");
            cpointsl.setText("");
            if (s2.getRowCount() > 0) {
                s2.getDataVector().removeAllElements();
                s2.fireTableDataChanged();
            }
            pricel.setText(dsales);
            get_retail_wholesale_color();
            get_net_default_values();
            if (pricel.getText().equals("Wholesale")) {
                h16.setSelectedItem("Credit");
            }
            savebutton.setVisible(true);
            viewbutton.setVisible(true);
            deletebutton.setVisible(false);
            alterbutton.setVisible(false);
            printbutton.setVisible(false);
            holdbutton.setVisible(true);
            retrivebutton.setVisible(true);
            selvagates = false;
            h17.requestFocus();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void get_net_default_values() {
        double value = 0.0;
        String value1 = String.format("%." + hmany + "f", value);
        netl.setText(value1);
        paidl.setText(value1);
        ball.setText(value1);
    }

    public void view(String billno) {
        try {
            pointsl.setText("");
            tot_pointsl.setText("");
            String query = "select distinct billno from sales where billno='" + billno + "'";
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
                deletebutton.setVisible(true);
                alterbutton.setVisible(true);
                printbutton.setVisible(true);
                holdbutton.setVisible(false);
                retrivebutton.setVisible(false);
                selvagates = true;
                if (s2.getRowCount() > 0) {
                    s2.getDataVector().removeAllElements();
                    s2.fireTableDataChanged();
                }

                query = "select billno,date_format(dat,'%d/%m/%Y'),tim,location,terminal,cashier,items,quans,sub,disp,disamt,gross,taxamt,addamt,round,pby,net,paid,bal,cash,card,others,price_type,tax_type,tax,cid,cardno,mobile,cname,today_points,total_points,cost_rate from sales where billno='"
                        + billno + "'";
                set1 = util.doQuery(query);
                while (set1.next()) {
                    h1.setText(set1.getString(1));
                    h2.setText(set1.getString(2));
                    h3.setText(set1.getString(3));
                    h4.setText(set1.getString(4));
                    h5.setText(set1.getString(5));
                    h6.setText(set1.getString(6));

                    h7.setText(set1.getString(7));
                    h8.setText(set1.getString(8));

                    String sub = String.format("%." + hmany + "f", set1.getDouble(9));
                    String disamt = String.format("%." + hmany + "f", set1.getDouble(11));
                    String gross = String.format("%." + hmany + "f", set1.getDouble(12));
                    String taxamt = String.format("%." + hmany + "f", set1.getDouble(13));
                    String addamt = String.format("%." + hmany + "f", set1.getDouble(14));
                    String round = String.format("%." + hmany + "f", set1.getDouble(15));

                    h9.setText(sub);
                    h10.setText(set1.getString(10));
                    h11.setText(disamt);
                    h12.setText(gross);
                    h13.setText(taxamt);
                    h14.setText(addamt);
                    h15.setText(round);
                    h16.setSelectedItem(set1.getString(16));
                    double net = set1.getDouble(17);

                    String paid = String.format("%." + hmany + "f", set1.getDouble(18));
                    String bal = String.format("%." + hmany + "f", set1.getDouble(19));
                    paidl.setText(paid);
                    ball.setText(bal);

                    String cash = String.format("%." + hmany + "f", set1.getDouble(20));
                    String card = String.format("%." + hmany + "f", set1.getDouble(21));
                    String others = String.format("%." + hmany + "f", set1.getDouble(22));

                    h25.setText(cash);
                    h26.setText(card);
                    h27.setText(others);

                    pricel.setText(set1.getString(23));
                    ttype = set1.getString(24);
                    tax = set1.getString(25);

                    h21.setText(set1.getString(26));
                    h22.setText(set1.getString(27));
                    h23.setText(set1.getString(28));
                    h24.setText(set1.getString(29));

                    pointsl.setText(set1.getString(30));
                    tot_pointsl.setText(set1.getString(31));
                    pratel.setText(set1.getString(32));
                    String net2 = String.format("%." + hmany + "f", net);
                    netl.setText(net2);

                    double old_bal = 0;
                    String query1 = "select sum(tot-paid) from cust_bal where cid='" + h21.getText() + "'";
                    ResultSet r1;
                    try {
                        r1 = util.doQuery(query1);
                        while (r1.next()) {
                            old_bal = r1.getDouble(1);
                        }
                        creditLabel.setText("Credit: " + String.format("%." + hmany + "f", old_bal));
                    } catch (SQLException ex) {
                        System.getLogger(sales.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    } catch (ClassNotFoundException ex) {
                        System.getLogger(sales.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                }

                query = "select serial,ino,iname,quan,mrp,price,amount,disp,disamt,sub,taxp,taxamt,total,udes,barcode,hsn,tax,entry,cost_rate,profit,iname1,prate,rprice,wprice from sales_items where billno='"
                        + billno + "'";
                set1 = util.doQuery(query);
                while (set1.next()) {
                    String mrp = String.format("%." + hmany + "f", set1.getDouble(5));
                    String price = String.format("%." + hmany + "f", set1.getDouble(6));
                    String amount = String.format("%." + hmany + "f", set1.getDouble(7));
                    String disamt = String.format("%." + hmany + "f", set1.getDouble(9));
                    String sub = String.format("%." + hmany + "f", set1.getDouble(10));
                    String taxamt = String.format("%." + hmany + "f", set1.getDouble(12));
                    String total = String.format("%." + hmany + "f", set1.getDouble(13));
                    String rprice = String.format("%." + hmany + "f", set1.getDouble(23));
                    String quan2 = String.format("%." + hmany + "f", set1.getDouble(4));
                    String[] split3 = quan2.split("\\.");
                    int what3 = Integer.parseInt(split3[1]);
                    if (what3 <= 0) {
                        quan2 = split3[0];
                    }

                    s2.addRow(new Object[] { set1.getString(1), set1.getString(2), set1.getString(3), quan2, mrp,
                            rprice, price, amount, set1.getString(8), disamt,
                            sub, set1.getInt(11), taxamt, total, set1.getString(14), set1.getString(15),
                            set1.getString(16), set1.getString(17), set1.getString(18), set1.getString(19),
                            set1.getString(20), set1.getString(21), set1.getString(22), set1.getString(23),
                            set1.getString(24), "" });
                }
                get_customer_details_using_mobileno();
                get_retail_wholesale_color();
            } // if selva true ends
        } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void save_draft() {
        try {
            if (h10.getText().equals("")) {
                h10.setText("" + 0);
            }
            if (h11.getText().equals("")) {
                h11.setText("" + 0);
            }
            if (h14.getText().equals("")) {
                h14.setText("" + 0);
            }

            if (h22.getText().equals("")) {
                h22.setText(".");
            }
            if (h23.getText().equals("")) {
                h23.setText(".");
            }
            if (h24.getText().equals("")) {
                h24.setText(".");
            }

            if (h25.getText().equals("")) {
                h25.setText("" + 0);
            }
            if (h26.getText().equals("")) {
                h26.setText("" + 0);
            }
            if (h27.getText().equals("")) {
                h27.setText("" + 0);
            }

            String file_name = "Items_List";
            File draftsDir = new File(folder + "/Drafts/");
            if (!draftsDir.exists()) {
                draftsDir.mkdirs();
            }
            try (FileWriter f = new FileWriter(new File(folder + "/Drafts/" + file_name + ".txt"))) {
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    String barcode = jTable1.getValueAt(i, 15).toString();
                    String iname = jTable1.getValueAt(i, 2).toString();
                    String price = jTable1.getValueAt(i, 6).toString();
                    String quan = jTable1.getValueAt(i, 3).toString();
                    String ino = jTable1.getValueAt(i, 1).toString();
                    String entry = jTable1.getValueAt(i, 18).toString();
                    String remarks = jTable1.getValueAt(i, 25).toString();
                    String unit = jTable1.getValueAt(i, 14).toString();
                    f.write("" + barcode + "$" + iname + "$" + price + "$" + quan + "$" + ino + "$" + entry + "$"
                            + remarks + "$" + unit + "\r\n");
                } // row counts ends here
            }

            String file_name1 = "Bill_Details";
            try (FileWriter f1 = new FileWriter(new File(folder + "/Drafts/" + file_name1 + ".txt"))) {
                String billno = h1.getText();
                String date = h2.getText();
                String time = h3.getText();
                String location = h4.getText();
                String terminal = h5.getText();
                String cashier = h6.getText();

                String items = h7.getText();
                String quans = h8.getText();
                String sub = h9.getText();
                String disp = h10.getText();
                String dis = h11.getText();
                String gross = h12.getText();
                String taxamt = h13.getText();
                String other = h14.getText();
                String round = h15.getText();
                String pby = h16.getSelectedItem().toString();
                String net = netl.getText();
                String paid = paidl.getText();
                String bal = ball.getText();

                String cid = h21.getText();
                String cardno = h22.getText();
                String mobile = h23.getText();
                String cname = h24.getText();

                String cash = h25.getText();
                String card = h26.getText();
                String others = h27.getText();

                String price_type = pricel.getText();
                f1.write(billno + "$" + date + "$" + time + "$" + location + "$" + terminal + "$" + cashier + "$"
                        + items + "$" + quans + "$" + sub + "$" + disp + "$" + dis + "$" + gross + "$" + taxamt + "$"
                        + other + "$" + round + "$" + pby + "$" + net + "$" + paid + "$" + bal + "$" + cid + "$"
                        + cardno + "$" + mobile + "$" + cname + "$" + cash + "$" + card + "$" + others + "$" + ttype
                        + "$" + tax + "$" + price_type);
            }

        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    void load_draft() {
        try {
            if (s2.getRowCount() > 0) {
                JOptionPane.showMessageDialog(this, "Data Already Exist!", "Already Exist", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String file_name = "Items_List";
            BufferedReader br = new BufferedReader(new FileReader(new File(folder + "/Drafts/" + file_name + ".txt")));
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                String what = sCurrentLine;
                StringTokenizer token = new StringTokenizer(what, "$");
                String barcode = "", ino = "", iname = "", entry = "", remarks = "", unit = "";
                double quan = 0, price = 0;

                while (token.hasMoreTokens()) {
                    barcode = token.nextToken();
                    iname = token.nextToken();
                    price = Double.parseDouble(token.nextToken());
                    quan = Double.parseDouble(token.nextToken());
                    ino = token.nextToken();
                    entry = token.nextToken();
                    remarks = token.nextToken();
                    unit = token.nextToken();
                }
                add_item(barcode, ino, iname, price, quan, remarks, unit);
            } // while loop reading lines one by one ends he

            String file_name1 = "Bill_Details";
            BufferedReader br1 = new BufferedReader(
                    new FileReader(new File(folder + "/Drafts/" + file_name1 + ".txt")));
            String sCurrentLine1;
            while ((sCurrentLine1 = br1.readLine()) != null) {
                String what = sCurrentLine1;
                StringTokenizer token = new StringTokenizer(what, "$");
                while (token.hasMoreTokens()) {
                    h1.setText(token.nextToken());
                    h2.setText(token.nextToken());
                    h3.setText(token.nextToken());
                    h4.setText(token.nextToken());
                    h5.setText(token.nextToken());
                    h6.setText(token.nextToken());

                    h7.setText(token.nextToken());
                    h8.setText(token.nextToken());
                    h9.setText(token.nextToken());
                    h10.setText(token.nextToken());
                    h11.setText(token.nextToken());
                    h12.setText(token.nextToken());
                    h13.setText(token.nextToken());
                    h14.setText(token.nextToken());
                    h15.setText(token.nextToken());
                    h16.setSelectedItem(token.nextToken());
                    netl.setText(token.nextToken());
                    paidl.setText(token.nextToken());
                    ball.setText(token.nextToken());

                    h21.setText(token.nextToken());
                    h22.setText(token.nextToken());
                    h23.setText(token.nextToken());
                    h24.setText(token.nextToken());

                    h25.setText(token.nextToken());
                    h26.setText(token.nextToken());
                    h27.setText(token.nextToken());

                    ttype = token.nextToken();
                    tax = token.nextToken();
                    pricel.setText(token.nextToken());

                    double old_bal = 0;
                    String query = "select sum(tot-paid) from cust_bal where cid='" + h21.getText() + "'";
                    ResultSet r = util.doQuery(query);
                    while (r.next()) {
                        old_bal = r.getDouble(1);
                    }
                    creditLabel.setText("Credit: " + String.format("%." + hmany + "f", old_bal));
                }
            } // while line loop ends

        } catch (HeadlessException | IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        } catch (SQLException ex) {
            System.getLogger(sales.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (ClassNotFoundException ex) {
            System.getLogger(sales.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public double getRowProfit(int rowIndex) {
        try {
            double quantity = Double.parseDouble(jTable1.getValueAt(rowIndex, 3).toString());
            double price = Double.parseDouble(jTable1.getValueAt(rowIndex, 6).toString());
            double discount = Double.parseDouble(jTable1.getValueAt(rowIndex, 8).toString());
            double purchaseRate = Double.parseDouble(jTable1.getValueAt(rowIndex, 22).toString());

            double amount = price * quantity;
            double discountAmount = (discount / 100) * amount;
            double subtotal = amount - discountAmount;
            double cost = purchaseRate * quantity;

            return subtotal - cost; // profit
        } catch (Exception e) {
            e.printStackTrace(); // Optional: handle more gracefully
            return 0.0;
        }
    }

    public double getTotalProfit() {
        double totalProfit = 0.0;
        int rowCount = jTable1.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            totalProfit += getRowProfit(i);
        }

        return totalProfit;
    }

    void get_customer_details_using_mobileno() {
        try {
            String scode1 = "";
            customer_selection = false;
            String query = "select cid,cardno,mobile,cname,scode from cust where mobile='" + h23.getText() + "'";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                h21.setText(r.getString(1));
                h22.setText(r.getString(2));
                h23.setText(r.getString(3));
                h24.setText(r.getString(4));
                scode1 = r.getString(5);
                customer_selection = true;
                double old_bal = 0;
                String query1 = "select sum(tot-paid) from cust_bal where cid='" + h21.getText() + "'";
                ResultSet r1 = util.doQuery(query1);
                while (r1.next()) {
                    old_bal = r1.getDouble(1);
                }
                creditLabel.setText("Credit: " + String.format("%." + hmany + "f", old_bal));
            }
            if (customer_selection == false) {
                h21.setText("--");
                h24.setText("");
                creditLabel.setText(null);
            }
            get_state_code(scode1);
            get_customer_points();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_customer_details_using_customer_card() {
        try {
            String scode1 = "";
            customer_selection = false;
            String query = "select cid,cardno,mobile,cname,scode from cust where cardno='" + h22.getText() + "'";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                h21.setText(r.getString(1));
                h22.setText(r.getString(2));
                h23.setText(r.getString(3));
                h24.setText(r.getString(4));
                scode1 = r.getString(5);
                customer_selection = true;
                double old_bal = 0;
                String query1 = "select sum(tot-paid) from cust_bal where cid='" + h21.getText() + "'";
                ResultSet r1 = util.doQuery(query1);
                while (r1.next()) {
                    old_bal = r1.getDouble(1);
                }
                creditLabel.setText("Credit: " + String.format("%." + hmany + "f", old_bal));
            }
            if (customer_selection == false) {
                h21.setText("--");
                h24.setText("");
                h23.setText("");
                creditLabel.setText(null);
            }
            get_state_code(scode1);
            get_customer_points();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_customer_points() {
        try {
            int points = 0;
            String query = "select points from cust_points where cid='" + h21.getText() + "'";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                points = r.getInt(1);
            }
            cpointsl.setText("Customer Points: " + points);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_state_code(String scode1) {
        if (scode1.equals(scode) || scode1.length() <= 1) {
            tax = "Local";
        } else {
            tax = "IGST";
        }
    }

    void get_balance() {
        try {
            boolean selva = false;
            String query = "select cid from cust_bal where cid='" + h21.getText() + "' limit 1";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                double bal = 0;
                query = "select sum(tot-paid) from cust_bal where cid='" + h21.getText() + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    bal = r.getDouble(1);
                }
                double climit = 0;
                query = "select climit from cust where cid='" + h21.getText() + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    climit = r.getDouble(1);
                }
                int dues = 0;
                query = "select count(billno) from cust_bal where cid='" + h21.getText() + "' and tot-paid>0";
                r = util.doQuery(query);
                while (r.next()) {
                    dues = r.getInt(1);
                }
                String bal2 = String.format("%." + hmany + "f", bal);
                String climit2 = String.format("%." + hmany + "f", climit);
                JOptionPane.showMessageDialog(this, "<html><h2>Current Balance: " + bal2 + "<br>Credit Limit: "
                        + climit2 + "<br>No of Bills Due: " + dues + "</h2></html>");
            } // customer in balance table
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    void load_iname_table() {
        try {
            s4.addColumn("Barcode");
            s4.addColumn("It.Code");
            s4.addColumn("Item Name");
            s4.addColumn("Regional Name");
            s4.addColumn("Purchase Price");
            s4.addColumn("MRP");
            s4.addColumn("Retail Price");
            s4.addColumn("Wholesale Price");
            jTable3.setModel(s4);
            jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(150);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(304);
            jTable3.getColumnModel().getColumn(3).setPreferredWidth(290);
            jTable3.getColumnModel().getColumn(4).setPreferredWidth(120);
            jTable3.getColumnModel().getColumn(5).setPreferredWidth(110);
            jTable3.getColumnModel().getColumn(6).setPreferredWidth(110);
            jTable3.getColumnModel().getColumn(7).setPreferredWidth(115);
            if (utype.equalsIgnoreCase("User")) {
                jTable3.getColumnModel().getColumn(4).setPreferredWidth(0);
            }
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable3.getTableHeader().setFont(new Font(Ta, Bold, size));
            jTable3.getTableHeader().setResizingAllowed(false);
            jTable3.getTableHeader().setReorderingAllowed(false);
        } catch (Exception e) {
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

    void get_price_change() {
        if (pricel.getText().equals("Retail")) {
            h16.setSelectedItem("Credit");
            pricel.setText("Wholesale");
        } else {
            h16.setSelectedItem("Cash");
            pricel.setText("Retail");
        }
        h17.requestFocus();
        get_retail_wholesale_color();
    }

    void get_retail_wholesale_color() {
        if (pricel.getText().equals("Retail")) {
            pricel.setForeground(new Color(0, 153, 51, 255));
            jLabel28.setForeground(new Color(153, 0, 153, 255));
        } else {
            pricel.setForeground(Color.red);
            jLabel28.setForeground(Color.red);
        }
    }

    void alter_sales() {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found to Alter!", "No Records",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selvagates == false) {
                JOptionPane.showMessageDialog(this, "User 'View' Option Before Alter!", "User 'View' Option",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (utype.equalsIgnoreCase("User")) {
                JOptionPane.showMessageDialog(this, "Login as 'Administrator' to Alter!", "Permission Restricted",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String reason = JOptionPane.showInputDialog(this, "<html><h1>Enter Reason for Alter ?</h1></html>",
                    "Reason", JOptionPane.PLAIN_MESSAGE);
            if (reason == null || "".equals(reason)) {
                JOptionPane.showMessageDialog(this, "Invalid Reason!", "Invalid", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int as = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Alter ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            save_draft();
            String billno = h1.getText();
            ArrayList barcode = new ArrayList();
            ArrayList ino = new ArrayList();
            ArrayList iname = new ArrayList();
            ArrayList quan = new ArrayList();
            ArrayList entry = new ArrayList();

            String query = "select barcode,ino,iname,quan,entry from sales_items where billno='" + billno + "'";
            ResultSet set1 = util.doQuery(query);
            boolean selva = false;
            while (set1.next()) {
                selva = true;
                barcode.add(set1.getString(1));
                ino.add(set1.getString(2));
                iname.add(set1.getString(3));
                quan.add(set1.getString(4));
                entry.add(set1.getString(5));
            }
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText());
            String date = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);
            String time = h3.getText();
            String terminal = h5.getText();
            String cashier = h6.getText();
            String cid = h21.getText();
            String cname = h24.getText();
            String pby = h16.getSelectedItem().toString();
            String old_amount = "" + 0;
            query = "select net from sales where billno='" + billno + "' ";
            set1 = util.doQuery(query);
            while (set1.next()) {
                old_amount = String.format("%." + hmany + "f", set1.getDouble(1));
            }
            Connection conn = util.getConnection();
            conn.setAutoCommit(false);
            try {
                String insertAlterQuery = "INSERT INTO alter_sales (billno, dat, tim, cashier, terminal, cid, cname, pby, amount, newamount, reason, user, last) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement psAlter = conn.prepareStatement(insertAlterQuery);
                psAlter.setString(1, billno);
                psAlter.setString(2, date);
                psAlter.setString(3, time);
                psAlter.setString(4, cashier);
                psAlter.setString(5, terminal);
                psAlter.setString(6, cid);
                psAlter.setString(7, cname);
                psAlter.setString(8, pby);
                psAlter.setString(9, old_amount);
                psAlter.setString(10, netl.getText());
                psAlter.setString(11, reason);
                psAlter.setString(12, username);
                psAlter.setString(13, last);
                psAlter.executeUpdate();

                if (selva == true) {
                    String updateStockQuery = "UPDATE stock SET quan = quan + ? WHERE barcode=? AND ino=? AND iname=? AND entry=?";
                    PreparedStatement psStock = conn.prepareStatement(updateStockQuery);
                    for (int i = 0; i < barcode.size(); i++) {
                        psStock.setDouble(1, Double.parseDouble(quan.get(i).toString()));
                        psStock.setString(2, barcode.get(i).toString());
                        psStock.setString(3, ino.get(i).toString());
                        psStock.setString(4, iname.get(i).toString());
                        psStock.setString(5, entry.get(i).toString());
                        psStock.addBatch();
                    }
                    psStock.executeBatch();
                }

                PreparedStatement psDeleteSales = conn.prepareStatement("DELETE FROM sales WHERE billno=?");
                psDeleteSales.setString(1, billno);
                psDeleteSales.executeUpdate();

                PreparedStatement psDeleteItems = conn.prepareStatement("DELETE FROM sales_items WHERE billno=?");
                psDeleteItems.setString(1, billno);
                psDeleteItems.executeUpdate();

                if (h16.getSelectedItem().equals("Credit")) {
                    PreparedStatement psDeleteCustBal = conn.prepareStatement("DELETE FROM cust_bal WHERE billno=?");
                    psDeleteCustBal.setString(1, billno);
                    psDeleteCustBal.executeUpdate();
                }

                if (points_option.equalsIgnoreCase("Yes")) {
                    PreparedStatement psUpdatePoints = conn
                            .prepareStatement("UPDATE cust_points SET points = points - ? WHERE cid=?");
                    psUpdatePoints.setDouble(1, Double.parseDouble(pointsl.getText()));
                    psUpdatePoints.setString(2, h21.getText());
                    psUpdatePoints.executeUpdate();
                }

                conn.commit();
                save();
                String message = ": SECURITY ALERT : \nSales Bill Altered,\nBill No: " + billno + "\nBill Amount: "
                        + old_amount + "\nAltered Amount: " + netl.getText() + "\nReason: " + reason + "\nby: "
                        + h6.getText() + "\nat: " + last;
                alter_sms(message);

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error during alter: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    void delete() {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found to Delete!", "No Records",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selvagates == false) {
                JOptionPane.showMessageDialog(this, "User 'View' Option Before Delete!", "User 'View' Option",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (utype.equalsIgnoreCase("User")) {
                JOptionPane.showMessageDialog(this, "Login as 'Administrator' to Delete!", "Permission Restricted",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String reason = JOptionPane.showInputDialog(this, "<html><h1>Enter Reason for Delete ?</h1></html>",
                    "Reason", JOptionPane.PLAIN_MESSAGE);
            if (reason == null || "".equals(reason)) {
                JOptionPane.showMessageDialog(this, "Invalid Reason!", "Invalid", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int as = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Delete ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            save_draft();
            String billno = h1.getText();
            ArrayList barcode = new ArrayList();
            ArrayList ino = new ArrayList();
            ArrayList iname = new ArrayList();
            ArrayList quan = new ArrayList();
            ArrayList entry = new ArrayList();

            String query = "select barcode,ino,iname,quan,entry from sales_items where billno='" + billno + "'";
            ResultSet set1 = util.doQuery(query);
            boolean selva = false;
            while (set1.next()) {
                selva = true;
                barcode.add(set1.getString(1));
                ino.add(set1.getString(2));
                iname.add(set1.getString(3));
                quan.add(set1.getString(4));
                entry.add(set1.getString(5));
            }
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText());
            String date = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);
            String time = h3.getText();
            String terminal = h5.getText();
            String cashier = h6.getText();
            String cid = h21.getText();
            String cname = h24.getText();
            String pby = h16.getSelectedItem().toString();
            String old_amount = "" + 0;
            query = "select net from sales where billno='" + billno + "' ";
            set1 = util.doQuery(query);
            while (set1.next()) {
                old_amount = set1.getString(1);
            }
            Connection conn = util.getConnection();
            conn.setAutoCommit(false);
            try {
                String insertDeleteQuery = "INSERT INTO alter_sales_delete (billno, dat, tim, cashier, terminal, cid, cname, pby, amount, newamount, reason, user, last) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement psDeleteLog = conn.prepareStatement(insertDeleteQuery);
                psDeleteLog.setString(1, billno);
                psDeleteLog.setString(2, date);
                psDeleteLog.setString(3, time);
                psDeleteLog.setString(4, cashier);
                psDeleteLog.setString(5, terminal);
                psDeleteLog.setString(6, cid);
                psDeleteLog.setString(7, cname);
                psDeleteLog.setString(8, pby);
                psDeleteLog.setString(9, old_amount);
                psDeleteLog.setString(10, netl.getText());
                psDeleteLog.setString(11, reason);
                psDeleteLog.setString(12, username);
                psDeleteLog.setString(13, last);
                psDeleteLog.executeUpdate();

                if (selva == true) {
                    String updateStockQuery = "UPDATE stock SET quan = quan + ? WHERE barcode=? AND ino=? AND iname=? AND entry=?";
                    PreparedStatement psStock = conn.prepareStatement(updateStockQuery);
                    for (int i = 0; i < barcode.size(); i++) {
                        psStock.setDouble(1, Double.parseDouble(quan.get(i).toString()));
                        psStock.setString(2, barcode.get(i).toString());
                        psStock.setString(3, ino.get(i).toString());
                        psStock.setString(4, iname.get(i).toString());
                        psStock.setString(5, entry.get(i).toString());
                        psStock.addBatch();
                    }
                    psStock.executeBatch();
                }

                PreparedStatement psDeleteSales = conn.prepareStatement("DELETE FROM sales WHERE billno=?");
                psDeleteSales.setString(1, billno);
                psDeleteSales.executeUpdate();

                PreparedStatement psDeleteItems = conn.prepareStatement("DELETE FROM sales_items WHERE billno=?");
                psDeleteItems.setString(1, billno);
                psDeleteItems.executeUpdate();

                String rno = "SALE-" + billno;
                PreparedStatement psDeletePeracc = conn.prepareStatement("DELETE FROM peracc WHERE billno=?");
                psDeletePeracc.setString(1, rno);
                psDeletePeracc.executeUpdate();

                if (h16.getSelectedItem().equals("Credit")) {
                    PreparedStatement psDeleteCustBal = conn.prepareStatement("DELETE FROM cust_bal WHERE billno=?");
                    psDeleteCustBal.setString(1, billno);
                    psDeleteCustBal.executeUpdate();
                }

                if (points_option.equalsIgnoreCase("Yes")) {
                    PreparedStatement psUpdatePoints = conn
                            .prepareStatement("UPDATE cust_points SET points = points - ? WHERE cid=?");
                    psUpdatePoints.setDouble(1, Double.parseDouble(pointsl.getText()));
                    psUpdatePoints.setString(2, h21.getText());
                    psUpdatePoints.executeUpdate();
                }

                conn.commit();
                JOptionPane.showMessageDialog(this, "<html><h1>Deleted Successfully</h1></html>", "Deleted",
                        JOptionPane.PLAIN_MESSAGE);
                String message = ": SECURITY ALERT : \nSales Bill Deleted,\nBill No: " + billno + "\nBill Amount: "
                        + old_amount + "\nReason: " + reason + "\nby: " + h6.getText() + "\nat: " + last;
                alter_sms(message);
                form_clear();

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error during delete: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    void serial_num() {
        int serial = 1;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            jTable1.setValueAt(serial, i, 0);
            serial++;
        }
    }

    void edit_quantity(double quan, String barcode, String ino, String iname, int row) {
        try {
            double tquan;
            double stock = 0;
            String entry = "purchase";
            String query = "select quan,entry from stock where barcode='" + barcode + "' and ino='" + ino
                    + "' and iname='" + iname + "' ";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                stock = r.getDouble(1);
                entry = r.getString(2);
            }

            double entry_stock = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String ino1 = jTable1.getValueAt(i, 1).toString();
                String barcode1 = jTable1.getValueAt(i, 15).toString();
                String entry1 = jTable1.getValueAt(i, 18).toString();
                double quan1 = Double.parseDouble(jTable1.getValueAt(i, 3).toString());
                if (ino.equals(ino1) && barcode.equals(barcode1) && entry.equals(entry1)) {
                    entry_stock = quan1;
                }
            } // for loop ends
            tquan = entry_stock + quan;
            if (stock_bill.equalsIgnoreCase("No")) {
                if (tquan > stock && stock_bill.equalsIgnoreCase("No")) {
                    JOptionPane.showMessageDialog(this, "Stock-in-Hand: " + stock + "\n Already Added: " + entry_stock,
                            "Invalid Stock", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } // billing without stock ends
            String quan2 = String.format("%." + hmany + "f", tquan);
            String[] split3 = quan2.split("\\.");
            int what3 = Integer.parseInt(split3[1]);
            if (what3 <= 0) {
                quan2 = split3[0];
            }
            jTable1.setValueAt(quan2 + "", row, 3);
            apply_all_changes();
            h17.requestFocus();
        } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void send_sms() {
        try {
            if (sms_alert.equalsIgnoreCase("Yes") && h23.getText().length() == 10) {
                String message;
                int today_points = (int) Double.parseDouble(pointsl.getText());
                int total_points = (int) Double.parseDouble(tot_pointsl.getText());
                String cname = "\n\nFor Queries,\n" + smsfoot1 + "\n" + smsfoot2;
                if (pricel.getText().equals("Retail")) {
                    if (points_option.equalsIgnoreCase("Yes")) {
                        message = "Dear Customer, \nThank you for Your Purchase, Bill No: " + h1.getText()
                                + ", Amount Rs." + netl.getText() + " by " + h16.getSelectedItem()
                                + ". You Have Earned :" + today_points + " Points, Total Points are: " + total_points
                                + "" + cname;
                    } else {
                        message = "Dear Customer, \nThank you for Your Purchase, Bill No: " + h1.getText()
                                + ", Amount Rs." + netl.getText() + " by " + h16.getSelectedItem() + " " + cname;
                    }
                } // retail bill ends
                else { // wholesale starts
                    String closing_bal = "";
                    String query = "select sum(tot-paid) from cust_bal where cid='" + h21.getText() + "'";
                    ResultSet r = util.doQuery(query);
                    while (r.next()) {
                        closing_bal = String.format("%." + hmany + "f", r.getDouble(1));
                    }

                    message = "Dear Customer, \nThank you for Your Purchase, Bill No: " + h1.getText() + ", Amount Rs."
                            + netl.getText() + " by " + h16.getSelectedItem() + ". Your Net Balance is " + closing_bal
                            + "" + cname;
                } // wholesale ends
                new smspack.SMS_Sender_Single().getData(smsuser, smspass, sender, h23.getText(), message);
            } // sms alert is yes
        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void alter_sms(String message) {
        try {
            if (management_no1.length() == 10 && alter_sms.equals("Yes")) {
                new SMS_Sender_Single().getData(smsuser, smspass, sender, management_no1, message);
            }

            if (management_no2.length() == 10 && alter_sms.equals("Yes")) {
                new SMS_Sender_Single().getData(smsuser, smspass, sender, management_no2, message);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void load_hold_table() {
        try {
            s5.addColumn("Hold Sno");
            s5.addColumn("Location");
            s5.addColumn("Terminal");
            s5.addColumn("Cust_Name");
            s5.addColumn("Items");
            s5.addColumn("Qty");
            s5.addColumn("Est.Bill Amount");
            jTable4.setModel(s5);
            jTable4.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable4.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable4.getColumnModel().getColumn(1).setPreferredWidth(140);
            jTable4.getColumnModel().getColumn(2).setPreferredWidth(140);
            jTable4.getColumnModel().getColumn(3).setPreferredWidth(300);
            jTable4.getColumnModel().getColumn(4).setPreferredWidth(110);
            jTable4.getColumnModel().getColumn(5).setPreferredWidth(110);
            jTable4.getColumnModel().getColumn(6).setPreferredWidth(134);
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable4.getTableHeader().setFont(new Font(Ta, Bold, size));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void hold() {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "Oops", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int as = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Hold Bill ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            save_draft();
            int billno = 1;
            String query = "select max(billno) from sales_hold";
            ResultSet r = util.doQuery(query);
            boolean selva = false;
            while (r.next()) {
                selva = true;
                billno = r.getInt(1);
            }

            if (selva == true) {
                billno = billno + 1;
            }
            String items = h7.getText();
            String quans = h8.getText();
            String net = netl.getText();
            String cname = h24.getText();

            String location = h4.getText();
            String terminal = h5.getText();
            String cashier = h6.getText();

            Connection conn = util.getConnection();
            conn.setAutoCommit(false);
            try {
                String insertHoldQuery = "INSERT INTO sales_hold (billno, location, terminal, cashier, items, quans, net, cname, barcode, ino, iname, price, quan, remarks, unit) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement psHold = conn.prepareStatement(insertHoldQuery);

                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    String ino = jTable1.getValueAt(i, 1).toString();
                    String iname = jTable1.getValueAt(i, 2).toString();
                    String quan = jTable1.getValueAt(i, 3).toString();
                    String price = jTable1.getValueAt(i, 6).toString();
                    String barcode = jTable1.getValueAt(i, 15).toString();
                    String remarks = jTable1.getValueAt(i, 25).toString();
                    String unit = jTable1.getValueAt(i, 14).toString();

                    psHold.setInt(1, billno);
                    psHold.setString(2, location);
                    psHold.setString(3, terminal);
                    psHold.setString(4, cashier);
                    psHold.setString(5, items);
                    psHold.setString(6, quans);
                    psHold.setString(7, net);
                    psHold.setString(8, cname);
                    psHold.setString(9, barcode);
                    psHold.setString(10, ino);
                    psHold.setString(11, iname);
                    psHold.setString(12, price);
                    psHold.setString(13, quan);
                    psHold.setString(14, remarks);
                    psHold.setString(15, unit);
                    psHold.addBatch();
                }
                psHold.executeBatch();
                conn.commit();
                JOptionPane.showMessageDialog(this, "<html><h1>Bill Hold Successfully</h1></html>", "Saved",
                        JOptionPane.PLAIN_MESSAGE);
                form_clear();

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error during hold: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void retrive(String billno) {
        try {
            String query = "select barcode,ino,iname,price,quan,remarks,unit from sales_hold where billno='" + billno
                    + "' ";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                add_item(r.getString(1), r.getString(2), r.getString(3), r.getDouble(4), r.getDouble(5), r.getString(6),
                        r.getString(7));
            }
            query = "delete from sales_hold where billno='" + billno + "' ";
            int a = util.doManipulation(query);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void retail_price() {
        try {
            int as = JOptionPane.showConfirmDialog(this,
                    "<html><h1>Want to Change All Products to 'Retail Price' ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String rrate = jTable1.getValueAt(i, 23).toString();
                jTable1.setValueAt(rrate, i, 6);
            } // row counts ends
            apply_all_changes();
            pricel.setText("Retail");
        } catch (HeadlessException e) {
            System.out.println(e);
        }
    }

    void wholesale_price() {
        try {
            int as = JOptionPane.showConfirmDialog(this,
                    "<html><h1>Want to Change All Products to 'Wholesale Price' ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String wrate = jTable1.getValueAt(i, 24).toString();
                jTable1.setValueAt(wrate, i, 6);
            } // row counts ends
            pricel.setText("Wholesale");
            apply_all_changes();
        } catch (HeadlessException e) {
            System.out.println(e);
        }
    }

    public sales(DataUtil util) {
        this(util, "Default Version", "Default User");
    }

    public sales(DataUtil util, String version, String userType) {
        initComponents();
        this.util = util;
        setTitle(version);
        button_short();
        load_items_table();
        get_defaults();
        display_terminal();
        inol.setVisible(false);
        color1.setVisible(false);
        color2.setVisible(false);
        load_iname_table();
        load_customer_table();
        deletebutton.setVisible(false);
        alterbutton.setVisible(false);
        printbutton.setVisible(false);
        pointsl.setVisible(false);
        tot_pointsl.setVisible(false);
        pratel.setVisible(false);
        load_hold_table();
        get_net_default_values();
        bind();
    }

    private void bind() {
        jTable1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int row = jTable1.rowAtPoint(e.getPoint());
                int col = jTable1.columnAtPoint(e.getPoint());
                if (col == 13 && row != -1) {
                    jTable1.setToolTipText("Profit: " + String.format("%." + hmany + "f", getRowProfit(row)));
                } else {
                    jTable1.setToolTipText(null); // No tooltip for other columns
                }
            }
        });

        netl.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                String text = netl.getText();
                // Remove allowed characters (0, ., space), then check if anything remains
                String stripped = text.replaceAll("[0.\\s]", "");
                if (stripped.isEmpty()) {
                    netl.setToolTipText("Profit: 00.00"); // No tooltip
                } else {
                    netl.setToolTipText("Profit: " + String.format("%." + hmany + "f", getTotalProfit()));
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        multi_pay_mode = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        h28 = new javax.swing.JTextField();
        h25 = new javax.swing.JTextField();
        h26 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel32 = new javax.swing.JLabel();
        h27 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        selectbutton = new javax.swing.JButton();
        iname_list = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        cname_list = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        hold_list = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        infol = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        h3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        h1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        h2 = new javax.swing.JTextField();
        h4 = new javax.swing.JTextField();
        h5 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        h6 = new javax.swing.JTextField();
        h7 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        h8 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        h9 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        h10 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        h12 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        h14 = new javax.swing.JTextField();
        h15 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        netl = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        paidl = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        ball = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        h13 = new javax.swing.JTextField();
        h11 = new javax.swing.JTextField();
        h16 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        h18 = new javax.swing.JTextField();
        h20 = new javax.swing.JTextField();
        h17 = new javax.swing.JTextField();
        h19 = new javax.swing.JTextField();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        viewbutton = new javax.swing.JButton();
        nextbutton = new javax.swing.JButton();
        prebutton = new javax.swing.JButton();
        applybutton = new javax.swing.JButton();
        removebutton = new javax.swing.JButton();
        loadbutton = new javax.swing.JButton();
        draftbutton = new javax.swing.JButton();
        savebutton = new javax.swing.JButton();
        tenderbutton = new javax.swing.JButton();
        stockl = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        retrivebutton = new javax.swing.JButton();
        holdbutton = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        h21 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        h22 = new javax.swing.JTextField();
        cpointsl = new javax.swing.JLabel();
        h23 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        pricel = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        inol = new javax.swing.JLabel();
        color2 = new javax.swing.JTextField();
        color1 = new javax.swing.JTextField();
        alterbutton = new javax.swing.JButton();
        deletebutton = new javax.swing.JButton();
        printbutton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        h24 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        pointsl = new javax.swing.JTextField();
        tot_pointsl = new javax.swing.JTextField();
        pratel = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        wholesalebutton = new javax.swing.JButton();
        retailbutton = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        h29 = new javax.swing.JTextField();
        creditLabel = new javax.swing.JLabel();
        weighingButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        unitCombo = new javax.swing.JComboBox<>();

        multi_pay_mode.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        multi_pay_mode.setUndecorated(true);

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel29.setText(" Multi Pay Mode");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel30.setText("Cash");

        h28.setEditable(false);
        h28.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        h28.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        h25.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        h25.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h25ActionPerformed(evt);
            }
        });

        h26.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        h26.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h26.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h26FocusGained(evt);
            }
        });
        h26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h26ActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel31.setText("Card");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel32.setText("Others");

        h27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        h27.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h27.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h27FocusGained(evt);
            }
        });
        h27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h27ActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel33.setText("Total");

        selectbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        selectbutton.setIcon(ColorConstants.loadIcon("/icons/selectall45.png")); // NOI18N
        selectbutton.setMnemonic('s');
        selectbutton.setText("Select");
        selectbutton.setToolTipText("");
        selectbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectbuttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 160,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(h25, javax.swing.GroupLayout.PREFERRED_SIZE, 220,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(h26, javax.swing.GroupLayout.PREFERRED_SIZE, 220,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(h27, javax.swing.GroupLayout.PREFERRED_SIZE, 220,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(h28, javax.swing.GroupLayout.PREFERRED_SIZE, 220,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 310,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addComponent(selectbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 140,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)));
        jPanel9Layout.setVerticalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(h25, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(h26, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(h27, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addGroup(jPanel9Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(h28, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(10, 10, 10)
                                .addComponent(selectbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)));

        javax.swing.GroupLayout multi_pay_modeLayout = new javax.swing.GroupLayout(multi_pay_mode.getContentPane());
        multi_pay_mode.getContentPane().setLayout(multi_pay_modeLayout);
        multi_pay_modeLayout.setHorizontalGroup(
                multi_pay_modeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 306,
                                javax.swing.GroupLayout.PREFERRED_SIZE));
        multi_pay_modeLayout.setVerticalGroup(
                multi_pay_modeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        iname_list.setUndecorated(true);
        iname_list.getContentPane().setLayout(null);

        jScrollPane3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jScrollPane3FocusLost(evt);
            }
        });

        jTable3.setFont(new java.awt.Font("Arial Unicode MS", 0, 18)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }));
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
        jScrollPane3.setBounds(0, 0, 1280, 490);

        jButton4.setIcon(ColorConstants.loadIcon("/icons/delete22.png")); // NOI18N
        jButton4.setMnemonic('o');
        jButton4.setText("Close");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        iname_list.getContentPane().add(jButton4);
        jButton4.setBounds(1140, 490, 140, 30);

        cname_list.setUndecorated(true);
        cname_list.getContentPane().setLayout(null);

        jScrollPane2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jScrollPane2FocusLost(evt);
            }
        });

        jTable2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }));
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

        jButton3.setIcon(ColorConstants.loadIcon("/icons/delete22.png")); // NOI18N
        jButton3.setMnemonic('o');
        jButton3.setText("Close");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        cname_list.getContentPane().add(jButton3);
        jButton3.setBounds(900, 480, 140, 30);

        hold_list.setBackground(new java.awt.Color(255, 255, 204));
        hold_list.setUndecorated(true);
        hold_list.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                hold_listFocusLost(evt);
            }
        });
        hold_list.getContentPane().setLayout(null);

        jScrollPane4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jScrollPane4FocusLost(evt);
            }
        });

        jTable4.setBackground(new java.awt.Color(255, 255, 204));
        jTable4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }));
        jTable4.setRowHeight(25);
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jTable4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable4KeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);

        hold_list.getContentPane().add(jScrollPane4);
        jScrollPane4.setBounds(0, 30, 1040, 450);

        jButton5.setIcon(ColorConstants.loadIcon("/icons/delete22.png")); // NOI18N
        jButton5.setMnemonic('o');
        jButton5.setText("Close");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        hold_list.getContentPane().add(jButton5);
        jButton5.setBounds(900, 480, 140, 30);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setText(" Hold Bills :");
        hold_list.getContentPane().add(jLabel14);
        jLabel14.setBounds(0, 0, 150, 30);

        setClosable(true);
        setNextFocusableComponent(h17);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 249, 233));

        infol.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        infol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(infol, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(infol, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)));

        getContentPane().add(jPanel1);
        jPanel1.setBounds(200, 0, 660, 50);

        jPanel2.setLayout(null);

        h3.setEditable(false);
        h3.setBackground(new java.awt.Color(242, 236, 236));
        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h3.setBorder(null);
        jPanel2.add(h3);
        h3.setBounds(180, 40, 90, 30);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Bill No");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(0, 10, 100, 30);

        h1.setEditable(false);
        h1.setBackground(new java.awt.Color(242, 236, 236));
        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h1.setText("--");
        h1.setBorder(null);
        jPanel2.add(h1);
        h1.setBounds(100, 10, 170, 30);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Date");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(0, 40, 100, 30);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Location");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(0, 70, 100, 30);

        h2.setEditable(false);
        h2.setBackground(new java.awt.Color(242, 236, 236));
        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h2.setBorder(null);
        jPanel2.add(h2);
        h2.setBounds(100, 40, 80, 30);

        h4.setEditable(false);
        h4.setBackground(new java.awt.Color(242, 236, 236));
        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h4.setBorder(null);
        jPanel2.add(h4);
        h4.setBounds(100, 70, 170, 30);

        h5.setEditable(false);
        h5.setBackground(new java.awt.Color(242, 236, 236));
        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h5.setBorder(null);
        jPanel2.add(h5);
        h5.setBounds(100, 100, 170, 30);
        jPanel2.add(jSeparator1);
        jSeparator1.setBounds(-50, 170, 410, 10);

        h6.setEditable(false);
        h6.setBackground(new java.awt.Color(242, 236, 236));
        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h6.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h6.setBorder(null);
        jPanel2.add(h6);
        h6.setBounds(100, 130, 170, 30);

        h7.setEditable(false);
        h7.setBackground(new java.awt.Color(242, 236, 236));
        h7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h7.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h7.setBorder(null);
        jPanel2.add(h7);
        h7.setBounds(100, 180, 170, 30);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Total Items");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(0, 180, 100, 30);

        h8.setEditable(false);
        h8.setBackground(new java.awt.Color(242, 236, 236));
        h8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h8.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h8.setBorder(null);
        jPanel2.add(h8);
        h8.setBounds(100, 210, 170, 30);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Total Qty");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(0, 210, 100, 30);

        h9.setEditable(false);
        h9.setBackground(new java.awt.Color(242, 236, 236));
        h9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h9.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h9.setBorder(null);
        jPanel2.add(h9);
        h9.setBounds(100, 240, 170, 30);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Sub Total");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(0, 240, 100, 30);

        h10.setBackground(new java.awt.Color(242, 236, 236));
        h10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h10.setBorder(null);
        h10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h10FocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                h10FocusLost(evt);
            }
        });
        h10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h10ActionPerformed(evt);
            }
        });
        jPanel2.add(h10);
        h10.setBounds(100, 270, 170, 30);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Discount %");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(0, 270, 100, 30);

        h12.setEditable(false);
        h12.setBackground(new java.awt.Color(242, 236, 236));
        h12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h12.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h12.setBorder(null);
        jPanel2.add(h12);
        h12.setBounds(100, 330, 170, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Discount Amt");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(0, 300, 100, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Grant Total");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(0, 330, 100, 30);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Tax Amt");
        jPanel2.add(jLabel15);
        jLabel15.setBounds(0, 360, 100, 30);

        h14.setBackground(new java.awt.Color(242, 236, 236));
        h14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h14.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h14.setBorder(null);
        h14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h14FocusGained(evt);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                h14FocusLost(evt);
            }
        });
        h14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h14ActionPerformed(evt);
            }
        });
        jPanel2.add(h14);
        h14.setBounds(100, 390, 170, 30);

        h15.setEditable(false);
        h15.setBackground(new java.awt.Color(242, 236, 236));
        h15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h15.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h15.setBorder(null);
        jPanel2.add(h15);
        h15.setBounds(100, 420, 170, 30);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("Other.Charges");
        jPanel2.add(jLabel17);
        jLabel17.setBounds(0, 390, 100, 30);

        jPanel3.setLayout(null);

        jPanel5.setBackground(new java.awt.Color(102, 0, 102));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel5.setLayout(null);

        jLabel19.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Net");
        jPanel5.add(jLabel19);
        jLabel19.setBounds(10, 10, 40, 30);

        netl.setEditable(false);
        netl.setBackground(new java.awt.Color(102, 0, 102));
        netl.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        netl.setForeground(new java.awt.Color(255, 255, 255));
        netl.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        netl.setText("0.00");
        netl.setBorder(null);
        jPanel5.add(netl);
        netl.setBounds(90, 10, 180, 30);

        jPanel3.add(jPanel5);
        jPanel5.setBounds(0, 0, 290, 50);

        jPanel6.setBackground(new java.awt.Color(102, 0, 102));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel6.setLayout(null);

        paidl.setBackground(new java.awt.Color(102, 0, 102));
        paidl.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        paidl.setForeground(new java.awt.Color(255, 255, 255));
        paidl.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        paidl.setText("0.00");
        paidl.setBorder(null);
        paidl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                paidlFocusGained(evt);
            }
        });
        paidl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paidlActionPerformed(evt);
            }
        });
        jPanel6.add(paidl);
        paidl.setBounds(90, 10, 180, 30);

        jLabel20.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Tender");
        jPanel6.add(jLabel20);
        jLabel20.setBounds(10, 10, 80, 30);

        jPanel3.add(jPanel6);
        jPanel6.setBounds(0, 50, 290, 50);

        jPanel7.setBackground(new java.awt.Color(102, 0, 102));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel7.setLayout(null);

        ball.setEditable(false);
        ball.setBackground(new java.awt.Color(102, 0, 102));
        ball.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        ball.setForeground(new java.awt.Color(255, 255, 255));
        ball.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ball.setText("0.00");
        ball.setBorder(null);
        jPanel7.add(ball);
        ball.setBounds(90, 10, 180, 30);

        jLabel16.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Return");
        jPanel7.add(jLabel16);
        jLabel16.setBounds(10, 10, 80, 30);

        jPanel3.add(jPanel7);
        jPanel7.setBounds(0, 100, 290, 50);

        jPanel2.add(jPanel3);
        jPanel3.setBounds(0, 480, 340, 150);

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("Pay Mode");
        jPanel2.add(jLabel18);
        jLabel18.setBounds(0, 450, 100, 30);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Terminal");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(0, 100, 100, 30);

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText("Round Amount");
        jPanel2.add(jLabel22);
        jLabel22.setBounds(0, 420, 100, 30);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Cashier");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(0, 130, 100, 30);

        h13.setEditable(false);
        h13.setBackground(new java.awt.Color(242, 236, 236));
        h13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h13.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h13.setBorder(null);
        jPanel2.add(h13);
        h13.setBounds(100, 360, 170, 30);

        h11.setEditable(false);
        h11.setBackground(new java.awt.Color(242, 236, 236));
        h11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h11.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h11.setBorder(null);
        jPanel2.add(h11);
        h11.setBounds(100, 300, 170, 30);

        h16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h16.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Cash", "Card", "Credit", "UPI", "Others", "Multi Pay" }));
        h16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h16KeyPressed(evt);
            }
        });
        jPanel2.add(h16);
        h16.setBounds(100, 450, 180, 30);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(1010, 0, 280, 630);

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 110, 990, 350);

        h18.setEditable(false);
        h18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        h18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(h18);
        h18.setBounds(200, 80, 310, 30);

        h20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        h20.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        h20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h20ActionPerformed(evt);
            }
        });
        getContentPane().add(h20);
        h20.setBounds(730, 80, 130, 30);

        h17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        h17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        h17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h17ActionPerformed(evt);
            }
        });
        h17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h17KeyPressed(evt);
            }
        });
        getContentPane().add(h17);
        h17.setBounds(0, 80, 200, 30);

        h19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        h19.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        h19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h19ActionPerformed(evt);
            }
        });
        getContentPane().add(h19);
        h19.setBounds(590, 80, 140, 30);

        clearbutton.setBackground(new java.awt.Color(255, 255, 153));
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
        clearbutton.setBounds(700, 580, 140, 50);

        closebutton.setBackground(new java.awt.Color(255, 255, 153));
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
        closebutton.setBounds(840, 580, 150, 50);

        viewbutton.setBackground(new java.awt.Color(255, 255, 153));
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
        viewbutton.setBounds(560, 580, 140, 50);

        nextbutton.setBackground(new java.awt.Color(255, 255, 153));
        nextbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        nextbutton.setIcon(ColorConstants.loadIcon("/icons/next45.png")); // NOI18N
        nextbutton.setMnemonic('u');
        nextbutton.setText("Next Entry");
        nextbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(nextbutton);
        nextbutton.setBounds(420, 580, 140, 50);

        prebutton.setBackground(new java.awt.Color(255, 255, 153));
        prebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        prebutton.setIcon(ColorConstants.loadIcon("/icons/pre45.png")); // NOI18N
        prebutton.setMnemonic('r');
        prebutton.setText("Last Entry");
        prebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(prebutton);
        prebutton.setBounds(280, 580, 140, 50);

        applybutton.setBackground(new java.awt.Color(255, 255, 153));
        applybutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        applybutton.setIcon(ColorConstants.loadIcon("/icons/apply45.png")); // NOI18N
        applybutton.setMnemonic('a');
        applybutton.setText("Appy");
        applybutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applybuttonActionPerformed(evt);
            }
        });
        getContentPane().add(applybutton);
        applybutton.setBounds(700, 530, 140, 50);

        removebutton.setBackground(new java.awt.Color(255, 255, 153));
        removebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        removebutton.setIcon(ColorConstants.loadIcon("/icons/remove45.png")); // NOI18N
        removebutton.setMnemonic('m');
        removebutton.setText("Remove");
        removebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(removebutton);
        removebutton.setBounds(840, 530, 150, 50);

        loadbutton.setBackground(new java.awt.Color(255, 255, 153));
        loadbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        loadbutton.setIcon(ColorConstants.loadIcon("/icons/draftload45.png")); // NOI18N
        loadbutton.setMnemonic('k');
        loadbutton.setText("Load Draft");
        loadbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(loadbutton);
        loadbutton.setBounds(560, 530, 140, 50);

        draftbutton.setBackground(new java.awt.Color(255, 255, 153));
        draftbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        draftbutton.setIcon(ColorConstants.loadIcon("/icons/draft45.png")); // NOI18N
        draftbutton.setMnemonic('f');
        draftbutton.setText("Draft");
        draftbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                draftbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(draftbutton);
        draftbutton.setBounds(420, 530, 140, 50);

        savebutton.setBackground(new java.awt.Color(255, 255, 153));
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
        savebutton.setBounds(140, 530, 140, 50);

        tenderbutton.setBackground(new java.awt.Color(255, 255, 153));
        tenderbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tenderbutton.setIcon(ColorConstants.loadIcon("/icons/cash45.png")); // NOI18N
        tenderbutton.setMnemonic('t');
        tenderbutton.setText("Tender");
        tenderbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenderbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(tenderbutton);
        tenderbutton.setBounds(280, 530, 140, 50);

        stockl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        stockl.setForeground(new java.awt.Color(255, 153, 0));
        stockl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(stockl);
        stockl.setBounds(290, 50, 210, 30);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Remarks");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(860, 50, 130, 30);

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(null);

        jLabel28.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(153, 0, 153));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("SALES");
        jLabel28.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jLabel28FocusGained(evt);
            }
        });
        jPanel4.add(jLabel28);
        jLabel28.setBounds(0, 0, 200, 50);

        getContentPane().add(jPanel4);
        jPanel4.setBounds(0, 0, 200, 50);

        jLabel23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel23.setText(" Customer Name");
        getContentPane().add(jLabel23);
        jLabel23.setBounds(550, 490, 110, 30);

        retrivebutton.setBackground(new java.awt.Color(255, 255, 153));
        retrivebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        retrivebutton.setIcon(ColorConstants.loadIcon("/icons/selectall45.png")); // NOI18N
        retrivebutton.setMnemonic('j');
        retrivebutton.setText("Retrive");
        retrivebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrivebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(retrivebutton);
        retrivebutton.setBounds(140, 580, 140, 50);

        holdbutton.setBackground(new java.awt.Color(255, 255, 153));
        holdbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        holdbutton.setIcon(ColorConstants.loadIcon("/icons/load45.jpg.png")); // NOI18N
        holdbutton.setMnemonic('h');
        holdbutton.setText("Hold");
        holdbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                holdbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(holdbutton);
        holdbutton.setBounds(0, 580, 140, 50);

        jLabel24.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel24.setText("Item");
        jLabel24.setToolTipText("Click Here to Add New Item");
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel24);
        jLabel24.setBounds(5, 50, 70, 30);

        jLabel25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel25.setText(" Cust_Id");
        getContentPane().add(jLabel25);
        jLabel25.setBounds(0, 490, 60, 30);

        h21.setEditable(false);
        h21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h21.setText("--");
        getContentPane().add(h21);
        h21.setBounds(60, 490, 100, 30);

        jLabel26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel26.setText(" Card No");
        getContentPane().add(jLabel26);
        jLabel26.setBounds(160, 490, 60, 30);

        h22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h22ActionPerformed(evt);
            }
        });
        getContentPane().add(h22);
        h22.setBounds(220, 490, 160, 30);

        cpointsl.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cpointsl.setForeground(new java.awt.Color(255, 153, 0));
        cpointsl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(cpointsl);
        cpointsl.setBounds(90, 460, 390, 30);

        h23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h23ActionPerformed(evt);
            }
        });
        getContentPane().add(h23);
        h23.setBounds(450, 490, 100, 30);

        jPanel8.setBackground(new java.awt.Color(255, 255, 204));
        jPanel8.setLayout(null);

        pricel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        pricel.setForeground(new java.awt.Color(0, 153, 51));
        pricel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pricel.setText("Retail");
        pricel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pricel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pricelMouseClicked(evt);
            }
        });
        jPanel8.add(pricel);
        pricel.setBounds(0, 0, 130, 50);

        getContentPane().add(jPanel8);
        jPanel8.setBounds(860, 0, 130, 50);

        jLabel34.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel34.setText("Rate");
        getContentPane().add(jLabel34);
        jLabel34.setBounds(590, 50, 40, 30);

        inol.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        getContentPane().add(inol);
        inol.setBounds(500, 50, 90, 30);

        color2.setBackground(new java.awt.Color(204, 0, 0));
        color2.setForeground(new java.awt.Color(255, 255, 255));
        color2.setText("2220");
        getContentPane().add(color2);
        color2.setBounds(650, 60, 20, 22);

        color1.setBackground(new java.awt.Color(242, 236, 236));
        getContentPane().add(color1);
        color1.setBounds(640, 60, 10, 22);

        alterbutton.setBackground(new java.awt.Color(255, 255, 153));
        alterbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        alterbutton.setIcon(ColorConstants.loadIcon("/icons/upload45.png")); // NOI18N
        alterbutton.setMnemonic('u');
        alterbutton.setText("Alter");
        alterbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alterbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(alterbutton);
        alterbutton.setBounds(0, 580, 140, 50);

        deletebutton.setBackground(new java.awt.Color(255, 255, 153));
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
        deletebutton.setBounds(140, 580, 140, 50);

        printbutton.setBackground(new java.awt.Color(255, 255, 153));
        printbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        printbutton.setIcon(ColorConstants.loadIcon("/icons/print45.png")); // NOI18N
        printbutton.setMnemonic('p');
        printbutton.setText("Re-Print");
        printbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(printbutton);
        printbutton.setBounds(0, 530, 140, 50);

        jButton1.setMnemonic('w');
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(940, 530, 30, 0);

        h24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h24KeyPressed(evt);
            }
        });
        getContentPane().add(h24);
        h24.setBounds(660, 490, 330, 30);

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
        });
        getContentPane().add(jTextField1);
        jTextField1.setBounds(0, 0, 60, 0);
        getContentPane().add(pointsl);
        pointsl.setBounds(670, 60, 20, 22);
        getContentPane().add(tot_pointsl);
        tot_pointsl.setBounds(690, 60, 10, 22);
        getContentPane().add(pratel);
        pratel.setBounds(700, 60, 20, 22);

        jLabel21.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel21.setText("Description");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(200, 50, 90, 30);

        wholesalebutton.setBackground(new java.awt.Color(255, 255, 153));
        wholesalebutton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        wholesalebutton.setMnemonic('x');
        wholesalebutton.setText("Wholesale Price");
        wholesalebutton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 51)));
        wholesalebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wholesalebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(wholesalebutton);
        wholesalebutton.setBounds(820, 460, 170, 30);

        retailbutton.setBackground(new java.awt.Color(255, 255, 153));
        retailbutton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        retailbutton.setMnemonic('z');
        retailbutton.setText("Retail Price");
        retailbutton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 51)));
        retailbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retailbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(retailbutton);
        retailbutton.setBounds(660, 460, 160, 30);

        jLabel35.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel35.setText(" Mobile No");
        getContentPane().add(jLabel35);
        jLabel35.setBounds(380, 490, 70, 30);

        jLabel27.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel27.setText("Qty");
        getContentPane().add(jLabel27);
        jLabel27.setBounds(730, 50, 30, 30);

        h29.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        h29.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        h29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h29ActionPerformed(evt);
            }
        });
        getContentPane().add(h29);
        h29.setBounds(860, 80, 130, 30);

        creditLabel.setForeground(new java.awt.Color(102, 102, 0));
        creditLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(creditLabel);
        creditLabel.setBounds(530, 460, 130, 30);

        weighingButton.setBackground(new java.awt.Color(255, 255, 204));
        weighingButton.setText("Weighing");
        weighingButton.setBorderPainted(false);
        weighingButton.setDefaultCapable(false);
        weighingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weighingButtonActionPerformed(evt);
            }
        });
        getContentPane().add(weighingButton);
        weighingButton.setBounds(770, 53, 90, 30);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Unit");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(510, 60, 28, 17);

        unitCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unitComboActionPerformed(evt);
            }
        });
        getContentPane().add(unitCombo);
        unitCombo.setBounds(510, 80, 80, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbuttonActionPerformed
        form_clear();

    }// GEN-LAST:event_clearbuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }// GEN-LAST:event_closebuttonActionPerformed

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_viewbuttonActionPerformed
        String grn = JOptionPane.showInputDialog(this, "Enter Bill No ?", "Bill No", JOptionPane.PLAIN_MESSAGE);
        if ("".equals(grn) || grn == null) {
            JOptionPane.showMessageDialog(this, "Invalid Bill No!", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        view(grn);
    }// GEN-LAST:event_viewbuttonActionPerformed

    private void nextbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_nextbuttonActionPerformed
        try {

            String grn = h1.getText();
            if (grn.equalsIgnoreCase("--")) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String query = "select billno from sales where billno > '" + grn + "' order by billno limit 1";
            ResultSet set1 = util.doQuery(query);
            boolean selva = false;
            String search_grn = "";
            while (set1.next()) {
                selva = true;
                search_grn = set1.getString(1);
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            view(search_grn);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_nextbuttonActionPerformed

    private void prebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_prebuttonActionPerformed
        try {
            String grn = h1.getText();
            String query;
            if (grn.equalsIgnoreCase("--")) {
                query = "select max(billno) from sales";
            } else {
                query = "select billno from sales where billno < '" + grn + "' order by billno desc limit 1";
            }

            ResultSet set1 = util.doQuery(query);
            boolean selva = false;
            String search_grn = "";
            while (set1.next()) {
                selva = true;
                search_grn = set1.getString(1);
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            view(search_grn);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }

    }// GEN-LAST:event_prebuttonActionPerformed

    private void applybuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_applybuttonActionPerformed
        apply_all_changes();
    }// GEN-LAST:event_applybuttonActionPerformed

    private void removebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_removebuttonActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int[] row_indexes = jTable1.getSelectedRows();
        for (int i = 0; i < row_indexes.length; i++) {
            s2.removeRow(jTable1.getSelectedRow());
        }
        apply_all_changes();
        serial_num();
        h17.requestFocus();
    }// GEN-LAST:event_removebuttonActionPerformed

    private void loadbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_loadbuttonActionPerformed
        load_draft();
    }// GEN-LAST:event_loadbuttonActionPerformed

    private void draftbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_draftbuttonActionPerformed
        save_draft();
        JOptionPane.showMessageDialog(this, "<html><h1>Draft Saved Successfully</h1></html>", "Saved",
                JOptionPane.PLAIN_MESSAGE);
        form_clear();
    }// GEN-LAST:event_draftbuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_savebuttonActionPerformed
        save();
    }// GEN-LAST:event_savebuttonActionPerformed

    private void tenderbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tenderbuttonActionPerformed

        multi_pay_mode.requestFocus();
        Point l = stockl.getLocationOnScreen();
        multi_pay_mode.setLocation(l.x, l.y + stockl.getHeight());
        multi_pay_mode.setSize(306, 301);
        multi_pay_mode.setVisible(true);
        h25.requestFocus();
    }// GEN-LAST:event_tenderbuttonActionPerformed

    private void retrivebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_retrivebuttonActionPerformed
        if (s5.getRowCount() > 0) {
            s5.getDataVector().removeAllElements();
            s5.fireTableDataChanged();
        }
        try {
            hold_list.requestFocus();
            Point l = jLabel28.getLocationOnScreen();
            hold_list.setLocation(l.x, l.y + jLabel28.getHeight());
            hold_list.setSize(1141, 528);
            hold_list.setVisible(true);
            String query = "select distinct billno,location,terminal,cname,items,quans,net from sales_hold where location='"
                    + h4.getText() + "' and terminal ='" + h5.getText() + "' order by billno";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                s5.addRow(new Object[] { r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5),
                        r.getString(6), r.getString(7) });
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_retrivebuttonActionPerformed

    private void holdbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_holdbuttonActionPerformed
        hold();
    }// GEN-LAST:event_holdbuttonActionPerformed

    private void h27ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h27ActionPerformed
        get_paid_bal_details();
        get_bal_details();
        multi_pay_mode.dispose();
        h22.requestFocus();

    }// GEN-LAST:event_h27ActionPerformed

    private void h25ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h25ActionPerformed
        get_paid_bal_details();
        h26.requestFocus();
    }// GEN-LAST:event_h25ActionPerformed

    private void h26ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h26ActionPerformed
        get_paid_bal_details();
        h27.requestFocus();
    }// GEN-LAST:event_h26ActionPerformed

    private void paidlActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_paidlActionPerformed
        get_bal_details();

    }// GEN-LAST:event_paidlActionPerformed

    private void selectbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_selectbuttonActionPerformed
        get_paid_bal_details();
        get_bal_details();
        multi_pay_mode.dispose();
        h22.requestFocus();
    }// GEN-LAST:event_selectbuttonActionPerformed

    private void h26FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h26FocusGained
        if (h26.getText().equals("")) {
            h26.setText("0.00");
        }
        double card = Double.parseDouble(h26.getText());
        if (card <= 0) {
            h26.setText("");
        }
    }// GEN-LAST:event_h26FocusGained

    private void h27FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h27FocusGained
        if (h27.getText().equals("")) {
            h27.setText("0.00");
        }
        double card = Double.parseDouble(h27.getText());
        if (card <= 0) {
            h27.setText("");
        }
    }// GEN-LAST:event_h27FocusGained

    private void paidlFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_paidlFocusGained

        if (paidl.getText().equals("")) {
            paidl.setText("0.00");
        }
        double paid = Double.parseDouble(paidl.getText());
        String paid2 = String.format("%." + hmany + "f", paid);
        paidl.setText(paid2);
        if (paid <= 0) {
            paidl.setText("");
        }
    }// GEN-LAST:event_paidlFocusGained

    private void h17ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h17ActionPerformed
        if (h17.getText().equals("") && s2.getRowCount() > 0) {
            h10.requestFocus();
        } else {
            get_item_details_using_barocde();
            get_stock();
            if (entry_mode.equals("Auto") && !h18.getText().equals("") && !h19.getText().equals("")) {
                h20.setText("" + 1);
                String barcode = h17.getText();
                String iname = h18.getText();
                String ino = inol.getText();
                double price = Double.parseDouble(h19.getText());
                double quan = Double.parseDouble(h20.getText());
                if (h29.getText().equals("")) {
                    h29.setText(".");
                }
                add_item(barcode, ino, iname, price, quan, h29.getText(), null);
                // iname_list.dispose();
                h17.requestFocus();
            }
        }

    }// GEN-LAST:event_h17ActionPerformed

    private void h10FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h10FocusGained
        if (h10.getText().equals("")) {
            h10.setText("0");
        }
        double disp = Double.parseDouble(h10.getText());
        if (disp <= 0) {
            h10.setText("");
        }
    }// GEN-LAST:event_h10FocusGained

    private void h10ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h10ActionPerformed
        if (h10.getText().equals("")) {
            h10.setText("0");
        }
        double disp = Double.parseDouble(h10.getText());
        if (pricel.getText().equals("Retail") && disp > max_rdis) {
            JOptionPane.showMessageDialog(this, "<html><h1>Maximum Retail Discount: " + max_rdis + "%</h1></html>",
                    "Invalid Discount", JOptionPane.ERROR_MESSAGE);
            h10.setText("" + 0);
            return;
        }

        if (pricel.getText().equals("Wholesale") && disp > max_wdis) {
            JOptionPane.showMessageDialog(this, "<html><h1>Maximum Wholesale Discount: " + max_wdis + "%</h1></html>",
                    "Invalid Discount", JOptionPane.ERROR_MESSAGE);
            h10.setText("" + 0);
            return;
        }
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            jTable1.setValueAt(disp, i, 8);
        }
        apply_all_changes();
        h14.requestFocus();

    }// GEN-LAST:event_h10ActionPerformed

    private void h14ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h14ActionPerformed
        if (h14.getText().equals("")) {
            h14.setText("0");
        }
        final_calculate();
        h16.requestFocus();

    }// GEN-LAST:event_h14ActionPerformed

    private void h14FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h14FocusGained
        if (h14.getText().equals("")) {
            h14.setText("0");
        }
        double other = Double.parseDouble(h14.getText());
        if (other <= 0) {
            h14.setText("");
        }
        h14.setBackground(color2.getBackground());
        h14.setForeground(color2.getForeground());

    }// GEN-LAST:event_h14FocusGained

    private void jLabel28FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jLabel28FocusGained
        h17.requestFocus();
    }// GEN-LAST:event_jLabel28FocusGained

    private void h10FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h10FocusLost
        h10.setBackground(color1.getBackground());
        h10.setForeground(color1.getForeground());
    }// GEN-LAST:event_h10FocusLost

    private void h14FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h14FocusLost
        h14.setBackground(color1.getBackground());
        h14.setForeground(color1.getForeground());
    }// GEN-LAST:event_h14FocusLost

    private void h22ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h22ActionPerformed

        if (h22.getText().equals("")) {
            h22.setText(".");
        }

        if (h22.toString().equals(".")) {
        } else {
            get_customer_details_using_customer_card();
        }

        h23.requestFocus();
    }// GEN-LAST:event_h22ActionPerformed

    private void h23ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h23ActionPerformed

        if (h23.getText().equals("")) {
            h23.setText(".");
        }
        if (h23.toString().equals(".")) {
        } else {
            get_customer_details_using_mobileno();
        }
        h24.requestFocus();
    }// GEN-LAST:event_h23ActionPerformed

    private void alterbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_alterbuttonActionPerformed
        alter_sales();
    }// GEN-LAST:event_alterbuttonActionPerformed

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deletebuttonActionPerformed
        delete();
    }// GEN-LAST:event_deletebuttonActionPerformed

    private void printbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_printbuttonActionPerformed
        String billformat = retail_format;
        if (pricel.getText().equals("Wholesale")) {
            billformat = wholesale_format;
        }
        new print_class().get_print(util, h1.getText(), drive, folder, billformat);
    }// GEN-LAST:event_printbuttonActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTable1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            apply_all_changes();
        }

    }// GEN-LAST:event_jTable1KeyPressed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable3MouseClicked
        selectItemFromJTable();
    }// GEN-LAST:event_jTable3MouseClicked

    private void jTable3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTable3KeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            selectItemFromJTable();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            iname_list.dispose();
            h17.requestFocus();
        }
    }// GEN-LAST:event_jTable3KeyPressed

    private void selectItemFromJTable() {
        if (jTable3.getRowCount() > 0) {
            try {
                String selectedName = jTable3.getValueAt(jTable3.getSelectedRow(), 2).toString();
                String query = "SELECT i.ino, i.iname, i.barcode, "
                        + (CompanySettingUtil.getInstance().isDisplayBatch() ? "i.batch" : "i.size")
                        + ", DATE_FORMAT(i.mfg_date, '%d/%m/%Y') as mfg_date, DATE_FORMAT(i.exp_date, '%d/%m/%Y') as exp_date, i.rprice, i.wprice, i.udes, i.subunit, i.subconv, (SELECT IFNULL(SUM(s.quan), 0) FROM stock s WHERE s.ino = i.ino) AS stock FROM item i WHERE i.iname = '"
                        + selectedName + "'";
                ResultSet set = util.doQuery(query);

                List<Object[]> rows = new ArrayList<>();
                Map<String, String> valueMap = new HashMap<>();
                while (set.next()) {
                    List<Object> row = new ArrayList<>();
                    row.add(set.getString("ino"));
                    row.add(set.getString("iname"));
                    row.add(set.getString("barcode"));
                    row.add(set.getString(CompanySettingUtil.getInstance().isDisplayBatch() ? "batch" : "size"));

                    if (CompanySettingUtil.getInstance().isDisplayMfg()) {
                        row.add(set.getString("mfg_date"));
                    }
                    if (CompanySettingUtil.getInstance().isDisplayExp()) {
                        row.add(set.getString("exp_date"));
                    }

                    row.add(set.getString("rprice"));
                    row.add(set.getString("wprice"));
                    row.add(set.getString("stock"));

                    rows.add(row.toArray());

                    valueMap.put("ino", set.getString("ino"));
                    valueMap.put("iname", set.getString("iname"));
                    valueMap.put("rprice", set.getString("rprice"));
                    valueMap.put("wprice", set.getString("wprice"));
                    valueMap.put("barcode", set.getString("barcode"));
                    valueMap.put("udes", set.getString("udes"));
                    valueMap.put("subunit", set.getString("subunit"));
                    valueMap.put("subconv", set.getString("subconv"));
                }
                if (rows.size() == 1) {
                    selectedItemDisplay(valueMap.get("barcode"), valueMap.get("ino"), valueMap.get("iname"),
                            valueMap.get("rprice"), valueMap.get("wprice"), valueMap.get("udes"),
                            valueMap.get("subunit"), valueMap.get("subconv"));
                } else {
                    // Multiple records found, show selection dialog
                    String selectedIno = showSelectionDialog(rows);
                    if (selectedIno != null) {
                        query = "SELECT ino, iname, barcode,"
                                + (CompanySettingUtil.getInstance().isDisplayBatch() ? "batch" : "size")
                                + ",rprice, wprice, udes, subunit,subconv FROM item WHERE ino = '" + selectedIno + "'";
                        set = util.doQuery(query);
                        valueMap.clear();
                        while (set.next()) {
                            selectedItemDisplay(set.getString("barcode"), set.getString("ino"), set.getString("iname"),
                                    set.getString("rprice"), set.getString("wprice"), set.getString("udes"),
                                    set.getString("subunit"), set.getString("subconv"));
                        }
                    } else {
                        // User cancelled or no selection
                        JOptionPane.showMessageDialog(this, "No item selected.", "Cancelled",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
            }
        }
        h19.requestFocus();
        iname_list.dispose();
    }

    private void selectedItemDisplay(String barcode, String ino, String iname, String rPrice, String wPrice,
            String mainUnit, String subUnit, String conv) {
        h17.setText(barcode);
        inol.setText(ino);
        h18.setText(iname);
        this.mainUnit = mainUnit;
        this.subUnit = subUnit;
        this.unitConv = conv;
        String[] items = { mainUnit, subUnit };
        unitCombo.setModel(new DefaultComboBoxModel<>(items));
        String rprice = rPrice;
        String wprice = wPrice;
        if (pricel.getText().equals("Retail")) {
            double r1 = Double.parseDouble(rprice);
            itemRate = r1;
            h19.setText(rprice);
            if (r1 == 0) {
                h19.setText("");
            }
        } else {
            double r1 = Double.parseDouble(wprice);
            itemRate = r1;
            h19.setText(wprice);
            if (r1 == 0) {
                h19.setText("");
            }
        }
        get_stock();
    }

    private String showSelectionDialog(List<Object[]> data) {
        List<String> colList = new ArrayList<>();
        colList.add("Item No");
        colList.add("Item Name");
        colList.add("Barcode");
        colList.add(CompanySettingUtil.getInstance().isDisplayBatch() ? "Batch" : "Size");

        if (CompanySettingUtil.getInstance().isDisplayMfg()) {
            colList.add("Mfg Date");
        }
        if (CompanySettingUtil.getInstance().isDisplayExp()) {
            colList.add("Exp Date");
        }

        colList.add("RPrice");
        colList.add("WPrice");
        colList.add("Stock");

        String[] columns = colList.toArray(new String[0]);

        // Prepare table data (including rprice and wprice)
        Object[][] tableData = new Object[data.size()][columns.length];
        for (int i = 0; i < data.size(); i++) {
            Object[] row = data.get(i);
            for (int j = 0; j < columns.length; j++) {
                tableData[i][j] = row[j];
            }
        }

        JTable table = new JTable(tableData, columns);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(700, 300));

        JButton okButton = new JButton("Select");
        JButton cancelButton = new JButton("Cancel");

        final String[] selectedIno = { null };

        okButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                selectedIno[0] = table.getValueAt(selectedRow, 0).toString(); // get ino
            }
            SwingUtilities.getWindowAncestor(okButton).dispose();
        });

        table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        selectedIno[0] = table.getValueAt(selectedRow, 0).toString(); // Get ino

                        // Dispose the window smoothly after key event completes
                        SwingUtilities.invokeLater(() -> {
                            Window window = SwingUtilities.getWindowAncestor(table);
                            if (window != null) {
                                window.dispose();
                            }
                        });
                        // Optionally, consume the Enter key to prevent JTable from editing cell
                        evt.consume();
                    }
                }
            }
        });
        cancelButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(cancelButton).dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JDialog dialog = new JDialog((Frame) null, "Select Item", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null); // center on screen
        dialog.setVisible(true);

        return selectedIno[0]; // null if no selection
    }

    private void jScrollPane3FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jScrollPane3FocusLost
        iname_list.dispose();
    }// GEN-LAST:event_jScrollPane3FocusLost

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
        iname_list.dispose();
    }// GEN-LAST:event_jButton4ActionPerformed

    private void h17KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_h17KeyPressed

        iname_list.requestFocus();
        jTable3.requestFocus();
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (h17.getText().equals("") && s2.getRowCount() > 0) {
                    h10.requestFocus();
                } else {
                    h19.requestFocus();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                h17.requestFocus();
                iname_list.dispose();
                break;
            case KeyEvent.VK_DOWN:
                if (!h17.getText().equals("") && h17.getText() != null && h17.getText() != "") {
                    if (s4.getRowCount() > 0) {
                        s4.getDataVector().removeAllElements();
                        s4.fireTableDataChanged();
                    }
                    try {
                        iname_list.requestFocus();
                        Point l = jLabel28.getLocationOnScreen();
                        iname_list.setLocation(l.x, l.y + jLabel28.getHeight());
                        iname_list.setSize(1281, 528);
                        iname_list.setVisible(true);
                        String query = "SELECT i.barcode, i.ino, i.iname, i.iname1, i.prate, i.mrp, i.rprice, i.wprice "
                                + "FROM item i "
                                + "INNER JOIN ( "
                                + "   SELECT iname, MIN(ino) AS min_ino "
                                + "   FROM item "
                                + "   WHERE iname LIKE '%" + h17.getText() + "%' "
                                + "   GROUP BY iname "
                                + ") sub ON i.iname = sub.iname AND i.ino = sub.min_ino "
                                + "ORDER BY i.ino "
                                + "LIMIT 500";
                        ResultSet r = util.doQuery(query);
                        while (r.next()) {
                            s4.addRow(new Object[] { r.getString(1), r.getString(2), r.getString(3), r.getString(4),
                                    r.getString(5), r.getString(6), r.getString(7), r.getString(8) });
                        }
                    } catch (ClassNotFoundException | SQLException e) {
                        System.out.println(e);
                    }
                } else {
                    if (s4.getRowCount() > 0) {
                        s4.getDataVector().removeAllElements();
                        s4.fireTableDataChanged();
                    }
                    try {
                        iname_list.requestFocus();
                        Point l = jLabel28.getLocationOnScreen();
                        iname_list.setLocation(l.x, l.y + jLabel28.getHeight());
                        iname_list.setSize(1281, 528);
                        iname_list.setVisible(true);
                        // Query for all items when h17 is empty
                        String query = "SELECT i.barcode, i.ino, i.iname, i.iname1, i.prate, i.mrp, i.rprice, i.wprice "
                                + "FROM item i "
                                + "INNER JOIN ( "
                                + "   SELECT iname, MIN(ino) AS min_ino "
                                + "   FROM item "
                                + "   GROUP BY iname "
                                + ") sub ON i.iname = sub.iname AND i.ino = sub.min_ino "
                                + "ORDER BY i.ino "
                                + "LIMIT 500";
                        ResultSet r = util.doQuery(query);
                        while (r.next()) {
                            s4.addRow(new Object[] {
                                    r.getString(1), r.getString(2), r.getString(3), r.getString(4),
                                    r.getString(5), r.getString(6), r.getString(7), r.getString(8)
                            });
                        }
                    } catch (ClassNotFoundException | SQLException e) {
                        System.out.println(e);
                    }
                }
                break;
            default:
                break;
        }
    }// GEN-LAST:event_h17KeyPressed

    private void h19ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h19ActionPerformed
        h20.requestFocus();
    }// GEN-LAST:event_h19ActionPerformed

    private void h20ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h20ActionPerformed
        // if(h17.getText().equals(""))
        // {
        // JOptionPane.showMessageDialog(this, "<html><h1>Enter Item
        // Details!</h1></html>","Invalid!",JOptionPane.ERROR_MESSAGE);
        // h17.requestFocus();return;
        // }
        // if(h18.getText().equals(""))
        // {
        // JOptionPane.showMessageDialog(this, "<html><h1>Enter Item
        // Details!</h1></html>","Invalid!",JOptionPane.ERROR_MESSAGE);
        // h17.requestFocus();return;
        // }
        // if(inol.getText().equals(""))
        // {
        // JOptionPane.showMessageDialog(this, "<html><h1>Enter Item
        // Details!</h1></html>","Invalid!",JOptionPane.ERROR_MESSAGE);
        // h17.requestFocus();return;
        // }
        if (h20.getText().equals("")) {
            h20.setText("" + 1);
        }
        // String barcode=h17.getText();
        // String iname=h18.getText();
        // double price=Double.parseDouble(h19.getText());
        // double quan=Double.parseDouble(h20.getText());
        // String ino=inol.getText();
        // add_item(barcode, ino, iname, price, quan);
        // h17.requestFocus();

        h29.requestFocus();
    }// GEN-LAST:event_h20ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable2MouseClicked

        if (jTable2.getRowCount() > 0) {
            h21.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
            h22.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 3).toString());
            h23.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 4).toString());
            h24.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 2).toString());
            String scode1 = jTable2.getValueAt(jTable2.getSelectedRow(), 6).toString();
            get_state_code(scode1);
            get_balance();
            customer_selection = true;
            double old_bal = 0;
            String query1 = "select sum(tot-paid) from cust_bal where cid='" + h21.getText() + "'";
            ResultSet r1;
            try {
                r1 = util.doQuery(query1);
                while (r1.next()) {
                    old_bal = r1.getDouble(1);
                }
                creditLabel.setText("Credit: " + String.format("%." + hmany + "f", old_bal));
            } catch (SQLException ex) {
                System.getLogger(sales.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            } catch (ClassNotFoundException ex) {
                System.getLogger(sales.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        h24.requestFocus();
        cname_list.dispose();
    }// GEN-LAST:event_jTable2MouseClicked

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTable2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable2.getRowCount() > 0) {
                h21.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
                h22.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 3).toString());
                h23.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 4).toString());
                h24.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 2).toString());
                String scode1 = jTable2.getValueAt(jTable2.getSelectedRow(), 6).toString();
                get_state_code(scode1);
                get_balance();
                customer_selection = true;
                double old_bal = 0;
                String query1 = "select sum(tot-paid) from cust_bal where cid='" + h21.getText() + "'";
                ResultSet r1;
                try {
                    r1 = util.doQuery(query1);
                    while (r1.next()) {
                        old_bal = r1.getDouble(1);
                    }
                    creditLabel.setText("Credit: " + String.format("%." + hmany + "f", old_bal));
                } catch (SQLException ex) {
                    System.getLogger(sales.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                } catch (ClassNotFoundException ex) {
                    System.getLogger(sales.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
            h24.requestFocus();
            cname_list.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cname_list.dispose();
            h24.requestFocus();
        }
    }// GEN-LAST:event_jTable2KeyPressed

    private void jScrollPane2FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jScrollPane2FocusLost
        cname_list.dispose();
    }// GEN-LAST:event_jScrollPane2FocusLost

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
        cname_list.dispose();
    }// GEN-LAST:event_jButton3ActionPerformed

    private void pricelMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_pricelMouseClicked
        get_price_change();

    }// GEN-LAST:event_pricelMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed

        get_price_change();
    }// GEN-LAST:event_jButton1ActionPerformed

    private void h24KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_h24KeyPressed

        cname_list.requestFocus();
        jTable2.requestFocus();
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (s2.getRowCount() > 0) {
                    save();
                } else {
                    h17.requestFocus();
                }

                break;
            case KeyEvent.VK_ESCAPE:
                h24.requestFocus();
                cname_list.dispose();
                break;
            case KeyEvent.VK_DOWN:
                if (s3.getRowCount() > 0) {
                    s3.getDataVector().removeAllElements();
                    s3.fireTableDataChanged();
                }
                try {
                    cname_list.requestFocus();
                    Point l = jLabel28.getLocationOnScreen();
                    cname_list.setLocation(l.x, l.y + jLabel28.getHeight());
                    cname_list.setSize(1063, 528);
                    cname_list.setVisible(true);
                    String query;
                    if (pricel.getText().equals("Retail")) {
                        query = "select cid,ctype,cname,cardno,mobile,city,scode from cust where cname like '"
                                + h24.getText() + "%' order by cname limit 300";
                    } else {
                        query = "select cid,ctype,cname,cardno,mobile,city,scode from cust where cname like '"
                                + h24.getText() + "%' and ctype='Credit Customer' order by cname limit 300";
                    }

                    ResultSet r = util.doQuery(query);
                    while (r.next()) {
                        s3.addRow(new Object[] { r.getString(1), r.getString(2), r.getString(3), r.getString(4),
                                r.getString(5), r.getString(6), r.getString(7) });
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                break;
        }
    }// GEN-LAST:event_h24KeyPressed

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTextField1FocusGained
        if (dsales.equals("Retail")) {
            h17.requestFocus();
        } else {
            h24.requestFocus();
        }

    }// GEN-LAST:event_jTextField1FocusGained

    private void h16KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_h16KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            get_pay_mode();
        }
        // TODO add your handling code here:
    }// GEN-LAST:event_h16KeyPressed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable4MouseClicked
        if (jTable4.getRowCount() > 0) {
            retrive(jTable4.getValueAt(jTable4.getSelectedRow(), 0).toString());
        }
        h17.requestFocus();
        hold_list.dispose();
    }// GEN-LAST:event_jTable4MouseClicked

    private void jTable4KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTable4KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable4.getRowCount() > 0) {
                retrive(jTable4.getValueAt(jTable4.getSelectedRow(), 0).toString());
            }
            h17.requestFocus();
            hold_list.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            hold_list.dispose();
            h17.requestFocus();
        }
    }// GEN-LAST:event_jTable4KeyPressed

    private void jScrollPane4FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jScrollPane4FocusLost
        hold_list.dispose();
    }// GEN-LAST:event_jScrollPane4FocusLost

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton5ActionPerformed
        hold_list.dispose();
    }// GEN-LAST:event_jButton5ActionPerformed

    private void hold_listFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_hold_listFocusLost
        hold_list.dispose();
    }// GEN-LAST:event_hold_listFocusLost

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel24MouseClicked
        if (utype.equals("User")) {
            JOptionPane.showMessageDialog(this, "Login as 'Administrator' to Add New Item on Sales Itself",
                    "Permission Restricted", JOptionPane.ERROR_MESSAGE);
            return;
        }
        item_master oe = new item_master(util);
        JDesktopPane desktop_pane = getDesktopPane();
        desktop_pane.add(oe);
        oe.setVisible(true);
        Dimension desktopSize = desktop_pane.getSize();
        Dimension jInternalFrameSize = oe.getSize();
        oe.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);
    }// GEN-LAST:event_jLabel24MouseClicked

    private void retailbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_retailbuttonActionPerformed
        retail_price();
    }// GEN-LAST:event_retailbuttonActionPerformed

    private void wholesalebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_wholesalebuttonActionPerformed
        wholesale_price();
    }// GEN-LAST:event_wholesalebuttonActionPerformed

    private void h29ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h29ActionPerformed
        if (h17.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "<html><h1>Enter Item Details!</h1></html>", "Invalid!",
                    JOptionPane.ERROR_MESSAGE);
            h17.requestFocus();
            return;
        }
        if (h18.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "<html><h1>Enter Item Details!</h1></html>", "Invalid!",
                    JOptionPane.ERROR_MESSAGE);
            h17.requestFocus();
            return;
        }
        if (inol.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "<html><h1>Enter Item Details!</h1></html>", "Invalid!",
                    JOptionPane.ERROR_MESSAGE);
            h17.requestFocus();
            return;
        }
        // if(h20.getText().equals("")){h20.setText(""+1);}
        String barcode = h17.getText();
        String iname = h18.getText();
        double price = Double.parseDouble(h19.getText());
        double quan = Double.parseDouble(h20.getText());
        String ino = inol.getText();
        if (h29.getText().equals("")) {
            h29.setText(".");
        }
        String unit = unitCombo.getSelectedItem() != null ? unitCombo.getSelectedItem().toString()
                : unitCombo.getItemAt(0);
        add_item(barcode, ino, iname, price, quan, h29.getText(), unit);
        h17.requestFocus();
        // TODO add your handling code here:
    }// GEN-LAST:event_h29ActionPerformed

    private void weighingButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_weighingButtonActionPerformed
        weighingButton.setEnabled(false);
        new POSWeighing().handleWeighAction(this, drive, folder, (qty) -> {
            weighingButton.setEnabled(true);
            if (qty > 0) {
                h20.setText(String.valueOf(qty));
                h20.requestFocus();
            }
        });
    }// GEN-LAST:event_weighingButtonActionPerformed

    private void unitComboActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_unitComboActionPerformed
        if (unitCombo.getSelectedItem() != null && unitCombo.getSelectedItem().equals(mainUnit)) {
            h19.setText(String.valueOf(itemRate));
        } else if (unitCombo.getSelectedItem() != null && unitCombo.getSelectedItem().equals(subUnit)) {
            try {
                double unitCOnv1 = Double.parseDouble(unitConv);
                if (unitCOnv1 == 0) {
                    h19.setText("0.00");
                    return;
                }
                double r = itemRate / unitCOnv1;
                h19.setText(String.format("%.2f", r));
            } catch (Exception e) {
                h19.setText("");
                e.printStackTrace();
            }
        }
    }// GEN-LAST:event_unitComboActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton alterbutton;
    private javax.swing.JButton applybutton;
    private javax.swing.JTextField ball;
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JDialog cname_list;
    private javax.swing.JTextField color1;
    private javax.swing.JTextField color2;
    private javax.swing.JLabel cpointsl;
    private javax.swing.JLabel creditLabel;
    private javax.swing.JButton deletebutton;
    private javax.swing.JButton draftbutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h10;
    private javax.swing.JTextField h11;
    private javax.swing.JTextField h12;
    private javax.swing.JTextField h13;
    private javax.swing.JTextField h14;
    private javax.swing.JTextField h15;
    private javax.swing.JComboBox<String> h16;
    private javax.swing.JTextField h17;
    private javax.swing.JTextField h18;
    private javax.swing.JTextField h19;
    private javax.swing.JTextField h2;
    private javax.swing.JTextField h20;
    private javax.swing.JTextField h21;
    private javax.swing.JTextField h22;
    private javax.swing.JTextField h23;
    private javax.swing.JTextField h24;
    private javax.swing.JTextField h25;
    private javax.swing.JTextField h26;
    private javax.swing.JTextField h27;
    private javax.swing.JTextField h28;
    private javax.swing.JTextField h29;
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h4;
    private javax.swing.JTextField h5;
    private javax.swing.JTextField h6;
    private javax.swing.JTextField h7;
    private javax.swing.JTextField h8;
    private javax.swing.JTextField h9;
    private javax.swing.JDialog hold_list;
    private javax.swing.JButton holdbutton;
    private javax.swing.JDialog iname_list;
    private javax.swing.JLabel infol;
    private javax.swing.JLabel inol;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton loadbutton;
    private javax.swing.JDialog multi_pay_mode;
    private javax.swing.JTextField netl;
    private javax.swing.JButton nextbutton;
    private javax.swing.JTextField paidl;
    private javax.swing.JTextField pointsl;
    private javax.swing.JTextField pratel;
    private javax.swing.JButton prebutton;
    private javax.swing.JLabel pricel;
    private javax.swing.JButton printbutton;
    private javax.swing.JButton removebutton;
    private javax.swing.JButton retailbutton;
    private javax.swing.JButton retrivebutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JButton selectbutton;
    private javax.swing.JLabel stockl;
    private javax.swing.JButton tenderbutton;
    private javax.swing.JTextField tot_pointsl;
    private javax.swing.JComboBox<String> unitCombo;
    private javax.swing.JButton viewbutton;
    private javax.swing.JButton weighingButton;
    private javax.swing.JButton wholesalebutton;
    // End of variables declaration//GEN-END:variables
}
