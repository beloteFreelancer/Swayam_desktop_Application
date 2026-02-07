package couldpack;

import Utils.ColorConstants;
import com.selrom.db.DataUtil;
import emailpack.selrom_email;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import menupack.menu_form;
import smspack.SMS_Sender_Single;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 *         mysoft.java@gmail.com
 */
public class day_book_close extends javax.swing.JInternalFrame {

    DataUtil util = null;
    String drive = "", folder = "", backup_drive = "", backup_folder = "", version = "", username = "";
    int hmany = 2;

    final void button_short() {
        try {
            titlelablel.setText("<html><u>Day End Process</u></html>");
            setTitle("Day End Process");
            this.setSize(444, 358);
            ImageIcon icon = ColorConstants.loadIcon("/images/icon.png");
            if (icon != null) {
                this.setFrameIcon(icon);
            }
            menupack.menu_form me = new menu_form();
            drive = "";
            folder = Utils.AppConfig.getAppPath();
            version = me.getVersion();
            username = me.getUsername();
            FileInputStream m = new FileInputStream(folder + "/Config_Files/Backup_Folder.properties");
            Properties p = new Properties(null);
            p.load(m);
            backup_drive = p.getProperty("drive");
            backup_folder = p.getProperty("folder");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    void backup_database() {
        try {
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("yyyy-MM-dd_hh.mma");
            String file_name = "Selrom_Retail_" + g.format(d);

            // Backup H2 database file
            String backup_file_name = Utils.AppConfig.getAppPath() + "/" + file_name + ".mv.db";
            // Assuming DB file is ./Swayam_main.mv.db
            java.nio.file.Files.copy(java.nio.file.Paths.get("./Swayam_main.mv.db"),
                    java.nio.file.Paths.get(backup_file_name), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Backup Completed");

            String[] attachFiles = new String[1];
            attachFiles[0] = backup_file_name;

            String user = ".", pass = ".", backup_option = "No";
            String query = "select user,pass,backup_option,email1 from setting_email";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                user = r.getString(1);
                pass = r.getString(2);
                backup_option = r.getString(3);
            }
            if (backup_option.equalsIgnoreCase("Yes") && user.length() > 9) {
                new selrom_email().sendEmailWithAttachments(version + "-Database Backup " + g.format(d),
                        "<html>Backup Date&Time: " + g.format(d) + "<br>User: " + username + "</html>", attachFiles,
                        user, pass, user);
            }
            System.out.println("Email Send Successfully");
        } catch (IOException | ClassNotFoundException | SQLException | MessagingException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public void send_email_statement() {
        try {
            String user = ".", pass = ".", mailto = ".", statement_option = "No";
            String query = "select user,pass,statement_option,email1 from setting_email";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                user = r.getString(1);
                pass = r.getString(2);
                statement_option = r.getString(3);
                mailto = r.getString(4);
            }
            if (statement_option.equalsIgnoreCase("Yes") && mailto.length() > 5 && user.length() > 9) {
                Date d = new Date();
                SimpleDateFormat g = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat g2 = new SimpleDateFormat("dd-MM-yyyy");
                String date = g.format(d);
                String date1 = g2.format(d);

                SimpleDateFormat g1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
                String today = g1.format(d);

                String subject = version + " -DAY BOOK (" + date1 + ") As on " + today;
                String ip = InetAddress.getLocalHost().toString();

                String message = "<html>"
                        + "<br> Send by User  : " + username + " "
                        + "<br> System IP     : " + ip + ""
                        + "<br> Powered by    : " + version + "<br><br><br></html>";

                int billno = 0;
                double quans = 0, dis = 0, cash = 0, card = 0, credit = 0, others = 0, net = 0;
                query = "select count(billno),sum(quans),sum(disamt),sum(cash),sum(card),sum(credit),sum(others),sum(net) from sales where dat='"
                        + date + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    billno = r.getInt(1);
                    quans = r.getDouble(2);
                    dis = r.getDouble(3);
                    cash = r.getDouble(4);
                    card = r.getDouble(5);
                    credit = r.getDouble(6);
                    others = r.getDouble(7);
                    net = r.getDouble(8);
                }
                String cash1 = String.format("%." + hmany + "f", cash);
                String card1 = String.format("%." + hmany + "f", card);
                String credit1 = String.format("%." + hmany + "f", credit);
                String others1 = String.format("%." + hmany + "f", others);
                String net1 = String.format("%." + hmany + "f", net);
                String dis1 = String.format("%." + hmany + "f", dis);
                String sales = "<html> <u><b>SALES DETAILS:<b></u>"
                        + ""
                        + "<br>  Bills   : " + billno
                        + "<br>  Qty's   : " + quans + ""
                        + "<br>  Discount: " + dis1 + ""
                        + "<br>"
                        + "<br> Cash  : " + cash1 + ""
                        + "<br> Card  : " + card1 + ""
                        + "<br> Credit: " + credit1 + ""
                        + "<br> Others: " + others1 + ""
                        + "<br> TOTAL : " + net1 + ""
                        + " </html>";
                message = message + sales;

                billno = 0;
                quans = 0;
                net = 0;
                query = "select count(billno),sum(quans),sum(net) from purchase where dat='" + date + "' ";
                r = util.doQuery(query);
                while (r.next()) {
                    billno = r.getInt(1);
                    quans = r.getDouble(2);
                    net = r.getDouble(3);
                }

                String pnet = String.format("%." + hmany + "f", net);
                String purchase = "<html><br><br> <u><b>"
                        + ""
                        + "PURCHASE ENTRIES:"
                        + ""
                        + "<b></u>"
                        + ""
                        + "<br>  Bills   : " + billno
                        + "<br>  Qty's   : " + quans + ""
                        + "<br>  TOTAL   : " + pnet + ""
                        + " </html>";
                message = message + purchase;

                double sup_bal = 0, cust_bal = 0;
                query = "select sum(tot-paid) from cust_bal";
                r = util.doQuery(query);
                while (r.next()) {
                    cust_bal = r.getDouble(1);
                }

                query = "select sum(tot-paid) from ven_bal";
                r = util.doQuery(query);
                while (r.next()) {
                    sup_bal = r.getDouble(1);
                }

                billno = 0;
                net = 0;
                query = "select  count(billno),sum(tot-paid) from cust_bal where tot-paid>0 and ddate<" + date;
                r = util.doQuery(query);
                while (r.next()) {
                    billno = r.getInt(1);
                    net = r.getDouble(2);
                }

                int billno2 = 0;
                double net2 = 0;
                query = "select  count(billno),sum(tot-paid) from ven_bal where tot-paid>0 and ddate<" + date;
                r = util.doQuery(query);
                while (r.next()) {
                    billno2 = r.getInt(1);
                    net2 = r.getDouble(2);
                }

                String cust_bal1 = String.format("%." + hmany + "f", cust_bal);
                String sup_bal1 = String.format("%." + hmany + "f", sup_bal);

                String cnet = String.format("%." + hmany + "f", net);
                String snet = String.format("%." + hmany + "f", net2);

                String dues = "<html><br><br> <u><b>OUT STANDINGS:<b></u>"
                        + ""
                        + "<br>  From Customers: " + cust_bal1 + ""
                        + "<br>  To Suppliers  :  " + sup_bal1 + ""
                        + "<br><br>Customer Over Dues=>   Dues:" + billno + "   ...Amount: " + cnet + ""
                        + "<br>  Supplier Over Dues=>   Dues:" + billno2 + "   ...Amount: " + snet + ""
                        + " </html>";
                message = message + dues;

                double pvalue = 0, ret_value = 0, whole_value = 0;
                query = "select sum(quan*prate),sum(quan*rprice),sum(quan*wprice) from stock";
                r = util.doQuery(query);
                while (r.next()) {
                    pvalue = r.getDouble(1);
                    ret_value = r.getDouble(2);
                    whole_value = r.getDouble(3);
                }
                String pvalue1 = String.format("%." + hmany + "f", pvalue);
                String ret_value1 = String.format("%." + hmany + "f", ret_value);
                String whole_value1 = String.format("%." + hmany + "f", whole_value);

                billno = 0;
                query = "select count(b.ino) from item a,stock b where a.ino=b.ino and quan<minstock";
                r = util.doQuery(query);
                while (r.next()) {
                    billno = r.getInt(1);
                }

                String stock = "<html><br><br> <u><b>STOCK DETAILS:<b></u>"
                        + ""
                        + "<br>  Stock Purchase Value: " + pvalue1 + ""
                        + "<br>  Stock Retail Value  :  " + ret_value1 + ""
                        + "<br>  Stock Wholesale Value:  " + whole_value1 + ""
                        + "<br><br>  <b><i>Re-Order Level Items :" + billno + "<i></b>"
                        + " </html>";
                message = message + stock;

                int delete_sales = 0, alter_sales = 0, delete_estimate = 0, alter_estimate = 0;
                query = "select count(billno) from alter_sales_delete where dat='" + date + "' ";
                r = util.doQuery(query);
                while (r.next()) {
                    delete_sales = r.getInt(1);
                }
                query = "select count(billno) from alter_sales where dat='" + date + "' ";
                r = util.doQuery(query);
                while (r.next()) {
                    alter_sales = r.getInt(1);
                }
                query = "select count(billno) from alter_estimate_delete where dat='" + date + "' ";
                r = util.doQuery(query);
                while (r.next()) {
                    delete_estimate = r.getInt(1);
                }
                query = "select count(billno) from alter_estimate where dat='" + date + "' ";
                r = util.doQuery(query);
                while (r.next()) {
                    alter_estimate = r.getInt(1);
                }

                String delete_bills = "<html><br><br> <u><b>DELETED /ALTERED BILLS:<b></u>"
                        + ""
                        + "<br>  Deleted Sales Bills: " + delete_sales + ""
                        + "<br>  Altered Sales Bills: " + alter_sales + ""
                        + "<br>  Deleted Estimate Bills: " + delete_estimate + ""
                        + "<br>  Altered Estimate Bills: " + alter_estimate + ""
                        + " </html>";
                message = message + delete_bills;

                String backup_file_name = folder + "/Config_Files/text.txt";
                String[] attachFiles = new String[1];
                attachFiles[0] = backup_file_name;
                new selrom_email().sendEmailWithAttachments(subject, message, attachFiles, user, pass, mailto);
                System.out.println("Daily Statement Mail Send Successfully");

            } // email statement yes ends

        } catch (ClassNotFoundException | UnknownHostException | SQLException | MessagingException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    void send_sms_statement() {
        try {
            String sender = "", smsuser = "", smspass = "", statement_sms = "No", mobile1 = "", mobile2 = "";
            String query = "select sender,user,pass,statement_sms,mobile1,mobile2 from setting_sms";
            ResultSet r = util.doQuery(query);
            while (r.next()) {
                sender = r.getString(1);
                smsuser = r.getString(2);
                smspass = r.getString(3);
                statement_sms = r.getString(4);
                mobile1 = r.getString(5);
                mobile2 = r.getString(6);
            }
            if (statement_sms.equalsIgnoreCase("Yes")) {
                Date d = new Date();
                SimpleDateFormat g = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat g2 = new SimpleDateFormat("dd-MM-yyyy");
                String date = g.format(d);
                String today = g2.format(d);
                int billno = 0;
                double quans = 0, dis = 0, cash = 0, card = 0, credit = 0, others = 0, net = 0;
                query = "select count(billno),sum(quans),sum(disamt),sum(cash),sum(card),sum(credit),sum(others),sum(net) from sales where dat='"
                        + date + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    billno = r.getInt(1);
                    quans = r.getDouble(2);
                    dis = r.getDouble(3);
                    cash = r.getDouble(4);
                    card = r.getDouble(5);
                    credit = r.getDouble(6);
                    others = r.getDouble(7);
                    net = r.getDouble(8);
                }
                String cash1 = String.format("%." + hmany + "f", cash);
                String card1 = String.format("%." + hmany + "f", card);
                String credit1 = String.format("%." + hmany + "f", credit);
                String others1 = String.format("%." + hmany + "f", others);
                String net1 = String.format("%." + hmany + "f", net);
                String dis1 = String.format("%." + hmany + "f", dis);

                int delete_sales = 0, alter_sales = 0, delete_estimate = 0, alter_estimate = 0;
                query = "select count(billno) from alter_sales_delete where dat='" + date + "' ";
                r = util.doQuery(query);
                while (r.next()) {
                    delete_sales = r.getInt(1);
                }
                query = "select count(billno) from alter_sales where dat='" + date + "' ";
                r = util.doQuery(query);
                while (r.next()) {
                    alter_sales = r.getInt(1);
                }
                query = "select count(billno) from alter_estimate_delete where dat='" + date + "' ";
                r = util.doQuery(query);
                while (r.next()) {
                    delete_estimate = r.getInt(1);
                }
                query = "select count(billno) from alter_estimate where dat='" + date + "' ";
                r = util.doQuery(query);
                while (r.next()) {
                    alter_estimate = r.getInt(1);
                }

                String message = ": DAILY STATEMENT : "
                        + "\n (" + today + ")"
                        + "\n\n Total Bills : " + billno
                        + "\n Total Qty(s): " + quans
                        + "\n Total Dis: " + dis1
                        + "\n Cash  : " + cash1 + ""
                        + "\n Card  : " + card1 + ""
                        + "\n Credit: " + credit1 + ""
                        + "\n Others: " + others1 + ""
                        + "\n\nTOTAL SALES: " + net1 + "\n"
                        + "\nDeleted Sales: " + delete_sales + ""
                        + "\nAltered Sales: " + alter_sales + ""
                        + "\nDeleted Estimate: " + delete_estimate + ""
                        + "\nAltered Estimate: " + alter_estimate + ""
                        + "\n\nSend by: " + username;
                if (mobile1.length() == 10) {
                    new SMS_Sender_Single().getData(smsuser, smspass, sender, mobile1, message);
                }
                if (mobile2.length() == 10) {
                    new SMS_Sender_Single().getData(smsuser, smspass, sender, mobile2, message);
                }

            } // statement sms is yes

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public day_book_close(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        closebutton = new javax.swing.JButton();
        clearbutton1 = new javax.swing.JButton();
        clearbutton2 = new javax.swing.JButton();
        clearbutton3 = new javax.swing.JButton();
        clearbutton4 = new javax.swing.JButton();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Day End Process");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 230, 30);

        closebutton.setBackground(new java.awt.Color(255, 255, 204));
        closebutton.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        closebutton.setIcon(ColorConstants.loadIcon("/icons/close45.png")); // NOI18N
        closebutton.setMnemonic('o');
        closebutton.setText("5. Close");
        closebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(closebutton);
        closebutton.setBounds(20, 250, 390, 50);

        clearbutton1.setBackground(new java.awt.Color(255, 255, 204));
        clearbutton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        clearbutton1.setIcon(ColorConstants.loadIcon("/icons/load45.jpg.png")); // NOI18N
        clearbutton1.setText("1. Day End All Process");
        clearbutton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbutton1ActionPerformed(evt);
            }
        });
        getContentPane().add(clearbutton1);
        clearbutton1.setBounds(20, 50, 390, 50);

        clearbutton2.setBackground(new java.awt.Color(255, 255, 204));
        clearbutton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        clearbutton2.setIcon(ColorConstants.loadIcon("/icons/server45.png")); // NOI18N
        clearbutton2.setText("2. Backup Database");
        clearbutton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbutton2ActionPerformed(evt);
            }
        });
        getContentPane().add(clearbutton2);
        clearbutton2.setBounds(20, 100, 390, 50);

