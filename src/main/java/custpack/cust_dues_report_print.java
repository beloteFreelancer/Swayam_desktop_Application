package custpack;

import com.selrom.db.DataUtil;
import com.selrom.db.disable_warnigs;
import com.selrom.utils.JasperReportCompiler;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import menupack.SelRomJasper;
import menupack.menu_form;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class cust_dues_report_print {

    JasperViewer jasperViewer;
    String hmany = "2";
    DataUtil util;

    public void Report(DataUtil util, String option, String area) {
        this.util = util;
        try {

            Map<String, Object> parameters = new HashMap<>();
            String cnamez = "";
            String query = "select cname,hmany from setting_bill";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                cnamez = r.getString(1);
                hmany = r.getString(2);
            }
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("yyyy-MM-dd");
            String today = g1.format(d);
            parameters.put("parameter1", "" + cnamez.trim());
            parameters.put("parameter2", "Balance List as on " + g.format(d));

            ArrayList cname2 = new ArrayList();
            if (option.equals("Yes")) {
                query = "select distinct a.cname from cust_bal a,cust b where tot-paid>0 and a.cid=b.cid order by a.cname";
            } else {
                query = "select distinct a.cname from cust_bal a,cust b where city='" + area + "' and tot-paid>0 and a.cid=b.cid order by a.cname";
            }
            r = util.doQuery(query);
            while (r.next()) {
                cname2.add(r.getString(1));
            }
            ArrayList cname = new ArrayList();
            ArrayList sno = new ArrayList();
            ArrayList billno = new ArrayList();
            ArrayList date = new ArrayList();
            ArrayList tot = new ArrayList();
            ArrayList paid = new ArrayList();
            ArrayList bal = new ArrayList();
            ArrayList days = new ArrayList();

            String query2 = "";
            ResultSet r2;
            int serial = 1;
            double ntot = 0, npaid = 0, nbal = 0;
            for (int i = 0; i < cname2.size(); i++) {
                cname.add(cname2.get(i));
                sno.add("");
                billno.add("");
                date.add("");
                tot.add("");
                paid.add("");
                bal.add("");
                days.add("");

                query2 = "select billno,date_format(dat,'%d/%m/%Y'),tot,paid,datediff(dat,'" + today + "') from cust_bal a,cust b where tot-paid>0 and a.cid=b.cid and a.cname='" + cname2.get(i) + "' order by billno";
                r2 = util.doQuery(query2);
                while (r2.next()) {
                    cname.add("");
                    sno.add(serial);
                    billno.add(r2.getString(1));
                    date.add(r2.getString(2));

                    double tot_amt = r2.getDouble(3);
                    double paid_amt = r2.getDouble(4);
                    int daysz = r2.getInt(5);
                    double bal_amt = tot_amt - paid_amt;

                    ntot = ntot + tot_amt;
                    npaid = npaid + paid_amt;
                    nbal = nbal + bal_amt;

                    String tot_amt2 = String.format("%." + hmany + "f", tot_amt);
                    String paid_amt2 = String.format("%." + hmany + "f", paid_amt);
                    String bal_amt2 = String.format("%." + hmany + "f", bal_amt);
                    tot.add(tot_amt2);
                    paid.add(paid_amt2);
                    bal.add(bal_amt2);
                    days.add(daysz);
                    serial = serial + 1;
                }
            }
            ArrayList k = new ArrayList();
            for (int i = 0; i < cname.size(); i++) {
                SelRomJasper selRomJasper = new SelRomJasper();
                selRomJasper.setField1(sno.get(i).toString());
                selRomJasper.setField2(billno.get(i).toString());
                selRomJasper.setField3(date.get(i).toString());
                selRomJasper.setField4(tot.get(i).toString());
                selRomJasper.setField5(paid.get(i).toString());
                selRomJasper.setField6(bal.get(i).toString());
                selRomJasper.setField7(days.get(i).toString());
                selRomJasper.setField8(cname.get(i).toString());
                k.add(selRomJasper);
            }//array size ends

            String tot_amt2 = String.format("%." + hmany + "f", ntot);
            String paid_amt2 = String.format("%." + hmany + "f", npaid);
            String bal_amt2 = String.format("%." + hmany + "f", nbal);

            parameters.put("parameter3", tot_amt2);
            parameters.put("parameter4", paid_amt2);
            parameters.put("parameter5", bal_amt2);

            disable_warnigs.disableAccessWarnings();
            menu_form me = new menu_form();
            String drive = "";
            String folder = Utils.AppConfig.getAppPath();

            JasperReport jasperReport = JasperReportCompiler.compileReport("/JasperFiles/Reports/Cust_Dues_Report.jrxml");
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(k);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

            jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
            jasperViewer.setTitle("Balace List");
            jasperViewer.setSize(400, 555);
            jasperViewer.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
            jasperViewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);

            jasperViewer.removeWindowListener(jasperViewer.getWindowListeners()[0]);
            jasperViewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }
}
