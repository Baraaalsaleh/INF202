/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.Resource;

import berichtserstellungssystem.Resource.MagneticEquipment;
import berichtserstellungssystem.Resource.Employee;
import berichtserstellungssystem.Resource.Customer;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Baraa
 */
public class Manager extends Person{
    private ArrayList addedEmployees = new ArrayList<Employee>();
    private ArrayList addedTestUnits = new ArrayList<MagneticEquipment>();
    private ArrayList addedCustomers = new ArrayList<Customer>();

    public Manager(String name, String lastname, String username, String password, String gender, String address, String email, long TCNr, long telephone, long personalNr, Date birthDate) {
        super(name, lastname, username, password, gender, address, email, TCNr, telephone, personalNr, birthDate);
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

    public void addTestUnits(MagneticEquipment addedTestUnit) {
        this.addedTestUnits.add(addedTestUnit);
    }

    public void addCustomers(Customer addedCustomer) {
        this.addedCustomers.add(addedCustomer);
    }
    
    public void delEmployees(Employee deletedEmployee) {
        this.addedEmployees.remove(deletedEmployee);
    }

    public void delTestUnits(MagneticEquipment deletedTestUnit) {
        this.addedTestUnits.remove(deletedTestUnit);
    }

    public void delCustomers(Customer deletedCustomer) {
        this.addedCustomers.remove(deletedCustomer);
    }
}
