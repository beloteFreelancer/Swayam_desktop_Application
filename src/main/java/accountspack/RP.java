package accountspack;

import com.selrom.db.DataUtil;
import com.selrom.db.disable_warnigs;
import com.selrom.utils.JasperReportCompiler;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class RP {

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
            parameters.put("parameter2", "RECEIPTS & PAYMENTS");
            parameters.put("parameter3", "(Date from " + dfrom + " to " + dto + ")");

            double sales_cash = 0, sales_card = 0, sales_other = 0;
            query = "select sum(cash),sum(card),sum(others) from sales where dat< '" + dfrom1 + "'";
            r = util.doQuery(query);
            while (r.next()) {
                sales_cash = r.getDouble(1);
                sales_card = r.getDouble(2);
                sales_other = r.getDouble(3);
            }
            double credit_cash = 0, credit_bank = 0, credit_others = 0;
            query = "select Sum(CASE WHEN t.pby = 'Cash' THEN t.amount ELSE NULL END) AS Cash, Sum(CASE WHEN t.pby = 'Bank' THEN t.amount ELSE NULL END) AS Bank, Sum(CASE WHEN t.pby = 'Others' THEN t.amount ELSE NULL END) AS Others from account_voucher t where t.dat < '" + dfrom1 + "' and t.entry='Credit' ";
            r = util.doQuery(query);
            while (r.next()) {
                credit_cash = r.getDouble(1);
                credit_bank = r.getDouble(2);
                credit_others = r.getDouble(3);
            }

            double cpay_cash = 0, cpay_bank = 0, cpay_other = 0;
            query = "select Sum(CASE WHEN t.pby = 'Cash' THEN t.amount ELSE NULL END) AS Cash, Sum(CASE WHEN t.pby = 'Bank' THEN t.amount ELSE NULL END) AS Bank, Sum(CASE WHEN t.pby = 'Others' THEN t.amount ELSE NULL END) AS Others from cust_pay t where t.dat < '" + dfrom1 + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                cpay_cash = r.getDouble(1);
                cpay_bank = r.getDouble(2);
                cpay_other = r.getDouble(3);
            }

            double pret_cash = 0, pret_bank = 0, pret_other = 0;
            query = "select Sum(CASE WHEN t.pby = 'Cash' THEN t.net ELSE NULL END) AS Cash, Sum(CASE WHEN t.pby = 'Bank' THEN t.net ELSE NULL END) AS Bank, Sum(CASE WHEN t.pby = 'Others' THEN t.net ELSE NULL END) AS Others from preturn t where t.dat < '" + dfrom1 + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                pret_cash = r.getDouble(1);
                pret_bank = r.getDouble(2);
                pret_other = r.getDouble(3);
            }
            double cash_income = sales_cash + cpay_cash + credit_cash + pret_cash;
            double bank_income = sales_card + sales_other + cpay_bank + cpay_other + credit_bank + credit_others + pret_bank + pret_other;

            double pur_cash = 0, pur_bank = 0, pur_other = 0;
            query = "select Sum(CASE WHEN t.pby = 'Cash' THEN t.net ELSE NULL END) AS Cash, Sum(CASE WHEN t.pby = 'Bank' THEN t.net ELSE NULL END) AS Bank, Sum(CASE WHEN t.pby = 'Others' THEN t.net ELSE NULL END) AS Others from purchase t where t.dat < '" + dfrom1 + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                pur_cash = r.getDouble(1);
                pur_bank = r.getDouble(2);
                pur_other = r.getDouble(3);
            }
            double debit_cash = 0, debit_bank = 0, debit_others = 0;
            query = "select Sum(CASE WHEN t.pby = 'Cash' THEN t.amount ELSE NULL END) AS Cash, Sum(CASE WHEN t.pby = 'Bank' THEN t.amount ELSE NULL END) AS Bank, Sum(CASE WHEN t.pby = 'Others' THEN t.amount ELSE NULL END) AS Others from account_voucher t where t.dat < '" + dfrom1 + "' and t.entry='Debit' ";
            r = util.doQuery(query);
            while (r.next()) {
                debit_cash = r.getDouble(1);
                debit_bank = r.getDouble(2);
                debit_others = r.getDouble(3);
            }
            double spay_cash = 0, spay_bank = 0, spay_other = 0;
            query = "select Sum(CASE WHEN t.pby = 'Cash' THEN t.amount ELSE NULL END) AS Cash, Sum(CASE WHEN t.pby = 'Bank' THEN t.amount ELSE NULL END) AS Bank, Sum(CASE WHEN t.pby = 'Others' THEN t.amount ELSE NULL END) AS Others from ven_pay t where t.dat < '" + dfrom1 + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                spay_cash = r.getDouble(1);
                spay_bank = r.getDouble(2);
                spay_other = r.getDouble(3);
            }
            double sret_cash = 0, sret_card = 0, sret_other = 0;
            query = "select sum(cash),sum(card),sum(others) from sreturn where dat< '" + dfrom1 + "'";
            r = util.doQuery(query);
            while (r.next()) {
                sret_cash = r.getDouble(1);
                sret_card = r.getDouble(2);
                sret_other = r.getDouble(3);
            }
            double stock_cash = 0, stock_bank = 0, stock_other = 0;
            query = "select Sum(CASE WHEN t.pby = 'Cash' THEN t.net ELSE NULL END) AS Cash, Sum(CASE WHEN t.pby = 'Bank' THEN t.net ELSE NULL END) AS Bank, Sum(CASE WHEN t.pby = 'Others' THEN t.net ELSE NULL END) AS Others from stock_entry t where t.dat < '" + dfrom1 + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                stock_cash = r.getDouble(1);
                stock_bank = r.getDouble(2);
                stock_other = r.getDouble(3);
            }

            double cash_expense = pur_cash + spay_cash + debit_cash + sret_cash + stock_cash;
            double bank_expense = pur_bank + pur_other + spay_bank + spay_other + debit_bank + debit_others + sret_card + sret_other + stock_bank + stock_other;

            double cash_open = cash_income - cash_expense;
            double bank_open = bank_income - bank_expense;

            ArrayList rpart = new ArrayList();
            ArrayList rcash = new ArrayList();
            ArrayList rbank = new ArrayList();
            ArrayList ppart = new ArrayList();
            ArrayList pcash = new ArrayList();
            ArrayList pbank = new ArrayList();

            String cash_open2 = String.format("%." + hmany + "f", cash_open);
            String bank_open2 = String.format("%." + hmany + "f", bank_open);

            rpart.add("Opening Balance");
            rcash.add(cash_open2);
            rbank.add(bank_open2);
            ppart.add(".");
            pcash.add("" + 0);
            pbank.add("" + 0);

            //receipt 1
            query = "select sum(cash),sum(card),sum(others) from sales where dat between '" + dfrom1 + "' and '" + dto1 + "'";
            r = util.doQuery(query);
            while (r.next()) {
                double cash, card, others;
                cash = r.getDouble(1);
                card = r.getDouble(2);
                others = r.getDouble(3);
                card = card + others;

                double tot_sales = cash + card;
                if (tot_sales > 0) {
                    rpart.add("Sales");
                    rcash.add(cash);
                    rbank.add(card);
                    ppart.add(".");
                    pcash.add("" + 0);
                    pbank.add("" + 0);
                }

            }
            //receipt 1 ends

            //receipt 2
            query = "select Sum(CASE WHEN t.pby = 'Cash' THEN t.amount ELSE NULL END) AS Cash, Sum(CASE WHEN t.pby = 'Bank' THEN t.amount ELSE NULL END) AS Bank, Sum(CASE WHEN t.pby = 'Others' THEN t.amount ELSE NULL END) AS Others from cust_pay t where t.dat between '" + dfrom1 + "' and '" + dto1 + "'";
            r = util.doQuery(query);
            while (r.next()) {
                double cash, bank, others;
                cash = r.getDouble(1);
                bank = r.getDouble(2);
                others = r.getDouble(3);
                bank = bank + others;

                double tot_pay = cash + bank;
                if (tot_pay > 0) {
                    rpart.add("Customer Payments");
                    rcash.add(cash);
                    rbank.add(bank);
                    ppart.add(".");
                    pcash.add("" + 0);
                    pbank.add("" + 0);
                }
            }
            //receipt 2 ends

            //receipt 3
            query = "select t.account,Sum(CASE WHEN t.pby = 'Cash' THEN t.amount ELSE NULL END) AS Cash, Sum(CASE WHEN t.pby = 'Bank' THEN t.amount ELSE NULL END) AS Bank, Sum(CASE WHEN t.pby = 'Others' THEN t.amount ELSE NULL END) AS Others from account_voucher t where t.dat between '" + dfrom1 + "' and '" + dto1 + "' and t.entry='Credit' group by t.account";
            r = util.doQuery(query);
            while (r.next()) {
                double cash, bank, others;
                cash = r.getDouble(2);
                bank = r.getDouble(3);
                others = r.getDouble(4);
                bank = bank + others;

                rpart.add(r.getString(1));
                rcash.add(cash);
                rbank.add(bank);
                ppart.add(".");
                pcash.add("" + 0);
                pbank.add("" + 0);
            }
            //receipt 3 ends

            //receipt 4
            query = "select Sum(CASE WHEN t.pby = 'Cash' THEN t.net ELSE NULL END) AS Cash, Sum(CASE WHEN t.pby = 'Bank' THEN t.net ELSE NULL END) AS Bank, Sum(CASE WHEN t.pby = 'Others' THEN t.net ELSE NULL END) AS Others from preturn t where t.dat between '" + dfrom1 + "' and '" + dto1 + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                double cash, bank, others;
                cash = r.getDouble(1);
                bank = r.getDouble(2);
                others = r.getDouble(3);
                bank = bank + others;

                double tot_ret = cash + bank;
                if (tot_ret > 0) {
                    rpart.add("Purchase Return");
                    rcash.add(cash);
                    rbank.add(bank);
                    ppart.add(".");
                    pcash.add("" + 0);
                    pbank.add("" + 0);
                }
            }

            //receipt 4 ends
            //pay 1
            query = "select Sum(CASE WHEN t.pby = 'Cash' THEN t.net ELSE NULL END) AS Cash, Sum(CASE WHEN t.pby = 'Bank' THEN t.net ELSE NULL END) AS Bank, Sum(CASE WHEN t.pby = 'Others' THEN t.net ELSE NULL END) AS Others from purchase t where t.dat between '" + dfrom1 + "' and '" + dto1 + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                double cash, bank, others;
                cash = r.getDouble(1);
                bank = r.getDouble(2);
                others = r.getDouble(3);
                bank = bank + others;

                double tot_pur = cash + bank;
                if (tot_pur > 0) {
                    rpart.add(".");
                    rcash.add("" + 0);
                    rbank.add("" + 0);
                    ppart.add("Purchase");
                    pcash.add("" + cash);
                    pbank.add("" + bank);
                }
            }

            //pay1 ends
            //pay 2
            query = "select Sum(CASE WHEN t.pby = 'Cash' THEN t.amount ELSE NULL END) AS Cash, Sum(CASE WHEN t.pby = 'Bank' THEN t.amount ELSE NULL END) AS Bank, Sum(CASE WHEN t.pby = 'Others' THEN t.amount ELSE NULL END) AS Others from ven_pay t where t.dat between '" + dfrom1 + "' and '" + dto1 + "'";
            r = util.doQuery(query);
            while (r.next()) {
                double cash, bank, others;
                cash = r.getDouble(1);
                bank = r.getDouble(2);
                others = r.getDouble(3);
                bank = bank + others;

                double tot_pay = cash + bank;
                if (tot_pay > 0) {
                    rpart.add(".");
                    rcash.add("" + 0);
                    rbank.add("" + 0);
                    ppart.add("Supplier Payments");
                    pcash.add("" + cash);
                    pbank.add("" + bank);
                }
            }
            //pay2 ends

            //pay 3
            query = "select t.account,Sum(CASE WHEN t.pby = 'Cash' THEN t.amount ELSE NULL END) AS Cash, Sum(CASE WHEN t.pby = 'Bank' THEN t.amount ELSE NULL END) AS Bank, Sum(CASE WHEN t.pby = 'Others' THEN t.amount ELSE NULL END) AS Others from account_voucher t where t.dat between '" + dfrom1 + "' and '" + dto1 + "' and t.entry='Debit' group by t.account";
            r = util.doQuery(query);
            while (r.next()) {
                double cash, bank, others;
                cash = r.getDouble(2);
                bank = r.getDouble(3);
                others = r.getDouble(4);
                bank = bank + others;

                rpart.add(".");
                rcash.add("" + 0);
                rbank.add("" + 0);
                ppart.add("" + r.getString(1));
                pcash.add("" + cash);
                pbank.add("" + bank);
            }
            //pay3 ends

            //pay 4
            query = "select sum(cash),sum(card),sum(others) from sreturn where dat between '" + dfrom1 + "' and '" + dto1 + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                double cash, bank, others;
                cash = r.getDouble(1);
                bank = r.getDouble(2);
                others = r.getDouble(3);
                bank = bank + others;

                double tot_sret = cash + bank;
                if (tot_sret > 0) {
                    rpart.add(".");
                    rcash.add("" + 0);
                    rbank.add("" + 0);
                    ppart.add("Sales Return");
                    pcash.add("" + cash);
                    pbank.add("" + bank);
                }

            }
            //pay4 ends

            //pay 5
            query = "select Sum(CASE WHEN t.pby = 'Cash' THEN t.net ELSE NULL END) AS Cash, Sum(CASE WHEN t.pby = 'Bank' THEN t.net ELSE NULL END) AS Bank, Sum(CASE WHEN t.pby = 'Others' THEN t.net ELSE NULL END) AS Others from stock_entry t where t.dat between '" + dfrom1 + "' and '" + dto1 + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                double cash, bank, others;
                cash = r.getDouble(1);
                bank = r.getDouble(2);
                others = r.getDouble(3);
                bank = bank + others;

                double tot_stock = cash + bank;
                if (tot_stock > 0) {
                    rpart.add(".");
                    rcash.add("" + 0);
                    rbank.add("" + 0);
                    ppart.add("Stock Purchase");
                    pcash.add("" + cash);
                    pbank.add("" + bank);
                }

            }

            //pay5 ends
            ArrayList k = new ArrayList();
            double rcash2 = 0, rbank2 = 0, pcash2 = 0, pbank2 = 0;
            for (int i = 0; i < rpart.size(); i++) {
                SelRomJasper selRomJasper = new SelRomJasper();

                String rpart1 = rpart.get(i).toString();
                double rcash1 = Double.parseDouble(rcash.get(i).toString());
                double rbank1 = Double.parseDouble(rbank.get(i).toString());

                String ppart1 = ppart.get(i).toString();
                double pcash1 = Double.parseDouble(pcash.get(i).toString());
                double pbank1 = Double.parseDouble(pbank.get(i).toString());

                rcash2 = rcash2 + rcash1;
                rbank2 = rbank2 + rbank1;

                pcash2 = pcash2 + pcash1;
                pbank2 = pbank2 + pbank1;

                if (rpart1.equals(".")) {
                    rpart1 = "";
                }
                if (ppart1.equals(".")) {
                    ppart1 = "";
                }

                String receipt_cash = String.format("%." + hmany + "f", rcash1);
                String receipt_bank = String.format("%." + hmany + "f", rbank1);
                String pay_cash = String.format("%." + hmany + "f", pcash1);
                String pay_bank = String.format("%." + hmany + "f", pbank1);

                if (rcash1 <= 0) {
                    receipt_cash = "";
                }
                if (rbank1 <= 0) {
                    receipt_bank = "";
                }
                if (pcash1 <= 0) {
                    pay_cash = "";
                }
                if (pbank1 <= 0) {
                    pay_bank = "";
                }

                selRomJasper.setField1(rpart1);
                selRomJasper.setField2(receipt_cash);
                selRomJasper.setField3(receipt_bank);

                selRomJasper.setField5(ppart1);
                selRomJasper.setField6(pay_cash);
                selRomJasper.setField7(pay_bank);

                k.add(selRomJasper);
            }//array size ends

            String ncash = String.format("%." + hmany + "f", rcash2);
            String nbank = String.format("%." + hmany + "f", rbank2);
            String pcash3 = String.format("%." + hmany + "f", pcash2);
            String pbank3 = String.format("%." + hmany + "f", pbank2);

            parameters.put("parameter4", "" + ncash);
            parameters.put("parameter5", "" + nbank);
            parameters.put("parameter6", "" + pcash3);
            parameters.put("parameter7", "" + pbank3);

            double close_cash = Double.parseDouble(ncash) - Double.parseDouble(pcash3);
            double close_bank = Double.parseDouble(nbank) - Double.parseDouble(pbank3);
            String close_cash2 = String.format("%." + hmany + "f", close_cash);
            String close_bank2 = String.format("%." + hmany + "f", close_bank);

            parameters.put("parameter8", "Cash Balance: " + close_cash2);
            parameters.put("parameter9", "Bank Balance: " + close_bank2);

            disable_warnigs.disableAccessWarnings();
            menu_form me = new menu_form();
            String drive = "";
            String folder = Utils.AppConfig.getAppPath();

            JasperReport jasperReport = JasperReportCompiler.compileReport("/JasperFiles/Accounts/RP.jrxml");
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(k);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

            jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
            jasperViewer.setTitle("Receipts & Payments");
            jasperViewer.setSize(400, 555);
            jasperViewer.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
            jasperViewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);

            jasperViewer.removeWindowListener(jasperViewer.getWindowListeners()[0]);
            jasperViewer.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            jasperViewer.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    jasperViewer.dispose();
                }
            });

        } catch (ClassNotFoundException | NumberFormatException | SQLException | ParseException | JRException e) {
            System.out.println(e.getMessage());
        }

    }
}
