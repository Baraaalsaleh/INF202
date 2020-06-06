/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.GUI;

import berichtserstellungssystem.Common;
import berichtserstellungssystem.DatabaseManagement.*;
import berichtserstellungssystem.Resource.*;
import berichtserstellungssystem.Report.*;
import berichtserstellungssystem.Verification;
import java.awt.Dimension;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.text.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Baraa
 */
public class Report extends javax.swing.JFrame {
    MagneticReport toEditM;
    RadiographicReport toEditR;
    
    int process = 1;

    GridBagLayout layout = new GridBagLayout();
    private JTextField[] radiographicResult1 = new JTextField[20];
    private JTextField[] radiographicResult2 = new JTextField[20];
    private JTextField[] radiographicResult3 = new JTextField[20];
    private JTextField[] radiographicResult4 = new JTextField[20];
    private JTextField[] radiographicResult5 = new JTextField[20];
    private ArrayList<JTextField[]> radiographicResults = new ArrayList();
    private JTextField[] magneticResult1 = new JTextField[8];
    private JTextField[] magneticResult2 = new JTextField[8];
    private JTextField[] magneticResult3 = new JTextField[8];
    private JTextField[] magneticResult4 = new JTextField[8];
    private JTextField[] magneticResult5 = new JTextField[8];
    private JTextField[] magneticResult6 = new JTextField[8];
    private JTextField[] magneticResult7 = new JTextField[8];
    private JTextField[] magneticResult8 = new JTextField[8];
    private JTextField[] magneticResult9 = new JTextField[8];
    private JTextField[] magneticResult10 = new JTextField[8];
    private ArrayList<JTextField[]> magneticResults = new ArrayList();
    
    private Date reportDate = new Date();
    private Customer theCustomer = new Customer();
    private Employee operator = new Employee();
    private Employee evaluator = new Employee();
    private Employee confirmator = new Employee();
    private MagneticEquipment equip1 = new MagneticEquipment();
    private RadiographicEquipment equip2 = new RadiographicEquipment();
    private String orderNr = "";
    private String offerNr = "";
    private String project = "";
    private String surfaceCondition = "";
    private String stageOfExamination = "";
    
    
    int operator_id;
    int evaluator_id;
    int confirmation_id;
    
    
    /**
     * Creates new form Report
     */
    public Report() {
        start();
    }
    
    public Report(int type, String customer, String reportNo) {
        start();
        int id = ReportManagement.getReportId(customer, reportNo);
        System.out.println(id);
        if (type == DatabaseManagement.getMAGNETIC_TYPE()) {
            toEditM = ReportManagement.getReport(id);
            getInfos((berichtserstellungssystem.Report.Report) toEditM);
        }
        else {
            long i = id;
            toEditR = ReportManagement.getReport(i);
            getInfos((berichtserstellungssystem.Report.Report) toEditR);
        }
        this.process = 2;
        setEveryThing();
    }
    public Report(MagneticReport toEditM, String[] data) {
        start();
        this.toEditM = toEditM;
        int type = DatabaseManagement.getMAGNETIC_TYPE();
        process = 2;
        
        if (type == DatabaseManagement.getMAGNETIC_TYPE()) {
            getInfosM((berichtserstellungssystem.Report.Report) toEditM, data);
        }
        setEveryThing();
         for (int i = 0; i < 6; i++) {
                System.out.println(data[i]);
            }
        _MoperatorName.setText(data[0]);
        _MevaluatorName.setText(data[1]);
        _MconfirmationName.setText(data[2]);
        _MoperatorLevel.setText(data[3]);
        _MevaluatorLevel.setText(data[4]);
        _MconfirmationLevel.setText(data[5]);
    }
    
    public Report(Customer customer, String orderNr, String offerNr, Employee operator, Employee evaluator, Employee confirmation, MagneticEquipment equip1, RadiographicEquipment equip2,String project, String surfaceCondition, String stageOfExamination, Date reportDate) {
        start();
        this.theCustomer = customer;
        this.orderNr = orderNr;
        this.offerNr = offerNr;
        this.operator = operator;
        this.evaluator = evaluator;
        this.confirmator = confirmation;
        this.equip1 = equip1;
        this.equip2 = equip2;
        this.project = project;
        this.surfaceCondition = surfaceCondition;
        this.stageOfExamination = stageOfExamination;
        this.reportDate = reportDate;
        
        setEveryThing();
    }
    
    private void start() {
        initComponents();
        jScrollPane2.getVerticalScrollBar().setUnitIncrement(40);
    }
    
    private void getInfos(berichtserstellungssystem.Report.Report r) {
        String cus = r.getCustomer();
        this.theCustomer = CustomerManagement.getCustomer(cus);
        this.orderNr = r.getOrderNumber();
        this.offerNr = r.getOfferNumber();
        this.operator_id = r.getOperator_id();
        this.evaluator_id = r.getEvaluator_id();
        this.confirmation_id = r.getConfirmation_id();
        System.out.println(r.toString());
        System.out.println("getInfos: " + operator_id + " " + evaluator_id + " " + confirmation_id);
        this.operator = new Employee(PersonManagement.getEmployeeById(this.operator_id));
        this.evaluator = new Employee(PersonManagement.getEmployeeById(this.evaluator_id));
        this.confirmator = new Employee(PersonManagement.getEmployeeById(this.confirmation_id));
        String equip = r.getEquipment();
        if (toEditM != null) {
            this.equip1 = new MagneticEquipment(EquipmentManagement.getMagneticEquipment(equip));
        }
        else {
            this.equip2 = new RadiographicEquipment(EquipmentManagement.getRadiographicEquipment(equip));
        }
        this.project = r.getProjectName();
        this.surfaceCondition = r.getSurfaceCondition();
        this.stageOfExamination = r.getStageOfExamination();
        this.reportDate = r.getReportDate();
    }
    
    private void getInfosM(berichtserstellungssystem.Report.Report r, String[] data) {
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        String cus = r.getCustomer();
        this.theCustomer = CustomerManagement.getCustomer(cus);
        this.orderNr = r.getOrderNumber();
        this.offerNr = r.getOfferNumber();
        ArrayList<Employee> employees = PersonManagement.employees(0, 100, 1, null);
        ArrayList<Integer> liste = new ArrayList();
        for (Employee e : employees) {
            String fullName = e.getName() + " " + e.getLastname();
            if (data[0].equals(fullName)) {
                int temp = PersonManagement.getPersonId((Person) e);
                liste.add(temp);
            }
        }
        if (!liste.isEmpty() && liste.size() == 1) {
            this.operator_id = liste.get(0);
            r.setOperator_id(operator_id);
        }
        else {
            JOptionPane.showMessageDialog(dialog, "Operatör tanımlanmadı! Operatör adı, soyadı ve seviye ile tanımlanmadı!", "Hata", JOptionPane.PLAIN_MESSAGE);
        }
        
        liste = new ArrayList();
        for (Employee e : employees) {
            String fullName = e.getName() + " " + e.getLastname();
            if (data[1].equals(fullName)) {
                int temp = PersonManagement.getPersonId((Person) e);
                liste.add(temp);
            }
        }
        if (!liste.isEmpty() && liste.size() == 1) {
            this.evaluator_id = liste.get(0);
            r.setEvaluator_id(evaluator_id);
        }
        else {
            JOptionPane.showMessageDialog(dialog, "Değerlendiren tanımlanmadı! Değerlendiren adı, soyadı ve seviye ile tanımlanmadı!", "Hata", JOptionPane.PLAIN_MESSAGE);
        }
        
        liste = new ArrayList();
        for (Employee e : employees) {
            String fullName = e.getName() + " " + e.getLastname();
            if (data[2].equals(fullName)) {
                int temp = PersonManagement.getPersonId((Person) e);
                liste.add(temp);
            }
        }
        if (!liste.isEmpty() && liste.size() == 1) {
            this.confirmation_id = liste.get(0);
            r.setConfirmation_id(confirmation_id);
        }
        else {
            JOptionPane.showMessageDialog(dialog, "Onaylayan tanımlanmadı! Onaylayan adı, soyadı ve seviye ile tanımlanmadı!", "Hata", JOptionPane.PLAIN_MESSAGE);
        }
        
        System.out.println("getInfosM: " + operator_id + " " + evaluator_id + " " + confirmation_id);
        this.operator = new Employee(PersonManagement.getEmployeeById(this.operator_id));
        this.evaluator = new Employee(PersonManagement.getEmployeeById(this.evaluator_id));
        this.confirmator = new Employee(PersonManagement.getEmployeeById(this.confirmation_id));
        String equip = r.getEquipment();
        if (toEditM != null) {
            this.equip1 = new MagneticEquipment(EquipmentManagement.getMagneticEquipment(equip));
        }
        else {
            this.equip2 = new RadiographicEquipment(EquipmentManagement.getRadiographicEquipment(equip));
        }
        this.project = r.getProjectName();
        this.surfaceCondition = r.getSurfaceCondition();
        this.stageOfExamination = r.getStageOfExamination();
        this.reportDate = r.getReportDate();
    }
    
    private void setEveryThing() {
        radiographicResultsCapsul();
        magneticResultsCapsul();
        if (process == 1) {
            if (equip2 != null) {
                jTabbedPane1.remove(1);
                _customer.setText(this.theCustomer.getName());
                _project.setText(this.project);
                _inspectionPlace.setText(this.theCustomer.getAddress());
                _inspectionClass.setText("TS EN ISO 17636-1 / Class B");
                _evaluationStandard.setText("TS EN ISO 10675-1 / AL1");
                _inspectionProcedure.setText("P-101-002");
                _inspectionScope.setText("100 %");
                _drawingNo.setText("-");
                _surfaceCondition.setText(this.surfaceCondition);
                _stageOfExamination.setText(this.stageOfExamination);
                _page.setText("2/1");
                _reportNo.setText("");
                _reportDate.setText(Common.date_toStringReverse(this.reportDate, "."));
                _orderNo.setText(this.orderNr);
                _offerNo.setText(this.offerNr);
                
                _equipment.setText(equip2.getName());
                _usedDevice.setText("");
                _ir192.setSelected(equip2.isIr192());
                _se75.setSelected(equip2.isSe75());
                _xRay.setSelected(equip2.isxRay());
                _focalSpotSize.setText(equip2.getFocalSpotSize());
                _exposureTime.setText(equip2.getExposureTime());
                _filmFocusDistance.setText(equip2.getFilmFocusDistance());
                _pbScreens.setText(equip2.getPbScreens());
                _filters.setText(equip2.getFilters());
                
                _heatTreatment.setText("");
                _filmBrand.setText("");
                _d4.setSelected(false);
                _d5.setSelected(false);
                _d7.setSelected(false);
                _en.setSelected(false);
                _astm.setSelected(false);
                _sourceSide.setSelected(false);
                _filmSide.setSelected(false);
                _automatic.setSelected(false);
                _manuel.setSelected(false);
                _temp.setText("");
                
                _f1012.setText("");
                _f1016.setText("");
                _f1024.setText("");
                _f1036.setText("");
                _f1048.setText("");
                _f3040.setText("");
                _suitableFilm.setText("");
                _repairFilm.setText("");
                _testArr1.setSelected(false);
                _testArr2.setSelected(false);
                _testArr3.setSelected(false);
                _testArr4.setSelected(false);
                _testArr5.setSelected(false);
                _testArr6.setSelected(false);
                
                _inspectionDates.setText(Common.date_toStringReverse(this.reportDate, "."));
                _description.setText("");
                
                _operatorName.setText(this.operator.getName() + " " + this.operator.getLastname());
                _evaluatorName.setText(this.evaluator.getName() + " " + this.evaluator.getLastname());
                _confirmationName.setText(this.confirmator.getName() + " " + this.confirmator.getLastname());
                _operatorLevel.setText(Integer.toString(this.operator.getLevel()));
                _evaluatorLevel.setText(Integer.toString(this.evaluator.getLevel()));
                _confirmationLevel.setText(Integer.toString(this.confirmator.getLevel()));
                _date1.setText(Common.date_toStringReverse(this.reportDate, "."));
                _date2.setText(Common.date_toStringReverse(this.reportDate, "."));
                _date3.setText(Common.date_toStringReverse(this.reportDate, "."));
            }
            else if (equip1 != null) {
                jTabbedPane1.setSelectedIndex(1);
                jTabbedPane1.remove(0);
                _Mcustomer.setText(this.theCustomer.getName());
                _Mproject.setText(this.project);
                _MinspectionPlace.setText(this.theCustomer.getAddress());
                _MinspectionStandard.setText("TS EN ISO 17638");
                _MevaluationStandard.setText("TS EN ISO 23278 Class B");
                _MinspectionProcedure.setText("P-101-004");
                _MinspectionScope.setText("100 %");
                _MdrawingNo.setText("-");
                _MsurfaceCondition.setText(this.surfaceCondition);
                _MstageOfExamination.setText(this.stageOfExamination);
                _Mpage.setText("1");
                _MreportNo.setText("");
                _MreportDate.setText(Common.date_toStringReverse(this.reportDate, "."));
                _MorderNo.setText(this.orderNr);
                _MofferNo.setText(this.offerNr);
                
                _poleDistance.setText(equip1.getPolesDistance());
                _Mequipment.setText(equip1.getName());
                _MPCarrier.setText(equip1.getMpCarrier());
                _magTech.setText(equip1.getMagTechnic());
                _UV.setText(equip1.getUvIntensity());
                _distanceOfLight.setText(equip1.getLightDistance());
                _examinationArea.setText("KAYNAK+HAZ");
                _currentType.setText("AC");
                _luxmeter.setText("1200 Lux");
                _testMedium.setText("");
                _demagnetization.setText("");
                _MheatTreatment.setText("");
                _surfaceTemperature.setText("");
                _gauss.setText("3.2 kA/m");
                _MsurfaceCondition2.setText("TAŞLANMIŞ / GRINDING");
                _identification.setText("***");
                _liftingTestDate.setText("");
                
                _butt.setSelected(false);
                _fillet.setSelected(false);
                _standardDeviations.setText("Standarttan sapma yoktur.");
                _MinspectionDates.setText(Common.date_toStringReverse(this.reportDate, "."));
                _Mdescription.setText("");
                
                _MoperatorName.setText(this.operator.getName() + " " + this.operator.getLastname());
                _MevaluatorName.setText(this.evaluator.getName() + " " + this.evaluator.getLastname());
                _MconfirmationName.setText(this.confirmator.getName() + " " + this.confirmator.getLastname());
                _MoperatorLevel.setText(Integer.toString(this.operator.getLevel()));
                _MevaluatorLevel.setText(Integer.toString(this.evaluator.getLevel()));
                _MconfirmationLevel.setText(Integer.toString(this.confirmator.getLevel()));
                _Mdate1.setText(Common.date_toStringReverse(this.reportDate, "."));
                _Mdate2.setText(Common.date_toStringReverse(this.reportDate, "."));
                _Mdate3.setText(Common.date_toStringReverse(this.reportDate, "."));
            }
        }
        else {
            jLabel1.setEnabled(true);
            jLabel4.setEnabled(true);
            if (this.toEditR != null) {
                jTabbedPane1.remove(1);
                _customer.setText(toEditR.getCustomer());
                _project.setText(toEditR.getProjectName());
                _inspectionPlace.setText(toEditR.getInspectionPlace());
                _inspectionClass.setText(toEditR.getInspectionClass());
                _evaluationStandard.setText(toEditR.getEvaluationStandard());
                _inspectionProcedure.setText(toEditR.getInspectionProcedure());
                _inspectionScope.setText(toEditR.getInspectionScope());
                _drawingNo.setText(toEditR.getDrawingNo());
                _surfaceCondition.setText(toEditR.getSurfaceCondition());
                _stageOfExamination.setText(toEditR.getStageOfExamination());
                _page.setText(toEditR.getPage());
                _reportNo.setText(toEditR.getReportNumber());
                _reportDate.setText(Common.date_toStringReverse(toEditR.getReportDate(), "."));
                _orderNo.setText(toEditR.getOrderNumber());
                _offerNo.setText(toEditR.getOfferNumber());
                
                _equipment.setText(toEditR.getEquipment());
                _usedDevice.setText(toEditR.getUsedDevice());
                _ir192.setSelected(toEditR.isIr192());
                _se75.setSelected(toEditR.isSe75());
                _xRay.setSelected(toEditR.isxRay());
                _focalSpotSize.setText(toEditR.getFocalSpotSize());
                _exposureTime.setText(toEditR.getExposureTime());
                _filmFocusDistance.setText(toEditR.getFilmFocusDistance());
                _pbScreens.setText(toEditR.getPbScreens());
                _filters.setText(toEditR.getFilters());
                
                _heatTreatment.setText(toEditR.getHeatTreatment());
                _filmBrand.setText(toEditR.getFilmBrand());
                _d4.setSelected(toEditR.isD4MX125());
                _d5.setSelected(toEditR.isD5T200());
                _d7.setSelected(toEditR.isD7AA400());
                _en.setSelected(toEditR.isEn());
                _astm.setSelected(toEditR.isAstm());
                _sourceSide.setSelected(toEditR.isSourceSide());
                _filmSide.setSelected(toEditR.isFilmSide());
                _automatic.setSelected(toEditR.isAutomatic());
                _manuel.setSelected(toEditR.isManuel());
                _temp.setText(toEditR.getTemp());
                int[] s = toEditR.getFilmQuantity();
                _f1012.setText(Integer.toString(s[0]));
                _f1016.setText(Integer.toString(s[1]));
                _f1024.setText(Integer.toString(s[2]));
                _f1036.setText(Integer.toString(s[3]));
                _f1048.setText(Integer.toString(s[4]));
                _f3040.setText(Integer.toString(s[5]));
                _suitableFilm.setText(Integer.toString(s[6]));
                _repairFilm.setText(Integer.toString(s[7]));
                boolean[] b = toEditR.getTestArrangements();
                _testArr1.setSelected(b[0]);
                _testArr2.setSelected(b[1]);
                _testArr3.setSelected(b[2]);
                _testArr4.setSelected(b[3]);
                _testArr5.setSelected(b[4]);
                _testArr6.setSelected(b[5]);
                
                ArrayList<RadiographicInspectionResult> radioResults = toEditR.getInspectionResults();
                JTextField[][] all = new JTextField[5][20];
                all[0] = radiographicResult1;
                all[1] = radiographicResult2;
                all[2] = radiographicResult3;
                all[3] = radiographicResult4;
                all[4] = radiographicResult5;
                for (int i = 0; i < radioResults.size(); i++) {
                    RadiographicInspectionResult temp = radioResults.get(i);
                    all[i][0].setText(temp.getShootingArea());
                    all[i][1].setText(temp.getFilmNo());
                    all[i][2].setText(temp.getMaterialType());
                    all[i][3].setText(temp.getWeldingType());
                    all[i][4].setText(temp.getWelderNr());
                    all[i][5].setText(temp.getPosition());
                    all[i][6].setText(temp.getThickness());
                    all[i][7].setText(temp.getPenetremeter());
                    all[i][8].setText(temp.getVisibleQ());
                    all[i][9].setText(temp.getDensity());
                    all[i][10].setText(temp.getF1012());
                    all[i][11].setText(temp.getF1016());
                    all[i][12].setText(temp.getF1024());
                    all[i][13].setText(temp.getF1036());
                    all[i][14].setText(temp.getF1048());
                    all[i][15].setText(temp.getF3040());
                    all[i][16].setText(temp.getDefectLocation());
                    all[i][17].setText(temp.getDefectType());
                    all[i][18].setText(temp.getPreEvaluation());
                    all[i][19].setText(temp.getFinalEvaluation());
                }
                
                _inspectionDates.setText(toEditR.getInspectionDates());
                _description.setText(toEditR.getDescriptionOfAttachments());
                _operatorName.setText(this.operator.getName() + " " + this.operator.getLastname());
                _evaluatorName.setText(this.evaluator.getName() + " " + this.evaluator.getLastname());
                _confirmationName.setText(this.confirmator.getName() + " " + this.confirmator.getLastname());
                _operatorLevel.setText(Integer.toString(this.operator.getLevel()));
                _evaluatorLevel.setText(Integer.toString(this.evaluator.getLevel()));
                _confirmationLevel.setText(Integer.toString(this.confirmator.getLevel()));
                _date1.setText(Common.date_toStringReverse(this.reportDate, "."));
                _date2.setText(Common.date_toStringReverse(this.reportDate, "."));
                _date3.setText(Common.date_toStringReverse(this.reportDate, "."));
            }
            else if (equip1 != null) {
                jTabbedPane1.setSelectedIndex(1);
                jTabbedPane1.remove(0);
                
                _Mcustomer.setText(toEditM.getCustomer());
                _Mproject.setText(toEditM.getProjectName());
                _MinspectionPlace.setText(toEditM.getInspectionPlace());
                _MinspectionStandard.setText(toEditM.getInspectionClass());
                _MevaluationStandard.setText(toEditM.getEvaluationStandard());
                _MinspectionProcedure.setText(toEditM.getInspectionProcedure());
                _MinspectionScope.setText(toEditM.getInspectionScope());
                _MdrawingNo.setText(toEditM.getDrawingNo());
                _MsurfaceCondition.setText(toEditM.getSurfaceCondition());
                _MstageOfExamination.setText(toEditM.getStageOfExamination());
                _Mpage.setText(toEditM.getPage());
                _MreportNo.setText(toEditM.getReportNumber());
                _MreportDate.setText(Common.date_toStringReverse(toEditM.getReportDate(), "."));
                _MorderNo.setText(toEditM.getOrderNumber());
                _MofferNo.setText(toEditM.getOfferNumber());

                _poleDistance.setText(toEditM.getPoleDistance());
                _Mequipment.setText(toEditM.getEquipment());
                _MPCarrier.setText(toEditM.getMpCarrier());
                _magTech.setText(toEditM.getMagTech());
                _UV.setText(toEditM.getUvIntensity());
                _distanceOfLight.setText(toEditM.getDistanceOfLight());
                _examinationArea.setText(toEditM.getExaminationArea());
                _currentType.setText(toEditM.getCurrentType());
                _luxmeter.setText(toEditM.getLuxmeter());
                _testMedium.setText(toEditM.getTestMedium());
                _demagnetization.setText(toEditM.getDemagnetization());
                _MheatTreatment.setText(toEditM.getHeatTreatment());
                _surfaceTemperature.setText(toEditM.getSurfaceTemperature());
                _gauss.setText(toEditM.getGaussFieldStrength());
                _MsurfaceCondition2.setText(toEditM.getSurfaceCondition2());
                _identification.setText(toEditM.getIdentificationOfLightEquip());
                _liftingTestDate.setText(toEditM.getLiftingTest());
                
                _butt.setSelected(toEditM.isButtWeld());
                _fillet.setSelected(toEditM.isFilletWeld());
                _standardDeviations.setText(toEditM.getStandardDeviations());
                _MinspectionDates.setText(toEditM.getInspectionDates());
                _Mdescription.setText(toEditM.getDescriptionOfAttachments());
                
                ArrayList<MagneticInspectionResult> magneticResults = toEditM.getInspectionResults();
                JTextField[][] magResults = new JTextField[10][8];
                magResults[0] = magneticResult1;
                magResults[1] = magneticResult2;
                magResults[2] = magneticResult3;
                magResults[3] = magneticResult4;
                magResults[4] = magneticResult5;
                magResults[5] = magneticResult6;
                magResults[6] = magneticResult7;
                magResults[7] = magneticResult8;
                magResults[8] = magneticResult9;
                magResults[9] = magneticResult10;
                
                for (int i = 0; i < magneticResults.size(); i++) {
                    MagneticInspectionResult temp = magneticResults.get(i);
                    System.out.println(i + ": " + temp.toString());
                    magResults[i][0].setText(temp.getWeldPieceNo());
                    magResults[i][1].setText(temp.getTestLength());
                    magResults[i][2].setText(temp.getWeldingProcess());
                    magResults[i][3].setText(temp.getThickness());
                    magResults[i][4].setText(temp.getDiameter());
                    magResults[i][5].setText(temp.getDefectType());
                    magResults[i][6].setText(temp.getDefectLocation());
                    magResults[i][7].setText(temp.getResult());
                }                
                
                _MoperatorName.setText(this.operator.getName() + " " + this.operator.getLastname());
                _MevaluatorName.setText(this.evaluator.getName() + " " + this.evaluator.getLastname());
                _MconfirmationName.setText(this.confirmator.getName() + " " + this.confirmator.getLastname());
                _MoperatorLevel.setText(Integer.toString(this.operator.getLevel()));
                _MevaluatorLevel.setText(Integer.toString(this.evaluator.getLevel()));
                _MconfirmationLevel.setText(Integer.toString(this.confirmator.getLevel()));
                _Mdate1.setText(Common.date_toStringReverse(this.reportDate, "."));
                _Mdate2.setText(Common.date_toStringReverse(this.reportDate, "."));
                _Mdate3.setText(Common.date_toStringReverse(this.reportDate, "."));
            }
        }
    }
    
