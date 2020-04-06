/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.Resource;

import berichtserstellungssystem.Resource.Equipment;

/**
 *
 * @author Baraa
 */
public class RadiographicEquipment extends Equipment{
    private String usedDevice;
    private boolean ir192;
    private boolean se75;
    private boolean xRay;
    private String focalSpotSize;
    private String exposureTime;
    private String filmFocusDistance;
    private String pbScreens;
    private String filters;

    public RadiographicEquipment(String usedDevice, boolean ir192, boolean se75, boolean xRay, String focalSpotSize, String exposureTime, String filmFocusDistance, String pbSreens, String filters, String name) {
        super(name);
        this.usedDevice = usedDevice;
        this.ir192 = ir192;
        this.se75 = se75;
        this.xRay = xRay;
        this.focalSpotSize = focalSpotSize;
        this.exposureTime = exposureTime;
        this.filmFocusDistance = filmFocusDistance;
        this.pbScreens = pbSreens;
        this.filters = filters;
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
    
    
}
