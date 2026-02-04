package estimatepack;

import com.selrom.db.DataUtil;
import com.selrom.db.disable_warnigs;
import com.selrom.utils.JasperReportCompiler;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.InputStream;
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
import menupack.AmountInWords;
import menupack.SelRomJasper;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

public class print_estimate_a4_return {

    JasperViewer jasperViewer;
    int num = 0;
    String rupe = "";
    int hmany = 2, hmany2 = 3;
    DataUtil util;

    public void Report(DataUtil util, String billno, String drive, String folder, String billformat) {
        this.util = util;
        try {

            Map<String, Object> parameters = new HashMap<>();
            InputStream is = getClass().getResourceAsStream("/images/icon.png");
            parameters.put("logo", is);
            String add1 = "", add2 = "", add3 = "", add4 = "", add5 = "", sname = "", scode = "", letter = "",
                    head = "", sms1 = "", sms2 = "", sms3 = "", sms4 = "", logoPath = "";
            String query = "select cname,add1,add2,add3,add4,state,scode,ehead,sms1,sms2,sms3,sms4,letter,hmany,logo_path from setting_bill";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                logoPath = r.getString("logo_path");
                add1 = r.getString(1);
                add2 = r.getString(2);
                add3 = r.getString(3);
                add4 = r.getString(4);
                add5 = r.getString(5);
                sname = r.getString(6);
                scode = r.getString(7);
                head = r.getString(8);
                sms1 = r.getString(9);
                sms2 = r.getString(10);
                sms3 = r.getString(11);
                sms4 = r.getString(12);
                letter = r.getString(13);
                hmany = r.getInt(14);
            }
            parameters.put("logo_path", logoPath);
            parameters.put("parameter1", "" + add1);
            parameters.put("parameter2", "");
            parameters.put("parameter3", "");
            parameters.put("parameter4", "");
            parameters.put("parameter5", "");
            parameters.put("parameter6", "");

            if (add2.length() > 1) {
                parameters.put("parameter2", "" + add2);
            }
            if (add3.length() > 1) {
                parameters.put("parameter3", "" + add3);
            }
            if (add4.length() > 1) {
                parameters.put("parameter4", "" + add4);
            }
            if (add5.length() > 1) {
                parameters.put("parameter5", "" + add5);
            }
            parameters.put("parameter57", "For " + add1.trim().toUpperCase());
            parameters.put("parameter7", "ESTIMATE RETURN");

            String date = "", quans = "", cid = "", tax = "", items = "";
            double sub = 0, dis = 0, addamt = 0, net = 0, taxamt = 0, bal = 0;
            query = "select date_format(dat,'%d/%m/%Y'),items,quans,sub,disamt,addamt,net,bal,cid,taxamt,tax from ereturn where billno='"
                    + billno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                date = r.getString(1);
                items = r.getString(2);
                quans = r.getString(3);
                sub = r.getDouble(4);
                dis = r.getDouble(5);
                addamt = r.getDouble(6);
                net = r.getDouble(7);
                bal = r.getDouble(8);
                cid = r.getString(9);
                taxamt = r.getDouble(10);
                tax = r.getString(11);
            }
            String billno1 = "";
            if (!letter.equals("")) {
                billno1 = letter + "" + billno;
            }
            parameters.put("parameter9", ": " + billno1);
            parameters.put("parameter10", ": " + date);
            parameters.put("parameter11", ": " + sname);
            parameters.put("parameter12", ": " + scode);
            parameters.put("parameter14", ":");
            parameters.put("parameter17", ":");

            parameters.put("parameter18", "");
            parameters.put("parameter19", "");
            parameters.put("parameter20", "");
            parameters.put("parameter21", "");
            parameters.put("parameter22", "");
            parameters.put("parameter23", "");
            parameters.put("parameter24", "");

            String cname = "", ad1 = "", ad2 = "", ad3 = "", area = "", mobile = "", phone = "", ctin = "", csname = "",
                    cscode = "";
            query = "select cname,add1,add2,add3,city,mobile,phone,gstno,sname,scode from cust where cid='" + cid + "'";
            r = util.doQuery(query);
            while (r.next()) {
                cname = r.getString(1);
                ad1 = r.getString(2);
                ad2 = r.getString(3);
                ad3 = r.getString(4);
                area = r.getString(5);
                mobile = r.getString(6);
                phone = r.getString(7);
                ctin = r.getString(8);
                csname = r.getString(9);
                cscode = r.getString(10);
            }

            if (phone.length() > 1) {
                mobile = mobile + ",  " + phone;
            }
            if (cname.length() > 1) {
                parameters.put("parameter18", "" + cname);
            }
            if (ad1.length() > 1) {
                parameters.put("parameter19", "" + ad1);
            }
            if (ad2.length() > 1) {
                parameters.put("parameter20", "" + ad2);
            }
            if (ad3.length() > 1) {
                parameters.put("parameter21", "" + ad3);
            }
            if (mobile.length() > 1) {
                parameters.put("parameter22", "Contact Nos: " + mobile);
            }
            if (csname.length() > 1) {
                parameters.put("parameter23", "State: " + csname + "   State Code:" + cscode);
            }
            if (area.length() > 1) {
                parameters.put("parameter16", ": " + area);
            }
            parameters.put("parameter31", "" + items);

