package itempack;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.selrom.db.DataUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public class item_search extends javax.swing.JInternalFrame {

    DataUtil util = null;
    ResultSet r;
    AutoCompleteSupport support;

    final void button_short() {
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        titlelablel.setText("<html><u>Search Items</u></html>");
        setTitle("Search Items");
        this.setSize(685, 399);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
    }

    final void get_sug() {
        try {
            int count = 0;
            String query = "select iname from item";
            ResultSet set = util.doQuery(query);
            while (set.next()) {
                count = count + 1;
            }
            query = "select iname from item";
            set = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (set.next()) {
                f[index] = set.getString(1);
                index++;
            }
            support = AutoCompleteSupport.install(
                    h1, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_item_no() {
        try {
            String query = "select ino from item where iname='" + h1.getSelectedItem() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                h2.setText(r.getString(1));
            }
            get_details();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_details() {
        try {
            h.setText("");
            h.append(" ------------------------------------------------------------------------");
            h.append("\n Barcode                               Stock-in-Hand                Entry");
            h.append("\n ------------------------------------------------------------------------\n");
            String query = "select barcode,quan,entry from stock where ino='" + h2.getText() + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                String barcode = r.getString(1);
                String quan = r.getString(2);
                String entry = r.getString(3);

                int bb = barcode.length();
                int qq3 = 35 - bb;
                if (qq3 <= 0) {
                } else {
                    for (int e = 0; e < qq3; e++) {
                        barcode = barcode + " ";
                    }
                }

                int bb1 = quan.length();
                int qq4 = 15 - bb1;
                if (qq4 <= 0) {
                } else {
                    for (int e = 0; e < qq4; e++) {
                        quan = " " + quan;
                    }
                }

                int bb2 = entry.length();
                int qq2 = 20 - bb2;
                if (qq2 <= 0) {
                } else {
                    for (int e = 0; e < qq2; e++) {
                        entry = " " + entry;
                    }
                }

                h.append(" " + barcode + " " + quan + " " + entry + "\n");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print(e.getMessage());
        }
    }

    void get_item_name() {
        try {
            String query = "select iname from item where ino='" + h2.getText() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                h1.setSelectedItem(r.getString(1));
            }
            get_details();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public item_search(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();
        get_sug();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        h = new javax.swing.JTextArea();
        h1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        h2 = new javax.swing.JTextField();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Search Items");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 230, 30);

        clearbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        clearbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/clear45.png"))); // NOI18N
        clearbutton.setMnemonic('c');
        clearbutton.setText("Clear");
        clearbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(clearbutton);
        clearbutton.setBounds(400, 300, 130, 50);

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
        closebutton.setBounds(530, 300, 120, 50);

        h.setColumns(20);
        h.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        h.setForeground(new java.awt.Color(255, 0, 51));
        h.setRows(5);
        h.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jScrollPane1.setViewportView(h);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 80, 630, 210);

        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "." }));
        h1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                h1ItemStateChanged(evt);
            }
        });
        getContentPane().add(h1);
        h1.setBounds(100, 40, 410, 30);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Item Name");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 40, 80, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h2ActionPerformed(evt);
            }
        });
        getContentPane().add(h2);
        h2.setBounds(510, 40, 140, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        h1.setSelectedItem("");
        h.setText("");
        h1.requestFocus();
    }//GEN-LAST:event_clearbuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void h1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_h1ItemStateChanged
        get_item_no();
    }//GEN-LAST:event_h1ItemStateChanged

    private void h2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h2ActionPerformed
        get_item_name();
    }//GEN-LAST:event_h2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JTextArea h;
    private javax.swing.JComboBox<String> h1;
    private javax.swing.JTextField h2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel titlelablel;
    // End of variables declaration//GEN-END:variables
}
