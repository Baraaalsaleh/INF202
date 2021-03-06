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
public class RadiographicReport extends Report{
    private String usedDevice = "";
    private boolean ir192 = false;
    private boolean se75 = false;
    private boolean xRay = false;
    private String focalSpotSize = "";
    private String exposureTime = "";
    private String filmFocusDistance = "";
    private String pbScreens = "";
    private String filters = "";
    private String filmBrand = "";
    private boolean d4MX125 = false;
    private boolean d5T200 = false; 
    private boolean d7AA400 = false;
    private boolean en = false;
    private boolean astm = false;
    private boolean sourceSide = false;
    private boolean filmSide = false;
    private boolean automatic = false;
    private boolean manuel = false;
    private String temp = "";
    private int[] filmQuantity = {0,0,0,0,0,0,0,0};
    private boolean[] testArrangements = {false, false, false, false, false, false};
    private ArrayList<RadiographicInspectionResult> inspectionResults= new ArrayList();


    public RadiographicReport(String usedDevice, boolean ir192, boolean se75, boolean xRay, String focalSpotSize, String exposureTime, String filmFocusDistance, String pbSreens, String filters, String filmBrand, boolean d4MX125, boolean d5T200, boolean d7AA400, boolean en, boolean astm, boolean sourceSide, boolean filmSide, boolean automatic, boolean manuel, String temp, String customer, String projectName, String inspectionPlace, String inspectionClass, String evaluationStandard, String inspectionProcedure, String inspectionScope, String drawingNo, String surfaceCondition, String stageOfExaminaiton, String page, String reportNumber, Date reportDate, String orderNumber, String offerNumber, String equipment, String heatTreatment, String inspectionDates, String descriptionOfAttachments, int operator, int evaluator, int confirmation, String customerName, String customerLevel, String bottom) {
        super(customer, projectName, inspectionPlace, inspectionClass, evaluationStandard, inspectionProcedure, inspectionScope, drawingNo, surfaceCondition, stageOfExaminaiton, page, reportNumber, reportDate, orderNumber, offerNumber, equipment, heatTreatment, inspectionDates, descriptionOfAttachments, operator, evaluator, confirmation, bottom);
        this.usedDevice = usedDevice;
        this.ir192 = ir192;
        this.se75 = se75;
        this.xRay = xRay;
        this.focalSpotSize = focalSpotSize;
        this.exposureTime = exposureTime;
        this.filmFocusDistance = filmFocusDistance;
        this.pbScreens = pbSreens;
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
    
    public RadiographicReport(ResultSet rs) {
        super(rs);
        try {
            this.usedDevice = rs.getString("usedDevice");
            this.ir192 = rs.getBoolean("ir192");
            this.se75 = rs.getBoolean("se75");
            this.xRay = rs.getBoolean("xRay");
            this.focalSpotSize = rs.getString("focalSpotSize");
            this.exposureTime = rs.getString("exposureTime");
            this.filmFocusDistance = rs.getString("filmFocusDistance");
            this.pbScreens = rs.getString("pbScreens");
            this.filters = rs.getString("filters");
            this.filmBrand = rs.getString("filmBrand");
            this.d4MX125 = rs.getBoolean("d4MX125");
            this.d5T200 = rs.getBoolean("d5T200");
            this.d7AA400 = rs.getBoolean("d7AA400");
            this.en = rs.getBoolean("en");
            this.astm = rs.getBoolean("astm");
            this.sourceSide = rs.getBoolean("sourceSide");
            this.filmSide = rs.getBoolean("filmSide");
            this.automatic = rs.getBoolean("automatic");
            this.manuel = rs.getBoolean("manuel");
            this.temp = rs.getString("temp");
            setFilmQuantityString(rs.getString("filmQuantity"));
            setTestArrangementsString(rs.getString("testArrangements"));
        } catch(SQLException e) {
            System.out.println("RadiographicReport " + e);
        }
        
    }

    @Override
    public String toString() {
        return "RadiographicReport{" + "usedDevice=" + usedDevice + ", ir192=" + ir192 + ", se75=" + se75 + ", xRay=" + xRay + ", focalSpotSize=" + focalSpotSize + ", exposureTime=" + exposureTime + ", filmFocusDistance=" + filmFocusDistance + ", pbScreens=" + pbScreens + ", filters=" + filters + ", filmBrand=" + filmBrand + ", d4MX125=" + d4MX125 + ", d5T200=" + d5T200 + ", d7AA400=" + d7AA400 + ", en=" + en + ", astm=" + astm + ", sourceSide=" + sourceSide + ", filmSide=" + filmSide + ", automatic=" + automatic + ", manuel=" + manuel + ", temp=" + temp + ", filmQuantity=" + filmQuantity + ", testArrangements=" + testArrangements + ", inspectionResults=" + inspectionResults + '}' + super.toString();
    }

    public RadiographicReport () {
        
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

    public void setExposureTime(String exposureTime) {
        this.exposureTime = exposureTime;
    }

    public void setFilmFocusDistance(String filmFocusDistance) {
        this.filmFocusDistance = filmFocusDistance;
    }

    public void setPbScreens(String pbScreens) {
        this.pbScreens = pbScreens;
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

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setFilmQuantity(int[] filmQuantity) {
        this.filmQuantity = filmQuantity;
    }
    
    public void setFilmQuantityString(String filmQuantity) {
        int[] temp = new int[8];
        String[] tempS = filmQuantity.split(",");
        try {
            if (tempS.length == 8) {
                for (int i = 0; i < 8; i++) {
                    temp[i] = Integer.parseInt(tempS[i]);
                }
                this.filmQuantity = temp;
            }
            else {
                System.out.println("setFilmQuantityString not enough elements");
            }
        }
        catch (NumberFormatException e) {
            System.out.println("setFilmQuantityString " + e);
        }
    }
    
    public void setTestArrangements(boolean[] testArrangements) {
        this.testArrangements = testArrangements;
    }
    
     public void setTestArrangementsString(String testArrangements) {
        boolean[] temp = new boolean[6];
        String[] tempS = testArrangements.split(",");
            if (tempS.length == 6) {
                for (int i = 0; i < 6; i++) {
                    temp[i] = Boolean.parseBoolean(tempS[i]);
                }
                this.testArrangements = temp;
            }
            else {
                System.out.println("setTestArrangementsString not enough elements");
            }
    }

    public void setInspectionResults(ArrayList<RadiographicInspectionResult> inspectionResults) {
        this.inspectionResults = inspectionResults;
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

    public String getExposureTime() {
        return exposureTime;
    }

    public String getFilmFocusDistance() {
        return filmFocusDistance;
    }

    public String getPbScreens() {
        return pbScreens;
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

    public String getTemp() {
        return temp;
    }

    public int[] getFilmQuantity() {
        return filmQuantity;
    }

    public String getFilmQuantityString() {
        String res = "";
        for (int i = 0; i < filmQuantity.length; i++){
            if (i != (filmQuantity.length - 1)) {
                res += filmQuantity[i] + ",";
            }
            else {
                res += filmQuantity[i];
            }
        }
        return res;
    }
    
    public boolean[] getTestArrangements() {
        return testArrangements;
    }

    public String getTestArrangementsString () {
        String res = "";
        for (int i = 0; i < testArrangements.length; i++){
            if (i != (testArrangements.length - 1)) {
                res += testArrangements[i] + ",";
            }
            else {
                res += testArrangements[i];
            }
        }
        return res;
    }
    
    public ArrayList<RadiographicInspectionResult> getInspectionResults() {
        return inspectionResults;
    }
    
    
}
