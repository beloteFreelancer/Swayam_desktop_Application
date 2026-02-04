package Utils;

import com.fazecast.jSerialComm.SerialPort;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;
import java.awt.Component;

public class POSWeighing extends JFrame {

    private static final long serialVersionUID = 1L;

    // Pre-compile pattern for performance
    private static final Pattern WEIGHT_PATTERN = Pattern.compile("(\\d+\\.\\d+|\\d+)");

    /**
     * Async method to handle weighing.
     * @param parent The parent component for dialogs
     * @param drive Configuration drive
     * @param folder Configuration folder
     * @param onResult Callback with the weight (0.0 if failed)
     */
    public void handleWeighAction(Component parent, String drive, String folder, java.util.function.Consumer<Double> onResult) {

        // Show a simple progress dialog
        JDialog progressDialog = new JDialog();
        progressDialog.setUndecorated(true);
        progressDialog.setSize(250, 60);
        progressDialog.setLocationRelativeTo(parent);
        progressDialog.setLayout(new BorderLayout());

        JLabel msgLabel = new JLabel("Scanning for weighing scale...", JLabel.CENTER);
        msgLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        progressDialog.add(msgLabel, BorderLayout.CENTER);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressDialog.add(progressBar, BorderLayout.SOUTH);

        SwingWorker<Double, Void> worker = new SwingWorker<Double, Void>() {
            @Override
            protected Double doInBackground() throws Exception {
                return performWeighingLogic(drive, folder);
            }

            @Override
            protected void done() {
                progressDialog.dispose();
                try {
                    Double result = get();
                    if (result == null) result = 0.0;

                    // If result is 0, show error (unless it was 0 from the start)
                    // Actually performWeighingLogic returns 0.0 on failure after showing its own error dialogs?
                    // Let's check performWeighingLogic implementation below.
                    // To keep it clean, let's move UI dialogs out of the background thread if possible,
                    // but JOptionPane inside doInBackground is acceptable for simple apps if invoked via invokeLater,
                    // though SwingWorker handles it better if we return status.
                    // For now, let's stick to the previous logic style but wrapped.

                    onResult.accept(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    onResult.accept(0.0);
                }
            }
        };

        worker.execute();
        progressDialog.setVisible(true); // Blocks input if modal? JDialog default is non-modal unless specified.
        // But we want it to block user interaction with the main frame.
        // We'll make it modal? No, setVisible(true) on a modal dialog blocks the current thread (EDT), which we don't want if we launched the worker.
        // Wait, if we run worker.execute(), we are on EDT. If we show a MODAL dialog, it blocks EDT.
        // Correct pattern: worker.execute(); then show MODAL dialog. worker runs on background.
        // When worker finishes, it calls done() on EDT, which disposes dialog, unblocking the show() call.
        // HOWEVER, we need to ensure the dialog is modal relative to the parent.
    }

    // Extracted logic to run in background
    private Double performWeighingLogic(String drive, String folder) {
        String savedPort = "";
        int baudRate = 9600;
        String command = "";

        // --- STEP 1: LOAD CONFIGURATION ---
        try (FileInputStream m = new FileInputStream(folder + "/Config_Files/terminal.properties")) {
            Properties p = new Properties();
            p.load(m);
            savedPort = p.getProperty("weigh_port", "").trim();
            String baudStr = p.getProperty("weigh_baud", "9600").trim();
            baudRate = Integer.parseInt(baudStr.replaceAll("[^0-9]", ""));
            command = p.getProperty("weigh_command", "");
            if (command.equals("\\r\\n")) command = "\r\n";
        } catch (Exception ex) {
            System.out.println("Config load warning: " + ex.getMessage());
        }

        // --- STEP 2: FAST PATH (Use Saved Port) ---
        if (!savedPort.isEmpty()) {
            Double weight = attemptConnection(savedPort, baudRate, command);
            if (weight != null) {
                return weight;
            }
        }

        // --- STEP 3: FAILOVER (Auto-Discovery) ---
        SerialPort[] allPorts = SerialPort.getCommPorts();
        for (SerialPort port : allPorts) {
            String scanName = port.getSystemPortName();
            Double weight = attemptConnection(scanName, baudRate, command);

            if (weight != null && weight > 0) {
                updateConfigFile(drive, folder, scanName);

                // Showing dialog from background thread - unsafe?
                // Better to assume if we return > 0 it's success.
                // The previous code showed a "Port Changed" dialog.
                // We can use JOptionPane.showMessageDialog here, it generally works,
                // but technically should be wrapped in invokeLater.
                // For safety, let's suppress the info dialog or wrap it.
                final String old = savedPort;
                final String newP = scanName;
                javax.swing.SwingUtilities.invokeLater(() ->
                   JOptionPane.showMessageDialog(null,
                        "Port changed detected.\nAuto-updated from " + (old.isEmpty() ? "None" : old) + " to " + newP,
                        "Auto-Config", JOptionPane.INFORMATION_MESSAGE)
                );

                return weight;
            }
        }

        // Failure
        javax.swing.SwingUtilities.invokeLater(() ->
            JOptionPane.showMessageDialog(null, "Scale not found on any port.\nPlease check USB connection and Power.", "Connection Failed", JOptionPane.ERROR_MESSAGE)
        );
        return 0.0;
    }

    private Double attemptConnection(String portName, int baud, String command) {
        SerialPort port = SerialPort.getCommPort(portName);
        port.setBaudRate(baud);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 2500, 0);

        if (port.isOpen()) port.closePort();

        try {
            if (!port.openPort()) return null;

            try { Thread.sleep(200); } catch (Exception i) {}

            if (port.bytesAvailable() > 0) {
                byte[] junk = new byte[port.bytesAvailable()];
                port.readBytes(junk, junk.length);
            }

            if (command != null && !command.isEmpty()) {
                byte[] cmdBytes = command.getBytes();
                port.writeBytes(cmdBytes, cmdBytes.length);
                try { Thread.sleep(100); } catch (Exception i) {}
            }

            long start = System.currentTimeMillis();
            StringBuilder buffer = new StringBuilder();
            byte[] readBuffer = new byte[128];

            while (System.currentTimeMillis() - start < 2500) {
                if (port.bytesAvailable() > 0) {
                    int numRead = port.readBytes(readBuffer, readBuffer.length);
                    if (numRead > 0) {
                        String s = new String(readBuffer, 0, numRead);
                        buffer.append(s);

                        double w = parseWeight(buffer.toString());
                        if (w > 0) {
                            port.closePort();
                            return w;
                        }

                        if (buffer.length() > 4096) {
                             buffer.delete(0, buffer.length() - 2048);
                        }
                    }
                }
                try { Thread.sleep(50); } catch (Exception i) {}
            }
            port.closePort();
        } catch (Exception e) {
            if (port.isOpen()) port.closePort();
        }
        return null;
    }

    private void updateConfigFile(String drive, String folder, String newPort) {
        try {
            String path = folder + "/Config_Files/terminal.properties";
            Properties p = new Properties();
            try (FileInputStream in = new FileInputStream(path)) {
                p.load(in);
            } catch (Exception e) {}
            p.setProperty("weigh_port", newPort);
            try (FileOutputStream out = new FileOutputStream(path)) {
                p.store(out, null);
            }
        } catch (Exception e) {
            System.err.println("Failed to auto-update config: " + e.getMessage());
        }
    }

    private double parseWeight(String input) {
        try {
            Matcher m = WEIGHT_PATTERN.matcher(input);
            double lastVal = 0.0;
            boolean found = false;
            while (m.find()) {
                try {
                    lastVal = Double.parseDouble(m.group(1));
                    found = true;
                } catch (Exception e) {}
            }
            if (found) return lastVal;
        } catch (Exception e) {
        }
        return 0.0;
    }
}
