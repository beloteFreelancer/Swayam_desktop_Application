package accountspack;

import com.selrom.db.DataUtil;
import com.selrom.db.disable_warnigs;
import com.selrom.utils.JasperReportCompiler;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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

public class Bankbook_Summary {

    JasperViewer jasperViewer;
    String hmany = "2";
    DataUtil util;

    public void Report(DataUtil util, String dfrom, String dto) {
        this.util = util;
        try {

            Map<String, Object> parameters = new HashMap<>();

            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(dfrom);
            String dfrom1 = (new SimpleDateFormat("yyyy/MM/dd").format(nm));
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(dto);
            String dto1 = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));

            String cname = "";
            String query = "select cname,hmany from setting_bill";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                cname = r.getString(1);
                hmany = r.getString(2);
            }
            parameters.put("parameter1", "" + cname.trim());
            parameters.put("parameter2", "Bank Book Summary");
            parameters.put("parameter3", "(Date from " + dfrom + " to " + dto + ")");

            double sales = 0;
            query = "select sum(card) from sales where dat< '" + dfrom1 + "'";
            r = util.doQuery(query);
            while (r.next()) {
                sales = r.getDouble(1);
            }
            double preturn = 0;
            query = "select sum(net) from preturn where dat< '" + dfrom1 + "' and pby='Bank' ";
            r = util.doQuery(query);
            while (r.next()) {
                preturn = r.getDouble(1);
            }
            double cust_pay = 0;
            query = "select sum(amount) from cust_pay where dat< '" + dfrom1 + "' and pby='Bank' ";
            r = util.doQuery(query);
            while (r.next()) {
                cust_pay = r.getDouble(1);
            }
            double other_income = 0;
            query = "select sum(amount) from account_voucher where dat< '" + dfrom1 + "' and entry='Credit' and pby='Bank' ";
            r = util.doQuery(query);
            while (r.next()) {
                other_income = r.getDouble(1);
            }
            double con_credit = 0;
            query = "select sum(amount) from account_transfer where dat < '" + dfrom1 + "' and category='Cash Ac to Bank Ac'  ";
            r = util.doQuery(query);
            while (r.next()) {
                con_credit = r.getDouble(1);
            }
            double open_credit = sales + preturn + cust_pay + other_income + con_credit;

            double purchase = 0;
            query = "select sum(net) from purchase where dat< '" + dfrom1 + "' and pby='Bank' ";
            r = util.doQuery(query);
            while (r.next()) {
                purchase = r.getDouble(1);
            }
            double sreturn = 0;
            query = "select sum(card) from sreturn where dat< '" + dfrom1 + "'";
            r = util.doQuery(query);
            while (r.next()) {
                sreturn = r.getDouble(1);
            }
            double ven_pay = 0;
            query = "select sum(amount) from ven_pay where dat< '" + dfrom1 + "' and pby='Bank' ";
            r = util.doQuery(query);
            while (r.next()) {
                ven_pay = r.getDouble(1);
            }
            double other_expense = 0;
            query = "select sum(amount) from account_voucher where dat< '" + dfrom1 + "' and entry='Debit' and pby='Bank' ";
            r = util.doQuery(query);
            while (r.next()) {
                other_expense = r.getDouble(1);
            }
            double con_debit = 0;
            query = "select sum(amount) from account_transfer where dat < '" + dfrom1 + "' and category='Bank Ac to Cash Ac'  ";
            r = util.doQuery(query);
            while (r.next()) {
                con_debit = r.getDouble(1);
            }
            double open_debit = purchase + sreturn + ven_pay + other_expense + con_debit;

            String open_credit2 = String.format("%." + hmany + "f", open_credit);
            String open_debit2 = String.format("%." + hmany + "f", open_debit);

            if (open_credit <= 0) {
                open_credit2 = "";
            }
            if (open_debit <= 0) {
                open_debit2 = "";
            }

            parameters.put("parameter4", open_debit2);
            parameters.put("parameter5", open_credit2);

            ArrayList iname = new ArrayList();
            ArrayList debit = new ArrayList();
            ArrayList credit = new ArrayList();

            query = "select sum(card) from sales where dat between '" + dfrom1 + "' and '" + dto1 + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                double amt = r.getDouble(1);
                if (amt > 0) {
                    iname.add("Sales");
                    debit.add(0);
                    credit.add(amt);
                }
            }

            query = "select sum(net) from preturn where dat between '" + dfrom1 + "' and '" + dto1 + "' and pby='Bank' ";
            r = util.doQuery(query);
            while (r.next()) {
                double amt = r.getDouble(1);
                if (amt > 0) {
                    iname.add("Purchase Return");
                    debit.add(0);
                    credit.add(amt);
                }

            }
            query = "select sum(amount) from cust_pay where dat between '" + dfrom1 + "' and '" + dto1 + "' and pby='Bank' ";
            r = util.doQuery(query);
            while (r.next()) {
                double amt = r.getDouble(1);
                if (amt > 0) {
                    iname.add("Customer Payments");
                    debit.add(0);
                    credit.add(amt);
                }
            }

            query = "select sum(net) from purchase where dat between '" + dfrom1 + "' and '" + dto1 + "' and pby='Bank' ";
            r = util.doQuery(query);
            while (r.next()) {
                double amt = r.getDouble(1);
                if (amt > 0) {
                    iname.add("Purchase");
                    debit.add(amt);
                    credit.add(0);
                }
            }

            query = "select sum(card) from sreturn where dat between '" + dfrom1 + "' and '" + dto1 + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                double amt = r.getDouble(1);
                if (amt > 0) {
                    iname.add("Sales Return");
                    debit.add(amt);
                    credit.add(0);
                }
            }

            query = "select sum(amount) from ven_pay where dat between '" + dfrom1 + "' and '" + dto1 + "' and pby='Bank' ";
            r = util.doQuery(query);
            while (r.next()) {
                double amt = r.getDouble(1);
                if (amt > 0) {
                    iname.add("Supplier Payments");
                    debit.add(amt);
                    credit.add(0);
                }
            }

            query = "select t.account,sum(case when t.entry = 'Credit' then t.amount else null end) as Credit, sum(case when t.entry = 'Debit' then t.amount else null end) as Debit from account_voucher t  where dat between '" + dfrom1 + "' and '" + dto1 + "' and pby='Bank' group by t.account";
            r = util.doQuery(query);
            while (r.next()) {
                double camt = r.getDouble(2);
                double damt = r.getDouble(3);
                if (camt > 0 || damt > 0) {
                    iname.add(r.getString(1));
                    credit.add(camt);
                    debit.add(damt);
                }
            }

            query = "select sum(amount) from account_transfer where dat between '" + dfrom1 + "' and '" + dto1 + "' and category='Bank Ac to Cash Ac'  ";
            r = util.doQuery(query);
            while (r.next()) {
                double amt = r.getDouble(1);
                if (amt > 0) {
                    iname.add("Contra:  to Cash A/c");
                    debit.add(r.getString(1));
                    credit.add(0);
                }
            }

            query = "select sum(amount) from account_transfer where dat between '" + dfrom1 + "' and '" + dto1 + "' and category='Cash Ac to Bank Ac'  ";
            r = util.doQuery(query);
            while (r.next()) {
                double amt = r.getDouble(1);
                if (amt > 0) {
                    iname.add("Contra:  from Cash A/c");
                    debit.add(0);
                    credit.add(r.getString(1));
                }
            }

            double tdebit = 0, tcredit = 0;
            ArrayList k = new ArrayList();
            for (int i = 0; i < iname.size(); i++) {
                SelRomJasper selRomJasper = new SelRomJasper();
                String iname1 = iname.get(i).toString();
                double debit1 = Double.parseDouble(debit.get(i).toString());
                double credit1 = Double.parseDouble(credit.get(i).toString());

                tdebit = tdebit + debit1;
                tcredit = tcredit + credit1;

                String debit2 = String.format("%." + hmany + "f", debit1);
                String credit2 = String.format("%." + hmany + "f", credit1);

                if (iname1.equals(".")) {
                    iname1 = "";
                }
                if (debit1 <= 0) {
                    debit2 = "";
                }
                if (credit1 <= 0) {
                    credit2 = "";
                }

                selRomJasper.setField3(iname1);
                selRomJasper.setField4(debit2);
                selRomJasper.setField5(credit2);
                k.add(selRomJasper);
            }//array size ends

            String tdebit2 = String.format("%." + hmany + "f", tdebit);
            String tcredit2 = String.format("%." + hmany + "f", tcredit);

            parameters.put("parameter6", tdebit2);
            parameters.put("parameter7", tcredit2);

            double cdebit = 0, ccredit = 0;
            cdebit = open_debit + tdebit;
            ccredit = open_credit + tcredit;

            String ccredit2 = String.format("%." + hmany + "f", ccredit);
            String cdebit2 = String.format("%." + hmany + "f", cdebit);

            if (cdebit <= 0) {
                cdebit2 = "";
            }
            if (ccredit <= 0) {
                ccredit2 = "";
            }
            if (cdebit > ccredit) {
                double diff = cdebit - ccredit;
                String diff1 = (long) diff + ".00";
                parameters.put("parameter8", diff1);
                parameters.put("parameter9", "");
            } else if (ccredit > cdebit) {
                double diff = ccredit - cdebit;
                String diff1 = (long) diff + ".00";
                parameters.put("parameter8", "");
                parameters.put("parameter9", diff1);
            } else {
                parameters.put("parameter8", cdebit2);
                parameters.put("parameter9", ccredit2);
            }

            disable_warnigs.disableAccessWarnings();
            menu_form me = new menu_form();
            String drive = "";
            String folder = Utils.AppConfig.getAppPath();

            JasperReport jasperReport = JasperReportCompiler.compileReport("/JasperFiles/Accounts/Bank_Book_Summary.jrxml");
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(k);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

            jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
            jasperViewer.setTitle("Bank Book Summary");
            jasperViewer.setSize(400, 555);
            jasperViewer.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
            jasperViewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);

            jasperViewer.removeWindowListener(jasperViewer.getWindowListeners()[0]);
            jasperViewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } catch (ClassNotFoundException | NumberFormatException | SQLException | ParseException | JRException e) {
            System.out.println(e.getMessage());
        }

    }
}
