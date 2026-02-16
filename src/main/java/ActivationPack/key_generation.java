package ActivationPack;

import com.selrom.db.DataUtil;
import java.sql.Connection;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.UIManager;
import java.util.UUID;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
/**
 *
 * @author Selrom Software
 */
 public final class key_generation extends javax.swing.JFrame {
 DataUtil util=null;
 Connection c;
 
  void get_defaults()
  {
  setSize(523, 640);
  setLocationRelativeTo(getRootPane()); 
  setResizable(false);  
  setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
  }
  void get_connection()
  {
  try
  {
    String password="VMfO8TllTQoc6Llw";
    String username="3gKZsiyCbqqdp7s.root";
    String url="jdbc:mysql://gateway01.ap-southeast-1.prod.aws.tidbcloud.com:4000/pos_license?createDatabaseIfNotExist=true";
    String driver="com.mysql.cj.jdbc.Driver";
  Class.forName(driver);
  c = DriverManager.getConnection( url, username, password );    
  }catch(ClassNotFoundException | SQLException e){System.out.println(e.toString());}
  }
 public class sample2 extends DefaultTableModel
{
@Override
public void addColumn(Object columnName)
{
super.addColumn(columnName);
}
@Override
public void addRow(Object[] rowData )
{
super.addRow(rowData);
}
@Override
public boolean isCellEditable(int row, int column)
{
return column == 14; 
}
}
sample2 s2=new sample2();
final void load_list_table()
{    
s2.addColumn("Key");
jTable1.setModel(s2);
jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
jTable1.getColumnModel().getColumn(0).setPreferredWidth(444);
String Ta = "Tahoma";
int Bold = 1, size = 18;
jTable1.getTableHeader().setFont(new Font(Ta, Bold, size));
}
  void generate_keys()
  {
  try
  {
  int hmany=Integer.parseInt(h1.getText());
  for (int i = 0; i < hmany; i++) 
  {
  String key=generateRandomStringByUUID();
  s2.addRow(new Object[]{key.toUpperCase()});
  }
  totl.setText("  Total Records: "+jTable1.getRowCount());
  }catch(NumberFormatException e){System.out.println(e.toString());}
  }
  public static String generateRandomStringByUUID() 
  {
  return UUID.randomUUID().toString();
  } 
  void remove_duplicates()
  {
  try
  {
  PreparedStatement t;
  ResultSet r;
  boolean selva=false;
  int count=0;
  for(int i=0;i<jTable1.getRowCount();i++)
  {
  t=c.prepareStatement("select key1 from call_logs where key1=?");
  t.setObject(1, jTable1.getValueAt(i, 0));
  r=t.executeQuery();
  while(r.next())
  {
  selva=true;    
  }
  if(selva==true)
  {
  s2.removeRow(i);
  count=count+1;
  }
  }
  totl.setText("  Total Records: "+jTable1.getRowCount());
  
  JOptionPane.showMessageDialog(this, "Duplicates Removed: "+count,"Removed",JOptionPane.PLAIN_MESSAGE);
  }catch(SQLException e){System.out.println(e.toString());}
  }
  void save()
  {
  try
  {
  int as=JOptionPane.showConfirmDialog(this, "Want to Save ?","Are You Sure",JOptionPane.YES_NO_OPTION);
  if(as==JOptionPane.NO_OPTION)
  {
  return;    
  }
  int sno=1;
  PreparedStatement t;
  t=c.prepareStatement("select max(sno) from call_logs");
  ResultSet r=t.executeQuery();
  boolean selva=false;
  while(r.next())
  {
  selva=true; sno=r.getInt(1);
  }
  if(selva==true) { sno=sno+1;}
  Date d=new Date();
  SimpleDateFormat g=new SimpleDateFormat("yyyy-MM-dd");
  SimpleDateFormat g1=new SimpleDateFormat("hh:mm:ss a");
  String date=g.format(d);
  String time=g1.format(d);
  Statement s;
  s=c.createStatement();
  String status="Available",entry_no=".",cid=".",keydetails=".",user=".",last=".";
  for(int i=0;i<jTable1.getRowCount();i++)
  {
  String key=jTable1.getValueAt(i, 0).toString();
  s.addBatch("insert into call_logs values ('"+sno+"','"+date+"','"+time+"','"+key+"','"+status+"','"+entry_no+"','"+cid+"','"+keydetails+"','"+user+"','"+last+"')");   
  sno++;
  }//for loop ends
  int av[]=s.executeBatch();
  int av1=av.length;
  if(av1>0)
  {
  JOptionPane.showMessageDialog(this, "Saved Successfully","Saved",JOptionPane.PLAIN_MESSAGE);
  clear();
  }
      
  }catch(HeadlessException | SQLException e){System.out.println(e.toString());}
  }
  void clear()
  {
  h1.setText("");
  totl.setText("");
  if(s2.getRowCount()>0)
   {
   s2.getDataVector().removeAllElements();
   s2.fireTableDataChanged();    
   } 
  }
    public key_generation() 
    {
    initComponents();
    util=new DataUtil();
    get_defaults();
    get_connection();
    load_list_table();
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        h1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        totl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 102), 3));
        jPanel1.setLayout(null);

        jButton1.setBackground(new java.awt.Color(204, 0, 59));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(153, 153, 255));
        jButton1.setText("Exit");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(360, 590, 90, 40);

        jButton2.setBackground(new java.awt.Color(204, 0, 59));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(153, 153, 255));
        jButton2.setText("Save");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(180, 590, 90, 40);

        jButton3.setBackground(new java.awt.Color(204, 0, 59));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(153, 153, 255));
        jButton3.setText("Clear");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(270, 590, 90, 40);

        jPanel2.setBackground(new java.awt.Color(204, 0, 102));
        jPanel2.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Key Generation");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(0, 0, 460, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 460, 40);

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
        jTable1.setRowHeight(27);
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(2, 80, 450, 470);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("How many Keys ?");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 50, 160, 30);

        h1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(h1);
        h1.setBounds(160, 50, 120, 30);

        jButton5.setBackground(new java.awt.Color(204, 0, 59));
        jButton5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(153, 153, 255));
        jButton5.setText("Generate Keys");
        jButton5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5);
        jButton5.setBounds(280, 50, 170, 30);

        jButton4.setBackground(new java.awt.Color(204, 0, 59));
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(153, 153, 255));
        jButton4.setText("Remove Duplicate");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(0, 590, 180, 40);

        totl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(totl);
        totl.setBounds(0, 550, 440, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
clear();   
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    generate_keys();   
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
     remove_duplicates();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    save();
    }//GEN-LAST:event_jButton2ActionPerformed

    public static void main(String args[]) {
      
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(key_generation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        UIManager.put("nimbusFocus", new Color(153, 0, 153, 255));
        UIManager.put("nimbusSelectionBackground", new Color(153, 0, 153, 255));
        UIManager.put("nimbusBase", new Color(153, 0, 153, 225));
        UIManager.put("control", new Color(255, 255, 255, 255)); 
        java.awt.EventQueue.invokeLater(() -> 
        {
            new key_generation().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField h1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel totl;
    // End of variables declaration//GEN-END:variables
}
