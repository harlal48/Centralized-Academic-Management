package com.mycompany.ceamsparabit;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class DeviceDetails extends javax.swing.JDialog {

    ParabitCeamsDb obdb;

    public DeviceDetails(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        obdb = new ParabitCeamsDb();
//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Dimension screenSize = toolkit.getScreenSize();
//
//        this.setSize(screenSize.width, screenSize.height);
setSize(Toolkit.getDefaultToolkit().getScreenSize());

        this.setVisible(true);
        alldatafunc();
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1385, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
private void alldatafunc() {
        try {
            String s = "SELECT NNEID,DeviceType , StateCode , DeviceStatus , AllotedBy , AllotedDate , ReturnDate FROM nnemprecords where StateCode = " + ParabitLogin.srs.getInt(2);
            obdb.rs = obdb.stm.executeQuery(s);
            System.out.println(s);
            // Clear existing table columns and rows
            DefaultTableModel tb1model = (DefaultTableModel) jTable1.getModel();
            tb1model.setRowCount(0);
            tb1model.setColumnCount(0);

            tb1model.addColumn("NagarNigam Employee ID");
            tb1model.addColumn("Device type");
            tb1model.addColumn("State code");
            tb1model.addColumn("Device Status");
            tb1model.addColumn("Alloted By");
            tb1model.addColumn("AllotedDate");
            tb1model.addColumn("ReturnDate");
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

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DeviceDetails dialog = new DeviceDetails(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
