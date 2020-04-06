/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.Resource;

import berichtserstellungssystem.Resource.Equipment;
import java.util.Objects;

/**
 *
 * @author Baraa
 */
public class MagneticEquipment extends Equipment{
    private int polesDistance;
    private String MPCarrier;
    private String MagTechnic;
    private double UVIntensity;
    private int lightDistance;

    public MagneticEquipment(int polesDistance, String MPCarrier, String MagTechnic, double UVIntensity, int lightDistance, String name) {
        super(name);
        this.polesDistance = polesDistance;
        this.MPCarrier = MPCarrier;
        this.MagTechnic = MagTechnic;
        this.UVIntensity = UVIntensity;
        this.lightDistance = lightDistance;
    }  
    
    public int getPolesDistance() {
        return polesDistance;
    }

    public String getMPCarrier() {
        return MPCarrier;
    }

    public String getMagTechnic() {
        return MagTechnic;
    }

    public double getUVIntensity() {
        return UVIntensity;
    }

    public int getLightDistance() {
        return lightDistance;
    }

    public void setPolesDistance(int polesDistance) {
        this.polesDistance = polesDistance;
    }

    public void setMPCarrier(String MPCarrier) {
        this.MPCarrier = MPCarrier;
    }

    public void setMagTechnic(String MagTechnic) {
        this.MagTechnic = MagTechnic;
    }

    public void setUVIntensity(double UVIntensity) {
        this.UVIntensity = UVIntensity;
    }

    public void setLightDistance(int lightDistance) {
        this.lightDistance = lightDistance;
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
        final MagneticEquipment other = (MagneticEquipment) obj;
        if (this.polesDistance != other.polesDistance) {
            return false;
        }
        if (Double.doubleToLongBits(this.UVIntensity) != Double.doubleToLongBits(other.UVIntensity)) {
            return false;
        }
        if (this.lightDistance != other.lightDistance) {
            return false;
        }
        if (!Objects.equals(this.getName(), other.getName())) {
            return false;
        }
        if (!Objects.equals(this.MPCarrier, other.MPCarrier)) {
            return false;
        }
        if (!Objects.equals(this.MagTechnic, other.MagTechnic)) {
            return false;
        }
        return true;
    }
    
    
}
