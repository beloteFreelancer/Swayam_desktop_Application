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
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class print_a4_half_II {

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
            parameters.put("logo_path", logoPath);            parameters.put("parameter1", "" + add1);
            parameters.put("parameter2", "");
            parameters.put("parameter3", "");
            parameters.put("parameter4", "");
            parameters.put("parameter5", "");
            parameters.put("parameter6", "");
            parameters.put("parameter34", "");

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
            parameters.put("parameter34", "For " + add1.trim().toUpperCase());
            parameters.put("parameter7", "" + head.trim());

            String date = "", cid = "", tax = "", salesman = "", ttype = "";
            double sub = 0, dis = 0, addamt = 0, net = 0, taxamt = 0, quans = 0;
            query = "select date_format(dat,'%d/%m/%Y'),quans,sub,disamt,addamt,net,cid,taxamt,tax,tax_type from sales where billno='" + billno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                date = r.getString(1);
                quans = r.getDouble(2);
                sub = r.getDouble(3);
                dis = r.getDouble(4);
                addamt = r.getDouble(5);
                net = r.getDouble(6);
                cid = r.getString(7);
                taxamt = r.getDouble(8);
                tax = r.getString(9);
                ttype = r.getString(10);
            }
            String billno1 = billno;
            if (!letter.equals(".")) {
                billno1 = letter + "" + billno;
            }
            parameters.put("parameter8", ": " + billno1);
            parameters.put("parameter9", ": " + date);

            parameters.put("parameter12", "");
            parameters.put("parameter13", "");
            parameters.put("parameter14", "");
            parameters.put("parameter15", "");
            parameters.put("parameter16", "");
            parameters.put("parameter17", "");

            String cname = "", ad1 = "", ad2 = "", ad3 = "", area = "", mobile = "", phone = "", ctin = "", csname = "", cscode = "", route = "";
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

            // System.out.println("Customer Id: "+cid+"\nName: "+cname+"\nAddress: "+ad1+"\n"+ad2+"\n"+ad3+"\n PH: "+mobile);
            if (phone.length() > 1) {
                mobile = mobile + ",  " + phone;
            }
            if (cname.length() > 1) {
                parameters.put("parameter12", "" + cname);
            }
            if (ad1.length() > 1) {
                parameters.put("parameter13", "" + ad1);
            }
            if (ad2.length() > 1) {
                parameters.put("parameter14", "" + ad2);
            }
            if (ad3.length() > 1) {
                parameters.put("parameter15", "" + ad3 + "  PH: " + mobile);
            }
            if (ctin.length() > 1) {
                parameters.put("parameter17", "GSTIN: " + ctin + "  State: " + csname + "(" + cscode + ")");
            }
            parameters.put("parameter11", ": " + route);
            parameters.put("parameter10", ": " + salesman);

            double points = 0;
            query = "select points from cust_points where cid='" + cid + "'";
            r = util.doQuery(query);
            while (r.next()) {
                points = r.getDouble(1);
            }
            parameters.put("parameter16", "Total Points: " + points);

            String quans2 = String.format("%." + hmany1 + "f", quans);
            String[] spli3 = quans2.split("\\.");
            int wha3 = Integer.parseInt(spli3[1]);
            if (wha3 <= 0) {
                quans2 = spli3[0];
            }
            parameters.put("parameter18", "" + quans2);

            String sub2 = String.format("%." + hmany + "f", sub);
            parameters.put("parameter20", "" + sub2);
            parameters.put("parameter24", "" + sub2);

            parameters.put("parameter25", "");
            parameters.put("parameter26", "");
            parameters.put("parameter27", "");
            parameters.put("parameter28", "");
            parameters.put("parameter29", "");
            parameters.put("parameter30", "");
            parameters.put("parameter31", "");
            parameters.put("parameter32", "");

            if (dis > 0) {
                String dis2 = String.format("%." + hmany + "f", dis);
                parameters.put("parameter25", "Discount :");
                parameters.put("parameter26", "" + dis2);
            }

            String taxamt2 = String.format("%." + hmany + "f", taxamt);
            if (tax.equals("Local")) {
                double cgst = taxamt / 2;
                String cgst2 = String.format("%." + hmany + "f", cgst);
                parameters.put("parameter27", "CGST :");
                parameters.put("parameter28", "" + cgst2);
                parameters.put("parameter29", "SGST :");
                parameters.put("parameter30", "" + cgst2);
            } else {
                parameters.put("parameter27", "IGST :");
                parameters.put("parameter28", "" + taxamt2);
            }

            if (addamt > 0) {
                String addamt2 = String.format("%." + hmany + "f", addamt);
                parameters.put("parameter31", "Add Amount :");
                parameters.put("parameter32", "" + addamt2);
            }

            String net2 = String.format("%." + hmany + "f", net);
            parameters.put("parameter33", "" + net2);

            num = (int) net;
            num();
            parameters.put("parameter35", " Rupees " + rupe + " Only.");

            parameters.put("parameter44", "");
            parameters.put("parameter45", "");
            parameters.put("parameter46", "");
            parameters.put("parameter47", "");

            if (sms1.equals(".")) {
            } else {
                parameters.put("parameter44", "" + sms1);
            }
            if (sms2.equals(".")) {
            } else {
                parameters.put("parameter45", "" + sms2);
            }
            if (sms2.equals(".")) {
            } else {
                parameters.put("parameter46", "" + sms3);
            }
            if (sms2.equals(".")) {
            } else {
                parameters.put("parameter47", "" + sms4);
            }

            String language = "english";

            ArrayList k = new ArrayList();
            int serial = 1;
            String iname, udes, hsn, ename;
            double quan, price, amount, disp, disamt, tot, taxp, taxamt1, total, free, mrp, samt;
            double namount = 0, ndisamt = 0, ntot = 0, ntax = 0, ntotal = 0, nfree = 0, new_net = 0, nsamt = 0;

            query = "select iname1,quan,price,amount,disp,disamt,sub,taxp,taxamt,total,udes,hsn,iname,mrp from sales_items where billno='" + billno + "'";
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

                ename = r.getString(13);
                mrp = r.getDouble(14);

                namount = namount + amount;
                ndisamt = ndisamt + disamt;
                ntot = ntot + tot;
                ntax = ntax + taxamt1;
                ntotal = ntotal + total;
                nfree = nfree;
                nsamt = nsamt + disamt;
                if (language.equalsIgnoreCase("english")) {
                    iname = ename;
                }
                selRomJasper.setField1("" + serial);
                selRomJasper.setField2(" " + iname);
                selRomJasper.setField3("" + hsn);
                //selRomJasper.setField5(""+udes);

                String quan2 = String.format("%." + hmany1 + "f", quan);
                String[] spli4 = quan2.split("\\.");
                int wh3 = Integer.parseInt(spli4[1]);
                if (wh3 <= 0) {
                    quan2 = spli4[0];
                }
                selRomJasper.setField5("" + quan2);

                selRomJasper.setField6("");

                String price2 = String.format("%." + hmany + "f", price);
                String amount2 = String.format("%." + hmany + "f", amount);
                String disp2 = String.format("%." + hmany + "f", disp);
                String disamt2 = String.format("%." + hmany + "f", disamt);
                String tot2 = String.format("%." + hmany + "f", tot);
                String taxp3 = String.format("%." + hmany + "f", taxp);
                String taxamt3 = String.format("%." + hmany + "f", taxamt1);
                String total2 = String.format("%." + hmany + "f", total);
                String mrp2 = String.format("%." + hmany + "f", mrp);

                selRomJasper.setField7("" + price2);
                selRomJasper.setField8("" + amount2);
                selRomJasper.setField11("" + total2);
                selRomJasper.setField16("" + mrp2);

                double sgst = taxamt1 / 2;
                double sgst_per = taxp / 2;
                //String sgst_per2=String.format("%."+hmany+"f", sgst_per);
                String sgst2 = String.format("%." + hmany + "f", sgst);

                selRomJasper.setField9("" + sgst_per);
                selRomJasper.setField10("" + sgst2);
                selRomJasper.setField20(sgst_per + "%");

                double new_total = total;
                new_net = new_net + new_total;
                double nrate = new_total / quan;

                String nrate2 = String.format("%." + hmany + "f", nrate);
                String new_total2 = String.format("%." + hmany + "f", new_total);
                selRomJasper.setField17("" + nrate2);
                selRomJasper.setField18("" + new_total2);
                selRomJasper.setField19("" + disp + "%");
                k.add(selRomJasper);
                serial = serial + 1;
            }
            serial = serial - 1;
            if (serial < 10) {
                int diff = 10 - serial;
                for (int i = 0; i < diff; i++) {
                    SelRomJasper selRomJasper = new SelRomJasper();
                    selRomJasper.setField1("");
                    selRomJasper.setField2("");
                    selRomJasper.setField3("");
                    selRomJasper.setField4("");
                    selRomJasper.setField5("");
                    selRomJasper.setField6("");
                    selRomJasper.setField7("");
                    selRomJasper.setField8("");
                    selRomJasper.setField9("");
                    selRomJasper.setField10("");
                    selRomJasper.setField11("");
                    selRomJasper.setField16("");
                    selRomJasper.setField17("");
                    selRomJasper.setField18("");
                    selRomJasper.setField19("");
                    selRomJasper.setField20("");
                    k.add(selRomJasper);
                }
            }//lessthan 5 records
            else if (serial > 10) {
                int diff = 29 - serial;
                for (int i = 0; i < diff; i++) {
                    SelRomJasper selRomJasper = new SelRomJasper();
                    selRomJasper.setField1("");
                    selRomJasper.setField2("");
                    selRomJasper.setField3("");
                    selRomJasper.setField4("");
                    selRomJasper.setField5("");
                    selRomJasper.setField6("");
                    selRomJasper.setField7("");
                    selRomJasper.setField8("");
                    selRomJasper.setField9("");
                    selRomJasper.setField10("");
                    selRomJasper.setField11("");
                    selRomJasper.setField16("");
                    selRomJasper.setField17("");
                    selRomJasper.setField18("");
                    selRomJasper.setField19("");
                    selRomJasper.setField20("");
                    k.add(selRomJasper);
                }
            }

            double ncgst = ntax / 2;
            String namount2 = String.format("%." + hmany + "f", namount);
            String ndisamt2 = String.format("%." + hmany + "f", ndisamt);
            String ntot2 = String.format("%." + hmany + "f", ntot);
            String ncgst2 = String.format("%." + hmany + "f", ncgst);
            String ntotal2 = String.format("%." + hmany + "f", ntotal);
            String new_net2 = String.format("%." + hmany + "f", new_net);
            String nsamt2 = String.format("%." + hmany + "f", nsamt);
            parameters.put("parameter20", "" + namount2);
            parameters.put("parameter21", "" + ncgst2);
            parameters.put("parameter22", "" + ncgst2);
            parameters.put("parameter23", "" + ntotal2);
            parameters.put("parameter75", "" + new_net2);
            parameters.put("parameter76", "" + nsamt2);

            String nfree2 = String.format("%." + hmany1 + "f", nfree);
            String[] spli5 = nfree2.split("\\.");
            int wh5 = Integer.parseInt(spli5[1]);
            if (wh5 <= 0) {
                nfree2 = spli5[0];
            }
            parameters.put("parameter19", "" + nfree2);

            parameters.put("parameter36", "");
            parameters.put("parameter37", "");
            parameters.put("parameter38", "");
            parameters.put("parameter39", "");
            parameters.put("parameter40", "");
            parameters.put("parameter41", "");
            parameters.put("parameter42", "");
            parameters.put("parameter43", "");

            double tax5 = 0, tax12 = 0, tax18 = 0, tax28 = 0;
            query = "select sum(taxamt) from sales_items where billno='" + billno + "' and taxp='5' ";
            r = util.doQuery(query);
            while (r.next()) {
                tax5 = r.getDouble(1);
            }
            query = "select sum(taxamt) from sales_items where billno='" + billno + "' and taxp='12' ";
            r = util.doQuery(query);
            while (r.next()) {
                tax12 = r.getDouble(1);
            }
            query = "select sum(taxamt) from sales_items where billno='" + billno + "' and taxp='18' ";
            r = util.doQuery(query);
            while (r.next()) {
                tax18 = r.getDouble(1);
            }
            query = "select sum(taxamt) from sales_items where billno='" + billno + "' and taxp='28' ";
            r = util.doQuery(query);
            while (r.next()) {
                tax28 = r.getDouble(1);
            }
            String tax52 = String.format("%." + hmany + "f", tax5);
            String tax122 = String.format("%." + hmany + "f", tax12);
            String tax182 = String.format("%." + hmany + "f", tax18);
            String tax282 = String.format("%." + hmany + "f", tax28);

            if (tax5 > 0) {
                parameters.put("parameter40", tax52);
                double sgst = tax5 / 2;
                String sgst2 = String.format("%." + hmany + "f", sgst);
                parameters.put("parameter36", sgst2);
            }
            if (tax12 > 0) {
                parameters.put("parameter41", tax122);
                double sgst = tax12 / 2;
                String sgst2 = String.format("%." + hmany + "f", sgst);
                parameters.put("parameter37", sgst2);
            }
            if (tax18 > 0) {
                parameters.put("parameter42", tax182);
                double sgst = tax18 / 2;
                String sgst2 = String.format("%." + hmany + "f", sgst);
                parameters.put("parameter38", sgst2);
            }
            if (tax28 > 0) {
                parameters.put("parameter43", tax282);
                double sgst = tax28 / 2;
                String sgst2 = String.format("%." + hmany + "f", sgst);
                parameters.put("parameter39", sgst2);
            }
            double old = 0, bal = 0;
            query = "select sum(tot-paid) from cust_bal where billno < '" + billno + "' and cid='" + cid + "'";
            r = util.doQuery(query);
            while (r.next()) {
                old = r.getDouble(1);
            }
            bal = net + old;
            String old2 = String.format("%." + hmany + "f", old);
            String bal2 = String.format("%." + hmany + "f", bal);

            parameters.put("parameter48", old2);
            parameters.put("parameter49", net2);
            parameters.put("parameter50", bal2);

            disable_warnigs.disableAccessWarnings();
            String reportPath = "";
            if (billformat.equalsIgnoreCase("A4-Half_II")) {
                reportPath = "/JasperFiles/A4_Half/A4Half-III.jrxml";
            } else if (billformat.equalsIgnoreCase("A4-Half_IV")) {
                reportPath = "/JasperFiles/A4_Half/A4Half-IV.jrxml";
            }

            JasperReport jasperReport = JasperReportCompiler.compileReport(reportPath);

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
