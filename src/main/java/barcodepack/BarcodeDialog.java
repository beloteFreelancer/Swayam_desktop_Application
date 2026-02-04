package barcodepack;

import Utils.BarcodeUtils;
import Utils.BarcodeUtils.FieldSettings;
import Utils.BarcodeUtils.LayoutSettings;
import Utils.CompanySettingUtil;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.Item;

public class BarcodeDialog {

    private static DraggableField selectedField = null;
    private static JTextField propXField;
    private static JTextField propYField;
    private static JTextField propFontField;

    private static JComboBox<String> barcodeTypeCombo;
    private static JTextField barcodeWidthField;
    private static JTextField barcodeHeightField;
    private static JTextField horizontalGapField;
    private static JTextField verticalGapField;
    private static JTextField pageWidthField;
    private static JTextField stickersPerRowField;
    private static JPanel canvasPanel;
    private static JTextField marginTopField;
    private static JTextField marginLeftField;

    private static JComboBox<String> pageTypeCombo;
    private static JTextField fontSizeField;

    private static final double MM_TO_POINTS = 2.8346456693;

    public static void updateBarcodeSettingsDialog(Window parent) {
        JDialog dialog = new JDialog(parent, "Barcode Print Settings (Visual Designer)", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(1100, 600);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout(10, 10));

        // === 1. TOP PANEL (Layout Settings) ===
        // Initialize Components
        barcodeTypeCombo = new JComboBox<>(new String[]{"Code128", "QR Code", "PDF_417", "Aztec"});
        barcodeWidthField = new JTextField("50", 4);
        barcodeHeightField = new JTextField("30", 4);
        fontSizeField = new JTextField("10", 4);
        stickersPerRowField = new JTextField("3", 3);
        pageWidthField = new JTextField("210", 4);
        horizontalGapField = new JTextField("2", 4);
        verticalGapField = new JTextField("2", 4);
        marginTopField = new JTextField("0", 4);
        marginLeftField = new JTextField("0", 4);
        pageTypeCombo = new JComboBox<>(new String[]{"A4 Sheet (Full Page)", "Sticker Roll (Row by Row)"});

        // Main Top Panel Container
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));

        // Group 1: Page Layout
        JPanel pagePanel = new JPanel(new GridBagLayout());
        pagePanel.setBorder(BorderFactory.createTitledBorder("Page Layout"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        pagePanel.add(new JLabel("Page Type:"), gbc);
        gbc.gridx = 1;
        pagePanel.add(pageTypeCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        pagePanel.add(new JLabel("Page/Roll Width (mm):"), gbc);
        gbc.gridx = 1;
        pagePanel.add(pageWidthField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        pagePanel.add(new JLabel("Labels per row:"), gbc);
        gbc.gridx = 1;
        pagePanel.add(stickersPerRowField, gbc);


        // Group 2: Content & Size
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createTitledBorder("Content & Size"));

        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(new JLabel("Barcode Type:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        contentPanel.add(barcodeTypeCombo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        contentPanel.add(new JLabel("Barcode W (mm):"), gbc);
        gbc.gridx = 1;
        contentPanel.add(barcodeWidthField, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        contentPanel.add(new JLabel("H (mm):"), gbc);
        gbc.gridx = 3;
        contentPanel.add(barcodeHeightField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        contentPanel.add(new JLabel("Font Size (pt):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        contentPanel.add(fontSizeField, gbc);


        // Group 3: Margins & Gaps
        JPanel marginPanel = new JPanel(new GridBagLayout());
        marginPanel.setBorder(BorderFactory.createTitledBorder("Margins & Gaps"));
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 0;
        marginPanel.add(new JLabel("Margin Top (mm):"), gbc);
        gbc.gridx = 1;
        marginPanel.add(marginTopField, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        marginPanel.add(new JLabel("Margin Left (mm):"), gbc);
        gbc.gridx = 3;
        marginPanel.add(marginLeftField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        marginPanel.add(new JLabel("H Gap (mm):"), gbc);
        gbc.gridx = 1;
        marginPanel.add(horizontalGapField, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        marginPanel.add(new JLabel("V Gap (mm):"), gbc);
        gbc.gridx = 3;
        marginPanel.add(verticalGapField, gbc);

        // Add groups to Top Panel
        topPanel.add(pagePanel);
        topPanel.add(contentPanel);
        topPanel.add(marginPanel);

        dialog.add(topPanel, BorderLayout.NORTH);

        // === 2. TOOLBOX PANEL (Left) ===
        JPanel toolboxPanel = new JPanel(new BorderLayout(5, 5));
        toolboxPanel.setBorder(BorderFactory.createTitledBorder("Toolbox"));
        String[] fieldNames = getFields();
        JList<String> fieldList = new JList<>(fieldNames);
        fieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        toolboxPanel.add(new JScrollPane(fieldList), BorderLayout.CENTER);
        JButton addFieldBtn = new JButton("Add Field to Canvas");
        JButton addStaticBtn = new JButton("Add Static Text");
        JPanel toolboxButtonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        toolboxButtonPanel.add(addFieldBtn);
        toolboxButtonPanel.add(addStaticBtn);
        toolboxPanel.add(toolboxButtonPanel, BorderLayout.SOUTH);
        dialog.add(toolboxPanel, BorderLayout.WEST);

        // === 3. CANVAS PANEL (Center) ===
        canvasPanel = new JPanel();
        canvasPanel.setLayout(null);
        canvasPanel.setBorder(BorderFactory.createTitledBorder("Canvas / Preview"));
        canvasPanel.setBackground(Color.WHITE);
        JScrollPane canvasScroller = new JScrollPane(canvasPanel);

        // === 4. PROPERTIES PANEL (Right) ===
        JPanel propertiesPanel = new JPanel(new GridBagLayout());
        propertiesPanel.setBorder(BorderFactory.createTitledBorder("Field Properties"));
        GridBagConstraints pgbc = new GridBagConstraints();
        pgbc.insets = new Insets(4, 4, 4, 4); pgbc.anchor = GridBagConstraints.WEST;
        pgbc.gridx = 0; pgbc.gridy = 0;
        propertiesPanel.add(new JLabel("X Position:"), pgbc);
        pgbc.gridx = 1;
        propXField = new JTextField(5);
        propertiesPanel.add(propXField, pgbc);
        pgbc.gridx = 0; pgbc.gridy = 1;
        propertiesPanel.add(new JLabel("Y Position:"), pgbc);
        pgbc.gridx = 1;
        propYField = new JTextField(5);
        propertiesPanel.add(propYField, pgbc);
        pgbc.gridx = 0; pgbc.gridy = 2;
        propertiesPanel.add(new JLabel("Font Size:"), pgbc);
        pgbc.gridx = 1;
        propFontField = new JTextField(5);
        propertiesPanel.add(propFontField, pgbc);
        JButton applyPropsBtn = new JButton("Apply Properties");
        pgbc.gridx = 0; pgbc.gridy = 3; pgbc.gridwidth = 2;
        propertiesPanel.add(applyPropsBtn, pgbc);
        JButton removeFieldBtn = new JButton("Remove from Canvas");
        removeFieldBtn.setForeground(Color.RED);
        pgbc.gridx = 0; pgbc.gridy = 4; pgbc.gridwidth = 2;
        propertiesPanel.add(removeFieldBtn, pgbc);
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(propertiesPanel, BorderLayout.NORTH);

        // === 5. ASSEMBLE CENTER/RIGHT ===
        JSplitPane centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                canvasScroller, rightPanel);
        centerSplit.setDividerLocation(800);
        dialog.add(centerSplit, BorderLayout.CENTER);

        // === 6. BOTTOM BUTTONS ===
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save Settings");
        bottom.add(saveBtn);
        dialog.add(bottom, BorderLayout.SOUTH);

        // === 7. LOAD SETTINGS ===
        Map<String, FieldSettings> settings = BarcodeUtils.loadBarcodeSettings();
        LayoutSettings layout = BarcodeUtils.loadOtherSettings();

        pageTypeCombo.setSelectedItem(layout.getPageType());
        barcodeTypeCombo.setSelectedItem(layout.getBarcodeType());
        barcodeWidthField.setText(String.valueOf(layout.getBarcodeWidth()));
        barcodeHeightField.setText(String.valueOf(layout.getBarcodeHeight()));
        fontSizeField.setText(String.valueOf(layout.getFontSize()));
        stickersPerRowField.setText(String.valueOf(layout.getStickersPerRow()));
        pageWidthField.setText(String.valueOf(layout.getPageWidth()));
        horizontalGapField.setText(String.valueOf(layout.getHorizontalGapMM()));
        verticalGapField.setText(String.valueOf(layout.getVerticalGapMM()));
        marginTopField.setText(String.valueOf(layout.getMarginTop()));
        marginLeftField.setText(String.valueOf(layout.getMarginLeft()));


        for (Map.Entry<String, FieldSettings> entry : settings.entrySet()) {
            String fieldName = entry.getKey();
            FieldSettings fs = entry.getValue();
            if (fs.isInclude()) {
                String displayText;
                if (fieldName.startsWith("STATIC:")) {
                    try { displayText = fieldName.split(":")[1]; } catch (Exception ex) { displayText = "Static Text"; }
                } else if (fieldName.equals("Barcode")) {
                    displayText = null;
                } else {
                    displayText = fieldName + ": [Value]";
                }
                DraggableField label = new DraggableField(displayText, fieldName);
                label.setFont(new Font(label.getFont().getName(), Font.PLAIN, fs.getFontSize()));
                label.setBounds(fs.getX() + 10, fs.getY() + 10, 200, 25);
                if (fieldName.equals("Barcode")) {
                    label.refreshBarcodePreview();
                } else {
                    label.setSize(label.getPreferredSize());
                }
                canvasPanel.add(label);
            }
        }
        canvasPanel.revalidate();
        canvasPanel.repaint();

        // === 8. ACTIONS ===
        setupRealtimeBarcodeListeners();

        addFieldBtn.addActionListener(e -> {
            String selected = fieldList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(dialog, "Please select a field from the toolbox.", "No Field Selected", JOptionPane.WARNING_MESSAGE);
                return;
            }
            for (Component comp : canvasPanel.getComponents()) {
                if (comp instanceof DraggableField) {
                    if (((DraggableField) comp).getFieldName().equals(selected)) {
                        JOptionPane.showMessageDialog(dialog, "This field is already on the canvas.", "Field Exists", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }
            String displayText = selected + ": [Value]";
            if (selected.equals("Barcode")) {
                displayText = null;
            }
            DraggableField newLabel = new DraggableField(displayText, selected);
            newLabel.setBounds(10, 10, 200, 25);
            newLabel.setFont(new Font(newLabel.getFont().getName(), Font.PLAIN, (int)Double.parseDouble(fontSizeField.getText())));
            if (selected.equals("Barcode")) {
                newLabel.refreshBarcodePreview();
            } else {
                newLabel.setSize(newLabel.getPreferredSize());
            }
            canvasPanel.add(newLabel);
            canvasPanel.revalidate();
            canvasPanel.repaint();
            selectField(newLabel);
        });
        addStaticBtn.addActionListener(e -> {
            String text = JOptionPane.showInputDialog(dialog, "Enter text for the label:", "Add Static Text", JOptionPane.PLAIN_MESSAGE);
            if (text != null && !text.isEmpty()) {
                String fieldName = "STATIC:" + text + ":" + System.currentTimeMillis();
                DraggableField newLabel = new DraggableField(text, fieldName);
                newLabel.setBounds(10, 10, 200, 25);
                newLabel.setFont(new Font(newLabel.getFont().getName(), Font.PLAIN, (int)Double.parseDouble(fontSizeField.getText())));
                newLabel.setSize(newLabel.getPreferredSize());
                canvasPanel.add(newLabel);
                canvasPanel.revalidate();
                canvasPanel.repaint();
                selectField(newLabel);
            }
        });
        applyPropsBtn.addActionListener(e -> {
            if (selectedField != null) {
                try {
                    int x = Integer.parseInt(propXField.getText());
                    int y = Integer.parseInt(propYField.getText());
                    int font = Integer.parseInt(propFontField.getText());
                    selectedField.setLocation(x, y);
                    selectedField.setFont(new Font(selectedField.getFont().getName(), Font.PLAIN, font));
                    if (selectedField.getFieldName().equals("Barcode")) {
                        selectedField.refreshBarcodePreview();
                    } else {
                        selectedField.setSize(selectedField.getPreferredSize());
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Invalid number format for X, Y, or Font Size.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        removeFieldBtn.addActionListener(e -> {
            if (selectedField != null) {
                int choice = JOptionPane.showConfirmDialog(dialog, "Remove '" + selectedField.getFieldName() + "' from the canvas?", "Confirm Remove", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    canvasPanel.remove(selectedField);
                    canvasPanel.revalidate();
                    canvasPanel.repaint();
                    selectField(null);
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "No field selected to remove.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Save button action
        saveBtn.addActionListener(e -> {
            try {
                Rectangle designedBounds = getDesignedStickerBounds(canvasPanel);
                double designedWidthPT = designedBounds.width;
                double rollWidthPT = Double.parseDouble(pageWidthField.getText()) * MM_TO_POINTS;
                double imageablePageWidth = rollWidthPT;
                if(pageTypeCombo.getSelectedItem().toString().equals("A4 Sheet (Full Page)")) {
                    imageablePageWidth -= 72; // Subtract margins for A4
                }
                int cols = Integer.parseInt(stickersPerRowField.getText());
                double hGapPT = Double.parseDouble(horizontalGapField.getText()) * MM_TO_POINTS;
                double calculatedColumnWidthPT = (imageablePageWidth - (hGapPT * (cols - 1))) / cols;
                if (designedWidthPT > calculatedColumnWidthPT) {
                    String warnMsg = String.format(
                            "<html><b>Warning:</b> Your design is too wide.<br><br>" +
                                    "Your sticker design is <b>%.1f mm</b> wide.<br>" +
                                    "Your settings allow for a column width of only <b>%.1f mm</b>.<br><br>" +
                                    "This will cause your sticker to be <b>clipped</b> (cut off) when printing.<br>" +
                                    "Reduce fields, font sizes, or 'Labels per row'.<br><br>" +
                                    "Do you want to save anyway?</html>",
                            designedWidthPT / MM_TO_POINTS,
                            calculatedColumnWidthPT / MM_TO_POINTS
                    );
                    int choice = JOptionPane.showConfirmDialog(dialog, warnMsg, "Design Width Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                        return;
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid number in layout settings.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Map<String, FieldSettings> configToSave = new LinkedHashMap<>();
            Map<String, FieldSettings> currentSettings = BarcodeUtils.loadBarcodeSettings();
            Rectangle designBounds = getDesignedStickerBounds(canvasPanel);
            for(Map.Entry<String, FieldSettings> entry : currentSettings.entrySet()) {
                FieldSettings old = entry.getValue();
                configToSave.put(entry.getKey(), new FieldSettings(false, old.getX(), old.getY(), old.getFontSize()));
            }
            for (Component comp : canvasPanel.getComponents()) {
                if (comp instanceof DraggableField) {
                    DraggableField field = (DraggableField) comp;
                    configToSave.put(field.getFieldName(), new FieldSettings(
                            true,
                            field.getX() - designBounds.x,
                            field.getY() - designBounds.y,
                            field.getFont().getSize()
                    ));
                }
            }

            LayoutSettings ls = new LayoutSettings(
                    barcodeTypeCombo.getSelectedItem().toString(),
                    Double.parseDouble(barcodeWidthField.getText()),
                    Double.parseDouble(barcodeHeightField.getText()),
                    Double.parseDouble(fontSizeField.getText()),
                    Integer.parseInt(stickersPerRowField.getText()),
                    Double.parseDouble(horizontalGapField.getText()),
                    Double.parseDouble(verticalGapField.getText()),
                    Double.parseDouble(pageWidthField.getText()),
                    pageTypeCombo.getSelectedItem().toString(),
                    Double.parseDouble(marginLeftField.getText()),
                    Double.parseDouble(marginTopField.getText()),
                    0, 0 // Defaults for stickerW/H
            );

            BarcodeUtils.saveBarcodeSettings("company", configToSave, ls);
            JOptionPane.showMessageDialog(dialog, "Settings saved.");
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    private static void setupRealtimeBarcodeListeners() {
        barcodeTypeCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                SwingUtilities.invokeLater(() -> {
                    DraggableField barcodeField = findBarcodeFieldOnCanvas();
                    if (barcodeField != null) {
                        barcodeField.refreshBarcodePreview();
                    }
                });
            }
        });
        DocumentListener previewRefreshListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { refreshPreview(); }
            @Override public void removeUpdate(DocumentEvent e) { refreshPreview(); }
            @Override public void changedUpdate(DocumentEvent e) { refreshPreview(); }
            private void refreshPreview() {
                SwingUtilities.invokeLater(() -> {
                    DraggableField barcodeField = findBarcodeFieldOnCanvas();
                    if (barcodeField != null) {
                        barcodeField.refreshBarcodePreview();
                    }
                });
            }
        };
        barcodeWidthField.getDocument().addDocumentListener(previewRefreshListener);
        barcodeHeightField.getDocument().addDocumentListener(previewRefreshListener);
    }
    private static DraggableField findBarcodeFieldOnCanvas() {
        if (canvasPanel == null) return null;
        for (Component comp : canvasPanel.getComponents()) {
            if (comp instanceof DraggableField) {
                DraggableField field = (DraggableField) comp;
                if (field.getFieldName().equals("Barcode")) {
                    return field;
                }
            }
        }
        return null;
    }
    private static Rectangle getDesignedStickerBounds(JPanel canvasPanel) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = 0, maxY = 0;
        boolean hasFields = false;
        try {
            double barcodeWidthMM = Double.parseDouble(barcodeWidthField.getText());
            double barcodeHeightMM = Double.parseDouble(barcodeHeightField.getText());
            for (Component comp : canvasPanel.getComponents()) {
                if (comp instanceof DraggableField) {
                    hasFields = true;
                    DraggableField field = (DraggableField) comp;
                    int fieldX = field.getX();
                    int fieldY = field.getY();
                    int fieldWidth;
                    int fieldHeight;
                    if (field.getFieldName().equals("Barcode")) {
                        fieldWidth = (int)(barcodeWidthMM * MM_TO_POINTS);
                        fieldHeight = (int)(barcodeHeightMM * MM_TO_POINTS);
                    } else {
                        fieldWidth = field.getPreferredSize().width;
                        fieldHeight = field.getPreferredSize().height;
                    }
                    if (fieldX < minX) minX = fieldX;
                    if (fieldY < minY) minY = fieldY;
                    if (fieldX + fieldWidth > maxX) maxX = fieldX + fieldWidth;
                    if (fieldY + fieldHeight > maxY) maxY = fieldY + fieldHeight;
                }
            }
        } catch (Exception e) {}
        if (!hasFields) {
            return new Rectangle(0, 0, 0, 0);
        }
        return new Rectangle(minX, minY, (maxX - minX) + 5, (maxY - minY) + 5);
    }
    private static void updatePropertiesPanel() {
        if (selectedField != null) {
            propXField.setText(String.valueOf(selectedField.getX()));
            propYField.setText(String.valueOf(selectedField.getY()));
            propFontField.setText(String.valueOf(selectedField.getFont().getSize()));
            propXField.setEnabled(true);
            propYField.setEnabled(true);
            boolean isText = !selectedField.getFieldName().equals("Barcode");
            propFontField.setEnabled(isText);
        } else {
            propXField.setText(""); propYField.setText(""); propFontField.setText("");
            propXField.setEnabled(false); propYField.setEnabled(false); propFontField.setEnabled(false);
        }
    }
    private static void selectField(DraggableField field) {
        if (selectedField != null) selectedField.setActive(false);
        selectedField = field;
        if (selectedField != null) selectedField.setActive(true);
        updatePropertiesPanel();
    }

    public static String[] getFields() {
        CompanySettingUtil settingUtil = CompanySettingUtil.getInstance();
        String batchOrSize = settingUtil.isDisplayBatch() ? "Batch" : "Size";
        List<String> fields = new ArrayList<>();
        fields.add("CompanyName");
        fields.add("Barcode");
        fields.add("Iname");
        fields.add(batchOrSize);
        fields.add("MRP");
        fields.add("RPrice");
        fields.add("WPrice");
        if (CompanySettingUtil.getInstance().isDisplayMfg()) fields.add("MFG");
        if (CompanySettingUtil.getInstance().isDisplayExp()) fields.add("EXP");
        return fields.toArray(new String[0]);
    }

    static class DraggableField extends JLabel {
        private Point anchorPoint;
        private final String fieldName;
        private final Border defaultBorder = BorderFactory.createEmptyBorder(2, 5, 2, 5);
        private final Border activeBorder = BorderFactory.createLineBorder(Color.BLUE, 1);
        public DraggableField(String text, String fieldName) {
            super(text);
            this.fieldName = fieldName;
            this.setOpaque(true);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            if (fieldName.equals("Barcode")) {
                this.setBorder(BorderFactory.createDashedBorder(Color.GRAY, 2, 2));
                this.setBackground(Color.WHITE);
                refreshBarcodePreview();
            } else {
                this.setBackground(Color.LIGHT_GRAY);
                this.setBorder(defaultBorder);
            }
            MouseAdapter adapter = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    anchorPoint = e.getPoint();
                    selectField(DraggableField.this);
                }
                @Override
                public void mouseDragged(MouseEvent e) {
                    int x = getX() + (e.getX() - anchorPoint.x);
                    int y = getY() + (e.getY() - anchorPoint.y);
                    setLocation(x, y);
                    propXField.setText(String.valueOf(x));
                    propYField.setText(String.valueOf(y));
                }
            };
            addMouseListener(adapter);
            addMouseMotionListener(adapter);
        }
        public String getFieldName() { return fieldName; }
        public void setActive(boolean active) {
            if (fieldName.equals("Barcode")) {
                setBorder(active ? activeBorder : BorderFactory.createDashedBorder(Color.GRAY, 2, 2));
            } else {
                setBorder(active ? activeBorder : defaultBorder);
            }
        }
        public void refreshBarcodePreview() {
            if (!fieldName.equals("Barcode")) {
                return;
            }
            try {
                int w = (int)(Double.parseDouble(barcodeWidthField.getText()) * MM_TO_POINTS);
                int h = (int)(Double.parseDouble(barcodeHeightField.getText()) * MM_TO_POINTS);
                String type = barcodeTypeCombo.getSelectedItem().toString();
                BufferedImage img = BarcodeUtils.generateCodeImage("123456789", type, w, h);
                this.setIcon(new ImageIcon(img));
                this.setText(null);
                this.setSize(w, h);
            } catch (Exception e) {
                this.setIcon(null);
                this.setText("[Barcode]");
                this.setSize(50, 20);
            }
        }
    }


}
