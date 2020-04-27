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
    final private static String URL = "jdbc:hsqldb:Data/Bericht; shutdown=true";
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
    
    public static void createDataBase () {
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(
            "CREATE TABLE Person ( \n" +
            "id INT PRIMARY KEY ,\n" +
            "TCNr BIGINT UNIQUE NOT NULL,\n" +
            "PersonalNr BIGINT Unique NOT NULL, \n" +
            "name VARCHAR(64) NOT NULL,\n" +
            "lastname VARCHAR(64) NOT NULL,\n" +
            "gender VARCHAR(16) NOT NULL,\n" +
            "birthdate Date DEFAULT '1990-01-01',\n" +
            "address VARCHAR(256) DEFAULT 'No Information',\n" +
            "email VARCHAR(64) DEFAULT 'NO E-Mail',\n" +
            "telephone BIGINT DEFAULT 5555555555,\n" +
            "status TINYINT NOT NULL) \n" +
            ";\n" +
            "\n" +
            "\n" +
            "CREATE TABLE Employee (\n" +
            "id INT PRIMARY KEY ,\n" +
            "Person_id INT NOT NULL,\n" +
            "Manager_id INT NOT NULL,\n" +
            "level TINYINT NOT NULL,\n" +
            "permition_End_Date Date NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE Manager (\n" +
            "id INT PRIMARY KEY ,\n" +
            "Person_id INT NOT NULL,\n" +
            "username VARCHAR(64) NOT NULL,\n" +
            "password VARCHAR(256) NOT NULL);\n" +
            "\n" +
            "CREATE TABLE Customer (\n" +
            "id INT PRIMARY KEY ,\n" +
            "Manager_id INT NOT NULL,\n" +
            "name VARCHAR(128) NOT NULL,\n" +
            "address VARCHAR(256) DEFAULT 'No Information'\n" +
            ");\n" +
            "\n" +
            "\n" +
            "CREATE TABLE Customer_Order (\n" +
            "id INT PRIMARY KEY ,\n" +
            "Customer_id INT NOT NULL,\n" +
            "Manager_id INT NOT NULL,\n" +
            "OrderNr VARCHAR(64) NOT NULL\n" +
            ");\n" +
            "\n" +
            "\n" +
            "CREATE TABLE Customer_Offer (\n" +
            "id INT PRIMARY KEY ,\n" +
            "Customer_id INT NOT NULL,\n" +
            "Manager_id INT NOT NULL,\n" +
            "OfferNr VARCHAR(64) NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE Equipment (\n" +
            "id INT PRIMARY KEY ,\n" +
            "Manager_id INT NOT NULL,\n" +
            "name VARCHAR(128) NOT NULL,\n" +
            "calibrationEndDate DATE NOT NULL,\n" +
            "type INT NOT NULL\n" +
            ");\n" +
            "\n" +
            "\n" +
            "CREATE TABLE MagneticEquipment (\n" +
            "id INT PRIMARY KEY ,\n" +
            "Equipment_id INT NOT NULL,\n" +
            "poles_Distance VARCHAR(32) NOT NULL,\n" +
            "MPCarrier VARCHAR(128) NOT NULL,\n" +
            "MagTechnic VARCHAR(128) NOT NULL,\n" +
            "UVIntensity VARCHAR(32) NOT NULL,\n" +
            "lightDistance VARCHAR(32) NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE RadiographicEquipment (\n" +
            "id INT PRIMARY KEY ,\n" +
            "Equipment_id INT NOT NULL,\n" +
            "ir192 BOOLEAN NOT NULL,\n" +
            "se75 BOOLEAN NOT NULL,\n" +
            "xRay BOOLEAN NOT NULL,\n" +
            "focalSpotSize VARCHAR(32) NOT NULL,\n" +
            "exposureTime VARCHAR(32) NOT NULL,\n" +
            "filmFocusDistance VARCHAR(32) NOT NULL,\n" +
            "pbScreens VARCHAR(32) NOT NULL,\n" +
            "filters VARCHAR(32) NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE Report (\n" +
            "id INT PRIMARY KEY ,\n" +
            "Customer VARCHAR(64) NOT NULL,\n" +
            "projectName VARCHAR(64) NOT NULL,\n" +
            "inspectionPlace VARCHAR(32) NOT NULL,\n" +
            "inspectionClass VARCHAR(32) NOT NULL,\n" +
            "evaluationStandard VARCHAR(32) NOT NULL,\n" +
            "inspectionProcedure VARCHAR(32) NOT NULL,\n" +
            "inspectionScope VARCHAR(32) NOT NULL,\n" +
            "drawingNo VARCHAR(32) NOT NULL,\n" +
            "surfaceCondition VARCHAR(32) NOT NULL,\n" +
            "stageOfExamination VARCHAR(32) NOT NULL,\n" +
            "page VARCHAR(8) NOT NULL,\n" +
            "reportNumber VARCHAR(16) NOT NULL,\n" +
            "reportDate DATE NOT NULL,\n" +
            "orderNumber VARCHAR(32) NOT NULL,\n" +
            "offerNumber VARCHAR(32) NOT NULL,\n" +
            "equipment VARCHAR(128) NOT NULL,\n" +
            "heatTreatment VARCHAR(32) NOT NULL,\n" +
            "inspectionDates VARCHAR(64) NOT NULL,\n" +
            "descriptionOfAttachments VARCHAR(256),\n" +
            "operator_Employee_id INT NOT NULL,\n" +
            "evaluator_Employee_id INT NOT NULL,\n" +
            "confirmation_Employee_id INT NOT NULL,\n" +
            "bottom VARCHAR(256) NOT NULL,\n" +
            "type INT NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE RadiographicReport (\n" +
            "id INT PRIMARY KEY ,\n" +
            "Report_id INT NOT NULL,\n" +
            "usedDevice VARCHAR(32) NOT NULL,\n" +
            "ir192 BOOLEAN,\n" +
            "se75 BOOLEAN,\n" +
            "xRay BOOLEAN,\n" +
            "focalSpotSize VARCHAR(32) NOT NULL,\n" +
            "exposureTime VARCHAR(32) NOT NULL,\n" +
            "filmFocusDistance VARCHAR(32) NOT NULL,\n" +
            "pbScreens VARCHAR(32) NOT NULL,\n" +
            "filters VARCHAR(32) NOT NULL,\n" +
            "filmBrand VARCHAR(32) NOT NULL,\n" +
            "d4MX125 BOOLEAN,\n" +
            "d5T200 BOOLEAN,\n" +
            "d7AA400 BOOLEAN,\n" +
            "en BOOLEAN,\n" +
            "astm BOOLEAN,\n" +
            "sourceSide BOOLEAN,\n" +
            "filmSide BOOLEAN,\n" +
            "automatic BOOLEAN,\n" +
            "manuel BOOLEAN,\n" +
            "temp VARCHAR(32),\n" +
            "filmQuantity VARCHAR(96),\n" +
            "testArrangements VARCHAR(64)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE MagneticReport (\n" +
            "id INT PRIMARY KEY ,\n" +
            "Report_id INT NOT NULL,\n" +
            "poleDistance VARCHAR(32) NOT NULL,\n" +
            "MPCarrier VARCHAR(32) NOT NULL,\n" +
            "magTech VARCHAR(32) NOT NULL,\n" +
            "UVIntensity VARCHAR(32) NOT NULL,\n" +
            "distanceOfLight VARCHAR(32) NOT NULL,\n" +
            "examinationArea VARCHAR(32) NOT NULL,\n" +
            "currentType VARCHAR(32) NOT NULL,\n" +
            "Luxmeter VARCHAR(32) NOT NULL, \n" +
            "testMedium VARCHAR(32) NOT NULL,\n" +
            "demagnetization VARCHAR(32) NOT NULL,\n" +
            "surfaceTemperature VARCHAR(32) NOT NULL,\n" +
            "gaussFieldStrength VARCHAR(32) NOT NULL,\n" +
            "surfaceCondition2 VARCHAR(32) NOT NULL,\n" +
            "identificationOfLightEquip VARCHAR(32) NOT NULL,\n" +
            "liftingTest VARCHAR(32) NOT NULL,\n" +
            "buttWeld BOOLEAN,\n" +
            "filletWeld BOOLEAN,\n" +
            "standardDeviations VARCHAR(32)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE RadiographicResults (\n" +
            "id INT PRIMARY KEY ,\n" +
            "Report_id INT NOT NULL,\n" +
            "shootingArea VARCHAR(32) NOT NULL,\n" +
            "filmNo VARCHAR(32) NOT NULL,\n" +
            "materialType VARCHAR(32) NOT NULL,\n" +
            "weldingType VARCHAR(32) NOT NULL,\n" +
            "welderNr VARCHAR(32) NOT NULL,\n" +
            "position VARCHAR(32) NOT NULL,\n" +
            "thickness VARCHAR(32) NOT NULL,\n" +
            "penetremeter VARCHAR(32) NOT NULL,\n" +
            "visibleQ VARCHAR(32) NOT NULL,\n" +
            "density VARCHAR(32) NOT NULL,\n" +
            "f1012 VARCHAR(32) NOT NULL,\n" +
            "f1016 VARCHAR(32) NOT NULL,\n" +
            "f1024 VARCHAR(32) NOT NULL,\n" +
            "f1036 VARCHAR(32) NOT NULL,\n" +
            "f1048 VARCHAR(32) NOT NULL,\n" +
            "f3040 VARCHAR(32) NOT NULL,\n" +
            "defectLocation VARCHAR(32) NOT NULL,\n" +
            "defectType VARCHAR(32) NOT NULL,\n" +
            "preEvaluation VARCHAR(32) NOT NULL,\n" +
            "finalEvaluation VARCHAR(32) NOT NULL\n" +
            ");\n" +
            "\n" +
            "\n" +
            "CREATE TABLE MagneticResults (\n" +
            "id INT PRIMARY KEY ,\n" +
            "Report_id INT NOT NULL,\n" +
            "weldPieceNo VARCHAR(32) NOT NULL,\n" +
            "testLength VARCHAR(32) NOT NULL,\n" +
            "weldingProcess VARCHAR(32) NOT NULL,\n" +
            "thickness VARCHAR(32) NOT NULL,\n" +
            "diameter VARCHAR(32) NOT NULL,\n" +
            "defectType VARCHAR(32) NOT NULL,\n" +
            "defectLocation VARCHAR(32) NOT NULL,\n" +
            "result VARCHAR(32) NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE LastModification (\n" +
            "id INT PRIMARY KEY,\n" +
            "type INT NOT NULL,\n" +
            "Manager_id INT NOT NULL,\n" +
            "Element_id INT NOT NULL,\n" +
            "date DATE NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE Project (\n" +
            "id INT PRIMARY KEY,\n" +
            "project VARCHAR(64) NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE SurfaceCondition (\n" +
            "id INT PRIMARY KEY,\n" +
            "surfaceCondition VARCHAR(64) NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE StageOfExamination (\n" +
            "id INT PRIMARY KEY,\n" +
            "stageOfExamination VARCHAR(64) NOT NULL\n" +
            ");");
            System.out.println("The database is ready");
        } catch (SQLException e) {
            System.out.println("createDatabase " + e);
        }
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
