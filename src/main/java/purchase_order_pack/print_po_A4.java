package purchase_order_pack;

import com.selrom.db.DataUtil;
import com.selrom.db.disable_warnigs;
import com.selrom.utils.JasperReportCompiler;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import menupack.AmountInWords;
import menupack.SelRomJasper;
import menupack.menu_form;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class print_po_A4 {

    JasperViewer jasperViewer;
    int num = 0;
    String rupe = "", hmany = "2";
    ;
DataUtil util;

    public void Report(DataUtil util, String billno) {
        this.util = util;
        try {

            Map<String, Object> parameters = new HashMap<>();

            String add1 = "", add2 = "", add3 = "", add4 = "", add5 = "", sname = "", scode = "", logoPath = "";
            String query = "select cname,add1,add2,add3,add4,state,scode,hmany,logo_path from setting_bill";
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
                hmany = r.getString(8);
            }
            parameters.put("logo_path", logoPath);            parameters.put("parameter1", "" + add1);
            parameters.put("parameter2", "");
            parameters.put("parameter3", "");
            parameters.put("parameter4", "");
            parameters.put("parameter5", "");
            parameters.put("parameter6", "");
            parameters.put("parameter7", "");

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
            parameters.put("parameter6", "State: " + sname + "   State Code: " + scode);
            parameters.put("parameter24", "For " + add1.trim().toUpperCase());

            String date = "", quans = "", cname = "", sms1 = "", sms2 = "", sms3 = "", sms4 = "";
            double sub = 0, net = 0, taxamt = 0;
            query = "select date_format(dat,'%d/%m/%Y'),quans,sub,taxamt,net,cname,term1,term2,term3,term4 from po_entry where grn='" + billno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                date = r.getString(1);
                quans = r.getString(2);
                sub = r.getDouble(3);
                taxamt = r.getDouble(4);
                net = r.getDouble(5);
                cname = r.getString(6);

                sms1 = r.getString(7);
                sms2 = r.getString(8);
                sms3 = r.getString(9);
                sms4 = r.getString(10);
            }

            parameters.put("parameter14", "" + billno);
            parameters.put("parameter15", "" + date);

            parameters.put("parameter8", "");
            parameters.put("parameter9", "");
            parameters.put("parameter10", "");
            parameters.put("parameter11", "");
            parameters.put("parameter12", "");
            parameters.put("parameter13", "");

            String ad1 = "", ad2 = "", ad3 = "", area = "", mobile = "", phone = "", ctin = "", csname = "", cscode = "";
            query = "select cname,add1,add2,add3,city,mobile,phone,gstno,sname,scode from vendor where cname='" + cname + "'";
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
                parameters.put("parameter8", "" + cname);
            }
            if (ad1.length() > 1) {
                parameters.put("parameter9", "" + ad1);
            }
            if (ad2.length() > 1) {
                parameters.put("parameter10", "" + ad2);
            }
            if (ad3.length() > 1) {
                parameters.put("parameter11", "" + ad3 + "  Contact Nos: " + mobile);
            }
            if (ctin.length() > 1) {
                parameters.put("parameter12", "GSTIN: " + ctin);
            }
            if (csname.length() > 1) {
                parameters.put("parameter13", "State: " + csname + "   State Code:" + cscode);
            }

            int quans2 = (int) Double.parseDouble(quans);
            parameters.put("parameter16", "" + quans2);

            String sub2 = String.format("%." + hmany + "f", sub);
            parameters.put("parameter17", "" + sub2);

            parameters.put("parameter21", "");
            parameters.put("parameter22", "");

            if (taxamt > 0) {
                String taxamt2 = String.format("%." + hmany + "f", taxamt);
                parameters.put("parameter21", "Tax Amount :");
                parameters.put("parameter22", "" + taxamt2);
                parameters.put("parameter18", "" + taxamt2);
            }

            String net2 = String.format("%." + hmany + "f", net);
            parameters.put("parameter19", "" + net2);

            num = (int) net;
            num();
            parameters.put("parameter25", " Rupees " + rupe + " Only.");

            parameters.put("parameter26", "");
            parameters.put("parameter27", "");
            parameters.put("parameter28", "");
            parameters.put("parameter29", "");
            if (sms1.equals(".")) {
            } else {
                parameters.put("parameter26", "" + sms1);
            }
            if (sms2.equals(".")) {
            } else {
                parameters.put("parameter27", "" + sms2);
            }
            if (sms2.equals(".")) {
            } else {
                parameters.put("parameter28", "" + sms3);
            }
            if (sms2.equals(".")) {
            } else {
                parameters.put("parameter29", "" + sms4);
            }

            ArrayList k = new ArrayList();
            int serial = 1;
            String iname;
            int taxp = 0;
            double quan, price, amount, taxamt1, total;
            query = "select iname,quan,price,amount,taxp,taxamt,total from po_items where grn='" + billno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                SelRomJasper selRomJasper = new SelRomJasper();
                iname = r.getString(1);
                quan = r.getDouble(2);
                price = r.getDouble(3);
                amount = r.getDouble(4);
                taxp = (int) r.getDouble(5);
                taxamt1 = r.getDouble(6);
                total = r.getDouble(7);

                selRomJasper.setField1("" + serial);
                selRomJasper.setField2(" " + iname);
                int quan1 = (int) quan;
                selRomJasper.setField3("" + quan1);

                String price2 = String.format("%." + hmany + "f", price);
                String amount2 = String.format("%." + hmany + "f", amount);
                String taxamt3 = String.format("%." + hmany + "f", taxamt1);
                String total2 = String.format("%." + hmany + "f", total);

                selRomJasper.setField3("" + quan1);
                selRomJasper.setField4("" + price2);
                selRomJasper.setField5("" + amount2);
                selRomJasper.setField6("" + taxp);
                selRomJasper.setField7("" + taxamt3);
                selRomJasper.setField8("" + total2);

                k.add(selRomJasper);
                serial = serial + 1;
            }
            disable_warnigs.disableAccessWarnings();
            menu_form me = new menu_form();
            String drive = "";
            String folder = Utils.AppConfig.getAppPath();

            JasperReport jasperReport = JasperReportCompiler.compileReport("/JasperFiles/PO/PO_A4.jrxml");
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(k);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

            jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
            jasperViewer.setTitle("Print Preview");
            jasperViewer.setSize(400, 555);
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

        } catch (ClassNotFoundException | NumberFormatException | SQLException | JRException e) {
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
