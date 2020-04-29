package berichtserstellungssystem.DatabaseManagement;

import berichtserstellungssystem.Report.*;
import berichtserstellungssystem.Common;
import berichtserstellungssystem.*;
import berichtserstellungssystem.DatabaseManagement.*;
import berichtserstellungssystem.Resource.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Baraa
 */
public class ReportManagement extends DatabaseManagement{
    //Einfügen von Bericht (magnetic)
    public static int insertMagneticReport (MagneticReport report){
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + DataPreparation.prepareString(report.getCustomer()) + "' AND reportNumber = '" + DataPreparation.prepareString(report.getReportNumber()) + "';");

            if (rs.next() == false) {
                String reportDate = Common.date_toString(report.getReportDate());
                int operator_id = report.getOperator_id();
                int evaluator_id = report.getEvaluator_id();
                int confirmation_id = report.getConfirmation_id();   
                int id = 0;
                rs = stmt.executeQuery("SELECT MAX(id) as id  FROM Report;");
                if (rs.next()) {
                    id = rs.getInt("id");
                }
                stmt.executeUpdate("INSERT INTO Report (id, customer, projectName, inspectionPlace, inspectionClass, evaluationStandard, inspectionProcedure, inspectionScope, drawingNo, "
                        + "surfaceCondition, stageOfExamination, page, reportNumber, reportDate, orderNumber, offerNumber, equipment, heatTreatment, inspectionDates, descriptionOfAttachments,"
                        + " operator_Employee_id, evaluator_Employee_id, confirmation_Employee_id, bottom, type) VALUES\n"
                        + "(" + (id+1) + ", '" + DataPreparation.prepareString(report.getCustomer()) + "', '" + DataPreparation.prepareString(report.getProjectName()) + "', '" 
                        + DataPreparation.prepareString(report.getInspectionPlace()) + "', '" + DataPreparation.prepareString(report.getInspectionClass()) + "', '"
                        + DataPreparation.prepareString(report.getEvaluationStandard()) + "', '" + DataPreparation.prepareString(report.getInspectionProcedure()) + "', '" 
                        + DataPreparation.prepareString(report.getInspectionScope()) + "', '" + DataPreparation.prepareString(report.getDrawingNo()) + "', '"
                        + DataPreparation.prepareString(report.getSurfaceCondition()) + "', '" + DataPreparation.prepareString(report.getStageOfExamination()) + "', '" 
                        + DataPreparation.prepareString(report.getPage()) + "', '" + DataPreparation.prepareString(report.getReportNumber()) + "', '" + DataPreparation.prepareString(reportDate) 
                        + "', '" + DataPreparation.prepareString(report.getOrderNumber()) + "', '" + DataPreparation.prepareString(report.getOfferNumber()) + "', '" + DataPreparation.prepareString(report.getEquipment())
                        + "', '" + DataPreparation.prepareString(report.getHeatTreatment()) + "', '" + DataPreparation.prepareString(report.getInspectionDates())
                        + "', '" + DataPreparation.prepareString(report.getDescriptionOfAttachments()) + "', " + operator_id + ", " + evaluator_id + ", " + confirmation_id + ", '"
                        + "', '" + DataPreparation.prepareString(report.getBottom()) + "', " + DatabaseManagement.getMAGNETIC_TYPE() + ");");
                
                int report_id = 0;
                rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + DataPreparation.prepareString(report.getCustomer()) + "' AND reportNumber = '" + DataPreparation.prepareString(report.getReportNumber()) + "';");
                if (rs.next()){
                    report_id = rs.getInt("id");
                }
                id = 0;
                rs = stmt.executeQuery("SELECT MAX(id) as id FROM MagneticReport;");
                if (rs.next()) {
                    id = rs.getInt("id");
                }
                stmt.executeUpdate("INSERT INTO MagneticReport (id, Report_id, poleDistance, MPCarrier, magTech, UVIntensity, distanceOfLight, examinationArea, currentType, Luxmeter,"
                + " testMedium, demagnetization, surfaceTemperature, gaussFieldStrength, surfaceCondition2, identificationOfLightEquip, liftingTest, buttWeld, filletWeld, standardDeviations) VALUES\n"
                + "(" + (id+1) + ", " + report_id + ", '" + DataPreparation.prepareString(report.getPoleDistance()) + "', '" + DataPreparation.prepareString(report.getMpCarrier()) + "', '" + DataPreparation.prepareString(report.getMagTech())
                + "', '" + DataPreparation.prepareString(report.getUvIntensity()) + "', '" + DataPreparation.prepareString(report.getDistanceOfLight()) + "', '" + DataPreparation.prepareString(report.getExaminationArea())
                + "', '" + DataPreparation.prepareString(report.getCurrentType()) + "', '" + DataPreparation.prepareString(report.getLuxmeter()) + "', '" + DataPreparation.prepareString(report.getTestMedium())
                + "', '" + DataPreparation.prepareString(report.getDemagnetization()) + "', '" + DataPreparation.prepareString(report.getSurfaceTemperature()) + "', '" + DataPreparation.prepareString(report.getGaussFieldStrength())
                + "', '" + DataPreparation.prepareString(report.getSurfaceCondition2()) + "', '" + DataPreparation.prepareString(report.getIdentificationOfLightEquip()) + "', '" + DataPreparation.prepareString(report.getLiftingTest())
                + "', " + report.isButtWeld() + ", " + report.isFilletWeld() + ", '" + DataPreparation.prepareString(report.getStandardDeviations()) + "'); ");
                ArrayList<MagneticInspectionResult> results = report.getInspectionResults();
                id = 0;
                for (int i = 0; i < results.size(); i++) {
                    rs = stmt.executeQuery("SELECT MAX(id) as id FROM MagneticResults;");
                    if (rs.next()) {
                        id = rs.getInt("id");
                    }
                    MagneticInspectionResult temp = results.get(i);
                    stmt.executeUpdate("INSERT INTO MagneticResults (id, Report_id, weldPieceNo, testLength, weldingProcess, thickness, diameter, defectType, defectLocation, result) "
                    + "VALUES (" + (id+1+i) + ", " + report_id + ", '" + DataPreparation.prepareString(temp.getWeldPieceNo()) + "', '" + DataPreparation.prepareString(temp.getTestLength()) + "', '" 
                    + DataPreparation.prepareString(temp.getWeldingProcess()) + "', '" + DataPreparation.prepareString(temp.getThickness()) + "', '"
                    + DataPreparation.prepareString(temp.getDiameter()) + "', '" + DataPreparation.prepareString(temp.getDefectType()) + "', '" 
                    + DataPreparation.prepareString(temp.getDefectLocation()) + "', '" + DataPreparation.prepareString(temp.getResult()) + "');");
                }                 
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                System.out.println("insertMagneticReport " + e);
                con = DatabaseManagement.connect();
                deleteReport(report.getCustomer(), report.getReportNumber());
                return -1;
            }
    }
    
    //Einfügen von Bericht (radiographic)
    public static int insertRadiographicReport (RadiographicReport report){
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + DataPreparation.prepareString(report.getCustomer()) + "' AND reportNumber = '" + DataPreparation.prepareString(report.getReportNumber()) + "';");

            if (rs.next() == false) {
                int id = 0;
                rs = stmt.executeQuery("SELECT MAX(id) as id FROM Report;");
                if (rs.next()) {
                    id = rs.getInt("id");
                }
                String reportDate = Common.date_toString(report.getReportDate());
                int operator_id = report.getOperator_id();
                int evaluator_id = report.getEvaluator_id();
                int confirmation_id = report.getConfirmation_id();
                
                stmt.executeUpdate("INSERT INTO Report (id, customer, projectName, inspectionPlace, inspectionClass, evaluationStandard, inspectionProcedure, inspectionScope, drawingNo, "
                        + "surfaceCondition, stageOfExamination, page, reportNumber, reportDate, orderNumber, offerNumber, equipment, heatTreatment, inspectionDates, descriptionOfAttachments,"
                        + " operator_Employee_id, evaluator_Employee_id, confirmation_Employee_id, bottom, type) VALUES\n"
                        + "(" + (id+1) + ", '" + DataPreparation.prepareString(report.getCustomer()) + "', '" + DataPreparation.prepareString(report.getProjectName()) + "', '" 
                        + DataPreparation.prepareString(report.getInspectionPlace()) + "', '" + DataPreparation.prepareString(report.getInspectionClass()) + "', '"
                        + DataPreparation.prepareString(report.getEvaluationStandard()) + "', '" + DataPreparation.prepareString(report.getInspectionProcedure()) + "', '" 
                        + DataPreparation.prepareString(report.getInspectionScope()) + "', '" + DataPreparation.prepareString(report.getDrawingNo()) + "', '"
                        + DataPreparation.prepareString(report.getSurfaceCondition()) + "', '" + DataPreparation.prepareString(report.getStageOfExamination()) + "', '" 
                        + DataPreparation.prepareString(report.getPage()) + "', '" + DataPreparation.prepareString(report.getReportNumber()) + "', '" + DataPreparation.prepareString(reportDate) + "', '"
                        + DataPreparation.prepareString(report.getOrderNumber()) + "', '" + DataPreparation.prepareString(report.getOfferNumber()) + "', '" + DataPreparation.prepareString(report.getEquipment())
                        + "', '" + DataPreparation.prepareString(report.getHeatTreatment()) + "', '" + DataPreparation.prepareString(report.getInspectionDates())
                        + "', '" + DataPreparation.prepareString(report.getDescriptionOfAttachments()) + "', " + operator_id + ", " + evaluator_id + ", " + confirmation_id + ", '"
                        + DataPreparation.prepareString(report.getBottom()) + "', " + DatabaseManagement.getRADIOGRAPHIC_TYPE() + ");");
                
                int report_id = 0;
                rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + DataPreparation.prepareString(report.getCustomer()) + "' AND reportNumber = '" + DataPreparation.prepareString(report.getReportNumber()) + "';");
                if (rs.next()){
                    report_id = rs.getInt("id");
                }
                id = 0;
                rs = stmt.executeQuery("SELECT MAX(id) as id FROM RadiographicReport;");
                if (rs.next()) {
                    id = rs.getInt("id");
                }
                stmt.executeUpdate("INSERT INTO RadiographicReport (id, Report_id, usedDevice, ir192, se75, xRay, focalSpotSize , exposureTime, filmFocusDistance, pbScreens, filters,"
                        + " filmBrand, d4MX125, d5T200, d7AA400, en , astm, sourceSide, filmSide, automatic , manuel , temp , filmQuantity, testArrangements) VALUES "
                        + "(" + (id+1) + ", " + report_id + ", '" + DataPreparation.prepareString(report.getUsedDevice()) + "', " + report.isIr192() + ", " + report.isSe75() + ", " + report.isxRay() + ", '" 
                        + DataPreparation.prepareString(report.getFocalSpotSize()) + "' , '" + DataPreparation.prepareString(report.getExposureTime()) + "', '" + DataPreparation.prepareString(report.getFilmFocusDistance()) 
                        + "', '" + DataPreparation.prepareString(report.getPbScreens()) + "', '" + DataPreparation.prepareString(report.getFilters()) + "', '"
                        + report.getFilmBrand() + "', " + report.isD4MX125() + ", " + report.isD5T200() + ", " + report.isD7AA400() + ", " + report.isEn() + ", " + report.isAstm() 
                        + ", " + report.isSourceSide() + ", " + report.isFilmSide() + ", " + report.isAutomatic() + ", " + report.isManuel() + ", '" + DataPreparation.prepareString(report.getTemp()) + "', '"
                        + report.getFilmQuantityString() + "', '" + report.getTestArrangementsString() + "');");

                ArrayList<RadiographicInspectionResult> results = report.getInspectionResults();
                id = 0;
                for (int i = 0; i < results.size(); i++) {
                    rs = stmt.executeQuery("SELECT MAX(id) as id FROM RadiographicResults;");
                    if (rs.next()) {
                        id = rs.getInt("id");
                    }
                    RadiographicInspectionResult temp = results.get(i);
                    stmt.executeUpdate("INSERT INTO RadiographicResults (id, Report_id, shootingArea, filmNo, materialType, weldingType, welderNr, position, thickness, penetremeter,"
                            + " visibleQ, density, f1012, f1016, f1024, f1036, f1048, f3040, defectLocation, defectType, preEvaluation, finalEvaluation) VALUES "
                            + "(" + (id+1) + ", " + report_id + ", '" + DataPreparation.prepareString(temp.getShootingArea()) + "', '" + DataPreparation.prepareString(temp.getFilmNo()) + "', '" 
                            + DataPreparation.prepareString(temp.getMaterialType()) + "', '" + DataPreparation.prepareString(temp.getWeldingType()) + "', '" 
                            + DataPreparation.prepareString(temp.getWelderNr()) + "', '" + DataPreparation.prepareString(temp.getPosition()) + "', '" + DataPreparation.prepareString(temp.getThickness())
                            + "', '" + DataPreparation.prepareString(temp.getPenetremeter()) + "', '" + DataPreparation.prepareString(temp.getVisibleQ()) + "', '" + DataPreparation.prepareString(temp.getDensity()) 
                            + "', '" + DataPreparation.prepareString(temp.getF1012()) + "', '" + DataPreparation.prepareString(temp.getF1016()) + "', '" + DataPreparation.prepareString(temp.getF1024()) + "', '"
                            + DataPreparation.prepareString(temp.getF1036()) + "', '" + DataPreparation.prepareString(temp.getF1048()) + "', '" + DataPreparation.prepareString(temp.getF3040())
                            + "', '" + DataPreparation.prepareString(temp.getDefectLocation()) + "', '" + DataPreparation.prepareString(temp.getDefectType()) + "', '" + DataPreparation.prepareString(temp.getPreEvaluation())
                            + "', '" + DataPreparation.prepareString(temp.getFinalEvaluation()) + "')");
                }               
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                System.out.println("insertRadiographicReport " + e);
                con = DatabaseManagement.connect();
                deleteReport(report.getCustomer(), report.getReportNumber());
                return -1;
            }
    }
    //Löschen eines Berichtes
    public static int deleteReport (String customer, String reportNo){
        ResultSet rs;
        int report_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + customer + "' AND reportNumber = '" + reportNo + "';");
            if (rs.next()) {
                report_id = rs.getInt("id");

                stmt.executeUpdate("DELETE FROM Report WHERE id = " + report_id + ";");
                stmt.executeUpdate("DELETE FROM MangneticReport WHERE Report_id = " + report_id + ";");
                stmt.executeUpdate("DELETE FROM RadiographicReport WHERE Report_id = " + report_id + ";");
                stmt.executeUpdate("DELETE FROM MangneticResults WHERE Report_id = " + report_id + ";");
                stmt.executeUpdate("DELETE FROM RadiographicResults WHERE Report_id = " + report_id + ";");                
                return 1;
            }
            else {
                return 0;
            }
        }
            catch (SQLException e) {
                System.out.println("deleteReport " + e);
                return -1;
            }
    }
    //Modifikation von Bericht (magnetic)
    public static int updateMagneticReport (MagneticReport report){
        ResultSet rs;
        int report_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + DataPreparation.prepareString(report.getCustomer()) + "' AND reportNumber = '" + DataPreparation.prepareString(report.getReportNumber()) + "';");

            if (rs.next()) {
                report_id = rs.getInt("id");                
                String reportDate = Common.date_toString(report.getReportDate());
                int operator_id = report.getOperator_id();
                int evaluator_id = report.getEvaluator_id();
                int confirmation_id = report.getConfirmation_id();                  
                
                stmt.executeUpdate("UPDATE Report SET customer = '" + DataPreparation.prepareString(report.getCustomer()) + "', projectName = '" + DataPreparation.prepareString(report.getProjectName()) 
                        + "', inspectionPlace = '" + DataPreparation.prepareString(report.getInspectionPlace()) + "', inspectionClass = '" + DataPreparation.prepareString(report.getInspectionClass()) 
                        + "', evaluationStandard = '" + DataPreparation.prepareString(report.getEvaluationStandard()) + "', inspectionProcedure = '" + DataPreparation.prepareString(report.getInspectionProcedure())
                        + "', inspectionScope = '" + DataPreparation.prepareString(report.getInspectionScope()) + "', drawingNo = '" + DataPreparation.prepareString(report.getDrawingNo()) + "', surfaceCondition = '" 
                        + DataPreparation.prepareString(report.getSurfaceCondition()) + "', stageOfExamination = '" + DataPreparation.prepareString(report.getStageOfExamination()) + "', page = '" 
                        + DataPreparation.prepareString(report.getPage()) + "', reportNumber = '" + DataPreparation.prepareString(report.getReportNumber()) + "', reportDate = '" 
                        + reportDate + "', orderNumber = '" + DataPreparation.prepareString(report.getOrderNumber()) + "', offerNumber = '" + DataPreparation.prepareString(report.getOfferNumber())
                        + "', equipment = '" + DataPreparation.prepareString(report.getEquipment()) + "', heatTreatment = '" + DataPreparation.prepareString(report.getHeatTreatment()) + "', inspectionDates = '" 
                        + DataPreparation.prepareString(report.getInspectionDates()) + "', descriptionOfAttachments = '" + DataPreparation.prepareString(report.getDescriptionOfAttachments()) 
                        + "', operator_Employee_id = " + operator_id + ", evaluator_Employee_id = " + evaluator_id + ", confirmation_Employee_id = " + confirmation_id + ", bottom = '" 
                        + DataPreparation.prepareString(report.getBottom()) + "', type = " + DatabaseManagement.getMAGNETIC_TYPE() + " WHERE id = " + report_id + ";");

                stmt.executeUpdate("UPDATE MagneticReport SET poleDistance =  '" + DataPreparation.prepareString(report.getPoleDistance()) + "', MPCarrier = '" + DataPreparation.prepareString(report.getMpCarrier()) + "', magTech = '"
                        + DataPreparation.prepareString(report.getMagTech()) + "', UVIntensity = '" + DataPreparation.prepareString(report.getUvIntensity()) + "', distanceOfLight = '" + DataPreparation.prepareString(report.getDistanceOfLight()) + "', examinationArea = '"
                        + DataPreparation.prepareString(report.getExaminationArea()) + "', currentType = '" + DataPreparation.prepareString(report.getCurrentType()) + "', Luxmeter = '" + DataPreparation.prepareString(report.getLuxmeter())
                        + "', testMedium = '" + DataPreparation.prepareString(report.getTestMedium()) + "', demagnetization = '" + DataPreparation.prepareString(report.getDemagnetization()) + "', surfaceTemperature = '" 
                        + DataPreparation.prepareString(report.getSurfaceTemperature()) + "', gaussFieldStrength = '" + DataPreparation.prepareString(report.getGaussFieldStrength()) + "', surfaceCondition2 = '" 
                        + DataPreparation.prepareString(report.getSurfaceCondition2()) + "', identificationOfLightEquip = '" + DataPreparation.prepareString(report.getIdentificationOfLightEquip()) + "', liftingTest = '" 
                        + DataPreparation.prepareString(report.getLiftingTest()) + "', buttWeld = " + report.isButtWeld() + ", filletWeld = " + report.isButtWeld() + ", standardDeviations = '" 
                        + DataPreparation.prepareString(report.getStandardDeviations()) + "' WHERE Report_id = " + report_id + ";");
                
                stmt.executeUpdate("DELETE FROM MangneticResults WHERE Report_id = " + report_id + ";");
                int id = 0;
                ArrayList<MagneticInspectionResult> results = report.getInspectionResults();
                for (int i = 0; i < results.size(); i++) {
                    rs = stmt.executeQuery("SELECT MAX(id) as id FROM MagneticResults;");
                    if (rs.next()) {
                        id = rs.getInt("id");
                    }
                    MagneticInspectionResult temp = results.get(i);
                    stmt.executeUpdate("INSERT INTO MagneticResults (id, Report_id, weldPieceNo, testLength, weldingProcess, thickness, diameter, defectType, defectLocation, result) "
                    + "VALUES (" + (id+1) + ", " + report_id + ", '" + DataPreparation.prepareString(temp.getWeldPieceNo()) + "', '" + DataPreparation.prepareString(temp.getTestLength()) + "', '" + DataPreparation.prepareString(temp.getWeldingProcess()) 
                            + "', '" + DataPreparation.prepareString(temp.getThickness()) + "', '" + DataPreparation.prepareString(temp.getDiameter()) + "', '" + DataPreparation.prepareString(temp.getDefectType())
                            + "', '" + DataPreparation.prepareString(temp.getDefectLocation()) + "', '" + DataPreparation.prepareString(temp.getResult()) + "');");
                }                 
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                System.out.println("updateMagneticReport " + e);
                return -1;
            }
    }
   //Modifikation von Bericht (radiographic)
    public static int updateRadiographicReport (RadiographicReport report){
        ResultSet rs;
        int report_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + DataPreparation.prepareString(report.getCustomer()) + "' AND reportNumber = '" + DataPreparation.prepareString(report.getReportNumber()) + "';");

            if (rs.next()) {
                report_id = rs.getInt("id");                
                String reportDate = Common.date_toString(report.getReportDate());
                int operator_id = report.getOperator_id();
                int evaluator_id = report.getEvaluator_id();
                int confirmation_id = report.getConfirmation_id();

                stmt.executeUpdate("UPDATE Report SET customer = '" + DataPreparation.prepareString(report.getCustomer()) + "', projectName = '" + DataPreparation.prepareString(report.getProjectName())
                        + "', inspectionPlace = '" + DataPreparation.prepareString(report.getInspectionPlace()) + "', inspectionClass = '" + DataPreparation.prepareString(report.getInspectionClass()) 
                        + "', evaluationStandard = '" + DataPreparation.prepareString(report.getEvaluationStandard()) + "', inspectionProcedure = '" + DataPreparation.prepareString(report.getInspectionProcedure()) 
                        + "', inspectionScope = '" + report.getInspectionScope() + "', drawingNo = '" + report.getDrawingNo() + "', surfaceCondition = '" 
                        + DataPreparation.prepareString(report.getSurfaceCondition()) + "', stageOfExamination = '" + DataPreparation.prepareString(report.getStageOfExamination()) + "', page = '" 
                        + DataPreparation.prepareString(report.getPage()) + "', reportNumber = '" + DataPreparation.prepareString(report.getReportNumber()) + "', reportDate = '" + DataPreparation.prepareString(reportDate)
                        + "', orderNumber = '" + DataPreparation.prepareString(report.getOrderNumber()) + "', offerNumber = '" + DataPreparation.prepareString(report.getOfferNumber())
                        + "', equipment = '" + DataPreparation.prepareString(report.getEquipment()) + "', heatTreatment = '" + DataPreparation.prepareString(report.getHeatTreatment()) + "', inspectionDates = '" 
                        + DataPreparation.prepareString(report.getInspectionDates()) + "', descriptionOfAttachments = '" + DataPreparation.prepareString(report.getDescriptionOfAttachments()) + "', operator_Employee_id = " 
                        + operator_id + ", evaluator_Employee_id = " + evaluator_id + ", confirmation_Employee_id = " + confirmation_id + ", bottom = '" 
                        + DataPreparation.prepareString(report.getBottom()) + "', type = " + DatabaseManagement.getRADIOGRAPHIC_TYPE() + " WHERE id = " + report_id + ";");

                 stmt.executeUpdate("UPDATE RadiographicReport SET usedDevice = '" + DataPreparation.prepareString(report.getUsedDevice()) + "', ir192 = " + report.isIr192() + ", se75 = " + report.isSe75() + ", xRay = " 
                        + report.isxRay() + ", focalSpotSize = '" + DataPreparation.prepareString(report.getFocalSpotSize()) + "', exposureTime = '" + DataPreparation.prepareString(report.getExposureTime())
                        + "', filmFocusDistance = '" + DataPreparation.prepareString(report.getFilmFocusDistance()) + "', pbScreens = '" + DataPreparation.prepareString(report.getPbScreens()) + "', filters = '" 
                        + DataPreparation.prepareString(report.getFilters()) + "', filmBrand = '" + DataPreparation.prepareString(report.getFilmBrand()) + "', d4MX125 = " + report.isD4MX125() + ", d5T200 = " 
                        + report.isD5T200() + ", d7AA400 = " + report.isD7AA400() + ", en = " + report.isEn() + ", astm = " + report.isAstm() + ", sourceSide = " + report.isSourceSide() + ", filmSide = " + report.isFilmSide() 
                        + ", automatic = " + report.isAutomatic() + ", manuel = " + report.isManuel() + ", temp ='" + DataPreparation.prepareString(report.getTemp()) + "', filmQuantity = '"+ report.getFilmQuantityString() 
                        + "', testArrangements = '" + report.getTestArrangementsString() + "' WHERE Report_id = " + report_id + ";");
                stmt.executeUpdate("DELETE FROM RadiographicResults WHERE Report_id = " + report_id + ";");                     
                ArrayList<RadiographicInspectionResult> results = report.getInspectionResults();
                int id = 0;
                for (int i = 0; i < results.size(); i++) {
                    rs = stmt.executeQuery("SELECT MAX(id) as id FROM MagneticResults;");
                    if (rs.next()) {
                        id = rs.getInt("id");
                    }
                    RadiographicInspectionResult temp = results.get(i);
                    stmt.executeUpdate("INSERT INTO RadiographicResults (id, Report_id, shootingArea, filmNo, materialType, weldingType, welderNr, position, thickness, penetremeter,"
                            + " visibleQ, density, f1012, f1016, f1024, f1036, f1048, f3040, defectLocation, defectType, preEvaluation, finalEvaluation) VALUES "
                            + "(" + (id+1) + ", " + report_id + ", '" + DataPreparation.prepareString(temp.getShootingArea()) + "', '" + DataPreparation.prepareString(temp.getFilmNo()) + "', '" 
                            + DataPreparation.prepareString(temp.getMaterialType()) + "', '" + DataPreparation.prepareString(temp.getWeldingType()) + "', '" + DataPreparation.prepareString(temp.getWelderNr())
                            + "', '" + DataPreparation.prepareString(temp.getPosition()) + "', '" + DataPreparation.prepareString(temp.getThickness()) + "', '" + DataPreparation.prepareString(temp.getPenetremeter()) + "', '"
                            + DataPreparation.prepareString(temp.getVisibleQ()) + "', '" + DataPreparation.prepareString(temp.getDensity()) + "', '" + DataPreparation.prepareString(temp.getF1012())
                            + "', '" + DataPreparation.prepareString(temp.getF1016()) + "', '" + DataPreparation.prepareString(temp.getF1024()) + "', '" + DataPreparation.prepareString(temp.getF1036()) + "', '" 
                            + DataPreparation.prepareString(temp.getF1048()) + "', '" + DataPreparation.prepareString(temp.getF3040()) + "', '" + DataPreparation.prepareString(temp.getDefectLocation())
                            + "', '" + DataPreparation.prepareString(temp.getDefectType()) + "', '" + DataPreparation.prepareString(temp.getPreEvaluation()) + "', '" + DataPreparation.prepareString(temp.getFinalEvaluation()) + "')");
                }               
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                System.out.println("updateRadiographicReport " + e);
                return -1;
            }
    }
    //Abfragen von allen Berichten
    public static ResultSet getReports (int limit) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, Customer, reportNumber, type, reportDate FROM Report LIMIT " + limit + ";");
            if (rs.next()) {
                return rs;
            }   
        }
        catch (SQLException e){
            System.out.println("getReports " + e);
            return rs;
        }
        return rs;
    }
    //Abfragen von Reportinformationen
    public static ResultSet[] getReport (int id) {
        ResultSet[] rs = new ResultSet[3];
        try {
            stmt = con.createStatement();            
            rs[0] = stmt.executeQuery("SELECT R.customer, R.projectName, R.inspectionPlace, R.inspectionClass, R.evaluationStandard, R.inspectionProcedure, R.inspectionScope, R.drawingNo," 
                    + " R.surfaceCondition, R.stageOfExamination, R.page, R.reportNumber, R.reportDate, R.orderNumber, R.offerNumber, R.equipment, R.heatTreatment, R.inspectionDates,"
                    + " R.descriptionOfAttachments, R.operator_Employee_id, R.evaluator_Employee_id, R.confirmation_Employee_id,"
                    + " R.type FROM Report R JOIN MagneticReport MR ON MR.Report_id = R.id WHERE R.id = " + id + ";");
            rs[1] = stmt.executeQuery("SELECT weldPieceNo, testLength, weldingProcess, thickness, diameter, defectType, defectLocation, result FROM MagneticResults "
                    + "WHERE Report_id = " + id + ";");
            rs[2] = stmt.executeQuery("SELECT Report_id, shootingArea, filmNo, materialType, weldingType, welderNr, position, thickness, penetremeter,"
                    + " visibleQ, density, f1012, f1016, f1024, f1036, f1048, f3040, defectType, preEvaluation, finalEvaluation FROM RadiographicResults "
                    + "WHERE Report_id = " + id + ";");
        }
        catch (SQLException e){
            System.out.println("getReport " + e);
            return rs;
        }
        return rs;
    }
    
}
