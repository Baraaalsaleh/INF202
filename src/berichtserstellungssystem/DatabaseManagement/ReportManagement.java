package berichtserstellungssystem.DatabaseManagement;

import berichtserstellungssystem.Report.*;
import berichtserstellungssystem.Common;
import berichtserstellungssystem.DatabaseManagement.DatabaseManagement;
import berichtserstellungssystem.Report.*;
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
    public int insertMagneticReport (MagneticReport report){
        Connection con = this.connect();
        Common common = new Common();
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + report.getCustomer() + "' AND reportNumber = " + report.getReportNumber() + ";");

            if (rs.next() == false) {
                String reportDate = common.date_toString(report.getReportDate());
                int operator_id = 0;
                int evaluator_id = 0;
                int confirmation_id = 0;
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + report.getOperator().getPersonalNr() + ";");
                if (rs.next()){
                    operator_id = rs.getInt("id");
                }                
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + report.getEvaluator().getPersonalNr() + ";");
                if (rs.next()){
                    evaluator_id = rs.getInt("id");
                }                
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + report.getConfirmation().getPersonalNr() + ";");
                if (rs.next()){
                    confirmation_id = rs.getInt("id");
                }                
                
                
                stmt.executeUpdate("INSERT INTO Report (customer, projectName, inspectionPlace, inspectionClass, evaluationStandard, inspectionProcedure, inspectionScope, drawingNo, "
                        + "surfaceCondition, stageOfExamination, page, reportNumber, reportDate, orderNumber, offerNumber, equipment, heatTreatment, inspectionDates, descriptionOfAttachments,"
                        + " operator_Employee_id, operatorSignature, evaluator_Employee_id, evaluatorSignature, confirmation_Employee_id, confirmationSignature, customerName, customerLevel, type) VALUES\n"
                        + "('" + report.getCustomer() + "', '" + report.getProjectName() + "', '" + report.getInspectionPlace() + "', '" + report.getInspectionClass() + "', '"
                        + report.getEvaluationStandard() + "', '" + report.getInspectionProcedure() + "', '" + report.getInspectionScope() + "', '" + report.getDrawingNo() + "', '"
                        + report.getSurfaceCondition() + "', '" + report.getStageOfExaminaiton() + "', '" + report.getPage() + "', '" + report.getReportNumber() + "', '" + reportDate + "', '"
                        + report.getOrderNumber() + "', '" + report.getOfferNumber() + "', '" + report.getEquipment() + "', '" + report.getHeatTreatment() + "', '" + report.getInspectionDates()
                        + "', '" + report.getDescriptionOfAttachments() + "', " + operator_id + ", false, " + evaluator_id + ", false, " + confirmation_id + ", false, '"
                        + report.getCustomerName() + "', '" + report.getCustomerLevel() + "', " + this.getMagnetic_type() + ");");
                
                int report_id = 0;
                rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + report.getCustomer() + "' AND reportNumber = " + report.getReportNumber() + ";");
                if (rs.next()){
                    report_id = rs.getInt("id");
                }                   
                stmt.executeUpdate("INSERT INTO MagneticReport (Report_id, poleDistance, MPCarrier, magTech, UVIntensity, distanceOfLight, examinationArea, currentType, Luxmeter,"
                + " testMedium, demagnetization, surfaceTemperature, gaussFieldStrength, identificationOfLightEquip, liftingTest, buttWeld, filletWeld, standardDeviations) VALUES\n"
                + "(" + report_id + ", '" + report.getPoleDistance() + "', '" + report.getMPCarrier() + "', '" + report.getMagTech() + "', '" + report.getUVIntensity() + "', '"
                + report.getDistanceOfLight() + "', '" + report.getExamintionArea() + "', '" + report.getCurrentType() + "', '" + report.getLuxmeter() + "', '" + report.getTestMedium()
                + "', '" + report.getDemagnetization() + "', '" + report.getSurfaceTemperature() + "', '" + report.getGaussFieldStrength() + "', '" + report.getIdentificationOfLightEquip()
                + "', '" + report.getLiftingTest() + "', " + report.isButtWeld() + ", " + report.isFilletWeld() + ", '" + report.getStandardDeviations() + "'); ");
                ArrayList<MagneticInspectionResult> results = report.getInspectionResults();
                for (int i = 0; i < results.size(); i++) {
                    MagneticInspectionResult temp = results.get(i);
                    stmt.executeUpdate("INSERT INTO MagneticResults (Report_id, weldPieceNo, testLength, weldingProcess, thickness, diameter, defectType, defectLocation, result) "
                    + "VALUES (" + report_id + ", '" + temp.getWeldPieceNo() + "', '" + temp.getTestLength() + "', '" + temp.getWeldingProcess() + "', '" + temp.getThickness() + "', '"
                    + temp.getDiameter() + "', '" + temp.getDefectType() + "', '" + temp.getDefectLocation() + "', '" + temp.getResult() + "');");
                }                 
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                return -1;
            }
    }
    
    //Einfügen von Bericht (radiographic)
    public int insertRadiographicReport (RadiographicReport report){
        Connection con = this.connect();
        Common common = new Common();
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + report.getCustomer() + "' AND reportNumber = " + report.getReportNumber() + ";");

            if (rs.next() == false) {
                String reportDate = common.date_toString(report.getReportDate());
                int operator_id = 0;
                int evaluator_id = 0;
                int confirmation_id = 0;
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + report.getOperator().getPersonalNr() + ";");
                if (rs.next()){
                    operator_id = rs.getInt("id");
                }                
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + report.getEvaluator().getPersonalNr() + ";");
                if (rs.next()){
                    evaluator_id = rs.getInt("id");
                }                
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + report.getConfirmation().getPersonalNr() + ";");
                if (rs.next()){
                    confirmation_id = rs.getInt("id");
                }                
                
                
                stmt.executeUpdate("INSERT INTO Report (customer, projectName, inspectionPlace, inspectionClass, evaluationStandard, inspectionProcedure, inspectionScope, drawingNo, "
                        + "surfaceCondition, stageOfExamination, page, reportNumber, reportDate, orderNumber, offerNumber, equipment, heatTreatment, inspectionDates, descriptionOfAttachments,"
                        + " operator_Employee_id, operatorSignature, evaluator_Employee_id, evaluatorSignature, confirmation_Employee_id, confirmationSignature, customerName, customerLevel, type) VALUES\n"
                        + "('" + report.getCustomer() + "', '" + report.getProjectName() + "', '" + report.getInspectionPlace() + "', '" + report.getInspectionClass() + "', '"
                        + report.getEvaluationStandard() + "', '" + report.getInspectionProcedure() + "', '" + report.getInspectionScope() + "', '" + report.getDrawingNo() + "', '"
                        + report.getSurfaceCondition() + "', '" + report.getStageOfExaminaiton() + "', '" + report.getPage() + "', '" + report.getReportNumber() + "', '" + reportDate + "', '"
                        + report.getOrderNumber() + "', '" + report.getOfferNumber() + "', '" + report.getEquipment() + "', '" + report.getHeatTreatment() + "', '" + report.getInspectionDates()
                        + "', '" + report.getDescriptionOfAttachments() + "', " + operator_id + ", false, " + evaluator_id + ", false, " + confirmation_id + ", false, '"
                        + report.getCustomerName() + "', '" + report.getCustomerLevel() + "', " + this.getRadiographic_type() + ");");
                
                int report_id = 0;
                rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + report.getCustomer() + "' AND reportNumber = " + report.getReportNumber() + ";");
                if (rs.next()){
                    report_id = rs.getInt("id");
                }                   
                stmt.executeUpdate("INSERT INTO RadiographicReport (Report_id, usedDevice, ir192, se75, xRay, focalSpotSize , exposureTime, filmFocusDistance, pbScreens, filters,"
                        + " filmBrand, d4MX125, d5T200, d7AA400, en , astm, sourceSide, filmSide, automatic , manuel , temp , filmQuantity, testArrangements) VALUES "
                        + "(" + report_id + ", '" + report.getUsedDevice() + "', " + report.isIr192() + ", " + report.isSe75() + ", " + report.isxRay() + ", '" + report.getFocalSpotSize()
                        + "' , '" + report.getExposureTime() + "', '" + report.getFilmFocusDistance() + "', '" + report.getPbScreens() + "', '" + report.getFilters() + "', '"
                        + report.getFilmBrand() + "', " + report.isD4MX125() + ", " + report.isD5T200() + ", " + report.isD7AA400() + ", " + report.isEn() + ", " + report.isAstm() 
                        + ", " + report.isSourceSide() + ", " + report.isFilmSide() + ", " + report.isAutomatic() + ", " + report.isManuel() + ", '" + report.getTemp() + "', '"
                        + report.getFilmQuantity() + "', '" + report.getTestArrangements() + "');");
                
                ArrayList<RadiographicInspectionResult> results = report.getInspectionResults();
                for (int i = 0; i < results.size(); i++) {
                    RadiographicInspectionResult temp = results.get(i);
                    stmt.executeUpdate("INSERT INTO RadiographicResults (Report_id, shootingArea, filmNo, materialType, weldingType, welderNr, position, thickness, penetremeter,"
                            + " visibleQ, density, f1012, f1016, f1024, f1036, f1048, f3040, defectType, preEvaluation, finalEvaluation) VALUES "
                            + "(" + report_id + ", '" + temp.getShootingArea() + "', '" + temp.getFilmNo() + "', '" + temp.getMaterialType() + "', '" + temp.getWeldingType()
                            + "', '" + temp.getWelderNr() + "', '" + temp.getPosition() + "', '" + temp.getThickness() + "', '" + temp.getPenetremeter() + "', '"
                            + temp.getVisibleQ() + "', '" + temp.getDensity() + "', '" + temp.getF1012() + "', '" + temp.getF1016() + "', '" + temp.getF1024() + "', '"
                            + temp.getF1036() + "', '" + temp.getF1048() + "', '" + temp.getF3040() + "', '" + temp.getDefectType() + "', '" + temp.getPreEvaluation()
                            + "', '" + temp.getFinalEvaluation() + "')");
                }               
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                return -1;
            }
    }
    //Löschen eines Berichtes
    public int deleteReport (Report report){
        Connection con = this.connect();
        ResultSet rs;
        int report_id = 0;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + report.getCustomer() + "' AND reportNumber = " + report.getReportNumber() + ";");
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
                return -1;
            }
    }
    //Modifikation von Bericht (magnetic)
    public int updateMagneticReport (MagneticReport report){
        Connection con = this.connect();
        Common common = new Common();
        ResultSet rs;
        int report_id = 0;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + report.getCustomer() + "' AND reportNumber = " + report.getReportNumber() + ";");

            if (rs.next()) {
                report_id = rs.getInt("id");                
                String reportDate = common.date_toString(report.getReportDate());
                int operator_id = 0;
                int evaluator_id = 0;
                int confirmation_id = 0;
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + report.getOperator().getPersonalNr() + ";");
                if (rs.next()){
                    operator_id = rs.getInt("id");
                }                
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + report.getEvaluator().getPersonalNr() + ";");
                if (rs.next()){
                    evaluator_id = rs.getInt("id");
                }                
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + report.getConfirmation().getPersonalNr() + ";");
                if (rs.next()){
                    confirmation_id = rs.getInt("id");
                }                
                
                
                stmt.executeUpdate("UPDATE Report SET customer = '" + report.getCustomer() + "', projectName = '" + report.getProjectName() + "', inspectionPlace = '" + report.getInspectionPlace() 
                        + "', inspectionClass = '" + report.getInspectionClass() + "', evaluationStandard = '" + report.getEvaluationStandard() + "', inspectionProcedure = '" 
                        + report.getInspectionProcedure() + "', inspectionScope = '" + report.getInspectionScope() + "', drawingNo = '" + report.getDrawingNo() + "', surfaceCondition = '" 
                        + report.getSurfaceCondition() + "', stageOfExamination = '" + report.getStageOfExaminaiton() + "', page = '" + report.getPage() + "', reportNumber = '" 
                        + report.getReportNumber() + "', reportDate = '" + reportDate + "', orderNumber = '" + report.getOrderNumber() + "', offerNumber = '" + report.getOfferNumber() 
                        + "', equipment = '" + report.getEquipment() + "', heatTreatment = '" + report.getHeatTreatment() + "', inspectionDates = '" + report.getInspectionDates() 
                        + "', descriptionOfAttachments = '" + report.getDescriptionOfAttachments() + "', operator_Employee_id = " + operator_id + ", operatorSignature = false, evaluator_Employee_id = "
                        + evaluator_id + ", evaluatorSignature = false, confirmation_Employee_id = " + confirmation_id + ", confirmationSignature = false, customerName = '" 
                        + report.getCustomerName() + "', customerLevel = '" + report.getCustomerLevel() + "', type = " + this.getMagnetic_type() + " WHERE id = " + report_id + ";");

                stmt.executeUpdate("UPDATE MagneticReport SET poleDistance =  '" + report.getPoleDistance() + "', MPCarrier = '" + report.getMPCarrier() + "', magTech = '"
                        + report.getMagTech() + "', UVIntensity = '" + report.getUVIntensity() + "', distanceOfLight = '" + report.getDistanceOfLight() + "', examinationArea = '"
                        + report.getExamintionArea() + "', currentType = '" + report.getCurrentType() + "', Luxmeter = '" + report.getLuxmeter() + "', testMedium = '" + report.getTestMedium()
                        + "', demagnetization = '" + report.getDemagnetization() + "', surfaceTemperature = '" + report.getSurfaceTemperature() + "', gaussFieldStrength = '"
                        + report.getGaussFieldStrength() + "', identificationOfLightEquip = '" + report.getIdentificationOfLightEquip() + "', liftingTest = '" + report.getLiftingTest() 
                        + "', buttWeld = " + report.isButtWeld() + ", filletWeld = " + report.isButtWeld() + ", standardDeviations = '" + report.getStandardDeviations() + "' WHERE Report_id = " 
                        + report_id + ";");
                
                stmt.executeUpdate("DELETE FROM MangneticResults WHERE Report_id = " + report_id + ";");
                ArrayList<MagneticInspectionResult> results = report.getInspectionResults();
                for (int i = 0; i < results.size(); i++) {
                    MagneticInspectionResult temp = results.get(i);
                    stmt.executeUpdate("INSERT INTO MagneticResults (Report_id, weldPieceNo, testLength, weldingProcess, thickness, diameter, defectType, defectLocation, result) "
                    + "VALUES (" + report_id + ", '" + temp.getWeldPieceNo() + "', '" + temp.getTestLength() + "', '" + temp.getWeldingProcess() + "', '" + temp.getThickness() + "', '"
                    + temp.getDiameter() + "', '" + temp.getDefectType() + "', '" + temp.getDefectLocation() + "', '" + temp.getResult() + "');");
                }                 
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                return -1;
            }
    }
   //Modifikation von Bericht (radiographic)
    public int updateRadiographicReport (RadiographicReport report){
         Connection con = this.connect();
        Common common = new Common();
        ResultSet rs;
        int report_id = 0;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Report WHERE customer = '" + report.getCustomer() + "' AND reportNumber = " + report.getReportNumber() + ";");

            if (rs.next()) {
                report_id = rs.getInt("id");                
                String reportDate = common.date_toString(report.getReportDate());
                int operator_id = 0;
                int evaluator_id = 0;
                int confirmation_id = 0;
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + report.getOperator().getPersonalNr() + ";");
                if (rs.next()){
                    operator_id = rs.getInt("id");
                }                
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + report.getEvaluator().getPersonalNr() + ";");
                if (rs.next()){
                    evaluator_id = rs.getInt("id");
                }                
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + report.getConfirmation().getPersonalNr() + ";");
                if (rs.next()){
                    confirmation_id = rs.getInt("id");
                }                
                
                
                stmt.executeUpdate("UPDATE Report SET customer = '" + report.getCustomer() + "', projectName = '" + report.getProjectName() + "', inspectionPlace = '" + report.getInspectionPlace() 
                        + "', inspectionClass = '" + report.getInspectionClass() + "', evaluationStandard = '" + report.getEvaluationStandard() + "', inspectionProcedure = '" 
                        + report.getInspectionProcedure() + "', inspectionScope = '" + report.getInspectionScope() + "', drawingNo = '" + report.getDrawingNo() + "', surfaceCondition = '" 
                        + report.getSurfaceCondition() + "', stageOfExamination = '" + report.getStageOfExaminaiton() + "', page = '" + report.getPage() + "', reportNumber = '" 
                        + report.getReportNumber() + "', reportDate = '" + reportDate + "', orderNumber = '" + report.getOrderNumber() + "', offerNumber = '" + report.getOfferNumber() 
                        + "', equipment = '" + report.getEquipment() + "', heatTreatment = '" + report.getHeatTreatment() + "', inspectionDates = '" + report.getInspectionDates() 
                        + "', descriptionOfAttachments = '" + report.getDescriptionOfAttachments() + "', operator_Employee_id = " + operator_id + ", operatorSignature = false, evaluator_Employee_id = "
                        + evaluator_id + ", evaluatorSignature = false, confirmation_Employee_id = " + confirmation_id + ", confirmationSignature = false, customerName = '" 
                        + report.getCustomerName() + "', customerLevel = '" + report.getCustomerLevel() + "', type = " + this.getRadiographic_type() + " WHERE id = " + report_id + ";");

                 stmt.executeUpdate("UPDATE RadiographicReport SET usedDevice = '" + report.getUsedDevice() + "', ir192 = " + report.isIr192() + ", se75 = " + report.isSe75() + ", xRay = " 
                        + report.isxRay() + ", focalSpotSize = '" + report.getFocalSpotSize() + "', exposureTime = '" + report.getExposureTime() + "', filmFocusDistance = '"
                        + report.getFilmFocusDistance() + "', pbScreens = '" + report.getPbScreens() + "', filters = '" + report.getFilters() + "', filmBrand = '"
                        + report.getFilmBrand() + "', d4MX125 = " + report.isD4MX125() + ", d5T200 = " + report.isD5T200() + ", d7AA400 = " + report.isD7AA400() 
                        + ", en = " + report.isEn() + ", astm = " + report.isAstm() + ", sourceSide = " + report.isSourceSide() + ", filmSide = " + report.isFilmSide() 
                        + ", automatic = " + report.isAutomatic() + ", manuel = " + report.isManuel() + ", temp ='" + report.getTemp() + "', filmQuantity = '"+ report.getFilmQuantity() 
                        + "', testArrangements = '" + report.getTestArrangements() + "' WHERE Report_id = " + report_id + ";");
                stmt.executeUpdate("DELETE FROM RadiographicResults WHERE Report_id = " + report_id + ";");                     
                ArrayList<RadiographicInspectionResult> results = report.getInspectionResults();
                for (int i = 0; i < results.size(); i++) {
                    RadiographicInspectionResult temp = results.get(i);
                    stmt.executeUpdate("INSERT INTO RadiographicResults (Report_id, shootingArea, filmNo, materialType, weldingType, welderNr, position, thickness, penetremeter,"
                            + " visibleQ, density, f1012, f1016, f1024, f1036, f1048, f3040, defectType, preEvaluation, finalEvaluation) VALUES "
                            + "(" + report_id + ", '" + temp.getShootingArea() + "', '" + temp.getFilmNo() + "', '" + temp.getMaterialType() + "', '" + temp.getWeldingType()
                            + "', '" + temp.getWelderNr() + "', '" + temp.getPosition() + "', '" + temp.getThickness() + "', '" + temp.getPenetremeter() + "', '"
                            + temp.getVisibleQ() + "', '" + temp.getDensity() + "', '" + temp.getF1012() + "', '" + temp.getF1016() + "', '" + temp.getF1024() + "', '"
                            + temp.getF1036() + "', '" + temp.getF1048() + "', '" + temp.getF3040() + "', '" + temp.getDefectType() + "', '" + temp.getPreEvaluation()
                            + "', '" + temp.getFinalEvaluation() + "')");
                }               
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                return -1;
            }
    }        
}
