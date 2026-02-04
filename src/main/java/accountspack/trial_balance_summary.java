package accountspack;

import com.selrom.db.DataUtil;
import com.selrom.db.disable_warnigs;
import com.selrom.utils.JasperReportCompiler;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
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
import net.sf.jasperreports.view.JasperViewer;

public class trial_balance_summary {

    JasperViewer jasperViewer;
    DataUtil util;

    public void Report(DataUtil util) {
        try {
            JasperReport jasperReport;
            JasperPrint jasperPrint;
            ResultSet r;
            this.util = util;

            Map<String, Object> parameters = new HashMap<>();
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd-MM-yyyy");
            String cname = "";
            int hmany = 2;
            String query = "select cname,hmany from setting_bill";
            r = util.doQuery(query);
            while (r.next()) {
                cname = r.getString(1);
                hmany = r.getInt(2);
            }

            parameters.put("parameter1", "" + cname);
            parameters.put("parameter2", "Trial Balance");
            parameters.put("parameter7", "(As on " + g.format(d) + ")");

            ArrayList part = new ArrayList();
            ArrayList credit = new ArrayList();
            ArrayList debit = new ArrayList();
            double ca_debit = 0, ca_credit = 0;
            double open_debit = 0, open_credit = 0;
            double camt = 0, damt = 0;
            query = "select sum(case when t.entry = 'Credit' then t.amount else null end) as Credit, sum(case when t.entry = 'Debit' then t.amount else null end) as Debit from account_voucher t  where under='Capital Account' ";
            r = util.doQuery(query);
            while (r.next()) {
                camt = r.getDouble(1);
                damt = r.getDouble(2);
                ca_debit = ca_debit + damt;
                ca_credit = ca_credit + camt;
            }
            query = "select sum(debit),sum(credit) from account_master where under='Capital Account' ";
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
                    part.add("Capital Account");
                    credit.add(0);
                    debit.add(bal);
                } else {
                    double bal = camt - damt;
                    part.add("Capital Account");
                    credit.add(bal);
                    debit.add(0);
                }
            }//balances is grearthen zero

