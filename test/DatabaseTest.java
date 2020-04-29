/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import berichtserstellungssystem.DatabaseManagement.DatabaseManagement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Baraa
 */
public class DatabaseTest {
    
    public DatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test 
    public void testConnection() {
        Connection con = DatabaseManagement.connect();
        if (con != null) {
            Assert.assertTrue(true);
        }
        else {
            Assert.assertFalse("Connection failed!", true);
        }
    }
    
    @Test
    public void personTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, TCNr, PersonalNr, name, lastname, gender, birthdate, address, email, telephone, status FROM Person LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("Person: " + e, true);
        }
    }
    
    @Test
    public void employeeTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, Person_id, Manager_id, level, permition_End_Date FROM Employee LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("Employee: " + e, true);
        }
    }
    
    @Test
    public void managerTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, Person_id, username, password FROM Manager LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("Manager: " + e, true);
        }
    }
    @Test
    public void customerTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, Manager_id, name, address FROM Customer LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("Customer: " + e, true);
        }
    }
    @Test
    public void customerOfferTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, Customer_id, Manager_id, OfferNr FROM Customer_Offer LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("Customer_Offer: " + e, true);
        }
    }
    @Test
    public void customerOrderTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, Customer_id, Manager_id, OrderNr FROM Customer_Order LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("Customer_Order: " + e, true);
        }
    }
    @Test
    public void equipmentTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, Manager_id, name, calibrationEndDate, type FROM Equipment LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("Equipment: " + e, true);
        }
    }
    @Test
    public void magneticEquipmentTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, Equipment_id, poles_Distance, MPCarrier, MagTechnic, UVIntensity, lightDistance FROM MagneticEquipment LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("MagneticEquipment: " + e, true);
        }
    }
    @Test
    public void RadiographicEquipmentTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, Equipment_id, ir192, se75, xRay, focalSpotSize, exposureTime, filmFocusDistance, pbScreens, filters FROM RadiographicEquipment LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("RadiographicEquipment: " + e, true);
        }
    }
    @Test
    public void reportTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, customer, projectName, inspectionPlace, inspectionClass, evaluationStandard, inspectionProcedure, inspectionScope, drawingNo, "
                        + "surfaceCondition, stageOfExamination, page, reportNumber, reportDate, orderNumber, offerNumber, equipment, heatTreatment, inspectionDates, descriptionOfAttachments,"
                        + " operator_Employee_id, evaluator_Employee_id, confirmation_Employee_id, bottom, type FROM Report LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("Report: " + e, true);
        }
    }
    @Test
    public void magneticReportTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, Report_id, poleDistance, MPCarrier, magTech, UVIntensity, distanceOfLight, examinationArea, currentType, Luxmeter,"
                + " testMedium, demagnetization, surfaceTemperature, gaussFieldStrength, surfaceCondition2, identificationOfLightEquip, liftingTest, buttWeld, filletWeld, standardDeviations FROM MagneticReport LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("MagneticReport: " + e, true);
        }
    }
    @Test
    public void radiographicReportTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, Report_id, usedDevice, ir192, se75, xRay, focalSpotSize , exposureTime, filmFocusDistance, pbScreens, filters,"
                        + " filmBrand, d4MX125, d5T200, d7AA400, en , astm, sourceSide, filmSide, automatic , manuel , temp , filmQuantity, testArrangements FROM RadiographicReport LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("RadiographicReport: " + e, true);
        }
    }
    @Test
    public void magneticResultsTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, Report_id, weldPieceNo, testLength, weldingProcess, thickness, diameter, defectType, defectLocation, result FROM MagneticResults LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("MagneticResults: " + e, true);
        }
    }
    @Test
    public void radiographicResultsTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, Report_id, shootingArea, filmNo, materialType, weldingType, welderNr, position, thickness, penetremeter,"
                            + " visibleQ, density, f1012, f1016, f1024, f1036, f1048, f3040, defectLocation, defectType, preEvaluation, finalEvaluation FROM RadiographicResults LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("RadiographicResults: " + e, true);
        }
    }
    @Test
    public void lastModificationTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, Type, Manager_id, Element_id, date FROM LastModification LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("LastModification: " + e, true);
        }
    }
    @Test
    public void projectTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, project FROM Project LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("Project: " + e, true);
        }
    }
    @Test
    public void surfaceConditionTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, surfaceCondition FROM SurfaceCondition LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("SurfaceCondition: " + e, true);
        }
    }
    @Test
    public void stageOfExaminationTableIsThere() {
        Connection con = DatabaseManagement.con;
        Statement stmt = DatabaseManagement.stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, stageOfExamination FROM StageOfExamination LIMIT 1;");
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.assertFalse("StageOfExamination: " + e, true);
        }
    }
}
