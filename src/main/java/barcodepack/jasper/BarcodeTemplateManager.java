package barcodepack.jasper;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BarcodeTemplateManager extends JDialog {

    private JList<String> templateList;
    private DefaultListModel<String> listModel;
    private static final String TEMPLATE_DIR = System.getProperty("user.home") + "/Retail_POS_Files/BarcodeTemplates/";
    private static final String DEFAULT_FILE = TEMPLATE_DIR + "default_template.txt";
    private JLabel previewLabel;

    public BarcodeTemplateManager(Window owner) {
        super(owner, "Barcode Templates", ModalityType.APPLICATION_MODAL);
        setSize(900, 600); // Increased size for split pane
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        new File(TEMPLATE_DIR).mkdirs();

        // --- Main Content (Split Pane) ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.3); // Give more space to preview on resize

        // Left: Template List
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel listHeader = new JLabel("Templates");
        listHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        listHeader.setBorder(new EmptyBorder(0,0,5,0));
        leftPanel.add(listHeader, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        templateList = new JList<>(listModel);
        templateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        templateList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                lbl.setBorder(new EmptyBorder(5, 5, 5, 5));
                String name = (String) value;
                String defaultName = getSelectedTemplateName();
                if (name.equals(defaultName)) {
                    lbl.setText(name + " (Default)");
                    lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));
                    lbl.setForeground(new Color(0, 100, 0));
                }
                return lbl;
            }
        });

        templateList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updatePreview(templateList.getSelectedValue());
            }
        });

        leftPanel.add(new JScrollPane(templateList), BorderLayout.CENTER);
        splitPane.setLeftComponent(leftPanel);

        // Right: Preview
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel previewHeader = new JLabel("Preview");
        previewHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        previewHeader.setBorder(new EmptyBorder(0,0,5,0));
        rightPanel.add(previewHeader, BorderLayout.NORTH);

        previewLabel = new JLabel("Select a template to preview", SwingConstants.CENTER);
        previewLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        previewLabel.setBackground(Color.WHITE);
        previewLabel.setOpaque(true);

        // Wrap preview in scroll pane in case image is large
        rightPanel.add(new JScrollPane(previewLabel), BorderLayout.CENTER);
        splitPane.setRightComponent(rightPanel);

        add(splitPane, BorderLayout.CENTER);

        // --- Bottom Toolbar ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        JButton createBtn = new JButton("Create New");
        createBtn.setIcon(UIManager.getIcon("FileView.fileIcon")); // Use default icon if available

        JButton editBtn = new JButton("Edit Selected");
        JButton deleteBtn = new JButton("Delete");
        JButton selectBtn = new JButton("Set as Default");

        bottomPanel.add(createBtn);
        bottomPanel.add(editBtn);
        bottomPanel.add(deleteBtn);
        bottomPanel.add(Box.createHorizontalStrut(20));
        bottomPanel.add(selectBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        // --- Actions ---

        createBtn.addActionListener(e -> {
            try {
                BarcodeDesigner designer = new BarcodeDesigner(this, null);
                designer.setVisible(true);
                loadTemplates();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening designer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        editBtn.addActionListener(e -> {
            try {
                String selected = templateList.getSelectedValue();
                if (selected != null) {
                    File file = new File(TEMPLATE_DIR + selected);
                    BarcodeDesigner designer = new BarcodeDesigner(this, file);
                    designer.setVisible(true);
                    loadTemplates();
                    // Reselect the edited file
                    templateList.setSelectedValue(selected, true);
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a template to edit.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening designer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteBtn.addActionListener(e -> {
             String selected = templateList.getSelectedValue();
            if (selected != null) {
                if (JOptionPane.showConfirmDialog(this, "Delete " + selected + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    new File(TEMPLATE_DIR + selected).delete();
                    new File(TEMPLATE_DIR + selected.replace(".jrxml", ".png")).delete();
                    loadTemplates();
                }
            } else {
                 JOptionPane.showMessageDialog(this, "Please select a template to delete.");
            }
        });

        selectBtn.addActionListener(e -> {
            String selected = templateList.getSelectedValue();
            if (selected != null) {
                try {
                    Files.write(Paths.get(DEFAULT_FILE), selected.getBytes());
                    JOptionPane.showMessageDialog(this, "Default template set to: " + selected);
                    templateList.repaint(); // To update the "Default" label in renderer
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                 JOptionPane.showMessageDialog(this, "Please select a template to set as default.");
            }
        });

        loadTemplates();
    }

    private void loadTemplates() {
        listModel.clear();
        File dir = new File(TEMPLATE_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".jrxml"));
        if (files != null) {
            for (File f : files) {
                listModel.addElement(f.getName());
            }
        }
    }

    private void updatePreview(String templateName) {
        if (templateName == null) {
            previewLabel.setIcon(null);
            previewLabel.setText("Select a template to preview");
            return;
        }

        File jrxmlFile = new File(TEMPLATE_DIR + templateName);
        File pngFile = new File(TEMPLATE_DIR + templateName.replace(".jrxml", ".png"));

        previewLabel.setIcon(null);
        previewLabel.setText("Loading preview...");

        SwingWorker<ImageIcon, Void> worker = new SwingWorker<>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                if (!pngFile.exists()) {
                    BarcodePreviewGenerator.generatePreview(jrxmlFile);
                }

                if (pngFile.exists()) {
                    BufferedImage img = ImageIO.read(pngFile);
                    if (img != null) {
                        int maxW = 500;
                        int maxH = 500;
                        int iw = img.getWidth();
                        int ih = img.getHeight();

                        if (iw > maxW || ih > maxH) {
                            double scale = Math.min((double) maxW / iw, (double) maxH / ih);
                            int nw = (int) (iw * scale);
                            int nh = (int) (ih * scale);
                            Image scaled = img.getScaledInstance(nw, nh, Image.SCALE_SMOOTH);
                            return new ImageIcon(scaled);
                        } else {
                            return new ImageIcon(img);
                        }
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    ImageIcon icon = get();
                    if (icon != null) {
                        previewLabel.setText(null);
                        previewLabel.setIcon(icon);
                    } else {
                        previewLabel.setText("Preview not available");
                    }
                } catch (Exception e) {
                    previewLabel.setText("Error loading preview");
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    public static String getSelectedTemplateName() {
        try {
            File f = new File(DEFAULT_FILE);
            if (f.exists()) {
                return new String(Files.readAllBytes(f.toPath())).trim();
            }
        } catch (Exception e) {}
        return null;
    }

    public static File getSelectedTemplateFile() {
        String name = getSelectedTemplateName();
        if (name != null) {
            File f = new File(TEMPLATE_DIR + name);
            if (f.exists()) return f;
        }
        return null;
    }
}
