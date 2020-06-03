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
public class RadiographicInspectionResult extends Result{
    private String shootingArea = "";
    private String filmNo = "";
    private String materialType = "";
    private String weldingType = "";
    private String welderNr = "";
    private String position = "";
    private String thickness = "";
    private String penetremeter = "";
    private String visibleQ = "";
    private String density = "";
    private String f1012 = "";
    private String f1016 = "";
    private String f1024 = "";
    private String f1036 = "";
    private String f1048 = "";
    private String f3040 = "";
    private String defectLocation = "";
    private String defectType = "";
    private String preEvaluation = "";
    private String finalEvaluation = "";

    public RadiographicInspectionResult(String shootingArea, String filmNo, String materialType, String weldingType, String welderNr, String position, String thickness, String penetremeter, String visibleQ, String density, String f1012, String f1016, String f1024, String f1036, String f1048, String f3040, String defectLocation, String defectType, String preEvaluation, String finalEvaluation, int num) {
        super(num);
        this.shootingArea = shootingArea;
        this.filmNo = filmNo;
        this.materialType = materialType;
        this.weldingType = weldingType;
        this.welderNr = welderNr;
        this.position = position;
        this.thickness = thickness;
        this.penetremeter = penetremeter;
        this.visibleQ = visibleQ;
        this.density = density;
        this.f1012 = f1012;
        this.f1016 = f1016;
        this.f1024 = f1024;
        this.f1036 = f1036;
        this.f1048 = f1048;
        this.f3040 = f3040;
        this.defectLocation = defectLocation;
        this.defectType = defectType;
        this.preEvaluation = preEvaluation;
        this.finalEvaluation = finalEvaluation;
    }
    
    public RadiographicInspectionResult(ResultSet rs, int num) {
        super(num);
        try {
            this.shootingArea = rs.getString("shootingArea");
            this.filmNo = rs.getString("filmNo");
            this.materialType = rs.getString("materialType");
            this.weldingType = rs.getString("weldingType");
            this.welderNr = rs.getString("welderNr");
            this.position = rs.getString("position");
            this.thickness = rs.getString("thickness");
            this.penetremeter = rs.getString("penetremeter");
            this.visibleQ = rs.getString("visibleQ");
            this.density = rs.getString("density");
            this.f1012 = rs.getString("f1012");
            this.f1016 = rs.getString("f1016");
            this.f1024 = rs.getString("f1024");
            this.f1036 = rs.getString("f1036");
            this.f1048 = rs.getString("f1048");
            this.f3040 = rs.getString("f3040");
            this.defectLocation = rs.getString("defectLocation");
            this.defectType = rs.getString("defectType");
            this.preEvaluation = rs.getString("preEvaluation");
            this.finalEvaluation = rs.getString("finalEvaluation");
        } catch(SQLException e) {
            System.out.println("RadiographicInspectionResult " + e);
        }
        
    }

    public RadiographicInspectionResult () {
        
    }
    
    public void setShootingArea(String shootingArea) {
        this.shootingArea = shootingArea;
    }

    public void setFilmNo(String filmNo) {
        this.filmNo = filmNo;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public void setWeldingType(String weldingType) {
        this.weldingType = weldingType;
    }

    public void setWelderNr(String welderNr) {
        this.welderNr = welderNr;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public void setPenetremeter(String penetremeter) {
        this.penetremeter = penetremeter;
    }

    public void setVisibleQ(String visibleQ) {
        this.visibleQ = visibleQ;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public void setF1012(String f1012) {
        this.f1012 = f1012;
    }

    public void setF1016(String f1016) {
        this.f1016 = f1016;
    }

    public void setF1024(String f1024) {
        this.f1024 = f1024;
    }

    public void setF1036(String f1036) {
        this.f1036 = f1036;
    }

    public void setF1048(String f1048) {
        this.f1048 = f1048;
    }

    public void setF3040(String f3040) {
        this.f3040 = f3040;
    }

    public void setDefectLocation(String defectLocation) {
        this.defectLocation = defectLocation;
    }

    
    public void setDefectType(String defectType) {
        this.defectType = defectType;
    }

    public void setPreEvaluation(String preEvaluation) {
        this.preEvaluation = preEvaluation;
    }

    public void setFinalEvaluation(String finalEvaluation) {
        this.finalEvaluation = finalEvaluation;
    }

    public String getShootingArea() {
        return shootingArea;
    }

    public String getFilmNo() {
        return filmNo;
    }

    public String getMaterialType() {
        return materialType;
    }

    public String getWeldingType() {
        return weldingType;
    }

    public String getWelderNr() {
        return welderNr;
    }

    public String getPosition() {
        return position;
    }

    public String getThickness() {
        return thickness;
    }

    public String getPenetremeter() {
        return penetremeter;
    }

    public String getVisibleQ() {
        return visibleQ;
    }

    public String getDensity() {
        return density;
    }

    public String getF1012() {
        return f1012;
    }

    public String getF1016() {
        return f1016;
    }

    public String getF1024() {
        return f1024;
    }

    public String getF1036() {
        return f1036;
    }

    public String getF1048() {
        return f1048;
    }

    public String getF3040() {
        return f3040;
    }

    public String getDefectLocation() {
        return defectLocation;
    }

    public String getDefectType() {
        return defectType;
    }

    public String getPreEvaluation() {
        return preEvaluation;
    }

    public String getFinalEvaluation() {
        return finalEvaluation;
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
        final RadiographicInspectionResult other = (RadiographicInspectionResult) obj;
        if (!Objects.equals(this.shootingArea, other.shootingArea)) {
            return false;
        }
        if (!Objects.equals(this.filmNo, other.filmNo)) {
            return false;
        }
        if (!Objects.equals(this.materialType, other.materialType)) {
            return false;
        }
        if (!Objects.equals(this.weldingType, other.weldingType)) {
            return false;
        }
        if (!Objects.equals(this.welderNr, other.welderNr)) {
            return false;
        }
        if (!Objects.equals(this.position, other.position)) {
            return false;
        }
        if (!Objects.equals(this.thickness, other.thickness)) {
            return false;
        }
        if (!Objects.equals(this.penetremeter, other.penetremeter)) {
            return false;
        }
        if (!Objects.equals(this.visibleQ, other.visibleQ)) {
            return false;
        }
        if (!Objects.equals(this.density, other.density)) {
            return false;
        }
        if (!Objects.equals(this.f1012, other.f1012)) {
            return false;
        }
        if (!Objects.equals(this.f1016, other.f1016)) {
            return false;
        }
        if (!Objects.equals(this.f1024, other.f1024)) {
            return false;
        }
        if (!Objects.equals(this.f1036, other.f1036)) {
            return false;
        }
        if (!Objects.equals(this.f1048, other.f1048)) {
            return false;
        }
        if (!Objects.equals(this.f3040, other.f3040)) {
            return false;
        }
        if (!Objects.equals(this.defectType, other.defectType)) {
            return false;
        }
        if (!Objects.equals(this.preEvaluation, other.preEvaluation)) {
            return false;
        }
        if (!Objects.equals(this.finalEvaluation, other.finalEvaluation)) {
            return false;
        }
        return true;
    }
    
    
}
