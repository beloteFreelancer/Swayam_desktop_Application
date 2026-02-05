package itempack;

import Utils.CompanySettingUtil;
import Utils.ItemUtil;
import Utils.ColorConstants;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.selrom.db.DataUtil;
import com.selrom.db.Database;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public final class item_master extends javax.swing.JInternalFrame {

    DataUtil util = null;
    AutoCompleteSupport support, support1, support2, support3, support4, support5, support6, support7;
    String rrate = "", wrate = "";
    double rdis = 0, wdis = 0;
    int hmany = 2;

    final void button_short() {
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        updatebutton.setText("<html><b>Update</b><br>(Alt+U)</h6><html>");
        prebutton.setText("<html><b>Last Entry</b><br>(Alt+R)</h6><html>");
        nextbutton.setText("<html><b>Next Entry</b><br>(Alt+N)</h6><html>");

        titlelablel.setText("<html><u>Item Master</u></html>");
        setTitle("Item Master");
        this.setSize(611, 621);
        javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            this.setFrameIcon(icon);
        }
    }

    void get_default() {
        try {
            String print_name = "";
            String query = "select print_name,rprice,wprice,rdis,wdis,hmany from setting_bill";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                print_name = r.getString(1);
                rrate = r.getString(2);
                wrate = r.getString(3);
                rdis = r.getDouble(4);
                wdis = r.getDouble(5);
                hmany = r.getInt(6);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_ino() {
        try {
            int sno = 1;
            String query = "select max(ino) from item";
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

    final void get_sug1() {
        try {
            int count = 0;
            String query = "select distinct hsn from item";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct hsn from item";
            set = util.doQuery(query);
            String f[] = new String[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support = AutoCompleteSupport.install(h11, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    final void get_sug2() {
        try {
            int count = 0;
            String query = "select distinct cat from item";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct cat from item";
            set = util.doQuery(query);
            String f[] = new String[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support1 = AutoCompleteSupport.install(h9, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    final void get_sug3() {
        try {
            int count = 0;
            String query = "select distinct rack from item";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct rack from item";
            set = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support2 = AutoCompleteSupport.install(h15, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug4() {
        try {
            int count = 0;
            String query = "select distinct udes from item union select distinct subunit from item;";
            ResultSet set = util.doQuery(query);
            ArrayList<String> unitsList = new ArrayList<>();
            while (set.next()) {
                if (set.getString(1) != null && !set.getString(1).isBlank()) {
                    unitsList.add(set.getString(1));
                }
            }
            support3 = AutoCompleteSupport.install(h12, GlazedLists.eventListOf(unitsList.toArray()));
            support7 = AutoCompleteSupport.install(subUnitCombobox, GlazedLists.eventListOf(unitsList.toArray()));

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    final void get_sug5() {
        try {
            int count = 0;
            String query = "select distinct iname from item";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct iname from item order by iname";
            set = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support4 = AutoCompleteSupport.install(h2, GlazedLists.eventListOf(f));

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    final void get_sug6() {
        try {
            int count = 0;
            String query = "select distinct manu from item";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select distinct manu from item";
            set = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support5 = AutoCompleteSupport.install(h10, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    final void get_sug7() {
        try {
            int count = 0;
            String query = "select barcode from item";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select barcode from item";
            set = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support6 = AutoCompleteSupport.install(h17, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_defaults() {
        if (h3.getText().equals("")) {
            h3.setText(h2.getSelectedItem().toString());
        }
        if (h4.getText().equals("")) {
            h4.setText("" + 0);
        }
        if (h5.getText().equals("")) {
            h5.setText("" + 0);
        }
        if (h6.getText().equals("")) {
            h6.setText("" + 0);
        }
        if (h7.getText().equals("")) {
            h7.setText("" + 0);
        }
        if (h8.getText().equals("")) {
            h8.setText("" + 0);
        }
        if (h9.getSelectedItem() == null || h9.getSelectedItem() == "") {
            h9.setSelectedItem(".");
        }
        if (h10.getSelectedItem() == null || h10.getSelectedItem() == "") {
            h10.setSelectedItem(".");
        }
        if (h11.getSelectedItem() == null || h11.getSelectedItem() == "") {
            h11.setSelectedItem(".");
        }
        if (h13.getText().equals("")) {
            h13.setText("" + 0);
        }
        if (h14.getText().equals("")) {
            h14.setText("" + 0);
        }
        if (h15.getSelectedItem() == null || h15.getSelectedItem() == "") {
            h15.setSelectedItem(".");
        }
        if (h16.getText().equals("")) {
            h16.setText("" + 0);
        }
        if (h19.getText().equals("")) {
            h19.setText("" + 0);
        }
        if (h20.getText().equals("")) {
            h20.setText(".");
        }
    }

    void save(boolean fromSave) {
        try {
            if (h2.getSelectedItem() == null || h2.getSelectedItem().toString().isBlank()) {
                JOptionPane.showMessageDialog(this, "Please enter Item Name.", "Missing Item Name",
                        JOptionPane.ERROR_MESSAGE);
                h2.requestFocus();
                return;
            }

            get_defaults();

            int taxp;
            try {
                taxp = Integer.parseInt(h5.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tax % must be a number.", "Invalid Tax",
                        JOptionPane.ERROR_MESSAGE);
                h5.requestFocus();
                return;
            }

            if (taxp != 0 && taxp != 5 && taxp != 12 && taxp != 18 && taxp != 28) {
                JOptionPane.showMessageDialog(this, "<html><h4>Allowed Tax %: 0%, 5%, 12%, 18%, 28%</h4></html>",
                        "Invalid Tax", JOptionPane.ERROR_MESSAGE);
                h5.requestFocus();
                return;
            }

            if (batchField.getText().isBlank()) {
                JOptionPane.showMessageDialog(
                        this,
                        "<html><h4>" + (CompanySettingUtil.getInstance().isDisplayBatch() ? "Batch is required."
                                : "Size is required.") + "</html>",
                        "Invalid Tax",
                        JOptionPane.ERROR_MESSAGE);
                batchField.requestFocus();
                return;
            }

            double stock;
            try {
                stock = Double.parseDouble(h19.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Stock must be a valid number.", "Invalid Tax",
                        JOptionPane.ERROR_MESSAGE);
                h5.requestFocus();
                return;
            }

            get_ino();

            if (h17.getSelectedItem() == null || h17.getSelectedItem().toString().isBlank()) {
                h17.setSelectedItem(h1.getText());
            }

            String ino = h1.getText();
            String barcode = h17.getSelectedItem().toString().trim();
            String iname = h2.getSelectedItem().toString().trim();
            String iname1 = h3.getText().trim();
            String prate = h4.getText().trim();
            String mrp = h6.getText().trim();
            String rprice = h7.getText().trim();
            String wprice = h8.getText().trim();
            String cat = h9.getSelectedItem().toString().trim();
            String manu = h10.getSelectedItem().toString().trim();
            String hsn = h11.getSelectedItem().toString().trim();
            String udes = h20.getText().trim();
            String minstock = h13.getText().trim();
            String maxstock = h14.getText().trim();
            String rack = h15.getSelectedItem().toString().trim();
            String disp = h16.getText().trim();
            String batch = batchField.getText();
            String exp = expDateField.getText().isBlank() ? null : expDateField.getText();
            String mfg = mfgDateField.getText().isBlank() ? null : mfgDateField.getText();
            ResultSet rs;
            if (fromSave) {
                // Check for existing item name and barcode
                rs = util.doQuery("SELECT iname FROM item WHERE iname = '" + iname + "'");
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Item Name already exists!", "Duplicate Entry",
                            JOptionPane.ERROR_MESSAGE);
                    h2.requestFocus();
                    return;
                }
            }

            rs = util.doQuery("SELECT barcode FROM item WHERE barcode = '" + barcode + "'");
            if (rs.next()) {
                if (fromSave) {
                    JOptionPane.showMessageDialog(this, "Barcode already exists!", "Duplicate Entry",
                            JOptionPane.ERROR_MESSAGE);
                    h17.requestFocus();
                    return;
                }
            }
            if (exp != null && !isValidDate(exp, "yyyy-MM-dd")) {
                JOptionPane.showMessageDialog(this, "Invalid expiry date. Please enter in yyyy/mm/dd format.",
                        "Invalid Date", JOptionPane.ERROR_MESSAGE);
                expDateField.requestFocus();
                return;
            }

            if (mfg != null && !isValidDate(mfg, "yyyy-MM-dd")) {
                JOptionPane.showMessageDialog(this, "Invalid manufacturing date. Please enter in yyyy/mm/dd format.",
                        "Invalid Date", JOptionPane.ERROR_MESSAGE);
                mfgDateField.requestFocus();
                return;
            }

            // Sub unit handling
            String subunit = subUnitField.getText().trim();
            String subconv = subUnitValuePerUnit.getText().trim();
            Double subconvValue = null;

            if (!subunit.isBlank()) {
                if (subunit.equalsIgnoreCase(udes)) {
                    JOptionPane.showMessageDialog(this, "Main Unit and Sub Unit cannot be the same.",
                            "Invalid Unit Selection", JOptionPane.ERROR_MESSAGE);
                    subUnitField.requestFocus();
                    return;
                }

                try {
                    subconvValue = Double.parseDouble(subconv);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a valid number for Sub Unit Conversion (e.g. 0.5, 1, 2).", "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                    subUnitValuePerUnit.requestFocus();
                    return;
                }
            }
            String insertItemSQL;
            if (CompanySettingUtil.getInstance().isDisplayBatch()) {
                insertItemSQL = "INSERT INTO item (ino, barcode, iname, iname1, prate, taxp, mrp, rprice, wprice, cat, manu, hsn, udes, minstock, maxstock, rack, disp, ostock, subunit, subconv, batch, exp_date, mfg_date) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
            } else {
                insertItemSQL = "INSERT INTO item (ino, barcode, iname, iname1, prate, taxp, mrp, rprice, wprice, cat, manu, hsn, udes, minstock, maxstock, rack, disp, ostock, subunit, subconv, size,exp_date, mfg_date) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
            }
            // INSERT INTO item
            try (PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement(insertItemSQL)) {
                pstmt.setString(1, ino);
                pstmt.setString(2, barcode);
                pstmt.setString(3, iname);
                pstmt.setString(4, iname1);
                pstmt.setDouble(5, Double.parseDouble(prate));
                pstmt.setInt(6, taxp);
                pstmt.setDouble(7, Double.parseDouble(mrp));
                pstmt.setDouble(8, Double.parseDouble(rprice));
                pstmt.setDouble(9, Double.parseDouble(wprice));
                pstmt.setString(10, cat);
                pstmt.setString(11, manu);
                pstmt.setString(12, hsn);
                pstmt.setString(13, udes);
                pstmt.setDouble(14, Double.parseDouble(minstock));
                pstmt.setDouble(15, Double.parseDouble(maxstock));
                pstmt.setString(16, rack);
                pstmt.setDouble(17, Double.parseDouble(disp));
                pstmt.setDouble(18, stock);
                pstmt.setString(19, subunit.isBlank() ? null : subunit);
                if (subunit.isBlank()) {
                    pstmt.setNull(20, java.sql.Types.DOUBLE);
                } else {
                    pstmt.setDouble(20, subconvValue);
                }
                pstmt.setString(21, batch);
                pstmt.setString(22, exp);
                pstmt.setString(23, mfg);
                pstmt.executeUpdate();
            }

            // INSERT INTO stock
            String sQuery = "INSERT INTO stock (barcode, ino, iname, mrp, rprice, wprice, prate, quan, cat, entry) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement(sQuery)) {
                pstmt.setString(1, barcode);
                pstmt.setString(2, ino);
                pstmt.setString(3, iname);
                pstmt.setDouble(4, Double.parseDouble(mrp));
                pstmt.setDouble(5, Double.parseDouble(rprice));
                pstmt.setDouble(6, Double.parseDouble(wprice));
                pstmt.setDouble(7, Double.parseDouble(prate));
                pstmt.setDouble(8, stock);
                pstmt.setString(9, cat);
                pstmt.setString(10, "purchase");
                pstmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(
                    this,
                    fromSave ? "Saved Successfully" : "Copy Successfully",
                    fromSave ? "Saved" : "Copied",
                    JOptionPane.PLAIN_MESSAGE);
            clear();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
        }
    }

    void clear() {
        try {
            h1.setText("--");

            support1.uninstall();
            support2.uninstall();
            support3.uninstall();
            support4.uninstall();
            support5.uninstall();
            support6.uninstall();
            support7.uninstall();
            updatebutton.setVisible(false);
            deletebutton.setVisible(false);
            copyButton.setVisible(false);
            savebutton.setVisible(true);
            get_sug2();
            get_sug3();
            get_sug4();
            get_sug5();
            get_sug6();
            get_sug7();
            h17.setSelectedItem("");
            h2.setSelectedItem("");
            h3.setText("");
            h4.setText("");
            h6.setText("");
            h7.setText("");
            h8.setText("");
            h11.setSelectedItem("");
            h5.setText("");
            h9.setSelectedItem("");
            h10.setSelectedItem("");
            h13.setText("");
            h14.setText("");
            h16.setText("");
            h18.setText("");
            h20.setText("");
            h15.setSelectedItem("");
            h12.setSelectedItem("");
            h2.setSelectedItem("");
            h2.requestFocus();
            h19.setText("");
            h19.setEnabled(true);
            subUnitCombobox.setSelectedItem("");
            subUnitField.setText("");
            subUnitValuePerUnit.setText("");
            mainUnitField.setText("");
            batchField.setText("");
            expDateField.setText("");
            mfgDateField.setText("");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void view(String ino) {
        try {
            String query;
            if (CompanySettingUtil.getInstance().isDisplayBatch()) {
                query = "select ino,barcode,iname,iname1,prate,taxp,mrp,rprice,wprice,cat,manu,hsn,udes,minstock,maxstock,rack,disp,ostock,subunit,subconv,batch,exp_date,mfg_date from item where ino='"
                        + ino + "' ";
            } else {
                query = "select ino,barcode,iname,iname1,prate,taxp,mrp,rprice,wprice,cat,manu,hsn,udes,minstock,maxstock,rack,disp,ostock,subunit,subconv,size,exp_date,mfg_date from item where ino='"
                        + ino + "' ";
            }
            ResultSet set1 = util.doQuery(query);
            boolean selva = false;
            while (set1.next()) {
                h1.setText(set1.getString(1));
                h17.setSelectedItem(set1.getString(2));
                h2.setSelectedItem(set1.getString(3));
                h3.setText(set1.getString(4));
                h4.setText(set1.getString(5));
                h5.setText(set1.getString(6));
                h6.setText(set1.getString(7));
                h7.setText(set1.getString(8));
                h8.setText(set1.getString(9));
                h9.setSelectedItem(set1.getString(10));
                h10.setSelectedItem(set1.getString(11));
                h11.setSelectedItem(set1.getString(12));
                h20.setText(set1.getString(13));
                h13.setText(set1.getString(14));
                h14.setText(set1.getString(15));
                h15.setSelectedItem(set1.getString(16));
                h16.setText(set1.getString(17));
                h19.setText(set1.getString(18));
                subUnitField.setText(set1.getString(19));
                subUnitCombobox.setSelectedItem(set1.getString(19));
                mainUnitField.setText(set1.getString(13));
                subUnitValuePerUnit.setText(set1.getString(20));
                batchField.setText(set1.getString(21));
                expDateField.setText(set1.getString(22));
                mfgDateField.setText(set1.getString(23));
                selva = true;
            }
            if (selva == true) {
                savebutton.setVisible(false);
                copyButton.setVisible(true);
                updatebutton.setVisible(true);
                deletebutton.setVisible(true);
            } else if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isValidDate(String dateStr, String format) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);

        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse(dateStr, pos);

        return date != null && pos.getIndex() == dateStr.length();
    }

    void delete(String ino) {
        try {
            int choice = JOptionPane.showConfirmDialog(this, "Do you really want to delete this item?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) {
                return;
            }

            // Check if item exists
            String checkQuery = "SELECT 1 FROM item WHERE ino = ?";
            try (PreparedStatement checkStmt = Database.getInstance().getConnection().prepareStatement(checkQuery)) {
                checkStmt.setInt(1, Integer.parseInt(ino));
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(this, "Item not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            // Delete from stock
            String deleteStock = "DELETE FROM stock WHERE ino = ?";
            try (PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement(deleteStock)) {
                pstmt.setInt(1, Integer.parseInt(ino));
                pstmt.executeUpdate();
            }

            // Delete from item
            String deleteItem = "DELETE FROM item WHERE ino = ?";
            int rowsDeleted = 0;
            try (PreparedStatement pstmt = Database.getInstance().getConnection().prepareStatement(deleteItem)) {
                pstmt.setInt(1, Integer.parseInt(ino));
                rowsDeleted = pstmt.executeUpdate();
            }

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Item deleted successfully.", "Success", JOptionPane.PLAIN_MESSAGE);
                clear();
            } else {
                JOptionPane.showMessageDialog(this, "No records deleted. Please check the item code.", "Info",
                        JOptionPane.WARNING_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid item code format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting item: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    void update() {
        try {
            if (h2.getSelectedItem() == null || h2.getSelectedItem().toString().isBlank()) {
                JOptionPane.showMessageDialog(this, "Please enter Item Name.", "Item Name", JOptionPane.ERROR_MESSAGE);
                h2.requestFocus();
                return;
            }

            get_defaults();

            int taxp;
            try {
                taxp = Integer.parseInt(h5.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tax % must be a valid number.", "Invalid Tax",
                        JOptionPane.ERROR_MESSAGE);
                h5.requestFocus();
                return;
            }

            if (taxp != 0 && taxp != 5 && taxp != 12 && taxp != 18 && taxp != 28) {
                JOptionPane.showMessageDialog(this, "<html><h4>Allowed: 0%, 5%, 12%, 18%, 28%</h4></html>",
                        "Invalid Tax", JOptionPane.ERROR_MESSAGE);
                h5.requestFocus();
                return;
            }

            if (batchField.getText().isBlank()) {
                JOptionPane.showMessageDialog(
                        this,
                        "<html><h4>" + (CompanySettingUtil.getInstance().isDisplayBatch() ? "Batch is required."
                                : "Size is required.") + "</html>",
                        "Invalid Tax",
                        JOptionPane.ERROR_MESSAGE);
                batchField.requestFocus();
                return;
            }

            double stock;
            try {
                stock = Double.parseDouble(h19.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Stock must be a valid number.", "Invalid Tax",
                        JOptionPane.ERROR_MESSAGE);
                h5.requestFocus();
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Do you want to update this item?", "Confirm Update",
                    JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            // Gather field values
            String ino = h1.getText().trim();
            String barcode = h17.getSelectedItem().toString().trim();
            String iname = h2.getSelectedItem().toString().trim();
            String iname1 = h3.getText().trim();
            String prate = h4.getText().trim();
            String mrp = h6.getText().trim();
            String rprice = h7.getText().trim();
            String wprice = h8.getText().trim();
            String cat = h9.getSelectedItem().toString().trim();
            String manu = h10.getSelectedItem().toString().trim();
            String hsn = h11.getSelectedItem().toString().trim();
            String udes = h20.getText().trim();
            String minstock = h13.getText().trim();
            String maxstock = h14.getText().trim();
            String rack = h15.getSelectedItem().toString().trim();
            String disp = h16.getText().trim();
            String subunit = subUnitField.getText().trim();
            String subconv = subUnitValuePerUnit.getText().trim();
            String batch = batchField.getText();
            String exp = expDateField.getText().isBlank() ? null : expDateField.getText();
            String mfg = mfgDateField.getText().isBlank() ? null : mfgDateField.getText();

            Double subconvValue = null;
            if (!subunit.isBlank()) {
                if (subunit.equalsIgnoreCase(udes)) {
                    JOptionPane.showMessageDialog(this, "Main Unit and Sub Unit cannot be the same.", "Invalid Unit",
                            JOptionPane.ERROR_MESSAGE);
                    subUnitField.requestFocus();
                    return;
                }

                try {
                    subconvValue = Double.parseDouble(subconv);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid Sub Unit Conversion (e.g. 1, 0.5)", "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                    subUnitValuePerUnit.requestFocus();
                    return;
                }
            }

            if (exp != null && !isValidDate(exp, "yyyy-MM-dd")) {
                JOptionPane.showMessageDialog(this, "Invalid expiry date. Please enter in yyyy/mm/dd format.",
                        "Invalid Date", JOptionPane.ERROR_MESSAGE);
                expDateField.requestFocus();
                return;
            }

            if (mfg != null && !isValidDate(mfg, "yyyy-MM-dd")) {
                JOptionPane.showMessageDialog(this, "Invalid manufacturing date. Please enter in yyyy/mm/dd format.",
                        "Invalid Date", JOptionPane.ERROR_MESSAGE);
                mfgDateField.requestFocus();
                return;
            }

            // ========== Update item table ==========
            StringBuilder itemQuery = new StringBuilder();
            itemQuery.append("UPDATE item SET ");
            itemQuery.append("barcode='").append(barcode).append("', ");
            itemQuery.append("iname='").append(iname).append("', ");
            itemQuery.append("iname1='").append(iname1).append("', ");
            itemQuery.append("prate=").append(prate).append(", ");
            itemQuery.append("taxp=").append(taxp).append(", ");
            itemQuery.append("mrp=").append(mrp).append(", ");
            itemQuery.append("rprice=").append(rprice).append(", ");
            itemQuery.append("wprice=").append(wprice).append(", ");
            itemQuery.append("cat='").append(cat).append("', ");
            itemQuery.append("manu='").append(manu).append("', ");
            itemQuery.append("hsn='").append(hsn).append("', ");
            itemQuery.append("udes='").append(udes).append("', ");
            itemQuery.append("minstock=").append(minstock).append(", ");
            itemQuery.append("maxstock=").append(maxstock).append(", ");
            itemQuery.append("rack='").append(rack).append("', ");
            itemQuery.append("disp=").append(disp).append(", ");
            itemQuery.append("ostock=").append(stock).append(", ");
            if (CompanySettingUtil.getInstance().isDisplayBatch()) {
                itemQuery.append("batch='").append(batch).append("', ");
            } else {
                itemQuery.append("size='").append(batch).append("', ");
            }
            if (CompanySettingUtil.getInstance().isDisplayExp()) {
                if (exp != null) {
                    itemQuery.append("exp_date='").append(exp).append("', ");
                } else {
                    itemQuery.append("exp_date= NULL ,");
                }
            }

            if (CompanySettingUtil.getInstance().isDisplayMfg()) {
                if (mfg != null) {
                    itemQuery.append("mfg_date='").append(mfg).append("', ");
                } else {
                    itemQuery.append("mfg_date= NULL ,");
                }
            }

            if (!subunit.isBlank()) {
                itemQuery.append("subunit='").append(subunit).append("', ");
                itemQuery.append("subconv=").append(subconvValue);
            } else {
                itemQuery.append("subunit=NULL, subconv=NULL");
            }

            itemQuery.append(" WHERE ino='").append(ino).append("'");

            ArrayList<String> queryList = new ArrayList<>();
            queryList.add(itemQuery.toString());
            queryList.add("UPDATE stock SET "
                    + "barcode='" + barcode + "', "
                    + "iname='" + iname + "', "
                    + "mrp=" + mrp + ", "
                    + "rprice=" + rprice + ", "
                    + "wprice=" + wprice + ", "
                    + "prate=" + prate + ", "
                    + "quan=" + stock + ", "
                    + "cat='" + cat + "' "
                    + "WHERE ino='" + ino + "'");
            // ========== Run All Updates ==========
            int affected = util.doManipulation_Batch(queryList);
            if (affected > 0) {
                JOptionPane.showMessageDialog(this, "Updated Successfully", "Update", JOptionPane.INFORMATION_MESSAGE);
                clear();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. Please check input values.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
        }
    }

    public item_master(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();
        get_sug1();
        get_sug2();
        get_sug3();
        get_sug4();
        get_sug5();
        get_sug6();
        get_sug7();
        get_default();
        h17.requestFocus();
        updatebutton.setVisible(false);
        deletebutton.setVisible(false);
        copyButton.setVisible(false);
        if (CompanySettingUtil.getInstance().isDisplayBatch()) {
            batchLabel.setText(" Batch");
        } else {
            batchLabel.setText("Size");
        }
        if (CompanySettingUtil.getInstance().isDisplayExp()) {
            expLabel.setVisible(true);
            expDateField.setVisible(true);
            expJcalendarButton.setVisible(true);
        } else {
            expLabel.setVisible(false);
            expDateField.setVisible(false);
            expJcalendarButton.setVisible(false);
        }
        if (CompanySettingUtil.getInstance().isDisplayMfg()) {
            mfgLabel.setVisible(true);
            mfgDateField.setVisible(true);
            mfgJcalendarButton.setVisible(true);
        } else {
            mfgLabel.setVisible(false);
            mfgDateField.setVisible(false);
            mfgJcalendarButton.setVisible(false);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        h14 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        h11 = new javax.swing.JComboBox<>();
        h1 = new javax.swing.JTextField();
        h10 = new javax.swing.JComboBox<>();
        h5 = new javax.swing.JTextField();
        h16 = new javax.swing.JTextField();
        h13 = new javax.swing.JTextField();
        savebutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        deletebutton = new javax.swing.JButton();
        updatebutton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        h12 = new javax.swing.JComboBox<>();
        h15 = new javax.swing.JComboBox<>();
        h3 = new javax.swing.JTextField();
        h2 = new javax.swing.JComboBox<>();
        h9 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        h4 = new javax.swing.JTextField();
        h6 = new javax.swing.JTextField();
        h7 = new javax.swing.JTextField();
        h8 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        h17 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        h18 = new javax.swing.JTextField();
        nextbutton = new javax.swing.JButton();
        prebutton = new javax.swing.JButton();
        exclusiveButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        h19 = new javax.swing.JTextField();
        h20 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        inclusiveButton = new javax.swing.JButton();
        barcodeGenerateButton = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        subUnitCombobox = new javax.swing.JComboBox<>();
        label1 = new java.awt.Label();
        subUnitValuePerUnit = new javax.swing.JFormattedTextField();
        mainUnitField = new javax.swing.JTextField();
        subUnitField = new javax.swing.JTextField();
        batchLabel = new javax.swing.JLabel();
        batchField = new javax.swing.JTextField();
        expLabel = new javax.swing.JLabel();
        expJcalendarButton = new net.sourceforge.jcalendarbutton.JCalendarButton();
        mfgJcalendarButton = new net.sourceforge.jcalendarbutton.JCalendarButton();
        expDateField = new javax.swing.JTextField();
        mfgDateField = new javax.swing.JTextField();
        mfgLabel = new javax.swing.JLabel();
        copyButton = new javax.swing.JButton();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Item Master");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 150, 30);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText(" It.Wise Dis %");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(310, 380, 110, 30);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText(" Opening Stock");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(310, 410, 100, 30);

        h14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h14);
        h14.setBounds(420, 350, 150, 30);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText(" Wholesale Price");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(310, 200, 110, 30);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("HSN Code");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(20, 290, 100, 30);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Category");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(20, 230, 100, 30);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Manufacturer");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(20, 260, 100, 30);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Re-Order Qty");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(20, 350, 100, 30);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText(" Unit Desc");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(310, 290, 110, 30);

        h11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h11);
        h11.setBounds(120, 290, 190, 30);

        h1.setEditable(false);
        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setText("--");
        h1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h1FocusGained(evt);
            }
        });
        getContentPane().add(h1);
        h1.setBounds(120, 50, 450, 30);

        h10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h10);
        h10.setBounds(120, 260, 450, 30);

        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                h5FocusLost(evt);
            }
        });
        getContentPane().add(h5);
        h5.setBounds(420, 140, 120, 30);

        h16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h16);
        h16.setBounds(420, 380, 150, 30);

        h13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h13);
        h13.setBounds(120, 350, 190, 30);

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
        savebutton.setBounds(120, 480, 150, 50);

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
        clearbutton.setBounds(270, 530, 150, 50);

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
        closebutton.setBounds(420, 530, 150, 50);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Item_Name");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(20, 80, 100, 30);

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
        deletebutton.setBounds(120, 530, 150, 50);

        updatebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        updatebutton.setIcon(ColorConstants.loadIcon("/icons/load45.jpg.png")); // NOI18N
        updatebutton.setMnemonic('u');
        updatebutton.setText("Update");
        updatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(updatebutton);
        updatebutton.setBounds(120, 480, 150, 50);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText(" Max.Stock Qty");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(310, 350, 110, 30);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Item Location");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(20, 380, 100, 30);

        h12.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        h12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        h12.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                h12ItemStateChanged(evt);
            }
        });
        h12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h12ActionPerformed(evt);
            }
        });
        getContentPane().add(h12);
        h12.setBounds(490, 290, 80, 30);

        h15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h15);
        h15.setBounds(120, 380, 190, 30);

        h3.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        getContentPane().add(h3);
        h3.setBounds(120, 110, 450, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h2);
        h2.setBounds(120, 80, 420, 30);

        h9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h9);
        h9.setBounds(120, 230, 190, 30);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Print Name");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(20, 110, 100, 30);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Retail Price");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(20, 200, 100, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h4);
        h4.setBounds(120, 140, 190, 30);

        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                h6FocusLost(evt);
            }
        });
        getContentPane().add(h6);
        h6.setBounds(120, 170, 190, 30);

        h7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h7FocusGained(evt);
            }
        });
        getContentPane().add(h7);
        h7.setBounds(120, 200, 190, 30);

        h8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h8FocusGained(evt);
            }
        });
        getContentPane().add(h8);
        h8.setBounds(420, 200, 150, 30);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("MRP");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(20, 170, 100, 30);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("Purchase Price");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(20, 140, 100, 30);

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("Item_Code");
        getContentPane().add(jLabel18);
        jLabel18.setBounds(20, 50, 90, 30);

        h17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h17.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        getContentPane().add(h17);
        h17.setBounds(120, 410, 160, 30);

        jButton1.setIcon(ColorConstants.loadIcon("/icons/search22.png")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(280, 410, 30, 30);

        jButton2.setIcon(ColorConstants.loadIcon("/icons/search22.png")); // NOI18N
        jButton2.setMnemonic('v');
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(540, 80, 30, 30);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText(" Tax Per %");
        getContentPane().add(jLabel19);
        jLabel19.setBounds(310, 140, 110, 30);

        h18.setEditable(false);
        h18.setBackground(new java.awt.Color(255, 255, 204));
        h18.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        h18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        h18.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h18FocusGained(evt);
            }
        });
        getContentPane().add(h18);
        h18.setBounds(310, 170, 200, 30);

        nextbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        nextbutton.setIcon(ColorConstants.loadIcon("/icons/next45.png")); // NOI18N
        nextbutton.setMnemonic('n');
        nextbutton.setText("Next Entry");
        nextbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(nextbutton);
        nextbutton.setBounds(420, 480, 150, 50);

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
        prebutton.setBounds(270, 480, 150, 50);

        exclusiveButton.setBackground(new java.awt.Color(153, 153, 153));
        exclusiveButton.setIcon(ColorConstants.loadIcon("/icons/search22.png")); // NOI18N
        exclusiveButton.setMnemonic('v');
        exclusiveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exclusiveButtonActionPerformed(evt);
            }
        });
        getContentPane().add(exclusiveButton);
        exclusiveButton.setBounds(540, 170, 30, 30);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Barcode");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(20, 410, 100, 30);

        h19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h19);
        h19.setBounds(420, 410, 150, 30);

        h20.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        h20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h20ActionPerformed(evt);
            }
        });
        getContentPane().add(h20);
        h20.setBounds(420, 290, 70, 30);

        jButton4.setIcon(ColorConstants.loadIcon("/icons/search22.png")); // NOI18N
        jButton4.setMnemonic('v');
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4);
        jButton4.setBounds(540, 140, 30, 30);

        inclusiveButton.setBackground(new java.awt.Color(204, 204, 204));
        inclusiveButton.setIcon(ColorConstants.loadIcon("/icons/search22.png")); // NOI18N
        inclusiveButton.setMnemonic('v');
        inclusiveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inclusiveButtonActionPerformed(evt);
            }
        });
        getContentPane().add(inclusiveButton);
        inclusiveButton.setBounds(510, 170, 30, 30);

        barcodeGenerateButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        barcodeGenerateButton.setIcon(ColorConstants.loadIcon("/icons/barcodeIcon.png")); // NOI18N
        barcodeGenerateButton.setText("Barcode");
        barcodeGenerateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barcodeGenerateButtonActionPerformed(evt);
            }
        });
        getContentPane().add(barcodeGenerateButton);
        barcodeGenerateButton.setBounds(10, 480, 110, 50);

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setText("Main Unit");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(20, 320, 100, 30);

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText(" Sub Unit");
        getContentPane().add(jLabel22);
        jLabel22.setBounds(310, 320, 110, 30);

        subUnitCombobox.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        subUnitCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subUnitComboboxActionPerformed(evt);
            }
        });
        getContentPane().add(subUnitCombobox);
        subUnitCombobox.setBounds(490, 320, 80, 30);

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setText("=");
        getContentPane().add(label1);
        label1.setBounds(194, 320, 50, 30);
        getContentPane().add(subUnitValuePerUnit);
        subUnitValuePerUnit.setBounds(250, 320, 60, 30);

        mainUnitField.setEnabled(false);
        getContentPane().add(mainUnitField);
        mainUnitField.setBounds(120, 320, 64, 30);
        getContentPane().add(subUnitField);
        subUnitField.setBounds(420, 320, 70, 30);

        batchLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(batchLabel);
        batchLabel.setBounds(315, 230, 110, 30);
        getContentPane().add(batchField);
        batchField.setBounds(420, 230, 150, 30);

        expLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        expLabel.setText(" Exp Date");
        getContentPane().add(expLabel);
        expLabel.setBounds(310, 440, 100, 30);

        expJcalendarButton.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                expJcalendarButtonPropertyChange(evt);
            }
        });
        getContentPane().add(expJcalendarButton);
        expJcalendarButton.setBounds(540, 440, 30, 30);

        mfgJcalendarButton.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                mfgJcalendarButtonPropertyChange(evt);
            }
        });
        getContentPane().add(mfgJcalendarButton);
        mfgJcalendarButton.setBounds(280, 440, 30, 30);
        getContentPane().add(expDateField);
        expDateField.setBounds(420, 440, 120, 30);
        getContentPane().add(mfgDateField);
        mfgDateField.setBounds(120, 440, 160, 30);

        mfgLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        mfgLabel.setText("Mfg Date");
        getContentPane().add(mfgLabel);
        mfgLabel.setBounds(20, 440, 100, 30);

        copyButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        copyButton.setIcon(ColorConstants.loadIcon("/icons/copy.png")); // NOI18N
        copyButton.setText("Copy");
        copyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyButtonActionPerformed(evt);
            }
        });
        getContentPane().add(copyButton);
        copyButton.setBounds(10, 530, 110, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }// GEN-LAST:event_closebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_savebuttonActionPerformed

        save(true);
    }// GEN-LAST:event_savebuttonActionPerformed

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deletebuttonActionPerformed
        delete(h1.getText());
    }// GEN-LAST:event_deletebuttonActionPerformed

    private void h1FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h1FocusGained
        h2.requestFocus();
    }// GEN-LAST:event_h1FocusGained

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbuttonActionPerformed
        clear();
    }// GEN-LAST:event_clearbuttonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed

        if (h17.getSelectedItem() == null || h17.getSelectedItem() == "") {
            JOptionPane.showMessageDialog(this, "Enter Barcode ?", "Barcode", JOptionPane.ERROR_MESSAGE);
            h17.requestFocus();
            return;
        }
        try {
            String ino = "";
            boolean selva = false;
            String query = "select distinct ino from item where barcode='" + h17.getSelectedItem() + "'";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                ino = set.getString(1);
                selva = true;
            }
            if (selva == true) {
                view(ino);
            } else {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "Invalid", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed

        if (h2.getSelectedItem() == null || h2.getSelectedItem().toString().isBlank()) {
            JOptionPane.showMessageDialog(this, "Enter Item Name ?", "Item Name", JOptionPane.ERROR_MESSAGE);
            h2.requestFocus();
            return;
        }
        try {
            String selectedName = h2.getSelectedItem().toString();
            String query = "SELECT ino, iname, barcode,"
                    + (CompanySettingUtil.getInstance().isDisplayBatch() ? "batch" : "size")
                    + " FROM item WHERE iname = '" + selectedName + "'";
            ResultSet set = util.doQuery(query);

            List<Object[]> rows = new ArrayList<>();
            while (set.next()) {
                rows.add(new Object[] {
                        set.getString("ino"),
                        set.getString("iname"),
                        set.getString("barcode"),
                        set.getString(CompanySettingUtil.getInstance().isDisplayBatch() ? "batch" : "size")
                });
            }

            if (rows.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "Invalid", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (rows.size() == 1) {
                // Only one record found, directly view it
                view(rows.get(0)[0].toString());
            } else {
                // Multiple records found, show selection dialog
                String selectedIno = showSelectionDialog(rows);
                if (selectedIno != null) {
                    view(selectedIno);
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

    }// GEN-LAST:event_jButton2ActionPerformed

    private String showSelectionDialog(List<Object[]> data) {
        // Column names for the table
        String[] columns = { "Item No", "Item Name", "Barcode",
                CompanySettingUtil.getInstance().isDisplayBatch() ? "Batch" : "Size" };

        // Convert List<Object[]> to Object[][]
        Object[][] tableData = data.toArray(new Object[0][]);

        // Create JTable
        JTable table = new JTable(tableData, columns);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        // Show dialog
        int option = JOptionPane.showConfirmDialog(this, scrollPane, "Select Item", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                // Return the ino of selected row
                return table.getValueAt(selectedRow, 0).toString();
            }
        }
        return null; // no selection or cancelled
    }

    private void h6FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h6FocusLost
        if (h4.getText().equals("")) {
            h4.setText("" + 0);
        }
        if (h6.getText().equals("")) {
            h6.setText("" + 0);
        }
        double mrp = Double.parseDouble(h6.getText());
        double prate = Double.parseDouble(h4.getText());
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
        h7.setText("" + rprice);

        if (wrate.equalsIgnoreCase("MRP Price")) {
            wprice = mrp;
        } else if (wrate.equalsIgnoreCase("MRP- Discount %")) {
            double disamt = (wdis * mrp) / 100;
            wprice = mrp - disamt;
        } else if (wrate.equalsIgnoreCase("Purchase Price + Margin %")) {
            double addamt = (wdis * prate) / 100;
            wprice = prate + addamt;
        }
        String wprice2 = String.format("%." + hmany + "f", wprice);
        h8.setText("" + wprice2);

    }// GEN-LAST:event_h6FocusLost

    private void h5FocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h5FocusLost
        if (h4.getText().equals("")) {
            h4.setText("" + 0);
        }
        if (h5.getText().equals("")) {
            h5.setText("" + 0);
        }
        double prate = Double.parseDouble(h4.getText());
        int taxp = Integer.parseInt(h5.getText());
        double taxamt = (taxp * prate) / 100;
        double basic = prate + taxamt;
        String basic2 = String.format("%." + hmany + "f", basic);
        h18.setText("Basic Cost: " + basic2 + "   (Ex.Tax)");
    }// GEN-LAST:event_h5FocusLost

    private void h18FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h18FocusGained
        h7.requestFocus();
    }// GEN-LAST:event_h18FocusGained

    private void h7FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h7FocusGained
        if (h7.getText().equals("")) {
            h7.setText("" + 0);
        }
        double rprice = Double.parseDouble(h7.getText());
        if (rprice <= 0) {
            h7.setText("");
        }
    }// GEN-LAST:event_h7FocusGained

    private void h8FocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_h8FocusGained
        if (h8.getText().equals("")) {
            h8.setText("" + 0);
        }
        double rprice = Double.parseDouble(h8.getText());
        if (rprice <= 0) {
            h8.setText("");
        }
    }// GEN-LAST:event_h8FocusGained

    private void updatebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_updatebuttonActionPerformed
        update();

    }// GEN-LAST:event_updatebuttonActionPerformed

    private void nextbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_nextbuttonActionPerformed
        try {

            String ino = h1.getText();
            if (ino.equalsIgnoreCase("--")) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String query = "select ino from item where ino > '" + ino + "' order by ino limit 1";
            ResultSet set1 = util.doQuery(query);
            boolean selva = false;
            String search_ino = "";
            while (set1.next()) {
                selva = true;
                search_ino = set1.getString(1);
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            view(search_ino);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_nextbuttonActionPerformed

    private void prebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_prebuttonActionPerformed

        try {
            String ino = h1.getText();
            String query;
            if (ino.equalsIgnoreCase("--")) {
                query = "select max(ino) from item";
            } else {
                query = "select ino from item where ino < '" + ino + "' order by ino desc limit 1";
            }

            ResultSet set1 = util.doQuery(query);
            boolean selva = false;
            String search_ino = "";
            while (set1.next()) {
                selva = true;
                search_ino = set1.getString(1);
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            view(search_ino);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
        }
    }// GEN-LAST:event_prebuttonActionPerformed

    private void exclusiveButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exclusiveButtonActionPerformed
        try {
            double basePrice = Double.parseDouble(h4.getText().trim());
            double taxPercent = Double.parseDouble(h5.getText().trim());
            double totalPrice = basePrice + (basePrice * taxPercent / 100);
            h18.setText(String.format("Exclusive Price: %.2f", totalPrice));
            h18.setBackground(new Color(204, 232, 212));
        } catch (NumberFormatException ex) {
            h18.setText("Invalid input");
        }

    }// GEN-LAST:event_exclusiveButtonActionPerformed

    private void h12ItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_h12ItemStateChanged
        if (h12.getSelectedItem() == null || h12.getSelectedItem() == "" || h12.getSelectedItem().equals(".")) {
        } else {
            h20.setText(h12.getSelectedItem().toString());
        }
    }// GEN-LAST:event_h12ItemStateChanged

    private void inclusiveButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_inclusiveButtonActionPerformed
        try {
            double mrp = Double.parseDouble(h4.getText().trim());
            double taxPercent = Double.parseDouble(h5.getText().trim());
            double basePrice = mrp / (1 + (taxPercent / 100));
            h18.setText(String.format("Inclusive Price: %.2f", basePrice));
            h18.setBackground(new Color(204, 232, 112));
        } catch (NumberFormatException ex) {
            h18.setText("Invalid input");
        }
    }// GEN-LAST:event_inclusiveButtonActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
        if (h4.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Enter Purchase Price ?", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (h5.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Enter Tax Per% ?", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double prate = Double.parseDouble(h4.getText());
        double taxp = Double.parseDouble(h5.getText());
        double devide = 100 + taxp;
        devide = prate * (100 / devide);
        double taxamt = prate - devide;
        prate = prate - taxamt;
        String prate1 = String.format("%." + hmany + "f", prate);
        JOptionPane.showMessageDialog(this,
                "<html><b>Cost Price: " + prate1 + "\nPurchase Price - Inclusive of Tax '" + h5.getText() + "'%",
                "Inclusive of Tax", JOptionPane.PLAIN_MESSAGE);

    }// GEN-LAST:event_jButton4ActionPerformed

    private void barcodeGenerateButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_barcodeGenerateButtonActionPerformed
        // Get barcode
        String barcode = (h17.getSelectedItem() != null) ? h17.getSelectedItem().toString().trim() : "";
        if (barcode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Barcode.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get item number
        String inoText = h1.getText();
        if (inoText == null || inoText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please save Item first.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int ino;
        try {
            ino = Integer.parseInt(inoText.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Item Number must be a valid integer.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get item name
        String iname = (h2.getSelectedItem() != null) ? h2.getSelectedItem().toString().trim() : "";
        if (iname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a valid Item Name.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get MRP
        String mrpText = h6.getText();
        if (mrpText == null || mrpText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the MRP.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double mrp;
        try {
            mrp = Double.parseDouble(mrpText.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "MRP must be a valid number.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get retail price
        String rpriceText = h7.getText();
        if (rpriceText == null || rpriceText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Retail Price.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        double rprice;
        try {
            rprice = Double.parseDouble(rpriceText.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Retail Price must be a valid number.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get wholesale price
        String wpriceText = h8.getText();
        if (wpriceText == null || wpriceText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Wholesale Price.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        double wprice;
        try {
            wprice = Double.parseDouble(wpriceText.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Wholesale Price must be a valid number.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get alternate name
        String iname1 = h3.getText();
        if (iname1 == null || iname1.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Print Name.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = String.format(
                "INSERT INTO barcode (barcode, ino, iname, mrp, retail_price, wholesale_price, iname1) "
                        + "VALUES ('%s', %d, '%s', '%s', '%s', '%s', '%s')",
                barcode, ino, iname, mrp, rprice, wprice, iname1);
        try {
            int count = util.doManipulation(query);
            if (count > 0) {
                String quan = JOptionPane.showInputDialog(this, "How Many Lables ?", "Nos", JOptionPane.PLAIN_MESSAGE);
                if (quan == null || "".equals(quan)) {
                    JOptionPane.showMessageDialog(this, "Invalid Quantity!", "Invalid", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                List<Integer> items = new ArrayList<>();
                int quan1 = Integer.parseInt(quan);
                for (int i = 0; i < quan1; i++) {
                    items.add(ino);
                }

                barcodepack.jasper.JasperBarcodePrinter.print(ItemUtil.getItemsByItemNos(items));

            } else {
                JOptionPane.showMessageDialog(this, "Check Entries, Try Again...");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred while saving the barcode.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }// GEN-LAST:event_barcodeGenerateButtonActionPerformed

    private void h12ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h12ActionPerformed
        Object selectedUnit = h12.getSelectedItem();
        if (selectedUnit != null) {
            mainUnitField.setText(selectedUnit.toString());
        }
    }// GEN-LAST:event_h12ActionPerformed

    private void h20ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_h20ActionPerformed
        String selectedUnit = h20.getText();
        mainUnitField.setText(selectedUnit.toString());
    }// GEN-LAST:event_h20ActionPerformed

    private void subUnitComboboxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_subUnitComboboxActionPerformed
        Object selectedSubunit = subUnitCombobox.getSelectedItem();
        if (selectedSubunit != null) {
            {
                subUnitField.setText(selectedSubunit.toString());
            }
        }
    }// GEN-LAST:event_subUnitComboboxActionPerformed

    private void mfgJcalendarButtonPropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_mfgJcalendarButtonPropertyChange
        try {
            if (evt.getNewValue() instanceof Date) {
                String ses = evt.getNewValue().toString();
                Date nm = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(ses);
                String date = (new SimpleDateFormat("yyyy-MM-dd").format(nm));
                mfgDateField.setText(date);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_mfgJcalendarButtonPropertyChange

    private void expJcalendarButtonPropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_expJcalendarButtonPropertyChange
        try {
            if (evt.getNewValue() instanceof Date) {
                String ses = evt.getNewValue().toString();
                Date nm = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(ses);
                String date = (new SimpleDateFormat("yyyy-MM-dd").format(nm));
                expDateField.setText(date);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_expJcalendarButtonPropertyChange

    private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_copyButtonActionPerformed
        h17.setSelectedItem(null);
        save(false);
    }// GEN-LAST:event_copyButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton barcodeGenerateButton;
    private javax.swing.JTextField batchField;
    private javax.swing.JLabel batchLabel;
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton copyButton;
    private javax.swing.JButton deletebutton;
    private javax.swing.JButton exclusiveButton;
    private javax.swing.JTextField expDateField;
    private net.sourceforge.jcalendarbutton.JCalendarButton expJcalendarButton;
    private javax.swing.JLabel expLabel;
    private javax.swing.JTextField h1;
    private javax.swing.JComboBox<String> h10;
    private javax.swing.JComboBox<String> h11;
    private javax.swing.JComboBox<String> h12;
    private javax.swing.JTextField h13;
    private javax.swing.JTextField h14;
    private javax.swing.JComboBox<String> h15;
    private javax.swing.JTextField h16;
    private javax.swing.JComboBox<String> h17;
    private javax.swing.JTextField h18;
    private javax.swing.JTextField h19;
    private javax.swing.JComboBox<String> h2;
    private javax.swing.JTextField h20;
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h4;
    private javax.swing.JTextField h5;
    private javax.swing.JTextField h6;
    private javax.swing.JTextField h7;
    private javax.swing.JTextField h8;
    private javax.swing.JComboBox<String> h9;
    private javax.swing.JButton inclusiveButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private java.awt.Label label1;
    private javax.swing.JTextField mainUnitField;
    private javax.swing.JTextField mfgDateField;
    private net.sourceforge.jcalendarbutton.JCalendarButton mfgJcalendarButton;
    private javax.swing.JLabel mfgLabel;
    private javax.swing.JButton nextbutton;
    private javax.swing.JButton prebutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JComboBox<String> subUnitCombobox;
    private javax.swing.JTextField subUnitField;
    private javax.swing.JFormattedTextField subUnitValuePerUnit;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JButton updatebutton;
    // End of variables declaration//GEN-END:variables
}
