package barcodepack.jasper;

import Utils.BarcodeUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarcodePreviewGenerator {

    public static void generatePreview(File jrxmlFile) {
        try {
            String fileName = jrxmlFile.getName();
            String pngName;
            if (fileName.toLowerCase().endsWith(".jrxml")) {
                pngName = fileName.substring(0, fileName.length() - 6) + ".png";
            } else {
                pngName = fileName + ".png";
            }
            File pngFile = new File(jrxmlFile.getParent(), pngName);

            // 1. Parse Layout for Barcode settings
            JRXMLParser.TemplateData meta = JRXMLParser.parse(jrxmlFile);

            // 2. Prepare Dummy Data
            List<Map<String, ?>> dataList = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("Iname", "Sample Item");
            map.put("MRP", "100.00");
            map.put("RPrice", "90.00");
            map.put("WPrice", "80.00");
            map.put("Batch", "B-101");
            map.put("Size", "L");
            map.put("MFG", "01/2025");
            map.put("EXP", "12/2025");
            map.put("CompanyName", "My Company");
            map.put("Barcode", "12345678");

            // Convert barcode dimensions from MM (stored in layout) to Pixels (approx 72 DPI) for the image generation
            // The factor 2.83465 converts MM to Points (1/72 inch).
            int bwPx = (int)(meta.layout.getBarcodeWidth() * 2.83465);
            int bhPx = (int)(meta.layout.getBarcodeHeight() * 2.83465);

            String bType = meta.layout.getBarcodeType();
            if (bType == null) bType = "Code128";

            try {
                BufferedImage barcodeImg = BarcodeUtils.generateCodeImage("12345678", bType, bwPx, bhPx);
                map.put("BarcodeImage", barcodeImg);
            } catch (Exception e) {
                // Ignore barcode generation error, report will skip image
            }

            dataList.add(map);

            // 3. Compile and Fill
            JasperReport report = JasperCompileManager.compileReport(jrxmlFile.getAbsolutePath());
            JasperPrint print = JasperFillManager.fillReport(report, new HashMap<>(), new JRMapCollectionDataSource(dataList));

            // 4. Export to Image
            if (!print.getPages().isEmpty()) {
                // 2x zoom for better quality thumbnail
                BufferedImage pageImage = (BufferedImage) JasperPrintManager.printPageToImage(print, 0, 2.0f);
                ImageIO.write(pageImage, "png", pngFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
