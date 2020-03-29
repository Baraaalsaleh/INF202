/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem;

import java.util.Date;

/**
 *
 * @author Baraa
 */
public class Employee extends Person{
    private int level;
    private boolean permitted;
    private Date permitionEndDate = new Date();

    public Employee(String name, String lastname, String geschlecht, Date birthDate, String adress, String email, long TCNr, int[] telephone, int personalNr, Date permitionEndDate, int level, boolean permitted) {
        this.setName(name);
        this.setLastname(lastname);
        this.setGender(geschlecht);
        this.setBirthDate(birthDate);
        this.setAddress(adress);
        this.setEmail(email);
        this.setTCNr(TCNr);
        this.setTelephone(telephone);
        this.setPersonalNr(personalNr);
        this.permitionEndDate = permitionEndDate;
        this.level = level;
        this.permitted = permitted;
        this.setUsername(Long.toString(TCNr));
        this.setPassword(Integer.toString(personalNr));
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
