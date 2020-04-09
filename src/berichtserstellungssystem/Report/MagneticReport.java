/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.Report;

import berichtserstellungssystem.Resource.Employee;
import berichtserstellungssystem.Report.Report;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Baraa
 */
public class MagneticReport extends Report{
    private String poleDistance;
    private String MPCarrier;
    private String magTech;
    private String UVIntensity;
    private String distanceOfLight;
    private String examintionArea;
    private String currentType;
    private String Luxmeter;
    private String testMedium;
    private String demagnetization;
    private String surfaceTemperature;
    private String gaussFieldStrength;
    private String identificationOfLightEquip;
    private String liftingTest;
    private boolean buttWeld;
    private boolean filletWeld;
    private String standardDeviations;
    private ArrayList<MagneticInspectionResult> inspectionResults= new ArrayList();

    public MagneticReport(String poleDistance, String MPCarrier, String magTech, String UVIntensity, String distanceOfLight, String examintionArea, String currentType, String Luxmeter, String testMedium, String demagnetization, String surfaceTemperature, String gaussFieldStrength, String identificationOfLightEquip, String liftingTest, boolean buttWeld, boolean filletWeld, String standardDeviations, String customer, String projectName, String inspectionPlace, String inspectionClass, String evaluationStandard, String inspectionProcedure, String inspectionScope, String drawingNo, String surfaceCondition, String stageOfExaminaiton, String page, String reportNumber, Date reportDate, String orderNumber, String offerNumber, String equipment, String heatTreatment, String inspectionDates, String descriptionOfAttachments, Employee operator, Employee evaluator, Employee confirmation, String customerName, String customerLevel) {
        super(customer, projectName, inspectionPlace, inspectionClass, evaluationStandard, inspectionProcedure, inspectionScope, drawingNo, surfaceCondition, stageOfExaminaiton, page, reportNumber, reportDate, orderNumber, offerNumber, equipment, heatTreatment, inspectionDates, descriptionOfAttachments, operator, evaluator, confirmation, customerName, customerLevel);
        this.poleDistance = poleDistance;
        this.MPCarrier = MPCarrier;
        this.magTech = magTech;
        this.UVIntensity = UVIntensity;
        this.distanceOfLight = distanceOfLight;
        this.examintionArea = examintionArea;
        this.currentType = currentType;
        this.Luxmeter = Luxmeter;
        this.testMedium = testMedium;
        this.demagnetization = demagnetization;
        this.surfaceTemperature = surfaceTemperature;
        this.gaussFieldStrength = gaussFieldStrength;
        this.identificationOfLightEquip = identificationOfLightEquip;
        this.liftingTest = liftingTest;
        this.buttWeld = buttWeld;
        this.filletWeld = filletWeld;
        this.standardDeviations = standardDeviations;
    }

    public void setPoleDistance(String poleDistance) {
        this.poleDistance = poleDistance;
    }

    public void setMPCarrier(String MPCarrier) {
        this.MPCarrier = MPCarrier;
    }

    public void setMagTech(String magTech) {
        this.magTech = magTech;
    }

    public void setUVIntensity(String UVIntensity) {
        this.UVIntensity = UVIntensity;
    }

    public void setDistanceOfLight(String distanceOfLight) {
        this.distanceOfLight = distanceOfLight;
    }

    public void setExamintionArea(String examintionArea) {
        this.examintionArea = examintionArea;
    }

    public void setCurrentType(String currentType) {
        this.currentType = currentType;
    }

    public void setLuxmeter(String Luxmeter) {
        this.Luxmeter = Luxmeter;
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

    public String getMPCarrier() {
        return MPCarrier;
    }

    public String getMagTech() {
        return magTech;
    }

    public String getUVIntensity() {
        return UVIntensity;
    }

    public String getDistanceOfLight() {
        return distanceOfLight;
    }

    public String getExamintionArea() {
        return examintionArea;
    }

    public String getCurrentType() {
        return currentType;
    }

    public String getLuxmeter() {
        return Luxmeter;
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
