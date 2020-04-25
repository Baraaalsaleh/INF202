/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.DatabaseManagement;

import berichtserstellungssystem.Common;
import berichtserstellungssystem.*;
import berichtserstellungssystem.DatabaseManagement.*;
import berichtserstellungssystem.Resource.Employee;
import berichtserstellungssystem.Resource.Manager;
import berichtserstellungssystem.Resource.Person;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Baraa
 */
public class PersonManagement extends DatabaseManagement{    
    
    //Einfügen von Mitarbeiter
    public static int insertEmployee (Employee employee, Manager manager){
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + employee.getPersonalNr() + ";");
            if (rs.next() == false) {
                String birthdate = Common.date_toString(employee.getBirthDate());
                String permitionEndDate = Common.date_toString(employee.getPermitionEndDate());

                stmt.executeUpdate("INSERT INTO Person (TCNr, PersonalNr, name, lastname, gender, birthdate, address, email, telephone, status) " +
                "VALUES (" + employee.getTcNr() + "," + employee.getPersonalNr() + ",'" + DataPreparation.prepareString(employee.getName()) + "','" + DataPreparation.prepareString(employee.getLastname()) + "','"
                + DataPreparation.prepareString(employee.getGender()) + "', '" + birthdate + "','" + DataPreparation.prepareString(employee.getAddress()) + "','" + DataPreparation.prepareString(employee.getEmail())
                        + "'," + employee.getTelephone() + ", " + DatabaseManagement.getEMPLOYEE_STATUS() + ");");                
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
                System.out.println("Employee id = " + employee_Id + " and Manager id = " + manager_Id);
                stmt.executeUpdate("INSERT INTO Employee (Person_id, Manager_id, level, permition_End_Date) VALUES (" + employee_Id + "," + manager_Id + ", " + employee.getLevel() + ",'" + permitionEndDate + "');");
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                System.out.println("insertEmployee " + e);
                con = DatabaseManagement.connect();
                deletePerson(employee.getPersonalNr());
                return -1;
            }
    }

    //Einfügen von Manager
    public static int insertManager (Manager manager, int status){
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next() == false) {
                String myDate = Common.date_toString(manager.getBirthDate());
                stmt.executeUpdate("INSERT INTO Person (TCNr, PersonalNr, name, lastname, gender, birthdate, address, email, telephone, status) " +
                "VALUES (" + manager.getTcNr() + "," + manager.getPersonalNr() + ",'" + DataPreparation.prepareString(manager.getName()) + "','" + DataPreparation.prepareString(manager.getLastname()) + "', '"
                 + DataPreparation.prepareString(manager.getGender()) + "','" + myDate + "','" + DataPreparation.prepareString(manager.getAddress()) + "','" + DataPreparation.prepareString(manager.getEmail())
                        + "'," + manager.getTelephone() + ", " + status + ");");
                int manager_id = 0;
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                    stmt.executeUpdate("INSERT INTO Manager (Person_id, username, password) VALUES (" + manager_id + ", '" + manager.getPersonalNr() + "', '" + manager.getTcNr() + "');");
                    return 1;
                }
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                System.out.println("insertManager " + e);
                con = DatabaseManagement.connect();
                deletePerson(manager.getPersonalNr());
                return -1;
            }
        return -1;
    }
    //Löschen einer Person
    public static int deletePerson (long personalNr){
        ResultSet rs;
        try {
            stmt = con.createStatement();
            int id = 0;
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + personalNr + ";");
            if (rs.next()){
                id = rs.getInt("id");
                }      
            stmt.executeUpdate("DELETE FROM Person WHERE PersonalNr = " + personalNr + ";");         
            stmt.executeUpdate("DELETE FROM Employee WHERE Person_id = " + id + ";");         
            stmt.executeUpdate("DELETE FROM LastModification WHERE Element_id = " + id + ";");                 
            return 1;
            } 
        catch (SQLException e) {
            System.out.println("deletePerson " + e);
            return -1;
            }
    }
    //Modifiziere Informationen einer Employee
    public static int updateEmployee (Employee employee, Manager manager){
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + employee.getPersonalNr() + ";");
            if (rs.next()) {
                String birthdate = Common.date_toString(employee.getBirthDate());          
                String permitionEndDate = Common.date_toString(employee.getPermitionEndDate());

                stmt.executeUpdate("UPDATE Person SET TCNr = " + employee.getTcNr() + ", name = '" + DataPreparation.prepareString(employee.getName()) + "', lastname = '" +
                    DataPreparation.prepareString(employee.getLastname()) + "', gender = '" + DataPreparation.prepareString(employee.getGender()) + "', birthdate = '"  
                    + birthdate + "', address = '" + DataPreparation.prepareString(employee.getAddress()) + "', email = '"  + DataPreparation.prepareString(employee.getEmail())
                    + "', telephone = " + employee.getTelephone() + " WHERE PersonalNr = " + employee.getPersonalNr() + ";");                
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
                rs = stmt.executeQuery("SELECT id FROM LastModification WHERE Element_id = " + employee_Id + " AND type = " + DatabaseManagement.getEMPLOYEE_TYPE() + ";");
                if (rs.next()){
                    int last_id = rs.getInt("id");
                    stmt.executeUpdate("DELETE FROM LastModification WHERE id = " + last_id + ";");
                }
                stmt.executeUpdate("INSERT INTO LastModification (Type, Manager_id, Element_id, date) VALUES (" + DatabaseManagement.getEMPLOYEE_TYPE() + ", " + manager_Id + ", " + employee_Id + ", NOW());");
                return 1;
                
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                System.out.println("updateEmployee " + e);
                return -1;
            }
    }
    //Modifiziere Managerinformationen
    public static int updateManager (Manager manager){
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()) {
                String birthdate = Common.date_toString(manager.getBirthDate());
                stmt.executeUpdate("UPDATE Person SET TCNr = " + manager.getTcNr() + ", name = '" + DataPreparation.prepareString(manager.getName()) + "', lastname = '" +
                        DataPreparation.prepareString(manager.getLastname()) + "', gender = '" + DataPreparation.prepareString(manager.getGender()) + "', birthdate = '"  + birthdate 
                        + "', address = '" + DataPreparation.prepareString(manager.getAddress()) + "', email = '"  + DataPreparation.prepareString(manager.getEmail())
                        + "', telephone = " + manager.getTelephone() + " WHERE PersonalNr = " + manager.getPersonalNr() + ";");                
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                System.out.println("updateManager " + e);
                return -1;
            }
    }
    //Modifikation von Kontaktinformationen des Mitarbeiters von dem Mitarbeiter selbst
    public static int updateManager_self (Manager manager){
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            
            if (rs.next()) {
                int manager_id = rs.getInt("id");
                
                stmt.executeUpdate("UPDATE Person SET address = '" + DataPreparation.prepareString(manager.getAddress()) + "', email = '"  + DataPreparation.prepareString(manager.getEmail())
                        + "', telephone = " + manager.getTelephone() + " WHERE id = " + manager_id + ";");
                                
                stmt.executeUpdate("UPDATE Manager SET username = '" +  DataPreparation.prepareString(manager.getUsername()) + "', password = '" 
                        + DataPreparation.prepareString(manager.getPassword()) + "' WHERE Person_id = " + manager_id + ";");
                
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                System.out.println("updateManager_self " + e);
                return -1;
            }
    }
    //Abfragen von allen Mitarbeitern
    public static ResultSet getEmployees (int biggerThan) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT P.id, P.name, P.lastname, P.PersonalNr, E.level, E.permition_End_Date FROM Person P JOIN Employee E ON P.id = E.Person_id WHERE status = " + DatabaseManagement.getEMPLOYEE_STATUS() 
                    + " AND P.id > " + biggerThan + " LIMIT 1;");
        }
        catch (SQLException e){
            System.out.println("getEmployees " + e);
        }
        return rs;
    }
    public static ArrayList<Employee> employees (int start, int limit, int type, Manager manager) {
        ArrayList<Employee> res = new ArrayList();
        Employee temp;
        ResultSet rs;
        int count = 0;
        int i = start;
        while (count < limit) {
            try {
                if (type == 1) {
                    rs = getEmployees(i);
                }
                else if (type == 2) {
                    rs = getAddedEmployees(i, manager);
                }
                else {
                    rs = getEditedEmployees(i, manager);
                }
                
                if (rs.next()) {
                    temp = new Employee(rs.getString("name"), rs.getString("lastname"), rs.getLong("PersonalNr"));
                    temp.setPermitionEndDate(rs.getDate("permition_End_Date"));
                    temp.setLevel(rs.getInt("level"));
                    System.out.println(i);
                    i += (rs.getInt("id")-i);
                    System.out.println(temp.getName());
                    res.add(temp);
                    count++;
                }
                else {
                    break;
                }
            } catch (SQLException ex) {
                System.out.println("employees " + ex);
                
            }
        }
        return res;
    }
    //Abfragen von allen Mitarbeitern, die von einem bestimmten Manager eingefügt wurden
    public static ResultSet getAddedEmployees (int biggerThan, Manager manager) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            int manager_id = 0;
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()){
                manager_id = rs.getInt("id");
            }               
            rs = stmt.executeQuery("SELECT P.id, P.name, P.lastname, P.PersonalNr, E.level, E.permition_End_Date FROM Person P JOIN Employee E ON P.id = E.Person_id WHERE P.status = " + DatabaseManagement.getEMPLOYEE_STATUS() 
                    + " AND E.Manager_id = " + manager_id + " AND P.id > " + biggerThan +" LIMIT 1;");
        }
        catch (SQLException e){
            System.out.println("getAddedEmployees " + e);            
        }
        return rs;
    }
    //Abfragen von allen Mitarbeitern, die von einem bestimmten Manager modifiziert wurden
    public static ResultSet getEditedEmployees (int biggerThan, Manager manager) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            int manager_id = 0;
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()){
                manager_id = rs.getInt("id");
            }
            rs = stmt.executeQuery("SELECT P.id, P.name, P.lastname, P.PersonalNr, E.level, E.permition_End_Date FROM Person P JOIN Employee E ON P.id = E.Person_id JOIN LastModification L ON P.id = L.Element_id WHERE P.status = " 
                    + DatabaseManagement.getEMPLOYEE_STATUS() + " AND L.Manager_id = " + manager_id + " AND L.type = " + DatabaseManagement.getEMPLOYEE_TYPE() 
                    + " AND P.id > " + biggerThan + " LIMIT 1;");  
        }
        catch (SQLException e){
            System.out.println("getEditedEmployees " + e);
        }
        return rs;
    }    
    //Abfragen aller Manager
    public static ArrayList<Manager> managers (int start, int limit) {
        ArrayList<Manager> res = new ArrayList();
        System.out.println("I got here first");
        Manager temp;
        ResultSet rs;
        int count = 0;
        int i = start;
        while (count < limit) {
            try {
                rs = getManagers(i);
                if (rs.next()) {
                    temp = new Manager(rs.getString("name"), rs.getString("lastname"), rs.getLong("PersonalNr"));
                    System.out.println(i);
                    i += (rs.getInt("id")-i);
                    System.out.println(temp.getName());
                    res.add(temp);
                    count++;
                }
                else {
                    break;
                }
            } catch (SQLException ex) {
                System.out.println("managers " + ex);
            }
        }
        return res;
    }
    public static ResultSet getManagers (int biggerThan) {
        ResultSet rs = null;
        System.out.println("I got here");
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT P.id, P.name, P.lastname, P.PersonalNr FROM Person P WHERE status = " + DatabaseManagement.getMANAGER_STATUS()
                    + " AND P.id > " + biggerThan + " LIMIT 1;");            
        }
        catch (SQLException e){
            System.out.println("getManagers " + e);
        }
        return rs;
    }
    //Abfragen von Mitarbeiterinformationen
    public static ResultSet getEmployee (long personalNr){
        ResultSet rs = null;
        int employee_id = 0;
        try {
            stmt = con.createStatement();
            System.out.println(personalNr);
            rs = stmt.executeQuery("SELECT P.id, P.TCNr, P.PersonalNr, P.name, P.lastname, P.gender, P.birthdate, P.address,\n" +
"                    P.email, P.telephone, E.level, E.permition_End_Date FROM Person P JOIN Employee E ON P.id = E.Person_id WHERE P.PersonalNr = " + personalNr + ";"); 
            if (rs.next()) {
                return rs;
            }
        }
        catch (SQLException e){
            System.out.println("getEmployee " + e);
        }
        return rs;
    } 
    
    //Abfragen von Mitarbeiterinformationen
    public static ResultSet getEmployeeById (int id){
        ResultSet rs = null;
        int employee_id = 0;
        try {
            stmt = con.createStatement();        
            rs = stmt.executeQuery("SELECT P.id, P.TCNr, P.PersonalNr, P.name as Employee_name, P.lastname as Employee_lastname, P.gender, P.birthdate, P.address,"
                    + " P.email, P.telephone, E.level, E.permition_End_Date, L.date, Per.name as adder_name, Per.lastname as adder_lastname, Person.name as changer_name, "
                    + "Person.lastname as changer_lastname FROM Person P JOIN Employee E ON P.id = E.Employee_id JOIN Person Per ON E.Manager_id = Per.id"
                    + " JOIN LastModification L ON L.Element_id = P.id JOIN Person ON L.Manager_id = Person.id WHERE L.type = " + DatabaseManagement.getEMPLOYEE_TYPE() + 
                    " AND P.id = " + id + ";");
            if (rs.next()) {
                return rs;
            }   
        }
        catch (SQLException e){
            System.out.println("getEmployeeById " + e);
            return rs;
        }
        return rs;
    } 
    
        //Abfragen von Mitarbeiterinformationen (Manager)
    public static ResultSet getManager (long personalNr){
        ResultSet rs = null;
        int manager_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + personalNr + ";");
            if (rs.next()){
                manager_id = rs.getInt("id");
            }
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT P.TCNr, P.PersonalNr, P.name, P.lastname, P.gender, P.birthdate, P.address,"
                    + " P.email, P.telephone, M.username, M.password FROM Person P JOIN Manager M ON P.id = M.Person_id"
                    + " WHERE P.id = " + manager_id + ";");
            if (rs.next()) {
                return rs;
            } 
        }
        catch (SQLException e){
            System.out.println("getManager " + e);
            return rs;
        }
        return rs;
    } 
            //Abfragen von Mitarbeiterinformationen (Manager)
    public static ResultSet getManagerById (int manager_id){
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT P.TCNr, P.PersonalNr, P.name, P.lastname, P.gender, P.birthdate, P.address,"
                    + " P.email, P.telephone, P.status, M.username, M.password FROM Person P JOIN Manager M ON P.id = M.Person_id"
                    + " WHERE P.id = " + manager_id + ";");
            if (rs.next()) {
                return rs;
            }   
        }
        catch (SQLException e){
            System.out.println("getManagerById " + e);
            return rs;
        }
        return rs;
    } 
    //Login
    public static int login (String username, String password){
        int res = -1;
        if (con != null){
            ResultSet rs = null;
            try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT Person_id FROM Manager WHERE username = '" + DataPreparation.prepareString(username) + "' AND password = BINARY '" + DataPreparation.prepareString(password) + "';");
            if (rs.next()) {
                res = (rs.getInt("Person_id"));
                }
            else {
                res = 0;
                }
            }
            catch (SQLException e){
                System.out.println("login " + e);
                res = -1;
            }
        }
        
        return res;
    } 
    
    public static boolean findAdmin() {
        boolean res = false;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE status = " + DatabaseManagement.getAdmin_STATUS() + ";");
            if (rs.next()) {
                res = true;
            }
        } catch (SQLException ex) {
            System.out.println("findAdmin " + ex);
        }
        return res;       
    }
}
