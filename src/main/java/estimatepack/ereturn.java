package estimatepack;

import Utils.UPIPaymentDialog;
import com.selrom.db.DataUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import menupack.menu_form;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class ereturn extends javax.swing.JInternalFrame {

    DataUtil util = null;
    DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
    DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
    boolean selvagates = false, customer_selection = false;
    String username = "", utype = "", drive = "", folder = "", version = "", retail_format = "", wholesale_format = "", round_option = "Yes";
    String scode = "", sname = "", ttype = "", stock_bill, less_prate = "", auto_sms = "", tax = "Local", entry_mode = "Auto";
    double max_rdis = 0, max_wdis = 0;
    int hmany = 2, hmany1 = 3;

    final void get_defaults() {
        try {
            String query = "select scode,ttype,stock_bill,less_prate,rdis,wdis,entry_mode,state,bformat,bformat1,hmany,round from setting_bill";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                scode = r.getString(1);
                ttype = r.getString(2);
                stock_bill = r.getString(3);
                less_prate = r.getString(4);
                max_rdis = r.getDouble(5);
                max_wdis = r.getDouble(6);
                entry_mode = r.getString(7);
                sname = r.getString(8);
                retail_format = r.getString(9);
                wholesale_format = r.getString(10);
                hmany = r.getInt(11);
                round_option = r.getString(12);
            }
            menupack.menu_form me = new menu_form();
            username = me.getUsername();
            utype = me.getUserType();
            drive = "";
            folder = Utils.AppConfig.getAppPath();
            version = me.getVersion();
            h17.requestFocusInWindow();
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
            return column == 3 || column == 5 || column == 7;
        }
    }
    sample2 s2 = new sample2();
    sample2 s3 = new sample2();
    sample2 s4 = new sample2();

    final void button_short() {
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        viewbutton.setText("<html><b>View</b><br>(Alt+V)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        prebutton.setText("<html><b>Last Entry</b><br>(Alt+R)</h6><html>");
        nextbutton.setText("<html><b>Next Entry</b><br>(Alt+N)</h6><html>");
        applybutton.setText("<html><b>Apply</b><br>(Alt+A)</h6><html>");
        removebutton.setText("<html><b>Remove</b><br>(Alt+M)</h6><html>");
        printbutton.setText("<html><b>Re-Print</b><br>(Alt+P)</h6><html>");
        alterbutton.setText("<html><b>Alter</b><br>(Alt+U)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        tenderbutton.setText("<html><b>Tender</b><br>(Alt+T)</h6><html>");
        get_date_time();
    }

    void get_date_time() {
        Date d = new Date();
        SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
        h2.setText(g.format(d));
        h3.setText(g1.format(d));
        h6.setText(username);
    }

    void display_terminal() {
        try {
            try (FileInputStream m = new FileInputStream(folder + "/Config_Files/terminal.properties")) {
                Properties p = new Properties(null);
                p.load(m);
                h4.setText(p.getProperty("location"));
                h5.setText(p.getProperty("terminal"));
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

        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(115);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(355);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(145);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(90);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(10).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(11).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(12).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(13).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(14).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(15).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(16).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(17).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(18).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(19).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(20).setPreferredWidth(0);
        String Ta = "Arial";
        int Bold = 1, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
    }

    void get_billno() {
        try {
            int sno = 1;
            String query = "select max(billno) from ereturn";
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
                JOptionPane.showMessageDialog(this, "<html><h1>Invalid Products Details!</h1></hmtl>", "Invalid", JOptionPane.ERROR_MESSAGE);
                fields_clear();
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

    void fields_clear() {
        h17.setText("");
        h18.setText("");
        h19.setText("");
        h20.setText("");
        inol.setText("");
    }

    void add_item(String barcode, String ino, String iname, double price, double quan) {
        try {
            String hsn = ".", udes = "", iname1 = "";
            double disp = 0, prate = 0, mrp = 0;
            int taxp = 0;
            boolean selva = false;
            String query = "select hsn,udes,disp,taxp,iname1,prate,mrp from item where ino='" + ino + "' and barcode='" + barcode + "' and iname='" + iname + "' ";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                hsn = r.getString(1);
                udes = r.getString(2);
                disp = r.getDouble(3);
                taxp = r.getInt(4);
                iname1 = r.getString(5);
                prate = r.getDouble(6);
                mrp = r.getDouble(7);
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "Invalid Product Details!", "Invalid", JOptionPane.ERROR_MESSAGE);
                fields_clear();
                return;
            }
            double stock = 0;
            String entry = "purchase";
            query = "select quan,entry from stock where barcode='" + barcode + "' and ino='" + ino + "' and iname='" + iname + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                stock = r.getDouble(1);
                entry = r.getString(2);
            }
            double amount, disamt, sub, taxamt, total;
            amount = price * quan;
            disamt = (disp * amount) / 100;
            sub = amount - disamt;
            taxamt = 0;
            total = sub;
            String price2 = String.format("%." + hmany + "f", price);
            String mrp2 = String.format("%." + hmany + "f", mrp);
            String amount2 = String.format("%." + hmany + "f", amount);
            String quan2 = String.format("%." + hmany1 + "f", quan);

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
                String old_barcode = jTable1.getValueAt(i, 14).toString();
                String old_entry = jTable1.getValueAt(i, 17).toString();

                if (ino.equalsIgnoreCase(old_ino) && barcode.equalsIgnoreCase(old_barcode) && entry.equalsIgnoreCase(old_entry)) {
                    selvakumar = true;
                    old_row = i;
                    old_quan = Double.parseDouble(jTable1.getValueAt(i, 3).toString());
                }
            }// row counts ends for checking

            if (selvakumar == false) {
                s2.addRow(new Object[]{jTable1.getRowCount() + 1, ino, iname, quan2, mrp2, price2, amount2, disp, disamt, sub, taxp, taxamt, total, udes, barcode, hsn, tax, entry, prate, 0, iname1});
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

            infol.setText("<html>" + iname + "&nbsp &nbsp &nbsp &nbsp <b>MRP: " + mrp2 + "</b> &nbsp &nbsp &nbsp " + quan2 + "x" + price2 + "=" + amount2 + "</html>");
            //row selected
            Rectangle rect = jTable1.getCellRect(jTable1.getRowCount() - 1, 0, true);
            jTable1.scrollRectToVisible(rect);
            jTable1.setRowSelectionInterval(jTable1.getRowCount() - 1, jTable1.getRowCount() - 1);
            //row selected ends

            apply_all_changes();
            fields_clear();
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void apply_all_changes() {
        try {
            double nsub = 0, ndis = 0, ngross = 0, ntax = 0, nquan = 0;
            int items = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                double quan = Double.parseDouble(jTable1.getValueAt(i, 3).toString());
                double price = Double.parseDouble(jTable1.getValueAt(i, 5).toString());
                double disp = Double.parseDouble(jTable1.getValueAt(i, 7).toString());
                double taxp = Double.parseDouble(jTable1.getValueAt(i, 10).toString());

                double amount = price * quan;
                double disamt = (disp * amount) / 100;
                double sub = amount - disamt;

                double taxamt = 0, total = 0;
                total = sub;
                taxamt = 0;
                String price2 = String.format("%." + hmany + "f", price);
                String amount2 = String.format("%." + hmany + "f", amount);
                String disamt2 = String.format("%." + hmany + "f", disamt);
                String sub2 = String.format("%." + hmany + "f", sub);
                String taxamt2 = String.format("%." + hmany + "f", taxamt);
                String total2 = String.format("%." + hmany + "f", total);

                jTable1.setValueAt(price2, i, 5);
                jTable1.setValueAt(amount2, i, 6);
                jTable1.setValueAt(disamt2, i, 8);
                jTable1.setValueAt(sub2, i, 9);
                jTable1.setValueAt(taxamt2, i, 11);
                jTable1.setValueAt(total2, i, 12);

                nquan = nquan + quan;
                nsub = nsub + amount;
                ndis = ndis + disamt;
                ngross = ngross + sub;
                ntax = ntax + taxamt;
                items = items + 1;
            }//table row counts ends

            String nsub2 = String.format("%." + hmany + "f", nsub);
            String ndis2 = String.format("%." + hmany + "f", ndis);
            String ngross2 = String.format("%." + hmany + "f", ngross);
            String ntax2 = String.format("%." + hmany + "f", ntax);

            h7.setText("" + items);
            h8.setText("" + nquan);
            h9.setText("" + nsub2);
            h11.setText("" + ndis2);
            h12.setText("" + ngross2);
            h13.setText("" + ntax2);
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
            String gross2 = String.format("%." + hmany + "f", gross);
            h12.setText("" + gross2);

            String grant = String.format("%." + hmany + "f", gt);
            if (round_option.equals("Yes")) {
                //round off starts
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
                //round off ends
            }//round option ends
            else {
                h15.setText("" + 0);
                netl.setText(grant);
            } //no round option

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
            JOptionPane.showMessageDialog(this, "<html><h1>Paid Amount is Lessthan Bill Amount!</h1></html>", "Invalid", JOptionPane.ERROR_MESSAGE);
            paidl.setText("0.00");
            ball.setText("0.00");
        } else {
            ball.setText(bal2);
            h22.requestFocus();
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
                    tot = net - paid;
                    if (tot < 0) {
                        tot = 0;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                final double fPaid = paid;
                final double fNet = net;

                UPIPaymentDialog dialog = new UPIPaymentDialog(frame, tot, (t) -> {
                    double totalPaid = fPaid + t;
                    paidl.setText(String.format("%.2f", totalPaid));
                    ball.setText(String.format("%.2f", fNet - totalPaid));
                    h22.requestFocus();
                });
            });
            return;
        } else {
            multi_pay_mode.requestFocus();
            Point l = jLabel1.getLocationOnScreen();
            multi_pay_mode.setLocation(l.x, l.y + jLabel1.getHeight());
            multi_pay_mode.setSize(306, 301);
            multi_pay_mode.setVisible(true);
            h25.requestFocus();
        }
    }

    void save() {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found to Save!", "No Records", JOptionPane.ERROR_MESSAGE);
                h17.requestFocus();
                return;
            }
            get_date_time();
            apply_all_changes();

            if (selvagates == false) {
                get_billno();
            }
            boolean selva = false;
            String query = "select billno from ereturn where billno='" + h1.getText() + "'";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                JOptionPane.showMessageDialog(this, "Already Exist!\nUse 'Alter' Option to Alter...", "Already Exist", JOptionPane.ERROR_MESSAGE);
                savebutton.requestFocus();
                return;
            }
            if (h21.getText().equals("")) {
                h22.setText("--");
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
            if (paidl.getText().equals("")) {
                paidl.setText("0.00");
            }
            if (ball.getText().equals("")) {
                ball.setText("0.00");
            }

            String cid = h21.getText();
            double net = Double.parseDouble(netl.getText());

//credit ereturn checking
            if (pricel.getText().equals("Wholesale")) {
                String ctype = "Retail";
                query = "select ctype from cust where cid='" + cid + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    ctype = r.getString(1);
                }
                if (ctype.equals("Retail")) {
                    JOptionPane.showMessageDialog(this, "<html>For Wholesale Bill First Create Customer Master<br>, Create as <b> 'Credit Customer'</b></html>", "Invalid Wholesale Customer!", JOptionPane.ERROR_MESSAGE);
                    h24.requestFocus();
                    return;
                }
            }//price lable is wholesale ends

            int due_days = 0;
            //payment mode credit starts
            if (h16.getSelectedItem().equals("Credit")) {
                String ctype = "Retail";
                query = "select ctype from cust where cid='" + cid + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    ctype = r.getString(1);
                }

                if (ctype.equals("Retail")) {
                    JOptionPane.showMessageDialog(this, "<html>For Wholesale Bill First Create Customer Master<br>, Create as <b> 'Credit Customer'</b></html>", "Invalid Wholesale Customer!", JOptionPane.ERROR_MESSAGE);
                    h24.setText("");
                    h24.requestFocus();
                    return;
                }
            }
            //paymode credits ends
            //credit ereturn checking ends
            String cname = h24.getText();
            String cardno = h22.getText();
            String mobile = h23.getText();

            boolean cust_card = false;
            int newcid = 0;
            //customer card checking
            if (customer_selection == false && mobile.length() == 10) {
                query = "select cid from cust where cardno='" + cardno + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    cust_card = true;
                }
                if (cust_card == true) {
                    JOptionPane.showMessageDialog(this, "Customer Card No Already Exist!", "Already Exist", JOptionPane.ERROR_MESSAGE);
                    h22.requestFocus();
                    return;
                }
                query = "select max(cid) from cust";
                r = util.doQuery(query);
                while (r.next()) {
                    newcid = r.getInt(1);
                }
                newcid = newcid + 1;
                cid = newcid + "";
            }
            //customer card checking ends

            int aa = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (aa == JOptionPane.NO_OPTION) {
                return;
            }
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText());
            String date = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);
            String billno = h1.getText();
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

            String paid = paidl.getText();
            String bal = ball.getText();
            String cash = h25.getText();
            String card = h26.getText();
            String others = h27.getText();
            String credit = "" + 0;
            String upi = "0";

            if (pby.equalsIgnoreCase("Cash")) {
                cash = net + "";
                card = "" + 0;
                credit = "" + 0;
                others = "" + 0;
                upi = "0";
            } else if (pby.equalsIgnoreCase("Card")) {
                cash = "" + 0;
                card = "" + net;
                credit = "" + 0;
                others = "" + 0;
                upi = "0";
            } else if (pby.equalsIgnoreCase("Credit")) {
                cash = "" + 0;
                card = "" + 0;
                credit = "" + net;
                others = "" + 0;
                upi = "0";
            } else if (pby.equalsIgnoreCase("UPI")) {
                cash = "" + 0;
                card = "" + 0;
                credit = "" + 0;
                others = "" + 0;
                upi = "" + net;
            } else if (pby.equalsIgnoreCase("Others")) {
                cash = "" + 0;
                card = "" + 0;
                credit = "" + 0;
                others = "" + net;
                upi = "0";
            }
            String price_type = pricel.getText();

            Connection conn = util.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement psEreturnItems = conn.prepareStatement("insert into ereturn_items values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            PreparedStatement psUpdateStock = conn.prepareStatement("update stock set quan=quan+? where barcode=? and ino=? and iname=? and entry=?");

            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String serial = jTable1.getValueAt(i, 0).toString();
                String ino = jTable1.getValueAt(i, 1).toString();
                String iname = jTable1.getValueAt(i, 2).toString();
                String quan = jTable1.getValueAt(i, 3).toString();
                String mrp = jTable1.getValueAt(i, 4).toString();
                String price = jTable1.getValueAt(i, 5).toString();
                String amount = jTable1.getValueAt(i, 6).toString();
                String disp1 = jTable1.getValueAt(i, 7).toString();
                String disamt1 = jTable1.getValueAt(i, 8).toString();
                String sub1 = jTable1.getValueAt(i, 9).toString();
                String taxp1 = jTable1.getValueAt(i, 10).toString();
                String taxamt1 = jTable1.getValueAt(i, 11).toString();
                String total = jTable1.getValueAt(i, 12).toString();
                String udes = jTable1.getValueAt(i, 13).toString();
                String barcode = jTable1.getValueAt(i, 14).toString();
                String hsn = jTable1.getValueAt(i, 15).toString();
                String entry = jTable1.getValueAt(i, 17).toString();
                String cost_rate = jTable1.getValueAt(i, 18).toString();
                String profit = jTable1.getValueAt(i, 19).toString();
                String iname1 = "";
                if (jTable1.getValueAt(i, 20) != null) {
                     iname1 = jTable1.getValueAt(i, 20).toString();
                }
                String item_type = "Old";

                psEreturnItems.setString(1, billno);
                psEreturnItems.setString(2, date);
                psEreturnItems.setString(3, cid);
                psEreturnItems.setString(4, serial);
                psEreturnItems.setString(5, ino);
                psEreturnItems.setString(6, iname);
                psEreturnItems.setString(7, quan);
                psEreturnItems.setString(8, mrp);
                psEreturnItems.setString(9, price);
                psEreturnItems.setString(10, amount);
                psEreturnItems.setString(11, disp1);
                psEreturnItems.setString(12, disamt1);
                psEreturnItems.setString(13, sub1);
                psEreturnItems.setString(14, taxp1);
                psEreturnItems.setString(15, taxamt1);
                psEreturnItems.setString(16, total);
                psEreturnItems.setString(17, udes);
                psEreturnItems.setString(18, barcode);
                psEreturnItems.setString(19, hsn);
                psEreturnItems.setString(20, tax);
                psEreturnItems.setString(21, entry);
                psEreturnItems.setString(22, cost_rate);
                psEreturnItems.setString(23, profit);
                psEreturnItems.setString(24, iname1);
                psEreturnItems.setString(25, price_type);
                psEreturnItems.setString(26, ttype);
                psEreturnItems.setString(27, item_type);
                psEreturnItems.addBatch();

                psUpdateStock.setDouble(1, Double.parseDouble(quan));
                psUpdateStock.setString(2, barcode);
                psUpdateStock.setString(3, ino);
                psUpdateStock.setString(4, iname);
                psUpdateStock.setString(5, entry);
                psUpdateStock.addBatch();
            }//jtable row counts ends
            psEreturnItems.executeBatch();
            psUpdateStock.executeBatch();

            PreparedStatement psEreturn = conn.prepareStatement("insert into ereturn values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            psEreturn.setString(1, billno);
            psEreturn.setString(2, date);
            psEreturn.setString(3, time);
            psEreturn.setString(4, location);
            psEreturn.setString(5, cashier);
            psEreturn.setString(6, terminal);
            psEreturn.setString(7, items);
            psEreturn.setString(8, quans);
            psEreturn.setString(9, sub);
            psEreturn.setString(10, disp);
            psEreturn.setString(11, disamt);
            psEreturn.setString(12, gross);
            psEreturn.setString(13, taxamt);
            psEreturn.setString(14, addamt);
            psEreturn.setString(15, round);
            psEreturn.setDouble(16, net);
            psEreturn.setString(17, pby);
            psEreturn.setString(18, paid);
            psEreturn.setString(19, bal);
            psEreturn.setString(20, cash);
            psEreturn.setString(21, card);
            psEreturn.setString(22, credit);
            psEreturn.setString(23, others);
            psEreturn.setString(24, upi);
            psEreturn.setString(25, price_type);
            psEreturn.setString(26, ttype);
            psEreturn.setString(27, tax);
            psEreturn.setString(28, cid);
            psEreturn.setString(29, cardno);
            psEreturn.setString(30, cname);
            psEreturn.setString(31, mobile);
            psEreturn.setString(32, username);
            psEreturn.setString(33, last);
            psEreturn.executeUpdate();

            conn.commit();
            int count = 1;
            if (count > 0) {
                int bb = JOptionPane.showConfirmDialog(this, "<html><h1>You Want to Print Bill ?</h1></html>", "Saved Successfully", JOptionPane.YES_OPTION);
                if (bb == JOptionPane.YES_OPTION) {
                    String billformat = retail_format;
                    if (pricel.getText().equals("Wholesale")) {
                        billformat = wholesale_format;
                    }
                    new print_class_estimate_return().get_print(util, billno, drive, folder, billformat);
                }
                form_clear();
            } else {
                JOptionPane.showMessageDialog(this, "Check Product Entries and then Try Again!", "Invalid Products", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    void form_clear() {
        try {
            fields_clear();
            get_date_time();
            display_terminal();
            get_net_default_values();
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
            h22.setText("");
            h23.setText("");
            h24.setText("");
            infol.setText("");
            if (s2.getRowCount() > 0) {
                s2.getDataVector().removeAllElements();
                s2.fireTableDataChanged();
            }
            if (pricel.getText().equals("Wholesale")) {
                h16.setSelectedItem("Credit");
            }
            savebutton.setVisible(true);
            viewbutton.setVisible(true);
            deletebutton.setVisible(false);
            alterbutton.setVisible(false);
            printbutton.setVisible(false);
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
            String query = "select distinct billno from ereturn where billno='" + billno + "'";
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
                selvagates = true;
                if (s2.getRowCount() > 0) {
                    s2.getDataVector().removeAllElements();
                    s2.fireTableDataChanged();
                }

                query = "select billno,date_format(dat,'%d/%m/%Y'),tim,location,terminal,cashier,items,quans,sub,disp,disamt,gross,taxamt,addamt,round,pby,net,paid,bal,cash,card,others,price_type,tax_type,tax,cid,cardno,mobile,cname from ereturn where billno='" + billno + "'";
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

                    String net2 = String.format("%." + hmany + "f", net);
                    netl.setText(net2);
                }

                query = "select serial,ino,iname,quan,mrp,price,amount,disp,disamt,sub,taxp,taxamt,total,udes,barcode,hsn,tax,entry,cost_rate,profit,iname1 from ereturn_items where billno='" + billno + "'";
                set1 = util.doQuery(query);
                while (set1.next()) {
                    String mrp = String.format("%." + hmany + "f", set1.getDouble(5));
                    String price = String.format("%." + hmany + "f", set1.getDouble(6));
                    String amount = String.format("%." + hmany + "f", set1.getDouble(7));
                    String disamt = String.format("%." + hmany + "f", set1.getDouble(9));
                    String sub = String.format("%." + hmany + "f", set1.getDouble(10));
                    String taxamt = String.format("%." + hmany + "f", set1.getDouble(12));
                    String total = String.format("%." + hmany + "f", set1.getDouble(13));

                    String quan2 = String.format("%." + hmany + "f", set1.getDouble(4));
                    String[] split3 = quan2.split("\\.");
                    int what3 = Integer.parseInt(split3[1]);
                    if (what3 <= 0) {
                        quan2 = split3[0];
                    }

                    s2.addRow(new Object[]{set1.getString(1), set1.getString(2), set1.getString(3), quan2, mrp, price, amount, set1.getString(8), disamt,
                        sub, set1.getInt(11), taxamt, total, set1.getString(14), set1.getString(15), set1.getString(16), set1.getString(17), set1.getString(18), set1.getString(19), set1.getString(20), set1.getString(21)});
                }
                get_customer_details_using_mobileno();
            }//if selva true ends
        } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
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
            }
            if (customer_selection == false) {
                h21.setText("--");
                h24.setText("");
            }
            get_state_code(scode1);
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
            }
            if (customer_selection == false) {
                h21.setText("--");
                h24.setText("");
                h23.setText("");
            }
            get_state_code(scode1);
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

    void load_iname_table() {
        try {
            s4.addColumn("Barcode");
            s4.addColumn("It.Code");
            s4.addColumn("Item Name");
            s4.addColumn("Purchase Price");
            s4.addColumn("MRP");
            s4.addColumn("Retail Price");
            s4.addColumn("Wholesale Price");
            jTable3.setModel(s4);
            jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(150);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(394);
            jTable3.getColumnModel().getColumn(3).setPreferredWidth(120);
            jTable3.getColumnModel().getColumn(4).setPreferredWidth(120);
            jTable3.getColumnModel().getColumn(5).setPreferredWidth(120);
            jTable3.getColumnModel().getColumn(6).setPreferredWidth(120);

            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable3.getTableHeader().setFont(new Font(Ta, Bold, size));
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
        if (s2.getRowCount() > 0) {
            JOptionPane.showMessageDialog(this, "<html><h1>Items Already Entered!</h1></html>", "Invalid", JOptionPane.ERROR_MESSAGE);
            h17.requestFocus();
            return;
        }
        if (pricel.getText().equals("Retail")) {
            h16.setSelectedItem("Credit");
            h24.requestFocus();
            pricel.setText("Wholesale");
        } else {
            h16.setSelectedItem("Cash");
            h17.requestFocus();
            pricel.setText("Retail");
        }
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

    void alter_ereturn() {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found to Alter!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selvagates == false) {
                JOptionPane.showMessageDialog(this, "User 'View' Option Before Alter!", "User 'View' Option", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (utype.equalsIgnoreCase("User")) {
                JOptionPane.showMessageDialog(this, "Login as 'Administrator' to Alter!", "Permission Restricted", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int as = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Alter ?</h1></html>", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            String billno = h1.getText();
            ArrayList<String> barcode = new ArrayList<>();
            ArrayList<String> ino = new ArrayList<>();
            ArrayList<String> iname = new ArrayList<>();
            ArrayList<String> quan = new ArrayList<>();
            ArrayList<String> entry = new ArrayList<>();

            String query = "select barcode,ino,iname,quan,entry from ereturn_items where billno='" + billno + "'";
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

            ArrayList query_batch = new ArrayList();
            if (selva == true) {
                for (int i = 0; i < barcode.size(); i++) {
                    query_batch.add("update stock set quan=quan-" + quan.get(i) + " where barcode='" + barcode.get(i) + "' and ino='" + ino.get(i) + "' and iname='" + iname.get(i) + "' and entry='" + entry.get(i) + "'");
                }
            }//selva true ends
            query_batch.add("delete from ereturn where billno='" + billno + "'");
            query_batch.add("delete from ereturn_items where billno='" + billno + "'");
            if (h16.getSelectedItem().equals("Credit")) {
                query_batch.add("delete from cust_bal where billno='" + billno + "'");
            }
            int count = util.doManipulation_Batch(query_batch);
            if (count > 0) {
                save();
            } else {
                JOptionPane.showMessageDialog(this, "Check Entries and then Try Again!", "Invalid", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void delete() {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found to Delete!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selvagates == false) {
                JOptionPane.showMessageDialog(this, "User 'View' Option Before Delete!", "User 'View' Option", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (utype.equalsIgnoreCase("User")) {
                JOptionPane.showMessageDialog(this, "Login as 'Administrator' to Delete!", "Permission Restricted", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int as = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Delete ?</h1></html>", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            String billno = h1.getText();
            ArrayList<String> barcode = new ArrayList<>();
            ArrayList<String> ino = new ArrayList<>();
            ArrayList<String> iname = new ArrayList<>();
            ArrayList<String> quan = new ArrayList<>();
            ArrayList<String> entry = new ArrayList<>();

            String query = "select barcode,ino,iname,quan,entry from ereturn_items where billno='" + billno + "'";
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

            Connection conn = util.getConnection();
            conn.setAutoCommit(false);

            if (selva == true) {
                PreparedStatement psUpdateStock = conn.prepareStatement("update stock set quan=quan-? where barcode=? and ino=? and iname=? and entry=?");
                for (int i = 0; i < barcode.size(); i++) {
                    psUpdateStock.setDouble(1, Double.parseDouble(quan.get(i)));
                    psUpdateStock.setString(2, barcode.get(i));
                    psUpdateStock.setString(3, ino.get(i));
                    psUpdateStock.setString(4, iname.get(i));
                    psUpdateStock.setString(5, entry.get(i));
                    psUpdateStock.addBatch();
                }
                psUpdateStock.executeBatch();
            }//selva true ends

            PreparedStatement psDeleteEreturn = conn.prepareStatement("delete from ereturn where billno=?");
            psDeleteEreturn.setString(1, billno);
            psDeleteEreturn.executeUpdate();

            PreparedStatement psDeleteEreturnItems = conn.prepareStatement("delete from ereturn_items where billno=?");
            psDeleteEreturnItems.setString(1, billno);
            psDeleteEreturnItems.executeUpdate();

            if (h16.getSelectedItem().equals("Credit")) {
                PreparedStatement psDeleteCustBal = conn.prepareStatement("delete from cust_bal where billno=?");
                psDeleteCustBal.setString(1, billno);
                psDeleteCustBal.executeUpdate();
            }
            conn.commit();
            int count = 1;
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Deleted Successfully</h1></html>", "Deleted", JOptionPane.PLAIN_MESSAGE);
                form_clear();
            } else {
                JOptionPane.showMessageDialog(this, "Check Entries and then Try Again!", "Invalid", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
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

    public ereturn(DataUtil util) {
        initComponents();

        this.setSize(1307, 659);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
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
        get_net_default_values();
        deletebutton.setVisible(false);
        alterbutton.setVisible(false);
        printbutton.setVisible(false);
        h13.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
        savebutton = new javax.swing.JButton();
        tenderbutton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        h21 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        h22 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
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
        h13 = new javax.swing.JTextField();

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
        selectbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/selectall45.png"))); // NOI18N
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
            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(h25, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(h26, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(h27, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(h28, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(selectbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(h25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(h26, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(h27, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(h28, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addComponent(selectbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout multi_pay_modeLayout = new javax.swing.GroupLayout(multi_pay_mode.getContentPane());
        multi_pay_mode.getContentPane().setLayout(multi_pay_modeLayout);
        multi_pay_modeLayout.setHorizontalGroup(
            multi_pay_modeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        multi_pay_modeLayout.setVerticalGroup(
            multi_pay_modeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        iname_list.setUndecorated(true);
        iname_list.getContentPane().setLayout(null);

        jScrollPane3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jScrollPane3FocusLost(evt);
            }
        });

        jTable3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
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
        jScrollPane3.setBounds(0, 0, 1130, 490);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete22.png"))); // NOI18N
        jButton4.setMnemonic('o');
        jButton4.setText("Close");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        iname_list.getContentPane().add(jButton4);
        jButton4.setBounds(990, 490, 140, 30);

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
        setNextFocusableComponent(h17);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 249, 233));

        infol.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        infol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(infol, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(infol, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(260, 0, 600, 50);

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
        h14.setBounds(100, 360, 170, 30);

        h15.setEditable(false);
        h15.setBackground(new java.awt.Color(242, 236, 236));
        h15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h15.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h15.setBorder(null);
        jPanel2.add(h15);
        h15.setBounds(100, 390, 170, 30);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("Other.Charges");
        jPanel2.add(jLabel17);
        jLabel17.setBounds(0, 360, 100, 30);

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
        jLabel18.setBounds(0, 420, 110, 30);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Terminal");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(0, 100, 100, 30);

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText("Round Amount");
        jPanel2.add(jLabel22);
        jLabel22.setBounds(0, 390, 100, 30);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Cashier");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(0, 130, 100, 30);

        h11.setEditable(false);
        h11.setBackground(new java.awt.Color(242, 236, 236));
        h11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h11.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h11.setBorder(null);
        jPanel2.add(h11);
        h11.setBounds(100, 300, 170, 30);

        h16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Card", "Credit", "UPI", "Others", "Multi Pay" }));
        h16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h16KeyPressed(evt);
            }
        });
        jPanel2.add(h16);
        h16.setBounds(100, 420, 180, 30);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(1010, 0, 280, 630);

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 110, 990, 380);

        h18.setEditable(false);
        h18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        h18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(h18);
        h18.setBounds(200, 80, 520, 30);

        h20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        h20.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        h20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h20ActionPerformed(evt);
            }
        });
        getContentPane().add(h20);
        h20.setBounds(860, 80, 130, 30);

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
        h19.setBounds(720, 80, 140, 30);

        clearbutton.setBackground(new java.awt.Color(255, 255, 153));
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
        clearbutton.setBounds(710, 580, 140, 50);

        closebutton.setBackground(new java.awt.Color(255, 255, 153));
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
        closebutton.setBounds(850, 580, 140, 50);

        viewbutton.setBackground(new java.awt.Color(255, 255, 153));
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
        viewbutton.setBounds(850, 530, 140, 50);

        nextbutton.setBackground(new java.awt.Color(255, 255, 153));
        nextbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        nextbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/next45.png"))); // NOI18N
        nextbutton.setMnemonic('u');
        nextbutton.setText("Next Entry");
        nextbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(nextbutton);
        nextbutton.setBounds(710, 530, 140, 50);

        prebutton.setBackground(new java.awt.Color(255, 255, 153));
        prebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        prebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pre45.png"))); // NOI18N
        prebutton.setMnemonic('r');
        prebutton.setText("Last Entry");
        prebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(prebutton);
        prebutton.setBounds(570, 530, 140, 50);

        applybutton.setBackground(new java.awt.Color(255, 255, 153));
        applybutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        applybutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/apply45.png"))); // NOI18N
        applybutton.setMnemonic('a');
        applybutton.setText("Appy");
        applybutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applybuttonActionPerformed(evt);
            }
        });
        getContentPane().add(applybutton);
        applybutton.setBounds(280, 530, 140, 50);

        removebutton.setBackground(new java.awt.Color(255, 255, 153));
        removebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        removebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/remove45.png"))); // NOI18N
        removebutton.setMnemonic('m');
        removebutton.setText("Remove");
        removebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(removebutton);
        removebutton.setBounds(420, 530, 150, 50);

        savebutton.setBackground(new java.awt.Color(255, 255, 153));
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
        savebutton.setBounds(0, 530, 140, 50);

        tenderbutton.setBackground(new java.awt.Color(255, 255, 153));
        tenderbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tenderbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cash45.png"))); // NOI18N
        tenderbutton.setMnemonic('t');
        tenderbutton.setText("Tender");
        tenderbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenderbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(tenderbutton);
        tenderbutton.setBounds(140, 530, 140, 50);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Description");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(200, 50, 90, 30);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Qty");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(860, 50, 40, 30);

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(null);

        jLabel28.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(153, 0, 153));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("EST.RETURN");
        jLabel28.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jLabel28FocusGained(evt);
            }
        });
        jPanel4.add(jLabel28);
        jLabel28.setBounds(0, 0, 258, 50);

        getContentPane().add(jPanel4);
        jPanel4.setBounds(0, 0, 260, 50);

        jLabel23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel23.setText(" Customer Name");
        getContentPane().add(jLabel23);
        jLabel23.setBounds(550, 490, 110, 30);

        jLabel24.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel24.setText("Item");
        getContentPane().add(jLabel24);
        jLabel24.setBounds(5, 50, 40, 30);

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

        jLabel27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel27.setText(" Mobile No");
        getContentPane().add(jLabel27);
        jLabel27.setBounds(380, 490, 70, 30);

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
        jLabel34.setBounds(720, 50, 50, 30);

        inol.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        getContentPane().add(inol);
        inol.setBounds(670, 50, 50, 30);

        color2.setBackground(new java.awt.Color(204, 0, 0));
        color2.setForeground(new java.awt.Color(255, 255, 255));
        color2.setText("2220");
        getContentPane().add(color2);
        color2.setBounds(350, 60, 50, 20);

        color1.setBackground(new java.awt.Color(242, 236, 236));
        getContentPane().add(color1);
        color1.setBounds(300, 60, 50, 20);

        alterbutton.setBackground(new java.awt.Color(255, 255, 153));
        alterbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        alterbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/upload45.png"))); // NOI18N
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
        deletebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete45.png"))); // NOI18N
        deletebutton.setMnemonic('d');
        deletebutton.setText("Delete");
        deletebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(deletebutton);
        deletebutton.setBounds(280, 580, 140, 50);

        printbutton.setBackground(new java.awt.Color(255, 255, 153));
        printbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        printbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/print45.png"))); // NOI18N
        printbutton.setMnemonic('p');
        printbutton.setText("Re-Print");
        printbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(printbutton);
        printbutton.setBounds(140, 580, 140, 50);

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

        h13.setEditable(false);
        h13.setBackground(new java.awt.Color(255, 153, 153));
        h13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h13.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        h13.setBorder(null);
        h13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h13ActionPerformed(evt);
            }
        });
        getContentPane().add(h13);
        h13.setBounds(620, 580, 70, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        form_clear();

    }//GEN-LAST:event_clearbuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewbuttonActionPerformed
        String grn = JOptionPane.showInputDialog(this, "Enter Bill No ?", "Bill No", JOptionPane.PLAIN_MESSAGE);
        if ("".equals(grn) || grn == null) {
            JOptionPane.showMessageDialog(this, "Invalid Bill No!", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        view(grn);
    }//GEN-LAST:event_viewbuttonActionPerformed

    private void nextbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextbuttonActionPerformed
        try {

            String grn = h1.getText();
            if (grn.equalsIgnoreCase("--")) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String query = "select billno from ereturn where billno > '" + grn + "' order by billno limit 1";
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
    }//GEN-LAST:event_nextbuttonActionPerformed

    private void prebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prebuttonActionPerformed
        try {
            String grn = h1.getText();
            String query;
            if (grn.equalsIgnoreCase("--")) {
                query = "select max(billno) from ereturn";
            } else {
                query = "select billno from ereturn where billno < '" + grn + "' order by billno desc limit 1";
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

    }//GEN-LAST:event_prebuttonActionPerformed

    private void applybuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applybuttonActionPerformed
        apply_all_changes();
    }//GEN-LAST:event_applybuttonActionPerformed

    private void removebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removebuttonActionPerformed
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

    }//GEN-LAST:event_removebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        save();
    }//GEN-LAST:event_savebuttonActionPerformed

    private void tenderbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenderbuttonActionPerformed

        multi_pay_mode.requestFocus();
        Point l = jLabel1.getLocationOnScreen();
        multi_pay_mode.setLocation(l.x, l.y + jLabel1.getHeight());
        multi_pay_mode.setSize(306, 301);
        multi_pay_mode.setVisible(true);
        h25.requestFocus();
    }//GEN-LAST:event_tenderbuttonActionPerformed

    private void h27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h27ActionPerformed
        get_paid_bal_details();
        get_bal_details();
        multi_pay_mode.dispose();
        h22.requestFocus();

    }//GEN-LAST:event_h27ActionPerformed

    private void h25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h25ActionPerformed
        get_paid_bal_details();
        h26.requestFocus();
    }//GEN-LAST:event_h25ActionPerformed

    private void h26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h26ActionPerformed
        get_paid_bal_details();
        h27.requestFocus();
    }//GEN-LAST:event_h26ActionPerformed

    private void paidlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paidlActionPerformed
        get_bal_details();

    }//GEN-LAST:event_paidlActionPerformed

    private void selectbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectbuttonActionPerformed
        get_paid_bal_details();
        get_bal_details();
        multi_pay_mode.dispose();
        h22.requestFocus();
    }//GEN-LAST:event_selectbuttonActionPerformed

    private void h26FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h26FocusGained
        if (h26.getText().equals("")) {
            h26.setText("0.00");
        }
        double card = Double.parseDouble(h26.getText());
        if (card <= 0) {
            h26.setText("");
        }
    }//GEN-LAST:event_h26FocusGained

    private void h27FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h27FocusGained
        if (h27.getText().equals("")) {
            h27.setText("0.00");
        }
        double card = Double.parseDouble(h27.getText());
        if (card <= 0) {
            h27.setText("");
        }
    }//GEN-LAST:event_h27FocusGained

    private void paidlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_paidlFocusGained

        if (paidl.getText().equals("")) {
            paidl.setText("0.00");
        }
        double paid = Double.parseDouble(paidl.getText());
        String paid2 = String.format("%." + hmany + "f", paid);
        paidl.setText(paid2);
        if (paid <= 0) {
            paidl.setText("");
        }
    }//GEN-LAST:event_paidlFocusGained

    private void h17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h17ActionPerformed
        if (h17.getText().equals("") && s2.getRowCount() > 0) {
            h10.requestFocus();
        } else {
            get_item_details_using_barocde();
            if (entry_mode.equals("Auto") && !h18.getText().equals("") && !h19.getText().equals("")) {
                h20.setText("" + 1);
                String barcode = h17.getText();
                String iname = h18.getText();
                String ino = inol.getText();
                double price = Double.parseDouble(h19.getText());
                double quan = Double.parseDouble(h20.getText());
                add_item(barcode, ino, iname, price, quan);
                h17.requestFocus();
            }
        }

    }//GEN-LAST:event_h17ActionPerformed

    private void h10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h10FocusGained
        if (h10.getText().equals("")) {
            h10.setText("0");
        }
        double disp = Double.parseDouble(h10.getText());
        if (disp <= 0) {
            h10.setText("");
        }
        h10.setBackground(color2.getBackground());
        h10.setForeground(color2.getForeground());

    }//GEN-LAST:event_h10FocusGained

    private void h10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h10ActionPerformed
        if (h10.getText().equals("")) {
            h10.setText("0");
        }
        double disp = Double.parseDouble(h10.getText());
        if (pricel.getText().equals("Retail") && disp > max_rdis) {
            JOptionPane.showMessageDialog(this, "<html><h1>Maximum Retail Discount: " + max_rdis + "%</h1></html>", "Invalid Discount", JOptionPane.ERROR_MESSAGE);
            h10.setText("" + 0);
            return;
        }

        if (pricel.getText().equals("Wholesale") && disp > max_wdis) {
            JOptionPane.showMessageDialog(this, "<html><h1>Maximum Wholesale Discount: " + max_wdis + "%</h1></html>", "Invalid Discount", JOptionPane.ERROR_MESSAGE);
            h10.setText("" + 0);
            return;
        }
        if (disp > 0) {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                jTable1.setValueAt(disp, i, 7);
            }
            apply_all_changes();
        }//discount is greaterthan zero
        h14.requestFocus();

    }//GEN-LAST:event_h10ActionPerformed

    private void h14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h14ActionPerformed
        if (h14.getText().equals("")) {
            h14.setText("0");
        }
        final_calculate();
        h16.requestFocus();

    }//GEN-LAST:event_h14ActionPerformed

    private void h14FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h14FocusGained
        if (h14.getText().equals("")) {
            h14.setText("0");
        }
        double other = Double.parseDouble(h14.getText());
        if (other <= 0) {
            h14.setText("");
        }
        h14.setBackground(color2.getBackground());
        h14.setForeground(color2.getForeground());

    }//GEN-LAST:event_h14FocusGained

    private void jLabel28FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jLabel28FocusGained
        h17.requestFocus();
    }//GEN-LAST:event_jLabel28FocusGained

    private void h10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h10FocusLost
        h10.setBackground(color1.getBackground());
        h10.setForeground(color1.getForeground());
    }//GEN-LAST:event_h10FocusLost

    private void h14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h14FocusLost
        h14.setBackground(color1.getBackground());
        h14.setForeground(color1.getForeground());
    }//GEN-LAST:event_h14FocusLost

    private void h16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_h16KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            get_pay_mode();
        }

    }//GEN-LAST:event_h16KeyPressed

    private void h22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h22ActionPerformed

        if (h22.getText().equals("")) {
            h22.setText(".");
        }

        if (h22.toString().equals(".")) {
        } else {
            get_customer_details_using_customer_card();
        }

        h23.requestFocus();
    }//GEN-LAST:event_h22ActionPerformed

    private void h23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h23ActionPerformed

        if (h23.getText().equals("")) {
            h23.setText(".");
        }
        if (h23.toString().equals(".")) {
        } else {
            get_customer_details_using_mobileno();
        }
        h24.requestFocus();
    }//GEN-LAST:event_h23ActionPerformed

    private void alterbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alterbuttonActionPerformed
        alter_ereturn();
    }//GEN-LAST:event_alterbuttonActionPerformed

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebuttonActionPerformed
        delete();
    }//GEN-LAST:event_deletebuttonActionPerformed

    private void printbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printbuttonActionPerformed
        String billformat = retail_format;
        if (pricel.getText().equals("Wholesale")) {
            billformat = wholesale_format;
        }
        new print_class_estimate_return().get_print(util, h1.getText(), drive, folder, billformat);
    }//GEN-LAST:event_printbuttonActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            apply_all_changes();
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        if (jTable3.getRowCount() > 0) {
            h17.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
            inol.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 1).toString());
            h18.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 2).toString());
            String rprice = jTable3.getValueAt(jTable3.getSelectedRow(), 4).toString();
            String wprice = jTable3.getValueAt(jTable3.getSelectedRow(), 5).toString();
            if (pricel.getText().equals("Retail")) {
                h19.setText(rprice);
            } else {
                h19.setText(wprice);
            }

        }
        h19.requestFocus();
        iname_list.dispose();
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTable3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable3KeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable3.getRowCount() > 0) {
                h17.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
                inol.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 1).toString());
                h18.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 2).toString());
                String rprice = jTable3.getValueAt(jTable3.getSelectedRow(), 4).toString();
                String wprice = jTable3.getValueAt(jTable3.getSelectedRow(), 5).toString();
                if (pricel.getText().equals("Retail")) {
                    h19.setText(rprice);
                } else {
                    h19.setText(wprice);
                }

            }
            h19.requestFocus();
            iname_list.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            iname_list.dispose();
            h17.requestFocus();
        }
    }//GEN-LAST:event_jTable3KeyPressed

    private void jScrollPane3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane3FocusLost
        iname_list.dispose();
    }//GEN-LAST:event_jScrollPane3FocusLost

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        iname_list.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void h17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_h17KeyPressed

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
                if (s4.getRowCount() > 0) {
                    s4.getDataVector().removeAllElements();
                    s4.fireTableDataChanged();
                }
                try {
                    iname_list.requestFocus();
                    Point l = jLabel28.getLocationOnScreen();
                    iname_list.setLocation(l.x, l.y + jLabel28.getHeight());
                    iname_list.setSize(1141, 528);
                    iname_list.setVisible(true);
                    String query = "select barcode,ino,iname,prate,mrp,rprice,wprice from item where iname like '" + h17.getText() + "%' order by ino limit 500";
                    ResultSet r = util.doQuery(query);
                    while (r.next()) {
                        s4.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6), r.getString(7)});
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                break;
        }
    }//GEN-LAST:event_h17KeyPressed

    private void h19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h19ActionPerformed
        h20.requestFocus();
    }//GEN-LAST:event_h19ActionPerformed

    private void h20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h20ActionPerformed
        if (h17.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "<html><h1>Enter Item Details!</h1></html>", "Invalid!", JOptionPane.ERROR_MESSAGE);
            h17.requestFocus();
            return;
        }
        if (h18.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "<html><h1>Enter Item Details!</h1></html>", "Invalid!", JOptionPane.ERROR_MESSAGE);
            h17.requestFocus();
            return;
        }
        if (inol.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "<html><h1>Enter Item Details!</h1></html>", "Invalid!", JOptionPane.ERROR_MESSAGE);
            h17.requestFocus();
            return;
        }
        if (h20.getText().equals("")) {
            h20.setText("" + 1);
        }
        String barcode = h17.getText();
        String iname = h18.getText();
        double price = Double.parseDouble(h19.getText());
        double quan = Double.parseDouble(h20.getText());
        String ino = inol.getText();
        add_item(barcode, ino, iname, price, quan);
        h17.requestFocus();

    }//GEN-LAST:event_h20ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

        if (jTable2.getRowCount() > 0) {
            h21.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
            h22.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 3).toString());
            h23.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 4).toString());
            h24.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 2).toString());
            String scode1 = jTable2.getValueAt(jTable2.getSelectedRow(), 6).toString();
            get_state_code(scode1);
            customer_selection = true;
        }
        h24.requestFocus();
        cname_list.dispose();
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable2.getRowCount() > 0) {
                h21.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
                h22.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 3).toString());
                h23.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 4).toString());
                h24.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 2).toString());
                String scode1 = jTable2.getValueAt(jTable2.getSelectedRow(), 6).toString();
                get_state_code(scode1);
                customer_selection = true;
            }
            h24.requestFocus();
            cname_list.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cname_list.dispose();
            h24.requestFocus();
        }
    }//GEN-LAST:event_jTable2KeyPressed

    private void jScrollPane2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane2FocusLost
        cname_list.dispose();
    }//GEN-LAST:event_jScrollPane2FocusLost

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        cname_list.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void pricelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pricelMouseClicked
        get_price_change();

    }//GEN-LAST:event_pricelMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        get_price_change();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void h24KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_h24KeyPressed

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
                        query = "select cid,ctype,cname,cardno,mobile,city,scode from cust where cname like '" + h24.getText() + "%' order by cname limit 300";
                    } else {
                        query = "select cid,ctype,cname,cardno,mobile,city,scode from cust where cname like '" + h24.getText() + "%' and ctype='Credit Customer' order by cname limit 300";
                    }

                    ResultSet r = util.doQuery(query);
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
    }//GEN-LAST:event_h24KeyPressed

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        h17.requestFocus();
    }//GEN-LAST:event_jTextField1FocusGained

    private void h13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_h13ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton alterbutton;
    private javax.swing.JButton applybutton;
    private javax.swing.JTextField ball;
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JDialog cname_list;
    private javax.swing.JTextField color1;
    private javax.swing.JTextField color2;
    private javax.swing.JButton deletebutton;
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
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h4;
    private javax.swing.JTextField h5;
    private javax.swing.JTextField h6;
    private javax.swing.JTextField h7;
    private javax.swing.JTextField h8;
    private javax.swing.JTextField h9;
    private javax.swing.JDialog iname_list;
    private javax.swing.JLabel infol;
    private javax.swing.JLabel inol;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JDialog multi_pay_mode;
    private javax.swing.JTextField netl;
    private javax.swing.JButton nextbutton;
    private javax.swing.JTextField paidl;
    private javax.swing.JButton prebutton;
    private javax.swing.JLabel pricel;
    private javax.swing.JButton printbutton;
    private javax.swing.JButton removebutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JButton selectbutton;
    private javax.swing.JButton tenderbutton;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
