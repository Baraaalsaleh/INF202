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
    final private static String url = "jdbc:mysql://sql7.freemysqlhosting.net:3306";
    final private static String user = "sql7330144";
    final private static String pass = "VzpLwH6zGh";
    final private static int admin_status = 0;        
    final private static int manager_status = 1;
    final private static int employee_status = 2;
    final private static int employee_type = 1;
    final private static int equipment_type = 2;
    final private static int customer_type = 3;
    final private static int report_type = 4;
    final private static int magnetic_type = 1;
    final private static int radiographic_type = 2;
    /*
    final private static ArrayList<String> table_names = new ArrayList (Arrays.asList("Person", "Employee", "Manager", "Customer", "Customer_Offer",
            "Customer_Order", "Equipment", "MagneticEquipment", "RadiographicEquipment", "Report", "MagneticReport", "RadiographicReport",
            "MagneticResults", "RadiographicResults", "LastModification", "Project", "SurfaceCondition", "StageOfExamination"));
    final private static ArrayList<String> person = new ArrayList(Arrays.asList("id", "TCNr", "PersonalNr", "name", "lastname", "birthdate",
            "address", "email", "telephone", "status"));
    final private static ArrayList<String> employee = new ArrayList(Arrays.asList("id", "Person_id", "Manager_id", "level", "permition_End_Date"));
    final private static ArrayList<String> manager = new ArrayList(Arrays.asList("id", "Person_id", "username", "password"));
    final private static ArrayList<String> customer = new ArrayList(Arrays.asList("id", "Manager_id", "name", "address"));
    final private static ArrayList<String> customer_Offer = new ArrayList(Arrays.asList("id", "Customer_id", "Manager_id", "OfferNr"));
    final private static ArrayList<String> customer_Order = new ArrayList(Arrays.asList("id", "Customer_id", "Manager_id", "OrderNr"));
    final private static ArrayList<String> equipment = new ArrayList(Arrays.asList("id", "Manager_id", "name", "type"));
    final private static ArrayList<String> magneticEquipment = new ArrayList(Arrays.asList("id", "Equipment_id", "poles_Distance", "MPCarrier", "MagTechnic",
            "UVIntensity", "lightDistance"));
    final private static ArrayList<String> radiographicEquipment = new ArrayList(Arrays.asList("id", "Equipment_id", "ir192", "se75", "xRay", "focalSpotSize",
            "exposureTime", "filmFocusDistance", "pbScreens", "filters"));
    final private static ArrayList<String> report = new ArrayList(Arrays.asList("id", "Customer", "projectName", "inspectionPlace", "inspectionClass",
            "evaluationStandard", "inspectionProcedure", "inspectionScope", "drawingNo", "surfaceCondition", "stageOfExamination", "page", "reportNumber",
            "reportDate", "orderNumber", "offerNumber", "equipment", "heatTreatment", "inspectionDates", "descriptionOfAttachments", "operator_Employee_id",
            "evaluator_Employee_id", "confirmation_Employee_id", "customerName", "customerLevel", "type"));    
    final private static ArrayList<String> magneticReport = new ArrayList(Arrays.asList("id", "Report_id", "poleDistance", "MPCarrier", "magTech",
            "UVIntensity", "distanceOfLight", "examinationArea", "currentType", "Luxmeter", "testMedium", "demagnetization", "surfaceTemperature", 
            "gaussFieldStrength", "identificationOfLightEquip", "liftingTest", "buttWeld", "filletWeld", "standardDeviations"));
    final private static ArrayList<String> radiographicReport = new ArrayList(Arrays.asList("id", "Report_id", "usedDevice", "ir192", "se75", "xRay", "focalSpotSize",
            "exposureTime", "filmFocusDistance", "pbScreens", "filters", "filmBrand", "d4MX125", "d5T200", "d7AA400", "en", "astm", "sourceSide", "filmSide",
            "automatic", "manuel", "temp", "filmQuantity", "testArrangements"));
    final private static ArrayList<String> magneticResults = new ArrayList(Arrays.asList("id", "Report_id", "weldPieceNo", "testLength", "weldingProcess",
            "thickness", "diameter", "defectType", "defectLocation", "result"));
        final private static ArrayList<String> radiographicResults = new ArrayList(Arrays.asList("id", "Report_id", "shootingArea", "filmNo", "materialType",
            "weldingType", "welderNr", "position", "thickness", "penetremeter", "visibleQ", "density", "f1012", "f1016", "f1024", "f1036", "f1048", "f3040",
            "defectType", "preEvaluation", "finalEvaluation"));
    final private static ArrayList<String> lastModification = new ArrayList(Arrays.asList("id", "type", "Manager_id", "Element_id", "Date"));
    final private static ArrayList<String> projects = new ArrayList(Arrays.asList("id", "project"));
    final private static ArrayList<String> surfaceCondition = new ArrayList(Arrays.asList("id", "surfaceCondition"));
    final private static ArrayList<String> stageOfExamination = new ArrayList(Arrays.asList("id", "stageOfExamination"));
    final private static ArrayList<String> notString = new ArrayList(Arrays.asList("id", "TCNr", "PersonalNr", "status", "Person_id", "Manager_id", "level",
            "Customer_id", "type", "Equipment_id", "ir192", "se75", "xRay", "operator_Employee_id", "evaluator_Employee_id", "confirmation_Employee_id",
            "Report_id", "d4MX125", "d5T200", "d7AA400", "en", "astm", "sourceSide", "filmSide", "automatic", "manuel", "buttWeld", "filletWeld",
            "Element_id"));
    */
    public static Connection connect (){
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
/*
    public static int insert (String table_name, ArrayList<String> columns, ArrayList<String> values){
        String statement = "INSERT INTO " + table_name + " (";
        for (int i = 0; i < columns.size() - 1; i++) {
            statement += columns.get(i) + ", ";
        }
        statement += columns.get(columns.size()-1) + ") VALUES (";
        boolean isString = true;        
        for (int i = 0; i < values.size()-1; i++){
            isString = true;
            for (String s : notString){
                if (s == columns.get(i)){
                    statement += "" + values.get(i) + ", ";
                    isString = false;
                    break;
                }
                if (isString) {
                    statement += "'" + values.get(i) + "', ";
                }
            }
        }
        isString = true;
        for (String s : notString){
                if (s == columns.get(columns.size()-1)){
                    statement += "" + values.get(values.size()-1) + ");";
                    isString = false;
                    break;
                }
            }
        if (isString) {
                    statement += "'" + values.get(values.size()-1) + "');";
                }
        Connection con = connect();
        int res = 0;
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(statement);
            res = 1;
        }
        
        catch (SQLException e) {
            res = -1;
        }
        
        return res;
    }
    
    public static int update (String table_name, ArrayList<String> columns, ArrayList<String> values, String condition){
        String statement = "UPDATE " + table_name + " SET ";
        boolean isString;
        for (int i = 0; i < columns.size() - 1; i++) {
            isString = true;
            for (String s : notString){
                if (s == columns.get(i)){
                    statement += columns.get(i) + " = " + values.get(i) + ", ";
                    isString = false;
                    break;
                }
                if (isString) {
                    statement += columns.get(i) + " = '" + values.get(i) + "', ";
                }
            }
        }
        isString = true;
        for (String s : notString){
                if (s == columns.get(columns.size()-1)){
                    statement += columns.get(columns.size()-1) + " = " + values.get(values.size()-1) + " " + condition + ";";
                    isString = false;
                    break;
                }
            }
        if (isString) {
                    statement += columns.get(columns.size()-1) + " = '" + values.get(values.size()-1) + "' " + condition + ";";
                }
        Connection con = connect();
        int res = 0;
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(statement);
            res = 1;
        }
        
        catch (SQLException e) {
            res = -1;
        }
        
        return res;
    }
    
    public static ResultSet select (String table_name, ArrayList<String> columns, String condition){
        String statement = "SELECT ";
        for (int i = 0; i < columns.size() - 1; i++) {
            statement += columns.get(i) + ", ";
        }
        statement += columns.get(columns.size()-1) + " ";
        statement += "FROM " + table_name + " " + condition + ";";
        
        Connection con = connect();
        ResultSet res = null;
        try {
            Statement stmt = con.createStatement();
            res = stmt.executeQuery(statement);
        }
        
        catch (SQLException e) {
            res = null;
        }
        
        return res;
    }
    
    public static int delete (String table_name, String condition){
        String statement = "DELETE FROM " + table_name + " " + condition + ";";
        Connection con = connect();
        int res = 0;
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(statement);
            res = 1;
        }
        
        catch (SQLException e) {
            res = -1;
        }
        
        return res;
    }
    */
    
    public static int getEquipment_type() {
        return equipment_type;
    }

    public static int getCustomer_type() {
        return customer_type;
    }

    public static int getReport_type() {
        return report_type;
    }

    public static int getAdmin_status() {
        return admin_status;
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
/*
    public static ArrayList<String> getTable_names() {
        return table_names;
    }

    public static ArrayList<String> getPerson() {
        return person;
    }

    public static ArrayList<String> getEmployee() {
        return employee;
    }

    public static ArrayList<String> getManager() {
        return manager;
    }

    public static ArrayList<String> getCustomer() {
        return customer;
    }

    public static ArrayList<String> getCustomer_Offer() {
        return customer_Offer;
    }

    public static ArrayList<String> getCustomer_Order() {
        return customer_Order;
    }

    public static ArrayList<String> getEquipment() {
        return equipment;
    }

    public static ArrayList<String> getMagneticEquipment() {
        return magneticEquipment;
    }

    public static ArrayList<String> getRadiographicEquipment() {
        return radiographicEquipment;
    }

    public static ArrayList<String> getReport() {
        return report;
    }

    public static ArrayList<String> getMagneticReport() {
        return magneticReport;
    }

    public static ArrayList<String> getRadiographicReport() {
        return radiographicReport;
    }

    public static ArrayList<String> getMagneticResults() {
        return magneticResults;
    }

    public static ArrayList<String> getRadiographicResults() {
        return radiographicResults;
    }

    public static ArrayList<String> getLastModification() {
        return lastModification;
    }

    public static ArrayList<String> getProjects() {
        return projects;
    }

    public static ArrayList<String> getSurfaceCondition() {
        return surfaceCondition;
    }

    public static ArrayList<String> getStageOfExamination() {
        return stageOfExamination;
    }

    public static ArrayList<String> getNotString() {
        return notString;
    }
    
    
    public String toString (int a) {
        int b = a;
        Integer.toString(b)
    }
*/
}
