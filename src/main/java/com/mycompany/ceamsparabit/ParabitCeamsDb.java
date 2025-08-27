package com.mycompany.ceamsparabit;

import java.sql.*;

public class ParabitCeamsDb {

    Connection con;
    Statement stm;
    ResultSet rs;

    public static void main(String args[]) {
        ParabitCeamsDb ob = new ParabitCeamsDb();
    }

    public ParabitCeamsDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/parabitceams", "root", "");
            stm = con.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
     }
}

