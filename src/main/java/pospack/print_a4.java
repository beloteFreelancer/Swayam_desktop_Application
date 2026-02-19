package pospack;

import com.selrom.db.DataUtil;
import com.selrom.db.disable_warnigs;
import com.selrom.utils.JasperReportCompiler;

import menupack.AmountInWords;
import menupack.SelRomJasper;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class print_a4 {

    int num = 0;
    String rupe = "";
    int hmany = 2, hmany1 = 3;
    DataUtil util;

    public void Report(DataUtil util, String billno, String drive, String folder, String billformat) {
        this.util = util;

        try {
            Map<String, Object> parameters = new HashMap<>();

            // ==================== SHOP SETTINGS ====================
            String add1 = "", add2 = "", add3 = "", add4 = "", add5 = "", sname = "", scode = "", letter = "",
                    head = "", sms1 = "", sms2 = "", sms3 = "", sms4 = "", logoPath = "", gstin = "";

            ResultSet r = util.doQuery(
                    "select cname,add1,add2,add3,add4,state,scode,bhead,sms1,sms2,sms3,sms4,letter,hmany,logo_path from setting_bill");
            while (r.next()) {
                add1 = r.getString(1);
                add2 = r.getString(2);
                add3 = r.getString(3);
                add4 = r.getString(4);
                gstin = r.getString(5);

                
              
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
            parameters.put("parameter1", add1);
            parameters.put("parameter2", add2.length() > 1 ? add2 : "");
            parameters.put("parameter3", add3.length() > 1 ? add3 : "");
            parameters.put("parameter4", add4.length() > 1 ? add4 : "");
           
            parameters.put("parameter57", "For " + add1.trim().toUpperCase());
            parameters.put("parameter7", head.trim());

            // Set new company info parameters for Sales_A4.jrxml header
            parameters.put("CompanyName", add1);
            parameters.put("CompanyAddress", (add2 + (add3.length() > 1 ? ", " + add3 : "") + (add4.length() > 1 ? ", " + add4 : "") + (add5.length() > 1 ? ", " + add5 : "")).replaceAll(", $", ""));
            parameters.put("CompanyGST", gstin); // Ensure GST number is set from add4
          
            

            // ==================== SALES HEADER ====================
            String date = "", quans = "", cid = "", tax = "", pby = "";
            double sub = 0, dis = 0, net = 0, taxamt = 0;

            r = util.doQuery(
                    "select date_format(dat,'%d/%m/%Y'),quans,sub,disamt,net,cid,taxamt,tax,pby from sales where billno='"
                            + billno + "'");
            while (r.next()) {
                date = r.getString(1);
                quans = r.getString(2);
                sub = r.getDouble(3);
                dis = r.getDouble(4);
                net = r.getDouble(5);
                cid = r.getString(6);
                taxamt = r.getDouble(7);
                tax = r.getString(8);
                pby = r.getString(9);
            }

            String billno1 = letter.equals(".") ? billno : letter + billno;

            parameters.put("parameter8", ": No");
            parameters.put("parameter9", ": " + billno1);
            parameters.put("parameter10", ": " + date);
            parameters.put("parameter11", ": " + pby);
            parameters.put("parameter12", ": " + sname + " (" + scode + ")");

            // ==================== CUSTOMER ====================
            String cname = "", ad1 = "", ad2 = "", ad3 = "", area = "", mobile = "", phone = "", ctin = "", csname = "",
                    cscode = "";
            r = util.doQuery("select cname,add1,add2,add3,city,mobile,phone,gstno,sname,scode from cust where cid='"
                    + cid + "'");
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

            if (phone.length() > 1)
                mobile += ",  " + phone;

            parameters.put("parameter18", cname.length() > 1 ? cname : "");
            parameters.put("parameter19", ad1.length() > 1 ? ad1 : "");
            parameters.put("parameter20", ad2.length() > 1 ? ad2 : "");
            parameters.put("parameter21", ad3.length() > 1 ? ad3 : "");
            parameters.put("parameter22", mobile.length() > 1 ? "Contact Nos: " + mobile : "");
            parameters.put("parameter23", ctin.length() > 1 ? "GSTIN: " + ctin : "");
            parameters.put("parameter24", csname.length() > 1 ? "State: " + csname + "   State Code:" + cscode : "");
            parameters.put("parameter16", area.length() > 1 ? ": " + area : "");

            // Pass new Customer Details parameters for Sales_A4.jrxml
            parameters.put("CustomerName", cname);
            parameters.put("CustomerPhone", mobile);
            parameters.put("CustomerAddress", (ad1 + " " + ad2 + " " + ad3 + " " + area).trim());
            parameters.put("CustomerGSTIN", ctin);
            parameters.put("InvoiceNo", billno1);
            parameters.put("InvoiceDate", date);
            parameters.put("SalesMan", pby);

            // Delivery address (same as customer)
            parameters.put("parameter25", parameters.get("parameter18"));
            parameters.put("parameter26", parameters.get("parameter19"));
            parameters.put("parameter27", parameters.get("parameter20"));
            parameters.put("parameter28", parameters.get("parameter21"));
            parameters.put("parameter29", parameters.get("parameter22"));
            parameters.put("parameter30", parameters.get("parameter23"));
            parameters.put("parameter31", parameters.get("parameter24"));

            parameters.put("parameter32", (int) Double.parseDouble(quans));

            // ==================== AMOUNTS ====================
            String sub2 = String.format("%." + hmany + "f", sub);
            parameters.put("parameter38", sub2);

            if (dis > 0) {
                parameters.put("parameter39", "Discount :");
                parameters.put("parameter40", String.format("%." + hmany + "f", dis));
            }

            String taxamt2 = String.format("%." + hmany + "f", taxamt);
            if (tax.equals("Local")) {
                double cgst = taxamt / 2;
                String cgst2 = String.format("%." + hmany + "f", cgst);
                parameters.put("parameter41", "CGST :");
                parameters.put("parameter42", cgst2);
                parameters.put("parameter43", "SGST :");
                parameters.put("parameter44", cgst2);
            } else {
                parameters.put("parameter41", "IGST :");
                parameters.put("parameter42", taxamt2);
            }

            String net2 = String.format("%." + hmany + "f", net);
            parameters.put("parameter47", net2);
            parameters.put("parameter48", ": No");
            parameters.put("parameter49", ": " + taxamt2);

            num = (int) net;
            num();
            parameters.put("parameter50", " Rupees " + rupe + " Only.");

            // SMS lines
            parameters.put("parameter53", !sms1.equals(".") ? sms1 : "");
            parameters.put("parameter54", !sms2.equals(".") ? sms2 : "");
            parameters.put("parameter55", !sms3.equals(".") ? sms3 : "");
            parameters.put("parameter59", !sms4.equals(".") ? sms4 : "");

            // Bank details
            String ano = "", aname = "", bname = "", ifsc = "";
            r = util.doQuery("select ano,aname,bank,branch,ifsc from bank");
            while (r.next()) {
                ano = r.getString(1);
                aname = r.getString(2);
                bname = r.getString(3);
                ifsc = r.getString(5);
            }
            parameters.put("parameter51", !ano.equals(".") ? " A\\C NO: " + ano + ", NAME: " + aname : "");
            parameters.put("parameter52", !ifsc.equals(".") ? " BANK:" + bname + ", IFSC: " + ifsc : "");

            // ==================== ITEMS ====================
            ArrayList<SelRomJasper> k = new ArrayList<>();
            int serial = 1;

            double namount = 0, ndisamt = 0, ntot = 0, ntax = 0, ntotal = 0;

            r = util.doQuery(
                    "select iname1,quan,price,amount,disp,disamt,sub,taxp,taxamt,total,udes,hsn from sales_items where billno='"
                            + billno + "'");
            while (r.next()) {
                SelRomJasper item = new SelRomJasper();

                item.setSno(String.valueOf(serial));
                item.setProduct_name(r.getString(1));
                item.setSize(r.getString(12));

                String quan2 = String.format("%." + hmany1 + "f", r.getDouble(2));
                String[] parts = quan2.split("\\.");
                if (parts.length > 1 && Integer.parseInt(parts[1]) <= 0) {
                    quan2 = parts[0];
                }
                item.setQty(quan2);
                item.setMrp(String.format("%." + hmany + "f", r.getDouble(3)));
                item.setDisc_amt(String.format("%." + hmany + "f", r.getDouble(6)));
                item.setRate(String.format("%." + hmany + "f", r.getDouble(7)));
                item.setAmount(String.format("%." + hmany + "f", r.getDouble(4)));

                k.add(item);
                serial++;

                namount += r.getDouble(4);
                ndisamt += r.getDouble(6);
                ntot += r.getDouble(7);
                ntax += r.getDouble(9);
                ntotal += r.getDouble(10);
            }

            double ncgst = ntax / 2;

            String namount2 = String.format("%." + hmany + "f", namount);
            String ndisamt2 = String.format("%." + hmany + "f", ndisamt);
            String ntot2 = String.format("%." + hmany + "f", ntot);
            String ncgst2 = String.format("%." + hmany + "f", ncgst);
            String ntotal2 = String.format("%." + hmany + "f", ntotal);

            parameters.put("parameter33", namount2);
            parameters.put("parameter34", ntot2);
            parameters.put("parameter58", ndisamt2);
            parameters.put("parameter35", ncgst2);
            parameters.put("parameter36", ncgst2);
            parameters.put("parameter37", ntotal2);

            // Set parameters for summary section in Sales_A4.jrxml, always use Double and never null
            parameters.put("AmountInWords", "Rupees " + (rupe != null ? rupe : "Zero") + " Only");
            parameters.put("TotalAmountBeforeTax", Double.valueOf(sub));
            if (tax.equals("Local")) {
                double cgst = taxamt / 2;
                parameters.put("CGSTAmount", Double.valueOf(cgst));
                parameters.put("SGSTAmount", Double.valueOf(cgst));
                parameters.put("IGSTAmount", Double.valueOf(0.0));
            } else {
                parameters.put("CGSTAmount", Double.valueOf(0.0));
                parameters.put("SGSTAmount", Double.valueOf(0.0));
                parameters.put("IGSTAmount", Double.valueOf(taxamt));
            }
            parameters.put("TaxAmountGST", Double.valueOf(taxamt));
            parameters.put("GrandTotal", Double.valueOf(net));

            // ALSO pass formatted string versions to avoid JR formatting/locale issues
            parameters.put("TotalAmountBeforeTaxStr", String.format("%.2f", sub));
            if (tax.equals("Local")) {
                double cgst = taxamt / 2;
                parameters.put("CGSTAmountStr", String.format("%.2f", cgst));
                parameters.put("SGSTAmountStr", String.format("%.2f", cgst));
                parameters.put("IGSTAmountStr", String.format("%.2f", 0.0));
            } else {
                parameters.put("CGSTAmountStr", String.format("%.2f", 0.0));
                parameters.put("SGSTAmountStr", String.format("%.2f", 0.0));
                parameters.put("IGSTAmountStr", String.format("%.2f", taxamt));
            }
            parameters.put("TaxAmountGSTStr", String.format("%.2f", taxamt));
            parameters.put("GrandTotalStr", String.format("%.2f", net));

            // DEBUG: Print all parameters before report generation
            System.out.println("PARAMETERS FOR JASPER:");
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }

            // ==================== REPORT ====================
            disable_warnigs.disableAccessWarnings();

            java.io.InputStream stream = getClass().getResourceAsStream("/JasperFiles/A4/Sales_A4.jrxml");
            if (stream == null) {
                System.out.println("Sales_A4.jrxml not found!");
                return;
            }

            JasperReport jasperReport = JasperReportCompiler.compileReport("/JasperFiles/A4/Sales_A4.jrxml");
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(k);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
            com.selrom.utils.ReportPreview.showPreview(jasperPrint, drive, folder);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
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
}