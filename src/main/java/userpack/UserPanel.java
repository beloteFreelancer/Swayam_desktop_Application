package userpack;

import com.selrom.db.DataUtil;
import java.awt.Font;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import menupack.sample2;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public class UserPanel extends JPanel {

    DataUtil util = null;
    sample2 s2 = new sample2();

    // Variables declaration
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton deletebutton;
    private javax.swing.JComboBox<String> h1;
    private javax.swing.JTextField h2;
    private javax.swing.JPasswordField h3;
    private javax.swing.JPasswordField h4;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton passbutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;

    final void button_short() {
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        passbutton.setText("<html><b>View Password</b><br>(Alt+V)</h6><html>");

        titlelablel.setText("<html><u>Users Management</u></html>");
    }

    final void load_list_table() {
        s2.addColumn("User Name");
        s2.addColumn("User Level");
        s2.addColumn("Last Modified at");
        jTable1.setModel(s2);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(180);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    void clear() {
        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) h1.getModel();
        if (model.getIndexOf("License Owner") != -1) {
            model.removeElement("License Owner");
        }

        h1.setSelectedIndex(0);
        h2.setText("");
        h3.setText("");
        h4.setText("");
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        get_display();
        h2.requestFocus();
    }

    void get_display() {
        try {
            ResultSet r;
            String query = "select user,utype,last from users order by utype,user";
            r = util.doQuery(query);
            while (r.next()) {
                s2.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3)});
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
    }

    public UserPanel(DataUtil util) {
        initComponents();
        button_short();
        this.util = util;
        load_list_table();
        get_display();
    }

    public void setCloseButtonVisible(boolean visible) {
        if (closebutton != null) {
            closebutton.setVisible(visible);
        }
    }

    // Copy initComponents from user.java but adapted for JPanel
    private void initComponents() {

        closebutton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        titlelablel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        h4 = new javax.swing.JPasswordField();
        h3 = new javax.swing.JPasswordField();
        h2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        h1 = new javax.swing.JComboBox<>();
        savebutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        deletebutton = new javax.swing.JButton();
        passbutton = new javax.swing.JButton();

        setLayout(null); // Keep null layout as in original

        closebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        closebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close45.png"))); // NOI18N
        closebutton.setMnemonic('o');
        closebutton.setText("Close");
        closebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebuttonActionPerformed(evt);
            }
        });
        add(closebutton);
        closebutton.setBounds(810, 450, 180, 50);

        jTable1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setRowHeight(25);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(450, 52, 540, 390);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Users Management");
        add(titlelablel);
        titlelablel.setBounds(10, 0, 400, 30);

        jPanel1.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Password");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(0, 60, 120, 30);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Confirm Password");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(0, 90, 130, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jPanel1.add(h4);
        h4.setBounds(120, 90, 300, 30);

        h3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jPanel1.add(h3);
        h3.setBounds(120, 60, 300, 30);

        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h2);
        h2.setBounds(120, 30, 300, 30);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("User Name");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(0, 30, 120, 30);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("User Level");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(0, 0, 120, 30);

        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "User", "Administrator", "Super Admin" }));
        jPanel1.add(h1);
        h1.setBounds(120, 0, 300, 30);

        savebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        savebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save45.png"))); // NOI18N
        savebutton.setMnemonic('s');
        savebutton.setText("Save");
        savebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebuttonActionPerformed(evt);
            }
        });
        jPanel1.add(savebutton);
        savebutton.setBounds(120, 130, 150, 50);

        clearbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        clearbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/clear45.png"))); // NOI18N
        clearbutton.setMnemonic('c');
        clearbutton.setText("Clear");
        clearbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbuttonActionPerformed(evt);
            }
        });
        jPanel1.add(clearbutton);
        clearbutton.setBounds(270, 130, 150, 50);

        add(jPanel1);
        jPanel1.setBounds(20, 50, 420, 180);

        deletebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        deletebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete45.png"))); // NOI18N
        deletebutton.setMnemonic('d');
        deletebutton.setText("Delete");
        deletebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebuttonActionPerformed(evt);
            }
        });
        add(deletebutton);
        deletebutton.setBounds(630, 450, 180, 50);

        passbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        passbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/apply45.png"))); // NOI18N
        passbutton.setMnemonic('v');
        passbutton.setText("View Password");
        passbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passbuttonActionPerformed(evt);
            }
        });
        add(passbutton);
        passbutton.setBounds(450, 450, 180, 50);

        this.setSize(1022, 536);
    }

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            ResultSet r;
            boolean selva = false;
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            String last = g.format(d) + "  " + g1.format(d);
            if (h2.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter User Name", "User Name", JOptionPane.ERROR_MESSAGE);
                h2.requestFocus();
                return;
            }
            if (h3.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Enter Password", "Password", JOptionPane.ERROR_MESSAGE);
                h3.requestFocus();
                return;
            }
            if (h4.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Confirm Password", "Password", JOptionPane.ERROR_MESSAGE);
                h4.requestFocus();
                return;
            }

            if (h3.getText().equals(h4.getText())) {
                String query = "select user, utype from users where user='" + h2.getText() + "'";
                r = util.doQuery(query);
                String currentUtype = null;
                while (r.next()) {
                    selva = true;
                    currentUtype = r.getString(2);
                }

                String selectedType = (String) h1.getSelectedItem();

                if ("License Owner".equals(selectedType)) {
                    if (!selva) {
                         JOptionPane.showMessageDialog(this, "Cannot create License Owner user.", "Action Denied", JOptionPane.ERROR_MESSAGE);
                         return;
                    }
                    if (!"License Owner".equals(currentUtype)) {
                         JOptionPane.showMessageDialog(this, "Cannot promote user to License Owner.", "Action Denied", JOptionPane.ERROR_MESSAGE);
                         return;
                    }
                }

                // Hash the password
                String hashedPassword = Utils.PasswordUtils.hashPassword(h3.getText());

                if (selva == true) {
                    query = "update users set utype='" + h1.getSelectedItem() + "',pass='" + hashedPassword + "',last='" + last + "' where user='" + h2.getText() + "'";
                    int count = util.doManipulation(query);
                    if (count > 0) {
                        JOptionPane.showMessageDialog(this, "Updated Successfully", "Updated", JOptionPane.PLAIN_MESSAGE);
                        clear();
                    }
                } else {
                    query = "insert into users values ('" + h1.getSelectedItem() + "','" + h2.getText() + "','" + hashedPassword + "','" + last + "')";
                    int count = util.doManipulation(query);
                    if (count > 0) {
                        JOptionPane.showMessageDialog(this, "Saved Successfully", "Saved", JOptionPane.PLAIN_MESSAGE);
                        clear();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sorry, Password & Confirm Password Not Matching!", "Invalid", JOptionPane.ERROR_MESSAGE);
                h3.requestFocus();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {
        // Prioritize finding JInternalFrame parent
        java.awt.Container parent = this.getParent();
        while (parent != null) {
            if (parent instanceof javax.swing.JInternalFrame) {
                ((javax.swing.JInternalFrame)parent).dispose();
                return;
            }
            parent = parent.getParent();
        }

        // If not in internal frame, try window
        java.awt.Window win = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (win != null) {
            win.dispose();
        }
    }

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {
        try {

            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if License Owner
            String userLevel = (String) jTable1.getValueAt(jTable1.getSelectedRow(), 1);
            if ("License Owner".equals(userLevel)) {
                 JOptionPane.showMessageDialog(this, "License Owner cannot be deleted!", "Action Denied", JOptionPane.WARNING_MESSAGE);
                 return;
            }

            int as = JOptionPane.showConfirmDialog(this, "Want to Delete", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            String query = "delete from users where user='" + jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "'";
            int count = util.doManipulation(query);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Deleted Successfully", "Deleted", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void passbuttonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Cannot view hashed password
            JOptionPane.showMessageDialog(this, "Password cannot be viewed as it is hashed for security.", "Password", JOptionPane.PLAIN_MESSAGE);
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
        }

    }

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {
        clear();
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        try {
            if (s2.getRowCount() > 0) {
                String query = "select utype,user,pass from users where user='" + jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "'";
                ResultSet r = util.doQuery(query);
                while (r.next()) {
                    String uType = r.getString(1);
                    DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) h1.getModel();

                    if ("License Owner".equals(uType)) {
                        if (model.getIndexOf("License Owner") == -1) {
                            model.addElement("License Owner");
                        }
                        h1.setSelectedItem("License Owner");
                    } else {
                        if (model.getIndexOf("License Owner") != -1) {
                            model.removeElement("License Owner");
                        }
                        h1.setSelectedItem(uType);
                    }

                    h2.setText(r.getString(2));
                    // Don't show hashed password
                    h3.setText("");
                    h4.setText("");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