    private boolean everyThingIsOkayR() {
        boolean res = true;
        if (_inspectionPlace.getText().trim().equals("") || _inspectionPlace.getText().trim().length() < 3 || _inspectionPlace.getText().trim().length() > 32) {
            _inspectionPlace.setBackground(Color.pink);
            _inspectionPlace.setToolTipText("Zorunlu alan, 3 - 32 Harften oluşmalı");
            return false;
        }
        else {
            _inspectionPlace.setBackground(Color.white);
            _inspectionPlace.setToolTipText(null);
        }
        
        if (_inspectionClass.getText().trim().equals("") || _inspectionClass.getText().trim().length() > 32) {
            _inspectionClass.setBackground(Color.pink);
            _inspectionClass.setToolTipText("Zorunlu alan, en fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _inspectionClass.setBackground(Color.white);
            _inspectionClass.setToolTipText(null);
        }
        
        if (_evaluationStandard.getText().trim().equals("") || _evaluationStandard.getText().trim().length() > 32) {
            _evaluationStandard.setBackground(Color.pink);
            _evaluationStandard.setToolTipText("Zorunlu alan, en fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _evaluationStandard.setBackground(Color.white);
            _evaluationStandard.setToolTipText(null);
        }
        
        if (_inspectionProcedure.getText().trim().equals("") || _inspectionProcedure.getText().trim().length() > 32) {
            _inspectionProcedure.setBackground(Color.pink);
            _inspectionProcedure.setToolTipText("Zorunlu alan, en fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _inspectionProcedure.setBackground(Color.white);
            _inspectionProcedure.setToolTipText(null);
        }
        
        if (_inspectionScope.getText().trim().equals("") || _inspectionScope.getText().trim().length() > 32) {
            _inspectionScope.setBackground(Color.pink);
            _inspectionScope.setToolTipText("Zorunlu alan, en fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _inspectionScope.setBackground(Color.white);
            _inspectionScope.setToolTipText(null);
        }
        
        if (_drawingNo.getText().trim().equals("") || _drawingNo.getText().trim().length() > 32) {
            _drawingNo.setBackground(Color.pink);
            _drawingNo.setToolTipText("Zorunlu alan, en fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _drawingNo.setBackground(Color.white);
            _drawingNo.setToolTipText(null);
        }
        
        if (_page.getText().trim().equals("") || _page.getText().trim().length() > 8) {
            _page.setBackground(Color.pink);
            _page.setToolTipText("Zorunlu alan, en fazla 8 Harften oluşmalı");
            return false;
        }
        else {
            _page.setBackground(Color.white);
            _page.setToolTipText(null);
        }
        
        if (_reportNo.getText().trim().equals("") || _reportNo.getText().trim().length() > 16) {
            _reportNo.setBackground(Color.pink);
            _reportNo.setToolTipText("Zorunlu alan, en fazla 16 Harften oluşmalı");
            return false;
        }
        else if (process == 1) {
            if (!ReportManagement.reportNumberAccepted(_reportNo.getText().trim(), this.theCustomer.getName())) {
                _reportNo.setBackground(Color.pink);
                _reportNo.setToolTipText("Girdiğiniz rapor numarası bu firma için daha önce kullanılmıştır!");
                return false;
            }
            else {
                _reportNo.setBackground(Color.white);
                _reportNo.setToolTipText(null);
            }
        }
        else {
            _reportNo.setBackground(Color.white);
            _reportNo.setToolTipText(null);
        }
        
        if (_equipment.getText().trim().equals("") || _equipment.getText().trim().length() > 128) {
            _equipment.setBackground(Color.pink);
            _equipment.setToolTipText("Zorunlu alan, en fazla 128 Harften oluşmalı");
            return false;
        }
        else {
            _equipment.setBackground(Color.white);
            _equipment.setToolTipText(null);
        }
        
        if (_usedDevice.getText().trim().equals("") || _usedDevice.getText().trim().length() > 32) {
            _usedDevice.setBackground(Color.pink);
            _usedDevice.setToolTipText("Zorunlu alan, en fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _usedDevice.setBackground(Color.white);
            _usedDevice.setToolTipText(null);
        }
        
        if (_heatTreatment.getText().trim().length() > 32) {
            _heatTreatment.setBackground(Color.pink);
            _heatTreatment.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _heatTreatment.setBackground(Color.white);
            _heatTreatment.setToolTipText(null);
        }
        
        if (_filmBrand.getText().trim().length() > 32) {
            _filmBrand.setBackground(Color.pink);
            _filmBrand.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _filmBrand.setBackground(Color.white);
            _filmBrand.setToolTipText(null);
        }
        
        if (_focalSpotSize.getText().trim().equals("") || _focalSpotSize.getText().trim().length() > 32) {
            _focalSpotSize.setBackground(Color.pink);
            _focalSpotSize.setToolTipText("Zorunlu alan, en fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _focalSpotSize.setBackground(Color.white);
            _focalSpotSize.setToolTipText(null);
        }
        
        if (_exposureTime.getText().trim().equals("") || _exposureTime.getText().trim().length() > 32) {
            _exposureTime.setBackground(Color.pink);
            _exposureTime.setToolTipText("Zorunlu alan, en fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _exposureTime.setBackground(Color.white);
            _exposureTime.setToolTipText(null);
        }
        
        if (_filmFocusDistance.getText().trim().equals("") || _filmFocusDistance.getText().trim().length() > 32) {
            _filmFocusDistance.setBackground(Color.pink);
            _filmFocusDistance.setToolTipText("Zorunlu alan, en fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _filmFocusDistance.setBackground(Color.white);
            _filmFocusDistance.setToolTipText(null);
        }
        
        if (_pbScreens.getText().trim().equals("") || _pbScreens.getText().trim().length() > 32) {
            _pbScreens.setBackground(Color.pink);
            _pbScreens.setToolTipText("Zorunlu alan, en fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _pbScreens.setBackground(Color.white);
            _pbScreens.setToolTipText(null);
        }
        
        if (_filters.getText().trim().length() > 32) {
            _filters.setBackground(Color.pink);
            _filters.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _filters.setBackground(Color.white);
            _filters.setToolTipText(null);
        }
        
        if (_heatTreatment.getText().trim().length() > 32) {
            _heatTreatment.setBackground(Color.pink);
            _heatTreatment.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _heatTreatment.setBackground(Color.white);
            _heatTreatment.setToolTipText(null);
        }
        
        if (!Verification.isNumber(_f1012.getText().trim()) && !_f1012.getText().trim().equals("")) {
            _f1012.setBackground(Color.pink);
            _f1012.setToolTipText("Bu alan sayı olmalı!");
            return false;
        }
        else {
            _f1012.setBackground(Color.white);
            _f1012.setToolTipText(null);
        }
        
        if (!Verification.isNumber(_f1016.getText().trim()) && !_f1016.getText().trim().equals("")) {
            _f1016.setBackground(Color.pink);
            _f1016.setToolTipText("Bu alan sayı olmalı!");
            return false;
        }
        else {
            _f1016.setBackground(Color.white);
            _f1016.setToolTipText(null);
        }
        
        if (!Verification.isNumber(_f1024.getText().trim()) && !_f1024.getText().trim().equals("")) {
            _f1024.setBackground(Color.pink);
            _f1024.setToolTipText("Bu alan sayı olmalı!");
            return false;
        }
        else {
            _f1024.setBackground(Color.white);
            _f1024.setToolTipText(null);
        }
        
        if (!Verification.isNumber(_f1036.getText().trim()) && !_f1036.getText().trim().equals("")) {
            _f1036.setBackground(Color.pink);
            _f1036.setToolTipText("Bu alan sayı olmalı!");
            return false;
        }
        else {
            _f1036.setBackground(Color.white);
            _f1036.setToolTipText(null);
        }
        
        if (!Verification.isNumber(_f1048.getText().trim()) && !_f1048.getText().trim().equals("")) {
            _f1048.setBackground(Color.pink);
            _f1048.setToolTipText("Bu alan sayı olmalı!");
            return false;
        }
        else {
            _f1048.setBackground(Color.white);
            _f1048.setToolTipText(null);
        }
        
        if (!Verification.isNumber(_f3040.getText().trim()) && !_f3040.getText().trim().equals("")) {
            _f3040.setBackground(Color.pink);
            _f3040.setToolTipText("Bu alan sayı olmalı!");
            return false;
        }
        else {
            _f3040.setBackground(Color.white);
            _f3040.setToolTipText(null);
        }
        
        if (!Verification.isNumber(_suitableFilm.getText().trim()) && !_suitableFilm.getText().trim().equals("")) {
            _suitableFilm.setBackground(Color.pink);
            _suitableFilm.setToolTipText("Bu alan sayı olmalı!");
            return false;
        }
        else {
            _suitableFilm.setBackground(Color.white);
            _suitableFilm.setToolTipText(null);
        }
        
        if (!Verification.isNumber(_repairFilm.getText().trim()) && !_repairFilm.getText().trim().equals("")) {
            _repairFilm.setBackground(Color.pink);
            _repairFilm.setToolTipText("Bu alan sayı olmalı!");
            return false;
        }
        else {
            _repairFilm.setBackground(Color.white);
            _repairFilm.setToolTipText(null);
        }
        
        if (_description.getText().trim().length() > 256) {
            _description.setBackground(Color.pink);
            _description.setToolTipText("En fazla 256 Harften oluşmalı");
            return false;
        }
        else {
            _description.setBackground(Color.white);
            _description.setToolTipText(null);
        }
        
        if (_shootingArea1.getText().trim().length() > 32) {
            _shootingArea1.setBackground(Color.pink);
            _shootingArea1.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else if (_shootingArea1.getText().trim().equals("")) {
            for (JTextField t : this.radiographicResult1) {
                if (!t.getText().trim().equals("")) {
                    _shootingArea1.setBackground(Color.pink);
                    _shootingArea1.setToolTipText("Zorunlu alan");
                    return false;
                }
                else {
                    _shootingArea1.setBackground(Color.white);
                    _shootingArea1.setToolTipText(null);
                }
            }
        }
        else {
            _shootingArea1.setBackground(Color.white);
            _shootingArea1.setToolTipText(null);
        }
        
        for (int i = 0; i < this.radiographicResult1.length; i++) {
            JTextField t = this.radiographicResult1[i];
            if (i > 9 && i < 16) {
                if (!t.getText().trim().equals("") && !Verification.isNumber(t.getText().trim())) {
                    t.setBackground(Color.pink);
                    t.setToolTipText("Bu alanda sadece sayi girilebilir!");
                    return false;
                }
                else {
                    t.setBackground(Color.white);
                    t.setToolTipText(null);
                }
            }
            else {
                if (t.getText().trim().length() > 32) {
                    t.setBackground(Color.pink);
                    t.setToolTipText("En fazla 32 Harften oluşmalı");
                    return false;
                }
                else {
                    t.setBackground(Color.white);
                    t.setToolTipText(null);
                }
            }
        }
        
        if (_shootingArea2.getText().trim().length() > 32) {
            _shootingArea2.setBackground(Color.pink);
            _shootingArea2.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else if (_shootingArea2.getText().trim().equals("")) {
            for (JTextField t : this.radiographicResult2) {
                if (!t.getText().trim().equals("")) {
                    _shootingArea2.setBackground(Color.pink);
                    _shootingArea2.setToolTipText("Zorunlu alan");
                    return false;
                }
                else {
                    _shootingArea2.setBackground(Color.white);
                    _shootingArea2.setToolTipText(null);
                }
            }
        }
        else {
            _shootingArea2.setBackground(Color.white);
            _shootingArea2.setToolTipText(null);
        }
        
        for (int i = 0; i < this.radiographicResult2.length; i++) {
            JTextField t = this.radiographicResult2[i];
            if (i > 9 && i < 16) {
                if (!t.getText().trim().equals("") && !Verification.isNumber(t.getText().trim())) {
                    t.setBackground(Color.pink);
                    t.setToolTipText("Bu alanda sadece sayi girilebilir!");
                    return false;
                }
                else {
                    t.setBackground(Color.white);
                    t.setToolTipText(null);
                }
            }
            else {
                if (t.getText().trim().length() > 32) {
                    t.setBackground(Color.pink);
                    t.setToolTipText("En fazla 32 Harften oluşmalı");
                    return false;
                }
                else {
                    t.setBackground(Color.white);
                    t.setToolTipText(null);
                }
            }
        }
        
        if (_shootingArea3.getText().trim().length() > 32) {
            _shootingArea3.setBackground(Color.pink);
            _shootingArea3.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else if (_shootingArea3.getText().trim().equals("")) {
            for (JTextField t : this.radiographicResult3) {
                if (!t.getText().trim().equals("")) {
                    _shootingArea3.setBackground(Color.pink);
                    _shootingArea3.setToolTipText("Zorunlu alan");
                    return false;
                }
                else {
                    _shootingArea3.setBackground(Color.white);
                    _shootingArea3.setToolTipText(null);
                }
            }
        }
        else {
            _shootingArea3.setBackground(Color.white);
            _shootingArea3.setToolTipText(null);
        }
        
        for (int i = 0; i < this.radiographicResult3.length; i++) {
            JTextField t = this.radiographicResult3[i];
            if (i > 9 && i < 16) {
                if (!t.getText().trim().equals("") && !Verification.isNumber(t.getText().trim())) {
                    t.setBackground(Color.pink);
                    t.setToolTipText("Bu alanda sadece sayi girilebilir!");
                    return false;
                }
                else {
                    t.setBackground(Color.white);
                    t.setToolTipText(null);
                }
            }
            else {
                if (t.getText().trim().length() > 32) {
                    t.setBackground(Color.pink);
                    t.setToolTipText("En fazla 32 Harften oluşmalı");
                    return false;
                }
                else {
                    t.setBackground(Color.white);
                    t.setToolTipText(null);
                }
            }
        }
        
        if (_shootingArea4.getText().trim().length() > 32) {
            _shootingArea4.setBackground(Color.pink);
            _shootingArea4.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else if (_shootingArea4.getText().trim().equals("")) {
            for (JTextField t : this.radiographicResult4) {
                if (!t.getText().trim().equals("")) {
                    _shootingArea4.setBackground(Color.pink);
                    _shootingArea4.setToolTipText("Zorunlu alan");
                    return false;
                }
                else {
                    _shootingArea4.setBackground(Color.white);
                    _shootingArea4.setToolTipText(null);
                }
            }
        }
        else {
            _shootingArea4.setBackground(Color.white);
            _shootingArea4.setToolTipText(null);
        }
        
        for (int i = 0; i < this.radiographicResult4.length; i++) {
            JTextField t = this.radiographicResult4[i];
            if (i > 9 && i < 16) {
                if (!t.getText().trim().equals("") && !Verification.isNumber(t.getText().trim())) {
                    t.setBackground(Color.pink);
                    t.setToolTipText("Bu alanda sadece sayi girilebilir!");
                    return false;
                }
                else {
                    t.setBackground(Color.white);
                    t.setToolTipText(null);
                }
            }
            else {
                if (t.getText().trim().length() > 32) {
                    t.setBackground(Color.pink);
                    t.setToolTipText("En fazla 32 Harften oluşmalı");
                    return false;
                }
                else {
                    t.setBackground(Color.white);
                    t.setToolTipText(null);
                }
            }
        }
        
        if (_shootingArea5.getText().trim().length() > 32) {
            _shootingArea5.setBackground(Color.pink);
            _shootingArea5.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else if (_shootingArea5.getText().trim().equals("")) {
            for (JTextField t : this.radiographicResult5) {
                if (!t.getText().trim().equals("")) {
                    _shootingArea5.setBackground(Color.pink);
                    _shootingArea5.setToolTipText("Zorunlu alan");
                    return false;
                }
                else {
                    _shootingArea5.setBackground(Color.white);
                    _shootingArea5.setToolTipText(null);
                }
            }
        }
        else {
            _shootingArea5.setBackground(Color.white);
            _shootingArea5.setToolTipText(null);
        }
        
        for (int i = 0; i < this.radiographicResult5.length; i++) {
            JTextField t = this.radiographicResult5[i];
            if (i > 9 && i < 16) {
                if (!t.getText().trim().equals("") && !Verification.isNumber(t.getText().trim())) {
                    t.setBackground(Color.pink);
                    t.setToolTipText("Bu alanda sadece sayi girilebilir!");
                    return false;
                }
                else {
                    t.setBackground(Color.white);
                    t.setToolTipText(null);
                }
            }
            else {
                if (t.getText().trim().length() > 32) {
                    t.setBackground(Color.pink);
                    t.setToolTipText("En fazla 32 Harften oluşmalı");
                    return false;
                }
                else {
                    t.setBackground(Color.white);
                    t.setToolTipText(null);
                }
            }
        }
        
        return res;
    }
    
    private boolean everyThingIsOkayM() {
        boolean res = true;
        if (_MinspectionPlace.getText().trim().equals("") || _MinspectionPlace.getText().trim().length() < 3 || _MinspectionPlace.getText().trim().length() > 32) {
            _MinspectionPlace.setBackground(Color.pink);
            _MinspectionPlace.setToolTipText("Zorunlu alan, 3 - 32 Harften oluşmalı");
            return false;
        }
        else {
            _MinspectionPlace.setBackground(Color.white);
            _MinspectionPlace.setToolTipText(null);
        }
        
        if (_MinspectionStandard.getText().trim().equals("") || _MinspectionStandard.getText().trim().length() > 32) {
            _MinspectionStandard.setBackground(Color.pink);
            _MinspectionStandard.setToolTipText("Zorunlu alan, en fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _MinspectionStandard.setBackground(Color.white);
            _MinspectionStandard.setToolTipText(null);
        }
        
        if (_MevaluationStandard.getText().trim().equals("") || _MevaluationStandard.getText().trim().length() > 32) {
            _MevaluationStandard.setBackground(Color.pink);
            _MevaluationStandard.setToolTipText("Zorunlu alan, en fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _MevaluationStandard.setBackground(Color.white);
            _MevaluationStandard.setToolTipText(null);
        }
        
        if (_MinspectionProcedure.getText().trim().equals("") || _MinspectionProcedure.getText().trim().length() > 32) {
            _MinspectionProcedure.setBackground(Color.pink);
            _MinspectionProcedure.setToolTipText("Zorunlu alan, en fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _MinspectionProcedure.setBackground(Color.white);
            _MinspectionProcedure.setToolTipText(null);
        }
        
        if (_MinspectionScope.getText().trim().equals("") || _MinspectionScope.getText().trim().length() > 32) {
            _MinspectionScope.setBackground(Color.pink);
            _MinspectionScope.setToolTipText("Zorunlu alan, en fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _MinspectionScope.setBackground(Color.white);
            _MinspectionScope.setToolTipText(null);
        }
        
        if (_MdrawingNo.getText().trim().equals("") || _MdrawingNo.getText().trim().length() > 32) {
            _MdrawingNo.setBackground(Color.pink);
            _MdrawingNo.setToolTipText("Zorunlu alan, en fazla 32 Harften oluşmalı");
            return false;
        }
        else {
            _MdrawingNo.setBackground(Color.white);
            _MdrawingNo.setToolTipText(null);
        }
        
        if (_Mpage.getText().trim().equals("") || _Mpage.getText().trim().length() > 8) {
            _Mpage.setBackground(Color.pink);
            _Mpage.setToolTipText("Zorunlu alan, en fazla 8 Harften oluşmalı");
            return false;
        }
        else {
            _Mpage.setBackground(Color.white);
            _Mpage.setToolTipText(null);
        }
        
        if (_MreportNo.getText().trim().equals("") || _MreportNo.getText().trim().length() > 16) {
            _MreportNo.setBackground(Color.pink);
            _MreportNo.setToolTipText("Zorunlu alan, en fazla 16 Harften oluşmalı");
            return false;
        }
        else if (process == 1) {
            if (!ReportManagement.reportNumberAccepted(_MreportNo.getText().trim(), this.theCustomer.getName())) {
                _MreportNo.setBackground(Color.pink);
                _MreportNo.setToolTipText("Girdiğiniz rapor numarası bu firma için daha önce kullanılmıştır!");
                return false;
            }
        }
        else {
            _MreportNo.setBackground(Color.white);
            _MreportNo.setToolTipText(null);
        }
        
        if (_Mequipment.getText().trim().equals("") || _Mequipment.getText().trim().length() > 128) {
            _Mequipment.setBackground(Color.pink);
            _Mequipment.setToolTipText("Zorunlu alan, en fazla 128 Harften oluşmalı");
            return false;
        }
        else {
            _Mequipment.setBackground(Color.white);
            _Mequipment.setToolTipText(null);
        }
        
        
        
        if (_Mdescription.getText().trim().length() > 256) {
            _Mdescription.setBackground(Color.pink);
            _Mdescription.setToolTipText("En fazla 256 Harften oluşmalı");
            return false;
        }
        else {
            _Mdescription.setBackground(Color.white);
            _Mdescription.setToolTipText(null);
        }
       
        if (_pieceNo1.getText().trim().length() > 32) {
            _pieceNo1.setBackground(Color.pink);
            _pieceNo1.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else if (_pieceNo1.getText().trim().equals("")) {
            for (JTextField t : this.magneticResult1) {
                if (!t.getText().trim().equals("")) {
                    _pieceNo1.setBackground(Color.pink);
                    _pieceNo1.setToolTipText("Zorunlu alan");
                    return false;
                }
                else {
                    _pieceNo1.setBackground(Color.white);
                    _pieceNo1.setToolTipText(null);
                }
            }
        }
        else {
            _pieceNo1.setBackground(Color.white);
            _pieceNo1.setToolTipText(null);
        }
        
        for (JTextField t : this.magneticResult1) {
            if (t.getText().trim().length() > 32) {
                t.setBackground(Color.pink);
                t.setToolTipText("En fazla 32 Harften oluşmalı");
                return false;
            }
            else {
                t.setBackground(Color.white);
                t.setToolTipText(null);
            }
        }
        
        if (_pieceNo2.getText().trim().length() > 32) {
            _pieceNo2.setBackground(Color.pink);
            _pieceNo2.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else if (_pieceNo2.getText().trim().equals("")) {
            for (JTextField t : this.magneticResult2) {
                if (!t.getText().trim().equals("")) {
                    _pieceNo2.setBackground(Color.pink);
                    _pieceNo2.setToolTipText("Zorunlu alan");
                    return false;
                }
                else {
                    _pieceNo2.setBackground(Color.white);
                    _pieceNo2.setToolTipText(null);
                }
            }
        }
        else {
            _pieceNo2.setBackground(Color.white);
            _pieceNo2.setToolTipText(null);
        }
        
        for (JTextField t : this.magneticResult2) {
            if (t.getText().trim().length() > 32) {
                t.setBackground(Color.pink);
                t.setToolTipText("En fazla 32 Harften oluşmalı");
                return false;
            }
            else {
                t.setBackground(Color.white);
                t.setToolTipText(null);
            }
        }
        
        if (_pieceNo3.getText().trim().length() > 32) {
            _pieceNo3.setBackground(Color.pink);
            _pieceNo3.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else if (_pieceNo3.getText().trim().equals("")) {
            for (JTextField t : this.magneticResult3) {
                if (!t.getText().trim().equals("")) {
                    _pieceNo3.setBackground(Color.pink);
                    _pieceNo3.setToolTipText("Zorunlu alan");
                    return false;
                }
                else {
                    _pieceNo3.setBackground(Color.white);
                    _pieceNo3.setToolTipText(null);
                }
            }
        }
        else {
            _pieceNo3.setBackground(Color.white);
            _pieceNo3.setToolTipText(null);
        }
        
        for (JTextField t : this.magneticResult3) {
            if (t.getText().trim().length() > 32) {
                t.setBackground(Color.pink);
                t.setToolTipText("En fazla 32 Harften oluşmalı");
                return false;
            }
            else {
                t.setBackground(Color.white);
                t.setToolTipText(null);
            }
        }
        
        if (_pieceNo4.getText().trim().length() > 32) {
            _pieceNo4.setBackground(Color.pink);
            _pieceNo4.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else if (_pieceNo4.getText().trim().equals("")) {
            for (JTextField t : this.magneticResult4) {
                if (!t.getText().trim().equals("")) {
                    _pieceNo4.setBackground(Color.pink);
                    _pieceNo4.setToolTipText("Zorunlu alan");
                    return false;
                }
                else {
                    _pieceNo4.setBackground(Color.white);
                    _pieceNo4.setToolTipText(null);
                }
            }
        }
        else {
            _pieceNo4.setBackground(Color.white);
            _pieceNo4.setToolTipText(null);
        }
        
        for (JTextField t : this.magneticResult4) {
            if (t.getText().trim().length() > 32) {
                t.setBackground(Color.pink);
                t.setToolTipText("En fazla 32 Harften oluşmalı");
                return false;
            }
            else {
                t.setBackground(Color.white);
                t.setToolTipText(null);
            }
        }
        
        if (_pieceNo5.getText().trim().length() > 32) {
            _pieceNo5.setBackground(Color.pink);
            _pieceNo5.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else if (_pieceNo5.getText().trim().equals("")) {
            for (JTextField t : this.magneticResult5) {
                if (!t.getText().trim().equals("")) {
                    _pieceNo5.setBackground(Color.pink);
                    _pieceNo5.setToolTipText("Zorunlu alan");
                    return false;
                }
                else {
                    _pieceNo5.setBackground(Color.white);
                    _pieceNo5.setToolTipText(null);
                }
            }
        }
        else {
            _pieceNo5.setBackground(Color.white);
            _pieceNo5.setToolTipText(null);
        }
        
        for (JTextField t : this.magneticResult5) {
            if (t.getText().trim().length() > 32) {
                t.setBackground(Color.pink);
                t.setToolTipText("En fazla 32 Harften oluşmalı");
                return false;
            }
            else {
                t.setBackground(Color.white);
                t.setToolTipText(null);
            }
        }
        
        if (_pieceNo6.getText().trim().length() > 32) {
            _pieceNo6.setBackground(Color.pink);
            _pieceNo6.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else if (_pieceNo6.getText().trim().equals("")) {
            for (JTextField t : this.magneticResult6) {
                if (!t.getText().trim().equals("")) {
                    _pieceNo6.setBackground(Color.pink);
                    _pieceNo6.setToolTipText("Zorunlu alan");
                    return false;
                }
                else {
                    _pieceNo6.setBackground(Color.white);
                    _pieceNo6.setToolTipText(null);
                }
            }
        }
        else {
            _pieceNo6.setBackground(Color.white);
            _pieceNo6.setToolTipText(null);
        }
        
        for (JTextField t : this.magneticResult6) {
            if (t.getText().trim().length() > 32) {
                t.setBackground(Color.pink);
                t.setToolTipText("En fazla 32 Harften oluşmalı");
                return false;
            }
            else {
                t.setBackground(Color.white);
                t.setToolTipText(null);
            }
        }
        
        if (_pieceNo7.getText().trim().length() > 32) {
            _pieceNo7.setBackground(Color.pink);
            _pieceNo7.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else if (_pieceNo7.getText().trim().equals("")) {
            for (JTextField t : this.magneticResult7) {
                if (!t.getText().trim().equals("")) {
                    _pieceNo7.setBackground(Color.pink);
                    _pieceNo7.setToolTipText("Zorunlu alan");
                    return false;
                }
                else {
                    _pieceNo7.setBackground(Color.white);
                    _pieceNo7.setToolTipText(null);
                }
            }
        }
        else {
            _pieceNo7.setBackground(Color.white);
            _pieceNo7.setToolTipText(null);
        }
        
        for (JTextField t : this.magneticResult7) {
            if (t.getText().trim().length() > 32) {
                t.setBackground(Color.pink);
                t.setToolTipText("En fazla 32 Harften oluşmalı");
                return false;
            }
            else {
                t.setBackground(Color.white);
                t.setToolTipText(null);
            }
        }
        
        if (_pieceNo8.getText().trim().length() > 32) {
            _pieceNo8.setBackground(Color.pink);
            _pieceNo8.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else if (_pieceNo8.getText().trim().equals("")) {
            for (JTextField t : this.magneticResult8) {
                if (!t.getText().trim().equals("")) {
                    _pieceNo8.setBackground(Color.pink);
                    _pieceNo8.setToolTipText("Zorunlu alan");
                    return false;
                }
                else {
                    _pieceNo8.setBackground(Color.white);
                    _pieceNo8.setToolTipText(null);
                }
            }
        }
        else {
            _pieceNo8.setBackground(Color.white);
            _pieceNo8.setToolTipText(null);
        }
        
        for (JTextField t : this.magneticResult8) {
            if (t.getText().trim().length() > 32) {
                t.setBackground(Color.pink);
                t.setToolTipText("En fazla 32 Harften oluşmalı");
                return false;
            }
            else {
                t.setBackground(Color.white);
                t.setToolTipText(null);
            }
        }
        
        if (_pieceNo9.getText().trim().length() > 32) {
            _pieceNo9.setBackground(Color.pink);
            _pieceNo9.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else if (_pieceNo9.getText().trim().equals("")) {
            for (JTextField t : this.magneticResult9) {
                if (!t.getText().trim().equals("")) {
                    _pieceNo9.setBackground(Color.pink);
                    _pieceNo9.setToolTipText("Zorunlu alan");
                    return false;
                }
                else {
                    _pieceNo9.setBackground(Color.white);
                    _pieceNo9.setToolTipText(null);
                }
            }
        }
        else {
            _pieceNo9.setBackground(Color.white);
            _pieceNo9.setToolTipText(null);
        }
        
        for (JTextField t : this.magneticResult9) {
            if (t.getText().trim().length() > 32) {
                t.setBackground(Color.pink);
                t.setToolTipText("En fazla 32 Harften oluşmalı");
                return false;
            }
            else {
                t.setBackground(Color.white);
                t.setToolTipText(null);
            }
        }
        
        if (_pieceNo10.getText().trim().length() > 32) {
            _pieceNo10.setBackground(Color.pink);
            _pieceNo10.setToolTipText("En fazla 32 Harften oluşmalı");
            return false;
        }
        else if (_pieceNo10.getText().trim().equals("")) {
            for (JTextField t : this.magneticResult10) {
                if (!t.getText().trim().equals("")) {
                    _pieceNo10.setBackground(Color.pink);
                    _pieceNo10.setToolTipText("Zorunlu alan");
                    return false;
                }
                else {
                    _pieceNo10.setBackground(Color.white);
                    _pieceNo10.setToolTipText(null);
                }
            }
        }
        else {
            _pieceNo10.setBackground(Color.white);
            _pieceNo10.setToolTipText(null);
        }
        
        for (JTextField t : this.magneticResult10) {
            if (t.getText().trim().length() > 32) {
                t.setBackground(Color.pink);
                t.setToolTipText("En fazla 32 Harften oluşmalı");
                return false;
            }
            else {
                t.setBackground(Color.white);
                t.setToolTipText(null);
            }
        }
        
        return res;
    }
    
    private void message (int done) {
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        String func = "Kayit";
        if (process == 2) {
            func = "Güncelleme";
        }
        else if (process == 3) {
            func = "Excel Dosyasi oluşturma";
        }
        if (done == 1) {
            JOptionPane.showMessageDialog(dialog, func  + " işlemi başarıyla tamamlanmıştır", "Başarılı İşlem", JOptionPane.PLAIN_MESSAGE);
            if (process == 3) {
                process = 2;
            }
            else if (process == 1) {
                jLabel2.setEnabled(false);
                jLabel1.setEnabled(true);
                jLabel4.setEnabled(true);
                process = 2;
            }
        }
        else if (done == 0) {
            JOptionPane.showMessageDialog(dialog, "Girdiğiniz rapor numarasi bu firma için daha önce kullanıldı!", "Hatalı İşlem", JOptionPane.PLAIN_MESSAGE);
            if (process == 3) { process = 2;}
        }
        else {
            JOptionPane.showMessageDialog(dialog, "Veri tabanına bağlanırken hata oluştu!, Lütfen tekrar deneyin.", "Hatalı Bağlantı", JOptionPane.PLAIN_MESSAGE);
            try {
                DatabaseManagement.con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
            DatabaseManagement.con = DatabaseManagement.connect();
            if (process == 3) { process = 2;}
        }
    }
    
    public void setValues(int element, Customer cus, String[] s, Employee[] e, MagneticEquipment mq, RadiographicEquipment rq, Date date) {
        switch(element) {
            case 0:
                this.theCustomer = cus;
                if (equip1 != null) {
                    _Mcustomer.setText(cus.getName());
                    _MinspectionPlace.setText(cus.getAddress());
                }
                else {
                    _customer.setText(cus.getName());
                    _inspectionPlace.setText(cus.getAddress());
                }

                break;
            case 1:
                if (equip1 != null) {
                    _MorderNo.setText(s[0]);
                }
                else {
                    _orderNo.setText(s[0]);
                }
                
                
                break;
            case 2:
                if (equip1 != null) {
                    _MofferNo.setText(s[1]);
                }
                else {
                    _offerNo.setText(s[1]);
                }
                
                
                break;
            case 3:
                this.operator = e[0];
                this.operator_id = PersonManagement.getPersonId((Person) operator);
                if (equip1 != null) {
                    _MoperatorName.setText(operator.getName() + " " + operator.getLastname());
                    _MoperatorLevel.setText(Integer.toString(operator.getLevel()));
                }
                else {
                    _operatorName.setText(operator.getName() + " " + operator.getLastname());
                    _operatorLevel.setText(Integer.toString(operator.getLevel()));
                }
                break;
                
                
            case 4:
                this.evaluator = e[1];
                this.evaluator_id = PersonManagement.getPersonId((Person) evaluator);
                if (equip1 != null) {
                    _MevaluatorName.setText(evaluator.getName() + " " + evaluator.getLastname());
                    _MevaluatorLevel.setText(Integer.toString(evaluator.getLevel()));
                }
                else {
                    _evaluatorName.setText(evaluator.getName() + " " + evaluator.getLastname());
                    _evaluatorLevel.setText(Integer.toString(evaluator.getLevel()));
                }
                break;
                
                
            case 5:
                this.confirmator = e[2];
                this.confirmation_id = PersonManagement.getPersonId((Person) confirmator);
                if (equip1 != null) {
                    _MconfirmationName.setText(confirmator.getName() + " " + confirmator.getLastname());
                    _MconfirmationLevel.setText(Integer.toString(confirmator.getLevel()));
                }
                else {
                    _confirmationName.setText(confirmator.getName() + " " + confirmator.getLastname());
                    _confirmationLevel.setText(Integer.toString(confirmator.getLevel()));
                }
                
                
                break;
            case 6:
                if (equip1 != null) {
                    this.equip1 = mq;
                    _poleDistance.setText(equip1.getPolesDistance());
                    _Mequipment.setText(equip1.getName());
                    _MPCarrier.setText(equip1.getMpCarrier());
                    _magTech.setText(equip1.getMagTechnic());
                    _UV.setText(equip1.getUvIntensity());
                    _distanceOfLight.setText(equip1.getLightDistance());
                }
                else {
                    this.equip2 = rq;
                    _equipment.setText(equip2.getName());
                    _usedDevice.setText("");
                    _ir192.setSelected(equip2.isIr192());
                    _se75.setSelected(equip2.isSe75());
                    _xRay.setSelected(equip2.isxRay());
                    _focalSpotSize.setText(equip2.getFocalSpotSize());
                    _exposureTime.setText(equip2.getExposureTime());
                    _filmFocusDistance.setText(equip2.getFilmFocusDistance());
                    _pbScreens.setText(equip2.getPbScreens());
                    _filters.setText(equip2.getFilters());
                }
                break;
                
                
            case 7:
                if (equip1 != null) {
                    _Mproject.setText(s[2]);
                }
                else {
                    _project.setText(s[2]);
                }
                break;
                
                
            case 8:
                if (equip1 != null) {
                    _MsurfaceCondition.setText(s[3]);
                }
                else {
                    _surfaceCondition.setText(s[3]);
                }
                break;
                
                
            case 9:
                if (equip1 != null) {
                    _MstageOfExamination.setText(s[4]);
                }
                else {
                    _stageOfExamination.setText(s[4]);
                }
                break;
                
                
                
            case 10:
                this.reportDate = date;
                if (equip1 != null) {
                    _MreportDate.setText(Common.date_toStringReverse(date, "."));
                    _MinspectionDates.setText(Common.date_toStringReverse(date, "."));
                    _Mdate1.setText(Common.date_toStringReverse(date, "."));
                    _Mdate2.setText(Common.date_toStringReverse(date, "."));
                    _Mdate3.setText(Common.date_toStringReverse(date, "."));
                }
                else {
                    _reportDate.setText(Common.date_toStringReverse(date, "."));
                    _inspectionDates.setText(Common.date_toStringReverse(date, "."));
                    _date1.setText(Common.date_toStringReverse(date, "."));
                    _date2.setText(Common.date_toStringReverse(date, "."));
                    _date3.setText(Common.date_toStringReverse(date, "."));
                }               
                break;
        }
    }
    
    private void add() {
        if (equip1 == null) {
            RadiographicReport toAdd = collectDataRadiographic();
            int res = ReportManagement.insertRadiographicReport(toAdd);
            message(res);
        }
        else {
            MagneticReport toAdd = collectDataMagnetic();
            int res = ReportManagement.insertMagneticReport(toAdd);
            message(res);
        }
    }
    
    private void update() {
        if (this.toEditR != null) {
            int report_id = 0;
            report_id = ReportManagement.getReportId(this.toEditR.getCustomer(), this.toEditR.getReportNumber());
            if (report_id == 0) {
                message(0);
            }
            else {
                RadiographicReport toEdit = collectDataRadiographic();
                int res = ReportManagement.updateRadiographicReport(toEdit, report_id);
                message(res);
            }
        }
        else {
            int report_id = 0;
            report_id = ReportManagement.getReportId(this.toEditM.getCustomer(), this.toEditM.getReportNumber());
            if (report_id == 0) {
                message(0);
            }
            else {
                MagneticReport toEdit = collectDataMagnetic();
                int res = ReportManagement.updateMagneticReport(toEdit, report_id);
                message(res);
            }
        }
    }
    
    private void printReport(JPanel panel, double a, double b) {
        PrinterJob print = PrinterJob.getPrinterJob();
        print.setJobName("Print Report");
        print.setPrintable(new Printable() {
            @Override
            public int print(Graphics grphcs, PageFormat pf, int i) throws PrinterException {
                if (i > 0) {
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D graphics = (Graphics2D) grphcs;
                graphics.translate(50, 30);
                graphics.scale(a, b);
                panel.paint(graphics);
                return Printable.PAGE_EXISTS;
            }
        });
        
        boolean returningResult = print.printDialog();
        if (returningResult) {
            try {
                print.print();
            } catch (PrinterException e){
                JOptionPane.showMessageDialog(this, "Hatalı İşlem" + e.getMessage());
            }
        }
    }
    
    private int exportExcelM(String path) {
        try {
            FileInputStream input = new FileInputStream("Data\\MagneticTemplate.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(input);
            XSSFSheet sheet = workbook.getSheetAt(0);
            //Report Information
            XSSFRow row = sheet.getRow(2);
            XSSFCell cell = row.getCell(3);
            cell.setCellValue(toEditM.getCustomer());
            cell = row.getCell(19);
            cell.setCellValue(toEditM.getInspectionProcedure());
            cell = row.getCell(26);
            cell.setCellValue(toEditM.getPage());
            
            row = sheet.getRow(3);
            cell = row.getCell(3);
            cell.setCellValue(toEditM.getProjectName());
            cell = row.getCell(19);
            cell.setCellValue(toEditM.getInspectionScope());
            cell = row.getCell(26);
            cell.setCellValue(toEditM.getReportNumber());
            
            row = sheet.getRow(4);
            cell = row.getCell(3);
            cell.setCellValue(toEditM.getInspectionPlace());
            cell = row.getCell(19);
            cell.setCellValue(toEditM.getDrawingNo());
            cell = row.getCell(26);
            cell.setCellValue(Common.date_toStringReverse(toEditM.getReportDate(), "."));
            
            row = sheet.getRow(5);
            cell = row.getCell(3);
            cell.setCellValue(toEditM.getInspectionClass());
            cell = row.getCell(19);
            cell.setCellValue(toEditM.getSurfaceCondition());
            cell = row.getCell(26);
            cell.setCellValue(toEditM.getOrderNumber());
            
            row = sheet.getRow(6);
            cell = row.getCell(3);
            cell.setCellValue(toEditM.getEvaluationStandard());
            cell = row.getCell(19);
            cell.setCellValue(toEditM.getStageOfExamination());
            cell = row.getCell(26);
            cell.setCellValue(toEditM.getOfferNumber());
            
            //Equipment information
            row = sheet.getRow(8);
            cell = row.getCell(4);
            cell.setCellValue(toEditM.getPoleDistance());
            cell = row.getCell(16);
            cell.setCellValue(toEditM.getExaminationArea());
            cell = row.getCell(25);
            cell.setCellValue(toEditM.getSurfaceTemperature());
            
            row = sheet.getRow(9);
            cell = row.getCell(4);
            cell.setCellValue(toEditM.getEquipment());
            cell = row.getCell(16);
            cell.setCellValue(toEditM.getCurrentType());
            cell = row.getCell(25);
            cell.setCellValue(toEditM.getGaussFieldStrength());
            
            row = sheet.getRow(10);
            cell = row.getCell(4);
            cell.setCellValue(toEditM.getMpCarrier());
            cell = row.getCell(16);
            cell.setCellValue(toEditM.getLuxmeter());
            
            row = sheet.getRow(11);
            cell = row.getCell(4);
            cell.setCellValue(toEditM.getMagTech());
            cell = row.getCell(16);
            cell.setCellValue(toEditM.getTestMedium());
            cell = row.getCell(25);
            cell.setCellValue(toEditM.getSurfaceCondition2());
            
            row = sheet.getRow(12);
            cell = row.getCell(4);
            cell.setCellValue(toEditM.getUvIntensity());
            cell = row.getCell(16);
            cell.setCellValue(toEditM.getDemagnetization());
            cell = row.getCell(25);
            cell.setCellValue(toEditM.getIdentificationOfLightEquip());
            
            row = sheet.getRow(13);
            cell = row.getCell(4);
            cell.setCellValue(toEditM.getDistanceOfLight());
            cell = row.getCell(16);
            cell.setCellValue(toEditM.getHeatTreatment());
            cell = row.getCell(25);
            cell.setCellValue(toEditM.getLiftingTest());
            
            row = sheet.getRow(14);
            cell = row.getCell(0);
            cell.setCellValue(toEditM.isButtWeld());
            cell = row.getCell(7);
            cell.setCellValue(toEditM.isFilletWeld());
            
            //General
            row = sheet.getRow(19);
            cell = row.getCell(7);
            cell.setCellValue(toEditM.getStandardDeviations());
            
            row = sheet.getRow(20);
            cell = row.getCell(7);
            cell.setCellValue(toEditM.getInspectionDates());
            
            row = sheet.getRow(21);
            cell = row.getCell(7);
            cell.setCellValue(toEditM.getDescriptionOfAttachments());
            //Results
            
            ArrayList<MagneticInspectionResult> reses = toEditM.getInspectionResults();
            for (int i = 24; i < (reses.size()+24); i++) {
                MagneticInspectionResult temp = reses.get(i-24);
                row = sheet.getRow(i);
                cell = row.getCell(1);
                cell.setCellValue(temp.getWeldPieceNo());
                cell = row.getCell(8);
                cell.setCellValue(temp.getTestLength());
                cell = row.getCell(11);
                cell.setCellValue(temp.getWeldingProcess());
                cell = row.getCell(17);
                cell.setCellValue(temp.getThickness());
                cell = row.getCell(18);
                cell.setCellValue(temp.getDiameter());
                cell = row.getCell(22);
                cell.setCellValue(temp.getDefectType());
                cell = row.getCell(24);
                cell.setCellValue(temp.getDefectLocation());
                cell = row.getCell(27);
                cell.setCellValue(temp.getResult());
            }
            //Employees
            row = sheet.getRow(35);
            cell = row.getCell(5);
            cell.setCellValue(_MoperatorName.getText().trim());
            cell = row.getCell(15);
            cell.setCellValue(_MevaluatorName.getText().trim());
            cell = row.getCell(20);
            cell.setCellValue(_MconfirmationName.getText().trim());
            
            row = sheet.getRow(36);
            cell = row.getCell(5);
            cell.setCellValue(Integer.toString(this.operator.getLevel()));
            cell = row.getCell(15);
            cell.setCellValue(Integer.toString(this.evaluator.getLevel()));
            cell = row.getCell(20);
            cell.setCellValue(Integer.toString(this.confirmator.getLevel()));
            
            row = sheet.getRow(37);
            cell = row.getCell(5);
            cell.setCellValue(this.reportDate);
            cell = row.getCell(15);
            cell.setCellValue(this.reportDate);
            cell = row.getCell(20);
            cell.setCellValue(this.reportDate);
            
            row = sheet.getRow(39);
            cell = row.getCell(0);
            cell.setCellValue(toEditM.getBottom());
            
            
            FileOutputStream output = new FileOutputStream(path + ".xlsx");
            workbook.write(output);
            output.close();
            return 1;
        } catch(Exception e) {
            return -1;
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        _page = new javax.swing.JTextField();
        _customer = new javax.swing.JTextField();
        _inspectionProcedure = new javax.swing.JTextField();
        _project = new javax.swing.JTextField();
        _inspectionScope = new javax.swing.JTextField();
        _reportNo = new javax.swing.JTextField();
        _inspectionPlace = new javax.swing.JTextField();
        _drawingNo = new javax.swing.JTextField();
        _reportDate = new javax.swing.JTextField();
        _surfaceCondition = new javax.swing.JTextField();
        _inspectionClass = new javax.swing.JTextField();
        _orderNo = new javax.swing.JTextField();
        _stageOfExamination = new javax.swing.JTextField();
        _evaluationStandard = new javax.swing.JTextField();
        _offerNo = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        _filters = new javax.swing.JTextField();
        _heatTreatment = new javax.swing.JTextField();
        _usedDevice = new javax.swing.JTextField();
        _focalSpotSize = new javax.swing.JTextField();
        _exposureTime = new javax.swing.JTextField();
        _filmFocusDistance = new javax.swing.JTextField();
        _pbScreens = new javax.swing.JTextField();
        _f1016 = new javax.swing.JTextField();
        _equipment = new javax.swing.JTextField();
        _filmBrand = new javax.swing.JTextField();
        _temp = new javax.swing.JTextField();
        _repairFilm = new javax.swing.JTextField();
        _suitableFilm = new javax.swing.JTextField();
        _f3040 = new javax.swing.JTextField();
        _f1048 = new javax.swing.JTextField();
        _f1036 = new javax.swing.JTextField();
        _f1024 = new javax.swing.JTextField();
        _f1012 = new javax.swing.JTextField();
        _astm = new javax.swing.JCheckBox();
        _ir192 = new javax.swing.JCheckBox();
        _se75 = new javax.swing.JCheckBox();
        _xRay = new javax.swing.JCheckBox();
        _d4 = new javax.swing.JCheckBox();
        _d5 = new javax.swing.JCheckBox();
        _d7 = new javax.swing.JCheckBox();
        _sourceSide = new javax.swing.JCheckBox();
        _filmSide = new javax.swing.JCheckBox();
        _automatic = new javax.swing.JCheckBox();
        _manuel = new javax.swing.JCheckBox();
        _en = new javax.swing.JCheckBox();
        jPanel18 = new javax.swing.JPanel();
        _testArr6 = new javax.swing.JCheckBox();
        _testArr2 = new javax.swing.JCheckBox();
        _testArr3 = new javax.swing.JCheckBox();
        _testArr4 = new javax.swing.JCheckBox();
        _testArr5 = new javax.swing.JCheckBox();
        _testArr1 = new javax.swing.JCheckBox();
        jPanel19 = new javax.swing.JPanel();
        _description = new javax.swing.JTextField();
        _inspectionDates = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        _finalEvaluation1 = new javax.swing.JTextField();
        _shootingArea1 = new javax.swing.JTextField();
        _filmNo1 = new javax.swing.JTextField();
        _materialType1 = new javax.swing.JTextField();
        _weldingType1 = new javax.swing.JTextField();
        _welderNr1 = new javax.swing.JTextField();
        _position1 = new javax.swing.JTextField();
        _thickness1 = new javax.swing.JTextField();
        _penetremetreQ1 = new javax.swing.JTextField();
        _visibleQ1 = new javax.swing.JTextField();
        _density1 = new javax.swing.JTextField();
        _f1012_1 = new javax.swing.JTextField();
        _f1016_1 = new javax.swing.JTextField();
        _f1024_1 = new javax.swing.JTextField();
        _f1036_1 = new javax.swing.JTextField();
        _f1048_1 = new javax.swing.JTextField();
        _f3040_1 = new javax.swing.JTextField();
        _defectLocation1 = new javax.swing.JTextField();
        _defectType1 = new javax.swing.JTextField();
        _preEvaluation1 = new javax.swing.JTextField();
        _finalEvaluation2 = new javax.swing.JTextField();
        _shootingArea2 = new javax.swing.JTextField();
        _filmNo2 = new javax.swing.JTextField();
        _weldingType2 = new javax.swing.JTextField();
        _welderNr2 = new javax.swing.JTextField();
        _position2 = new javax.swing.JTextField();
        _thickness2 = new javax.swing.JTextField();
        _penetremetreQ2 = new javax.swing.JTextField();
        _visibleQ2 = new javax.swing.JTextField();
        _density2 = new javax.swing.JTextField();
        _f1012_2 = new javax.swing.JTextField();
        _f1016_2 = new javax.swing.JTextField();
        _f1024_2 = new javax.swing.JTextField();
        _f1036_2 = new javax.swing.JTextField();
        _f1048_2 = new javax.swing.JTextField();
        _f3040_2 = new javax.swing.JTextField();
        _defectLocation2 = new javax.swing.JTextField();
        _defectType2 = new javax.swing.JTextField();
        _preEvaluation2 = new javax.swing.JTextField();
        _materialType2 = new javax.swing.JTextField();
        _finalEvaluation3 = new javax.swing.JTextField();
        _preEvaluation3 = new javax.swing.JTextField();
        _defectType3 = new javax.swing.JTextField();
        _defectLocation3 = new javax.swing.JTextField();
        _f1036_3 = new javax.swing.JTextField();
        _f1048_3 = new javax.swing.JTextField();
        _f3040_3 = new javax.swing.JTextField();
        _f1024_3 = new javax.swing.JTextField();
        _f1016_3 = new javax.swing.JTextField();
        _f1012_3 = new javax.swing.JTextField();
        _density3 = new javax.swing.JTextField();
        _visibleQ3 = new javax.swing.JTextField();
        _penetremetreQ3 = new javax.swing.JTextField();
        _thickness3 = new javax.swing.JTextField();
        _position3 = new javax.swing.JTextField();
        _welderNr3 = new javax.swing.JTextField();
        _weldingType3 = new javax.swing.JTextField();
        _materialType3 = new javax.swing.JTextField();
        _filmNo3 = new javax.swing.JTextField();
        _shootingArea3 = new javax.swing.JTextField();
        _visibleQ4 = new javax.swing.JTextField();
        _f1012_4 = new javax.swing.JTextField();
        _thickness4 = new javax.swing.JTextField();
        _weldingType4 = new javax.swing.JTextField();
        _materialType4 = new javax.swing.JTextField();
        _filmNo4 = new javax.swing.JTextField();
        _f1036_4 = new javax.swing.JTextField();
        _shootingArea4 = new javax.swing.JTextField();
        _defectType4 = new javax.swing.JTextField();
        _f1048_4 = new javax.swing.JTextField();
        _density4 = new javax.swing.JTextField();
        _penetremetreQ4 = new javax.swing.JTextField();
        _f3040_4 = new javax.swing.JTextField();
        _position4 = new javax.swing.JTextField();
        _defectLocation4 = new javax.swing.JTextField();
        _preEvaluation4 = new javax.swing.JTextField();
        _f1024_4 = new javax.swing.JTextField();
        _f1016_4 = new javax.swing.JTextField();
        _finalEvaluation4 = new javax.swing.JTextField();
        _welderNr4 = new javax.swing.JTextField();
        _f1016_5 = new javax.swing.JTextField();
        _f1012_5 = new javax.swing.JTextField();
        _f1024_5 = new javax.swing.JTextField();
        _f3040_5 = new javax.swing.JTextField();
        _position5 = new javax.swing.JTextField();
        _preEvaluation5 = new javax.swing.JTextField();
        _density5 = new javax.swing.JTextField();
        _defectType5 = new javax.swing.JTextField();
        _materialType5 = new javax.swing.JTextField();
        _welderNr5 = new javax.swing.JTextField();
        _thickness5 = new javax.swing.JTextField();
        _f1048_5 = new javax.swing.JTextField();
        _weldingType5 = new javax.swing.JTextField();
        _visibleQ5 = new javax.swing.JTextField();
        _shootingArea5 = new javax.swing.JTextField();
        _f1036_5 = new javax.swing.JTextField();
        _filmNo5 = new javax.swing.JTextField();
        _finalEvaluation5 = new javax.swing.JTextField();
        _penetremetreQ5 = new javax.swing.JTextField();
        _defectLocation5 = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        _operatorName = new javax.swing.JTextField();
        _evaluatorName = new javax.swing.JTextField();
        _confirmationName = new javax.swing.JTextField();
        _operatorLevel = new javax.swing.JTextField();
        _evaluatorLevel = new javax.swing.JTextField();
        _confirmationLevel = new javax.swing.JTextField();
        _bottom = new javax.swing.JTextField();
        _date1 = new javax.swing.JTextField();
        _date2 = new javax.swing.JTextField();
        _date3 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        _MstageOfExamination = new javax.swing.JTextField();
        _MinspectionProcedure = new javax.swing.JTextField();
        _MinspectionScope = new javax.swing.JTextField();
        _MdrawingNo = new javax.swing.JTextField();
        _MsurfaceCondition = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        _MevaluationStandard = new javax.swing.JTextField();
        _Mcustomer = new javax.swing.JTextField();
        _Mproject = new javax.swing.JTextField();
        _MinspectionPlace = new javax.swing.JTextField();
        _MinspectionStandard = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        _MofferNo = new javax.swing.JTextField();
        _Mpage = new javax.swing.JTextField();
        _MreportNo = new javax.swing.JTextField();
        _MreportDate = new javax.swing.JTextField();
        _MorderNo = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        _distanceOfLight = new javax.swing.JTextField();
        _poleDistance = new javax.swing.JTextField();
        _Mequipment = new javax.swing.JTextField();
        _MPCarrier = new javax.swing.JTextField();
        _UV = new javax.swing.JTextField();
        _magTech = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        _MheatTreatment = new javax.swing.JTextField();
        _examinationArea = new javax.swing.JTextField();
        _currentType = new javax.swing.JTextField();
        _luxmeter = new javax.swing.JTextField();
        _testMedium = new javax.swing.JTextField();
        _demagnetization = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        _gauss = new javax.swing.JTextField();
        _liftingTestDate = new javax.swing.JTextField();
        _surfaceTemperature = new javax.swing.JTextField();
        _MsurfaceCondition2 = new javax.swing.JTextField();
        _identification = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        _fillet = new javax.swing.JCheckBox();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        _butt = new javax.swing.JCheckBox();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        _Mdescription = new javax.swing.JTextField();
        _standardDeviations = new javax.swing.JTextField();
        _MinspectionDates = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        _result1 = new javax.swing.JTextField();
        _pieceNo1 = new javax.swing.JTextField();
        _testLength1 = new javax.swing.JTextField();
        _weldingProcess1 = new javax.swing.JTextField();
        _Mthickness1 = new javax.swing.JTextField();
        _diameter1 = new javax.swing.JTextField();
        _MdefectType1 = new javax.swing.JTextField();
        _MdefectLocation1 = new javax.swing.JTextField();
        _result2 = new javax.swing.JTextField();
        _MdefectLocation2 = new javax.swing.JTextField();
        _MdefectType2 = new javax.swing.JTextField();
        _diameter2 = new javax.swing.JTextField();
        _Mthickness2 = new javax.swing.JTextField();
        _weldingProcess2 = new javax.swing.JTextField();
        _testLength2 = new javax.swing.JTextField();
        _pieceNo2 = new javax.swing.JTextField();
        _MdefectType3 = new javax.swing.JTextField();
        _testLength3 = new javax.swing.JTextField();
        _result3 = new javax.swing.JTextField();
        _weldingProcess3 = new javax.swing.JTextField();
        _diameter3 = new javax.swing.JTextField();
        _Mthickness3 = new javax.swing.JTextField();
        _MdefectLocation3 = new javax.swing.JTextField();
        _pieceNo3 = new javax.swing.JTextField();
        _MdefectType4 = new javax.swing.JTextField();
        _Mthickness4 = new javax.swing.JTextField();
        _testLength4 = new javax.swing.JTextField();
        _result4 = new javax.swing.JTextField();
        _diameter4 = new javax.swing.JTextField();
        _MdefectLocation4 = new javax.swing.JTextField();
        _weldingProcess4 = new javax.swing.JTextField();
        _pieceNo4 = new javax.swing.JTextField();
        _testLength8 = new javax.swing.JTextField();
        _weldingProcess8 = new javax.swing.JTextField();
        _Mthickness8 = new javax.swing.JTextField();
        _result8 = new javax.swing.JTextField();
        _pieceNo8 = new javax.swing.JTextField();
        _MdefectLocation8 = new javax.swing.JTextField();
        _diameter8 = new javax.swing.JTextField();
        _MdefectType8 = new javax.swing.JTextField();
        _pieceNo7 = new javax.swing.JTextField();
        _pieceNo6 = new javax.swing.JTextField();
        _pieceNo5 = new javax.swing.JTextField();
        _testLength5 = new javax.swing.JTextField();
        _testLength6 = new javax.swing.JTextField();
        _testLength7 = new javax.swing.JTextField();
        _weldingProcess7 = new javax.swing.JTextField();
        _weldingProcess6 = new javax.swing.JTextField();
        _weldingProcess5 = new javax.swing.JTextField();
        _Mthickness5 = new javax.swing.JTextField();
        _Mthickness6 = new javax.swing.JTextField();
        _Mthickness7 = new javax.swing.JTextField();
        _diameter7 = new javax.swing.JTextField();
        _diameter6 = new javax.swing.JTextField();
        _diameter5 = new javax.swing.JTextField();
        _MdefectType5 = new javax.swing.JTextField();
        _MdefectType6 = new javax.swing.JTextField();
        _MdefectType7 = new javax.swing.JTextField();
        _MdefectLocation7 = new javax.swing.JTextField();
        _MdefectLocation6 = new javax.swing.JTextField();
        _MdefectLocation5 = new javax.swing.JTextField();
        _result5 = new javax.swing.JTextField();
        _result6 = new javax.swing.JTextField();
        _result7 = new javax.swing.JTextField();
        _diameter9 = new javax.swing.JTextField();
        _testLength9 = new javax.swing.JTextField();
        _Mthickness9 = new javax.swing.JTextField();
        _result10 = new javax.swing.JTextField();
        _pieceNo10 = new javax.swing.JTextField();
        _MdefectLocation9 = new javax.swing.JTextField();
        _Mthickness10 = new javax.swing.JTextField();
        _diameter10 = new javax.swing.JTextField();
        _testLength10 = new javax.swing.JTextField();
        _weldingProcess10 = new javax.swing.JTextField();
        _result9 = new javax.swing.JTextField();
        _pieceNo9 = new javax.swing.JTextField();
        _MdefectType10 = new javax.swing.JTextField();
        _MdefectType9 = new javax.swing.JTextField();
        _MdefectLocation10 = new javax.swing.JTextField();
        _weldingProcess9 = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        _MoperatorName = new javax.swing.JTextField();
        _MevaluatorName = new javax.swing.JTextField();
        _MconfirmationName = new javax.swing.JTextField();
        _MoperatorLevel = new javax.swing.JTextField();
        _Mbottom = new javax.swing.JTextField();
        _MconfirmationLevel = new javax.swing.JTextField();
        _MevaluatorLevel = new javax.swing.JTextField();
        _Mdate1 = new javax.swing.JTextField();
        _Mdate2 = new javax.swing.JTextField();
        _Mdate3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setFocusTraversalPolicyProvider(true);
        setMaximumSize(new java.awt.Dimension(1200, 800));
        setMinimumSize(new java.awt.Dimension(1200, 800));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(java.awt.SystemColor.inactiveCaption);
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        jPanel1.setMaximumSize(new java.awt.Dimension(403, 800));
        jPanel1.setMinimumSize(new java.awt.Dimension(403, 800));
        jPanel1.setPreferredSize(new java.awt.Dimension(403, 800));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/PDF.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.setEnabled(false);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/save.png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.setEnabled(false);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/XLSX.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.setEnabled(false);
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/edit1.png"))); // NOI18N
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane2.setMaximumSize(new java.awt.Dimension(781, 778));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(781, 778));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(781, 778));

        jLayeredPane1.setMaximumSize(new java.awt.Dimension(1350, 2100));
        jLayeredPane1.setMinimumSize(new java.awt.Dimension(1350, 2100));
        jLayeredPane1.setName(""); // NOI18N
        jLayeredPane1.setLayout(new java.awt.CardLayout());

        jTabbedPane1.setMaximumSize(new java.awt.Dimension(1350, 2100));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(1350, 2100));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1350, 2100));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTabbedPane1MousePressed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setMaximumSize(new java.awt.Dimension(1350, 1900));
        jPanel5.setMinimumSize(new java.awt.Dimension(1350, 1900));
        jPanel5.setPreferredSize(new java.awt.Dimension(1350, 1900));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel16.setOpaque(false);
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        _page.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _page.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _page.setBorder(null);
        _page.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _page.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel16.add(_page, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 3, 221, 48));

        _customer.setEditable(false);
        _customer.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _customer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _customer.setToolTipText("Değiştirilmez");
        _customer.setBorder(null);
        _customer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel16.add(_customer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 3, 413, 48));

        _inspectionProcedure.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _inspectionProcedure.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _inspectionProcedure.setBorder(null);
        _inspectionProcedure.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _inspectionProcedure.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel16.add(_inspectionProcedure, new org.netbeans.lib.awtextra.AbsoluteConstraints(593, 3, 180, 48));

        _project.setEditable(false);
        _project.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _project.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _project.setToolTipText("Değiştirilmez");
        _project.setBorder(null);
        _project.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel16.add(_project, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 55, 413, 50));

        _inspectionScope.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _inspectionScope.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _inspectionScope.setBorder(null);
        _inspectionScope.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                _inspectionScopeFocusLost(evt);
            }
        });
        _inspectionScope.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel16.add(_inspectionScope, new org.netbeans.lib.awtextra.AbsoluteConstraints(593, 55, 180, 50));

        _reportNo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _reportNo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _reportNo.setBorder(null);
        _reportNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _reportNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel16.add(_reportNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 55, 221, 50));

        _inspectionPlace.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _inspectionPlace.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _inspectionPlace.setBorder(null);
        _inspectionPlace.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _inspectionPlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _inspectionPlaceActionPerformed(evt);
            }
        });
        _inspectionPlace.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel16.add(_inspectionPlace, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 109, 413, 49));

        _drawingNo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _drawingNo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _drawingNo.setBorder(null);
        _drawingNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _drawingNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel16.add(_drawingNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(593, 109, 180, 48));

        _reportDate.setEditable(false);
        _reportDate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _reportDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _reportDate.setToolTipText("Değiştirilmez");
        _reportDate.setBorder(null);
        _reportDate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel16.add(_reportDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 109, 221, 48));

        _surfaceCondition.setEditable(false);
        _surfaceCondition.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _surfaceCondition.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _surfaceCondition.setToolTipText("Değiştirilmez");
        _surfaceCondition.setBorder(null);
        _surfaceCondition.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel16.add(_surfaceCondition, new org.netbeans.lib.awtextra.AbsoluteConstraints(593, 161, 180, 51));

        _inspectionClass.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _inspectionClass.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _inspectionClass.setBorder(null);
        _inspectionClass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _inspectionClass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel16.add(_inspectionClass, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 161, 413, 51));

        _orderNo.setEditable(false);
        _orderNo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _orderNo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _orderNo.setToolTipText("Değiştirilmez");
        _orderNo.setBorder(null);
        _orderNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel16.add(_orderNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 161, 221, 51));

        _stageOfExamination.setEditable(false);
        _stageOfExamination.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _stageOfExamination.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _stageOfExamination.setToolTipText("Değiştirilmez");
        _stageOfExamination.setBorder(null);
        _stageOfExamination.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel16.add(_stageOfExamination, new org.netbeans.lib.awtextra.AbsoluteConstraints(593, 216, 180, 48));

        _evaluationStandard.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _evaluationStandard.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _evaluationStandard.setBorder(null);
        _evaluationStandard.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _evaluationStandard.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel16.add(_evaluationStandard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 216, 413, 48));

        _offerNo.setEditable(false);
        _offerNo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _offerNo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _offerNo.setToolTipText("Değiştirilmez");
        _offerNo.setBorder(null);
        _offerNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel16.add(_offerNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 216, 221, 48));

        jPanel5.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(244, 150, 1120, 270));

        jPanel17.setOpaque(false);
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        _filters.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _filters.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _filters.setBorder(null);
        _filters.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                _filtersFocusLost(evt);
            }
        });
        _filters.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_filters, new org.netbeans.lib.awtextra.AbsoluteConstraints(1236, 77, 116, 22));

        _heatTreatment.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _heatTreatment.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _heatTreatment.setBorder(null);
        _heatTreatment.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                _heatTreatmentFocusLost(evt);
            }
        });
        _heatTreatment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_heatTreatment, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 158, 113, 64));

        _usedDevice.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _usedDevice.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _usedDevice.setBorder(null);
        _usedDevice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _usedDevice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_usedDevice, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 77, 256, 22));

