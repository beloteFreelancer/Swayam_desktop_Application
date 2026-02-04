package com.selrom.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.print.JRPrinterAWT;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleGraphics2DExporterOutput;
import net.sf.jasperreports.export.SimpleGraphics2DReportConfiguration;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.swing.JRViewerListener;
import net.sf.jasperreports.swing.JRViewerEvent;

public class ScalableJRViewer extends JRViewer {

    private JSpinner widthSpinner;
    private JSpinner heightSpinner;
    private JCheckBox chkLockAspectRatio;
    private JCheckBox chkShowPreview;
    private JComboBox<String> unitComboBox;
    private JLabel orgWidthLabel;
    private final JasperPrint jasperPrint;
    private SelectionLabel previewLabel;
    private JScrollPane previewScrollPane;
    private JButton btnPrintSelection;
    private String drive;
    private String folder;
    private boolean isInternalUpdate = false;


    public ScalableJRViewer(JasperPrint jrPrint) {
        super(jrPrint);
        this.jasperPrint = jrPrint;
        customizeToolbar();
    }

    public ScalableJRViewer(JasperPrint jrPrint, String drive, String folder) {
        super(jrPrint);
        this.jasperPrint = jrPrint;
        this.drive = drive;
        this.folder = folder;
        customizeToolbar();
    }

