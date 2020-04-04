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
public class DatabaseManagement {
    String url = "jdbc:mysql://sql7.freemysqlhosting.net:3306";
    private String user = "sql7330144";
    private String pass = "VzpLwH6zGh";
    private int equipment_type = 2;
    private int customer_type = 3;
    private int report_type = 4;
    
    
    public Connection connect (){
        Connection con = null;
        try {
        System.out.println("Connecting database...");
        con = DriverManager.getConnection(url, user, pass);
        System.out.println("Database connected!");
        Statement stmt = con.createStatement();
        stmt.executeUpdate("USE sql7330144;");
        } catch (SQLException e) {
        System.out.println("Database connection error:" + e);
        }   
        return con;
    }

    
    //Einfügen von Kunden
    //Erstellen der Offerliste eines Kunden
    //Erstellen der Orderliste eines Kunden
    //Erstellen eines Berichts (Magnetic und Radiographic

    public int getEquipment_type() {
        return equipment_type;
    }

    public int getCustomer_type() {
        return customer_type;
    }

    public int getReport_type() {
        return report_type;
    }
    
}
