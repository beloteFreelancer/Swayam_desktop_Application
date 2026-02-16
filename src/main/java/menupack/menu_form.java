package menupack;

import ActivationPack.AES;
import MISPack.profit_billwise;
import MISPack.profit_category;
import MISPack.profit_daywise;
import MISPack.profit_itemwise;
import MISPack.profit_itemwise_barcode;
import MISPack.sales_alter_list;
import MISPack.sales_deleted_list;
import MISPack.top_customers_for_item;
import MISPack.top_customers_points_wise;
import MISPack.top_selling_items;
import MISPack.top_users;
import accountspack.Bankbook_Summary_select;
import accountspack.Cashbook_Summary_select;
import accountspack.Group_select;
import accountspack.RP_select;
import accountspack.balance_sheet_summary;
import accountspack.bank_master;
import accountspack.day_book_print;
import accountspack.master_accounts;
import accountspack.profit_loss_summary;
import accountspack.trial_balance_summary;
import accountspack.voucher_entry;
import accountspack.voucher_report;
import accountspack.voucher_report_group;
import accountspack.voucher_report_group_ledger;
import accountspack.voucher_report_pby;
import accountspack.voucher_trasfer;
import attendancepack.attendance_entry;
import attendancepack.attendance_report;
import attendancepack.attendance_summary;
import barcodepack.barcode_print;
import com.selrom.db.DataUtil;
import couldpack.day_book_close;
import custpack.cust_balance_report;
import custpack.cust_bill;
import custpack.cust_bill_report;
import custpack.cust_bills_bulk_upload;
import custpack.cust_dues_overdue;
import custpack.cust_dues_report;
import custpack.cust_dues_report_area;
import custpack.cust_dues_report_print_select;
import custpack.cust_import_excel;
import custpack.cust_list;
import custpack.cust_list_area;
import custpack.cust_list_state;
import custpack.cust_master;
import custpack.cust_pay;
import custpack.cust_pay_report;
import emailpack.email_setting;
import estimatepack.daily_estimate_report_select;
import estimatepack.daily_estimate_select;
import estimatepack.ereturn;
import estimatepack.ereturn_report;
import estimatepack.ereturn_summary_item;
import estimatepack.estimate;
import estimatepack.estimate_report;
import estimatepack.estimate_report_bill_itemwise;
import estimatepack.estimate_report_cust;
import estimatepack.estimate_report_location;
import estimatepack.estimate_report_price_type;
import estimatepack.estimate_report_terminal;
import estimatepack.estimate_summary;
import estimatepack.estimate_summary_category;
import estimatepack.estimate_summary_cust;
import estimatepack.estimate_summary_daywise;
import estimatepack.estimate_summary_item;
import estimatepack.estimate_summary_manu;
import estimatepack.estimate_summary_userwise;
import hrpack.pay_advance;
import hrpack.pay_advance_report;
import hrpack.pay_bill_generation;
import hrpack.pay_bill_report;
import hrpack.pay_loan;
import hrpack.pay_loan_report;
import hrpack.pay_loan_report_active;
import hrpack.staff_entry;
import hrpack.staff_list;
import itempack.item_master;
import itempack.item_search;
import itempack.items_import_excel;
import itempack.items_list;
import itempack.stock_import;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import Utils.ColorConstants;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import loyaltypack.cust_points_details;
import loyaltypack.cust_points_withdraw;
import loyaltypack.cust_points_withdraw_list;
import loyaltypack.points_setting;
import packingpack.packing_entry;
import packingpack.packing_report;
import pospack.sales;
import pospack.setting_terminal;
import pospack.sreturn;
import purchase_order_pack.purchase_order;
import purchase_order_pack.purchase_order_list;
import purchase_order_pack.purchase_order_report;
import purchase_order_pack.vendor_link;
import purchasepack.preturn_entry;
import purchasepack.preturn_report;
import purchasepack.preturn_summary_iname;
import purchasepack.purchase_entry;
import purchasepack.purchase_report;
import purchasepack.purchase_report_cname;
import purchasepack.purchase_report_iname;
import purchasepack.purchase_summary_category;
import purchasepack.purchase_summary_iname;
import purchasepack.purchase_summary_manu;
import purchasepack.purchase_summary_supplier;
import salespack.daily_sales_report_select;
import salespack.daily_sales_select;
import salespack.sales_report;
import salespack.sales_report_bill_itemwise;
import salespack.sales_report_cust;
import salespack.sales_report_location;
import salespack.sales_report_price_type;
import salespack.sales_report_terminal;
import salespack.sales_summary;
import salespack.sales_summary_category;
import salespack.sales_summary_cust;
import salespack.sales_summary_daywise;
import salespack.sales_summary_item;
import salespack.sales_summary_manu;
import salespack.sales_summary_userwise;
import salespack.sreturn_report;
import salespack.sreturn_summary_item;
import smspack.manual_sms;
import smspack.sms_setting;
import stockpack.stock_alter;
import stockpack.stock_alter_report;
import stockpack.stock_bulk_rate_update;
import stockpack.stock_entry;
import stockpack.stock_entry_report;
import stockpack.stock_report;
import stockpack.stock_report_barcode;
import stockpack.stock_report_entry_wise;
import stockpack.stock_report_max_stock;
import stockpack.stock_report_min_stock;
import stockpack.stock_report_nill_stock;
import stockpack.stock_summary;
import stockpack.stock_summary_entry_wise;
import stockpack.expiry_report;
import tax_reports.tax_hsn_purchase;
import tax_reports.tax_hsn_purchase_return;
import tax_reports.tax_hsn_sales;
import tax_reports.tax_hsn_sales_return;
import tax_reports.tax_purchase;
import tax_reports.tax_purchase_return;
import tax_reports.tax_sales;
import tax_reports.tax_sales_return;
import tax_reports.tax_summary;
import userpack.user;
import userpack.user_permissions;
import userpack.PermissionManager;
import venpack.ven_bill;
import venpack.ven_bill_report;
import venpack.ven_bills_bulk_upload;
import venpack.ven_dues_overdue;
import venpack.ven_dues_report;
import venpack.ven_pay;
import venpack.ven_pay_report;
import venpack.vendor_import_excel;
import venpack.vendor_list;
import venpack.vendor_master;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public final class menu_form extends javax.swing.JFrame {

    DataUtil util = null;
    String username = "", user_type = "", drive = "", folder = Utils.AppConfig.getAppPath(),
            version = "Swayam software";
    int hmany = 2;

    public String getUsername() {
        return username;
    }

    public String getUserType() {
        return user_type;
    }

    public String getDrive() {
        return drive;
    }

    public String getFoler() {
        return folder;
    }

    public String getVersion() {
        return version;
    }

    public int getHmany() {
        return hmany;
    }

    void get_user_details(String user_type1, String user1) {
        username = user1;
        user_type = user_type1;
        jLabel33.setText(username);

        if (user_type.equalsIgnoreCase("User") || user_type.equalsIgnoreCase("License Owner")) {
            get_validation();
        } else {
            get_details();
            clearl.setVisible(true);
        }
        if (!user_type.equalsIgnoreCase("Super Admin")) {
            jMenu20.setVisible(false);
            jMenu9.setVisible(false);
        }
        jMenu10.setVisible(true); // Always show Sales menu
    }

    final void get_defauls() {
        try {
            setTitle(version);
            jLabel35.setText(UserSession.getServerIp());
            InetAddress localhost = InetAddress.getLocalHost();
            String system_ip = localhost.getHostAddress().trim();
            jLabel34.setText(system_ip);

            // try (FileInputStream m = new FileInputStream(folder +
            // "/Config_Files/userlog.properties")) {
            // Properties p = new Properties(null);
            // p.load(m);
            // username = p.getProperty("user");
            // user_type = p.getProperty("utype");
            // }

            if (UserSession.isLoggedIn()) {
                username = UserSession.getUsername();
                user_type = UserSession.getUserType();
            }

            // version details
            final String secretKey = "!@#$%^&*()_+;.,|";
            String cname = "", what_version = "", date = "";
            String query = "select cname,vname,dat from setting_user";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                cname = r.getString(1);
                what_version = r.getString(2);
                date = r.getString(3);
            }
            what_version = AES.decrypt(what_version, secretKey);
            cname = AES.decrypt(cname, secretKey);
            
            // Set version label and activation button state
            if (what_version != null && !what_version.trim().isEmpty()) {
                versionl.setText("🏆 " + what_version + " Edition");
                
                // Check if it's a full version
                if (what_version.toLowerCase().contains("full") || what_version.toLowerCase().contains("professional") || what_version.toLowerCase().contains("premium")) {
                    // Already activated - change button appearance
                    activatel.setText("✓ Full Version Activated");
                    activatel.setToolTipText("Full version is already activated");
                    activatel.setVisible(true);
                } else {
                    // Trial version - show activation button
                    activatel.setText("⚡ Activate Full Version");
                    activatel.setToolTipText("Click here to activate full version");
                    activatel.setVisible(true);
                }
            } else {
                versionl.setText("🏆 Swayam Professional Edition");
                activatel.setText("⚡ Activate Full Version");
                activatel.setToolTipText("Click here to activate full version");
                activatel.setVisible(true);
            }
            
            if (cname != null && !cname.trim().isEmpty()) {
                // Replace any "BBS" references with "Swayam"
                cname = cname.replace("BBS", "Swayam").replace("bbs", "Swayam");
                cnamel.setText(cname + "  ");
            }

            // version details ends

            String shopName = null;
            query = "select cname, hmany from setting_bill";
            r = util.doQuery(query);
            while (r.next()) {
                shopName = r.getString(1);
                hmany = r.getInt(2);
            }

            if (shopName != null && !shopName.trim().isEmpty() && !shopName.trim().equals(".")) {
                // Replace any "BBS" references with "Swayam"
                shopName = shopName.replace("BBS", "Swayam").replace("bbs", "Swayam");
                cnamel.setText(shopName + "  ");
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String check_internet_connect() {
        String connection = "Yes";
        try {
            URL url = new URL("http://google.com");
            URLConnection con = url.openConnection();
            con.getInputStream();
        } catch (IOException e) {
            System.out.println(e);
            connection = "No";
        }
        return connection;
    }

    final void button_short() {
        salesbutton.setText("<html><b>Sales</b>&nbsp(Alt+S)&nbsp&nbsp&nbsp<html>");
        purchasebutton.setText("<html><b>Purchase</b>&nbsp(Alt+P)&nbsp&nbsp&nbsp<html>");
        estimatebutton.setText("<html><b>Estimate</b>&nbsp(Alt+M)&nbsp&nbsp&nbsp<html>");
        itembutton.setText("<html><b>Item</b>&nbsp(Alt+I)&nbsp&nbsp&nbsp<html>");
        cashbookbutton.setText("<html><b>Voucher</b>&nbsp(Alt+V)&nbsp&nbsp&nbsp<html>");
        customerbutton.setText("<html><b>Customer</b>&nbsp(Alt+C)&nbsp&nbsp&nbsp<html>");
        searchbutton.setText("<html><b>Search</b>&nbsp(Alt+H)&nbsp&nbsp&nbsp<html>");
        smsbutton.setText("<html><b>SMS</b>&nbsp(Alt+Y)&nbsp&nbsp&nbsp<html>");
        settingbutton.setText("<html><b>Setting</b>&nbsp(Alt+T)&nbsp&nbsp&nbsp<html>");
        logbutton.setText("<html><b>Log out</b>&nbsp(Alt+L)&nbsp&nbsp&nbsp<html>");
        exitbutton.setText("<html><b>Exit</b>&nbsp(Alt+E)&nbsp&nbsp&nbsp<html>");

        dayclose.setText("<html><b><u>Day Book Close</u></b><html>");
        clearl.setText("<html><b><u>Refresh Page</u></b><html>");

        javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            setIconImage(icon.getImage());
        }
        Rectangle maxBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        this.setSize(maxBounds.width, maxBounds.height);
    }

    final void get_details() {
        try {
            int scount = 0;
            double sale = 0;
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("yyyy/MM/dd");
            String today = g.format(d);
            String query = "select sum(net),count(billno) from sales where dat='" + today + "'";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                sale = r.getDouble(1);
                scount = r.getInt(2);
            }
            String sale1 = String.format("%." + hmany + "f", sale);
            jLabel12.setText(sale1);
            jLabel8.setText("" + scount);

            int purchase = 0;
            query = "select count(grn) from purchase where dat='" + today + "'";
            r = util.doQuery(query);
            while (r.next()) {
                purchase = r.getInt(1);
            }
            jLabel6.setText("" + purchase);

            int po = 0;
            query = "select count(grn) from po_entry where dat='" + today + "'";
            r = util.doQuery(query);
            while (r.next()) {
                po = r.getInt(1);
            }
            jLabel15.setText("" + po);

            double pay = 0;
            query = "select sum(net) from cust_pay where dat='" + today + "'";
            r = util.doQuery(query);
            while (r.next()) {
                pay = r.getDouble(1);
            }
            String pay1 = String.format("%." + hmany + "f", pay);
            jLabel10.setText(pay1);

            double mrp_value = 0;
            query = "select sum(prate*quan) from stock where quan >0";
            r = util.doQuery(query);
            while (r.next()) {
                mrp_value = r.getDouble(1);
            }
            String mrp2 = String.format("%." + hmany + "f", mrp_value);
            jLabel4.setText(mrp2);

            int items = 0;
            query = "select count(ino) from item";
            r = util.doQuery(query);
            while (r.next()) {
                items = r.getInt(1);
            }
            jLabel19.setText("" + items);

            int minstock = 0;
            query = "select distinct count(a.ino) from stock a,item b where a.ino=b.ino and quan<minstock";
            r = util.doQuery(query);
            while (r.next()) {
                minstock = r.getInt(1);
            }
            jLabel17.setText("" + minstock);

            int maxstock = 0;
            query = "select distinct count(a.ino) from stock a,item b where a.ino=b.ino and quan>maxstock";
            r = util.doQuery(query);
            while (r.next()) {
                maxstock = r.getInt(1);
            }
            jLabel21.setText("" + maxstock);

            int nillstock = 0;
            query = "select distinct count(ino) from stock where quan<=0";
            r = util.doQuery(query);
            while (r.next()) {
                nillstock = r.getInt(1);
            }
            jLabel2.setText("" + nillstock);

            double cust_bal = 0;
            query = "select sum(tot-paid) from cust_bal";
            r = util.doQuery(query);
            while (r.next()) {
                cust_bal = r.getDouble(1);
            }
            String cust_bal2 = String.format("%." + hmany + "f", cust_bal);
            jLabel24.setText(cust_bal2);

            double ven_bal = 0;
            query = "select sum(tot-paid) from ven_bal";
            r = util.doQuery(query);
            while (r.next()) {
                ven_bal = r.getDouble(1);
            }
            String ven_bal2 = String.format("%." + hmany + "f", ven_bal);
            jLabel27.setText(ven_bal2);

            int vouchers = 0;
            query = "select count(billno) from account_voucher";
            r = util.doQuery(query);
            while (r.next()) {
                vouchers = r.getInt(1);
            }
            jLabel29.setText("" + vouchers);

            int cust_over_due = 0;
            query = "select count(billno) from cust_bal where ddate < '" + today + "' and tot-paid>0";
            r = util.doQuery(query);
            while (r.next()) {
                cust_over_due = r.getInt(1);
            }
            jLabel37.setText("" + cust_over_due);

            int ven_over_due = 0;
            query = "select count(billno) from ven_bal where ddate < '" + today + "' and tot-paid>0";
            r = util.doQuery(query);
            while (r.next()) {
                ven_over_due = r.getInt(1);
            }
            jLabel39.setText("" + ven_over_due);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_validation() {
        PermissionManager pm = new PermissionManager(util);
        pm.loadPermissions(username);

        // --- 1. DEFAULT: Hide All Modules & Sensitive Data ---
        jMenu10.setVisible(false); // Sales
        jMenu3.setVisible(false); // Purchase
        jMenu1.setVisible(false); // Item
        jMenu5.setVisible(false); // Stock
        jMenu11.setVisible(false); // PO
        jMenu19.setVisible(false); // Packing
        jMenu12.setVisible(false); // Accounts
        jMenu2.setVisible(false); // Supplier
        jMenu7.setVisible(false); // Customer
        jMenu15.setVisible(false); // HR
        jMenu6.setVisible(false); // GSTR
        jMenu13.setVisible(false); // SMS
        jMenu17.setVisible(false); // Email
        jMenu14.setVisible(false); // Barcode
        jMenu18.setVisible(false); // Loyalty
        jMenu9.setVisible(false); // Users
        jMenu16.setVisible(false); // Backup
        jMenu21.setVisible(false); // Estimate

        // FIX: Ensure these are hidden by default
        jMenu22.setVisible(false); // Payroll
        jMenu23.setVisible(false); // Attendance
        jMenu20.setVisible(false); // MIS (Was missing)
        jMenu4.setVisible(false); // Setting (Was missing)

        // Hide Toolbar Buttons
        salesbutton.setVisible(false);
        purchasebutton.setVisible(false);
        itembutton.setVisible(false);
        searchbutton.setVisible(false);
        cashbookbutton.setVisible(false);
        customerbutton.setVisible(false);
        smsbutton.setVisible(false);
        settingbutton.setVisible(false);
        estimatebutton.setVisible(false);

        // Hide Dashboard Utilities
        clearl.setVisible(false);
        dayclose.setVisible(false);

        // --- 2. LICENSE OWNER / SUPER ADMIN FLOW ---
        if ("License Owner".equalsIgnoreCase(user_type)) {
            // Hide Standard Dashboard for Owner (Owner uses AdminDashboard)
            jToolBar1.setVisible(false);
            jPanel4.setVisible(false);

            // Show User Menu only
            jMenu9.setVisible(true);

            // Show Admin Dashboard
            try {
                AdminDashboard dashboard = new AdminDashboard(util, user_type);
                jDesktopPane1.add(dashboard);
                dashboard.setVisible(true);
                try {
                    dashboard.setMaximum(true);
                } catch (java.beans.PropertyVetoException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        // --- 3. APPLY USER PERMISSIONS ---

        // SALES
        if (pm.hasPermission("Sales")) {
            jMenu10.setVisible(true);
            salesbutton.setVisible(true);
            jPanel1.setVisible(true); // Sales Amount Tile
            jPanel2.setVisible(true); // Sales Count Tile
        } else {
            jPanel1.setVisible(false);
            jPanel2.setVisible(false);
        }

        // PURCHASE
        if (pm.hasPermission("Purchase")) {
            jMenu3.setVisible(true);
            purchasebutton.setVisible(true);
            jPanel6.setVisible(true); // Purchase Entries Tile
        } else {
            jPanel6.setVisible(false);
        }

        // ESTIMATE
        if (pm.hasPermission("Estimate")) {
            jMenu21.setVisible(true);
            estimatebutton.setVisible(true);
        }

        // ITEM
        if (pm.hasPermission("Item")) {
            jMenu1.setVisible(true);
            itembutton.setVisible(true);
            searchbutton.setVisible(true);
            jPanel10.setVisible(true); // Item Count Tile
        } else {
            jPanel10.setVisible(false);
        }

        // STOCK
        if (pm.hasPermission("Stock")) {
            jMenu5.setVisible(true);
            jPanel7.setVisible(true); // Stock Value Tile
            jPanel8.setVisible(true); // Re-order Tile
            jPanel9.setVisible(true); // Nill Stock Tile
            jPanel11.setVisible(true); // Excess Qty Tile
        } else {
            jPanel7.setVisible(false);
            jPanel8.setVisible(false);
            jPanel9.setVisible(false);
            jPanel11.setVisible(false);
        }

        // PO (Purchase Order)
        if (pm.hasPermission("PO")) {
            jMenu11.setVisible(true);
            jPanel3.setVisible(true); // PO Count Tile
        } else {
            jPanel3.setVisible(false);
        }

        // PACKING
        if (pm.hasPermission("Packing")) {
            jMenu19.setVisible(true);
        }

        // ACCOUNTS
        if (pm.hasPermission("Accounts")) {
            jMenu12.setVisible(true);
            cashbookbutton.setVisible(true);
            jPanel16.setVisible(true); // Vouchers Tile
        } else {
            jPanel16.setVisible(false);
        }

        // SUPPLIER
        if (pm.hasPermission("Supplier")) {
            System.out.println("Supplier permission granted");
            jMenu2.setVisible(true);
            jPanel15.setVisible(true); // Supplier Bal Tile
            jPanel14.setVisible(true); // Supplier Overdue Tile
        } else {
            jPanel15.setVisible(false);
            jPanel14.setVisible(false);
        }

        // CUSTOMER
        if (pm.hasPermission("Customer")) {
            jMenu7.setVisible(true);
            customerbutton.setVisible(true);
            jPanel13.setVisible(true); // Cust Bal Tile
            jPanel5.setVisible(true); // Cust Pay Tile
            jPanel17.setVisible(true); // Cust Overdue Tile
        } else {
            jPanel13.setVisible(false);
            jPanel5.setVisible(false);
            jPanel17.setVisible(false);
        }

        // HR
        if (pm.hasPermission("HR")) {
            jMenu15.setVisible(true);
        }

        // PAYROLL
        if (pm.hasPermission("Payroll")) {
            jMenu22.setVisible(true);
        }

        // ATTENDANCE
        if (pm.hasPermission("Attendance")) {
            jMenu23.setVisible(true);
        }

        // MIS REPORTS (Was Missing)
        if (pm.hasPermission("MIS") || pm.hasPermission("MIS Reports")) {
            jMenu20.setVisible(true);
        }

        // SETTINGS (Was Missing)
        if (pm.hasPermission("Setting") || pm.hasPermission("Settings")) {
            jMenu4.setVisible(true);
            settingbutton.setVisible(true);
        }

        // TAX REPORTS
        if (pm.hasPermission("Tax Reports")) {
            jMenu6.setVisible(true);
        }

        // SMS
        if (pm.hasPermission("SMS")) {
            jMenu13.setVisible(true);
            smsbutton.setVisible(true);
        }

        // EMAIL
        if (pm.hasPermission("Email")) {
            jMenu17.setVisible(true);
        }

        // BARCODE
        if (pm.hasPermission("Manual Barcode")) {
            jMenu14.setVisible(true);
        }

        // LOYALTY
        if (pm.hasPermission("Loyalty")) {
            jMenu18.setVisible(true);
        }

        // BACKUP
        if (pm.hasPermission("Backup")) {
            jMenu16.setVisible(true);
            dayclose.setVisible(true);
        }

        // MAIN DASHBOARD VISIBILITY
        if (pm.hasPermission("Home Page Display")) {
            jPanel4.setVisible(true);
            get_details();
            clearl.setVisible(true);
        } else {
            jPanel4.setVisible(false);
            clearl.setVisible(false);
        }
    }

    public menu_form() {
        initComponents();
        util = new DataUtil();
        get_defauls();
        button_short();
        versionl.setVisible(true);
        activatel.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem164 = new javax.swing.JMenuItem();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jToolBar1 = new javax.swing.JToolBar();
        salesbutton = new javax.swing.JButton();
        purchasebutton = new javax.swing.JButton();
        estimatebutton = new javax.swing.JButton();
        itembutton = new javax.swing.JButton();
        searchbutton = new javax.swing.JButton();
        cashbookbutton = new javax.swing.JButton();
        customerbutton = new javax.swing.JButton();
        smsbutton = new javax.swing.JButton();
        settingbutton = new javax.swing.JButton();
        logbutton = new javax.swing.JButton();
        exitbutton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        versionl = new javax.swing.JLabel();
        activatel = new javax.swing.JLabel();
        cnamel = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        dayclose = new javax.swing.JLabel();
        clearl = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu10 = new javax.swing.JMenu();
        jSeparator85 = new javax.swing.JPopupMenu.Separator();
        jMenuItem38 = new javax.swing.JMenuItem();
        jMenuItem39 = new javax.swing.JMenuItem();
        jSeparator88 = new javax.swing.JPopupMenu.Separator();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();
        jSeparator90 = new javax.swing.JPopupMenu.Separator();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jSeparator87 = new javax.swing.JPopupMenu.Separator();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem114 = new javax.swing.JMenuItem();
        jSeparator89 = new javax.swing.JPopupMenu.Separator();
        jMenuItem31 = new javax.swing.JMenuItem();
        jMenuItem32 = new javax.swing.JMenuItem();
        jSeparator92 = new javax.swing.JPopupMenu.Separator();
        jMenu3 = new javax.swing.JMenu();
        jSeparator68 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem34 = new javax.swing.JMenuItem();
        jSeparator69 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem84 = new javax.swing.JMenuItem();
        jSeparator70 = new javax.swing.JPopupMenu.Separator();
        jMenuItem85 = new javax.swing.JMenuItem();
        jMenuItem86 = new javax.swing.JMenuItem();
        jMenuItem87 = new javax.swing.JMenuItem();
        jMenuItem88 = new javax.swing.JMenuItem();
        jSeparator71 = new javax.swing.JPopupMenu.Separator();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem36 = new javax.swing.JMenuItem();
        jSeparator91 = new javax.swing.JPopupMenu.Separator();
        jMenu21 = new javax.swing.JMenu();
        jSeparator125 = new javax.swing.JPopupMenu.Separator();
        jMenuItem118 = new javax.swing.JMenuItem();
        jMenuItem134 = new javax.swing.JMenuItem();
        jSeparator126 = new javax.swing.JPopupMenu.Separator();
        jMenuItem119 = new javax.swing.JMenuItem();
        jMenuItem120 = new javax.swing.JMenuItem();
        jSeparator127 = new javax.swing.JPopupMenu.Separator();
        jMenuItem121 = new javax.swing.JMenuItem();
        jMenuItem122 = new javax.swing.JMenuItem();
        jMenuItem123 = new javax.swing.JMenuItem();
        jMenuItem124 = new javax.swing.JMenuItem();
        jMenuItem125 = new javax.swing.JMenuItem();
        jMenuItem126 = new javax.swing.JMenuItem();
        jSeparator128 = new javax.swing.JPopupMenu.Separator();
        jMenuItem127 = new javax.swing.JMenuItem();
        jMenuItem128 = new javax.swing.JMenuItem();
        jMenuItem129 = new javax.swing.JMenuItem();
        jMenuItem130 = new javax.swing.JMenuItem();
        jMenuItem131 = new javax.swing.JMenuItem();
        jMenuItem132 = new javax.swing.JMenuItem();
        jMenuItem133 = new javax.swing.JMenuItem();
        jSeparator129 = new javax.swing.JPopupMenu.Separator();
        jMenuItem135 = new javax.swing.JMenuItem();
        jMenuItem136 = new javax.swing.JMenuItem();
        jSeparator130 = new javax.swing.JPopupMenu.Separator();
        jMenu1 = new javax.swing.JMenu();
        jSeparator72 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem50 = new javax.swing.JMenuItem();
        jMenuItem103 = new javax.swing.JMenuItem();
        jMenuItem63 = new javax.swing.JMenuItem();
        jSeparator74 = new javax.swing.JPopupMenu.Separator();
        jMenu5 = new javax.swing.JMenu();
        jSeparator63 = new javax.swing.JPopupMenu.Separator();
        jMenuItem51 = new javax.swing.JMenuItem();
        jMenuItem67 = new javax.swing.JMenuItem();
        jMenuItem78 = new javax.swing.JMenuItem();
        jMenuItem163 = new javax.swing.JMenuItem();
        jMenuItemTerminal = new javax.swing.JMenuItem();
        jSeparator64 = new javax.swing.JPopupMenu.Separator();
        jMenuItem66 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem94 = new javax.swing.JMenuItem();
        jMenuItem95 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem52 = new javax.swing.JMenuItem();
        jMenuItem79 = new javax.swing.JMenuItem();
        jSeparator95 = new javax.swing.JPopupMenu.Separator();
        jMenuItem73 = new javax.swing.JMenuItem();
        jMenuItem74 = new javax.swing.JMenuItem();
        jSeparator106 = new javax.swing.JPopupMenu.Separator();
        jMenu11 = new javax.swing.JMenu();
        jSeparator100 = new javax.swing.JPopupMenu.Separator();
        jMenuItem61 = new javax.swing.JMenuItem();
        jMenuItem91 = new javax.swing.JMenuItem();
        jMenuItem60 = new javax.swing.JMenuItem();
        jSeparator99 = new javax.swing.JPopupMenu.Separator();
        jMenuItem92 = new javax.swing.JMenuItem();
        jSeparator118 = new javax.swing.JPopupMenu.Separator();
        jMenu19 = new javax.swing.JMenu();
        jSeparator119 = new javax.swing.JPopupMenu.Separator();
        jMenuItem97 = new javax.swing.JMenuItem();
        jMenuItem98 = new javax.swing.JMenuItem();
        jSeparator120 = new javax.swing.JPopupMenu.Separator();
        jMenu12 = new javax.swing.JMenu();
        jSeparator93 = new javax.swing.JPopupMenu.Separator();
        jMenuItem42 = new javax.swing.JMenuItem();
        jMenuItem140 = new javax.swing.JMenuItem();
        jMenuItem41 = new javax.swing.JMenuItem();
        jMenuItem43 = new javax.swing.JMenuItem();
        jSeparator94 = new javax.swing.JPopupMenu.Separator();
        jMenuItem104 = new javax.swing.JMenuItem();
        jMenuItem99 = new javax.swing.JMenuItem();
        jMenuItem100 = new javax.swing.JMenuItem();
        jMenuItem138 = new javax.swing.JMenuItem();
        jMenuItem137 = new javax.swing.JMenuItem();
        jMenuItem139 = new javax.swing.JMenuItem();
        jMenuItem142 = new javax.swing.JMenuItem();
        jMenuItem143 = new javax.swing.JMenuItem();
        jSeparator131 = new javax.swing.JPopupMenu.Separator();
        jMenuItem44 = new javax.swing.JMenuItem();
        jMenuItem101 = new javax.swing.JMenuItem();
        jMenuItem102 = new javax.swing.JMenuItem();
        jMenuItem141 = new javax.swing.JMenuItem();
        jSeparator98 = new javax.swing.JPopupMenu.Separator();
        jMenu2 = new javax.swing.JMenu();
        jSeparator75 = new javax.swing.JPopupMenu.Separator();
        jMenuItem54 = new javax.swing.JMenuItem();
        jMenuItem147 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem96 = new javax.swing.JMenuItem();
        jMenuItem149 = new javax.swing.JMenuItem();
        jSeparator76 = new javax.swing.JPopupMenu.Separator();
        jMenuItem55 = new javax.swing.JMenuItem();
        jMenuItem59 = new javax.swing.JMenuItem();
        jMenuItem56 = new javax.swing.JMenuItem();
        jMenuItem148 = new javax.swing.JMenuItem();
        jSeparator108 = new javax.swing.JPopupMenu.Separator();
        jMenuItem109 = new javax.swing.JMenuItem();
        jSeparator96 = new javax.swing.JPopupMenu.Separator();
        jMenu7 = new javax.swing.JMenu();
        jSeparator80 = new javax.swing.JPopupMenu.Separator();
        jMenuItem45 = new javax.swing.JMenuItem();
        jMenuItem144 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem93 = new javax.swing.JMenuItem();
        jMenuItem146 = new javax.swing.JMenuItem();
        jSeparator65 = new javax.swing.JPopupMenu.Separator();
        jMenuItem46 = new javax.swing.JMenuItem();
        jMenuItem47 = new javax.swing.JMenuItem();
        jMenuItem48 = new javax.swing.JMenuItem();
        jMenuItem58 = new javax.swing.JMenuItem();
        jMenuItem57 = new javax.swing.JMenuItem();
        jMenuItem145 = new javax.swing.JMenuItem();
        jMenuItem162 = new javax.swing.JMenuItem();
        jSeparator86 = new javax.swing.JPopupMenu.Separator();
        jMenuItem75 = new javax.swing.JMenuItem();
        jMenuItem76 = new javax.swing.JMenuItem();
        jMenuItem77 = new javax.swing.JMenuItem();
        jSeparator107 = new javax.swing.JPopupMenu.Separator();
        jMenu15 = new javax.swing.JMenu();
        jSeparator103 = new javax.swing.JPopupMenu.Separator();
        jMenuItem64 = new javax.swing.JMenuItem();
        jMenuItem65 = new javax.swing.JMenuItem();
        jSeparator109 = new javax.swing.JPopupMenu.Separator();
        jMenu22 = new javax.swing.JMenu();
        jSeparator132 = new javax.swing.JPopupMenu.Separator();
        jMenuItem150 = new javax.swing.JMenuItem();
        jMenuItem153 = new javax.swing.JMenuItem();
        jMenuItem154 = new javax.swing.JMenuItem();
        jSeparator134 = new javax.swing.JPopupMenu.Separator();
        jMenuItem151 = new javax.swing.JMenuItem();
        jMenuItem155 = new javax.swing.JMenuItem();
        jMenuItem156 = new javax.swing.JMenuItem();
        jMenuItem157 = new javax.swing.JMenuItem();
        jSeparator135 = new javax.swing.JPopupMenu.Separator();
        jMenu23 = new javax.swing.JMenu();
        jSeparator133 = new javax.swing.JPopupMenu.Separator();
        jMenuItem152 = new javax.swing.JMenuItem();
        jMenuItem158 = new javax.swing.JMenuItem();
        jMenuItem159 = new javax.swing.JMenuItem();
        jSeparator136 = new javax.swing.JPopupMenu.Separator();
        jMenu6 = new javax.swing.JMenu();
        jSeparator79 = new javax.swing.JPopupMenu.Separator();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem37 = new javax.swing.JMenuItem();
        jMenuItem33 = new javax.swing.JMenuItem();
        jSeparator66 = new javax.swing.JPopupMenu.Separator();
        jMenuItem69 = new javax.swing.JMenuItem();
        jMenuItem68 = new javax.swing.JMenuItem();
        jMenuItem71 = new javax.swing.JMenuItem();
        jMenuItem70 = new javax.swing.JMenuItem();
        jSeparator104 = new javax.swing.JPopupMenu.Separator();
        jMenuItem72 = new javax.swing.JMenuItem();
        jSeparator105 = new javax.swing.JPopupMenu.Separator();
        jMenu13 = new javax.swing.JMenu();
        jSeparator67 = new javax.swing.JPopupMenu.Separator();
        jMenuItem49 = new javax.swing.JMenuItem();
        jMenuItem53 = new javax.swing.JMenuItem();
        jSeparator97 = new javax.swing.JPopupMenu.Separator();
        jMenu17 = new javax.swing.JMenu();
        jSeparator113 = new javax.swing.JPopupMenu.Separator();
        jMenuItem81 = new javax.swing.JMenuItem();
        jSeparator114 = new javax.swing.JPopupMenu.Separator();
        jMenu14 = new javax.swing.JMenu();
        jSeparator101 = new javax.swing.JPopupMenu.Separator();
        jMenuItem62 = new javax.swing.JMenuItem();
        jSeparator102 = new javax.swing.JPopupMenu.Separator();
        jMenu18 = new javax.swing.JMenu();
        jSeparator115 = new javax.swing.JPopupMenu.Separator();
        jMenuItem83 = new javax.swing.JMenuItem();
        jMenuItem89 = new javax.swing.JMenuItem();
        jMenuItem82 = new javax.swing.JMenuItem();
        jSeparator116 = new javax.swing.JPopupMenu.Separator();
        jMenuItem90 = new javax.swing.JMenuItem();
        jSeparator117 = new javax.swing.JPopupMenu.Separator();
        jMenu20 = new javax.swing.JMenu();
        jSeparator121 = new javax.swing.JPopupMenu.Separator();
        jMenuItem105 = new javax.swing.JMenuItem();
        jMenuItem106 = new javax.swing.JMenuItem();
        jMenuItem160 = new javax.swing.JMenuItem();
        jMenuItem107 = new javax.swing.JMenuItem();
        jMenuItem110 = new javax.swing.JMenuItem();
        jSeparator122 = new javax.swing.JPopupMenu.Separator();
        jMenuItem111 = new javax.swing.JMenuItem();
        jMenuItem112 = new javax.swing.JMenuItem();
        jMenuItem113 = new javax.swing.JMenuItem();
        jMenuItem115 = new javax.swing.JMenuItem();
        jSeparator123 = new javax.swing.JPopupMenu.Separator();
        jMenuItem116 = new javax.swing.JMenuItem();
        jMenuItem117 = new javax.swing.JMenuItem();
        jSeparator124 = new javax.swing.JPopupMenu.Separator();
        jMenu9 = new javax.swing.JMenu();
        jSeparator83 = new javax.swing.JPopupMenu.Separator();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jSeparator84 = new javax.swing.JPopupMenu.Separator();
        jMenu16 = new javax.swing.JMenu();
        jSeparator111 = new javax.swing.JPopupMenu.Separator();
        jMenuItem80 = new javax.swing.JMenuItem();
        jSeparator112 = new javax.swing.JPopupMenu.Separator();
        jMenu4 = new javax.swing.JMenu();
        jSeparator78 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem40 = new javax.swing.JMenuItem();
        jSeparator77 = new javax.swing.JPopupMenu.Separator();
        jMenu8 = new javax.swing.JMenu();
        jSeparator81 = new javax.swing.JPopupMenu.Separator();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jSeparator82 = new javax.swing.JPopupMenu.Separator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // Modern Material Design Toolbar with gradient and rounded corners
        jToolBar1 = new javax.swing.JToolBar() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING,
                        java.awt.RenderingHints.VALUE_RENDER_QUALITY);

                // Material Design elevation shadow
                for (int i = 0; i < 4; i++) {
                    int alpha = Math.max(0, 20 - i * 5);
                    g2d.setColor(new java.awt.Color(0, 0, 0, alpha));
                    g2d.fillRoundRect(0, i + 1, getWidth(), getHeight() - i, 12, 12);
                }

                // Professional gradient background
                java.awt.Color color1 = new java.awt.Color(25, 118, 210, 255); // Material Blue 600
                java.awt.Color color2 = new java.awt.Color(21, 101, 192, 255); // Material Blue 700
                java.awt.Color color3 = new java.awt.Color(30, 136, 229, 255); // Material Blue 500

                java.awt.GradientPaint gradient = new java.awt.GradientPaint(
                        0, 0, color3, 0, getHeight(), color2);
                g2d.setPaint(gradient);

                // Rounded toolbar background
                java.awt.geom.RoundRectangle2D toolbar = new java.awt.geom.RoundRectangle2D.Float(
                        0, 0, getWidth(), getHeight(), 12, 12);
                g2d.fill(toolbar);

                // Subtle top highlight
                java.awt.GradientPaint highlight = new java.awt.GradientPaint(
                        0, 0, new java.awt.Color(255, 255, 255, 40),
                        0, getHeight() / 3, new java.awt.Color(255, 255, 255, 0));
                g2d.setPaint(highlight);
                g2d.fill(toolbar);

                // Bottom accent line
                g2d.setColor(new java.awt.Color(255, 255, 255, 60));
                g2d.fillRoundRect(0, getHeight() - 2, getWidth(), 2, 2, 2);

                g2d.dispose();
            }
        };
        jToolBar1.setOpaque(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));
        jToolBar1.setFloatable(false); // Modern toolbars shouldn't be floatable

        // Force proper rendering
        jToolBar1.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                jToolBar1.revalidate();
                jToolBar1.repaint();
            }
        });

        // Modern Material Design Sales Button with proper hover
        salesbutton = new javax.swing.JButton() {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (isHovered) {
                    java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                    g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                            java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new java.awt.Color(255, 255, 255, 50));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2d.dispose();
                }
                super.paintComponent(g);
            }

            public void setHovered(boolean hovered) {
                this.isHovered = hovered;
                repaint();
            }
        };
        salesbutton.setFont(new java.awt.Font("Segoe UI", 1, 12));
        salesbutton.setForeground(new java.awt.Color(255, 255, 255, 240));
        salesbutton.setIcon(ColorConstants.loadIcon("/icons/pos45.png"));
        salesbutton.setMnemonic('s');
        salesbutton.setText("💰 Sales");
        salesbutton.setToolTipText("Open Sales Module (Alt+S)");
        salesbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        salesbutton.setFocusable(false);
        salesbutton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        salesbutton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        salesbutton.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));
        salesbutton.setOpaque(false);
        salesbutton.setContentAreaFilled(false);
        salesbutton.setBorderPainted(false);

        // Proper hover effect with custom button
        salesbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ((javax.swing.JButton) evt.getSource()).putClientProperty("setHovered", true);
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), true);
                } catch (Exception e) {
                    // Fallback - force repaint
                    evt.getComponent().repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ((javax.swing.JButton) evt.getSource()).putClientProperty("setHovered", false);
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), false);
                } catch (Exception e) {
                    // Fallback - force repaint
                    evt.getComponent().repaint();
                }
            }
        });
        salesbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesbuttonActionPerformed(evt);
            }
        });
        jToolBar1.add(salesbutton);

        jToolBar1.addSeparator();

        // Modern Material Design Purchase Button with proper hover
        purchasebutton = new javax.swing.JButton() {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (isHovered) {
                    java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                    g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                            java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new java.awt.Color(255, 255, 255, 50));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2d.dispose();
                }
                super.paintComponent(g);
            }

            public void setHovered(boolean hovered) {
                this.isHovered = hovered;
                repaint();
            }
        };
        purchasebutton.setFont(new java.awt.Font("Segoe UI", 1, 12));
        purchasebutton.setForeground(new java.awt.Color(255, 255, 255, 240));
        purchasebutton.setIcon(ColorConstants.loadIcon("/icons/load45.jpg.png"));
        purchasebutton.setMnemonic('p');
        purchasebutton.setText("📦 Purchase");
        purchasebutton.setToolTipText("Open Purchase Module (Alt+P)");
        purchasebutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        purchasebutton.setFocusable(false);
        purchasebutton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        purchasebutton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        purchasebutton.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));
        purchasebutton.setOpaque(false);
        purchasebutton.setContentAreaFilled(false);
        purchasebutton.setBorderPainted(false);

        purchasebutton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), true);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), false);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }
        });
        purchasebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchasebuttonActionPerformed(evt);
            }
        });
        jToolBar1.add(purchasebutton);

        jToolBar1.addSeparator();

        // Modern Material Design Estimate Button with proper hover
        estimatebutton = new javax.swing.JButton() {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (isHovered) {
                    java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                    g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                            java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new java.awt.Color(255, 255, 255, 50));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2d.dispose();
                }
                super.paintComponent(g);
            }

            public void setHovered(boolean hovered) {
                this.isHovered = hovered;
                repaint();
            }
        };
        estimatebutton.setFont(new java.awt.Font("Segoe UI", 1, 12));
        estimatebutton.setForeground(new java.awt.Color(255, 255, 255, 240));
        estimatebutton.setIcon(ColorConstants.loadIcon("/icons/est45.png"));
        estimatebutton.setMnemonic('m');
        estimatebutton.setText("📊 Estimate");
        estimatebutton.setToolTipText("Create Estimates (Alt+M)");
        estimatebutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        estimatebutton.setFocusable(false);
        estimatebutton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        estimatebutton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        estimatebutton.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));
        estimatebutton.setOpaque(false);
        estimatebutton.setContentAreaFilled(false);
        estimatebutton.setBorderPainted(false);

        estimatebutton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), true);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), false);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }
        });
        estimatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estimatebuttonActionPerformed(evt);
            }
        });
        jToolBar1.add(estimatebutton);

        jToolBar1.addSeparator();

        // Modern Material Design Item Button with proper hover
        itembutton = new javax.swing.JButton() {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (isHovered) {
                    java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                    g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                            java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new java.awt.Color(255, 255, 255, 50));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2d.dispose();
                }
                super.paintComponent(g);
            }

            public void setHovered(boolean hovered) {
                this.isHovered = hovered;
                repaint();
            }
        };
        itembutton.setFont(new java.awt.Font("Segoe UI", 1, 12));
        itembutton.setForeground(new java.awt.Color(255, 255, 255, 240));
        itembutton.setIcon(ColorConstants.loadIcon("/icons/item45.png"));
        itembutton.setMnemonic('i');
        itembutton.setText("➕ New Item");
        itembutton.setToolTipText("Add New Item (Alt+I)");
        itembutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        itembutton.setFocusable(false);
        itembutton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        itembutton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        itembutton.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));
        itembutton.setOpaque(false);
        itembutton.setContentAreaFilled(false);
        itembutton.setBorderPainted(false);

        itembutton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), true);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), false);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }
        });
        itembutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itembuttonActionPerformed(evt);
            }
        });
        jToolBar1.add(itembutton);

        // Modern Material Design Search Button with proper hover
        searchbutton = new javax.swing.JButton() {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (isHovered) {
                    java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                    g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                            java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new java.awt.Color(255, 255, 255, 50));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2d.dispose();
                }
                super.paintComponent(g);
            }

            public void setHovered(boolean hovered) {
                this.isHovered = hovered;
                repaint();
            }
        };
        searchbutton.setFont(new java.awt.Font("Segoe UI", 1, 12));
        searchbutton.setForeground(new java.awt.Color(255, 255, 255, 240));
        searchbutton.setIcon(ColorConstants.loadIcon("/icons/search45.png"));
        searchbutton.setMnemonic('h');
        searchbutton.setText("🔍 Search");
        searchbutton.setToolTipText("Search Items & Records (Alt+H)");
        searchbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchbutton.setFocusable(false);
        searchbutton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        searchbutton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        searchbutton.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));
        searchbutton.setOpaque(false);
        searchbutton.setContentAreaFilled(false);
        searchbutton.setBorderPainted(false);

        searchbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), true);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), false);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }
        });
        searchbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbuttonActionPerformed(evt);
            }
        });
        jToolBar1.add(searchbutton);

        jToolBar1.addSeparator();

        // Modern Material Design Voucher Button with proper hover
        cashbookbutton = new javax.swing.JButton() {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (isHovered) {
                    java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                    g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                            java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new java.awt.Color(255, 255, 255, 50));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2d.dispose();
                }
                super.paintComponent(g);
            }

            public void setHovered(boolean hovered) {
                this.isHovered = hovered;
                repaint();
            }
        };
        cashbookbutton.setFont(new java.awt.Font("Segoe UI", 1, 12));
        cashbookbutton.setForeground(new java.awt.Color(255, 255, 255, 240));
        cashbookbutton.setIcon(ColorConstants.loadIcon("/icons/cashbook45.png"));
        cashbookbutton.setMnemonic('v');
        cashbookbutton.setText("📝 Voucher");
        cashbookbutton.setToolTipText("Manage Vouchers (Alt+V)");
        cashbookbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cashbookbutton.setFocusable(false);
        cashbookbutton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cashbookbutton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cashbookbutton.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));
        cashbookbutton.setOpaque(false);
        cashbookbutton.setContentAreaFilled(false);
        cashbookbutton.setBorderPainted(false);

        cashbookbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), true);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), false);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }
        });
        cashbookbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashbookbuttonActionPerformed(evt);
            }
        });
        jToolBar1.add(cashbookbutton);

        // Modern Material Design Customer Button with proper hover
        customerbutton = new javax.swing.JButton() {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (isHovered) {
                    java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                    g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                            java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new java.awt.Color(255, 255, 255, 50));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2d.dispose();
                }
                super.paintComponent(g);
            }

            public void setHovered(boolean hovered) {
                this.isHovered = hovered;
                repaint();
            }
        };
        customerbutton.setFont(new java.awt.Font("Segoe UI", 1, 12));
        customerbutton.setForeground(new java.awt.Color(255, 255, 255, 240));
        customerbutton.setIcon(ColorConstants.loadIcon("/icons/cust45.png"));
        customerbutton.setMnemonic('c');
        customerbutton.setText("👥 Customer");
        customerbutton.setToolTipText("Manage Customers (Alt+C)");
        customerbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        customerbutton.setFocusable(false);
        customerbutton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        customerbutton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        customerbutton.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));
        customerbutton.setOpaque(false);
        customerbutton.setContentAreaFilled(false);
        customerbutton.setBorderPainted(false);

        customerbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), true);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), false);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }
        });
        customerbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerbuttonActionPerformed(evt);
            }
        });
        jToolBar1.add(customerbutton);

        jToolBar1.addSeparator();

        // Modern Material Design SMS Button with proper hover
        smsbutton = new javax.swing.JButton() {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (isHovered) {
                    java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                    g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                            java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new java.awt.Color(255, 255, 255, 50));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2d.dispose();
                }
                super.paintComponent(g);
            }

            public void setHovered(boolean hovered) {
                this.isHovered = hovered;
                repaint();
            }
        };
        smsbutton.setFont(new java.awt.Font("Segoe UI", 1, 12));
        smsbutton.setForeground(new java.awt.Color(255, 255, 255, 240));
        smsbutton.setIcon(ColorConstants.loadIcon("/icons/sms45.png"));
        smsbutton.setMnemonic('y');
        smsbutton.setText("📱 SMS");
        smsbutton.setToolTipText("Send SMS Messages (Alt+Y)");
        smsbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        smsbutton.setFocusable(false);
        smsbutton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        smsbutton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        smsbutton.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));
        smsbutton.setOpaque(false);
        smsbutton.setContentAreaFilled(false);
        smsbutton.setBorderPainted(false);

        smsbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), true);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), false);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }
        });
        smsbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smsbuttonActionPerformed(evt);
            }
        });
        jToolBar1.add(smsbutton);

        // Modern Material Design Settings Button with proper hover
        settingbutton = new javax.swing.JButton() {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (isHovered) {
                    java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                    g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                            java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new java.awt.Color(255, 255, 255, 50));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2d.dispose();
                }
                super.paintComponent(g);
            }

            public void setHovered(boolean hovered) {
                this.isHovered = hovered;
                repaint();
            }
        };
        settingbutton.setFont(new java.awt.Font("Segoe UI", 1, 12));
        settingbutton.setForeground(new java.awt.Color(255, 255, 255, 240));
        settingbutton.setIcon(ColorConstants.loadIcon("/icons/setting45.png"));
        settingbutton.setMnemonic('t');
        settingbutton.setText("⚙️ Settings");
        settingbutton.setToolTipText("Application Settings (Alt+T)");
        settingbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        settingbutton.setFocusable(false);
        settingbutton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        settingbutton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        settingbutton.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));
        settingbutton.setOpaque(false);
        settingbutton.setContentAreaFilled(false);
        settingbutton.setBorderPainted(false);

        settingbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), true);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), false);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }
        });
        settingbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingbuttonActionPerformed(evt);
            }
        });
        jToolBar1.add(settingbutton);

        jToolBar1.addSeparator();

        // Modern Material Design Logout Button with proper hover
        logbutton = new javax.swing.JButton() {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (isHovered) {
                    java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                    g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                            java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new java.awt.Color(255, 193, 7, 60));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2d.dispose();
                }
                super.paintComponent(g);
            }

            public void setHovered(boolean hovered) {
                this.isHovered = hovered;
                repaint();
            }
        };
        logbutton.setFont(new java.awt.Font("Segoe UI", 1, 12));
        logbutton.setForeground(new java.awt.Color(255, 255, 255, 240));
        logbutton.setIcon(ColorConstants.loadIcon("/icons/login45.png"));
        logbutton.setMnemonic('l');
        logbutton.setText("🚪 Log Out");
        logbutton.setToolTipText("Log Out (Alt+L)");
        logbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logbutton.setFocusable(false);
        logbutton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        logbutton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        logbutton.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));
        logbutton.setOpaque(false);
        logbutton.setContentAreaFilled(false);
        logbutton.setBorderPainted(false);

        logbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), true);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), false);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }
        });
        logbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logbuttonActionPerformed(evt);
            }
        });
        jToolBar1.add(logbutton);

        // Modern Material Design Exit Button with proper hover
        exitbutton = new javax.swing.JButton() {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                if (isHovered) {
                    java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                    g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                            java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new java.awt.Color(244, 67, 54, 60));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2d.dispose();
                }
                super.paintComponent(g);
            }

            public void setHovered(boolean hovered) {
                this.isHovered = hovered;
                repaint();
            }
        };
        exitbutton.setFont(new java.awt.Font("Segoe UI", 1, 12));
        exitbutton.setForeground(new java.awt.Color(255, 255, 255, 240));
        exitbutton.setIcon(ColorConstants.loadIcon("/icons/exit45.png"));
        exitbutton.setMnemonic('e');
        exitbutton.setText("❌ Exit");
        exitbutton.setToolTipText("Exit Application (Alt+E)");
        exitbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitbutton.setFocusable(false);
        exitbutton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        exitbutton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        exitbutton.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));
        exitbutton.setOpaque(false);
        exitbutton.setContentAreaFilled(false);
        exitbutton.setBorderPainted(false);

        exitbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), true);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                try {
                    java.lang.reflect.Method setHovered = evt.getSource().getClass().getMethod("setHovered",
                            boolean.class);
                    setHovered.invoke(evt.getSource(), false);
                } catch (Exception e) {
                    evt.getComponent().repaint();
                }
            }
        });
        exitbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitbuttonActionPerformed(evt);
            }
        });
        jToolBar1.add(exitbutton);

        // Professional Main Panel with Material Design Background
        jPanel4 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING,
                        java.awt.RenderingHints.VALUE_RENDER_QUALITY);

                // Material Design background with subtle pattern
                java.awt.Color color1 = new java.awt.Color(250, 252, 255, 255); // Pure white
                java.awt.Color color2 = new java.awt.Color(245, 247, 252, 255); // Subtle blue-grey
                java.awt.GradientPaint gradient = new java.awt.GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Subtle dot pattern for depth
                g2d.setColor(new java.awt.Color(0, 0, 0, 8));
                for (int i = 0; i < getWidth(); i += 60) {
                    for (int j = 0; j < getHeight(); j += 60) {
                        g2d.fillOval(i, j, 2, 2);
                    }
                }

                g2d.dispose();
            }
        };
        jPanel4.setLayout(null);

        // Material Design: Today's Sales Amount Panel with Professional Gradient
        jPanel1 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING,
                        java.awt.RenderingHints.VALUE_RENDER_QUALITY);

                // Material Design elevation shadow
                for (int i = 0; i < 6; i++) {
                    int alpha = Math.max(0, 30 - i * 5);
                    g2d.setColor(new java.awt.Color(0, 0, 0, alpha));
                    g2d.fill(new java.awt.geom.RoundRectangle2D.Float(
                            i, i + 2, getWidth() - 2 * i, getHeight() - 2 * i, 16, 16));
                }

                // Material Design Green gradient
                java.awt.Color color1 = new java.awt.Color(67, 160, 71, 255);
                java.awt.Color color2 = new java.awt.Color(56, 142, 60, 255);
                java.awt.Color color3 = new java.awt.Color(76, 175, 80, 255);
                java.awt.GradientPaint gradient = new java.awt.GradientPaint(0, 0, color3, getWidth(), getHeight(),
                        color2);
                g2d.setPaint(gradient);

                // Card with rounded corners
                java.awt.geom.RoundRectangle2D card = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(),
                        getHeight(), 16, 16);
                g2d.fill(card);

                // Subtle highlight
                java.awt.GradientPaint highlight = new java.awt.GradientPaint(0, 0,
                        new java.awt.Color(255, 255, 255, 40), 0, getHeight() / 3,
                        new java.awt.Color(255, 255, 255, 0));
                g2d.setPaint(highlight);
                g2d.fill(card);

                g2d.dispose();
            }
        };
        jPanel1.setOpaque(false);
        jPanel1.setLayout(null);
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Simple hover effect without complex animations
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(255, 255, 255, 80), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel1.setBorder(null);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 28)); // Professional typography
        jLabel12.setForeground(java.awt.Color.WHITE);
        jLabel12.setText("₹ 0.00");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel12);
        jLabel12.setBounds(20, 18, 370, 40);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // Clean secondary text
        jLabel13.setForeground(new java.awt.Color(255, 255, 255, 200)); // Semi-transparent white
        jLabel13.setText("💰 Today's Sales Amount");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(20, 58, 200, 25);

        jPanel4.add(jPanel1);
        jPanel1.setBounds(20, 100, 410, 90);

        // Material Design: Sales Bills Panel with Professional Blue Theme
        jPanel2 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING,
                        java.awt.RenderingHints.VALUE_RENDER_QUALITY);

                // Material Design elevation shadows with static values
                for (int i = 0; i < 8; i++) {
                    int alpha = Math.max(0, 40 - i * 5);
                    g2d.setColor(new java.awt.Color(0, 0, 0, alpha));
                    g2d.fillRoundRect(-i, -i, getWidth() + i * 2, getHeight() + i * 2, 14 + i, 14 + i);
                }

                // Material Design Blue gradient with depth
                java.awt.Color color1 = new java.awt.Color(42, 132, 218, 255); // Material Blue 600
                java.awt.Color color2 = new java.awt.Color(30, 116, 198, 255); // Material Blue 700
                java.awt.Color color3 = new java.awt.Color(33, 150, 243, 255); // Material Blue 500

                java.awt.GradientPaint gradient = new java.awt.GradientPaint(0, 0, color3, getWidth(), getHeight(),
                        color2);
                g2d.setPaint(gradient);

                // Professional card design
                java.awt.geom.RoundRectangle2D card = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(),
                        getHeight(), 14, 14);
                g2d.fill(card);

                // Subtle top highlight
                java.awt.GradientPaint highlight = new java.awt.GradientPaint(0, 0,
                        new java.awt.Color(255, 255, 255, 25), 0, getHeight() / 4,
                        new java.awt.Color(255, 255, 255, 0));
                g2d.setPaint(highlight);
                g2d.fill(card);

                g2d.dispose();
            }
        };
        jPanel2.setOpaque(false);
        jPanel2.setLayout(null);
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Simple hover effect like jPanel1
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(255, 255, 255, 80), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel2.setBorder(null);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 26)); // Professional typography
        jLabel8.setForeground(java.awt.Color.WHITE);
        jLabel8.setText("0");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel8);
        jLabel8.setBounds(18, 18, 160, 40);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 13)); // Refined secondary text
        jLabel9.setForeground(new java.awt.Color(255, 255, 255, 180)); // Semi-transparent white
        jLabel9.setText("📄 Sales Bills Today");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(18, 58, 160, 22);

        jPanel4.add(jPanel2);
        jPanel2.setBounds(20, 200, 200, 90);

        // Material Design Header with Professional Typography
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // Clean, professional font - reduced for better fit
        jLabel1.setForeground(new java.awt.Color(33, 33, 33)); // Material Design dark grey
        jLabel1.setText("📊 Today's Business Overview"); // More professional icon
        jLabel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(33, 150, 243, 120)), // Material
                                                                                                                // Blue
                                                                                                                // accent
                javax.swing.BorderFactory.createEmptyBorder(8, 0, 12, 0)));
        jPanel4.add(jLabel1);
        jLabel1.setBounds(20, 55, 280, 35);

        // Material Design: Purchase Orders Panel with Professional Orange Theme
        jPanel3 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING,
                        java.awt.RenderingHints.VALUE_RENDER_QUALITY);

                // Material Design elevation shadows with static values
                for (int i = 0; i < 8; i++) {
                    int alpha = Math.max(0, 40 - i * 5);
                    g2d.setColor(new java.awt.Color(255, 152, 0, alpha));
                    g2d.fillRoundRect(-i, -i, getWidth() + i * 2, getHeight() + i * 2, 14 + i, 14 + i);
                }

                // Material Design Orange gradient with warmth
                java.awt.Color color1 = new java.awt.Color(255, 152, 0, 255); // Material Orange 500
                java.awt.Color color2 = new java.awt.Color(245, 124, 0, 255); // Material Orange 600
                java.awt.Color color3 = new java.awt.Color(255, 183, 77, 255); // Material Orange 300

                java.awt.GradientPaint gradient = new java.awt.GradientPaint(0, 0, color3, getWidth(), getHeight(),
                        color2);
                g2d.setPaint(gradient);

                // Professional rounded card
                java.awt.geom.RoundRectangle2D card = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(),
                        getHeight(), 14, 14);
                g2d.fill(card);

                // Warm highlight effect
                java.awt.GradientPaint warmHighlight = new java.awt.GradientPaint(0, 0,
                        new java.awt.Color(255, 255, 255, 35), 0, getHeight() / 3,
                        new java.awt.Color(255, 255, 255, 0));
                g2d.setPaint(warmHighlight);
                g2d.fill(card);

                g2d.dispose();
            }
        };
        jPanel3.setOpaque(false);
        jPanel3.setLayout(null);
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Simple hover effect like jPanel1
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(255, 255, 255, 80), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel3.setBorder(null);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 26)); // Balanced typography
        jLabel15.setForeground(java.awt.Color.WHITE);
        jLabel15.setText("0");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel15);
        jLabel15.setBounds(18, 18, 160, 40);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 13)); // Clean secondary text
        jLabel16.setForeground(new java.awt.Color(255, 255, 255, 190)); // Slightly more opaque for orange background
        jLabel16.setText("📦 Purchase Orders");
        jPanel3.add(jLabel16);
        jLabel16.setBounds(18, 58, 160, 22);

        jPanel4.add(jPanel3);
        jPanel3.setBounds(230, 200, 200, 90);

        // Material Design: Customer Payments Panel with Professional Teal Theme
        jPanel5 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING,
                        java.awt.RenderingHints.VALUE_RENDER_QUALITY);

                // Material Design elevation shadows with teal tint
                for (int i = 0; i < 8; i++) {
                    int alpha = Math.max(0, 40 - i * 5);
                    g2d.setColor(new java.awt.Color(0, 188, 212, alpha));
                    g2d.fillRoundRect(-i, -i, getWidth() + i * 2, getHeight() + i * 2, 14 + i, 14 + i);
                }

                // Material Design Cyan gradient with depth
                java.awt.Color color1 = new java.awt.Color(0, 188, 212, 255); // Material Cyan 600
                java.awt.Color color2 = new java.awt.Color(0, 151, 167, 255); // Material Cyan 700
                java.awt.Color color3 = new java.awt.Color(38, 198, 218, 255); // Material Cyan 400

                java.awt.GradientPaint gradient = new java.awt.GradientPaint(0, 0, color3, getWidth(), getHeight(),
                        color2);
                g2d.setPaint(gradient);

                // Professional card with smooth corners
                java.awt.geom.RoundRectangle2D card = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(),
                        getHeight(), 14, 14);
                g2d.fill(card);

                // Cool inner highlight
                java.awt.GradientPaint coolHighlight = new java.awt.GradientPaint(0, 0,
                        new java.awt.Color(255, 255, 255, 30), 0, getHeight() / 3,
                        new java.awt.Color(255, 255, 255, 0));
                g2d.setPaint(coolHighlight);
                g2d.fill(card);

                g2d.dispose();
            }
        };
        jPanel5.setOpaque(false);
        jPanel5.setLayout(null);
        jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Simple hover effect like jPanel1
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(255, 255, 255, 80), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel5.setBorder(null);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 26)); // Professional typography
        jLabel10.setForeground(java.awt.Color.WHITE);
        jLabel10.setText("0");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel10);
        jLabel10.setBounds(18, 18, 160, 40);

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 13)); // Clean secondary text
        jLabel25.setForeground(new java.awt.Color(255, 255, 255, 185)); // Semi-transparent white
        jLabel25.setText("💳 Customer Payments");
        jPanel5.add(jLabel25);
        jLabel25.setBounds(18, 58, 160, 22);

        jPanel4.add(jPanel5);
        jPanel5.setBounds(20, 300, 200, 90);

        // Material Design: Purchase Entries Panel with Professional Amber Theme
        jPanel6 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING,
                        java.awt.RenderingHints.VALUE_RENDER_QUALITY);

                // Material Design elevation shadows with amber tint
                for (int i = 0; i < 8; i++) {
                    int alpha = Math.max(0, 40 - i * 5);
                    g2d.setColor(new java.awt.Color(255, 193, 7, alpha));
                    g2d.fillRoundRect(-i, -i, getWidth() + i * 2, getHeight() + i * 2, 14 + i, 14 + i);
                }

                // Material Design Amber gradient with richness
                java.awt.Color color1 = new java.awt.Color(255, 193, 7, 255); // Material Amber 500
                java.awt.Color color2 = new java.awt.Color(255, 160, 0, 255); // Material Amber 600
                java.awt.Color color3 = new java.awt.Color(255, 213, 79, 255); // Material Amber 300

                java.awt.GradientPaint gradient = new java.awt.GradientPaint(0, 0, color3, getWidth(), getHeight(),
                        color2);
                g2d.setPaint(gradient);

                // Professional card with premium feel
                java.awt.geom.RoundRectangle2D card = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(),
                        getHeight(), 14, 14);
                g2d.fill(card);

                // Golden highlight
                java.awt.GradientPaint goldHighlight = new java.awt.GradientPaint(0, 0,
                        new java.awt.Color(255, 255, 255, 40), 0, getHeight() / 3,
                        new java.awt.Color(255, 255, 255, 0));
                g2d.setPaint(goldHighlight);
                g2d.fill(card);

                g2d.dispose();
            }
        };
        jPanel6.setOpaque(false);
        jPanel6.setLayout(null);
        jPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Simple hover effect like jPanel1
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(255, 255, 255, 80), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel6.setBorder(null);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 26)); // Professional typography
        jLabel6.setForeground(new java.awt.Color(33, 33, 33)); // Dark grey for better contrast on amber
        jLabel6.setText("0");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel6.add(jLabel6);
        jLabel6.setBounds(18, 18, 160, 40);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 13)); // Clean secondary text
        jLabel7.setForeground(new java.awt.Color(66, 66, 66, 160)); // Semi-transparent dark grey for amber background
        jLabel7.setText("📋 Purchase Entries");
        jPanel6.add(jLabel7);
        jLabel7.setBounds(18, 58, 160, 22);

        jPanel4.add(jPanel6);
        jPanel6.setBounds(230, 300, 200, 90);

        // Professional Design: Total Stock Value Panel with Dark Modern Theme
        jPanel7 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                // Dark professional gradient
                java.awt.Color color1 = new java.awt.Color(55, 71, 79, 255); // Material Blue Grey
                java.awt.Color color2 = new java.awt.Color(38, 50, 56, 255); // Darker blue grey
                java.awt.GradientPaint gradient = new java.awt.GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);

                // Modern card design
                java.awt.geom.RoundRectangle2D card = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(),
                        getHeight(), 15, 15);
                g2d.fill(card);

                // Premium border accent
                g2d.setColor(new java.awt.Color(156, 39, 176, 150)); // Purple accent
                g2d.setStroke(new java.awt.BasicStroke(2));
                g2d.draw(card);

                g2d.dispose();
            }
        };
        jPanel7.setOpaque(false);
        jPanel7.setLayout(null);
        jPanel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Simple hover effect
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(255, 255, 255, 100), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel7.setBorder(null);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 32));
        jLabel4.setForeground(new java.awt.Color(220, 220, 220));
        jLabel4.setText("₹ 0.00");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel7.add(jLabel4);
        jLabel4.setBounds(15, 15, 380, 45);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15));
        jLabel5.setForeground(new java.awt.Color(220, 220, 220));
        jLabel5.setText("📦 Total Stock Value");
        jPanel7.add(jLabel5);
        jLabel5.setBounds(15, 60, 200, 25);

        jPanel4.add(jPanel7);
        jPanel7.setBounds(450, 100, 410, 90);

        jPanel8 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ColorConstants.ERROR_RED);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        jPanel8.setOpaque(false);
        jPanel8.setLayout(null);

        // Simple hover effect like jPanel1
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(255, 255, 255, 80), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel8.setBorder(null);
            }
        });

        jLabel17.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jLabel17.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel17.setText("0");
        jLabel17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        jPanel8.add(jLabel17);
        jLabel17.setBounds(15, 15, 170, 45);

        jLabel18.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel18.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel18.setText("Reorder Level");
        jPanel8.add(jLabel18);
        jLabel18.setBounds(15, 60, 170, 25);

        jPanel4.add(jPanel8);
        jPanel8.setBounds(450, 200, 200, 90);

        jPanel9 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ColorConstants.ERROR_RED);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        jPanel9.setOpaque(false);
        jPanel9.setLayout(null);

        // Simple hover effect like jPanel1
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(255, 255, 255, 80), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel9.setBorder(null);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jLabel2.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel2.setText("0");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel9.add(jLabel2);
        jLabel2.setBounds(15, 15, 170, 45);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel3.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel3.setText("Nil Stock Items");
        jPanel9.add(jLabel3);
        jLabel3.setBounds(15, 60, 170, 25);

        jPanel4.add(jPanel9);
        jPanel9.setBounds(660, 200, 200, 90);

        jPanel10 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ColorConstants.SUCCESS_GREEN);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        jPanel10.setOpaque(false);
        jPanel10.setLayout(null);

        // Simple hover effect like jPanel1
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(255, 255, 255, 80), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel10.setBorder(null);
            }
        });

        jLabel19.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jLabel19.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel19.setText("0");
        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });
        jPanel10.add(jLabel19);
        jLabel19.setBounds(15, 15, 170, 45);

        jLabel20.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel20.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel20.setText("Total Items");
        jPanel10.add(jLabel20);
        jLabel20.setBounds(15, 60, 170, 25);

        jPanel4.add(jPanel10);
        jPanel10.setBounds(450, 300, 200, 90);

        jPanel11 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ColorConstants.WARNING_YELLOW);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        jPanel11.setOpaque(false);
        jPanel11.setLayout(null);

        // Simple hover effect like jPanel1
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(255, 255, 255, 80), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel11.setBorder(null);
            }
        });

        jLabel21.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jLabel21.setForeground(ColorConstants.TEXT_PRIMARY);
        jLabel21.setText("0");
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });
        jPanel11.add(jLabel21);
        jLabel21.setBounds(15, 15, 170, 45);

        jLabel22.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel22.setForeground(ColorConstants.TEXT_PRIMARY);
        jLabel22.setText("Excess Stock");
        jPanel11.add(jLabel22);
        jLabel22.setBounds(15, 60, 170, 25);

        jPanel4.add(jPanel11);
        jPanel11.setBounds(660, 300, 200, 90);

        // Override paintComponent for gradient background with curved corners
        jPanel12 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING,
                        java.awt.RenderingHints.VALUE_RENDER_QUALITY);

                int w = getWidth();
                int h = getHeight();

                // Material Design elevation shadow
                for (int i = 0; i < 8; i++) {
                    int alpha = Math.max(0, 30 - i * 4);
                    g2d.setColor(new java.awt.Color(0, 0, 0, alpha));
                    g2d.fillRoundRect(-i, -i, w + (i * 2), h + (i * 2), 20 + i, 20 + i);
                }

                // Professional gradient background
                java.awt.Color color1 = new java.awt.Color(13, 71, 161, 255); // Material Blue 900
                java.awt.Color color2 = new java.awt.Color(25, 118, 210, 255); // Material Blue 600
                java.awt.Color color3 = new java.awt.Color(33, 150, 243, 255); // Material Blue 500

                java.awt.GradientPaint gradient = new java.awt.GradientPaint(
                        0, 0, color3, 0, h, color1);
                g2d.setPaint(gradient);

                // Rounded footer background
                java.awt.geom.RoundRectangle2D footer = new java.awt.geom.RoundRectangle2D.Float(
                        0, 0, w, h, 20, 20);
                g2d.fill(footer);

                // Subtle top highlight
                java.awt.GradientPaint highlight = new java.awt.GradientPaint(
                        0, 0, new java.awt.Color(255, 255, 255, 60),
                        0, h / 3, new java.awt.Color(255, 255, 255, 0));
                g2d.setPaint(highlight);
                g2d.fill(footer);

                // Professional border accent
                g2d.setStroke(new java.awt.BasicStroke(2));
                g2d.setColor(new java.awt.Color(255, 255, 255, 40));
                g2d.draw(footer);

                // Bottom accent line
                g2d.setColor(new java.awt.Color(255, 255, 255, 80));
                g2d.fillRoundRect(20, h - 4, w - 40, 2, 2, 2);

                g2d.dispose();
            }
        };
        jPanel12.setOpaque(false);
        jPanel12.setLayout(null);
        jPanel12.setLayout(null);

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 28)); // Refined size for better balance
        jLabel26.setForeground(new java.awt.Color(255, 255, 255, 245)); // Slightly transparent white
        jLabel26.setText("🎯 Swayam Billing Software - Professional Edition");
        jLabel26.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 5, 0)); // Bottom spacing
        jPanel12.add(jLabel26);
        jLabel26.setBounds(30, 20, 700, 35);

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 13)); // Slightly smaller for better hierarchy
        jLabel42.setForeground(new java.awt.Color(255, 255, 255, 200)); // Semi-transparent for secondary info
        jLabel42.setText(
                "📧 Swayamsoftware+@gmail.com  •  📞9999999999   •  🌐 Swayamerp.in  •  💼 Complete Business Solution");
        jLabel42.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 8, 0)); // Bottom spacing
        jPanel12.add(jLabel42);
        jLabel42.setBounds(30, 55, 900, 25);

        versionl.setFont(new java.awt.Font("Segoe UI", 1, 15)); // Consistent sizing
        versionl.setForeground(new java.awt.Color(255, 235, 59, 255)); // Brighter yellow for visibility
        versionl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        versionl.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 235, 59, 100), 1),
                javax.swing.BorderFactory.createEmptyBorder(4, 12, 4, 12)));
        versionl.setOpaque(true);
        versionl.setBackground(new java.awt.Color(255, 235, 59, 20));
        jPanel12.add(versionl);
        versionl.setBounds(750, 18, 570, 30);

        activatel = new javax.swing.JLabel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                // Professional call-to-action button gradient
                java.awt.Color color1 = new java.awt.Color(76, 175, 80, 200);
                java.awt.Color color2 = new java.awt.Color(56, 142, 60, 200);
                java.awt.GradientPaint gradient = new java.awt.GradientPaint(
                        0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);

                java.awt.geom.RoundRectangle2D button = new java.awt.geom.RoundRectangle2D.Float(
                        0, 0, getWidth(), getHeight(), 15, 15);
                g2d.fill(button);

                // Highlight effect
                g2d.setColor(new java.awt.Color(255, 255, 255, 60));
                g2d.fill(new java.awt.geom.RoundRectangle2D.Float(
                        0, 0, getWidth(), getHeight() / 2, 15, 15));

                // Professional border
                g2d.setStroke(new java.awt.BasicStroke(1.5f));
                g2d.setColor(new java.awt.Color(46, 125, 50, 180));
                g2d.draw(button);

                g2d.dispose();
                super.paintComponent(g);
            }
        };
        activatel.setOpaque(false);
        activatel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // Consistent sizing
        activatel.setForeground(new java.awt.Color(255, 255, 255, 255)); // Pure white for contrast
        activatel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        activatel.setText("⚡ Activate Full Version"); // Default text, will be updated by version check
        activatel.setToolTipText("Click here to activate full version");
        activatel.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 16, 6, 16));
        activatel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        activatel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                activatel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255, 80), 2),
                        javax.swing.BorderFactory.createEmptyBorder(4, 14, 4, 14)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                activatel.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 16, 6, 16));
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                activatelMouseClicked(evt);
            }
        });
        jPanel12.add(activatel);
        activatel.setBounds(950, 50, 220, 32);

        jPanel4.add(jPanel12);
        jPanel12.setBounds(0, 510, 1360, 110);

        cnamel.setFont(new java.awt.Font("Segoe UI", 1, 40)); // Modern professional font
        cnamel.setForeground(new java.awt.Color(33, 150, 243)); // Professional blue
        cnamel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cnamel.setText(".");
        // Add text shadow effect
        cnamel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2),
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(33, 150, 243, 30), 1)));
        jPanel4.add(cnamel);
        cnamel.setBounds(20, 5, 1340, 50);

        // Modern System Info Cards
        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 13));
        jLabel30.setForeground(new java.awt.Color(33, 33, 33));
        jLabel30.setText(" 🖥️ System IP");
        jLabel30.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(33, 150, 243, 100), 1),
                javax.swing.BorderFactory.createEmptyBorder(6, 12, 6, 12)));
        jLabel30.setOpaque(true);
        jLabel30.setBackground(new java.awt.Color(248, 249, 250));
        jPanel4.add(jLabel30);
        jLabel30.setBounds(20, 445, 200, 30);

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setForeground(ColorConstants.TEXT_PRIMARY);
        jLabel31.setText(" 🖧 Server IP");
        jLabel31.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(ColorConstants.PRIMARY_BLUE_LIGHT, 1),
                javax.swing.BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        jLabel31.setOpaque(true);
        jLabel31.setBackground(ColorConstants.BACKGROUND_LIGHT);
        jPanel4.add(jLabel31);
        jLabel31.setBounds(20, 475, 200, 30);

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setForeground(ColorConstants.TEXT_PRIMARY);
        jLabel32.setText(" 👤 Login as");
        jLabel32.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(ColorConstants.PRIMARY_BLUE_LIGHT, 1),
                javax.swing.BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        jLabel32.setOpaque(true);
        jLabel32.setBackground(ColorConstants.BACKGROUND_LIGHT);
        jPanel4.add(jLabel32);
        jLabel32.setBounds(20, 415, 200, 30);

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setForeground(ColorConstants.TEXT_PRIMARY);
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(ColorConstants.PRIMARY_BLUE_LIGHT, 1),
                javax.swing.BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        jLabel33.setOpaque(true);
        jLabel33.setBackground(ColorConstants.INPUT_BACKGROUND);
        jPanel4.add(jLabel33);
        jLabel33.setBounds(220, 415, 210, 30);

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setForeground(ColorConstants.TEXT_PRIMARY);
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(ColorConstants.PRIMARY_BLUE_LIGHT, 1),
                javax.swing.BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        jLabel34.setOpaque(true);
        jLabel34.setBackground(ColorConstants.INPUT_BACKGROUND);
        jPanel4.add(jLabel34);
        jLabel34.setBounds(220, 445, 210, 30);

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setForeground(ColorConstants.TEXT_PRIMARY);
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(ColorConstants.PRIMARY_BLUE_LIGHT, 1),
                javax.swing.BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        jLabel35.setOpaque(true);
        jLabel35.setBackground(ColorConstants.INPUT_BACKGROUND);
        jPanel4.add(jLabel35);
        jLabel35.setBounds(220, 475, 210, 30);

        // Professional Modern Day Close Button
        dayclose = new javax.swing.JLabel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                // Modern warning button gradient
                java.awt.Color color1 = new java.awt.Color(255, 193, 7, 255);
                java.awt.Color color2 = new java.awt.Color(255, 143, 0, 255);
                java.awt.GradientPaint gradient = new java.awt.GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);

                // Rounded button with shadow
                java.awt.geom.RoundRectangle2D button = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(),
                        getHeight(), 20, 20);
                g2d.fill(button);

                // Professional highlight
                g2d.setColor(new java.awt.Color(255, 255, 255, 60));
                g2d.fill(new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight() / 2, 20, 20));

                // Border accent
                g2d.setColor(new java.awt.Color(245, 124, 0, 180));
                g2d.setStroke(new java.awt.BasicStroke(1));
                g2d.draw(button);

                g2d.dispose();
                super.paintComponent(g);
            }
        };
        dayclose.setOpaque(false);
        dayclose.setFont(new java.awt.Font("Segoe UI", 1, 13));
        dayclose.setForeground(new java.awt.Color(66, 66, 66));
        dayclose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dayclose.setText("🔒 Day Book Close");
        dayclose.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(ColorConstants.SECONDARY_ORANGE_DARK, 1),
                javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        dayclose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dayclose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                daycloseMouseClicked(evt);
            }
        });
        jPanel4.add(dayclose);
        dayclose.setBounds(560, 450, 140, 30);

        // Professional Modern Button Design
        clearl = new javax.swing.JLabel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                // Modern button gradient
                java.awt.Color color1 = new java.awt.Color(76, 175, 80, 255);
                java.awt.Color color2 = new java.awt.Color(56, 142, 60, 255);
                java.awt.GradientPaint gradient = new java.awt.GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);

                // Rounded button
                java.awt.geom.RoundRectangle2D button = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(),
                        getHeight(), 20, 20);
                g2d.fill(button);

                // Glass effect highlight
                g2d.setColor(new java.awt.Color(255, 255, 255, 80));
                g2d.fill(new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight() / 2, 20, 20));

                g2d.dispose();
                super.paintComponent(g);
            }
        };
        clearl.setOpaque(false);
        clearl.setFont(new java.awt.Font("Segoe UI", 1, 13));
        clearl.setForeground(java.awt.Color.WHITE);
        clearl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clearl.setText("🔄 Refresh");
        clearl.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(ColorConstants.PRIMARY_BLUE_DARK, 1),
                javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        clearl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearlMouseClicked(evt);
            }
        });
        jPanel4.add(clearl);
        clearl.setBounds(710, 450, 120, 30);

        jPanel13 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ColorConstants.PRIMARY_BLUE_LIGHT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        jPanel13.setOpaque(false);
        jPanel13.setLayout(null);

        // Simple hover effect like jPanel1
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(255, 255, 255, 80), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel13.setBorder(null);
            }
        });

        jLabel23.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel23.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel23.setText("Customer Balance");
        jPanel13.add(jLabel23);
        jLabel23.setBounds(15, 60, 200, 25);

        jLabel24.setFont(new java.awt.Font("Arial Black", 1, 28)); // NOI18N
        jLabel24.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel24.setText("0.00");
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
        });
        jPanel13.add(jLabel24);
        jLabel24.setBounds(15, 15, 380, 45);

        jPanel4.add(jPanel13);
        jPanel13.setBounds(880, 100, 410, 90);

        jPanel14 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ColorConstants.ERROR_RED);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        jPanel14.setOpaque(false);
        jPanel14.setLayout(null);

        // Simple hover effect like jPanel1
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(255, 255, 255, 80), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel14.setBorder(null);
            }
        });

        jLabel39.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jLabel39.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel39.setText("0");
        jLabel39.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel39MouseClicked(evt);
            }
        });
        jPanel14.add(jLabel39);
        jLabel39.setBounds(15, 15, 170, 45);

        jLabel40.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel40.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel40.setText("Supplier Overdues");
        jPanel14.add(jLabel40);
        jLabel40.setBounds(15, 60, 170, 25);

        jPanel4.add(jPanel14);
        jPanel14.setBounds(1095, 300, 200, 90);

        jPanel15 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ColorConstants.SECONDARY_ORANGE_DARK);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        jPanel15.setOpaque(false);
        jPanel15.setLayout(null);

        // Simple hover effect like jPanel1
        jPanel15.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(255, 255, 255, 80), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel15.setBorder(null);
            }
        });

        jLabel27.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jLabel27.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel27.setText("0");
        jLabel27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
        });
        jPanel15.add(jLabel27);
        jLabel27.setBounds(15, 15, 170, 45);

        jLabel28.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel28.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel28.setText("Supplier Balance");
        jPanel15.add(jLabel28);
        jLabel28.setBounds(15, 60, 170, 25);

        jPanel4.add(jPanel15);
        jPanel15.setBounds(880, 200, 200, 90);

        jPanel16 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ColorConstants.PRIMARY_BLUE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        jPanel16.setOpaque(false);
        jPanel16.setLayout(null);

        // Simple hover effect like jPanel1
        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(255, 255, 255, 80), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel16.setBorder(null);
            }
        });

        jLabel29.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jLabel29.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel29.setText("0");
        jLabel29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
            }
        });
        jPanel16.add(jLabel29);
        jLabel29.setBounds(15, 15, 170, 45);

        jLabel36.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel36.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel36.setText("Vouchers Today");
        jPanel16.add(jLabel36);
        jLabel36.setBounds(15, 60, 170, 25);

        jPanel4.add(jPanel16);
        jPanel16.setBounds(1095, 200, 200, 90);

        jPanel17 = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ColorConstants.ERROR_RED);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        jPanel17.setOpaque(false);
        jPanel17.setLayout(null);

        // Simple hover effect like jPanel1
        jPanel17.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(255, 255, 255, 80), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel17.setBorder(null);
            }
        });

        jLabel37.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jLabel37.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel37.setText("0");
        jLabel37.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel37MouseClicked(evt);
            }
        });
        jPanel17.add(jLabel37);
        jLabel37.setBounds(15, 15, 170, 45);

        jLabel38.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel38.setForeground(ColorConstants.TEXT_LIGHT);
        jLabel38.setText("Customer Overdues");
        jPanel17.add(jLabel38);
        jLabel38.setBounds(15, 60, 170, 25);

        jPanel4.add(jPanel17);
        jPanel17.setBounds(880, 300, 200, 90);

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // Modern typography - reduced for better fit
        jLabel41.setForeground(new java.awt.Color(33, 33, 33));
        jLabel41.setText("📦 Stock Management");
        jLabel41.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(156, 39, 176, 100)),
                javax.swing.BorderFactory.createEmptyBorder(5, 0, 10, 0)));
        jPanel4.add(jLabel41);
        jLabel41.setBounds(450, 70, 200, 30);

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 18)); // Reduced font for better fit
        jLabel43.setForeground(new java.awt.Color(33, 33, 33));
        jLabel43.setText("💰 Financial Overview");
        jLabel43.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(76, 175, 80, 100)),
                javax.swing.BorderFactory.createEmptyBorder(5, 0, 10, 0)));
        jLabel43.setBounds(880, 70, 210, 30);
        jPanel4.add(jLabel43);

        jDesktopPane1.setLayer(jToolBar1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jPanel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
                jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 1360, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jDesktopPane1Layout.setVerticalGroup(
                jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)));

        getContentPane().add(jDesktopPane1);

        // Center jDesktopPane1 horizontally and align to top
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int desktopPaneWidth = 1360;
        int desktopPaneHeight = 690;
        int x = (screenSize.width - desktopPaneWidth) / 2; // Center horizontally
        int y = 30; // Align to top with menu bar offset
        jDesktopPane1.setBounds(x, y, desktopPaneWidth, desktopPaneHeight);

        // Set menu bar to start from left edge
        jMenuBar1.setBounds(0, 0, 1360, 30);

        jMenuBar1.setPreferredSize(new java.awt.Dimension(1360, 30));
        jMenuBar1.setBackground(ColorConstants.PRIMARY_BLUE);
        jMenuBar1.setForeground(ColorConstants.TEXT_LIGHT);
        jMenuBar1.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        // Set as the frame's menu bar to ensure proper display
        this.setJMenuBar(jMenuBar1);

        jMenu10.setText("Sales");
        jMenu10.setForeground(ColorConstants.TEXT_LIGHT);
        jMenu10.setFont(new java.awt.Font("Arial", 1, 14));
        jMenu10.setMnemonic('S');

        jSeparator85.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator85.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator85.setOpaque(true);
        jSeparator85.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu10.add(jSeparator85);

        jMenuItem38.setText("Point of Sales (Billing)");
        jMenuItem38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem38ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem38);

        jMenuItem39.setText("Sales Return");
        jMenuItem39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem39ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem39);

        jSeparator88.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator88.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator88.setOpaque(true);
        jSeparator88.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu10.add(jSeparator88);

        jMenuItem29.setText("Daily Sales");
        jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem29ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem29);

        jMenuItem30.setText("Daily Sales Report");
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem30);

        jSeparator90.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator90.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator90.setOpaque(true);
        jSeparator90.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu10.add(jSeparator90);

        jMenuItem17.setText("Sales Report");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem17);

        jMenuItem18.setText("Location Wise Sales Report");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem18);

        jMenuItem20.setText("Terminal Wise Sales Report");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem20);

        jMenuItem19.setText("Price Type Wise Sales Report");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem19);

        jMenuItem23.setText("Customer Wise Sales Report");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem23);

        jMenuItem28.setText("Bill Wise Item Sales");
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem28);

        jSeparator87.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator87.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator87.setOpaque(true);
        jSeparator87.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu10.add(jSeparator87);

        jMenuItem21.setText("Sales Summary");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem21);

        jMenuItem27.setText("Day Wise Sales Summary");
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem27);

        jMenuItem22.setText("Customer Wise Sales Summary");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem22);

        jMenuItem24.setText("Item Wise Sales Summary");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem24);

        jMenuItem25.setText("Category Wise Sales Summary");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem25);

        jMenuItem26.setText("Manufacturer Wise Sales Summary");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem26);

        jMenuItem114.setText("Cashier Wise Sales Summary");
        jMenuItem114.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem114ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem114);

        jSeparator89.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator89.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator89.setOpaque(true);
        jSeparator89.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu10.add(jSeparator89);

        jMenuItem31.setText("Sales Return Report");
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem31);

        jMenuItem32.setText("Item Wise Sales Return Summary");
        jMenuItem32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem32ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem32);

        jSeparator92.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator92.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator92.setOpaque(true);
        jSeparator92.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu10.add(jSeparator92);

        jMenuBar1.add(jMenu10);

        jMenu3.setText("Purchase");
        jMenu3.setForeground(ColorConstants.TEXT_LIGHT);
        jMenu3.setFont(new java.awt.Font("Arial", 1, 14));
        jMenu3.setMnemonic('P');

        jSeparator68.setBackground(ColorConstants.SECONDARY_ORANGE);
        jSeparator68.setForeground(ColorConstants.SECONDARY_ORANGE);
        jSeparator68.setOpaque(true);
        jSeparator68.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu3.add(jSeparator68);

        jMenuItem3.setText("Purchase Register");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem34.setText("Purchase Return Register");
        jMenuItem34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem34ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem34);

        jSeparator69.setBackground(ColorConstants.SECONDARY_ORANGE);
        jSeparator69.setForeground(ColorConstants.SECONDARY_ORANGE);
        jSeparator69.setOpaque(true);
        jSeparator69.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu3.add(jSeparator69);

        jMenuItem5.setText("Purchase Report");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem6.setText("Supplier Wise Purchase Report");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuItem84.setText("Item Wise Purchase Details");
        jMenuItem84.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem84ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem84);

        jSeparator70.setBackground(ColorConstants.SECONDARY_ORANGE);
        jSeparator70.setForeground(ColorConstants.SECONDARY_ORANGE);
        jSeparator70.setOpaque(true);
        jSeparator70.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu3.add(jSeparator70);

        jMenuItem85.setText("Item Wise Purchase Summary");
        jMenuItem85.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem85ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem85);

        jMenuItem86.setText("Manufacturer Wise Purchase Summary");
        jMenuItem86.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem86ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem86);

        jMenuItem87.setText("Category Wise Purchase Summary");
        jMenuItem87.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem87ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem87);

        jMenuItem88.setText("Supplier Wise Purchase Summary");
        jMenuItem88.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem88ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem88);

        jSeparator71.setBackground(ColorConstants.SECONDARY_ORANGE);
        jSeparator71.setForeground(ColorConstants.SECONDARY_ORANGE);
        jSeparator71.setOpaque(true);
        jSeparator71.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu3.add(jSeparator71);

        jMenuItem35.setText("Purchase Return Report");
        jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem35ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem35);

        jMenuItem36.setText("Item Wise Purchase Return Summary");
        jMenuItem36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem36ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem36);

        jSeparator91.setBackground(ColorConstants.SECONDARY_ORANGE);
        jSeparator91.setForeground(ColorConstants.SECONDARY_ORANGE);
        jSeparator91.setOpaque(true);
        jSeparator91.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu3.add(jSeparator91);

        jMenuBar1.add(jMenu3);

        jMenu21.setText("Estimate");
        jMenu21.setForeground(ColorConstants.TEXT_LIGHT);
        jMenu21.setFont(new java.awt.Font("Arial", 1, 14));
        jMenu21.setMnemonic('E');

        jSeparator125.setBackground(ColorConstants.WARNING_YELLOW);
        jSeparator125.setForeground(ColorConstants.WARNING_YELLOW);
        jSeparator125.setOpaque(true);
        jSeparator125.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu21.add(jSeparator125);

        jMenuItem118.setText("Estimate Preparation");
        jMenuItem118.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem118ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem118);

        jMenuItem134.setText("Estimate Return");
        jMenuItem134.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem134ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem134);

        jSeparator126.setBackground(ColorConstants.WARNING_YELLOW);
        jSeparator126.setForeground(ColorConstants.WARNING_YELLOW);
        jSeparator126.setOpaque(true);
        jSeparator126.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu21.add(jSeparator126);

        jMenuItem119.setText("Daily Sales");
        jMenuItem119.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem119ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem119);

        jMenuItem120.setText("Daily Sales Report");
        jMenuItem120.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem120ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem120);

        jSeparator127.setBackground(ColorConstants.WARNING_YELLOW);
        jSeparator127.setForeground(ColorConstants.WARNING_YELLOW);
        jSeparator127.setOpaque(true);
        jSeparator127.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu21.add(jSeparator127);

        jMenuItem121.setText("Estimate Report");
        jMenuItem121.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem121ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem121);

        jMenuItem122.setText("Location Wise Estimate Report");
        jMenuItem122.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem122ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem122);

        jMenuItem123.setText("Terminal Wise Estimate Report");
        jMenuItem123.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem123ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem123);

        jMenuItem124.setText("Price Type Wise Estimate Report");
        jMenuItem124.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem124ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem124);

        jMenuItem125.setText("Customer Wise Estimate Report");
        jMenuItem125.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem125ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem125);

        jMenuItem126.setText("Bill Wise Items Estimate");
        jMenuItem126.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem126ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem126);

        jSeparator128.setBackground(ColorConstants.WARNING_YELLOW);
        jSeparator128.setForeground(ColorConstants.WARNING_YELLOW);
        jSeparator128.setOpaque(true);
        jSeparator128.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu21.add(jSeparator128);

        jMenuItem127.setText("Estimate Summary");
        jMenuItem127.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem127ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem127);

        jMenuItem128.setText("Day Wise Estimate Summary");
        jMenuItem128.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem128ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem128);

        jMenuItem129.setText("Customer Wise Estimate Summary");
        jMenuItem129.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem129ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem129);

        jMenuItem130.setText("Item Wise Estimate Summary");
        jMenuItem130.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem130ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem130);

        jMenuItem131.setText("Category Wise Estimate Summary");
        jMenuItem131.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem131ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem131);

        jMenuItem132.setText("Manufacturer Wise Estimate Summary");
        jMenuItem132.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem132ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem132);

        jMenuItem133.setText("Cashier Wise Estimate Summary");
        jMenuItem133.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem133ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem133);

        jSeparator129.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator129.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator129.setOpaque(true);
        jSeparator129.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu21.add(jSeparator129);

        jMenuItem135.setText("Estimate Return Report");
        jMenuItem135.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem135ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem135);

        jMenuItem136.setText("Item Wise Estimate Return Summary");
        jMenuItem136.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem136ActionPerformed(evt);
            }
        });
        jMenu21.add(jMenuItem136);

        jSeparator130.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator130.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator130.setOpaque(true);
        jSeparator130.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu21.add(jSeparator130);

        jMenuBar1.add(jMenu21);

        jMenu1.setText("Item");

        jSeparator72.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator72.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator72.setOpaque(true);
        jSeparator72.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu1.add(jSeparator72);

        jMenuItem1.setText("Item Master");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem50.setText("Search Item");
        jMenuItem50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem50ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem50);

        jMenuItem103.setText("Items List");
        jMenuItem103.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem103ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem103);

        jMenuItem63.setText("Items Bulk Upload");
        jMenuItem63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem63ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem63);

        jSeparator74.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator74.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator74.setOpaque(true);
        jSeparator74.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu1.add(jSeparator74);

        jMenuBar1.add(jMenu1);

        jMenu5.setText("Stock");
        jMenu5.setForeground(ColorConstants.TEXT_LIGHT);
        jMenu5.setFont(new java.awt.Font("Arial", 1, 14));
        jMenu5.setMnemonic('t');
        jMenu5.setFont(new java.awt.Font("Arial", 1, 14));

        jSeparator63.setBackground(ColorConstants.SUCCESS_GREEN);
        jSeparator63.setForeground(ColorConstants.SUCCESS_GREEN);
        jSeparator63.setOpaque(true);
        jSeparator63.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu5.add(jSeparator63);

        jMenuItem51.setText("Manual Stock Entry");
        jMenuItem51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem51ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem51);

        jMenuItem67.setText("Bulk Price Update");
        jMenuItem67.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem67ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem67);

        jMenuItem78.setText("Stock Alter");
        jMenuItem78.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem78ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem78);

        jMenuItem163.setText("Stock Bulk Upload");
        jMenuItem163.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem163ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem163);

        jSeparator64.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator64.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator64.setOpaque(true);
        jSeparator64.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu5.add(jSeparator64);

        jMenuItem66.setText("Stock Report");
        jMenuItem66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem66ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem66);

        jMenuItem7.setText("Stock Summary");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem7);

        jMenuItem9.setText("Barcode Wise Stock Report");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem9);

        jMenuItem94.setText("Minimum Stock Report");
        jMenuItem94.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem94ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem94);

        jMenuItem95.setText("Maximum Stock Report");
        jMenuItem95.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem95ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem95);

        jMenuItem8.setText("Nill Stock Report");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem8);

        jMenuItem52.setText("Manual Stock Entry Report");
        jMenuItem52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem52ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem52);

        jMenuItem79.setText("Stock Alter Report");
        jMenuItem79.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem79ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem79);

        jMenuItem164.setText("Expiry Report");
        jMenuItem164.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem164ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem164);

        jSeparator95.setBackground(ColorConstants.SUCCESS_GREEN);
        jSeparator95.setForeground(ColorConstants.SUCCESS_GREEN);
        jSeparator95.setOpaque(true);
        jSeparator95.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu5.add(jSeparator95);

        jMenuItem73.setText("Entry Wise Stock Report");
        jMenuItem73.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem73ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem73);

        jMenuItem74.setText("Entry Wise Stock Summary");
        jMenuItem74.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem74ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem74);

        jSeparator106.setBackground(ColorConstants.SUCCESS_GREEN);
        jSeparator106.setForeground(ColorConstants.SUCCESS_GREEN);
        jSeparator106.setOpaque(true);
        jSeparator106.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu5.add(jSeparator106);

        jMenuBar1.add(jMenu5);

        jMenu11.setText("PO");

        jSeparator100.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator100.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator100.setOpaque(true);
        jSeparator100.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu11.add(jSeparator100);

        jMenuItem61.setText("Purchase Order");
        jMenuItem61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem61ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem61);

        jMenuItem91.setText("Purchase Orders List");
        jMenuItem91.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem91ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem91);

        jMenuItem60.setText("Supplier & Products Link");
        jMenuItem60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem60ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem60);

        jSeparator99.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator99.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator99.setOpaque(true);
        jSeparator99.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu11.add(jSeparator99);

        jMenuItem92.setText("Purchase Orders Report");
        jMenuItem92.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem92ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem92);

        jSeparator118.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator118.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator118.setOpaque(true);
        jSeparator118.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu11.add(jSeparator118);

        jMenuBar1.add(jMenu11);

        jMenu19.setText("Packing");

        jSeparator119.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator119.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator119.setOpaque(true);
        jSeparator119.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu19.add(jSeparator119);

        jMenuItem97.setText("Packing Entry");
        jMenuItem97.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem97ActionPerformed(evt);
            }
        });
        jMenu19.add(jMenuItem97);

        jMenuItem98.setText("Packing Report");
        jMenuItem98.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem98ActionPerformed(evt);
            }
        });
        jMenu19.add(jMenuItem98);

        jSeparator120.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator120.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator120.setOpaque(true);
        jSeparator120.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu19.add(jSeparator120);

        jMenuBar1.add(jMenu19);

        jMenu12.setForeground(ColorConstants.TEXT_LIGHT);
        jMenu12.setFont(new java.awt.Font("Arial", 1, 14));

        jSeparator93.setBackground(ColorConstants.INFO_BLUE);
        jSeparator93.setForeground(ColorConstants.INFO_BLUE);
        jSeparator93.setOpaque(true);
        jSeparator93.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu12.add(jSeparator93);

        jMenuItem42.setText("Voucher Entry");
        jMenuItem42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem42ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem42);

        jMenuItem140.setText("Transfer Entry (Contra)");
        jMenuItem140.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem140ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem140);

        jMenuItem41.setText("Account Group Master");
        jMenuItem41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem41ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem41);

        jMenuItem43.setText("Bank Master");
        jMenuItem43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem43ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem43);

        jSeparator94.setBackground(ColorConstants.SUCCESS_GREEN);
        jSeparator94.setForeground(ColorConstants.SUCCESS_GREEN);
        jSeparator94.setOpaque(true);
        jSeparator94.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu12.add(jSeparator94);

        jMenuItem104.setText("Day Book Print");
        jMenuItem104.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem104ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem104);

        jMenuItem99.setText("Receipts & Payments");
        jMenuItem99.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem99ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem99);

        jMenuItem100.setText("Group Summary");
        jMenuItem100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem100ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem100);

        jMenuItem138.setText("Profit & Loss A/c");
        jMenuItem138.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem138ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem138);

        jMenuItem137.setText("Trial Balance");
        jMenuItem137.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem137ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem137);

        jMenuItem139.setText("Balance Sheet");
        jMenuItem139.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem139ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem139);

        jMenuItem142.setText("Cash Book Summary");
        jMenuItem142.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem142ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem142);

        jMenuItem143.setText("Bank Book Summary");
        jMenuItem143.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem143ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem143);

        jSeparator131.setBackground(ColorConstants.INFO_BLUE);
        jSeparator131.setForeground(ColorConstants.INFO_BLUE);
        jSeparator131.setOpaque(true);
        jSeparator131.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu12.add(jSeparator131);

        jMenuItem44.setText("Voucher Report");
        jMenuItem44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem44ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem44);

        jMenuItem101.setText("Pay Mode Wise Voucher Report");
        jMenuItem101.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem101ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem101);

        jMenuItem102.setText("Account Group(Ledger) Wise Voucher Report");
        jMenuItem102.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem102ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem102);

        jMenuItem141.setText("Group Wise Voucher Report");
        jMenuItem141.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem141ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem141);

        jSeparator98.setBackground(ColorConstants.INFO_BLUE);
        jSeparator98.setForeground(ColorConstants.INFO_BLUE);
        jSeparator98.setOpaque(true);
        jSeparator98.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu12.add(jSeparator98);

        jMenuBar1.add(jMenu12);
        jMenu2.setText("Supplier");
        jMenu2.setForeground(ColorConstants.TEXT_LIGHT);
        jMenu2.setFont(new java.awt.Font("Arial", 1, 14));

        jSeparator75.setBackground(ColorConstants.SECONDARY_ORANGE_DARK);
        jSeparator75.setForeground(ColorConstants.SECONDARY_ORANGE_DARK);
        jSeparator75.setOpaque(true);
        jSeparator75.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu2.add(jSeparator75);

        jMenuItem54.setText("Supplier Payment Entry");
        jMenuItem54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem54ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem54);

        jMenuItem147.setText("Supplier Bill Register");
        jMenuItem147.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem147ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem147);

        jMenuItem2.setText("Supplier Master");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem96.setText("Supplier List Bulk Upload");
        jMenuItem96.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem96ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem96);

        jMenuItem149.setText("Supplier Bills Bulk Upload");
        jMenuItem149.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem149ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem149);

        jSeparator76.setBackground(ColorConstants.SECONDARY_ORANGE_DARK);
        jSeparator76.setForeground(ColorConstants.SECONDARY_ORANGE_DARK);
        jSeparator76.setOpaque(true);
        jSeparator76.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu2.add(jSeparator76);

        jMenuItem55.setText("Supplier Dues Report");
        jMenuItem55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem55ActionPerformed(evt);

            }
        });
        jMenu2.add(jMenuItem55);

        jMenuItem59.setText("Supplier Overdues Report");
        jMenuItem59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem59ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem59);

        jMenuItem56.setText("Supplier Payments Report");
        jMenuItem56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem56ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem56);

        jMenuItem148.setText("Supplier Bills Report");
        jMenuItem148.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem148ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem148);

        jSeparator108.setBackground(ColorConstants.SECONDARY_ORANGE_DARK);
        jSeparator108.setForeground(ColorConstants.SECONDARY_ORANGE_DARK);
        jSeparator108.setOpaque(true);
        jSeparator108.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu2.add(jSeparator108);

        jMenuItem109.setText("Supplier List");
        jMenuItem109.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem109ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem109);

        jSeparator96.setBackground(ColorConstants.SUCCESS_GREEN);
        jSeparator96.setForeground(ColorConstants.SUCCESS_GREEN);
        jSeparator96.setOpaque(true);
        jSeparator96.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu2.add(jSeparator96);

        jMenuBar1.add(jMenu2);

        jMenu7.setText("Customer");

        jSeparator80.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator80.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator80.setOpaque(true);
        jSeparator80.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu7.add(jSeparator80);

        jMenuItem45.setText("Customer Receipt Entry");
        jMenuItem45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem45ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem45);

        jMenuItem144.setText("Customer Bill Register");
        jMenuItem144.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem144ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem144);

        jMenuItem12.setText("Customer Master");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem12);

        jMenuItem93.setText("Customer List Bulk Upload");
        jMenuItem93.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem93ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem93);

        jMenuItem146.setText("Customer Bills Bulk Upload");
        jMenuItem146.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem146ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem146);

        jSeparator65.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator65.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator65.setOpaque(true);
        jSeparator65.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu7.add(jSeparator65);

        jMenuItem46.setText("Customer Dues Report");
        jMenuItem46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem46ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem46);

        jMenuItem47.setText("Are Wise Customer Dues Report");
        jMenuItem47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem47ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem47);

        jMenuItem48.setText("Customer Overall Balance Report");
        jMenuItem48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem48ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem48);

        jMenuItem58.setText("Customer Overdues Report");
        jMenuItem58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem58ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem58);

        jMenuItem57.setText("Customer Receipts Report");
        jMenuItem57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem57ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem57);

        jMenuItem145.setText("Customer Bills Report");
        jMenuItem145.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem145ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem145);

        jMenuItem162.setText("Print Customer Balance List");
        jMenuItem162.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem162ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem162);

        jSeparator86.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator86.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator86.setOpaque(true);
        jSeparator86.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu7.add(jSeparator86);

        jMenuItem75.setText("Customer List");
        jMenuItem75.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem75ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem75);

        jMenuItem76.setText("Area Wise Customer List");
        jMenuItem76.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem76ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem76);

        jMenuItem77.setText("State Wise Customer List");
        jMenuItem77.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem77ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem77);

        jSeparator107.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator107.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator107.setOpaque(true);
        jSeparator107.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu7.add(jSeparator107);

        jMenuBar1.add(jMenu7);

        jMenu15.setText("HR");

        jSeparator103.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator103.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator103.setOpaque(true);
        jSeparator103.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu15.add(jSeparator103);

        jMenuItem64.setText("Staff Profile");
        jMenuItem64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem64ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem64);

        jMenuItem65.setText("Staffs List");
        jMenuItem65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem65ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem65);

        jSeparator109.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator109.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator109.setOpaque(true);
        jSeparator109.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu15.add(jSeparator109);

        jMenuBar1.add(jMenu15);

        jMenu22.setText("Payroll");

        jSeparator132.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator132.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator132.setOpaque(true);
        jSeparator132.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu22.add(jSeparator132);

        jMenuItem150.setText("Salary Advance Register");
        jMenuItem150.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem150ActionPerformed(evt);
            }
        });
        jMenu22.add(jMenuItem150);

        jMenuItem153.setText("Loan Register");
        jMenuItem153.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem153ActionPerformed(evt);
            }
        });
        jMenu22.add(jMenuItem153);

        jMenuItem154.setText("Payroll Generation");
        jMenuItem154.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem154ActionPerformed(evt);
            }
        });
        jMenu22.add(jMenuItem154);

        jSeparator134.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator134.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator134.setOpaque(true);
        jSeparator134.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu22.add(jSeparator134);

        jMenuItem151.setText("Salary Advance Report");
        jMenuItem151.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem151ActionPerformed(evt);
            }
        });
        jMenu22.add(jMenuItem151);

        jMenuItem155.setText("Loans Report");
        jMenuItem155.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem155ActionPerformed(evt);
            }
        });
        jMenu22.add(jMenuItem155);

        jMenuItem156.setText("Active Loans");
        jMenuItem156.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem156ActionPerformed(evt);
            }
        });
        jMenu22.add(jMenuItem156);

        jMenuItem157.setText("Salary Report");
        jMenuItem157.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem157ActionPerformed(evt);
            }
        });
        jMenu22.add(jMenuItem157);

        jSeparator135.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator135.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator135.setOpaque(true);
        jSeparator135.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu22.add(jSeparator135);

        jMenuBar1.add(jMenu22);

        jMenu23.setText("Attendance");

        jSeparator133.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator133.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator133.setOpaque(true);
        jSeparator133.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu23.add(jSeparator133);

        jMenuItem152.setText("Attendance Register");
        jMenuItem152.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem152ActionPerformed(evt);
            }
        });
        jMenu23.add(jMenuItem152);

        jMenuItem158.setText("Attendance Report");
        jMenuItem158.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem158ActionPerformed(evt);
            }
        });
        jMenu23.add(jMenuItem158);

        jMenuItem159.setText("Attendance Summary");
        jMenuItem159.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem159ActionPerformed(evt);
            }
        });
        jMenu23.add(jMenuItem159);

        jSeparator136.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator136.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator136.setOpaque(true);
        jSeparator136.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu23.add(jSeparator136);

        jMenuBar1.add(jMenu23);

        jMenu6.setText("GSTR");

        jSeparator79.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator79.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator79.setOpaque(true);
        jSeparator79.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu6.add(jSeparator79);

        jMenuItem10.setText("GSTR -Purchase");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem10);

        jMenuItem11.setText("GSTR -Sales");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem11);

        jMenuItem37.setText("GSTR -Purchase Return");
        jMenuItem37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem37ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem37);

        jMenuItem33.setText("GSTR -Sales Return");
        jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem33ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem33);

        jSeparator66.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator66.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator66.setOpaque(true);
        jSeparator66.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu6.add(jSeparator66);

        jMenuItem69.setText("HSN Wise Purchase");
        jMenuItem69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem69ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem69);

        jMenuItem68.setText("HSN Wise Sales");
        jMenuItem68.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem68ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem68);

        jMenuItem71.setText("HSN Wise Purchase Return");
        jMenuItem71.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem71ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem71);

        jMenuItem70.setText("HSN Wise Sales Return");
        jMenuItem70.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem70ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem70);

        jSeparator104.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator104.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator104.setOpaque(true);
        jSeparator104.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu6.add(jSeparator104);

        jMenuItem72.setText("GSTR Summary");
        jMenuItem72.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem72ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem72);

        jSeparator105.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator105.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator105.setOpaque(true);
        jSeparator105.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu6.add(jSeparator105);

        jMenuBar1.add(jMenu6);

        jMenu13.setText("SMS");

        jSeparator67.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator67.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator67.setOpaque(true);
        jSeparator67.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu13.add(jSeparator67);

        jMenuItem49.setText("Manual SMS");
        jMenuItem49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem49ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem49);

        jMenuItem53.setText("SMS Setting");
        jMenuItem53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem53ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem53);

        jSeparator97.setBackground(ColorConstants.INFO_BLUE);
        jSeparator97.setForeground(ColorConstants.INFO_BLUE);
        jSeparator97.setOpaque(true);
        jSeparator97.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu13.add(jSeparator97);

        jMenuBar1.add(jMenu13);

        jMenu17.setText("Email");

        jSeparator113.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator113.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator113.setOpaque(true);
        jSeparator113.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu17.add(jSeparator113);

        jMenuItem81.setText("Email Setting");
        jMenuItem81.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem81ActionPerformed(evt);
            }
        });
        jMenu17.add(jMenuItem81);

        jSeparator114.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator114.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator114.setOpaque(true);
        jSeparator114.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu17.add(jSeparator114);

        jMenuBar1.add(jMenu17);

        jMenu14.setText("Barcode");

        jSeparator101.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator101.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator101.setOpaque(true);
        jSeparator101.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu14.add(jSeparator101);

        jMenuItem62.setText("Barcode Printing");
        jMenuItem62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem62ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem62);

        jSeparator102.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator102.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator102.setOpaque(true);
        jSeparator102.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu14.add(jSeparator102);

        jMenuBar1.add(jMenu14);

        jMenu18.setText("Loyalty");

        jSeparator115.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator115.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator115.setOpaque(true);
        jSeparator115.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu18.add(jSeparator115);

        jMenuItem83.setText("Customer Points Details");
        jMenuItem83.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem83ActionPerformed(evt);
            }
        });
        jMenu18.add(jMenuItem83);

        jMenuItem89.setText("Customer Points Withdrawal ");
        jMenuItem89.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem89ActionPerformed(evt);
            }
        });
        jMenu18.add(jMenuItem89);

        jMenuItem82.setText("Loyalty Points Setting");
        jMenuItem82.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem82ActionPerformed(evt);
            }
        });
        jMenu18.add(jMenuItem82);

        jSeparator116.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator116.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator116.setOpaque(true);
        jSeparator116.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu18.add(jSeparator116);

        jMenuItem90.setText("Customer Points Withdrawal List");
        jMenuItem90.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem90ActionPerformed(evt);
            }
        });
        jMenu18.add(jMenuItem90);

        jSeparator117.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator117.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator117.setOpaque(true);
        jSeparator117.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu18.add(jSeparator117);

        jMenuBar1.add(jMenu18);

        jMenu20.setText("MIS");

        jSeparator121.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator121.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator121.setOpaque(true);
        jSeparator121.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu20.add(jSeparator121);

        jMenuItem105.setText("Category Wise Profit");
        jMenuItem105.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem105ActionPerformed(evt);
            }
        });
        jMenu20.add(jMenuItem105);

        jMenuItem106.setText("Item Wise Profit");
        jMenuItem106.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem106ActionPerformed(evt);
            }
        });
        jMenu20.add(jMenuItem106);

        jMenuItem160.setText("Barcode Wise Profit");
        jMenuItem160.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem160ActionPerformed(evt);
            }
        });
        jMenu20.add(jMenuItem160);

        jMenuItem107.setText("Bill Wise Profit");
        jMenuItem107.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem107ActionPerformed(evt);
            }
        });
        jMenu20.add(jMenuItem107);

        jMenuItem110.setText("Day Wise Profit");
        jMenuItem110.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem110ActionPerformed(evt);
            }
        });
        jMenu20.add(jMenuItem110);

        jSeparator122.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator122.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator122.setOpaque(true);
        jSeparator122.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu20.add(jSeparator122);

        jMenuItem111.setText("Top Selling Items by Qty Wise");
        jMenuItem111.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem111ActionPerformed(evt);
            }
        });
        jMenu20.add(jMenuItem111);

        jMenuItem112.setText("Customers Search for Item");
        jMenuItem112.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem112ActionPerformed(evt);
            }
        });
        jMenu20.add(jMenuItem112);

        jMenuItem113.setText("Top Customers by Points Wise");
        jMenuItem113.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem113ActionPerformed(evt);
            }
        });
        jMenu20.add(jMenuItem113);

        jMenuItem115.setText("Cashier Wise Sales");
        jMenuItem115.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem115ActionPerformed(evt);
            }
        });
        jMenu20.add(jMenuItem115);

        jSeparator123.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator123.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator123.setOpaque(true);
        jSeparator123.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu20.add(jSeparator123);

        jMenuItem116.setText("Altered Sales Bills");
        jMenuItem116.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem116ActionPerformed(evt);
            }
        });
        jMenu20.add(jMenuItem116);

        jMenuItem117.setText("Deleted Sales Bills");
        jMenuItem117.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem117ActionPerformed(evt);
            }
        });
        jMenu20.add(jMenuItem117);

        jSeparator124.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator124.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator124.setOpaque(true);
        jSeparator124.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu20.add(jSeparator124);

        jMenuBar1.add(jMenu20);

        jMenu9.setText("Users");

        jSeparator83.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator83.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator83.setOpaque(true);
        jSeparator83.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu9.add(jSeparator83);

        jMenuItem15.setText("User Management");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem15);

        jMenuItem16.setText("User Permissions");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem16);

        jMenuItemTerminal.setText("Terminal Management");
        jMenuItemTerminal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTerminalActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItemTerminal);

        jSeparator84.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator84.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator84.setOpaque(true);
        jSeparator84.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu9.add(jSeparator84);

        jMenuBar1.add(jMenu9);

        jMenu16.setText("Backup");

        jSeparator111.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator111.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator111.setOpaque(true);
        jSeparator111.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu16.add(jSeparator111);

        jMenuItem80.setText("Day End Process");
        jMenuItem80.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem80ActionPerformed(evt);
            }
        });
        jMenu16.add(jMenuItem80);

        jSeparator112.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator112.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator112.setOpaque(true);
        jSeparator112.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu16.add(jSeparator112);

        jMenuBar1.add(jMenu16);

        jMenu4.setText("Setting");

        jSeparator78.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator78.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator78.setOpaque(true);
        jSeparator78.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu4.add(jSeparator78);

        jMenuItem4.setText("Setting");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuItem40.setText("Terminal Setting");
        jMenuItem40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem40ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem40);

        jSeparator77.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator77.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator77.setOpaque(true);
        jSeparator77.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu4.add(jSeparator77);

        jMenuBar1.add(jMenu4);

        jMenu8.setText("Exit");

        jSeparator81.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator81.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator81.setOpaque(true);
        jSeparator81.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu8.add(jSeparator81);

        jMenuItem13.setText("Log out");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem13);

        jMenuItem14.setText("Exit");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem14);

        jSeparator82.setBackground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator82.setForeground(ColorConstants.PRIMARY_BLUE_LIGHT);
        jSeparator82.setOpaque(true);
        jSeparator82.setPreferredSize(new java.awt.Dimension(0, 3));
        jMenu8.add(jSeparator82);

        jMenuBar1.add(jMenu8);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem1ActionPerformed

        try {
            item_master im = new item_master(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            vendor_master im = new vendor_master(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem3ActionPerformed

        try {
            purchase_entry im = new purchase_entry(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem4ActionPerformed

        try {
            setting_bill im = new setting_bill(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem4ActionPerformed

    private void itembuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_itembuttonActionPerformed

        try {
            item_master im = new item_master(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_itembuttonActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem5ActionPerformed

        try {
            purchase_report im = new purchase_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem6ActionPerformed
        try {
            purchase_report_cname im = new purchase_report_cname(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem84ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem84ActionPerformed
        try {
            purchase_report_iname im = new purchase_report_iname(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem84ActionPerformed

    private void jMenuItem85ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem85ActionPerformed
        try {
            purchase_summary_iname im = new purchase_summary_iname(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem85ActionPerformed

    private void jMenuItem86ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem86ActionPerformed

        try {
            purchase_summary_manu im = new purchase_summary_manu(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem86ActionPerformed

    private void jMenuItem87ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem87ActionPerformed
        try {
            purchase_summary_category im = new purchase_summary_category(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem87ActionPerformed

    private void jMenuItem88ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem88ActionPerformed

        try {
            purchase_summary_supplier im = new purchase_summary_supplier(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem88ActionPerformed

    private void jMenuItem103ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem103ActionPerformed
        try {
            items_list im = new items_list(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem103ActionPerformed

    private void jMenuItem109ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem109ActionPerformed

        try {
            vendor_list im = new vendor_list(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem109ActionPerformed

    private void jMenuItem66ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem66ActionPerformed

        try {
            stock_report im = new stock_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem66ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem7ActionPerformed

        try {
            stock_summary im = new stock_summary(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem94ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem94ActionPerformed
        try {
            stock_report_min_stock im = new stock_report_min_stock(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem94ActionPerformed

    private void jMenuItem95ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem95ActionPerformed

        try {
            stock_report_max_stock im = new stock_report_max_stock(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem95ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem8ActionPerformed
        try {
            stock_report_nill_stock im = new stock_report_nill_stock(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem9ActionPerformed
        try {
            stock_report_barcode im = new stock_report_barcode(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem9ActionPerformed

    private void purchasebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_purchasebuttonActionPerformed
        try {
            purchase_entry im = new purchase_entry(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_purchasebuttonActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem10ActionPerformed
        try {
            tax_reports.tax_purchase im = new tax_purchase(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem11ActionPerformed
        try {
            tax_reports.tax_sales im = new tax_sales(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem12ActionPerformed

        try {
            custpack.cust_master im = new cust_master(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem12ActionPerformed

    private void searchbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchbuttonActionPerformed

        try {
            item_search im = new item_search(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_searchbuttonActionPerformed

    private void smsbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_smsbuttonActionPerformed
        try {
            manual_sms im = new manual_sms(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_smsbuttonActionPerformed

    private void logbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_logbuttonActionPerformed
        this.dispose();
        new login().setVisible(true);
    }// GEN-LAST:event_logbuttonActionPerformed

    private void exitbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exitbuttonActionPerformed
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec("taskkill /F /IM java.exe");
            rt.exec("taskkill /F /IM javaw.exe");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_exitbuttonActionPerformed

    private void cashbookbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cashbookbuttonActionPerformed
        try {
            voucher_entry im = new voucher_entry(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_cashbookbuttonActionPerformed

    private void settingbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_settingbuttonActionPerformed

        try {
            setting_bill im = new setting_bill(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_settingbuttonActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem14ActionPerformed
        int lk = JOptionPane.showConfirmDialog(this, "Want to Exit ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
        if (lk == JOptionPane.NO_OPTION) {
            return;
        }
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec("taskkill /F /IM java.exe");
            rt.exec("taskkill /F /IM javaw.exe");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem17ActionPerformed
        try {
            salespack.sales_report im = new sales_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem18ActionPerformed
        try {
            salespack.sales_report_location im = new sales_report_location(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem19ActionPerformed
        try {
            salespack.sales_report_price_type im = new sales_report_price_type(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem20ActionPerformed
        try {
            salespack.sales_report_terminal im = new sales_report_terminal(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem21ActionPerformed
        try {
            salespack.sales_summary im = new sales_summary(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem22ActionPerformed

        try {
            salespack.sales_summary_cust im = new sales_summary_cust(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem23ActionPerformed

        try {
            salespack.sales_report_cust im = new sales_report_cust(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem24ActionPerformed
        try {
            salespack.sales_summary_item im = new sales_summary_item(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem25ActionPerformed
        try {
            salespack.sales_summary_category im = new sales_summary_category(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem26ActionPerformed

        try {
            salespack.sales_summary_manu im = new sales_summary_manu(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem27ActionPerformed
        try {
            salespack.sales_summary_daywise im = new sales_summary_daywise(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem28ActionPerformed
        try {
            salespack.sales_report_bill_itemwise im = new sales_report_bill_itemwise(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_jMenuItem28ActionPerformed

    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem29ActionPerformed

        try {
            salespack.daily_sales_select im = new daily_sales_select(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem29ActionPerformed

    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem30ActionPerformed

        try {
            salespack.daily_sales_report_select im = new daily_sales_report_select(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // TODO add your handling code here:
    }// GEN-LAST:event_jMenuItem30ActionPerformed

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem31ActionPerformed

        try {
            salespack.sreturn_report im = new sreturn_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO add your handling code here:
    }// GEN-LAST:event_jMenuItem31ActionPerformed

    private void jMenuItem32ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem32ActionPerformed

        try {
            salespack.sreturn_summary_item im = new sreturn_summary_item(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem32ActionPerformed

    private void jMenuItem33ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem33ActionPerformed

        try {
            tax_sales_return im = new tax_sales_return(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_jMenuItem33ActionPerformed

    private void jMenuItem34ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem34ActionPerformed
        try {
            preturn_entry im = new preturn_entry(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_jMenuItem34ActionPerformed

    private void jMenuItem35ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem35ActionPerformed

        try {
            preturn_report im = new preturn_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_jMenuItem35ActionPerformed

    private void jMenuItem36ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem36ActionPerformed
        try {
            preturn_summary_iname im = new preturn_summary_iname(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem36ActionPerformed

    private void jMenuItem37ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem37ActionPerformed
        try {
            tax_purchase_return im = new tax_purchase_return(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem37ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem15ActionPerformed
        try {
            user im = new user(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem13ActionPerformed

        this.dispose();
        new login().setVisible(true);
    }// GEN-LAST:event_jMenuItem13ActionPerformed

    private void salesbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_salesbuttonActionPerformed
        try {
            sales im = new sales(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// GEN-LAST:event_salesbuttonActionPerformed

    private void jMenuItem38ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem38ActionPerformed
        try {
            sales im = new sales(util, version, user_type);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO add your handling code here:
    }// GEN-LAST:event_jMenuItem38ActionPerformed

    private void jMenuItem39ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem39ActionPerformed

        try {
            sreturn im = new sreturn(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem39ActionPerformed

    private void jMenuItem40ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem40ActionPerformed

        try {
            setting_terminal im = new setting_terminal(drive, folder);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_jMenuItem40ActionPerformed

    private void jMenuItem41ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem41ActionPerformed
        try {
            master_accounts im = new master_accounts(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem41ActionPerformed

    private void jMenuItem42ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem42ActionPerformed
        try {
            voucher_entry im = new voucher_entry(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem42ActionPerformed

    private void jMenuItem43ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem43ActionPerformed
        try {
            bank_master im = new bank_master(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem43ActionPerformed

    private void jMenuItem44ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem44ActionPerformed
        try {
            voucher_report im = new voucher_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_jMenuItem44ActionPerformed

    private void jMenuItem45ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem45ActionPerformed
        try {
            cust_pay im = new cust_pay(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem45ActionPerformed

    private void jMenuItem46ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem46ActionPerformed
        try {
            cust_dues_report im = new cust_dues_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem46ActionPerformed

    private void jMenuItem47ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem47ActionPerformed

        try {
            cust_dues_report_area im = new cust_dues_report_area(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem47ActionPerformed

    private void jMenuItem48ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem48ActionPerformed
        try {
            cust_balance_report im = new cust_balance_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_jMenuItem48ActionPerformed

    private void jMenuItem49ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem49ActionPerformed
        try {
            manual_sms im = new manual_sms(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem49ActionPerformed

    private void jMenuItem50ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem50ActionPerformed
        try {
            item_search im = new item_search(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem50ActionPerformed

    private void clearlMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_clearlMouseClicked
        int as = JOptionPane.showConfirmDialog(this, "Want to Refresh ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
        if (as == JOptionPane.NO_OPTION) {
            return;
        }
        get_details();
    }// GEN-LAST:event_clearlMouseClicked

    private void jMenuItem51ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem51ActionPerformed

        try {
            stock_entry im = new stock_entry(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem51ActionPerformed

    private void jMenuItem52ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem52ActionPerformed
        try {
            stock_entry_report im = new stock_entry_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem52ActionPerformed

    private void jMenuItem53ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem53ActionPerformed
        try {
            smspack.sms_setting im = new sms_setting(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem53ActionPerformed

    private void jMenuItem54ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem54ActionPerformed
        try {
            ven_pay im = new ven_pay(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem54ActionPerformed

    private void jMenuItem55ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem55ActionPerformed
        try {
            ven_dues_report im = new ven_dues_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem55ActionPerformed

    private void jMenuItem56ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem56ActionPerformed
        try {
            ven_pay_report im = new ven_pay_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_jMenuItem56ActionPerformed

    private void jMenuItem57ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem57ActionPerformed
        try {
            cust_pay_report im = new cust_pay_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem57ActionPerformed

    private void jMenuItem58ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem58ActionPerformed
        try {
            cust_dues_overdue im = new cust_dues_overdue(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem58ActionPerformed

    private void jMenuItem59ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem59ActionPerformed

        try {
            ven_dues_overdue im = new ven_dues_overdue(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem59ActionPerformed

    private void daycloseMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_daycloseMouseClicked
        int as = JOptionPane.showConfirmDialog(this, "Want to Close Day Book ?", "Are You Sure",
                JOptionPane.YES_NO_OPTION);
        if (as == JOptionPane.YES_OPTION) {
            try {
                day_book_close im = new day_book_close(util);
                jDesktopPane1.add(im);
                im.setVisible(true);

                Dimension desktopSize = jDesktopPane1.getSize();
                Dimension jInternalFrameSize = im.getSize();
                im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                        (desktopSize.height - jInternalFrameSize.height) / 2);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }// GEN-LAST:event_daycloseMouseClicked

    private void jMenuItem60ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem60ActionPerformed
        try {
            vendor_link im = new vendor_link(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem60ActionPerformed

    private void jMenuItem61ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem61ActionPerformed
        try {
            purchase_order_pack.purchase_order im = new purchase_order(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem61ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem16ActionPerformed
        try {
            user_permissions im = new user_permissions(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItemTerminalActionPerformed(java.awt.event.ActionEvent evt) {
        new TerminalManagerDialog(this).setVisible(true);
    }

    private void jMenuItem62ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem62ActionPerformed
        try {
            barcodepack.barcode_print im = new barcode_print(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem62ActionPerformed

    private void jMenuItem63ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem63ActionPerformed
        try {
            items_import_excel im = new items_import_excel(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem63ActionPerformed

    private void jMenuItem64ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem64ActionPerformed
        try {
            staff_entry im = new staff_entry(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem64ActionPerformed

    private void jMenuItem65ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem65ActionPerformed
        try {
            staff_list im = new staff_list(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem65ActionPerformed

    private void jMenuItem67ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem67ActionPerformed

        try {
            stock_bulk_rate_update im = new stock_bulk_rate_update(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO add your handling code here:
    }// GEN-LAST:event_jMenuItem67ActionPerformed

    private void jMenuItem68ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem68ActionPerformed
        try {
            tax_hsn_sales im = new tax_hsn_sales(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem68ActionPerformed

    private void jMenuItem69ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem69ActionPerformed
        try {
            tax_hsn_purchase im = new tax_hsn_purchase(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem69ActionPerformed

    private void jMenuItem70ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem70ActionPerformed

        try {
            tax_hsn_sales_return im = new tax_hsn_sales_return(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem70ActionPerformed

    private void jMenuItem71ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem71ActionPerformed
        try {
            tax_hsn_purchase_return im = new tax_hsn_purchase_return(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem71ActionPerformed

    private void jMenuItem72ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem72ActionPerformed
        try {
            tax_summary im = new tax_summary(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem72ActionPerformed

    private void jMenuItem73ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem73ActionPerformed
        try {
            stock_report_entry_wise im = new stock_report_entry_wise(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem73ActionPerformed

    private void jMenuItem74ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem74ActionPerformed

        try {
            stock_summary_entry_wise im = new stock_summary_entry_wise(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem74ActionPerformed

    private void jMenuItem75ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem75ActionPerformed
        try {
            cust_list im = new cust_list(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // TODO add your handling code here:
    }// GEN-LAST:event_jMenuItem75ActionPerformed

    private void jMenuItem76ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem76ActionPerformed
        try {
            cust_list_area im = new cust_list_area(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem76ActionPerformed

    private void jMenuItem77ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem77ActionPerformed

        try {
            cust_list_state im = new cust_list_state(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO add your handling code here:
    }// GEN-LAST:event_jMenuItem77ActionPerformed

    private void jMenuItem78ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem78ActionPerformed

        try {
            stock_alter im = new stock_alter(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem78ActionPerformed

    private void jMenuItem79ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem79ActionPerformed
        try {
            stock_alter_report im = new stock_alter_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO add your handling code here:
    }// GEN-LAST:event_jMenuItem79ActionPerformed

    private void jMenuItem80ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem80ActionPerformed

        try {
            day_book_close im = new day_book_close(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO add your handling code here:
    }// GEN-LAST:event_jMenuItem80ActionPerformed

    private void jMenuItem81ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem81ActionPerformed
        try {
            emailpack.email_setting im = new email_setting(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem81ActionPerformed

    private void jMenuItem82ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem82ActionPerformed
        try {
            points_setting im = new points_setting(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem82ActionPerformed

    private void jMenuItem83ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem83ActionPerformed

        try {
            cust_points_details im = new cust_points_details(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem83ActionPerformed

    private void jMenuItem89ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem89ActionPerformed
        try {
            cust_points_withdraw im = new cust_points_withdraw(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem89ActionPerformed

    private void jMenuItem90ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem90ActionPerformed

        try {
            cust_points_withdraw_list im = new cust_points_withdraw_list(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem90ActionPerformed

    private void jMenuItem91ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem91ActionPerformed
        try {
            purchase_order_list im = new purchase_order_list(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // TODO add your handling code here:
    }// GEN-LAST:event_jMenuItem91ActionPerformed

    private void jMenuItem92ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem92ActionPerformed

        try {
            purchase_order_report im = new purchase_order_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem92ActionPerformed

    private void jMenuItem93ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem93ActionPerformed
        try {
            cust_import_excel im = new cust_import_excel(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem93ActionPerformed

    private void jMenuItem96ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem96ActionPerformed
        try {
            vendor_import_excel im = new vendor_import_excel(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem96ActionPerformed

    private void jMenuItem97ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem97ActionPerformed
        try {
            packingpack.packing_entry im = new packing_entry(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem97ActionPerformed

    private void jMenuItem98ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem98ActionPerformed
        try {
            packingpack.packing_report im = new packing_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem98ActionPerformed

    private void jMenuItem99ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem99ActionPerformed
        try {
            RP_select im = new RP_select(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem99ActionPerformed

    private void jMenuItem100ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem100ActionPerformed
        try {
            Group_select im = new Group_select(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem100ActionPerformed

    private void jMenuItem101ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem101ActionPerformed
        try {
            voucher_report_pby im = new voucher_report_pby(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem101ActionPerformed

    private void jMenuItem102ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem102ActionPerformed

        try {
            voucher_report_group_ledger im = new voucher_report_group_ledger(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem102ActionPerformed

    private void jMenuItem105ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem105ActionPerformed
        try {
            profit_category im = new profit_category(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO add your handling code here:
    }// GEN-LAST:event_jMenuItem105ActionPerformed

    private void jMenuItem106ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem106ActionPerformed
        try {
            profit_itemwise im = new profit_itemwise(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem106ActionPerformed

    private void jMenuItem107ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem107ActionPerformed
        try {
            profit_billwise im = new profit_billwise(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem107ActionPerformed

    private void jMenuItem110ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem110ActionPerformed
        try {
            profit_daywise im = new profit_daywise(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem110ActionPerformed

    private void jMenuItem111ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem111ActionPerformed
        try {
            top_selling_items im = new top_selling_items(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // TODO add your handling code here:
    }// GEN-LAST:event_jMenuItem111ActionPerformed

    private void jMenuItem112ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem112ActionPerformed

        try {
            top_customers_for_item im = new top_customers_for_item(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem112ActionPerformed

    private void jMenuItem113ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem113ActionPerformed

        try {
            top_customers_points_wise im = new top_customers_points_wise(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem113ActionPerformed

    private void jMenuItem114ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem114ActionPerformed
        try {
            sales_summary_userwise im = new sales_summary_userwise(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem114ActionPerformed

    private void jMenuItem115ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem115ActionPerformed
        try {
            top_users im = new top_users(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem115ActionPerformed

    private void jMenuItem116ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem116ActionPerformed
        try {
            sales_alter_list im = new sales_alter_list(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem116ActionPerformed

    private void jMenuItem117ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem117ActionPerformed
        try {
            sales_deleted_list im = new sales_deleted_list(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem117ActionPerformed

    private void jMenuItem118ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem118ActionPerformed
        try {
            estimate im = new estimate(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem118ActionPerformed

    private void estimatebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_estimatebuttonActionPerformed
        try {
            estimate im = new estimate(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_estimatebuttonActionPerformed

    private void jMenuItem119ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem119ActionPerformed
        try {
            daily_estimate_select im = new daily_estimate_select(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem119ActionPerformed

    private void jMenuItem120ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem120ActionPerformed
        try {
            daily_estimate_report_select im = new daily_estimate_report_select(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_jMenuItem120ActionPerformed

    private void jMenuItem121ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem121ActionPerformed
        try {
            estimate_report im = new estimate_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem121ActionPerformed

    private void jMenuItem122ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem122ActionPerformed
        try {
            estimate_report_location im = new estimate_report_location(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem122ActionPerformed

    private void jMenuItem123ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem123ActionPerformed
        try {
            estimate_report_terminal im = new estimate_report_terminal(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem123ActionPerformed

    private void jMenuItem124ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem124ActionPerformed
        try {
            estimate_report_price_type im = new estimate_report_price_type(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem124ActionPerformed

    private void jMenuItem125ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem125ActionPerformed

        try {
            estimate_report_cust im = new estimate_report_cust(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem125ActionPerformed

    private void jMenuItem126ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem126ActionPerformed
        try {
            estimate_report_bill_itemwise im = new estimate_report_bill_itemwise(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem126ActionPerformed

    private void jMenuItem127ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem127ActionPerformed
        try {
            estimate_summary im = new estimate_summary(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem127ActionPerformed

    private void jMenuItem128ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem128ActionPerformed
        try {
            estimate_summary_daywise im = new estimate_summary_daywise(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem128ActionPerformed

    private void jMenuItem129ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem129ActionPerformed
        try {
            estimate_summary_cust im = new estimate_summary_cust(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem129ActionPerformed

    private void jMenuItem130ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem130ActionPerformed
        try {
            estimate_summary_item im = new estimate_summary_item(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem130ActionPerformed

    private void jMenuItem131ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem131ActionPerformed
        try {
            estimate_summary_category im = new estimate_summary_category(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem131ActionPerformed

    private void jMenuItem132ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem132ActionPerformed
        try {
            estimate_summary_manu im = new estimate_summary_manu(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem132ActionPerformed

    private void jMenuItem133ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem133ActionPerformed
        try {
            estimate_summary_userwise im = new estimate_summary_userwise(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem133ActionPerformed

    private void jMenuItem134ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem134ActionPerformed
        try {
            ereturn im = new ereturn(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem134ActionPerformed

    private void jMenuItem135ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem135ActionPerformed
        try {
            ereturn_report im = new ereturn_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem135ActionPerformed

    private void jMenuItem136ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem136ActionPerformed
        try {
            ereturn_summary_item im = new ereturn_summary_item(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem136ActionPerformed

    private void customerbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_customerbuttonActionPerformed
        try {
            cust_master im = new cust_master(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_customerbuttonActionPerformed

    private void jMenuItem138ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem138ActionPerformed

        try {
            new profit_loss_summary().Report(util);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_jMenuItem138ActionPerformed

    private void jMenuItem137ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem137ActionPerformed
        new trial_balance_summary().Report(util);
    }// GEN-LAST:event_jMenuItem137ActionPerformed

    private void jMenuItem139ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem139ActionPerformed
        new balance_sheet_summary().Report(util);
    }// GEN-LAST:event_jMenuItem139ActionPerformed

    private void jMenuItem140ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem140ActionPerformed
        try {
            voucher_trasfer im = new voucher_trasfer(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem140ActionPerformed

    private void jMenuItem141ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem141ActionPerformed
        try {
            voucher_report_group im = new voucher_report_group(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem141ActionPerformed

    private void jMenuItem142ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem142ActionPerformed
        try {
            Cashbook_Summary_select im = new Cashbook_Summary_select(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// GEN-LAST:event_jMenuItem142ActionPerformed

    private void jMenuItem143ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem143ActionPerformed
        try {
            Bankbook_Summary_select im = new Bankbook_Summary_select(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem143ActionPerformed

    private void activatelMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_activatelMouseClicked
        // Check if already activated
        if (activatel.getText().contains("Activated")) {
            JOptionPane.showMessageDialog(this, 
                "Full version is already activated!\nEnjoy all premium features.",
                "Already Activated", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Check internet connection for activation
        String connection = check_internet_connect();
        if (connection.equals("Yes")) {
            try {
                new ActivationPack.activation_home().setVisible(true);
                this.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error opening activation window: " + e.getMessage(),
                    "Activation Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Internet connection is required for activation!\nPlease check your network connection and try again.",
                "No Internet Connection", 
                JOptionPane.WARNING_MESSAGE);
        }
    }// GEN-LAST:event_activatelMouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel12MouseClicked
        try {
            salespack.sales_report im = new sales_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jLabel12MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel8MouseClicked
        try {
            salespack.sales_report im = new sales_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jLabel8MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel10MouseClicked
        try {
            cust_pay_report im = new cust_pay_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jLabel10MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel15MouseClicked
        try {
            purchase_order_report im = new purchase_order_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jLabel15MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel6MouseClicked
        try {
            purchase_report im = new purchase_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } // TODO add your handling code here:
    }// GEN-LAST:event_jLabel6MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel4MouseClicked
        try {
            stock_summary im = new stock_summary(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jLabel4MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel17MouseClicked
        try {
            stock_report_min_stock im = new stock_report_min_stock(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } // TODO add your handling code here:
    }// GEN-LAST:event_jLabel17MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel21MouseClicked

        try {
            stock_report_max_stock im = new stock_report_max_stock(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } // TODO add your handling code here:
    }// GEN-LAST:event_jLabel21MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel2MouseClicked
        try {
            stock_report_nill_stock im = new stock_report_nill_stock(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } // TODO add your handling code here:
    }// GEN-LAST:event_jLabel2MouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel19MouseClicked
        try {
            items_list im = new items_list(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } // TODO add your handling code here:
    }// GEN-LAST:event_jLabel19MouseClicked

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel24MouseClicked
        try {
            cust_balance_report im = new cust_balance_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } // TODO add your handling code here:
    }// GEN-LAST:event_jLabel24MouseClicked

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel27MouseClicked
        try {
            ven_dues_report im = new ven_dues_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } // TODO add your handling code here:
    }// GEN-LAST:event_jLabel27MouseClicked

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel29MouseClicked

        try {
            voucher_report im = new voucher_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } // TODO add your handling code here:
    }// GEN-LAST:event_jLabel29MouseClicked

    private void jLabel37MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel37MouseClicked
        try {
            cust_dues_overdue im = new cust_dues_overdue(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } // TODO add your handling code here:
    }// GEN-LAST:event_jLabel37MouseClicked

    private void jLabel39MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel39MouseClicked
        try {
            ven_dues_overdue im = new ven_dues_overdue(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jLabel39MouseClicked

    private void jMenuItem144ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem144ActionPerformed
        try {
            cust_bill im = new cust_bill(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem144ActionPerformed

    private void jMenuItem145ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem145ActionPerformed
        try {
            cust_bill_report im = new cust_bill_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem145ActionPerformed

    private void jMenuItem146ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem146ActionPerformed
        try {
            cust_bills_bulk_upload im = new cust_bills_bulk_upload(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem146ActionPerformed

    private void jMenuItem147ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem147ActionPerformed
        try {
            ven_bill im = new ven_bill(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem147ActionPerformed

    private void jMenuItem148ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem148ActionPerformed
        try {
            ven_bill_report im = new ven_bill_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem148ActionPerformed

    private void jMenuItem149ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem149ActionPerformed
        try {
            ven_bills_bulk_upload im = new ven_bills_bulk_upload(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem149ActionPerformed

    private void jMenuItem150ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem150ActionPerformed
        try {
            pay_advance im = new pay_advance(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem150ActionPerformed

    private void jMenuItem151ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem151ActionPerformed
        try {
            pay_advance_report im = new pay_advance_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem151ActionPerformed

    private void jMenuItem152ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem152ActionPerformed
        try {
            attendancepack.attendance_entry im = new attendance_entry(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem152ActionPerformed

    private void jMenuItem153ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem153ActionPerformed
        try {
            pay_loan im = new pay_loan(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem153ActionPerformed

    private void jMenuItem154ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem154ActionPerformed
        try {
            pay_bill_generation im = new pay_bill_generation(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem154ActionPerformed

    private void jMenuItem155ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem155ActionPerformed
        try {
            pay_loan_report im = new pay_loan_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem155ActionPerformed

    private void jMenuItem156ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem156ActionPerformed
        try {
            pay_loan_report_active im = new pay_loan_report_active(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem156ActionPerformed

    private void jMenuItem157ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem157ActionPerformed
        try {
            pay_bill_report im = new pay_bill_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem157ActionPerformed

    private void jMenuItem158ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem158ActionPerformed
        try {
            attendance_report im = new attendance_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem158ActionPerformed

    private void jMenuItem159ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem159ActionPerformed
        try {
            attendance_summary im = new attendance_summary(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem159ActionPerformed

    private void jMenuItem160ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem160ActionPerformed
        try {
            profit_itemwise_barcode im = new profit_itemwise_barcode(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem160ActionPerformed

    private void jMenuItem162ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem162ActionPerformed
        try {
            cust_dues_report_print_select im = new cust_dues_report_print_select(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem162ActionPerformed

    private void jMenuItem163ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem163ActionPerformed
        try {
            stock_import im = new stock_import(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }// GEN-LAST:event_jMenuItem163ActionPerformed

    private void jMenuItem164ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            expiry_report im = new expiry_report(util);
            jDesktopPane1.add(im);
            im.setVisible(true);

            Dimension desktopSize = jDesktopPane1.getSize();
            Dimension jInternalFrameSize = im.getSize();
            im.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void jMenuItem104ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem104ActionPerformed
        try {
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            String from = g.format(d);

            new day_book_print().Report(util, from, from);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } // TODO add your handling code here:
    }// GEN-LAST:event_jMenuItem104ActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException
                | UnsupportedLookAndFeelException ex) {
            System.out.println(ex.getMessage());
        }
        UIManager.put("nimbusFocus", new Color(0, 51, 255, 255));
        UIManager.put("nimbusSelectionBackground", new Color(0, 51, 255, 255));
        UIManager.put("nimbusBase", new Color(0, 51, 255, 255));
        UIManager.put("control", new Color(255, 255, 255, 255));
        UIManager.getLookAndFeelDefaults().put("Button.background", new Color(255, 255, 255, 255));
        UIManager.getLookAndFeelDefaults().put("Button.textForeground", new Color(55, 55, 55, 255));
        java.awt.EventQueue.invokeLater(() -> {
            new menu_form().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel activatel;
    private javax.swing.JButton cashbookbutton;
    private javax.swing.JLabel clearl;
    private javax.swing.JLabel cnamel;
    private javax.swing.JButton customerbutton;
    private javax.swing.JLabel dayclose;
    private javax.swing.JButton estimatebutton;
    private javax.swing.JButton exitbutton;
    private javax.swing.JButton itembutton;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu14;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu16;
    private javax.swing.JMenu jMenu17;
    private javax.swing.JMenu jMenu18;
    private javax.swing.JMenu jMenu19;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu20;
    private javax.swing.JMenu jMenu21;
    private javax.swing.JMenu jMenu22;
    private javax.swing.JMenu jMenu23;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem100;
    private javax.swing.JMenuItem jMenuItem101;
    private javax.swing.JMenuItem jMenuItem102;
    private javax.swing.JMenuItem jMenuItem103;
    private javax.swing.JMenuItem jMenuItem104;
    private javax.swing.JMenuItem jMenuItem105;
    private javax.swing.JMenuItem jMenuItem106;
    private javax.swing.JMenuItem jMenuItem107;
    private javax.swing.JMenuItem jMenuItem109;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem110;
    private javax.swing.JMenuItem jMenuItem111;
    private javax.swing.JMenuItem jMenuItem112;
    private javax.swing.JMenuItem jMenuItem113;
    private javax.swing.JMenuItem jMenuItem114;
    private javax.swing.JMenuItem jMenuItem115;
    private javax.swing.JMenuItem jMenuItem116;
    private javax.swing.JMenuItem jMenuItem117;
    private javax.swing.JMenuItem jMenuItem118;
    private javax.swing.JMenuItem jMenuItem119;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem120;
    private javax.swing.JMenuItem jMenuItem121;
    private javax.swing.JMenuItem jMenuItem122;
    private javax.swing.JMenuItem jMenuItem123;
    private javax.swing.JMenuItem jMenuItem124;
    private javax.swing.JMenuItem jMenuItem125;
    private javax.swing.JMenuItem jMenuItem126;
    private javax.swing.JMenuItem jMenuItem127;
    private javax.swing.JMenuItem jMenuItem128;
    private javax.swing.JMenuItem jMenuItem129;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem130;
    private javax.swing.JMenuItem jMenuItem131;
    private javax.swing.JMenuItem jMenuItem132;
    private javax.swing.JMenuItem jMenuItem133;
    private javax.swing.JMenuItem jMenuItem134;
    private javax.swing.JMenuItem jMenuItem135;
    private javax.swing.JMenuItem jMenuItem136;
    private javax.swing.JMenuItem jMenuItem137;
    private javax.swing.JMenuItem jMenuItem138;
    private javax.swing.JMenuItem jMenuItem139;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem140;
    private javax.swing.JMenuItem jMenuItem141;
    private javax.swing.JMenuItem jMenuItem142;
    private javax.swing.JMenuItem jMenuItem143;
    private javax.swing.JMenuItem jMenuItem144;
    private javax.swing.JMenuItem jMenuItem145;
    private javax.swing.JMenuItem jMenuItem146;
    private javax.swing.JMenuItem jMenuItem147;
    private javax.swing.JMenuItem jMenuItem148;
    private javax.swing.JMenuItem jMenuItem149;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem150;
    private javax.swing.JMenuItem jMenuItem151;
    private javax.swing.JMenuItem jMenuItem152;
    private javax.swing.JMenuItem jMenuItem153;
    private javax.swing.JMenuItem jMenuItem154;
    private javax.swing.JMenuItem jMenuItem155;
    private javax.swing.JMenuItem jMenuItem156;
    private javax.swing.JMenuItem jMenuItem157;
    private javax.swing.JMenuItem jMenuItem158;
    private javax.swing.JMenuItem jMenuItem159;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem160;
    private javax.swing.JMenuItem jMenuItem162;
    private javax.swing.JMenuItem jMenuItem163;
    private javax.swing.JMenuItem jMenuItem164;
    private javax.swing.JMenuItem jMenuItemTerminal;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem32;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem36;
    private javax.swing.JMenuItem jMenuItem37;
    private javax.swing.JMenuItem jMenuItem38;
    private javax.swing.JMenuItem jMenuItem39;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem40;
    private javax.swing.JMenuItem jMenuItem41;
    private javax.swing.JMenuItem jMenuItem42;
    private javax.swing.JMenuItem jMenuItem43;
    private javax.swing.JMenuItem jMenuItem44;
    private javax.swing.JMenuItem jMenuItem45;
    private javax.swing.JMenuItem jMenuItem46;
    private javax.swing.JMenuItem jMenuItem47;
    private javax.swing.JMenuItem jMenuItem48;
    private javax.swing.JMenuItem jMenuItem49;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem50;
    private javax.swing.JMenuItem jMenuItem51;
    private javax.swing.JMenuItem jMenuItem52;
    private javax.swing.JMenuItem jMenuItem53;
    private javax.swing.JMenuItem jMenuItem54;
    private javax.swing.JMenuItem jMenuItem55;
    private javax.swing.JMenuItem jMenuItem56;
    private javax.swing.JMenuItem jMenuItem57;
    private javax.swing.JMenuItem jMenuItem58;
    private javax.swing.JMenuItem jMenuItem59;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem60;
    private javax.swing.JMenuItem jMenuItem61;
    private javax.swing.JMenuItem jMenuItem62;
    private javax.swing.JMenuItem jMenuItem63;
    private javax.swing.JMenuItem jMenuItem64;
    private javax.swing.JMenuItem jMenuItem65;
    private javax.swing.JMenuItem jMenuItem66;
    private javax.swing.JMenuItem jMenuItem67;
    private javax.swing.JMenuItem jMenuItem68;
    private javax.swing.JMenuItem jMenuItem69;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem70;
    private javax.swing.JMenuItem jMenuItem71;
    private javax.swing.JMenuItem jMenuItem72;
    private javax.swing.JMenuItem jMenuItem73;
    private javax.swing.JMenuItem jMenuItem74;
    private javax.swing.JMenuItem jMenuItem75;
    private javax.swing.JMenuItem jMenuItem76;
    private javax.swing.JMenuItem jMenuItem77;
    private javax.swing.JMenuItem jMenuItem78;
    private javax.swing.JMenuItem jMenuItem79;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem80;
    private javax.swing.JMenuItem jMenuItem81;
    private javax.swing.JMenuItem jMenuItem82;
    private javax.swing.JMenuItem jMenuItem83;
    private javax.swing.JMenuItem jMenuItem84;
    private javax.swing.JMenuItem jMenuItem85;
    private javax.swing.JMenuItem jMenuItem86;
    private javax.swing.JMenuItem jMenuItem87;
    private javax.swing.JMenuItem jMenuItem88;
    private javax.swing.JMenuItem jMenuItem89;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem jMenuItem90;
    private javax.swing.JMenuItem jMenuItem91;
    private javax.swing.JMenuItem jMenuItem92;
    private javax.swing.JMenuItem jMenuItem93;
    private javax.swing.JMenuItem jMenuItem94;
    private javax.swing.JMenuItem jMenuItem95;
    private javax.swing.JMenuItem jMenuItem96;
    private javax.swing.JMenuItem jMenuItem97;
    private javax.swing.JMenuItem jMenuItem98;
    private javax.swing.JMenuItem jMenuItem99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu.Separator jSeparator100;
    private javax.swing.JPopupMenu.Separator jSeparator101;
    private javax.swing.JPopupMenu.Separator jSeparator102;
    private javax.swing.JPopupMenu.Separator jSeparator103;
    private javax.swing.JPopupMenu.Separator jSeparator104;
    private javax.swing.JPopupMenu.Separator jSeparator105;
    private javax.swing.JPopupMenu.Separator jSeparator106;
    private javax.swing.JPopupMenu.Separator jSeparator107;
    private javax.swing.JPopupMenu.Separator jSeparator108;
    private javax.swing.JPopupMenu.Separator jSeparator109;
    private javax.swing.JPopupMenu.Separator jSeparator111;
    private javax.swing.JPopupMenu.Separator jSeparator112;
    private javax.swing.JPopupMenu.Separator jSeparator113;
    private javax.swing.JPopupMenu.Separator jSeparator114;
    private javax.swing.JPopupMenu.Separator jSeparator115;
    private javax.swing.JPopupMenu.Separator jSeparator116;
    private javax.swing.JPopupMenu.Separator jSeparator117;
    private javax.swing.JPopupMenu.Separator jSeparator118;
    private javax.swing.JPopupMenu.Separator jSeparator119;
    private javax.swing.JPopupMenu.Separator jSeparator120;
    private javax.swing.JPopupMenu.Separator jSeparator121;
    private javax.swing.JPopupMenu.Separator jSeparator122;
    private javax.swing.JPopupMenu.Separator jSeparator123;
    private javax.swing.JPopupMenu.Separator jSeparator124;
    private javax.swing.JPopupMenu.Separator jSeparator125;
    private javax.swing.JPopupMenu.Separator jSeparator126;
    private javax.swing.JPopupMenu.Separator jSeparator127;
    private javax.swing.JPopupMenu.Separator jSeparator128;
    private javax.swing.JPopupMenu.Separator jSeparator129;
    private javax.swing.JPopupMenu.Separator jSeparator130;
    private javax.swing.JPopupMenu.Separator jSeparator131;
    private javax.swing.JPopupMenu.Separator jSeparator132;
    private javax.swing.JPopupMenu.Separator jSeparator133;
    private javax.swing.JPopupMenu.Separator jSeparator134;
    private javax.swing.JPopupMenu.Separator jSeparator135;
    private javax.swing.JPopupMenu.Separator jSeparator136;
    private javax.swing.JPopupMenu.Separator jSeparator63;
    private javax.swing.JPopupMenu.Separator jSeparator64;
    private javax.swing.JPopupMenu.Separator jSeparator65;
    private javax.swing.JPopupMenu.Separator jSeparator66;
    private javax.swing.JPopupMenu.Separator jSeparator67;
    private javax.swing.JPopupMenu.Separator jSeparator68;
    private javax.swing.JPopupMenu.Separator jSeparator69;
    private javax.swing.JPopupMenu.Separator jSeparator70;
    private javax.swing.JPopupMenu.Separator jSeparator71;
    private javax.swing.JPopupMenu.Separator jSeparator72;
    private javax.swing.JPopupMenu.Separator jSeparator74;
    private javax.swing.JPopupMenu.Separator jSeparator75;
    private javax.swing.JPopupMenu.Separator jSeparator76;
    private javax.swing.JPopupMenu.Separator jSeparator77;
    private javax.swing.JPopupMenu.Separator jSeparator78;
    private javax.swing.JPopupMenu.Separator jSeparator79;
    private javax.swing.JPopupMenu.Separator jSeparator80;
    private javax.swing.JPopupMenu.Separator jSeparator81;
    private javax.swing.JPopupMenu.Separator jSeparator82;
    private javax.swing.JPopupMenu.Separator jSeparator83;
    private javax.swing.JPopupMenu.Separator jSeparator84;
    private javax.swing.JPopupMenu.Separator jSeparator85;
    private javax.swing.JPopupMenu.Separator jSeparator86;
    private javax.swing.JPopupMenu.Separator jSeparator87;
    private javax.swing.JPopupMenu.Separator jSeparator88;
    private javax.swing.JPopupMenu.Separator jSeparator89;
    private javax.swing.JPopupMenu.Separator jSeparator90;
    private javax.swing.JPopupMenu.Separator jSeparator91;
    private javax.swing.JPopupMenu.Separator jSeparator92;
    private javax.swing.JPopupMenu.Separator jSeparator93;
    private javax.swing.JPopupMenu.Separator jSeparator94;
    private javax.swing.JPopupMenu.Separator jSeparator95;
    private javax.swing.JPopupMenu.Separator jSeparator96;
    private javax.swing.JPopupMenu.Separator jSeparator97;
    private javax.swing.JPopupMenu.Separator jSeparator98;
    private javax.swing.JPopupMenu.Separator jSeparator99;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton logbutton;
    private javax.swing.JButton purchasebutton;
    private javax.swing.JButton salesbutton;
    private javax.swing.JButton searchbutton;
    private javax.swing.JButton settingbutton;
    private javax.swing.JButton smsbutton;
    private javax.swing.JLabel versionl;
    // End of variables declaration//GEN-END:variables
}
