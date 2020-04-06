/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.DatabaseManagement;

import berichtserstellungssystem.Common;
import berichtserstellungssystem.DatabaseManagement.DatabaseManagement;
import berichtserstellungssystem.Resource.Employee;
import berichtserstellungssystem.Resource.Manager;
import berichtserstellungssystem.Resource.Person;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Baraa
 */
public class PersonManagement extends DatabaseManagement{    
    //Einfügen von Mitarbeiter
    public int insertEmployee (Employee employee, Manager manager){
        Connection con = this.connect();
        Common common = new Common();
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + employee.getPersonalNr() + ";");
            if (rs.next() == false) {
                String birthdate = common.date_toString(employee.getBirthDate());
                String permitionEndDate = common.date_toString(employee.getPermitionEndDate());

                stmt.executeUpdate("INSERT INTO Person (TCNr, PersonalNr, name, lastname, username, password, birthdate, address, email, telephone, status) " +
                "VALUES (" + employee.getTCNr() + "," + employee.getPersonalNr() + ",'" + employee.getName() + "','" + employee.getLastname() + "','" + employee.getUsername() +
                "','" + employee.getPassword() + "','" + birthdate + "','" + employee.getAddress() + "','" + employee.getEmail()+ "'," + employee.getTelephone() + ", " + this.getEmployee_status() + ");");                
                int employee_Id = 0;
                int manager_Id = 0;            
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + employee.getPersonalNr() + ";");
                if (rs.next()){
                    employee_Id = rs.getInt("id");
                }                
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_Id = rs.getInt("id");
                }      
                stmt.executeUpdate("INSERT INTO Employee (Employee_id, Manager_id, level, permition_End_Date) VALUES (" + employee_Id + "," + manager_Id + ", " + employee.getLevel() + ",'" + permitionEndDate + "');");
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                return -1;
            }
    }

    //Einfügen von Manager
    public int insertManager (Manager manager){
        Connection con = this.connect();
        Common common = new Common();
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next() == false) {
                String myDate = common.date_toString(manager.getBirthDate());
                stmt.executeUpdate("INSERT INTO Person (TCNr, PersonalNr, name, lastname, username, password, birthdate, address, email, telephone, status) " +
                "VALUES (" + manager.getTCNr() + "," + manager.getPersonalNr() + ",'" + manager.getName() + "','" + manager.getLastname() + "','" + manager.getUsername() +
                "','" + manager.getPassword() + "','" + myDate + "','" + manager.getAddress() + "','" + manager.getEmail()+ "'," + manager.getTelephone() + ", " + this.getManager_status() + ");");
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                return -1;
            }
    }
    //Löschen einer Person
    public int removePerson (Person person){
        Connection con = this.connect();
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            int id = 0;
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + person.getPersonalNr() + ";");
            if (rs.next()){
                id = rs.getInt("id");
                }      
            stmt.executeUpdate("DELETE FROM Person WHERE PersonalNr = " + person.getPersonalNr() + ";");         
            stmt.executeUpdate("DELETE FROM Employee WHERE Employee_id = " + id + ";");         
            stmt.executeUpdate("DELETE FROM LastModification WHERE Element_id = " + id + ";");                 
            return 1;
            } 
        catch (SQLException e) {
                return -1;
            }
    }
    //Modifiziere Informationen einer Employee
    public int updateEmployee (Employee employee, Manager manager){
        Connection con = this.connect();
        Common common = new Common();
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + employee.getPersonalNr() + ";");
            if (rs.next()) {
                String birthdate = common.date_toString(employee.getBirthDate());          
                String permitionEndDate = common.date_toString(employee.getPermitionEndDate());

                stmt.executeUpdate("UPDATE Person SET TCNr = " + employee.getTCNr() + ", name = '" + employee.getName() + "', lastname = '" +
                employee.getLastname() + "', birthdate = '"  + birthdate + "', address = '" + employee.getAddress() + "', email = '"  + employee.getEmail()+ "', telephone = " + employee.getTelephone() + "WHERE PersonalNr = " + employee.getPersonalNr() + ";");                
                int employee_Id = 0;
                int manager_Id = 0;            
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + employee.getPersonalNr() + ";");
                if (rs.next()){
                    employee_Id = rs.getInt("id");
                }                
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_Id = rs.getInt("id");
                }      
                stmt.executeUpdate("UPDATE Employee SET level = " + employee.getLevel() + ", permition_End_Date = '" + permitionEndDate + "' WHERE Employee_id = " + employee_Id + ";");
                rs = stmt.executeQuery("SELECT id FROM LastModification WHERE Element_id = " + employee_Id + " AND type = " + this.getEmployee_type() + ";");
                if (rs.next()){
                    int last_id = rs.getInt("id");
                    stmt.executeUpdate("DELETE FROM LastModification WHERE id = " + last_id + ";");
                    stmt.executeUpdate("INSERT INTO LastModification (Type, Manager_id, Element_id, date) VALUES (" + this.getEmployee_type() + ", " + manager_Id + ", " + employee_Id + ", NOW();");
                }
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                return -1;
            }
    }
    //Modifiziere Managerinformationen
    public int updateManager (Manager manager){
        Connection con = this.connect();
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()) {
                java.util.Date date = manager.getBirthDate();
                String birthdate = date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
                stmt.executeUpdate("UPDATE Person SET TCNr = " + manager.getTCNr() + ", name = '" + manager.getName() + "', lastname = '" +
                manager.getLastname() + "', birthdate = '"  + birthdate + "', address = '" + manager.getAddress() + "', email = '"  + manager.getEmail()+ "', telephone = " + manager.getTelephone() + "WHERE PersonalNr = " + manager.getPersonalNr() + ";");                
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                return -1;
            }
    }
    //Modifikation von Kontaktinformationen des Mitarbeiters von dem Mitarbeiter selbst
    public int updatePerson_self (Employee employee){
        Connection con = this.connect();
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + employee.getPersonalNr() + ";");
            if (rs.next()) {
                stmt.executeUpdate("UPDATE Person SET address = '" + employee.getAddress() + "', email = '"  + employee.getEmail()+ "', telephone = " + employee.getTelephone() + 
                " username = '" + employee.getUsername() + "', password = " + employee.getPassword() + "WHERE PersonalNr = " + employee.getPersonalNr() + ";");
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                return -1;
            }
    }
    //Abfragen von allen Mitarbeitern
    public ResultSet getEmployees (int limit) {
        Connection con = this.connect();
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT name, lastname, PersonalNr FROM Person WHERE status = " + this.getEmployee_status() + " LIMIT " + limit + ";");
            if (rs.next()) {
                return rs;
            }   
        }
        catch (SQLException e){
            return rs;
        }
        return rs;
    }
    //Abfragen von allen Mitarbeitern, die von einem bestimmten Manager eingefügt wurden
    public ResultSet getAddedEmployees (int limit, Manager manager) {
        Connection con = this.connect();
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            int manager_id = 0;
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()){
                manager_id = rs.getInt("id");
            }               
            rs = stmt.executeQuery("SELECT P.name, P.lastname, P.PersonalNr FROM Person P JOIN Employee E ON P.id = E.Employee_id WHERE P.status = " + this.getEmployee_status() + " AND E.Manager_id = " + manager_id + " LIMIT " + limit + ";");
            if (rs.next()) {
                return rs;
            }   
        }
        catch (SQLException e){
            return rs;
        }
        return rs;
    }
    //Abfragen von allen Mitarbeitern, die von einem bestimmten Manager modifiziert wurden
    public ResultSet getEditedEmployees (int limit, Manager manager) {
        Connection con = this.connect();
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            int manager_id = 0;
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()){
                manager_id = rs.getInt("id");
            }               
            rs = stmt.executeQuery("SELECT P.name, P.lastname, P.PersonalNr FROM Person P JOIN LastModification L ON P.id = L.Element_id WHERE P.status = " 
                    + this.getEmployee_status() + " AND L.Manager_id = " + manager_id + " AND L.type = " + this.getEmployee_type() + " LIMIT " + limit + ";");
            if (rs.next()) {
                return rs;
            }   
        }
        catch (SQLException e){
            return rs;
        }
        return rs;
    }    
    //Abfragen aller Manager
    public ResultSet getManagers (int limit) {
        Connection con = this.connect();
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT name, lastname, PersonalNr FROM Person WHERE status = " + this.getManager_status() + " LIMIT " + limit + ";");
            if (rs.next()) {
                return rs;
            }   
        }
        catch (SQLException e){
            return rs;
        }
        return rs;
    }
    //Abfragen von Mitarbeiterinformationen
    public ResultSet getEmployee (long personalNr){
        Connection con = this.connect();
        ResultSet rs = null;
        int employee_id = 0;
        try {
            Statement stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + personalNr + ";");
                if (rs.next()){
                    employee_id = rs.getInt("id");
                }                  
            rs = stmt.executeQuery("SELECT P.TCNr, P.PersonalNr, P.name as Employee_name, P.lastname as Employee_lastname, P.username, P.password, P.birthdate, P.address,"
                    + " P.email, P.telephone, E.level, E.permition_End_Date, L.date, Per.name as adder_name, Per.lastname as adder_lastname, Person.name as changer_name, "
                    + "Person.lastname as changer_lastname FROM Person P JOIN Employee E ON P.id = E.Employee_id JOIN Person Per ON E.Manager_id = Per.id"
                    + " JOIN LastModification L ON L.Element_id = P.id JOIN Person ON L.Manager_id = Person.id WHERE L.type = " + this.getEmployee_type() + 
                    " AND P.PersonalNr = " + personalNr + ";");
            if (rs.next()) {
                return rs;
            }   
        }
        catch (SQLException e){
            return rs;
        }
        return rs;
    } 
    //Abfragen von Managerinformationen
    public ResultSet getManager (long personalNr){
        Connection con = this.connect();
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Person WHERE PersonalNr = " + personalNr + ";");
            if (rs.next()) {
                return rs;
            }   
        }
        catch (SQLException e){
            return rs;
        }
        return rs;
    } 
}
