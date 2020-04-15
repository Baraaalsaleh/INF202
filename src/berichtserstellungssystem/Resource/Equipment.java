/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.Resource;

import java.util.Date;

/**
 *
 * @author Baraa
 */
public class Equipment {
    private String name;
    private Date calibrationEndDate;
    private int type;

    public Equipment(String name, Date calibrationEndDate) {
        this.name = name;
        this.calibrationEndDate = calibrationEndDate;
    }
    
    public Equipment() {

    }

    public void setCalibrationEndDate(Date calibrationEndDate) {
        this.calibrationEndDate = calibrationEndDate;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Date getCalibrationEndDate() {
        return calibrationEndDate;
    }

    public int getType() {
        return type;
    }
    
    
}
