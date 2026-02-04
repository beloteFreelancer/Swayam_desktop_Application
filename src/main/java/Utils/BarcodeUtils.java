package Utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.selrom.db.DataUtil;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class BarcodeUtils {

    // (loadBarcodeSettings method... no change)
    public static Map<String, FieldSettings> loadBarcodeSettings() {
        Map<String, FieldSettings> fieldSettingsMap = new LinkedHashMap<>();

        fieldSettingsMap.put("CompanyName", new FieldSettings(false, 10, 10, 10, 'L', null));
        fieldSettingsMap.put("Barcode", new FieldSettings(false, 10, 30, 10, 'C', null));
        fieldSettingsMap.put("Iname", new FieldSettings(false, 10, 50, 10, 'L', null));
        fieldSettingsMap.put("Batch", new FieldSettings(false, 10, 70, 10, 'L', null));
        fieldSettingsMap.put("Size", new FieldSettings(false, 10, 70, 10, 'L', null));
        fieldSettingsMap.put("MRP", new FieldSettings(false, 10, 90, 10, 'L', null));
        fieldSettingsMap.put("RPrice", new FieldSettings(false, 10, 110, 10, 'L', null));
        fieldSettingsMap.put("WPrice", new FieldSettings(false, 10, 130, 10, 'L', null));
        fieldSettingsMap.put("MFG", new FieldSettings(false, 10, 150, 10, 'L', null));
        fieldSettingsMap.put("EXP", new FieldSettings(false, 10, 170, 10, 'L', null));

        try {
            DataUtil dataUtil = new DataUtil();
            // Note: DB doesn't have prefix column yet, so we treat it as null/default
            String query = "SELECT field_name, include, x_pos, y_pos, font_size FROM barcode_settings WHERE company_name = 'company';";
            ResultSet rs = dataUtil.doQuery(query);
            while (rs.next()) {
                String field = rs.getString("field_name");
                boolean include = rs.getBoolean("include");
                int x = rs.getInt("x_pos");
                int y = rs.getInt("y_pos");
                int fontSize = rs.getInt("font_size");
                // Default to 'L' if not in DB
                fieldSettingsMap.put(field, new FieldSettings(include, x, y, fontSize, 'L', null));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return fieldSettingsMap;
    }

    // --- FIX: Load layout settings with page_type ---
    public static LayoutSettings loadOtherSettings() {
        LayoutSettings defaultSettings = new LayoutSettings("Code128", 50, 30, 10, 3, 2.0, 2.0, 210.0, "A4 Sheet (Full Page)", 0, 0, 50, 30);

        try {
            DataUtil dataUtil = new DataUtil();
            String query = "SELECT barcode_type, barcode_width, barcode_height, font_size, "
                    + "stickers_per_row, horizontal_gap, vertical_gap, page_width, page_type, margin_left, margin_top "
                    + "FROM barcode_layout_settings WHERE company_name = 'company';";
            ResultSet rs = dataUtil.doQuery(query);
            if (rs.next()) {
                return new LayoutSettings(
                        rs.getString("barcode_type"),
                        rs.getDouble("barcode_width"),
                        rs.getDouble("barcode_height"),
                        rs.getDouble("font_size"),
                        rs.getInt("stickers_per_row"),
                        rs.getDouble("horizontal_gap"),
                        rs.getDouble("vertical_gap"),
                        rs.getDouble("page_width"),
                        rs.getString("page_type"),
                        rs.getDouble("margin_left"),
                        rs.getDouble("margin_top"),
                        0, 0 // Defaults for stickerW/H if not in DB
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return defaultSettings;
        }
        return defaultSettings;
    }

    // (saveBarcodeSettings method... no change)
    public static boolean saveBarcodeSettings(String companyName,
                                              Map<String, FieldSettings> fieldSettingsMap,
                                              LayoutSettings layoutSettings) {
        boolean fieldSaved = saveFieldSettings(companyName, fieldSettingsMap);
        boolean layoutSaved = saveLayoutSettings(companyName, layoutSettings);
        return fieldSaved || layoutSaved;
    }

    // (saveFieldSettings method... no change)
    public static boolean saveFieldSettings(String companyName, Map<String, FieldSettings> fieldSettingsMap) {
        try {
            DataUtil dataUtil = new DataUtil();
            int rows = 0;
            for (Map.Entry<String, FieldSettings> entry : fieldSettingsMap.entrySet()) {
                String field = entry.getKey();
                FieldSettings s = entry.getValue();
                String query = "INSERT INTO barcode_settings (company_name, field_name, include, x_pos, y_pos, font_size) "
                        + "VALUES ('" + companyName + "', '" + field + "', " + s.isInclude() + ", " + s.getX() + ", " + s.getY() + ", " + s.getFontSize() + ") "
                        + "ON DUPLICATE KEY UPDATE include=VALUES(include), x_pos=VALUES(x_pos), y_pos=VALUES(y_pos), font_size=VALUES(font_size)";
                rows += dataUtil.doManipulation(query);
            }
            return rows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    // --- FIX: Save layout settings with page_type ---
    public static boolean saveLayoutSettings(String companyName, LayoutSettings layout) {
        try {
            DataUtil dataUtil = new DataUtil();
            String query = "INSERT INTO barcode_layout_settings (company_name, barcode_type, barcode_width, barcode_height, font_size, "
                    + "stickers_per_row, horizontal_gap, vertical_gap, page_width, page_type, margin_left, margin_top) "
                    + "VALUES ('" + companyName + "', '" + layout.getBarcodeType() + "', " + layout.getBarcodeWidth() + ", "
                    + layout.getBarcodeHeight() + ", " + layout.getFontSize() + ", " + layout.getStickersPerRow() + ", "
                    + layout.getHorizontalGapMM() + ", " + layout.getVerticalGapMM() + ", " + layout.getPageWidth() + ", '"
                    + layout.getPageType() + "', " + layout.getMarginLeft() + ", " + layout.getMarginTop() + ") "
                    + "ON DUPLICATE KEY UPDATE barcode_type=VALUES(barcode_type), barcode_width=VALUES(barcode_width), "
                    + "barcode_height=VALUES(barcode_height), font_size=VALUES(font_size), "
                    + "stickers_per_row=VALUES(stickers_per_row), horizontal_gap=VALUES(horizontal_gap), vertical_gap=VALUES(vertical_gap), "
                    + "page_width=VALUES(page_width), page_type=VALUES(page_type), margin_left=VALUES(margin_left), margin_top=VALUES(margin_top)";
            int rows = dataUtil.doManipulation(query);
            return rows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    // (generateCodeImage method... no change)
    public static BufferedImage generateCodeImage(String text, String type, int width, int height) throws Exception {
        if (text == null || text.isEmpty() || width <= 0 || height <= 0) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }
        BarcodeFormat format = switch (type) {
            case "QR Code" -> BarcodeFormat.QR_CODE;
            case "PDF_417" -> BarcodeFormat.PDF_417;
            case "Aztec" -> BarcodeFormat.AZTEC;
            default -> BarcodeFormat.CODE_128;
        };
        BitMatrix matrix = new MultiFormatWriter().encode(text, format, width, height);
        return MatrixToImageWriter.toBufferedImage(matrix);
    }

    // (FieldSettings class... updated with alignment)
    public static class FieldSettings {
        private final boolean include;
        private final int x;
        private final int y;
        private final int fontSize;
        private final char alignment; // 'L', 'C', 'R', 'T'
        private final String prefix;

        public FieldSettings(boolean include, int x, int y, int fontSize) {
            this(include, x, y, fontSize, 'L', null);
        }

        public FieldSettings(boolean include, int x, int y, int fontSize, char alignment) {
            this(include, x, y, fontSize, alignment, null);
        }

        public FieldSettings(boolean include, int x, int y, int fontSize, char alignment, String prefix) {
            this.include = include;
            this.x = x;
            this.y = y;
            this.fontSize = fontSize;
            this.alignment = alignment;
            this.prefix = prefix;
        }

        public boolean isInclude() { return include; }
        public int getX() { return x; }
        public int getY() { return y; }
        public int getFontSize() { return fontSize; }
        public char getAlignment() { return alignment; }
        public String getPrefix() { return prefix; }
    }

    // --- FIX: Updated LayoutSettings class ---
    public static class LayoutSettings {
        private String barcodeType;
        private double barcodeWidth;
        private double barcodeHeight;
        private double fontSize;
        private int stickersPerRow;
        private double horizontalGapMM;
        private double verticalGapMM;
        private double pageWidth;
        private String pageType;
        private double marginLeft;
        private double marginTop;
        private double stickerWidth;
        private double stickerHeight;


        public LayoutSettings() {}

        public LayoutSettings(String barcodeType, double barcodeWidth, double barcodeHeight,
                              double fontSize, int stickersPerRow,
                              double horizontalGapMM, double verticalGapMM, double pageWidth,
                              String pageType, double marginLeft, double marginTop,
                              double stickerWidth, double stickerHeight) {
            this.barcodeType = barcodeType;
            this.barcodeWidth = barcodeWidth;
            this.barcodeHeight = barcodeHeight;
            this.fontSize = fontSize;
            this.stickersPerRow = stickersPerRow;
            this.horizontalGapMM = horizontalGapMM;
            this.verticalGapMM = verticalGapMM;
            this.pageWidth = pageWidth;
            this.pageType = pageType;
            this.marginLeft = marginLeft;
            this.marginTop = marginTop;
            this.stickerWidth = stickerWidth;
            this.stickerHeight = stickerHeight;
        }

        public String getBarcodeType() { return barcodeType; }
        public void setBarcodeType(String barcodeType) { this.barcodeType = barcodeType; }
        public double getBarcodeWidth() { return barcodeWidth; }
        public void setBarcodeWidth(double barcodeWidth) { this.barcodeWidth = barcodeWidth; }
        public double getBarcodeHeight() { return barcodeHeight; }
        public void setBarcodeHeight(double barcodeHeight) { this.barcodeHeight = barcodeHeight; }
        public double getFontSize() { return fontSize; }
        public void setFontSize(double fontSize) { this.fontSize = fontSize; }
        public int getStickersPerRow() { return stickersPerRow; }
        public void setStickersPerRow(int stickersPerRow) { this.stickersPerRow = stickersPerRow; }
        public double getHorizontalGapMM() { return horizontalGapMM; }
        public void setHorizontalGapMM(double horizontalGapMM) { this.horizontalGapMM = horizontalGapMM; }
        public double getVerticalGapMM() { return verticalGapMM; }
        public void setVerticalGapMM(double verticalGapMM) { this.verticalGapMM = verticalGapMM; }
        public double getPageWidth() { return pageWidth; }
        public void setPageWidth(double pageWidth) { this.pageWidth = pageWidth; }
        public String getPageType() { return pageType; }
        public void setPageType(String pageType) { this.pageType = pageType; }
        public double getMarginLeft() { return marginLeft; }
        public void setMarginLeft(double marginLeft) { this.marginLeft = marginLeft; }
        public double getMarginTop() { return marginTop; }
        public void setMarginTop(double marginTop) { this.marginTop = marginTop; }
        public double getStickerWidth() { return stickerWidth; }
        public void setStickerWidth(double stickerWidth) { this.stickerWidth = stickerWidth; }
        public double getStickerHeight() { return stickerHeight; }
        public void setStickerHeight(double stickerHeight) { this.stickerHeight = stickerHeight; }
    }
}
