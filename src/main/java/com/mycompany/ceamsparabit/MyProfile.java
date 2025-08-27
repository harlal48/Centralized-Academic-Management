package com.mycompany.ceamsparabit;

import java.awt.Dimension;
import java.awt.Toolkit;
//for fatch image from dataabase
//Connection con = null;
//    PreparedStatement ps = null;
//    ResultSet rs = null;
//
//    try {
//        // Initialize the database connection (assumes a method 'DB' returns a Connection)
//        con = DB();
//
//        // Prepare the SQL query to retrieve the image data
//        ps = con.prepareStatement("SELECT image FROM Image WHERE id = ?");
//        ps.setString(1, tl.getText());  // Use prepared statement parameter to prevent SQL injection
//
//        // Execute the query
//        rs = ps.executeQuery();
//
//        if (rs.next()) {
//            Blob imgBlob = rs.getBlob("image");  // Get the image as a Blob
//            byte[] imgData = imgBlob.getBytes(1, (int) imgBlob.length());
//
//            // Convert byte array to BufferedImage
//            BufferedImage imag = ImageIO.read(new ByteArrayInputStream(imgData));
//
//            // Set image icon to JLabel (assuming jLabel2 is your target label)
//            ImageIcon jkl = new ImageIcon(imag);
//            jLabel2.setIcon(jkl);
//        }
//
//    } catch (SQLException e) {
//        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, e);
//    } catch (IOException ex) {
//        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
//    } finally {
//        // Ensure resources are closed
//        try {
//            if (rs != null) rs.close();
//            if (ps != null) ps.close();
//            if (con != null) con.close();
//        } catch (SQLException e) {
//            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, e);
//        }
//    }


public class MyProfile extends javax.swing.JFrame {
String employeename,employeeworkpost,wardname,address;
            int employeeid,statecode,districtcode,subdistrictcode,zonecode,wardcode;
            long aadharno;     
          
    public MyProfile() {
        initComponents();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setVisible(true);

//       setExtendedState(MAXIMIZED_BOTH);
        ParabitCeamsDb obdb=new ParabitCeamsDb();
        String s="select EmpName,WorkPost,WardName,OffAddress,AadharNo,DistrictCode,StateCode,SubDistrictCode,NNEID,ZoneCode,WardCode from nnemprecords "; 
       System.out.println(s);
       try{
       employeename=ParabitLogin.srs.getString(9);
       employeeworkpost=ParabitLogin.srs.getString(13);
      wardname=ParabitLogin.srs.getString(8);
      address=ParabitLogin.srs.getString(16);
      employeeid=ParabitLogin.srs.getInt(1);
      statecode=ParabitLogin.srs.getInt(2);
      districtcode=ParabitLogin.srs.getInt(3);
      subdistrictcode=ParabitLogin.srs.getInt(4);
      zonecode=ParabitLogin.srs.getInt(5);
      wardcode=ParabitLogin.srs.getInt(6);
      aadharno=ParabitLogin.srs.getLong(20);
      
      empname.setText(empname.getText() + " " +employeename);
      empwpost.setText(empwpost.getText() + " " +employeeworkpost);
      wname.setText(wname.getText() + " " +wardname);
      add.setText(add.getText() + " " +address);
      empid.setText(empid.getText() + " " +employeeid);
      scode.setText(scode.getText() + " " +statecode);
      distcode.setText(distcode.getText() + " " +districtcode);
      subdcode.setText(subdcode.getText() + " " +subdistrictcode);
      zcode.setText(zcode.getText() + " " +zonecode);
      wcode.setText(wcode.getText() + " " +wardcode);
      adhano.setText(adhano.getText() + " " +aadharno);

       }catch(Exception e){
           System.out.println(e);
       }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        empname = new javax.swing.JLabel();
        empwpost = new javax.swing.JLabel();
        scode = new javax.swing.JLabel();
        subdcode = new javax.swing.JLabel();
        empid = new javax.swing.JLabel();
        adhano = new javax.swing.JLabel();
        distcode = new javax.swing.JLabel();
        wname = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        wcode = new javax.swing.JLabel();
        devid = new javax.swing.JLabel();
        deviallotedby = new javax.swing.JLabel();
        zcode = new javax.swing.JLabel();
        add = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        empname.setText("Employee Name :");

        empwpost.setText("Employee Workpost :");

        scode.setText("State Code :");

        subdcode.setText(" SubDistCode :");

        empid.setText("Employee ID :");

        adhano.setText("Aadhar Number :");

        distcode.setText("District Code :");

        wname.setText("Ward Name :");

        wcode.setText("Ward Code :");

        devid.setText("Device ID :");

        deviallotedby.setText("Device Alloted By :");

        zcode.setText("Zone Code :");

        add.setText("Address :");

        jTextField1.setText(" ");

        jTextField2.setText(" ");

        jTextField3.setText("  ");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setText("MY    PROFILE");
        jLabel15.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel16.setText("                           Employee Image");
        jLabel16.setToolTipText("");
        jLabel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(subdcode, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(scode, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(empwpost, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(empname, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(wname, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(190, 190, 190)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(empid, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(adhano, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(distcode, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(zcode, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(wcode, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(devid, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(deviallotedby))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(106, 106, 106)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(383, 383, 383)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(86, 424, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(empid)
                                    .addComponent(empname))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(empwpost)
                                    .addComponent(adhano))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(scode)
                                    .addComponent(distcode))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(subdcode)
                                    .addComponent(zcode))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(wname)
                                    .addComponent(wcode))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(add)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(devid)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(316, 316, 316)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(deviallotedby)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(172, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
  
    }//GEN-LAST:event_jTextField3ActionPerformed

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyProfile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel add;
    private javax.swing.JLabel adhano;
    private javax.swing.JLabel deviallotedby;
    private javax.swing.JLabel devid;
    private javax.swing.JLabel distcode;
    private javax.swing.JLabel empid;
    private javax.swing.JLabel empname;
    private javax.swing.JLabel empwpost;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel scode;
    private javax.swing.JLabel subdcode;
    private javax.swing.JLabel wcode;
    private javax.swing.JLabel wname;
    private javax.swing.JLabel zcode;
    // End of variables declaration//GEN-END:variables
}
