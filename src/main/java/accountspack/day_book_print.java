package accountspack;

import com.selrom.db.DataUtil;
import com.selrom.db.disable_warnigs;
import com.selrom.utils.JasperReportCompiler;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JFrame;
import menupack.SelRomJasper;
import menupack.menu_form;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class day_book_print {

    JasperViewer jasperViewer;
    DataUtil util;

    public void Report(DataUtil util, String dfrom, String dto) throws ParseException {
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
            DecimalFormat df1 = new DecimalFormat("#0.00");

            SimpleDateFormat g1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
            String today = g1.format(d);
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(dfrom);
            String dfrom1 = (new SimpleDateFormat("yyyy/MM/dd").format(nm));
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(dto);
            String dto1 = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));

            String query = "select cname,hmany from setting_bill";
            r = util.doQuery(query);
            while (r.next()) {
                cname = r.getString(1);
                hmany = r.getInt(2);
            }
            parameters.put("parameter1", cname);

            parameters.put("parameter2", "DAY BOOK STATEMENT  (" + dfrom + ")");
            parameters.put("parameter6", "(As on " + today + ")");

            parameters.put("parameter9", "Opening Balance on  " + dfrom + " =");
            parameters.put("parameter11", "Closing Balance on  " + dto + " =");
            int ssno = 0;
            double obal = 0;

            // Calculate opening balance from all relevant tables
            String salesOpeningQuery = "SELECT COALESCE(SUM(net), 0) FROM sales WHERE dat < '" + dfrom1 + "'";
            ResultSet rsSalesOpening = util.doQuery(salesOpeningQuery);
            if (rsSalesOpening.next()) {
                obal += rsSalesOpening.getDouble(1);
            }

            String purchaseOpeningQuery = "SELECT COALESCE(SUM(net), 0) FROM purchase WHERE dat < '" + dfrom1 + "'";
            ResultSet rsPurchaseOpening = util.doQuery(purchaseOpeningQuery);
            if (rsPurchaseOpening.next()) {
                obal -= rsPurchaseOpening.getDouble(1);
            }

            String sreturnOpeningQuery = "SELECT COALESCE(SUM(net), 0) FROM sreturn WHERE dat < '" + dfrom1 + "'";
            ResultSet rsSReturnOpening = util.doQuery(sreturnOpeningQuery);
            if (rsSReturnOpening.next()) {
                obal -= rsSReturnOpening.getDouble(1);
            }

            String preturnOpeningQuery = "SELECT COALESCE(SUM(net), 0) FROM preturn WHERE dat < '" + dfrom1 + "'";
            ResultSet rsPReturnOpening = util.doQuery(preturnOpeningQuery);
            if (rsPReturnOpening.next()) {
                obal += rsPReturnOpening.getDouble(1);
            }

            // Account Voucher Debit
            String voucherDebitOpeningQuery = "SELECT COALESCE(SUM(amount), 0) FROM account_voucher WHERE dat < '" + dfrom1 + "' AND entry = 'Debit'";
            ResultSet rsVoucherDebitOpening = util.doQuery(voucherDebitOpeningQuery);
            if (rsVoucherDebitOpening.next()) {
                obal -= rsVoucherDebitOpening.getDouble(1);
            }

            // Account Voucher Credit
            String voucherCreditOpeningQuery = "SELECT COALESCE(SUM(amount), 0) FROM account_voucher WHERE dat < '" + dfrom1 + "' AND entry = 'Credit'";
            ResultSet rsVoucherCreditOpening = util.doQuery(voucherCreditOpeningQuery);
            if (rsVoucherCreditOpening.next()) {
                obal += rsVoucherCreditOpening.getDouble(1);
            }

            // Cust Pay (Credit/Inflow)
            String custPayOpeningQuery = "SELECT COALESCE(SUM(amount), 0) FROM cust_pay WHERE dat < '" + dfrom1 + "'";
            ResultSet rsCustPayOpening = util.doQuery(custPayOpeningQuery);
            if (rsCustPayOpening.next()) {
                obal += rsCustPayOpening.getDouble(1);
            }

            // Ven Pay (Debit/Outflow)
            String venPayOpeningQuery = "SELECT COALESCE(SUM(amount), 0) FROM ven_pay WHERE dat < '" + dfrom1 + "'";
            ResultSet rsVenPayOpening = util.doQuery(venPayOpeningQuery);
            if (rsVenPayOpening.next()) {
                obal -= rsVenPayOpening.getDouble(1);
            }

            // Pay Bill (Debit/Outflow)
            String payBillOpeningQuery = "SELECT COALESCE(SUM(net), 0) FROM pay_bill WHERE dat < '" + dfrom1 + "'";
            ResultSet rsPayBillOpening = util.doQuery(payBillOpeningQuery);
            if (rsPayBillOpening.next()) {
                obal -= rsPayBillOpening.getDouble(1);
            }

            parameters.put("parameter10", df1.format(obal));

            ArrayList<Transaction> transactions = new ArrayList<>();
            ArrayList k = new ArrayList();

            try {
                // Fetch Sales
                query = "SELECT dat, billno, cname, net FROM sales WHERE dat BETWEEN '" + dfrom1 + "' AND '" + dto1 + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    transactions.add(new Transaction(r.getDate("dat"), "SALE-" + r.getString("billno"), r.getString("cname"), 0, r.getDouble("net"), "Sales"));
                }

                // Fetch Purchases
                query = "SELECT dat, grn, cname, net FROM purchase WHERE dat BETWEEN '" + dfrom1 + "' AND '" + dto1 + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    transactions.add(new Transaction(r.getDate("dat"), "PUR-" + r.getString("grn"), r.getString("cname"), r.getDouble("net"), 0, "Purchase"));
                }

                // Fetch Sales Returns
                query = "SELECT dat, billno, cname, net FROM sreturn WHERE dat BETWEEN '" + dfrom1 + "' AND '" + dto1 + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    transactions.add(new Transaction(r.getDate("dat"), "SRET-" + r.getString("billno"), r.getString("cname"), r.getDouble("net"), 0, "Sales Return"));
                }

                // Fetch Purchase Returns
                query = "SELECT dat, grn, cname, net FROM preturn WHERE dat BETWEEN '" + dfrom1 + "' AND '" + dto1 + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    transactions.add(new Transaction(r.getDate("dat"), "PRET-" + r.getString("grn"), r.getString("cname"), 0, r.getDouble("net"), "Purchase Return"));
                }

                // Fetch Account Vouchers
                query = "SELECT dat, billno, whom, what, amount, entry FROM account_voucher WHERE dat BETWEEN '" + dfrom1 + "' AND '" + dto1 + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    String entry = r.getString("entry");
                    double amount = r.getDouble("amount");
                    String particulars = r.getString("whom");
                    if (r.getString("what") != null && !r.getString("what").isEmpty()) {
                        particulars += " - " + r.getString("what");
                    }

                    if ("Debit".equalsIgnoreCase(entry)) {
                        transactions.add(new Transaction(r.getDate("dat"), "VCH-" + r.getString("billno"), particulars, amount, 0, "Voucher Debit"));
                    } else {
                        transactions.add(new Transaction(r.getDate("dat"), "VCH-" + r.getString("billno"), particulars, 0, amount, "Voucher Credit"));
                    }
                }

                // Fetch Cust Pay
                query = "SELECT dat, billno, cname, amount, remarks FROM cust_pay WHERE dat BETWEEN '" + dfrom1 + "' AND '" + dto1 + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    String particulars = "Received from " + r.getString("cname");
                    if (r.getString("remarks") != null && !r.getString("remarks").isEmpty()) {
                        particulars += " (" + r.getString("remarks") + ")";
                    }
                    transactions.add(new Transaction(r.getDate("dat"), "CP-" + r.getString("billno"), particulars, 0, r.getDouble("amount"), "Cust Receipt"));
                }

                // Fetch Ven Pay
                query = "SELECT dat, billno, cname, amount, remarks FROM ven_pay WHERE dat BETWEEN '" + dfrom1 + "' AND '" + dto1 + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    String particulars = "Paid to " + r.getString("cname");
                    if (r.getString("remarks") != null && !r.getString("remarks").isEmpty()) {
                        particulars += " (" + r.getString("remarks") + ")";
                    }
                    transactions.add(new Transaction(r.getDate("dat"), "VP-" + r.getString("billno"), particulars, r.getDouble("amount"), 0, "Ven Payment"));
                }

                // Fetch Pay Bill
                query = "SELECT dat, sno, cname, net FROM pay_bill WHERE dat BETWEEN '" + dfrom1 + "' AND '" + dto1 + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    transactions.add(new Transaction(r.getDate("dat"), "PAY-" + r.getString("sno"), "Salary - " + r.getString("cname"), r.getDouble("net"), 0, "Salary"));
                }

                Collections.sort(transactions, new Comparator<Transaction>() {
                    @Override
                    public int compare(Transaction t1, Transaction t2) {
                        return t1.getDate().compareTo(t2.getDate());
                    }
                });

                double tdebit = 0, tcredit = 0;
                int i = 0;
                for (Transaction transaction : transactions) {
                    String billno1 = transaction.getRefNo();
                    String date1 = new SimpleDateFormat("dd/MM/yyyy").format(transaction.getDate());
                    String part1 = transaction.getParticulars();
                    double debit2 = transaction.getDebit();
                    double credit2 = transaction.getCredit();
                    debit2 = Double.parseDouble(df1.format(debit2));
                    credit2 = Double.parseDouble(df1.format(credit2));

                    tcredit += credit2;
                    tdebit += debit2;

                    SelRomJasper selRomJasper = new SelRomJasper();
                    selRomJasper.setField1(date1);
                    selRomJasper.setField2(billno1);
                    selRomJasper.setField3(part1);
                    selRomJasper.setField5(debit2 > 0 ? df1.format(debit2) : "");
                    selRomJasper.setField6(credit2 > 0 ? df1.format(credit2) : "");

                    if (i == transactions.size() - 1) {
                        selRomJasper.setField4("90");
                    }
                    i++;
                    k.add(selRomJasper);
                }
                double cbal = obal + tcredit - tdebit;
                parameters.put("parameter12", df1.format(cbal));
            } catch (Exception e) {
                e.printStackTrace();
            }

            disable_warnigs.disableAccessWarnings();
            menupack.menu_form mp = new menu_form();
            String drive = mp.getDrive();
            String folder = mp.getFoler();
            jasperReport = JasperReportCompiler.compileReport("/JasperFiles/Accounts/cust_state.jrxml");
            JRBeanCollectionDataSource beanColDataSource
                    = new JRBeanCollectionDataSource(k);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
            jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
            jasperViewer.setSize(400, 555);
            jasperViewer.setTitle("DAY BOOK STATEMENT");
            jasperViewer.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
            jasperViewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            jasperViewer.removeWindowListener(jasperViewer.getWindowListeners()[0]);
            jasperViewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } catch (ClassNotFoundException | NumberFormatException | SQLException | JRException e) {
            System.out.println(e.getMessage());
        }
    }

}
