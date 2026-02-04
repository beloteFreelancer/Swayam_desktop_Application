package Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileStructureInitializer {

    public static void initialize() {
        // 1. Standard App Path (e.g., C:/Retail_POS_Files on Windows)
        String appPath = AppConfig.getAppPath();
        initializePath(appPath);

        // 2. User Home Path (For compatibility with modules hardcoded to use user.home)
        String userHomePath = System.getProperty("user.home") + File.separator + "Retail_POS_Files";
        // On Linux/Mac, appPath is usually userHomePath, so we avoid duplicate work.
        // On Windows, appPath is C:/Retail_POS_Files, so we ensure userHomePath also exists.
        if (!arePathsEqual(appPath, userHomePath)) {
            initializePath(userHomePath);
        }
    }

    private static boolean arePathsEqual(String p1, String p2) {
        try {
            return new File(p1).getCanonicalPath().equals(new File(p2).getCanonicalPath());
        } catch (IOException e) {
            return p1.equals(p2);
        }
    }

    private static void initializePath(String basePath) {
        // 1. Ensure Directories Exist
        createDirectory(basePath);
        createDirectory(basePath + "/Config_Files");
        createDirectory(basePath + "/BarcodeTemplates");
        createDirectory(basePath + "/Templates/Barcode");
        createDirectory(basePath + "/Backup");

        // 2. Ensure Files Exist
        createFileIfNotExists(basePath + "/Config_Files/terminal.properties", "");
        createFileIfNotExists(basePath + "/Config_Files/Backup_Folder.properties", "drive=C\nfolder=Retail_POS_Files/Backup");
    }

    private static void createDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                System.out.println("Created directory: " + path);
            } else {
                // Check if it exists now (concurrency) or failed
                if (!dir.exists()) {
                    System.err.println("Failed to create directory: " + path);
                }
            }
        }
    }

    private static void createFileIfNotExists(String path, String defaultContent) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                boolean created = file.createNewFile();
                if (created) {
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write(defaultContent);
                    }
                    System.out.println("Created file: " + path);
                }
            } catch (IOException e) {
                System.err.println("Failed to create file: " + path + " - " + e.getMessage());
            }
        }
    }
}
