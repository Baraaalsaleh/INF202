/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Baraa
 */
public class RadiographicReport extends Report{
    private String usedDevice;
    private boolean ir192;
    private boolean se75;
    private boolean xRay;
    private String focalSpotSize;
    private int exposureTime;
    private int filmFocusDistance;
    private double pbSreens;
    private String filters;
    private String filmBrand;
    private boolean d4MX125;
    private boolean d5T200; 
    private boolean d7AA400;
    private boolean en;
    private boolean astm;
    private boolean sourceSide;
    private boolean filmSide;
    private boolean automatic;
    private boolean manuel;
    private double temp;
    private int[] filmQuantity = new int[8];
    private boolean[] testArrangements = new boolean[6];
    private ArrayList<RadiographicInspectionResult> inspectionResults= new ArrayList();


    public RadiographicReport(String usedDevice, boolean ir192, boolean se75, boolean xRay, String focalSpotSize, int exposureTime, int filmFocusDistance, double pbSreens, String filters, String filmBrand, boolean d4MX125, boolean d5T200, boolean d7AA400, boolean en, boolean astm, boolean sourceSide, boolean filmSide, boolean automatic, boolean manuel, double temp, String customer, String projectName, String inspectionPlace, String inspectionClass, String evaluationStandard, String inspectionProcedure, double inspectionScope, String drawingNo, String surfaceCondition, String stageOfExaminaiton, String page, long reportNumber, Date reportDate, String orderNumber, String offerNumber, String equipment, String heatTreatment, String inspectionDates, String descriptionOfAttachments, Employee operator, boolean operatorSignature, Employee evaluator, boolean evaluatorSignature, Employee confirmation, boolean confirmationSignature, String customerName, String customerLevel) {
        super(customer, projectName, inspectionPlace, inspectionClass, evaluationStandard, inspectionProcedure, inspectionScope, drawingNo, surfaceCondition, stageOfExaminaiton, page, reportNumber, reportDate, orderNumber, offerNumber, equipment, heatTreatment, inspectionDates, descriptionOfAttachments, operator, operatorSignature, evaluator, evaluatorSignature, confirmation, confirmationSignature, customerName, customerLevel);
        this.usedDevice = usedDevice;
        this.ir192 = ir192;
        this.se75 = se75;
        this.xRay = xRay;
        this.focalSpotSize = focalSpotSize;
        this.exposureTime = exposureTime;
        this.filmFocusDistance = filmFocusDistance;
        this.pbSreens = pbSreens;
        this.filters = filters;
        this.filmBrand = filmBrand;
        this.d4MX125 = d4MX125;
        this.d5T200 = d5T200;
        this.d7AA400 = d7AA400;
        this.en = en;
        this.astm = astm;
        this.sourceSide = sourceSide;
        this.filmSide = filmSide;
        this.automatic = automatic;
        this.manuel = manuel;
        this.temp = temp;
    }

    public void setUsedDevice(String usedDevice) {
        this.usedDevice = usedDevice;
    }

    public void setIr192(boolean ir192) {
        this.ir192 = ir192;
    }

    public void setSe75(boolean se75) {
        this.se75 = se75;
    }

    public void setxRay(boolean xRay) {
        this.xRay = xRay;
    }

    public void setFocalSpotSize(String focalSpotSize) {
        this.focalSpotSize = focalSpotSize;
    }

    public void setExposureTime(int exposureTime) {
        this.exposureTime = exposureTime;
    }

    public void setFilmFocusDistance(int filmFocusDistance) {
        this.filmFocusDistance = filmFocusDistance;
    }

    public void setPbSreens(double pbSreens) {
        this.pbSreens = pbSreens;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public void setFilmBrand(String filmBrand) {
        this.filmBrand = filmBrand;
    }

    public void setD4MX125(boolean d4MX125) {
        this.d4MX125 = d4MX125;
    }

    public void setD5T200(boolean d5T200) {
        this.d5T200 = d5T200;
    }

    public void setD7AA400(boolean d7AA400) {
        this.d7AA400 = d7AA400;
    }

    public void setEn(boolean en) {
        this.en = en;
    }

    public void setAstm(boolean astm) {
        this.astm = astm;
    }

    public void setSourceSide(boolean sourceSide) {
        this.sourceSide = sourceSide;
    }

    public void setFilmSide(boolean filmSide) {
        this.filmSide = filmSide;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }

    public void setManuel(boolean manuel) {
        this.manuel = manuel;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setFilmQuantity(int[] filmQuantity) {
        this.filmQuantity = filmQuantity;
    }

    public void setTestArrangements(boolean[] testArrangements) {
        this.testArrangements = testArrangements;
    }

    public String getUsedDevice() {
        return usedDevice;
    }

    public boolean isIr192() {
        return ir192;
    }

    public boolean isSe75() {
        return se75;
    }

    public boolean isxRay() {
        return xRay;
    }

    public String getFocalSpotSize() {
        return focalSpotSize;
    }

    public int getExposureTime() {
        return exposureTime;
    }

    public int getFilmFocusDistance() {
        return filmFocusDistance;
    }

    public double getPbSreens() {
        return pbSreens;
    }

    public String getFilters() {
        return filters;
    }

    public String getFilmBrand() {
        return filmBrand;
    }

    public boolean isD4MX125() {
        return d4MX125;
    }

    public boolean isD5T200() {
        return d5T200;
    }

    public boolean isD7AA400() {
        return d7AA400;
    }

    public boolean isEn() {
        return en;
    }

    public boolean isAstm() {
        return astm;
    }

    public boolean isSourceSide() {
        return sourceSide;
    }

    public boolean isFilmSide() {
        return filmSide;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public boolean isManuel() {
        return manuel;
    }

    public double getTemp() {
        return temp;
    }

    public int[] getFilmQuantity() {
        return filmQuantity;
    }

    public boolean[] getTestArrangements() {
        return testArrangements;
    }
    
    
}
