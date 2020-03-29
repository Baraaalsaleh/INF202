/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem;

import java.util.Objects;

/**
 *
 * @author Baraa
 */
public class TestUnit {
    private String name;
    private int polesDistance;
    private String MPCarrier;
    private String MagTechnic;
    private double UVIntensity;
    private int lightDistance;

    public TestUnit(String name, int polesDistance, String MPCarrier, String MagTechnic, double UVStrength, int illuminationDistance) {
        this.name = name;
        this.polesDistance = polesDistance;
        this.MPCarrier = MPCarrier;
        this.MagTechnic = MagTechnic;
        this.UVIntensity = UVStrength;
        this.lightDistance = illuminationDistance;
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
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
        final TestUnit other = (TestUnit) obj;
        if (this.polesDistance != other.polesDistance) {
            return false;
        }
        if (Double.doubleToLongBits(this.UVIntensity) != Double.doubleToLongBits(other.UVIntensity)) {
            return false;
        }
        if (this.lightDistance != other.lightDistance) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
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
