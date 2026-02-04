package menupack;

import java.awt.BorderLayout;
import java.awt.Window;
import javax.swing.JDialog;

public class TerminalManagerDialog extends JDialog {

    public TerminalManagerDialog(Window parent) {
        super(parent, "Manage Authorized Terminals", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        initComponents();
        setSize(750, 550);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        TerminalManagerPanel panel = new TerminalManagerPanel();
        add(panel, BorderLayout.CENTER);
    }
}
