//Baraa Alsaleh, 19050800
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.Report;

import berichtserstellungssystem.Resource.Employee;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Baraa
 */
public class Report {
    private String customer = "";
    private String projectName = "";
    private String inspectionPlace = "";
    private String inspectionClass = "";
    private String evaluationStandard = "";
    private String inspectionProcedure = "";
    private String inspectionScope = ""; 
    private String drawingNo = "";
    private String surfaceCondition = "";
    private String stageOfExamination = "";
    private String page = "";
    private String reportNumber = "";
    private Date reportDate = new Date();
    private String orderNumber = "";
    private String offerNumber = "";
    private String equipment = "";
    private String heatTreatment = "";
    private String inspectionDates = "";
    private String descriptionOfAttachments = "";
    private int operator_id = 0;
    private int evaluator_id = 0;
    private int confirmation_id = 0;
    private String bottom = "";
    private int type = 0;

    public Report(String customer, String projectName, String inspectionPlace, String inspectionClass, String evaluationStandard, String inspectionProcedure, String inspectionScope, String drawingNo, String surfaceCondition, String stageOfExaminaiton, String page, String reportNumber, Date reportDate, String orderNumber, String offerNumber, String equipment, String heatTreatment, String inspectionDates, String descriptionOfAttachments, int operator, int evaluator, int confirmation, String bottom) {
        this.customer = customer;
        this.projectName = projectName;
        this.inspectionPlace = inspectionPlace;
        this.inspectionClass = inspectionClass;
        this.evaluationStandard = evaluationStandard;
        this.inspectionProcedure = inspectionProcedure;
        this.inspectionScope = inspectionScope;
        this.drawingNo = drawingNo;
        this.surfaceCondition = surfaceCondition;
        this.stageOfExamination = stageOfExaminaiton;
        this.page = page;
        this.reportNumber = reportNumber;
        this.reportDate = reportDate;
        this.orderNumber = orderNumber;
        this.offerNumber = offerNumber;
        this.equipment = equipment;
        this.heatTreatment = heatTreatment;
        this.inspectionDates = inspectionDates;
        this.descriptionOfAttachments = descriptionOfAttachments;
        this.operator_id = operator;
        this.evaluator_id = evaluator;
        this.confirmation_id = confirmation;
        //heiiiieieieiieieeiir
        this.bottom = bottom;
    }
    
    public Report(ResultSet ress) {
        try {
            this.customer = ress.getString("Customer");
            this.projectName = ress.getString("projectName");
            this.inspectionPlace = ress.getString("inspectionPlace");
            this.inspectionClass = ress.getString("inspectionClass");
            this.evaluationStandard = ress.getString("evaluationStandard");
            this.inspectionProcedure = ress.getString("inspectionProcedure");
            this.inspectionScope = ress.getString("inspectionScope");
            this.drawingNo = ress.getString("drawingNo");
            this.surfaceCondition = ress.getString("surfaceCondition");
            this.stageOfExamination = ress.getString("stageOfExamination");
            this.page = ress.getString("page");
            this.reportNumber = ress.getString("reportNumber");
            this.reportDate = ress.getDate("reportDate");
            this.orderNumber = ress.getString("orderNumber");
            this.offerNumber = ress.getString("offerNumber");
            this.equipment = ress.getString("equipment");
            this.heatTreatment = ress.getString("heatTreatment");
            this.inspectionDates = ress.getString("inspectionDates");
            this.descriptionOfAttachments = ress.getString("descriptionOfAttachments");
            this.operator_id = ress.getInt("operator_Employee_id");
            this.evaluator_id = ress.getInt("evaluator_Employee_id");
            this.confirmation_id = ress.getInt("confirmation_Employee_id");
            this.bottom = ress.getString("bottom");
        } catch (SQLException e) {
            System.out.println("Report " + e);
        }
        
    }
    
    public Report () {
        
    }
    
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setInspectionPlace(String inspectionPlace) {
        this.inspectionPlace = inspectionPlace;
    }

    public void setInspectionClass(String inspectionClass) {
        this.inspectionClass = inspectionClass;
    }

    public void setEvaluationStandard(String evaluationStandard) {
        this.evaluationStandard = evaluationStandard;
    }

    public void setInspectionProcedure(String inspectionProcedure) {
        this.inspectionProcedure = inspectionProcedure;
    }

    public void setInspectionScope(String inspectionScope) {
        this.inspectionScope = inspectionScope;
    }

    public void setDrawingNo(String drawingNo) {
        this.drawingNo = drawingNo;
    }

    public void setSurfaceCondition(String surfaceCondition) {
        this.surfaceCondition = surfaceCondition;
    }

    public void setStageOfExamination(String stageOfExamination) {
        this.stageOfExamination = stageOfExamination;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setReportNumber(String reportNumber) {
        this.reportNumber = reportNumber;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setOfferNumber(String offerNumber) {
        this.offerNumber = offerNumber;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public void setHeatTreatment(String heatTreatment) {
        this.heatTreatment = heatTreatment;
    }

    public void setInspectionDates(String inspectionDates) {
        this.inspectionDates = inspectionDates;
    }

    public void setDescriptionOfAttachments(String descriptionOfAttachments) {
        this.descriptionOfAttachments = descriptionOfAttachments;
    }

    public void setOperator_id(int operator_id) {
        this.operator_id = operator_id;
    }


    public void setEvaluator_id(int evaluator_id) {
        this.evaluator_id = evaluator_id;
    }

    public void setConfirmation_id(int confirmation_id) {
        this.confirmation_id = confirmation_id;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public String getCustomer() {
        return customer;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getInspectionPlace() {
        return inspectionPlace;
    }

    public String getInspectionClass() {
        return inspectionClass;
    }

    public String getEvaluationStandard() {
        return evaluationStandard;
    }

    public String getInspectionProcedure() {
        return inspectionProcedure;
    }

    public String getInspectionScope() {
        return inspectionScope;
    }

    public String getDrawingNo() {
        return drawingNo;
    }

    public String getSurfaceCondition() {
        return surfaceCondition;
    }

    public String getStageOfExamination() {
        return stageOfExamination;
    }

    public String getPage() {
        return page;
    }

    public String getReportNumber() {
        return reportNumber;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOfferNumber() {
        return offerNumber;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getHeatTreatment() {
        return heatTreatment;
    }

    public String getInspectionDates() {
        return inspectionDates;
    }

    public String getDescriptionOfAttachments() {
        return descriptionOfAttachments;
    }

    public int getOperator_id() {
        return operator_id;
    }

    public int getEvaluator_id() {
        return evaluator_id;
    }

    public int getConfirmation_id() {
        return confirmation_id;
    }

    public String getBottom() {
        return bottom;
    }

    public int getType() {
        return type;
    }
     
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Report other = (Report) obj;
        if (this.reportNumber != other.reportNumber) {
            return false;
        }
        return true;
    }
    
    
    
}
