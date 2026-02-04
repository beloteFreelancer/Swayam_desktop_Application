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

public class daily_estimate {

    JasperViewer jasperViewer;
    DataUtil util = null;
    ResultSet r;
    int hmany = 2;

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
            String query = "select distinct cname from setting_bill";
            r = util.doQuery(query);
            while (r.next()) {
                sname = r.getString(1);
            }

            parameters.put("parameter1", "" + sname + "".trim().toUpperCase());
            parameters.put("parameter2", "Estimate Summary (" + dfrom + " to " + dto + ")");
            parameters.put("parameter9", "Printed by: " + user + "  at: " + today);

            double tcash = 0, tcard = 0, tcredit = 0, tothers = 0, tnet = 0;
            long tbills = 0;
            ArrayList k = new ArrayList();
            query = "select location,terminal,count(billno),sum(cash),sum(card),sum(credit),sum(others),sum(net) from estimate where dat between '" + lk + "' and '" + lk1 + "' group by location,terminal order by location,terminal";
            r = util.doQuery(query);
            while (r.next()) {
                SelRomJasper selRomJasper = new SelRomJasper();

                selRomJasper.setField1("" + r.getString(1));
                selRomJasper.setField2("" + r.getString(2));
                int bills = r.getInt(3);
                double cash = r.getDouble(4);
                double card = r.getDouble(5);
                double credit = r.getDouble(6);
                double others = r.getDouble(7);
                double net = r.getDouble(8);

                tcash = tcash + cash;
                tcard = tcard + card;
                tcredit = tcredit + credit;
                tothers = tothers + others;
                tnet = tnet + net;
                tbills = tbills + bills;

                String cash2 = String.format("%." + hmany + "f", cash);
                String card2 = String.format("%." + hmany + "f", card);
                String credit2 = String.format("%." + hmany + "f", credit);
                String others2 = String.format("%." + hmany + "f", others);
                String net2 = String.format("%." + hmany + "f", net);

                selRomJasper.setField3("" + r.getString(3));
                selRomJasper.setField4(cash2);
                selRomJasper.setField5(card2);
                selRomJasper.setField6(credit2);
                selRomJasper.setField7(others2);
                selRomJasper.setField8(net2);

                k.add(selRomJasper);
            }
            //sales ends
            String tcash2 = String.format("%." + hmany + "f", tcash);
            String tcard2 = String.format("%." + hmany + "f", tcard);
            String tcredit2 = String.format("%." + hmany + "f", tcredit);
            String tothers2 = String.format("%." + hmany + "f", tothers);
            String tnet2 = String.format("%." + hmany + "f", tnet);

            parameters.put("parameter3", "" + tbills);
            parameters.put("parameter4", "" + tcash2);
            parameters.put("parameter5", "" + tcard2);
            parameters.put("parameter6", "" + tcredit2);
            parameters.put("parameter7", "" + tothers2);
            parameters.put("parameter8", "" + tnet2);

            disable_warnigs.disableAccessWarnings();

            jasperReport = JasperReportCompiler.compileReport("/JasperFiles/Reports/Daily_Sales.jrxml");

            JRBeanCollectionDataSource beanColDataSource
                    = new JRBeanCollectionDataSource(k);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
            jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
            jasperViewer.setSize(400, 555);
            jasperViewer.setTitle("Estimate Summary");
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
