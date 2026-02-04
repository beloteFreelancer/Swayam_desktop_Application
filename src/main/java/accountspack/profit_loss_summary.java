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

public class profit_loss_summary {

    JasperViewer jasperViewer;
    DataUtil util;

    public void Report(DataUtil util) {
        try {
            this.util = util;
            JasperReport jasperReport;
            JasperPrint jasperPrint;
            ResultSet r;
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
            parameters.put("parameter1", cname);
            parameters.put("parameter2", "Profit & Loss A/c");
            parameters.put("parameter7", "(As on " + g.format(d) + ")");

            ArrayList expense = new ArrayList();
            ArrayList exp_amt = new ArrayList();
            ArrayList income = new ArrayList();
            ArrayList in_amt = new ArrayList();

            double sales = 0, preturn = 0, cust_pay = 0, other_income = 0;
            query = "select sum(cash+card+others) from sales";
            r = util.doQuery(query);
            while (r.next()) {
                sales = r.getDouble(1);
            }
            expense.add(".");
            exp_amt.add(0);
            income.add("Sales");
            in_amt.add(sales);

            query = "select sum(net) from preturn";
            r = util.doQuery(query);
            while (r.next()) {
                preturn = r.getDouble(1);
            }

            expense.add(".");
            exp_amt.add(0);
            income.add("Purchase Return");
            in_amt.add(preturn);

            query = "select sum(amount) from cust_pay";
            r = util.doQuery(query);
            while (r.next()) {
                cust_pay = r.getDouble(1);
            }
            expense.add(".");
            exp_amt.add(0);
            income.add("Customer Payments");
            in_amt.add(cust_pay);

            query = "select sum(amount) from account_voucher where under='Incomes' and entry='Credit'";
            r = util.doQuery(query);
            while (r.next()) {
                other_income = r.getDouble(1);
            }
            expense.add(".");
            exp_amt.add(0);
            income.add("Other Incomes");
            in_amt.add(other_income);

            double cstock = 0;
            query = "select sum(quan*prate) from stock";
            r = util.doQuery(query);
            while (r.next()) {
                cstock = r.getDouble(1);
            }
            expense.add(".");
            exp_amt.add(0);
            income.add("Closing Stock");
            in_amt.add(cstock);

            double total_income = sales + preturn + cust_pay + other_income + cstock;

            double expen = 0, purchase = 0, sales_return = 0, ven_pay = 0;
            query = "select sum(amount) from account_voucher where under='Expenses' and entry='Debit'";
            r = util.doQuery(query);
            while (r.next()) {
                expen = r.getDouble(1);
            }
            expense.add("Expenses");
            exp_amt.add(expen);
            income.add(".");
            in_amt.add(0);

            query = "select sum(net) from purchase";
            r = util.doQuery(query);
            while (r.next()) {
                purchase = r.getDouble(1);
            }
            expense.add("Purchase");
            exp_amt.add(purchase);
            income.add(".");
            in_amt.add(0);

            query = "select sum(cash+card+others) from sreturn";
            r = util.doQuery(query);
            while (r.next()) {
                sales_return = r.getDouble(1);
            }
            expense.add("Sales Return");
            exp_amt.add(sales_return);
            income.add(".");
            in_amt.add(0);

            query = "select sum(amount) from ven_pay";
            r = util.doQuery(query);
            while (r.next()) {
                ven_pay = r.getDouble(1);
            }
            expense.add("Supplier Payments");
            exp_amt.add(ven_pay);
            income.add(".");
            in_amt.add(0);

            double total_expen = purchase + sales_return + ven_pay + expen;

            if (total_income > total_expen) {
                double profit = total_income - total_expen;
                expense.add("***GROSS PROFIT");
                exp_amt.add(profit);
                income.add(".");
                in_amt.add(0);
            } else if (total_expen > total_income) {
                double loss = total_expen - total_income;
                expense.add(".");
                exp_amt.add(0);
                income.add("***GROSS LOSS");
                in_amt.add(loss);
            }

            ArrayList k = new ArrayList();
            double tdebit = 0, tcredit = 0;
            for (int i = 0; i < expense.size(); i++) {
                SelRomJasper selRomJasper = new SelRomJasper();
                String exp1 = expense.get(i).toString();
                double debit1 = Double.parseDouble(exp_amt.get(i).toString());
                String in1 = income.get(i).toString();
                double credit1 = Double.parseDouble(in_amt.get(i).toString());
                tdebit = tdebit + debit1;
                tcredit = tcredit + credit1;

                if (exp1.equals(".")) {
                    exp1 = "";
                }
                if (in1.equals(".")) {
                    in1 = "";
                }

                String debit2 = String.format("%." + hmany + "f", debit1);
                String credit2 = String.format("%." + hmany + "f", credit1);

                if (debit1 <= 0) {
                    debit2 = "";
                }
                if (credit1 <= 0) {
                    credit2 = "";
                }

                selRomJasper.setField1(exp1);
                selRomJasper.setField2(debit2);
                selRomJasper.setField3(in1);
                selRomJasper.setField4(credit2);

                k.add(selRomJasper);
            }//array size
            String tdebit2 = String.format("%." + hmany + "f", tdebit);
            String tcredit2 = String.format("%." + hmany + "f", tcredit);

            parameters.put("parameter3", "Total");
            parameters.put("parameter4", tdebit2);
            parameters.put("parameter5", "Total");
            parameters.put("parameter6", tcredit2);

            disable_warnigs.disableAccessWarnings();
            menupack.menu_form mp = new menu_form();
            String drive = mp.getDrive();
            String folder = mp.getFoler();
            jasperReport = JasperReportCompiler.compileReport("/JasperFiles/Accounts/Profit_Loss.jrxml");
            JRBeanCollectionDataSource beanColDataSource
                    = new JRBeanCollectionDataSource(k);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
            jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
            jasperViewer.setSize(400, 555);
            jasperViewer.setTitle("Profit & Loss A/c");
            jasperViewer.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
            jasperViewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            jasperViewer.removeWindowListener(jasperViewer.getWindowListeners()[0]);
            jasperViewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } catch (ClassNotFoundException | NumberFormatException | SQLException | JRException e) {
            System.out.println(e.getMessage());
        }
    }

}
