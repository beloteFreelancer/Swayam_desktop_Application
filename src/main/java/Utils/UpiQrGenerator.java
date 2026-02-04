package Utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

public class UpiQrGenerator {

    public UpiQrGenerator() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter PhonePe mobile number: ");
        String mobileNumber = scanner.nextLine().trim();

        System.out.print("Enter recipient name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter amount (e.g. 100.00): ");
        String amount = scanner.nextLine().trim();

        String upiId = mobileNumber + "@ybl"; // PhonePe UPI handle
        String upiUrl = String.format("upi://pay?pa=%s&am=%s&cu=INR", upiId, amount);

        String filename = "upi_qr.png";
        generateQR(upiUrl, filename, 300, 300);

        File qrFile = new File(filename);
        System.out.println("✅ QR Code generated successfully.");
        System.out.println("📍 Location: " + qrFile.getAbsolutePath());
    }

    private static void generateQR(String text, String filePath, int width, int height) {
        QRCodeWriter qrWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        } catch (WriterException | IOException e) {
            System.err.println("❌ Failed to generate QR Code: " + e.getMessage());
        }
    }

    public static BufferedImage generateQRImage(String text, int width, int height) {
        QRCodeWriter qrWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

}