        clearbutton3.setBackground(new java.awt.Color(255, 255, 204));
        clearbutton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        clearbutton3.setIcon(ColorConstants.loadIcon("/icons/sms45.png")); // NOI18N
        clearbutton3.setText("3. Daily Statement SMS");
        clearbutton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbutton3ActionPerformed(evt);
            }
        });
        getContentPane().add(clearbutton3);
        clearbutton3.setBounds(20, 150, 390, 50);

        clearbutton4.setBackground(new java.awt.Color(255, 255, 204));
        clearbutton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        clearbutton4.setIcon(ColorConstants.loadIcon("/icons/email45.png")); // NOI18N
        clearbutton4.setText("4. Daily Statement Email");
        clearbutton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbutton4ActionPerformed(evt);
            }
        });
        getContentPane().add(clearbutton4);
        clearbutton4.setBounds(20, 200, 390, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }// GEN-LAST:event_closebuttonActionPerformed

    private void clearbutton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbutton1ActionPerformed
        int as = JOptionPane.showConfirmDialog(this, "Want to Start Day End Process ?", "Are You Sure",
                JOptionPane.YES_NO_OPTION);
        if (as == JOptionPane.NO_OPTION) {
            return;
        }
        JOptionPane.showMessageDialog(this, "Do not close the sofrware until all process get complete...", "Warning",
                JOptionPane.WARNING_MESSAGE);
        int as1 = JOptionPane.showConfirmDialog(this, "Want to Start Process ?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (as1 == JOptionPane.NO_OPTION) {
            return;
        }
        send_sms_statement();
        backup_database();
        send_email_statement();
        JOptionPane.showMessageDialog(this, "<html><h1>Completed Successfully</h1></html>", "Completed",
                JOptionPane.PLAIN_MESSAGE);
    }// GEN-LAST:event_clearbutton1ActionPerformed

    private void clearbutton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbutton2ActionPerformed
        int as = JOptionPane.showConfirmDialog(this, "Want to start Backup Process ?", "Are You Sure",
                JOptionPane.YES_NO_OPTION);
        if (as == JOptionPane.NO_OPTION) {
            return;
        }
        JOptionPane.showMessageDialog(this, "Do not close the sofrware until backup process get complete...", "Warning",
                JOptionPane.WARNING_MESSAGE);
        int as1 = JOptionPane.showConfirmDialog(this, "Want to Start Process ?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (as1 == JOptionPane.NO_OPTION) {
            return;
        }
        backup_database();
        JOptionPane.showMessageDialog(this,
                "<html><h1>Backup Stored at: '" + backup_drive + ":\\" + backup_folder + "'</h1></html>",
                "Completed Successfully", JOptionPane.PLAIN_MESSAGE);
    }// GEN-LAST:event_clearbutton2ActionPerformed

    private void clearbutton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbutton3ActionPerformed
        int as = JOptionPane.showConfirmDialog(this, "Want to send SMS ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
        if (as == JOptionPane.NO_OPTION) {
            return;
        }
        send_sms_statement();
        JOptionPane.showMessageDialog(this, "<html><h1>SMS Send Successfully</h1></html>", "Send",
                JOptionPane.PLAIN_MESSAGE);
    }// GEN-LAST:event_clearbutton3ActionPerformed

    private void clearbutton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearbutton4ActionPerformed
        int as = JOptionPane.showConfirmDialog(this, "Want to send Email ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
        if (as == JOptionPane.NO_OPTION) {
            return;
        }
        send_email_statement();
        JOptionPane.showMessageDialog(this, "<html><h1>Email Send Successfully</h1></html>", "Send",
                JOptionPane.PLAIN_MESSAGE);
    }// GEN-LAST:event_clearbutton4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton1;
    private javax.swing.JButton clearbutton2;
    private javax.swing.JButton clearbutton3;
    private javax.swing.JButton clearbutton4;
    private javax.swing.JButton closebutton;
    private javax.swing.JLabel titlelablel;
    // End of variables declaration//GEN-END:variables
}
