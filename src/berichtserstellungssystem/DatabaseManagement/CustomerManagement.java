/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.DatabaseManagement;

import berichtserstellungssystem.DataPreparation;
import berichtserstellungssystem.Resource.Customer;
import berichtserstellungssystem.DatabaseManagement.DatabaseManagement;
import berichtserstellungssystem.Resource.Manager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Baraa
 */
public class CustomerManagement extends DatabaseManagement{

    //Einfügen von Kunden
    public static int insertCustomer (Customer customer, Manager manager){
        ResultSet rs;
        int manager_id = 0; 
        int customer_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Customer WHERE name = '" + DataPreparation.prepareString(customer.getName()) + "';");
            if (rs.next() == false) {
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                    }
                stmt.executeUpdate("INSERT INTO Customer (Manager_id, name, address) VALUES (" + manager_id + ", '" + DataPreparation.prepareString(customer.getName()) + "', '" + DataPreparation.prepareString(customer.getAddress()) + "');");
                rs = stmt.executeQuery("SELECT id FROM Customer WHERE name = '" + DataPreparation.prepareString(customer.getName()) + "';");
                if (rs.next()){
                    customer_id = rs.getInt("id");
                }
                ArrayList<String> offers = customer.getOfferNrs();
                for (int i = 0; i < offers.size(); i++) {
                    stmt.executeUpdate("INSERT INTO Customer_Offer (Customer_id, Manager_id, OfferNr) VALUES (" + customer_id + ", " + manager_id + ", '" + DataPreparation.prepareString(offers.get(i)) + "');");
                }
                ArrayList<String> orders = customer.getOrderNrs();
                for (int i = 0; i < orders.size(); i++) {
                    stmt.executeUpdate("INSERT INTO Customer_Order (Customer_id, Manager_id, OrderNr) VALUES (" + customer_id + ", " + manager_id + ", '" + DataPreparation.prepareString(orders.get(i)) + "');");
                }
                return 1;
            }
            else {
                return 0;
            }
        }
            catch (SQLException e) {
                System.out.println("insertCustomer " + e);
                con = DatabaseManagement.connect();
                deleteCustomer(customer.getName());
                return -1;
            }
    }
    //Löschen von Kunden
    public static int deleteCustomer (String name){
        ResultSet rs;
        int customer_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Customer WHERE name = '" + name + "';");
            if (rs.next()) {
                customer_id = rs.getInt("id");
                stmt.executeUpdate("DELETE FROM Customer WHERE id = " + customer_id + ";");
                stmt.executeUpdate("DELETE FROM Customer_Offer WHERE Customer_id = " + customer_id + ";");
                stmt.executeUpdate("DELETE FROM Customer_Order WHERE Customer_id = " + customer_id + ";");
                return 1;
            }
            else {
                return 0;
            }
        }
            catch (SQLException e) {
                System.out.println("deleteCustomer " + e);
                return -1;
            }
    }
    //Modifikation von Kundeninformationen
    public static int updateCustomer (Customer customer, Manager manager){
        ResultSet rs;
        int manager_id = 0; 
        int customer_id = 0;
        int last_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Customer WHERE name = '" + DataPreparation.prepareString(customer.getName()) + "';");
            if (rs.next()) {
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                    }
                stmt.executeUpdate("UPDATE Customer SET address = '" + DataPreparation.prepareString(customer.getAddress()) + "';");
                rs = stmt.executeQuery("SELECT id FROM Customer WHERE name = '" + DataPreparation.prepareString(customer.getName()) + "';");
                if (rs.next()){
                    customer_id = rs.getInt("id");
                }
                stmt.executeUpdate("DELETE FROM Customer_Offer WHERE Customer_id = " + customer_id + ";");
                stmt.executeUpdate("DELETE FROM Customer_Order WHERE Customer_id = " + customer_id + ";"); 
                ArrayList<String> offers = customer.getOfferNrs();
                for (int i = 0; i < offers.size(); i++) {
                    stmt.executeUpdate("INSERT INTO Customer_Offer (Customer_id, Manager_id, OfferNr) VALUES (" + customer_id + ", " + manager_id + ", '" + DataPreparation.prepareString(offers.get(i)) + "');");
                }
                ArrayList<String> orders = customer.getOrderNrs();
                for (int i = 0; i < orders.size(); i++) {
                    stmt.executeUpdate("INSERT INTO Customer_Order (Customer_id, Manager_id, OrderNr) VALUES (" + customer_id + ", " + manager_id + ", '" + DataPreparation.prepareString(orders.get(i)) + "');");
                }
                rs = stmt.executeQuery("SELECT id FROM LastModification WHERE Element_id = " + customer_id + " AND type = " + DatabaseManagement.getCustomer_type() + ";");
                if (rs.next()){
                    last_id = rs.getInt("id");
                }
                stmt.executeUpdate("DELETE FROM LastModification WHERE id = " + last_id + ";");
                stmt.executeUpdate("INSERT INTO LastModification (Type, Manager_id, Element_id, date) VALUES (" + DatabaseManagement.getCustomer_type() + ", " + manager_id + ", " + customer_id + ", NOW();");                                
                return 1;
            }
            else {
                return 0;
            }
        }
            catch (SQLException e) {
                System.out.println("updateCustomer " + e);
                return -1;
            }
    }
    //Abfragen von allen Kunden
    public static ResultSet getCustomers (int limit) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT name, address FROM Customer LIMIT " + limit + ";");
            if (rs.next()) {
                return rs;
            }   
        }
        catch (SQLException e){
            System.out.println("getCustomers " + e);
            return rs;
        }
        return rs;
    }
    //Abfragen von allen Kunden, die von einem bestimmten Manager eingerfügt wurden
    public static ResultSet getAddedCustomers (int limit, Manager manager) {
        ResultSet rs = null;
        int manager_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()){
                manager_id = rs.getInt("id");
                }            
            rs = stmt.executeQuery("SELECT name, address FROM Customer WHERE Manager_id = " + manager_id + " LIMIT " + limit + ";");
            if (rs.next()) {
                return rs;
            }   
        }
        catch (SQLException e){
            System.out.println("getAddedCustomers " + e);
            return rs;
        }
        return rs;
    }
    //Abfragen von allen Kunden, die von einem bestimmten Manager modifiziert wurden
    public static ResultSet getEditedCustomers (int limit, Manager manager) {
        ResultSet rs = null;
        int manager_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()){
                manager_id = rs.getInt("id");
                }            
            rs = stmt.executeQuery("SELECT C.name, C.address FROM Customer C JOIN LastModification L ON C.id = L.Element_id WHERE L.Manager_id = " + manager_id 
                    + " AND L.type = " + DatabaseManagement.getCustomer_type() + " LIMIT " + limit + ";");
            if (rs.next()) {
                return rs;
            }   
        }
        catch (SQLException e){
            System.out.println("getEditedCustomers " + e);
            return rs;
        }
        return rs;
    }    
    //Abfragen von einem bestimmten Kunden
    public static ResultSet[] getCustomer (String name) {
        ResultSet[] rs = new ResultSet[3];
        int customer_id = 0;
        try {
            stmt = con.createStatement();            
            rs[0] = stmt.executeQuery("SELECT id FROM Customer WHERE name = '" + DataPreparation.prepareString(name) + "';");
            if (rs[0].next()){
                customer_id = rs[0].getInt("id");
            }            
            rs[0] = stmt.executeQuery("SELECT C.name, C.address, P.name as adder_name, P.lastname as adder_lastname FROM Customer C JOIN Person P ON C.Manager_id = P.id WHERE C.id = " + customer_id + ";");
            rs[1] = stmt.executeQuery("SELECT C.OfferNr, P.name as adder_name, P.lastname as adder_lastname FROM Customer_Offer C JOIN Person P ON C.Manager_id = P.id WHERE C.Customer_id = " + customer_id + ";");
            rs[2] = stmt.executeQuery("SELECT C.OrderNr, P.name as adder_name, P.lastname as adder_lastname FROM Customer_Order C JOIN Person P ON C.Manager_id = P.id WHERE C.Customer_id " + customer_id + ";");            
        }
        catch (SQLException e){
            System.out.println("getCustomer " + e);
            return rs;
        }
        return rs;
    }        
}
