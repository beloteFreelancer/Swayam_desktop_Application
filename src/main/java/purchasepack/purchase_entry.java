package purchasepack;

import Utils.CompanySettingUtil;
import Utils.ItemUtil;
import Utils.ColorConstants;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import Utils.UPIPaymentDialog;
import com.selrom.db.DataUtil;
import itempack.item_master;
import java.awt.BorderLayout;
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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import java.util.StringTokenizer;
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

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public final class purchase_entry extends javax.swing.JInternalFrame {

    DataUtil util = null;
    DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
    DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
    String ttype = "Local", pur_rate_edit = "No";
    String rrate = "", wrate = "";
    double rdis = 0, wdis = 0;
    boolean selvagates = false;
    menupack.menu_form me = new menu_form();
    String username = me.getUsername();
    String user_type = me.getUserType();
    String drive = "";
    String folder = Utils.AppConfig.getAppPath();
    AutoCompleteSupport support;
    int hmany = 4;

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
            return column == 3 || column == 4 || column == 5 || column == 6 || column == 7 || column == 9
                    || column == 10;
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
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        alterbutton.setText("<html><b>Alter</b><br>(Alt+U)</h6><html>");
        excelbutton.setText("<html><b>load Excel</b><br>(Alt+X)</h6><html>");
        formatbutton.setText("<html><b>Excel Format</b><br>(Alt+Y)</h6><html>");
        webbutton.setText("<html><b>Web Order</b><br>(Alt+W)</h6><html>");
        prebutton.setText("<html><b>Last Entry</b><br>(Alt+R)</h6><html>");
        nextbutton.setText("<html><b>Next Entry</b><br>(Alt+N)</h6><html>");
        applybutton.setText("<html><b>Apply</b><br>(Alt+A)</h6><html>");
        removebutton.setText("<html><b>Remove</b><br>(Alt+M)</h6><html>");
        draftbutton.setText("<html><b>Draft</b><br>(Alt+F)</h6><html>");
        loadbutton.setText("<html><b>Load Draft</b><br>(Alt+T)</h6><html>");
        printbutton.setText("<html><b>Re-Print</b><br>(Alt+P)</h6><html>");
        titlelablel.setText("<html><u>Purchase Register</u></html>");

        Date d = new Date();
        SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
        h2.setText(g.format(d));
    }

    public final void load_items_table() {
        s2.addColumn("Bar Code");
        s2.addColumn("It.Code");
        s2.addColumn("Name");
        s2.addColumn("MRP");
        s2.addColumn("Retail Price");
        s2.addColumn("Wholesale Price");
        s2.addColumn("Purchase Price");
        s2.addColumn("Qty");
        s2.addColumn("Amount");
        s2.addColumn("Dis %");
        s2.addColumn("Dis Amt");
        s2.addColumn("Sub Total");
        s2.addColumn("Tax %");
        s2.addColumn("Tax Amount");
        s2.addColumn("Total");
        s2.addColumn("HSN");
        s2.addColumn("Tax Type");
        s2.addColumn("Category");
        s2.addColumn("Manufacturer");
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
        jTable1.getColumnModel().getColumn(16).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(17).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(18).setCellRenderer(dtcr1);
        jTable1.getColumnModel().getColumn(19).setCellRenderer(dtcr1);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(153);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(300);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(125);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(75);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(140);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(10).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(11).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(12).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(13).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(14).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(15).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(16).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(17).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(18).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(19).setPreferredWidth(180);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    final void get_defaults() {
        try {
            String query = "select pur_rate_edit,rprice,wprice,rdis,wdis from setting_bill";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                pur_rate_edit = r.getString(1);
                rrate = r.getString(2);
                wrate = r.getString(3);
                rdis = r.getDouble(4);
                wdis = r.getDouble(5);
            }
            if (pur_rate_edit.equals("No")) {
                h10.setEnabled(false);
                h11.setEnabled(false);
                h12.setEnabled(false);
                h13.setEnabled(false);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_grn_no() {
        try {
            int sno = 1;
            Connection conn = util.getConnection();
            PreparedStatement ps = conn.prepareStatement("select max(grn) from purchase");
            ResultSet r = ps.executeQuery();
            boolean selva = false;
            while (r.next()) {
                selva = true;
                sno = r.getInt(1);
            }
            if (selva == true) {
                sno = sno + 1;
            }
            h1.setText("" + sno);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_tax_type() {
        try {
            String cname = h3.getText();
            String scode = "", actual_code = "";
            Connection conn = util.getConnection();
            PreparedStatement ps = conn.prepareStatement("select scode from vendor where cname=?");
            ps.setString(1, cname);
            ResultSet set1 = ps.executeQuery();
            while (set1.next()) {
                scode = set1.getString(1);
            }
            ps = conn.prepareStatement("select scode from setting_bill");
            set1 = ps.executeQuery();
            while (set1.next()) {
                actual_code = set1.getString(1);
            }
            if (scode.equalsIgnoreCase(actual_code)) {
                ttype = "Local";
            } else {
                ttype = "IGST";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_item_details_using_item_no() {
        try {
            Connection conn = util.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "select ino,iname,barcode,prate,mrp,rprice,wprice,taxp from item where ino=? order by ino limit 1");
            ps.setString(1, h8.getText());
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                h8.setText(r.getString(1));
                h9.setText(r.getString(2));
                h7.setText(r.getString(3));
                h10.setText(r.getString(4));
                h11.setText(r.getString(5));
                h12.setText(r.getString(6));
                h13.setText(r.getString(7));
                h15.setText(r.getString(8));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_item_details_using_barcode() {
        try {
            Connection conn = util.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "select ino,iname,prate,mrp,rprice,wprice,taxp from item where barcode=? order by ino limit 1");
            ps.setString(1, h7.getText());
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                h8.setText(r.getString(1));
                h9.setText(r.getString(2));
                h10.setText(r.getString(3));
                h11.setText(r.getString(4));
                h12.setText(r.getString(5));
                h13.setText(r.getString(6));
                h15.setText(r.getString(7));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void add_item(String barcode, String ino, String iname, double prate, double mrp, double rrate, double wrate,
            double disp, double disamt, int taxp, double quan) {
        try {
            String cat = ".", manu = ".", hsn = ".", iname1 = ".";
            boolean selva = false;
            Connection conn = util.getConnection();
            PreparedStatement ps = conn.prepareStatement("select distinct cat,manu,hsn,iname1 from item where ino=?");
            ps.setString(1, ino);
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                cat = r.getString(1);
                manu = r.getString(2);
                hsn = r.getString(3);
                iname1 = r.getString(4);
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "Invalid Product Details!", "Invalid", JOptionPane.ERROR_MESSAGE);
                // fields_clear();
                return;
            }

            double taxamt, amount, sub, total;
            if (ttypel.getSelectedItem().equals("Inclusive of Tax")) {
                double devide = 100 + taxp;
                devide = prate * (100 / devide);
                taxamt = prate - devide;
                prate = prate - taxamt;
                amount = prate * quan;
                if (disp > 0) {
                    disamt = (disp * amount) / 100;
                }
                sub = amount - disamt;
                taxamt = taxamt * quan;
                total = sub + taxamt;
            } else if (ttypel.getSelectedItem().equals("Exclusive of Tax")) {
                amount = prate * quan;
                if (disp > 0) {
                    disamt = (disp * amount) / 100;
                }
                sub = amount - disamt;
                taxamt = (taxp * sub) / 100;
                total = sub + taxamt;
            } else {
                amount = prate * quan;
                if (disp > 0) {
                    disamt = (disp * amount) / 100;
                }
                sub = amount - disamt;
                taxamt = 0;
                total = sub;
            }
            String prate1 = String.format("%." + hmany + "f", prate);
            String mrp1 = String.format("%." + hmany + "f", mrp);
            s2.addRow(new Object[] { barcode, ino, iname, mrp1, rrate, wrate, prate1, quan, amount, disp, disamt, sub,
                    taxp, taxamt, total, hsn, ttype, cat, manu, iname1 });
            // row selected
            Rectangle rect = jTable1.getCellRect(jTable1.getRowCount() - 1, 0, true);
            jTable1.scrollRectToVisible(rect);
            jTable1.setRowSelectionInterval(jTable1.getRowCount() - 1, jTable1.getRowCount() - 1);
            // row selected ends
            ttypel.setEnabled(false);
            apply_all_changes();
            fields_clear();
        } catch (HeadlessException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void apply_all_changes() {
        try {
            double nsub = 0, ndis = 0, ngross = 0, ntax = 0, nquan = 0;
            int items = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                double rrate1 = Double.parseDouble(jTable1.getValueAt(i, 4).toString());
                double wrate1 = Double.parseDouble(jTable1.getValueAt(i, 5).toString());
                double prate = Double.parseDouble(jTable1.getValueAt(i, 6).toString());
                double quan = Double.parseDouble(jTable1.getValueAt(i, 7).toString());
                double disp = Double.parseDouble(jTable1.getValueAt(i, 9).toString());
                double disamt = Double.parseDouble(jTable1.getValueAt(i, 10).toString());
                double taxp = Double.parseDouble(jTable1.getValueAt(i, 12).toString());

                double amount = prate * quan;
                if (disp > 0) {
                    disamt = (disp * amount) / 100;
                }
                double sub = amount - disamt;
                double total, taxamt;
                if (ttypel.getSelectedItem().equals("No Tax")) {
                    total = sub;
                    taxamt = 0;
                } else {
                    taxamt = (taxp * sub) / 100;
                    total = sub + taxamt;
                }
                String amount1 = String.format("%." + hmany + "f", amount);
                String disamt1 = String.format("%." + hmany + "f", disamt);
                String sub1 = String.format("%." + hmany + "f", sub);
                String taxamt1 = String.format("%." + hmany + "f", taxamt);
                String total1 = String.format("%." + hmany + "f", total);
                String rrate2 = String.format("%." + hmany + "f", rrate1);
                String wrate2 = String.format("%." + hmany + "f", wrate1);

                jTable1.setValueAt(amount1, i, 8);
                jTable1.setValueAt(disamt1, i, 10);
                jTable1.setValueAt(sub1, i, 11);
                jTable1.setValueAt(taxamt1, i, 13);
                jTable1.setValueAt(total1, i, 14);

                jTable1.setValueAt(rrate2, i, 4);
                jTable1.setValueAt(wrate2, i, 5);

                nquan = nquan + quan;
                nsub = nsub + amount;
                ndis = ndis + disamt;
                ngross = ngross + sub;
                ntax = ntax + taxamt;
                items = items + 1;
            } // table row counts ends

            String nsub1 = String.format("%." + hmany + "f", nsub);
            String ndis1 = String.format("%." + hmany + "f", ndis);
            String ngross1 = String.format("%." + hmany + "f", ngross);
            String ntax1 = String.format("%." + hmany + "f", ntax);

            h17.setText("" + items);
            h18.setText("" + nquan);
            h19.setText("" + nsub1);
            h20.setText("" + ndis1);
            h21.setText("" + ngross1);
            h22.setText("" + ntax1);
            final_calculate();
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    void final_calculate() {
        try {
            if (h23.getText().equals("")) {
                h23.setText("" + 0);
            }
            if (h24.getText().equals("")) {
                h24.setText("" + 0);
            }

            double sub = Double.parseDouble(h19.getText());
            double disamt = Double.parseDouble(h20.getText());
            double gross = sub - disamt;

            double taxamt = Double.parseDouble(h22.getText());
            double fright = Double.parseDouble(h23.getText());
            double other = Double.parseDouble(h24.getText());
            double gt = gross + taxamt + fright + other;

            String gross1 = String.format("%." + hmany + "f", gross);
            String gt1 = String.format("%." + hmany + "f", gt);
            h21.setText("" + gross1);
            h25.setText("" + gt1);

            String grant = gt1 + "";
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
            String round1 = String.format("%." + hmany + "f", round);
            // round off ends
            h26.setText("" + round1);
            netl.setText(rup + ".00");
            h27.setText(rup + ".00");

            double diff = Double.parseDouble(h6.getText()) - Double.parseDouble(h27.getText());
            String diff1 = String.format("%." + hmany + "f", diff);
            diffl.setText("" + diff1);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    void fields_clear() {
        try {
            h7.setText("");
            h8.setText("");
            h9.setText("");
            h10.setText("");
            h11.setText("");
            h12.setText("");
            h13.setText("");
            h15.setText("");
            h16.setText("");
            h29.setText("");
            h8.requestFocus();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void save() {
        try {
            Connection conn = null;
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found to Save!", "No Records",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (h3.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Supplier Name ?", "Supplier Name",
                        JOptionPane.ERROR_MESSAGE);
                h3.requestFocus();
                return;
            }
            if (h4.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Purchase Bill No ?", "Bill No", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }
            if (h6.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Bill Amount ?", "Bill Amount", JOptionPane.ERROR_MESSAGE);
                h6.requestFocus();
                return;
            }
            if (h30.getText().equals("")) {
                h30.setText(".");
            }
            apply_all_changes();
            double billamount = Double.parseDouble(h6.getText());
            double net = Double.parseDouble(h27.getText());
            if (billamount != net) {
                JOptionPane.showMessageDialog(this, "Bill Amount & Net Amount Not Matching!", "Amount Not Matching",
                        JOptionPane.ERROR_MESSAGE);
                h6.requestFocus();
                return;
            }
            if (selvagates == false) {
                get_grn_no();
            }
            boolean selva = false;
            conn = util.getConnection();
            PreparedStatement psCheckGrn = conn.prepareStatement("select grn from purchase where grn=?");
            psCheckGrn.setString(1, h1.getText());
            ResultSet r = psCheckGrn.executeQuery();
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                JOptionPane.showMessageDialog(this, "Purchase Entry Already Exist!\nUse 'Alter' Option to Alter...",
                        "Already Exist", JOptionPane.ERROR_MESSAGE);
                h3.requestFocus();
                return;
            }

            selva = false;

            PreparedStatement psCheckBill = conn
                    .prepareStatement("select billno from purchase where cname=? and billno=?");
            psCheckBill.setString(1, h3.getText());
            psCheckBill.setString(2, h4.getText());
            r = psCheckBill.executeQuery();
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                JOptionPane.showMessageDialog(this,
                        "This Bill No: '" + h4.getText() + "' is Already Exist for '" + h3.getText() + "'",
                        "Already Exist", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }
            int due_days = 0;
            PreparedStatement psDueDays = conn.prepareStatement("select duedays from vendor where cname=?");
            psDueDays.setString(1, h3.getText());
            r = psDueDays.executeQuery();
            while (r.next()) {
                due_days = r.getInt(1);
            }
            int aa = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (aa == JOptionPane.NO_OPTION) {
                return;
            }

            auto_draft();
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(h5.getText());
            String bdate = (new SimpleDateFormat("yyyy/MM/dd").format(nm));
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText());
            String date = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);

            String grn = h1.getText();
            String billno = h4.getText();
            String cname = h3.getText();

            String items = h17.getText();
            String quans = h18.getText();
            String sub = h19.getText();
            String disamt = h20.getText();
            String gross = h21.getText();
            String taxamt = h22.getText();
            String fright = h23.getText();
            String other = h24.getText();
            String grant = h25.getText();
            String round = h26.getText();
            String pby = h28.getSelectedItem().toString();
            String entry = "purchase";
            String ttype1 = ttypel.getSelectedItem().toString();
            String po_no = h30.getText();
            ArrayList query_batch = new ArrayList();

            conn = util.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement psItems = conn.prepareStatement(
                    "INSERT INTO purchase_items (grn, dat, cname, billno, bdate, barcode, ino, iname, mrp, rprice, wprice, price, quan, amount, disp, disamt, sub, taxp, taxamt, total, hsn, ttype, cat, manu, ttype1, iname1) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            PreparedStatement psStockUpdate = conn
                    .prepareStatement("UPDATE stock SET quan=quan+? WHERE barcode=? AND ino=? AND iname=? AND entry=?");
            PreparedStatement psStockUpdateRate = conn.prepareStatement(
                    "UPDATE stock SET quan=quan+?, mrp=?, rprice=?, wprice=?, prate=? WHERE barcode=? AND ino=? AND iname=? AND entry=?");
            PreparedStatement psItemUpdate = conn.prepareStatement(
                    "UPDATE item SET mrp=?, rprice=?, wprice=?, prate=? WHERE barcode=? AND ino=? AND iname=?");
            PreparedStatement psStockInsert = conn.prepareStatement(
                    "INSERT INTO stock (barcode, ino, iname, mrp, rprice, wprice, prate, quan, cat, entry) VALUES (?,?,?,?,?,?,?,?,?,?)");

            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String barcode = jTable1.getValueAt(i, 0).toString();
                String ino = jTable1.getValueAt(i, 1).toString();
                String iname = jTable1.getValueAt(i, 2).toString();
                String mrp = jTable1.getValueAt(i, 3).toString();
                String rprice = jTable1.getValueAt(i, 4).toString();
                String wprice = jTable1.getValueAt(i, 5).toString();
                double prate = Double.parseDouble(jTable1.getValueAt(i, 6).toString());
                String quan = jTable1.getValueAt(i, 7).toString();
                String amount = jTable1.getValueAt(i, 8).toString();
                String disp1 = jTable1.getValueAt(i, 9).toString();
                String disamt1 = jTable1.getValueAt(i, 10).toString();
                String sub1 = jTable1.getValueAt(i, 11).toString();
                String taxp1 = jTable1.getValueAt(i, 12).toString();
                String taxamt1 = jTable1.getValueAt(i, 13).toString();
                String total = jTable1.getValueAt(i, 14).toString();
                String hsn = jTable1.getValueAt(i, 15).toString();
                String tax_type1 = jTable1.getValueAt(i, 16).toString();
                String cat = jTable1.getValueAt(i, 17).toString();
                String manu = jTable1.getValueAt(i, 18).toString();
                String iname1 = jTable1.getValueAt(i, 19).toString();

                psItems.setString(1, grn);
                psItems.setString(2, date);
                psItems.setString(3, cname);
                psItems.setString(4, billno);
                psItems.setString(5, bdate);
                psItems.setString(6, barcode);
                psItems.setString(7, ino);
                psItems.setString(8, iname);
                psItems.setString(9, mrp);
                psItems.setString(10, rprice);
                psItems.setString(11, wprice);
                psItems.setDouble(12, prate);
                psItems.setString(13, quan);
                psItems.setString(14, amount);
                psItems.setString(15, disp1);
                psItems.setString(16, disamt1);
                psItems.setString(17, sub1);
                psItems.setString(18, taxp1);
                psItems.setString(19, taxamt1);
                psItems.setString(20, total);
                psItems.setString(21, hsn);
                psItems.setString(22, tax_type1);
                psItems.setString(23, cat);
                psItems.setString(24, manu);
                psItems.setString(25, ttype1);
                psItems.setString(26, iname1);
                psItems.addBatch();

                double old_rate = 0;
                boolean selvak = false;
                PreparedStatement psCheckStock = conn.prepareStatement(
                        "SELECT DISTINCT prate FROM stock WHERE barcode=? AND ino=? AND iname=? AND entry=?");
                psCheckStock.setString(1, barcode);
                psCheckStock.setString(2, ino);
                psCheckStock.setString(3, iname);
                psCheckStock.setString(4, entry);
                r = psCheckStock.executeQuery();
                while (r.next()) {
                    selvak = true;
                    old_rate = r.getDouble(1);
                }
                if (selvak == true && old_rate == prate) {
                    psStockUpdate.setDouble(1, Double.parseDouble(quan));
                    psStockUpdate.setString(2, barcode);
                    psStockUpdate.setString(3, ino);
                    psStockUpdate.setString(4, iname);
                    psStockUpdate.setString(5, entry);
                    psStockUpdate.addBatch();
                } else if (selvak == true && old_rate != prate) {
                    psStockUpdateRate.setDouble(1, Double.parseDouble(quan));
                    psStockUpdateRate.setString(2, mrp);
                    psStockUpdateRate.setString(3, rprice);
                    psStockUpdateRate.setString(4, wprice);
                    psStockUpdateRate.setDouble(5, prate);
                    psStockUpdateRate.setString(6, barcode);
                    psStockUpdateRate.setString(7, ino);
                    psStockUpdateRate.setString(8, iname);
                    psStockUpdateRate.setString(9, entry);
                    psStockUpdateRate.addBatch();

                    psItemUpdate.setString(1, mrp);
                    psItemUpdate.setString(2, rprice);
                    psItemUpdate.setString(3, wprice);
                    psItemUpdate.setDouble(4, prate);
                    psItemUpdate.setString(5, barcode);
                    psItemUpdate.setString(6, ino);
                    psItemUpdate.setString(7, iname);
                    psItemUpdate.addBatch();
                } else if (selvak == false) {
                    psStockInsert.setString(1, barcode);
                    psStockInsert.setString(2, ino);
                    psStockInsert.setString(3, iname);
                    psStockInsert.setString(4, mrp);
                    psStockInsert.setString(5, rprice);
                    psStockInsert.setString(6, wprice);
                    psStockInsert.setDouble(7, prate);
                    psStockInsert.setString(8, quan);
                    psStockInsert.setString(9, cat);
                    psStockInsert.setString(10, entry);
                    psStockInsert.addBatch();
                }
            } // jtable row counts ends

            psItems.executeBatch();
            psStockUpdate.executeBatch();
            psStockUpdateRate.executeBatch();
            psItemUpdate.executeBatch();
            psStockInsert.executeBatch();

            PreparedStatement psPurchase = conn.prepareStatement(
                    "INSERT INTO purchase (grn, dat, cname, billno, bdate, bamount, items, quans, sub, dis, gross, tax, fright, other, gt, round, net, pby, user, last, ttype, po_no) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            psPurchase.setString(1, grn);
            psPurchase.setString(2, date);
            psPurchase.setString(3, cname);
            psPurchase.setString(4, billno);
            psPurchase.setString(5, bdate);
            psPurchase.setDouble(6, billamount);
            psPurchase.setString(7, items);
            psPurchase.setString(8, quans);
            psPurchase.setString(9, sub);
            psPurchase.setString(10, disamt);
            psPurchase.setString(11, gross);
            psPurchase.setString(12, taxamt);
            psPurchase.setString(13, fright);
            psPurchase.setString(14, other);
            psPurchase.setString(15, grant);
            psPurchase.setString(16, round);
            psPurchase.setDouble(17, net);
            psPurchase.setString(18, pby);
            psPurchase.setString(19, username);
            psPurchase.setString(20, last);
            psPurchase.setString(21, ttype1);
            psPurchase.setString(22, po_no);
            psPurchase.executeUpdate();

            if (!po_no.equals(".")) {
                PreparedStatement psPo = conn
                        .prepareStatement("UPDATE po_entry SET pstatus='Order Received' WHERE grn=?");
                psPo.setString(1, po_no);
                psPo.executeUpdate();
            }
            if (pby.equalsIgnoreCase("Credit")) {
                int paid1 = 0;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(date));
                c.add(Calendar.DAY_OF_MONTH, due_days);
                String ddate = sdf.format(c.getTime());

                PreparedStatement psVenBal = conn.prepareStatement(
                        "INSERT INTO ven_bal (billno, dat, ddate, cname, tot, paid, last) VALUES (?,?,?,?,?,?,?)");
                psVenBal.setString(1, billno);
                psVenBal.setString(2, date);
                psVenBal.setString(3, ddate);
                psVenBal.setString(4, cname);
                psVenBal.setDouble(5, net);
                psVenBal.setInt(6, paid1);
                psVenBal.setString(7, last);
                psVenBal.executeUpdate();
            } // credit bill ends

            conn.commit();
            int count = 1;
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>GRN No: " + h1.getText() + "</h1></html>",
                        "Saved Successfully", JOptionPane.PLAIN_MESSAGE);
                int bb = JOptionPane.showConfirmDialog(this, "<html><h1>You Want to Print Barcode ?</h1></html>",
                        "Barcode", JOptionPane.YES_NO_OPTION);
                if (bb == JOptionPane.YES_OPTION) {
                    print_barcode();
                }
                form_clear();
            } else {
                JOptionPane.showMessageDialog(this, "Check Product Entries and then Try Again!", "Invalid Products",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | NumberFormatException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    void form_clear() {
        try {
            fields_clear();
            h1.setText("--");
            h3.setText("");
            creditLabel.setText(null);
            h4.setText("");
            h5.setText("");
            h6.setText("");

            h17.setText("");
            h18.setText("");
            h19.setText("");
            h20.setText("");
            h21.setText("");
            h22.setText("");
            h23.setText("");
            h24.setText("");
            h25.setText("");
            h26.setText("");
            h27.setText("");
            h28.setSelectedItem(0);
            h14.setText("");
            netl.setText("");
            diffl.setText("");
            h30.setText("");
            h30.setEditable(true);
            searchbutton1.setEnabled(true);
            if (s2.getRowCount() > 0) {
                s2.getDataVector().removeAllElements();
                s2.fireTableDataChanged();
            }
            ttypel.setEnabled(true);
            savebutton.setVisible(true);
            viewbutton.setVisible(true);
            deletebutton.setVisible(false);
            alterbutton.setVisible(false);
            printbutton.setVisible(false);
            support.uninstall();
            get_sug();
            h30.requestFocus();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void view(String grn) {
        try {
            Connection conn = util.getConnection();
            PreparedStatement psCheck = conn.prepareStatement("select distinct grn from purchase where grn=?");
            psCheck.setString(1, grn);
            ResultSet set1 = psCheck.executeQuery();
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

                PreparedStatement psView = conn.prepareStatement(
                        "select distinct grn,date_format(dat,'%d/%m/%Y'),cname,billno,date_format(bdate,'%d/%m/%Y'),bamount,items,quans,sub,dis,gross,tax,fright,other,gt,round,net,pby,ttype,po_no from purchase where grn=?");
                psView.setString(1, grn);
                set1 = psView.executeQuery();
                while (set1.next()) {
                    h1.setText(set1.getString(1));
                    h2.setText(set1.getString(2));
                    h3.setText(set1.getString(3));
                    h4.setText(set1.getString(4));
                    h5.setText(set1.getString(5));
                    h6.setText(set1.getString(6));

                    h17.setText(set1.getString(7));
                    h18.setText(set1.getString(8));
                    h19.setText(set1.getString(9));
                    h20.setText(set1.getString(10));
                    h21.setText(set1.getString(11));
                    h22.setText(set1.getString(12));
                    h23.setText(set1.getString(13));
                    h24.setText(set1.getString(14));
                    h25.setText(set1.getString(15));
                    h26.setText(set1.getString(16));

                    h28.setSelectedItem(set1.getString(18));
                    double net = set1.getDouble(17);
                    h27.setText("" + net);
                    netl.setText("" + net);
                    ttypel.setSelectedItem(set1.getString(19));
                    h30.setText(set1.getString(20));
                    double old_bal = 0;
                    ResultSet r1;
                    try {
                        PreparedStatement psBal = conn
                                .prepareStatement("select sum(tot-paid) from ven_bal where cname=?");
                        psBal.setString(1, h3.getText());
                        r1 = psBal.executeQuery();
                        while (r1.next()) {
                            old_bal = r1.getDouble(1);
                        }
                        creditLabel.setText("Credit: " + String.format("%." + hmany + "f", old_bal));
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                PreparedStatement psItems = conn.prepareStatement(
                        "select barcode,ino,iname,mrp,rprice,wprice,price,quan,amount,disp,disamt,sub,taxp,taxamt,total,hsn,ttype,cat,manu,iname1 from purchase_items where grn=?");
                psItems.setString(1, grn);
                set1 = psItems.executeQuery();
                while (set1.next()) {
                    s2.addRow(new Object[] { set1.getString(1), set1.getString(2), set1.getString(3), set1.getString(4),
                            set1.getString(5), set1.getString(6), set1.getString(7), set1.getString(8),
                            set1.getString(9),
                            set1.getString(10), set1.getString(11), set1.getString(12), set1.getString(13),
                            set1.getString(14), set1.getString(15), set1.getString(16), set1.getString(17),
                            set1.getString(18), set1.getString(19), set1.getString(20) });
                }
            } // if selva true ends
        } catch (HeadlessException | NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void save_draft() {
        try {
            if (h3.getText().equals("")) {
                h3.setText(".");
                creditLabel.setText(null);
            }
            if (h4.getText().equals("")) {
                h4.setText(".");
            }
            if (h5.getText().equals("")) {
                h5.setText(h2.getText());
            }
            if (h6.getText().equals("")) {
                h6.setText("" + 0);
            }

            if (h20.getText().equals("")) {
                h20.setText("" + 0);
            }
            if (h23.getText().equals("")) {
                h23.setText("" + 0);
            }
            if (h24.getText().equals("")) {
                h24.setText("" + 0);
            }
            if (diffl.getText().equals("")) {
                diffl.setText("" + 0);
            }
            if (h30.getText().equals("")) {
                h30.setText(".");
            }
            String file_name = "Items_List_Purchase";
            File draftsDir = new File(folder + "/Drafts/");
            if (!draftsDir.exists()) {
                draftsDir.mkdirs();
            }
            try (FileWriter f = new FileWriter(new File(folder + "/Drafts/" + file_name + ".txt"))) {
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    String barcode = jTable1.getValueAt(i, 0).toString();
                    String ino = jTable1.getValueAt(i, 1).toString();
                    String iname = jTable1.getValueAt(i, 2).toString();
                    String mrp = jTable1.getValueAt(i, 3).toString();
                    String rprice = jTable1.getValueAt(i, 4).toString();
                    String wprice = jTable1.getValueAt(i, 5).toString();
                    String prate = jTable1.getValueAt(i, 6).toString();
                    String quan = jTable1.getValueAt(i, 7).toString();
                    String disp1 = jTable1.getValueAt(i, 9).toString();
                    String disamt1 = jTable1.getValueAt(i, 10).toString();
                    String taxp1 = jTable1.getValueAt(i, 12).toString();
                    f.write("" + barcode + "$" + ino + "$" + iname + "$" + prate + "$" + mrp + "$" + rprice + "$"
                            + wprice + "$" + disp1 + "$" + disamt1 + "$" + taxp1 + "$" + quan + "\r\n");
                } // row counts ends here
            }

            String file_name1 = "Bill_Details_Purchase";
            try (FileWriter f1 = new FileWriter(new File(folder + "/Drafts/" + file_name1 + ".txt"))) {
                String grn = h1.getText();
                String date = h2.getText();
                String cname = h3.getText();
                String billno = h4.getText();
                String bdate = h5.getText();
                String bamount = h6.getText();

                String items = h17.getText();
                String quans = h18.getText();
                String sub = h19.getText();
                String dis = h20.getText();
                String gross = h21.getText();
                String taxamt = h22.getText();
                String fright = h23.getText();
                String other = h24.getText();
                String grant = h25.getText();
                String round = h26.getText();
                String net = h27.getText();
                String pby = h28.getSelectedItem().toString();
                String diff = diffl.getText();
                String ttype1 = ttypel.getSelectedItem().toString();
                String po_no = h30.getText();
                f1.write("" + grn + "$" + date + "$" + cname + "$" + billno + "$" + bdate + "$" + bamount + "" + "$"
                        + items + "" + "$" + quans + "$" + sub + "$" + dis + "$" + gross + "$" + taxamt + "$" + fright
                        + "$" + other + "$" + grant + "$" + round + "$" + net + "$" + pby + "$" + diff + "$" + ttype1
                        + "$" + po_no);
            }
            JOptionPane.showMessageDialog(this, "<html><h1>Draft Saved Successfully</h1></html>", "Saved",
                    JOptionPane.PLAIN_MESSAGE);
            form_clear();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    void auto_draft() {
        try {
            if (h3.getText().equals("")) {
                h3.setText(".");
                creditLabel.setText(null);
            }
            if (h4.getText().equals("")) {
                h4.setText(".");
            }
            if (h5.getText().equals("")) {
                h5.setText(h2.getText());
            }
            if (h6.getText().equals("")) {
                h6.setText("" + 0);
            }

            if (h20.getText().equals("")) {
                h20.setText("" + 0);
            }
            if (h23.getText().equals("")) {
                h23.setText("" + 0);
            }
            if (h24.getText().equals("")) {
                h24.setText("" + 0);
            }
            if (h30.getText().equals("")) {
                h30.setText(".");
            }
            String file_name = "Items_List_Purchase";
            try (FileWriter f = new FileWriter(new File(folder + "/Drafts/" + file_name + ".txt"))) {
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    String barcode = jTable1.getValueAt(i, 0).toString();
                    String ino = jTable1.getValueAt(i, 1).toString();
                    String iname = jTable1.getValueAt(i, 2).toString();
                    String mrp = jTable1.getValueAt(i, 3).toString();
                    String rprice = jTable1.getValueAt(i, 4).toString();
                    String wprice = jTable1.getValueAt(i, 5).toString();
                    String prate = jTable1.getValueAt(i, 6).toString();
                    String quan = jTable1.getValueAt(i, 7).toString();
                    String disp1 = jTable1.getValueAt(i, 9).toString();
                    String disamt1 = jTable1.getValueAt(i, 10).toString();
                    String taxp1 = jTable1.getValueAt(i, 12).toString();
                    f.write("" + barcode + "$" + ino + "$" + iname + "$" + prate + "$" + mrp + "$" + rprice + "$"
                            + wprice + "$" + disp1 + "$" + disamt1 + "$" + taxp1 + "$" + quan + "\r\n");
                } // row counts ends here
            }

            String file_name1 = "Bill_Details_Purchase";
            try (FileWriter f1 = new FileWriter(new File(folder + "/Drafts/" + file_name1 + ".txt"))) {
                String grn = h1.getText();
                String date = h2.getText();
                String cname = h3.getText();
                String billno = h4.getText();
                String bdate = h5.getText();
                String bamount = h6.getText();

                String items = h17.getText();
                String quans = h18.getText();
                String sub = h19.getText();
                String dis = h20.getText();
                String gross = h21.getText();
                String taxamt = h22.getText();
                String fright = h23.getText();
                String other = h24.getText();
                String grant = h25.getText();
                String round = h26.getText();
                String net = h27.getText();
                String pby = h28.getSelectedItem().toString();
                String diff = diffl.getText();
                String ttype1 = ttypel.getSelectedItem().toString();
                String po_no = h30.getText();
                f1.write("" + grn + "$" + date + "$" + cname + "$" + billno + "$" + bdate + "$" + bamount + "" + "$"
                        + items + "" + "$" + quans + "$" + sub + "$" + dis + "$" + gross + "$" + taxamt + "$" + fright
                        + "$" + other + "$" + grant + "$" + round + "$" + net + "$" + pby + "$" + diff + "$" + ttype1
                        + "$" + po_no);
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
            String file_name = "Items_List_Purchase";
            BufferedReader br = new BufferedReader(new FileReader(new File(folder + "/Drafts/" + file_name + ".txt")));
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                String what = sCurrentLine;
                StringTokenizer token = new StringTokenizer(what, "$");
                String barcode = "", ino = "", iname = "";
                double prate = 0, mrp = 0, rprice = 0, wprice = 0, disp = 0, quan = 0, disamt = 0;
                int taxp = 0;

                while (token.hasMoreTokens()) {
                    barcode = token.nextToken();
                    ino = token.nextToken();
                    iname = token.nextToken();
                    prate = Double.parseDouble(token.nextToken());
                    mrp = Double.parseDouble(token.nextToken());
                    rprice = Double.parseDouble(token.nextToken());
                    wprice = Double.parseDouble(token.nextToken());
                    disp = Double.parseDouble(token.nextToken());
                    disamt = Double.parseDouble(token.nextToken());
                    taxp = Integer.parseInt(token.nextToken());
                    quan = Double.parseDouble(token.nextToken());
                }
                add_item(barcode, ino, iname, prate, mrp, rprice, wprice, disp, disamt, taxp, quan);
            } // while loop reading lines one by one ends he

            String file_name1 = "Bill_Details_Purchase";
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

                    h17.setText(token.nextToken());
                    h18.setText(token.nextToken());
                    h19.setText(token.nextToken());
                    h20.setText(token.nextToken());
                    h21.setText(token.nextToken());
                    h22.setText(token.nextToken());
                    h23.setText(token.nextToken());
                    h24.setText(token.nextToken());
                    h25.setText(token.nextToken());
                    h26.setText(token.nextToken());
                    h27.setText(token.nextToken());
                    h28.setSelectedItem(token.nextToken());
                    diffl.setText(token.nextToken());
                    netl.setText(h27.getText());
                    ttypel.setSelectedItem(token.nextToken());
                    h30.setText(token.nextToken());
                    double old_bal = 0;
                    String query1 = "select sum(tot-paid) from ven_bal where cname='" + h3.getText() + "'";
                    ResultSet r1;
                    try {
                        r1 = util.doQuery(query1);
                        while (r1.next()) {
                            old_bal = r1.getDouble(1);
                        }
                        creditLabel.setText("Credit: " + String.format("%." + hmany + "f", old_bal));
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            } // while line loop ends

        } catch (HeadlessException | IOException | NumberFormatException e) {
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
            if (user_type.equalsIgnoreCase("User")) {
                JOptionPane.showMessageDialog(this, "Login as 'Administrator' to Delete!", "Permission Restricted",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int as = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Delete ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            String grn = h1.getText();
            String entry = "purchase";

            Connection conn = util.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement psUpdateStock = conn
                    .prepareStatement("update stock set quan=quan-? where barcode=? and ino=? and iname=? and entry=?");

            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String barcode = jTable1.getValueAt(i, 0).toString();
                String ino = jTable1.getValueAt(i, 1).toString();
                String iname = jTable1.getValueAt(i, 2).toString();
                String quan = jTable1.getValueAt(i, 7).toString();

                psUpdateStock.setDouble(1, Double.parseDouble(quan));
                psUpdateStock.setString(2, barcode);
                psUpdateStock.setString(3, ino);
                psUpdateStock.setString(4, iname);
                psUpdateStock.setString(5, entry);
                psUpdateStock.addBatch();
            } // jtable row counts ends
            psUpdateStock.executeBatch();

            PreparedStatement psDeletePurchase = conn.prepareStatement("delete from purchase where grn=?");
            psDeletePurchase.setString(1, grn);
            psDeletePurchase.executeUpdate();

            PreparedStatement psDeletePurchaseItems = conn.prepareStatement("delete from purchase_items where grn=?");
            psDeletePurchaseItems.setString(1, grn);
            psDeletePurchaseItems.executeUpdate();

            if (h28.getSelectedItem().equals("Credit")) {
                PreparedStatement psDeleteVenBal = conn
                        .prepareStatement("delete from ven_bal where billno=? and cname=?");
                psDeleteVenBal.setString(1, h4.getText());
                psDeleteVenBal.setString(2, h3.getText());
                psDeleteVenBal.executeUpdate();
            }

            conn.commit();
            int count = 1;
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Deleted Successfully</h1></html>", "Deleted",
                        JOptionPane.PLAIN_MESSAGE);
                form_clear();
            } else {
                JOptionPane.showMessageDialog(this, "Check Entries and then Try Again!", "Invalid",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void alter_purchase() {
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
            if (user_type.equalsIgnoreCase("User")) {
                JOptionPane.showMessageDialog(this, "Login as 'Administrator' to Alter!", "Permission Restricted",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int as = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Alter ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }

            String grn = h1.getText();
            ArrayList<String> barcode = new ArrayList<>();
            ArrayList<String> ino = new ArrayList<>();
            ArrayList<String> iname = new ArrayList<>();
            ArrayList<String> quan = new ArrayList<>();
            String entry = "purchase";

            Connection conn = util.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn
                    .prepareStatement("select barcode,ino,iname,quan from purchase_items where grn=?");
            ps.setString(1, grn);
            ResultSet set1 = ps.executeQuery();
            boolean selva = false;
            while (set1.next()) {
                selva = true;
                barcode.add(set1.getString(1));
                ino.add(set1.getString(2));
                iname.add(set1.getString(3));
                quan.add(set1.getString(4));
            }

            if (selva == true) {
                PreparedStatement psUpdateStock = conn.prepareStatement(
                        "update stock set quan=quan-? where barcode=? and ino=? and iname=? and entry=?");
                for (int i = 0; i < barcode.size(); i++) {
                    psUpdateStock.setDouble(1, Double.parseDouble(quan.get(i)));
                    psUpdateStock.setString(2, barcode.get(i));
                    psUpdateStock.setString(3, ino.get(i));
                    psUpdateStock.setString(4, iname.get(i));
                    psUpdateStock.setString(5, entry);
                    psUpdateStock.addBatch();
                }
                psUpdateStock.executeBatch();
            } // selva true ends

            PreparedStatement psDeletePurchase = conn.prepareStatement("delete from purchase where grn=?");
            psDeletePurchase.setString(1, grn);
            psDeletePurchase.executeUpdate();

            PreparedStatement psDeletePurchaseItems = conn.prepareStatement("delete from purchase_items where grn=?");
            psDeletePurchaseItems.setString(1, grn);
            psDeletePurchaseItems.executeUpdate();

            if (h28.getSelectedItem().equals("Credit")) {
                PreparedStatement psDeleteVenBal = conn
                        .prepareStatement("delete from ven_bal where billno=? and cname=?");
                psDeleteVenBal.setString(1, h4.getText());
                psDeleteVenBal.setString(2, h3.getText());
                psDeleteVenBal.executeUpdate();
            }
            conn.commit();
            int count = 1;
            if (count > 0) {
                save();
            } else {
                JOptionPane.showMessageDialog(this, "Check Entries and then Try Again!", "Invalid",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void load_supplier_table() {
        try {
            s3.addColumn("Name");
            s3.addColumn("City");
            jTable2.setModel(s3);
            jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(500);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(347);
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable2.getTableHeader().setFont(new Font(Ta, Bold, size));
        } catch (Exception e) {
        }
    }

    void load_iname_table() {
        try {
            s4.addColumn("It.Code");
            s4.addColumn("Barcode");
            s4.addColumn("Item Name");
            s4.addColumn("Category");
            s4.addColumn("Manufacturer");
            jTable3.setModel(s4);
            jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(314);
            jTable3.getColumnModel().getColumn(3).setPreferredWidth(220);
            jTable3.getColumnModel().getColumn(4).setPreferredWidth(300);
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable3.getTableHeader().setFont(new Font(Ta, Bold, size));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void print_barcode() {
        try {
            int hmany1 = 2;
            ArrayList barcode = new ArrayList();
            ArrayList ino = new ArrayList();
            ArrayList iname = new ArrayList();
            ArrayList mrp = new ArrayList();
            ArrayList rprice = new ArrayList();
            ArrayList wprice = new ArrayList();
            ArrayList tname = new ArrayList();
            ArrayList<Integer> items = new ArrayList<>();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String barcode1 = jTable1.getValueAt(i, 0).toString();
                String ino1 = jTable1.getValueAt(i, 1).toString();
                String iname1 = jTable1.getValueAt(i, 2).toString();
                double mrp1 = Double.parseDouble(jTable1.getValueAt(i, 3).toString());
                double rprice1 = Double.parseDouble(jTable1.getValueAt(i, 4).toString());
                double wprice1 = Double.parseDouble(jTable1.getValueAt(i, 5).toString());
                double quan = Double.parseDouble(jTable1.getValueAt(i, 7).toString());
                String tname1 = jTable1.getValueAt(i, 19).toString();
                int quan1 = (int) quan;

                String mrp2 = String.format("%." + hmany1 + "f", mrp1);
                String rprice2 = String.format("%." + hmany1 + "f", rprice1);
                String wprice2 = String.format("%." + hmany1 + "f", wprice1);

                for (int j = 0; j < quan1; j++) {
                    barcode.add(barcode1);
                    ino.add(ino1);
                    iname.add(iname1);
                    mrp.add(mrp2);
                    rprice.add(rprice2);
                    wprice.add(wprice2);
                    tname.add(tname1);
                    items.add(Integer.parseInt(ino1));
                }
            } // rowcounts ends

            Connection conn = util.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement psDelete = conn.prepareStatement("delete from barcode");
            psDelete.executeUpdate();

            PreparedStatement psInsert = conn.prepareStatement("insert into barcode values (?,?,?,?,?,?,?)");
            for (int i = 0; i < barcode.size(); i++) {
                psInsert.setString(1, barcode.get(i).toString());
                psInsert.setString(2, ino.get(i).toString());
                psInsert.setString(3, iname.get(i).toString());
                psInsert.setString(4, mrp.get(i).toString());
                psInsert.setString(5, rprice.get(i).toString());
                psInsert.setString(6, wprice.get(i).toString());
                psInsert.setString(7, tname.get(i).toString());
                psInsert.addBatch();
            }
            int[] count = psInsert.executeBatch();
            conn.commit();
            if (count.length > 0) {
                barcodepack.jasper.JasperBarcodePrinter.print(ItemUtil.getItemsByItemNos(items));
            }
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    final void get_sug() {
        try {
            int count = 0;
            Connection conn = util.getConnection();
            PreparedStatement ps = conn.prepareStatement("select count(iname) from item");
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                count = set.getInt(1);
            }
            ps = conn.prepareStatement("select iname from item");
            set = ps.executeQuery();
            Object f[] = new Object[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support = AutoCompleteSupport.install(hh, GlazedLists.eventListOf(f));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void load_from_po_number() {
        try {
            boolean selva = false;
            String po_status = "Ordered";
            Connection conn = util.getConnection();
            PreparedStatement ps = conn.prepareStatement("select pstatus from po_entry where grn=?");
            ps.setString(1, h30.getText());
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                selva = true;
                po_status = r.getString(1);
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "Invalid PO Number!", "Invalid", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (po_status.equals("Order Received")) {
                JOptionPane.showMessageDialog(this, "This PO Already Received!", "Invalid", JOptionPane.ERROR_MESSAGE);
                return;
            }

            h30.setEditable(false);
            searchbutton1.setEnabled(false);
            ps = conn.prepareStatement("select cname,ttype from po_entry where grn=?");
            ps.setString(1, h30.getText());
            r = ps.executeQuery();
            while (r.next()) {
                h3.setText(r.getString(1));
                ttypel.setSelectedItem(r.getString(2));
                double old_bal = 0;
                ResultSet r1;
                try {
                    PreparedStatement psBal = conn.prepareStatement("select sum(tot-paid) from ven_bal where cname=?");
                    psBal.setString(1, h3.getText());
                    r1 = psBal.executeQuery();
                    while (r1.next()) {
                        old_bal = r1.getDouble(1);
                    }
                    creditLabel.setText("Credit: " + String.format("%." + hmany + "f", old_bal));
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }

            get_tax_type();
            ps = conn.prepareStatement(
                    "select barcode,a.ino,a.iname,a.mrp,rprice,wprice,price,quan,a.taxp,hsn,cat,manu,iname1 from po_items a,item b where a.ino=b.ino and grn=?");
            ps.setString(1, h30.getText());
            r = ps.executeQuery();
            while (r.next()) {
                s2.addRow(new Object[] { r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5),
                        r.getString(6), r.getString(7), r.getString(8), 0, 0, 0, 0, r.getString(9), 0, 0,
                        r.getString(10), ttype, r.getString(11), r.getString(12), r.getString(13) });
            }
            apply_all_changes();

        } catch (HeadlessException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public purchase_entry(DataUtil util) {
        initComponents();
        this.setTitle("Purchase Register");
        this.setSize(1258, 650);
        javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            this.setFrameIcon(icon);
        }
        this.util = util;
        button_short();
        load_items_table();
        get_defaults();
        h3.requestFocus();
        load_supplier_table();
        load_iname_table();
        alterbutton.setVisible(false);
        deletebutton.setVisible(false);
        jPanel1.setVisible(false);
        get_sug();
        printbutton.setVisible(false);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cname_list = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        iname_list = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        titlelablel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        savebutton = new javax.swing.JButton();
        viewbutton = new javax.swing.JButton();
        draftbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        alterbutton = new javax.swing.JButton();
        excelbutton = new javax.swing.JButton();
        h2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        h11 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        h1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        h3 = new javax.swing.JTextField();
        h9 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        h5 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        h4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        h15 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        h6 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        h7 = new javax.swing.JTextField();
        h10 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        h8 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        h13 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        h16 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        h14 = new javax.swing.JTextField();
        jCalendarButton1 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jLabel20 = new javax.swing.JLabel();
        h12 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        h18 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        h23 = new javax.swing.JTextField();
        h24 = new javax.swing.JTextField();
        h20 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        h17 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        h22 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        h19 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        h21 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        h25 = new javax.swing.JTextField();
        h26 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        h27 = new javax.swing.JTextField();
        clearbutton = new javax.swing.JButton();
        nextbutton = new javax.swing.JButton();
        prebutton = new javax.swing.JButton();
        removebutton = new javax.swing.JButton();
        webbutton = new javax.swing.JButton();
        formatbutton = new javax.swing.JButton();
        applybutton = new javax.swing.JButton();
        loadbutton = new javax.swing.JButton();
        deletebutton = new javax.swing.JButton();
        diffl = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        netl = new javax.swing.JLabel();
        h28 = new javax.swing.JComboBox<>();
        searchbutton = new javax.swing.JButton();
        itembutton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        ttypel = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        hh = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        h29 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        h30 = new javax.swing.JTextField();
        searchbutton1 = new javax.swing.JButton();
        printbutton = new javax.swing.JButton();
        creditLabel = new javax.swing.JLabel();

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
        jScrollPane2.setBounds(0, 0, 850, 400);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete22.png"))); // NOI18N
        jButton3.setMnemonic('o');
        jButton3.setText("Close");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        cname_list.getContentPane().add(jButton3);
        jButton3.setBounds(710, 400, 140, 30);

        iname_list.setUndecorated(true);
        iname_list.getContentPane().setLayout(null);

        jScrollPane3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jScrollPane3FocusLost(evt);
            }
        });

        jTable3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
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
        jScrollPane3.setBounds(0, 0, 1090, 350);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete22.png"))); // NOI18N
        jButton4.setMnemonic('o');
        jButton4.setText("Close");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        iname_list.getContentPane().add(jButton4);
        jButton4.setBounds(950, 350, 140, 30);

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Purchase Register");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 200, 30);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText(" GRN Date");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(250, 40, 80, 30);

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
        savebutton.setBounds(480, 510, 150, 50);

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
        viewbutton.setBounds(780, 560, 150, 50);

        draftbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        draftbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/draft45.png"))); // NOI18N
        draftbutton.setMnemonic('f');
        draftbutton.setText("Draft");
        draftbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                draftbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(draftbutton);
        draftbutton.setBounds(630, 510, 150, 50);

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
        closebutton.setBounds(1080, 560, 150, 50);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText(" Round Amt");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(630, 470, 90, 30);

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
        alterbutton.setBounds(10, 510, 150, 50);

        excelbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        excelbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/load45.jpg.png"))); // NOI18N
        excelbutton.setMnemonic('x');
        excelbutton.setText("Load Excel");
        excelbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excelbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(excelbutton);
        excelbutton.setBounds(10, 560, 151, 50);

        h2.setEditable(false);
        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(330, 40, 160, 30);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText(" PO No");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(490, 40, 80, 30);

        h11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h11ActionPerformed(evt);
            }
        });
        getContentPane().add(h11);
        h11.setBounds(70, 180, 180, 30);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("GRN No");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(10, 40, 60, 30);

        h1.setEditable(false);
        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setText("--");
        h1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h1FocusGained(evt);
            }
        });
        getContentPane().add(h1);
        h1.setBounds(70, 40, 180, 30);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Supplier");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(10, 70, 60, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h3KeyPressed(evt);
            }
        });
        getContentPane().add(h3);
        h3.setBounds(70, 70, 420, 30);

        h9.setEditable(false);
        h9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h9);
        h9.setBounds(250, 150, 480, 30);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText(" Bill Date");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(250, 100, 100, 30);

        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h5ActionPerformed(evt);
            }
        });
        getContentPane().add(h5);
        h5.setBounds(330, 100, 130, 30);

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
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 220, 1220, 220);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Bill No");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(10, 100, 60, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h4ActionPerformed(evt);
            }
        });
        getContentPane().add(h4);
        h4.setBounds(70, 100, 180, 30);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText(" Qty");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(1130, 180, 30, 30);

        h15.setEditable(false);
        h15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h15);
        h15.setBounds(1090, 180, 40, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("MRP");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(10, 180, 60, 30);

        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h6ActionPerformed(evt);
            }
        });
        getContentPane().add(h6);
        h6.setBounds(570, 100, 150, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("   Tax %");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(1030, 180, 60, 30);

        h7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h7ActionPerformed(evt);
            }
        });
        getContentPane().add(h7);
        h7.setBounds(790, 150, 200, 30);

        h10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h10ActionPerformed(evt);
            }
        });
        getContentPane().add(h10);
        h10.setBounds(1090, 150, 140, 30);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("It.Code");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(10, 150, 60, 30);

        h8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h8ActionPerformed(evt);
            }
        });
        h8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h8KeyPressed(evt);
            }
        });
        getContentPane().add(h8);
        h8.setBounds(70, 150, 180, 30);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText(" Retail Price");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(250, 180, 80, 30);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText(" Wholesale Price");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(490, 180, 110, 30);

        h13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h13ActionPerformed(evt);
            }
        });
        getContentPane().add(h13);
        h13.setBounds(600, 180, 130, 30);

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText(" Barcode");
        getContentPane().add(jLabel18);
        jLabel18.setBounds(730, 150, 60, 30);

        h16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h16ActionPerformed(evt);
            }
        });
        getContentPane().add(h16);
        h16.setBounds(1160, 180, 70, 30);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText(" Dis Amt");
        getContentPane().add(jLabel19);
        jLabel19.setBounds(850, 180, 60, 30);

        h14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h14ActionPerformed(evt);
            }
        });
        getContentPane().add(h14);
        h14.setBounds(790, 180, 60, 30);

        jCalendarButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
        jCalendarButton1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton1PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton1);
        jCalendarButton1.setBounds(460, 100, 30, 30);

        jLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel20.setText(" Total Qty");
        getContentPane().add(jLabel20);
        jLabel20.setBounds(210, 440, 100, 30);

        h12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h12ActionPerformed(evt);
            }
        });
        getContentPane().add(h12);
        h12.setBounds(330, 180, 160, 30);

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setText(" Sub Total");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(430, 440, 80, 30);

        h18.setEditable(false);
        h18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h18);
        h18.setBounds(310, 440, 120, 30);

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText(" Discount");
        getContentPane().add(jLabel22);
        jLabel22.setBounds(630, 440, 80, 30);

        jLabel23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel23.setText("Total Items");
        getContentPane().add(jLabel23);
        jLabel23.setBounds(10, 440, 80, 30);

        h23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h23ActionPerformed(evt);
            }
        });
        getContentPane().add(h23);
        h23.setBounds(90, 470, 120, 30);

        h24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h24ActionPerformed(evt);
            }
        });
        getContentPane().add(h24);
        h24.setBounds(310, 470, 120, 30);

        h20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h20ActionPerformed(evt);
            }
        });
        getContentPane().add(h20);
        h20.setBounds(710, 440, 90, 30);

        jLabel24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel24.setText(" Net Total");
        getContentPane().add(jLabel24);
        jLabel24.setBounds(800, 470, 80, 30);

        h17.setEditable(false);
        h17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h17);
        h17.setBounds(90, 440, 120, 30);

        jLabel25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel25.setText(" Pay Mode");
        getContentPane().add(jLabel25);
        jLabel25.setBounds(1010, 470, 90, 30);

        h22.setEditable(false);
        h22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h22);
        h22.setBounds(1100, 440, 130, 30);

        jLabel26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel26.setText("Frieght");
        getContentPane().add(jLabel26);
        jLabel26.setBounds(10, 470, 80, 30);

        h19.setEditable(false);
        h19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h19);
        h19.setBounds(510, 440, 120, 30);

        jLabel27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel27.setText(" Other Charges");
        getContentPane().add(jLabel27);
        jLabel27.setBounds(210, 470, 100, 30);

        h21.setEditable(false);
        h21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h21);
        h21.setBounds(880, 440, 130, 30);

        jLabel28.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel28.setText(" Grant Total");
        getContentPane().add(jLabel28);
        jLabel28.setBounds(430, 470, 80, 30);

        h25.setEditable(false);
        h25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h25);
        h25.setBounds(510, 470, 120, 30);

        h26.setEditable(false);
        h26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h26);
        h26.setBounds(710, 470, 90, 30);

        jLabel30.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel30.setText(" Gross Total");
        getContentPane().add(jLabel30);
        jLabel30.setBounds(800, 440, 90, 30);

        jLabel29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel29.setText(" Tax Amount");
        getContentPane().add(jLabel29);
        jLabel29.setBounds(1010, 440, 90, 30);

        h27.setEditable(false);
        h27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h27);
        h27.setBounds(880, 470, 130, 30);

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
        clearbutton.setBounds(930, 560, 150, 50);

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
        nextbutton.setBounds(630, 560, 150, 50);

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
        prebutton.setBounds(480, 560, 150, 50);

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
        removebutton.setBounds(930, 510, 150, 50);

        webbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        webbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/web45.png"))); // NOI18N
        webbutton.setMnemonic('w');
        webbutton.setText("Web Order");
        webbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(webbutton);
        webbutton.setBounds(320, 560, 160, 50);

        formatbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        formatbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/excel45.png"))); // NOI18N
        formatbutton.setMnemonic('y');
        formatbutton.setText("Excel Format");
        formatbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formatbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(formatbutton);
        formatbutton.setBounds(160, 560, 160, 50);

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
        applybutton.setBounds(1080, 510, 150, 50);

        loadbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        loadbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/draftload45.png"))); // NOI18N
        loadbutton.setMnemonic('t');
        loadbutton.setText("Load Draft");
        loadbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(loadbutton);
        loadbutton.setBounds(780, 510, 150, 50);

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
        deletebutton.setBounds(320, 510, 160, 50);

        diffl.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        diffl.setForeground(new java.awt.Color(0, 0, 153));
        getContentPane().add(diffl);
        diffl.setBounds(1100, 30, 140, 30);

        jLabel46.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel46.setText("Difference:");
        getContentPane().add(jLabel46);
        jLabel46.setBounds(1100, 10, 140, 30);

        netl.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        netl.setForeground(new java.awt.Color(255, 0, 0));
        netl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(netl);
        netl.setBounds(780, 0, 300, 50);

        h28.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h28.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Credit", "Cash", "Bank", "UPI", "Others" }));
        h28.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h28KeyPressed(evt);
            }
        });
        getContentPane().add(h28);
        h28.setBounds(1100, 470, 130, 30);

        searchbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        searchbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search22.png"))); // NOI18N
        searchbutton.setMnemonic('h');
        searchbutton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        searchbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(searchbutton);
        searchbutton.setBounds(720, 100, 60, 30);

        itembutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        itembutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add22_1.png"))); // NOI18N
        itembutton.setMnemonic('i');
        itembutton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        itembutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itembuttonActionPerformed(evt);
            }
        });
        getContentPane().add(itembutton);
        itembutton.setBounds(720, 70, 60, 30);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText(" Bill Amount");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(490, 100, 80, 30);

        ttypel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ttypel.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Exclusive of Tax", "Inclusive of Tax", "No Tax" }));
        getContentPane().add(ttypel);
        ttypel.setBounds(570, 70, 150, 30);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));
        jPanel1.setLayout(null);

        jLabel14.setText("Use 'Alt+Q' for Close Search");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(150, 40, 170, 30);

        hh.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        hh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        jPanel1.add(hh);
        hh.setBounds(60, 10, 370, 30);

        jButton1.setBackground(new java.awt.Color(255, 255, 204));
        jButton1.setMnemonic('q');
        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(320, 40, 110, 23);

        jLabel32.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel32.setText("It.Name");
        jPanel1.add(jLabel32);
        jLabel32.setBounds(10, 10, 50, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(790, 70, 440, 70);

        jLabel31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel31.setText(" Purchase Price");
        getContentPane().add(jLabel31);
        jLabel31.setBounds(990, 150, 100, 30);

        jLabel33.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel33.setText(" Dis %");
        getContentPane().add(jLabel33);
        jLabel33.setBounds(730, 180, 60, 30);

        h29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h29ActionPerformed(evt);
            }
        });
        getContentPane().add(h29);
        h29.setBounds(910, 180, 80, 30);

        jLabel34.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel34.setText(" Tax Type");
        getContentPane().add(jLabel34);
        jLabel34.setBounds(490, 70, 80, 30);

        h30.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h30ActionPerformed(evt);
            }
        });
        getContentPane().add(h30);
        h30.setBounds(570, 40, 150, 30);

        searchbutton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        searchbutton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search22.png"))); // NOI18N
        searchbutton1.setMnemonic('h');
        searchbutton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        searchbutton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbutton1ActionPerformed(evt);
            }
        });
        getContentPane().add(searchbutton1);
        searchbutton1.setBounds(720, 40, 60, 30);

        printbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        printbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/print45.png"))); // NOI18N
        printbutton.setMnemonic('p');
        printbutton.setText("Print");
        printbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(printbutton);
        printbutton.setBounds(160, 510, 160, 50);

        creditLabel.setForeground(new java.awt.Color(153, 153, 0));
        creditLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(creditLabel);
        creditLabel.setBounds(570, 0, 150, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }// GEN-LAST:event_closebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_savebuttonActionPerformed
        String pby = h28.getSelectedItem().toString();
        if (pby.equalsIgnoreCase("UPI")) {
            double net;
            try {
                apply_all_changes();
                net = Double.parseDouble(h27.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Net Amount", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (net <= 0) {
                JOptionPane.showMessageDialog(this, "Net amount must be greater than zero for UPI payment.",
                        "Invalid Amount", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Frame frame = JOptionPane.getFrameForComponent(this);
            UPIPaymentDialog dialog = new UPIPaymentDialog(frame, net, (paidAmount) -> {
                save();
            });
            dialog.setVisible(true);
        } else {
            save();
        }

    }// GEN-LAST:event_savebuttonActionPerformed

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_viewbuttonActionPerformed
        String grn = JOptionPane.showInputDialog(this, "Enter GRN No ?", "GRN No", JOptionPane.PLAIN_MESSAGE);
        if ("".equals(grn) || grn == null) {
            JOptionPane.showMessageDialog(this, "Invalid GRN No!", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        view(grn);

    }// GEN-LAST:event_viewbuttonActionPerformed

    private void alterbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_alterbuttonActionPerformed
        alter_purchase();
    }// GEN-LAST:event_alterbuttonActionPerformed

    private void excelbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_excelbuttonActionPerformed
        try {
            javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
            int returnVal = fc.showOpenDialog(this);

            if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try (java.io.FileInputStream fis = new java.io.FileInputStream(file);
                        org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(
                                fis)) {

                    org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
                    for (org.apache.poi.ss.usermodel.Row row : sheet) {
                        if (row.getRowNum() == 0)
                            continue; // Skip header

                        String barcode = getCellValue(row.getCell(0));
                        String ino = getCellValue(row.getCell(1));
                        String iname = getCellValue(row.getCell(2));
                        double prate = getNumericCellValue(row.getCell(3));
                        double mrp = getNumericCellValue(row.getCell(4));
                        double rrate = getNumericCellValue(row.getCell(5));
                        double wrate = getNumericCellValue(row.getCell(6));
                        double disp = getNumericCellValue(row.getCell(7));
                        double disamt = getNumericCellValue(row.getCell(8));
                        int taxp = (int) getNumericCellValue(row.getCell(9));
                        double quan = getNumericCellValue(row.getCell(10));

                        if (!ino.isEmpty() && !iname.isEmpty()) {
                            add_item(barcode, ino, iname, prate, mrp, rrate, wrate, disp, disamt, taxp, quan);
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error importing Excel: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }// GEN-LAST:event_excelbuttonActionPerformed

    private String getCellValue(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell == null)
            return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                java.text.DecimalFormat df = new java.text.DecimalFormat("#");
                return df.format(cell.getNumericCellValue());
            default:
                return "";
        }
    }

    private double getNumericCellValue(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell == null)
            return 0;
        if (cell.getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == org.apache.poi.ss.usermodel.CellType.STRING) {
            try {
                return Double.parseDouble(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    private void draftbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_draftbuttonActionPerformed
        save_draft();
    }// GEN-LAST:event_draftbuttonActionPerformed

    private void jCalendarButton1PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jCalendarButton1PropertyChange
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
    }// GEN-LAST:event_jCalendarButton1PropertyChange

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbuttonActionPerformed
        form_clear();

    }// GEN-LAST:event_clearbuttonActionPerformed

    private void nextbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_nextbuttonActionPerformed
        try {

            String grn = h1.getText();
            if (grn.equalsIgnoreCase("--")) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Connection conn = util.getConnection();
            PreparedStatement ps = conn.prepareStatement("select grn from purchase where grn > ? order by grn limit 1");
            ps.setString(1, grn);
            ResultSet set1 = ps.executeQuery();
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
        } catch (HeadlessException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_nextbuttonActionPerformed

    private void prebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_prebuttonActionPerformed

        try {
            String grn = h1.getText();
            Connection conn = util.getConnection();
            ResultSet set1;
            if (grn.equalsIgnoreCase("--")) {
                PreparedStatement ps = conn.prepareStatement("select max(grn) from purchase");
                set1 = ps.executeQuery();
            } else {
                PreparedStatement ps = conn
                        .prepareStatement("select grn from purchase where grn < ? order by grn desc limit 1");
                ps.setString(1, grn);
                set1 = ps.executeQuery();
            }

            boolean selva = false;
            String search_grn = "";
            while (set1.next()) {
                selva = true;
                search_grn = set1.getString(1);
            }
            if (selva == false || search_grn == null) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            view(search_grn);
        } catch (HeadlessException | SQLException e) {
            System.out.println(e.toString());
        }
    }// GEN-LAST:event_prebuttonActionPerformed

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

    }// GEN-LAST:event_removebuttonActionPerformed

    private void webbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_webbuttonActionPerformed
        JOptionPane.showMessageDialog(this, "Please Contact Customer Care to Enable this Feature!");
        // TODO add your handling code here:
    }// GEN-LAST:event_webbuttonActionPerformed

    private void formatbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_formatbuttonActionPerformed
        try {
            String drive = new menupack.menu_form().getDrive();
            String folder = new menupack.menu_form().getFoler();
            File file = new File(folder + "/Drafts/Purchase_Import_Format.xlsx");

            try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
                org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Purchase Format");
                org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
                String[] headers = { "Barcode", "Item No", "Item Name", "Purchase Rate", "MRP", "Retail Price",
                        "Wholesale Price", "Discount %", "Discount Amt", "Tax %", "Quantity" };

                for (int i = 0; i < headers.length; i++) {
                    headerRow.createCell(i).setCellValue(headers[i]);
                }

                try (java.io.FileOutputStream fileOut = new java.io.FileOutputStream(file)) {
                    workbook.write(fileOut);
                }
            }

            Utils.AppConfig.openFile(file.getAbsolutePath());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating format: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }// GEN-LAST:event_formatbuttonActionPerformed

    private void applybuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_applybuttonActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            return;
        }
        apply_all_changes();
    }// GEN-LAST:event_applybuttonActionPerformed

    private void loadbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_loadbuttonActionPerformed
        load_draft();
    }// GEN-LAST:event_loadbuttonActionPerformed

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deletebuttonActionPerformed
        delete();
    }// GEN-LAST:event_deletebuttonActionPerformed

    private void h7ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h7ActionPerformed
        if (h7.getText().equals("")) {
            h20.requestFocus();
        } else {
            if (h9.getText().equals("")) {
                get_item_details_using_barcode();
                h14.requestFocus();
            } else {
                h14.requestFocus();
            }
        }

    }// GEN-LAST:event_h7ActionPerformed

    private void h8ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h8ActionPerformed
        if (h8.getText().equals("")) {
            h7.requestFocus();
        } else {
            if (h9.getText().equals("")) {
                get_item_details_using_item_no();
            }
            h14.requestFocus();
        }
    }// GEN-LAST:event_h8ActionPerformed

    private void h16ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h16ActionPerformed
        try {
            if (h8.getText().equals("") || h9.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Item Details ?", "Item Details", JOptionPane.ERROR_MESSAGE);
                h8.requestFocus();
                return;
            }
            if (h7.getText().equals("")) {
                h7.setText(h8.getText());
            }
            if (h10.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Purchase Price ?", "Purchase Price",
                        JOptionPane.ERROR_MESSAGE);
                h10.requestFocus();
                return;
            }
            if (h11.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter MRP ?", "MRP", JOptionPane.ERROR_MESSAGE);
                h11.requestFocus();
                return;
            }
            if (h12.getText().equals("")) {
                h12.setText(h11.getText());
            }
            if (h13.getText().equals("")) {
                h13.setText(h11.getText());
            }
            if (h14.getText().equals("")) {
                h14.setText("" + 0);
            }
            if (h15.getText().equals("")) {
                h15.setText("" + 0);
            }
            if (h16.getText().equals("")) {
                h16.setText("" + 1);
            }
            if (h29.getText().equals("")) {
                h29.setText("" + 0);
            }
            String barcode = h7.getText();
            String ino = h8.getText();
            String iname = h9.getText();
            double prate = Double.parseDouble(h10.getText());
            double mrp = Double.parseDouble(h11.getText());
            double rrate1 = Double.parseDouble(h12.getText());
            double wrate1 = Double.parseDouble(h13.getText());
            double disp = Double.parseDouble(h14.getText());
            double disamt = Double.parseDouble(h29.getText());
            int taxp = Integer.parseInt(h15.getText());
            double quan = Double.parseDouble(h16.getText());

            if (taxp != 0 && taxp != 5 && taxp != 12 && taxp != 18 && taxp != 28) {
                JOptionPane.showMessageDialog(this, "<html><h4>Allowed: 0%, 5%, 12%, 18%, 28%</h4></html>",
                        "Invalid GST", JOptionPane.ERROR_MESSAGE);
                h18.requestFocus();
                return;
            }
            add_item(barcode, ino, iname, prate, mrp, rrate1, wrate1, disp, disamt, taxp, quan);
        } catch (HeadlessException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_h16ActionPerformed

    private void h10ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h10ActionPerformed
        h11.requestFocus();
    }// GEN-LAST:event_h10ActionPerformed

    private void h11ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h11ActionPerformed
        if (h11.getText().equals("")) {
            h11.setText("" + 0);
        }
        if (h10.getText().equals("")) {
            h10.setText("" + 0);
        }
        double mrp = Double.parseDouble(h11.getText());
        double prate = Double.parseDouble(h10.getText());
        double rprice = 0, wprice = 0;

        if (rrate.equalsIgnoreCase("MRP Price")) {
            rprice = mrp;
        } else if (rrate.equalsIgnoreCase("MRP- Discount %")) {
            double disamt = (rdis * mrp) / 100;
            rprice = mrp - disamt;
        } else if (rrate.equalsIgnoreCase("Purchase Price + Margin %")) {
            double addamt = (rdis * prate) / 100;
            rprice = prate + addamt;
        }
        String rprice1 = String.format("%." + hmany + "f", rprice);
        h12.setText("" + rprice1);

        if (wrate.equalsIgnoreCase("MRP Price")) {
            wprice = mrp;
        } else if (wrate.equalsIgnoreCase("MRP- Discount %")) {
            double disamt = (wdis * mrp) / 100;
            wprice = mrp - disamt;
        } else if (wrate.equalsIgnoreCase("Purchase Price + Margin %")) {
            double addamt = (wdis * prate) / 100;
            wprice = prate + addamt;
        }
        String wprice1 = String.format("%." + hmany + "f", wprice);
        h13.setText("" + wprice1);

        if (rrate.equalsIgnoreCase("Manual") && wrate.equalsIgnoreCase("Manual")) {
            h12.setText("");
            h13.setText("");
            h12.requestFocus();
        } else {
            h14.requestFocus();
        }

    }// GEN-LAST:event_h11ActionPerformed

    private void h12ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h12ActionPerformed
        h13.requestFocus();
    }// GEN-LAST:event_h12ActionPerformed

    private void h13ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h13ActionPerformed
        h14.requestFocus();
    }// GEN-LAST:event_h13ActionPerformed

    private void h14ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h14ActionPerformed
        h29.requestFocus();
    }// GEN-LAST:event_h14ActionPerformed

    private void h4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h4ActionPerformed
        if (h4.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Enter Bill No ?", "Bill No", JOptionPane.ERROR_MESSAGE);
            h4.requestFocus();
            return;
        }
        h5.requestFocus();
    }// GEN-LAST:event_h4ActionPerformed

    private void h5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h5ActionPerformed
        if (h5.getText().equals("")) {
            h5.setText(h2.getText());
        }
        h6.requestFocus();
    }// GEN-LAST:event_h5ActionPerformed

    private void h6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h6ActionPerformed
        if (h6.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Enter Bill Amount ?", "Bill Amount", JOptionPane.ERROR_MESSAGE);
            h6.requestFocus();
            return;
        }
        h8.requestFocus();

    }// GEN-LAST:event_h6ActionPerformed

    private void h20ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h20ActionPerformed
        if (h20.getText().equals("")) {
            h20.setText("" + 0);
        }
        if (Double.parseDouble(h20.getText()) > 0) {
            apply_all_changes();
        }
        h23.requestFocus();
    }// GEN-LAST:event_h20ActionPerformed

    private void h23ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h23ActionPerformed

        if (h23.getText().equals("")) {
            h23.setText("" + 0);
        }
        if (Double.parseDouble(h23.getText()) > 0) {
            apply_all_changes();
        }
        h24.requestFocus();
    }// GEN-LAST:event_h23ActionPerformed

    private void h24ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h24ActionPerformed
        if (h24.getText().equals("")) {
            h24.setText("" + 0);
        }
        if (Double.parseDouble(h24.getText()) > 0) {
            apply_all_changes();
        }
        h28.requestFocus();

    }// GEN-LAST:event_h24ActionPerformed

    private void h28KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_h28KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            save();
        }
    }// GEN-LAST:event_h28KeyPressed

    private void h1FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h1FocusGained
        h30.requestFocus();
    }// GEN-LAST:event_h1FocusGained

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
        cname_list.dispose();
    }// GEN-LAST:event_jButton3ActionPerformed

    private void h3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_h3KeyPressed
        cname_list.requestFocus();
        jTable2.requestFocus();
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                h4.requestFocus();
                break;
            case KeyEvent.VK_ESCAPE:
                h3.requestFocus();
                cname_list.dispose();
                break;
            case KeyEvent.VK_DOWN:
                if (s3.getRowCount() > 0) {
                    s3.getDataVector().removeAllElements();
                    s3.fireTableDataChanged();
                }
                try {
                    cname_list.requestFocus();
                    Point l = h3.getLocationOnScreen();
                    cname_list.setLocation(l.x, l.y + h3.getHeight());
                    cname_list.setSize(857, 438);
                    cname_list.setVisible(true);
                    Connection conn = util.getConnection();
                    PreparedStatement ps = conn.prepareStatement(
                            "select cname,city from vendor where cname like ? order by cname limit 300");
                    ps.setString(1, h3.getText() + "%");
                    ResultSet r = ps.executeQuery();
                    while (r.next()) {
                        s3.addRow(new Object[] { r.getString(1), r.getString(2) });
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                break;
        }

    }// GEN-LAST:event_h3KeyPressed

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTable2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable2.getRowCount() > 0) {
                h3.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
                get_tax_type();
                double old_bal = 0;

                try {
                    Connection conn = util.getConnection();
                    PreparedStatement ps = conn.prepareStatement("select sum(tot-paid) from ven_bal where cname=?");
                    ps.setString(1, h3.getText());
                    ResultSet r1 = ps.executeQuery();
                    while (r1.next()) {
                        old_bal = r1.getDouble(1);
                    }
                    creditLabel.setText("Credit: " + String.format("%." + hmany + "f", old_bal));
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }
            h4.requestFocus();
            cname_list.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cname_list.dispose();
            h3.requestFocus();
        }

    }// GEN-LAST:event_jTable2KeyPressed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable2MouseClicked

        if (jTable2.getRowCount() > 0) {
            h3.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
            get_tax_type();
            double old_bal = 0;
            try {
                Connection conn = util.getConnection();
                PreparedStatement ps = conn.prepareStatement("select sum(tot-paid) from ven_bal where cname=?");
                ps.setString(1, h3.getText());
                ResultSet r1 = ps.executeQuery();
                while (r1.next()) {
                    old_bal = r1.getDouble(1);
                }
                creditLabel.setText("Credit: " + String.format("%." + hmany + "f", old_bal));
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        h4.requestFocus();
        cname_list.dispose();
    }// GEN-LAST:event_jTable2MouseClicked

    private void jScrollPane2FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jScrollPane2FocusLost
        cname_list.dispose();
    }// GEN-LAST:event_jScrollPane2FocusLost

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable3MouseClicked
        if (jTable3.getRowCount() > 0) {
            selectItemFromJTable();
        }
        get_item_details_using_item_no();
        h14.requestFocus();
        iname_list.dispose();
    }// GEN-LAST:event_jTable3MouseClicked

    private void jTable3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTable3KeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable3.getRowCount() > 0) {
                selectItemFromJTable();
            }
            get_item_details_using_item_no();
            h14.requestFocus();
            iname_list.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            iname_list.dispose();
            h8.requestFocus();
        }
    }// GEN-LAST:event_jTable3KeyPressed

    private void selectItemFromJTable() {
        if (jTable3.getRowCount() > 0) {
            try {
                String selectedName = jTable3.getValueAt(jTable3.getSelectedRow(), 2).toString();
                Connection conn = util.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT ino, iname, barcode,"
                        + (CompanySettingUtil.getInstance().isDisplayBatch() ? "batch" : "size")
                        + ",rprice, wprice FROM item WHERE iname = ?");
                ps.setString(1, selectedName);
                ResultSet set = ps.executeQuery();

                List<Object[]> rows = new ArrayList<>();
                Map<String, String> valueMap = new HashMap<>();
                while (set.next()) {
                    rows.add(new Object[] {
                            set.getString("ino"),
                            set.getString("iname"),
                            set.getString("barcode"),
                            set.getString(CompanySettingUtil.getInstance().isDisplayBatch() ? "batch" : "size"),
                            set.getString("rprice"),
                            set.getString("wprice")
                    });

                    valueMap.put("ino", set.getString("ino"));
                    valueMap.put("iname", set.getString("iname"));
                    valueMap.put("rprice", set.getString("rprice"));
                    valueMap.put("wprice", set.getString("wprice"));
                    valueMap.put("barcode", set.getString("barcode"));

                }
                if (rows.size() == 1) {
                    h8.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
                } else {
                    // Multiple records found, show selection dialog
                    String selectedIno = showSelectionDialog(rows);
                    if (selectedIno != null) {
                        h8.setText(selectedIno);
                    } else {
                        // User cancelled or no selection
                        JOptionPane.showMessageDialog(this, "No item selected.", "Cancelled",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
            }
        }
        h19.requestFocus();
        iname_list.dispose();
    }

    private String showSelectionDialog(List<Object[]> data) {
        String[] columns = { "Item No", "Item Name", "Barcode",
                CompanySettingUtil.getInstance().isDisplayBatch() ? "Batch" : "Size", "RPrice", "WPrice" };

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

    private void h8KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_h8KeyPressed

        iname_list.requestFocus();
        jTable3.requestFocus();
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                h8.requestFocus();
                iname_list.dispose();
                break;
            case KeyEvent.VK_DOWN:
                if (s4.getRowCount() > 0) {
                    s4.getDataVector().removeAllElements();
                    s4.fireTableDataChanged();
                }
                try {
                    iname_list.requestFocus();
                    Point l = jLabel15.getLocationOnScreen();
                    iname_list.setLocation(l.x, l.y + jLabel15.getHeight());
                    iname_list.setSize(1100, 382);
                    iname_list.setVisible(true);
                    String query = "SELECT i.ino, i.barcode, i.iname, i.cat, i.manu FROM item i INNER JOIN ( SELECT iname, MIN(ino) AS min_ino FROM item WHERE iname LIKE  '%"
                            + h8.getText().trim().replace("'", "''")
                            + "%' GROUP BY iname ) sub ON i.iname = sub.iname AND i.ino = sub.min_ino ORDER BY i.ino LIMIT 500";
                    ResultSet r = util.doQuery(query);
                    while (r.next()) {
                        s4.addRow(new Object[] { r.getString(1), r.getString(2), r.getString(3), r.getString(4),
                                r.getString(5) });
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                break;
        }

    }// GEN-LAST:event_h8KeyPressed

    private void searchbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchbuttonActionPerformed
        jPanel1.setVisible(true);
        hh.requestFocus();

    }// GEN-LAST:event_searchbuttonActionPerformed

    private void itembuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_itembuttonActionPerformed

        itempack.item_master oe = new item_master(util);
        JDesktopPane desktop_pane = getDesktopPane();
        desktop_pane.add(oe);
        oe.setVisible(true);

        Dimension desktopSize = desktop_pane.getSize();
        Dimension jInternalFrameSize = oe.getSize();
        oe.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);
    }// GEN-LAST:event_itembuttonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        jPanel1.setVisible(false);
        h8.requestFocus();

    }// GEN-LAST:event_jButton1ActionPerformed

    private void h29ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h29ActionPerformed
        h16.requestFocus();
    }// GEN-LAST:event_h29ActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTable1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            apply_all_changes();
        }
    }// GEN-LAST:event_jTable1KeyPressed

    private void h30ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h30ActionPerformed
        h3.requestFocus();
    }// GEN-LAST:event_h30ActionPerformed

    private void searchbutton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchbutton1ActionPerformed
        if (h30.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Enter PO Number ?", "Invalid", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        load_from_po_number();
        // TODO add your handling code here:
    }// GEN-LAST:event_searchbutton1ActionPerformed

    private void printbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_printbuttonActionPerformed
        int bb = JOptionPane.showConfirmDialog(this, "<html><h1>You Want to Print Barcode ?</h1></html>", "Barcode",
                JOptionPane.YES_NO_OPTION);
        if (bb == JOptionPane.YES_OPTION) {
            print_barcode();
        }
    }// GEN-LAST:event_printbuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton alterbutton;
    private javax.swing.JButton applybutton;
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JDialog cname_list;
    private javax.swing.JLabel creditLabel;
    private javax.swing.JButton deletebutton;
    private javax.swing.JLabel diffl;
    private javax.swing.JButton draftbutton;
    private javax.swing.JButton excelbutton;
    private javax.swing.JButton formatbutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h10;
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
    private javax.swing.JTextField h20;
    private javax.swing.JTextField h21;
    private javax.swing.JTextField h22;
    private javax.swing.JTextField h23;
    private javax.swing.JTextField h24;
    private javax.swing.JTextField h25;
    private javax.swing.JTextField h26;
    private javax.swing.JTextField h27;
    private javax.swing.JComboBox<String> h28;
    private javax.swing.JTextField h29;
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h30;
    private javax.swing.JTextField h4;
    private javax.swing.JTextField h5;
    private javax.swing.JTextField h6;
    private javax.swing.JTextField h7;
    private javax.swing.JTextField h8;
    private javax.swing.JTextField h9;
    private javax.swing.JComboBox<String> hh;
    private javax.swing.JDialog iname_list;
    private javax.swing.JButton itembutton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton1;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JButton loadbutton;
    private javax.swing.JLabel netl;
    private javax.swing.JButton nextbutton;
    private javax.swing.JButton prebutton;
    private javax.swing.JButton printbutton;
    private javax.swing.JButton removebutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JButton searchbutton;
    private javax.swing.JButton searchbutton1;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JComboBox<String> ttypel;
    private javax.swing.JButton viewbutton;
    private javax.swing.JButton webbutton;
    // End of variables declaration//GEN-END:variables
}
