package packingpack;

import com.selrom.db.DataUtil;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.KeyEvent;
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
import menupack.menu_form;
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public final class packing_entry extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();
    sample2 s4 = new sample2();
    sample2 s5 = new sample2();
    ResultSet r;
    String username = "", utype = "User";
    boolean selvagates = false;
    int hmany = 2;

    final void button_short() {
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        removebutton.setText("<html><b>Remove</b><br>(Alt+M)</h6><html>");
        viewbutton.setText("<html><b>View</b><br>(Alt+V)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        printbutton.setText("<html><b>Re-Print</b><br>(Alt+P)</h6><html>");
        prebutton.setText("<html><b>Last Entry</b><br>(Alt+R)</h6><html>");
        nextbutton.setText("<html><b>Next Entry</b><br>(Alt+N)</h6><html>");
        titlelablel.setText("<html><u>Packing Entry</u></html>");
        jLabel21.setText("<html><u>Main Product</u></html>");
        jLabel17.setText("<html><u>Packed Product</u></html>");
        setTitle("Packing Entry");
        this.setSize(1021, 648);
        java.net.URL iconUrl = ClassLoader.getSystemResource("/images/icon.png");
        if (iconUrl != null) {
            ImageIcon icon = new ImageIcon(iconUrl);
            this.setFrameIcon(icon);
        }
        Date d = new Date();
        SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
        h2.setText(g.format(d));
        menupack.menu_form me = new menu_form();
        username = me.getUsername();
        utype = me.getUserType();
        hmany = me.getHmany();
    }

    final void load_list_table() {
        s2.addColumn("It.Code");
        s2.addColumn("It.Name");
        s2.addColumn("Qty");
        s2.addColumn("Weight");
        s2.addColumn("Total Weight");
        s2.addColumn("Barcode");
        s2.addColumn("MRP");
        s2.addColumn("RPrice");
        s2.addColumn("WPrice");
        s2.addColumn("Entry");
        s2.addColumn("Category");
        s2.addColumn("Prate");
        s2.addColumn("Stock_Type");
        s2.addColumn("Print Name");
        jTable1.setModel(s2);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(450);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(124);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(120);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(10).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(11).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(12).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(13).setPreferredWidth(200);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.getTableHeader().setResizingAllowed(false);
    }

    void load_iname_table() {
        try {
            s4.addColumn("It.Code");
            s4.addColumn("Barcode");
            s4.addColumn("Item Name");
            s4.addColumn("Category");
            jTable3.setModel(s4);
            jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(314);
            jTable3.getColumnModel().getColumn(3).setPreferredWidth(270);
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable3.getTableHeader().setFont(new Font(Ta, Bold, size));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void load_iname_table1() {
        try {
            s5.addColumn("It.Code");
            s5.addColumn("Barcode");
            s5.addColumn("Item Name");
            s5.addColumn("Category");
            jTable4.setModel(s5);
            jTable4.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable4.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable4.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable4.getColumnModel().getColumn(2).setPreferredWidth(314);
            jTable4.getColumnModel().getColumn(3).setPreferredWidth(270);
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable4.getTableHeader().setFont(new Font(Ta, Bold, size));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void get_item_details_using_item_no() {
        try {
            String query = "select ino,iname from item where ino='" + h4.getText() + "' order by ino limit 1";
            r = util.doQuery(query);
            while (r.next()) {
                h4.setText(r.getString(1));
                h5.setText(r.getString(2));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_item_details_using_item_no1() {
        try {
            String query = "select ino,iname from item where ino='" + h9.getText() + "' order by ino limit 1";
            r = util.doQuery(query);
            while (r.next()) {
                h9.setText(r.getString(1));
                h10.setText(r.getString(2));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void add_item() {
        try {
            if (h9.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Item Code ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h9.requestFocus();
                return;
            }
            if (h10.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Item Code ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h9.requestFocus();
                return;
            }
            if (h11.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Qty ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h11.requestFocus();
                return;
            }
            if (h13.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Weight ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h13.requestFocus();
                return;
            }
            String entry = "stock", stock_type = "new_stock";
            String query = "select entry from stock where ino='" + h9.getText() + "' order by ino limit 1";
            r = util.doQuery(query);
            while (r.next()) {
                entry = r.getString(1);
                stock_type = "old_stock";
            }
            String cat = ".", mrp = "" + 0, rprice = "" + 0, wprice = "" + 0, prate = "" + 0, barcode = ".",
                    iname1 = "";
            query = "select cat,mrp,rprice,wprice,prate,barcode,iname1 from item where ino='" + h9.getText()
                    + "' order by ino limit 1";
            r = util.doQuery(query);
            while (r.next()) {
                cat = r.getString(1);
                mrp = r.getString(2);
                rprice = r.getString(3);
                wprice = r.getString(4);
                prate = r.getString(5);
                barcode = r.getString(6);
                iname1 = r.getString(7);
            }
            int quan = Integer.parseInt(h11.getText());
            double weight = Double.parseDouble(h13.getText());
            double tweight = quan * weight;

            String weight1 = String.format("%.3f", weight);
            String tweight1 = String.format("%.3f", tweight);

            s2.addRow(new Object[] { h9.getText(), h10.getText(), quan, weight1, tweight1, barcode, mrp, rprice, wprice,
                    entry, cat, prate, stock_type, iname1 });
            clear_filds();
            calculate();
            h9.requestFocus();
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void clear_filds() {
        h9.setText("");
        h10.setText("");
        h11.setText("");
        h13.setText("");

    }

    void calculate() {
        h7.setText(jTable1.getRowCount() + "");
        double nquan = 0, nweight = 0;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            nquan = nquan + Double.parseDouble(jTable1.getValueAt(i, 2).toString());
            nweight = nweight + Double.parseDouble(jTable1.getValueAt(i, 4).toString());
        }
        h8.setText("" + nquan);
        String weight = String.format("%.3f", nweight);
        h14.setText("" + weight);
    }

    void get_sno() {
        try {
            int sno = 1;
            String query = "select max(sno) from packing";
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
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found to Save!", "No Records",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int aa = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (aa == JOptionPane.NO_OPTION) {
                return;
            }
            if (h6.getText().equals("")) {
                h6.setText("" + 1);
            }
            if (h12.getText().equals("")) {
                h12.setText("" + 0);
            }

            get_sno();
            String sno = h1.getText();
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText());
            String date = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));

            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);
            String items = h7.getText();
            String quans = h8.getText();
            double weight = Double.parseDouble(h14.getText());
            String ino1 = h4.getText();
            String iname1 = h5.getText();
            double quan1 = Double.parseDouble(h6.getText());
            double weight1 = Double.parseDouble(h12.getText());

            boolean selva = false;
            String query = "select sno from packing where sno='" + h1.getText() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                JOptionPane.showMessageDialog(this, "Packing Entry Already Exist!", "Already Exist",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            double stock = 0;
            query = "select quan from stock where ino='" + ino1 + "'";
            r = util.doQuery(query);
            while (r.next()) {
                stock = r.getDouble(1);
            }
            if (quan1 > stock) {
                JOptionPane.showMessageDialog(this, "Stock-in-Hand: " + stock, "Invalid Stock",
                        JOptionPane.ERROR_MESSAGE);
                h6.requestFocus();
                return;
            }
            if (weight != weight1) {
                JOptionPane.showMessageDialog(this, "Source Weight & Packed Weight Not Matchning!", "Invalid",
                        JOptionPane.ERROR_MESSAGE);
                h12.requestFocus();
                return;
            }

            ArrayList query_list = new ArrayList();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String ino = jTable1.getValueAt(i, 0).toString();
                String iname = jTable1.getValueAt(i, 1).toString();
                String quan = jTable1.getValueAt(i, 2).toString();
                String weight2 = jTable1.getValueAt(i, 3).toString();
                String tweight2 = jTable1.getValueAt(i, 4).toString();
                String barcode = jTable1.getValueAt(i, 5).toString();
                String mrp = jTable1.getValueAt(i, 6).toString();
                String rprice = jTable1.getValueAt(i, 7).toString();
                String wprice = jTable1.getValueAt(i, 8).toString();
                String entry = jTable1.getValueAt(i, 9).toString();
                String cat = jTable1.getValueAt(i, 10).toString();
                String prate = jTable1.getValueAt(i, 11).toString();
                String stock_type = jTable1.getValueAt(i, 12).toString();

                query_list.add("insert into packing_items values ('" + sno + "','" + ino + "','" + iname + "','" + quan
                        + "','" + weight2 + "','" + tweight2 + "','" + barcode + "','" + mrp + "','" + rprice + "','"
                        + wprice + "','" + entry + "','" + cat + "','" + prate + "','" + stock_type + "')");
                if (stock_type.equalsIgnoreCase("new_stock")) {
                    query_list.add("insert into stock values ('" + barcode + "','" + ino + "','" + iname + "','" + mrp
                            + "','" + rprice + "','" + wprice + "','" + prate + "','" + quan + "','" + cat + "','"
                            + entry + "')");
                } // new stock ends here
                else if (stock_type.equalsIgnoreCase("old_stock")) {
                    query_list.add("update stock set quan=quan+" + quan + " where ino='" + ino + "' and iname='" + iname
                            + "' and entry='" + entry + "' ");
                }
            }
            query_list.add(
                    "update stock set quan=quan-" + quan1 + " where ino='" + ino1 + "' and iname='" + iname1 + "' ");

            query_list.add("insert into packing values ('" + sno + "','" + date + "','" + ino1 + "','" + iname1 + "','"
                    + quan1 + "','" + weight1 + "','" + items + "','" + quans + "','" + weight + "','" + username
                    + "','" + last + "')");
            int a = util.doManipulation_Batch(query_list);
            if (a > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Saved Successfully</h1></html>", "Saved",
                        JOptionPane.PLAIN_MESSAGE);
                int bb = JOptionPane.showConfirmDialog(this, "<html><h1>You Want to Print Barcode ?</h1></html>",
                        "Barcode", JOptionPane.YES_NO_OPTION);
                if (bb == JOptionPane.YES_OPTION) {
                    print_barcode();
                }
                form_clear();
            } else {
                JOptionPane.showMessageDialog(this, "Try Again...", "Oops", JOptionPane.ERROR_MESSAGE);
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
            int as = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Delete ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            ArrayList query_batch = new ArrayList();
            String sno = h1.getText();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String ino = jTable1.getValueAt(i, 0).toString();
                String iname = jTable1.getValueAt(i, 1).toString();
                String quan = jTable1.getValueAt(i, 2).toString();
                String barcode = jTable1.getValueAt(i, 5).toString();
                String entry = jTable1.getValueAt(i, 9).toString();

                query_batch.add("update stock set quan=quan-" + quan + "  where barcode='" + barcode + "' and ino='"
                        + ino + "' and iname='" + iname + "' and entry='" + entry + "' ");
            } // jtable row counts ends

            String ino1 = h4.getText();
            String iname1 = h5.getText();
            double quan1 = Double.parseDouble(h6.getText());
            query_batch.add(
                    "update stock set quan=quan+" + quan1 + " where ino='" + ino1 + "' and iname='" + iname1 + "' ");
            query_batch.add("delete from packing where sno='" + sno + "'");
            query_batch.add("delete from packing_items where sno='" + sno + "'");
            int count = util.doManipulation_Batch(query_batch);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Deleted Successfully</h1></html>", "Deleted",
                        JOptionPane.PLAIN_MESSAGE);
                form_clear();
            } else {
                JOptionPane.showMessageDialog(this, "Check Entries and then Try Again!", "Invalid",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void view(String sno) {
        try {
            String query = "select sno from packing where sno='" + sno + "'";
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
                printbutton.setVisible(true);
                deletebutton.setVisible(true);
                selvagates = true;
                if (s2.getRowCount() > 0) {
                    s2.getDataVector().removeAllElements();
                    s2.fireTableDataChanged();
                }
                query = "select sno,date_format(dat,'%d/%m/%Y'),ino,iname,quan,items,quans,weights,weight from packing where sno='"
                        + sno + "'";
                set1 = util.doQuery(query);
                while (set1.next()) {
                    h1.setText(set1.getString(1));
                    h2.setText(set1.getString(2));
                    h4.setText(set1.getString(3));
                    h5.setText(set1.getString(4));
                    h6.setText(set1.getString(5));
                    h7.setText(set1.getString(6));
                    h8.setText(set1.getString(7));
                    String weights = String.format("%.3f", set1.getDouble(8));
                    String weight = String.format("%.3f", set1.getDouble(9));
                    h14.setText(weights);
                    h12.setText(weight);
                }
                query = "select ino,iname,quan,weight,tweight,barcode,mrp,rprice,wprice,entry,cat,prate,stock_type from packing_items where sno='"
                        + sno + "'";
                set1 = util.doQuery(query);
                while (set1.next()) {
                    String weight = String.format("%.3f", set1.getDouble(4));
                    String tweight = String.format("%.3f", set1.getDouble(5));
                    s2.addRow(new Object[] { set1.getString(1), set1.getString(2), set1.getString(3), weight, tweight,
                            set1.getString(6), set1.getString(7), set1.getString(8), set1.getString(9),
                            set1.getString(10), set1.getString(11), set1.getString(12) });
                }

                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    String ino = jTable1.getValueAt(i, 0).toString();
                    query = "select iname1 from item where ino='" + ino + "'";
                    set1 = util.doQuery(query);
                    while (set1.next()) {
                        jTable1.setValueAt(r.getString(1), i, 13);
                    }
                }

            } // if selva true ends
        } catch (HeadlessException | ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void form_clear() {
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        h1.setText("--");
        h7.setText("");
        h8.setText("");
        h4.setText("");
        h5.setText("");
        h6.setText("");
        h12.setText("");
        savebutton.setVisible(true);
        viewbutton.setVisible(true);
        printbutton.setVisible(false);
        deletebutton.setVisible(false);
    }

    void print_barcode() {
        try {
            ArrayList barcode = new ArrayList();
            ArrayList ino = new ArrayList();
            ArrayList iname = new ArrayList();
            ArrayList mrp = new ArrayList();
            ArrayList rprice = new ArrayList();
            ArrayList wprice = new ArrayList();
            ArrayList pname = new ArrayList();
            menu_form me = new menu_form();
            String drive = "";
            String folder = Utils.AppConfig.getAppPath();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String ino1 = jTable1.getValueAt(i, 0).toString();
                String iname1 = jTable1.getValueAt(i, 1).toString();
                double quan = Double.parseDouble(jTable1.getValueAt(i, 2).toString());
                String barcode1 = jTable1.getValueAt(i, 3).toString();
                double mrp1 = Double.parseDouble(jTable1.getValueAt(i, 4).toString());
                double rprice1 = Double.parseDouble(jTable1.getValueAt(i, 5).toString());
                double wprice1 = Double.parseDouble(jTable1.getValueAt(i, 6).toString());
                String iname2 = jTable1.getValueAt(i, 13).toString();
                int quan1 = (int) quan;
                String mrp2 = String.format("%." + hmany + "f", mrp1);
                String rprice2 = String.format("%." + hmany + "f", rprice1);
                String wprice2 = String.format("%." + hmany + "f", wprice1);

                for (int j = 0; j < quan1; j++) {
                    barcode.add(barcode1);
                    ino.add(ino1);
                    iname.add(iname1);
                    mrp.add("MRP: " + mrp2);
                    rprice.add("S.Price: " + rprice2);
                    wprice.add("S.Price: " + wprice2);
                    pname.add(iname2);
                }
            } // rowcounts ends

            ArrayList query_batch = new ArrayList();
            query_batch.add("delete from barcode");
            for (int i = 0; i < barcode.size(); i++) {
                query_batch.add("insert into barcode values ('" + barcode.get(i) + "','" + ino.get(i) + "','"
                        + iname.get(i) + "','" + mrp.get(i) + "','" + rprice.get(i) + "','" + wprice.get(i) + "','"
                        + pname.get(i) + "' )");
            }
            int count = util.doManipulation_Batch(query_batch);
            if (count > 0) {
                // JOptionPane.showMessageDialog(this, "Barcode Print Successfully");
                Runtime rt = Runtime.getRuntime();
                Utils.AppConfig.openFile(folder + "/Barcode_Files/Barcode_Print.btw");
            }
        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public packing_entry(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();
        load_list_table();
        load_iname_table();
        load_iname_table1();
        deletebutton.setVisible(false);
        printbutton.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        iname_list = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        iname_list1 = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        titlelablel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        viewbutton = new javax.swing.JButton();
        h1 = new javax.swing.JTextField();
        h2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        h4 = new javax.swing.JTextField();
        h5 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        h6 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        savebutton = new javax.swing.JButton();
        removebutton = new javax.swing.JButton();
        h7 = new javax.swing.JTextField();
        h8 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        h9 = new javax.swing.JTextField();
        h10 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        h11 = new javax.swing.JTextField();
        deletebutton = new javax.swing.JButton();
        printbutton = new javax.swing.JButton();
        prebutton = new javax.swing.JButton();
        nextbutton = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        h12 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        h13 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        h14 = new javax.swing.JTextField();
        whatl = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();

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
        jScrollPane3.setBounds(0, 0, 840, 400);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete22.png"))); // NOI18N
        jButton4.setMnemonic('o');
        jButton4.setText("Close");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        iname_list.getContentPane().add(jButton4);
        jButton4.setBounds(700, 400, 140, 30);

        iname_list1.setUndecorated(true);
        iname_list1.getContentPane().setLayout(null);

        jScrollPane4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jScrollPane4FocusLost(evt);
            }
        });

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

        iname_list1.getContentPane().add(jScrollPane4);
        jScrollPane4.setBounds(0, 0, 840, 400);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete22.png"))); // NOI18N
        jButton5.setMnemonic('o');
        jButton5.setText("Close");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        iname_list1.getContentPane().add(jButton5);
        jButton5.setBounds(700, 400, 140, 30);

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Packing Entry");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 400, 30);

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable1FocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 242, 1000, 260);

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
        clearbutton.setBounds(750, 560, 130, 50);

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
        viewbutton.setBounds(450, 560, 150, 50);

        h1.setEditable(false);
        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setText("--");
        h1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h1FocusGained(evt);
            }
        });
        getContentPane().add(h1);
        h1.setBounds(70, 40, 160, 30);

        h2.setEditable(false);
        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(310, 40, 120, 30);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Entry No");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(10, 40, 60, 30);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText(" Qty");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(700, 200, 30, 30);

        h4.setBackground(new java.awt.Color(255, 255, 204));
        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h4ActionPerformed(evt);
            }
        });
        h4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h4KeyPressed(evt);
            }
        });
        getContentPane().add(h4);
        h4.setBounds(70, 120, 160, 30);

        h5.setEditable(false);
        h5.setBackground(new java.awt.Color(255, 255, 204));
        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h5);
        h5.setBounds(230, 120, 470, 30);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText(" Qty");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(200, 510, 40, 30);

        h6.setBackground(new java.awt.Color(255, 255, 204));
        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h6ActionPerformed(evt);
            }
        });
        h6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h6KeyPressed(evt);
            }
        });
        getContentPane().add(h6);
        h6.setBounds(730, 120, 90, 30);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText(" Entry Date");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(230, 40, 80, 30);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("Packed Product");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(10, 170, 110, 30);

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("Net Weight");
        getContentPane().add(jLabel18);
        jLabel18.setBounds(360, 510, 80, 30);

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
        savebutton.setBounds(0, 560, 150, 50);

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
        removebutton.setBounds(600, 560, 150, 50);

        h7.setEditable(false);
        h7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h7);
        h7.setBounds(80, 510, 120, 30);

        h8.setEditable(false);
        h8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h8);
        h8.setBounds(230, 510, 120, 30);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Entry No");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(10, 40, 60, 30);

        jLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel20.setText("It.Code");
        getContentPane().add(jLabel20);
        jLabel20.setBounds(10, 200, 60, 30);

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setText("Main Product");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(10, 90, 110, 30);

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText("It.Code");
        getContentPane().add(jLabel22);
        jLabel22.setBounds(10, 120, 60, 30);

        h9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h9ActionPerformed(evt);
            }
        });
        h9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h9KeyPressed(evt);
            }
        });
        getContentPane().add(h9);
        h9.setBounds(70, 200, 160, 30);

        h10.setEditable(false);
        h10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h10);
        h10.setBounds(230, 200, 470, 30);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText(" Weight");
        getContentPane().add(jLabel19);
        jLabel19.setBounds(820, 200, 50, 30);

        h11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h11ActionPerformed(evt);
            }
        });
        h11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h11KeyPressed(evt);
            }
        });
        getContentPane().add(h11);
        h11.setBounds(730, 200, 90, 30);

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
        deletebutton.setBounds(450, 560, 150, 50);

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
        printbutton.setBounds(0, 560, 150, 50);

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
        prebutton.setBounds(150, 560, 150, 50);

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
        nextbutton.setBounds(300, 560, 150, 50);

        jLabel23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel23.setText(" Qty");
        getContentPane().add(jLabel23);
        jLabel23.setBounds(700, 120, 30, 30);

        h12.setBackground(new java.awt.Color(255, 255, 204));
        h12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h12ActionPerformed(evt);
            }
        });
        h12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h12KeyPressed(evt);
            }
        });
        getContentPane().add(h12);
        h12.setBounds(870, 120, 130, 30);

        jLabel24.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel24.setText("Weighing Scal Value:");
        getContentPane().add(jLabel24);
        jLabel24.setBounds(740, 0, 180, 30);

        h13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h13ActionPerformed(evt);
            }
        });
        h13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                h13KeyPressed(evt);
            }
        });
        getContentPane().add(h13);
        h13.setBounds(870, 200, 130, 30);

        jLabel25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel25.setText(" Total Items");
        getContentPane().add(jLabel25);
        jLabel25.setBounds(0, 510, 80, 30);

        h14.setEditable(false);
        h14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h14);
        h14.setBounds(440, 510, 130, 30);

        whatl.setEditable(false);
        whatl.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        whatl.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        whatl.setText("0.000");
        whatl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        whatl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                whatlFocusGained(evt);
            }
        });
        getContentPane().add(whatl);
        whatl.setBounds(740, 30, 250, 50);

        jLabel26.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(102, 102, 102));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Press 'Down' Key to Read Weight from Scal");
        getContentPane().add(jLabel26);
        jLabel26.setBounds(690, 150, 310, 30);

        jLabel27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel27.setText(" Weight");
        getContentPane().add(jLabel27);
        jLabel27.setBounds(820, 120, 50, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable1MouseClicked

    }// GEN-LAST:event_jTable1MouseClicked

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbuttonActionPerformed
        form_clear();

    }// GEN-LAST:event_clearbuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }// GEN-LAST:event_closebuttonActionPerformed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jTable1FocusGained

    }// GEN-LAST:event_jTable1FocusGained

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_viewbuttonActionPerformed
        String grn = JOptionPane.showInputDialog(this, "Enter Entry No ?", "EntryNo", JOptionPane.PLAIN_MESSAGE);
        if ("".equals(grn) || grn == null) {
            JOptionPane.showMessageDialog(this, "Invalid Entry No!", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        view(grn);
    }// GEN-LAST:event_viewbuttonActionPerformed

    private void h1FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h1FocusGained
        h4.requestFocus();
    }// GEN-LAST:event_h1FocusGained

    private void h4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h4ActionPerformed
        if (h4.getText().equals("")) {
            h9.requestFocus();
        } else {
            if (h5.getText().equals("")) {
                get_item_details_using_item_no();
            }
            h6.requestFocus();
        }
    }// GEN-LAST:event_h4ActionPerformed

    private void h4KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_h4KeyPressed

        iname_list.requestFocus();
        jTable3.requestFocus();
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                h4.requestFocus();
                iname_list.dispose();
                break;
            case KeyEvent.VK_DOWN:
                if (s4.getRowCount() > 0) {
                    s4.getDataVector().removeAllElements();
                    s4.fireTableDataChanged();
                }
                try {
                    iname_list.requestFocus();
                    Point l = jLabel21.getLocationOnScreen();
                    iname_list.setLocation(l.x, l.y + jLabel21.getHeight());
                    iname_list.setSize(840, 432);
                    iname_list.setVisible(true);
                    String query = "select ino,barcode,iname,cat from item where iname like '" + h4.getText()
                            + "%' order by ino limit 500";
                    r = util.doQuery(query);
                    while (r.next()) {
                        s4.addRow(new Object[] { r.getString(1), r.getString(2), r.getString(3), r.getString(4) });
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                break;
        }

    }// GEN-LAST:event_h4KeyPressed

    private void h6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h6ActionPerformed
        h12.requestFocus();
    }// GEN-LAST:event_h6ActionPerformed

    private void h6KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_h6KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_h6KeyPressed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable3MouseClicked
        if (jTable3.getRowCount() > 0) {
            h4.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
        }
        get_item_details_using_item_no();
        h6.requestFocus();
        iname_list.dispose();
    }// GEN-LAST:event_jTable3MouseClicked

    private void jTable3KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTable3KeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable3.getRowCount() > 0) {
                h4.setText(jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString());
            }
            get_item_details_using_item_no();
            h6.requestFocus();
            iname_list.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            iname_list.dispose();
            h4.requestFocus();
        }
    }// GEN-LAST:event_jTable3KeyPressed

    private void jScrollPane3FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jScrollPane3FocusLost
        iname_list.dispose();
    }// GEN-LAST:event_jScrollPane3FocusLost

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
        iname_list.dispose();
    }// GEN-LAST:event_jButton4ActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_savebuttonActionPerformed
        save();
    }// GEN-LAST:event_savebuttonActionPerformed

    private void removebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_removebuttonActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int[] row_indexes = jTable1.getSelectedRows();
        for (int i = 0; i < row_indexes.length; i++) {
            s2.removeRow(jTable1.getSelectedRow());
        }
        calculate();

    }// GEN-LAST:event_removebuttonActionPerformed

    private void h9ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h9ActionPerformed
        if (h9.getText().equals("") & s2.getRowCount() > 0) {
            savebutton.requestFocus();
        } else {
            if (h10.getText().equals("")) {
                get_item_details_using_item_no1();
            }
            h11.requestFocus();
        }
        // TODO add your handling code here:
    }// GEN-LAST:event_h9ActionPerformed

    private void h9KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_h9KeyPressed
        iname_list1.requestFocus();
        jTable4.requestFocus();
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                h9.requestFocus();
                iname_list1.dispose();
                break;
            case KeyEvent.VK_DOWN:
                if (s5.getRowCount() > 0) {
                    s5.getDataVector().removeAllElements();
                    s5.fireTableDataChanged();
                }
                try {
                    iname_list1.requestFocus();
                    Point l = jLabel20.getLocationOnScreen();
                    iname_list1.setLocation(l.x, l.y + jLabel20.getHeight());
                    iname_list1.setSize(840, 432);
                    iname_list1.setVisible(true);
                    String query = "select ino,barcode,iname,cat from item where iname like '" + h9.getText()
                            + "%' order by ino limit 500";
                    r = util.doQuery(query);
                    while (r.next()) {
                        s5.addRow(new Object[] { r.getString(1), r.getString(2), r.getString(3), r.getString(4) });
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                break;
        }
    }// GEN-LAST:event_h9KeyPressed

    private void h11ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h11ActionPerformed
        h13.requestFocus();
    }// GEN-LAST:event_h11ActionPerformed

    private void h11KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_h11KeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_h11KeyPressed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTable4MouseClicked
        if (jTable4.getRowCount() > 0) {
            h9.setText(jTable4.getValueAt(jTable4.getSelectedRow(), 0).toString());
        }
        get_item_details_using_item_no1();
        h11.requestFocus();
        iname_list1.dispose();
    }// GEN-LAST:event_jTable4MouseClicked

    private void jTable4KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTable4KeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTable4.getRowCount() > 0) {
                h9.setText(jTable4.getValueAt(jTable4.getSelectedRow(), 0).toString());
            }
            get_item_details_using_item_no1();
            h11.requestFocus();
            iname_list1.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            iname_list1.dispose();
            h9.requestFocus();
        }
    }// GEN-LAST:event_jTable4KeyPressed

    private void jScrollPane4FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jScrollPane4FocusLost
        iname_list1.dispose();
    }// GEN-LAST:event_jScrollPane4FocusLost

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton5ActionPerformed
        iname_list1.dispose();
    }// GEN-LAST:event_jButton5ActionPerformed

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deletebuttonActionPerformed
        delete();
    }// GEN-LAST:event_deletebuttonActionPerformed

    private void printbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_printbuttonActionPerformed
        int bb = JOptionPane.showConfirmDialog(this, "<html><h1>You Want to Print Barcode ?</h1></html>", "Barcode",
                JOptionPane.YES_NO_OPTION);
        if (bb == JOptionPane.YES_OPTION) {
            print_barcode();
        }
        // TODO add your handling code here:
    }// GEN-LAST:event_printbuttonActionPerformed

    private void prebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_prebuttonActionPerformed

        try {
            String grn = h1.getText();
            String query;
            if (grn.equalsIgnoreCase("--")) {
                query = "select max(sno) from packing";
            } else {
                query = "select sno from packing where sno < '" + grn + "' order by sno desc limit 1";
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

    private void nextbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_nextbuttonActionPerformed
        try {

            String grn = h1.getText();
            if (grn.equalsIgnoreCase("--")) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String query = "select sno from packing where sno > '" + grn + "' order by sno limit 1";
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

    private void h12ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h12ActionPerformed
        h9.requestFocus();
    }// GEN-LAST:event_h12ActionPerformed

    private void h12KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_h12KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            h12.setText(whatl.getText());
        }
    }// GEN-LAST:event_h12KeyPressed

    private void h13ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h13ActionPerformed
        add_item();
    }// GEN-LAST:event_h13ActionPerformed

    private void h13KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_h13KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            h13.setText(whatl.getText());
        }
    }// GEN-LAST:event_h13KeyPressed

    private void whatlFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_whatlFocusGained
        h4.requestFocus();
    }// GEN-LAST:event_whatlFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton deletebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h10;
    private javax.swing.JTextField h11;
    private javax.swing.JTextField h12;
    private javax.swing.JTextField h13;
    private javax.swing.JTextField h14;
    private javax.swing.JTextField h2;
    private javax.swing.JTextField h4;
    private javax.swing.JTextField h5;
    private javax.swing.JTextField h6;
    private javax.swing.JTextField h7;
    private javax.swing.JTextField h8;
    private javax.swing.JTextField h9;
    private javax.swing.JDialog iname_list;
    private javax.swing.JDialog iname_list1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JButton nextbutton;
    private javax.swing.JButton prebutton;
    private javax.swing.JButton printbutton;
    private javax.swing.JButton removebutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JButton viewbutton;
    private javax.swing.JTextField whatl;
    // End of variables declaration//GEN-END:variables
}
