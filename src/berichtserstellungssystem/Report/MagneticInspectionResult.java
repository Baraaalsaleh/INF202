/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.Report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author Baraa
 */
public class MagneticInspectionResult extends Result{
    private String weldPieceNo = "";
    private String testLength = "";
    private String weldingProcess = "";
    private String thickness = "";
    private String diameter = "";
    private String defectType = "";
    private String defectLocation = "";
    private String result = "";

    public MagneticInspectionResult(String weldPieceNo, String testLength, String weldingProcess, String thickness, String diameter, String defectType, String defectLocation, String result, int serialNo) {
        super(serialNo);
        this.weldPieceNo = weldPieceNo;
        this.testLength = testLength;
        this.weldingProcess = weldingProcess;
        this.thickness = thickness;
        this.diameter = diameter;
        this.defectType = defectType;
        this.defectLocation = defectLocation;
        this.result = result;
    }
    
    public MagneticInspectionResult(ResultSet rs, int serialNo) {
        super(serialNo);
        try {
            this.weldPieceNo = rs.getString("weldPieceNo");
            this.testLength = rs.getString("testLength");
            this.weldingProcess = rs.getString("weldingProcess");
            this.thickness = rs.getString("thickness");
            this.diameter = rs.getString("diameter");
            this.defectType = rs.getString("defectType");
            this.defectLocation = rs.getString("defectLocation");
            this.result = rs.getString("result");
        } catch(SQLException e) {
            System.out.println("MagneticInspectionResult " + e);
        }
        
    }

    public MagneticInspectionResult () {
        
    }
   
    
    public void setWeldPieceNo(String weldPieceNo) {
        this.weldPieceNo = weldPieceNo;
    }

    public void setTestLength(String testLength) {
        this.testLength = testLength;
    }

    public void setWeldingProcess(String weldingProcess) {
        this.weldingProcess = weldingProcess;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public void setDefectType(String defectType) {
        this.defectType = defectType;
    }

    public void setDefectLocation(String defectLocation) {
        this.defectLocation = defectLocation;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getWeldPieceNo() {
        return weldPieceNo;
    }

    public String getTestLength() {
        return testLength;
    }

    public String getWeldingProcess() {
        return weldingProcess;
    }

    public String getThickness() {
        return thickness;
    }

    public String getDiameter() {
        return diameter;
    }

    public String getDefectType() {
        return defectType;
    }

    public String getDefectLocation() {
        return defectLocation;
    }

    public String getResult() {
        return result;
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
        final MagneticInspectionResult other = (MagneticInspectionResult) obj;
        if (!Objects.equals(this.weldPieceNo, other.weldPieceNo)) {
            return false;
        }
        if (!Objects.equals(this.testLength, other.testLength)) {
            return false;
        }
        if (!Objects.equals(this.weldingProcess, other.weldingProcess)) {
            return false;
        }
        if (!Objects.equals(this.thickness, other.thickness)) {
            return false;
        }
        if (!Objects.equals(this.diameter, other.diameter)) {
            return false;
        }
        if (!Objects.equals(this.defectType, other.defectType)) {
            return false;
        }
        if (!Objects.equals(this.defectLocation, other.defectLocation)) {
            return false;
        }
        if (!Objects.equals(this.result, other.result)) {
            return false;
        }
        return true;
    }
    
    
}
