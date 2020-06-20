//Baraa Alsaleh, 19050800
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.Resource;

import berichtserstellungssystem.Resource.Equipment;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Baraa
 */
public class MagneticEquipment extends Equipment{
    private String polesDistance = "";
    private String mpCarrier = "";
    private String magTechnic = "";
    private String uvIntensity = "";
    private String lightDistance = "";

    public MagneticEquipment(String polesDistance, String MPCarrier, String MagTechnic, String UVIntensity, String lightDistance, String name, Date calibrationEndDate) {
        super(name, calibrationEndDate);
        this.polesDistance = polesDistance;
        this.mpCarrier = MPCarrier;
        this.magTechnic = MagTechnic;
        this.uvIntensity = UVIntensity;
        this.lightDistance = lightDistance;
    }


    
    public MagneticEquipment (ResultSet rs){
        try {
            this.setCalibrationEndDate(rs.getDate("calibrationEndDate"));
            this.setName(rs.getString("name"));
            this.polesDistance = rs.getString("poles_Distance");
            this.mpCarrier = rs.getString("MPCarrier");
            this.magTechnic = rs.getString("MagTechnic");
            this.uvIntensity = rs.getString("UVIntensity");
            this.lightDistance = rs.getString("lightDistance");
        } catch (SQLException ex) {
            System.out.println("MagneticEquipment " + ex);
        }
    }
    
    public MagneticEquipment () {
        
    }
    
    public String getPolesDistance() {
        return polesDistance;
    }

    public String getMpCarrier() {
        return mpCarrier;
    }

    public String getMagTechnic() {
        return magTechnic;
    }

    public String getUvIntensity() {
        return uvIntensity;
    }

    public String getLightDistance() {
        return lightDistance;
    }

    public void setPolesDistance(String polesDistance) {
        this.polesDistance = polesDistance;
    }

    public void setMpCarrier(String mpCarrier) {
        this.mpCarrier = mpCarrier;
    }

    public void setMagTechnic(String magTechnic) {
        this.magTechnic = magTechnic;
    }

    public void setUvIntensity(String uvIntensity) {
        this.uvIntensity = uvIntensity;
    }

    public void setLightDistance(String lightDistance) {
        this.lightDistance = lightDistance;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final MagneticEquipment other = (MagneticEquipment) obj;
        if (!Objects.equals(this.polesDistance, other.polesDistance)) {
            return false;
        }
        if (!Objects.equals(this.mpCarrier, other.mpCarrier)) {
            return false;
        }
        if (!Objects.equals(this.magTechnic, other.magTechnic)) {
            return false;
        }
        if (!Objects.equals(this.uvIntensity, other.uvIntensity)) {
            return false;
        }
        if (!Objects.equals(this.lightDistance, other.lightDistance)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
