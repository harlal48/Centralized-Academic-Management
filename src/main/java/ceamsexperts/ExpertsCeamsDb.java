package ceamsexperts;

import java.sql.*;

public class ExpertsCeamsDb {
    
    Connection con;
    Statement stm;
    ResultSet rs;
    
    public static void main(String args[]){
       
        ExpertsCeamsDb ob=new ExpertsCeamsDb();
        
    }
    
   public ExpertsCeamsDb(){
       
       try {
           
           Class.forName("com.mysql.cj.jdbc.Driver");
             con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/parabitceams", "root", "");
           stm=con.createStatement();
           
       } catch (Exception e){
           
           System.out.println(e);
       }
   } 
}
