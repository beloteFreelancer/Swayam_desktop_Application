package accountspack;

import com.selrom.db.DataUtil;
import com.selrom.db.disable_warnigs;
import com.selrom.utils.JasperReportCompiler;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import menupack.SelRomJasper;
import menupack.menu_form;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class balance_sheet_summary {

    JasperViewer jasperViewer;
    DataUtil util;

    public void Report(DataUtil util) {
        try {
            JasperReport jasperReport;
            JasperPrint jasperPrint;
            ResultSet r;
            this.util = util;

            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd-MM-yyyy");

            Map<String, Object> parameters = new HashMap<>();
            String cname = "";
            int hmany = 2;
            String query = "select cname,hmany from setting_bill";
            r = util.doQuery(query);
            while (r.next()) {
                cname = r.getString(1);
                hmany = r.getInt(2);
            }
            parameters.put("parameter1", cname);
            parameters.put("parameter2", "Balance Sheet");
            parameters.put("parameter7", "(As on " + g.format(d) + ")");

            ArrayList liab = new ArrayList();
            ArrayList liab_amt = new ArrayList();
            ArrayList asset = new ArrayList();
            ArrayList asset_amt = new ArrayList();
            double ca_debit = 0, ca_credit = 0;
            double damt = 0, camt = 0, open_debit = 0, open_credit = 0;
//Capital Accounts Starts
            query = "select sum(case when t.entry = 'Credit' then t.amount else null end) as Credit, sum(case when t.entry = 'Debit' then t.amount else null end) as Debit from account_voucher t where under='Capital Account' ";
            r = util.doQuery(query);
            while (r.next()) {
                camt = r.getDouble(1);
                damt = r.getDouble(2);
                ca_debit = ca_debit + damt;
                ca_credit = ca_credit + camt;
            }
            query = "select sum(debit),sum(credit) from account_master  where under='Capital Account' ";
            r = util.doQuery(query);
            while (r.next()) {
                damt = damt + r.getDouble(1);
                camt = camt + r.getDouble(2);
                open_debit = open_debit + r.getDouble(1);
                open_credit = open_credit + r.getDouble(2);
            }

            if (damt > camt) {
                double bal = damt - camt;
                liab.add(".");
                liab_amt.add(0);
                asset.add("Capital Account");
                asset_amt.add(bal);
            } else if (camt > damt) {
                double bal = camt - damt;
                liab.add("Capital Account");
                liab_amt.add(bal);
                asset.add(".");
                asset_amt.add(0);
            }
//Capital Accounts Ends Here

//Loans Starting
            damt = 0;
            camt = 0;
            query = "select sum(case when t.entry = 'Credit' then t.amount else null end) as Credit, sum(case when t.entry = 'Debit' then t.amount else null end) as Debit from account_voucher t where under='Loans (Liability)' ";
            r = util.doQuery(query);
            while (r.next()) {
                camt = r.getDouble(1);
                damt = r.getDouble(2);
                ca_debit = ca_debit + damt;
                ca_credit = ca_credit + camt;
            }
            query = "select sum(debit),sum(credit) from account_master  where under='Loans (Liability)' ";
            r = util.doQuery(query);
            while (r.next()) {
                damt = damt + r.getDouble(1);
                camt = camt + r.getDouble(2);
                open_debit = open_debit + r.getDouble(1);
                open_credit = open_credit + r.getDouble(2);
            }
            if (camt > damt) {
                double bal = camt - damt;
                liab.add("Loans (Liability)");
                liab_amt.add(bal);
                asset.add(".");
                asset_amt.add(0);
            } else if (camt > damt) {
                double bal = damt - camt;
                liab.add(".");
                liab_amt.add(0);
                asset.add("Loans (Liability)");
                asset_amt.add(bal);
            }
//Loans ends here

//Current Liabilities Starts
            damt = 0;
            camt = 0;
            query = "select sum(case when t.entry = 'Credit' then t.amount else null end) as Credit, sum(case when t.entry = 'Debit' then t.amount else null end) as Debit from account_voucher t where under='Current Liabilities' ";
            r = util.doQuery(query);
            while (r.next()) {
                camt = r.getDouble(1);
                damt = r.getDouble(2);
                ca_debit = ca_debit + damt;
                ca_credit = ca_credit + camt;
            }
            query = "select sum(debit),sum(credit) from account_master  where under='Current Liabilities' ";
            r = util.doQuery(query);
            while (r.next()) {
                damt = damt + r.getDouble(1);
                camt = camt + r.getDouble(2);
                open_debit = open_debit + r.getDouble(1);
                open_credit = open_credit + r.getDouble(2);
            }
            if (damt > camt) {
                double bal = damt - camt;
                liab.add(".");
                liab_amt.add(0);
                asset.add("Current Liabilities");
                asset_amt.add(bal);
            } else if (camt > damt) {
                double bal = camt - damt;
                liab.add("Current Liabilities");
                liab_amt.add(bal);
                asset.add(".");
                asset_amt.add(0);
            }
//Current Liabilities Ends Here

//suspense account
            damt = 0;
            camt = 0;
            query = "select sum(case when t.entry = 'Credit' then t.amount else null end) as Credit, sum(case when t.entry = 'Debit' then t.amount else null end) as Debit from account_voucher t where under='Suspense Account' ";
            r = util.doQuery(query);
            while (r.next()) {
                camt = r.getDouble(1);
                damt = r.getDouble(2);
                ca_debit = ca_debit + damt;
                ca_credit = ca_credit + camt;
            }
            query = "select sum(debit),sum(credit) from account_master  where under='Suspense Account' ";
            r = util.doQuery(query);
            while (r.next()) {
                damt = damt + r.getDouble(1);
                camt = camt + r.getDouble(2);
                open_debit = open_debit + r.getDouble(1);
                open_credit = open_credit + r.getDouble(2);
            }

            if (camt > 0 || damt > 0) {
                if (damt > camt) {
                    double bal = damt - camt;
                    liab.add(".");
                    liab_amt.add(0);
                    asset.add("Suspense Account");
                    asset_amt.add(bal);
                } else {
                    double bal = camt - damt;
                    liab.add("Suspense Account");
                    liab_amt.add(bal);
                    asset.add(".");
                    asset_amt.add(0);
                }
            }//balances is grearthen zero
//suspense account ends

//fixed assets starts
            damt = 0;
            camt = 0;
            query = "select sum(case when t.entry = 'Credit' then t.amount else null end) as Credit, sum(case when t.entry = 'Debit' then t.amount else null end) as Debit from account_voucher t where under='Fixed Assets' ";
            r = util.doQuery(query);
            while (r.next()) {
                camt = r.getDouble(1);
                damt = r.getDouble(2);
                ca_debit = ca_debit + damt;
                ca_credit = ca_credit + camt;
            }
            query = "select sum(debit),sum(credit) from account_master  where under='Fixed Assets' ";
            r = util.doQuery(query);
            while (r.next()) {
                damt = damt + r.getDouble(1);
                camt = camt + r.getDouble(2);
                open_debit = open_debit + r.getDouble(1);
                open_credit = open_credit + r.getDouble(2);
            }

            if (camt > 0 || damt > 0) {
                if (camt > damt) {
                    double bal = camt - damt;
                    liab.add("Fixed Assets");
                    liab_amt.add(bal);
                    asset.add(".");
                    asset_amt.add(0);
                } else {
                    double bal = damt - camt;
                    liab.add(".");
                    liab_amt.add(0);
                    asset.add("Fixed Assets");
                    asset_amt.add(bal);
                }
            }//balances is grearthen zero
//fixed assets ends here

            double cstock1 = 0;
            query = "select sum(quan*prate) from stock";
            r = util.doQuery(query);
            while (r.next()) {
                cstock1 = r.getDouble(1);
            }

            if (cstock1 > 0) {
                liab.add(".");
                liab_amt.add(0);
                asset.add("Closing Stock");
                asset_amt.add(cstock1);
            }

            //Profit & Loss Account
            double sales = 0, preturn = 0, cust_pay = 0, other_income = 0;
            query = "select sum(cash+card+others) from sales";
            r = util.doQuery(query);
            while (r.next()) {
                sales = r.getDouble(1);
            }
            query = "select sum(net) from preturn";
            r = util.doQuery(query);
            while (r.next()) {
                preturn = r.getDouble(1);
            }
            query = "select sum(amount) from cust_pay";
            r = util.doQuery(query);
            while (r.next()) {
                cust_pay = r.getDouble(1);
            }
            query = "select sum(amount) from account_voucher where under='Incomes' and entry='Credit'";
            r = util.doQuery(query);
            while (r.next()) {
                other_income = r.getDouble(1);
            }
            double cstock = 0;
            query = "select sum(quan*prate) from stock";
            r = util.doQuery(query);
            while (r.next()) {
                cstock = r.getDouble(1);
            }
            double income = sales + preturn + cust_pay + other_income + cstock;

            double expen = 0, purchase = 0, sales_return = 0, ven_pay = 0;
            query = "select sum(amount) from account_voucher where under='Expenses' and entry='Debit'";
            r = util.doQuery(query);
            while (r.next()) {
                expen = r.getDouble(1);
            }
            query = "select sum(net) from purchase";
            r = util.doQuery(query);
            while (r.next()) {
                purchase = r.getDouble(1);
            }
            query = "select sum(cash+card+others) from sreturn";
            r = util.doQuery(query);
            while (r.next()) {
                sales_return = r.getDouble(1);
            }
            query = "select sum(amount) from ven_pay";
            r = util.doQuery(query);
            while (r.next()) {
                ven_pay = r.getDouble(1);
            }
            double expense = purchase + sales_return + ven_pay + expen;

            if (expense > income) {
                double loss = expense - income;
                liab.add(".");
                liab_amt.add(0);
                asset.add("Profit & Loss A/c");
                asset_amt.add(loss);
            } else {
                double profit = income - expense;
                liab.add("Profit & Loss A/c");
                liab_amt.add(profit);
                asset.add(".");
                asset_amt.add(0);
            }
            //profit & loss account ends
            ca_debit = ca_debit + expense;
            ca_credit = ca_credit + income - cstock;

            //Bank Accounts Starts
            query = "select sum(case when t.entry = 'Credit' then t.amount else null end) as Credit, sum(case when t.entry = 'Debit' then t.amount else null end) as Debit from account_voucher t where under='Bank Accounts' ";
            r = util.doQuery(query);
            while (r.next()) {
                ca_credit = ca_credit + r.getDouble(2);
                ca_debit = ca_debit + r.getDouble(1);
            }
            query = "select sum(debit),sum(credit) from account_master  where under='Bank Accounts' ";
            r = util.doQuery(query);
            while (r.next()) {
                open_debit = open_debit + r.getDouble(1);
                open_credit = open_credit + r.getDouble(2);

                ca_debit = ca_debit + r.getDouble(2);
                ca_credit = ca_credit + r.getDouble(1);
            }
            //Bank Accounts Ends Here

            //Current Assets Opening Balance
            query = "select sum(debit),sum(credit) from account_master  where under='Current Assets' ";
            r = util.doQuery(query);
            while (r.next()) {
                ca_debit = ca_debit + r.getDouble(2);
                ca_credit = ca_credit + r.getDouble(1);

                open_debit = open_debit + r.getDouble(1);
                open_credit = open_credit + r.getDouble(2);
            }
            //Current Assets Opening Balance Ends

            if (ca_credit > ca_debit) {
                double diff = ca_credit - ca_debit;
                liab.add(".");
                liab_amt.add(0);
                asset.add("Current Assets");
                asset_amt.add(diff);
            } else if (ca_debit > ca_credit) {
                double diff = ca_debit - ca_credit;
                liab.add("Current Assets");
                liab_amt.add(diff);
                asset.add(".");
                asset_amt.add(0);
            }

            //Opening Balance
            if (open_credit > 0 || open_debit > 0) {
                if (open_credit > open_debit) {
                    double diff = open_credit - open_debit;
                    liab.add(".");
                    liab_amt.add(0);
                    asset.add("*** Difference in Opening Balances");
                    asset_amt.add(diff);
                } else if (open_debit > open_credit) {
                    double diff = open_debit - open_credit;
                    liab.add("*** Difference in Opening Balances");
                    liab_amt.add(diff);
                    asset.add(".");
                    asset_amt.add(0);
                }
            }
            //Opening Balance Ends Here

            double tlamt = 0, taamt = 0;
            ArrayList k = new ArrayList();
            for (int i = 0; i < liab.size(); i++) {
                SelRomJasper selRomJasper = new SelRomJasper();

                String liab1 = liab.get(i).toString();
                String asset1 = asset.get(i).toString();

                double lamt = Double.parseDouble(liab_amt.get(i).toString());
                double aamt = Double.parseDouble(asset_amt.get(i).toString());
                String lamt2 = String.format("%." + hmany + "f", lamt);
                String aamt2 = String.format("%." + hmany + "f", aamt);

                tlamt = tlamt + lamt;
                taamt = taamt + aamt;

                if (lamt <= 0) {
                    lamt2 = "";
                }
                if (aamt <= 0) {
                    aamt2 = "";
                }
                if (liab1.equals(".")) {
                    liab1 = "";
                }
                if (asset1.equals(".")) {
                    asset1 = "";
                }

                selRomJasper.setField1(liab1);
                selRomJasper.setField2(lamt2);
                selRomJasper.setField3(asset1);
                selRomJasper.setField4(aamt2);
                k.add(selRomJasper);
            }//array size

            String tlamt2 = String.format("%." + hmany + "f", tlamt);
            String taamt2 = String.format("%." + hmany + "f", taamt);

            parameters.put("parameter3", "Total");
            parameters.put("parameter5", "Total");
            parameters.put("parameter4", tlamt2);
            parameters.put("parameter6", taamt2);

            disable_warnigs.disableAccessWarnings();
            jasperReport = JasperReportCompiler.compileReport("/JasperFiles/Accounts/Balance_Sheet.jrxml");
            JRBeanCollectionDataSource beanColDataSource
                    = new JRBeanCollectionDataSource(k);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
            jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
            jasperViewer.setSize(400, 555);
            jasperViewer.setTitle("Balance Sheet");
            jasperViewer.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
            jasperViewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            jasperViewer.removeWindowListener(jasperViewer.getWindowListeners()[0]);
            jasperViewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } catch (ClassNotFoundException | NumberFormatException | SQLException | JRException e) {
            System.out.println(e.getMessage());
        }
    }

}
