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
    private String polesDistance;
    private String MPCarrier;
    private String MagTechnic;
    private String UVIntensity;
    private String lightDistance;

    public MagneticEquipment(String polesDistance, String MPCarrier, String MagTechnic, String UVIntensity, String lightDistance, String name, Date calibrationEndDate) {
        super(name, calibrationEndDate);
        this.polesDistance = polesDistance;
        this.MPCarrier = MPCarrier;
        this.MagTechnic = MagTechnic;
        this.UVIntensity = UVIntensity;
        this.lightDistance = lightDistance;
    }


    
    public MagneticEquipment (ResultSet rs){
        try {
            this.setCalibrationEndDate(rs.getDate("calibrationEndDate"));
            this.setName(rs.getString("name"));
            this.polesDistance = rs.getString("poles_Distance");
            this.MPCarrier = rs.getString("MPCarrier");
            this.MagTechnic = rs.getString("MagTechnic");
            this.UVIntensity = rs.getString("UVIntensity");
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

    public String getMPCarrier() {
        return MPCarrier;
    }

    public String getMagTechnic() {
        return MagTechnic;
    }

    public String getUVIntensity() {
        return UVIntensity;
    }

    public String getLightDistance() {
        return lightDistance;
    }

    public void setPolesDistance(String polesDistance) {
        this.polesDistance = polesDistance;
    }

    public void setMPCarrier(String MPCarrier) {
        this.MPCarrier = MPCarrier;
    }

    public void setMagTechnic(String MagTechnic) {
        this.MagTechnic = MagTechnic;
    }

    public void setUVIntensity(String UVIntensity) {
        this.UVIntensity = UVIntensity;
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
        if (!Objects.equals(this.MPCarrier, other.MPCarrier)) {
            return false;
        }
        if (!Objects.equals(this.MagTechnic, other.MagTechnic)) {
            return false;
        }
        if (!Objects.equals(this.UVIntensity, other.UVIntensity)) {
            return false;
        }
        if (!Objects.equals(this.lightDistance, other.lightDistance)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
