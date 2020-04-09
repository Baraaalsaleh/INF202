/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.DatabaseManagement;

import berichtserstellungssystem.Common;
import berichtserstellungssystem.DatabaseManagement.*;
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
    public static int insertEmployee (Employee employee, Manager manager){
        Connection con = DatabaseManagement.connect();
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + employee.getPersonalNr() + ";");
            if (rs.next() == false) {
                String birthdate = Common.date_toString(employee.getBirthDate());
                String permitionEndDate = Common.date_toString(employee.getPermitionEndDate());

                stmt.executeUpdate("INSERT INTO Person (TCNr, PersonalNr, name, lastname, birthdate, address, email, telephone, status) " +
                "VALUES (" + employee.getTCNr() + "," + employee.getPersonalNr() + ",'" + employee.getName() + "','" + employee.getLastname() + "','"
                + birthdate + "','" + employee.getAddress() + "','" + employee.getEmail()+ "'," + employee.getTelephone() + ", " + DatabaseManagement.getEmployee_status() + ");");                
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
                stmt.executeUpdate("INSERT INTO Employee (Person_id, Manager_id, level, permition_End_Date) VALUES (" + employee_Id + "," + manager_Id + ", " + employee.getLevel() + ",'" + permitionEndDate + "');");
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
    public static int insertManager (Manager manager){
        Connection con = DatabaseManagement.connect();
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next() == false) {
                String myDate = Common.date_toString(manager.getBirthDate());
                stmt.executeUpdate("INSERT INTO Person (TCNr, PersonalNr, name, lastname, birthdate, address, email, telephone, status) " +
                "VALUES (" + manager.getTCNr() + "," + manager.getPersonalNr() + ",'" + manager.getName() + "','" + manager.getLastname() + "','"
                 + "','" + myDate + "','" + manager.getAddress() + "','" + manager.getEmail()+ "'," + manager.getTelephone() + ", " 
                        + DatabaseManagement.getManager_status() + ");");
                int manager_id = 0;
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                }
                stmt.executeUpdate("INSERT INTO Manager (Person_id, username, password) VALUES (" + manager_id + ", '" + manager.getUsername() + "', '" + manager.getPassword() + "');");
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
    public static int removePerson (Person person){
        Connection con = DatabaseManagement.connect();
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            int id = 0;
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + person.getPersonalNr() + ";");
            if (rs.next()){
                id = rs.getInt("id");
                }      
            stmt.executeUpdate("DELETE FROM Person WHERE PersonalNr = " + person.getPersonalNr() + ";");         
            stmt.executeUpdate("DELETE FROM Employee WHERE Person_id = " + id + ";");         
            stmt.executeUpdate("DELETE FROM LastModification WHERE Element_id = " + id + ";");                 
            return 1;
            } 
        catch (SQLException e) {
                return -1;
            }
    }
    //Modifiziere Informationen einer Employee
    public static int updateEmployee (Employee employee, Manager manager){
        Connection con = DatabaseManagement.connect();
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + employee.getPersonalNr() + ";");
            if (rs.next()) {
                String birthdate = Common.date_toString(employee.getBirthDate());          
                String permitionEndDate = Common.date_toString(employee.getPermitionEndDate());

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
                stmt.executeUpdate("UPDATE Employee SET level = " + employee.getLevel() + ", permition_End_Date = '" + permitionEndDate + "' WHERE Person_id = " + employee_Id + ";");
                rs = stmt.executeQuery("SELECT id FROM LastModification WHERE Element_id = " + employee_Id + " AND type = " + DatabaseManagement.getEmployee_type() + ";");
                if (rs.next()){
                    int last_id = rs.getInt("id");
                    stmt.executeUpdate("DELETE FROM LastModification WHERE id = " + last_id + ";");
                    stmt.executeUpdate("INSERT INTO LastModification (Type, Manager_id, Element_id, date) VALUES (" + DatabaseManagement.getEmployee_type() + ", " + manager_Id + ", " + employee_Id + ", NOW();");
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
    public static int updateManager (Manager manager){
        Connection con = DatabaseManagement.connect();
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()) {
                String birthdate = Common.date_toString(manager.getBirthDate());
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
    public static int updatePerson_self (Manager manager){
        Connection con = DatabaseManagement.connect();
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()) {
                int manager_id = 0;
                stmt.executeUpdate("UPDATE Person SET address = '" + manager.getAddress() + "', email = '"  + manager.getEmail()+ "', telephone = " + manager.getTelephone() + 
                " WHERE id = " + manager_id + ";");
                stmt.executeUpdate("UPDATE Manager SET username = '" +  manager.getUsername() + "', password = '" + manager.getPassword() + "' WHERE Person_id = " + manager_id + ";");
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
    public static ResultSet getEmployees (int limit) {
        Connection con = DatabaseManagement.connect();
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT name, lastname, PersonalNr FROM Person WHERE status = " + DatabaseManagement.getEmployee_status() + " LIMIT " + limit + ";");
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
    public static ResultSet getAddedEmployees (int limit, Manager manager) {
        Connection con = DatabaseManagement.connect();
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            int manager_id = 0;
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()){
                manager_id = rs.getInt("id");
            }               
            rs = stmt.executeQuery("SELECT P.name, P.lastname, P.PersonalNr FROM Person P JOIN Employee E ON P.id = E.Person_id WHERE P.status = " + DatabaseManagement.getEmployee_status() + " AND E.Manager_id = " + manager_id + " LIMIT " + limit + ";");
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
    public static ResultSet getEditedEmployees (int limit, Manager manager) {
        Connection con = DatabaseManagement.connect();
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            int manager_id = 0;
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()){
                manager_id = rs.getInt("id");
            }               
            rs = stmt.executeQuery("SELECT P.name, P.lastname, P.PersonalNr FROM Person P JOIN LastModification L ON P.id = L.Element_id WHERE P.status = " 
                    + DatabaseManagement.getEmployee_status() + " AND L.Manager_id = " + manager_id + " AND L.type = " + DatabaseManagement.getEmployee_type() + " LIMIT " + limit + ";");
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
    public static ResultSet getManagers (int limit) {
        Connection con = DatabaseManagement.connect();
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT name, lastname, PersonalNr FROM Person WHERE status = " + DatabaseManagement.getManager_status() + " LIMIT " + limit + ";");
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
    public static ResultSet getEmployee (long personalNr){
        Connection con = DatabaseManagement.connect();
        ResultSet rs = null;
        int employee_id = 0;
        try {
            Statement stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + personalNr + ";");
                if (rs.next()){
                    employee_id = rs.getInt("id");
                }                  
            rs = stmt.executeQuery("SELECT P.TCNr, P.PersonalNr, P.name as Employee_name, P.lastname as Employee_lastname, P.birthdate, P.address,"
                    + " P.email, P.telephone, E.level, E.permition_End_Date, L.date, Per.name as adder_name, Per.lastname as adder_lastname, Person.name as changer_name, "
                    + "Person.lastname as changer_lastname FROM Person P JOIN Employee E ON P.id = E.Employee_id JOIN Person Per ON E.Manager_id = Per.id"
                    + " JOIN LastModification L ON L.Element_id = P.id JOIN Person ON L.Manager_id = Person.id WHERE L.type = " + DatabaseManagement.getEmployee_type() + 
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
        //Abfragen von Mitarbeiterinformationen (Manager)
    public static ResultSet getManager (long personalNr){
        Connection con = DatabaseManagement.connect();
        ResultSet rs = null;
        int manager_id = 0;
        try {
            Statement stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + personalNr + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                }                  
            rs = stmt.executeQuery("SELECT P.TCNr, P.PersonalNr, P.name, P.lastname, P.birthdate, P.address,"
                    + " P.email, P.telephone, M.username, M.password FROM Person P JOIN Manager M ON P.id = M.Person_id"
                    + " WHERE P.id = " + manager_id + ";");
            if (rs.next()) {
                return rs;
            }   
        }
        catch (SQLException e){
            return rs;
        }
        return rs;
    } 
    //Login
    public static int login (String username, String password){
        Connection con = DatabaseManagement.connect();
        ResultSet rs = null;
        int res = 0;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT Person_id FROM Manager WHERE username = '" + username + "' AND password = '" + password + "';");
            if (rs.next()) {
                res = (rs.getInt("Person_id"));
            }   
        }
        catch (SQLException e){
            res = -1;
        }
        return res;
    } 
}
