package purchasepack;

import com.selrom.db.DataUtil;
import com.selrom.db.disable_warnigs;
import com.selrom.utils.JasperReportCompiler;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import menupack.SelRomJasper;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

/**
 *
 * @author Selrom Software
 */
public class print_preturn {

    DataUtil util;
    int hmany = 2, hmany1 = 3;

    public void get_print(DataUtil util, String grn, String drive, String folder, String billformat) {
        try {
            this.util = util;
            Map<String, Object> parameters = new HashMap<>();            parameters.put("parameter1", "");
            parameters.put("parameter2", "");
            parameters.put("parameter3", "");
            parameters.put("parameter4", "");
            parameters.put("parameter12", "");
            parameters.put("parameter13", "");
            parameters.put("parameter15", "");
            parameters.put("parameter16", "");
            parameters.put("parameter17", "");
            parameters.put("parameter18", "");
            parameters.put("parameter26", "");
            parameters.put("parameter27", "");
            parameters.put("parameter28", "");

            parameters.put("parameter23", "");
            parameters.put("parameter24", "");
            parameters.put("parameter25", "");

            String add1 = "", add2 = "", add3 = "", add4 = "", head = "", sms1 = "", sms2 = "", sms3 = "", letter = "", logoPath = "";
            String query = "select cname,add1,add2,add3,bhead,sms1,sms2,sms3,hmany,letter,logo_path from setting_bill";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                logoPath = r.getString("logo_path");
                add1 = r.getString(1);
                add2 = r.getString(2);
                add3 = r.getString(3);
                add4 = r.getString(4);
                head = r.getString(5);
                sms1 = r.getString(6);
                sms2 = r.getString(7);
                sms3 = r.getString(8);
                hmany = r.getInt(9);
                letter = r.getString(10);
            }
            parameters.put("logo_path", logoPath);
            if (!add1.equals(".")) {
                parameters.put("parameter1", add1);
            }
            if (!add2.equals(".")) {
                parameters.put("parameter2", add2);
            }
            if (!add3.equals(".")) {
                parameters.put("parameter3", add3);
            }
            if (!add4.equals(".")) {
                parameters.put("parameter4", add4);
            }

            parameters.put("parameter5", head);

            String date = "", billno = "", items = "", quans = "", pby = "", cname = "";
            double sub = 0, disamt = 0, fright = 0, other = 0, net = 0, taxamt = 0;

            query = "select date_format(dat,'%d/%m/%Y'),cname,billno,items,quans,sub,dis,fright,other,net,pby,tax from preturn where grn='" + grn + "'";
            r = util.doQuery(query);
            while (r.next()) {
                date = r.getString(1);
                cname = r.getString(2);
                billno = r.getString(3);
                items = r.getString(4);
                quans = r.getString(5);
                sub = r.getDouble(6);
                disamt = r.getDouble(7);
                fright = r.getDouble(8);
                other = r.getDouble(9);
                net = r.getDouble(10);
                pby = r.getString(11);
                taxamt = r.getDouble(12);
            }

            parameters.put("parameter6", "Return No: " + grn);
            parameters.put("parameter7", "Date: " + date);
            parameters.put("parameter8", "Bill No: " + billno);
            parameters.put("parameter9", "");
            parameters.put("parameter10", "");
            parameters.put("parameter11", "");

            if (!cname.equals(".")) {
                parameters.put("parameter12", cname + "");
            }

            String sub2 = String.format("%." + hmany + "f", sub);
            parameters.put("parameter14", sub2);

            double quans1 = Double.parseDouble(quans);
            int quans2 = (int) quans1;
            parameters.put("parameter31", "Total Items: " + items + "    Qty: " + quans2);

            if (disamt > 0) {
                String disamt2 = String.format("%." + hmany + "f", disamt);
                parameters.put("parameter15", "Discount : ");
                parameters.put("parameter16", "" + disamt2);
            }

            double other_total = fright + other;
            if (other_total > 0) {
                String addamt2 = String.format("%." + hmany + "f", other_total);
                parameters.put("parameter17", "Others : ");
                parameters.put("parameter18", "" + addamt2);
            }

            String net2 = String.format("%." + hmany + "f", net);
            parameters.put("parameter19", "" + net2);

            double cgst = taxamt / 2;
            String cgst2 = String.format("%." + hmany + "f", cgst);
            parameters.put("parameter20", "CGST Includes: " + cgst2);
            parameters.put("parameter21", "SGST Includes: " + cgst2);

            parameters.put("parameter22", "Paymode: " + pby);

            // For purchase return, received/balance might not be applicable in the same way or are 0
            parameters.put("parameter23", "Net Amount: " + net2);
            parameters.put("parameter24", "");

            parameters.put("parameter28", "");
            parameters.put("parameter29", "");
            parameters.put("parameter30", "");

            if (!sms1.equals(".")) {
                parameters.put("parameter28", "" + sms1);
            }
            if (!sms2.equals(".")) {
                parameters.put("parameter29", "" + sms2);
            }
            if (!sms3.equals(".")) {
                parameters.put("parameter30", "" + sms3);
            }

            parameters.put("parameter32", "PURCHASE RETURN");

            ArrayList<SelRomJasper> k = new ArrayList<>();
            double mrp, price, amount, quan;
            String iname = "";
            int serial = 1;

            // Fetch items from preturn_items
            query = "select iname,quan,mrp,price,amount from preturn_items where grn='" + grn + "'";
            r = util.doQuery(query);
            while (r.next()) {
                iname = r.getString(1);
                quan = r.getDouble(2);
                mrp = r.getDouble(3);
                price = r.getDouble(4);
                amount = r.getDouble(5);

                String mrp2 = String.format("%." + hmany + "f", mrp);
                String price2 = String.format("%." + hmany + "f", price);
                String amount2 = String.format("%." + hmany + "f", amount);

                String quan2 = String.format("%." + hmany1 + "f", quan);
                String[] spli3 = quan2.split("\\.");
                int wha3 = Integer.parseInt(spli3[1]);
                if (wha3 <= 0) {
                    quan2 = spli3[0];
                }

                SelRomJasper selRomJasper = new SelRomJasper();
                selRomJasper.setField1("" + iname);
                selRomJasper.setField2("" + quan2);
                selRomJasper.setField3("" + mrp2);
                selRomJasper.setField4("" + price2);
                selRomJasper.setField5("" + amount2);
                selRomJasper.setField6(""); // udes not typically in preturn_items?
                selRomJasper.setField7("" + serial);
                k.add(selRomJasper);
                serial = serial + 1;
            }

            disable_warnigs.disableAccessWarnings();

            String reportPath = "/JasperFiles/A4_Unicode/A4_GST.jrxml"; // Default to A4 for purchase return usually

            // Map bill formats if needed, though typically A4 is used for purchase returns
            if (billformat.contains("Thermal")) {
                 reportPath = "/JasperFiles/Thermal_Unicode/Thermal_GST.jrxml";
            }

            JasperReport jasperReport = JasperReportCompiler.compileReport(reportPath);
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(k);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

            com.selrom.utils.ReportPreview.showPreview(jasperPrint, drive, folder);

        } catch (SQLException | JRException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
