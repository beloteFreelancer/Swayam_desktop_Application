package pospack;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class setting_terminal extends javax.swing.JInternalFrame {

    String drive = "", folder = "";

    void display() {
        try {
            try (FileInputStream m = new FileInputStream(folder + "/Config_Files/terminal.properties")) {
                Properties p = new Properties(null);
                p.load(m);
                h1.setText(p.getProperty("location"));
                h2.setText(p.getProperty("terminal"));

                if (p.getProperty("enable_preview") != null) {
                    if (p.getProperty("enable_preview").equals("true")) {
                        previewCheckBox.setSelected(true);
                    } else {
                        previewCheckBox.setSelected(false);
                    }
                } else {
                    previewCheckBox.setSelected(true); // Default enabled
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void save() {
        try {
            if (h1.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Location ?", "Location", JOptionPane.ERROR_MESSAGE);
                h1.requestFocus();
                return;
            }
            if (h2.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Terminal ?", "Terminal", JOptionPane.ERROR_MESSAGE);
                h2.requestFocus();
                return;
            }
            Properties prop = new Properties();
            // Load existing properties first to not overwrite weighing settings
            try (FileInputStream m = new FileInputStream(folder + "/Config_Files/terminal.properties")) {
                prop.load(m);
            } catch (IOException e) {
               // ignore
            }

            prop.setProperty("location", h1.getText());
            prop.setProperty("terminal", h2.getText());
            prop.setProperty("enable_preview", previewCheckBox.isSelected() ? "true" : "false");

            prop.store(new FileOutputStream(folder + "/Config_Files/terminal.properties"), null);
            JOptionPane.showMessageDialog(this, "Saved Successfully", "Saved", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    final void button_short() {
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
    }

    public setting_terminal(String drive1, String folder1) {
        initComponents();
        setTitle("Setting");
        this.setSize(430, 300);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
        drive = drive1;
        folder = folder1;
        display();
        button_short();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        h2 = new javax.swing.JTextField();
        h1 = new javax.swing.JTextField();
        closebutton = new javax.swing.JButton();
        savebutton = new javax.swing.JButton();
        weighingConnectButton = new javax.swing.JButton();
        previewCheckBox = new javax.swing.JCheckBox();

        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Terminal");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 60, 60, 30);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Location");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(30, 30, 60, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h2);
        h2.setBounds(90, 60, 290, 30);

        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h1);
        h1.setBounds(90, 30, 290, 30);

        closebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        closebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close45.png"))); // NOI18N
        closebutton.setMnemonic('o');
        closebutton.setText("Close");
        closebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(closebutton);
        closebutton.setBounds(240, 100, 140, 50);

        savebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        savebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save45.png"))); // NOI18N
        savebutton.setMnemonic('s');
        savebutton.setText("Save");
        savebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(savebutton);
        savebutton.setBounds(90, 100, 150, 50);

        weighingConnectButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        weighingConnectButton.setText("Weighing Connect");
        weighingConnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weighingConnectButtonActionPerformed(evt);
            }
        });
        getContentPane().add(weighingConnectButton);
        weighingConnectButton.setBounds(90, 160, 290, 30);

        previewCheckBox.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        previewCheckBox.setText("Enable Print Preview");
        getContentPane().add(previewCheckBox);
        previewCheckBox.setBounds(90, 200, 290, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        save();
    }//GEN-LAST:event_savebuttonActionPerformed

    private void weighingConnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weighingConnectButtonActionPerformed
        new WeighingConfig(JOptionPane.getFrameForComponent(this), true, drive, folder).setVisible(true);
    }//GEN-LAST:event_weighingConnectButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField h2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton savebutton;
    private javax.swing.JButton weighingConnectButton;
    private javax.swing.JCheckBox previewCheckBox;
    // End of variables declaration//GEN-END:variables
}
