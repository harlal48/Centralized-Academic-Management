package com.mycompany.ceamsparabit;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class NewlandRegistration extends javax.swing.JDialog {

    PvtImageLoader il;
    ParabitCeamsDb obdb;
    ParabitCeamsDb obdb1;
    String PollutionIndex, ColonySociety, Address, LandMark, AreaSqft, CenterCoOrdinate, Radius;
    int lrid = 0;
    Object lrID;
    int WC, ZC, VV, SDC, DC, SC, VC;
    String WN, SDN, DN, SN, cityName, imgquery, status;
    int ws, lt, st, pt, at, shapeT;

    ZoneId z = ZoneId.of("Asia/Kolkata");
    LocalDate today = LocalDate.now(z);
    LocalTime currentTime = LocalTime.now(z);
    Random r = new Random();
    int cmt = r.nextInt();
    //for new constructor
    int zc1, wc1, vv1, sdc1, dc1, sc1, vc1;
    float areasqft, pollutionindex, radius;
    String wn1, sdn1, dn1, sn1, cityname1, colony, address, landmark, coo, ll1, ll2, ll3, ll4, ll5, ll6, ll7, ll8, ll9, ll10, ll11, ll12;
    String top, tol, toa, sn, tos, wr, sts;
    StringBuilder textt;
JMenuItem jMenuItem4;
    Time time;
    Date date;

    public NewlandRegistration(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        System.out.println(cmt);

        il = new PvtImageLoader();
        obdb = new ParabitCeamsDb();
        obdb1 = new ParabitCeamsDb();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        this.setSize(screenSize.width, screenSize.height);
        this.setVisible(true);

        try {
            lrid = ParabitLogin.srs.getInt(1);
            String s = "SELECT DISTINCT nn.EmpName,nn.DistrictCode ,"
                    + "nn.SubDistrictCode,vv.DistrictName,vv.SubDistrictName,"
                    + "vv.StateName,vv.VillageVersion , vv.VillageCode  "
                    + "FROM nnemprecords nn,villages vv WHERE nn.SubdistrictCode"
                    + " = vv.SubDistrictCode and nn.districtcode = vv.DistrictCode"
                    + " and NNEID =" + ParabitLogin.srs.getInt(1);
            System.out.println(s);
            obdb.rs = obdb.stm.executeQuery(s);
            obdb.rs.next();
            WN = ParabitLogin.srs.getString(8);
            SDN = obdb.rs.getString(5);
            DN = obdb.rs.getString(4);
            SN = obdb.rs.getString(6);
            VV = obdb.rs.getInt(7);
            VC = obdb.rs.getInt(8);
            WC = ParabitLogin.srs.getInt(6);
            ZC = ParabitLogin.srs.getInt(5);
            SDC = ParabitLogin.srs.getInt(4);
            DC = ParabitLogin.srs.getInt(3);
            SC = ParabitLogin.srs.getInt(2);
            wcode.setText(wcode.getText() + " " + WC);
            WName.setText(WName.getText() + " " + WN);
            zcode.setText(zcode.getText() + " " + ZC);
            VVersion.setText(VVersion.getText() + " " + VV);
            SDCode.setText(SDCode.getText() + " " + SDC);
            DCode.setText(DCode.getText() + " " + DC);
            SCode.setText(SCode.getText() + " " + SC);
            SName.setText(SName.getText() + " " + SN);
            SDName.setText(SDName.getText() + " " + SDN);
            DName.setText(DName.getText() + " " + DN);
            MDL.setText(MDL.getText() + " " + today);
            MTL.setText(MTL.getText() + " " + currentTime);
            VCode.setText(VCode.getText() + " " + VC);
        } catch (Exception e) {
            System.out.println(e);
        }
        addwaterType();
        LandType();
        SoilType();
        PollutionType();
        AreaType();
        ShapeType();
//        try {
//            System.out.println(ParabitLogin.srs.getString(1));
//        } catch (Exception e) {
//        }

    }

    public NewlandRegistration(java.awt.Frame parent, boolean modal, Object lrID,JMenuItem jMenuItem4) {

        super(parent, modal);
        initComponents();
        System.out.println(cmt);
this.jMenuItem4 = jMenuItem4;
        il = new PvtImageLoader();
        obdb = new ParabitCeamsDb();
        obdb1 = new ParabitCeamsDb();
        this.lrID = lrID;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        this.setSize(screenSize.width, screenSize.height);
        this.setVisible(true);
        datafromdatabase();

    }

    public void datafromdatabase() {
        try {
//           SELECT att.MarkAreaType FROM areatype att ,landprofile lp WHERE att.sno = lp.AreaType; 
            // String s1 = "SELECT *from landprofile where LRID = " + lrID;

            String s1 = "SELECT pt.TypeOfPollution,lp.StateCode,lt.TypeOfLand,area.MarkAreaType,"
                    + " st.ShapeName,soil.SoilType, wr.Resource,lp.Status,lp.StateName ,lp.WardName,lp.SubDistrictName,"
                    + "lp.City ,lp.ColonySocietyName,lp.Address ,lp.LandMark ,"
                    + "lp.DistrictName ,lp.WardCode ,lp.AreaSqft,lp.PollutionIndex,lp.CenterCoOrdinate,"
                    + "lp.Radius,lp.ZoneCode,lp.SubDistrictCode,lp.DistrictCode,"
                    + "lp.VillageCode ,lp.LL1, lp.LL2 ,lp.LL3, lp.LL4, "
                    + "lp.LL5, lp.LL6, lp.LL7, lp.LL8, lp.LL9, lp.LL10, lp.LL11,lp.LL12,lp.LMarkDate,lp.LMarkTime FROM pollution pt,landtype lt,"
                    + "areatype area,shapetype st,typesofsoil soil,waterresources wr,"
                    + "landprofile lp WHERE lp.PollutionType=pt.SNo and lp.LandType=lt.SNo and"
                    + " lp.AreaType=area.sno and lp.Shape=st.SNo and "
                    + "lp.SoilType=soil.SNo and lp.WAvailable=wr.SNo and lp.LRID= " + lrID;
            System.out.println(s1);
            obdb.rs = obdb.stm.executeQuery(s1);
            obdb.rs.next();

            top = obdb.rs.getString(1);
            sc1 = obdb.rs.getInt(2);
            tol = obdb.rs.getString(3);
            toa = obdb.rs.getString(4);
            sn = obdb.rs.getString(5);
            tos = obdb.rs.getString(6);
            wr = obdb.rs.getString(7);
            sts = obdb.rs.getString(8);
            sn1 = obdb.rs.getString(9);
            wn1 = obdb.rs.getString(10);
            sdn1 = obdb.rs.getString(11);
            cityname1 = obdb.rs.getString(12);
            colony = obdb.rs.getString(13);
            address = obdb.rs.getString(14);
            landmark = obdb.rs.getString(15);
            dn1 = obdb.rs.getString(16);
            wc1 = obdb.rs.getInt(17);
            areasqft = obdb.rs.getFloat(18);
            pollutionindex = obdb.rs.getFloat(19);
            coo = obdb.rs.getString(20);
            radius = obdb.rs.getFloat(21);
            zc1 = obdb.rs.getInt(22);
            sdc1 = obdb.rs.getInt(23);
            dc1 = obdb.rs.getInt(24);
            vc1 = obdb.rs.getInt(25);
            ll1 = obdb.rs.getString(26);
            ll2 = obdb.rs.getString(27);
            ll3 = obdb.rs.getString(28);
            ll4 = obdb.rs.getString(29);
            ll5 = obdb.rs.getString(30);
            ll6 = obdb.rs.getString(31);
            ll7 = obdb.rs.getString(32);
            ll8 = obdb.rs.getString(33);
            ll9 = obdb.rs.getString(34);
            ll10 = obdb.rs.getString(35);
            ll11 = obdb.rs.getString(36);
            ll12 = obdb.rs.getString(37);

            date = obdb.rs.getDate(38);
            time = obdb.rs.getTime(39);

            cb3.addItem(String.valueOf(top));
            MDL.setText(MDL.getText() + " " + date);
            MTL.setText(MDL.getText() + " " + time);

            SCode.setText(SCode.getText() + " " + sc1);
            cb1.addItem(String.valueOf(tol));
            cb5.addItem(String.valueOf(toa));
            cb6.addItem(String.valueOf(sn));
            cb2.addItem(String.valueOf(tos));
            cb4.addItem(String.valueOf(wr));
            cb7.addItem(String.valueOf(sts));
            WName.setText(WName.getText() + " " + wn1);
            SName.setText(SName.getText() + " " + sn1);
            SDName.setText(SDName.getText() + " " + sdn1);
            city.setText(cityname1);
            CSL.setText(colony);
            AL.setText(address);
            LML.setText(landmark);
            //    cb7.setString(cb7.getString() + " " + STATUS);
            DName.setText(DName.getText() + " " + dn1);
            wcode.setText(wcode.getText() + " " + wc1);

            ASFL.setText(ASFL.getText() + " " + areasqft);
            PIL.setText(PIL.getText() + " " + pollutionindex);
            CCOL.setText(CCOL.getText() + " " + coo);
            RL.setText(RL.getText() + " " + radius);
            //  MDL.setText(MDL.getText() + " " + md);
            zcode.setText(zcode.getText() + " " + zc1);
            SDCode.setText(SDCode.getText() + " " + sdc1);
            DCode.setText(DCode.getText() + " " + dc1);
            VCode.setText(VCode.getText() + " " + vc1);

            lltf1.setText(ll1);
            lltf2.setText(ll2);
            lltf3.setText(ll3);
            lltf4.setText(ll4);
            lltf5.setText(ll5);
            lltf6.setText(ll6);
            lltf7.setText(ll7);
            lltf8.setText(ll8);
            lltf9.setText(ll9);
            lltf10.setText(ll10);
            lltf11.setText(ll11);
            lltf12.setText(ll12);

            databaseImage("SImg", l1, l6);
            databaseImage("Img1", l2, l6);
            databaseImage("Img2", l3, l6);
            databaseImage("Img3", l4, l6);
            databaseImage("Img4", l5, l6);

        } catch (Exception e) {
            System.out.println("e");
        }
      
        if(jMenuItem4.getText().equals("Remark")){
            jButton2.setVisible(false);
            jButton3.setVisible(false);
            jButton4.setVisible(false);
            jButton15.setVisible(false);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tab = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        wcode = new javax.swing.JLabel();
        zcode = new javax.swing.JLabel();
        SDCode = new javax.swing.JLabel();
        DCode = new javax.swing.JLabel();
        SCode = new javax.swing.JLabel();
        WName = new javax.swing.JLabel();
        VVersion = new javax.swing.JLabel();
        SDName = new javax.swing.JLabel();
        DName = new javax.swing.JLabel();
        SName = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        CSL = new javax.swing.JTextField();
        AL = new javax.swing.JTextField();
        LML = new javax.swing.JTextField();
        ASFL = new javax.swing.JTextField();
        CCOL = new javax.swing.JTextField();
        RL = new javax.swing.JTextField();
        PIL = new javax.swing.JTextField();
        cb1 = new javax.swing.JComboBox<>();
        cb2 = new javax.swing.JComboBox<>();
        cb3 = new javax.swing.JComboBox<>();
        cb5 = new javax.swing.JComboBox<>();
        cb4 = new javax.swing.JComboBox<>();
        cb6 = new javax.swing.JComboBox<>();
        city = new javax.swing.JTextField();
        VCode = new javax.swing.JLabel();
        MDL = new javax.swing.JLabel();
        MTL = new javax.swing.JLabel();
        cb7 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        ckb1 = new javax.swing.JCheckBox();
        ckb4 = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        ckb2 = new javax.swing.JCheckBox();
        ckb3 = new javax.swing.JCheckBox();
        jCheckBox15 = new javax.swing.JCheckBox();
        jCheckBox16 = new javax.swing.JCheckBox();
        jCheckBox17 = new javax.swing.JCheckBox();
        jCheckBox18 = new javax.swing.JCheckBox();
        jCheckBox19 = new javax.swing.JCheckBox();
        jCheckBox20 = new javax.swing.JCheckBox();
        jCheckBox21 = new javax.swing.JCheckBox();
        jCheckBox22 = new javax.swing.JCheckBox();
        jCheckBox23 = new javax.swing.JCheckBox();
        jCheckBox24 = new javax.swing.JCheckBox();
        jCheckBox25 = new javax.swing.JCheckBox();
        jCheckBox26 = new javax.swing.JCheckBox();
        jCheckBox27 = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lltf1 = new javax.swing.JTextField();
        lltf2 = new javax.swing.JTextField();
        lltf3 = new javax.swing.JTextField();
        lltf7 = new javax.swing.JTextField();
        lltf8 = new javax.swing.JTextField();
        lltf6 = new javax.swing.JTextField();
        lltf5 = new javax.swing.JTextField();
        lltf9 = new javax.swing.JTextField();
        lltf10 = new javax.swing.JTextField();
        lltf11 = new javax.swing.JTextField();
        lltf12 = new javax.swing.JTextField();
        lltf4 = new javax.swing.JTextField();
        jCheckBox28 = new javax.swing.JCheckBox();
        jCheckBox29 = new javax.swing.JCheckBox();
        jCheckBox30 = new javax.swing.JCheckBox();
        jCheckBox31 = new javax.swing.JCheckBox();
        jCheckBox32 = new javax.swing.JCheckBox();
        jCheckBox33 = new javax.swing.JCheckBox();
        jCheckBox34 = new javax.swing.JCheckBox();
        jCheckBox35 = new javax.swing.JCheckBox();
        jCheckBox36 = new javax.swing.JCheckBox();
        jCheckBox37 = new javax.swing.JCheckBox();
        jCheckBox38 = new javax.swing.JCheckBox();
        jCheckBox39 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        img3 = new javax.swing.JButton();
        img4 = new javax.swing.JButton();
        img5 = new javax.swing.JButton();
        l3 = new javax.swing.JLabel();
        l4 = new javax.swing.JLabel();
        l5 = new javax.swing.JLabel();
        img1 = new javax.swing.JButton();
        l1 = new javax.swing.JLabel();
        img2 = new javax.swing.JButton();
        l2 = new javax.swing.JLabel();
        l6 = new javax.swing.JLabel();
        jCheckBox40 = new javax.swing.JCheckBox();
        jCheckBox41 = new javax.swing.JCheckBox();
        jCheckBox42 = new javax.swing.JCheckBox();
        ckb5 = new javax.swing.JCheckBox();
        jCheckBox44 = new javax.swing.JCheckBox();
        correction = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton2.setBackground(new java.awt.Color(102, 255, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setText("Next");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        wcode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        wcode.setText("Ward Code:");

        zcode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        zcode.setText("Zone Code:");

        SDCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SDCode.setText("Sub Dist Code:");

        DCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        DCode.setText("District Code:");

        SCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SCode.setText("State Code:");

        WName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        WName.setText("Ward Name:");

        VVersion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        VVersion.setText("City :");

        SDName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SDName.setText("Sub Dist Name:");

        DName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        DName.setText("District Name:");

        SName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SName.setText("State Name:");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setText("Marking Date:");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setText("Land Type:");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setText("Pollution Index:");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setText("Area Type:");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel29.setText("Marking Time:");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Soil Type:");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel30.setText("Pollution Type:");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel31.setText("W.Availity :");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel32.setText("Colony/Society:");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel33.setText("Address:");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel34.setText("Land Mark:");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel35.setText("Area Sqft:");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel36.setText("Shape :");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel38.setText("CentrecoOrdinates:");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel39.setText("Radius :");

        AL.setText(" ");

        LML.setText(" ");

        ASFL.setText(" ");

        CCOL.setText(" ");

        RL.setText(" ");

        PIL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PILActionPerformed(evt);
            }
        });

        cb1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cb1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb1ActionPerformed(evt);
            }
        });

        cb2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        cb3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cb3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb3ActionPerformed(evt);
            }
        });

        cb5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cb5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb5ActionPerformed(evt);
            }
        });

        cb4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        cb6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        city.setText(" ");
        city.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cityActionPerformed(evt);
            }
        });

        VCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        VCode.setText("Village Code:");

        MDL.setText(" ");

        MTL.setText(" ");

        cb7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cb7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Listed Land", "Pending Land", "Activated Land", "Rejected Land", "Projected Completed Land", "Locked Land" }));
        cb7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb7ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Status :");

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Zone code is wrong");

        jCheckBox2.setSelected(true);
        jCheckBox2.setText("City is wrong");

        jCheckBox3.setSelected(true);
        jCheckBox3.setText("Land Type is wrong");

        jCheckBox4.setSelected(true);
        jCheckBox4.setText("Soil Type is wrong");

        jCheckBox5.setSelected(true);
        jCheckBox5.setText("  Sub District Code is wrong");
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });

        jCheckBox6.setSelected(true);
        jCheckBox6.setText(" ");

        jCheckBox7.setSelected(true);
        jCheckBox7.setText(" Shape Type is wrong");

        jCheckBox8.setSelected(true);
        jCheckBox8.setText(" Pollution Type is wrong");

        jCheckBox9.setSelected(true);
        jCheckBox9.setText(" ");

        ckb1.setSelected(true);
        ckb1.setText("  Ward code is wrong");

        ckb4.setSelected(true);
        ckb4.setText(" Marking Time is wrong");

        jCheckBox12.setSelected(true);
        jCheckBox12.setText(" ");

        ckb2.setSelected(true);
        ckb2.setText("  Ward Name is wrong");

        ckb3.setSelected(true);
        ckb3.setText(" Marking Date is wrong");

        jCheckBox15.setSelected(true);
        jCheckBox15.setText(" ");

        jCheckBox16.setSelected(true);
        jCheckBox16.setText(" Area Type is Wrong");

        jCheckBox17.setSelected(true);
        jCheckBox17.setText(" Status is wrong");

        jCheckBox18.setSelected(true);
        jCheckBox18.setText(" ");

        jCheckBox19.setSelected(true);
        jCheckBox19.setText(" ");

        jCheckBox20.setSelected(true);
        jCheckBox20.setText("  Colony/Society is wrong");

        jCheckBox21.setSelected(true);
        jCheckBox21.setText("Land Mark is wrong");

        jCheckBox22.setSelected(true);
        jCheckBox22.setText(" ");

        jCheckBox23.setSelected(true);
        jCheckBox23.setText("Address is wrong");

        jCheckBox24.setSelected(true);
        jCheckBox24.setText("Area Sqft is wrong");

        jCheckBox25.setSelected(true);
        jCheckBox25.setText("CentreCoOrdinates is wrong");

        jCheckBox26.setSelected(true);
        jCheckBox26.setText("Pollution Index is wrong");

        jCheckBox27.setSelected(true);
        jCheckBox27.setText(" Radius is wrong");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(596, 596, 596))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ckb3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ckb4, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ckb2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ckb1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(wcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(WName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(MDL, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(MTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(VVersion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cb1, 0, 139, Short.MAX_VALUE)
                                    .addComponent(cb2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(city))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jCheckBox7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                    .addComponent(jCheckBox8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 19, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(zcode, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(25, 25, 25))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(SDCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(SDName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox19, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jCheckBox22, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jCheckBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cb6, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox16, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb4, 0, 165, Short.MAX_VALUE)
                        .addGap(72, 72, 72)
                        .addComponent(jCheckBox17, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jCheckBox15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cb5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(DCode, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(DName, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jCheckBox18, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(VCode, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                    .addComponent(SName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(29, 29, 29)
                        .addComponent(cb7, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jCheckBox23, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBox20, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBox21, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBox24, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBox26, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBox25, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCheckBox27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel35)
                            .addComponent(jLabel34)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CSL, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                            .addComponent(AL)
                            .addComponent(LML)
                            .addComponent(ASFL)
                            .addComponent(PIL)
                            .addComponent(CCOL)
                            .addComponent(RL))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(318, 318, 318))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(wcode)
                            .addComponent(zcode)
                            .addComponent(SDCode)
                            .addComponent(DCode)
                            .addComponent(SCode)
                            .addComponent(jCheckBox1)
                            .addComponent(jCheckBox5)
                            .addComponent(jCheckBox9)
                            .addComponent(ckb1)
                            .addComponent(jCheckBox22))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(WName)
                            .addComponent(SDName)
                            .addComponent(VVersion)
                            .addComponent(city, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox6)
                            .addComponent(ckb2)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(DName)
                        .addComponent(jCheckBox19))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SName)
                        .addComponent(jCheckBox15)))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox16))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)))
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel26)
                                .addComponent(jLabel25)
                                .addComponent(cb1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(MDL)
                                .addComponent(jCheckBox3)
                                .addComponent(ckb3)
                                .addComponent(jCheckBox7))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel28)
                                .addComponent(cb5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(VCode)
                                .addComponent(jCheckBox18)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel31)
                                .addComponent(cb4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cb7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel15)
                                .addComponent(jCheckBox12)
                                .addComponent(jCheckBox17))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cb2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)
                                .addComponent(jCheckBox4)
                                .addComponent(jLabel29)
                                .addComponent(ckb4)
                                .addComponent(MTL)
                                .addComponent(jCheckBox8)))))
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CSL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(jCheckBox20))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(jCheckBox23))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LML, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(jCheckBox21))
                .addGap(13, 13, 13)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ASFL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(jCheckBox24))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PIL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(jCheckBox26))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jCheckBox25)
                    .addComponent(CCOL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(jCheckBox27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(131, 131, 131))
        );

        tab.addTab("Land Mark Address", jPanel1);

        jButton4.setBackground(new java.awt.Color(148, 163, 220));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton4.setText("Previous");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton15.setBackground(new java.awt.Color(51, 255, 204));
        jButton15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton15.setText("Next");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Latitude Longitude");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Latitude longitude ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Latitude longitude ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Latitude longitude ");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Latitude longitude ");

        jLabel7.setText("Latitude longitude ");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Latitude longitude ");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Latitude longitude ");

        jLabel11.setText("Latitude longitude ");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Latitude longitude ");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Latitude longitude ");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Latitude longitude ");

        lltf2.setText(" ");

        lltf3.setText(" ");

        lltf7.setText(" ");

        lltf8.setText(" ");
        lltf8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lltf8ActionPerformed(evt);
            }
        });

        lltf6.setText(" ");

        lltf5.setText(" ");

        lltf9.setText(" ");

        lltf10.setText(" ");

        lltf11.setText(" ");
        lltf11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lltf11ActionPerformed(evt);
            }
        });

        lltf12.setText(" ");
        lltf12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lltf12ActionPerformed(evt);
            }
        });

        lltf4.setText(" ");
        lltf4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lltf4ActionPerformed(evt);
            }
        });

        jCheckBox28.setSelected(true);
        jCheckBox28.setText(" Latitude Longitude 1 is wrong");

        jCheckBox29.setSelected(true);
        jCheckBox29.setText("  Latitude Longitude 2 is wrong");

        jCheckBox30.setSelected(true);
        jCheckBox30.setText("  Latitude Longitude 11 is wrong");

        jCheckBox31.setSelected(true);
        jCheckBox31.setText("  Latitude Longitude 9 is wrong");

        jCheckBox32.setSelected(true);
        jCheckBox32.setText("  Latitude Longitude 7 is wrong");

        jCheckBox33.setSelected(true);
        jCheckBox33.setText("  Latitude Longitude 6 is wrong");

        jCheckBox34.setSelected(true);
        jCheckBox34.setText("  Latitude Longitude 4 is wrong");

        jCheckBox35.setSelected(true);
        jCheckBox35.setText("  Latitude Longitude 12 is wrong");

        jCheckBox36.setSelected(true);
        jCheckBox36.setText("  Latitude Longitude 10 is wrong");
        jCheckBox36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox36ActionPerformed(evt);
            }
        });

        jCheckBox37.setSelected(true);
        jCheckBox37.setText("  Latitude Longitude 8 is wrong");

        jCheckBox38.setSelected(true);
        jCheckBox38.setText("  Latitude Longitude 5 is wrong");

        jCheckBox39.setSelected(true);
        jCheckBox39.setText("  Latitude Longitude 3 is wrong");
        jCheckBox39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox39ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jCheckBox33, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jCheckBox38, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jCheckBox34, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jCheckBox39, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jCheckBox29, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jCheckBox28, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(47, 47, 47)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lltf2, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lltf1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lltf3, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lltf5, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lltf6, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lltf4, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(141, 141, 141)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jCheckBox31, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jCheckBox37, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jCheckBox30, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jCheckBox35, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jCheckBox32, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jCheckBox36, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(57, 57, 57)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lltf8, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                            .addComponent(lltf7)
                            .addComponent(lltf9)
                            .addComponent(lltf10)
                            .addComponent(lltf11)
                            .addComponent(lltf12)))
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 318, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lltf1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox28))
                        .addGap(56, 56, 56)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lltf2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(lltf8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox29)
                            .addComponent(jCheckBox37)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(lltf7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox32))))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lltf4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox34)
                            .addComponent(jLabel14)
                            .addComponent(lltf10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox36)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lltf3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(lltf9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox39)
                            .addComponent(jCheckBox31))))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(lltf5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox38))
                        .addGap(56, 56, 56))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lltf11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox30))
                        .addGap(65, 65, 65)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lltf12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBox35)
                        .addComponent(lltf6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(jCheckBox33)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(128, 128, 128))
        );

        tab.addTab("Resgeographical add", jPanel3);

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton3.setText("Previous");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        img3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        img3.setText(" Image3");
        img3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                img3ActionPerformed(evt);
            }
        });

        img4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        img4.setText("Image4");
        img4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                img4ActionPerformed(evt);
            }
        });

        img5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        img5.setText("Image5");
        img5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                img5ActionPerformed(evt);
            }
        });

        l3.setText(" ");
        l3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        l3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                l3MouseEntered(evt);
            }
        });

        l4.setText(" ");
        l4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        l4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                l4MouseEntered(evt);
            }
        });

        l5.setText(" ");
        l5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        l5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                l5MouseEntered(evt);
            }
        });

        img1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        img1.setText("Image1");
        img1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                img1ActionPerformed(evt);
            }
        });

        l1.setText(" ");
        l1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        l1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                l1MouseEntered(evt);
            }
        });

        img2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        img2.setText("Image2");
        img2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                img2ActionPerformed(evt);
            }
        });

        l2.setText(" ");
        l2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        l2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                l2MouseEntered(evt);
            }
        });

        l6.setText(" ");
        l6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        l6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                l6MouseClicked(evt);
            }
        });

        jCheckBox40.setSelected(true);
        jCheckBox40.setText("  Image 2 is wrong");

        jCheckBox41.setSelected(true);
        jCheckBox41.setText("  Image 3 is wrong");

        jCheckBox42.setSelected(true);
        jCheckBox42.setText("  Image 4 is wrong");

        ckb5.setSelected(true);
        ckb5.setText(" Image 1 is wrong");

        jCheckBox44.setSelected(true);
        jCheckBox44.setText("  Image 5 is wrong");

        correction.setText(" ERROR");
        correction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                correctionActionPerformed(evt);
            }
        });

        jButton1.setText("Activity");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jCheckBox40, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ckb5, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jCheckBox41, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jCheckBox42, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCheckBox44, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(img3, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                                    .addComponent(img4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(img5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(22, 22, 22)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(l4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                                    .addComponent(l3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(l5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(img2, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                                    .addComponent(img1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(22, 22, 22)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(l2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(l1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(63, 63, 63)
                        .addComponent(l6, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(204, 204, 204)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(correction, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(179, 179, 179))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(img1)
                    .addComponent(ckb5))
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(img2)
                    .addComponent(l2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox40))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(img3)
                    .addComponent(l3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox41))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(img4)
                    .addComponent(l4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox42))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(l5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(img5)
                            .addComponent(jCheckBox44))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(126, 126, 126))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(52, 52, 52)
                        .addComponent(correction))
                    .addComponent(l6, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(244, 244, 244))
        );

        tab.addTab("Site Image", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 1578, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(tab)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void databaseImage(String image, JLabel label, JLabel boxlabel) {
        try {
            String query = "";
            // SQL query to get the image from the database
            try {
                query = "SELECT lp." + image + " FROM landprofile lp ";
            } catch (Exception e) {
                query = "SELECT lp." + image + " FROM landprofile lp, nnemprecords nn WHERE nn.LoginID = "
                        + ParabitLogin.srs.getInt(25) + " and nn.LoginPSW = "
                        + ParabitLogin.srs.getInt(26) + " and nn.Status = 1 and lp.LRID = " + lrID;
            }
            System.out.println(query);
            obdb.stm = obdb.con.prepareStatement(query);

            // Execute the query
            obdb.rs = obdb.stm.executeQuery(query);

            if (obdb.rs.next()) {
                byte[] imageData = obdb.rs.getBytes(image); // Replace "EmpPhoto" with your actual image column name

                // Check if imageData is not null and has data
                if (imageData != null && imageData.length > 0) {
                    ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
                    Image img = ImageIO.read(bais);

                    // Check if img was successfully created
                    if (img != null) {
                        // Resize the image for the smaller label
                        Image scaledImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);

                        // Set the scaled image on the smaller JLabel
                        label.setIcon(new ImageIcon(scaledImg));

                        // Add a mouse listener to display the larger image
                        label.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                            @Override
                            public void mouseMoved(java.awt.event.MouseEvent evt) {
                                // Resize the image for the larger label
                                Image largeImg = img.getScaledInstance(boxlabel.getWidth(), boxlabel.getHeight(), Image.SCALE_SMOOTH);
                                boxlabel.setIcon(new ImageIcon(largeImg));
                            }
                        });
                    } else {
                        System.out.println("Image data is invalid or not in a supported format.");
                    }
                } else {
                    System.out.println("No image found for this record.");
                }
            }
        } catch (IOException | SQLException e) {
            System.out.println(e);
        }
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        tab.setSelectedIndex(1);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        tab.setSelectedIndex(0);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        tab.setSelectedIndex(2);
        String setLL = "UPDATE landprofile SET LL1 ='" + lltf1.getText() + "'," + "LL2 = '" + lltf2.getText() + "'," + "LL3 = '" + lltf3.getText() + "'," + "LL4 = '" + lltf4.getText() + "'," + "LL5 = '" + lltf5.getText() + "'," + "LL6 = '" + lltf6.getText() + "'," + "LL7 = '" + lltf7.getText() + "'," + "LL8 = '" + lltf8.getText() + "'," + "LL9 = '" + lltf9.getText() + "'," + "LL10 = '" + lltf10.getText() + "'," + "LL11 = '" + lltf11.getText() + "'," + "LL12 = '" + lltf12.getText() + "' where LRID =" + lrid;
        try {
            obdb.stm.executeUpdate(setLL);
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void img1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_img1ActionPerformed
        il.loadImage(l1, 0);
        imgquery = "UPDATE landprofile SET SImg = ? where LRID =" + lrid;
        imageUpload(imgquery);
    }//GEN-LAST:event_img1ActionPerformed

    private void img2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_img2ActionPerformed
        il.loadImage(l2, 1);
        imgquery = "UPDATE landprofile SET Img1 = ? where LRID =" + lrid;

        imageUpload(imgquery);
    }//GEN-LAST:event_img2ActionPerformed

    private void img3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_img3ActionPerformed
        il.loadImage(l3, 2);
        imgquery = "UPDATE landprofile SET Img2 = ? where LRID =" + lrid;

        imageUpload(imgquery);
    }//GEN-LAST:event_img3ActionPerformed

    private void img4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_img4ActionPerformed
        il.loadImage(l4, 3);
        imgquery = "UPDATE landprofile SET Img3 = ? where LRID =" + lrid;

        imageUpload(imgquery);
    }//GEN-LAST:event_img4ActionPerformed

    private void img5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_img5ActionPerformed
        il.loadImage(l5, 4);
        imgquery = "UPDATE landprofile SET Img4 = ? where LRID =" + lrid;

        imageUpload(imgquery);
    }//GEN-LAST:event_img5ActionPerformed

    private void lltf4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lltf4ActionPerformed

    }//GEN-LAST:event_lltf4ActionPerformed

    private void lltf8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lltf8ActionPerformed

    }//GEN-LAST:event_lltf8ActionPerformed

    private void lltf11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lltf11ActionPerformed

    }//GEN-LAST:event_lltf11ActionPerformed

    private void lltf12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lltf12ActionPerformed

    }//GEN-LAST:event_lltf12ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (!(city.getText().isEmpty()) && !(PIL.getText().isEmpty()) && !(CSL.getText().isEmpty()) && !(AL.getText().isEmpty()) && !(LML.getText().isEmpty()) && !(ASFL.getText().isEmpty()) && !(CCOL.getText().isEmpty()) && !(RL.getText().isEmpty())) {
            PollutionIndex = PIL.getText();
            ColonySociety = CSL.getText();
            Address = AL.getText();
            LandMark = LML.getText();
            AreaSqft = ASFL.getText();
            CenterCoOrdinate = CCOL.getText();
            Radius = RL.getText();
            cityName = city.getText();
            ws = cb4.getSelectedIndex() + 1;
            lt = cb1.getSelectedIndex() + 1;
            st = cb2.getSelectedIndex() + 1;
            pt = cb3.getSelectedIndex() + 1;
            at = cb5.getSelectedIndex() + 1;
            shapeT = cb6.getSelectedIndex() + 1;
            status = cb7.getSelectedItem().toString();

            String insertQuery = "INSERT INTO landprofile(statecode,districtcode,SubDistrictcode,ZoneCode,WardCode,City,PollutionIndex,ColonySocietyName,Address,Landmark,AreaSqft,CenterCoOrdinate,Radius,WAvailable,LandType,SoilType,PollutionType,AreaType,Shape,LmarkDate,Comment,LMarkTime,VillageCode,STATUS,StateName,WardName,DistrictName,SubDistrictName)values(" + SC + "," + DC + "," + SDC + "," + ZC + "," + WC + ",'" + cityName + "'," + PollutionIndex + ",'" + ColonySociety + "','" + Address + "','" + LandMark + "'," + AreaSqft + ",'" + CenterCoOrdinate + "'," + Radius + "," + ws + "," + lt + "," + st + "," + pt + "," + at + "," + shapeT + ",'" + today + "','" + cmt + "','" + currentTime + "'," + VC + ",'" + status + "','" + SN + "','" + WN + "','" + DN + "','" + SDN + "')";
            System.out.println(insertQuery);
            try {
                obdb.stm.executeUpdate(insertQuery);
            } catch (Exception e) {
                System.out.println(e + "hello");
            }
            String s = "select lrid from landprofile where WardCode = " + WC + " and Comment ='" + cmt + "'";
            System.out.println(s);
            try {
                obdb1.rs = obdb1.stm.executeQuery(s);
                obdb1.rs.next();
                lrid = obdb1.rs.getInt(1);
            } catch (Exception e) {
                System.out.print(e + "hii");
            }
            tab.setSelectedIndex(1);
        } else {
            JOptionPane.showMessageDialog(null, "please fill the important field");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cb1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb1ActionPerformed

    private void cityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cityActionPerformed

    }//GEN-LAST:event_cityActionPerformed

    private void l6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l6MouseClicked

    }//GEN-LAST:event_l6MouseClicked

    private void l1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l1MouseEntered
        try {
            il.showImage(l6, 0);
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_l1MouseEntered

    private void l2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l2MouseEntered
        try {
            il.showImage(l6, 1);
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_l2MouseEntered

    private void l4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l4MouseEntered
        try {
            il.showImage(l6, 3);
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_l4MouseEntered

    private void l5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l5MouseEntered
        try {
            il.showImage(l6, 4);
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_l5MouseEntered

    private void PILActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PILActionPerformed

    }//GEN-LAST:event_PILActionPerformed

    private void l3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_l3MouseEntered
        try {
            il.showImage(l6, 2);
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_l3MouseEntered

    private void cb7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb7ActionPerformed

    }//GEN-LAST:event_cb7ActionPerformed

    private void cb3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb3ActionPerformed

    }//GEN-LAST:event_cb3ActionPerformed

    private void jCheckBox36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox36ActionPerformed

    }//GEN-LAST:event_jCheckBox36ActionPerformed

    private void cb5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb5ActionPerformed

    private void correctionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correctionActionPerformed

        checkUncheckedCheckboxes();

    }//GEN-LAST:event_correctionActionPerformed

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void jCheckBox39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox39ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox39ActionPerformed
    public void imageUpload(String imgquery) {
        try {
            FileInputStream fileInputStream = new FileInputStream(il.filePath);
            byte[] imageData = new byte[fileInputStream.available()];
            fileInputStream.read(imageData);
            PreparedStatement ps = obdb.con.prepareStatement(imgquery);
            // PreparedStatement ps = obdb.con.prepareStatement();
            ps.setBytes(1, imageData);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Image inserted successfully");
            } else {
                System.out.println("Image not inserted ");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void addwaterType() {
        String a = "select * from waterresources";
        try {
            obdb.rs = obdb.stm.executeQuery(a);
            while (obdb.rs.next()) {
                String type = obdb.rs.getString(2);
                cb4.addItem(type);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void LandType() {
        String a = "select * from LandType";
        try {
            obdb.rs = obdb.stm.executeQuery(a);
            while (obdb.rs.next()) {
                String type = obdb.rs.getString(2);
                cb1.addItem(type);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void SoilType() {
        String a = "select * from typesofsoil";
        try {
            obdb.rs = obdb.stm.executeQuery(a);
            while (obdb.rs.next()) {
                String type = obdb.rs.getString(2);
                cb2.addItem(type);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void PollutionType() {
        String a = "select * from pollution";
        try {
            obdb.rs = obdb.stm.executeQuery(a);
            while (obdb.rs.next()) {
                String type = obdb.rs.getString(2);
                cb3.addItem(type);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void AreaType() {
        String a = "select * from areatype";
        try {
            obdb.rs = obdb.stm.executeQuery(a);
            while (obdb.rs.next()) {
                String type = obdb.rs.getString(2);
                cb5.addItem(type);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void ShapeType() {
        String a = "select * from shapetype";
        try {
            obdb.rs = obdb.stm.executeQuery(a);
            while (obdb.rs.next()) {
                String type = obdb.rs.getString(2);
                cb6.addItem(type);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void checkboxfun() {
//    JCheckBox[] checkboxes = {jCheckBox1,jCheckBox2};
//        for (JCheckBox checkbox : checkboxes) {
//    checkbox.setVisible(false);
//}

        jCheckBox1.setVisible(false);
        jCheckBox2.setVisible(false);
        jCheckBox3.setVisible(false);
        jCheckBox4.setVisible(false);
        jCheckBox5.setVisible(false);
        jCheckBox6.setVisible(false);
        jCheckBox7.setVisible(false);
        jCheckBox8.setVisible(false);
        jCheckBox9.setVisible(false);
        ckb1.setVisible(false);
        ckb4.setVisible(false);
        jCheckBox12.setVisible(false);
        ckb2.setVisible(false);
        ckb3.setVisible(false);
        jCheckBox15.setVisible(false);
        jCheckBox16.setVisible(false);
        jCheckBox17.setVisible(false);
        jCheckBox18.setVisible(false);
        jCheckBox19.setVisible(false);
        jCheckBox20.setVisible(false);
        jCheckBox21.setVisible(false);
        jCheckBox22.setVisible(false);
        jCheckBox23.setVisible(false);
        jCheckBox24.setVisible(false);
        jCheckBox25.setVisible(false);
        jCheckBox26.setVisible(false);
        jCheckBox27.setVisible(false);
        jCheckBox28.setVisible(false);
        jCheckBox29.setVisible(false);
        jCheckBox30.setVisible(false);
        jCheckBox31.setVisible(false);
        jCheckBox32.setVisible(false);
        jCheckBox33.setVisible(false);
        jCheckBox34.setVisible(false);
        jCheckBox35.setVisible(false);
        jCheckBox36.setVisible(false);
        jCheckBox37.setVisible(false);
        jCheckBox38.setVisible(false);
        jCheckBox39.setVisible(false);
        jCheckBox40.setVisible(false);
        jCheckBox41.setVisible(false);
        jCheckBox42.setVisible(false);
        ckb5.setVisible(false);
        jCheckBox44.setVisible(false);
    }

    private void checkUncheckedCheckboxes() {
        JCheckBox[] checkboxes = {ckb1, ckb2, ckb3, ckb4, ckb5, jCheckBox1, jCheckBox2, jCheckBox3, jCheckBox4, jCheckBox5, jCheckBox6, jCheckBox7, jCheckBox8, jCheckBox22, jCheckBox19, jCheckBox16, jCheckBox12, jCheckBox9, jCheckBox15, jCheckBox18, jCheckBox17, jCheckBox20, jCheckBox23, jCheckBox21, jCheckBox24, jCheckBox26, jCheckBox25, jCheckBox27, jCheckBox28, jCheckBox29, jCheckBox39, jCheckBox34, jCheckBox38, jCheckBox33, jCheckBox32, jCheckBox37, jCheckBox31, jCheckBox36, jCheckBox30, jCheckBox35, jCheckBox40, jCheckBox41, jCheckBox42, jCheckBox44};
        StringBuilder uncheckedMessage = new StringBuilder();
        String sb = String.valueOf(lrID);
        for (JCheckBox checkbox : checkboxes) {
            if (!checkbox.isSelected()) {
                textt = uncheckedMessage.append(checkbox.getText()).append(", \n");
               
            }
        }
         String ss = "INSERT INTO `remarkinformation`(`LRID`, `CheckboxComment`) VALUES (" + sb + ", '" + textt + "')";
                System.out.println(ss);
                try {
//                    obdb.rs = obdb.stm.executeUpdate(ss);
                    obdb.stm.executeUpdate(ss);
                } catch (Exception e) {
                    System.out.println(e);
                }
        if (uncheckedMessage.length() > 0) {
            System.out.println(uncheckedMessage.toString());
            JOptionPane.showMessageDialog(this, textt.toString(), "Unchecked Checkboxes", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("All checkboxes are selected.");
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewlandRegistration dialog = new NewlandRegistration(new javax.swing.JFrame(), true, null,null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AL;
    private javax.swing.JTextField ASFL;
    private javax.swing.JTextField CCOL;
    private javax.swing.JTextField CSL;
    private javax.swing.JLabel DCode;
    private javax.swing.JLabel DName;
    private javax.swing.JTextField LML;
    private javax.swing.JLabel MDL;
    private javax.swing.JLabel MTL;
    private javax.swing.JTextField PIL;
    private javax.swing.JTextField RL;
    private javax.swing.JLabel SCode;
    private javax.swing.JLabel SDCode;
    private javax.swing.JLabel SDName;
    private javax.swing.JLabel SName;
    private javax.swing.JLabel VCode;
    private javax.swing.JLabel VVersion;
    private javax.swing.JLabel WName;
    private javax.swing.JComboBox<String> cb1;
    private javax.swing.JComboBox<String> cb2;
    private javax.swing.JComboBox<String> cb3;
    private javax.swing.JComboBox<String> cb4;
    private javax.swing.JComboBox<String> cb5;
    private javax.swing.JComboBox<String> cb6;
    private javax.swing.JComboBox<String> cb7;
    private javax.swing.JTextField city;
    private javax.swing.JCheckBox ckb1;
    private javax.swing.JCheckBox ckb2;
    private javax.swing.JCheckBox ckb3;
    private javax.swing.JCheckBox ckb4;
    private javax.swing.JCheckBox ckb5;
    private javax.swing.JButton correction;
    private javax.swing.JButton img1;
    private javax.swing.JButton img2;
    private javax.swing.JButton img3;
    private javax.swing.JButton img4;
    private javax.swing.JButton img5;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox15;
    private javax.swing.JCheckBox jCheckBox16;
    private javax.swing.JCheckBox jCheckBox17;
    private javax.swing.JCheckBox jCheckBox18;
    private javax.swing.JCheckBox jCheckBox19;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox20;
    private javax.swing.JCheckBox jCheckBox21;
    private javax.swing.JCheckBox jCheckBox22;
    private javax.swing.JCheckBox jCheckBox23;
    private javax.swing.JCheckBox jCheckBox24;
    private javax.swing.JCheckBox jCheckBox25;
    private javax.swing.JCheckBox jCheckBox26;
    private javax.swing.JCheckBox jCheckBox27;
    private javax.swing.JCheckBox jCheckBox28;
    private javax.swing.JCheckBox jCheckBox29;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox30;
    private javax.swing.JCheckBox jCheckBox31;
    private javax.swing.JCheckBox jCheckBox32;
    private javax.swing.JCheckBox jCheckBox33;
    private javax.swing.JCheckBox jCheckBox34;
    private javax.swing.JCheckBox jCheckBox35;
    private javax.swing.JCheckBox jCheckBox36;
    private javax.swing.JCheckBox jCheckBox37;
    private javax.swing.JCheckBox jCheckBox38;
    private javax.swing.JCheckBox jCheckBox39;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox40;
    private javax.swing.JCheckBox jCheckBox41;
    private javax.swing.JCheckBox jCheckBox42;
    private javax.swing.JCheckBox jCheckBox44;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel l1;
    private javax.swing.JLabel l2;
    private javax.swing.JLabel l3;
    private javax.swing.JLabel l4;
    private javax.swing.JLabel l5;
    private javax.swing.JLabel l6;
    private javax.swing.JTextField lltf1;
    private javax.swing.JTextField lltf10;
    private javax.swing.JTextField lltf11;
    private javax.swing.JTextField lltf12;
    private javax.swing.JTextField lltf2;
    private javax.swing.JTextField lltf3;
    private javax.swing.JTextField lltf4;
    private javax.swing.JTextField lltf5;
    private javax.swing.JTextField lltf6;
    private javax.swing.JTextField lltf7;
    private javax.swing.JTextField lltf8;
    private javax.swing.JTextField lltf9;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JLabel wcode;
    private javax.swing.JLabel zcode;
    // End of variables declaration//GEN-END:variables
}
