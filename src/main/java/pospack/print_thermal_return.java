package pospack;

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
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
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
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public class print_thermal_return {

    DataUtil util;
    int hmany = 2, hmany1 = 3;

    public void Report(DataUtil util, String billno, String drive, String folder, String billformat) {
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

            String date = "", location = "", terminal = "", cashier = "", items = "", quans = "", pby = "", cname = "", mobile = "", time = "", cid = "";
            double sub = 0, disamt = 0, addamt = 0, net = 0, paid = 0, bal = 0, taxamt = 0, today_points = 0, total_points = 0;
            query = "select date_format(dat,'%d/%m/%Y'),tim,location,terminal,cashier,items,quans,sub,disamt,addamt,net,pby,paid,bal,cname,mobile,cid,taxamt from sreturn where billno='" + billno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                date = r.getString(1);
                time = r.getString(2);
                location = r.getString(3);
                terminal = r.getString(4);
                cashier = r.getString(5);
                items = r.getString(6);
                quans = r.getString(7);
                sub = r.getDouble(8);
                disamt = r.getDouble(9);
                addamt = r.getDouble(10);
                net = r.getDouble(11);
                pby = r.getString(12);
                paid = r.getDouble(13);
                bal = r.getDouble(14);
                cname = r.getString(15);
                mobile = r.getString(16);
                cid = r.getString(17);
                taxamt = r.getDouble(18);
            }
            String billno1 = billno;
            if (!letter.equals(".")) {
                billno1 = letter + "" + billno;
            }

            parameters.put("parameter6", "Bill No: R" + billno1);
            parameters.put("parameter7", "Date: " + date);
            parameters.put("parameter8", "Cashier: " + cashier);
            parameters.put("parameter9", "Time: " + time);
            parameters.put("parameter10", "Location: " + location);
            parameters.put("parameter11", "Terminal: " + terminal);

            if (!cname.equals(".")) {
                parameters.put("parameter12", cname + "");
            }
            if (!mobile.equals(".")) {
                parameters.put("parameter13", mobile + ",  Cust_Id: " + cid);
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

            if (addamt > 0) {
                String addamt2 = String.format("%." + hmany + "f", addamt);
                parameters.put("parameter17", "Others : ");
                parameters.put("parameter18", "" + addamt2);
            }

            String net2 = net + "";
            String[] split3 = net2.split("\\.");
            int what3 = split3[1].length();
            if (what3 < 2) {
                net2 = net2 + "0";
            }

            String paid2 = String.format("%." + hmany + "f", paid);
            String bal2 = String.format("%." + hmany + "f", bal);

            parameters.put("parameter19", "" + net2);

            double cgst = taxamt / 2;
            String cgst2 = String.format("%." + hmany + "f", cgst);
            parameters.put("parameter20", "CGST Includes: " + cgst2);
            parameters.put("parameter21", "SGST Includes: " + cgst2);

            parameters.put("parameter22", "Paymode: " + pby);
            parameters.put("parameter23", "Received Amt: " + paid2);
            parameters.put("parameter24", "Balance: " + bal2);

            parameters.put("parameter28", "");
            parameters.put("parameter28", "");
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

            ArrayList<SelRomJasper> k = new ArrayList<>();
            double mrp, price, amount, quan;
            String udes = "";
            double mrptot = 0;
            int serial = 1;
            query = "select iname1,quan,mrp,price,amount,udes from sreturn_items where billno='" + billno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                quan = r.getDouble(2);
                mrp = r.getDouble(3);
                price = r.getDouble(4);
                amount = r.getDouble(5);
                udes = r.getString(6);
                mrptot = mrptot + (mrp * quan);

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
                selRomJasper.setField1("" + r.getString(1));
                selRomJasper.setField2("" + quan2);
                selRomJasper.setField3("" + mrp2);
                selRomJasper.setField4("" + price2);
                selRomJasper.setField5("" + amount2);
                selRomJasper.setField6("" + udes);
                selRomJasper.setField7("" + serial);
                k.add(selRomJasper);
                serial = serial + 1;
            }//while loop ends//adding items ends

            double savings = mrptot - net;
            if (savings > 0) {
                String savings2 = String.format("%." + hmany + "f", savings);
                parameters.put("parameter25", "Today Savings: " + savings2);
            }

            int today_points1 = (int) today_points;
            int total_points1 = (int) total_points;

            parameters.put("parameter26", "Today Points: " + today_points1);
            parameters.put("parameter27", "Total Earned Points: " + total_points1);

            disable_warnigs.disableAccessWarnings();

            String reportPath = "";
            switch (billformat) {
                case "Thermal GST":
                    reportPath = "/JasperFiles/Thermal_Unicode/Thermal_GST.jrxml";
                    break;
                case "Thermal GST MRP":
                    reportPath = "/JasperFiles/Thermal_Unicode/Thermal_GST_MRP.jrxml";
                    break;
                case "Thermal MRP Without GST":
                    reportPath = "/JasperFiles/Thermal_Unicode/Thermal_MRP_Without_GST.jrxml";
                    break;
                case "Thermal Short Bill":
                    reportPath = "/JasperFiles/Thermal_Unicode/Thermal_Short_Bill.jrxml";
                    break;
                case "Thermal GST 4-Inch MRP":
                    reportPath = "/JasperFiles/Thermal_Unicode/Thermal_GST_MRP_4_Inch.jrxml";
                    break;
                case "Thermal 4-Inch Without GST":
                    reportPath = "/JasperFiles/Thermal_Unicode/Thermal_4_Inch_Without_GST.jrxml";
                    break;
                case "Thermal 2-Inch MRP":
                    reportPath = "/JasperFiles/Thermal_Unicode/Thermal_GST_MRP_2_Inch.jrxml";
                    break;
                default:
                    reportPath = "/JasperFiles/Thermal_Unicode/Thermal_GST.jrxml";
                    break;
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
