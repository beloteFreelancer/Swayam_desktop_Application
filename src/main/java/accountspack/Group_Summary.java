package accountspack;

import com.selrom.db.DataUtil;
import com.selrom.db.disable_warnigs;
import com.selrom.utils.JasperReportCompiler;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

public class Group_Summary {

    JasperViewer jasperViewer;
    String hmany = "2";
    DataUtil util;

    public void Report(DataUtil util, String account) {
        this.util = util;
        try {
            Map<String, Object> parameters = new HashMap<>();
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd-MM-yyyy");

            String cname = "";
            String query = "select cname,hmany from setting_bill";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                cname = r.getString(1);
                hmany = r.getString(2);
            }
            parameters.put("parameter1", "" + cname.trim());
            parameters.put("parameter2", "GROUP SUMMARY:  " + account);
            parameters.put("parameter3", "(As on " + g.format(d) + ")");

            ArrayList k = new ArrayList();
            double ncredit = 0, ndebit = 0;
            query = "select date_format(dat,'%d/%m/%Y'),billno,what,amount,entry from account_voucher where account='" + account + "' order by dat,billno";
            r = util.doQuery(query);
            while (r.next()) {
                SelRomJasper selRomJasper = new SelRomJasper();
                selRomJasper.setField1(r.getString(1));
                selRomJasper.setField2(r.getString(2));
                selRomJasper.setField3(r.getString(3));

                String credit = "", debit = "";
                double amount = r.getDouble(4);
                String amount2 = String.format("%." + hmany + "f", amount);

                String entry = r.getString(5);
                if (entry.equals("Credit")) {
                    credit = amount2;
                    ncredit = ncredit + amount;
                } else {
                    debit = amount2;
                    ndebit = ndebit + amount;
                }
                selRomJasper.setField4(debit);
                selRomJasper.setField5(credit);

                k.add(selRomJasper);
            }//while loop ends
            String ncredit2 = String.format("%." + hmany + "f", ncredit);
            String ndebit2 = String.format("%." + hmany + "f", ndebit);

            if (ncredit <= 0) {
                ncredit2 = "";
            }
            if (ndebit <= 0) {
                ndebit2 = "";
            }
            parameters.put("parameter6", ndebit2);
            parameters.put("parameter7", ncredit2);

            double open_debit = 0, open_credit = 0;
            query = "select sum(debit),sum(credit) from account_master where head='" + account + "'";
            r = util.doQuery(query);
            while (r.next()) {
                open_debit = r.getDouble(1);
                open_credit = r.getDouble(2);
            }
            String open_debit2 = String.format("%." + hmany + "f", open_debit);
            String open_credit2 = String.format("%." + hmany + "f", open_credit);

            if (open_credit <= 0) {
                open_credit2 = "";
            }
            if (open_debit <= 0) {
                open_debit2 = "";
            }
            parameters.put("parameter4", open_debit2);
            parameters.put("parameter5", open_credit2);

            double cdebit = ndebit + open_debit;
            double ccredit = ncredit + open_credit;

            String ccredit2 = String.format("%." + hmany + "f", ccredit);
            String cdebit2 = String.format("%." + hmany + "f", cdebit);

            if (ccredit <= 0) {
                ccredit2 = "";
            }
            if (cdebit <= 0) {
                cdebit2 = "";
            }
            parameters.put("parameter8", cdebit2);
            parameters.put("parameter9", ccredit2);

            disable_warnigs.disableAccessWarnings();
            menu_form me = new menu_form();
            String drive = "";
            String folder = Utils.AppConfig.getAppPath();

            JasperReport jasperReport = JasperReportCompiler.compileReport("/JasperFiles/Accounts/Group_Summary.jrxml");
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(k);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

            jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
            jasperViewer.setTitle("Group Summary");
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

        } catch (ClassNotFoundException | SQLException | JRException e) {
            System.out.println(e.getMessage());
        }

    }
}
