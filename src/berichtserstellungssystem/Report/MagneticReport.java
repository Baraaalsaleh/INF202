//Baraa Alsaleh, 19050800
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.Report;

import berichtserstellungssystem.Resource.Employee;
import berichtserstellungssystem.Report.Report;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Baraa
 */
public class MagneticReport extends Report{
    private String poleDistance;
    private String mpCarrier;
    private String magTech;
    private String uvIntensity;
    private String distanceOfLight;
    private String examinationArea;
    private String currentType;
    private String luxmeter;
    private String testMedium;
    private String demagnetization;
    private String surfaceTemperature;
    private String gaussFieldStrength;
    //hiieiieiieiieeiir
    private String surfaceCondition2;
    private String identificationOfLightEquip;
    private String liftingTest;
    private boolean buttWeld;
    private boolean filletWeld;
    private String standardDeviations;
    private ArrayList<MagneticInspectionResult> inspectionResults= new ArrayList();

    public MagneticReport(String poleDistance, String MPCarrier, String magTech, String UVIntensity, String distanceOfLight, String examintionArea, String currentType, String Luxmeter, String testMedium, String demagnetization, String surfaceTemperature, String gaussFieldStrength, String surfaceCondtion2, String identificationOfLightEquip, String liftingTest, boolean buttWeld, boolean filletWeld, String standardDeviations, String customer, String projectName, String inspectionPlace, String inspectionClass, String evaluationStandard, String inspectionProcedure, String inspectionScope, String drawingNo, String surfaceCondition, String stageOfExaminaiton, String page, String reportNumber, Date reportDate, String orderNumber, String offerNumber, String equipment, String heatTreatment, String inspectionDates, String descriptionOfAttachments, int operator, int evaluator, int confirmation, String customerName, String customerLevel, String bottom) {
        super(customer, projectName, inspectionPlace, inspectionClass, evaluationStandard, inspectionProcedure, inspectionScope, drawingNo, surfaceCondition, stageOfExaminaiton, page, reportNumber, reportDate, orderNumber, offerNumber, equipment, heatTreatment, inspectionDates, descriptionOfAttachments, operator, evaluator, confirmation, bottom);
        this.poleDistance = poleDistance;
        this.mpCarrier = MPCarrier;
        this.magTech = magTech;
        this.uvIntensity = UVIntensity;
        this.distanceOfLight = distanceOfLight;
        this.examinationArea = examintionArea;
        this.currentType = currentType;
        this.luxmeter = Luxmeter;
        this.testMedium = testMedium;
        this.demagnetization = demagnetization;
        this.surfaceTemperature = surfaceTemperature;
        this.gaussFieldStrength = gaussFieldStrength;
        this.surfaceCondition2 = surfaceCondition2;
        this.identificationOfLightEquip = identificationOfLightEquip;
        this.liftingTest = liftingTest;
        this.buttWeld = buttWeld;
        this.filletWeld = filletWeld;
        this.standardDeviations = standardDeviations;
    }
    
    public MagneticReport(ResultSet ress) {
        super(ress);
        try {
            this.poleDistance = ress.getString("poleDistance");
            this.mpCarrier = ress.getString("MPCarrier");
            this.magTech = ress.getString("magTech");
            this.uvIntensity = ress.getString("UVIntensity");
            this.distanceOfLight = ress.getString("distanceOfLight");
            this.examinationArea = ress.getString("examinationArea");
            this.currentType = ress.getString("currentType");
            this.luxmeter = ress.getString("Luxmeter");
            this.testMedium = ress.getString("testMedium");
            this.demagnetization = ress.getString("demagnetization");
            this.surfaceTemperature = ress.getString("surfaceTemperature");
            this.gaussFieldStrength = ress.getString("gaussFieldStrength");
            this.surfaceCondition2 = ress.getString("surfaceCondition2");
            this.identificationOfLightEquip = ress.getString("identificationOfLightEquip");
            this.liftingTest = ress.getString("liftingTest");
            this.buttWeld = ress.getBoolean("buttWeld");
            this.filletWeld = ress.getBoolean("filletWeld");
            this.standardDeviations = ress.getString("standardDeviations"); 
        } catch (SQLException e) {
            System.out.println("MagneticReport " + e);
        }
    }

