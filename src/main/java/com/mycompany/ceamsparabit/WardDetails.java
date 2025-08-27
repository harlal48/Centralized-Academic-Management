package com.mycompany.ceamsparabit;

import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class WardDetails extends javax.swing.JFrame {

    ParabitCeamsDb obdb;
    public WardDetails() {
        initComponents();
        obdb = new ParabitCeamsDb();
        
        buttonGroup1.add(jRadioButton1);
        buttonGroup1.add(jRadioButton2);
        buttonGroup1.add(jRadioButton3);
        buttonGroup1.add(jRadioButton4);
         alldatafunc(jTable1);
    }

   public void alldatafunc(JTable table) {
                try {
String s = "SELECT NNEID,WardCode,WardNumber,WardName"
                    + ",EmpName,WorkPost,OffMobNo,PerMobNo,Status"
                    + ",DeviceNumber,DeviceStatus FROM nnemprecords "
                    + "WHERE ZoneCode = " + ParabitLogin.srs.getInt(5)
                    + " ORDER BY 'nnemprecords,NNEID' ASC";
            obdb.rs = obdb.stm.executeQuery(s);
            System.out.println(s);
            DefaultTableModel tb1model = (DefaultTableModel) table.getModel();
            tb1model.setRowCount(0);
            tb1model.setColumnCount(0);

            tb1model.addColumn("NagarNigam Employee ID");
            tb1model.addColumn("Ward Code");
            tb1model.addColumn("Ward Number");
            tb1model.addColumn("Ward Name");
            tb1model.addColumn("Employee Name");
            tb1model.addColumn("Work Post");
            tb1model.addColumn("Office Mobile No");
            tb1model.addColumn("Personal Mobile Number");
            tb1model.addColumn("Status");
            tb1model.addColumn("Device Number");
            tb1model.addColumn("DeviceStatus");
//            tb1model.addColumn("Pollution Index");
//            tb1model.addColumn("Status");

            // Get column count based on manually added columns
            int columnCount = tb1model.getColumnCount();

            // Add rows to the table model
            while (obdb.rs.next()) {
                String[] tbdata = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    tbdata[i - 1] = obdb.rs.getString(i);
                }
                tb1model.addRow(tbdata);
            }
        } catch (SQLException ex) {
            System.out.print(ex);
        }
    }
   public void LandInspector(JTable table) {
                try {
String s = "SELECT NNEID,WardCode,WardNumber,WardName"
                    + ",EmpName,WorkPost,OffMobNo,PerMobNo,Status"
                    + ",DeviceNumber,DeviceStatus FROM nnemprecords "
                    + "WHERE WorkPost='Land Inspector' and ZoneCode = " + ParabitLogin.srs.getInt(5)
                    + " ORDER BY 'nnemprecords,NNEID' ASC";
            obdb.rs = obdb.stm.executeQuery(s);
            System.out.println(s);
            DefaultTableModel tb1model = (DefaultTableModel) table.getModel();
            tb1model.setRowCount(0);
            tb1model.setColumnCount(0);

            tb1model.addColumn("NagarNigam Employee ID");
            tb1model.addColumn("Ward Code");
            tb1model.addColumn("Ward Number");
            tb1model.addColumn("Ward Name");
            tb1model.addColumn("Employee Name");
            tb1model.addColumn("Work Post");
            tb1model.addColumn("Office Mobile No");
            tb1model.addColumn("Personal Mobile Number");
            tb1model.addColumn("Status");
            tb1model.addColumn("Device Number");
            tb1model.addColumn("DeviceStatus");
//            tb1model.addColumn("Pollution Index");
//            tb1model.addColumn("Status");

            // Get column count based on manually added columns
            int columnCount = tb1model.getColumnCount();

            // Add rows to the table model
            while (obdb.rs.next()) {
                String[] tbdata = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    tbdata[i - 1] = obdb.rs.getString(i);
                }
                tb1model.addRow(tbdata);
            }
        } catch (SQLException ex) {
            System.out.print(ex);
        }
    }
   
   public void WardMunicipleOfficer(JTable table) {
                try {
String s = "SELECT NNEID,WardCode,WardNumber,WardName"
                    + ",EmpName,WorkPost,OffMobNo,PerMobNo,Status"
                    + ",DeviceNumber,DeviceStatus FROM nnemprecords "
                    + "WHERE WorkPost='Ward Municipal Officer' and ZoneCode = " + ParabitLogin.srs.getInt(5)
                    + " ORDER BY 'nnemprecords,NNEID' ASC";
            obdb.rs = obdb.stm.executeQuery(s);
            System.out.println(s);
            // Clear existing table columns and rows
            DefaultTableModel tb1model = (DefaultTableModel) table.getModel();
            tb1model.setRowCount(0);
            tb1model.setColumnCount(0);

            tb1model.addColumn("NagarNigam Employee ID");
            tb1model.addColumn("Ward Code");
            tb1model.addColumn("Ward Number");
            tb1model.addColumn("Ward Name");
            tb1model.addColumn("Employee Name");
            tb1model.addColumn("Work Post");
            tb1model.addColumn("Office Mobile No");
            tb1model.addColumn("Personal Mobile Number");
            tb1model.addColumn("Status");
            tb1model.addColumn("Device Number");
            tb1model.addColumn("DeviceStatus");
//            tb1model.addColumn("Pollution Index");
//            tb1model.addColumn("Status");

            int columnCount = tb1model.getColumnCount();
            // Add rows to the table model
            while (obdb.rs.next()) {
                String[] tbdata = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    tbdata[i - 1] = obdb.rs.getString(i);
                }
                tb1model.addRow(tbdata);
            }
        } catch (SQLException ex) {
            System.out.print(ex);
        }
    }
   
   public void AssistantProjectOfficer(JTable table) {
                try {
String s = "SELECT NNEID,WardCode,WardNumber,WardName"
                    + ",EmpName,WorkPost,OffMobNo,PerMobNo,Status"
                    + ",DeviceNumber,DeviceStatus FROM nnemprecords "
                    + "WHERE WorkPost='Assistant project Officer' and ZoneCode = " + ParabitLogin.srs.getInt(5)
                    + " ORDER BY 'nnemprecords,NNEID' ASC";
            obdb.rs = obdb.stm.executeQuery(s);
            System.out.println(s);

            DefaultTableModel tb1model = (DefaultTableModel) table.getModel();
            tb1model.setRowCount(0);
            tb1model.setColumnCount(0);

            // Manually add column names
            tb1model.addColumn("NagarNigam Employee ID");
            tb1model.addColumn("Ward Code");
            tb1model.addColumn("Ward Number");
            tb1model.addColumn("Ward Name");
            tb1model.addColumn("Employee Name");
            tb1model.addColumn("Work Post");
            tb1model.addColumn("Office Mobile No");
            tb1model.addColumn("Personal Mobile Number");
            tb1model.addColumn("Status");
            tb1model.addColumn("Device Number");
            tb1model.addColumn("DeviceStatus");
//            tb1model.addColumn("Pollution Index");
//            tb1model.addColumn("Status");

            // Get column count based on manually added columns
            int columnCount = tb1model.getColumnCount();
            // Add rows to the table model
            while (obdb.rs.next()) {
                String[] tbdata = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    tbdata[i - 1] = obdb.rs.getString(i);
                }
                tb1model.addRow(tbdata);
            }
        } catch (SQLException ex) {
            System.out.print(ex);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jRadioButton1.setText("All");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("Land Inspector");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jRadioButton3.setText("Ward Municiple Officer");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        jRadioButton4.setText("Assistant Project Officer");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jRadioButton2)
                .addGap(53, 53, 53)
                .addComponent(jRadioButton3)
                .addGap(65, 65, 65)
                .addComponent(jRadioButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addGap(0, 81, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
          alldatafunc(jTable1);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
       LandInspector(jTable1);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        WardMunicipleOfficer(jTable1);
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
         AssistantProjectOfficer(jTable1);
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    public static void main(String args[]) {
    
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WardDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
