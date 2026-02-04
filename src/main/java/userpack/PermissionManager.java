package userpack;

import com.selrom.db.DataUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PermissionManager {

    private DataUtil util;
    private Map<String, Boolean> permissionsCache;
    private String currentUser;

    // MASTER LIST: Defines all available permissions in the system.
    // Used by user_permissions form to populate the list and menu_form to validate.
    public static final String[] ALL_FEATURES = {
            "Sales",
            "Purchase",
            "Estimate",
            "Item",
            "Stock",
            "PO",
            "Packing",
            "Accounts",
            "Supplier",
            "Customer",
            "HR",
            "Payroll",      // New
            "Attendance",   // New
            "Tax Reports",
            "SMS",
            "Email",
            "Manual Barcode",
            "Loyalty",
            "Backup",
            "Home Page Display",
            "MIS",          // New
            "Setting"       // New
    };

    public PermissionManager(DataUtil util) {
        this.util = util;
        this.permissionsCache = new HashMap<>();
    }

    public void loadPermissions(String username) {
        this.currentUser = username;
        this.permissionsCache.clear();
        try {
            String query = "select fname, option1 from users_permissions where user='" + username + "'";
            ResultSet rs = util.doQuery(query);
            while (rs.next()) {
                String feature = rs.getString(1);
                String allowed = rs.getString(2);
                permissionsCache.put(feature, "Yes".equalsIgnoreCase(allowed));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasPermission(String featureName) {
        // Default to false if not found
        return permissionsCache.getOrDefault(featureName, false);
    }

    // Check if user is Admin or Super Admin (who usually have all permissions)
    public boolean isAdmin(String userType) {
        return "Administrator".equalsIgnoreCase(userType)
                || "Super Admin".equalsIgnoreCase(userType)
                || "License Owner".equalsIgnoreCase(userType);
    }

    /**
     * Helper to get the master list.
     * Useful for the User Permissions UI to ensure all checkboxes are generated.
     */
    public static String[] getAllFeatures() {
        return ALL_FEATURES;
    }
}
