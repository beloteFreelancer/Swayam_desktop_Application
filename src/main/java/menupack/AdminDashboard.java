package menupack;

import com.selrom.db.DataUtil;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import userpack.UserPanel;
import Utils.ColorConstants;

public class AdminDashboard extends JInternalFrame {

    private String userType;

    public AdminDashboard(DataUtil util, String userType) {
        super("Administrator Dashboard", true, true, true, true);
        this.userType = userType;
        initComponents(util);
    }

    private void initComponents(DataUtil util) {
        setTitle("Administrator Dashboard - BBS Software");
        setResizable(true);
        setMaximizable(true);
        setClosable(false);
        setIconifiable(true);

        javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            setFrameIcon(icon);
        }

        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // 1. User Management Panel (Restricted)
        // Only show if user is License Owner or Super Admin
        if ("License Owner".equalsIgnoreCase(userType) || "Super Admin".equalsIgnoreCase(userType)) {
            UserPanel userPanel = new UserPanel(util);
            userPanel.setCloseButtonVisible(false);
            tabbedPane.addTab("User Management", null, userPanel, "Manage Users, Passwords and Permissions");
        }

        // 2. Terminal Management Panel (Visible to authorized admins)
        TerminalManagerPanel terminalPanel = new TerminalManagerPanel();
        terminalPanel.setCloseButtonVisible(false);
        tabbedPane.addTab("Terminal Management", null, terminalPanel, "Manage Authorized Terminals");

        add(tabbedPane, BorderLayout.CENTER);

        setSize(1100, 700);
    }
}