            //Fixed Assets
            camt = 0;
            damt = 0;
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
                if (damt > camt) {
                    double bal = damt - camt;
                    part.add("Fixed Assets");
                    credit.add(0);
                    debit.add(bal);
                } else {
                    double bal = camt - damt;
                    part.add("Fixed Assets");
                    credit.add(bal);
                    debit.add(0);
                }
            }//balances is grearthen zero
            //Fixed Assets Ending

            //Loans Starts
            camt = 0;
            damt = 0;
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

            if (camt > 0 || damt > 0) {
                if (camt > damt) {
                    double bal = camt - damt;
                    part.add("Loans (Liability)");
                    credit.add(bal);
                    debit.add(0);
                } else {
                    double bal = damt - camt;
                    part.add("Loans (Liability)");
                    credit.add(0);
                    debit.add(bal);
                }
            }//balances greater than zero
            //Loans Ends Here

            //Current Liabilities
            camt = 0;
            damt = 0;
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
            if (camt > 0 || damt > 0) {
                if (camt > damt) {
                    double bal = camt - damt;
                    part.add("Current Liabilities");
                    credit.add(bal);
                    debit.add(0);
                } else {
                    double bal = damt - camt;
                    part.add("Current Liabilities");
                    credit.add(0);
                    debit.add(bal);
                }
            }//balances is greaterthan zero
            //Current Liabilities Ends Here

            //Suspense Account Starts
            camt = 0;
            damt = 0;
            query = "select sum(case when t.entry = 'Credit' then t.amount else null end) as Credit, sum(case when t.entry = 'Debit' then t.amount else null end) as Debit from account_voucher t where under='Suspense Account' ";
            r = util.doQuery(query);
            while (r.next()) {
                camt = r.getDouble(1);
                damt = r.getDouble(2);
                ca_debit = ca_debit + damt;
                ca_credit = ca_credit + camt;
            }
            query = "select sum(debit),sum(credit) from account_master where under='Suspense Account' ";
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
                    part.add("Suspense Account");
                    credit.add(0);
                    debit.add(bal);
                } else {
                    double bal = camt - damt;
                    part.add("Suspense Account");
                    credit.add(bal);
                    debit.add(0);
                }
            }//balances is grearthen zero
            //Suspense Account Ends Here

            //Incomes Starts Here
            double sales = 0, preturn = 0, cust_pay = 0, other_income = 0;
            query = "select sum(cash+card+others) from sales";
            r = util.doQuery(query);
            while (r.next()) {
                sales = r.getDouble(1);
            }

            if (sales > 0) {
                part.add("Sales");
                credit.add(sales);
                debit.add(0);
            }

            query = "select sum(net) from preturn";
            r = util.doQuery(query);
            while (r.next()) {
                preturn = r.getDouble(1);
            }
            if (preturn > 0) {
                part.add("Purchase Return");
                credit.add(preturn);
                debit.add(0);
            }

            query = "select sum(amount) from cust_pay";
            r = util.doQuery(query);
            while (r.next()) {
                cust_pay = r.getDouble(1);
            }
            if (cust_pay > 0) {
                part.add("Customer Payments");
                credit.add(cust_pay);
                debit.add(0);
            }

            double other_dt = 0;
            camt = 0;
            damt = 0;
            query = "select sum(case when t.entry = 'Credit' then t.amount else null end) as Credit, sum(case when t.entry = 'Debit' then t.amount else null end) as Debit from account_voucher t where under='Incomes' ";
            r = util.doQuery(query);
            while (r.next()) {
                other_income = r.getDouble(1);
                other_dt = r.getDouble(2);
            }

            query = "select sum(debit),sum(credit) from account_master where under='Incomes' ";
            r = util.doQuery(query);
            while (r.next()) {
                damt = r.getDouble(1);
                camt = r.getDouble(2);
                open_debit = open_debit + r.getDouble(1);
                open_credit = open_credit + r.getDouble(2);
            }
            other_income = other_income + camt;
            other_dt = other_dt + damt;

            part.add("Incomes");
            credit.add(other_income);
            debit.add(other_dt);
            //Incomes Ends Here

            double purchase = 0;
            query = "select sum(net) from purchase";
            r = util.doQuery(query);
            while (r.next()) {
                purchase = r.getDouble(1);
            }
            part.add("Purchase");
            credit.add(0);
            debit.add(purchase);

            double sales_return = 0, ven_pay = 0;
            query = "select sum(cash+card+others) from sreturn";
            r = util.doQuery(query);
            while (r.next()) {
                sales_return = r.getDouble(1);
            }
            part.add("Sales Return");
            credit.add(0);
            debit.add(sales_return);

            query = "select sum(amount) from ven_pay";
            r = util.doQuery(query);
            while (r.next()) {
                ven_pay = r.getDouble(1);
            }
            part.add("Supplier Payments");
            credit.add(0);
            debit.add(ven_pay);

            double expen = 0, other_ct = 0;
            camt = 0;
            damt = 0;
            query = "select sum(case when t.entry = 'Credit' then t.amount else null end) as Credit, sum(case when t.entry = 'Debit' then t.amount else null end) as Debit from account_voucher t where under='Expenses' ";
            r = util.doQuery(query);
            while (r.next()) {
                other_ct = r.getDouble(1);
                expen = r.getDouble(2);
            }
            query = "select sum(debit),sum(credit) from account_master  where under='Expenses' ";
            r = util.doQuery(query);
            while (r.next()) {
                damt = r.getDouble(1);
                camt = r.getDouble(2);
                open_debit = open_debit + r.getDouble(1);
                open_credit = open_credit + r.getDouble(2);
            }
            expen = expen + damt;
            other_ct = other_ct + camt;

            part.add("Expenses");
            credit.add(other_ct);
            debit.add(expen);

            double total_income = sales + preturn + cust_pay + other_income;
            double total_expen = purchase + sales_return + ven_pay + expen;

            ca_debit = ca_debit + total_expen;
            ca_credit = ca_credit + total_income;

            //Bank Accounts
            query = "select sum(case when t.entry = 'Credit' then t.amount else null end) as Credit, sum(case when t.entry = 'Debit' then t.amount else null end) as Debit from account_voucher t where under='Bank Accounts' ";
            r = util.doQuery(query);
            while (r.next()) {
                ca_credit = ca_credit + r.getDouble(1);
                ca_debit = ca_debit + r.getDouble(2);
            }
            query = "select sum(debit),sum(credit) from account_master where under='Bank Accounts' ";
            r = util.doQuery(query);
            while (r.next()) {
                open_debit = open_debit + r.getDouble(1);
                open_credit = open_credit + r.getDouble(2);

                ca_credit = ca_credit + r.getDouble(1);
                ca_debit = ca_debit + r.getDouble(2);
            }
            //Bank Accounts

            //current assets starts
            if (ca_credit > ca_debit) {
                double diff = ca_credit - ca_debit;
                part.add("Current Assets");
                credit.add(0);
                debit.add(diff);
            } else if (ca_debit > ca_credit) {
                double diff = ca_debit - ca_credit;
                part.add("Current Assets");
                credit.add(diff);
                debit.add(0);
            }
            //current ends here

            //Opening Balance
            if (open_credit > 0 || open_debit > 0) {
                if (open_credit > open_debit) {
                    double diff = open_credit - open_debit;
                    part.add("*** Difference in Opening Balances");
                    credit.add(0);
                    debit.add(diff);
                } else if (open_debit > open_credit) {
                    double diff = open_debit - open_credit;
                    part.add("*** Difference in Opening Balances");
                    credit.add(diff);
                    debit.add(0);
                }
            }
            //Opening Balance Ends Here

            double tdebit = 0, tcredit = 0;
            ArrayList k = new ArrayList();
            for (int i = 0; i < part.size(); i++) {
                SelRomJasper selRomJasper = new SelRomJasper();
                String part1 = part.get(i).toString();

                double debit1 = Double.parseDouble(debit.get(i).toString());
                double credit1 = Double.parseDouble(credit.get(i).toString());
                tdebit = tdebit + debit1;
                tcredit = tcredit + credit1;

                String debit2 = String.format("%." + hmany + "f", debit1);
                String credit2 = String.format("%." + hmany + "f", credit1);

                if (debit1 <= 0) {
                    debit2 = "";
                }
                if (credit1 <= 0) {
                    credit2 = "";
                }
                if (part1.equals(".")) {
                    part1 = "";
                }

                selRomJasper.setField1(part1);
                selRomJasper.setField2(debit2);
                selRomJasper.setField3(credit2);
                k.add(selRomJasper);
            }//array size

            String tdebit2 = String.format("%." + hmany + "f", tdebit);
            String tcredit2 = String.format("%." + hmany + "f", tcredit);

            parameters.put("parameter3", "Grand Total");
            parameters.put("parameter4", tdebit2);
            parameters.put("parameter5", tcredit2);

            disable_warnigs.disableAccessWarnings();
            menupack.menu_form mp = new menu_form();
            String drive = mp.getDrive();
            String folder = mp.getFoler();
            jasperReport = JasperReportCompiler.compileReport("/JasperFiles/Accounts/Trial_Balance.jrxml");
            JRBeanCollectionDataSource beanColDataSource
                    = new JRBeanCollectionDataSource(k);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
            jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
            jasperViewer.setSize(400, 555);
            jasperViewer.setTitle("Trial Balance");
            jasperViewer.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
            jasperViewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            jasperViewer.removeWindowListener(jasperViewer.getWindowListeners()[0]);
            jasperViewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } catch (ClassNotFoundException | NumberFormatException | SQLException | JRException e) {
            System.out.println(e.getMessage());
        }
    }

}
