import com.formdev.flatlaf.FlatLightLaf;
import menupack.login;
import com.selrom.db.Database;
import com.selrom.db.DBSetupDialog; // Import the new dialog
import javax.swing.*;

public class BBS_Software {

    public static void main(String[] args) {
        // --- NEW: Initialize File Structure ---
        Utils.FileStructureInitializer.initialize();
        // --------------------------------------

        try {
            // This is the line that themes your app
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        // --- NEW: Check if Database is Configured ---
        if (!Database.getInstance().isConfigured()) {
            // Config file missing. Launch Setup Dialog.
            DBSetupDialog setup = new DBSetupDialog(null, true);
            setup.setVisible(true);

            // If user closed dialog without saving, exit app
            if (!setup.isSaved()) {
                System.exit(0);
            }
        }
        // --------------------------------------------

        // Initialize Database
        try {
            Database.getInstance().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Initialization Failed: " + e.getMessage());
            System.exit(1);
        }

        login.login_main(args);
    }
}
