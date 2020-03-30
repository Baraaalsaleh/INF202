/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem;
import java.sql.*;
/**
 *
 * @author Baraa
 */
public class Connect {
    String url = "jdbc:mysql://sql7.freemysqlhosting.net:3306";
    private String user = "sql7330144";
    private String pass = "VzpLwH6zGh";
        
    
    public Connection connect (){
        Connection con = null;
        try {
        System.out.println("Connecting database...");
        con = DriverManager.getConnection(url, user, pass);
        System.out.println("Database connected!");
        } catch (SQLException e) {
        System.out.println("Database connection error:" + e);
        }   
        return con;
    }

    
    public void create_The_Database (){
     try {
            System.out.println("Connecting database...");
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("Database connected!");
            Statement stmt = con.createStatement();

            ResultSet rs;
            int result;
            stmt.executeQuery("USE sql7330144;");
            result = stmt.executeUpdate("INSERT INTO Person (name, lastname, birthdate, address, email, TCNr, telephone, personalNr, username, password, status) " +
            "VALUES ('Baraa', 'Alsaleh', '1994-01-20', 'There where no one wants to live', 'ba.alsaleh14@gmail.com', 99481540730, 5398127380, 170501103, 'alsaleh6000', 'qweSDFcvb10', 0)");
            
            //con.commit();
            rs = stmt.executeQuery("SELECT * FROM Person");
            while (rs.next()) {
            System.out.println(rs.getString("id"));
             }

            } catch (SQLException e) {
            System.out.println("Database connection error. SQLException: " + e);
            }
    }
    
}
