package Utils;

import com.selrom.db.DataUtil;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.function.Consumer;
import javax.swing.*;

public class UPIPaymentDialog extends JDialog {

    private JTextField amountField;
    private JButton generateButton, successButton, printButton, closeButton;
    private JLabel qrLabel;
    private String upiId;
    private double amount;

    public UPIPaymentDialog(Frame parent, double amount, Consumer<Double> callBack) {
        super(parent, "UPI Payment", true);
        this.amount = amount;
        setLayout(new BorderLayout());
        setSize(400, 500);
        setLocationRelativeTo(parent);

        upiId = fetchUpiIdFromDatabase(); // Read from setting_bill
        if (upiId == null || upiId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "UPI ID not configured in settings!", "Missing UPI ID", JOptionPane.WARNING_MESSAGE);
            dispose(); // close the dialog
            return;
        }
        amountField = new JTextField(15);
        generateButton = new JButton("Generate");
        successButton = new JButton("Success");
        printButton = new JButton("Print QR");
        closeButton = new JButton("Close");
        qrLabel = new JLabel("", SwingConstants.CENTER);
        amountField.setText(String.valueOf(amount));
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Amount:"));
        topPanel.add(amountField);
        topPanel.add(generateButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(successButton);
        bottomPanel.add(printButton);
        bottomPanel.add(closeButton);

        add(topPanel, BorderLayout.NORTH);
        add(qrLabel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        generateButton.addActionListener(e -> {
            String amountText = amountField.getText().trim();
            if (!amountText.isEmpty()) {
                try {
                    double amt = Double.parseDouble(amountText);
                    this.amount = amt;
                    DecimalFormat df = new DecimalFormat("#.00");
                    String upiUrl = "upi://pay?pa=" + upiId + "&am=" + df.format(amt) + "&cu=INR";
                    BufferedImage qr = UpiQrGenerator.generateQRImage(upiUrl, 250, 250);
                    if (qr != null) {
                        qrLabel.setIcon(new ImageIcon(qr));
                    }
                } catch (NumberFormatException ex) {
                    this.amount = 0;
                    JOptionPane.showMessageDialog(this, "Invalid amount");
                }
            }
        });

        printButton.addActionListener(e -> {
            ImageIcon icon = (ImageIcon) qrLabel.getIcon();
            if (icon != null) {
                printImage(icon.getImage());
            }
        });

        successButton.addActionListener(e -> {
            callBack.accept(amount);
            dispose(); // or do further success logic
        });

        closeButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void printImage(Image image) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex != 0) {
                return Printable.NO_SUCH_PAGE;
            }
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            g2d.drawImage(image, 100, 100, 200, 200, null);
            return Printable.PAGE_EXISTS;
        });
        if (job.printDialog()) { // ✅ Show printer selection dialog
            try {
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }

    }

    private String fetchUpiIdFromDatabase() {
        String upi = "";
        try {
            ResultSet rs = new DataUtil().doQuery("SELECT upi_id FROM setting_bill LIMIT 1;");
            if (rs.next()) {
                upi = rs.getString("upi_id");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return upi;
    }

    public double getAmount() {
        return amount;
    }
}
