package ActivationPack;

import Utils.ColorConstants;
import com.selrom.db.DataUtil;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Selrom Software
 */
public final class license_blocked extends javax.swing.JFrame {

    DataUtil util = null;

    void get_defaults() {
        setSize(457, 241);
        setLocationRelativeTo(getRootPane());
        setResizable(false);
        javax.swing.ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
        if (icon != null) {
            setIconImage(icon.getImage());
        }
    }

    public static String check_internet_connect() {
        String connection = "Yes";
        try {
            URL url = new URL("http://google.com");
            URLConnection con = url.openConnection();
            con.getInputStream();
        } catch (IOException e) {
            System.out.println(e);
            connection = "No";
        }
        return connection;
    }

    public license_blocked() {
        initComponents();
        util = new DataUtil();
        get_defaults();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        updatebutton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255), 3));
        jPanel1.setLayout(null);

        jButton1.setBackground(new java.awt.Color(0, 51, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Exit");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(360, 210, 100, 30);

        jPanel2.setBackground(new java.awt.Color(0, 51, 255));
        jPanel2.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("License is Blocked");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(0, 0, 440, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 610, 40);

        updatebutton.setBackground(new java.awt.Color(0, 51, 255));
        updatebutton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        updatebutton.setForeground(new java.awt.Color(255, 255, 255));
        updatebutton.setText("Activate with New License");
        updatebutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebuttonActionPerformed(evt);
            }
        });
        jPanel1.add(updatebutton);
        updatebutton.setBounds(40, 60, 390, 40);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 102));
        jPanel1.add(jLabel1);
        jLabel1.setBounds(30, 180, 410, 30);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("* Internet connection is required for activation.");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(20, 110, 320, 20);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("* Activation process may takes sometimes based on internet connection speed.");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 130, 440, 20);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Please contact customer care for more details:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(30, 160, 440, 20);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }// GEN-LAST:event_jButton1ActionPerformed

    private void updatebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_updatebuttonActionPerformed
        String connection = check_internet_connect();
        if (connection.equals("Yes")) {
            new activation_home().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Internet connection is required for activation!",
                    "No Internet Connection", JOptionPane.ERROR_MESSAGE);
        }

    }// GEN-LAST:event_updatebuttonActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(license_blocked.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        UIManager.put("nimbusFocus", new Color(0, 51, 255, 255));
        UIManager.put("nimbusSelectionBackground", new Color(0, 51, 255, 255));
        UIManager.put("nimbusBase", new Color(0, 51, 255, 255));
        UIManager.put("control", new Color(255, 255, 255, 255));
        java.awt.EventQueue.invokeLater(() -> {
            new license_blocked().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton updatebutton;
    // End of variables declaration//GEN-END:variables
}
