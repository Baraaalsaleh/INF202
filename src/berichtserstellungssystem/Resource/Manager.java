//Baraa Alsaleh, 19050800
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.Resource;

import berichtserstellungssystem.Resource.MagneticEquipment;
import berichtserstellungssystem.Resource.Employee;
import berichtserstellungssystem.Resource.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Baraa
 */
public class Manager extends Person{
//    private ArrayList addedEmployees = new ArrayList<Employee>();
//    private ArrayList addedTestUnits = new ArrayList<MagneticEquipment>();
//    private ArrayList addedCustomers = new ArrayList<Customer>();
    private String username = "";
    private String password = "";

    public Manager(String name, String lastname, String username, String password, String gender, String address, String email, long TCNr, long telephone, long personalNr, Date birthDate) {
        super(name, lastname, gender, address, email, TCNr, telephone, personalNr, birthDate);
        this.username = username;
        this.password = password;
    }
    
    public Manager (String name, String lastname, long personalNr) {
        super(name, lastname, personalNr);
    }
    
    public Manager (ResultSet rs) {
        super(rs);
        try {
            this.username = rs.getString("username");
            this.password = rs.getString("password");
        } catch (SQLException e) {
            System.out.println("Manager RS " + e);
        }
    }

    public Manager () {
        
    }
    
//    public ArrayList getAddedEmployees() {
//        return addedEmployees;
//    }
//
//    public ArrayList getAddedTestUnits() {
//        return addedTestUnits;
//    }
//
//    public ArrayList getAddedCustomers() {
//        return addedCustomers;
//    }
//
//    public void addEmployees(Employee addedEmployee) {
//        this.addedEmployees.add(addedEmployee);
//    }
//
//    public void addTestUnits(MagneticEquipment addedTestUnit) {
//        this.addedTestUnits.add(addedTestUnit);
//    }
//
//    public void addCustomers(Customer addedCustomer) {
//        this.addedCustomers.add(addedCustomer);
//    }
//    
//    public void delEmployees(Employee deletedEmployee) {
//        this.addedEmployees.remove(deletedEmployee);
//    }
//
//    public void delTestUnits(MagneticEquipment deletedTestUnit) {
//        this.addedTestUnits.remove(deletedTestUnit);
//    }
//
//    public void delCustomers(Customer deletedCustomer) {
//        this.addedCustomers.remove(deletedCustomer);
//    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Manager{" + "username=" + username + ", password=" + password + super.toString() +'}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.username);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    
    
    
}
