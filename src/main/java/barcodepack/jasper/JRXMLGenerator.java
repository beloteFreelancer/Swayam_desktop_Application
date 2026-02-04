package barcodepack.jasper;

import Utils.BarcodeUtils.FieldSettings;
import Utils.BarcodeUtils.LayoutSettings;
import java.util.Map;

public class JRXMLGenerator {

    private static final double MM_TO_POINTS = 2.8346456693;

    public static String generate(LayoutSettings layout, Map<String, FieldSettings> fields) {
        StringBuilder xml = new StringBuilder();

        // Conversions
        double marginLeftPts = layout.getMarginLeft() * MM_TO_POINTS;
        double marginTopPts = layout.getMarginTop() * MM_TO_POINTS;

        boolean isRoll = layout.getPageType() != null && layout.getPageType().toLowerCase().contains("roll");

        // Calculate Band Height (Sticker Height)
        int bandHeight;
        if (layout.getStickerHeight() > 0) {
             bandHeight = (int)(layout.getStickerHeight() * MM_TO_POINTS);
        } else {
             // Fallback: calculate from content
             int maxContentY = 0;
             for (FieldSettings fs : fields.values()) {
                if (fs.isInclude()) {
                    int bottom = fs.getY() + fs.getFontSize() + 5;
                    if (fields.containsKey("Barcode") && fields.get("Barcode") == fs) {
                        bottom = fs.getY() + (int)(layout.getBarcodeHeight() * MM_TO_POINTS);
                    }
                    if (bottom > maxContentY) maxContentY = bottom;
                }
             }
             bandHeight = maxContentY + 10; // Padding
        }

        // Calculate Page Dimensions
        int pageWidth = (int) Math.round(layout.getPageWidth() * MM_TO_POINTS);
        int pageHeight;

        if (isRoll) {
            // For Roll: Page Height must accommodate margins + band height + gaps
            int vGap = (int)(layout.getVerticalGapMM() * MM_TO_POINTS);
            pageHeight = (int)marginTopPts + bandHeight + vGap;

            if (pageHeight < 30) pageHeight = 50;
        } else {
            pageHeight = (int) Math.round(297 * MM_TO_POINTS); // A4 Height
        }

        int cols = layout.getStickersPerRow();
        if (cols < 1) cols = 1;

        double hGapPts = layout.getHorizontalGapMM() * MM_TO_POINTS;
        double marginRightPts = isRoll ? 0 : marginLeftPts;

        // Correctly calculate column width based on Sticker Width if provided
        int iColWidth;
        if (layout.getStickerWidth() > 0) {
            iColWidth = (int) Math.round(layout.getStickerWidth() * MM_TO_POINTS);
        } else {
            // Fallback to calculating from Page Width
            double availableWidth = (layout.getPageWidth() * MM_TO_POINTS) - marginLeftPts - marginRightPts;
            double colWidth = (availableWidth - (hGapPts * (cols - 1))) / cols;
            iColWidth = (int) Math.floor(colWidth);
        }

        int iColSpacing = (int) Math.floor(hGapPts);
        int iMarginLeft = (int) marginLeftPts;
        int iMarginRight = (int) marginRightPts;
        int iMarginTop = (int) marginTopPts;

        // FINAL CHECK: Ensure PageHeight >= TopMargin + BandHeight
        if (pageHeight < iMarginTop + bandHeight) {
            pageHeight = iMarginTop + bandHeight + 5; // Add safety buffer
        }

        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" ")
           .append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ")
           .append("xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" ")
           .append("name=\"BarcodeSticker\" ")
           .append("pageWidth=\"").append(pageWidth).append("\" ")
           .append("pageHeight=\"").append(pageHeight).append("\" ")
           .append("columnCount=\"").append(cols).append("\" ")
           .append("printOrder=\"Horizontal\" ")
           .append("columnWidth=\"").append(iColWidth).append("\" ")
           .append("columnSpacing=\"").append(iColSpacing).append("\" ")
           .append("leftMargin=\"").append(iMarginLeft).append("\" ")
           .append("rightMargin=\"").append(iMarginRight).append("\" ")
           .append("topMargin=\"").append(iMarginTop).append("\" ")
           .append("bottomMargin=\"0\" ")
           .append("isIgnorePagination=\"false\">\n");

        // === METADATA PROPERTIES (To restore UI state) ===
        xml.append("  <property name=\"com.selrom.barcode.barcodeType\" value=\"").append(layout.getBarcodeType()).append("\"/>\n");
        xml.append("  <property name=\"com.selrom.barcode.barcodeWidth\" value=\"").append(layout.getBarcodeWidth()).append("\"/>\n");
        xml.append("  <property name=\"com.selrom.barcode.barcodeHeight\" value=\"").append(layout.getBarcodeHeight()).append("\"/>\n");
        xml.append("  <property name=\"com.selrom.barcode.fontSize\" value=\"").append(layout.getFontSize()).append("\"/>\n");
        xml.append("  <property name=\"com.selrom.barcode.stickersPerRow\" value=\"").append(layout.getStickersPerRow()).append("\"/>\n");
        xml.append("  <property name=\"com.selrom.barcode.horizontalGap\" value=\"").append(layout.getHorizontalGapMM()).append("\"/>\n");
        xml.append("  <property name=\"com.selrom.barcode.verticalGap\" value=\"").append(layout.getVerticalGapMM()).append("\"/>\n");
        xml.append("  <property name=\"com.selrom.barcode.pageWidth\" value=\"").append(layout.getPageWidth()).append("\"/>\n");
        xml.append("  <property name=\"com.selrom.barcode.pageType\" value=\"").append(layout.getPageType()).append("\"/>\n");
        xml.append("  <property name=\"com.selrom.barcode.marginLeft\" value=\"").append(layout.getMarginLeft()).append("\"/>\n");
        xml.append("  <property name=\"com.selrom.barcode.marginTop\" value=\"").append(layout.getMarginTop()).append("\"/>\n");

        xml.append("  <property name=\"com.selrom.barcode.stickerWidth\" value=\"").append(layout.getStickerWidth()).append("\"/>\n");
        xml.append("  <property name=\"com.selrom.barcode.stickerHeight\" value=\"").append(layout.getStickerHeight()).append("\"/>\n");

        xml.append("  <property name=\"com.jaspersoft.studio.unit.pageHeight\" value=\"mm\"/>\n");
        xml.append("  <property name=\"com.jaspersoft.studio.unit.pageWidth\" value=\"mm\"/>\n");

        String[] fieldNames = {"Iname", "MRP", "RPrice", "WPrice", "Batch", "Size", "MFG", "EXP", "CompanyName", "Barcode"};
        for (String f : fieldNames) {
            xml.append("  <field name=\"").append(f).append("\" class=\"java.lang.String\"/>\n");
        }
        xml.append("  <field name=\"BarcodeImage\" class=\"java.awt.Image\"/>\n");

        xml.append("  <detail>\n");
        xml.append("    <band height=\"").append(bandHeight).append("\" splitType=\"Stretch\">\n");

        for (Map.Entry<String, FieldSettings> entry : fields.entrySet()) {
            String fieldName = entry.getKey();
            FieldSettings fs = entry.getValue();
            if (!fs.isInclude()) continue;

            int x = fs.getX();
            int y = fs.getY();
            int fontSize = fs.getFontSize();
            char align = fs.getAlignment();
            String prefix = fs.getPrefix(); // Get prefix

            String textAlign = switch(align) {
                case 'C' -> "Center";
                case 'R' -> "Right";
                default -> "Left";
            };

            if (fieldName.equals("Barcode")) {
                int w = (int)(layout.getBarcodeWidth() * MM_TO_POINTS);
                int h = (int)(layout.getBarcodeHeight() * MM_TO_POINTS);
                String hAlign = switch(align) {
                    case 'C' -> "Center";
                    case 'R' -> "Right";
                    default -> "Left";
                };

                // For barcode alignment:
                if (align == 'C' || align == 'R') {
                    x = 0;
                    w = iColWidth; // Ensure it spans the full width for centering
                }

                xml.append("      <image scaleImage=\"RetainShape\" hAlign=\"").append(hAlign).append("\" vAlign=\"Middle\">\n");
                xml.append("        <reportElement x=\"").append(x).append("\" y=\"").append(y)
                   .append("\" width=\"").append(w).append("\" height=\"").append(h).append("\">\n");
                xml.append("           <property name=\"com.selrom.barcode.alignment\" value=\"").append(align).append("\"/>\n");
                xml.append("        </reportElement>\n");
                xml.append("        <imageExpression><![CDATA[$F{BarcodeImage}]]></imageExpression>\n");
                xml.append("      </image>\n");
            } else {
                String textExp;
                String metaName;
                if (fieldName.startsWith("STATIC:")) {
                    textExp = "\"" + escapeXML(fieldName.split(":")[1]) + "\"";
                    metaName = fieldName;
                } else {
                    // Apply prefix if it exists
                    if (prefix != null && !prefix.isEmpty()) {
                        String safePrefix = escapeXML(prefix);
                        if ("BarcodeNumber".equals(fieldName)) {
                             textExp = "($F{Barcode} == null ? \"\" : \"" + safePrefix + "\" + $F{Barcode})";
                        } else {
                             textExp = "($F{" + fieldName + "} == null ? \"\" : \"" + safePrefix + "\" + $F{" + fieldName + "})";
                        }
                    } else {
                        if ("BarcodeNumber".equals(fieldName)) {
                             textExp = "$F{Barcode}";
                        } else {
                             textExp = "$F{" + fieldName + "}";
                        }
                    }
                    metaName = fieldName;
                }

                int w = iColWidth - x;
                if (w < 10) w = 100;

                if (align == 'C') {
                    x = 0;
                    w = iColWidth;
                } else if (align == 'R') {
                    x = 0;
                    w = iColWidth;
                }

                xml.append("      <textField textAdjust=\"ScaleFont\" isBlankWhenNull=\"true\">\n");
                xml.append("        <reportElement x=\"").append(x).append("\" y=\"").append(y)
                   .append("\" width=\"").append(w).append("\" height=\"").append(fontSize + 4).append("\">\n");
                xml.append("            <property name=\"com.selrom.barcode.fieldName\" value=\"").append(escapeXML(metaName)).append("\"/>\n");
                xml.append("            <property name=\"com.selrom.barcode.alignment\" value=\"").append(align).append("\"/>\n");
                if (prefix != null && !prefix.isEmpty()) {
                    xml.append("            <property name=\"com.selrom.barcode.prefix\" value=\"").append(escapeXML(prefix)).append("\"/>\n");
                }
                xml.append("        </reportElement>\n");
                xml.append("        <textElement textAlignment=\"").append(textAlign).append("\">\n");
                xml.append("          <font fontName=\"SansSerif\" size=\"").append(fontSize).append("\"/>\n");
                xml.append("        </textElement>\n");
                xml.append("        <textFieldExpression><![CDATA[").append(textExp).append("]]></textFieldExpression>\n");
                xml.append("      </textField>\n");
            }
        }

        xml.append("    </band>\n");
        xml.append("  </detail>\n");
        xml.append("</jasperReport>\n");

        return xml.toString();
    }

    private static String escapeXML(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
