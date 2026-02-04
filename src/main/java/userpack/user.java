package userpack;

import com.selrom.db.DataUtil;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class user extends javax.swing.JInternalFrame {

    public user(DataUtil util) {
        super("Users Management", true, true, true, true);
        initComponents(util);
    }

    private void initComponents(DataUtil util) {
        setTitle("Users Management");
        this.setSize(1022, 536);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);

        setLayout(new BorderLayout());
        UserPanel panel = new UserPanel(util);
        add(panel, BorderLayout.CENTER);
    }
}
