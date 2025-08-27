package com.mycompany.ceamsparabit;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LandDetails2 extends javax.swing.JDialog {

    ParabitCeamsDb obdb;
    ParabitCeamsDb obdb1;
    DefaultTableModel tbmodel1;
    Object lrID;
    String LisLand, PenLand, ActLand, RejLand, PComLand, LockLand, RequestLand, wateravailable, Centercoordinate, Latitutelongitute;
    int ShapeTypes, Areatypes, Pollutiontypes, Soiltypes;
    float radius;
    public JButton bt;
    String wp;
Object lrid;
    public LandDetails2(java.awt.Frame parent, boolean modal, JButton bt) {
        super(parent, modal);
        initComponents();
        this.bt = bt;

        try {
            wp = ParabitLogin.srs.getString(13);
            if (wp.equals("Land Inspector") && bt.getText().equals("Land Approval")) {
                // al.setVisible(false);
                jTabbedPane2.setSelectedIndex(3);

            }
            if (wp.equals("Land Inspector") && bt.getText().equals("Land Details")) {
                // al.setVisible(false);
                jTabbedPane2.setSelectedIndex(0);

            }
            if (wp.equals("Zone Municipal Officer") && bt.getText().equals("Land Details")) {
                // al.setVisible(false);
                jTabbedPane2.setSelectedIndex(1);

            }
            if (wp.equals("Municipal Commissioner ") && bt.getText().equals("  Land Details")) {
                // al.setVisible(false);
                jTabbedPane2.setSelectedIndex(2);

            }

        } catch (Exception e) {

        }

        obdb = new ParabitCeamsDb();
        obdb1 = new ParabitCeamsDb();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setVisible(true);
        Datamember();
        wardName();
    }

    private void landTable(JTable table, String s, JLabel pic) {
        try {
            //String s = "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status = 'Listed Land'";
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

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint()); // Get row at mouse click point
                    int column = table.columnAtPoint(e.getPoint()); // Get column at mouse click point

                    if (row != -1 && column != -1) { // Check if the click is within a valid cell
                        table.setRowSelectionInterval(row, row); // Select the row at click point
                        table.setColumnSelectionInterval(column, column); // Optional: select column
                        myPopup.show(table, e.getX(), e.getY()); // Show popup menu at clicked location
                    }
                }
                if (e.getClickCount() == 1) { // Check for single-click
                    int selectedRow = table.getSelectedRow();
                    int selcetedcolumn = table.getSelectedColumn();
                    if (selectedRow != -1 && selcetedcolumn != -1) {
                        String cellValue = table.getValueAt(selectedRow, selcetedcolumn).toString();
                        String lrid = table.getValueAt(selectedRow, 0).toString();
                        System.out.println(lrid);
                        //this is for getting image from database and set into jlabel
                        // here we get image from database one by one because all images get fromo database at a same time is make very slowour program execution 
                        try {
                            String imageQuery = "SELECT Img1 FROM landprofile WHERE LRID = ?";
                            System.out.println(imageQuery);
                            PreparedStatement pstmt = obdb.con.prepareStatement(imageQuery);
                            pstmt.setString(1, lrid);
                            obdb.rs = pstmt.executeQuery();
                            if (obdb.rs.next()) {
                                Blob blob = obdb.rs.getBlob("Img1");
                                byte[] imageData = blob.getBytes(1, (int) blob.length());

                                ImageIcon imageIcon = new ImageIcon(imageData);
                                Image scaledImage = imageIcon.getImage().getScaledInstance(pic.getWidth(), pic.getHeight(), Image.SCALE_SMOOTH);
                                pic.setIcon(new ImageIcon(scaledImage));
                            }
                        } catch (SQLException ee) {
                            ee.printStackTrace();
                        }
                    }
                }
            }
        });

    }

    public void Datamember() {
        landTable(jTable1, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status = 'Listed Land'", jLabel7);
        landTable(jTable2, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status = 'Pending Land'", pendingland2);
        landTable(jTable3, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status = 'Activated Land'", jLabel60);
        landTable(jTable4, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status = 'Rejected Land'", jLabel77);
        landTable(jTable5, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status = 'Project Completed Land'", jLabel2);
        landTable(jTable6, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status = 'Locked Land'", jLabel110);
        landTable(jTable7, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status = 'Requested Land'", jLabel26);
        listedLand();
        pendingLand();
        activatedLand();
        rejectedLand();
        projectCompletedLand();
        lockedLand();
        requestLand();
    }
    private void wardName() {
        try {
            String a = "select WardName from zoneward where Zone = " + ParabitLogin.srs.getInt("ZoneCode") + " and StateCode = " + ParabitLogin.srs.getInt("StateCode") + " and DistrictCode = " + ParabitLogin.srs.getInt("DistrictCode") + " ORDER BY zoneward.WardName ASC";
            System.out.println(a);
            obdb1.rs = obdb1.stm.executeQuery(a);
            while (obdb1.rs.next()) {
                jComboBox1.addItem(obdb1.rs.getString(1));
                jComboBox2.addItem(obdb1.rs.getString(1));
                jComboBox3.addItem(obdb1.rs.getString(1));
                jComboBox4.addItem(obdb1.rs.getString(1));
                jComboBox5.addItem(obdb1.rs.getString(1));
                jComboBox6.addItem(obdb1.rs.getString(1));
                jComboBox7.addItem(obdb1.rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void comboBoxWardName(JTable table, String sql) {
        try {
            tbmodel1 = (DefaultTableModel) table.getModel();
            // Clear the table
            if (tbmodel1 != null) {
                tbmodel1.setRowCount(0);
            }

            System.out.println(sql);

            obdb1.rs = obdb1.stm.executeQuery(sql);
            // Populate the table
            while (obdb1.rs.next()) {
                String tbData[] = {
                    obdb1.rs.getString(1), obdb1.rs.getString(5), obdb1.rs.getString(6),
                    obdb1.rs.getString(7), obdb1.rs.getString(8), obdb1.rs.getString(9),
                    obdb1.rs.getString(15), obdb1.rs.getString(16), obdb1.rs.getString(18),
                    obdb1.rs.getString(20), obdb1.rs.getString(21), obdb1.rs.getString(22), obdb1.rs.getString(23)
                };
                tbmodel1.addRow(tbData);
            }
        } catch (SQLException e) {
            System.out.println("Some Problem ------>>>>>> " + e);
        } catch (NullPointerException e) {
            System.out.println("Table Model is null ------>>>>>> " + e);
        }
    }

    public void alldatafunc(JTable table, String s) {
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

    private void textFieldTable1(JTable tablename, int rowIndex) {
        try {
            String lrid = tablename.getValueAt(rowIndex, 0).toString();
            String s = "SELECT p.TypeOfPollution, ay.markareatype, shy.shapeName, sy.soiltype, "
                    + "lp.Radius, wa.resource, lp.CenterCoOrdinate, lp.LL1, lp.Status "
                    + "FROM landprofile lp, zoneward zw, shapetype shy, areatype ay, "
                    + "typesofsoil sy, waterresources wa, pollution p "
                    + "WHERE lp.Shape = shy.SNo AND lp.AreaType = ay.SNo AND "
                    + "lp.SoilType = sy.SNo AND lp.WAvailable = wa.SNo AND lp.PollutionType = p.SNo AND "
                    + "lp.statecode = zw.StateCode AND lp.districtcode = zw.DistrictCode AND "
                    + "lp.ZoneCode = zw.Zone AND lp.LRID = " + lrid + " GROUP BY LRID";
            System.out.println(s);
            obdb1.rs = obdb1.stm.executeQuery(s);
            while (obdb1.rs.next()) {
                String ss = obdb1.rs.getString(9);
                System.out.println("Status" + ss);

                switch (ss) {
                    case "Listed Land":
                        jTextField1.setText(obdb1.rs.getString(1));  //polluti type
                        jTextField6.setText(obdb1.rs.getString(2));
                        jTextField2.setText(obdb1.rs.getString(3));
                        jTextField5.setText(obdb1.rs.getString(4));
                        jTextField4.setText(obdb1.rs.getString(5));
                        jTextField3.setText(obdb1.rs.getString(6));
                        jTextField8.setText(obdb1.rs.getString(7));
                        jTextField7.setText(obdb1.rs.getString(8));
                        break;

                    case "Pending Land":
                        jTextField19.setText(obdb1.rs.getString(1));
                        jTextField24.setText(obdb1.rs.getString(2));
                        jTextField20.setText(obdb1.rs.getString(3));
                        jTextField23.setText(obdb1.rs.getString(4));
                        jTextField22.setText(obdb1.rs.getString(5));
                        jTextField21.setText(obdb1.rs.getString(6));
                        jTextField18.setText(obdb1.rs.getString(7));
                        jTextField17.setText(obdb1.rs.getString(8));
                        break;
                    case "Activated Land":
                        jTextField25.setText(obdb1.rs.getString(1));
                        jTextField30.setText(obdb1.rs.getString(2));
                        jTextField26.setText(obdb1.rs.getString(3));
                        jTextField29.setText(obdb1.rs.getString(4));
                        jTextField28.setText(obdb1.rs.getString(5));
                        jTextField27.setText(obdb1.rs.getString(6));
                        jTextField32.setText(obdb1.rs.getString(7));
                        jTextField31.setText(obdb1.rs.getString(8));
                        break;
                    case "Rejected Land":
                        jTextField33.setText(obdb1.rs.getString(1));
                        jTextField38.setText(obdb1.rs.getString(2));
                        jTextField34.setText(obdb1.rs.getString(3));
                        jTextField37.setText(obdb1.rs.getString(4));
                        jTextField36.setText(obdb1.rs.getString(5));
                        jTextField35.setText(obdb1.rs.getString(6));
                        jTextField40.setText(obdb1.rs.getString(7));
                        jTextField39.setText(obdb1.rs.getString(8));
                        break;
                    case "Project Completed Land":
                        jTextField41.setText(obdb1.rs.getString(1));
                        jTextField46.setText(obdb1.rs.getString(2));
                        jTextField42.setText(obdb1.rs.getString(3));
                        jTextField45.setText(obdb1.rs.getString(4));
                        jTextField44.setText(obdb1.rs.getString(5));
                        jTextField43.setText(obdb1.rs.getString(6));
                        jTextField48.setText(obdb1.rs.getString(7));
                        jTextField47.setText(obdb1.rs.getString(8));
                        break;
                    case "Locked Land":
                        jTextField49.setText(obdb1.rs.getString(1));
                        jTextField54.setText(obdb1.rs.getString(2));
                        jTextField50.setText(obdb1.rs.getString(3));
                        jTextField53.setText(obdb1.rs.getString(4));
                        jTextField52.setText(obdb1.rs.getString(5));
                        jTextField51.setText(obdb1.rs.getString(6));
                        jTextField56.setText(obdb1.rs.getString(7));
                        jTextField55.setText(obdb1.rs.getString(8));
                        break;

                    case "Requested Land":
                        jTextField9.setText(obdb1.rs.getString(1));
                        jTextField14.setText(obdb1.rs.getString(2));
                        jTextField10.setText(obdb1.rs.getString(3));
                        jTextField13.setText(obdb1.rs.getString(4));
                        jTextField12.setText(obdb1.rs.getString(5));
                        jTextField11.setText(obdb1.rs.getString(6));
                        jTextField16.setText(obdb1.rs.getString(7));
                        jTextField15.setText(obdb1.rs.getString(8));
                        break;
                    default:
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void listedLand() {
        try {
            String s = "select count(Status) from landprofile where Status = 'Listed Land'";
            System.out.println(s);
            obdb1.rs = obdb1.stm.executeQuery(s);
            while (obdb1.rs.next()) {
                LisLand = obdb1.rs.getString(1);
                Listedland1.setText(Listedland1.getText() + " " + LisLand);
                Listedland2.setText(Listedland2.getText() + " " + LisLand);
                Listedland3.setText(Listedland3.getText() + " " + LisLand);
                Listedland4.setText(Listedland4.getText() + " " + LisLand);
                Listedland5.setText(Listedland5.getText() + " " + LisLand);
                Listedland6.setText(Listedland6.getText() + " " + LisLand);
                Listedland7.setText(Listedland7.getText() + " " + LisLand);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void pendingLand() {
        try {
            String s = "select count(Status) from landprofile where Status = 'Pending Land'";
            System.out.println(s);
            obdb1.rs = obdb1.stm.executeQuery(s);
            while (obdb1.rs.next()) {
                PenLand = obdb1.rs.getString(1);
                Pendingland1.setText(Pendingland1.getText() + " " + PenLand);
                Pendingland2.setText(Pendingland2.getText() + " " + PenLand);
                Pendingland3.setText(Pendingland3.getText() + " " + PenLand);
                Pendingland4.setText(Pendingland4.getText() + " " + PenLand);
                Pendingland5.setText(Pendingland5.getText() + " " + PenLand);
                Pendingland6.setText(Pendingland6.getText() + " " + PenLand);
                Pendingland7.setText(Pendingland7.getText() + " " + PenLand);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void activatedLand() {
        try {
            String s = "select count(Status) from landprofile where Status = 'Activated Land'";
            System.out.println(s);
            obdb1.rs = obdb1.stm.executeQuery(s);
            while (obdb1.rs.next()) {
                ActLand = obdb1.rs.getString(1);
                Activatedland1.setText(Activatedland1.getText() + " " + ActLand);
                Activatedland2.setText(Activatedland2.getText() + " " + ActLand);
                Activatedland3.setText(Activatedland3.getText() + " " + ActLand);
                Activatedland4.setText(Activatedland4.getText() + " " + ActLand);
                Activatedland5.setText(Activatedland5.getText() + " " + ActLand);
                Activatedland6.setText(Activatedland6.getText() + " " + ActLand);
                Activatedland7.setText(Activatedland7.getText() + " " + ActLand);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void rejectedLand() {
        try {
            String s = "select count(Status) from landprofile where Status = 'Rejected Land'";
            System.out.println(s);
            obdb1.rs = obdb1.stm.executeQuery(s);
            while (obdb1.rs.next()) {
                RejLand = obdb1.rs.getString(1);
                Rejectedland1.setText(Rejectedland1.getText() + " " + RejLand);
                Rejectedland2.setText(Rejectedland2.getText() + " " + RejLand);
                Rejectedland3.setText(Rejectedland3.getText() + " " + RejLand);
                Rejectedland4.setText(Rejectedland4.getText() + " " + RejLand);
                Rejectedland5.setText(Rejectedland5.getText() + " " + RejLand);
                Rejectedland6.setText(Rejectedland6.getText() + " " + RejLand);
                Rejectedland7.setText(Rejectedland7.getText() + " " + RejLand);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void projectCompletedLand() {
        try {
            String s = "select count(Status) from landprofile where Status = 'Project Completed Land'";
            System.out.println(s);
            obdb1.rs = obdb1.stm.executeQuery(s);
            while (obdb1.rs.next()) {
                PComLand = obdb1.rs.getString(1);
                ProjectCompletedland1.setText(ProjectCompletedland1.getText() + " " + PComLand);
                ProjectCompletedland2.setText(ProjectCompletedland2.getText() + " " + PComLand);
                ProjectCompletedland3.setText(ProjectCompletedland3.getText() + " " + PComLand);
                ProjectCompletedland4.setText(ProjectCompletedland4.getText() + " " + PComLand);
                ProjectCompletedland5.setText(ProjectCompletedland5.getText() + " " + PComLand);
                ProjectCompletedland6.setText(ProjectCompletedland6.getText() + " " + PComLand);
                ProjectCompletedland7.setText(ProjectCompletedland7.getText() + " " + PComLand);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private void lockedLand() {
        try {
            String s = "select count(Status) from landprofile where Status = 'Locked Land'";
            System.out.println(s);
            obdb1.rs = obdb1.stm.executeQuery(s);
            while (obdb1.rs.next()) {
                LockLand = obdb1.rs.getString(1);
                Lockedland1.setText(Lockedland1.getText() + " " + LockLand);
                Lockedland2.setText(Lockedland2.getText() + " " + LockLand);
                Lockedland3.setText(Lockedland3.getText() + " " + LockLand);
                Lockedland4.setText(Lockedland4.getText() + " " + LockLand);
                Lockedland5.setText(Lockedland5.getText() + " " + LockLand);
                Lockedland6.setText(Lockedland6.getText() + " " + LockLand);
                Lockedland7.setText(Lockedland7.getText() + " " + LockLand);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private void requestLand() {
        try {
            String s = "select count(Status) from landprofile where Status = 'Requested Land'";
            System.out.println(s);
            obdb1.rs = obdb1.stm.executeQuery(s);
            while (obdb1.rs.next()) {
                RequestLand = obdb1.rs.getString(1);
                Requestedland1.setText(Requestedland1.getText() + " " + RequestLand);
                Requestedland2.setText(Requestedland2.getText() + " " + RequestLand);
                Requestedland3.setText(Requestedland3.getText() + " " + RequestLand);
                Requestedland4.setText(Requestedland4.getText() + " " + RequestLand);
                Requestedland5.setText(Requestedland5.getText() + " " + RequestLand);
                Requestedland6.setText(Requestedland6.getText() + " " + RequestLand);
                Requestedland7.setText(Requestedland7.getText() + " " + RequestLand);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        myPopup = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        Listedland1 = new javax.swing.JLabel();
        Pendingland1 = new javax.swing.JLabel();
        Activatedland1 = new javax.swing.JLabel();
        Rejectedland1 = new javax.swing.JLabel();
        ProjectCompletedland1 = new javax.swing.JLabel();
        Lockedland1 = new javax.swing.JLabel();
        Requestedland1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jl7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        Listedland2 = new javax.swing.JLabel();
        Pendingland2 = new javax.swing.JLabel();
        Activatedland2 = new javax.swing.JLabel();
        Rejectedland2 = new javax.swing.JLabel();
        ProjectCompletedland2 = new javax.swing.JLabel();
        Lockedland2 = new javax.swing.JLabel();
        Requestedland2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        Listedland3 = new javax.swing.JLabel();
        Pendingland3 = new javax.swing.JLabel();
        Activatedland3 = new javax.swing.JLabel();
        Rejectedland3 = new javax.swing.JLabel();
        ProjectCompletedland3 = new javax.swing.JLabel();
        Lockedland3 = new javax.swing.JLabel();
        Requestedland3 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        pendingland2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jTextField24 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        Listedland4 = new javax.swing.JLabel();
        Pendingland4 = new javax.swing.JLabel();
        Activatedland4 = new javax.swing.JLabel();
        Rejectedland4 = new javax.swing.JLabel();
        ProjectCompletedland4 = new javax.swing.JLabel();
        Lockedland4 = new javax.swing.JLabel();
        Requestedland4 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jTextField26 = new javax.swing.JTextField();
        jTextField27 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jTextField30 = new javax.swing.JTextField();
        jTextField31 = new javax.swing.JTextField();
        jTextField32 = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        Listedland5 = new javax.swing.JLabel();
        Pendingland5 = new javax.swing.JLabel();
        Activatedland5 = new javax.swing.JLabel();
        Rejectedland5 = new javax.swing.JLabel();
        ProjectCompletedland5 = new javax.swing.JLabel();
        Lockedland5 = new javax.swing.JLabel();
        Requestedland5 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jTextField33 = new javax.swing.JTextField();
        jTextField34 = new javax.swing.JTextField();
        jTextField35 = new javax.swing.JTextField();
        jTextField36 = new javax.swing.JTextField();
        jTextField37 = new javax.swing.JTextField();
        jTextField38 = new javax.swing.JTextField();
        jTextField39 = new javax.swing.JTextField();
        jTextField40 = new javax.swing.JTextField();
        jComboBox4 = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        Listedland6 = new javax.swing.JLabel();
        Pendingland6 = new javax.swing.JLabel();
        Activatedland6 = new javax.swing.JLabel();
        Rejectedland6 = new javax.swing.JLabel();
        ProjectCompletedland6 = new javax.swing.JLabel();
        Lockedland6 = new javax.swing.JLabel();
        Requestedland6 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jTextField41 = new javax.swing.JTextField();
        jTextField42 = new javax.swing.JTextField();
        jTextField43 = new javax.swing.JTextField();
        jTextField44 = new javax.swing.JTextField();
        jTextField45 = new javax.swing.JTextField();
        jTextField46 = new javax.swing.JTextField();
        jTextField47 = new javax.swing.JTextField();
        jTextField48 = new javax.swing.JTextField();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        Listedland7 = new javax.swing.JLabel();
        Pendingland7 = new javax.swing.JLabel();
        Activatedland7 = new javax.swing.JLabel();
        Rejectedland7 = new javax.swing.JLabel();
        ProjectCompletedland7 = new javax.swing.JLabel();
        Lockedland7 = new javax.swing.JLabel();
        Requestedland7 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jTextField49 = new javax.swing.JTextField();
        jTextField50 = new javax.swing.JTextField();
        jTextField51 = new javax.swing.JTextField();
        jTextField52 = new javax.swing.JTextField();
        jTextField53 = new javax.swing.JTextField();
        jTextField54 = new javax.swing.JTextField();
        jTextField55 = new javax.swing.JTextField();
        jTextField56 = new javax.swing.JTextField();

        jMenuItem1.setText("Send to Approval\n");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        myPopup.add(jMenuItem1);

        jMenuItem2.setText("Delete");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        myPopup.add(jMenuItem2);

        jMenuItem3.setText("Complete Details");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        myPopup.add(jMenuItem3);

        jMenuItem4.setText("Remark");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        myPopup.add(jMenuItem4);

        jMenuItem5.setText("Update Details\n");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        myPopup.add(jMenuItem5);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Listedland1.setText(" Listed Land:");

        Pendingland1.setText("Pending Land:");

        Activatedland1.setText("Activated Land:");

        Rejectedland1.setText("Rejected Land:");

        ProjectCompletedland1.setText("Projected Completed Land:");

        Lockedland1.setText("Locked Land:");

        Requestedland1.setText("Requested Land:");

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel18.setText("Ward Name:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel7.setText(" ");
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jl7.setText("Pollution type :");

        jLabel9.setText("Shape :");

        jLabel10.setText("Water Resource :");

        jLabel11.setText("Radius :");

        jLabel12.setText("Soil Type :");

        jLabel13.setText("Area Type :");

        jLabel14.setText("Lattitute Longitute :");

        jLabel15.setText("Center-Co-Ordinate :");

        jTextField1.setText(" ");

        jTextField2.setText(" ");

        jTextField3.setText(" ");

        jTextField4.setText(" ");

        jTextField5.setText(" ");

        jTextField7.setText(" ");

        jTextField8.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Listedland1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(Pendingland1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Activatedland1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(Rejectedland1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(ProjectCompletedland1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Lockedland1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Requestedland1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jl7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addGap(57, 57, 57)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField3)
                    .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(jTextField5))
                .addGap(589, 589, 589))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1016, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 92, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Listedland1)
                    .addComponent(Pendingland1)
                    .addComponent(Activatedland1)
                    .addComponent(Rejectedland1)
                    .addComponent(ProjectCompletedland1)
                    .addComponent(Lockedland1)
                    .addComponent(Requestedland1))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl7)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(105, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Listed Land", jPanel1);

        Listedland2.setText(" Listed Land:");

        Pendingland2.setText("Pending Land:");

        Activatedland2.setText("Activated Land:");

        Rejectedland2.setText("Rejected Land:");

        ProjectCompletedland2.setText("Projected Completed Land:");

        Lockedland2.setText("Locked Land:");

        Requestedland2.setText("Requested Land:");

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable7MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable7);

        jLabel25.setText("Ward Name:");

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });

        jLabel26.setText(" ");
        jLabel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel27.setText("Pollution type :");

        jLabel28.setText("Shape :");

        jLabel29.setText("Water Resource :");

        jLabel30.setText("Radius :");

        jLabel31.setText("Soil Type :");

        jLabel32.setText("Area Type :");

        jLabel33.setText("Lattitute Longitute :");

        jLabel34.setText("Center-Co-Ordinate :");

        jTextField9.setText(" ");

        jTextField10.setText(" ");

        jTextField11.setText(" ");

        jTextField12.setText(" ");

        jTextField13.setText(" ");

        jTextField14.setText(" ");

        jTextField15.setText(" ");

        jTextField16.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField9)
                            .addComponent(jTextField10, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                        .addGap(59, 59, 59)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField13, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                            .addComponent(jTextField14)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(Listedland2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(Pendingland2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Activatedland2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(Rejectedland2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(ProjectCompletedland2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Lockedland2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(Requestedland2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1028, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(104, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Listedland2)
                    .addComponent(Pendingland2)
                    .addComponent(Activatedland2)
                    .addComponent(Rejectedland2)
                    .addComponent(ProjectCompletedland2)
                    .addComponent(Lockedland2)
                    .addComponent(Requestedland2))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jLabel29)
                            .addComponent(jLabel31)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(jLabel30)
                            .addComponent(jLabel32)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(99, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Requested Land", jPanel2);

        Listedland3.setText(" Listed Land:");

        Pendingland3.setText("Pending Land:");

        Activatedland3.setText("Activated Land:");

        Rejectedland3.setText("Rejected Land:");

        ProjectCompletedland3.setText("Projected Completed Land:");

        Lockedland3.setText("Locked Land:");

        Requestedland3.setText("Requested Land:");

        jLabel42.setText("Ward Name:");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);

        pendingland2.setText(" ");
        pendingland2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel44.setText("Pollution type :");

        jLabel45.setText("Shape :");

        jLabel47.setText("Water Resource :");

        jLabel48.setText("Soil Type :");

        jLabel49.setText("Area Type :");

        jLabel50.setText("Radius :");

        jLabel46.setText("Lattitute Longitute :");

        jLabel51.setText("Center-Co-Ordinate :");

        jTextField17.setText(" ");

        jTextField18.setText(" ");

        jTextField19.setText(" ");

        jTextField20.setText(" ");

        jTextField21.setText(" ");

        jTextField22.setText(" ");

        jTextField23.setText(" ");

        jTextField24.setText(" ");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField20, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(jTextField19))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                            .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField21, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                            .addComponent(jTextField22))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField23, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(jTextField24))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(Listedland3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(Pendingland3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(Activatedland3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20)
                        .addComponent(Rejectedland3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(ProjectCompletedland3, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Lockedland3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(Requestedland3, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1030, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pendingland2, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(1048, 1048, 1048)
                                .addComponent(jLabel46)
                                .addGap(42, 42, 42))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Listedland3)
                    .addComponent(Pendingland3)
                    .addComponent(Activatedland3)
                    .addComponent(Rejectedland3)
                    .addComponent(ProjectCompletedland3)
                    .addComponent(Lockedland3)
                    .addComponent(Requestedland3))
                .addGap(16, 16, 16)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(pendingland2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(jLabel47)
                    .addComponent(jLabel48)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(jLabel49)
                    .addComponent(jLabel50)
                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(95, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Pending Land", jPanel8);

        Listedland4.setText(" Listed Land:");

        Pendingland4.setText("Pending Land:");

        Activatedland4.setText("Activated Land:");

        Rejectedland4.setText("Rejected Land:");

        ProjectCompletedland4.setText("Projected Completed Land:");

        Lockedland4.setText("Locked Land:");

        Requestedland4.setText("Requested Land:");

        jLabel59.setText("Ward Name:");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable3);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel60.setText(" ");
        jLabel60.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel61.setText("Pollution type :");

        jLabel62.setText("Shape :");

        jLabel63.setText("Water Resource :");

        jLabel64.setText("Radius :");

        jLabel65.setText("Soil Type :");

        jLabel66.setText("Area Type :");

        jLabel67.setText("Lattitute Longitute :");

        jLabel68.setText("Center-Co-Ordinate :");

        jTextField25.setText(" ");

        jTextField26.setText(" ");

        jTextField27.setText(" ");

        jTextField28.setText(" ");

        jTextField29.setText(" ");

        jTextField30.setText(" ");

        jTextField31.setText(" ");

        jTextField32.setText(" ");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel64, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel66, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(Listedland4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(Pendingland4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Activatedland4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(Rejectedland4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(ProjectCompletedland4, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Lockedland4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(Requestedland4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel59)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1017, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel67)
                                        .addGap(40, 40, 40))
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel68)
                                        .addGap(33, 33, 33)))
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(115, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Listedland4)
                    .addComponent(Pendingland4)
                    .addComponent(Activatedland4)
                    .addComponent(Rejectedland4)
                    .addComponent(ProjectCompletedland4)
                    .addComponent(Lockedland4)
                    .addComponent(Requestedland4))
                .addGap(16, 16, 16)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel61)
                            .addComponent(jLabel63)
                            .addComponent(jLabel65)
                            .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel62)
                            .addComponent(jLabel64)
                            .addComponent(jLabel66)
                            .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel67)
                            .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel68)
                            .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(103, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Activated Land", jPanel9);

        Listedland5.setText(" Listed Land:");

        Pendingland5.setText("Pending Land:");

        Activatedland5.setText("Activated Land:");

        Rejectedland5.setText("Rejected Land:");

        ProjectCompletedland5.setText("Projected Completed Land:");

        Lockedland5.setText("Locked Land:");

        Requestedland5.setText("Requested Land:");

        jLabel76.setText("Ward Name:");

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable4);

        jLabel77.setText(" ");
        jLabel77.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel78.setText("Pollution type :");

        jLabel79.setText("Shape :");

        jLabel80.setText("Water Resource :");

        jLabel81.setText("Radius :");

        jLabel82.setText("Soil Type :");

        jLabel83.setText("Area Type :");

        jLabel84.setText("Lattitute Longitute :");

        jLabel85.setText("Center-Co-Ordinate :");

        jTextField33.setText(" ");

        jTextField34.setText(" ");

        jTextField35.setText(" ");

        jTextField36.setText(" ");

        jTextField37.setText(" ");

        jTextField38.setText(" ");

        jTextField39.setText(" ");

        jTextField40.setText(" ");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(Listedland5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(Pendingland5, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Activatedland5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(Rejectedland5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(ProjectCompletedland5, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Lockedland5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(Requestedland5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel76)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(1080, 1080, 1080)
                                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel80)
                                    .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField36, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1048, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel84)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel85)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField40)))))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Listedland5)
                    .addComponent(Pendingland5)
                    .addComponent(Activatedland5)
                    .addComponent(Rejectedland5)
                    .addComponent(ProjectCompletedland5)
                    .addComponent(Lockedland5)
                    .addComponent(Requestedland5))
                .addGap(16, 16, 16)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel76)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel78)
                            .addComponent(jLabel80)
                            .addComponent(jLabel82)
                            .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel79)
                            .addComponent(jLabel81)
                            .addComponent(jLabel83)
                            .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel84)
                            .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel85)
                            .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(111, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Rejected Land", jPanel10);

        Listedland6.setText(" Listed Land:");

        Pendingland6.setText("Pending Land:");

        Activatedland6.setText("Activated Land:");

        Rejectedland6.setText("Rejected Land:");

        ProjectCompletedland6.setText("Projected Completed Land:");

        Lockedland6.setText("Locked Land:");

        Requestedland6.setText("Requested Land:");

        jLabel93.setText("Ward Name:");

        jLabel94.setText("Pollution type :");

        jLabel95.setText("Shape :");

        jLabel96.setText("Water Resource :");

        jLabel97.setText("Radius :");

        jLabel98.setText("Soil Type :");

        jLabel99.setText("Area Type :");

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTable5);

        jTextField41.setText(" ");
        jTextField41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField41ActionPerformed(evt);
            }
        });

        jTextField42.setText(" ");
        jTextField42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField42ActionPerformed(evt);
            }
        });

        jTextField43.setText(" ");

        jTextField44.setText(" ");

        jTextField45.setText(" ");

        jTextField46.setText(" ");
        jTextField46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField46ActionPerformed(evt);
            }
        });

        jTextField47.setText(" ");
        jTextField47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField47ActionPerformed(evt);
            }
        });

        jTextField48.setText(" ");

        jLabel108.setText("Lattitute Longitute :");

        jLabel109.setText("Center-Co-Ordinate :");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        jLabel2.setText(" ");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(Listedland6, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(Pendingland6, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Activatedland6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(Rejectedland6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(ProjectCompletedland6, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Lockedland6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(Requestedland6, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel94, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                                    .addComponent(jLabel95, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel96, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel97, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField43)
                                    .addComponent(jTextField44, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel98, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField45)
                                    .addComponent(jTextField46, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel108)
                                    .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField47, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                                    .addComponent(jTextField48))))))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Listedland6)
                    .addComponent(Pendingland6)
                    .addComponent(Activatedland6)
                    .addComponent(Rejectedland6)
                    .addComponent(ProjectCompletedland6)
                    .addComponent(Lockedland6)
                    .addComponent(Requestedland6))
                .addGap(16, 16, 16)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel93)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel96)
                                .addComponent(jTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel98)
                                .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel94))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel95)
                            .addComponent(jLabel97)
                            .addComponent(jTextField44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel99)
                            .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel108))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel109))))
                .addContainerGap(137, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Project Completed Land", jPanel11);

        Listedland7.setText(" Listed Land:");

        Pendingland7.setText("Pending Land:");

        Activatedland7.setText("Activated Land:");

        Rejectedland7.setText("Rejected Land:");

        ProjectCompletedland7.setText("Projected Completed Land:");

        Lockedland7.setText("Locked Land:");

        Requestedland7.setText("Requested Land:");

        jLabel107.setText("Ward Name:");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable6MouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jTable6);

        jLabel110.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel111.setText("Pollution type :");

        jLabel112.setText("Shape :");

        jLabel113.setText("Water Resource :");

        jLabel114.setText("Radius :");

        jLabel115.setText("Soil Type :");

        jLabel116.setText("Area Type :");

        jLabel117.setText("Lattitute Longitute :");

        jLabel118.setText("Center-Co-Ordinate :");

        jTextField49.setText(" ");
        jTextField49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField49ActionPerformed(evt);
            }
        });

        jTextField50.setText(" ");

        jTextField51.setText(" ");

        jTextField52.setText(" ");
        jTextField52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField52ActionPerformed(evt);
            }
        });

        jTextField53.setText(" ");
        jTextField53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField53ActionPerformed(evt);
            }
        });

        jTextField54.setText(" ");

        jTextField55.setText(" ");

        jTextField56.setText(" ");
        jTextField56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField56ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField49, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField50, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(81, 81, 81)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel113, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField51, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField52, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel115, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel116, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField53, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField54, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 772, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(Listedland7, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(Pendingland7, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Activatedland7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(Rejectedland7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(ProjectCompletedland7, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Lockedland7, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(Requestedland7, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 1019, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField56, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel117, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(37, 37, 37)
                                .addComponent(jTextField55, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel110, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Listedland7)
                    .addComponent(Activatedland7)
                    .addComponent(Rejectedland7)
                    .addComponent(ProjectCompletedland7)
                    .addComponent(Lockedland7)
                    .addComponent(Requestedland7)
                    .addComponent(Pendingland7))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel107)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel111)
                            .addComponent(jLabel113)
                            .addComponent(jLabel115)
                            .addComponent(jTextField49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel112)
                            .addComponent(jLabel114)
                            .addComponent(jLabel116)
                            .addComponent(jTextField50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel110, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel117)
                            .addComponent(jTextField55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel118)
                            .addComponent(jTextField56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(105, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Locked Land", jPanel12);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        int n = jComboBox2.getSelectedIndex();
        String s = (String) jComboBox2.getItemAt(n);
        System.out.println(s);
        if (n == 0) {
            tbmodel1.setRowCount(0);
            alldatafunc(jTable2, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status='Pending Land'");
        } else {
            try {
                comboBoxWardName(jTable2, "select lp.LRID,lp.GEmpID,lp.City,lp.VillageCode,zw.StateCode,"
                        + "lp.DistrictCode,lp.VillageCode,lp.City,lp.ZoneCode,"
                        + "shy.ShapeName,ay.MarkAreaType,lp.CenterCoOrdinate,"
                        + "lp.Radius,lp.LL1,lp.WardCode,lp.WardName,ly.TypeOfLand,"
                        + "lp.AreaSqft,wa.Resource,lp.LMarkDate,lp.LMarkTime,"
                        + "lp.PollutionIndex,lp.Status,lp.SImg from landprofile lp, zoneward zw, shapetype shy"
                        + ",areatype ay, landtype ly, typesofsoil sy, waterresources wa, pollution p"
                        + " where lp.Status = 'Pending Land' and lp.Shape = shy.SNo and lp.Areatype = ay.SNo and lp.LandType = ly.Sno"
                        + " and lp.SoilType = sy.SNo and lp.WAvailable = wa.SNo and lp.PollutionType = p.SNo"
                        + " and lp.StateCode = zw.StateCode and lp.DistrictCode = zw.DistrictCode and "
                        + "lp.ZoneCode = zw.zone and lp.WardCode = zw.WardCode and zw.WardName = '" + s + "'"
                        + " and lp.ZoneCode = " + ParabitLogin.srs.getInt(5) + " Group By LRID");
            } catch (SQLException ex) {
                Logger.getLogger(LandDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTextField47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField47ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField47ActionPerformed

    private void jTextField46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField46ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField46ActionPerformed

    private void jTextField42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField42ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField42ActionPerformed

    private void jTextField56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField56ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField56ActionPerformed

    private void jTextField53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField53ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField53ActionPerformed

    private void jTextField52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField52ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField52ActionPerformed

    private void jTextField49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField49ActionPerformed
    }//GEN-LAST:event_jTextField49ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        int n = jComboBox3.getSelectedIndex();
        String s = (String) jComboBox3.getItemAt(n);
        System.out.println(s);
        if (n == 0) {
            tbmodel1.setRowCount(0);
            alldatafunc(jTable3, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status='Activated Land'");
        } else {
            try {
                comboBoxWardName(jTable3, "select lp.LRID,lp.GEmpID,lp.City,lp.VillageCode,zw.StateCode,"
                        + "lp.DistrictCode,lp.VillageCode,lp.City,lp.ZoneCode,"
                        + "shy.ShapeName,ay.MarkAreaType,lp.CenterCoOrdinate,"
                        + "lp.Radius,lp.LL1,lp.WardCode,lp.WardName,ly.TypeOfLand,"
                        + "lp.AreaSqft,wa.Resource,lp.LMarkDate,lp.LMarkTime,"
                        + "lp.PollutionIndex,lp.Status,lp.SImg from landprofile lp, zoneward zw, shapetype shy"
                        + ",areatype ay, landtype ly, typesofsoil sy, waterresources wa, pollution p"
                        + " where lp.Status = 'Activated Land' and lp.Shape = shy.SNo and lp.Areatype = ay.SNo and lp.LandType = ly.Sno"
                        + " and lp.SoilType = sy.SNo and lp.WAvailable = wa.SNo and lp.PollutionType = p.SNo"
                        + " and lp.StateCode = zw.StateCode and lp.DistrictCode = zw.DistrictCode and "
                        + "lp.ZoneCode = zw.zone and lp.WardCode = zw.WardCode and zw.WardName = '" + s + "'"
                        + " and lp.ZoneCode = " + ParabitLogin.srs.getInt(5) + " Group By LRID");
            } catch (SQLException ex) {
                Logger.getLogger(LandDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        int n = jComboBox1.getSelectedIndex();
        String s = (String) jComboBox1.getItemAt(n);
        System.out.println(s);
        if (n == 0) {
            tbmodel1.setRowCount(0);

            alldatafunc(jTable1, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status='Listed Land'");
        } else {
            try {
                comboBoxWardName(jTable1, "select lp.LRID,lp.GEmpID,lp.City,lp.VillageCode,zw.StateCode,"
                        + "lp.DistrictCode,lp.VillageCode,lp.City,lp.ZoneCode,"
                        + "shy.ShapeName,ay.MarkAreaType,lp.CenterCoOrdinate,"
                        + "lp.Radius,lp.LL1,lp.WardCode,lp.WardName,ly.TypeOfLand,"
                        + "lp.AreaSqft,wa.Resource,lp.LMarkDate,lp.LMarkTime,"
                        + "lp.PollutionIndex,lp.Status,lp.SImg from landprofile lp, zoneward zw, shapetype shy"
                        + ",areatype ay, landtype ly, typesofsoil sy, waterresources wa, pollution p"
                        + " where lp.Status = 'Listed Land' and lp.Shape = shy.SNo and lp.Areatype = ay.SNo and lp.LandType = ly.Sno"
                        + " and lp.SoilType = sy.SNo and lp.WAvailable = wa.SNo and lp.PollutionType = p.SNo"
                        + " and lp.StateCode = zw.StateCode and lp.DistrictCode = zw.DistrictCode and "
                        + "lp.ZoneCode = zw.zone and lp.WardCode = zw.WardCode and zw.WardName = '" + s + "'"
                        + " and lp.ZoneCode = " + ParabitLogin.srs.getInt(5) + " Group By LRID");
            } catch (SQLException ex) {
                Logger.getLogger(LandDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        int n = jComboBox4.getSelectedIndex();
        String s = (String) jComboBox4.getItemAt(n);
        System.out.println(s);
        if (n == 0) {
            tbmodel1.setRowCount(0);
            alldatafunc(jTable4, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status='Rejected Land'");
        } else {
            try {
                comboBoxWardName(jTable4, "select lp.LRID,lp.GEmpID,lp.City,lp.VillageCode,zw.StateCode,"
                        + "lp.DistrictCode,lp.VillageCode,lp.City,lp.ZoneCode,"
                        + "shy.ShapeName,ay.MarkAreaType,lp.CenterCoOrdinate,"
                        + "lp.Radius,lp.LL1,lp.WardCode,lp.WardName,ly.TypeOfLand,"
                        + "lp.AreaSqft,wa.Resource,lp.LMarkDate,lp.LMarkTime,"
                        + "lp.PollutionIndex,lp.Status,lp.SImg from landprofile lp, zoneward zw, shapetype shy"
                        + ",areatype ay, landtype ly, typesofsoil sy, waterresources wa, pollution p"
                        + " where lp.Status = 'Rejected Land' and lp.Shape = shy.SNo and lp.Areatype = ay.SNo and lp.LandType = ly.Sno"
                        + " and lp.SoilType = sy.SNo and lp.WAvailable = wa.SNo and lp.PollutionType = p.SNo"
                        + " and lp.StateCode = zw.StateCode and lp.DistrictCode = zw.DistrictCode and "
                        + "lp.ZoneCode = zw.zone and lp.WardCode = zw.WardCode and zw.WardName = '" + s + "'"
                        + " and lp.ZoneCode = " + ParabitLogin.srs.getInt(5) + " Group By LRID");
            } catch (SQLException ex) {
                Logger.getLogger(LandDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        int n = jComboBox5.getSelectedIndex();
        String s = (String) jComboBox5.getItemAt(n);
        System.out.println(s);
        if (n == 0) {
            tbmodel1.setRowCount(0);
            alldatafunc(jTable5, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status='Project Completed Land'");
        } else {
            try {
                comboBoxWardName(jTable5, "select lp.LRID,lp.GEmpID,lp.City,lp.VillageCode,zw.StateCode,"
                        + "lp.DistrictCode,lp.VillageCode,lp.City,lp.ZoneCode,"
                        + "shy.ShapeName,ay.MarkAreaType,lp.CenterCoOrdinate,"
                        + "lp.Radius,lp.LL1,lp.WardCode,lp.WardName,ly.TypeOfLand,"
                        + "lp.AreaSqft,wa.Resource,lp.LMarkDate,lp.LMarkTime,"
                        + "lp.PollutionIndex,lp.Status,lp.SImg from landprofile lp, zoneward zw, shapetype shy"
                        + ",areatype ay, landtype ly, typesofsoil sy, waterresources wa, pollution p"
                        + " where lp.Status = 'Project Completed Land' and lp.Shape = shy.SNo and lp.Areatype = ay.SNo and lp.LandType = ly.Sno"
                        + " and lp.SoilType = sy.SNo and lp.WAvailable = wa.SNo and lp.PollutionType = p.SNo"
                        + " and lp.StateCode = zw.StateCode and lp.DistrictCode = zw.DistrictCode and "
                        + "lp.ZoneCode = zw.zone and lp.WardCode = zw.WardCode and zw.WardName = '" + s + "'"
                        + " and lp.ZoneCode = " + ParabitLogin.srs.getInt(5) + " Group By LRID");
            } catch (SQLException ex) {
                Logger.getLogger(LandDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        int n = jComboBox6.getSelectedIndex();
        String s = (String) jComboBox6.getItemAt(n);
        System.out.println(s);
        if (n == 0) {
            tbmodel1.setRowCount(0);
            alldatafunc(jTable6, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status='Locked Land'");
        } else {
            try {
                comboBoxWardName(jTable6, "select lp.LRID,lp.GEmpID,lp.City,lp.VillageCode,zw.StateCode,"
                        + "lp.DistrictCode,lp.VillageCode,lp.City,lp.ZoneCode,"
                        + "shy.ShapeName,ay.MarkAreaType,lp.CenterCoOrdinate,"
                        + "lp.Radius,lp.LL1,lp.WardCode,lp.WardName,ly.TypeOfLand,"
                        + "lp.AreaSqft,wa.Resource,lp.LMarkDate,lp.LMarkTime,"
                        + "lp.PollutionIndex,lp.Status,lp.SImg from landprofile lp, zoneward zw, shapetype shy"
                        + ",areatype ay, landtype ly, typesofsoil sy, waterresources wa, pollution p"
                        + " where lp.Status = 'Locked Land' and lp.Shape = shy.SNo and lp.Areatype = ay.SNo and lp.LandType = ly.Sno"
                        + " and lp.SoilType = sy.SNo and lp.WAvailable = wa.SNo and lp.PollutionType = p.SNo"
                        + " and lp.StateCode = zw.StateCode and lp.DistrictCode = zw.DistrictCode and "
                        + "lp.ZoneCode = zw.zone and lp.WardCode = zw.WardCode and zw.WardName = '" + s + "'"
                        + " and lp.ZoneCode = " + ParabitLogin.srs.getInt(5) + " Group By LRID");
            } catch (SQLException ex) {
                Logger.getLogger(LandDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        int n = jComboBox7.getSelectedIndex();
        String s = (String) jComboBox7.getItemAt(n);
        System.out.println(s);
        if (n == 0) {
            tbmodel1.setRowCount(0);
            alldatafunc(jTable7, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status='Requested Land'");
        } else {
            try {
                comboBoxWardName(jTable7, "select lp.LRID,lp.GEmpID,lp.City,lp.VillageCode,zw.StateCode,"
                        + "lp.DistrictCode,lp.VillageCode,lp.City,lp.ZoneCode,"
                        + "shy.ShapeName,ay.MarkAreaType,lp.CenterCoOrdinate,"
                        + "lp.Radius,lp.LL1,lp.WardCode,lp.WardName,ly.TypeOfLand,"
                        + "lp.AreaSqft,wa.Resource,lp.LMarkDate,lp.LMarkTime,"
                        + "lp.PollutionIndex,lp.Status,lp.SImg from landprofile lp, zoneward zw, shapetype shy"
                        + ",areatype ay, landtype ly, typesofsoil sy, waterresources wa, pollution p"
                        + " where lp.Status = 'Requested Land' and lp.Shape = shy.SNo and lp.Areatype = ay.SNo and lp.LandType = ly.Sno"
                        + " and lp.SoilType = sy.SNo and lp.WAvailable = wa.SNo and lp.PollutionType = p.SNo"
                        + " and lp.StateCode = zw.StateCode and lp.DistrictCode = zw.DistrictCode and "
                        + "lp.ZoneCode = zw.zone and lp.WardCode = zw.WardCode and zw.WardName = '" + s + "'"
                        + " and lp.ZoneCode = " + ParabitLogin.srs.getInt(5) + " Group By LRID");
            } catch (SQLException ex) {
                Logger.getLogger(LandDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            textFieldTable1(jTable1, selectedRow);
        } else {
            System.out.println("No row selected.");
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        int selectedRow = jTable3.getSelectedRow();
        if (selectedRow != -1) {
            textFieldTable1(jTable3, selectedRow);
        } else {
            System.out.println("No row selected.");
        }   
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        int selectedRow = jTable4.getSelectedRow();
        if (selectedRow != -1) {
            textFieldTable1(jTable4, selectedRow);
        } else {
            System.out.println("No row selected.");
        }   
    }//GEN-LAST:event_jTable4MouseClicked

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
        int selectedRow = jTable5.getSelectedRow();
        if (selectedRow != -1) {
            textFieldTable1(jTable5, selectedRow);
        } else {
            System.out.println("No row selected.");
        }  
    }//GEN-LAST:event_jTable5MouseClicked

    private void jTable6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable6MouseClicked
        int selectedRow = jTable6.getSelectedRow();
        if (selectedRow != -1) {
            textFieldTable1(jTable6, selectedRow);
        } else {
            System.out.println("No row selected.");
        }  
    }//GEN-LAST:event_jTable6MouseClicked

    private void jTable7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable7MouseClicked
        int selectedRow = jTable7.getSelectedRow();
        if (selectedRow != -1) {
            textFieldTable1(jTable7, selectedRow);
        } else {
            System.out.println("No row selected.");
        }
    }//GEN-LAST:event_jTable7MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow != -1) {
            textFieldTable1(jTable2, selectedRow);
        } else {
            System.out.println("No row selected.");
        }       
    }//GEN-LAST:event_jTable2MouseClicked

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
//        this.setVisible(false);
//        int selectedRow = jTable1.getSelectedRow(); 
//        if (selectedRow != -1) { 
//            lrID = jTable1.getValueAt(selectedRow, 0); 
//            System.out.println(lrID);
//        }
//        NewlandRegistration nl = new NewlandRegistration(null, false, lrID,null);
//        nl.setVisible(true);
//        nl.checkboxfun();

      JTable[] tables={jTable1,jTable2,jTable3,jTable4,jTable5,jTable6,jTable7};
        for(JTable table: tables){
            
             this.setVisible(false);
        int selectedRow = table.getSelectedRow(); 
        if (selectedRow != -1) {            
         if (table == jTable1) {
            lrID = table.getValueAt(selectedRow, 0); 
            System.out.println(lrID);
        } else if (table == jTable2) {
            lrID = table.getValueAt(selectedRow, 0); 
            System.out.println(lrID);
        } else if (table == jTable3) {
            lrID = table.getValueAt(selectedRow, 0);
            System.out.println(lrID);
        } else if (table == jTable4) {
           lrID = table.getValueAt(selectedRow, 0); 
            System.out.println(lrID);
        } else if (table == jTable5) {
            lrID = table.getValueAt(selectedRow, 0); 
            System.out.println(lrID);
        } else if (table == jTable6) {
           lrID = table.getValueAt(selectedRow, 0); 
            System.out.println(lrID);
        } else if (table == jTable7) {
           lrID = table.getValueAt(selectedRow, 0); 
            System.out.println(lrID);
        }
        NewlandRegistration nl = new NewlandRegistration(null, false, lrID,jMenuItem3);

        nl.setVisible(true);
            }
        
        } 
    
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
try {
            wp = ParabitLogin.srs.getString(13);          
            if (wp.equals("Land Inspector")){               
                int c = jTable1.getSelectedRow();       
            lrid = jTable1.getValueAt(c, 0);
            String s = "update landprofile set Status = 'Requested Land' where landprofile.LRID = " + lrid;
            System.out.println(s);
            obdb.stm.executeUpdate(s);
            JOptionPane.showMessageDialog(null, "Done");               
            }         
        } catch (SQLException ex) {
            Logger.getLogger(LandDetails2.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            wp = ParabitLogin.srs.getString(13);          
            if (wp.equals("Zone Municipal Officer")){              
                int c = jTable7.getSelectedRow();      
            lrid = jTable7.getValueAt(c, 0);
            String s = "update landprofile set Status = 'Pending Land' where landprofile.LRID = " + lrid;
            System.out.println(s);
            obdb.stm.executeUpdate(s);
            JOptionPane.showMessageDialog(null, "Done");   
            }          
        } catch (SQLException ex) {
            Logger.getLogger(LandDetails2.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            wp = ParabitLogin.srs.getString(13);          
            if (wp.equals("Municipal Commissioner ")){
                
                int c = jTable2.getSelectedRow();   
            lrid = jTable2.getValueAt(c, 0);
            String s = "update landprofile set Status = 'Activated Land' where landprofile.LRID = " + lrid;
            System.out.println(s);
            obdb.stm.executeUpdate(s);
            JOptionPane.showMessageDialog(null, "Done");             
            }
            Datamember();
        } catch (SQLException ex) {
            Logger.getLogger(LandDetails2.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            int c = jTable1.getSelectedRow();
            c++;
            String s = "update landprofile set Status = 'Rejected Land' where landprofile.LRID = " + c;
            System.out.println(s);
            obdb.stm.executeUpdate(s);
            JOptionPane.showMessageDialog(null, "Deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "2,Some Problem " + e);
        }       
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jTextField41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField41ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField41ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
       
        JTable[] tables={jTable1,jTable2,jTable3,jTable4,jTable5,jTable6,jTable7};
        for(JTable table: tables){
            
             this.setVisible(false);
        int selectedRow = table.getSelectedRow(); 
        if (selectedRow != -1) {            
         if (table == jTable1) {
            lrID = table.getValueAt(selectedRow, 0); 
            System.out.println(lrID);
        } else if (table == jTable2) {
            lrID = table.getValueAt(selectedRow, 0); 
            System.out.println(lrID);
        } else if (table == jTable3) {
            lrID = table.getValueAt(selectedRow, 0);
            System.out.println(lrID);
        } else if (table == jTable4) {
           lrID = table.getValueAt(selectedRow, 0); 
            System.out.println(lrID);
        } else if (table == jTable5) {
            lrID = table.getValueAt(selectedRow, 0); 
            System.out.println(lrID);
        } else if (table == jTable6) {
           lrID = table.getValueAt(selectedRow, 0); 
            System.out.println(lrID);
        } else if (table == jTable7) {
           lrID = table.getValueAt(selectedRow, 0); 
            System.out.println(lrID);
        }
        NewlandRegistration nl = new NewlandRegistration(null, false, lrID,jMenuItem4);

        nl.setVisible(true);
            }
        
        } 
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed

    }//GEN-LAST:event_jMenuItem5ActionPerformed
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
            java.util.logging.Logger.getLogger(LandDetails2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LandDetails2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LandDetails2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LandDetails2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LandDetails2 dialog = new LandDetails2(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JLabel Activatedland1;
    private javax.swing.JLabel Activatedland2;
    private javax.swing.JLabel Activatedland3;
    private javax.swing.JLabel Activatedland4;
    private javax.swing.JLabel Activatedland5;
    private javax.swing.JLabel Activatedland6;
    private javax.swing.JLabel Activatedland7;
    private javax.swing.JLabel Listedland1;
    private javax.swing.JLabel Listedland2;
    private javax.swing.JLabel Listedland3;
    private javax.swing.JLabel Listedland4;
    private javax.swing.JLabel Listedland5;
    private javax.swing.JLabel Listedland6;
    private javax.swing.JLabel Listedland7;
    private javax.swing.JLabel Lockedland1;
    private javax.swing.JLabel Lockedland2;
    private javax.swing.JLabel Lockedland3;
    private javax.swing.JLabel Lockedland4;
    private javax.swing.JLabel Lockedland5;
    private javax.swing.JLabel Lockedland6;
    private javax.swing.JLabel Lockedland7;
    private javax.swing.JLabel Pendingland1;
    private javax.swing.JLabel Pendingland2;
    private javax.swing.JLabel Pendingland3;
    private javax.swing.JLabel Pendingland4;
    private javax.swing.JLabel Pendingland5;
    private javax.swing.JLabel Pendingland6;
    private javax.swing.JLabel Pendingland7;
    private javax.swing.JLabel ProjectCompletedland1;
    private javax.swing.JLabel ProjectCompletedland2;
    private javax.swing.JLabel ProjectCompletedland3;
    private javax.swing.JLabel ProjectCompletedland4;
    private javax.swing.JLabel ProjectCompletedland5;
    private javax.swing.JLabel ProjectCompletedland6;
    private javax.swing.JLabel ProjectCompletedland7;
    private javax.swing.JLabel Rejectedland1;
    private javax.swing.JLabel Rejectedland2;
    private javax.swing.JLabel Rejectedland3;
    private javax.swing.JLabel Rejectedland4;
    private javax.swing.JLabel Rejectedland5;
    private javax.swing.JLabel Rejectedland6;
    private javax.swing.JLabel Rejectedland7;
    private javax.swing.JLabel Requestedland1;
    private javax.swing.JLabel Requestedland2;
    private javax.swing.JLabel Requestedland3;
    private javax.swing.JLabel Requestedland4;
    private javax.swing.JLabel Requestedland5;
    private javax.swing.JLabel Requestedland6;
    private javax.swing.JLabel Requestedland7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField42;
    private javax.swing.JTextField jTextField43;
    private javax.swing.JTextField jTextField44;
    private javax.swing.JTextField jTextField45;
    private javax.swing.JTextField jTextField46;
    private javax.swing.JTextField jTextField47;
    private javax.swing.JTextField jTextField48;
    private javax.swing.JTextField jTextField49;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField50;
    private javax.swing.JTextField jTextField51;
    private javax.swing.JTextField jTextField52;
    private javax.swing.JTextField jTextField53;
    private javax.swing.JTextField jTextField54;
    private javax.swing.JTextField jTextField55;
    private javax.swing.JTextField jTextField56;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JLabel jl7;
    private javax.swing.JPopupMenu myPopup;
    private javax.swing.JLabel pendingland2;
    // End of variables declaration//GEN-END:variables
}
