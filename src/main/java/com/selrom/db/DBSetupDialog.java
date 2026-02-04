package com.selrom.db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBSetupDialog extends JDialog {

    private JTextField txtHost;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private boolean saved = false;

    // The database name is fixed for this application
    private static final String DB_NAME = "BBS_main";

    public DBSetupDialog(Frame parent, boolean modal) {
        super(parent, "First Time Setup - Database Configuration", modal);
        initComponents();
        pack();
        setLocationRelativeTo(null); // Center on screen
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        JPanel panelParams = new JPanel(new GridLayout(3, 2, 5, 5));
        panelParams.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelParams.add(new JLabel("Database Host IP:"));
        txtHost = new JTextField("localhost"); // Default to localhost
        panelParams.add(txtHost);

        panelParams.add(new JLabel("MySQL Username:"));
        txtUser = new JTextField("root"); // Default to root
        panelParams.add(txtUser);

        panelParams.add(new JLabel("MySQL Password:"));
        txtPass = new JPasswordField("");
        panelParams.add(txtPass);

        add(panelParams, BorderLayout.CENTER);

        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnTest = new JButton("Test Connection");
        JButton btnSave = new JButton("Save & Start");
        JButton btnExit = new JButton("Exit");

        btnTest.addActionListener(this::testConnection);
        btnSave.addActionListener(this::saveConfig);
        btnExit.addActionListener(e -> System.exit(0));

        panelBtns.add(btnTest);
        panelBtns.add(btnSave);
        panelBtns.add(btnExit);

        add(panelBtns, BorderLayout.SOUTH);
    }

    private String getUrl() {
        String host = txtHost.getText().trim();
        if (host.isEmpty())
            host = "localhost";
        // Construct standard MySQL URL
        return "jdbc:mysql://" + host + ":3306/" + DB_NAME + "?createDatabaseIfNotExist=true";
    }

    private void testConnection(ActionEvent e) {
        String url = getUrl();
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                JOptionPane.showMessageDialog(this, "Connection Successful!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Connection Failed:\n" + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveConfig(ActionEvent e) {
        String url = getUrl();
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());

        // Validate connection before saving
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                // If successful, save config using Database singleton
                Database.getInstance().saveConfig(url, user, pass);
                saved = true;
                dispose();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Cannot Save. Connection Failed:\n" + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
