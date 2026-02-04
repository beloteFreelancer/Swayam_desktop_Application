package com.selrom.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.util.Properties;
import javax.swing.JDialog;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.swing.JRViewer;

public class ReportPreview {

    public static void showPreview(JasperPrint jasperPrint) {
        showPreview(jasperPrint, null, null);
    }

    private static double convertToPixels(double value, String unit) {
        if ("mm".equals(unit)) {
            return value * 72.0 / 25.4;
        } else if ("in".equals(unit)) {
            return value * 72.0;
        } else {
            return value;
        }
    }

    public static void showPreview(JasperPrint jasperPrint, String drive, String folder) {
        // Check if preview is enabled
        if (drive != null && folder != null) {
            try {
                Properties p = new Properties();
                try (FileInputStream in = new FileInputStream(folder + "/Config_Files/terminal.properties")) {
                    p.load(in);
                }
                String enabled = p.getProperty("enable_preview", "true");
                if ("false".equalsIgnoreCase(enabled)) {
                    // Print directly
                    try {
                        String widthStr = p.getProperty("preview_width");
                        String heightStr = p.getProperty("preview_height");
                        String unit = p.getProperty("preview_unit", "mm");

                        if (widthStr != null && !widthStr.isEmpty()) {
                            double width = Double.parseDouble(widthStr);
                            double height = 0;
                            if (heightStr != null && !heightStr.isEmpty()) {
                                height = Double.parseDouble(heightStr);
                            }

                            // Calculate scales
                            double targetPixelsX = convertToPixels(width, unit);
                            double targetPixelsY = (height > 0) ? convertToPixels(height, unit) : 0;

                            int originalWidth = jasperPrint.getPageWidth();
                            int originalHeight = jasperPrint.getPageHeight();
                            if (originalWidth == 0) originalWidth = 1;
                            if (originalHeight == 0) originalHeight = 1;

                            double scaleX = targetPixelsX / originalWidth;
                            double scaleY = (height > 0) ? (targetPixelsY / originalHeight) : 1.0; // Default to 1.0 if no height

                            PrinterJob job = PrinterJob.getPrinterJob();
                            ScalableJRViewer.ExposedJRPrinterAWT jrPrinter = new ScalableJRViewer.ExposedJRPrinterAWT(jasperPrint);
                            job.setPrintable(new ScalableJRViewer.ScaledPrintable(jrPrinter, scaleX, scaleY, originalWidth));

                            if (job.printDialog()) {
                                job.print();
                            }
                        } else {
                            // Default print
                            JasperPrintManager.printReport(jasperPrint, true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Fallback
                        JasperPrintManager.printReport(jasperPrint, true);
                    }
                    return;
                }
            } catch (Exception e) {
                // If error reading properties, default to showing preview
                e.printStackTrace();
            }
        }

        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle("Print Preview");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(900, 700);
        dialog.setLayout(new BorderLayout());

        // Use custom viewer with scaling support
        ScalableJRViewer viewer = new ScalableJRViewer(jasperPrint, drive, folder);
        dialog.add(viewer, BorderLayout.CENTER);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                viewer.saveSettings();
            }
        });

        // Center the dialog
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - dialog.getWidth()) / 2;
        int y = (screenSize.height - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);

        dialog.setVisible(true);
    }
}
