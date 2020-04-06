/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem;

import berichtserstellungssystem.Resource.Manager;
import berichtserstellungssystem.Resource.Employee;
import berichtserstellungssystem.DatabaseManagement.DatabaseManagement;
import java.sql.*;
import java.util.Date;




/**
 *
 * @author Baraa
 */
public class Berichtserstellungssystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        DatabaseManagement myConnector = new DatabaseManagement();
        Date myDate = new Date(1990,11, 30);

        Employee myEmployee = new Employee(2, true, "name", "lastname", "username", "password", "gender", "address", "email", 99999999999L, 5555555555L, 999999999L, myDate);
        Manager myManager = new Manager("Managername", "Managerlastname", "Managerusername", "Managerpassword", "Managergender", "Manageraddress", "Manageremail", 11111111111L, 5555555555L, 111111111L, myDate);
        //System.out.println(myConnector.insertManager(myManager));
        //System.out.println(myConnector.insertEmployee(myEmployee, myManager));
    }
    
}
