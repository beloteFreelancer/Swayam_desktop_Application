package barcodepack.jasper;

import Utils.BarcodeUtils;
import model.Item;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public class JasperBarcodePrinter {

    public static void print(List<Item> items) {
        File template = BarcodeTemplateManager.getSelectedTemplateFile();
        if (template == null) {
            JOptionPane.showMessageDialog(null, "No barcode template selected. Please configure one in settings.");
            return;
        }

        try {
            // 1. Prepare Data
            List<Map<String, ?>> dataList = new ArrayList<>();

            // We need to know barcode type/size from the template metadata to generate the image correctly
            JRXMLParser.TemplateData meta = JRXMLParser.parse(template);
            int bw = (int)(meta.layout.getBarcodeWidth() * 2.83465);
            int bh = (int)(meta.layout.getBarcodeHeight() * 2.83465);
            String bType = meta.layout.getBarcodeType();

            for (Item item : items) {
                Map<String, Object> map = new HashMap<>();
                map.put("Iname", item.getIname());
                map.put("MRP", String.format("%.2f", item.getMrp()));
                map.put("RPrice", String.format("%.2f", item.getRprice()));
                map.put("WPrice", String.format("%.2f", item.getWprice()));
                map.put("Batch", item.getBatch() == null ? "" : item.getBatch());
                map.put("Size", item.getSize() == null ? "" : item.getSize());
                map.put("MFG", item.getMfg() == null ? "" : item.getMfg());
                map.put("EXP", item.getExp() == null ? "" : item.getExp());
                map.put("CompanyName", item.getCompanyname() == null ? "" : item.getCompanyname());
                map.put("Barcode", item.getBarcode());

                // Generate Image
                if (item.getBarcode() != null && !item.getBarcode().isEmpty()) {
                    try {
                        BufferedImage img = BarcodeUtils.generateCodeImage(item.getBarcode(), bType, bw, bh);
                        map.put("BarcodeImage", img);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                dataList.add(map);
            }

            // 2. Compile
            JasperReport report = JasperCompileManager.compileReport(template.getAbsolutePath());

            // 3. Fill
            JasperPrint print = JasperFillManager.fillReport(report, new HashMap<>(), new JRMapCollectionDataSource(dataList));

            // 4. Print
            JasperPrintManager.printReport(print, true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Printing failed: " + e.getMessage());
        }
    }
}
