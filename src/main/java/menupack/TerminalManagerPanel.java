package menupack;

import com.selrom.db.DataUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class TerminalManagerPanel extends JPanel {

    private JTable authorizedTable;
    private DefaultTableModel authorizedModel;

    private JTable requestsTable;
    private DefaultTableModel requestsModel;

    private DataUtil util;
    private JLabel statusLabel;
    private int maxTerminals = 1; // Default to 1 (Server only)

    public TerminalManagerPanel() {
        this.util = new DataUtil();
        fetchLicenseLimit();
        initComponents();
        loadData();
        loadRequests();
    }

    private void fetchLicenseLimit() {
        try {
            ResultSet rs = util.doQuery("SELECT eno FROM setting_user LIMIT 1");
            if (rs != null && rs.next()) {
                String enoStr = rs.getString("eno");
                if (enoStr != null && !enoStr.trim().isEmpty()) {
                    try {
                        maxTerminals = Integer.parseInt(enoStr.trim());
                    } catch (NumberFormatException e) {
                        maxTerminals = 1;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching license limit: " + e.getMessage());
            maxTerminals = 1;
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 250));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Terminal Management");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(50, 50, 100));

        statusLabel = new JLabel("Loading...");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(statusLabel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 12));

        // --- Tab 1: Authorized Terminals ---
        JPanel authorizedPanel = new JPanel(new BorderLayout());

        String[] authColumns = {"Hardware ID", "Terminal Name", "First Connected"};
        authorizedModel = new DefaultTableModel(authColumns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        authorizedTable = new JTable(authorizedModel);
        setupTable(authorizedTable);

        authorizedPanel.add(new JScrollPane(authorizedTable), BorderLayout.CENTER);

        // Buttons for Authorized
        JPanel authBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Add Terminal");
        JButton btnRename = new JButton("Rename");
        JButton btnRemove = new JButton("Remove");

        btnAdd.addActionListener((ActionEvent e) -> addTerminal());
        btnRename.addActionListener((ActionEvent e) -> renameTerminal());
        btnRemove.addActionListener((ActionEvent e) -> removeSelectedTerminal());

        authBtnPanel.add(btnAdd);
        authBtnPanel.add(btnRename);
        authBtnPanel.add(btnRemove);
        authorizedPanel.add(authBtnPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Authorized Terminals", authorizedPanel);

        // --- Tab 2: Pending Requests ---
        JPanel requestsPanel = new JPanel(new BorderLayout());

        String[] reqColumns = {"Hardware ID", "Machine Name", "Request Time"};
        requestsModel = new DefaultTableModel(reqColumns, 0) {
             @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        requestsTable = new JTable(requestsModel);
        setupTable(requestsTable);

        requestsPanel.add(new JScrollPane(requestsTable), BorderLayout.CENTER);

        // Buttons for Requests
        JPanel reqBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnApprove = new JButton("Approve");
        JButton btnReject = new JButton("Reject");
        JButton btnRefresh = new JButton("Refresh");

        btnApprove.setForeground(new Color(0, 100, 0));
        btnReject.setForeground(new Color(150, 0, 0));

        btnApprove.addActionListener(e -> approveRequest());
        btnReject.addActionListener(e -> rejectRequest());
        btnRefresh.addActionListener(e -> loadRequests());

        reqBtnPanel.add(btnRefresh);
        reqBtnPanel.add(btnReject);
        reqBtnPanel.add(btnApprove);
        requestsPanel.add(reqBtnPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Pending Requests", requestsPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Footer / Helper
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        JLabel tipLabel = new JLabel("<html><b>Tip:</b> If a client cannot connect, check 'Pending Requests' or manually add their System ID.</html>");
        tipLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        tipLabel.setForeground(Color.DARK_GRAY);

        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnClose = new JButton("Close");
        btnClose.addActionListener(e -> closePanelAction());
        closePanel.add(btnClose);

        footerPanel.add(tipLabel, BorderLayout.WEST);
        footerPanel.add(closePanel, BorderLayout.EAST);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private JButton btnClose;

    public void setCloseButtonVisible(boolean visible) {
        if (btnClose != null) {
            btnClose.setVisible(visible);
        }
    }

    private void closePanelAction() {
        Window win = SwingUtilities.getWindowAncestor(this);
        if (win != null) {
            win.dispose();
        }
    }

    private void setupTable(JTable table) {
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableColumnModel cm = table.getColumnModel();
        cm.getColumn(0).setPreferredWidth(250); // HWID
        cm.getColumn(1).setPreferredWidth(150); // Name
        cm.getColumn(2).setPreferredWidth(150); // Date
    }

    private void updateStatus() {
        int currentClients = authorizedModel.getRowCount();
        int available = maxTerminals - currentClients;

        String status = String.format("<html>Terminal Usage: <b>%d</b> / <b>%d</b> used. Available Slots: <b>%d</b>.</html>",
                currentClients, maxTerminals, available < 0 ? 0 : available);
        statusLabel.setText(status);

        if (available <= 0) {
            statusLabel.setForeground(new Color(200, 0, 0));
        } else {
            statusLabel.setForeground(new Color(0, 100, 0));
        }
    }

    private void loadData() {
        authorizedModel.setRowCount(0);
        try {
            ResultSet rs = util.doQuery("SELECT hwid, label, first_seen FROM authorized_terminals");
            while (rs != null && rs.next()) {
                authorizedModel.addRow(new Object[]{
                    rs.getString("hwid"),
                    rs.getString("label"),
                    rs.getString("first_seen")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading authorized terminals: " + e.getMessage());
        }
        updateStatus();
    }

    private void loadRequests() {
        requestsModel.setRowCount(0);
        try {
            // Check if table exists first to avoid error spam
            ResultSet check = util.doQuery("SELECT count(*) FROM information_schema.tables WHERE table_name = 'access_requests'");
            if (check.next() && check.getInt(1) > 0) {
                 ResultSet rs = util.doQuery("SELECT hwid, label, request_time FROM access_requests");
                 while (rs != null && rs.next()) {
                     requestsModel.addRow(new Object[]{
                         rs.getString("hwid"),
                         rs.getString("label"),
                         rs.getString("request_time")
                     });
                 }
            }
        } catch (Exception e) {
            // Ignore (table might not exist yet)
        }
    }

    private void addTerminal() {
        int currentClients = authorizedModel.getRowCount();
        if (currentClients >= maxTerminals) {
             JOptionPane.showMessageDialog(this,
                     "License Limit Reached (" + maxTerminals + " Terminals).\nCannot add more terminals.",
                     "Limit Reached", JOptionPane.WARNING_MESSAGE);
             return;
        }

        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JDialog inputDialog = new JDialog(parentWindow, "Add New Terminal", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        inputDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtHwid = new JTextField(25);

        try {
            String hdd = login.get_hard_disc_Id();
            String mb = login.get_mother_board_id();
            txtHwid.setText(hdd + ":" + mb);
        } catch (Exception e) {
            // Ignore if unable to fetch
        }

        JTextField txtLabel = new JTextField(25);
        txtLabel.setText("Terminal " + (currentClients + 1));

        gbc.gridx = 0; gbc.gridy = 0;
        inputDialog.add(new JLabel("Hardware ID:"), gbc);
        gbc.gridx = 1;
        inputDialog.add(txtHwid, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputDialog.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        inputDialog.add(txtLabel, gbc);

        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");
        JPanel pnl = new JPanel(); pnl.add(btnSave); pnl.add(btnCancel);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        inputDialog.add(pnl, gbc);

        btnCancel.addActionListener(e -> inputDialog.dispose());

        btnSave.addActionListener(e -> {
            String hwid = txtHwid.getText().trim();
            String label = txtLabel.getText().trim();
            if (hwid.isEmpty() || label.isEmpty()) return;

            try {
                // Check Duplicate
                ResultSet rs = util.doQuery("SELECT count(*) FROM authorized_terminals WHERE hwid = '" + hwid.replace("'", "''") + "'");
                if (rs.next() && rs.getInt(1) > 0) {
                     JOptionPane.showMessageDialog(inputDialog, "Already authorized.", "Duplicate", JOptionPane.WARNING_MESSAGE);
                     return;
                }

                String query = "INSERT INTO authorized_terminals (hwid, label) VALUES ('" + hwid.replace("'", "''") + "', '" + label.replace("'", "''") + "')";
                if (util.doManipulation(query) > 0) {
                     JOptionPane.showMessageDialog(this, "Terminal authorized.");
                     inputDialog.dispose();
                     loadData();

                     // Also remove from requests if it was there
                     util.doManipulation("DELETE FROM access_requests WHERE hwid = '" + hwid.replace("'", "''") + "'");
                     loadRequests();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(inputDialog, "Error: " + ex.getMessage());
            }
        });

        inputDialog.pack();
        inputDialog.setLocationRelativeTo(this);
        inputDialog.setVisible(true);
    }

    private void renameTerminal() {
        int row = authorizedTable.getSelectedRow();
        if (row == -1) return;

        String currentLabel = (String) authorizedModel.getValueAt(row, 1);
        String hwid = (String) authorizedModel.getValueAt(row, 0);

        String newLabel = JOptionPane.showInputDialog(this, "New Name:", currentLabel);
        if (newLabel != null && !newLabel.trim().isEmpty()) {
            try {
                String query = "UPDATE authorized_terminals SET label = '" + newLabel.trim().replace("'", "''") + "' WHERE hwid = '" + hwid.replace("'", "''") + "'";
                util.doManipulation(query);
                loadData();
            } catch (Exception e) {}
        }
    }

    private void removeSelectedTerminal() {
        int row = authorizedTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a terminal first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String label = (String) authorizedModel.getValueAt(row, 1);
        String hwid = (String) authorizedModel.getValueAt(row, 0);

        int opt = JOptionPane.showConfirmDialog(this, "Remove '" + label + "'?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            try {
                util.doManipulation("DELETE FROM authorized_terminals WHERE hwid = '" + hwid.replace("'", "''") + "'");
                loadData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void approveRequest() {
        int row = requestsTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a request to approve.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int currentClients = authorizedModel.getRowCount();
        if (currentClients >= maxTerminals) {
             JOptionPane.showMessageDialog(this,
                     "License Limit Reached (" + maxTerminals + " Terminals).\nPlease remove an existing terminal first.",
                     "Limit Reached", JOptionPane.WARNING_MESSAGE);
             return;
        }

        String hwid = (String) requestsModel.getValueAt(row, 0);
        String label = (String) requestsModel.getValueAt(row, 1); // Machine Name

        try {
            // Insert into authorized
            String query = "INSERT INTO authorized_terminals (hwid, label) VALUES ('" + hwid.replace("'", "''") + "', 'Terminal " + (currentClients + 1) + " (" + label.replace("'", "''") + ")')";
            int count = util.doManipulation(query);

            if (count > 0) {
                // Delete from requests
                util.doManipulation("DELETE FROM access_requests WHERE hwid = '" + hwid.replace("'", "''") + "'");
                JOptionPane.showMessageDialog(this, "Terminal Approved Successfully.");
                loadData();
                loadRequests();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rejectRequest() {
        int row = requestsTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a request to reject.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String hwid = (String) requestsModel.getValueAt(row, 0);
        int opt = JOptionPane.showConfirmDialog(this, "Reject and delete this request?", "Confirm", JOptionPane.YES_NO_OPTION);

        if (opt == JOptionPane.YES_OPTION) {
            try {
                util.doManipulation("DELETE FROM access_requests WHERE hwid = '" + hwid.replace("'", "''") + "'");
                loadRequests();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
}