            String quans2 = String.format("%." + hmany2 + "f", Double.parseDouble(quans));
            String[] splis3 = quans2.split("\\.");
            int whas3 = Integer.parseInt(splis3[1]);
            if (whas3 <= 0) {
                quans2 = splis3[0];
            }
            parameters.put("parameter32", "" + quans2);

            String sub2 = String.format("%." + hmany + "f", sub);
            parameters.put("parameter38", "" + sub2);
            parameters.put("parameter33", "" + sub2);

            parameters.put("parameter39", "");
            parameters.put("parameter40", "");
            parameters.put("parameter41", "");
            parameters.put("parameter42", "");
            parameters.put("parameter43", "");
            parameters.put("parameter44", "");

            if (dis > 0) {
                String dis2 = String.format("%." + hmany + "f", dis);
                parameters.put("parameter39", "Discount :");
                parameters.put("parameter40", "" + dis2);
            }
            if (addamt > 0) {
                String addamt2 = String.format("%." + hmany + "f", addamt);
                parameters.put("parameter41", "Add Amt :");
                parameters.put("parameter42", "" + addamt2);
            }

            String net2 = String.format("%." + hmany + "f", net);
            parameters.put("parameter47", "" + net2);
            String bal2 = String.format("%." + hmany + "f", bal);
            parameters.put("parameter48", "" + bal2);

            num = (int) net;
            num();
            parameters.put("parameter50", " Rupees " + rupe + " Only.");

            parameters.put("parameter51", "");
            parameters.put("parameter52", "");
            parameters.put("parameter53", "");
            parameters.put("parameter54", "");
            parameters.put("parameter55", "");

            if (sms1.equals(".")) {
            } else {
                parameters.put("parameter51", "" + sms1);
            }
            if (sms2.equals(".")) {
            } else {
                parameters.put("parameter52", "" + sms2);
            }
            if (sms3.equals(".")) {
            } else {
                parameters.put("parameter53", "" + sms3);
            }
            if (sms4.equals(".")) {
            } else {
                parameters.put("parameter54", "" + sms4);
            }

            ArrayList k = new ArrayList();
            int serial = 1;
            String iname, udes, hsn;
            double quan, price, amount, disp, disamt, tot, taxp, taxamt1, total;
            double namount = 0, ndisamt = 0, ntot = 0, ntax = 0, ntotal = 0;
            query = "select iname1,quan,price,amount,disp,disamt,sub,taxp,taxamt,total,udes,hsn from ereturn_items where billno='"
                    + billno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                SelRomJasper selRomJasper = new SelRomJasper();
                iname = r.getString(1);
                quan = r.getDouble(2);
                price = r.getDouble(3);
                amount = r.getDouble(4);
                disp = r.getDouble(5);
                disamt = r.getDouble(6);
                tot = r.getDouble(7);
                taxp = r.getDouble(8);
                taxamt1 = r.getDouble(9);
                total = r.getDouble(10);
                udes = r.getString(11);
                hsn = r.getString(12);

                namount = namount + amount;
                ndisamt = ndisamt + disamt;
                ntot = ntot + tot;
                ntax = ntax + taxamt1;
                ntotal = ntotal + total;

                String quan2 = String.format("%." + hmany2 + "f", quan);
                String[] spli3 = quan2.split("\\.");
                int wha3 = Integer.parseInt(spli3[1]);
                if (wha3 <= 0) {
                    quan2 = spli3[0];
                }

                String price2 = String.format("%." + hmany + "f", price);
                String amount2 = String.format("%." + hmany + "f", amount);
                selRomJasper.setField1("" + serial);
                selRomJasper.setField2(" " + iname);
                selRomJasper.setField3("" + quan2);
                selRomJasper.setField5("" + price2);
                selRomJasper.setField6("" + amount2);

                k.add(selRomJasper);
                serial = serial + 1;
            }
            disable_warnigs.disableAccessWarnings();
            JasperReport jasperReport;
            if (billformat.equals("A5")) {
                jasperReport = JasperReportCompiler.compileReport("/JasperFiles/A4_Estimate/A5_Estimate.jrxml");
            } else {
                jasperReport = JasperReportCompiler.compileReport("/JasperFiles/A4_Estimate/A4_Estimate.jrxml");
            }

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(k);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

            com.selrom.utils.ReportPreview.showPreview(jasperPrint, drive, folder);

        } catch (ClassNotFoundException | SQLException | JRException e) {
            System.out.println(e.getMessage());
        }

    }

    void num() {
        try {
            AmountInWords a1 = new AmountInWords();
            a1.input(num);
            rupe = a1.convertToWords(num);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    void ruppes(String rupee) {
        rupe = rupee;
    }

}
