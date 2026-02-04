package Utils;

import java.io.File;
import java.awt.Desktop;

public class AppConfig {
    public static String getAppPath() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "C:/Retail_POS_Files";
        } else {
            // For Linux/Mac (and Sandbox environment), use user home or relative path
            String home = System.getProperty("user.home");
            return home + File.separator + "Retail_POS_Files";
        }
    }

    public static void openFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                } else {
                    System.out.println("Desktop is not supported. Cannot open file: " + filePath);
                }
            } else {
                System.out.println("File does not exist: " + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
