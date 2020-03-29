/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Baraa
 */
public class Manager extends Person{
    private ArrayList addedEmployees = new ArrayList<Employee>();
    private ArrayList addedTestUnits = new ArrayList<TestUnit>();
    private ArrayList addedCustomers = new ArrayList<Customer>();
    
    public Manager(String name, String lastname, String geschlecht, Date birthDate, String adress, String email, int[] TCNr, int[] telephone, int[] personalNr, Date permitionEndDate, int level, boolean permitted) {
        this.setName(name);
        this.setLastname(lastname);
        this.setGender(geschlecht);
        this.setBirthDate(birthDate);
        this.setAddress(adress);
        this.setEmail(email);
        this.setTCNr(TCNr);
        this.setTelephone(telephone);
        this.setPersonalNr(personalNr);
    }

    public ArrayList getAddedEmployees() {
        return addedEmployees;
    }

    public ArrayList getAddedTestUnits() {
        return addedTestUnits;
    }

    public ArrayList getAddedCustomers() {
        return addedCustomers;
    }

    public void addEmployees(Employee addedEmployee) {
        this.addedEmployees.add(addedEmployee);
    }

    public void addTestUnits(TestUnit addedTestUnit) {
        this.addedTestUnits.add(addedTestUnit);
    }

    public void addCustomers(Customer addedCustomer) {
        this.addedCustomers.add(addedCustomer);
    }
    
    public void delEmployees(Employee deletedEmployee) {
        this.addedEmployees.remove(deletedEmployee);
    }

    public void delTestUnits(TestUnit deletedTestUnit) {
        this.addedTestUnits.remove(deletedTestUnit);
    }

    public void delCustomers(Customer deletedCustomer) {
        this.addedCustomers.remove(deletedCustomer);
    }
}
