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
public class Employee extends Person{
    private int level;
    private boolean permitted;
    private Date permitionEndDate = new Date();

    
    public Employee(int level, boolean permitted, String name, String lastname, String gender, String address, String email, long TCNr, long telephone, long personalNr, Date birthDate) {
        super(name, lastname, gender, address, email, TCNr, telephone, personalNr, birthDate);
        this.level = level;
        this.permitted = permitted;
    }

    public int getLevel() {
        return level;
    }

    public boolean isPermitted() {
        return permitted;
    }

    public Date getPermitionEndDate() {
        return permitionEndDate;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setPermitted(boolean permitted) {
        this.permitted = permitted;
    }

    public void setPermitionEndDate(Date permitionEndDate) {
        this.permitionEndDate = permitionEndDate;
    }
    
    
}
