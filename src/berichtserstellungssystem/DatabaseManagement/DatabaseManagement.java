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
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Baraa
 */
public class DatabaseManagement {
    //final private static String URL = "jdbc:mysql://remotemysql.com:3306";
    //final private static String USER = "poucOfCVUy";
    //final private static String PASS = "5ObH4PSOeD";
    final private static String URL = "jdbc:hsqldb:file:C:/Users/Baraa/Desktop/Database2/Bericht";
    final private static String USER = "New";
    final private static String PASS = "New";
    final private static int Admin_STATUS = 0;
    final private static int MANAGER_STATUS = 1;
    final private static int EMPLOYEE_STATUS = 2;
    final private static int EMPLOYEE_TYPE = 1;
    final private static int EQUIPMENT_TYPE = 2;
    final private static int CUSTOMER_TYPE = 3;
    final private static int REPORT_TYPE = 4;
    final private static int MAGNETIC_TYPE = 1;
    final private static int RADIOGRAPHIC_TYPE = 2;
    public static Connection con = connect();
    public static Statement stmt;
    
    public static Connection connect (){
        Connection con = null;
        try {
            System.out.println("Connecting database...");
            Class.forName("org.hsqldb.jdbcDriver");
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Database connected!");
            //stmt = con.createStatement();
            //stmt.executeUpdate("USE PUBLIC;");
        } catch (SQLException e) {
            System.out.println("Database connection error:" + e);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database connection error:" + ex);
        }
        return con;
    }
    public static int getAdmin_STATUS() {
        return Admin_STATUS;
    }
    
    public static int getEQUIPMENT_TYPE() {
        return EQUIPMENT_TYPE;
    }

    public static int getCUSTOMER_TYPE() {
        return CUSTOMER_TYPE;
    }

    public static int getREPORT_TYPE() {
        return REPORT_TYPE;
    }

    public static int getMANAGER_STATUS() {
        return MANAGER_STATUS;
    }

    public static int getEMPLOYEE_STATUS() {
        return EMPLOYEE_STATUS;
    }

    public static int getEMPLOYEE_TYPE() {
        return EMPLOYEE_TYPE;
    }

    public static int getMAGNETIC_TYPE() {
        return MAGNETIC_TYPE;
    }

    public static int getRADIOGRAPHIC_TYPE() {
        return RADIOGRAPHIC_TYPE;
    }

}
