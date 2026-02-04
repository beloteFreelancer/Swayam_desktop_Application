package estimatepack;

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

public class daily_estimate_report {

    JasperViewer jasperViewer;
    int hmany = 2;
    DataUtil util = null;
    ResultSet r;

    public void Report(String dfrom, String dto, DataUtil util) {
        this.util = util;
        menupack.menu_form me = new menu_form();
        String user = me.getUsername();
        String drive = "";
        String folder = Utils.AppConfig.getAppPath();
        hmany = me.getHmany();

        JasperReport jasperReport;
        JasperPrint jasperPrint;
        try {
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
            String today = g.format(d);
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(dfrom);
            String lk = (new SimpleDateFormat("yyyy/MM/dd").format(nm));
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(dto);
            String lk1 = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));

            Map<String, Object> parameters = new HashMap<>();

            String sname = "";
            String query = "select cname from setting_bill";
            r = util.doQuery(query);
            while (r.next()) {
                sname = r.getString(1);
            }

            parameters.put("parameter1", "" + sname + "".trim().toUpperCase());
            parameters.put("parameter2", "Estimate Report (" + dfrom + " to " + dto + ")");
            parameters.put("parameter5", "Printed by: " + user + "  at: " + today);

            ArrayList terminal = new ArrayList();
            ArrayList cashier = new ArrayList();
            query = "select distinct terminal,cashier from estimate where dat between '" + lk + "' and '" + lk1 + "' order by terminal,cashier";
            r = util.doQuery(query);
            while (r.next()) {
                terminal.add(r.getString(1));
                cashier.add(r.getString(2));
            }
            double tnet = 0;
            long tbills = 0;

            ArrayList date = new ArrayList();
            ArrayList time = new ArrayList();
            ArrayList billno = new ArrayList();
            ArrayList items = new ArrayList();
            ArrayList quans = new ArrayList();
            ArrayList net = new ArrayList();
            ArrayList pby = new ArrayList();
            ArrayList price_type = new ArrayList();

            for (int i = 0; i < terminal.size(); i++) {
                date.add("");
                time.add("");
                billno.add("Counter: " + terminal.get(i).toString().toUpperCase() + ", " + cashier.get(i).toString().toUpperCase());
                items.add("");
                quans.add("");
                net.add("");
                pby.add("");
                price_type.add("");
                double total = 0;
                query = "select date_format(dat,'%d/%m/%Y'),tim,billno,items,quans,pby,price_type,net from estimate where dat between '" + lk + "' and '" + lk1 + "' and terminal='" + terminal.get(i) + "' and cashier='" + cashier.get(i) + "' order by dat,billno";
                r = util.doQuery(query);
                while (r.next()) {
                    date.add(r.getString(1));
                    time.add(r.getString(2));
                    billno.add(r.getString(3));
                    items.add(r.getString(4));
                    quans.add(r.getString(5));
                    pby.add(r.getString(6));
                    price_type.add(r.getString(7));
                    double amount = r.getDouble(8);
                    total = total + amount;
                    tnet = tnet + amount;
                    tbills = tbills + 1;

                    String amount1 = String.format("%." + hmany + "f", amount);
                    net.add(amount1);
                }
                date.add("");
                time.add("");
                billno.add("");
                items.add("");
                quans.add("");
                net.add("");
                pby.add("");
                price_type.add("");

                String total1 = String.format("%." + hmany + "f", total);
                date.add("");
                time.add("");
                billno.add("TOTAL");
                items.add("");
                quans.add("");
                net.add("" + total1);
                pby.add("");
                price_type.add("");

                date.add("");
                time.add("");
                billno.add("");
                items.add("");
                quans.add("");
                net.add("");
                pby.add("");
                price_type.add("");
                date.add("");
                time.add("");
                billno.add("");
                items.add("");
                quans.add("");
                net.add("");
                pby.add("");
                price_type.add("");
            }//terminal array size

            ArrayList k = new ArrayList();

            for (int i = 0; i < billno.size(); i++) {
                SelRomJasper selRomJasper = new SelRomJasper();

                selRomJasper.setField1("" + date.get(i));
                selRomJasper.setField2("" + time.get(i));
                selRomJasper.setField3("" + billno.get(i));
                selRomJasper.setField4("" + items.get(i));
                selRomJasper.setField5("" + quans.get(i));
                selRomJasper.setField6("" + net.get(i));
                selRomJasper.setField7("" + pby.get(i));
                selRomJasper.setField8("" + price_type.get(i));
                k.add(selRomJasper);
            }

            //sales ends
            String tnet1 = String.format("%." + hmany + "f", tnet);
            parameters.put("parameter3", "Total: " + tbills);
            parameters.put("parameter4", "" + tnet1);

            double cash = 0, card = 0, credit = 0, others = 0;
            query = "select Sum(CASE WHEN t.pby = 'Cash' THEN t.net ELSE NULL END) AS Cash,Sum(CASE WHEN t.pby = 'Card' THEN t.net ELSE NULL END) AS Card,Sum(CASE WHEN t.pby = 'Credit' THEN t.net ELSE NULL END) AS Credit,Sum(CASE WHEN t.pby = 'Others' THEN t.net ELSE NULL END) AS Others from estimate t where dat between '" + lk + "' and '" + lk1 + "'";
            r = util.doQuery(query);
            while (r.next()) {
                cash = r.getDouble(1);
                card = r.getDouble(2);
                credit = r.getDouble(3);
                others = r.getDouble(4);
            }
            String cash1 = String.format("%." + hmany + "f", cash);
            String card1 = String.format("%." + hmany + "f", card);
            String credit1 = String.format("%." + hmany + "f", credit);
            String others1 = String.format("%." + hmany + "f", others);

            parameters.put("parameter6", "" + tbills);
            parameters.put("parameter7", "" + cash1);
            parameters.put("parameter8", "" + card1);
            parameters.put("parameter9", "" + credit1);
            parameters.put("parameter10", "" + others1);
            parameters.put("parameter11", "" + tnet1);

            disable_warnigs.disableAccessWarnings();

            jasperReport = JasperReportCompiler.compileReport("/JasperFiles/Reports/Daily_Sales_Report.jrxml");

            JRBeanCollectionDataSource beanColDataSource
                    = new JRBeanCollectionDataSource(k);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
            jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
            jasperViewer.setSize(400, 555);
            jasperViewer.setTitle("Estimate Report");
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
