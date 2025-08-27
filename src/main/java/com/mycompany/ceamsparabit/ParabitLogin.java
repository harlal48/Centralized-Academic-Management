package com.mycompany.ceamsparabit;

//import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;

public class ParabitLogin extends javax.swing.JDialog {

    ParabitCeamsDb obdb;
    static ResultSet srs;
    public ParabitLogin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
//        getContentPane().setBackground(new Color(135,206,250));
       obdb=new ParabitCeamsDb();
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tf1 = new javax.swing.JTextField();
        tf2 = new javax.swing.JTextField();
        tf3 = new javax.swing.JTextField();
        b1 = new javax.swing.JButton();
        b3 = new javax.swing.JButton();
        b2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(178, 248, 157));
        setIconImage(null);

        jLabel1.setText("Login ID");

        jLabel2.setText("password");

        jLabel3.setText("OTP");

        tf1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf1ActionPerformed(evt);
            }
        });
        tf1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf1KeyPressed(evt);
            }
        });

        tf2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf2ActionPerformed(evt);
            }
        });
        tf2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf2KeyPressed(evt);
            }
        });

        tf3.setText(" ");
        tf3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf3ActionPerformed(evt);
            }
        });

        b1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        b1.setText(" Resend OTP");
        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ActionPerformed(evt);
            }
        });

        b3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        b3.setText("Login");
        b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b3ActionPerformed(evt);
            }
        });

        b2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        b2.setText("Forgot password");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tf3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(b1))
                            .addComponent(tf2)
                            .addComponent(tf1)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tf1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tf2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b1))))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b2)
                    .addComponent(b3))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tf3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf3ActionPerformed
       
    }//GEN-LAST:event_tf3ActionPerformed

    private void b1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b1ActionPerformed
     
    }//GEN-LAST:event_b1ActionPerformed

    private void b3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b3ActionPerformed
    String s1,s2,s3;
    s1=tf1.getText();
    s2=tf2.getText();
    s3=tf3.getText();
    String s= "select *from nnemprecords where loginid ='"+s1+"' and loginPSW ='"+s2+"' and Status =1";
   //String s = " select distinct nn.wardcode, nn.wardName,nn.zonecode,vv.villageversion,nn.statecode,vv.statename, nn.DistrictCode, nn.EmpName,nn.SubDistrictCode,vv.DistrictName,vv.SubDistrictName,nn.workpost From nnemprecords nn,villages vv where nn.DistrictCode = vv.DistrictCode and nn.SubDistrictCode = vv.SubDistrictCode and loginId = '"+s1+"' and loginpsw = '" + s2+ "' and status =1";
        System.out.println(s);
     
    try
    {
       
      obdb.rs =obdb.stm.executeQuery(s); //ye line waha se lekar aayegi kuchh
      srs=obdb.rs;
       if(obdb.rs.next()){
      System.out.println("Record Found");
      String ss=obdb.rs.getNString(13);
//            System.out.println(ss);
   if(ss.equals("Land Inspector")){
   LandInspector li = new LandInspector();
      this.setVisible(false);
      li.setVisible(true);
   }
   if(ss.equals("Zone Municipal Officer ")){
   ZoneInspector zi = new ZoneInspector();
      this.setVisible(false);
      zi.setVisible(true);
   }
    if(ss.equals("Municipal Commissioner ")){
   MunicipalCommissioner mc = new MunicipalCommissioner();
      this.setVisible(false);
      mc.setVisible(true);
   }
  }else{
      System.out.println("Result not found");
      
  }
    }catch(Exception e){
        System.out.println(e);
    }
 
    }//GEN-LAST:event_b3ActionPerformed

    private void tf2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf2KeyPressed
if(evt.getKeyCode()==KeyEvent.VK_ENTER ){
  
    String s1,s2,s3;
    s1=tf1.getText();
    s2=tf2.getText();
    s3=tf3.getText();
    String s= "select *from nnemprecords where loginid ='"+s1+"' and loginPSW ='"+s2+"' and Status =1";
   //String s = " select distinct nn.wardcode, nn.wardName,nn.zonecode,vv.villageversion,nn.statecode,vv.statename, nn.DistrictCode, nn.EmpName,nn.SubDistrictCode,vv.DistrictName,vv.SubDistrictName,nn.workpost From nnemprecords nn,villages vv where nn.DistrictCode = vv.DistrictCode and nn.SubDistrictCode = vv.SubDistrictCode and loginId = '"+s1+"' and loginpsw = '" + s2+ "' and status =1";
        System.out.println(s);
   
    try
    {
      obdb.rs =obdb.stm.executeQuery(s); //ye line waha se lekar aayegi kuchh
      srs=obdb.rs;
       if(obdb.rs.next()){
      System.out.println("Record Found");
      String ss=obdb.rs.getNString(13);
   if(ss.equals("Land Inspector")){
   LandInspector li = new LandInspector();
      this.setVisible(false);
      li.setVisible(true);
   }
   if(ss.equals("Zone Municipal Officer")){
   ZoneInspector zi = new ZoneInspector();
      this.setVisible(false);
      zi.setVisible(true);
   }
  }else{
      System.out.println("Result not found");
      
  }
    }catch(Exception e){
        System.out.println(e);
    }
  }     
    }//GEN-LAST:event_tf2KeyPressed

    private void tf1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf1ActionPerformed
        
    }//GEN-LAST:event_tf1ActionPerformed

    private void tf2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf2ActionPerformed
       
    }//GEN-LAST:event_tf2ActionPerformed

    private void tf1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf1KeyPressed
if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
    tf2.requestFocus(); 
}         
    }//GEN-LAST:event_tf1KeyPressed

    public static void main(String args[]) {
     /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NotificationTabel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NotificationTabel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NotificationTabel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NotificationTabel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ParabitLogin dialog = new ParabitLogin(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b1;
    private javax.swing.JButton b2;
    private javax.swing.JButton b3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField tf1;
    private javax.swing.JTextField tf2;
    private javax.swing.JTextField tf3;
    // End of variables declaration//GEN-END:variables
}
