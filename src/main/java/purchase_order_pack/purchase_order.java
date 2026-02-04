package purchase_order_pack;

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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import menupack.menu_form;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class purchase_order extends javax.swing.JInternalFrame {

    DataUtil util = null;
    DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
    DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
    boolean selvagates = false;
    menupack.menu_form me = new menu_form();
    String username = me.getUsername();
    String user_type = me.getUserType();
    int hmany = me.getHmany();

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
            return column == 2 || column == 5 || column == 6;
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
        prebutton.setText("<html><b>Last Entry</b><br>(Alt+R)</h6><html>");
        nextbutton.setText("<html><b>Next Entry</b><br>(Alt+N)</h6><html>");
        applybutton.setText("<html><b>Apply</b><br>(Alt+A)</h6><html>");
        removebutton.setText("<html><b>Remove</b><br>(Alt+M)</h6><html>");

        titlelablel.setText("<html><u>Purchase Order</u></html>");

        Date d = new Date();
        SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
        h2.setText(g.format(d));
    }

    public final void load_items_table() {
        s2.addColumn("It.Code");
        s2.addColumn("Name");
        s2.addColumn("MRP");
        s2.addColumn("Re-Order Level");
        s2.addColumn("Stock-in-Hand");
        s2.addColumn("Order_Qty");
        s2.addColumn("Purchase Price");
        s2.addColumn("Amount");
        s2.addColumn("Tax %");
        s2.addColumn("Tax Amount");
        s2.addColumn("Total");
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
        jTable1.setModel(s2);
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
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(123);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(300);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(10).setPreferredWidth(120);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
        jTable1.setShowGrid(true);
    }

    void get_grn_no() {
        try {
            int sno = 1;
            String query = "select max(grn) from po_entry";
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

    void get_item_details_using_itemcode() {
        try {
            String query = "select iname,taxp from item where ino='" + h7.getText() + "' order by ino limit 1";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                h8.setText(r.getString(1));
                h10.setText(r.getString(2));
            }
            query = "select distinct prate from stock where ino='" + h7.getText() + "' order by barcode";
            r = util.doQuery(query);
            while (r.next()) {
                h9.setText(r.getString(1));
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void add_items_using_supplier() {
        try {
            if (h3.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Supplier Name ?", "Supplier Name", JOptionPane.ERROR_MESSAGE);
                h3.requestFocus();
                return;
            }
            String query = "select distinct a.ino,a.iname,mrp,minstock,sum(quan),prate,taxp from vendor_link a,stock b where cname='" + h3.getText() + "' and a.ino=b.ino and a.iname=b.iname group by a.ino order by a.ino";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                int taxp = r.getInt(7);
                double prate = r.getDouble(6);

                if (ttypel.getSelectedItem().equals("Inclusive of Tax")) {
                    double devide = 100 + taxp;
                    devide = prate * (100 / devide);
                    double taxamt = prate - devide;
                    prate = prate - taxamt;
                }
                String prate2 = String.format("%." + hmany + "f", prate);
                s2.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), "" + 0, "" + prate2, "", "" + taxp, "" + 0, "" + 0});
            }
            apply_all_changes();
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void load_auto_genrate() {
        try {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                double stock = Double.parseDouble(jTable1.getValueAt(i, 3).toString());
                double minstock = Double.parseDouble(jTable1.getValueAt(i, 4).toString());
                double required = stock - minstock;
                if (required > 0) {
                    jTable1.setValueAt(required, i, 5);
                }
            }
            apply_all_changes();
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    void add_item() {
        try {
            boolean selva = false;
            String ino = h7.getText(), iname = h8.getText();
            double prate = Double.parseDouble(h9.getText());
            int taxp = Integer.parseInt(h10.getText());
            double quan = Double.parseDouble(h11.getText());
            double minstock = 0, stock = 0, mrp = 0;
            String query = "select minstock from item where ino='" + ino + "'";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                selva = true;
                minstock = r.getDouble(1);
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "Invalid Product Details!", "Invalid", JOptionPane.ERROR_MESSAGE);
                fields_clear();
                return;
            }

            query = "select sum(quan),mrp from stock where ino='" + ino + "' group by mrp";
            r = util.doQuery(query);
            while (r.next()) {
                stock = r.getDouble(1);
                mrp = r.getDouble(2);
            }

            double taxamt = 0, amount, total;
            if (ttypel.getSelectedItem().equals("Inclusive of Tax")) {
                double devide = 100 + taxp;
                devide = prate * (100 / devide);
                taxamt = prate - devide;
                prate = prate - taxamt;
                amount = prate * quan;
                taxamt = taxamt * quan;
                total = amount + taxamt;
            } else if (ttypel.getSelectedItem().equals("Exclusive of Tax")) {
                amount = prate * quan;
                taxamt = (taxp * amount) / 100;
                total = amount + taxamt;
            } else {
                amount = prate * quan;
                total = amount;
            }

            s2.addRow(new Object[]{ino, iname, mrp, minstock, stock, quan, prate, amount, taxp, taxamt, total});
            apply_all_changes();
            fields_clear();
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void apply_all_changes() {
        try {
            double nsub = 0, ntax = 0, nquan = 0;
            int items = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                double mrp = Double.parseDouble(jTable1.getValueAt(i, 2).toString());
                double quan = Double.parseDouble(jTable1.getValueAt(i, 5).toString());
                double prate = Double.parseDouble(jTable1.getValueAt(i, 6).toString());
                double taxp = Double.parseDouble(jTable1.getValueAt(i, 8).toString());

                double amount = prate * quan;
                double total, taxamt;
                if (ttypel.getSelectedItem().equals("No Tax")) {
                    total = amount;
                    taxamt = 0;
                } else {
                    taxamt = (taxp * amount) / 100;
                    total = amount + taxamt;
                }

                String mrp2 = String.format("%." + hmany + "f", mrp);
                String prate2 = String.format("%." + hmany + "f", prate);
                String amount2 = String.format("%." + hmany + "f", amount);
                String taxamt2 = String.format("%." + hmany + "f", taxamt);
                String total2 = String.format("%." + hmany + "f", total);

                jTable1.setValueAt(mrp2, i, 2);
                jTable1.setValueAt(prate2, i, 6);
                jTable1.setValueAt(amount2, i, 7);
                jTable1.setValueAt(taxamt2, i, 9);
                jTable1.setValueAt(total2, i, 10);

                nquan = nquan + quan;
                nsub = nsub + amount;
                ntax = ntax + taxamt;
                items = items + 1;
            }//table row counts ends
            String nsub2 = String.format("%." + hmany + "f", nsub);
            String ntax2 = String.format("%." + hmany + "f", ntax);
            h17.setText("" + items);
            h18.setText("" + nquan);
            h19.setText("" + nsub2);
            h20.setText("" + ntax2);
            final_calculate();
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    void final_calculate() {
        try {
            double sub = Double.parseDouble(h19.getText());
            double taxamt = Double.parseDouble(h20.getText());
            double gt = sub + taxamt;
            String grant = String.format("%." + hmany + "f", gt);
            //round off starts
            String[] grant1 = grant.split("\\.");
            String grant2 = grant1[0];
            String grant3 = grant1[1];

            if (grant3.length() > 2) {
                grant3 = grant3.substring(0, 2);
            }
            if (grant3.length() == 1) {
                grant3 = grant3 + "0";
            }

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
            //round off ends
            h21.setText("" + round2);
            netl.setText(rup + ".00");
            h22.setText(rup + ".00");
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
            h7.requestFocus();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void save() {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found to Save!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (h3.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Supplier Name ?", "Supplier Name", JOptionPane.ERROR_MESSAGE);
                h3.requestFocus();
                return;
            }
            if (h23.getText().equals("")) {
                h23.setText(".");
            }
            if (h24.getText().equals("")) {
                h24.setText(".");
            }
            if (h25.getText().equals("")) {
                h25.setText(".");
            }
            if (h26.getText().equals("")) {
                h26.setText(".");
            }

            apply_all_changes();

            if (selvagates == false) {
                get_grn_no();
            }
            boolean selva = false;
            String query = "select distinct grn from po_entry where grn='" + h1.getText() + "'";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                JOptionPane.showMessageDialog(this, "Entry Already Exist!\nUse 'Alter' Option to Alter...", "Already Exist", JOptionPane.ERROR_MESSAGE);
                h3.requestFocus();
                return;
            }
            boolean quan_check = false;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                double quan = Double.parseDouble(jTable1.getValueAt(i, 5).toString());
                if (quan <= 0) {
                    quan_check = true;
                }
            }
            if (quan_check == true) {
                JOptionPane.showMessageDialog(this, "Invalid Qty, Check Entries...", "Invalid Qty", JOptionPane.ERROR_MESSAGE);
                return;
            }
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

            String grn = h1.getText();
            String cname = h3.getText();
            String ttype = ttypel.getSelectedItem().toString();
            ArrayList query_batch = new ArrayList();

            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String ino = jTable1.getValueAt(i, 0).toString();
                String iname = jTable1.getValueAt(i, 1).toString();
                String mrp = jTable1.getValueAt(i, 2).toString();
                String minstock = jTable1.getValueAt(i, 3).toString();
                String stock = jTable1.getValueAt(i, 4).toString();
                String quan = jTable1.getValueAt(i, 5).toString();
                String price = jTable1.getValueAt(i, 6).toString();
                String amount = jTable1.getValueAt(i, 7).toString();
                String taxp = jTable1.getValueAt(i, 8).toString();
                String taxamt1 = jTable1.getValueAt(i, 9).toString();
                String total = jTable1.getValueAt(i, 10).toString();

                query_batch.add("insert into po_items values ('" + grn + "','" + date + "','" + cname + "','" + ino + "','" + iname + "','" + mrp + "','" + minstock + "','" + stock + "','" + quan + "','" + price + "','" + amount + "','" + taxp + "','" + taxamt1 + "','" + total + "','" + ttype + "')");
            }

            String items = h17.getText();
            String quans = h18.getText();
            String sub = h19.getText();
            String taxamt = h20.getText();
            String round = h21.getText();
            String net = h22.getText();
            String status = "Not Approved", pstatus = "Ordered";
            String rdate = date;

            String term1 = h23.getText();
            String term2 = h24.getText();
            String term3 = h25.getText();
            String term4 = h26.getText();
            query_batch.add("insert into po_entry values ('" + grn + "','" + date + "','" + cname + "','" + items + "','" + quans + "','" + sub + "','" + taxamt + "','" + round + "','" + net + "','" + status + "','" + pstatus + "','" + username + "','" + last + "','" + ttype + "','" + rdate + "','" + term1 + "','" + term2 + "','" + term3 + "','" + term4 + "')");

            int count = util.doManipulation_Batch(query_batch);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>PO Number: " + h1.getText() + "</h1></html>", "Saved Successfully", JOptionPane.PLAIN_MESSAGE);
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
            h1.setText("--");
            h3.setText("");
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
            netl.setText("");

            if (s2.getRowCount() > 0) {
                s2.getDataVector().removeAllElements();
                s2.fireTableDataChanged();
            }

            savebutton.setVisible(true);
            viewbutton.setVisible(true);
            deletebutton.setVisible(false);
            alterbutton.setVisible(false);
            h3.requestFocus();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void view(String grn) {
        try {
            String query = "select distinct grn from po_entry where grn='" + grn + "'";
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
                selvagates = true;
                if (s2.getRowCount() > 0) {
                    s2.getDataVector().removeAllElements();
                    s2.fireTableDataChanged();
                }

                query = "select grn,date_format(dat,'%d/%m/%Y'),cname,items,quans,sub,taxamt,round,net,ttype,term1,term2,term3,term4 from po_entry where grn='" + grn + "'";
                set1 = util.doQuery(query);
                while (set1.next()) {
                    h1.setText(set1.getString(1));
                    h2.setText(set1.getString(2));
                    h3.setText(set1.getString(3));
                    h17.setText(set1.getString(4));
                    h18.setText(set1.getString(5));
                    String sub = String.format("%." + hmany + "f", set1.getDouble(6));
                    String taxamt = String.format("%." + hmany + "f", set1.getDouble(7));
                    String round = String.format("%." + hmany + "f", set1.getDouble(8));
                    String net = String.format("%." + hmany + "f", set1.getDouble(9));
                    h19.setText(sub);
                    h20.setText(taxamt);
                    h21.setText(round);
                    h22.setText(net);
                    netl.setText(net);
                    ttypel.setSelectedItem(set1.getString(10));
                    h23.setText(set1.getString(11));
                    h24.setText(set1.getString(12));
                    h25.setText(set1.getString(13));
                    h26.setText(set1.getString(14));
                }
                query = "select ino,iname,mrp,minstock,stock,quan,price,amount,taxp,taxamt,total from po_items where grn='" + grn + "'";
                set1 = util.doQuery(query);
                while (set1.next()) {
                    String mrp2 = String.format("%." + hmany + "f", set1.getDouble(3));
                    String price2 = String.format("%." + hmany + "f", set1.getDouble(7));
                    String amount2 = String.format("%." + hmany + "f", set1.getDouble(8));
                    String taxamt2 = String.format("%." + hmany + "f", set1.getDouble(10));
                    String total2 = String.format("%." + hmany + "f", set1.getDouble(11));
                    s2.addRow(new Object[]{set1.getString(1), set1.getString(2), mrp2, set1.getString(4), set1.getString(5), set1.getString(6), price2, amount2, set1.getString(9), taxamt2, total2});
                }
            }//if selva true ends
        } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
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
            if (user_type.equalsIgnoreCase("User")) {
                JOptionPane.showMessageDialog(this, "Login as 'Administrator' to Delete!", "Permission Restricted", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int as = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Delete ?</h1></html>", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            ArrayList query_batch = new ArrayList();
            String grn = h1.getText();
            query_batch.add("delete from po_entry where grn='" + grn + "'");
            query_batch.add("delete from po_items where grn='" + grn + "'");
            int count = util.doManipulation_Batch(query_batch);
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

    void alter_po_entry() {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found to Alter!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selvagates == false) {
                JOptionPane.showMessageDialog(this, "User 'View' Option Before Alter!", "User 'View' Option", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (user_type.equalsIgnoreCase("User")) {
                JOptionPane.showMessageDialog(this, "Login as 'Administrator' to Alter!", "Permission Restricted", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int as = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Alter ?</h1></html>", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }

            String grn = h1.getText();
            ArrayList query_batch = new ArrayList();
            query_batch.add("delete from po_entry where grn='" + grn + "'");
            query_batch.add("delete from po_items where grn='" + grn + "'");
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
            s4.addColumn("Item Name");
            s4.addColumn("Category");
            s4.addColumn("Manufacturer");
            jTable3.setModel(s4);
            jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(317);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(220);
            jTable3.getColumnModel().getColumn(3).setPreferredWidth(270);
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable3.getTableHeader().setFont(new Font(Ta, Bold, size));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public purchase_order(DataUtil util) {
        initComponents();
        this.setTitle("Purchase Register");
        this.setSize(1258, 650);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
        this.util = util;
        button_short();
        load_items_table();
        h3.requestFocus();
        load_supplier_table();
        load_iname_table();
        alterbutton.setVisible(false);
        deletebutton.setVisible(false);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
        closebutton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        alterbutton = new javax.swing.JButton();
        h2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        h1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        h3 = new javax.swing.JTextField();
        h8 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        h10 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        h9 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        h7 = new javax.swing.JTextField();
        h11 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        h18 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        h17 = new javax.swing.JTextField();
        h20 = new javax.swing.JTextField();
        h19 = new javax.swing.JTextField();
        h21 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        h22 = new javax.swing.JTextField();
        clearbutton = new javax.swing.JButton();
        nextbutton = new javax.swing.JButton();
        prebutton = new javax.swing.JButton();
        removebutton = new javax.swing.JButton();
        applybutton = new javax.swing.JButton();
        deletebutton = new javax.swing.JButton();
        netl = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        ttypel = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        h26 = new javax.swing.JTextField();
        h25 = new javax.swing.JTextField();
        h23 = new javax.swing.JTextField();
        h24 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();

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
        jScrollPane3.setBounds(0, 0, 910, 350);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete22.png"))); // NOI18N
        jButton4.setMnemonic('o');
        jButton4.setText("Close");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        iname_list.getContentPane().add(jButton4);
        jButton4.setBounds(770, 350, 140, 30);

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Purchase Order");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 310, 30);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText(" PO Date");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(260, 40, 70, 30);

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
        savebutton.setBounds(10, 560, 160, 50);

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
        jLabel11.setBounds(820, 450, 80, 30);

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
        alterbutton.setBounds(10, 560, 160, 50);

        h2.setEditable(false);
        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(330, 40, 160, 30);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("PO No");
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
        h1.setBounds(80, 40, 180, 30);

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
        h3.setBounds(80, 70, 410, 30);

        h8.setEditable(false);
        h8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h8);
        h8.setBounds(260, 130, 530, 30);

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
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 170, 1220, 280);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Qty");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(1100, 130, 30, 30);

        h10.setEditable(false);
        h10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h10);
        h10.setBounds(1040, 130, 50, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText(" Tax %");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(990, 130, 60, 30);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText(" Purchase Price");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(790, 130, 100, 30);

        h9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h9ActionPerformed(evt);
            }
        });
        getContentPane().add(h9);
        h9.setBounds(890, 130, 100, 30);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Item Code");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(10, 130, 80, 30);

        h7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h7ActionPerformed(evt);
            }
        });
        h7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h7KeyPressed(evt);
            }
        });
        getContentPane().add(h7);
        h7.setBounds(80, 130, 180, 30);

        h11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h11ActionPerformed(evt);
            }
        });
        getContentPane().add(h11);
        h11.setBounds(1130, 130, 100, 30);

        jLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel20.setText(" Total Qty");
        getContentPane().add(jLabel20);
        jLabel20.setBounds(210, 450, 70, 30);

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setText(" Sub Total");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(400, 450, 70, 30);

        h18.setEditable(false);
        h18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h18);
        h18.setBounds(280, 450, 120, 30);

        jLabel24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel24.setText("  Net Value");
        getContentPane().add(jLabel24);
        jLabel24.setBounds(990, 450, 70, 30);

        h17.setEditable(false);
        h17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h17);
        h17.setBounds(90, 450, 120, 30);

        h20.setEditable(false);
        h20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h20);
        h20.setBounds(690, 450, 130, 30);

        h19.setEditable(false);
        h19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h19);
        h19.setBounds(470, 450, 130, 30);

        h21.setEditable(false);
        h21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h21);
        h21.setBounds(900, 450, 90, 30);

        jLabel29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel29.setText(" Tax Amount");
        getContentPane().add(jLabel29);
        jLabel29.setBounds(600, 450, 90, 30);

        h22.setEditable(false);
        h22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h22);
        h22.setBounds(1060, 450, 170, 30);

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
        removebutton.setBounds(170, 560, 150, 50);

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
        applybutton.setBounds(320, 560, 160, 50);

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
        deletebutton.setBounds(780, 560, 150, 50);

        netl.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        netl.setForeground(new java.awt.Color(255, 0, 0));
        netl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(netl);
        netl.setBounds(930, 10, 300, 50);

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search22.png"))); // NOI18N
        jButton1.setText("Load Items");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(790, 70, 140, 30);

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/select22.png"))); // NOI18N
        jButton2.setText("Auto Generate");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(930, 70, 160, 30);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText(" Tax Type");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(490, 70, 80, 30);

        ttypel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ttypel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Exclusive of Tax", "Inclusive of Tax", "No Tax" }));
        getContentPane().add(ttypel);
        ttypel.setBounds(560, 70, 230, 30);

        jLabel25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel25.setText("Total Items");
        getContentPane().add(jLabel25);
        jLabel25.setBounds(10, 450, 80, 30);

        h26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h26ActionPerformed(evt);
            }
        });
        getContentPane().add(h26);
        h26.setBounds(690, 520, 540, 30);

        h25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h25ActionPerformed(evt);
            }
        });
        getContentPane().add(h25);
        h25.setBounds(140, 520, 550, 30);

        h23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h23ActionPerformed(evt);
            }
        });
        getContentPane().add(h23);
        h23.setBounds(140, 490, 550, 30);

        h24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h24ActionPerformed(evt);
            }
        });
        getContentPane().add(h24);
        h24.setBounds(690, 490, 540, 30);

        jLabel26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel26.setText("Terms & Conditions");
        getContentPane().add(jLabel26);
        jLabel26.setBounds(10, 490, 130, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_closebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        save();

    }//GEN-LAST:event_savebuttonActionPerformed

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewbuttonActionPerformed
        String grn = JOptionPane.showInputDialog(this, "Enter GRN No ?", "GRN No", JOptionPane.PLAIN_MESSAGE);
        if ("".equals(grn) || grn == null) {
            JOptionPane.showMessageDialog(this, "Invalid GRN No!", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        view(grn);

    }//GEN-LAST:event_viewbuttonActionPerformed

    private void alterbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alterbuttonActionPerformed
        alter_po_entry();
    }//GEN-LAST:event_alterbuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        form_clear();

    }//GEN-LAST:event_clearbuttonActionPerformed

    private void nextbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextbuttonActionPerformed
        try {

            String grn = h1.getText();
            if (grn.equalsIgnoreCase("--")) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String query = "select distinct grn from po_entry where grn > '" + grn + "' order by grn limit 1";
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
                query = "select max(grn) from po_entry";
            } else {
                query = "select distinct grn from po_entry where grn < '" + grn + "' order by grn desc limit 1";
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

    }//GEN-LAST:event_removebuttonActionPerformed

    private void applybuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applybuttonActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            return;
        }
        apply_all_changes();
    }//GEN-LAST:event_applybuttonActionPerformed

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebuttonActionPerformed
        delete();
    }//GEN-LAST:event_deletebuttonActionPerformed

    private void h7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h7ActionPerformed
        Object obj = h7.getText();
        if (obj != null || obj != "") {
            get_item_details_using_itemcode();
        }

        if (h7.getText().equals("") && s2.getRowCount() > 0) {
            h23.requestFocus();
        } else {
            h9.requestFocus();
        }

    }//GEN-LAST:event_h7ActionPerformed

    private void h11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h11ActionPerformed
        try {
            if (h7.getText().equals("") || h8.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Item Details ?", "Item Details", JOptionPane.ERROR_MESSAGE);
                h7.requestFocus();
                return;
            }
            if (h9.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Purchase Price ?", "Purchase Price", JOptionPane.ERROR_MESSAGE);
                h9.requestFocus();
                return;
            }
            if (h10.getText().equals("")) {
                h10.setText("" + 0);
            }
            if (h11.getText().equals("")) {
                h11.setText("" + 1);
            }
            int taxp = Integer.parseInt(h10.getText());
            if (taxp != 0 && taxp != 5 && taxp != 12 && taxp != 18 && taxp != 28) {
                JOptionPane.showMessageDialog(this, "<html><h4>Allowed: 0%, 5%, 12%, 18%, 28%</h4></html>", "Invalid GST", JOptionPane.ERROR_MESSAGE);
                h7.requestFocus();
                return;
            }
            add_item();
        } catch (HeadlessException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }

    }//GEN-LAST:event_h11ActionPerformed

    private void h9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h9ActionPerformed
        h11.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_h9ActionPerformed

    private void h1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h1FocusGained
        h3.requestFocus();
    }//GEN-LAST:event_h1FocusGained

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        cname_list.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void h3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_h3KeyPressed
        cname_list.requestFocus();
        jTable2.requestFocus();
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                h7.requestFocus();
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
                    String query = "select distinct cname,city from vendor where cname like '" + h3.getText() + "%' order by cname limit 300";
                    ResultSet r = util.doQuery(query);
                    while (r.next()) {
                        s3.addRow(new Object[]{r.getString(1), r.getString(2)});
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                break;
        }

    }//GEN-LAST:event_h3KeyPressed

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable2.getRowCount() > 0) {
                h3.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
            }
            h7.requestFocus();
            cname_list.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cname_list.dispose();
            h3.requestFocus();
        }

    }//GEN-LAST:event_jTable2KeyPressed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

        if (jTable2.getRowCount() > 0) {
            h3.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
        }
        h7.requestFocus();
        cname_list.dispose();
    }//GEN-LAST:event_jTable2MouseClicked

    private void jScrollPane2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane2FocusLost
        cname_list.dispose();
    }//GEN-LAST:event_jScrollPane2FocusLost

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        if (jTable3.getRowCount() > 0) {
            h7.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
            h8.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 1).toString());
        }
        get_item_details_using_itemcode();
        h9.requestFocus();
        iname_list.dispose();
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTable3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable3KeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable3.getRowCount() > 0) {
                h7.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
                h8.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 1).toString());
            }
            get_item_details_using_itemcode();
            h9.requestFocus();
            iname_list.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            iname_list.dispose();
            h7.requestFocus();
        }
    }//GEN-LAST:event_jTable3KeyPressed

    private void jScrollPane3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane3FocusLost
        iname_list.dispose();
    }//GEN-LAST:event_jScrollPane3FocusLost

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        iname_list.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void h7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_h7KeyPressed

        iname_list.requestFocus();
        jTable3.requestFocus();
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                h7.requestFocus();
                iname_list.dispose();
                break;
            case KeyEvent.VK_DOWN:
                if (s4.getRowCount() > 0) {
                    s4.getDataVector().removeAllElements();
                    s4.fireTableDataChanged();
                }
                try {
                    iname_list.requestFocus();
                    Point l = h7.getLocationOnScreen();
                    iname_list.setLocation(l.x, l.y + h7.getHeight());
                    iname_list.setSize(916, 382);
                    iname_list.setVisible(true);
                    String query = "select distinct ino,iname,cat,manu from item where iname like '" + h7.getText() + "%' order by ino limit 500";
                    ResultSet r = util.doQuery(query);
                    while (r.next()) {
                        s4.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), r.getString(4)});
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                break;
        }

    }//GEN-LAST:event_h7KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        add_items_using_supplier();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            apply_all_changes();
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int as = JOptionPane.showConfirmDialog(this, "Want to Auto Fill Qty ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
        if (as == JOptionPane.NO_OPTION) {
            return;
        }
        load_auto_genrate();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void h23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h23ActionPerformed
        h24.requestFocus();
    }//GEN-LAST:event_h23ActionPerformed

    private void h24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h24ActionPerformed
        h25.requestFocus();
    }//GEN-LAST:event_h24ActionPerformed

    private void h25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h25ActionPerformed
        h26.requestFocus();
    }//GEN-LAST:event_h25ActionPerformed

    private void h26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h26ActionPerformed
        save();
    }//GEN-LAST:event_h26ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton alterbutton;
    private javax.swing.JButton applybutton;
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JDialog cname_list;
    private javax.swing.JButton deletebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h10;
    private javax.swing.JTextField h11;
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
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h7;
    private javax.swing.JTextField h8;
    private javax.swing.JTextField h9;
    private javax.swing.JDialog iname_list;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JLabel netl;
    private javax.swing.JButton nextbutton;
    private javax.swing.JButton prebutton;
    private javax.swing.JButton removebutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JComboBox<String> ttypel;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
