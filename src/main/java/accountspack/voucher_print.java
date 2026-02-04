package accountspack;

import com.selrom.db.DataUtil;
import com.selrom.utils.JasperReportCompiler;
import java.awt.print.PrinterJob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import menupack.AmountInWords;
import menupack.menu_form;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/*
 * @author selvagates, ©Selrom Software,  www.selromsoft.in,  Ph: +91 9942732229, Email: mysoft.java@gmail.com
 */
public class voucher_print {

    JasperViewer jasperViewer;
    DataUtil util = null;

    int num = 0, hmany = 2;
    String rupe = "";

    public void Report(String billno, DataUtil util1) throws Exception {
        this.util = util1;

        JasperReport jasperReport;
        JasperPrint jasperPrint;
        try {
            Map<String, Object> parameters = new <String, Object>HashMap();
            String class1 = "", fname = "", dob = "", com = "", sname = "";
            PreparedStatement t;
            ResultSet r;
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");

            menupack.menu_form me = new menu_form();
            String drive = "";
            String folder = Utils.AppConfig.getAppPath();

            String add1 = "", add2 = "", add3 = "", add4 = "";

            String query = "select cname,add1,add2,add3,hmany from setting_bill";
            r = util.doQuery(query);
            while (r.next()) {
                add1 = r.getString(1);
                add2 = r.getString(2);
                add3 = r.getString(3);
                add4 = r.getString(4);
                hmany = r.getInt(5);
            }
            parameters.put("parameter1", "" + add1);
            parameters.put("parameter2", "" + add2);
            parameters.put("parameter3", "" + add3);
            parameters.put("parameter4", "" + add4);
            parameters.put("parameter7", "M/s " + add1.trim().toUpperCase());

            String date = "", cname = "", what = "", pby = "", entry = "";
            double amount = 0;
            Collection<Map<String, ?>> col = new ArrayList<>();
            query = "select distinct date_format(dat,'%d/%m/%Y'),whom,what,pby,amount,entry from account_voucher where billno='"
                    + billno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                date = r.getString(1);
                cname = r.getString(2);
                what = r.getString(3);
                pby = r.getString(4);
                amount = r.getDouble(5);
                entry = r.getString(6);

                Map<String, Object> m = new HashMap<>();
                m.put("field1", date);
                m.put("field2", billno);
                m.put("field3", what);
                String amtStr = String.format("%." + hmany + "f", amount);
                if ("Debit".equalsIgnoreCase(entry)) {
                    m.put("field4", amtStr);
                    m.put("field5", "");
                } else {
                    m.put("field4", "");
                    m.put("field5", amtStr);
                }
                col.add(m);
            }

            parameters.put("parameter5", "Voucher No: " + billno);
            parameters.put("parameter6", "Date: " + date);
            parameters.put("parameter10", "" + what);
            parameters.put("parameter12", " : " + cname);

            int amount1 = (int) amount;
            num = amount1;
            num();
            String rupe2 = "", rupe3 = "";
            parameters.put("parameter8", "" + rupe);

            String amount2 = String.format("%." + hmany + "f", amount);
            parameters.put("parameter9", amount2);
            parameters.put("parameter11", "" + amount2);

            jasperReport = JasperReportCompiler.compileReport("/JasperFiles/Accounts/Voucher.jrxml");
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRMapCollectionDataSource(col));

            PrinterJob job = PrinterJob.getPrinterJob();
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            int selectedService = 0;
            for (int i = 0; i < services.length; i++) {
                if (services[i].getName().equalsIgnoreCase("pos")) {
                    selectedService = i;
                }
            }
            job.setPrintService(services[selectedService]);
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            printRequestAttributeSet.add(MediaSizeName.ISO_A4);
            printRequestAttributeSet.add(new Copies(1));
            JRPrintServiceExporter exporter;
            exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET,
                    services[selectedService].getAttributes());
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET,
                    printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.TRUE);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void num() throws Exception {

        AmountInWords a1 = new AmountInWords();
        a1.input(num);
        rupe = a1.convertToWords(num);
    }

    public void ruppes(String rupee) {
        rupe = rupee;
    }

}
