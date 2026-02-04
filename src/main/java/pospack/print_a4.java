package pospack;

import com.selrom.db.DataUtil;
import com.selrom.db.disable_warnigs;
import com.selrom.utils.JasperReportCompiler;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileNotFoundException;
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
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

public class print_a4 {

    JasperViewer jasperViewer;
    int num = 0;
    String rupe = "";
    int hmany = 2, hmany1 = 3;
    DataUtil util;

    public void Report(DataUtil util, String billno, String drive, String folder, String billformat) {
        this.util = util;
        try {

            Map<String, Object> parameters = new HashMap<>();

            String add1 = "", add2 = "", add3 = "", add4 = "", add5 = "", sname = "", scode = "", letter = "", head = "", sms1 = "", sms2 = "", sms3 = "", sms4 = "", logoPath = "";
            String query = "select cname,add1,add2,add3,add4,state,scode,bhead,sms1,sms2,sms3,sms4,letter,hmany,logo_path from setting_bill";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
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
                logoPath = r.getString(15);
            }
            parameters.put("logo_path", logoPath);
            parameters.put("parameter1", "" + add1);
            parameters.put("parameter2", "");
            parameters.put("parameter3", "");
            parameters.put("parameter4", "");
            parameters.put("parameter5", "");
            parameters.put("parameter6", "");
            parameters.put("parameter60", "");

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
            parameters.put("parameter7", "" + head.trim());

            String date = "", quans = "", cid = "", tax = "", pby = "";
            double sub = 0, dis = 0, addamt = 0, net = 0, taxamt = 0;
            query = "select date_format(dat,'%d/%m/%Y'),quans,sub,disamt,addamt,net,cid,taxamt,tax,pby from sales where billno='" + billno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                date = r.getString(1);
                quans = r.getString(2);
                sub = r.getDouble(3);
                dis = r.getDouble(4);
                addamt = r.getDouble(5);
                net = r.getDouble(6);
                cid = r.getString(7);
                taxamt = r.getDouble(8);
                tax = r.getString(9);
                pby = r.getString(10);
            }
            parameters.put("parameter8", ": No");
            String billno1 = billno;
            if (!letter.equals(".")) {
                billno1 = letter + "" + billno;
            }
            parameters.put("parameter9", ": " + billno1);
            parameters.put("parameter10", ": " + date);
            parameters.put("parameter11", ": " + pby);
            parameters.put("parameter12", ": " + sname + " (" + scode + ")");

            parameters.put("parameter13", ":");
            parameters.put("parameter14", ":");
            parameters.put("parameter15", ":");
            parameters.put("parameter16", ":");
            parameters.put("parameter17", ":");

            parameters.put("parameter18", "");
            parameters.put("parameter19", "");
            parameters.put("parameter20", "");
            parameters.put("parameter21", "");
            parameters.put("parameter22", "");
            parameters.put("parameter23", "");
            parameters.put("parameter24", "");
            parameters.put("parameter25", "");
            parameters.put("parameter26", "");
            parameters.put("parameter27", "");
            parameters.put("parameter28", "");
            parameters.put("parameter29", "");
            parameters.put("parameter30", "");
            parameters.put("parameter31", "");
            String cname = "", ad1 = "", ad2 = "", ad3 = "", area = "", mobile = "", phone = "", ctin = "", csname = "", cscode = "";
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
            if (ctin.length() > 1) {
                parameters.put("parameter23", "GSTIN: " + ctin);
            }
            if (csname.length() > 1) {
                parameters.put("parameter24", "State: " + csname + "   State Code:" + cscode);
            }
            if (area.length() > 1) {
                parameters.put("parameter16", ": " + area);
            }

            //delivery
            if (cname.length() > 1) {
                parameters.put("parameter25", "" + cname);
            }
            if (ad1.length() > 1) {
                parameters.put("parameter26", "" + ad1);
            }
            if (ad2.length() > 1) {
                parameters.put("parameter27", "" + ad2);
            }
            if (ad3.length() > 1) {
                parameters.put("parameter28", "" + ad3);
            }
            if (mobile.length() > 1) {
                parameters.put("parameter29", "Contact Nos: " + mobile);
            }
            if (ctin.length() > 1) {
                parameters.put("parameter30", "GSTIN: " + ctin);
            }
            if (csname.length() > 1) {
                parameters.put("parameter31", "State: " + csname + "   State Code:" + cscode);
            }

            //delivery address ends
            int quans2 = (int) Double.parseDouble(quans);
            parameters.put("parameter32", "" + quans2);

            String sub2 = String.format("%." + hmany + "f", sub);
            parameters.put("parameter38", "" + sub2);

            parameters.put("parameter39", "");
            parameters.put("parameter40", "");
            parameters.put("parameter41", "");
            parameters.put("parameter42", "");
            parameters.put("parameter43", "");
            parameters.put("parameter44", "");
            parameters.put("parameter45", "");
            parameters.put("parameter46", "");

            if (dis > 0) {
                String dis2 = String.format("%." + hmany + "f", dis);
                parameters.put("parameter39", "Discount :");
                parameters.put("parameter40", "" + dis2);
            }

