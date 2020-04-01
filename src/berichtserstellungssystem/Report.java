/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem;

import java.util.Date;

/**
 *
 * @author Baraa
 */
public class Report {
    private String customer;
    private String projectName;
    private String inspectionPlace;
    private String inspectionClass;
    private String evaluationStandard;
    private String inspectionProcedure;
    private double inspectionScope;
    private String drawingNo;
    private String surfaceCondition;
    private String stageOfExaminaiton;
    private String page;
    private long reportNumber;
    private Date reportDate;
    private String orderNumber;
    private String offerNumber;
    private String equipment;
    private String heatTreatment;
    private String inspectionDates;
    private String descriptionOfAttachments;
    private Employee operator;
    private boolean operatorSignature;
    private Employee evaluator;
    private boolean evaluatorSignature;
    private Employee confirmation;
    private boolean confirmationSignature;
    private String customerName;
    private String customerLevel;

    public Report(String customer, String projectName, String inspectionPlace, String inspectionClass, String evaluationStandard, String inspectionProcedure, double inspectionScope, String drawingNo, String surfaceCondition, String stageOfExaminaiton, String page, long reportNumber, Date reportDate, String orderNumber, String offerNumber, String equipment, String heatTreatment, String inspectionDates, String descriptionOfAttachments, Employee operator, boolean operatorSignature, Employee evaluator, boolean evaluatorSignature, Employee confirmation, boolean confirmationSignature, String customerName, String customerLevel) {
        this.customer = customer;
        this.projectName = projectName;
        this.inspectionPlace = inspectionPlace;
        this.inspectionClass = inspectionClass;
        this.evaluationStandard = evaluationStandard;
        this.inspectionProcedure = inspectionProcedure;
        this.inspectionScope = inspectionScope;
        this.drawingNo = drawingNo;
        this.surfaceCondition = surfaceCondition;
        this.stageOfExaminaiton = stageOfExaminaiton;
        this.page = page;
        this.reportNumber = reportNumber;
        this.reportDate = reportDate;
        this.orderNumber = orderNumber;
        this.offerNumber = offerNumber;
        this.equipment = equipment;
        this.heatTreatment = heatTreatment;
        this.inspectionDates = inspectionDates;
        this.descriptionOfAttachments = descriptionOfAttachments;
        this.operator = operator;
        this.operatorSignature = operatorSignature;
        this.evaluator = evaluator;
        this.evaluatorSignature = evaluatorSignature;
        this.confirmation = confirmation;
        this.confirmationSignature = confirmationSignature;
        this.customerName = customerName;
        this.customerLevel = customerLevel;
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

    public void setInspectionScope(double inspectionScope) {
        this.inspectionScope = inspectionScope;
    }

    public void setDrawingNo(String drawingNo) {
        this.drawingNo = drawingNo;
    }

    public void setSurfaceCondition(String surfaceCondition) {
        this.surfaceCondition = surfaceCondition;
    }

    public void setStageOfExaminaiton(String stageOfExaminaiton) {
        this.stageOfExaminaiton = stageOfExaminaiton;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setReportNumber(long reportNumber) {
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

    public void setOperator(Employee operator) {
        this.operator = operator;
    }

    public void setOperatorSignature(boolean operatorSignature) {
        this.operatorSignature = operatorSignature;
    }

    public void setEvaluator(Employee evaluator) {
        this.evaluator = evaluator;
    }

    public void setEvaluatorSignature(boolean evaluatorSignature) {
        this.evaluatorSignature = evaluatorSignature;
    }

    public void setConfirmation(Employee confirmation) {
        this.confirmation = confirmation;
    }

    public void setConfirmationSignature(boolean confirmationSignature) {
        this.confirmationSignature = confirmationSignature;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerLevel(String customerLevel) {
        this.customerLevel = customerLevel;
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

    public double getInspectionScope() {
        return inspectionScope;
    }

    public String getDrawingNo() {
        return drawingNo;
    }

    public String getSurfaceCondition() {
        return surfaceCondition;
    }

    public String getStageOfExaminaiton() {
        return stageOfExaminaiton;
    }

    public String getPage() {
        return page;
    }

    public long getReportNumber() {
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

    public Employee getOperator() {
        return operator;
    }

    public boolean isOperatorSignature() {
        return operatorSignature;
    }

    public Employee getEvaluator() {
        return evaluator;
    }

    public boolean isEvaluatorSignature() {
        return evaluatorSignature;
    }

    public Employee getConfirmation() {
        return confirmation;
    }

    public boolean isConfirmationSignature() {
        return confirmationSignature;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerLevel() {
        return customerLevel;
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
