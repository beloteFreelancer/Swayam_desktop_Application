package barcodepack.jasper;

import Utils.BarcodeUtils.FieldSettings;
import Utils.BarcodeUtils.LayoutSettings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class JRXMLParser {

    public static class TemplateData {
        public LayoutSettings layout;
        public Map<String, FieldSettings> fields;
    }

    public static TemplateData parse(File file) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        TemplateData data = new TemplateData();
        data.layout = new LayoutSettings();
        data.fields = new LinkedHashMap<>();

        Element root = doc.getDocumentElement();

        // Restore Layout Settings from Properties
        NodeList props = root.getElementsByTagName("property");
        for (int i = 0; i < props.getLength(); i++) {
            Element prop = (Element) props.item(i);
            String name = prop.getAttribute("name");
            String value = prop.getAttribute("value");

            if (name.startsWith("com.selrom.barcode.")) {
                switch (name) {
                    case "com.selrom.barcode.barcodeType": data.layout.setBarcodeType(value); break;
                    case "com.selrom.barcode.barcodeWidth": data.layout.setBarcodeWidth(Double.parseDouble(value)); break;
                    case "com.selrom.barcode.barcodeHeight": data.layout.setBarcodeHeight(Double.parseDouble(value)); break;
                    case "com.selrom.barcode.fontSize": data.layout.setFontSize(Double.parseDouble(value)); break;
                    case "com.selrom.barcode.stickersPerRow": data.layout.setStickersPerRow(Integer.parseInt(value)); break;
                    case "com.selrom.barcode.horizontalGap": data.layout.setHorizontalGapMM(Double.parseDouble(value)); break;
                    case "com.selrom.barcode.verticalGap": data.layout.setVerticalGapMM(Double.parseDouble(value)); break;
                    case "com.selrom.barcode.pageWidth": data.layout.setPageWidth(Double.parseDouble(value)); break;
                    case "com.selrom.barcode.pageType": data.layout.setPageType(value); break;
                    case "com.selrom.barcode.marginLeft": data.layout.setMarginLeft(Double.parseDouble(value)); break;
                    case "com.selrom.barcode.marginTop": data.layout.setMarginTop(Double.parseDouble(value)); break;
                    case "com.selrom.barcode.stickerWidth": data.layout.setStickerWidth(Double.parseDouble(value)); break;
                    case "com.selrom.barcode.stickerHeight": data.layout.setStickerHeight(Double.parseDouble(value)); break;
                }
            }
        }

        // Restore Fields
        NodeList images = doc.getElementsByTagName("image");
        for (int i = 0; i < images.getLength(); i++) {
            Element img = (Element) images.item(i);
            Element reportElement = (Element) img.getElementsByTagName("reportElement").item(0);
            int x = Integer.parseInt(reportElement.getAttribute("x"));
            int y = Integer.parseInt(reportElement.getAttribute("y"));

            char align = 'C'; // Default for barcode
            NodeList innerProps = reportElement.getElementsByTagName("property");
            for (int j = 0; j < innerProps.getLength(); j++) {
                 Element p = (Element) innerProps.item(j);
                 if ("com.selrom.barcode.alignment".equals(p.getAttribute("name"))) {
                     String val = p.getAttribute("value");
                     if (val.length() > 0) align = val.charAt(0);
                     break;
                 }
            }

            data.fields.put("Barcode", new FieldSettings(true, x, y, 10, align, null));
        }

        NodeList textFields = doc.getElementsByTagName("textField");
        for (int i = 0; i < textFields.getLength(); i++) {
            Element tf = (Element) textFields.item(i);
            Element reportElement = (Element) tf.getElementsByTagName("reportElement").item(0);
            int x = Integer.parseInt(reportElement.getAttribute("x"));
            int y = Integer.parseInt(reportElement.getAttribute("y"));

            Element textElement = (Element) tf.getElementsByTagName("textElement").item(0);
            Element font = (Element) textElement.getElementsByTagName("font").item(0);
            int size = Integer.parseInt(font.getAttribute("size"));

            String fieldName = null;
            char align = 'L';
            String prefix = null;

            // 1. Try to find custom property
            NodeList innerProps = reportElement.getElementsByTagName("property");
            for (int j = 0; j < innerProps.getLength(); j++) {
                 Element p = (Element) innerProps.item(j);
                 String pName = p.getAttribute("name");
                 String pVal = p.getAttribute("value");

                 if ("com.selrom.barcode.fieldName".equals(pName)) {
                     fieldName = pVal;
                 } else if ("com.selrom.barcode.alignment".equals(pName)) {
                     if (pVal.length() > 0) align = pVal.charAt(0);
                 } else if ("com.selrom.barcode.prefix".equals(pName)) {
                     prefix = pVal;
                 }
            }

            // 2. If alignment not found in property, infer from textElement
            if (align == 'L' && textElement.hasAttribute("textAlignment")) {
                String ta = textElement.getAttribute("textAlignment");
                if ("Center".equalsIgnoreCase(ta)) align = 'C';
                else if ("Right".equalsIgnoreCase(ta)) align = 'R';
            }

            if (fieldName == null) {
                String expression = tf.getElementsByTagName("textFieldExpression").item(0).getTextContent();
                if (expression.contains("$F{")) {
                    fieldName = expression.substring(expression.indexOf("{") + 1, expression.indexOf("}"));
                } else {
                    fieldName = "STATIC:" + expression.replaceAll("\"", "");
                }
            }

            data.fields.put(fieldName, new FieldSettings(true, x, y, size, align, prefix));
        }

        return data;
    }
}
