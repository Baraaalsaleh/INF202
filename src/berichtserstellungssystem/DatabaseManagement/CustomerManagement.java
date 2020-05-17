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
            System.out.println(customer.getName());
            if (!rs.next()) {
                int id = 0;
                rs = stmt.executeQuery("SELECT MAX(id) as id FROM Customer;");
                if (rs.next()) {
                    id = rs.getInt("id");
                }
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                    }
                stmt.executeUpdate("INSERT INTO Customer (id, Manager_id, name, address) VALUES ("+ (id+1) + ", " + manager_id + ", '" + DataPreparation.prepareString(customer.getName()) + "', '" + DataPreparation.prepareString(customer.getAddress()) + "');");
                rs = stmt.executeQuery("SELECT id FROM Customer WHERE name = '" + DataPreparation.prepareString(customer.getName()) + "';");
                if (rs.next()){
                    customer_id = rs.getInt("id");
                }
                ArrayList<String> offers = customer.getOfferNrs();
                id = 0;
                if (!offers.isEmpty()) {
                    rs = stmt.executeQuery("SELECT MAX(id) as id FROM Customer_Offer;");
                    if (rs.next()) {
                        id = rs.getInt("id");
                    }
                    for (int i = 0; i < offers.size(); i++) {
                        stmt.executeUpdate("INSERT INTO Customer_Offer (id, Customer_id, Manager_id, OfferNr) VALUES (" + (id+1+i) + ", " + customer_id + ", " + manager_id + ", '" + DataPreparation.prepareString(offers.get(i)) + "');");
                    }
                }
                
                ArrayList<String> orders = customer.getOrderNrs();
                id = 0;
                if (!orders.isEmpty()) {
                    for (int i = 0; i < orders.size(); i++) {
                        rs = stmt.executeQuery("SELECT MAX(id) as id FROM Customer_Order;");
                        if (rs.next()) {
                            id = rs.getInt("id");
                        }
                        stmt.executeUpdate("INSERT INTO Customer_Order (id, Customer_id, Manager_id, OrderNr) VALUES (" + (id+1+i) + ", " + customer_id + ", " + manager_id + ", '" + DataPreparation.prepareString(orders.get(i)) + "');");
                    }
                }
                
                return 1;
            }
            else {
                return 0;
            }
        }
            catch (SQLException e) {
                System.out.println("insertCustomer " + e);
                //Falls das Einfügen nicht komplett funktionierte, wird die Verbindung an die Datenbank erneut und die Daten dieses Kundens komplett gelöscht
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
                int id = 0;
                for (int i = 0; i < offers.size(); i++) {
                    rs = stmt.executeQuery("SELECT MAX(id) as id FROM Customer_Offer;");
                    if (rs.next()) {
                        id = rs.getInt("id");
                    }
                    stmt.executeUpdate("INSERT INTO Customer_Offer (id, Customer_id, Manager_id, OfferNr) VALUES (" + (id+1+i) + ", " + customer_id + ", " + manager_id + ", '" + DataPreparation.prepareString(offers.get(i)) + "');");
                }
                ArrayList<String> orders = customer.getOrderNrs();
                id = 0;
                for (int i = 0; i < orders.size(); i++) {
                    rs = stmt.executeQuery("SELECT MAX(id) as id FROM Customer_Order;");
                    if (rs.next()) {
                        id = rs.getInt("id");
                    }
                    stmt.executeUpdate("INSERT INTO Customer_Order (id, Customer_id, Manager_id, OrderNr) VALUES (" + (id+1+i) + ", " + customer_id + ", " + manager_id + ", '" + DataPreparation.prepareString(orders.get(i)) + "');");
                }
                rs = stmt.executeQuery("SELECT id FROM LastModification WHERE Element_id = " + customer_id + " AND type = " + DatabaseManagement.getCUSTOMER_TYPE() + ";");
                if (rs.next()){
                    last_id = rs.getInt("id");
                    stmt.executeUpdate("DELETE FROM LastModification WHERE id = " + last_id + ";");
                }
                id = 0;
                rs = stmt.executeQuery("SELECT MAX(id) as id FROM LastModification;");
                if (rs.next()) {
                    id = rs.getInt("id");
                }
                stmt.executeUpdate("INSERT INTO LastModification (id, Type, Manager_id, Element_id, date) VALUES (" + (id+1) + ", " + DatabaseManagement.getCUSTOMER_TYPE() + ", " + manager_id + ", " + customer_id + ", NOW();");                                
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
    public static ArrayList<Customer> customers (int start, int limit, int type, Manager manager) {
        ArrayList<Customer> res = new ArrayList();
        Customer temp;
        ResultSet rs = null;
        int count = 0;
        int i = start;
        while (count < limit) {
            try {
                if (type == 1) {
                    rs = getCustomers(i);
                }
                else if (type == 2) {
                    rs = getAddedCustomers(i, manager);
                }
                else {
                    rs = getEditedCustomers(i, manager);
                }
                if (rs.next()) {
                    temp = new Customer(rs.getString("name"), rs.getString("address"));
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
                System.out.println("customers " + ex);
                
            }
        }
        return res;
    }
    
    public static ResultSet getCustomers (int biggerThan) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, name, address FROM Customer WHERE id > " + biggerThan + " LIMIT 1;"); 
        } catch (SQLException e){
            System.out.println("getCustomers " + e);
        }
        return rs;
    }
    //Abfragen von allen Kunden, die von einem bestimmten Manager eingerfügt wurden
    public static ResultSet getAddedCustomers (int biggerThan, Manager manager) {
        ResultSet rs = null;
        int manager_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()){
                manager_id = rs.getInt("id");
                }            
            rs = stmt.executeQuery("SELECT id, name, address FROM Customer WHERE Manager_id = " + manager_id + " AND id > " + biggerThan + " LIMIT 1;");
        }
        catch (SQLException e){
            System.out.println("getAddedCustomers " + e);
        }
        return rs;
    }
    //Abfragen von allen Kunden, die von einem bestimmten Manager modifiziert wurden
    public static ResultSet getEditedCustomers (int biggerThan, Manager manager) {
        ResultSet rs = null;
        int manager_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()){
                manager_id = rs.getInt("id");
            }           
            rs = stmt.executeQuery("SELECT C.id, C.name, C.address FROM Customer C JOIN LastModification L ON C.id = L.Element_id WHERE L.Manager_id = " + manager_id 
                    + " AND L.type = " + DatabaseManagement.getCUSTOMER_TYPE() + " AND C.id > " + biggerThan + " LIMIT " + 1 + ";");
        }
        catch (SQLException e){
            System.out.println("getEditedCustomers " + e);
        }
        return rs;
    }    
    //Abfragen von einem bestimmten Kunden
    public static Customer getCustomer (String name) {
        Customer temp = new Customer();
        ResultSet rs = null;
        int customer_id = 0;
        try {
            stmt = con.createStatement();
            System.out.println(name);
            rs = stmt.executeQuery("SELECT * FROM Customer WHERE name = '" + DataPreparation.prepareString(name) + "';");
            if (rs.next()){
                customer_id = rs.getInt("id");
                System.out.println(customer_id);
                temp = new Customer(rs.getString("name"), rs.getString("address"));
                temp.setOfferNrs(getAll(customer_id, 1));
                temp.setOrderNrs(getAll(customer_id, 2));
            }
        }
        catch (SQLException e){
            System.out.println("getCustomer " + e);
        }
        return temp;
    }
    
    //Abfragen von Order_Nr und Offer_Nr eines Kunden
    public static ArrayList<String> getAll(int customer_id, int type) {
        ArrayList<String> temp = new ArrayList();
        ResultSet rs = null;
        String tempS = "OrderNr";
        String table = "Customer_Order";
        if (type == 1) {
            tempS = "OfferNr";
            table = "Customer_Offer";
        }
        try {
            int last_id = 0;
            rs = stmt.executeQuery("SELECT * FROM " + table + " WHERE Customer_id = " + customer_id + " LIMIT 1;");
            if (rs.next()) {
                last_id = rs.getInt("id");
                temp.add(rs.getString(tempS));
                System.out.println(tempS + " " + rs.getString(tempS));
                while (true) {
                    rs = stmt.executeQuery("SELECT * FROM " + table + " WHERE Customer_id = " + customer_id + " AND id > " + last_id + " LIMIT 1;");
                    if (rs.next()) {
                        last_id = rs.getInt("id");
                        temp.add(rs.getString(tempS));
                        System.out.println(tempS + " " + rs.getString(tempS));
                    }
                    else {
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("getALL for " + table + " " + e);
        }
        return temp;
    }
}