    private void customizeToolbar() {
        // Create custom toolbar panel
        JPanel customToolbar = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        customToolbar.add(new JLabel("Width:"));

        widthSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 2000.0, 1.0));
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(widthSpinner, "#.##");
        widthSpinner.setEditor(editor);
        java.awt.Dimension d = widthSpinner.getPreferredSize();
        d.width = 70;
        widthSpinner.setPreferredSize(d);
        widthSpinner.setMaximumSize(d);
        customToolbar.add(widthSpinner);

        customToolbar.add(new JLabel("Height:"));

        heightSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 2000.0, 1.0));
        JSpinner.NumberEditor editorH = new JSpinner.NumberEditor(heightSpinner, "#.##");
        heightSpinner.setEditor(editorH);
        heightSpinner.setPreferredSize(d);
        heightSpinner.setMaximumSize(d);
        customToolbar.add(heightSpinner);

        chkLockAspectRatio = new JCheckBox("Lock Ratio");
        chkLockAspectRatio.setSelected(true);
        chkLockAspectRatio.addActionListener(e -> {
            if (chkLockAspectRatio.isSelected()) {
                updateHeightFromWidth();
            }
        });
        customToolbar.add(chkLockAspectRatio);

        unitComboBox = new JComboBox<>(new String[]{"mm", "in", "px"});

        java.awt.Dimension dCombo = unitComboBox.getPreferredSize();
        dCombo.width = 60;
        unitComboBox.setPreferredSize(dCombo);
        unitComboBox.setMaximumSize(dCombo);
        customToolbar.add(unitComboBox);

        orgWidthLabel = new JLabel();
        customToolbar.add(orgWidthLabel);

        chkShowPreview = new JCheckBox("Show Preview");
        chkShowPreview.setSelected(true);
        customToolbar.add(chkShowPreview);

        JButton btnPrintScaled = new JButton("Print Scaled");
        btnPrintScaled.setToolTipText("Print scaled to the specified width/height");
        btnPrintScaled.addActionListener(e -> {
            printScaled();
        });
        customToolbar.add(btnPrintScaled);

        btnPrintSelection = new JButton("Print Selection");
        btnPrintSelection.setToolTipText("Select an area on the preview and click this to print only that area.");
        btnPrintSelection.addActionListener(e -> printSelection());
        btnPrintSelection.setEnabled(false);
        customToolbar.add(btnPrintSelection);

        // Reorganize toolbars
        this.remove(tlbToolBar);
        JPanel topPanel = new JPanel(new java.awt.GridLayout(2, 1));
        topPanel.add(tlbToolBar);
        topPanel.add(customToolbar);
        this.add(topPanel, java.awt.BorderLayout.NORTH);

        // Initialize Preview Area
        // Remove existing center component (pnlMain)
        java.awt.Component centerComp = ((java.awt.BorderLayout)getLayout()).getLayoutComponent(java.awt.BorderLayout.CENTER);
        if (centerComp != null) {
            this.remove(centerComp);
        }

        previewLabel = new SelectionLabel();
        // Use LEFT and TOP alignment to ensure (0,0) of the label matches (0,0) of the image.
        // This prevents coordinate shifts when the viewport is larger than the image (centering offsets).
        previewLabel.setHorizontalAlignment(JLabel.LEFT);
        previewLabel.setVerticalAlignment(JLabel.TOP);
        previewScrollPane = new JScrollPane(previewLabel);
        previewScrollPane.getViewport().setBackground(java.awt.Color.GRAY);
        this.add(previewScrollPane, java.awt.BorderLayout.CENTER);

        // Listeners
        widthSpinner.addChangeListener(e -> {
            if (!isInternalUpdate) {
                if (chkLockAspectRatio.isSelected()) {
                    updateHeightFromWidth();
                }
                updatePreview();
            }
        });

        heightSpinner.addChangeListener(e -> {
            if (!isInternalUpdate) {
                if (chkLockAspectRatio.isSelected()) {
                    updateWidthFromHeight();
                }
                updatePreview();
            }
        });

        unitComboBox.addActionListener(e -> {
            updateSpinnerFromUnit();
            updatePreview();
        });

        viewerContext.addListener(new JRViewerListener() {
            @Override
            public void viewerEvent(JRViewerEvent event) {
                if (event.getCode() == JRViewerEvent.EVENT_PAGE_CHANGED ||
                    event.getCode() == JRViewerEvent.EVENT_REPORT_LOADED) {
                    updatePreview();
                }
            }
        });

        // Load settings if available
        loadSettings();
    }

    private void loadSettings() {
        if (drive != null && folder != null) {
            try (FileInputStream in = new FileInputStream(folder + "/Config_Files/terminal.properties")) {
                Properties p = new Properties();
                p.load(in);
                String unit = p.getProperty("preview_unit", "mm");
                String width = p.getProperty("preview_width");
                String height = p.getProperty("preview_height");
                String enabled = p.getProperty("enable_preview", "true");

                chkShowPreview.setSelected("true".equalsIgnoreCase(enabled));
                unitComboBox.setSelectedItem(unit);

                if (width != null && !width.isEmpty()) {
                     widthSpinner.setValue(Double.parseDouble(width));
                } else {
                     updateSpinnerFromUnit(); // default from report
                }

                if (height != null && !height.isEmpty()) {
                    heightSpinner.setValue(Double.parseDouble(height));
                } else {
                     if (width == null || width.isEmpty()) {
                        // updateSpinnerFromUnit() already called
                     }
                }
            } catch (Exception e) {
                unitComboBox.setSelectedItem("mm"); // default
                updateSpinnerFromUnit();
            }
        } else {
            // Default behavior if no drive/folder
            unitComboBox.setSelectedItem("mm");
            // updateSpinnerFromUnit will be triggered by listener or call here
        }
        // Force update just in case
        if (widthSpinner.getValue().equals(0.0)) {
            updateSpinnerFromUnit();
        }
    }

    private void updateHeightFromWidth() {
        isInternalUpdate = true;
        double originalWidth = jasperPrint.getPageWidth();
        double originalHeight = jasperPrint.getPageHeight();
        if (originalWidth > 0) {
            double ratio = originalHeight / originalWidth;
            double newHeight = (Double) widthSpinner.getValue() * ratio;
            heightSpinner.setValue(newHeight);
        }
        isInternalUpdate = false;
    }

    private void updateWidthFromHeight() {
        isInternalUpdate = true;
        double originalWidth = jasperPrint.getPageWidth();
        double originalHeight = jasperPrint.getPageHeight();
        if (originalHeight > 0) {
            double ratio = originalWidth / originalHeight;
            double newWidth = (Double) heightSpinner.getValue() * ratio;
            widthSpinner.setValue(newWidth);
        }
        isInternalUpdate = false;
    }

    public void saveSettings() {
        if (drive != null && folder != null) {
            try {
                Properties p = new Properties();
                try (FileInputStream in = new FileInputStream(folder + "/Config_Files/terminal.properties")) {
                    p.load(in);
                } catch (IOException e) {
                    // ignore if missing
                }

                p.setProperty("enable_preview", chkShowPreview.isSelected() ? "true" : "false");
                p.setProperty("preview_unit", (String) unitComboBox.getSelectedItem());
                p.setProperty("preview_width", widthSpinner.getValue().toString());
                p.setProperty("preview_height", heightSpinner.getValue().toString());

                try (FileOutputStream out = new FileOutputStream(folder + "/Config_Files/terminal.properties")) {
                    p.store(out, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateSpinnerFromUnit() {
        String unit = (String) unitComboBox.getSelectedItem();
        if (unit == null) return;
        int pixelWidth = jasperPrint.getPageWidth();
        double convertedWidth = convertFromPixels(pixelWidth, unit);

        int pixelHeight = jasperPrint.getPageHeight();
        double convertedHeight = convertFromPixels(pixelHeight, unit);

        isInternalUpdate = true;
        widthSpinner.setValue(convertedWidth);
        heightSpinner.setValue(convertedHeight);
        isInternalUpdate = false;

        orgWidthLabel.setText(" (Org: " + String.format("%.2f", convertedWidth) + " x " + String.format("%.2f", convertedHeight) + ") ");
        if (chkLockAspectRatio.isSelected()) {
             updateHeightFromWidth();
        }
    }

    private void updatePreview() {
        try {
            // Clear selection when preview updates
            previewLabel.clearSelection();
            btnPrintSelection.setEnabled(false);

            double targetWidthValue = (Double) widthSpinner.getValue();
            double targetHeightValue = (Double) heightSpinner.getValue();
            String unit = (String) unitComboBox.getSelectedItem();

            double targetPixelsX = convertToPixels(targetWidthValue, unit);
            double targetPixelsY = convertToPixels(targetHeightValue, unit);

            int originalWidth = jasperPrint.getPageWidth();
            int originalHeight = jasperPrint.getPageHeight();
            if (originalWidth == 0) originalWidth = 1;
            if (originalHeight == 0) originalHeight = 1;

            double scaleX = targetPixelsX / originalWidth;
            double scaleY = targetPixelsY / originalHeight;

            int w = (int) Math.ceil(targetPixelsX);
            int h = (int) Math.ceil(targetPixelsY);
            if (w <= 0) w = 1;
            if (h <= 0) h = 1;
            // Prevent OOM by capping preview image size (e.g. ~2000x2000 is reasonable for preview)
            if (w > 3000) w = 3000;
            if (h > 3000) h = 3000;

            BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();

            // Fill background white (paper color)
            g2d.setColor(java.awt.Color.WHITE);
            g2d.fillRect(0, 0, w, h);

            // Set clip to the full image dimensions to prevent NPE in FrameDrawer (clipArea is null)
            g2d.setClip(0, 0, w, h);

            g2d.scale(scaleX, scaleY);

            JRGraphics2DExporter exporter = new JRGraphics2DExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            SimpleGraphics2DExporterOutput output = new SimpleGraphics2DExporterOutput();
            output.setGraphics2D(g2d);
            exporter.setExporterOutput(output);

            SimpleGraphics2DReportConfiguration config = new SimpleGraphics2DReportConfiguration();
            config.setPageIndex(viewerContext.getPageIndex());
            exporter.setConfiguration(config);

            exporter.exportReport();
            g2d.dispose();

            previewLabel.setIcon(new ImageIcon(image));
            previewLabel.revalidate();
            previewLabel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double convertFromPixels(double pixels, String unit) {
        if ("mm".equals(unit)) {
            return pixels * 25.4 / 72.0;
        } else if ("in".equals(unit)) {
            return pixels / 72.0;
        } else {
            return pixels;
        }
    }

    private double convertToPixels(double value, String unit) {
        if ("mm".equals(unit)) {
            return value * 72.0 / 25.4;
        } else if ("in".equals(unit)) {
            return value * 72.0;
        } else {
            return value;
        }
    }

    private void printScaled() {
        try {
            if (!chkShowPreview.isSelected()) {
                int result = JOptionPane.showConfirmDialog(this,
                    "Do you want to use these dimensions for default print?",
                    "Confirm Default Settings",
                    JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    saveSettings();
                } else {
                     // Disable preview but clear dimensions to use defaults
                    if (drive != null && folder != null) {
                        try {
                            Properties p = new Properties();
                            try (FileInputStream in = new FileInputStream(folder + "/Config_Files/terminal.properties")) {
                                p.load(in);
                            } catch (IOException e) { /* ignore */ }

                            p.setProperty("enable_preview", "false");
                            p.remove("preview_width");
                            p.remove("preview_height");

                            try (FileOutputStream out = new FileOutputStream(folder + "/Config_Files/terminal.properties")) {
                                p.store(out, null);
                            }
                        } catch (Exception e) { e.printStackTrace(); }
                    }
                }
            } else {
                saveSettings();
            }

            double targetWidthValue = (Double) widthSpinner.getValue();
            double targetHeightValue = (Double) heightSpinner.getValue();
            String unit = (String) unitComboBox.getSelectedItem();

            double targetPixelsX = convertToPixels(targetWidthValue, unit);
            double targetPixelsY = convertToPixels(targetHeightValue, unit);

            int originalWidth = jasperPrint.getPageWidth();
            int originalHeight = jasperPrint.getPageHeight();

            if (originalWidth == 0) originalWidth = 1;
            if (originalHeight == 0) originalHeight = 1;

            double scaleX = targetPixelsX / originalWidth;
            double scaleY = targetPixelsY / originalHeight;

            PrinterJob job = PrinterJob.getPrinterJob();

            ExposedJRPrinterAWT jrPrinter = new ExposedJRPrinterAWT(jasperPrint);

            // Wrap it to apply scaling
            job.setPrintable(new ScaledPrintable(jrPrinter, scaleX, scaleY, originalWidth));

            if (job.printDialog()) {
                job.print();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error printing: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Expose these for ReportPreview
    public static class ExposedJRPrinterAWT extends JRPrinterAWT {
        public ExposedJRPrinterAWT(JasperPrint jasperPrint) throws JRException {
            super(jasperPrint);
        }
    }

    public static class ScaledPrintable implements Printable {
        private final Printable delegate;
        private final double scaleX;
        private final double scaleY;
        private final int originalWidth;

        public ScaledPrintable(Printable delegate, double scaleX, double scaleY, int originalWidth) {
            this.delegate = delegate;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
            this.originalWidth = originalWidth;
        }

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            double effectiveScaleX = scaleX;
            double effectiveScaleY = scaleY;

            // Auto-fit to printable width if content exceeds it
            double contentWidth = originalWidth * scaleX;
            double printableWidth = pageFormat.getImageableWidth();

            if (contentWidth > printableWidth && printableWidth > 0) {
                 double adjustment = printableWidth / contentWidth;
                 effectiveScaleX = scaleX * adjustment;
                 effectiveScaleY = scaleY * adjustment;
            }

            if (effectiveScaleX != 1.0 || effectiveScaleY != 1.0) {
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.scale(effectiveScaleX, effectiveScaleY);
            }
            return delegate.print(graphics, pageFormat, pageIndex);
        }
    }

    private void printSelection() {
        Rectangle selection = previewLabel.getSelectionRect();
        if (selection == null || selection.width <= 0 || selection.height <= 0) {
            JOptionPane.showMessageDialog(this, "No selection to print.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Calculate scale based on the actual preview image size, not the spinner values.
            // This ensures we use the same scale as what the user selected on screen.
            int previewWidth = previewLabel.getIcon() != null ? previewLabel.getIcon().getIconWidth() : 0;
            int previewHeight = previewLabel.getIcon() != null ? previewLabel.getIcon().getIconHeight() : 0;

            if (previewWidth == 0) {
                 // Fallback to spinner calculation if no image (unlikely if selection exists)
                double targetWidthValue = (Double) widthSpinner.getValue();
                String unit = (String) unitComboBox.getSelectedItem();
                previewWidth = (int) Math.ceil(convertToPixels(targetWidthValue, unit));
            }

            int originalWidth = jasperPrint.getPageWidth();
            int originalHeight = jasperPrint.getPageHeight();
            if (originalWidth == 0) originalWidth = 1;
            if (originalHeight == 0) originalHeight = 1;

            double scaleX = (double) previewWidth / originalWidth;
            double scaleY = (double) previewHeight / originalHeight;

            PrinterJob job = PrinterJob.getPrinterJob();

            ExposedJRPrinterAWT jrPrinter = new ExposedJRPrinterAWT(jasperPrint);

            // Wrap to apply clip, translate, and scale
            job.setPrintable(new SelectionPrintable(jrPrinter, scaleX, scaleY, selection));

            if (job.printDialog()) {
                job.print();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error printing selection: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private static class SelectionPrintable implements Printable {
        private final Printable delegate;
        private final double scaleX;
        private final double scaleY;
        private final Rectangle selection;

        public SelectionPrintable(Printable delegate, double scaleX, double scaleY, Rectangle selection) {
            this.delegate = delegate;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
            this.selection = selection;
        }

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            Graphics2D g2d = (Graphics2D) graphics;

            // 1. Clip to the selection dimensions (at the origin of the paper)
            g2d.clipRect(0, 0, selection.width, selection.height);

            // 2. Translate so the selection's top-left corner moves to (0,0)
            g2d.translate(-selection.x, -selection.y);

            // 3. Scale the content (same as preview)
            if (scaleX != 1.0 || scaleY != 1.0) {
                g2d.scale(scaleX, scaleY);
            }

            return delegate.print(graphics, pageFormat, pageIndex);
        }
    }

    private class SelectionLabel extends JLabel {
        private Rectangle selectionRect;
        private Point startPoint;

        public SelectionLabel() {
            MouseAdapter mouseHandler = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (getIcon() == null) return;
                    startPoint = e.getPoint();
                    selectionRect = null;
                    repaint();
                    btnPrintSelection.setEnabled(false);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (getIcon() == null || startPoint == null) return;

                    // Calculate new point, constrained to image bounds
                    int iconWidth = getIcon().getIconWidth();
                    int iconHeight = getIcon().getIconHeight();

                    int currX = e.getX();
                    int currY = e.getY();

                    if (currX < 0) currX = 0;
                    if (currY < 0) currY = 0;
                    if (currX > iconWidth) currX = iconWidth;
                    if (currY > iconHeight) currY = iconHeight;

                    int x = Math.min(startPoint.x, currX);
                    int y = Math.min(startPoint.y, currY);
                    int width = Math.abs(currX - startPoint.x);
                    int height = Math.abs(currY - startPoint.y);

                    if (width > 0 && height > 0) {
                        selectionRect = new Rectangle(x, y, width, height);
                        btnPrintSelection.setEnabled(true);
                    } else {
                        selectionRect = null;
                        btnPrintSelection.setEnabled(false);
                    }
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    startPoint = null;
                }
            };
            addMouseListener(mouseHandler);
            addMouseMotionListener(mouseHandler);
        }

        public Rectangle getSelectionRect() {
            return selectionRect;
        }

        public void clearSelection() {
            selectionRect = null;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (selectionRect != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(50, 150, 255, 100)); // Semi-transparent blue
                g2d.fill(selectionRect);
                g2d.setColor(Color.BLUE);
                g2d.draw(selectionRect);
                g2d.dispose();
            }
        }
    }
}
