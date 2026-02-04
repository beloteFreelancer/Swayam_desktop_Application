package hrpack;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.selrom.db.DataUtil;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import menupack.sample2;

/**
 *
 * @author K.SELVAKUMAR, copyrights K.SELVAKUMAR, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public final class staff_entry extends javax.swing.JInternalFrame {

    DataUtil util = null;
    AutoCompleteSupport support, support1, support2, support3, support4, support5, support6, support7, support8, support10, support11, support12;
    sample2 s3 = new sample2();
    ResultSet r;
    String img = "";

    void button_short() {
        savebutton.setText("<html><b>Save</b><br>(Alt+S)</h6><html>");
        viewbutton.setText("<html><b>View</b><br>(Alt+V)</h6><html>");
        clearbutton.setText("<html><b>Clear</b><br>(Alt+C)</h6><html>");
        closebutton.setText("<html><b>Close</b><br>(Alt+O)</h6><html>");
        deletebutton.setText("<html><b>Delete</b><br>(Alt+D)</h6><html>");
        updatebutton.setText("<html><b>Update</b><br>(Alt+U)</h6><html>");
        prebutton.setText("<html><b>Last Entry</b><br>(Alt+R)</h6><html>");
        nextbutton.setText("<html><b>Next Entry</b><br>(Alt+N)</h6><html>");

        titlelablel.setText("<html><u>Staff Profile</u></html>");

        Date d = new Date();
        SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
        h2.setText(g.format(d));
        h3.setText(g1.format(d));
        setTitle("Staff Profile");
        this.setSize(1123, 643);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        this.setFrameIcon(icon);
    }

    void get_sno_no() {
        try {
            int sno = 1;
            String query = "select max(sno) from staff_entry";
            r = util.doQuery(query);
            boolean selva = false;
            while (r.next()) {
                selva = true;
                sno = r.getInt(1);
            }
            if (selva == true) {
                sno = sno + 1;
            }
            h1.setText("" + sno);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug1() {
        try {
            int count = 0;
            String query = "select distinct desig from staff_entry";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select distinct desig from staff_entry";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support = AutoCompleteSupport.install(
                    h5, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug2() {
        try {
            int count = 0;
            String query = "select distinct sname from staff_entry";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select distinct sname from staff_entry";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support1 = AutoCompleteSupport.install(
                    h8, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug3() {
        try {
            int count = 0;
            String query = "select distinct fname from staff_entry";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select distinct fname from staff_entry";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support2 = AutoCompleteSupport.install(
                    h9, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug4() {
        try {
            int count = 0;
            String query = "select distinct bg from staff_entry";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select distinct bg from staff_entry";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support3 = AutoCompleteSupport.install(
                    h11, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug5() {
        try {
            int count = 0;
            String query = "select distinct nation from staff_entry";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select distinct nation from staff_entry";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support4 = AutoCompleteSupport.install(
                    h14, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug6() {
        try {
            int count = 0;
            String query = "select distinct add1 from staff_entry";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select distinct add1 from staff_entry";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support5 = AutoCompleteSupport.install(
                    h15, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug7() {
        try {
            int count = 0;
            String query = "select distinct add2 from staff_entry";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select distinct add2 from staff_entry";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support6 = AutoCompleteSupport.install(
                    h16, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug8() {
        try {
            int count = 0;
            String query = "select distinct add3 from staff_entry";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select distinct add3 from staff_entry";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support7 = AutoCompleteSupport.install(
                    h17, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug9() {
        try {
            int count = 0;
            String query = "select distinct area from staff_entry";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select distinct area from staff_entry";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support8 = AutoCompleteSupport.install(
                    h18, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug11() {
        try {
            int count = 0;
            String query = "select distinct job from staff_entry";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select distinct job from staff_entry";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support10 = AutoCompleteSupport.install(
                    h27, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_sug12() {
        try {
            int count = 0;
            String query = "select distinct account from staff_entry";
            r = util.doQuery(query);
            while (r.next()) {
                count = count + 1;
            }
            query = "select distinct account from staff_entry";
            r = util.doQuery(query);
            Object f[] = new Object[count];
            int index = 0;
            while (r.next()) {
                f[index] = r.getString(1);
                index++;
            }
            support11 = AutoCompleteSupport.install(
                    h4, GlazedLists.eventListOf(f));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void get_validate() {
        if (h5.getSelectedItem() == null || h5.getSelectedItem() == "") {
            JOptionPane.showMessageDialog(this, "Enter Designation ?", "Designation", JOptionPane.ERROR_MESSAGE);
            h5.requestFocus();
            return;
        }
        if (h8.getSelectedItem() == null || h8.getSelectedItem() == "") {
            JOptionPane.showMessageDialog(this, "Enter Staff Name ?", "Staff Name", JOptionPane.ERROR_MESSAGE);
            h8.requestFocus();
            return;
        }
        if (h4.getSelectedItem() == null || h4.getSelectedItem() == "") {
            h4.setSelectedItem(".");
        }
        if (h9.getSelectedItem() == null || h9.getSelectedItem() == "") {
            h9.setSelectedItem(".");
        }
        if (h11.getSelectedItem() == null || h11.getSelectedItem() == "") {
            h11.setSelectedItem(".");
        }
        if (h14.getSelectedItem() == null || h14.getSelectedItem() == "") {
            h14.setSelectedItem(".");
        }
        if (h15.getSelectedItem() == null || h15.getSelectedItem() == "") {
            h15.setSelectedItem(".");
        }
        if (h16.getSelectedItem() == null || h16.getSelectedItem() == "") {
            h16.setSelectedItem(".");
        }
        if (h17.getSelectedItem() == null || h17.getSelectedItem() == "") {
            h17.setSelectedItem(".");
        }
        if (h18.getSelectedItem() == null || h18.getSelectedItem() == "") {
            h18.setSelectedItem(".");
        }
        if (h27.getSelectedItem() == null || h27.getSelectedItem() == "") {
            h27.setSelectedItem(".");
        }

        if (h19.getText().equals("")) {
            h19.setText(".");
        }
        if (h20.getText().equals("")) {
            h20.setText(".");
        }
        if (h21.getText().equals("")) {
            h21.setText(".");
        }
        if (h22.getText().equals("")) {
            h22.setText(".");
        }
        if (h24.getText().equals("")) {
            h24.setText(".");
        }
        if (h25.getText().equals("")) {
            h25.setText(".");
        }
        if (h26.getText().equals("")) {
            h26.setText(".");
        }
        if (h28.getText().equals("")) {
            h28.setText(".");
        }
        if (h29.getText().equals("")) {
            h29.setText(".");
        }
        if (h30.getText().equals("")) {
            h30.setText(".");
        }
        if (h31.getText().equals("")) {
            h31.setText(".");
        }
        if (h32.getText().equals("")) {
            h32.setText("" + 0);
        }
        Date d = new Date();
        SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
        h2.setText(g.format(d));
        h3.setText(g1.format(d));

        if (h7.getText().equals("")) {
            h7.setText(h2.getText());
        }
        if (h12.getText().equals("")) {
            h12.setText("01/01/0001");
        }
    }

    void save() {
        try {
            get_validate();
            get_sno_no();

            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText());
            String date = (new SimpleDateFormat("yyyy/MM/dd").format(nm));
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(h7.getText());
            String jdate = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));
            Date nm2 = new SimpleDateFormat("dd/MM/yyyy").parse(h12.getText());
            String dob = (new SimpleDateFormat("yyyy/MM/dd").format(nm2));

            String sno = h1.getText();
            String time = h3.getText();
            String account = h4.getSelectedItem().toString();
            String desig = h5.getSelectedItem().toString();
            String sid = h6.getText();
            String sname = h8.getSelectedItem().toString();
            String fname = h9.getSelectedItem().toString();
            String gender = h10.getSelectedItem().toString();
            String bg = h11.getSelectedItem().toString();
            String religion = h13.getSelectedItem().toString();
            String nation = h14.getSelectedItem().toString();
            String add1 = h15.getSelectedItem().toString();
            String add2 = h16.getSelectedItem().toString();
            String add3 = h17.getSelectedItem().toString();
            String area = h18.getSelectedItem().toString();
            String pincode = h19.getText();
            String mobile = h20.getText();
            String phone = h21.getText();
            String email = h22.getText();

            String mstatus = h23.getSelectedItem().toString();
            String family = h24.getText();
            String edu = h25.getText();
            String exp = h26.getText();
            String job = h27.getSelectedItem().toString();
            String langu = h28.getText();
            String adhaar = h29.getText();
            String acno = h30.getText();
            String remarks = h31.getText();
            String salary = h32.getText();
            boolean selva = false;
            String query = "select distinct sid from staff_entry where sid='" + sid + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == true) {
                JOptionPane.showMessageDialog(this, "Staff Id Already Exist!", "Already Exit!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int aa = JOptionPane.showConfirmDialog(this, "<html><h1>Want to Save ?</h1></html>", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (aa == JOptionPane.NO_OPTION) {
                return;
            }
            String status = "Working", edate = "0001/01/01";
            query = "insert into staff_entry values ('" + sno + "','" + date + "','" + time + "','" + account + "','" + desig + "','" + sid + "','" + jdate + "','" + sname + "','" + fname + "','" + gender + "','" + bg + "','" + dob + "','" + religion + "','" + nation + "','" + add1 + "','" + add2 + "','" + add3 + "','" + area + "','" + pincode + "','" + mobile + "','" + phone + "','" + email + "','" + mstatus + "','" + family + "','" + edu + "','" + exp + "','" + job + "','" + langu + "','" + adhaar + "','" + acno + "','" + remarks + "','" + status + "','" + edate + "','" + salary + "')";
            int count = util.doManipulation(query);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "<html><h1>Saved Successfully</h1></html>", "Saved", JOptionPane.PLAIN_MESSAGE);
                save_image();
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    void save_image() {
        try {
            int a = img.length();
            if (a > 0) {
                FileInputStream m = new FileInputStream(img);
                byte bb[] = new byte[(int) m.available()];
                m.read(bb);
                boolean selva = false;
                ArrayList query1 = new ArrayList();
                String query = "select photo from staff_photo where sid='" + h6.getText() + "'";
                r = util.doQuery(query);
                while (r.next()) {
                    selva = true;
                }
                if (selva == true) {
                    query1.add("delete from staff_photo where sid='" + h6.getText() + "'");
                }
                query1.add("insert into staff_photo values('" + h6.getText() + "','" + bb + "')");
                int count = util.doManipulation_Batch(query1);
                if (count > 0) {
//JOptionPane.showMessageDialog(this, "Picture Saved","Picture",JOptionPane.PLAIN_MESSAGE);
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void clear() {
        try {
            h1.setText("--");
            Date d = new Date();
            SimpleDateFormat g = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat g1 = new SimpleDateFormat("hh:mm:ss a");
            h2.setText(g.format(d));
            h3.setText(g1.format(d));
            support.uninstall();
            support1.uninstall();
            support2.uninstall();
            support3.uninstall();
            support4.uninstall();
            support5.uninstall();
            support6.uninstall();
            support7.uninstall();
            support8.uninstall();
            support10.uninstall();
            support11.uninstall();

            updatebutton.setVisible(false);
            deletebutton.setVisible(false);
            savebutton.setVisible(true);
            viewbutton.setVisible(true);
            get_sug1();
            get_sug2();
            get_sug3();
            get_sug4();
            get_sug5();
            get_sug6();
            get_sug7();
            get_sug8();
            get_sug9();
            get_sug11();
            get_sug12();

            h6.setText("");
            h7.setText("");
            h12.setText("");
            h19.setText("");
            h20.setText("");
            h21.setText("");
            h22.setText("");
            h24.setText("");
            h25.setText("");
            h26.setText("");
            h28.setText("");
            h29.setText("");
            h30.setText("");
            h31.setText("");
            h32.setText("");
            h6.setEnabled(true);
            picturel.setIcon(null);
            h4.requestFocus();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void view(String sno) {
        try {
            picturel.setIcon(null);
            String query = "select distinct sno,date_format(dat,'%d/%m/%Y'),tim,account,desig,sid,date_format(jdate,'%d/%m/%Y'),sname,fname,gender,bg,date_format(dob,'%d/%m/%Y'),religion,nation,add1,add2,add3,area,pincode,mobile,phone,email,mstatus,family,edu,exp,job,langu,adhaar,acno,remarks,salary from staff_entry where sno='" + sno + "' ";
            r = util.doQuery(query);
            boolean selva = false;
            while (r.next()) {
                h1.setText(r.getString(1));
                h2.setText(r.getString(2));
                h3.setText(r.getString(3));
                h4.setSelectedItem(r.getString(4));
                h5.setSelectedItem(r.getString(5));
                h6.setText(r.getString(6));
                h7.setText(r.getString(7));
                h8.setSelectedItem(r.getString(8));
                h9.setSelectedItem(r.getString(9));
                h10.setSelectedItem(r.getString(10));
                h11.setSelectedItem(r.getString(11));
                h12.setText(r.getString(12));
                h13.setSelectedItem(r.getString(13));
                h14.setSelectedItem(r.getString(14));
                h15.setSelectedItem(r.getString(15));
                h16.setSelectedItem(r.getString(16));
                h17.setSelectedItem(r.getString(17));
                h18.setSelectedItem(r.getString(18));
                h19.setText(r.getString(19));
                h20.setText(r.getString(20));
                h21.setText(r.getString(21));
                h22.setText(r.getString(22));
                h23.setSelectedItem(r.getString(23));
                h24.setText(r.getString(24));
                h25.setText(r.getString(25));
                h26.setText(r.getString(26));
                h27.setSelectedItem(r.getString(27));
                h28.setText(r.getString(28));
                h29.setText(r.getString(29));
                h30.setText(r.getString(30));
                h31.setText(r.getString(31));
                h32.setText(r.getString(32));
                selva = true;
            }
            if (selva == true) {
                savebutton.setVisible(false);
                viewbutton.setVisible(false);
                updatebutton.setVisible(true);
                deletebutton.setVisible(true);
                h6.setEnabled(false);
            }

            ImageIcon im = new ImageIcon();
            boolean shiva = false;
            query = "select photo from staff_photo where sid='" + h6.getText() + "'";
            r = util.doQuery(query);
            while (r.next()) {
                byte[] b = r.getBytes("photo");
                im = new ImageIcon(b);
                shiva = true;
            }
            if (shiva == true) {
                ImageIcon icon2 = new ImageIcon(new ImageIcon(im.getImage()).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
                picturel.setIcon(icon2);
            } else if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void delete(String sno) {
        try {
            int as = JOptionPane.showConfirmDialog(this, "Want to Delete ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            boolean selva = false;
            String query = "select distinct sno from staff_entry where sno='" + sno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ArrayList query1 = new ArrayList();
            query1.add("delete from staff_entry where sno='" + sno + "'");
            query1.add("delete from staff_photo where sid='" + h6.getText() + "'");
            int count = util.doManipulation_Batch(query1);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Deleted Successfully", "Deleted", JOptionPane.PLAIN_MESSAGE);
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void Update(String sno) {
        try {
            int as = JOptionPane.showConfirmDialog(this, "Want to Update ?", "Are You Sure", JOptionPane.YES_NO_OPTION);
            if (as == JOptionPane.NO_OPTION) {
                return;
            }
            get_validate();
            boolean selva = false;
            String query = "select distinct sno from staff_entry where sno='" + sno + "'";
            r = util.doQuery(query);
            while (r.next()) {
                selva = true;
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Date nm = new SimpleDateFormat("dd/MM/yyyy").parse(h2.getText());
            String date = (new SimpleDateFormat("yyyy/MM/dd").format(nm));
            Date nm1 = new SimpleDateFormat("dd/MM/yyyy").parse(h7.getText());
            String jdate = (new SimpleDateFormat("yyyy/MM/dd").format(nm1));
            Date nm2 = new SimpleDateFormat("dd/MM/yyyy").parse(h12.getText());
            String dob = (new SimpleDateFormat("yyyy/MM/dd").format(nm2));

            String account = h4.getSelectedItem().toString();
            String desig = h5.getSelectedItem().toString();
            String sid = h6.getText();
            String sname = h8.getSelectedItem().toString();
            String fname = h9.getSelectedItem().toString();
            String gender = h10.getSelectedItem().toString();
            String bg = h11.getSelectedItem().toString();
            String religion = h13.getSelectedItem().toString();
            String nation = h14.getSelectedItem().toString();
            String add1 = h15.getSelectedItem().toString();
            String add2 = h16.getSelectedItem().toString();
            String add3 = h17.getSelectedItem().toString();
            String area = h18.getSelectedItem().toString();
            String pincode = h19.getText();
            String mobile = h20.getText();
            String phone = h21.getText();
            String email = h22.getText();

            String mstatus = h23.getSelectedItem().toString();
            String family = h24.getText();
            String edu = h25.getText();
            String exp = h26.getText();
            String job = h27.getSelectedItem().toString();
            String langu = h28.getText();
            String adhaar = h29.getText();
            String acno = h30.getText();
            String remarks = h31.getText();
            String salary = h32.getText();
            query = "update staff_entry set account='" + account + "',desig='" + desig + "',jdate='" + jdate + "',sname='" + sname + "',fname='" + fname + "',gender='" + gender + "',bg='" + bg + "',dob='" + dob + "',religion='" + religion + "',nation='" + nation + "',add1='" + add1 + "',add2='" + add2 + "',add3='" + add3 + "',area='" + area + "',pincode='" + pincode + "',mobile='" + mobile + "',phone='" + phone + "',email='" + email + "',mstatus='" + mstatus + "',family='" + family + "',edu='" + edu + "',exp='" + exp + "',job='" + job + "',langu='" + langu + "',adhaar='" + adhaar + "',acno='" + acno + "',remarks='" + remarks + "',salary='" + salary + "' where sno='" + sno + "' ";
            int count = util.doManipulation(query);
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Updated Successfully", "Updated", JOptionPane.PLAIN_MESSAGE);
                save_image();
                clear();
            }
        } catch (HeadlessException | ClassNotFoundException | SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public staff_entry(DataUtil util) {
        initComponents();
        this.util = util;
        button_short();
        get_sug1();
        get_sug2();
        get_sug3();
        get_sug4();
        get_sug5();
        get_sug6();
        get_sug7();
        get_sug8();
        get_sug9();
        get_sug11();
        get_sug12();
        h4.requestFocus();
        updatebutton.setVisible(false);
        deletebutton.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlelablel = new javax.swing.JLabel();
        closebutton = new javax.swing.JButton();
        savebutton = new javax.swing.JButton();
        updatebutton = new javax.swing.JButton();
        viewbutton = new javax.swing.JButton();
        deletebutton = new javax.swing.JButton();
        clearbutton = new javax.swing.JButton();
        prebutton = new javax.swing.JButton();
        nextbutton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        h1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        h2 = new javax.swing.JTextField();
        h3 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        h4 = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        h5 = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        h22 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        h8 = new javax.swing.JComboBox();
        jLabel32 = new javax.swing.JLabel();
        h9 = new javax.swing.JComboBox();
        h7 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        h14 = new javax.swing.JComboBox();
        h15 = new javax.swing.JComboBox();
        h16 = new javax.swing.JComboBox();
        jLabel37 = new javax.swing.JLabel();
        h17 = new javax.swing.JComboBox();
        h12 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        h21 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        h20 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        h10 = new javax.swing.JComboBox();
        jLabel40 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        h13 = new javax.swing.JComboBox();
        jLabel53 = new javax.swing.JLabel();
        jCalendarButton3 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        jCalendarButton2 = new net.sourceforge.jcalendarbutton.JCalendarButton();
        h11 = new javax.swing.JComboBox();
        h18 = new javax.swing.JComboBox();
        h19 = new javax.swing.JTextField();
        h6 = new javax.swing.JTextField();
        h29 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        picturel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        h27 = new javax.swing.JComboBox();
        h23 = new javax.swing.JComboBox();
        h31 = new javax.swing.JTextField();
        h28 = new javax.swing.JTextField();
        h30 = new javax.swing.JTextField();
        h24 = new javax.swing.JTextField();
        h25 = new javax.swing.JTextField();
        h26 = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        h32 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        uploadbutton = new javax.swing.JButton();
        resetbutton = new javax.swing.JButton();

        setClosable(true);
        getContentPane().setLayout(null);

        titlelablel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        titlelablel.setText("Staff Profile");
        getContentPane().add(titlelablel);
        titlelablel.setBounds(10, 0, 170, 30);

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
        closebutton.setBounds(960, 550, 130, 50);

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
        savebutton.setBounds(310, 550, 130, 50);

        updatebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        updatebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/upload45.png"))); // NOI18N
        updatebutton.setMnemonic('u');
        updatebutton.setText("Update");
        updatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(updatebutton);
        updatebutton.setBounds(180, 550, 130, 50);

        viewbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        viewbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/view45.png"))); // NOI18N
        viewbutton.setMnemonic('v');
        viewbutton.setText("View");
        viewbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(viewbutton);
        viewbutton.setBounds(700, 550, 130, 50);

        deletebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        deletebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete45.png"))); // NOI18N
        deletebutton.setMnemonic('d');
        deletebutton.setText("Delete");
        deletebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(deletebutton);
        deletebutton.setBounds(50, 550, 130, 50);

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
        clearbutton.setBounds(830, 550, 130, 50);

        prebutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        prebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pre45.png"))); // NOI18N
        prebutton.setMnemonic('r');
        prebutton.setText("Previous");
        prebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prebuttonActionPerformed(evt);
            }
        });
        getContentPane().add(prebutton);
        prebutton.setBounds(440, 550, 130, 50);

        nextbutton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        nextbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/next45.png"))); // NOI18N
        nextbutton.setMnemonic('n');
        nextbutton.setText("Next");
        nextbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(nextbutton);
        nextbutton.setBounds(570, 550, 130, 50);

        jPanel1.setLayout(null);

        jLabel27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel27.setText("Entry No");
        jPanel1.add(jLabel27);
        jLabel27.setBounds(0, 0, 80, 30);

        h1.setEditable(false);
        h1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h1.setText("--");
        h1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                h1FocusGained(evt);
            }
        });
        jPanel1.add(h1);
        h1.setBounds(80, 0, 230, 30);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("  Date");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(320, 0, 50, 30);

        h2.setEditable(false);
        h2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h2);
        h2.setBounds(370, 0, 100, 30);

        h3.setEditable(false);
        h3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h3);
        h3.setBounds(470, 0, 120, 30);

        jLabel23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel23.setText("Department");
        jPanel1.add(jLabel23);
        jLabel23.setBounds(0, 30, 80, 30);

        h4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        h4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h4ActionPerformed(evt);
            }
        });
        jPanel1.add(h4);
        h4.setBounds(80, 30, 510, 30);

        jLabel26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel26.setText("Date of Birth");
        jPanel1.add(jLabel26);
        jLabel26.setBounds(330, 210, 80, 30);

        jLabel29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel29.setText("Designation");
        jPanel1.add(jLabel29);
        jLabel29.setBounds(0, 60, 80, 30);

        h5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        jPanel1.add(h5);
        h5.setBounds(80, 60, 510, 30);

        jLabel28.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel28.setText("Date of Join");
        jPanel1.add(jLabel28);
        jLabel28.setBounds(320, 90, 90, 30);

        jLabel30.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel30.setText("Staff Id");
        jPanel1.add(jLabel30);
        jLabel30.setBounds(0, 90, 80, 30);

        h22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h22);
        h22.setBounds(80, 420, 510, 30);

        jLabel31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel31.setText("Staff Name");
        jPanel1.add(jLabel31);
        jLabel31.setBounds(0, 120, 80, 30);

        h8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        jPanel1.add(h8);
        h8.setBounds(80, 120, 510, 30);

        jLabel32.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel32.setText("Father Name");
        jPanel1.add(jLabel32);
        jLabel32.setBounds(0, 150, 90, 30);

        h9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        jPanel1.add(h9);
        h9.setBounds(80, 150, 510, 30);

        h7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h7);
        h7.setBounds(410, 90, 140, 30);

        jLabel34.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel34.setText("Blood Group");
        jPanel1.add(jLabel34);
        jLabel34.setBounds(0, 210, 90, 30);

        jLabel35.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel35.setText("Pin Code");
        jPanel1.add(jLabel35);
        jLabel35.setBounds(330, 360, 80, 30);

        jLabel36.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel36.setText("Religion");
        jPanel1.add(jLabel36);
        jLabel36.setBounds(0, 240, 80, 30);

        h14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        jPanel1.add(h14);
        h14.setBounds(410, 240, 180, 30);

        h15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        jPanel1.add(h15);
        h15.setBounds(80, 270, 510, 30);

        h16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h16.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        jPanel1.add(h16);
        h16.setBounds(80, 300, 510, 30);

        jLabel37.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel37.setText("Address");
        jPanel1.add(jLabel37);
        jLabel37.setBounds(0, 270, 80, 30);

        h17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h17.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        jPanel1.add(h17);
        h17.setBounds(80, 330, 510, 30);

        h12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h12);
        h12.setBounds(410, 210, 140, 30);

        jLabel39.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel39.setText("Area /City");
        jPanel1.add(jLabel39);
        jLabel39.setBounds(0, 360, 80, 30);

        h21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h21);
        h21.setBounds(410, 390, 180, 30);

        jLabel41.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel41.setText("Mobile No");
        jPanel1.add(jLabel41);
        jLabel41.setBounds(0, 390, 80, 30);

        h20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h20);
        h20.setBounds(80, 390, 230, 30);

        jLabel42.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel42.setText("Email");
        jPanel1.add(jLabel42);
        jLabel42.setBounds(0, 420, 80, 30);

        h10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female", "Others" }));
        jPanel1.add(h10);
        h10.setBounds(80, 180, 510, 30);

        jLabel40.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel40.setText("Gender");
        jPanel1.add(jLabel40);
        jLabel40.setBounds(0, 180, 80, 30);

        jLabel43.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel43.setText("Nationality");
        jPanel1.add(jLabel43);
        jLabel43.setBounds(330, 240, 80, 30);

        h13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hindu", "Christian", "Muslim", "Buddhis", "Sikhis", "Nonreligious", "Primal-Indigenous", "Juche", "Spiritism", "Judaism", "Bahai", "Jainism", "Shinto", "Cao Dai", "Zoroastrianism", "Tenrikyo", "Neo-Paganism", "Unitarian-Universalism", "African Traditional and Diasporic", "Chinese Traditional Religion" }));
        jPanel1.add(h13);
        h13.setBounds(80, 240, 230, 30);

        jLabel53.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel53.setText("Alt.Con.Nos");
        jPanel1.add(jLabel53);
        jLabel53.setBounds(330, 390, 80, 30);

        jCalendarButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
        jCalendarButton3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton3PropertyChange(evt);
            }
        });
        jPanel1.add(jCalendarButton3);
        jCalendarButton3.setBounds(550, 210, 40, 30);

        jCalendarButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cal40.png"))); // NOI18N
        jCalendarButton2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendarButton2PropertyChange(evt);
            }
        });
        jPanel1.add(jCalendarButton2);
        jCalendarButton2.setBounds(550, 90, 40, 30);

        h11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        jPanel1.add(h11);
        h11.setBounds(80, 210, 230, 30);

        h18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h18.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        jPanel1.add(h18);
        h18.setBounds(80, 360, 230, 30);

        h19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h19);
        h19.setBounds(410, 360, 180, 30);

        h6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h6);
        h6.setBounds(80, 90, 230, 30);

        h29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(h29);
        h29.setBounds(80, 450, 510, 30);

        jLabel50.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel50.setText("Adhaar No");
        jPanel1.add(jLabel50);
        jLabel50.setBounds(0, 450, 80, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(20, 40, 590, 480);

        picturel.setForeground(new java.awt.Color(204, 204, 204));
        picturel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        picturel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));
        picturel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(picturel);
        picturel.setBounds(890, 10, 200, 200);

        jPanel2.setLayout(null);

        jLabel44.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel44.setText("Marital Status");
        jPanel2.add(jLabel44);
        jLabel44.setBounds(0, 0, 120, 30);

        jLabel45.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel45.setText("Family Details");
        jPanel2.add(jLabel45);
        jLabel45.setBounds(0, 30, 120, 30);

        jLabel46.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel46.setText("Education Details");
        jPanel2.add(jLabel46);
        jLabel46.setBounds(0, 60, 120, 30);

        jLabel47.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel47.setText("Experince Details");
        jPanel2.add(jLabel47);
        jLabel47.setBounds(0, 90, 120, 30);

        jLabel48.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel48.setText("Job Details");
        jPanel2.add(jLabel48);
        jLabel48.setBounds(0, 120, 120, 30);

        jLabel49.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel49.setText("Languages Known");
        jPanel2.add(jLabel49);
        jLabel49.setBounds(0, 150, 120, 30);

        jLabel51.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel51.setText("Salary / Day");
        jPanel2.add(jLabel51);
        jLabel51.setBounds(0, 210, 120, 30);

        jLabel52.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel52.setText("Remarks");
        jPanel2.add(jLabel52);
        jLabel52.setBounds(0, 240, 120, 30);

        h27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h27.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "." }));
        jPanel2.add(h27);
        h27.setBounds(120, 120, 330, 30);

        h23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        h23.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Single", "Married", "Separated" }));
        jPanel2.add(h23);
        h23.setBounds(120, 0, 330, 30);

        h31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel2.add(h31);
        h31.setBounds(120, 240, 330, 30);

        h28.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel2.add(h28);
        h28.setBounds(120, 150, 330, 30);

        h30.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel2.add(h30);
        h30.setBounds(120, 180, 330, 30);

        h24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel2.add(h24);
        h24.setBounds(120, 30, 330, 30);

        h25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel2.add(h25);
        h25.setBounds(120, 60, 330, 30);

        h26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel2.add(h26);
        h26.setBounds(120, 90, 330, 30);

        jLabel54.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel54.setText("Bank A\\c No");
        jPanel2.add(jLabel54);
        jLabel54.setBounds(0, 180, 120, 30);

        h32.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel2.add(h32);
        h32.setBounds(120, 210, 330, 30);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(640, 250, 450, 270);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(620, 40, 10, 480);

        uploadbutton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        uploadbutton.setMnemonic('u');
        uploadbutton.setText("Upload");
        uploadbutton.setToolTipText("Use (Alt+U)  or Click Here to Upload");
        uploadbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(uploadbutton);
        uploadbutton.setBounds(890, 210, 100, 30);

        resetbutton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        resetbutton.setMnemonic('t');
        resetbutton.setText("Reset");
        resetbutton.setToolTipText("Use (Alt+T)  or Click Here to Reset Picture");
        resetbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(resetbutton);
        resetbutton.setBounds(990, 210, 100, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebuttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closebuttonActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        save();
    }//GEN-LAST:event_savebuttonActionPerformed

    private void clearbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbuttonActionPerformed
        clear();
        // TODO add your handling code here:
    }//GEN-LAST:event_clearbuttonActionPerformed

    private void viewbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewbuttonActionPerformed

        if (h6.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Enter Staff Id ?", "Staff Id", JOptionPane.ERROR_MESSAGE);
            h6.requestFocus();
            return;
        }
        try {
            String sno = "";
            String query = "select distinct sno from staff_entry where sid='" + h6.getText() + "' ";
            r = util.doQuery(query);
            while (r.next()) {
                sno = r.getString(1);
            }
            view(sno);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

    }//GEN-LAST:event_viewbuttonActionPerformed

    private void deletebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebuttonActionPerformed
        String cid = h1.getText();
        delete(cid);
    }//GEN-LAST:event_deletebuttonActionPerformed

    private void h1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h1FocusGained
        h4.requestFocus();
    }//GEN-LAST:event_h1FocusGained

    private void h4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h4ActionPerformed

    }//GEN-LAST:event_h4ActionPerformed

    private void prebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prebuttonActionPerformed

        try {
            String query;
            String sno = h1.getText();
            if (sno.equalsIgnoreCase("--")) {
                query = "select max(sno) from staff_entry";
            } else {
                query = "select distinct sno from staff_entry where sno < '" + sno + "' order by sno desc limit 1";
            }

            r = util.doQuery(query);
            boolean selva = false;
            String search_sno = "";
            while (r.next()) {
                selva = true;
                search_sno = r.getString(1);
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            view(search_sno);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_prebuttonActionPerformed

    private void nextbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextbuttonActionPerformed

        try {
            String query;
            String sno = h1.getText();
            if (sno.equalsIgnoreCase("--")) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            query = "select distinct sno from staff_entry where sno > '" + sno + "' order by sno limit 1";
            r = util.doQuery(query);
            boolean selva = false;
            String search_sno = "";
            while (r.next()) {
                selva = true;
                search_sno = r.getString(1);
            }
            if (selva == false) {
                JOptionPane.showMessageDialog(this, "No Records Were Found!", "No Records", JOptionPane.ERROR_MESSAGE);
                return;
            }
            view(search_sno);
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_nextbuttonActionPerformed

    private void updatebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatebuttonActionPerformed
        String sno = h1.getText();
        Update(sno);
    }//GEN-LAST:event_updatebuttonActionPerformed

    private void jCalendarButton2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendarButton2PropertyChange
        try {
            if (evt.getNewValue() instanceof Date) {
                String ses = evt.getNewValue().toString();
                Date nm = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(ses);
                String date = (new SimpleDateFormat("dd/MM/yyyy").format(nm));
                h7.setText(date);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_jCalendarButton2PropertyChange

    private void jCalendarButton3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendarButton3PropertyChange
        try {
            if (evt.getNewValue() instanceof Date) {
                String ses = evt.getNewValue().toString();
                Date nm = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(ses);
                String date = (new SimpleDateFormat("dd/MM/yyyy").format(nm));
                h12.setText(date);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

// TODO add your handling code here:
    }//GEN-LAST:event_jCalendarButton3PropertyChange

    private void uploadbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadbuttonActionPerformed

        try {
            JFileChooser j = new JFileChooser();
            j.showOpenDialog(this);
            img = j.getSelectedFile().getPath();

            BufferedImage image = ImageIO.read(new File(img));
            ImageIcon icon;
            if (image.getWidth() > 200 && image.getHeight() <= 200) {
                icon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(200, image.getHeight(), Image.SCALE_DEFAULT));
            } else if (image.getHeight() > 200 && image.getWidth() <= 200) {
                icon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(image.getWidth(), 200, Image.SCALE_DEFAULT));
            } else if (image.getHeight() > 200 && image.getWidth() > 200) {
                icon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
            } else {
                icon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
            }
            picturel.setIcon(icon);
        } catch (HeadlessException | IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

    }//GEN-LAST:event_uploadbuttonActionPerformed

    private void resetbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetbuttonActionPerformed

        picturel.setIcon(null);
    }//GEN-LAST:event_resetbuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearbutton;
    private javax.swing.JButton closebutton;
    private javax.swing.JButton deletebutton;
    private javax.swing.JTextField h1;
    private javax.swing.JComboBox h10;
    private javax.swing.JComboBox h11;
    private javax.swing.JTextField h12;
    private javax.swing.JComboBox h13;
    private javax.swing.JComboBox h14;
    private javax.swing.JComboBox h15;
    private javax.swing.JComboBox h16;
    private javax.swing.JComboBox h17;
    private javax.swing.JComboBox h18;
    private javax.swing.JTextField h19;
    private javax.swing.JTextField h2;
    private javax.swing.JTextField h20;
    private javax.swing.JTextField h21;
    private javax.swing.JTextField h22;
    private javax.swing.JComboBox h23;
    private javax.swing.JTextField h24;
    private javax.swing.JTextField h25;
    private javax.swing.JTextField h26;
    private javax.swing.JComboBox h27;
    private javax.swing.JTextField h28;
    private javax.swing.JTextField h29;
    private javax.swing.JTextField h3;
    private javax.swing.JTextField h30;
    private javax.swing.JTextField h31;
    private javax.swing.JTextField h32;
    private javax.swing.JComboBox h4;
    private javax.swing.JComboBox h5;
    private javax.swing.JTextField h6;
    private javax.swing.JTextField h7;
    private javax.swing.JComboBox h8;
    private javax.swing.JComboBox h9;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton2;
    private net.sourceforge.jcalendarbutton.JCalendarButton jCalendarButton3;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton nextbutton;
    private javax.swing.JLabel picturel;
    private javax.swing.JButton prebutton;
    private javax.swing.JButton resetbutton;
    private javax.swing.JButton savebutton;
    private javax.swing.JLabel titlelablel;
    private javax.swing.JButton updatebutton;
    private javax.swing.JButton uploadbutton;
    private javax.swing.JButton viewbutton;
    // End of variables declaration//GEN-END:variables
}
