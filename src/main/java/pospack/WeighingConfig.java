package pospack;

import com.fazecast.jSerialComm.SerialPort;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeighingConfig extends JDialog {

    private JComboBox<String> portCombo;
    private JComboBox<String> baudRateCombo;
    private JComboBox<String> commandCombo;
    private JButton saveButton, closeButton, testButton, refreshButton;
    private String drive, folder;
    private SerialPort[] ports;

    private static final Pattern WEIGHT_PATTERN = Pattern.compile("(\\d+\\.\\d+|\\d+)");

    public WeighingConfig(java.awt.Frame parent, boolean modal, String drive, String folder) {
        super(parent, modal);
        this.drive = drive;
        this.folder = folder;
        initComponents();
        loadSettings();
        refreshPorts();
    }

    private void initComponents() {
        setLayout(null);
        setTitle("Weighing Scale Configuration");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel portLabel = new JLabel("Port:");
        portLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        portLabel.setBounds(30, 30, 80, 30);
        add(portLabel);

        portCombo = new JComboBox<>();
        portCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        portCombo.setBounds(120, 30, 200, 30);
        add(portCombo);

        refreshButton = new JButton("R");
        refreshButton.setToolTipText("Refresh Ports");
        refreshButton.setBounds(330, 30, 50, 30);
        refreshButton.addActionListener(e -> refreshPorts());
        add(refreshButton);

        JLabel baudLabel = new JLabel("Baud Rate:");
        baudLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        baudLabel.setBounds(30, 80, 80, 30);
        add(baudLabel);

        baudRateCombo = new JComboBox<>(new String[]{"9600", "4800", "2400", "19200", "38400", "57600", "115200"});
        baudRateCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        baudRateCombo.setBounds(120, 80, 200, 30);
        add(baudRateCombo);

        JLabel cmdLabel = new JLabel("Command:");
        cmdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        cmdLabel.setBounds(30, 130, 80, 30);
        add(cmdLabel);

        commandCombo = new JComboBox<>(new String[]{"W", "SI", "S", "R", "READ", "PRINT", "\\r\\n"});
        commandCombo.setEditable(true);
        commandCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        commandCombo.setBounds(120, 130, 200, 30);
        add(commandCombo);

        testButton = new JButton("Test Connection");
        testButton.setFont(new Font("Arial", Font.PLAIN, 14));
        testButton.setBounds(120, 180, 200, 40);
        testButton.addActionListener(e -> testConnection());
        add(testButton);

        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 14));
        if(ClassLoader.getSystemResource("icons/save45.png") != null) {
            saveButton.setIcon(new ImageIcon(ClassLoader.getSystemResource("icons/save45.png")));
        }
        saveButton.setBounds(50, 240, 140, 50);
        saveButton.addActionListener(e -> saveSettings());
        add(saveButton);

        closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        if(ClassLoader.getSystemResource("icons/close45.png") != null) {
            closeButton.setIcon(new ImageIcon(ClassLoader.getSystemResource("icons/close45.png")));
        }
        closeButton.setBounds(220, 240, 140, 50);
        closeButton.addActionListener(e -> dispose());
        add(closeButton);
    }

    private void refreshPorts() {
        portCombo.removeAllItems();
        ports = SerialPort.getCommPorts();
        if (ports.length == 0) {
            portCombo.addItem("No Ports Found");
            portCombo.setEnabled(false);
        } else {
            portCombo.setEnabled(true);
            for (SerialPort port : ports) {
                String desc = port.getDescriptivePortName();
                String name = port.getSystemPortName();
                portCombo.addItem(name + " (" + desc + ")");
            }
        }
    }

    private void loadSettings() {
        try (FileInputStream m = new FileInputStream(folder + "/Config_Files/terminal.properties")) {
            Properties p = new Properties();
            p.load(m);

            String savedPort = p.getProperty("weigh_port", "");
            String savedBaud = p.getProperty("weigh_baud", "9600");
            String savedCmd = p.getProperty("weigh_command", "");

            for (int i = 0; i < portCombo.getItemCount(); i++) {
                String item = portCombo.getItemAt(i);
                if (item.startsWith(savedPort)) {
                    portCombo.setSelectedIndex(i);
                    break;
                }
            }
            baudRateCombo.setSelectedItem(savedBaud);
            commandCombo.setSelectedItem(savedCmd);
        } catch (IOException e) {
        }
    }

    private void saveSettings() {
        try {
            Properties p = new Properties();
            String filePath = folder + "/Config_Files/terminal.properties";

            try (FileInputStream m = new FileInputStream(filePath)) {
                p.load(m);
            } catch (IOException e) {}

            String selectedPortItem = (String) portCombo.getSelectedItem();
            String cleanPortName = "";

            if (ports != null && ports.length > 0 && portCombo.getSelectedIndex() >= 0 && portCombo.getSelectedIndex() < ports.length) {
                 cleanPortName = ports[portCombo.getSelectedIndex()].getSystemPortName();
            } else {
                 if (selectedPortItem != null && !selectedPortItem.equals("No Ports Found")) {
                    cleanPortName = selectedPortItem.split(" ")[0];
                 }
            }

            p.setProperty("weigh_port", cleanPortName);
            p.setProperty("weigh_baud", (String) baudRateCombo.getSelectedItem());

            String cmd = (String) commandCombo.getSelectedItem();
            if (cmd == null) cmd = "";
            p.setProperty("weigh_command", cmd);

            try (FileOutputStream out = new FileOutputStream(filePath)) {
                p.store(out, null);
            }

            JOptionPane.showMessageDialog(this, "Settings Saved Successfully", "Saved", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving settings: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void testConnection() {
        String selectedPortItem = (String) portCombo.getSelectedItem();
        if (selectedPortItem == null || selectedPortItem.equals("No Ports Found")) {
            JOptionPane.showMessageDialog(this, "No valid port selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get Parameters
        String pName = "";
        if (ports != null && ports.length > 0 && portCombo.getSelectedIndex() >= 0 && portCombo.getSelectedIndex() < ports.length) {
             pName = ports[portCombo.getSelectedIndex()].getSystemPortName();
        } else {
             pName = selectedPortItem.split(" ")[0];
        }
        final String portName = pName;
        final int baudRate = Integer.parseInt((String) baudRateCombo.getSelectedItem());
        String cmdTemp = (String) commandCombo.getSelectedItem();
        if (cmdTemp == null) cmdTemp = "";
        if (cmdTemp.equals("\\r\\n")) cmdTemp = "\r\n";
        final String command = cmdTemp;

        // UI Feedback
        testButton.setEnabled(false);
        testButton.setText("Testing...");

        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return performTest(portName, baudRate, command);
            }

            @Override
            protected void done() {
                testButton.setEnabled(true);
                testButton.setText("Test Connection");
                try {
                    String result = get();
                    if (result.startsWith("SUCCESS:")) {
                         JOptionPane.showMessageDialog(WeighingConfig.this, "Connection Successful!\n" + result.substring(8), "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else if (result.startsWith("WARNING:")) {
                         JOptionPane.showMessageDialog(WeighingConfig.this, result.substring(8), "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                         JOptionPane.showMessageDialog(WeighingConfig.this, result, "Connection Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    private String performTest(String portName, int baudRate, String command) {
        SerialPort port = SerialPort.getCommPort(portName);
        port.setBaudRate(baudRate);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 2000, 0);

        if (port.isOpen()) port.closePort();

        try {
            if (port.openPort()) {
                if (!command.isEmpty()) {
                    port.writeBytes(command.getBytes(), command.length());
                    try { Thread.sleep(50); } catch (InterruptedException ie) {}
                }

                long startTime = System.currentTimeMillis();
                StringBuilder response = new StringBuilder();
                boolean dataReceived = false;
                byte[] buffer = new byte[128];

                while (System.currentTimeMillis() - startTime < 2000) {
                    if (port.bytesAvailable() > 0) {
                        int len = port.readBytes(buffer, buffer.length);
                        if(len > 0) {
                            String s = new String(buffer, 0, len);
                            response.append(s);
                            dataReceived = true;
                        }
                    }
                    try { Thread.sleep(50); } catch (InterruptedException ie) {}
                }

                port.closePort();

                if (dataReceived) {
                    return "SUCCESS:Received Raw Data: " + response.toString();
                } else {
                    return "WARNING:Connection Opened, but no data received.\nCheck Baud Rate and Command.";
                }

            } else {
                return "Failed to open port " + portName + "\nPort might be busy or unavailable.";
            }
        } catch (Exception ex) {
            if (port.isOpen()) port.closePort();
            return "Error during test: " + ex.getMessage();
        }
    }
}
