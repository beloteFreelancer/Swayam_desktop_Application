package smspack;

import com.selrom.db.DataUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import menupack.menu_form;
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public final class manual_sms extends javax.swing.JInternalFrame {

    DataUtil util = null;
    sample2 s2 = new sample2();

    final void button_short() {
        generatebutton.setText("<html><b>Generate List</b><br>(Alt+G)</h6><html>");
        smsbutton.setText("<html><b>Send SMS</b><br>(Alt+S)</h6><html>");
        addbutton.setText("<html><b>Add</b><br>(Alt+A)</h6><html>");
        removebutton.setText("<html><b>Remove</b><br>(Alt+M)</h6><html>");
        invalidbutton.setText("<html><b>Remove Invalid Contact(s)</b><br>(Alt+I)</h6><html>");

        textbutton.setText("<html><b>Text File</b><br>(Alt+F)</h6><html>");
        downloadbutton.setText("<html><b>Down.Text</b><br>(Alt+W)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");

        titlelablel.setText("<html><u>Manual SMS</u></html>");

        setTitle("Manual SMS");
        this.setSize(1021, 654);
        java.net.URL iconUrl = ClassLoader.getSystemResource("/images/icon.png");
        if (iconUrl != null) {
            ImageIcon icon = new ImageIcon(iconUrl);
            this.setFrameIcon(icon);
        }
    }

    void load_table() {
        try {
            s2.addColumn("Cust_Id");
            s2.addColumn("Name");
            s2.addColumn("Mobile_No");
            jTable1.setModel(s2);
            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
            dtcr.setHorizontalAlignment(SwingConstants.CENTER);
            jTable1.getColumnModel().getColumn(0).setCellRenderer(dtcr);
            jTable1.getColumnModel().getColumn(1).setCellRenderer(dtcr);
            jTable1.getColumnModel().getColumn(2).setCellRenderer(dtcr);
            jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(274);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(110);
            String Ta = "Arial";
            int Bold = 0, size = 14;
            jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void generate_list() {
        try {
            String query;
            if (all.isSelected()) {
                query = "select cid,cname,mobile from cust order by cid";
            } else {
                query = "select cid,cname,mobile from cust where ctype='" + h1.getSelectedItem() + "' order by cid";
            }
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                s2.addRow(new Object[] { r.getString(1), r.getString(2), r.getString(3) });
            }
            checking();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void clear() {
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        h1.setSelectedIndex(0);
        all.setSelected(false);
        h3.setText("");
        totl.setText("");
        h1.setEnabled(true);
    }

    public void load_list_from_outside(ArrayList cid, ArrayList cname, ArrayList mobile) {
        try {
            for (int i = 0; i < cid.size(); i++) {
                s2.addRow(new Object[] { cid.get(i), cname.get(i), mobile.get(i) });
            }
            checking();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void checking() {
        try {
            int count, invalid = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String mobile = jTable1.getValueAt(i, 2).toString();
                if (mobile.length() != 10) {
                    invalid = invalid + 1;
                }
            }
            count = jTable1.getRowCount();
            totl.setText("Total Contact(s): " + count + "    Invalid: " + invalid);
            if (invalid > 0) {
                totl.setForeground(Color.red);
            } else {
                totl.setForeground(Color.BLACK);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void send_sms() {
        try {
            if (h3.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Message ?", "Invalid", JOptionPane.ERROR_MESSAGE);
                h3.requestFocus();
                return;
            }
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Conatcts(s)", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int as = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Send SMS ?</h1></html>", "Are You Sure",
                    JOptionPane.YES_NO_OPTION);
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
        }
    }

    public manual_sms(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();
        load_table();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        closebutton = new javax.swing.JButton();
        totl = new javax.swing.JLabel();
        invalidbutton = new javax.swing.JButton();
        removebutton = new javax.swing.JButton();
        addbutton = new javax.swing.JButton();
        downloadbutton = new javax.swing.JButton();
        textbutton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        generatebutton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        h3 = new javax.swing.JTextArea();
        all = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        h1 = new javax.swing.JComboBox<>();
        smsbutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Manual SMS");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 400, 30);

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
        closebutton.setBounds(880, 560, 120, 50);

        totl.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        totl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(totl);
        totl.setBounds(510, 30, 490, 30);

        invalidbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        invalidbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/retrive45.png"))); // NOI18N
        invalidbutton.setMnemonic('i');
        invalidbutton.setText("Remove Invalid Contact(s)");
        invalidbutton.setToolTipText("");
        invalidbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        invalidbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                invalidbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(invalidbutton);
        invalidbutton.setBounds(540, 560, 220, 50);

        removebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        removebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/remove45.png"))); // NOI18N
        removebutton.setMnemonic('m');
        removebutton.setText("Remove");
        removebutton.setToolTipText("Alt+M");
        removebutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        removebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(removebutton);
        removebutton.setBounds(400, 560, 140, 50);

        addbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        addbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add45.png"))); // NOI18N
        addbutton.setMnemonic('a');
        addbutton.setText("Add");
        addbutton.setToolTipText("");
        addbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(addbutton);
        addbutton.setBounds(290, 560, 110, 50);

        downloadbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        downloadbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/upload45.png"))); // NOI18N
        downloadbutton.setMnemonic('w');
        downloadbutton.setText("Down.Text");
        downloadbutton.setToolTipText("");
        downloadbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        downloadbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(downloadbutton);
        downloadbutton.setBounds(140, 560, 149, 50);

        textbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        textbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/text45.png"))); // NOI18N
        textbutton.setMnemonic('f');
        textbutton.setText("Text File");
        textbutton.setToolTipText("");
        textbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        textbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(textbutton);
        textbutton.setBounds(10, 560, 130, 50);

        jTable1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }));
        jTable1.setRowHeight(25);
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(512, 62, 490, 490);

        jPanel1.setLayout(null);

        generatebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/generate30.png"))); // NOI18N
        generatebutton.setMnemonic('g');
        generatebutton.setText("Generate List");
        generatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebuttonActionPerformed(evt);
            }
        });
        jPanel1.add(generatebutton);
        generatebutton.setBounds(320, 30, 160, 40);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Message");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(10, 90, 70, 30);

        h3.setColumns(20);
        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h3.setRows(5);
        jScrollPane1.setViewportView(h3);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(80, 80, 400, 140);

        all.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        all.setText("Select All");
        all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allActionPerformed(evt);
            }
        });
        jPanel1.add(all);
        all.setBounds(230, 30, 90, 40);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Category");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(0, 0, 80, 30);

        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "General", "Credit Customer" }));
        jPanel1.add(h1);
        h1.setBounds(80, 0, 400, 30);

        smsbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        smsbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sms45.png"))); // NOI18N
        smsbutton.setMnemonic('s');
        smsbutton.setText("Send SMS");
        smsbutton.setToolTipText("");
        smsbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        smsbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smsbuttonActionPerformed(evt);
            }
        });
        jPanel1.add(smsbutton);
        smsbutton.setBounds(330, 240, 150, 50);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 60, 480, 290);

        clearbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        clearbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/clear45.png"))); // NOI18N
        clearbutton.setMnemonic('c');
        clearbutton.setText("Clear");
        clearbutton.setToolTipText("");
        clearbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(clearbutton);
        clearbutton.setBounds(760, 560, 120, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }// GEN-LAST:event_closebuttonActionPerformed

    private void generatebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_generatebuttonActionPerformed
        generate_list();

    }// GEN-LAST:event_generatebuttonActionPerformed

    private void invalidbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_invalidbuttonActionPerformed

        for (int i = 0; i < jTable1.getRowCount(); i++) {
            String mobile = jTable1.getValueAt(i, 2).toString();
            if (mobile.length() != 10) {
                s2.removeRow(i);
            }
        }
        checking();
    }// GEN-LAST:event_invalidbuttonActionPerformed

    private void removebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_removebuttonActionPerformed

        int[] rows = jTable1.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            s2.removeRow(rows[i] - i);
        }
        checking();

    }// GEN-LAST:event_removebuttonActionPerformed

    private void addbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_addbuttonActionPerformed

        try {
            String number = JOptionPane.showInputDialog(this, "<html><h1> Enter Mobile No ? </h1></html>",
                    JOptionPane.PLAIN_MESSAGE);
            if (number.length() != 10) {
                JOptionPane.showMessageDialog(this, "Sorry, Invalid Mobile No!", "Oops", JOptionPane.ERROR_MESSAGE);
                return;
            }
            s2.addRow(new Object[] { ".", ".", number });
            checking();
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
        }

        // TODO add your handling code here:
    }// GEN-LAST:event_addbuttonActionPerformed

    private void downloadbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_downloadbuttonActionPerformed
        try {
            menupack.menu_form me = new menu_form();
            String drive = "";
            String folder = Utils.AppConfig.getAppPath();

            try (FileWriter f = new FileWriter(new File(folder + "/Drafts/SMS_List.txt"))) {
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    f.write(jTable1.getValueAt(i, 2).toString() + "\n");
                }
            }
            Runtime rt = Runtime.getRuntime();
            Utils.AppConfig.openFile(folder + "/Drafts/SMS_List.txt");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // TODO add your handling code here:
    }// GEN-LAST:event_downloadbuttonActionPerformed

    private void textbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_textbuttonActionPerformed
        try {
            JFileChooser j = new JFileChooser();
            j.showOpenDialog(this);
            String img = j.getSelectedFile().getPath();
            BufferedReader br = new BufferedReader(new FileReader(img));
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                s2.addRow(new Object[] { ".", ".", sCurrentLine });
            }
            checking();
        } catch (HeadlessException | IOException e) {
            System.out.println(e.getMessage());
        }
        // TODO add your handling code here:
    }// GEN-LAST:event_textbuttonActionPerformed

    private void allActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_allActionPerformed
        if (all.isSelected()) {
            h1.setEnabled(false);
        } else {
            h1.setEnabled(true);
        }
    }// GEN-LAST:event_allActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbuttonActionPerformed
        clear();
    }// GEN-LAST:event_clearbuttonActionPerformed

    private void smsbuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_smsbuttonActionPerformed
        send_sms();
    }// GEN-LAST:event_smsbuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addbutton;
    private javax.swing.JCheckBox all;
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton downloadbutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JComboBox<String> h1;
    private javax.swing.JTextArea h3;
    private javax.swing.JButton invalidbutton;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton removebutton;
    private javax.swing.JButton smsbutton;
    private javax.swing.JButton textbutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JLabel totl;
    // End of variables declaration//GEN-END:variables
}
