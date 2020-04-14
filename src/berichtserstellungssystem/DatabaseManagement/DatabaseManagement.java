/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.DatabaseManagement;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
/**
 *
 * @author Baraa
 */
public class DatabaseManagement {
    final private static String url = "jdbc:mysql://remotemysql.com:3306";
    final private static String user = "poucOfCVUy";
    final private static String pass = "5ObH4PSOeD";
    final private static int manager_status = 1;
    final private static int employee_status = 2;
    final private static int employee_type = 1;
    final private static int equipment_type = 2;
    final private static int customer_type = 3;
    final private static int report_type = 4;
    final private static int magnetic_type = 1;
    final private static int radiographic_type = 2;
    
    public static Connection connect (){
        Connection con = null;
        try {
        System.out.println("Connecting database...");
        con = DriverManager.getConnection(url, user, pass);
        System.out.println("Database connected!");
        Statement stmt = con.createStatement();
        stmt.executeUpdate("USE poucOfCVUy;");
        } catch (SQLException e) {
        System.out.println("Database connection error:" + e);
        }   
        return con;
    }
    
    public static int getEquipment_type() {
        return equipment_type;
    }

    public static int getCustomer_type() {
        return customer_type;
    }

    public static int getReport_type() {
        return report_type;
    }

    public static int getManager_status() {
        return manager_status;
    }

    public static int getEmployee_status() {
        return employee_status;
    }

    public static int getEmployee_type() {
        return employee_type;
    }

    public static int getMagnetic_type() {
        return magnetic_type;
    }

    public static int getRadiographic_type() {
        return radiographic_type;
    }

}
