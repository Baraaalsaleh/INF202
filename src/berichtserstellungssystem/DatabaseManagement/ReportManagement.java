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
        int step = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + Security.prepareString(report.getCustomer()) + "' AND reportNumber = '" + Security.prepareString(report.getReportNumber()) + "';");
            step = 1;
            if (!rs.next()) {
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
                        + "(" + (id+1) + ", '" + Security.prepareString(report.getCustomer()) + "', '" + Security.prepareString(report.getProjectName()) + "', '" 
                        + Security.prepareString(report.getInspectionPlace()) + "', '" + Security.prepareString(report.getInspectionClass()) + "', '"
                        + Security.prepareString(report.getEvaluationStandard()) + "', '" + Security.prepareString(report.getInspectionProcedure()) + "', '" 
                        + Security.prepareString(report.getInspectionScope()) + "', '" + Security.prepareString(report.getDrawingNo()) + "', '"
                        + Security.prepareString(report.getSurfaceCondition()) + "', '" + Security.prepareString(report.getStageOfExamination()) + "', '" 
                        + Security.prepareString(report.getPage()) + "', '" + Security.prepareString(report.getReportNumber()) + "', '" + Security.prepareString(reportDate) + "', '"
                        + Security.prepareString(report.getOrderNumber()) + "', '" + Security.prepareString(report.getOfferNumber()) + "', '" + Security.prepareString(report.getEquipment())
                        + "', '" + Security.prepareString(report.getHeatTreatment()) + "', '" + Security.prepareString(report.getInspectionDates())
                        + "', '" + Security.prepareString(report.getDescriptionOfAttachments()) + "', " + operator_id + ", " + evaluator_id + ", " + confirmation_id + ", '"
                        + Security.prepareString(report.getBottom()) + "', " + DatabaseManagement.getMAGNETIC_TYPE() + ");");
                
                step = 2;
                
                int report_id = 0;
                rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + Security.prepareString(report.getCustomer()) + "' AND reportNumber = '" + Security.prepareString(report.getReportNumber()) + "';");
                if (rs.next()){
                    report_id = rs.getInt("id");
                }
                step = 3;
                id = 0;
                rs = stmt.executeQuery("SELECT MAX(id) as id FROM MagneticReport;");
                if (rs.next()) {
                    id = rs.getInt("id");
                }
                step = 4;
                stmt.executeUpdate("INSERT INTO MagneticReport (id, Report_id, poleDistance, MPCarrier, magTech, UVIntensity, distanceOfLight, examinationArea, currentType, Luxmeter,"
                + " testMedium, demagnetization, surfaceTemperature, gaussFieldStrength, surfaceCondition2, identificationOfLightEquip, liftingTest, buttWeld, filletWeld, standardDeviations) VALUES\n"
                + "(" + (id+1) + ", " + report_id + ", '" + Security.prepareString(report.getPoleDistance()) + "', '" + Security.prepareString(report.getMpCarrier()) + "', '" + Security.prepareString(report.getMagTech())
                + "', '" + Security.prepareString(report.getUvIntensity()) + "', '" + Security.prepareString(report.getDistanceOfLight()) + "', '" + Security.prepareString(report.getExaminationArea())
                + "', '" + Security.prepareString(report.getCurrentType()) + "', '" + Security.prepareString(report.getLuxmeter()) + "', '" + Security.prepareString(report.getTestMedium())
                + "', '" + Security.prepareString(report.getDemagnetization()) + "', '" + Security.prepareString(report.getSurfaceTemperature()) + "', '" + Security.prepareString(report.getGaussFieldStrength())
                + "', '" + Security.prepareString(report.getSurfaceCondition2()) + "', '" + Security.prepareString(report.getIdentificationOfLightEquip()) + "', '" + Security.prepareString(report.getLiftingTest())
                + "', " + report.isButtWeld() + ", " + report.isFilletWeld() + ", '" + Security.prepareString(report.getStandardDeviations()) + "'); ");
                
                step = 5;
                ArrayList<MagneticInspectionResult> results = report.getInspectionResults();
                id = 0;
                for (int i = 0; i < results.size(); i++) {
                    rs = stmt.executeQuery("SELECT MAX(id) as id FROM MagneticResults;");
                    if (rs.next()) {
                        id = rs.getInt("id");
                    }
                    MagneticInspectionResult temp = results.get(i);
                    stmt.executeUpdate("INSERT INTO MagneticResults (id, Report_id, weldPieceNo, testLength, weldingProcess, thickness, diameter, defectType, defectLocation, result) "
                    + "VALUES (" + (id+1) + ", " + report_id + ", '" + Security.prepareString(temp.getWeldPieceNo()) + "', '" + Security.prepareString(temp.getTestLength()) + "', '" 
                    + Security.prepareString(temp.getWeldingProcess()) + "', '" + Security.prepareString(temp.getThickness()) + "', '"
                    + Security.prepareString(temp.getDiameter()) + "', '" + Security.prepareString(temp.getDefectType()) + "', '" 
                    + Security.prepareString(temp.getDefectLocation()) + "', '" + Security.prepareString(temp.getResult()) + "');");
                }
                step = 6;
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                System.out.println("insertMagneticReport step " + step + "    " + e);
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
            rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + Security.prepareString(report.getCustomer()) + "' AND reportNumber = '" + Security.prepareString(report.getReportNumber()) + "';");

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
                        + "(" + (id+1) + ", '" + Security.prepareString(report.getCustomer()) + "', '" + Security.prepareString(report.getProjectName()) + "', '" 
                        + Security.prepareString(report.getInspectionPlace()) + "', '" + Security.prepareString(report.getInspectionClass()) + "', '"
                        + Security.prepareString(report.getEvaluationStandard()) + "', '" + Security.prepareString(report.getInspectionProcedure()) + "', '" 
                        + Security.prepareString(report.getInspectionScope()) + "', '" + Security.prepareString(report.getDrawingNo()) + "', '"
                        + Security.prepareString(report.getSurfaceCondition()) + "', '" + Security.prepareString(report.getStageOfExamination()) + "', '" 
                        + Security.prepareString(report.getPage()) + "', '" + Security.prepareString(report.getReportNumber()) + "', '" + Security.prepareString(reportDate) + "', '"
                        + Security.prepareString(report.getOrderNumber()) + "', '" + Security.prepareString(report.getOfferNumber()) + "', '" + Security.prepareString(report.getEquipment())
                        + "', '" + Security.prepareString(report.getHeatTreatment()) + "', '" + Security.prepareString(report.getInspectionDates())
                        + "', '" + Security.prepareString(report.getDescriptionOfAttachments()) + "', " + operator_id + ", " + evaluator_id + ", " + confirmation_id + ", '"
                        + Security.prepareString(report.getBottom()) + "', " + DatabaseManagement.getRADIOGRAPHIC_TYPE() + ");");
                
                int report_id = 0;
                rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + Security.prepareString(report.getCustomer()) + "' AND reportNumber = '" + Security.prepareString(report.getReportNumber()) + "';");
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
                        + "(" + (id+1) + ", " + report_id + ", '" + Security.prepareString(report.getUsedDevice()) + "', " + report.isIr192() + ", " + report.isSe75() + ", " + report.isxRay() + ", '" 
                        + Security.prepareString(report.getFocalSpotSize()) + "' , '" + Security.prepareString(report.getExposureTime()) + "', '" + Security.prepareString(report.getFilmFocusDistance()) 
                        + "', '" + Security.prepareString(report.getPbScreens()) + "', '" + Security.prepareString(report.getFilters()) + "', '"
                        + report.getFilmBrand() + "', " + report.isD4MX125() + ", " + report.isD5T200() + ", " + report.isD7AA400() + ", " + report.isEn() + ", " + report.isAstm() 
                        + ", " + report.isSourceSide() + ", " + report.isFilmSide() + ", " + report.isAutomatic() + ", " + report.isManuel() + ", '" + Security.prepareString(report.getTemp()) + "', '"
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
                            + "(" + (id+1) + ", " + report_id + ", '" + Security.prepareString(temp.getShootingArea()) + "', '" + Security.prepareString(temp.getFilmNo()) + "', '" 
                            + Security.prepareString(temp.getMaterialType()) + "', '" + Security.prepareString(temp.getWeldingType()) + "', '" 
                            + Security.prepareString(temp.getWelderNr()) + "', '" + Security.prepareString(temp.getPosition()) + "', '" + Security.prepareString(temp.getThickness())
                            + "', '" + Security.prepareString(temp.getPenetremeter()) + "', '" + Security.prepareString(temp.getVisibleQ()) + "', '" + Security.prepareString(temp.getDensity()) 
                            + "', '" + Security.prepareString(temp.getF1012()) + "', '" + Security.prepareString(temp.getF1016()) + "', '" + Security.prepareString(temp.getF1024()) + "', '"
                            + Security.prepareString(temp.getF1036()) + "', '" + Security.prepareString(temp.getF1048()) + "', '" + Security.prepareString(temp.getF3040())
                            + "', '" + Security.prepareString(temp.getDefectLocation()) + "', '" + Security.prepareString(temp.getDefectType()) + "', '" + Security.prepareString(temp.getPreEvaluation())
                            + "', '" + Security.prepareString(temp.getFinalEvaluation()) + "')");
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
            }
            else {
                return 0;
            }
            stmt.executeUpdate("DELETE FROM Report WHERE id = " + report_id + ";");
            stmt.executeUpdate("DELETE FROM MagneticReport WHERE Report_id = " + report_id + ";");
            stmt.executeUpdate("DELETE FROM RadiographicReport WHERE Report_id = " + report_id + ";");
            stmt.executeUpdate("DELETE FROM MagneticResults WHERE Report_id = " + report_id + ";");
            stmt.executeUpdate("DELETE FROM RadiographicResults WHERE Report_id = " + report_id + ";");                
            return 1;
            
        }
            catch (SQLException e) {
                System.out.println("deleteReport " + e);
                return -1;
            }
    }
    //Modifikation von Bericht (magnetic)
    
    public static int updateMagneticReport (MagneticReport report, int report_id){
        ResultSet rs;
        //int report_id = 0;
        try {
            stmt = con.createStatement();
            String reportDate = Common.date_toString(report.getReportDate());
            int operator_id = report.getOperator_id();
            int evaluator_id = report.getEvaluator_id();
            int confirmation_id = report.getConfirmation_id();                  
                
            stmt.executeUpdate("UPDATE Report SET customer = '" + Security.prepareString(report.getCustomer()) + "', projectName = '" + Security.prepareString(report.getProjectName()) 
                        + "', inspectionPlace = '" + Security.prepareString(report.getInspectionPlace()) + "', inspectionClass = '" + Security.prepareString(report.getInspectionClass()) 
                        + "', evaluationStandard = '" + Security.prepareString(report.getEvaluationStandard()) + "', inspectionProcedure = '" + Security.prepareString(report.getInspectionProcedure())
                        + "', inspectionScope = '" + Security.prepareString(report.getInspectionScope()) + "', drawingNo = '" + Security.prepareString(report.getDrawingNo()) + "', surfaceCondition = '" 
                        + Security.prepareString(report.getSurfaceCondition()) + "', stageOfExamination = '" + Security.prepareString(report.getStageOfExamination()) + "', page = '" 
                        + Security.prepareString(report.getPage()) + "', reportNumber = '" + Security.prepareString(report.getReportNumber()) + "', reportDate = '" 
                        + reportDate + "', orderNumber = '" + Security.prepareString(report.getOrderNumber()) + "', offerNumber = '" + Security.prepareString(report.getOfferNumber())
                        + "', equipment = '" + Security.prepareString(report.getEquipment()) + "', heatTreatment = '" + Security.prepareString(report.getHeatTreatment()) + "', inspectionDates = '" 
                        + Security.prepareString(report.getInspectionDates()) + "', descriptionOfAttachments = '" + Security.prepareString(report.getDescriptionOfAttachments()) 
                        + "', operator_Employee_id = " + operator_id + ", evaluator_Employee_id = " + evaluator_id + ", confirmation_Employee_id = " + confirmation_id + ", bottom = '" 
                        + Security.prepareString(report.getBottom()) + "', type = " + DatabaseManagement.getMAGNETIC_TYPE() + " WHERE id = " + report_id + ";");

            stmt.executeUpdate("UPDATE MagneticReport SET poleDistance =  '" + Security.prepareString(report.getPoleDistance()) + "', MPCarrier = '" + Security.prepareString(report.getMpCarrier()) + "', magTech = '"
                        + Security.prepareString(report.getMagTech()) + "', UVIntensity = '" + Security.prepareString(report.getUvIntensity()) + "', distanceOfLight = '" + Security.prepareString(report.getDistanceOfLight()) + "', examinationArea = '"
                        + Security.prepareString(report.getExaminationArea()) + "', currentType = '" + Security.prepareString(report.getCurrentType()) + "', Luxmeter = '" + Security.prepareString(report.getLuxmeter())
                        + "', testMedium = '" + Security.prepareString(report.getTestMedium()) + "', demagnetization = '" + Security.prepareString(report.getDemagnetization()) + "', surfaceTemperature = '" 
                        + Security.prepareString(report.getSurfaceTemperature()) + "', gaussFieldStrength = '" + Security.prepareString(report.getGaussFieldStrength()) + "', surfaceCondition2 = '" 
                        + Security.prepareString(report.getSurfaceCondition2()) + "', identificationOfLightEquip = '" + Security.prepareString(report.getIdentificationOfLightEquip()) + "', liftingTest = '" 
                        + Security.prepareString(report.getLiftingTest()) + "', buttWeld = " + report.isButtWeld() + ", filletWeld = " + report.isFilletWeld()+ ", standardDeviations = '" 
                        + Security.prepareString(report.getStandardDeviations()) + "' WHERE Report_id = " + report_id + ";");
                
            stmt.executeUpdate("DELETE FROM MagneticResults WHERE Report_id = " + report_id + ";");
            int id = 0;
            ArrayList<MagneticInspectionResult> results = report.getInspectionResults();
            for (int i = 0; i < results.size(); i++) {
                rs = stmt.executeQuery("SELECT MAX(id) as id FROM MagneticResults;");
                if (rs.next()) {
                    id = rs.getInt("id");
                }
                MagneticInspectionResult temp = results.get(i);
                stmt.executeUpdate("INSERT INTO MagneticResults (id, Report_id, weldPieceNo, testLength, weldingProcess, thickness, diameter, defectType, defectLocation, result) "
                    + "VALUES (" + (id+1) + ", " + report_id + ", '" + Security.prepareString(temp.getWeldPieceNo()) + "', '" + Security.prepareString(temp.getTestLength()) + "', '" + Security.prepareString(temp.getWeldingProcess()) 
                    + "', '" + Security.prepareString(temp.getThickness()) + "', '" + Security.prepareString(temp.getDiameter()) + "', '" + Security.prepareString(temp.getDefectType())
                    + "', '" + Security.prepareString(temp.getDefectLocation()) + "', '" + Security.prepareString(temp.getResult()) + "');");
            }                
            return 1;
            
            } catch (SQLException e) {
                System.out.println("updateMagneticReport " + e);
                return -1;
            }
    }
   //Modifikation von Bericht (radiographic)
    public static int updateRadiographicReport (RadiographicReport report, int report_id){
        ResultSet rs;
        try {
            stmt = con.createStatement();

            String reportDate = Common.date_toString(report.getReportDate());
            int operator_id = report.getOperator_id();
            int evaluator_id = report.getEvaluator_id();
            int confirmation_id = report.getConfirmation_id();

            stmt.executeUpdate("UPDATE Report SET customer = '" + Security.prepareString(report.getCustomer()) + "', projectName = '" + Security.prepareString(report.getProjectName())
                        + "', inspectionPlace = '" + Security.prepareString(report.getInspectionPlace()) + "', inspectionClass = '" + Security.prepareString(report.getInspectionClass()) 
                        + "', evaluationStandard = '" + Security.prepareString(report.getEvaluationStandard()) + "', inspectionProcedure = '" + Security.prepareString(report.getInspectionProcedure()) 
                        + "', inspectionScope = '" + report.getInspectionScope() + "', drawingNo = '" + report.getDrawingNo() + "', surfaceCondition = '" 
                        + Security.prepareString(report.getSurfaceCondition()) + "', stageOfExamination = '" + Security.prepareString(report.getStageOfExamination()) + "', page = '" 
                        + Security.prepareString(report.getPage()) + "', reportNumber = '" + Security.prepareString(report.getReportNumber()) + "', reportDate = '" + Security.prepareString(reportDate)
                        + "', orderNumber = '" + Security.prepareString(report.getOrderNumber()) + "', offerNumber = '" + Security.prepareString(report.getOfferNumber())
                        + "', equipment = '" + Security.prepareString(report.getEquipment()) + "', heatTreatment = '" + Security.prepareString(report.getHeatTreatment()) + "', inspectionDates = '" 
                        + Security.prepareString(report.getInspectionDates()) + "', descriptionOfAttachments = '" + Security.prepareString(report.getDescriptionOfAttachments()) + "', operator_Employee_id = " 
                        + operator_id + ", evaluator_Employee_id = " + evaluator_id + ", confirmation_Employee_id = " + confirmation_id + ", bottom = '" 
                        + Security.prepareString(report.getBottom()) + "', type = " + DatabaseManagement.getRADIOGRAPHIC_TYPE() + " WHERE id = " + report_id + ";");

            stmt.executeUpdate("UPDATE RadiographicReport SET usedDevice = '" + Security.prepareString(report.getUsedDevice()) + "', ir192 = " + report.isIr192() + ", se75 = " + report.isSe75() + ", xRay = " 
                        + report.isxRay() + ", focalSpotSize = '" + Security.prepareString(report.getFocalSpotSize()) + "', exposureTime = '" + Security.prepareString(report.getExposureTime())
                        + "', filmFocusDistance = '" + Security.prepareString(report.getFilmFocusDistance()) + "', pbScreens = '" + Security.prepareString(report.getPbScreens()) + "', filters = '" 
                        + Security.prepareString(report.getFilters()) + "', filmBrand = '" + Security.prepareString(report.getFilmBrand()) + "', d4MX125 = " + report.isD4MX125() + ", d5T200 = " 
                        + report.isD5T200() + ", d7AA400 = " + report.isD7AA400() + ", en = " + report.isEn() + ", astm = " + report.isAstm() + ", sourceSide = " + report.isSourceSide() + ", filmSide = " + report.isFilmSide() 
                        + ", automatic = " + report.isAutomatic() + ", manuel = " + report.isManuel() + ", temp ='" + Security.prepareString(report.getTemp()) + "', filmQuantity = '"+ report.getFilmQuantityString() 
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
                            + "(" + (id+1+i) + ", " + report_id + ", '" + Security.prepareString(temp.getShootingArea()) + "', '" + Security.prepareString(temp.getFilmNo()) + "', '" 
                            + Security.prepareString(temp.getMaterialType()) + "', '" + Security.prepareString(temp.getWeldingType()) + "', '" + Security.prepareString(temp.getWelderNr())
                            + "', '" + Security.prepareString(temp.getPosition()) + "', '" + Security.prepareString(temp.getThickness()) + "', '" + Security.prepareString(temp.getPenetremeter()) + "', '"
                            + Security.prepareString(temp.getVisibleQ()) + "', '" + Security.prepareString(temp.getDensity()) + "', '" + Security.prepareString(temp.getF1012())
                            + "', '" + Security.prepareString(temp.getF1016()) + "', '" + Security.prepareString(temp.getF1024()) + "', '" + Security.prepareString(temp.getF1036()) + "', '" 
                            + Security.prepareString(temp.getF1048()) + "', '" + Security.prepareString(temp.getF3040()) + "', '" + Security.prepareString(temp.getDefectLocation())
                            + "', '" + Security.prepareString(temp.getDefectType()) + "', '" + Security.prepareString(temp.getPreEvaluation()) + "', '" + Security.prepareString(temp.getFinalEvaluation()) + "')");
                }               
                return 1;
                
            } catch (SQLException e) {
                System.out.println("updateRadiographicReport " + e);
                return -1;
            }
    }
    //Abfragen von allen Berichten
    
    public static ArrayList<Report> reports (int start, int limit) {
        ArrayList<Report> res = new ArrayList();
        Report temp;
        ResultSet rs;
        int count = 0;
        int i = start;
        while (count < limit) {
            try {
                rs = getReports(i);
                if (rs.next()) {
                    temp = new Report();
                    temp.setCustomer(rs.getString("Customer"));
                    temp.setReportNumber(rs.getString("reportNumber"));
                    temp.setReportDate(rs.getDate("reportDate"));
                    temp.setType(rs.getInt("type"));
                    i += (rs.getInt("id")-i);
                    res.add(temp);
                    count++;
                }
                else {
                    break;
                }
            } catch (SQLException ex) {
                System.out.println("employees " + ex);
            }
        }
        return res;
    }
    public static ResultSet getReports (int biggerThan) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, Customer, reportNumber, type, reportDate FROM Report WHERE id > " + biggerThan + " LIMIT 1;");
            return rs;
        }
        catch (SQLException e){
            System.out.println("getReports " + e);
            return rs;
        }
    }
    //Abfragen von Reportinformationen Hiiieirr allle eeeee Probleeeemeeee
    public static ResultSet getMagneticResult(int id, int biggerThan) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM MagneticResults WHERE Report_id = " + id + " AND id > " + biggerThan + ";");
            return rs;
        } catch (SQLException e) {
            System.out.println("getMagneticResult " + e);
        }
        return rs;
    }
    
    public static ResultSet getRadiographicResult(long id, int biggerThan) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM RadiographicResults WHERE Report_id = " + id + " AND id > " + biggerThan + ";");
            return rs;
        } catch (SQLException e) {
            System.out.println("getRadiographicResult " + e);
        }
        return rs;
    }
    
    public static ArrayList<MagneticInspectionResult> magneticResults(int id) {
        ArrayList<MagneticInspectionResult> results = new ArrayList();
        ResultSet rs;
        int biggerThan = 0;
        int count = 1;
        try {
            while(true) {
                rs = getMagneticResult(id, biggerThan);
                if (rs.next()) {
                    MagneticInspectionResult temp = new MagneticInspectionResult(rs, count);
                    results.add(temp);
                    biggerThan = rs.getInt("id");
                    count++;
                }
                else {
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("magneticResults " + e);
        }
        return results;
    }
    
    public static ArrayList<RadiographicInspectionResult> radiographicResults(long id) {
        ArrayList<RadiographicInspectionResult> results = new ArrayList();
        ResultSet rs;
        int biggerThan = 0;
        int count = 1;
        try {
            while(true) {
                rs = getRadiographicResult(id, biggerThan);
                if (rs.next()) {
                    RadiographicInspectionResult temp = new RadiographicInspectionResult(rs, count);
                    results.add(temp);
                    biggerThan = rs.getInt("id");
                    count++;
                }
                else {
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("radiographicResults " + e);
        }
        return results;
    }
    
    public static MagneticReport getReport (int id) {
        ResultSet rs;
        MagneticReport report = null;
        try {
            stmt = con.createStatement();            
            rs = stmt.executeQuery("SELECT * FROM Report R JOIN MagneticReport MR ON MR.Report_id = R.id WHERE R.id = " + id + ";");
            if (rs.next()) {
                report = new MagneticReport(rs);
                ArrayList<MagneticInspectionResult> results = magneticResults(id);
                report.setInspectionResults(results);
            }
            else {
                return null;
            }
            
            /*
            rs = stmt.executeQuery("SELECT Report_id, shootingArea, filmNo, materialType, weldingType, welderNr, position, thickness, penetremeter,"
                    + " visibleQ, density, f1012, f1016, f1024, f1036, f1048, f3040, defectType, preEvaluation, finalEvaluation FROM RadiographicResults "
                    + "WHERE Report_id = " + id + ";");*/
        }
        catch (SQLException e){
            System.out.println("getReport " + e);
        }
        return report;
    }
    
    public static RadiographicReport getReport (long id) {
        ResultSet rs;
        RadiographicReport report = null;
        try {
            stmt = con.createStatement();            
            rs = stmt.executeQuery("SELECT * FROM Report R JOIN RadiographicReport RR ON RR.Report_id = R.id WHERE R.id = " + id + ";");
            if (rs.next()) {
                report = new RadiographicReport(rs);
                ArrayList<RadiographicInspectionResult> results = radiographicResults(id);
                report.setInspectionResults(results);
            }
            else {
                return null;
            }
        }
        catch (SQLException e){
            System.out.println("getReport " + e);
        }
        return report;
    }
    
    public static boolean reportNumberAccepted(String reportNo, String customerName) {
        boolean res = true;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Report WHERE Customer = '" + customerName + "' AND reportNumber = '" + reportNo + "' LIMIT 1;");
            if (rs.next()) {
                System.out.println(rs.getInt("id"));
                res = false;
            }
        } catch (SQLException ex) {
            System.out.println("reportNumberAccepted " + ex);
            res = false;
        }
        System.out.println(res);
        return res;
    }
    
    public static int getReportId (String cus, String num) {
        ResultSet rs = null;
        int id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Report WHERE Customer = '" + cus + "' AND reportNumber = '" + num + "';");
            if (rs.next()) {
                id = rs.getInt("id");
            }
        }
        catch (SQLException e){
            System.out.println("getReports " + e);
        }
        return id;
    }
    
}
