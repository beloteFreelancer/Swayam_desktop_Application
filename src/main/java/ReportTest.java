import MISPack.*;
import salespack.*;
import purchasepack.*;
import accountspack.*;
import custpack.*;
import venpack.*;
import com.selrom.db.DataUtil;
import javax.swing.JInternalFrame;

public class ReportTest {
    public static void main(String[] args) {
        System.out.println("Starting Report Test...");
        try {
            // Mock DataUtil (since we can't really connect to DB, just passing null might work if constructor doesn't use it immediately)
            // Most constructors take DataUtil.
            DataUtil util = null;

            // Instantiate MISPack classes
            new profit_itemwise(util);
            new top_selling_items(util);
            new top_customers_for_item(util);
            new profit_itemwise_barcode(util);
            System.out.println("MISPack instantiated successfully.");

            // Instantiate salespack classes
            new sales_summary_item(util);
            System.out.println("salespack instantiated successfully.");

            // Instantiate purchasepack classes
            new purchase_summary_supplier(util);
            System.out.println("purchasepack instantiated successfully.");

            // Instantiate accountspack classes
            new voucher_report(util);
            new voucher_report_pby(util);
            new voucher_report_group_ledger(util);
            new voucher_report_group(util);
            System.out.println("accountspack instantiated successfully.");

            // Instantiate custpack classes
            new cust_pay_report(util);
            new cust_bill_report(util);
            new cust_bills_bulk_upload(util);
            new cust_balance_report(util);
            new cust_dues_report(util);
            new cust_list(util);
            new cust_dues_overdue(util);
            new cust_dues_report_area(util);
            new cust_list_area(util);
            new cust_list_state(util);
            new cust_bill(util); // Register
            new cust_pay(util); // Entry
            new cust_import_excel(util);
            System.out.println("custpack instantiated successfully.");

            // Instantiate venpack classes
            new ven_bill_report(util);
            new ven_bills_bulk_upload(util);
            new ven_dues_overdue(util);
            new ven_dues_report(util);
            new ven_pay_report(util);
            new vendor_import_excel(util);
            new vendor_list(util);
            new ven_bill(util);
            new ven_pay(util);
            System.out.println("venpack instantiated successfully.");

            System.out.println("All classes instantiated without runtime error.");

        } catch (Throwable e) {
            System.err.println("Failed to instantiate a class:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
