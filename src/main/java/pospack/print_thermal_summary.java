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
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public class print_thermal_summary {

    DataUtil util;
    int hmany = 2, hmany1 = 3;

    public void Report(DataUtil util, String billno, String drive, String folder, String billformat) {
        try {
            this.util = util;
            Map<String, Object> parameters = new <String, Object>HashMap();            parameters.put("parameter1", "");
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
            parameters.put("parameter55", "");
            parameters.put("parameter23", "");
            parameters.put("parameter24", "");
            parameters.put("parameter25", "");

            String add1 = "", add2 = "", add3 = "", add4 = "", head = "", sms1 = "", sms2 = "", sms3 = "", letter = "", add5 = "", sms4 = "", logoPath = "";
            String query = "select cname,add1,add2,add3,bhead,sms1,sms2,sms3,hmany,letter,add4,sms4,logo_path from setting_bill";
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
                add5 = r.getString(11);
                sms4 = r.getString(12);
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
            if (!add5.equals(".")) {
                parameters.put("parameter55", "GSTIN: " + add5);
            }
            parameters.put("parameter5", head);

            String date = "", location = "", terminal = "", cashier = "", items = "", quans = "", pby = "", cname = "", mobile = "", time = "", cid = "";
            double sub = 0, disamt = 0, addamt = 0, net = 0, paid = 0, bal = 0, taxamt = 0, today_points = 0, total_points = 0;
            query = "select date_format(dat,'%d/%m/%Y'),tim,location,terminal,cashier,items,quans,sub,disamt,addamt,net,pby,paid,bal,cname,mobile,cid,taxamt,today_points,total_points from sales where billno='" + billno + "'";
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
                today_points = r.getDouble(19);
                total_points = r.getDouble(20);
            }
            String billno1 = billno;
            if (!letter.equals(".")) {
                billno1 = letter + "" + billno;
            }

            parameters.put("parameter6", "Bill No: " + billno1);
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

            if (!cname.equals(".")) {
                parameters.put("parameter32", "Name: " + cname);
            }
            if (!mobile.equals(".")) {
                parameters.put("parameter33", "Mobile: " + mobile);
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
            String taxamt2 = String.format("%." + hmany + "f", taxamt);
            parameters.put("parameter20", "CGST Includes: " + cgst2);
            parameters.put("parameter21", "SGST Includes: " + cgst2);

            parameters.put("parameter32", "");
            parameters.put("parameter33", "");
            if (cgst > 0) {
                parameters.put("parameter32", cgst2);
            }
            if (taxamt > 0) {
                parameters.put("parameter33", taxamt2);
            }
            parameters.put("parameter22", "Paymode: " + pby);
            parameters.put("parameter23", "Received Amt: " + paid2);
            parameters.put("parameter24", "Balance: " + bal2);

            parameters.put("parameter28", "");
            parameters.put("parameter29", "");
            parameters.put("parameter30", "");
            parameters.put("parameter56", "");
            if (!sms1.equals(".")) {
                parameters.put("parameter28", "" + sms1);
            }
            if (!sms2.equals(".")) {
                parameters.put("parameter29", "" + sms2);
            }
            if (!sms3.equals(".")) {
                parameters.put("parameter30", "" + sms3);
            }
            if (!sms4.equals(".")) {
                parameters.put("parameter56", "" + sms4);
            }

            ArrayList iname3 = new ArrayList();
            ArrayList quan3 = new ArrayList();
            ArrayList mrp3 = new ArrayList();
            ArrayList price3 = new ArrayList();
            ArrayList udes3 = new ArrayList();
            ArrayList amount3 = new ArrayList();
            int count = 0;
            query = "select iname1,quan,mrp,price,amount,udes from sales_items where billno='" + billno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                iname3.add(r.getString(1));
                quan3.add(r.getString(2));
                mrp3.add(r.getString(3));
                price3.add(r.getString(4));
                amount3.add(r.getString(5));
                udes3.add(r.getString(6));
                count = count + 1;
            }

            ArrayList k = new ArrayList();
            double mrp, price, amount, quan;
            String udes, iname;
            double mrptot = 0;
            int serial = 1;
            int j = 0;
            for (int i = 0; i < iname3.size(); i++) {
                quan = Double.parseDouble(quan3.get(i).toString());
                mrp = Double.parseDouble(mrp3.get(i).toString());
                price = Double.parseDouble(price3.get(i).toString());
                amount = Double.parseDouble(amount3.get(i).toString());
                udes = udes3.get(i).toString();
                iname = iname3.get(i).toString();
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
                selRomJasper.setField1(iname);
                selRomJasper.setField2(quan2);
                selRomJasper.setField3(mrp2);
                selRomJasper.setField4(price2);
                selRomJasper.setField5(amount2);
                selRomJasper.setField6(udes);
                selRomJasper.setField7("" + serial);

                if (!udes.equals(".")) {
                    quan2 = quan2 + " " + udes;
                }
                selRomJasper.setField8(quan2);
                if (j == count - 1) {
                    selRomJasper.setField9("90");
                }
                k.add(selRomJasper);
                serial = serial + 1;
                j++;
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

            parameters.put("parameter35", "");
            parameters.put("parameter36", "");
            parameters.put("parameter37", "");
            parameters.put("parameter38", "");
            parameters.put("parameter39", "");
            parameters.put("parameter40", "");
            parameters.put("parameter41", "");
            parameters.put("parameter42", "");
            parameters.put("parameter43", "");
            parameters.put("parameter44", "");
            parameters.put("parameter45", "");
            parameters.put("parameter46", "");
            parameters.put("parameter47", "");
            parameters.put("parameter48", "");
            parameters.put("parameter49", "");
            parameters.put("parameter50", "");
            parameters.put("parameter51", "");
            parameters.put("parameter52", "");
            parameters.put("parameter53", "");
            parameters.put("parameter54", "");

            double value0 = 0, gst5 = 0, value5 = 0, gst12 = 0, value12 = 0, gst18 = 0, value18 = 0, gst28 = 0, value28 = 0;
            query = "select sum(sub) from sales_items where billno='" + billno + "' and taxp='0' ";
            r = util.doQuery(query);
            while (r.next()) {
                value0 = r.getDouble(1);
            }
            query = "select sum(sub),sum(taxamt) from sales_items where billno='" + billno + "' and taxp='5' ";
            r = util.doQuery(query);
            while (r.next()) {
                value5 = r.getDouble(1);
                gst5 = r.getDouble(2);
            }
            query = "select sum(sub),sum(taxamt) from sales_items where billno='" + billno + "' and taxp='12' ";
            r = util.doQuery(query);
            while (r.next()) {
                value12 = r.getDouble(1);
                gst12 = r.getDouble(2);
            }
            query = "select sum(sub),sum(taxamt) from sales_items where billno='" + billno + "' and taxp='18' ";
            r = util.doQuery(query);
            while (r.next()) {
                value18 = r.getDouble(1);
                gst18 = r.getDouble(2);
            }
            query = "select sum(sub),sum(taxamt) from sales_items where billno='" + billno + "' and taxp='28' ";
            r = util.doQuery(query);
            while (r.next()) {
                value28 = r.getDouble(1);
                gst28 = r.getDouble(2);
            }
            double tvalue = 0, tgst = 0;
            if (value0 > 0) {
                String value02 = String.format("%." + hmany + "f", value0);
                parameters.put("parameter35", "" + value02);
                tvalue = tvalue + value0;
            }
            if (value5 > 0) {
                String value52 = String.format("%." + hmany + "f", value5);
                double tax_amt = gst5 / 2;
                String tax_amt2 = String.format("%." + hmany + "f", tax_amt);
                String gst52 = String.format("%." + hmany + "f", gst5);
                parameters.put("parameter38", "" + value52);
                parameters.put("parameter39", "" + gst52);
                parameters.put("parameter40", "" + tax_amt2);
                tvalue = tvalue + value5;
                tgst = tgst + gst5;
            }

            if (value12 > 0) {
                String value122 = String.format("%." + hmany + "f", value12);
                double tax_amt = gst12 / 2;
                String tax_amt2 = String.format("%." + hmany + "f", tax_amt);
                String gst122 = String.format("%." + hmany + "f", gst12);
                parameters.put("parameter41", "" + value122);
                parameters.put("parameter42", "" + gst122);
                parameters.put("parameter43", "" + tax_amt2);
                tvalue = tvalue + value12;
                tgst = tgst + gst12;
            }

            if (value18 > 0) {
                String value182 = String.format("%." + hmany + "f", value18);
                double tax_amt = gst18 / 2;
                String tax_amt2 = String.format("%." + hmany + "f", tax_amt);
                String gst182 = String.format("%." + hmany + "f", gst18);
                parameters.put("parameter45", "" + value182);
                parameters.put("parameter46", "" + gst182);
                parameters.put("parameter47", "" + tax_amt2);
                tvalue = tvalue + value18;
                tgst = tgst + gst18;
            }
            if (value28 > 0) {
                String value282 = String.format("%." + hmany + "f", value28);
                double tax_amt = gst28 / 2;
                String tax_amt2 = String.format("%." + hmany + "f", tax_amt);
                String gst282 = String.format("%." + hmany + "f", gst28);
                parameters.put("parameter49", "" + value282);
                parameters.put("parameter50", "" + gst282);
                parameters.put("parameter51", "" + tax_amt2);
                tvalue = tvalue + value28;
                tgst = tgst + gst28;
            }

            String tvalue2 = String.format("%." + hmany + "f", tvalue);
            String tgst2 = String.format("%." + hmany + "f", tgst);
            double tcgst = tgst / 2;
            String tcgst2 = String.format("%." + hmany + "f", tcgst);
            parameters.put("parameter52", "" + tvalue2);
            parameters.put("parameter53", "" + tgst2);
            parameters.put("parameter54", "" + tcgst2);

            disable_warnigs.disableAccessWarnings();
            JasperReport jasperReport = JasperReportCompiler.compileReport("/JasperFiles/Thermal_Unicode/Thermal_GST_Summary.jrxml");
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(k);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

            com.selrom.utils.ReportPreview.showPreview(jasperPrint, drive, folder);

        } catch (ClassNotFoundException | SQLException | JRException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
