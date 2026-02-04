package barcodepack.jasper;

import Utils.BarcodeUtils;
import Utils.BarcodeUtils.FieldSettings;
import Utils.BarcodeUtils.LayoutSettings;
import model.Item;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BarcodeDesigner extends JDialog {

    private static final double MM_TO_POINTS = 2.8346456693;
    private File currentFile;
    private boolean isModified = false;

    // --- UI Components ---
    // Top Toolbar
    private JTextField txtStickerW, txtStickerH, txtCols, txtSheetW, txtColGap;
    private JTextField txtMarginLeft, txtMarginTop;
    private JToggleButton btnGrid, btnSnap;
    private JLabel lblCurrentFile;

    // Left Tools
    private JComboBox<String> comboFields;
    private JComboBox<String> comboPageType;

    // Center
    private JPanel canvasContainer;
    private StickerPanel stickerPanel;

    // Right Properties
    private JTextField propContent; // For Expression or Static Text
    private JTextField propPrefix; // Prefix/Title
    private JTextField propX, propY, propW, propH;
    private JSpinner propSize;
    private JCheckBox propBold;
    private JComboBox<String> propFont;
    private JComboBox<String> propBarcodeType;
    // Alignment properties in the properties panel
    private JToggleButton btnPropAlignL, btnPropAlignC, btnPropAlignR;
    private JButton btnUpdateProp, btnDeleteProp;

    // State
    private DraggableField selectedField = null;
    private LayoutSettings currentLayout = new LayoutSettings();

    public BarcodeDesigner(Window owner, File file) {
        super(owner, "Professional Barcode Designer", ModalityType.APPLICATION_MODAL);
        this.currentFile = file;

        // Default Layout Settings
        currentLayout.setBarcodeType("Code128");
        currentLayout.setBarcodeWidth(50);
        currentLayout.setBarcodeHeight(15);
        currentLayout.setFontSize(10);
        currentLayout.setStickersPerRow(3);
        currentLayout.setPageWidth(210); // A4
        currentLayout.setPageType("A4 Sheet (Full Page)");
        currentLayout.setHorizontalGapMM(2);
        currentLayout.setVerticalGapMM(2);
        currentLayout.setMarginLeft(0);
        currentLayout.setMarginTop(0);
        currentLayout.setStickerWidth(50);
        currentLayout.setStickerHeight(30);

        initComponents();

        if (currentFile != null) {
            loadFromFile(currentFile);
        } else {
            // Initialize with defaults
            updateStickerPanelSize();
        }

        setSize(1100, 650);
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // ===========================
        // 1. TOP TOOLBAR (Page Setup)
        // ===========================
        JToolBar topToolbar = new JToolBar();
        topToolbar.setFloatable(false);
        topToolbar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Sticker/Label Size
        topToolbar.add(new JLabel("<html><b>Label Size(mm)</b></html>"));
        topToolbar.add(Box.createHorizontalStrut(10));
        topToolbar.add(new JLabel("W:"));
        topToolbar.add(Box.createHorizontalStrut(5));
        txtStickerW = new JTextField("50", 3);
        txtStickerW.setMaximumSize(new Dimension(40, 25)); // Restrict max height
        topToolbar.add(txtStickerW);
        topToolbar.add(Box.createHorizontalStrut(10));
        topToolbar.add(new JLabel("H:"));
        topToolbar.add(Box.createHorizontalStrut(5));
        txtStickerH = new JTextField("30", 3);
        txtStickerH.setMaximumSize(new Dimension(40, 25));
        topToolbar.add(txtStickerH);

        // Columns & Spacing
        topToolbar.addSeparator();
        topToolbar.add(new JLabel("Cols:"));
        topToolbar.add(Box.createHorizontalStrut(5));
        txtCols = new JTextField("1", 2);
        txtCols.setMaximumSize(new Dimension(30, 25));
        topToolbar.add(txtCols);
        topToolbar.add(Box.createHorizontalStrut(10));
        topToolbar.add(new JLabel("Gap:"));
        topToolbar.add(Box.createHorizontalStrut(5));
        txtColGap = new JTextField("0", 3);
        txtColGap.setMaximumSize(new Dimension(40, 25));
        txtColGap.setToolTipText("Space between columns (mm)");
        topToolbar.add(txtColGap);

        // Sheet & Margins
        topToolbar.addSeparator();
        topToolbar.add(new JLabel("<html><b>Page(mm)</b></html>"));
        topToolbar.add(Box.createHorizontalStrut(10));
        topToolbar.add(new JLabel("Sheet W:"));
        topToolbar.add(Box.createHorizontalStrut(5));
        txtSheetW = new JTextField("210", 3);
        txtSheetW.setMaximumSize(new Dimension(40, 25));
        topToolbar.add(txtSheetW);

        topToolbar.add(Box.createHorizontalStrut(10));
        topToolbar.add(new JLabel("Marg L:"));
        topToolbar.add(Box.createHorizontalStrut(5));
        txtMarginLeft = new JTextField("0", 3);
        txtMarginLeft.setMaximumSize(new Dimension(40, 25));
        topToolbar.add(txtMarginLeft);
        topToolbar.add(Box.createHorizontalStrut(10));
        topToolbar.add(new JLabel("Marg T:"));
        topToolbar.add(Box.createHorizontalStrut(5));
        txtMarginTop = new JTextField("0", 3);
        txtMarginTop.setMaximumSize(new Dimension(40, 25));
        topToolbar.add(txtMarginTop);

        topToolbar.add(Box.createHorizontalStrut(10));
        JButton btnApplySize = new JButton("Apply");
        btnApplySize.setBackground(new Color(180, 180, 255));
        topToolbar.add(btnApplySize);

        topToolbar.addSeparator();

        btnGrid = new JToggleButton("Grid");
        btnSnap = new JToggleButton("Snap");
        JButton btnPreview = new JButton("Preview");
        JButton btnSave = new JButton("Save");
        btnSave.setBackground(new Color(100, 200, 100));

        lblCurrentFile = new JLabel(currentFile != null ? currentFile.getName() : "new.jrxml");
        lblCurrentFile.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));

        JButton btnLoad = new JButton("Load");

        topToolbar.add(btnGrid);
        topToolbar.add(btnSnap);
        topToolbar.add(btnPreview);
        topToolbar.add(Box.createHorizontalStrut(20));
        topToolbar.add(btnSave);
        topToolbar.add(Box.createHorizontalStrut(10));
        topToolbar.add(lblCurrentFile);
        topToolbar.add(Box.createHorizontalStrut(10));
        topToolbar.add(btnLoad);

        add(topToolbar, BorderLayout.NORTH);

        // Listener for Cols to enable/disable ColGap
        DocumentListener colListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { check(); }
            public void removeUpdate(DocumentEvent e) { check(); }
            public void changedUpdate(DocumentEvent e) { check(); }
            void check() {
                try {
                    int c = Integer.parseInt(txtCols.getText());
                    txtColGap.setEnabled(c > 1);
                } catch(Exception ex) {
                    txtColGap.setEnabled(false);
                }
            }
        };
        txtCols.getDocument().addDocumentListener(colListener);


        // ===========================
        // 2. LEFT PANEL (Tools)
        // ===========================
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        leftPanel.setPreferredSize(new Dimension(180, 0));

        // Section: Page Type
        JPanel pnlPageType = createTitledPanel("Page Type");
        comboPageType = new JComboBox<>(new String[]{"A4 Sheet (Full Page)", "Sticker Roll (Row by Row)"});
        pnlPageType.add(comboPageType);
        leftPanel.add(pnlPageType);
        leftPanel.add(Box.createVerticalStrut(5));

        // Section: Dynamic Field
        JPanel pnlDyn = createTitledPanel("Dynamic Field:");
        comboFields = new JComboBox<>(getFields());
        JButton btnAddField = new JButton("Add Field");
        btnAddField.setBackground(new Color(200, 200, 250));
        btnAddField.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlDyn.add(comboFields);
        pnlDyn.add(Box.createVerticalStrut(5));
        pnlDyn.add(btnAddField);
        leftPanel.add(pnlDyn);
        leftPanel.add(Box.createVerticalStrut(5));

        // Section: Static Text
        JPanel pnlStatic = createTitledPanel("Static Text:");
        JButton btnAddText = new JButton("Add Text");
        btnAddText.setBackground(new Color(200, 200, 250));
        btnAddText.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlStatic.add(btnAddText);
        leftPanel.add(pnlStatic);
        leftPanel.add(Box.createVerticalStrut(5));

        // Button: Add Barcode
        JButton btnAddBarcode = new JButton("Add Barcode");
        btnAddBarcode.setBackground(new Color(150, 150, 255));
        btnAddBarcode.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        btnAddBarcode.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(btnAddBarcode);
        leftPanel.add(Box.createVerticalStrut(10));

        // Section: Align (Quick Actions)
        JPanel pnlAlign = createTitledPanel("Quick Align:");
        JPanel alignBtnGrid = new JPanel(new GridLayout(1, 4, 2, 2));
        JButton btnAlignL = new JButton("L");
        JButton btnAlignC = new JButton("C");
        JButton btnAlignR = new JButton("R");
        JButton btnAlignT = new JButton("T");
        alignBtnGrid.add(btnAlignL);
        alignBtnGrid.add(btnAlignC);
        alignBtnGrid.add(btnAlignR);
        alignBtnGrid.add(btnAlignT);
        pnlAlign.add(alignBtnGrid);
        leftPanel.add(pnlAlign);

        leftPanel.add(Box.createVerticalGlue());

        add(leftPanel, BorderLayout.WEST);

        // ===========================
        // 3. CENTER PANEL (Canvas)
        // ===========================
        canvasContainer = new JPanel(new GridBagLayout());
        canvasContainer.setBackground(Color.LIGHT_GRAY);

        stickerPanel = new StickerPanel();
        stickerPanel.setBackground(Color.WHITE);
        stickerPanel.setLayout(null); // Absolute positioning
        stickerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add shadow effect (simple border for now)
        JPanel shadowWrap = new JPanel(new BorderLayout());
        shadowWrap.add(stickerPanel, BorderLayout.CENTER);
        shadowWrap.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createMatteBorder(0, 0, 5, 5, new Color(0,0,0,50))
        ));
        shadowWrap.setOpaque(false);

        canvasContainer.add(shadowWrap);
        add(new JScrollPane(canvasContainer), BorderLayout.CENTER);


        // ===========================
        // 4. RIGHT PANEL (Properties)
        // ===========================
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        rightPanel.setPreferredSize(new Dimension(220, 0));

        JLabel lblProps = new JLabel("Properties");
        lblProps.setFont(new Font("Arial", Font.BOLD, 12));
        rightPanel.add(lblProps);
        rightPanel.add(Box.createVerticalStrut(10));

        // Content
        rightPanel.add(new JLabel("Content / Expression:"));
        propContent = new JTextField();
        rightPanel.add(propContent);
        rightPanel.add(Box.createVerticalStrut(5));

        // Title / Prefix
        rightPanel.add(new JLabel("Title / Prefix:"));
        propPrefix = new JTextField();
        rightPanel.add(propPrefix);
        rightPanel.add(Box.createVerticalStrut(5));

        // Position
        JPanel pnlPos = new JPanel(new GridLayout(2, 4, 5, 5));
        pnlPos.add(new JLabel("X:")); propX = new JTextField(); pnlPos.add(propX);
        pnlPos.add(new JLabel("Y:")); propY = new JTextField(); pnlPos.add(propY);
        pnlPos.add(new JLabel("W:")); propW = new JTextField(); pnlPos.add(propW);
        pnlPos.add(new JLabel("H:")); propH = new JTextField(); pnlPos.add(propH);
        rightPanel.add(pnlPos);
        rightPanel.add(Box.createVerticalStrut(5));

        // Size & Style
        JPanel pnlStyle = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlStyle.add(new JLabel("Size: "));
        propSize = new JSpinner(new SpinnerNumberModel(10, 4, 72, 1));
        pnlStyle.add(propSize);
        pnlStyle.add(Box.createHorizontalStrut(10));
        propBold = new JCheckBox("Bold");
        pnlStyle.add(propBold);
        rightPanel.add(pnlStyle);

        // Alignment Property
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(new JLabel("Alignment:"));
        JPanel pnlPropAlign = new JPanel(new GridLayout(1, 3));
        ButtonGroup grpAlign = new ButtonGroup();
        btnPropAlignL = new JToggleButton("Left");
        btnPropAlignC = new JToggleButton("Center");
        btnPropAlignR = new JToggleButton("Right");
        grpAlign.add(btnPropAlignL);
        grpAlign.add(btnPropAlignC);
        grpAlign.add(btnPropAlignR);
        pnlPropAlign.add(btnPropAlignL);
        pnlPropAlign.add(btnPropAlignC);
        pnlPropAlign.add(btnPropAlignR);
        rightPanel.add(pnlPropAlign);


        // Font
        rightPanel.add(Box.createVerticalStrut(5));
        propFont = new JComboBox<>(new String[]{"SansSerif", "Serif", "Monospaced", "Arial", "Courier New"});
        rightPanel.add(propFont);
        rightPanel.add(Box.createVerticalStrut(10));

        // Barcode Type
        rightPanel.add(new JLabel("Barcode Type:"));
        propBarcodeType = new JComboBox<>(new String[]{"Code128", "QR Code", "PDF_417", "Aztec"});
        rightPanel.add(propBarcodeType);
        rightPanel.add(Box.createVerticalStrut(15));

        // Buttons
        btnUpdateProp = new JButton("Update Selected");
        btnUpdateProp.setBackground(new Color(200, 200, 255));
        btnUpdateProp.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnUpdateProp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        btnDeleteProp = new JButton("Delete Selected");
        btnDeleteProp.setForeground(Color.RED);
        btnDeleteProp.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDeleteProp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        rightPanel.add(btnUpdateProp);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(btnDeleteProp);

        rightPanel.add(Box.createVerticalGlue());

        add(rightPanel, BorderLayout.EAST);


        // ===========================
        // 5. EVENT LISTENERS
        // ===========================

        // Top Toolbar
        btnApplySize.addActionListener(e -> updateStickerPanelSize());
        btnGrid.addActionListener(e -> stickerPanel.repaint());
        btnSave.addActionListener(e -> saveTemplate());
        btnLoad.addActionListener(e -> loadTemplateDialog());
        btnPreview.addActionListener(e -> previewTemplate());

        // Tools
        btnAddField.addActionListener(e -> addFieldToCanvas((String)comboFields.getSelectedItem()));
        btnAddText.addActionListener(e -> addStaticTextToCanvas());
        btnAddBarcode.addActionListener(e -> addFieldToCanvas("Barcode"));

        btnAlignL.addActionListener(e -> quickAlign('L'));
        btnAlignC.addActionListener(e -> quickAlign('C'));
        btnAlignR.addActionListener(e -> quickAlign('R'));
        btnAlignT.addActionListener(e -> quickAlign('T'));

        // Properties
        ActionListener updateListener = e -> updateSelectedFieldFromProps();
        btnUpdateProp.addActionListener(updateListener);
        btnPropAlignL.addActionListener(updateListener);
        btnPropAlignC.addActionListener(updateListener);
        btnPropAlignR.addActionListener(updateListener);
        propBarcodeType.addActionListener(updateListener);
        propFont.addActionListener(updateListener);

        // Add listeners to text fields for auto-update on Enter or Focus Lost
        FocusAdapter focusListener = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                updateSelectedFieldFromProps();
            }
        };
        ActionListener actionListener = e -> updateSelectedFieldFromProps();

        propX.addActionListener(actionListener);
        propX.addFocusListener(focusListener);

        propY.addActionListener(actionListener);
        propY.addFocusListener(focusListener);

        propW.addActionListener(actionListener);
        propW.addFocusListener(focusListener);

        propH.addActionListener(actionListener);
        propH.addFocusListener(focusListener);

        propContent.addActionListener(actionListener);
        propContent.addFocusListener(focusListener);

        propPrefix.addActionListener(actionListener);
        propPrefix.addFocusListener(focusListener);

        propSize.addChangeListener(e -> updateSelectedFieldFromProps());
        propBold.addActionListener(updateListener);

        btnDeleteProp.addActionListener(e -> removeSelectedField());

        // Canvas Click (Deselect)
        stickerPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (stickerPanel.getComponentAt(e.getPoint()) == stickerPanel) {
                    selectField(null);
                }
            }
        });
    }

    private JPanel createTitledPanel(String title) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        return p;
    }

    private void updateStickerPanelSize() {
        try {
            double wMM = Double.parseDouble(txtStickerW.getText());
            double hMM = Double.parseDouble(txtStickerH.getText());
            int cols = Integer.parseInt(txtCols.getText());

            int wPT = (int) Math.round(wMM * MM_TO_POINTS);
            int hPT = (int) Math.round(hMM * MM_TO_POINTS);

            Dimension dim = new Dimension(wPT, hPT);
            stickerPanel.setPreferredSize(dim);
            stickerPanel.setSize(dim);
            stickerPanel.revalidate();
            canvasContainer.revalidate();
            canvasContainer.repaint();

            txtColGap.setEnabled(cols > 1);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format in Label Size.");
        }
    }

    private void addFieldToCanvas(String fieldName) {
        if (fieldName == null) return;

        DraggableField df = new DraggableField(fieldName);
        df.setLocation(10, 10);

        if ("Barcode".equals(fieldName)) {
            df.setSize(100, 40);
        } else {
            df.setSize(150, 20);
        }

        stickerPanel.add(df);
        stickerPanel.repaint();
        selectField(df);
    }

    private void addStaticTextToCanvas() {
        String text = JOptionPane.showInputDialog(this, "Enter Text:");
        if (text != null && !text.isEmpty()) {
            DraggableField df = new DraggableField("STATIC:" + text);
            df.setTextContent(text);
            df.setLocation(10, 10);
            df.setSize(100, 20);
            stickerPanel.add(df);
            stickerPanel.repaint();
            selectField(df);
        }
    }

    private void selectField(DraggableField df) {
        if (selectedField != null) selectedField.setActive(false);
        selectedField = df;
        if (selectedField != null) {
            selectedField.setActive(true);

            if (selectedField.isStatic()) {
                propContent.setText(selectedField.getTextContent());
                propContent.setEditable(true);
                propPrefix.setText("");
                propPrefix.setEnabled(false);
            } else if ("Barcode".equals(selectedField.getFieldName())) {
                propContent.setText("");
                propContent.setEditable(false);
                propPrefix.setText("");
                propPrefix.setEnabled(false);
            } else {
                propContent.setText("${" + selectedField.getFieldName() + "}");
                propContent.setEditable(false);
                propPrefix.setText(selectedField.getPrefix());
                propPrefix.setEnabled(true);
            }

            propX.setText(String.valueOf(selectedField.getX()));
            propY.setText(String.valueOf(selectedField.getY()));
            propW.setText(String.valueOf(selectedField.getWidth()));
            propH.setText(String.valueOf(selectedField.getHeight()));
            propSize.setValue(selectedField.getFontSize());
            propBold.setSelected(selectedField.isBold());

            char align = selectedField.getAlignment();
            if (align == 'L') btnPropAlignL.setSelected(true);
            else if (align == 'C') btnPropAlignC.setSelected(true);
            else if (align == 'R') btnPropAlignR.setSelected(true);

            boolean isBarcode = "Barcode".equals(selectedField.getFieldName());
            propBarcodeType.setEnabled(isBarcode);
            propFont.setEnabled(!isBarcode);
            propBold.setEnabled(!isBarcode);

            if (isBarcode) {
                propBarcodeType.setSelectedItem(currentLayout.getBarcodeType());
            }

        } else {
            propContent.setText("");
            propPrefix.setText("");
            propX.setText(""); propY.setText(""); propW.setText(""); propH.setText("");
            btnPropAlignL.setSelected(false);
            btnPropAlignC.setSelected(false);
            btnPropAlignR.setSelected(false);
        }
    }

    private void updateSelectedFieldFromProps() {
        if (selectedField == null) return;
        try {
            int x = Integer.parseInt(propX.getText());
            int y = Integer.parseInt(propY.getText());
            int w = Integer.parseInt(propW.getText());
            int h = Integer.parseInt(propH.getText());
            int size = (Integer) propSize.getValue();

            selectedField.setBounds(x, y, w, h);
            selectedField.setFontSize(size);
            selectedField.setBold(propBold.isSelected());

            if (selectedField.isStatic()) {
                selectedField.setTextContent(propContent.getText());
            } else if (!"Barcode".equals(selectedField.getFieldName())) {
                selectedField.setPrefix(propPrefix.getText());
            }

            if (btnPropAlignL.isSelected()) selectedField.setAlignment('L');
            else if (btnPropAlignC.isSelected()) selectedField.setAlignment('C');
            else if (btnPropAlignR.isSelected()) selectedField.setAlignment('R');

            if ("Barcode".equals(selectedField.getFieldName())) {
                currentLayout.setBarcodeType((String)propBarcodeType.getSelectedItem());
                selectedField.refreshBarcode();
            }

            stickerPanel.repaint();
        } catch (Exception e) {
            // Ignore invalid parsing during typing
        }
    }

    private void removeSelectedField() {
        if (selectedField != null) {
            stickerPanel.remove(selectedField);
            stickerPanel.repaint();
            selectField(null);
        }
    }

    private void quickAlign(char type) {
        if (selectedField == null) return;
        int pW = stickerPanel.getWidth();
        int pH = stickerPanel.getHeight();
        int fW = selectedField.getWidth();
        int fH = selectedField.getHeight();

        switch(type) {
            case 'L': selectedField.setLocation(0, selectedField.getY()); break;
            case 'C':
                selectedField.setLocation((pW - fW) / 2, selectedField.getY());
                btnPropAlignC.setSelected(true);
                selectedField.setAlignment('C');
                break;
            case 'R': selectedField.setLocation(pW - fW, selectedField.getY()); break;
            case 'T': selectedField.setLocation(selectedField.getX(), 0); break;
        }
        propX.setText(""+selectedField.getX());
        propY.setText(""+selectedField.getY());
    }

    private void saveTemplate() {
        try {
            LayoutSettings ls = new LayoutSettings();

            ls.setBarcodeType((String)propBarcodeType.getSelectedItem());
            if (ls.getBarcodeType() == null) ls.setBarcodeType("Code128");

            DraggableField bc = findBarcode();
            if (bc != null) {
                ls.setBarcodeWidth(bc.getWidth() / MM_TO_POINTS);
                ls.setBarcodeHeight(bc.getHeight() / MM_TO_POINTS);
            } else {
                ls.setBarcodeWidth(50);
                ls.setBarcodeHeight(15);
            }

            ls.setFontSize(10);

            ls.setStickersPerRow(Integer.parseInt(txtCols.getText()));

            try {
                ls.setHorizontalGapMM(Double.parseDouble(txtColGap.getText()));
                ls.setMarginLeft(Double.parseDouble(txtMarginLeft.getText()));
                ls.setMarginTop(Double.parseDouble(txtMarginTop.getText()));
            } catch(Exception e) {
                ls.setHorizontalGapMM(2.0); ls.setMarginLeft(0); ls.setMarginTop(0);
            }

            double stickerW = Double.parseDouble(txtStickerW.getText());
            double stickerH = Double.parseDouble(txtStickerH.getText());

            ls.setStickerWidth(stickerW);
            ls.setStickerHeight(stickerH);

            String pType = (String)comboPageType.getSelectedItem();
            ls.setPageType(pType);

            try {
                ls.setPageWidth(Double.parseDouble(txtSheetW.getText()));
            } catch(Exception e) {
                ls.setPageWidth(210.0);
            }

            Map<String, FieldSettings> fieldMap = new LinkedHashMap<>();
            for (Component c : stickerPanel.getComponents()) {
                if (c instanceof DraggableField) {
                    DraggableField df = (DraggableField) c;
                    String key = df.isStatic() ? df.getFieldName() : df.getFieldName();
                    fieldMap.put(key, new FieldSettings(true, df.getX(), df.getY(), df.getFontSize(), df.getAlignment(), df.getPrefix()));
                }
            }

            String xml = JRXMLGenerator.generate(ls, fieldMap);

            File fileToSave = currentFile;
            if (fileToSave == null) {
                String name = JOptionPane.showInputDialog(this, "Enter Filename (e.g. sticker.jrxml):");
                if (name == null) return;
                if (!name.endsWith(".jrxml")) name += ".jrxml";
                 fileToSave = new File(System.getProperty("user.home") + "/Retail_POS_Files/BarcodeTemplates/" + name);
            }

            try (FileWriter fw = new FileWriter(fileToSave)) {
                fw.write(xml);
            }

            BarcodePreviewGenerator.generatePreview(fileToSave);

            JOptionPane.showMessageDialog(this, "Saved to " + fileToSave.getName());
            currentFile = fileToSave;
            lblCurrentFile.setText(currentFile.getName());

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving: " + e.getMessage());
        }
    }

    private void previewTemplate() {
        try {
            saveTemplate(); // Save first to ensure current changes are used
            if (currentFile == null) return;

            // Use a dummy list for preview
            List<Item> dummyItems = new ArrayList<>();
            Item item = new Item();
            item.setIname("Preview Item");
            item.setMrp(100.00);
            item.setRprice(90.00);
            item.setWprice(80.00);
            item.setBatch("B123");
            item.setSize("L");
            item.setMfg("01/01/2025");
            item.setExp("31/12/2025");
            item.setCompanyname("My Company");
            item.setBarcode("12345678");
            dummyItems.add(item);

            // Reuse JasperBarcodePrinter logic but we need to bypass the printer dialog if it has one.
            // JasperBarcodePrinter.print(dummyItems) shows dialog and prints.
            // We want to SHOW on screen.

            // Let's copy the logic here for Viewer

            JRXMLParser.TemplateData meta = JRXMLParser.parse(currentFile);
            int bw = (int)(meta.layout.getBarcodeWidth() * 2.83465);
            int bh = (int)(meta.layout.getBarcodeHeight() * 2.83465);
            String bType = meta.layout.getBarcodeType();

            List<Map<String, ?>> dataList = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("Iname", item.getIname());
            map.put("MRP", String.format("%.2f", item.getMrp()));
            map.put("RPrice", String.format("%.2f", item.getRprice()));
            map.put("WPrice", String.format("%.2f", item.getWprice()));
            map.put("Batch", item.getBatch());
            map.put("Size", item.getSize());
            map.put("MFG", item.getMfg());
            map.put("EXP", item.getExp());
            map.put("CompanyName", item.getCompanyname());
            map.put("Barcode", item.getBarcode());

            if (item.getBarcode() != null) {
                BufferedImage img = BarcodeUtils.generateCodeImage(item.getBarcode(), bType, bw, bh);
                map.put("BarcodeImage", img);
            }
            dataList.add(map);

            JasperReport report = JasperCompileManager.compileReport(currentFile.getAbsolutePath());
            JasperPrint print = JasperFillManager.fillReport(report, new HashMap<>(), new JRMapCollectionDataSource(dataList));

            JDialog previewDialog = new JDialog(BarcodeDesigner.this, "Preview", true);
            previewDialog.setLayout(new BorderLayout());

            JRViewer viewer = new JRViewer(print);
            previewDialog.add(viewer, BorderLayout.CENTER);

            previewDialog.setSize(900, 700);
            previewDialog.setLocationRelativeTo(BarcodeDesigner.this);
            previewDialog.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Preview Error: " + e.getMessage());
        }
    }

    private void loadTemplateDialog() {
        JFileChooser fc = new JFileChooser(System.getProperty("user.home") + "/Retail_POS_Files/BarcodeTemplates/");
        fc.setFileFilter(new FileNameExtensionFilter("JasperReports (*.jrxml)", "jrxml"));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            loadFromFile(fc.getSelectedFile());
        }
    }

    private void loadFromFile(File f) {
        try {
            barcodepack.jasper.JRXMLParser.TemplateData data = barcodepack.jasper.JRXMLParser.parse(f);
            currentLayout = data.layout;

            stickerPanel.removeAll();

            for (Map.Entry<String, FieldSettings> entry : data.fields.entrySet()) {
                String name = entry.getKey();
                FieldSettings fs = entry.getValue();

                DraggableField df = new DraggableField(name);
                df.setLocation(fs.getX(), fs.getY());
                df.setFontSize(fs.getFontSize());
                df.setAlignment(fs.getAlignment());
                df.setPrefix(fs.getPrefix());

                if (name.startsWith("STATIC:")) {
                    df.setTextContent(name.split(":")[1]);
                    df.setSize(100, 20);
                } else if ("Barcode".equals(name)) {
                    df.setSize((int)(currentLayout.getBarcodeWidth()*MM_TO_POINTS), (int)(currentLayout.getBarcodeHeight()*MM_TO_POINTS));
                } else {
                    df.setSize(150, 20);
                }
                stickerPanel.add(df);
            }

            currentFile = f;
            lblCurrentFile.setText(f.getName());

            // Restore UI inputs
            txtCols.setText(""+currentLayout.getStickersPerRow());

            if (currentLayout.getStickerWidth() > 0) {
                txtStickerW.setText(""+currentLayout.getStickerWidth());
                txtStickerH.setText(""+currentLayout.getStickerHeight());
            } else {
                txtStickerW.setText("50");
                txtStickerH.setText("30");
            }

            txtSheetW.setText(""+currentLayout.getPageWidth());
            txtColGap.setText(""+currentLayout.getHorizontalGapMM());
            txtMarginLeft.setText(""+currentLayout.getMarginLeft());
            txtMarginTop.setText(""+currentLayout.getMarginTop());

            if (currentLayout.getPageType() != null) {
                comboPageType.setSelectedItem(currentLayout.getPageType());
            }

            // CRITICAL: Ensure stickerPanel is resized immediately
            updateStickerPanelSize();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load: " + e.getMessage());
        }
    }

    private DraggableField findBarcode() {
        for(Component c : stickerPanel.getComponents()) {
            if (c instanceof DraggableField && "Barcode".equals(((DraggableField)c).getFieldName())) {
                return (DraggableField)c;
            }
        }
        return null;
    }

    public static String[] getFields() {
        return new String[]{"CompanyName", "Barcode", "BarcodeNumber", "Iname", "MRP", "RPrice", "WPrice", "Batch", "Size", "MFG", "EXP"};
    }

    // ==========================================
    // INNER CLASSES
    // ==========================================

    class StickerPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (btnGrid.isSelected()) {
                g.setColor(new Color(230, 230, 230));
                int step = (int)(10 * MM_TO_POINTS); // 10mm grid
                if (step < 5) step = 20;
                for (int x = 0; x < getWidth(); x += step) g.drawLine(x, 0, x, getHeight());
                for (int y = 0; y < getHeight(); y += step) g.drawLine(0, y, getWidth(), y);
            }
        }
    }

    class DraggableField extends JPanel {
        private static final int HANDLE_SIZE = 8;
        private String fieldName;
        private String textContent;
        private String prefix = "";
        private JLabel lblContent;
        private boolean active = false;
        private char alignment = 'L';
        private boolean bold = false;

        // Resizing State
        private Point dragStartPoint;
        private Rectangle startBounds;
        private int resizeDirection = -1; // -1=Move, 0=NW, 1=N, 2=NE, 3=E, 4=SE, 5=S, 6=SW, 7=W

        public DraggableField(String name) {
            this.fieldName = name;
            setLayout(new BorderLayout());
            setBackground(new Color(0,0,0,0));
            setOpaque(false);

            lblContent = new JLabel();
            lblContent.setHorizontalAlignment(SwingConstants.CENTER);
            add(lblContent, BorderLayout.CENTER);

            if (name.startsWith("STATIC:")) {
                textContent = name.split(":")[1];
                lblContent.setText(textContent);
                lblContent.setOpaque(false);
            } else if ("Barcode".equals(name)) {
                refreshBarcode();
                setOpaque(true);
                setBackground(Color.WHITE);
            } else {
                updateText();
                lblContent.setOpaque(true);
                lblContent.setBackground(new Color(240, 240, 255));
                lblContent.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            }

            // Padding for handles? No, we draw on top.
            setBorder(BorderFactory.createEmptyBorder());

            MouseAdapter ma = new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    selectField(DraggableField.this);
                    dragStartPoint = e.getLocationOnScreen();
                    startBounds = getBounds();
                    resizeDirection = getCursorDirection(e.getPoint());
                }

                public void mouseDragged(MouseEvent e) {
                    if (dragStartPoint == null) return;

                    Point currentPoint = e.getLocationOnScreen();
                    int dx = currentPoint.x - dragStartPoint.x;
                    int dy = currentPoint.y - dragStartPoint.y;

                    int newX = startBounds.x;
                    int newY = startBounds.y;
                    int newW = startBounds.width;
                    int newH = startBounds.height;

                    if (resizeDirection == -1) { // Move
                        newX += dx;
                        newY += dy;
                    } else { // Resize
                        // Directions: 0=NW, 1=N, 2=NE, 3=E, 4=SE, 5=S, 6=SW, 7=W
                        if (resizeDirection == 0 || resizeDirection == 6 || resizeDirection == 7) { // West side
                            newX += dx;
                            newW -= dx;
                        }
                        if (resizeDirection == 0 || resizeDirection == 1 || resizeDirection == 2) { // North side
                            newY += dy;
                            newH -= dy;
                        }
                        if (resizeDirection == 2 || resizeDirection == 3 || resizeDirection == 4) { // East side
                            newW += dx;
                        }
                        if (resizeDirection == 4 || resizeDirection == 5 || resizeDirection == 6) { // South side
                            newH += dy;
                        }
                    }

                    // Minimum size
                    if (newW < 10) newW = 10;
                    if (newH < 10) newH = 10;

                    // Snap Logic
                    if (btnSnap.isSelected()) {
                        int grid = (int)(5 * MM_TO_POINTS);
                        newX = Math.round((float)newX / grid) * grid;
                        newY = Math.round((float)newY / grid) * grid;
                        // Also snap width/height? Usually yes for grid system
                        newW = Math.round((float)newW / grid) * grid;
                        newH = Math.round((float)newH / grid) * grid;
                        if (newW < grid) newW = grid;
                        if (newH < grid) newH = grid;
                    }

                    setBounds(newX, newY, newW, newH);

                    // Update property panel live
                    propX.setText(""+newX);
                    propY.setText(""+newY);
                    propW.setText(""+newW);
                    propH.setText(""+newH);

                    revalidate();
                    repaint();
                }

                public void mouseMoved(MouseEvent e) {
                    if (active) {
                        int dir = getCursorDirection(e.getPoint());
                        setCursor(Cursor.getPredefinedCursor(getCursorType(dir)));
                    } else {
                        setCursor(Cursor.getDefaultCursor());
                    }
                }
            };
            addMouseListener(ma);
            addMouseMotionListener(ma);
        }

        private int getCursorDirection(Point p) {
            if (!active) return -1;
            int w = getWidth();
            int h = getHeight();
            int hs = HANDLE_SIZE;

            // Corners
            if (new Rectangle(0, 0, hs, hs).contains(p)) return 0; // NW
            if (new Rectangle(w-hs, 0, hs, hs).contains(p)) return 2; // NE
            if (new Rectangle(w-hs, h-hs, hs, hs).contains(p)) return 4; // SE
            if (new Rectangle(0, h-hs, hs, hs).contains(p)) return 6; // SW

            // Sides
            if (new Rectangle(hs, 0, w-2*hs, hs).contains(p)) return 1; // N
            if (new Rectangle(w-hs, hs, hs, h-2*hs).contains(p)) return 3; // E
            if (new Rectangle(hs, h-hs, w-2*hs, hs).contains(p)) return 5; // S
            if (new Rectangle(0, hs, hs, h-2*hs).contains(p)) return 7; // W

            return -1; // Move
        }

        private int getCursorType(int dir) {
            switch(dir) {
                case 0: return Cursor.NW_RESIZE_CURSOR;
                case 1: return Cursor.N_RESIZE_CURSOR;
                case 2: return Cursor.NE_RESIZE_CURSOR;
                case 3: return Cursor.E_RESIZE_CURSOR;
                case 4: return Cursor.SE_RESIZE_CURSOR;
                case 5: return Cursor.S_RESIZE_CURSOR;
                case 6: return Cursor.SW_RESIZE_CURSOR;
                case 7: return Cursor.W_RESIZE_CURSOR;
                default: return Cursor.MOVE_CURSOR;
            }
        }

        @Override
        protected void paintBorder(Graphics g) {
            super.paintBorder(g);
            if (active) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.BLUE);
                g2.setStroke(new BasicStroke(1));
                g2.drawRect(0, 0, getWidth()-1, getHeight()-1);

                // Draw Handles
                int w = getWidth();
                int h = getHeight();
                int hs = HANDLE_SIZE;
                int halfHs = hs / 2;

                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, hs, hs); // NW
                g2.fillRect(w/2 - halfHs, 0, hs, hs); // N
                g2.fillRect(w-hs, 0, hs, hs); // NE
                g2.fillRect(w-hs, h/2 - halfHs, hs, hs); // E
                g2.fillRect(w-hs, h-hs, hs, hs); // SE
                g2.fillRect(w/2 - halfHs, h-hs, hs, hs); // S
                g2.fillRect(0, h-hs, hs, hs); // SW
                g2.fillRect(0, h/2 - halfHs, hs, hs); // W

                g2.setColor(Color.BLACK);
                g2.drawRect(0, 0, hs, hs); // NW
                g2.drawRect(w/2 - halfHs, 0, hs, hs); // N
                g2.drawRect(w-hs, 0, hs, hs); // NE
                g2.drawRect(w-hs, h/2 - halfHs, hs, hs); // E
                g2.drawRect(w-hs, h-hs, hs, hs); // SE
                g2.drawRect(w/2 - halfHs, h-hs, hs, hs); // S
                g2.drawRect(0, h-hs, hs, hs); // SW
                g2.drawRect(0, h/2 - halfHs, hs, hs); // W

                g2.dispose();
            }
        }

        public boolean isStatic() { return fieldName.startsWith("STATIC:"); }
        public String getFieldName() { return fieldName; }
        public String getTextContent() { return textContent; }
        public void setTextContent(String t) { this.textContent = t; lblContent.setText(t); }

        public String getPrefix() { return prefix; }
        public void setPrefix(String p) {
            this.prefix = p;
            updateText();
        }

        private void updateText() {
            if (!isStatic() && !"Barcode".equals(fieldName)) {
                if (prefix != null && !prefix.isEmpty()) {
                    lblContent.setText(prefix + "${" + fieldName + "}");
                } else {
                    lblContent.setText("${" + fieldName + "}");
                }
            }
        }

        public void setActive(boolean b) {
            this.active = b;
            repaint(); // Trigger paintBorder
        }

        public int getFontSize() {
            return lblContent.getFont().getSize();
        }

        public void setFontSize(int s) {
            Font f = lblContent.getFont();
            int style = bold ? Font.BOLD : Font.PLAIN;
            lblContent.setFont(new Font(f.getName(), style, s));
        }

        public boolean isBold() { return bold; }
        public void setBold(boolean b) {
            this.bold = b;
            setFontSize(getFontSize()); // Refresh font with new style
        }

        public char getAlignment() { return alignment; }
        public void setAlignment(char a) {
            this.alignment = a;
            switch(a) {
                case 'L': lblContent.setHorizontalAlignment(SwingConstants.LEFT); break;
                case 'C': lblContent.setHorizontalAlignment(SwingConstants.CENTER); break;
                case 'R': lblContent.setHorizontalAlignment(SwingConstants.RIGHT); break;
            }
        }

        public void refreshBarcode() {
            if (!"Barcode".equals(fieldName)) return;
            try {
                 int w = getWidth();
                 int h = getHeight();
                 if (w<=0) { w=100; h=40; }
                 String type = (String)propBarcodeType.getSelectedItem();
                 if (type == null) type = "Code128";

                 BufferedImage img = BarcodeUtils.generateCodeImage("123456", type, w, h);
                 lblContent.setIcon(new ImageIcon(img));
                 lblContent.setText(null);
            } catch (Exception e) {
                 lblContent.setIcon(null);
                 lblContent.setText("BARCODE");
            }
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            if ("Barcode".equals(fieldName)) refreshBarcode();
        }
    }
}
