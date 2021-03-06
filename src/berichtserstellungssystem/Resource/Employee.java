//Baraa Alsaleh, 19050800
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.Resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Baraa
 */
public class Employee extends Person{
    private int level = 0;
    private Date permitionEndDate = new Date();
    private LastModification lastChange = null;
    private Manager adder = new Manager();

    
    public Employee(int level, String name, String lastname, String gender, String address, String email, long TCNr, long telephone, long personalNr, Date birthDate, Date permitionEndDate) {
        super(name, lastname, gender, address, email, TCNr, telephone, personalNr, birthDate);
        this.level = level;
    }
    
    public Employee (ResultSet rs) {
        super(rs);
        try {       
            this.level = rs.getInt("level");
            String[] date = rs.getString("permition_End_Date").split("-");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            this.permitionEndDate = new Date(year-1900, month-1, day);
        }
        catch (SQLException e) {
            System.out.println("Employee.Constructor " + e);
        }
    }
    
    public Employee (String name, String lastname, long personalNr) {
        super(name, lastname, personalNr);
    }

    public Employee () {
        
    }
    
    public int getLevel() {
        return level;
    }

    public Date getPermitionEndDate() {
        return permitionEndDate;
    }
    
    public LastModification getLastChange() {
        return lastChange;
    }
    
    public Manager getAdder() {
        return adder;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setPermitionEndDate(Date permitionEndDate) {
        this.permitionEndDate = permitionEndDate;
    }
    
    public void setLastChange(LastModification lastChange) {
        this.lastChange = lastChange;
    }
    
    public void setAdder(Manager adder) {
        this.adder = adder;
    }

    @Override
    public String toString() {
        return "Employee{" + "level=" + level + ", permitionEndDate=" + permitionEndDate + super.toString() + '}';
    }
    
    
    
    
}
