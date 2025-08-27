package ceamsexperts;

import com.mycompany.ceamsparabit.NewlandRegistration;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sohan
 */
public class ExpertNotification extends javax.swing.JFrame implements Runnable {

    ExpertsCeamsDb obdb;
    Object lrID;
    Thread th;

    public ExpertNotification() {
        initComponents();
        obdb = new ExpertsCeamsDb();
        th = new Thread(this);
        th.start();

    }

    public void run() {

        while (true) {
            try {
                landTable(tb1, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status = 'Activated Land'");
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb1 = new javax.swing.JTable();

        jMenuItem1.setText("All Details");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tb1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tb1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        this.setVisible(false);
        int selectedRow = tb1.getSelectedRow(); // Selected row index
        if (selectedRow != -1) { // Check if a row is selected
            lrID = tb1.getValueAt(selectedRow, 0); // Get LRID from the first column
            System.out.println(lrID);
        }
        NewlandRegistration nl = new NewlandRegistration(null, false, lrID, null);
        nl.checkboxfun();
        nl.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void landTable(JTable table, String s) {
        try {

            obdb.rs = obdb.stm.executeQuery(s);
            System.out.println(s);
            // Clear existing table columns and rows
            DefaultTableModel tb1model = (DefaultTableModel) table.getModel();
            tb1model.setRowCount(0);
            tb1model.setColumnCount(0);
            // Manually add column names
            tb1model.addColumn("Land Registration ID");
            tb1model.addColumn("State Code");
            tb1model.addColumn("District Code");
            tb1model.addColumn("VillageCode");
            tb1model.addColumn("City");
            tb1model.addColumn("Zone Code");
            tb1model.addColumn("Ward Code");
            tb1model.addColumn("Ward Name");
            tb1model.addColumn("Area Sqft");
            tb1model.addColumn("Land Mark Date");
            tb1model.addColumn("Land Mark Time");
            tb1model.addColumn("Pollution Index");
            tb1model.addColumn("Status");

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

        tb1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tb1.rowAtPoint(e.getPoint()); // Get row at mouse click point
                    int column = tb1.columnAtPoint(e.getPoint()); // Get column at mouse click point

                    if (row != -1 && column != -1) { // Check if the click is within a valid cell
                        tb1.setRowSelectionInterval(row, row); // Select the row at click point
                        tb1.setColumnSelectionInterval(column, column); // Optional: select column
                        jPopupMenu1.show(tb1, e.getX(), e.getY()); // Show popup menu at clicked location
                    }
                }
            }
        });

    }

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
            java.util.logging.Logger.getLogger(ExpertNotification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExpertNotification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExpertNotification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExpertNotification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExpertNotification().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb1;
    // End of variables declaration//GEN-END:variables

}
