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
    
    private String user = "INF202";
    private String pass = "inf2020";
        
        
    public Connection connect (){
        Connection con = null;
        try {
        System.out.println("Connecting database...");
        Class.forName("org.hsqldb.jdbcDriver");
        String url = "jdbc:hsqldb:file:C:\\Users\\Baraa\\Desktop\\INF202\\Berichtserstellungssystem\\database\\;shutdown=true";
        con = (Connection) DriverManager.getConnection(url, user, pass);
        System.out.println("Database connected!");
        } catch (ClassNotFoundException e) {
        System.out.println("Database connection error:" + e);
        } catch (SQLException e) {
        System.out.println("Database connection error:" + e);
        }   
        return con;
    }
    
}