        _focalSpotSize.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _focalSpotSize.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _focalSpotSize.setBorder(null);
        _focalSpotSize.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _focalSpotSize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_focalSpotSize, new org.netbeans.lib.awtextra.AbsoluteConstraints(709, 77, 168, 22));

        _exposureTime.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _exposureTime.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _exposureTime.setBorder(null);
        _exposureTime.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _exposureTime.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_exposureTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(881, 77, 82, 22));

        _filmFocusDistance.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _filmFocusDistance.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _filmFocusDistance.setBorder(null);
        _filmFocusDistance.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _filmFocusDistance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_filmFocusDistance, new org.netbeans.lib.awtextra.AbsoluteConstraints(967, 77, 132, 22));

        _pbScreens.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _pbScreens.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _pbScreens.setBorder(null);
        _pbScreens.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _pbScreens.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_pbScreens, new org.netbeans.lib.awtextra.AbsoluteConstraints(1102, 77, 131, 22));

        _f1016.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1016.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1016.setBorder(null);
        _f1016.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1016.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_f1016, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 294, 100, 35));

        _equipment.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _equipment.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _equipment.setBorder(null);
        _equipment.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _equipment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_equipment, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 77, 113, 22));

        _filmBrand.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _filmBrand.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _filmBrand.setBorder(null);
        _filmBrand.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _filmBrand.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_filmBrand, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 199, 91, 23));

        _temp.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _temp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _temp.setBorder(null);
        _temp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _temp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_temp, new org.netbeans.lib.awtextra.AbsoluteConstraints(1201, 199, 151, 23));

        _repairFilm.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _repairFilm.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _repairFilm.setBorder(null);
        _repairFilm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _repairFilm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_repairFilm, new org.netbeans.lib.awtextra.AbsoluteConstraints(1154, 294, 198, 35));

        _suitableFilm.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _suitableFilm.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _suitableFilm.setBorder(null);
        _suitableFilm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _suitableFilm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_suitableFilm, new org.netbeans.lib.awtextra.AbsoluteConstraints(1018, 294, 132, 35));

        _f3040.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f3040.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f3040.setBorder(null);
        _f3040.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f3040.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_f3040, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 294, 104, 35));

        _f1048.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1048.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1048.setBorder(null);
        _f1048.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1048.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_f1048, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 294, 136, 35));

        _f1036.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1036.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1036.setBorder(null);
        _f1036.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1036.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_f1036, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 294, 158, 35));

        _f1024.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1024.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1024.setBorder(null);
        _f1024.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1024.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_f1024, new org.netbeans.lib.awtextra.AbsoluteConstraints(453, 294, 153, 35));

        _f1012.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1012.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1012.setBorder(null);
        _f1012.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1012.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel17.add(_f1012, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 294, 227, 35));

        _astm.setBackground(new java.awt.Color(255, 255, 255));
        _astm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _astm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _astm.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _astm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel17.add(_astm, new org.netbeans.lib.awtextra.AbsoluteConstraints(901, 132, 100, -1));

        _ir192.setBackground(new java.awt.Color(255, 255, 255));
        _ir192.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _ir192.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _ir192.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _ir192.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        _ir192.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel17.add(_ir192, new org.netbeans.lib.awtextra.AbsoluteConstraints(381, 30, 70, 40));

        _se75.setBackground(new java.awt.Color(255, 255, 255));
        _se75.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _se75.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _se75.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _se75.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        _se75.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel17.add(_se75, new org.netbeans.lib.awtextra.AbsoluteConstraints(451, 30, 120, 40));

        _xRay.setBackground(new java.awt.Color(255, 255, 255));
        _xRay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _xRay.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _xRay.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _xRay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel17.add(_xRay, new org.netbeans.lib.awtextra.AbsoluteConstraints(581, 30, 120, -1));

        _d4.setBackground(new java.awt.Color(255, 255, 255));
        _d4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _d4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _d4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _d4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        _d4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel17.add(_d4, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 161, 100, 60));

        _d5.setBackground(new java.awt.Color(255, 255, 255));
        _d5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _d5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _d5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _d5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        _d5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel17.add(_d5, new org.netbeans.lib.awtextra.AbsoluteConstraints(556, 161, 120, 60));

        _d7.setBackground(new java.awt.Color(255, 255, 255));
        _d7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _d7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _d7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _d7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        _d7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel17.add(_d7, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 161, 100, 60));

        _sourceSide.setBackground(new java.awt.Color(255, 255, 255));
        _sourceSide.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _sourceSide.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _sourceSide.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _sourceSide.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        _sourceSide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel17.add(_sourceSide, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 161, 100, 60));

        _filmSide.setBackground(new java.awt.Color(255, 255, 255));
        _filmSide.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _filmSide.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _filmSide.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _filmSide.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        _filmSide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel17.add(_filmSide, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 161, 90, 60));

        _automatic.setBackground(new java.awt.Color(255, 255, 255));
        _automatic.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _automatic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _automatic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _automatic.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        _automatic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel17.add(_automatic, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 131, 90, 90));

        _manuel.setBackground(new java.awt.Color(255, 255, 255));
        _manuel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _manuel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _manuel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _manuel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        _manuel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel17.add(_manuel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1107, 131, 90, 90));

        _en.setBackground(new java.awt.Color(255, 255, 255));
        _en.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _en.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _en.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _en.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel17.add(_en, new org.netbeans.lib.awtextra.AbsoluteConstraints(801, 132, 90, -1));

        jPanel5.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 440, 1350, 340));

        jPanel18.setOpaque(false);
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        _testArr6.setBackground(new java.awt.Color(255, 255, 255));
        _testArr6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _testArr6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _testArr6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _testArr6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        _testArr6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel18.add(_testArr6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1039, 9, 200, 200));

        _testArr2.setBackground(new java.awt.Color(255, 255, 255));
        _testArr2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _testArr2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _testArr2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _testArr2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        _testArr2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        _testArr2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel18.add(_testArr2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 9, 180, 200));

        _testArr3.setBackground(new java.awt.Color(255, 255, 255));
        _testArr3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _testArr3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _testArr3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _testArr3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        _testArr3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel18.add(_testArr3, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 9, 170, 200));

        _testArr4.setBackground(new java.awt.Color(255, 255, 255));
        _testArr4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _testArr4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _testArr4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _testArr4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        _testArr4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel18.add(_testArr4, new org.netbeans.lib.awtextra.AbsoluteConstraints(637, 9, 170, 200));

        _testArr5.setBackground(new java.awt.Color(255, 255, 255));
        _testArr5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _testArr5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _testArr5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _testArr5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        _testArr5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel18.add(_testArr5, new org.netbeans.lib.awtextra.AbsoluteConstraints(839, 9, 170, 200));

        _testArr1.setBackground(new java.awt.Color(255, 255, 255));
        _testArr1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        _testArr1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _testArr1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _testArr1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        _testArr1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _automaticMouseClicked(evt);
            }
        });
        jPanel18.add(_testArr1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 9, 190, 200));

        jPanel5.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(64, 820, 1290, 220));

        jPanel19.setOpaque(false);
        jPanel19.setLayout(null);

        _description.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _description.setBorder(null);
        _description.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _description.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel19.add(_description);
        _description.setBounds(5, 46, 676, 42);

        _inspectionDates.setEditable(false);
        _inspectionDates.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _inspectionDates.setForeground(new java.awt.Color(255, 0, 51));
        _inspectionDates.setToolTipText("Değiştirilmez");
        _inspectionDates.setBorder(null);
        _inspectionDates.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel19.add(_inspectionDates);
        _inspectionDates.setBounds(5, 0, 676, 42);

        jPanel5.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(684, 1270, 680, 90));

        jPanel20.setOpaque(false);
        jPanel20.setLayout(null);

        _finalEvaluation1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _finalEvaluation1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _finalEvaluation1.setBorder(null);
        _finalEvaluation1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _finalEvaluation1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_finalEvaluation1);
        _finalEvaluation1.setBounds(1195, 8, 115, 42);

        _shootingArea1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _shootingArea1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _shootingArea1.setBorder(null);
        _shootingArea1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _shootingArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_shootingArea1);
        _shootingArea1.setBounds(11, 8, 205, 42);

        _filmNo1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _filmNo1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _filmNo1.setBorder(null);
        _filmNo1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _filmNo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_filmNo1);
        _filmNo1.setBounds(220, 8, 85, 42);

        _materialType1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _materialType1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _materialType1.setBorder(null);
        _materialType1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _materialType1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_materialType1);
        _materialType1.setBounds(308, 8, 44, 42);

        _weldingType1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _weldingType1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _weldingType1.setBorder(null);
        _weldingType1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _weldingType1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_weldingType1);
        _weldingType1.setBounds(356, 8, 44, 42);

        _welderNr1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _welderNr1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _welderNr1.setBorder(null);
        _welderNr1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _welderNr1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_welderNr1);
        _welderNr1.setBounds(404, 8, 43, 42);

        _position1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _position1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _position1.setBorder(null);
        _position1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _position1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_position1);
        _position1.setBounds(451, 8, 53, 42);

        _thickness1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _thickness1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _thickness1.setBorder(null);
        _thickness1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _thickness1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_thickness1);
        _thickness1.setBounds(508, 8, 57, 42);

        _penetremetreQ1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _penetremetreQ1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _penetremetreQ1.setBorder(null);
        _penetremetreQ1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _penetremetreQ1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_penetremetreQ1);
        _penetremetreQ1.setBounds(569, 8, 62, 42);

        _visibleQ1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _visibleQ1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _visibleQ1.setBorder(null);
        _visibleQ1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _visibleQ1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_visibleQ1);
        _visibleQ1.setBounds(635, 8, 61, 42);

        _density1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _density1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _density1.setBorder(null);
        _density1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _density1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_density1);
        _density1.setBounds(700, 8, 51, 42);

        _f1012_1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1012_1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1012_1.setBorder(null);
        _f1012_1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1012_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1012_1);
        _f1012_1.setBounds(755, 8, 24, 42);

        _f1016_1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1016_1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1016_1.setBorder(null);
        _f1016_1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1016_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1016_1);
        _f1016_1.setBounds(783, 8, 25, 42);

        _f1024_1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1024_1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1024_1.setBorder(null);
        _f1024_1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1024_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1024_1);
        _f1024_1.setBounds(812, 8, 24, 42);

        _f1036_1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1036_1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1036_1.setBorder(null);
        _f1036_1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1036_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1036_1);
        _f1036_1.setBounds(840, 8, 25, 42);

        _f1048_1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1048_1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1048_1.setBorder(null);
        _f1048_1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1048_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1048_1);
        _f1048_1.setBounds(869, 8, 24, 42);

        _f3040_1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f3040_1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f3040_1.setBorder(null);
        _f3040_1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f3040_1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f3040_1);
        _f3040_1.setBounds(897, 8, 25, 42);

        _defectLocation1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _defectLocation1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _defectLocation1.setBorder(null);
        _defectLocation1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _defectLocation1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_defectLocation1);
        _defectLocation1.setBounds(926, 8, 66, 42);

        _defectType1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _defectType1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _defectType1.setBorder(null);
        _defectType1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _defectType1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_defectType1);
        _defectType1.setBounds(996, 8, 72, 42);

        _preEvaluation1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _preEvaluation1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _preEvaluation1.setBorder(null);
        _preEvaluation1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _preEvaluation1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_preEvaluation1);
        _preEvaluation1.setBounds(1072, 8, 119, 42);

        _finalEvaluation2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _finalEvaluation2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _finalEvaluation2.setBorder(null);
        _finalEvaluation2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _finalEvaluation2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_finalEvaluation2);
        _finalEvaluation2.setBounds(1195, 54, 115, 43);

        _shootingArea2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _shootingArea2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _shootingArea2.setBorder(null);
        _shootingArea2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _shootingArea2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_shootingArea2);
        _shootingArea2.setBounds(11, 54, 205, 43);

        _filmNo2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _filmNo2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _filmNo2.setBorder(null);
        _filmNo2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _filmNo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_filmNo2);
        _filmNo2.setBounds(220, 54, 85, 43);

        _weldingType2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _weldingType2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _weldingType2.setBorder(null);
        _weldingType2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _weldingType2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_weldingType2);
        _weldingType2.setBounds(356, 54, 44, 43);

        _welderNr2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _welderNr2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _welderNr2.setBorder(null);
        _welderNr2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _welderNr2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_welderNr2);
        _welderNr2.setBounds(404, 54, 43, 43);

        _position2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _position2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _position2.setBorder(null);
        _position2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _position2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_position2);
        _position2.setBounds(451, 54, 53, 43);

        _thickness2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _thickness2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _thickness2.setBorder(null);
        _thickness2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _thickness2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_thickness2);
        _thickness2.setBounds(508, 54, 57, 43);

        _penetremetreQ2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _penetremetreQ2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _penetremetreQ2.setBorder(null);
        _penetremetreQ2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _penetremetreQ2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_penetremetreQ2);
        _penetremetreQ2.setBounds(569, 54, 62, 43);

        _visibleQ2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _visibleQ2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _visibleQ2.setBorder(null);
        _visibleQ2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _visibleQ2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_visibleQ2);
        _visibleQ2.setBounds(635, 54, 61, 43);

        _density2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _density2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _density2.setBorder(null);
        _density2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _density2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_density2);
        _density2.setBounds(700, 54, 51, 43);

        _f1012_2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1012_2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1012_2.setBorder(null);
        _f1012_2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1012_2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1012_2);
        _f1012_2.setBounds(755, 54, 24, 43);

        _f1016_2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1016_2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1016_2.setBorder(null);
        _f1016_2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1016_2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1016_2);
        _f1016_2.setBounds(783, 54, 25, 43);

        _f1024_2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1024_2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1024_2.setBorder(null);
        _f1024_2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1024_2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1024_2);
        _f1024_2.setBounds(812, 54, 24, 43);

        _f1036_2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1036_2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1036_2.setBorder(null);
        _f1036_2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1036_2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1036_2);
        _f1036_2.setBounds(840, 54, 25, 43);

        _f1048_2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1048_2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1048_2.setBorder(null);
        _f1048_2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1048_2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1048_2);
        _f1048_2.setBounds(869, 54, 24, 43);

        _f3040_2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f3040_2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f3040_2.setBorder(null);
        _f3040_2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f3040_2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f3040_2);
        _f3040_2.setBounds(897, 54, 25, 43);

        _defectLocation2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _defectLocation2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _defectLocation2.setBorder(null);
        _defectLocation2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _defectLocation2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_defectLocation2);
        _defectLocation2.setBounds(926, 54, 66, 43);

        _defectType2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _defectType2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _defectType2.setBorder(null);
        _defectType2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _defectType2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_defectType2);
        _defectType2.setBounds(996, 54, 72, 43);

        _preEvaluation2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _preEvaluation2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _preEvaluation2.setBorder(null);
        _preEvaluation2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _preEvaluation2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_preEvaluation2);
        _preEvaluation2.setBounds(1072, 54, 119, 43);

        _materialType2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _materialType2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _materialType2.setBorder(null);
        _materialType2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _materialType2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_materialType2);
        _materialType2.setBounds(308, 54, 44, 43);

        _finalEvaluation3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _finalEvaluation3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _finalEvaluation3.setBorder(null);
        _finalEvaluation3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _finalEvaluation3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_finalEvaluation3);
        _finalEvaluation3.setBounds(1195, 101, 115, 42);

        _preEvaluation3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _preEvaluation3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _preEvaluation3.setBorder(null);
        _preEvaluation3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _preEvaluation3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_preEvaluation3);
        _preEvaluation3.setBounds(1072, 101, 119, 42);

        _defectType3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _defectType3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _defectType3.setBorder(null);
        _defectType3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _defectType3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_defectType3);
        _defectType3.setBounds(996, 101, 72, 42);

        _defectLocation3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _defectLocation3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _defectLocation3.setBorder(null);
        _defectLocation3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _defectLocation3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_defectLocation3);
        _defectLocation3.setBounds(926, 101, 66, 42);

        _f1036_3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1036_3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1036_3.setBorder(null);
        _f1036_3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1036_3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1036_3);
        _f1036_3.setBounds(840, 101, 25, 42);

        _f1048_3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1048_3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1048_3.setBorder(null);
        _f1048_3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1048_3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1048_3);
        _f1048_3.setBounds(869, 101, 24, 42);

        _f3040_3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f3040_3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f3040_3.setBorder(null);
        _f3040_3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f3040_3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f3040_3);
        _f3040_3.setBounds(897, 101, 25, 42);

        _f1024_3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1024_3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1024_3.setBorder(null);
        _f1024_3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1024_3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1024_3);
        _f1024_3.setBounds(812, 101, 24, 42);

        _f1016_3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1016_3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1016_3.setBorder(null);
        _f1016_3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1016_3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1016_3);
        _f1016_3.setBounds(783, 101, 25, 42);

        _f1012_3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1012_3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1012_3.setBorder(null);
        _f1012_3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1012_3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1012_3);
        _f1012_3.setBounds(755, 101, 24, 42);

        _density3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _density3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _density3.setBorder(null);
        _density3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _density3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_density3);
        _density3.setBounds(700, 101, 51, 42);

        _visibleQ3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _visibleQ3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _visibleQ3.setBorder(null);
        _visibleQ3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _visibleQ3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_visibleQ3);
        _visibleQ3.setBounds(635, 101, 61, 42);

        _penetremetreQ3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _penetremetreQ3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _penetremetreQ3.setBorder(null);
        _penetremetreQ3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _penetremetreQ3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_penetremetreQ3);
        _penetremetreQ3.setBounds(569, 101, 62, 42);

        _thickness3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _thickness3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _thickness3.setBorder(null);
        _thickness3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _thickness3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_thickness3);
        _thickness3.setBounds(508, 101, 57, 42);

        _position3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _position3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _position3.setBorder(null);
        _position3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _position3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_position3);
        _position3.setBounds(451, 101, 53, 42);

        _welderNr3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _welderNr3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _welderNr3.setBorder(null);
        _welderNr3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _welderNr3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_welderNr3);
        _welderNr3.setBounds(404, 101, 43, 42);

        _weldingType3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _weldingType3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _weldingType3.setBorder(null);
        _weldingType3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _weldingType3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_weldingType3);
        _weldingType3.setBounds(356, 101, 44, 42);

        _materialType3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _materialType3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _materialType3.setBorder(null);
        _materialType3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _materialType3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_materialType3);
        _materialType3.setBounds(308, 101, 44, 42);

        _filmNo3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _filmNo3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _filmNo3.setBorder(null);
        _filmNo3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _filmNo3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_filmNo3);
        _filmNo3.setBounds(220, 101, 85, 42);

        _shootingArea3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _shootingArea3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _shootingArea3.setBorder(null);
        _shootingArea3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _shootingArea3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_shootingArea3);
        _shootingArea3.setBounds(11, 101, 205, 42);

        _visibleQ4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _visibleQ4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _visibleQ4.setBorder(null);
        _visibleQ4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _visibleQ4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_visibleQ4);
        _visibleQ4.setBounds(635, 147, 61, 43);

        _f1012_4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1012_4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1012_4.setBorder(null);
        _f1012_4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1012_4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1012_4);
        _f1012_4.setBounds(755, 147, 24, 43);

        _thickness4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _thickness4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _thickness4.setBorder(null);
        _thickness4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _thickness4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_thickness4);
        _thickness4.setBounds(508, 147, 57, 43);

        _weldingType4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _weldingType4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _weldingType4.setBorder(null);
        _weldingType4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _weldingType4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_weldingType4);
        _weldingType4.setBounds(356, 147, 44, 43);

        _materialType4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _materialType4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _materialType4.setBorder(null);
        _materialType4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _materialType4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_materialType4);
        _materialType4.setBounds(308, 147, 44, 43);

        _filmNo4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _filmNo4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _filmNo4.setBorder(null);
        _filmNo4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _filmNo4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_filmNo4);
        _filmNo4.setBounds(220, 147, 85, 43);

        _f1036_4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1036_4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1036_4.setBorder(null);
        _f1036_4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1036_4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1036_4);
        _f1036_4.setBounds(840, 147, 25, 43);

        _shootingArea4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _shootingArea4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _shootingArea4.setBorder(null);
        _shootingArea4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _shootingArea4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_shootingArea4);
        _shootingArea4.setBounds(11, 147, 205, 43);

        _defectType4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _defectType4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _defectType4.setBorder(null);
        _defectType4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _defectType4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_defectType4);
        _defectType4.setBounds(996, 147, 72, 43);

        _f1048_4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1048_4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1048_4.setBorder(null);
        _f1048_4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1048_4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1048_4);
        _f1048_4.setBounds(869, 147, 24, 43);

        _density4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _density4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _density4.setBorder(null);
        _density4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _density4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_density4);
        _density4.setBounds(700, 147, 51, 43);

        _penetremetreQ4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _penetremetreQ4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _penetremetreQ4.setBorder(null);
        _penetremetreQ4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _penetremetreQ4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_penetremetreQ4);
        _penetremetreQ4.setBounds(569, 147, 62, 43);

        _f3040_4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f3040_4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f3040_4.setBorder(null);
        _f3040_4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f3040_4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f3040_4);
        _f3040_4.setBounds(897, 147, 25, 43);

        _position4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _position4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _position4.setBorder(null);
        _position4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _position4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_position4);
        _position4.setBounds(451, 147, 53, 43);

        _defectLocation4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _defectLocation4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _defectLocation4.setBorder(null);
        _defectLocation4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _defectLocation4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_defectLocation4);
        _defectLocation4.setBounds(926, 147, 66, 43);

        _preEvaluation4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _preEvaluation4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _preEvaluation4.setBorder(null);
        _preEvaluation4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _preEvaluation4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_preEvaluation4);
        _preEvaluation4.setBounds(1072, 147, 119, 43);

        _f1024_4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1024_4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1024_4.setBorder(null);
        _f1024_4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1024_4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1024_4);
        _f1024_4.setBounds(812, 147, 24, 43);

        _f1016_4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1016_4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1016_4.setBorder(null);
        _f1016_4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1016_4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1016_4);
        _f1016_4.setBounds(783, 147, 25, 43);

        _finalEvaluation4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _finalEvaluation4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _finalEvaluation4.setBorder(null);
        _finalEvaluation4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _finalEvaluation4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_finalEvaluation4);
        _finalEvaluation4.setBounds(1195, 147, 115, 43);

        _welderNr4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _welderNr4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _welderNr4.setBorder(null);
        _welderNr4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _welderNr4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_welderNr4);
        _welderNr4.setBounds(404, 147, 43, 43);

        _f1016_5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1016_5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1016_5.setBorder(null);
        _f1016_5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1016_5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1016_5);
        _f1016_5.setBounds(783, 194, 25, 41);

        _f1012_5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1012_5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1012_5.setBorder(null);
        _f1012_5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1012_5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1012_5);
        _f1012_5.setBounds(755, 194, 24, 41);

        _f1024_5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1024_5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1024_5.setBorder(null);
        _f1024_5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1024_5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1024_5);
        _f1024_5.setBounds(812, 194, 24, 41);

        _f3040_5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f3040_5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f3040_5.setBorder(null);
        _f3040_5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f3040_5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f3040_5);
        _f3040_5.setBounds(897, 194, 25, 41);

        _position5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _position5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _position5.setBorder(null);
        _position5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _position5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_position5);
        _position5.setBounds(451, 194, 53, 41);

        _preEvaluation5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _preEvaluation5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _preEvaluation5.setBorder(null);
        _preEvaluation5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _preEvaluation5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_preEvaluation5);
        _preEvaluation5.setBounds(1072, 194, 119, 41);

        _density5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _density5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _density5.setBorder(null);
        _density5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _density5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_density5);
        _density5.setBounds(700, 194, 51, 41);

        _defectType5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _defectType5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _defectType5.setBorder(null);
        _defectType5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _defectType5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_defectType5);
        _defectType5.setBounds(996, 194, 72, 41);

        _materialType5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _materialType5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _materialType5.setBorder(null);
        _materialType5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _materialType5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_materialType5);
        _materialType5.setBounds(308, 194, 44, 41);

        _welderNr5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _welderNr5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _welderNr5.setBorder(null);
        _welderNr5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _welderNr5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_welderNr5);
        _welderNr5.setBounds(404, 194, 43, 41);

        _thickness5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _thickness5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _thickness5.setBorder(null);
        _thickness5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _thickness5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_thickness5);
        _thickness5.setBounds(508, 194, 57, 41);

        _f1048_5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1048_5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1048_5.setBorder(null);
        _f1048_5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1048_5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1048_5);
        _f1048_5.setBounds(869, 194, 24, 41);

        _weldingType5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _weldingType5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _weldingType5.setBorder(null);
        _weldingType5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _weldingType5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_weldingType5);
        _weldingType5.setBounds(356, 194, 44, 41);

        _visibleQ5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _visibleQ5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _visibleQ5.setBorder(null);
        _visibleQ5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _visibleQ5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_visibleQ5);
        _visibleQ5.setBounds(635, 194, 61, 41);

        _shootingArea5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _shootingArea5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _shootingArea5.setBorder(null);
        _shootingArea5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _shootingArea5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_shootingArea5);
        _shootingArea5.setBounds(11, 194, 205, 41);

        _f1036_5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _f1036_5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _f1036_5.setBorder(null);
        _f1036_5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _f1036_5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_f1036_5);
        _f1036_5.setBounds(840, 194, 25, 41);

        _filmNo5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _filmNo5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _filmNo5.setBorder(null);
        _filmNo5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _filmNo5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_filmNo5);
        _filmNo5.setBounds(220, 194, 85, 41);

        _finalEvaluation5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _finalEvaluation5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _finalEvaluation5.setBorder(null);
        _finalEvaluation5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _finalEvaluation5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_finalEvaluation5);
        _finalEvaluation5.setBounds(1195, 194, 115, 41);

        _penetremetreQ5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _penetremetreQ5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _penetremetreQ5.setBorder(null);
        _penetremetreQ5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _penetremetreQ5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_penetremetreQ5);
        _penetremetreQ5.setBounds(569, 194, 62, 41);

        _defectLocation5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _defectLocation5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _defectLocation5.setBorder(null);
        _defectLocation5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        _defectLocation5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _descriptionKeyReleased(evt);
            }
        });
        jPanel20.add(_defectLocation5);
        _defectLocation5.setBounds(926, 194, 66, 41);

        jPanel5.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 1520, 1310, 240));

        jPanel21.setOpaque(false);
        jPanel21.setLayout(null);

        _operatorName.setEditable(false);
        _operatorName.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _operatorName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _operatorName.setToolTipText("Değiştirilmez");
        _operatorName.setBorder(null);
        _operatorName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel21.add(_operatorName);
        _operatorName.setBounds(220, 10, 250, 30);

        _evaluatorName.setEditable(false);
        _evaluatorName.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _evaluatorName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _evaluatorName.setToolTipText("Değiştirilmez");
        _evaluatorName.setBorder(null);
        _evaluatorName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel21.add(_evaluatorName);
        _evaluatorName.setBounds(480, 10, 290, 30);

        _confirmationName.setEditable(false);
        _confirmationName.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _confirmationName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _confirmationName.setToolTipText("Değiştirilmez");
        _confirmationName.setBorder(null);
        _confirmationName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel21.add(_confirmationName);
        _confirmationName.setBounds(790, 10, 210, 30);

        _operatorLevel.setEditable(false);
        _operatorLevel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _operatorLevel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _operatorLevel.setToolTipText("Değiştirilmez");
        _operatorLevel.setBorder(null);
        _operatorLevel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel21.add(_operatorLevel);
        _operatorLevel.setBounds(220, 50, 250, 30);

        _evaluatorLevel.setEditable(false);
        _evaluatorLevel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _evaluatorLevel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _evaluatorLevel.setToolTipText("Değiştirilmez");
        _evaluatorLevel.setBorder(null);
        _evaluatorLevel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel21.add(_evaluatorLevel);
        _evaluatorLevel.setBounds(480, 50, 290, 30);

        _confirmationLevel.setEditable(false);
        _confirmationLevel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _confirmationLevel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _confirmationLevel.setToolTipText("Değiştirilmez");
        _confirmationLevel.setBorder(null);
        _confirmationLevel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel21.add(_confirmationLevel);
        _confirmationLevel.setBounds(790, 50, 210, 30);

        _bottom.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        _bottom.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _bottom.setBorder(null);
        _bottom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel21.add(_bottom);
        _bottom.setBounds(84, 255, 1020, 30);

        _date1.setEditable(false);
        _date1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _date1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _date1.setToolTipText("Değiştirilmez");
        _date1.setBorder(null);
        _date1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel21.add(_date1);
        _date1.setBounds(220, 88, 250, 30);

        _date2.setEditable(false);
        _date2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _date2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _date2.setToolTipText("Değiştirilmez");
        _date2.setBorder(null);
        _date2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel21.add(_date2);
        _date2.setBounds(480, 88, 290, 30);

        _date3.setEditable(false);
        _date3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _date3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _date3.setToolTipText("Değiştirilmez");
        _date3.setBorder(null);
        _date3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _customerFocusGained(evt);
            }
        });
        jPanel21.add(_date3);
        _date3.setBounds(790, 88, 210, 30);

        jPanel5.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 1800, 1280, 270));

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/radio2.png"))); // NOI18N
        jPanel5.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 2060));

        jTabbedPane1.addTab("Radyografik Muayene Raporu", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setMaximumSize(new java.awt.Dimension(1350, 2000));
        jPanel6.setMinimumSize(new java.awt.Dimension(1350, 2000));
        jPanel6.setName(""); // NOI18N
        jPanel6.setPreferredSize(new java.awt.Dimension(1350, 2000));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        _MstageOfExamination.setEditable(false);
        _MstageOfExamination.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MstageOfExamination.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MstageOfExamination.setToolTipText("");
        _MstageOfExamination.setBorder(null);
        _MstageOfExamination.setMinimumSize(new java.awt.Dimension(470, 46));
        _MstageOfExamination.setName(""); // NOI18N
        _MstageOfExamination.setPreferredSize(new java.awt.Dimension(470, 46));
        _MstageOfExamination.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MstageOfExamination.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel4.add(_MstageOfExamination, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 211, 193, 48));

        _MinspectionProcedure.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MinspectionProcedure.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MinspectionProcedure.setToolTipText("");
        _MinspectionProcedure.setBorder(null);
        _MinspectionProcedure.setMinimumSize(new java.awt.Dimension(470, 46));
        _MinspectionProcedure.setName(""); // NOI18N
        _MinspectionProcedure.setPreferredSize(new java.awt.Dimension(470, 46));
        _MinspectionProcedure.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MinspectionProcedure.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel4.add(_MinspectionProcedure, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 6, 193, 46));

        _MinspectionScope.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MinspectionScope.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MinspectionScope.setToolTipText("");
        _MinspectionScope.setBorder(null);
        _MinspectionScope.setMinimumSize(new java.awt.Dimension(470, 46));
        _MinspectionScope.setName(""); // NOI18N
        _MinspectionScope.setPreferredSize(new java.awt.Dimension(470, 46));
        _MinspectionScope.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                _MinspectionScopeFocusLost(evt);
            }
        });
        _MinspectionScope.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel4.add(_MinspectionScope, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 56, 193, 48));

        _MdrawingNo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdrawingNo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdrawingNo.setToolTipText("");
        _MdrawingNo.setBorder(null);
        _MdrawingNo.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdrawingNo.setName(""); // NOI18N
        _MdrawingNo.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdrawingNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdrawingNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel4.add(_MdrawingNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 108, 193, 48));

        _MsurfaceCondition.setEditable(false);
        _MsurfaceCondition.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MsurfaceCondition.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MsurfaceCondition.setToolTipText("");
        _MsurfaceCondition.setBorder(null);
        _MsurfaceCondition.setMinimumSize(new java.awt.Dimension(470, 46));
        _MsurfaceCondition.setName(""); // NOI18N
        _MsurfaceCondition.setPreferredSize(new java.awt.Dimension(470, 46));
        _MsurfaceCondition.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MsurfaceCondition.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel4.add(_MsurfaceCondition, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 160, 193, 47));

        jPanel6.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 150, 200, 260));

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        _MevaluationStandard.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MevaluationStandard.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MevaluationStandard.setToolTipText("");
        _MevaluationStandard.setBorder(null);
        _MevaluationStandard.setMinimumSize(new java.awt.Dimension(470, 46));
        _MevaluationStandard.setName(""); // NOI18N
        _MevaluationStandard.setPreferredSize(new java.awt.Dimension(470, 46));
        _MevaluationStandard.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MevaluationStandard.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel2.add(_MevaluationStandard, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 211, 465, 48));

        _Mcustomer.setEditable(false);
        _Mcustomer.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mcustomer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mcustomer.setToolTipText("");
        _Mcustomer.setBorder(null);
        _Mcustomer.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        _Mcustomer.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mcustomer.setName(""); // NOI18N
        _Mcustomer.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mcustomer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _Mcustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel2.add(_Mcustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 6, 465, 46));

        _Mproject.setEditable(false);
        _Mproject.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mproject.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mproject.setToolTipText("");
        _Mproject.setBorder(null);
        _Mproject.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mproject.setName(""); // NOI18N
        _Mproject.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mproject.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _Mproject.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel2.add(_Mproject, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 56, 465, 48));

        _MinspectionPlace.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MinspectionPlace.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MinspectionPlace.setToolTipText("");
        _MinspectionPlace.setBorder(null);
        _MinspectionPlace.setMinimumSize(new java.awt.Dimension(470, 46));
        _MinspectionPlace.setName(""); // NOI18N
        _MinspectionPlace.setPreferredSize(new java.awt.Dimension(470, 46));
        _MinspectionPlace.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MinspectionPlace.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel2.add(_MinspectionPlace, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 108, 465, 48));

        _MinspectionStandard.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MinspectionStandard.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MinspectionStandard.setToolTipText("");
        _MinspectionStandard.setBorder(null);
        _MinspectionStandard.setMinimumSize(new java.awt.Dimension(470, 46));
        _MinspectionStandard.setName(""); // NOI18N
        _MinspectionStandard.setPreferredSize(new java.awt.Dimension(470, 46));
        _MinspectionStandard.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MinspectionStandard.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel2.add(_MinspectionStandard, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 160, 465, 47));

        jPanel6.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 470, 260));

        jPanel8.setOpaque(false);
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        _MofferNo.setEditable(false);
        _MofferNo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MofferNo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MofferNo.setToolTipText("");
        _MofferNo.setBorder(null);
        _MofferNo.setMinimumSize(new java.awt.Dimension(470, 46));
        _MofferNo.setName(""); // NOI18N
        _MofferNo.setPreferredSize(new java.awt.Dimension(470, 46));
        _MofferNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MofferNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel8.add(_MofferNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 211, 148, 48));

        _Mpage.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mpage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mpage.setToolTipText("");
        _Mpage.setBorder(null);
        _Mpage.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mpage.setName(""); // NOI18N
        _Mpage.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mpage.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _Mpage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel8.add(_Mpage, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 6, 148, 46));

        _MreportNo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MreportNo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MreportNo.setToolTipText("");
        _MreportNo.setBorder(null);
        _MreportNo.setMinimumSize(new java.awt.Dimension(470, 46));
        _MreportNo.setName(""); // NOI18N
        _MreportNo.setPreferredSize(new java.awt.Dimension(470, 46));
        _MreportNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MreportNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel8.add(_MreportNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 56, 148, 48));

        _MreportDate.setEditable(false);
        _MreportDate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MreportDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MreportDate.setToolTipText("");
        _MreportDate.setBorder(null);
        _MreportDate.setMinimumSize(new java.awt.Dimension(470, 46));
        _MreportDate.setName(""); // NOI18N
        _MreportDate.setPreferredSize(new java.awt.Dimension(470, 46));
        _MreportDate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MreportDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel8.add(_MreportDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 108, 148, 48));

        _MorderNo.setEditable(false);
        _MorderNo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MorderNo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MorderNo.setToolTipText("");
        _MorderNo.setBorder(null);
        _MorderNo.setMinimumSize(new java.awt.Dimension(470, 46));
        _MorderNo.setName(""); // NOI18N
        _MorderNo.setPreferredSize(new java.awt.Dimension(470, 46));
        _MorderNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MorderNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel8.add(_MorderNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 160, 148, 47));

        jPanel6.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 150, 160, 260));

        jPanel9.setOpaque(false);
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        _distanceOfLight.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _distanceOfLight.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _distanceOfLight.setToolTipText("");
        _distanceOfLight.setBorder(null);
        _distanceOfLight.setMinimumSize(new java.awt.Dimension(470, 46));
        _distanceOfLight.setName(""); // NOI18N
        _distanceOfLight.setPreferredSize(new java.awt.Dimension(470, 46));
        _distanceOfLight.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _distanceOfLight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel9.add(_distanceOfLight, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 260, 227, 46));

        _poleDistance.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _poleDistance.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _poleDistance.setToolTipText("");
        _poleDistance.setBorder(null);
        _poleDistance.setMinimumSize(new java.awt.Dimension(470, 46));
        _poleDistance.setName(""); // NOI18N
        _poleDistance.setPreferredSize(new java.awt.Dimension(470, 46));
        _poleDistance.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _poleDistance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel9.add(_poleDistance, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 7, 227, 48));

        _Mequipment.setEditable(false);
        _Mequipment.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mequipment.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mequipment.setToolTipText("");
        _Mequipment.setBorder(null);
        _Mequipment.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mequipment.setName(""); // NOI18N
        _Mequipment.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mequipment.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _Mequipment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel9.add(_Mequipment, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 58, 227, 47));

        _MPCarrier.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MPCarrier.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MPCarrier.setToolTipText("");
        _MPCarrier.setBorder(null);
        _MPCarrier.setMinimumSize(new java.awt.Dimension(470, 46));
        _MPCarrier.setName(""); // NOI18N
        _MPCarrier.setPreferredSize(new java.awt.Dimension(470, 46));
        _MPCarrier.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MPCarrier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel9.add(_MPCarrier, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 109, 227, 47));

        _UV.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _UV.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _UV.setToolTipText("");
        _UV.setBorder(null);
        _UV.setMinimumSize(new java.awt.Dimension(470, 46));
        _UV.setName(""); // NOI18N
        _UV.setPreferredSize(new java.awt.Dimension(470, 46));
        _UV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _UV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel9.add(_UV, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 210, 227, 47));

        _magTech.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _magTech.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _magTech.setToolTipText("");
        _magTech.setBorder(null);
        _magTech.setMinimumSize(new java.awt.Dimension(470, 46));
        _magTech.setName(""); // NOI18N
        _magTech.setPreferredSize(new java.awt.Dimension(470, 46));
        _magTech.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _magTech_weldingProcess7FocusGained(evt);
            }
        });
        _magTech.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _magTech_weldingProcess7KeyReleased(evt);
            }
        });
        jPanel9.add(_magTech, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 159, 227, 47));

        jPanel6.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 440, 230, 310));

        jPanel10.setOpaque(false);
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        _MheatTreatment.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MheatTreatment.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MheatTreatment.setToolTipText("");
        _MheatTreatment.setBorder(null);
        _MheatTreatment.setMinimumSize(new java.awt.Dimension(470, 46));
        _MheatTreatment.setName(""); // NOI18N
        _MheatTreatment.setPreferredSize(new java.awt.Dimension(470, 46));
        _MheatTreatment.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MheatTreatment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel10.add(_MheatTreatment, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 260, 283, 46));

        _examinationArea.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _examinationArea.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _examinationArea.setToolTipText("");
        _examinationArea.setBorder(null);
        _examinationArea.setMinimumSize(new java.awt.Dimension(470, 46));
        _examinationArea.setName(""); // NOI18N
        _examinationArea.setPreferredSize(new java.awt.Dimension(470, 46));
        _examinationArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _examinationArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel10.add(_examinationArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 7, 283, 48));

        _currentType.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _currentType.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _currentType.setToolTipText("");
        _currentType.setBorder(null);
        _currentType.setMinimumSize(new java.awt.Dimension(470, 46));
        _currentType.setName(""); // NOI18N
        _currentType.setPreferredSize(new java.awt.Dimension(470, 46));
        _currentType.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _currentType.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel10.add(_currentType, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 58, 283, 47));

        _luxmeter.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _luxmeter.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _luxmeter.setToolTipText("");
        _luxmeter.setBorder(null);
        _luxmeter.setMinimumSize(new java.awt.Dimension(470, 46));
        _luxmeter.setName(""); // NOI18N
        _luxmeter.setPreferredSize(new java.awt.Dimension(470, 46));
        _luxmeter.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _luxmeter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel10.add(_luxmeter, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 109, 283, 46));

        _testMedium.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _testMedium.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _testMedium.setToolTipText("");
        _testMedium.setBorder(null);
        _testMedium.setMinimumSize(new java.awt.Dimension(470, 46));
        _testMedium.setName(""); // NOI18N
        _testMedium.setPreferredSize(new java.awt.Dimension(470, 46));
        _testMedium.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _testMedium.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel10.add(_testMedium, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 159, 283, 47));

        _demagnetization.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _demagnetization.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _demagnetization.setToolTipText("");
        _demagnetization.setBorder(null);
        _demagnetization.setMinimumSize(new java.awt.Dimension(470, 46));
        _demagnetization.setName(""); // NOI18N
        _demagnetization.setPreferredSize(new java.awt.Dimension(470, 46));
        _demagnetization.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _demagnetization.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel10.add(_demagnetization, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 210, 283, 47));

        jPanel6.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 440, 290, 310));

        jPanel11.setOpaque(false);
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        _gauss.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _gauss.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _gauss.setToolTipText("");
        _gauss.setBorder(null);
        _gauss.setMinimumSize(new java.awt.Dimension(470, 46));
        _gauss.setName(""); // NOI18N
        _gauss.setPreferredSize(new java.awt.Dimension(470, 46));
        _gauss.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _gauss.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel11.add(_gauss, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 58, 197, 97));

        _liftingTestDate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _liftingTestDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _liftingTestDate.setToolTipText("");
        _liftingTestDate.setBorder(null);
        _liftingTestDate.setMinimumSize(new java.awt.Dimension(470, 46));
        _liftingTestDate.setName(""); // NOI18N
        _liftingTestDate.setPreferredSize(new java.awt.Dimension(470, 46));
        _liftingTestDate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _liftingTestDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel11.add(_liftingTestDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 260, 197, 46));

        _surfaceTemperature.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _surfaceTemperature.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _surfaceTemperature.setToolTipText("");
        _surfaceTemperature.setBorder(null);
        _surfaceTemperature.setMinimumSize(new java.awt.Dimension(470, 46));
        _surfaceTemperature.setName(""); // NOI18N
        _surfaceTemperature.setPreferredSize(new java.awt.Dimension(470, 46));
        _surfaceTemperature.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _surfaceTemperature.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel11.add(_surfaceTemperature, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 7, 197, 48));

        _MsurfaceCondition2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MsurfaceCondition2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MsurfaceCondition2.setToolTipText("");
        _MsurfaceCondition2.setBorder(null);
        _MsurfaceCondition2.setMinimumSize(new java.awt.Dimension(470, 46));
        _MsurfaceCondition2.setName(""); // NOI18N
        _MsurfaceCondition2.setPreferredSize(new java.awt.Dimension(470, 46));
        _MsurfaceCondition2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MsurfaceCondition2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel11.add(_MsurfaceCondition2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 159, 197, 47));

        _identification.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _identification.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _identification.setToolTipText("");
        _identification.setBorder(null);
        _identification.setMinimumSize(new java.awt.Dimension(470, 46));
        _identification.setName(""); // NOI18N
        _identification.setPreferredSize(new java.awt.Dimension(470, 46));
        _identification.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _identification.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel11.add(_identification, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 210, 197, 47));

        jPanel6.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 440, 210, 310));

        jPanel12.setOpaque(false);
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Butt Weld");
        jLabel20.setOpaque(true);
        jPanel12.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 110, 20));

        jLabel35.setBackground(new java.awt.Color(255, 255, 255));
        jLabel35.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Alın Kaynağı");
        jLabel35.setOpaque(true);
        jPanel12.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 104, 110, 30));

        _fillet.setBackground(new java.awt.Color(255, 255, 255));
        _fillet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _fillet.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _fillet.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        _fillet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _buttMouseClicked(evt);
            }
        });
        _fillet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _filletActionPerformed(evt);
            }
        });
        jPanel12.add(_fillet, new org.netbeans.lib.awtextra.AbsoluteConstraints(308, 10, 350, 170));

        jLabel36.setBackground(new java.awt.Color(255, 255, 255));
        jLabel36.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Köşe Kaynağı");
        jLabel36.setOpaque(true);
        jPanel12.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 120, 110, 30));

        jLabel37.setBackground(new java.awt.Color(255, 255, 255));
        jLabel37.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Fillet Weld");
        jLabel37.setOpaque(true);
        jPanel12.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 140, 110, 30));

        _butt.setBackground(new java.awt.Color(255, 255, 255));
        _butt.setToolTipText("");
        _butt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        _butt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _butt.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        _butt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _buttMouseClicked(evt);
            }
        });
        _butt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _buttActionPerformed(evt);
            }
        });
        jPanel12.add(_butt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 290, 170));

        jLabel39.setBackground(new java.awt.Color(255, 255, 255));
        jLabel39.setOpaque(true);
        jPanel12.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 130, 80, 40));

        jLabel40.setBackground(new java.awt.Color(255, 255, 255));
        jLabel40.setOpaque(true);
        jPanel12.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 124, 80, 40));

        jPanel6.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 750, 660, 180));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setOpaque(false);
        jPanel14.setLayout(null);

        _Mdescription.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mdescription.setToolTipText("");
        _Mdescription.setBorder(null);
        _Mdescription.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mdescription.setName(""); // NOI18N
        _Mdescription.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mdescription.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _Mdescription.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel14.add(_Mdescription);
        _Mdescription.setBounds(3, 100, 1040, 44);

        _standardDeviations.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _standardDeviations.setToolTipText("");
        _standardDeviations.setBorder(null);
        _standardDeviations.setMinimumSize(new java.awt.Dimension(470, 46));
        _standardDeviations.setName(""); // NOI18N
        _standardDeviations.setPreferredSize(new java.awt.Dimension(470, 46));
        _standardDeviations.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _standardDeviations.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel14.add(_standardDeviations);
        _standardDeviations.setBounds(3, 6, 1040, 43);

        _MinspectionDates.setEditable(false);
        _MinspectionDates.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MinspectionDates.setForeground(new java.awt.Color(255, 0, 51));
        _MinspectionDates.setToolTipText("");
        _MinspectionDates.setBorder(null);
        _MinspectionDates.setMinimumSize(new java.awt.Dimension(470, 46));
        _MinspectionDates.setName(""); // NOI18N
        _MinspectionDates.setPreferredSize(new java.awt.Dimension(470, 46));
        _MinspectionDates.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MinspectionDates.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel14.add(_MinspectionDates);
        _MinspectionDates.setBounds(3, 53, 1040, 44);

        jPanel6.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 930, 1050, 150));

        jPanel13.setOpaque(false);
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        _result1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _result1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _result1.setToolTipText("");
        _result1.setBorder(null);
        _result1.setMinimumSize(new java.awt.Dimension(470, 46));
        _result1.setName(""); // NOI18N
        _result1.setPreferredSize(new java.awt.Dimension(470, 46));
        _result1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _result1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_result1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1266, 2, 77, 36));

        _pieceNo1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _pieceNo1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _pieceNo1.setToolTipText("");
        _pieceNo1.setBorder(null);
        _pieceNo1.setMinimumSize(new java.awt.Dimension(470, 46));
        _pieceNo1.setName(""); // NOI18N
        _pieceNo1.setPreferredSize(new java.awt.Dimension(470, 46));
        _pieceNo1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _pieceNo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_pieceNo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 2, 253, 36));

        _testLength1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _testLength1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _testLength1.setToolTipText("");
        _testLength1.setBorder(null);
        _testLength1.setMinimumSize(new java.awt.Dimension(470, 46));
        _testLength1.setName(""); // NOI18N
        _testLength1.setPreferredSize(new java.awt.Dimension(470, 46));
        _testLength1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _testLength1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_testLength1, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 2, 150, 36));

        _weldingProcess1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _weldingProcess1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _weldingProcess1.setToolTipText("");
        _weldingProcess1.setBorder(null);
        _weldingProcess1.setMinimumSize(new java.awt.Dimension(470, 46));
        _weldingProcess1.setName(""); // NOI18N
        _weldingProcess1.setPreferredSize(new java.awt.Dimension(470, 46));
        _weldingProcess1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _weldingProcess1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_weldingProcess1, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 2, 204, 36));

        _Mthickness1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mthickness1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mthickness1.setToolTipText("");
        _Mthickness1.setBorder(null);
        _Mthickness1.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mthickness1.setName(""); // NOI18N
        _Mthickness1.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mthickness1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _Mthickness1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_Mthickness1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 2, 108, 36));

        _diameter1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _diameter1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _diameter1.setToolTipText("");
        _diameter1.setBorder(null);
        _diameter1.setMinimumSize(new java.awt.Dimension(470, 46));
        _diameter1.setName(""); // NOI18N
        _diameter1.setPreferredSize(new java.awt.Dimension(470, 46));
        _diameter1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _diameter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_diameter1, new org.netbeans.lib.awtextra.AbsoluteConstraints(812, 2, 132, 36));

        _MdefectType1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectType1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectType1.setToolTipText("");
        _MdefectType1.setBorder(null);
        _MdefectType1.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectType1.setName(""); // NOI18N
        _MdefectType1.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectType1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectType1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectType1, new org.netbeans.lib.awtextra.AbsoluteConstraints(948, 2, 134, 36));

        _MdefectLocation1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectLocation1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectLocation1.setToolTipText("");
        _MdefectLocation1.setBorder(null);
        _MdefectLocation1.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectLocation1.setName(""); // NOI18N
        _MdefectLocation1.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectLocation1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectLocation1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectLocation1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1086, 2, 176, 36));

        _result2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _result2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _result2.setToolTipText("");
        _result2.setBorder(null);
        _result2.setMinimumSize(new java.awt.Dimension(470, 46));
        _result2.setName(""); // NOI18N
        _result2.setPreferredSize(new java.awt.Dimension(470, 46));
        _result2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _result2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_result2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1266, 42, 77, 36));

        _MdefectLocation2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectLocation2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectLocation2.setToolTipText("");
        _MdefectLocation2.setBorder(null);
        _MdefectLocation2.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectLocation2.setName(""); // NOI18N
        _MdefectLocation2.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectLocation2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectLocation2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectLocation2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1086, 42, 176, 36));

        _MdefectType2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectType2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectType2.setToolTipText("");
        _MdefectType2.setBorder(null);
        _MdefectType2.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectType2.setName(""); // NOI18N
        _MdefectType2.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectType2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectType2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectType2, new org.netbeans.lib.awtextra.AbsoluteConstraints(948, 42, 134, 36));

        _diameter2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _diameter2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _diameter2.setToolTipText("");
        _diameter2.setBorder(null);
        _diameter2.setMinimumSize(new java.awt.Dimension(470, 46));
        _diameter2.setName(""); // NOI18N
        _diameter2.setPreferredSize(new java.awt.Dimension(470, 46));
        _diameter2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _diameter2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_diameter2, new org.netbeans.lib.awtextra.AbsoluteConstraints(812, 42, 132, 36));

        _Mthickness2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mthickness2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mthickness2.setToolTipText("");
        _Mthickness2.setBorder(null);
        _Mthickness2.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mthickness2.setName(""); // NOI18N
        _Mthickness2.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mthickness2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _Mthickness2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_Mthickness2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 42, 108, 36));

        _weldingProcess2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _weldingProcess2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _weldingProcess2.setToolTipText("");
        _weldingProcess2.setBorder(null);
        _weldingProcess2.setMinimumSize(new java.awt.Dimension(470, 46));
        _weldingProcess2.setName(""); // NOI18N
        _weldingProcess2.setPreferredSize(new java.awt.Dimension(470, 46));
        _weldingProcess2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _weldingProcess2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_weldingProcess2, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 42, 204, 36));

        _testLength2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _testLength2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _testLength2.setToolTipText("");
        _testLength2.setBorder(null);
        _testLength2.setMinimumSize(new java.awt.Dimension(470, 46));
        _testLength2.setName(""); // NOI18N
        _testLength2.setPreferredSize(new java.awt.Dimension(470, 46));
        _testLength2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _testLength2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_testLength2, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 42, 150, 36));

        _pieceNo2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _pieceNo2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _pieceNo2.setToolTipText("");
        _pieceNo2.setBorder(null);
        _pieceNo2.setMinimumSize(new java.awt.Dimension(470, 46));
        _pieceNo2.setName(""); // NOI18N
        _pieceNo2.setPreferredSize(new java.awt.Dimension(470, 46));
        _pieceNo2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _pieceNo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_pieceNo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 42, 253, 36));

        _MdefectType3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectType3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectType3.setToolTipText("");
        _MdefectType3.setBorder(null);
        _MdefectType3.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectType3.setName(""); // NOI18N
        _MdefectType3.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectType3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectType3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectType3, new org.netbeans.lib.awtextra.AbsoluteConstraints(948, 82, 134, 36));

        _testLength3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _testLength3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _testLength3.setToolTipText("");
        _testLength3.setBorder(null);
        _testLength3.setMinimumSize(new java.awt.Dimension(470, 46));
        _testLength3.setName(""); // NOI18N
        _testLength3.setPreferredSize(new java.awt.Dimension(470, 46));
        _testLength3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _testLength3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_testLength3, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 82, 150, 36));

        _result3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _result3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _result3.setToolTipText("");
        _result3.setBorder(null);
        _result3.setMinimumSize(new java.awt.Dimension(470, 46));
        _result3.setName(""); // NOI18N
        _result3.setPreferredSize(new java.awt.Dimension(470, 46));
        _result3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _result3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_result3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1266, 82, 77, 36));

        _weldingProcess3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _weldingProcess3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _weldingProcess3.setToolTipText("");
        _weldingProcess3.setBorder(null);
        _weldingProcess3.setMinimumSize(new java.awt.Dimension(470, 46));
        _weldingProcess3.setName(""); // NOI18N
        _weldingProcess3.setPreferredSize(new java.awt.Dimension(470, 46));
        _weldingProcess3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _weldingProcess3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_weldingProcess3, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 82, 204, 36));

        _diameter3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _diameter3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _diameter3.setToolTipText("");
        _diameter3.setBorder(null);
        _diameter3.setMinimumSize(new java.awt.Dimension(470, 46));
        _diameter3.setName(""); // NOI18N
        _diameter3.setPreferredSize(new java.awt.Dimension(470, 46));
        _diameter3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _diameter3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_diameter3, new org.netbeans.lib.awtextra.AbsoluteConstraints(812, 82, 132, 36));

        _Mthickness3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mthickness3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mthickness3.setToolTipText("");
        _Mthickness3.setBorder(null);
        _Mthickness3.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mthickness3.setName(""); // NOI18N
        _Mthickness3.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mthickness3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _Mthickness3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_Mthickness3, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 82, 108, 36));

        _MdefectLocation3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectLocation3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectLocation3.setToolTipText("");
        _MdefectLocation3.setBorder(null);
        _MdefectLocation3.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectLocation3.setName(""); // NOI18N
        _MdefectLocation3.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectLocation3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectLocation3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectLocation3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1086, 82, 176, 36));

        _pieceNo3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _pieceNo3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _pieceNo3.setToolTipText("");
        _pieceNo3.setBorder(null);
        _pieceNo3.setMinimumSize(new java.awt.Dimension(470, 46));
        _pieceNo3.setName(""); // NOI18N
        _pieceNo3.setPreferredSize(new java.awt.Dimension(470, 46));
        _pieceNo3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _pieceNo3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_pieceNo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 82, 253, 36));

        _MdefectType4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectType4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectType4.setToolTipText("");
        _MdefectType4.setBorder(null);
        _MdefectType4.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectType4.setName(""); // NOI18N
        _MdefectType4.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectType4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectType4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectType4, new org.netbeans.lib.awtextra.AbsoluteConstraints(948, 122, 134, 35));

        _Mthickness4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mthickness4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mthickness4.setToolTipText("");
        _Mthickness4.setBorder(null);
        _Mthickness4.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mthickness4.setName(""); // NOI18N
        _Mthickness4.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mthickness4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _Mthickness4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_Mthickness4, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 122, 108, 35));

        _testLength4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _testLength4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _testLength4.setToolTipText("");
        _testLength4.setBorder(null);
        _testLength4.setMinimumSize(new java.awt.Dimension(470, 46));
        _testLength4.setName(""); // NOI18N
        _testLength4.setPreferredSize(new java.awt.Dimension(470, 46));
        _testLength4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _testLength4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_testLength4, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 122, 150, 35));

        _result4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _result4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _result4.setToolTipText("");
        _result4.setBorder(null);
        _result4.setMinimumSize(new java.awt.Dimension(470, 46));
        _result4.setName(""); // NOI18N
        _result4.setPreferredSize(new java.awt.Dimension(470, 46));
        _result4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _result4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_result4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1266, 122, 77, 35));

        _diameter4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _diameter4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _diameter4.setToolTipText("");
        _diameter4.setBorder(null);
        _diameter4.setMinimumSize(new java.awt.Dimension(470, 46));
        _diameter4.setName(""); // NOI18N
        _diameter4.setPreferredSize(new java.awt.Dimension(470, 46));
        _diameter4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _diameter4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_diameter4, new org.netbeans.lib.awtextra.AbsoluteConstraints(812, 122, 132, 35));

        _MdefectLocation4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectLocation4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectLocation4.setToolTipText("");
        _MdefectLocation4.setBorder(null);
        _MdefectLocation4.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectLocation4.setName(""); // NOI18N
        _MdefectLocation4.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectLocation4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectLocation4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectLocation4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1086, 122, 176, 35));

        _weldingProcess4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _weldingProcess4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _weldingProcess4.setToolTipText("");
        _weldingProcess4.setBorder(null);
        _weldingProcess4.setMinimumSize(new java.awt.Dimension(470, 46));
        _weldingProcess4.setName(""); // NOI18N
        _weldingProcess4.setPreferredSize(new java.awt.Dimension(470, 46));
        _weldingProcess4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _weldingProcess4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_weldingProcess4, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 122, 204, 35));

        _pieceNo4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _pieceNo4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _pieceNo4.setToolTipText("");
        _pieceNo4.setBorder(null);
        _pieceNo4.setMinimumSize(new java.awt.Dimension(470, 46));
        _pieceNo4.setName(""); // NOI18N
        _pieceNo4.setPreferredSize(new java.awt.Dimension(470, 46));
        _pieceNo4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _pieceNo4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_pieceNo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 122, 253, 35));

        _testLength8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _testLength8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _testLength8.setToolTipText("");
        _testLength8.setBorder(null);
        _testLength8.setMinimumSize(new java.awt.Dimension(470, 46));
        _testLength8.setName(""); // NOI18N
        _testLength8.setPreferredSize(new java.awt.Dimension(470, 46));
        _testLength8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _testLength8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_testLength8, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 281, 150, 35));

        _weldingProcess8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _weldingProcess8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _weldingProcess8.setToolTipText("");
        _weldingProcess8.setBorder(null);
        _weldingProcess8.setMinimumSize(new java.awt.Dimension(470, 46));
        _weldingProcess8.setName(""); // NOI18N
        _weldingProcess8.setPreferredSize(new java.awt.Dimension(470, 46));
        _weldingProcess8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _weldingProcess8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_weldingProcess8, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 281, 204, 35));

        _Mthickness8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mthickness8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mthickness8.setToolTipText("");
        _Mthickness8.setBorder(null);
        _Mthickness8.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mthickness8.setName(""); // NOI18N
        _Mthickness8.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mthickness8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _Mthickness8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_Mthickness8, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 281, 108, 35));

        _result8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _result8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _result8.setToolTipText("");
        _result8.setBorder(null);
        _result8.setMinimumSize(new java.awt.Dimension(470, 46));
        _result8.setName(""); // NOI18N
        _result8.setPreferredSize(new java.awt.Dimension(470, 46));
        _result8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _result8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_result8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1266, 281, 77, 35));

        _pieceNo8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _pieceNo8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _pieceNo8.setToolTipText("");
        _pieceNo8.setBorder(null);
        _pieceNo8.setMinimumSize(new java.awt.Dimension(470, 46));
        _pieceNo8.setName(""); // NOI18N
        _pieceNo8.setPreferredSize(new java.awt.Dimension(470, 46));
        _pieceNo8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _pieceNo8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_pieceNo8, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 281, 253, 35));

        _MdefectLocation8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectLocation8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectLocation8.setToolTipText("");
        _MdefectLocation8.setBorder(null);
        _MdefectLocation8.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectLocation8.setName(""); // NOI18N
        _MdefectLocation8.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectLocation8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectLocation8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectLocation8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1086, 281, 176, 35));

        _diameter8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _diameter8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _diameter8.setToolTipText("");
        _diameter8.setBorder(null);
        _diameter8.setMinimumSize(new java.awt.Dimension(470, 46));
        _diameter8.setName(""); // NOI18N
        _diameter8.setPreferredSize(new java.awt.Dimension(470, 46));
        _diameter8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _diameter8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_diameter8, new org.netbeans.lib.awtextra.AbsoluteConstraints(812, 281, 132, 35));

        _MdefectType8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectType8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectType8.setToolTipText("");
        _MdefectType8.setBorder(null);
        _MdefectType8.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectType8.setName(""); // NOI18N
        _MdefectType8.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectType8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectType8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectType8, new org.netbeans.lib.awtextra.AbsoluteConstraints(948, 281, 134, 35));

        _pieceNo7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _pieceNo7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _pieceNo7.setToolTipText("");
        _pieceNo7.setBorder(null);
        _pieceNo7.setMinimumSize(new java.awt.Dimension(470, 46));
        _pieceNo7.setName(""); // NOI18N
        _pieceNo7.setPreferredSize(new java.awt.Dimension(470, 46));
        _pieceNo7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _pieceNo7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_pieceNo7, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 241, 253, 36));

        _pieceNo6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _pieceNo6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _pieceNo6.setToolTipText("");
        _pieceNo6.setBorder(null);
        _pieceNo6.setMinimumSize(new java.awt.Dimension(470, 46));
        _pieceNo6.setName(""); // NOI18N
        _pieceNo6.setPreferredSize(new java.awt.Dimension(470, 46));
        _pieceNo6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _pieceNo6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_pieceNo6, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 201, 253, 36));

        _pieceNo5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _pieceNo5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _pieceNo5.setToolTipText("");
        _pieceNo5.setBorder(null);
        _pieceNo5.setMinimumSize(new java.awt.Dimension(470, 46));
        _pieceNo5.setName(""); // NOI18N
        _pieceNo5.setPreferredSize(new java.awt.Dimension(470, 46));
        _pieceNo5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _pieceNo5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_pieceNo5, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 161, 253, 36));

        _testLength5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _testLength5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _testLength5.setToolTipText("");
        _testLength5.setBorder(null);
        _testLength5.setMinimumSize(new java.awt.Dimension(470, 46));
        _testLength5.setName(""); // NOI18N
        _testLength5.setPreferredSize(new java.awt.Dimension(470, 46));
        _testLength5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _testLength5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_testLength5, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 161, 150, 36));

        _testLength6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _testLength6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _testLength6.setToolTipText("");
        _testLength6.setBorder(null);
        _testLength6.setMinimumSize(new java.awt.Dimension(470, 46));
        _testLength6.setName(""); // NOI18N
        _testLength6.setPreferredSize(new java.awt.Dimension(470, 46));
        _testLength6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _testLength6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_testLength6, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 201, 150, 36));

        _testLength7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _testLength7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _testLength7.setToolTipText("");
        _testLength7.setBorder(null);
        _testLength7.setMinimumSize(new java.awt.Dimension(470, 46));
        _testLength7.setName(""); // NOI18N
        _testLength7.setPreferredSize(new java.awt.Dimension(470, 46));
        _testLength7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _testLength7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_testLength7, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 241, 150, 36));

        _weldingProcess7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _weldingProcess7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _weldingProcess7.setToolTipText("");
        _weldingProcess7.setBorder(null);
        _weldingProcess7.setMinimumSize(new java.awt.Dimension(470, 46));
        _weldingProcess7.setName(""); // NOI18N
        _weldingProcess7.setPreferredSize(new java.awt.Dimension(470, 46));
        _weldingProcess7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _weldingProcess7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_weldingProcess7, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 241, 204, 36));

        _weldingProcess6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _weldingProcess6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _weldingProcess6.setToolTipText("");
        _weldingProcess6.setBorder(null);
        _weldingProcess6.setMinimumSize(new java.awt.Dimension(470, 46));
        _weldingProcess6.setName(""); // NOI18N
        _weldingProcess6.setPreferredSize(new java.awt.Dimension(470, 46));
        _weldingProcess6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _weldingProcess6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_weldingProcess6, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 201, 204, 36));

        _weldingProcess5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _weldingProcess5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _weldingProcess5.setToolTipText("");
        _weldingProcess5.setBorder(null);
        _weldingProcess5.setMinimumSize(new java.awt.Dimension(470, 46));
        _weldingProcess5.setName(""); // NOI18N
        _weldingProcess5.setPreferredSize(new java.awt.Dimension(470, 46));
        _weldingProcess5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _weldingProcess5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_weldingProcess5, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 161, 204, 36));

        _Mthickness5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mthickness5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mthickness5.setToolTipText("");
        _Mthickness5.setBorder(null);
        _Mthickness5.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mthickness5.setName(""); // NOI18N
        _Mthickness5.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mthickness5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _Mthickness5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_Mthickness5, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 161, 108, 36));

        _Mthickness6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mthickness6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mthickness6.setToolTipText("");
        _Mthickness6.setBorder(null);
        _Mthickness6.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mthickness6.setName(""); // NOI18N
        _Mthickness6.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mthickness6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _Mthickness6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_Mthickness6, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 201, 108, 36));

        _Mthickness7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mthickness7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mthickness7.setToolTipText("");
        _Mthickness7.setBorder(null);
        _Mthickness7.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mthickness7.setName(""); // NOI18N
        _Mthickness7.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mthickness7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _Mthickness7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_Mthickness7, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 241, 108, 36));

        _diameter7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _diameter7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _diameter7.setToolTipText("");
        _diameter7.setBorder(null);
        _diameter7.setMinimumSize(new java.awt.Dimension(470, 46));
        _diameter7.setName(""); // NOI18N
        _diameter7.setPreferredSize(new java.awt.Dimension(470, 46));
        _diameter7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _diameter7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_diameter7, new org.netbeans.lib.awtextra.AbsoluteConstraints(812, 241, 132, 36));

        _diameter6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _diameter6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _diameter6.setToolTipText("");
        _diameter6.setBorder(null);
        _diameter6.setMinimumSize(new java.awt.Dimension(470, 46));
        _diameter6.setName(""); // NOI18N
        _diameter6.setPreferredSize(new java.awt.Dimension(470, 46));
        _diameter6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _diameter6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_diameter6, new org.netbeans.lib.awtextra.AbsoluteConstraints(812, 201, 132, 36));

        _diameter5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _diameter5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _diameter5.setToolTipText("");
        _diameter5.setBorder(null);
        _diameter5.setMinimumSize(new java.awt.Dimension(470, 46));
        _diameter5.setName(""); // NOI18N
        _diameter5.setPreferredSize(new java.awt.Dimension(470, 46));
        _diameter5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _diameter5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_diameter5, new org.netbeans.lib.awtextra.AbsoluteConstraints(812, 161, 132, 36));

        _MdefectType5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectType5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectType5.setToolTipText("");
        _MdefectType5.setBorder(null);
        _MdefectType5.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectType5.setName(""); // NOI18N
        _MdefectType5.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectType5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectType5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectType5, new org.netbeans.lib.awtextra.AbsoluteConstraints(948, 161, 134, 36));

        _MdefectType6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectType6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectType6.setToolTipText("");
        _MdefectType6.setBorder(null);
        _MdefectType6.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectType6.setName(""); // NOI18N
        _MdefectType6.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectType6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectType6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectType6, new org.netbeans.lib.awtextra.AbsoluteConstraints(948, 201, 134, 36));

        _MdefectType7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectType7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectType7.setToolTipText("");
        _MdefectType7.setBorder(null);
        _MdefectType7.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectType7.setName(""); // NOI18N
        _MdefectType7.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectType7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectType7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectType7, new org.netbeans.lib.awtextra.AbsoluteConstraints(948, 241, 134, 36));

        _MdefectLocation7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectLocation7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectLocation7.setToolTipText("");
        _MdefectLocation7.setBorder(null);
        _MdefectLocation7.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectLocation7.setName(""); // NOI18N
        _MdefectLocation7.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectLocation7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectLocation7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectLocation7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1086, 241, 176, 36));

        _MdefectLocation6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectLocation6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectLocation6.setToolTipText("");
        _MdefectLocation6.setBorder(null);
        _MdefectLocation6.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectLocation6.setName(""); // NOI18N
        _MdefectLocation6.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectLocation6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectLocation6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectLocation6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1086, 201, 176, 36));

        _MdefectLocation5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectLocation5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectLocation5.setToolTipText("");
        _MdefectLocation5.setBorder(null);
        _MdefectLocation5.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectLocation5.setName(""); // NOI18N
        _MdefectLocation5.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectLocation5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectLocation5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectLocation5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1086, 161, 176, 36));

        _result5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _result5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _result5.setToolTipText("");
        _result5.setBorder(null);
        _result5.setMinimumSize(new java.awt.Dimension(470, 46));
        _result5.setName(""); // NOI18N
        _result5.setPreferredSize(new java.awt.Dimension(470, 46));
        _result5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _result5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_result5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1266, 161, 77, 36));

        _result6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _result6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _result6.setToolTipText("");
        _result6.setBorder(null);
        _result6.setMinimumSize(new java.awt.Dimension(470, 46));
        _result6.setName(""); // NOI18N
        _result6.setPreferredSize(new java.awt.Dimension(470, 46));
        _result6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _result6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_result6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1266, 201, 77, 36));

        _result7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _result7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _result7.setToolTipText("");
        _result7.setBorder(null);
        _result7.setMinimumSize(new java.awt.Dimension(470, 46));
        _result7.setName(""); // NOI18N
        _result7.setPreferredSize(new java.awt.Dimension(470, 46));
        _result7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _result7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_result7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1266, 241, 77, 36));

        _diameter9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _diameter9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _diameter9.setToolTipText("");
        _diameter9.setBorder(null);
        _diameter9.setMinimumSize(new java.awt.Dimension(470, 46));
        _diameter9.setName(""); // NOI18N
        _diameter9.setPreferredSize(new java.awt.Dimension(470, 46));
        _diameter9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _diameter9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_diameter9, new org.netbeans.lib.awtextra.AbsoluteConstraints(812, 321, 132, 35));

        _testLength9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _testLength9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _testLength9.setToolTipText("");
        _testLength9.setBorder(null);
        _testLength9.setMinimumSize(new java.awt.Dimension(470, 46));
        _testLength9.setName(""); // NOI18N
        _testLength9.setPreferredSize(new java.awt.Dimension(470, 46));
        _testLength9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _testLength9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_testLength9, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 321, 150, 35));

        _Mthickness9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mthickness9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mthickness9.setToolTipText("");
        _Mthickness9.setBorder(null);
        _Mthickness9.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mthickness9.setName(""); // NOI18N
        _Mthickness9.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mthickness9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _Mthickness9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_Mthickness9, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 321, 108, 35));

        _result10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _result10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _result10.setToolTipText("");
        _result10.setBorder(null);
        _result10.setMinimumSize(new java.awt.Dimension(470, 46));
        _result10.setName(""); // NOI18N
        _result10.setPreferredSize(new java.awt.Dimension(470, 46));
        _result10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _result10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_result10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1266, 360, 77, 35));

        _pieceNo10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _pieceNo10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _pieceNo10.setToolTipText("");
        _pieceNo10.setBorder(null);
        _pieceNo10.setMinimumSize(new java.awt.Dimension(470, 46));
        _pieceNo10.setName(""); // NOI18N
        _pieceNo10.setPreferredSize(new java.awt.Dimension(470, 46));
        _pieceNo10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _pieceNo10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_pieceNo10, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 360, 253, 35));

        _MdefectLocation9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectLocation9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectLocation9.setToolTipText("");
        _MdefectLocation9.setBorder(null);
        _MdefectLocation9.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectLocation9.setName(""); // NOI18N
        _MdefectLocation9.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectLocation9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectLocation9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectLocation9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1086, 321, 176, 35));

        _Mthickness10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mthickness10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mthickness10.setToolTipText("");
        _Mthickness10.setBorder(null);
        _Mthickness10.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mthickness10.setName(""); // NOI18N
        _Mthickness10.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mthickness10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _Mthickness10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_Mthickness10, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 360, 108, 35));

        _diameter10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _diameter10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _diameter10.setToolTipText("");
        _diameter10.setBorder(null);
        _diameter10.setMinimumSize(new java.awt.Dimension(470, 46));
        _diameter10.setName(""); // NOI18N
        _diameter10.setPreferredSize(new java.awt.Dimension(470, 46));
        _diameter10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _diameter10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_diameter10, new org.netbeans.lib.awtextra.AbsoluteConstraints(812, 360, 132, 35));

        _testLength10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _testLength10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _testLength10.setToolTipText("");
        _testLength10.setBorder(null);
        _testLength10.setMinimumSize(new java.awt.Dimension(470, 46));
        _testLength10.setName(""); // NOI18N
        _testLength10.setPreferredSize(new java.awt.Dimension(470, 46));
        _testLength10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _testLength10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_testLength10, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 360, 150, 35));

        _weldingProcess10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _weldingProcess10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _weldingProcess10.setToolTipText("");
        _weldingProcess10.setBorder(null);
        _weldingProcess10.setMinimumSize(new java.awt.Dimension(470, 46));
        _weldingProcess10.setName(""); // NOI18N
        _weldingProcess10.setPreferredSize(new java.awt.Dimension(470, 46));
        _weldingProcess10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _weldingProcess10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_weldingProcess10, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 360, 204, 35));

        _result9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _result9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _result9.setToolTipText("");
        _result9.setBorder(null);
        _result9.setMinimumSize(new java.awt.Dimension(470, 46));
        _result9.setName(""); // NOI18N
        _result9.setPreferredSize(new java.awt.Dimension(470, 46));
        _result9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _result9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_result9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1266, 321, 77, 35));

        _pieceNo9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _pieceNo9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _pieceNo9.setToolTipText("");
        _pieceNo9.setBorder(null);
        _pieceNo9.setMinimumSize(new java.awt.Dimension(470, 46));
        _pieceNo9.setName(""); // NOI18N
        _pieceNo9.setPreferredSize(new java.awt.Dimension(470, 46));
        _pieceNo9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _pieceNo9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_pieceNo9, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 321, 253, 35));

        _MdefectType10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectType10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectType10.setToolTipText("");
        _MdefectType10.setBorder(null);
        _MdefectType10.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectType10.setName(""); // NOI18N
        _MdefectType10.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectType10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectType10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectType10, new org.netbeans.lib.awtextra.AbsoluteConstraints(948, 360, 134, 35));

        _MdefectType9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectType9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectType9.setToolTipText("");
        _MdefectType9.setBorder(null);
        _MdefectType9.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectType9.setName(""); // NOI18N
        _MdefectType9.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectType9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectType9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectType9, new org.netbeans.lib.awtextra.AbsoluteConstraints(948, 321, 134, 35));

        _MdefectLocation10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MdefectLocation10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MdefectLocation10.setToolTipText("");
        _MdefectLocation10.setBorder(null);
        _MdefectLocation10.setMinimumSize(new java.awt.Dimension(470, 46));
        _MdefectLocation10.setName(""); // NOI18N
        _MdefectLocation10.setPreferredSize(new java.awt.Dimension(470, 46));
        _MdefectLocation10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _MdefectLocation10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_MdefectLocation10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1086, 360, 176, 35));

        _weldingProcess9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _weldingProcess9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _weldingProcess9.setToolTipText("");
        _weldingProcess9.setBorder(null);
        _weldingProcess9.setMinimumSize(new java.awt.Dimension(470, 46));
        _weldingProcess9.setName(""); // NOI18N
        _weldingProcess9.setPreferredSize(new java.awt.Dimension(470, 46));
        _weldingProcess9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _weldingProcess9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _weldingProcess7KeyReleased(evt);
            }
        });
        jPanel13.add(_weldingProcess9, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 321, 204, 35));

        jPanel6.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1170, 1350, 400));

        jPanel15.setOpaque(false);
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        _MoperatorName.setEditable(false);
        _MoperatorName.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MoperatorName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MoperatorName.setToolTipText("");
        _MoperatorName.setBorder(null);
        _MoperatorName.setMinimumSize(new java.awt.Dimension(470, 46));
        _MoperatorName.setName(""); // NOI18N
        _MoperatorName.setPreferredSize(new java.awt.Dimension(470, 46));
        _MoperatorName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        jPanel15.add(_MoperatorName, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 310, 41));

        _MevaluatorName.setEditable(false);
        _MevaluatorName.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MevaluatorName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MevaluatorName.setToolTipText("");
        _MevaluatorName.setBorder(null);
        _MevaluatorName.setMinimumSize(new java.awt.Dimension(470, 46));
        _MevaluatorName.setName(""); // NOI18N
        _MevaluatorName.setPreferredSize(new java.awt.Dimension(470, 46));
        _MevaluatorName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        jPanel15.add(_MevaluatorName, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 6, 330, 41));

        _MconfirmationName.setEditable(false);
        _MconfirmationName.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MconfirmationName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MconfirmationName.setToolTipText("");
        _MconfirmationName.setBorder(null);
        _MconfirmationName.setMinimumSize(new java.awt.Dimension(470, 46));
        _MconfirmationName.setName(""); // NOI18N
        _MconfirmationName.setPreferredSize(new java.awt.Dimension(470, 46));
        _MconfirmationName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        jPanel15.add(_MconfirmationName, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 6, 230, 41));

        _MoperatorLevel.setEditable(false);
        _MoperatorLevel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MoperatorLevel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MoperatorLevel.setToolTipText("");
        _MoperatorLevel.setBorder(null);
        _MoperatorLevel.setMinimumSize(new java.awt.Dimension(470, 46));
        _MoperatorLevel.setName(""); // NOI18N
        _MoperatorLevel.setPreferredSize(new java.awt.Dimension(470, 46));
        _MoperatorLevel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        jPanel15.add(_MoperatorLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 55, 310, 40));

        _Mbottom.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        _Mbottom.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mbottom.setToolTipText("");
        _Mbottom.setBorder(null);
        _Mbottom.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mbottom.setName(""); // NOI18N
        _Mbottom.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mbottom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        _Mbottom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MbottomActionPerformed(evt);
            }
        });
        jPanel15.add(_Mbottom, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 255, 900, 30));

        _MconfirmationLevel.setEditable(false);
        _MconfirmationLevel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MconfirmationLevel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MconfirmationLevel.setToolTipText("");
        _MconfirmationLevel.setBorder(null);
        _MconfirmationLevel.setMinimumSize(new java.awt.Dimension(470, 46));
        _MconfirmationLevel.setName(""); // NOI18N
        _MconfirmationLevel.setPreferredSize(new java.awt.Dimension(470, 46));
        _MconfirmationLevel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        jPanel15.add(_MconfirmationLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 55, 230, 40));

        _MevaluatorLevel.setEditable(false);
        _MevaluatorLevel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _MevaluatorLevel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _MevaluatorLevel.setToolTipText("");
        _MevaluatorLevel.setBorder(null);
        _MevaluatorLevel.setMinimumSize(new java.awt.Dimension(470, 46));
        _MevaluatorLevel.setName(""); // NOI18N
        _MevaluatorLevel.setPreferredSize(new java.awt.Dimension(470, 46));
        _MevaluatorLevel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        jPanel15.add(_MevaluatorLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 55, 330, 40));

        _Mdate1.setEditable(false);
        _Mdate1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mdate1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mdate1.setToolTipText("");
        _Mdate1.setBorder(null);
        _Mdate1.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mdate1.setName(""); // NOI18N
        _Mdate1.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mdate1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        jPanel15.add(_Mdate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 310, 40));

        _Mdate2.setEditable(false);
        _Mdate2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mdate2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mdate2.setToolTipText("");
        _Mdate2.setBorder(null);
        _Mdate2.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mdate2.setName(""); // NOI18N
        _Mdate2.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mdate2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        jPanel15.add(_Mdate2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 330, 40));

        _Mdate3.setEditable(false);
        _Mdate3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Mdate3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _Mdate3.setToolTipText("");
        _Mdate3.setBorder(null);
        _Mdate3.setMinimumSize(new java.awt.Dimension(470, 46));
        _Mdate3.setName(""); // NOI18N
        _Mdate3.setPreferredSize(new java.awt.Dimension(470, 46));
        _Mdate3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                _weldingProcess7FocusGained(evt);
            }
        });
        jPanel15.add(_Mdate3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 100, 230, 40));

        jPanel6.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 1610, 1100, 270));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/magnetic2.png"))); // NOI18N
        jLabel3.setMaximumSize(new java.awt.Dimension(1350, 1900));
        jLabel3.setMinimumSize(new java.awt.Dimension(1350, 1900));
        jLabel3.setPreferredSize(new java.awt.Dimension(1350, 1900));
        jPanel6.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 1870));

        jTabbedPane1.addTab("Manyetik Parçacık Muayene Raporu", jPanel6);

        jLayeredPane1.add(jTabbedPane1, "card2");

        jScrollPane2.setViewportView(jLayeredPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1258, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

        
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.operator_id = PersonManagement.getPersonId(this.operator);
        this.evaluator_id = PersonManagement.getPersonId(this.evaluator);
        this.confirmation_id = PersonManagement.getPersonId(this.confirmator);
    }//GEN-LAST:event_formWindowOpened

    private void jTabbedPane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MousePressed
        jTabbedPane1.setSize(1350, 2100);
        jLayeredPane1.setSize(1350, 2100);
    }//GEN-LAST:event_jTabbedPane1MousePressed

    private void _MbottomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MbottomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event__MbottomActionPerformed

    private void _buttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__buttActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event__buttActionPerformed

    private void _filletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__filletActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event__filletActionPerformed

    private void _inspectionPlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__inspectionPlaceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event__inspectionPlaceActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        jLabel1.requestFocusInWindow();
        if (!jLabel2.isEnabled() && (everyThingIsOkayM() || everyThingIsOkayR())) {
            if (equip1 != null) {
                printReport(jPanel6, 0.38, 0.36);
            }
            else {
                 printReport(jPanel5, 0.38, 0.34);
            }
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    private void _descriptionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event__descriptionKeyReleased
        jLabel1.setEnabled(false);
        jLabel4.setEnabled(false);
        if (everyThingIsOkayR()) {
            jLabel2.setEnabled(true);
        }
        else {
            jLabel2.setEnabled(false);
        }
    }//GEN-LAST:event__descriptionKeyReleased

    private void _inspectionScopeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__inspectionScopeFocusLost
        if (!_inspectionScope.getText().trim().contains("%")){
            _inspectionScope.setText(_inspectionScope.getText().trim() + " %");
        }
    }//GEN-LAST:event__inspectionScopeFocusLost

    private void _filtersFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__filtersFocusLost
        if (_filters.getText().trim().equals("")){
            _filters.setText("--");
        }
    }//GEN-LAST:event__filtersFocusLost

    private void _heatTreatmentFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__heatTreatmentFocusLost
        if (_heatTreatment.getText().trim().equals("")){
            _heatTreatment.setText("-");
        }
    }//GEN-LAST:event__heatTreatmentFocusLost

    private void _weldingProcess7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event__weldingProcess7KeyReleased
        jLabel1.setEnabled(false);
        jLabel4.setEnabled(false);
        if (everyThingIsOkayM()) {
            jLabel2.setEnabled(true);
        }
        else {
            jLabel2.setEnabled(false);
        }
    }//GEN-LAST:event__weldingProcess7KeyReleased

    private void _weldingProcess7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__weldingProcess7FocusGained
        Component c = this.getFocusOwner();
        JTextField t = (JTextField) c;
        t.selectAll();
    }//GEN-LAST:event__weldingProcess7FocusGained

    private void _customerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__customerFocusGained
        Component c = this.getFocusOwner();
        JTextField t = (JTextField) c;
        t.selectAll();
    }//GEN-LAST:event__customerFocusGained

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        int res = 0;
        if (!jLabel2.isEnabled() && (everyThingIsOkayM() || everyThingIsOkayR())) {
            if (toEditM != null) {
                JFileChooser chooser = new JFileChooser();
                FileFilter ff = new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        if (file.getAbsolutePath().contains(".xlsx")) {
                            return true;
                        }
                        else {
                            return false;
                        }
                    }

                    @Override
                    public String getDescription() {
                        return "Excel Files";
                    }
                };
                chooser.setAcceptAllFileFilterUsed(false);
                chooser.addChoosableFileFilter(ff);
                chooser.setMultiSelectionEnabled(false);
                chooser.setDragEnabled(false);
                chooser.showSaveDialog(null);


            File f = chooser.getSelectedFile();
            res = exportExcelM(f.getAbsolutePath());
            }
            else {
                res = -2;
            }
            process = 3;
            message(res);
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void _MinspectionScopeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__MinspectionScopeFocusLost
        if (!_MinspectionScope.getText().trim().contains("%")){
            _MinspectionScope.setText(_MinspectionScope.getText().trim() + " %");
        }
    }//GEN-LAST:event__MinspectionScopeFocusLost

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        if (everyThingIsOkayM() || everyThingIsOkayR()) {
            if (process == 1) {
                add();
            }
            else {
               update();
            }
        }
        
    }//GEN-LAST:event_jLabel2MouseClicked

    private void _magTech_weldingProcess7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__magTech_weldingProcess7FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event__magTech_weldingProcess7FocusGained

    private void _magTech_weldingProcess7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event__magTech_weldingProcess7KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event__magTech_weldingProcess7KeyReleased

    private void _automaticMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event__automaticMouseClicked
        jLabel1.setEnabled(false);
        jLabel4.setEnabled(false);
        if (everyThingIsOkayR()) {
            jLabel2.setEnabled(true);
        }
        else {
            jLabel2.setEnabled(false);
        }
    }//GEN-LAST:event__automaticMouseClicked

    private void _buttMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event__buttMouseClicked
        jLabel1.setEnabled(false);
        jLabel4.setEnabled(false);
        if (everyThingIsOkayM()) {
            jLabel2.setEnabled(true);
        }
        else {
            jLabel2.setEnabled(false);
        }
    }//GEN-LAST:event__buttMouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        new FirstPage(theCustomer.getName() + ", " + theCustomer.getAddress(), orderNr, offerNr, operator, evaluator, confirmator, equip1, equip2, project, surfaceCondition, stageOfExamination, reportDate, this).setVisible(true);
    }//GEN-LAST:event_jLabel5MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String ars[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Report().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField _MPCarrier;
    private javax.swing.JTextField _Mbottom;
    private javax.swing.JTextField _MconfirmationLevel;
    private javax.swing.JTextField _MconfirmationName;
    private javax.swing.JTextField _Mcustomer;
    private javax.swing.JTextField _Mdate1;
    private javax.swing.JTextField _Mdate2;
    private javax.swing.JTextField _Mdate3;
    private javax.swing.JTextField _MdefectLocation1;
    private javax.swing.JTextField _MdefectLocation10;
    private javax.swing.JTextField _MdefectLocation2;
    private javax.swing.JTextField _MdefectLocation3;
    private javax.swing.JTextField _MdefectLocation4;
    private javax.swing.JTextField _MdefectLocation5;
    private javax.swing.JTextField _MdefectLocation6;
    private javax.swing.JTextField _MdefectLocation7;
    private javax.swing.JTextField _MdefectLocation8;
    private javax.swing.JTextField _MdefectLocation9;
    private javax.swing.JTextField _MdefectType1;
    private javax.swing.JTextField _MdefectType10;
    private javax.swing.JTextField _MdefectType2;
    private javax.swing.JTextField _MdefectType3;
    private javax.swing.JTextField _MdefectType4;
    private javax.swing.JTextField _MdefectType5;
    private javax.swing.JTextField _MdefectType6;
    private javax.swing.JTextField _MdefectType7;
    private javax.swing.JTextField _MdefectType8;
    private javax.swing.JTextField _MdefectType9;
    private javax.swing.JTextField _Mdescription;
    private javax.swing.JTextField _MdrawingNo;
    private javax.swing.JTextField _Mequipment;
    private javax.swing.JTextField _MevaluationStandard;
    private javax.swing.JTextField _MevaluatorLevel;
    private javax.swing.JTextField _MevaluatorName;
    private javax.swing.JTextField _MheatTreatment;
    private javax.swing.JTextField _MinspectionDates;
    private javax.swing.JTextField _MinspectionPlace;
    private javax.swing.JTextField _MinspectionProcedure;
    private javax.swing.JTextField _MinspectionScope;
    private javax.swing.JTextField _MinspectionStandard;
    private javax.swing.JTextField _MofferNo;
    private javax.swing.JTextField _MoperatorLevel;
    private javax.swing.JTextField _MoperatorName;
    private javax.swing.JTextField _MorderNo;
    private javax.swing.JTextField _Mpage;
    private javax.swing.JTextField _Mproject;
    private javax.swing.JTextField _MreportDate;
    private javax.swing.JTextField _MreportNo;
    private javax.swing.JTextField _MstageOfExamination;
    private javax.swing.JTextField _MsurfaceCondition;
    private javax.swing.JTextField _MsurfaceCondition2;
    private javax.swing.JTextField _Mthickness1;
    private javax.swing.JTextField _Mthickness10;
    private javax.swing.JTextField _Mthickness2;
    private javax.swing.JTextField _Mthickness3;
    private javax.swing.JTextField _Mthickness4;
    private javax.swing.JTextField _Mthickness5;
    private javax.swing.JTextField _Mthickness6;
    private javax.swing.JTextField _Mthickness7;
    private javax.swing.JTextField _Mthickness8;
    private javax.swing.JTextField _Mthickness9;
    private javax.swing.JTextField _UV;
    private javax.swing.JCheckBox _astm;
    private javax.swing.JCheckBox _automatic;
    private javax.swing.JTextField _bottom;
    private javax.swing.JCheckBox _butt;
    private javax.swing.JTextField _confirmationLevel;
    private javax.swing.JTextField _confirmationName;
    private javax.swing.JTextField _currentType;
    private javax.swing.JTextField _customer;
    private javax.swing.JCheckBox _d4;
    private javax.swing.JCheckBox _d5;
    private javax.swing.JCheckBox _d7;
    private javax.swing.JTextField _date1;
    private javax.swing.JTextField _date2;
    private javax.swing.JTextField _date3;
    private javax.swing.JTextField _defectLocation1;
    private javax.swing.JTextField _defectLocation2;
    private javax.swing.JTextField _defectLocation3;
    private javax.swing.JTextField _defectLocation4;
    private javax.swing.JTextField _defectLocation5;
    private javax.swing.JTextField _defectType1;
    private javax.swing.JTextField _defectType2;
    private javax.swing.JTextField _defectType3;
    private javax.swing.JTextField _defectType4;
    private javax.swing.JTextField _defectType5;
    private javax.swing.JTextField _demagnetization;
    private javax.swing.JTextField _density1;
    private javax.swing.JTextField _density2;
    private javax.swing.JTextField _density3;
    private javax.swing.JTextField _density4;
    private javax.swing.JTextField _density5;
    private javax.swing.JTextField _description;
    private javax.swing.JTextField _diameter1;
    private javax.swing.JTextField _diameter10;
    private javax.swing.JTextField _diameter2;
    private javax.swing.JTextField _diameter3;
    private javax.swing.JTextField _diameter4;
    private javax.swing.JTextField _diameter5;
    private javax.swing.JTextField _diameter6;
    private javax.swing.JTextField _diameter7;
    private javax.swing.JTextField _diameter8;
    private javax.swing.JTextField _diameter9;
    private javax.swing.JTextField _distanceOfLight;
    private javax.swing.JTextField _drawingNo;
    private javax.swing.JCheckBox _en;
    private javax.swing.JTextField _equipment;
    private javax.swing.JTextField _evaluationStandard;
    private javax.swing.JTextField _evaluatorLevel;
    private javax.swing.JTextField _evaluatorName;
    private javax.swing.JTextField _examinationArea;
    private javax.swing.JTextField _exposureTime;
    private javax.swing.JTextField _f1012;
    private javax.swing.JTextField _f1012_1;
    private javax.swing.JTextField _f1012_2;
    private javax.swing.JTextField _f1012_3;
    private javax.swing.JTextField _f1012_4;
    private javax.swing.JTextField _f1012_5;
    private javax.swing.JTextField _f1016;
    private javax.swing.JTextField _f1016_1;
    private javax.swing.JTextField _f1016_2;
    private javax.swing.JTextField _f1016_3;
    private javax.swing.JTextField _f1016_4;
    private javax.swing.JTextField _f1016_5;
    private javax.swing.JTextField _f1024;
    private javax.swing.JTextField _f1024_1;
    private javax.swing.JTextField _f1024_2;
    private javax.swing.JTextField _f1024_3;
    private javax.swing.JTextField _f1024_4;
    private javax.swing.JTextField _f1024_5;
    private javax.swing.JTextField _f1036;
    private javax.swing.JTextField _f1036_1;
    private javax.swing.JTextField _f1036_2;
    private javax.swing.JTextField _f1036_3;
    private javax.swing.JTextField _f1036_4;
    private javax.swing.JTextField _f1036_5;
    private javax.swing.JTextField _f1048;
    private javax.swing.JTextField _f1048_1;
    private javax.swing.JTextField _f1048_2;
    private javax.swing.JTextField _f1048_3;
    private javax.swing.JTextField _f1048_4;
    private javax.swing.JTextField _f1048_5;
    private javax.swing.JTextField _f3040;
    private javax.swing.JTextField _f3040_1;
    private javax.swing.JTextField _f3040_2;
    private javax.swing.JTextField _f3040_3;
    private javax.swing.JTextField _f3040_4;
    private javax.swing.JTextField _f3040_5;
    private javax.swing.JCheckBox _fillet;
    private javax.swing.JTextField _filmBrand;
    private javax.swing.JTextField _filmFocusDistance;
    private javax.swing.JTextField _filmNo1;
    private javax.swing.JTextField _filmNo2;
    private javax.swing.JTextField _filmNo3;
    private javax.swing.JTextField _filmNo4;
    private javax.swing.JTextField _filmNo5;
    private javax.swing.JCheckBox _filmSide;
    private javax.swing.JTextField _filters;
    private javax.swing.JTextField _finalEvaluation1;
    private javax.swing.JTextField _finalEvaluation2;
    private javax.swing.JTextField _finalEvaluation3;
    private javax.swing.JTextField _finalEvaluation4;
    private javax.swing.JTextField _finalEvaluation5;
    private javax.swing.JTextField _focalSpotSize;
    private javax.swing.JTextField _gauss;
    private javax.swing.JTextField _heatTreatment;
    private javax.swing.JTextField _identification;
    private javax.swing.JTextField _inspectionClass;
    private javax.swing.JTextField _inspectionDates;
    private javax.swing.JTextField _inspectionPlace;
    private javax.swing.JTextField _inspectionProcedure;
    private javax.swing.JTextField _inspectionScope;
    private javax.swing.JCheckBox _ir192;
    private javax.swing.JTextField _liftingTestDate;
    private javax.swing.JTextField _luxmeter;
    private javax.swing.JTextField _magTech;
    private javax.swing.JCheckBox _manuel;
    private javax.swing.JTextField _materialType1;
    private javax.swing.JTextField _materialType2;
    private javax.swing.JTextField _materialType3;
    private javax.swing.JTextField _materialType4;
    private javax.swing.JTextField _materialType5;
    private javax.swing.JTextField _offerNo;
    private javax.swing.JTextField _operatorLevel;
    private javax.swing.JTextField _operatorName;
    private javax.swing.JTextField _orderNo;
    private javax.swing.JTextField _page;
    private javax.swing.JTextField _pbScreens;
    private javax.swing.JTextField _penetremetreQ1;
    private javax.swing.JTextField _penetremetreQ2;
    private javax.swing.JTextField _penetremetreQ3;
    private javax.swing.JTextField _penetremetreQ4;
    private javax.swing.JTextField _penetremetreQ5;
    private javax.swing.JTextField _pieceNo1;
    private javax.swing.JTextField _pieceNo10;
    private javax.swing.JTextField _pieceNo2;
    private javax.swing.JTextField _pieceNo3;
    private javax.swing.JTextField _pieceNo4;
    private javax.swing.JTextField _pieceNo5;
    private javax.swing.JTextField _pieceNo6;
    private javax.swing.JTextField _pieceNo7;
    private javax.swing.JTextField _pieceNo8;
    private javax.swing.JTextField _pieceNo9;
    private javax.swing.JTextField _poleDistance;
    private javax.swing.JTextField _position1;
    private javax.swing.JTextField _position2;
    private javax.swing.JTextField _position3;
    private javax.swing.JTextField _position4;
    private javax.swing.JTextField _position5;
    private javax.swing.JTextField _preEvaluation1;
    private javax.swing.JTextField _preEvaluation2;
    private javax.swing.JTextField _preEvaluation3;
    private javax.swing.JTextField _preEvaluation4;
    private javax.swing.JTextField _preEvaluation5;
    private javax.swing.JTextField _project;
    private javax.swing.JTextField _repairFilm;
    private javax.swing.JTextField _reportDate;
    private javax.swing.JTextField _reportNo;
    private javax.swing.JTextField _result1;
    private javax.swing.JTextField _result10;
    private javax.swing.JTextField _result2;
    private javax.swing.JTextField _result3;
    private javax.swing.JTextField _result4;
    private javax.swing.JTextField _result5;
    private javax.swing.JTextField _result6;
    private javax.swing.JTextField _result7;
    private javax.swing.JTextField _result8;
    private javax.swing.JTextField _result9;
    private javax.swing.JCheckBox _se75;
    private javax.swing.JTextField _shootingArea1;
    private javax.swing.JTextField _shootingArea2;
    private javax.swing.JTextField _shootingArea3;
    private javax.swing.JTextField _shootingArea4;
    private javax.swing.JTextField _shootingArea5;
    private javax.swing.JCheckBox _sourceSide;
    private javax.swing.JTextField _stageOfExamination;
    private javax.swing.JTextField _standardDeviations;
    private javax.swing.JTextField _suitableFilm;
    private javax.swing.JTextField _surfaceCondition;
    private javax.swing.JTextField _surfaceTemperature;
    private javax.swing.JTextField _temp;
    private javax.swing.JCheckBox _testArr1;
    private javax.swing.JCheckBox _testArr2;
    private javax.swing.JCheckBox _testArr3;
    private javax.swing.JCheckBox _testArr4;
    private javax.swing.JCheckBox _testArr5;
    private javax.swing.JCheckBox _testArr6;
    private javax.swing.JTextField _testLength1;
    private javax.swing.JTextField _testLength10;
    private javax.swing.JTextField _testLength2;
    private javax.swing.JTextField _testLength3;
    private javax.swing.JTextField _testLength4;
    private javax.swing.JTextField _testLength5;
    private javax.swing.JTextField _testLength6;
    private javax.swing.JTextField _testLength7;
    private javax.swing.JTextField _testLength8;
    private javax.swing.JTextField _testLength9;
    private javax.swing.JTextField _testMedium;
    private javax.swing.JTextField _thickness1;
    private javax.swing.JTextField _thickness2;
    private javax.swing.JTextField _thickness3;
    private javax.swing.JTextField _thickness4;
    private javax.swing.JTextField _thickness5;
    private javax.swing.JTextField _usedDevice;
    private javax.swing.JTextField _visibleQ1;
    private javax.swing.JTextField _visibleQ2;
    private javax.swing.JTextField _visibleQ3;
    private javax.swing.JTextField _visibleQ4;
    private javax.swing.JTextField _visibleQ5;
    private javax.swing.JTextField _welderNr1;
    private javax.swing.JTextField _welderNr2;
    private javax.swing.JTextField _welderNr3;
    private javax.swing.JTextField _welderNr4;
    private javax.swing.JTextField _welderNr5;
    private javax.swing.JTextField _weldingProcess1;
    private javax.swing.JTextField _weldingProcess10;
    private javax.swing.JTextField _weldingProcess2;
    private javax.swing.JTextField _weldingProcess3;
    private javax.swing.JTextField _weldingProcess4;
    private javax.swing.JTextField _weldingProcess5;
    private javax.swing.JTextField _weldingProcess6;
    private javax.swing.JTextField _weldingProcess7;
    private javax.swing.JTextField _weldingProcess8;
    private javax.swing.JTextField _weldingProcess9;
    private javax.swing.JTextField _weldingType1;
    private javax.swing.JTextField _weldingType2;
    private javax.swing.JTextField _weldingType3;
    private javax.swing.JTextField _weldingType4;
    private javax.swing.JTextField _weldingType5;
    private javax.swing.JCheckBox _xRay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

    private void radiographicResultsCapsul(){
        radiographicResult1[0] = _shootingArea1;
        radiographicResult1[1] = _filmNo1;
        radiographicResult1[2] = _materialType1;
        radiographicResult1[3] = _weldingType1;
        radiographicResult1[4] = _welderNr1;
        radiographicResult1[5] = _position1;
        radiographicResult1[6] = _thickness1;
        radiographicResult1[7] = _penetremetreQ1;
        radiographicResult1[8] = _visibleQ1;
        radiographicResult1[9] = _density1;
        radiographicResult1[10] = _f1012_1;
        radiographicResult1[11] = _f1016_1;
        radiographicResult1[12] = _f1024_1;
        radiographicResult1[13] = _f1036_1;
        radiographicResult1[14] = _f1048_1;
        radiographicResult1[15] = _f3040_1;
        radiographicResult1[16] = _defectLocation1;
        radiographicResult1[17] = _defectType1;
        radiographicResult1[18] = _preEvaluation1;
        radiographicResult1[19] = _finalEvaluation1;
        
        radiographicResult2[0] = _shootingArea2;
        radiographicResult2[1] = _filmNo2;
        radiographicResult2[2] = _materialType2;
        radiographicResult2[3] = _weldingType2;
        radiographicResult2[4] = _welderNr2;
        radiographicResult2[5] = _position2;
        radiographicResult2[6] = _thickness2;
        radiographicResult2[7] = _penetremetreQ2;
        radiographicResult2[8] = _visibleQ2;
        radiographicResult2[9] = _density2;
        radiographicResult2[10] = _f1012_2;
        radiographicResult2[11] = _f1016_2;
        radiographicResult2[12] = _f1024_2;
        radiographicResult2[13] = _f1036_2;
        radiographicResult2[14] = _f1048_2;
        radiographicResult2[15] = _f3040_2;
        radiographicResult2[16] = _defectLocation2;
        radiographicResult2[17] = _defectType2;
        radiographicResult2[18] = _preEvaluation2;
        radiographicResult2[19] = _finalEvaluation2;
        
        radiographicResult3[0] = _shootingArea3;
        radiographicResult3[1] = _filmNo3;
        radiographicResult3[2] = _materialType3;
        radiographicResult3[3] = _weldingType3;
        radiographicResult3[4] = _welderNr3;
        radiographicResult3[5] = _position3;
        radiographicResult3[6] = _thickness3;
        radiographicResult3[7] = _penetremetreQ3;
        radiographicResult3[8] = _visibleQ3;
        radiographicResult3[9] = _density3;
        radiographicResult3[10] = _f1012_3;
        radiographicResult3[11] = _f1016_3;
        radiographicResult3[12] = _f1024_3;
        radiographicResult3[13] = _f1036_3;
        radiographicResult3[14] = _f1048_3;
        radiographicResult3[15] = _f3040_3;
        radiographicResult3[16] = _defectLocation3;
        radiographicResult3[17] = _defectType3;
        radiographicResult3[18] = _preEvaluation3;
        radiographicResult3[19] = _finalEvaluation3;
        
        radiographicResult4[0] = _shootingArea4;
        radiographicResult4[1] = _filmNo4;
        radiographicResult4[2] = _materialType4;
        radiographicResult4[3] = _weldingType4;
        radiographicResult4[4] = _welderNr4;
        radiographicResult4[5] = _position4;
        radiographicResult4[6] = _thickness4;
        radiographicResult4[7] = _penetremetreQ4;
        radiographicResult4[8] = _visibleQ4;
        radiographicResult4[9] = _density4;
        radiographicResult4[10] = _f1012_4;
        radiographicResult4[11] = _f1016_4;
        radiographicResult4[12] = _f1024_4;
        radiographicResult4[13] = _f1036_4;
        radiographicResult4[14] = _f1048_4;
        radiographicResult4[15] = _f3040_4;
        radiographicResult4[16] = _defectLocation4;
        radiographicResult4[17] = _defectType4;
        radiographicResult4[18] = _preEvaluation4;
        radiographicResult4[19] = _finalEvaluation4;
        
        radiographicResult5[0] = _shootingArea5;
        radiographicResult5[1] = _filmNo5;
        radiographicResult5[2] = _materialType5;
        radiographicResult5[3] = _weldingType5;
        radiographicResult5[4] = _welderNr5;
        radiographicResult5[5] = _position5;
        radiographicResult5[6] = _thickness5;
        radiographicResult5[7] = _penetremetreQ5;
        radiographicResult5[8] = _visibleQ5;
        radiographicResult5[9] = _density5;
        radiographicResult5[10] = _f1012_5;
        radiographicResult5[11] = _f1016_5;
        radiographicResult5[12] = _f1024_5;
        radiographicResult5[13] = _f1036_5;
        radiographicResult5[14] = _f1048_5;
        radiographicResult5[15] = _f3040_5;
        radiographicResult5[16] = _defectLocation5;
        radiographicResult5[17] = _defectType5;
        radiographicResult5[18] = _preEvaluation5;
        radiographicResult5[19] = _finalEvaluation5;
        
        if (!radiographicResult1[0].getText().trim().equals("")) {
            radiographicResults.add(radiographicResult1);
        }
        if (!radiographicResult2[0].getText().trim().equals("")) {
            radiographicResults.add(radiographicResult2);
        }
        if (!radiographicResult3[0].getText().trim().equals("")) {
            radiographicResults.add(radiographicResult3);
        }
        if (!radiographicResult4[0].getText().trim().equals("")) {
            radiographicResults.add(radiographicResult4);
        }
        if (!radiographicResult5[0].getText().trim().equals("")) {
            radiographicResults.add(radiographicResult5);
        }
    }
    
    private int[] getFilmQuantity (){
        int[] filmQuantity = new int[8];
        try {
            filmQuantity[0] = Integer.parseInt(_f1012.getText());
            filmQuantity[1] = Integer.parseInt(_f1016.getText());
            filmQuantity[2] = Integer.parseInt(_f1024.getText());
            filmQuantity[3] = Integer.parseInt(_f1036.getText());
            filmQuantity[4] = Integer.parseInt(_f1048.getText());
            filmQuantity[5] = Integer.parseInt(_f3040.getText());
            filmQuantity[6] = Integer.parseInt(_suitableFilm.getText());
            filmQuantity[7] = Integer.parseInt(_repairFilm.getText());
        }
        catch (NumberFormatException e){
            filmQuantity = null;
        }
        return filmQuantity;
    }
    
    private boolean[] getTestArrangements () {
        boolean[] testArrangements = new boolean[6];
        testArrangements[0] = _testArr1.isSelected();
        testArrangements[1] = _testArr2.isSelected();
        testArrangements[2] = _testArr3.isSelected();
        testArrangements[3] = _testArr4.isSelected();
        testArrangements[4] = _testArr5.isSelected();
        testArrangements[5] = _testArr6.isSelected();
        return testArrangements;
    }

    
    private ArrayList<RadiographicInspectionResult> getRadiographicResults () {
        ArrayList<RadiographicInspectionResult> results = new ArrayList(); 
        radiographicResultsCapsul();
        for (JTextField[] tf : radiographicResults) {
            RadiographicInspectionResult temp = new RadiographicInspectionResult();
            temp.setShootingArea(tf[0].getText());
            temp.setFilmNo(tf[1].getText());
            temp.setMaterialType(tf[2].getText());
            temp.setWeldingType(tf[3].getText());
            temp.setWelderNr(tf[4].getText());
            temp.setPosition(tf[5].getText());
            temp.setThickness(tf[6].getText());
            temp.setPenetremeter(tf[7].getText());
            temp.setVisibleQ(tf[8].getText());
            temp.setDensity(tf[9].getText());
            temp.setF1012(tf[10].getText());
            temp.setF1016(tf[11].getText());
            temp.setF1024(tf[12].getText());
            temp.setF1036(tf[13].getText());
            temp.setF1048(tf[14].getText());
            temp.setF3040(tf[15].getText());
            temp.setDefectLocation(tf[16].getText());
            temp.setDefectType(tf[17].getText());          
            temp.setPreEvaluation(tf[18].getText());
            temp.setFinalEvaluation(tf[19].getText());
            results.add(temp);
        }
        return results;
    }
    
    
    
    private RadiographicReport collectDataRadiographic (){
            RadiographicReport report = new RadiographicReport();
            report.setCustomer(_customer.getText());
            report.setProjectName(_project.getText());
            report.setInspectionPlace(_inspectionPlace.getText());
            report.setInspectionClass(_inspectionClass.getText());
            report.setEvaluationStandard(_evaluationStandard.getText());
            report.setInspectionProcedure(_inspectionProcedure.getText());
            report.setInspectionScope(_inspectionScope.getText());
            report.setDrawingNo(_drawingNo.getText());
            report.setSurfaceCondition(_surfaceCondition.getText());
            report.setStageOfExamination(_stageOfExamination.getText());
            report.setPage(_page.getText());
            report.setReportNumber(_reportNo.getText());
            Date date = Common.string_toDate(_reportDate.getText());
            if (date != null) {
                report.setReportDate(date);
            }
            report.setOrderNumber(_orderNo.getText());
            report.setOfferNumber(_offerNo.getText());
            report.setEquipment(_equipment.getText());
            report.setUsedDevice(_usedDevice.getText());
            report.setIr192(_ir192.isSelected());
            report.setSe75(_se75.isSelected());
            report.setxRay(_xRay.isSelected());
            report.setFocalSpotSize(_focalSpotSize.getText());
            report.setExposureTime(_exposureTime.getText());
            report.setFilmFocusDistance(_filmFocusDistance.getText());
            report.setPbScreens(_pbScreens.getText());
            report.setFilters(_filters.getText());
            report.setHeatTreatment(_heatTreatment.getText());
            report.setFilmBrand(_filmBrand.getText());
            report.setD4MX125(_d4.isSelected());
            report.setD5T200(_d5.isSelected());
            report.setD7AA400(_d7.isSelected());
            report.setEn(_en.isSelected());
            report.setAstm(_astm.isSelected());
            report.setSourceSide(_sourceSide.isSelected());
            report.setFilmSide(_filmSide.isSelected());
            report.setAutomatic(_automatic.isSelected());
            report.setManuel(_manuel.isSelected());
            report.setTemp(_temp.getText());
            int[] filmQuantity = getFilmQuantity();
            if (filmQuantity != null) {
                report.setFilmQuantity(getFilmQuantity());
            }
            report.setTestArrangements(getTestArrangements());
            report.setInspectionDates(_inspectionDates.getText());
            report.setDescriptionOfAttachments(_description.getText());
            report.setInspectionResults(getRadiographicResults());
            report.setOperator_id(this.operator_id);
            report.setEvaluator_id(this.evaluator_id);
            report.setConfirmation_id(this.confirmation_id);
            System.out.println(report.toString());
            return report;
    }
    
    private void magneticResultsCapsul(){
        magneticResult1[0] = _pieceNo1;
        magneticResult1[1] = _testLength1;
        magneticResult1[2] = _weldingProcess1;
        magneticResult1[3] = _Mthickness1;
        magneticResult1[4] = _diameter1;
        magneticResult1[5] = _MdefectType1;
        magneticResult1[6] = _MdefectLocation1;
        magneticResult1[7] = _result1;

        magneticResult2[0] = _pieceNo2;
        magneticResult2[1] = _testLength2;
        magneticResult2[2] = _weldingProcess2;
        magneticResult2[3] = _Mthickness2;
        magneticResult2[4] = _diameter2;
        magneticResult2[5] = _MdefectType2;
        magneticResult2[6] = _MdefectLocation2;
        magneticResult2[7] = _result2;

        magneticResult3[0] = _pieceNo3;
        magneticResult3[1] = _testLength3;
        magneticResult3[2] = _weldingProcess3;
        magneticResult3[3] = _Mthickness3;
        magneticResult3[4] = _diameter3;
        magneticResult3[5] = _MdefectType3;
        magneticResult3[6] = _MdefectLocation3;
        magneticResult3[7] = _result3;

        magneticResult4[0] = _pieceNo4;
        magneticResult4[1] = _testLength4;
        magneticResult4[2] = _weldingProcess4;
        magneticResult4[3] = _Mthickness4;
        magneticResult4[4] = _diameter4;
        magneticResult4[5] = _MdefectType4;
        magneticResult4[6] = _MdefectLocation4;
        magneticResult4[7] = _result4;

        magneticResult5[0] = _pieceNo5;
        magneticResult5[1] = _testLength5;
        magneticResult5[2] = _weldingProcess5;
        magneticResult5[3] = _Mthickness5;
        magneticResult5[4] = _diameter5;
        magneticResult5[5] = _MdefectType5;
        magneticResult5[6] = _MdefectLocation5;
        magneticResult5[7] = _result5;
        
        magneticResult6[0] = _pieceNo6;
        magneticResult6[1] = _testLength6;
        magneticResult6[2] = _weldingProcess6;
        magneticResult6[3] = _Mthickness6;
        magneticResult6[4] = _diameter6;
        magneticResult6[5] = _MdefectType6;
        magneticResult6[6] = _MdefectLocation6;
        magneticResult6[7] = _result6;        
        
        magneticResult7[0] = _pieceNo7;
        magneticResult7[1] = _testLength7;
        magneticResult7[2] = _weldingProcess7;
        magneticResult7[3] = _Mthickness7;
        magneticResult7[4] = _diameter7;
        magneticResult7[5] = _MdefectType7;
        magneticResult7[6] = _MdefectLocation7;
        magneticResult7[7] = _result7;       
        
        magneticResult8[0] = _pieceNo8;
        magneticResult8[1] = _testLength8;
        magneticResult8[2] = _weldingProcess8;
        magneticResult8[3] = _Mthickness8;
        magneticResult8[4] = _diameter8;
        magneticResult8[5] = _MdefectType8;
        magneticResult8[6] = _MdefectLocation8;
        magneticResult8[7] = _result8;        
        
        magneticResult9[0] = _pieceNo9;
        magneticResult9[1] = _testLength9;
        magneticResult9[2] = _weldingProcess9;
        magneticResult9[3] = _Mthickness9;
        magneticResult9[4] = _diameter9;
        magneticResult9[5] = _MdefectType9;
        magneticResult9[6] = _MdefectLocation9;
        magneticResult9[7] = _result9;
        
        magneticResult10[0] = _pieceNo10;
        magneticResult10[1] = _testLength10;
        magneticResult10[2] = _weldingProcess10;
        magneticResult10[3] = _Mthickness10;
        magneticResult10[4] = _diameter10;
        magneticResult10[5] = _MdefectType10;
        magneticResult10[6] = _MdefectLocation10;
        magneticResult10[7] = _result10;
        
        if (!magneticResult1[0].getText().trim().equals("")) {
            magneticResults.add(magneticResult1);
        }
        if (!magneticResult2[0].getText().trim().equals("")) {
            magneticResults.add(magneticResult2);
        }
        if (!magneticResult3[0].getText().trim().equals("")) {
            magneticResults.add(magneticResult3);
        }
        if (!magneticResult4[0].getText().trim().equals("")) {
            magneticResults.add(magneticResult4);
        }
        if (!magneticResult5[0].getText().trim().equals("")) {
            magneticResults.add(magneticResult5);
        }
        if (!magneticResult6[0].getText().trim().equals("")) {
            magneticResults.add(magneticResult6);
        }
        if (!magneticResult7[0].getText().trim().equals("")) {
            magneticResults.add(magneticResult7);
        }
        if (!magneticResult8[0].getText().trim().equals("")) {
            magneticResults.add(magneticResult8);
        }
        if (!magneticResult9[0].getText().trim().equals("")) {
            magneticResults.add(magneticResult9);
        }
        if (!magneticResult10[0].getText().trim().equals("")) {
            magneticResults.add(magneticResult10);
        }
    }
    
    
    private ArrayList<MagneticInspectionResult> getMagneticResults () {
        magneticResultsCapsul();
        ArrayList<MagneticInspectionResult> results = new ArrayList();
        for (JTextField[] tf : magneticResults) {
            MagneticInspectionResult temp = new MagneticInspectionResult();
            temp.setWeldPieceNo(tf[0].getText());
            temp.setTestLength(tf[1].getText());
            temp.setWeldingProcess(tf[2].getText());
            temp.setThickness(tf[3].getText());
            temp.setDiameter(tf[4].getText());
            temp.setDefectType(tf[5].getText());
            temp.setDefectLocation(tf[6].getText());
            temp.setResult(tf[7].getText());
            results.add(temp);
        }
        return results;
    }
 
    
    
    
    
    
    
    
    private MagneticReport collectDataMagnetic () {
            MagneticReport report = new MagneticReport();
            report.setCustomer(_Mcustomer.getText());
            report.setProjectName(_Mproject.getText());
            report.setInspectionPlace(_MinspectionPlace.getText());
            report.setInspectionClass(_MinspectionStandard.getText());
            report.setEvaluationStandard(_MevaluationStandard.getText());
            report.setInspectionProcedure(_MinspectionProcedure.getText());
            report.setInspectionScope(_MinspectionScope.getText());
            report.setDrawingNo(_MdrawingNo.getText());
            report.setSurfaceCondition(_MsurfaceCondition.getText());
            report.setStageOfExamination(_MstageOfExamination.getText());
            report.setPage(_Mpage.getText());
            report.setReportNumber(_MreportNo.getText());
            Date date = Common.string_toDate(_MreportDate.getText());
            if (date != null) {
                report.setReportDate(date);
            }
            report.setOrderNumber(_MorderNo.getText());
            report.setOfferNumber(_MofferNo.getText());
            report.setPoleDistance(_poleDistance.getText());
            report.setEquipment(_Mequipment.getText());
            report.setMpCarrier(_MPCarrier.getText());
            report.setMagTech(_MPCarrier.getText());
            report.setUvIntensity(_UV.getText());
            report.setDistanceOfLight(_distanceOfLight.getText());
            report.setExaminationArea(_examinationArea.getText());
            report.setCurrentType(_currentType.getText());
            report.setLuxmeter(_luxmeter.getText());
            report.setTestMedium(_testMedium.getText());
            report.setDemagnetization(_demagnetization.getText());
            report.setHeatTreatment(_MheatTreatment.getText());
            report.setSurfaceTemperature(_surfaceTemperature.getText());
            report.setGaussFieldStrength(_gauss.getText());
            report.setSurfaceCondition2(_MsurfaceCondition2.getText());
            report.setIdentificationOfLightEquip(_identification.getText());
            report.setLiftingTest(_liftingTestDate.getText());
            report.setButtWeld(_butt.isSelected());
            report.setFilletWeld(_fillet.isSelected());
            report.setStandardDeviations(_standardDeviations.getText());
            report.setInspectionDates(_MinspectionDates.getText());
            report.setDescriptionOfAttachments(_Mdescription.getText());
            report.setInspectionResults(getMagneticResults());
            report.setOperator_id(this.operator_id);
            report.setEvaluator_id(this.evaluator_id);
            report.setConfirmation_id(this.confirmation_id);
            report.setBottom(_Mbottom.getText());
            System.out.println(report.toString());
            return report;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}