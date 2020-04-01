/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem;
import java.sql.*;
/**
 *
 * @author Baraa
 */
public class DatabaseManagement {
    String url = "jdbc:mysql://sql7.freemysqlhosting.net:3306";
    private String user = "sql7330144";
    private String pass = "VzpLwH6zGh";
        
    
    public Connection connect (){
        Connection con = null;
        try {
        System.out.println("Connecting database...");
        con = DriverManager.getConnection(url, user, pass);
        System.out.println("Database connected!");
        Statement stmt = con.createStatement();
        stmt.executeUpdate("USE sql7330144;");
        } catch (SQLException e) {
        System.out.println("Database connection error:" + e);
        }   
        return con;
    }

    //Einfügen von Mitarbeiter
    public int insertEmployee (Employee employee, Manager manager){
        Connection con = this.connect();
        ResultSet rs;
        int result;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + employee.getPersonalNr() + ";");
            if (rs.next() == false) {
                java.util.Date date1 = employee.getBirthDate();
                String birthdate = date1.getYear() + "-" + date1.getMonth() + "-" + date1.getDay();
                java.util.Date date2 = employee.getPermitionEndDate();
                String permitionEndDate = date2.getYear() + "-" + date2.getMonth() + "-" + date2.getDay();

                stmt.executeUpdate("INSERT INTO Person (TCNr, PersonalNr, name, lastname, username, password, birthdate, address, email, telephone, status) " +
                "VALUES (" + employee.getTCNr() + "," + employee.getPersonalNr() + ",'" + employee.getName() + "','" + employee.getLastname() + "','" + employee.getUsername() +
                "','" + employee.getPassword() + "','" + birthdate + "','" + employee.getAddress() + "','" + employee.getEmail()+ "'," + employee.getTelephone() + ",2);");                
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
                stmt.executeUpdate("INSERT INTO Employee (Employee_id, Manager_id, level, permition_End_Date) VALUES (" + employee_Id + "," + manager_Id + ", 2,'" + permitionEndDate + "');");
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
        ResultSet rs;
        int result;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next() == false) {
                java.util.Date date = manager.getBirthDate();
                String myDate = date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
                stmt.executeUpdate("INSERT INTO Person (TCNr, PersonalNr, name, lastname, username, password, birthdate, address, email, telephone, status) " +
                "VALUES (" + manager.getTCNr() + "," + manager.getPersonalNr() + ",'" + manager.getName() + "','" + manager.getLastname() + "','" + manager.getUsername() +
                "','" + manager.getPassword() + "','" + myDate + "','" + manager.getAddress() + "','" + manager.getEmail()+ "'," + manager.getTelephone() + ",1);");
                return 1;
            }
            else {
                return 0;
            }
            } catch (SQLException e) {
                return -1;
            }
    }
    //Einfügen von Testmaschine (Magnetic und Radiographic)
    //Einfügen von Kunden
    //Erstellen der Offerliste eines Kunden
    //Erstellen der Orderliste eines Kunden
    //Erstellen eines Berichts (Magnetic und Radiographic
    
}
