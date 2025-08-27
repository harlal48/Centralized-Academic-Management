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

public class LandDetails extends javax.swing.JDialog {

    ParabitCeamsDb obdb;
    ParabitCeamsDb obdb1;
    DefaultTableModel tbmodel1;
    Object lrID;
    String LisLand, PenLand, ActLand, RejLand, PComLand, LockLand, RequestLand, wateravailable, Centercoordinate, Latitutelongitute;
    int ShapeTypes, Areatypes, Pollutiontypes, Soiltypes;
    float radius;
    public JButton bt;

    public LandDetails(java.awt.Frame parent, boolean modal, JButton bt) {
        super(parent, modal);
        initComponents();
        this.bt = bt;
        try {
            String wp = ParabitLogin.srs.getString(13);
            if (wp.equals("Land Inspector") && bt.getText().equals("Land Approval")) {
                // al.setVisible(false);
                jTabbedPane1.setSelectedIndex(2);

            }

        } catch (Exception e) {

        }

        obdb = new ParabitCeamsDb();
        obdb1 = new ParabitCeamsDb();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setVisible(true);
        landTable(jTable1, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status = 'Listed Land'", jLabel7);
        landTable(jTable2, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status = 'Pending Land'", pendingland2);
        landTable(jTable3, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status = 'Activated Land'", jLabel87);
        landTable(jTable4, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status = 'Rejected Land'", jLabel28);
        landTable(jTable5, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status = 'Project Completed Land'", jLabel35);
        landTable(jTable6, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status = 'Locked Land'", jLabel42);
        landTable(jTable7, "SELECT LRID,StateCode,DistrictCode,VillageCode,City,ZoneCode,WardCode,WardName,AreaSqft,LMarkDate,LMarkTime,PollutionIndex,Status FROM landprofile where Status = 'Requested Land'", jLabel49);
        listedLand();
        pendingLand();
        activatedLand();
        rejectedLand();
        projectCompletedLand();
        lockedLand();
        requestLand();
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

//                Pollutiontypes = obdb1.rs.getInt("PollutionType");
//                Areatypes = obdb1.rs.getInt("AreaType");
//                ShapeTypes = obdb1.rs.getInt("Shape");
//                Soiltypes = obdb1.rs.getInt("SoilType");
//                radius = obdb1.rs.getFloat("Radius");
//                wateravailable = obdb1.rs.getString("WAvailable");
//                Centercoordinate = obdb1.rs.getString("CenterCoOrdinate");
//                Latitutelongitute = obdb1.rs.getString("LL1");
                switch (ss) {
                    case "Listed Land":
                        jTextField1.setText(obdb1.rs.getString(1));
                        jTextField6.setText(obdb1.rs.getString(2));
                        jTextField2.setText(obdb1.rs.getString(3));
                        jTextField5.setText(obdb1.rs.getString(4));
                        jTextField4.setText(obdb1.rs.getString(5));
                        jTextField3.setText(obdb1.rs.getString(6));
                        jTextField8.setText(obdb1.rs.getString(7));
                        jTextField7.setText(obdb1.rs.getString(8));
                        break;
                    case "Pending Land":
                        jTextField57.setText(obdb1.rs.getString(1));
                        jTextField62.setText(obdb1.rs.getString(2));
                        jTextField58.setText(obdb1.rs.getString(3));
                        jTextField61.setText(obdb1.rs.getString(4));
                        jTextField60.setText(obdb1.rs.getString(5));
                        jTextField59.setText(obdb1.rs.getString(6));
                        jTextField64.setText(obdb1.rs.getString(8));
                        jTextField63.setText(obdb1.rs.getString(8));
                        break;
                    case "Activated Land":
                        jTextField65.setText(obdb1.rs.getString(1));
                        jTextField70.setText(obdb1.rs.getString(2));
                        jTextField66.setText(obdb1.rs.getString(3));
                        jTextField69.setText(obdb1.rs.getString(4));
                        jTextField68.setText(obdb1.rs.getString(5));
                        jTextField67.setText(obdb1.rs.getString(6));
                        jTextField72.setText(obdb1.rs.getString(7));
                        jTextField71.setText(obdb1.rs.getString(8));
                        break;
                    case "Rejected Land":
                        jTextField73.setText(obdb1.rs.getString(1));
                        jTextField78.setText(obdb1.rs.getString(2));
                        jTextField74.setText(obdb1.rs.getString(3));
                        jTextField77.setText(obdb1.rs.getString(4));
                        jTextField76.setText(obdb1.rs.getString(5));
                        jTextField75.setText(obdb1.rs.getString(6));
                        jTextField80.setText(obdb1.rs.getString(7));
                        jTextField79.setText(obdb1.rs.getString(8));
                        break;
                    case "Project Completed Land":
                        jTextField81.setText(obdb1.rs.getString(1));
                        jTextField86.setText(obdb1.rs.getString(2));
                        jTextField82.setText(obdb1.rs.getString(3));
                        jTextField85.setText(obdb1.rs.getString(4));
                        jTextField84.setText(obdb1.rs.getString(5));
                        jTextField83.setText(obdb1.rs.getString(6));
                        jTextField88.setText(obdb1.rs.getString(7));
                        jTextField87.setText(obdb1.rs.getString(8));
                        break;
                    case "Locked Land":
                        jTextField89.setText(obdb1.rs.getString(1));
                        jTextField94.setText(obdb1.rs.getString(2));
                        jTextField90.setText(obdb1.rs.getString(3));
                        jTextField93.setText(obdb1.rs.getString(4));
                        jTextField92.setText(obdb1.rs.getString(5));
                        jTextField91.setText(obdb1.rs.getString(6));
                        jTextField96.setText(obdb1.rs.getString(7));
                        jTextField95.setText(obdb1.rs.getString(8));
                        break;
                    case "Requested Land":
                        jTextField97.setText(obdb1.rs.getString(1));
                        jTextField102.setText(obdb1.rs.getString(2));
                        jTextField98.setText(obdb1.rs.getString(3));
                        jTextField101.setText(obdb1.rs.getString(4));
                        jTextField100.setText(obdb1.rs.getString(5));
                        jTextField99.setText(obdb1.rs.getString(6));
                        jTextField104.setText(obdb1.rs.getString(7));
                        jTextField103.setText(obdb1.rs.getString(8));
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        myPopup = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jPanel2 = new javax.swing.JPanel();
        al7 = new javax.swing.JPanel();
        Listedland2 = new javax.swing.JLabel();
        Pendingland2 = new javax.swing.JLabel();
        Activatedland2 = new javax.swing.JLabel();
        Rejectedland2 = new javax.swing.JLabel();
        ProjectCompletedland2 = new javax.swing.JLabel();
        Lockedland2 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        pendingland2 = new javax.swing.JLabel();
        Requestedland2 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jTextField57 = new javax.swing.JTextField();
        jTextField58 = new javax.swing.JTextField();
        jTextField59 = new javax.swing.JTextField();
        jTextField60 = new javax.swing.JTextField();
        jTextField61 = new javax.swing.JTextField();
        jTextField62 = new javax.swing.JTextField();
        jTextField63 = new javax.swing.JTextField();
        jTextField64 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        al = new javax.swing.JPanel();
        Listedland1 = new javax.swing.JLabel();
        Pendingland1 = new javax.swing.JLabel();
        Activatedland1 = new javax.swing.JLabel();
        Rejectedland1 = new javax.swing.JLabel();
        ProjectCompletedland1 = new javax.swing.JLabel();
        Lockedland1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Requestedland1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        al8 = new javax.swing.JPanel();
        Listedland3 = new javax.swing.JLabel();
        Pendingland3 = new javax.swing.JLabel();
        Activatedland3 = new javax.swing.JLabel();
        Rejectedland3 = new javax.swing.JLabel();
        ProjectCompletedland3 = new javax.swing.JLabel();
        Lockedland3 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel87 = new javax.swing.JLabel();
        Requestedland3 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jTextField65 = new javax.swing.JTextField();
        jTextField66 = new javax.swing.JTextField();
        jTextField67 = new javax.swing.JTextField();
        jTextField68 = new javax.swing.JTextField();
        jTextField69 = new javax.swing.JTextField();
        jTextField70 = new javax.swing.JTextField();
        jTextField71 = new javax.swing.JTextField();
        jTextField72 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        al9 = new javax.swing.JPanel();
        Listedland4 = new javax.swing.JLabel();
        Pendingland4 = new javax.swing.JLabel();
        Activatedland4 = new javax.swing.JLabel();
        Rejectedland4 = new javax.swing.JLabel();
        ProjectCompletedland4 = new javax.swing.JLabel();
        Lockedland4 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        Requestedland4 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jTextField73 = new javax.swing.JTextField();
        jTextField74 = new javax.swing.JTextField();
        jTextField75 = new javax.swing.JTextField();
        jTextField76 = new javax.swing.JTextField();
        jTextField77 = new javax.swing.JTextField();
        jTextField78 = new javax.swing.JTextField();
        jTextField79 = new javax.swing.JTextField();
        jTextField80 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        al10 = new javax.swing.JPanel();
        Listedland5 = new javax.swing.JLabel();
        Pendingland5 = new javax.swing.JLabel();
        Activatedland5 = new javax.swing.JLabel();
        Rejectedland5 = new javax.swing.JLabel();
        ProjectCompletedland5 = new javax.swing.JLabel();
        Lockedland5 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        Requestedland5 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jTextField81 = new javax.swing.JTextField();
        jTextField82 = new javax.swing.JTextField();
        jTextField83 = new javax.swing.JTextField();
        jTextField84 = new javax.swing.JTextField();
        jTextField85 = new javax.swing.JTextField();
        jTextField86 = new javax.swing.JTextField();
        jTextField87 = new javax.swing.JTextField();
        jTextField88 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        al11 = new javax.swing.JPanel();
        Listedland6 = new javax.swing.JLabel();
        Pendingland6 = new javax.swing.JLabel();
        Activatedland6 = new javax.swing.JLabel();
        Rejectedland6 = new javax.swing.JLabel();
        ProjectCompletedland6 = new javax.swing.JLabel();
        Lockedland6 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jLabel42 = new javax.swing.JLabel();
        Requestedland6 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jTextField89 = new javax.swing.JTextField();
        jTextField90 = new javax.swing.JTextField();
        jTextField91 = new javax.swing.JTextField();
        jTextField92 = new javax.swing.JTextField();
        jTextField93 = new javax.swing.JTextField();
        jTextField94 = new javax.swing.JTextField();
        jTextField95 = new javax.swing.JTextField();
        jTextField96 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        al12 = new javax.swing.JPanel();
        Listedland7 = new javax.swing.JLabel();
        Pendingland7 = new javax.swing.JLabel();
        Activatedland7 = new javax.swing.JLabel();
        Rejectedland7 = new javax.swing.JLabel();
        ProjectCompletedland7 = new javax.swing.JLabel();
        Lockedland7 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jLabel49 = new javax.swing.JLabel();
        Requestedland7 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        jTextField97 = new javax.swing.JTextField();
        jTextField98 = new javax.swing.JTextField();
        jTextField99 = new javax.swing.JTextField();
        jTextField100 = new javax.swing.JTextField();
        jTextField101 = new javax.swing.JTextField();
        jTextField102 = new javax.swing.JTextField();
        jTextField103 = new javax.swing.JTextField();
        jTextField104 = new javax.swing.JTextField();

        jMenuItem1.setText("Send to Approval");
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
        jMenuItem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem3MouseClicked(evt);
            }
        });
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        myPopup.add(jMenuItem3);

        Listedland2.setText("Listed Land:");

        Pendingland2.setText("Pending Land:");

        Activatedland2.setText("Activated Land:");

        Rejectedland2.setText("Rejected Land:");

        ProjectCompletedland2.setText("Projected Completed Land:");

        Lockedland2.setText("Locked Land:");

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
        jScrollPane8.setViewportView(jTable2);

        pendingland2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Requestedland2.setText("Requested Land :");

        jButton8.setText("Next");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel78.setText("Ward Name :");

        jLabel79.setText("Pollution Type :");

        jLabel80.setText("Shape :");

        jLabel81.setText("Water Resource :");

        jLabel82.setText("Radius :");

        jLabel83.setText("Soil Type :");

        jLabel84.setText("Area Type :");

        jLabel85.setText("Latitute Longitute :");

        jLabel86.setText("Center Co-Ordinate :");

        jTextField61.setText(" ");

        jTextField62.setText(" ");

        jTextField63.setText(" ");

        jTextField64.setText(" ");

        javax.swing.GroupLayout al7Layout = new javax.swing.GroupLayout(al7);
        al7.setLayout(al7Layout);
        al7Layout.setHorizontalGroup(
            al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(al7Layout.createSequentialGroup()
                .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(al7Layout.createSequentialGroup()
                        .addComponent(jScrollPane8)
                        .addGap(18, 18, 18)
                        .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pendingland2, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(al7Layout.createSequentialGroup()
                                .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel85, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel86, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField63)
                                    .addComponent(jTextField64)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, al7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(al7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(al7Layout.createSequentialGroup()
                                .addComponent(Listedland2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Pendingland2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Activatedland2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Rejectedland2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ProjectCompletedland2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Lockedland2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Requestedland2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(al7Layout.createSequentialGroup()
                                .addComponent(jLabel78)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(al7Layout.createSequentialGroup()
                                .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel79)
                                    .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField57, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(jTextField58))
                                .addGap(18, 18, 18)
                                .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField59, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                    .addComponent(jTextField60))
                                .addGap(18, 18, 18)
                                .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel84)
                                    .addComponent(jLabel83, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField61, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(jTextField62))))
                        .addGap(0, 102, Short.MAX_VALUE)))
                .addContainerGap())
        );
        al7Layout.setVerticalGroup(
            al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(al7Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Listedland2)
                    .addComponent(Pendingland2)
                    .addComponent(Activatedland2)
                    .addComponent(Rejectedland2)
                    .addComponent(ProjectCompletedland2)
                    .addComponent(Lockedland2)
                    .addComponent(Requestedland2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel78))
                .addGap(12, 12, 12)
                .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(al7Layout.createSequentialGroup()
                        .addComponent(pendingland2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel85)
                            .addComponent(jTextField63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel79)
                        .addComponent(jTextField57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel81)
                        .addComponent(jTextField59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel83)
                        .addComponent(jTextField61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel86)
                        .addComponent(jTextField64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(al7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel80)
                    .addComponent(jTextField58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel82)
                    .addComponent(jTextField60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel84)
                    .addComponent(jTextField62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addGap(27, 27, 27))
        );

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(al7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(al7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        Listedland1.setText("Listed Land:");

        Pendingland1.setText("Pending Land:");

        Activatedland1.setText("Activated Land:");

        Rejectedland1.setText("Rejected Land:");

        ProjectCompletedland1.setText("Projected Completed Land:");

        Lockedland1.setText("Locked Land:");

        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Requestedland1.setText("Requested Land :");

        jButton1.setText("Next");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel9.setText("Ward Name :");

        jLabel10.setText("Pollution Type :");

        jLabel11.setText("Shape :");

        jLabel12.setText("Water Resource :");

        jLabel13.setText("Radius :");

        jLabel14.setText("Soil Type :");

        jLabel15.setText("Area Type :");

        jLabel16.setText("Latitute Longitute :");

        jLabel17.setText("Center Co-Ordinate :");

        jTextField5.setText(" ");

        jTextField6.setText(" ");

        jTextField7.setText(" ");

        jTextField8.setText(" ");

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
        jScrollPane14.setViewportView(jTable1);

        javax.swing.GroupLayout alLayout = new javax.swing.GroupLayout(al);
        al.setLayout(alLayout);
        alLayout.setHorizontalGroup(
            alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(alLayout.createSequentialGroup()
                .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(alLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(alLayout.createSequentialGroup()
                                .addComponent(Listedland1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Pendingland1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Activatedland1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Rejectedland1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ProjectCompletedland1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Lockedland1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Requestedland1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(alLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(alLayout.createSequentialGroup()
                                .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(jTextField2))
                                .addGap(18, 18, 18)
                                .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                    .addComponent(jTextField4))
                                .addGap(18, 18, 18)
                                .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(jTextField6))))
                        .addGap(0, 114, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, alLayout.createSequentialGroup()
                        .addComponent(jScrollPane14)
                        .addGap(24, 24, 24)
                        .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(alLayout.createSequentialGroup()
                                .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField7)
                                    .addComponent(jTextField8)))
                            .addGroup(alLayout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        alLayout.setVerticalGroup(
            alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(alLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Listedland1)
                    .addComponent(Pendingland1)
                    .addComponent(Activatedland1)
                    .addComponent(Rejectedland1)
                    .addComponent(ProjectCompletedland1)
                    .addComponent(Lockedland1)
                    .addComponent(Requestedland1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(12, 12, 12)
                .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(alLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(alLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Listed Land", al);

        Listedland3.setText("Listed Land:");

        Pendingland3.setText("Pending Land:");

        Activatedland3.setText("Activated Land:");

        Rejectedland3.setText("Rejected Land:");

        ProjectCompletedland3.setText("Projected Completed Land:");

        Lockedland3.setText("Locked Land:");

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
        jTable3.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTable3AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(jTable3);

        jLabel87.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Requestedland3.setText("Requested Land :");

        jButton9.setText("Next");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel88.setText("Ward Name :");

        jLabel89.setText("Pollution Type :");

        jLabel90.setText("Shape :");

        jLabel91.setText("Water Resource :");

        jLabel92.setText("Radius :");

        jLabel93.setText("Soil Type :");

        jLabel94.setText("Area Type :");

        jLabel95.setText("Latitute Longitute :");

        jLabel96.setText("Center Co-Ordinate :");

        jTextField69.setText(" ");

        jTextField70.setText(" ");

        jTextField71.setText(" ");

        jTextField72.setText(" ");

        javax.swing.GroupLayout al8Layout = new javax.swing.GroupLayout(al8);
        al8.setLayout(al8Layout);
        al8Layout.setHorizontalGroup(
            al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(al8Layout.createSequentialGroup()
                .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(al8Layout.createSequentialGroup()
                        .addComponent(jScrollPane9)
                        .addGap(18, 18, 18)
                        .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(al8Layout.createSequentialGroup()
                                .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel95, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel96, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField71)
                                    .addComponent(jTextField72)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, al8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(al8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(al8Layout.createSequentialGroup()
                                .addComponent(Listedland3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Pendingland3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Activatedland3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Rejectedland3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ProjectCompletedland3, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Lockedland3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Requestedland3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(al8Layout.createSequentialGroup()
                                .addComponent(jLabel88)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(al8Layout.createSequentialGroup()
                                .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel89)
                                    .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField65, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(jTextField66))
                                .addGap(18, 18, 18)
                                .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel91, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel92, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField67, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                    .addComponent(jTextField68))
                                .addGap(18, 18, 18)
                                .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel94)
                                    .addComponent(jLabel93, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField69, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(jTextField70))))
                        .addGap(0, 102, Short.MAX_VALUE)))
                .addContainerGap())
        );
        al8Layout.setVerticalGroup(
            al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(al8Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Listedland3)
                    .addComponent(Pendingland3)
                    .addComponent(Activatedland3)
                    .addComponent(Rejectedland3)
                    .addComponent(ProjectCompletedland3)
                    .addComponent(Lockedland3)
                    .addComponent(Requestedland3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel88))
                .addGap(12, 12, 12)
                .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(al8Layout.createSequentialGroup()
                        .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel95)
                            .addComponent(jTextField71, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel89)
                        .addComponent(jTextField65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel91)
                        .addComponent(jTextField67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel93)
                        .addComponent(jTextField69, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel96)
                        .addComponent(jTextField72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(al8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel90)
                    .addComponent(jTextField66, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel92)
                    .addComponent(jTextField68, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel94)
                    .addComponent(jTextField70, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jButton9)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(al8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(al8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Activated Land", jPanel3);

        Listedland4.setText("Listed Land:");

        Pendingland4.setText("Pending Land:");

        Activatedland4.setText("Activated Land:");

        Rejectedland4.setText("Rejected Land:");

        ProjectCompletedland4.setText("Projected Completed Land:");

        Lockedland4.setText("Locked Land:");

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
        jScrollPane10.setViewportView(jTable4);

        jLabel28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Requestedland4.setText("Requested Land :");

        jButton10.setText("Next");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel98.setText("Ward Name :");

        jLabel99.setText("Pollution Type :");

        jLabel100.setText("Shape :");

        jLabel101.setText("Water Resource :");

        jLabel102.setText("Radius :");

        jLabel103.setText("Soil Type :");

        jLabel104.setText("Area Type :");

        jLabel105.setText("Latitute Longitute :");

        jLabel106.setText("Center Co-Ordinate :");

        jTextField77.setText(" ");

        jTextField78.setText(" ");

        jTextField79.setText(" ");

        jTextField80.setText(" ");

        javax.swing.GroupLayout al9Layout = new javax.swing.GroupLayout(al9);
        al9.setLayout(al9Layout);
        al9Layout.setHorizontalGroup(
            al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(al9Layout.createSequentialGroup()
                .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(al9Layout.createSequentialGroup()
                        .addComponent(jScrollPane10)
                        .addGap(18, 18, 18)
                        .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(al9Layout.createSequentialGroup()
                                .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel105, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel106, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField79)
                                    .addComponent(jTextField80)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, al9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(al9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(al9Layout.createSequentialGroup()
                                .addComponent(Listedland4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Pendingland4, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Activatedland4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Rejectedland4, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ProjectCompletedland4, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Lockedland4, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Requestedland4, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(al9Layout.createSequentialGroup()
                                .addComponent(jLabel98)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(al9Layout.createSequentialGroup()
                                .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel99)
                                    .addComponent(jLabel100, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField73, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(jTextField74))
                                .addGap(18, 18, 18)
                                .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel101, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel102, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField75, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                    .addComponent(jTextField76))
                                .addGap(18, 18, 18)
                                .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel104)
                                    .addComponent(jLabel103, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField77, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(jTextField78))))
                        .addGap(0, 102, Short.MAX_VALUE)))
                .addContainerGap())
        );
        al9Layout.setVerticalGroup(
            al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(al9Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Listedland4)
                    .addComponent(Pendingland4)
                    .addComponent(Activatedland4)
                    .addComponent(Rejectedland4)
                    .addComponent(ProjectCompletedland4)
                    .addComponent(Lockedland4)
                    .addComponent(Requestedland4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel98))
                .addGap(12, 12, 12)
                .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(al9Layout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel105)
                            .addComponent(jTextField79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel99)
                        .addComponent(jTextField73, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel101)
                        .addComponent(jTextField75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel103)
                        .addComponent(jTextField77, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel106)
                        .addComponent(jTextField80, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(al9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel100)
                    .addComponent(jTextField74, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel102)
                    .addComponent(jTextField76, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel104)
                    .addComponent(jTextField78, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jButton10)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(al9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(al9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Rejected Land", jPanel4);

        Listedland5.setText("Listed Land:");

        Pendingland5.setText("Pending Land:");

        Activatedland5.setText("Activated Land:");

        Rejectedland5.setText("Rejected Land:");

        ProjectCompletedland5.setText("Projected Completed Land:");

        Lockedland5.setText("Locked Land:");

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
        jScrollPane11.setViewportView(jTable5);

        jLabel35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Requestedland5.setText("Requested Land :");

        jButton11.setText("Next");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        jLabel108.setText("Ward Name :");

        jLabel109.setText("Pollution Type :");

        jLabel110.setText("Shape :");

        jLabel111.setText("Water Resource :");

        jLabel112.setText("Radius :");

        jLabel113.setText("Soil Type :");

        jLabel114.setText("Area Type :");

        jLabel115.setText("Latitute Longitute :");

        jLabel116.setText("Center Co-Ordinate :");

        jTextField85.setText(" ");

        jTextField86.setText(" ");

        jTextField87.setText(" ");

        jTextField88.setText(" ");

        javax.swing.GroupLayout al10Layout = new javax.swing.GroupLayout(al10);
        al10.setLayout(al10Layout);
        al10Layout.setHorizontalGroup(
            al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(al10Layout.createSequentialGroup()
                .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(al10Layout.createSequentialGroup()
                        .addComponent(jScrollPane11)
                        .addGap(18, 18, 18)
                        .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(al10Layout.createSequentialGroup()
                                .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel115, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel116, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField87)
                                    .addComponent(jTextField88)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, al10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(al10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(al10Layout.createSequentialGroup()
                                .addComponent(Listedland5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Pendingland5, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Activatedland5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Rejectedland5, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ProjectCompletedland5, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Lockedland5, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Requestedland5, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(al10Layout.createSequentialGroup()
                                .addComponent(jLabel108)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(al10Layout.createSequentialGroup()
                                .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel109)
                                    .addComponent(jLabel110, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField81, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(jTextField82))
                                .addGap(18, 18, 18)
                                .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField83, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                    .addComponent(jTextField84))
                                .addGap(18, 18, 18)
                                .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel114)
                                    .addComponent(jLabel113, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField85, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(jTextField86))))
                        .addGap(0, 102, Short.MAX_VALUE)))
                .addContainerGap())
        );
        al10Layout.setVerticalGroup(
            al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(al10Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Listedland5)
                    .addComponent(Pendingland5)
                    .addComponent(Activatedland5)
                    .addComponent(Rejectedland5)
                    .addComponent(ProjectCompletedland5)
                    .addComponent(Lockedland5)
                    .addComponent(Requestedland5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel108))
                .addGap(12, 12, 12)
                .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(al10Layout.createSequentialGroup()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel115)
                            .addComponent(jTextField87, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel109)
                        .addComponent(jTextField81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel111)
                        .addComponent(jTextField83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel113)
                        .addComponent(jTextField85, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel116)
                        .addComponent(jTextField88, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(al10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel110)
                    .addComponent(jTextField82, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel112)
                    .addComponent(jTextField84, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel114)
                    .addComponent(jTextField86, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jButton11)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(al10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(al10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Projected Completed Land", jPanel5);

        Listedland6.setText("Listed Land:");

        Pendingland6.setText("Pending Land:");

        Activatedland6.setText("Activated Land:");

        Rejectedland6.setText("Rejected Land:");

        ProjectCompletedland6.setText("Projected Completed Land:");

        Lockedland6.setText("Locked Land:");

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
        jScrollPane12.setViewportView(jTable6);

        jLabel42.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Requestedland6.setText("Requested Land :");

        jButton12.setText("Next");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });

        jLabel118.setText("Ward Name :");

        jLabel119.setText("Pollution Type :");

        jLabel120.setText("Shape :");

        jLabel121.setText("Water Resource :");

        jLabel122.setText("Radius :");

        jLabel123.setText("Soil Type :");

        jLabel124.setText("Area Type :");

        jLabel125.setText("Latitute Longitute :");

        jLabel126.setText("Center Co-Ordinate :");

        jTextField93.setText(" ");

        jTextField94.setText(" ");

        jTextField95.setText(" ");

        jTextField96.setText(" ");

        javax.swing.GroupLayout al11Layout = new javax.swing.GroupLayout(al11);
        al11.setLayout(al11Layout);
        al11Layout.setHorizontalGroup(
            al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(al11Layout.createSequentialGroup()
                .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(al11Layout.createSequentialGroup()
                        .addComponent(jScrollPane12)
                        .addGap(18, 18, 18)
                        .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(al11Layout.createSequentialGroup()
                                .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel125, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel126, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField95)
                                    .addComponent(jTextField96)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, al11Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(al11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(al11Layout.createSequentialGroup()
                                .addComponent(Listedland6, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Pendingland6, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Activatedland6, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Rejectedland6, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ProjectCompletedland6, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Lockedland6, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Requestedland6, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(al11Layout.createSequentialGroup()
                                .addComponent(jLabel118)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(al11Layout.createSequentialGroup()
                                .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel119)
                                    .addComponent(jLabel120, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField89, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(jTextField90))
                                .addGap(18, 18, 18)
                                .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel121, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel122, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField91, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                    .addComponent(jTextField92))
                                .addGap(18, 18, 18)
                                .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel124)
                                    .addComponent(jLabel123, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField93, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(jTextField94))))
                        .addGap(0, 102, Short.MAX_VALUE)))
                .addContainerGap())
        );
        al11Layout.setVerticalGroup(
            al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(al11Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Listedland6)
                    .addComponent(Pendingland6)
                    .addComponent(Activatedland6)
                    .addComponent(Rejectedland6)
                    .addComponent(ProjectCompletedland6)
                    .addComponent(Lockedland6)
                    .addComponent(Requestedland6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel118))
                .addGap(12, 12, 12)
                .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(al11Layout.createSequentialGroup()
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel125)
                            .addComponent(jTextField95, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel119)
                        .addComponent(jTextField89, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel121)
                        .addComponent(jTextField91, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel123)
                        .addComponent(jTextField93, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel126)
                        .addComponent(jTextField96, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(al11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel120)
                    .addComponent(jTextField90, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel122)
                    .addComponent(jTextField92, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel124)
                    .addComponent(jTextField94, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jButton12)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(al11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(al11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Locked Land", jPanel6);

        Listedland7.setText("Listed Land:");

        Pendingland7.setText("Pending Land:");

        Activatedland7.setText("Activated Land:");

        Rejectedland7.setText("Rejected Land:");

        ProjectCompletedland7.setText("Projected Completed Land:");

        Lockedland7.setText("Locked Land:");

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
        jScrollPane13.setViewportView(jTable7);

        jLabel49.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Requestedland7.setText("Requested Land :");

        jButton13.setText("Next");

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });

        jLabel128.setText("Ward Name :");

        jLabel129.setText("Pollution Type :");

        jLabel130.setText("Shape :");

        jLabel131.setText("Water Resource :");

        jLabel132.setText("Radius :");

        jLabel133.setText("Soil Type :");

        jLabel134.setText("Area Type :");

        jLabel135.setText("Latitute Longitute :");

        jLabel136.setText("Center Co-Ordinate :");

        jTextField101.setText(" ");

        jTextField102.setText(" ");

        jTextField103.setText(" ");

        jTextField104.setText(" ");

        javax.swing.GroupLayout al12Layout = new javax.swing.GroupLayout(al12);
        al12.setLayout(al12Layout);
        al12Layout.setHorizontalGroup(
            al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(al12Layout.createSequentialGroup()
                .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(al12Layout.createSequentialGroup()
                        .addComponent(jScrollPane13)
                        .addGap(18, 18, 18)
                        .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(al12Layout.createSequentialGroup()
                                .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel135, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel136, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField103)
                                    .addComponent(jTextField104)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, al12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(al12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(al12Layout.createSequentialGroup()
                                .addComponent(Listedland7, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Pendingland7, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Activatedland7, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Rejectedland7, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ProjectCompletedland7, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Lockedland7, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Requestedland7, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(al12Layout.createSequentialGroup()
                                .addComponent(jLabel128)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(al12Layout.createSequentialGroup()
                                .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel129)
                                    .addComponent(jLabel130, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField97, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(jTextField98))
                                .addGap(18, 18, 18)
                                .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel131, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel132, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField99, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                    .addComponent(jTextField100))
                                .addGap(18, 18, 18)
                                .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel134)
                                    .addComponent(jLabel133, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField101, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(jTextField102))))
                        .addGap(0, 102, Short.MAX_VALUE)))
                .addContainerGap())
        );
        al12Layout.setVerticalGroup(
            al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(al12Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Listedland7)
                    .addComponent(Pendingland7)
                    .addComponent(Activatedland7)
                    .addComponent(Rejectedland7)
                    .addComponent(ProjectCompletedland7)
                    .addComponent(Lockedland7)
                    .addComponent(Requestedland7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel128))
                .addGap(12, 12, 12)
                .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(al12Layout.createSequentialGroup()
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel135)
                            .addComponent(jTextField103, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel129)
                        .addComponent(jTextField97, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel131)
                        .addComponent(jTextField99, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel133)
                        .addComponent(jTextField101, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel136)
                        .addComponent(jTextField104, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(al12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel130)
                    .addComponent(jTextField98, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel132)
                    .addComponent(jTextField100, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel134)
                    .addComponent(jTextField102, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jButton13)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(al12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(al12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Request Land", jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            int c = jTable1.getSelectedRow();
            c++;
            String s = "update landprofile set Status = 'Requested Land' where landprofile.LRID = " + c;
            System.out.println(s);
            obdb.stm.executeUpdate(s);
            JOptionPane.showMessageDialog(null, "Done");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "1,Some Problem " + e);
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

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked

    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow != -1) {
            textFieldTable1(jTable2, selectedRow);
        } else {
            System.out.println("No row selected.");
        }
    }//GEN-LAST:event_jTable2MouseClicked

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
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        int selectedRow = jTable3.getSelectedRow();
        if (selectedRow != -1) {
            textFieldTable1(jTable3, selectedRow);
        } else {
            System.out.println("No row selected.");
        }
    }//GEN-LAST:event_jTable3MouseClicked

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

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        int selectedRow = jTable4.getSelectedRow();
        if (selectedRow != -1) {
            textFieldTable1(jTable4, selectedRow);
        } else {
            System.out.println("No row selected.");
        }
    }//GEN-LAST:event_jTable4MouseClicked

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

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
        int selectedRow = jTable5.getSelectedRow();
        if (selectedRow != -1) {
            textFieldTable1(jTable5, selectedRow);
        } else {
            System.out.println("No row selected.");
        }
    }//GEN-LAST:event_jTable5MouseClicked

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

    private void jTable6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable6MouseClicked
        int selectedRow = jTable6.getSelectedRow();
        if (selectedRow != -1) {
            textFieldTable1(jTable6, selectedRow);
        } else {
            System.out.println("No row selected.");
        }
    }//GEN-LAST:event_jTable6MouseClicked

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

    private void jTable7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable7MouseClicked
        int selectedRow = jTable7.getSelectedRow();
        if (selectedRow != -1) {
            textFieldTable1(jTable7, selectedRow);
        } else {
            System.out.println("No row selected.");
        }
    }//GEN-LAST:event_jTable7MouseClicked

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem3MouseClicked


    }//GEN-LAST:event_jMenuItem3MouseClicked

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        this.setVisible(false);
        int selectedRow = jTable1.getSelectedRow(); // Selected row index
        if (selectedRow != -1) { // Check if a row is selected
            lrID = jTable1.getValueAt(selectedRow, 0); // Get LRID from the first column
            System.out.println(lrID);
        }
        NewlandRegistration nl = new NewlandRegistration(null, false, lrID,null);
   nl.checkboxfun();
        nl.setVisible(true);


    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jTable3AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTable3AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable3AncestorAdded

    
    
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LandDetails dialog = new LandDetails(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JPanel al;
    private javax.swing.JPanel al10;
    private javax.swing.JPanel al11;
    private javax.swing.JPanel al12;
    private javax.swing.JPanel al7;
    private javax.swing.JPanel al8;
    private javax.swing.JPanel al9;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
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
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField100;
    private javax.swing.JTextField jTextField101;
    private javax.swing.JTextField jTextField102;
    private javax.swing.JTextField jTextField103;
    private javax.swing.JTextField jTextField104;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField57;
    private javax.swing.JTextField jTextField58;
    private javax.swing.JTextField jTextField59;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField60;
    private javax.swing.JTextField jTextField61;
    private javax.swing.JTextField jTextField62;
    private javax.swing.JTextField jTextField63;
    private javax.swing.JTextField jTextField64;
    private javax.swing.JTextField jTextField65;
    private javax.swing.JTextField jTextField66;
    private javax.swing.JTextField jTextField67;
    private javax.swing.JTextField jTextField68;
    private javax.swing.JTextField jTextField69;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField70;
    private javax.swing.JTextField jTextField71;
    private javax.swing.JTextField jTextField72;
    private javax.swing.JTextField jTextField73;
    private javax.swing.JTextField jTextField74;
    private javax.swing.JTextField jTextField75;
    private javax.swing.JTextField jTextField76;
    private javax.swing.JTextField jTextField77;
    private javax.swing.JTextField jTextField78;
    private javax.swing.JTextField jTextField79;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField80;
    private javax.swing.JTextField jTextField81;
    private javax.swing.JTextField jTextField82;
    private javax.swing.JTextField jTextField83;
    private javax.swing.JTextField jTextField84;
    private javax.swing.JTextField jTextField85;
    private javax.swing.JTextField jTextField86;
    private javax.swing.JTextField jTextField87;
    private javax.swing.JTextField jTextField88;
    private javax.swing.JTextField jTextField89;
    private javax.swing.JTextField jTextField90;
    private javax.swing.JTextField jTextField91;
    private javax.swing.JTextField jTextField92;
    private javax.swing.JTextField jTextField93;
    private javax.swing.JTextField jTextField94;
    private javax.swing.JTextField jTextField95;
    private javax.swing.JTextField jTextField96;
    private javax.swing.JTextField jTextField97;
    private javax.swing.JTextField jTextField98;
    private javax.swing.JTextField jTextField99;
    private javax.swing.JPopupMenu myPopup;
    private javax.swing.JLabel pendingland2;
    // End of variables declaration//GEN-END:variables
}
