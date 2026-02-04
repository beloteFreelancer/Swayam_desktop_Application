package attendancepack;

import com.selrom.db.DataUtil;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import menupack.menu_form;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class attendance_entry extends javax.swing.JInternalFrame {

    DataUtil util = null;
    ResultSet r;

    public class sample2 extends DefaultTableModel {

        @Override
        public void addColumn(Object columnName) {
            super.addColumn(columnName);
        }

        @Override
        public void addRow(Object[] rowData) {
            super.addRow(rowData);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 5;
        }
    }
    sample2 s2 = new sample2();

    void button_short() {
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        excelbutton.setText("<html><b>Excel</b><br>(Alt+I)</h6><html>");
        generatebutton.setText("<html><b>Load Staffs</b>  (Alt+G)</h6><html>");
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        titlelablel.setText("<html><u>Staff Attendance Register</u></html>");

        setTitle("Staff Attendance Register");
        this.setSize(1021, 648);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
    }

    public void load_list_table() {
        s2.addColumn("Staff Id");
        s2.addColumn("Name");
        s2.addColumn("Designation");
        s2.addColumn("Attendance");
        s2.addColumn("Status");
        s2.addColumn("Remarks");
        jTable1.setModel(s2);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(300);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(220);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(164);
        String Ta = "Arial";
        int Bold = 0, size = 14;
        jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
    }

    void load_staffs() {
        try {
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            h1.setText(g.format(d));
            boolean selva = false;
            String query, status = "Working";
            query = "select sid,sname,desig from staff_entry where status='" + status + "'";
            r = util.doQuery(query);
            while (r.next()) {
                s2.addRow(new Object[]{r.getString(1), r.getString(2), r.getString(3), "Present", "1", "."});
                selva = true;
            }
            if (selva == true) {
                generatebutton.setEnabled(false);
                h1.setEnabled(false);
                get_previous_list();
                count();
            }
        } catch (ClassNotFoundException | SQLException e) {
        }
    }

    void get_previous_list() {
        try {
            String query;
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(h1.getText());
            String date = (new SimpleDateFormat("yyyy/MM/dd").format(nm));
            boolean selva = false;
            query = "select dat from atten_entry where dat='" + date + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    String sid = jTable1.getValueAt(i, 0).toString();
                    String sname = jTable1.getValueAt(i, 1).toString();
                    query = "select status,value,remarks from atten_entry where dat='" + date + "' and sid='" + sid + "' and sname='" + sname + "'";
                    r = util.doQuery(query);
                    while (r.next()) {
                        jTable1.setValueAt(r.getString(1), i, 3);
                        jTable1.setValueAt(r.getString(2), i, 4);
                        jTable1.setValueAt(r.getString(3), i, 5);
                    }
                }
                count();
            }//if true ends
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            System.out.println();
        }
    }

    void count() {
        int tot = jTable1.getRowCount();
        int present = 0, absent;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            if (jTable1.getValueAt(i, 3).toString().equalsIgnoreCase("Present")) {
                present = present + 1;
            }
        }
        absent = tot - present;
        totl.setText(" Total Staffs: " + tot + "    Presents: " + present + "    Absents: " + absent);
    }

    void save() {
        try {
            if (s2.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "Oops", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int a = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (a == JOptionPane.NO_OPTION) {
                return;
            }
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                Object remarks = jTable1.getValueAt(i, 5);
                if (remarks == null || remarks == "") {
                    jTable1.setValueAt(".", i, 5);
                }
            }//row counts
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(h1.getText());
            String date = (new SimpleDateFormat("yyyy/MM/dd").format(nm));
            boolean selva = false;
            String query = "select distinct dat from atten_entry where dat='" + date + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                query = "delete from atten_entry where dat='" + date + "'";
                int b = util.doManipulation(query);
            }
            ArrayList list = new ArrayList();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                String sid = jTable1.getValueAt(i, 0).toString();
                String sname = jTable1.getValueAt(i, 1).toString();
                String desig = jTable1.getValueAt(i, 2).toString();
                String status = jTable1.getValueAt(i, 3).toString();
                String value = jTable1.getValueAt(i, 4).toString();
                String remarks = jTable1.getValueAt(i, 5).toString();
                list.add("insert into atten_entry values ('" + date + "','" + sid + "','" + sname + "','" + desig + "','" + status + "','" + value + "','" + remarks + "' )");
            }
            int as = util.doManipulation_Batch(list);
            if (as > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Saved Successfully</h1></html>", "Saved", JOptionPane.PLAIN_MESSAGE);
                clear();
            } else {
                JOptionPane.showMessageDialog(this, "Check Entries and try again!!!");
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException | ParseException e) {
            System.out.println(e);
        }
    }

    void clear() {
        if (s2.getRowCount() > 0) {
            s2.getDataVector().removeAllElements();
            s2.fireTableDataChanged();
        }
        generatebutton.setEnabled(true);
        h1.setText("");
        h1.setEnabled(true);
        totl.setText("");
    }

    public attendance_entry(DataUtil util) {
        initComponents();

        this.util = util;
        button_short();
        load_list_table();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        generatebutton = new javax.swing.JButton();
        excelbutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        closebutton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        totl = new javax.swing.JLabel();
        savebutton = new javax.swing.JButton();
        h1 = new javax.swing.JTextField();
        jCalendarButton1 = new net.sourceforge.jcalendarbutton.JCalendarButton();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Staff Attendance Register");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 250, 30);

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
        jTable1.setRowHeight(24);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable1FocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 72, 1000, 450);

        generatebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        generatebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/generate30.png"))); // NOI18N
        generatebutton.setMnemonic('g');
        generatebutton.setText("Load Staffs");
        generatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(generatebutton);
        generatebutton.setBounds(180, 40, 210, 30);

        excelbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        excelbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/excel45.png"))); // NOI18N
        excelbutton.setMnemonic('i');
        excelbutton.setText("Excel");
        excelbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excelbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(excelbutton);
        excelbutton.setBounds(620, 560, 130, 50);

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
        clearbutton.setBounds(750, 560, 130, 50);

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

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Date");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(10, 40, 40, 30);

        totl.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        totl.setForeground(new java.awt.Color(255, 102, 0));
        getContentPane().add(totl);
        totl.setBounds(0, 520, 1000, 40);

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
        savebutton.setBounds(490, 560, 130, 50);

        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        getContentPane().add(h1);
        h1.setBounds(50, 40, 100, 30);

        jCalendarButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
        jCalendarButton1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton1PropertyChange(evt);
            }
        });
        getContentPane().add(jCalendarButton1);
        jCalendarButton1.setBounds(150, 40, 30, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        String status;
        int val = 0;
        status = jTable1.getValueAt(jTable1.getSelectedRow(), 3).toString();
        if (status.equalsIgnoreCase("Absent")) {
            status = "Present";
            val = 1;
        } else if (status.equalsIgnoreCase("Present")) {
            status = "Absent";
            val = 0;
        }
        jTable1.setValueAt(status, jTable1.getSelectedRow(), 3);
        jTable1.setValueAt(val, jTable1.getSelectedRow(), 4);
        count();
    }//GEN-LAST:event_jTable1MouseClicked

    private void generatebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatebuttonActionPerformed
        load_staffs();

    }//GEN-LAST:event_generatebuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        clear();

    }//GEN-LAST:event_clearbuttonActionPerformed

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained

    }//GEN-LAST:event_jTable1FocusGained

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        save();
    }//GEN-LAST:event_savebuttonActionPerformed

    private void jCalendarButton1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendarButton1PropertyChange
        try {
            if (evt.getNewValue() instanceof Date) {
                String ses = evt.getNewValue().toString();
                Date nm = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(ses);
                String date = (new SimpleDateFormat("dd/MM/yyyy").format(nm));
                h1.setText(date);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_jCalendarButton1PropertyChange

    private void excelbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excelbuttonActionPerformed
        if (s2.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Sorry, No Records Were Found!", "Oops", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            menupack.menu_form mp = new menu_form();
            String drive = mp.getDrive();
            String folder = mp.getFoler();
            String file_name = "List";
            FileWriter f = new FileWriter(new File(folder + "/Drafts/" + file_name + ".xls"));
            f.write("Staff Attendance Register:\n");
            for (int i1 = 0; i1 < jTable1.getColumnCount(); i1++) {
                String kg = jTable1.getColumnName(i1);
                f.write(kg);
                f.write("\t");
            }
            f.write("\n");
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                for (int j = 0; j < jTable1.getColumnCount(); j++) {
                    String val = jTable1.getValueAt(i, j).toString();
                    f.write(val);
                    f.write("\t");
                }
                f.write("\n");
            }
            f.write("" + totl.getText());
            f.close();
            Runtime rt = Runtime.getRuntime();
            Utils.AppConfig.openFile(folder + "/Drafts/" + file_name + ".xls");
        } catch (IOException e) {
        }

    }//GEN-LAST:event_excelbuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton excelbutton;
    private javax.swing.JButton generatebutton;
    private javax.swing.JTextField h1;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JLabel totl;
    // End of variables declaration//GEN-END:variables
}