            String taxamt2 = String.format("%." + hmany + "f", taxamt);
            if (tax.equals("Local")) {
                double cgst = taxamt / 2;
                String cgst2 = String.format("%." + hmany + "f", cgst);
                parameters.put("parameter41", "CGST :");
                parameters.put("parameter42", "" + cgst2);
                parameters.put("parameter43", "SGST :");
                parameters.put("parameter44", "" + cgst2);
            } else {
                parameters.put("parameter41", "IGST :");
                parameters.put("parameter42", "" + taxamt2);
            }

            String net2 = String.format("%." + hmany + "f", net);
            parameters.put("parameter47", "" + net2);

            parameters.put("parameter48", ": No");
            parameters.put("parameter49", ": " + taxamt2);

            num = (int) net;
            num();
            parameters.put("parameter50", " Rupees " + rupe + " Only.");

            parameters.put("parameter51", "");
            parameters.put("parameter52", "");
            parameters.put("parameter53", "");
            parameters.put("parameter54", "");
            parameters.put("parameter55", "");
            parameters.put("parameter59", "");

            if (sms1.equals(".")) {
            } else {
                parameters.put("parameter53", "" + sms1);
            }
            if (sms2.equals(".")) {
            } else {
                parameters.put("parameter54", "" + sms2);
            }
            if (sms2.equals(".")) {
            } else {
                parameters.put("parameter55", "" + sms3);
            }
            if (sms2.equals(".")) {
            } else {
                parameters.put("parameter59", "" + sms4);
            }

            String ano = "", ifsc = "", bname = "", aname = "", branch = "";
            query = "select ano,aname,bank,branch,ifsc from bank";
            r = util.doQuery(query);
            while (r.next()) {
                ano = r.getString(1);
                aname = r.getString(2);
                bname = r.getString(3);
                branch = r.getString(4);
                ifsc = r.getString(5);
            }
            if (ano.equals(".")) {
            } else {
                parameters.put("parameter51", " A\\C NO: " + ano + ", NAME: " + aname);
            }
            if (ifsc.equals(".")) {
            } else {
                parameters.put("parameter52", " BANK:" + bname + ", IFSC: " + ifsc);
            }

            ArrayList k = new ArrayList();
            int serial = 1;
            String iname, udes, hsn;
            double quan, price, amount, disp, disamt, tot, taxp, taxamt1, total;
            double namount = 0, ndisamt = 0, ntot = 0, ntax = 0, ntotal = 0;
            query = "select iname1,quan,price,amount,disp,disamt,sub,taxp,taxamt,total,udes,hsn from sales_items where billno='" + billno + "'";
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

                selRomJasper.setField1("" + serial);
                selRomJasper.setField2(" " + iname);
                selRomJasper.setField3("" + hsn);
                selRomJasper.setField5("" + udes);

                String quan2 = String.format("%." + hmany1 + "f", quan);
                String[] split3 = quan2.split("\\.");
                int what3 = Integer.parseInt(split3[1]);
                if (what3 <= 0) {
                    quan2 = split3[0];
                }

                selRomJasper.setField6("" + quan2);

                String price2 = String.format("%." + hmany + "f", price);
                String amount2 = String.format("%." + hmany + "f", amount);
                String disp2 = String.format("%." + hmany + "f", disp);
                String disamt2 = String.format("%." + hmany + "f", disamt);
                String tot2 = String.format("%." + hmany + "f", tot);
                String taxp3 = String.format("%." + hmany + "f", taxp);
                String taxamt3 = String.format("%." + hmany + "f", taxamt1);
                String total2 = String.format("%." + hmany + "f", total);

                selRomJasper.setField6("" + quan2);
                selRomJasper.setField7("" + price2);
                selRomJasper.setField8("" + amount2);
                selRomJasper.setField9("" + disamt2);
                selRomJasper.setField10("" + tot2);
                selRomJasper.setField15("" + total2);

                double sgst = taxamt1 / 2;
                double sgst_per = taxp / 2;
                String sgst_per2 = String.format("%." + hmany + "f", sgst_per);
                String sgst2 = String.format("%." + hmany + "f", sgst);

                selRomJasper.setField11("" + sgst_per2);
                selRomJasper.setField12("" + sgst2);
                selRomJasper.setField13("" + taxp3);
                selRomJasper.setField14("" + taxamt3);

                k.add(selRomJasper);
                serial = serial + 1;
            }
            double ncgst = ntax / 2;

            String namount2 = String.format("%." + hmany + "f", namount);
            String ndisamt2 = String.format("%." + hmany + "f", ndisamt);
            String ntot2 = String.format("%." + hmany + "f", ntot);
            String ncgst2 = String.format("%." + hmany + "f", ncgst);
            String ntotal2 = String.format("%." + hmany + "f", ntotal);
            parameters.put("parameter33", "" + namount2);
            parameters.put("parameter34", "" + ntot2);
            parameters.put("parameter58", "" + ndisamt2);
            parameters.put("parameter35", "" + ncgst2);
            parameters.put("parameter36", "" + ncgst2);
            parameters.put("parameter37", "" + ntotal2);

            disable_warnigs.disableAccessWarnings();
            JasperReport jasperReport;

            // Consolidate both Local and IGST to use the standard Sales_A4 template
            // Removed reference to missing A4_Invoice_IGST.jrxml
            jasperReport = JasperReportCompiler.compileReport("/JasperFiles/A4/Sales_A4.jrxml");

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