    @Override
    public String toString() {
        return "MagneticReport{" + "poleDistance=" + poleDistance + ", MPCarrier=" + mpCarrier + ", magTech=" + magTech + ", UVIntensity=" + uvIntensity + ", distanceOfLight=" + distanceOfLight + ", examinationArea=" + examinationArea + ", currentType=" + currentType + ", Luxmeter=" + luxmeter + ", testMedium=" + testMedium + ", demagnetization=" + demagnetization + ", surfaceTemperature=" + surfaceTemperature + ", gaussFieldStrength=" + gaussFieldStrength + ", surfaceCondition2=" + surfaceCondition2 + ", identificationOfLightEquip=" + identificationOfLightEquip + ", liftingTest=" + liftingTest + ", buttWeld=" + buttWeld + ", filletWeld=" + filletWeld + ", standardDeviations=" + standardDeviations + ", inspectionResults=" + inspectionResults + '}' + super.toString();
    }

    public MagneticReport () {
        
    }
    public void setPoleDistance(String poleDistance) {
        this.poleDistance = poleDistance;
    }

    public void setMpCarrier(String mpCarrier) {
        this.mpCarrier = mpCarrier;
    }

    public void setMagTech(String magTech) {
        this.magTech = magTech;
    }

    public void setUvIntensity(String uvIntensity) {
        this.uvIntensity = uvIntensity;
    }

    public void setDistanceOfLight(String distanceOfLight) {
        this.distanceOfLight = distanceOfLight;
    }

    public void setExaminationArea(String examinationArea) {
        this.examinationArea = examinationArea;
    }

    public void setCurrentType(String currentType) {
        this.currentType = currentType;
    }

    public void setLuxmeter(String luxmeter) {
        this.luxmeter = luxmeter;
    }

    public void setTestMedium(String testMedium) {
        this.testMedium = testMedium;
    }

    public void setDemagnetization(String demagnetization) {
        this.demagnetization = demagnetization;
    }

    public void setSurfaceTemperature(String surfaceTemperature) {
        this.surfaceTemperature = surfaceTemperature;
    }

    public void setGaussFieldStrength(String gaussFieldStrength) {
        this.gaussFieldStrength = gaussFieldStrength;
    }

    public void setSurfaceCondition2(String surfaceCondition2) {
        this.surfaceCondition2 = surfaceCondition2;
    }

    public void setIdentificationOfLightEquip(String identificationOfLightEquip) {
        this.identificationOfLightEquip = identificationOfLightEquip;
    }

    public void setLiftingTest(String liftingTest) {
        this.liftingTest = liftingTest;
    }

    public void setButtWeld(boolean buttWeld) {
        this.buttWeld = buttWeld;
    }

    public void setFilletWeld(boolean filletWeld) {
        this.filletWeld = filletWeld;
    }

    public void setStandardDeviations(String standardDeviations) {
        this.standardDeviations = standardDeviations;
    }

    public void setInspectionResults(ArrayList<MagneticInspectionResult> inspectionResults) {
        this.inspectionResults = inspectionResults;
    }

    public String getPoleDistance() {
        return poleDistance;
    }

    public String getMpCarrier() {
        return mpCarrier;
    }

    public String getMagTech() {
        return magTech;
    }

    public String getUvIntensity() {
        return uvIntensity;
    }

    public String getDistanceOfLight() {
        return distanceOfLight;
    }

    public String getExaminationArea() {
        return examinationArea;
    }

    public String getCurrentType() {
        return currentType;
    }

    public String getLuxmeter() {
        return luxmeter;
    }

    public String getTestMedium() {
        return testMedium;
    }

    public String getDemagnetization() {
        return demagnetization;
    }

    public String getSurfaceTemperature() {
        return surfaceTemperature;
    }

    public String getGaussFieldStrength() {
        return gaussFieldStrength;
    }

    public String getSurfaceCondition2() {
        return surfaceCondition2;
    }

    public String getIdentificationOfLightEquip() {
        return identificationOfLightEquip;
    }

    public String getLiftingTest() {
        return liftingTest;
    }

    public boolean isButtWeld() {
        return buttWeld;
    }

    public boolean isFilletWeld() {
        return filletWeld;
    }

    public String getStandardDeviations() {
        return standardDeviations;
    }

    public ArrayList<MagneticInspectionResult> getInspectionResults() {
        return inspectionResults;
    }

    
    
    

}
