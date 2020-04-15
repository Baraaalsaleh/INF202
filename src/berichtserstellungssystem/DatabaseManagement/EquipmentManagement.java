/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.DatabaseManagement;

import berichtserstellungssystem.*;
import berichtserstellungssystem.DatabaseManagement.DatabaseManagement;
import berichtserstellungssystem.Resource.Equipment;
import berichtserstellungssystem.Resource.MagneticEquipment;
import berichtserstellungssystem.Resource.Manager;
import berichtserstellungssystem.Resource.RadiographicEquipment;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Baraa
 */
public class EquipmentManagement extends DatabaseManagement{
    //Einfügen von Testmaschine Magnetic
    public static int insertMagnetic (MagneticEquipment equip, Manager manager){
        ResultSet rs;
        int manager_id = 0; 
        int equip_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + DataPreparation.prepareString(equip.getName()) + "';");
            if (rs.next() == false) {
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                    }
                stmt.executeUpdate("INSERT INTO Equipment (Manager_id, name, calibrationEndDate, type) VALUES (" + manager_id + ", '" + DataPreparation.prepareString(equip.getName()) 
                        + "', '" + Common.date_toString(equip.getCalibrationEndDate()) + "', " + DatabaseManagement.getMagnetic_type() + ");");
                rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + DataPreparation.prepareString(equip.getName()) + "';");
                if (rs.next()){
                    equip_id = rs.getInt("id");
                    stmt.executeUpdate("INSERT INTO MagneticEquipment (Equipment_id, poles_Distance, MPCarrier, MagTechnic, UVIntensity, lightDistance) VALUES ("
                        + equip_id + ", '" + equip.getPolesDistance() + "', '" + DataPreparation.prepareString(equip.getMPCarrier()) + "', '" + DataPreparation.prepareString(equip.getMagTechnic())
                        + "', '" + equip.getUVIntensity() + "', '" + equip.getLightDistance() + "');");
                    return 1;
                    }
                else {
                    con = DatabaseManagement.connect();
                    deleteEquipment(equip.getName());
                    return -1;
                }
            }
            else {
                return 0;
            }
        }
            catch (SQLException e) {
                System.out.println("insertMagnetic " + e);
                return -1;
            }
    }
    //Einfügen von Testmaschine Radiographic
    public static int insertRadiographic (RadiographicEquipment equip, Manager manager){
        ResultSet rs;
        int manager_id = 0; 
        int equip_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + DataPreparation.prepareString(equip.getName()) + "';");
            if (rs.next() == false) {
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                    }
                stmt.executeUpdate("INSERT INTO Equipment (Manager_id, name, calibrationEndDate, type) VALUES (" + manager_id + ", '" + DataPreparation.prepareString(equip.getName()) 
                        + "', '" + Common.date_toString(equip.getCalibrationEndDate()) + "', " + DatabaseManagement.getRadiographic_type() + ");");
                rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + equip.getName() + "';");
                if (rs.next()){
                    equip_id = rs.getInt("id");
                    }
                stmt.executeUpdate("INSERT INTO RadiographicEquipment (Equipment_id, ir192, se75, xRay, focalSpotSize, exposureTime, filmFocusDistance, pbScreens, filters) \n" +
                "VALUES (" + equip_id + ", " + equip.isIr192() + ", " + equip.isSe75() + ", " + equip.isxRay() + ", '" + DataPreparation.prepareString(equip.getFocalSpotSize())
                        + "', '" + DataPreparation.prepareString(equip.getExposureTime()) + "', '" + DataPreparation.prepareString(equip.getFilmFocusDistance()) + "', '" 
                        + DataPreparation.prepareString(equip.getPbScreens()) + "', '" + DataPreparation.prepareString(equip.getFilters()) + "');");
                return 1;
            }
            else {
                return 0;
            }
        }
            catch (SQLException e) {
                System.out.println("insertRadiographic " + e);
                con = DatabaseManagement.connect();
                deleteEquipment(equip.getName());
                return -1;
            }
    }
    //Löschen einer Testmaschine
    public static int deleteEquipment (String name){
        ResultSet rs;
        int equip_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + name + "';");
            if (rs.next()) {
                equip_id = rs.getInt("id");
                stmt.executeUpdate("DELETE FROM Equipment WHERE id = " + equip_id + ";");
                stmt.executeUpdate("DELETE FROM MangneticEquipment WHERE Equipment_id = " + equip_id + ";");
                stmt.executeUpdate("DELETE FROM RadiographicEquipment WHERE Equipment_id = " + equip_id + ";");                
                return 1;
            }
            else {
                return 0;
            }
        }
            catch (SQLException e) {
                System.out.println("deleteEquipment " + e);
                return -1;
            }
    }
    //Modifikation von Testmaschnineninformationen (magnetic) 
    public static int updateMagnetic (MagneticEquipment equip, Manager manager){
        ResultSet rs;
        int manager_id = 0; 
        int equip_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + DataPreparation.prepareString(equip.getName()) + "';");
            if (rs.next()) {
                equip_id = rs.getInt("id");
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                    }
                stmt.executeUpdate("UPDATE Equipment SET calibrationEndDate = '" + Common.date_toString(equip.getCalibrationEndDate()) + "' WHERE id = " + equip_id + ";");
                stmt.executeUpdate("UPDATE MagneticEquipment SET poles_Distance = '" + equip.getPolesDistance() + "', MPCarrier = '" + DataPreparation.prepareString(equip.getMPCarrier())
                        + "', MagTechnic = '" + DataPreparation.prepareString(equip.getMagTechnic()) + "', UVIntensity = '" + equip.getUVIntensity() + "', lightDistance = '" + equip.getLightDistance() + "' WHERE Equipment_id = " 
                        + equip_id + ";");
                
                rs = stmt.executeQuery("SELECT id FROM LastModification WHERE Element_id = " + equip_id + " AND type = " + DatabaseManagement.getEquipment_type() + ";");
                if (rs.next()){
                    int last_id = rs.getInt("id");
                    stmt.executeUpdate("DELETE FROM LastModification WHERE id = " + last_id + ";");
                    stmt.executeUpdate("INSERT INTO LastModification (Type, Manager_id, Element_id, date) VALUES (" + DatabaseManagement.getEquipment_type() + ", " + manager_id + ", " + equip_id 
                            + ", NOW();");
                }
                return 1;
            }
            else {
                return 0;
            }
        }
            catch (SQLException e) {
                System.out.println("updateMagnetic " + e);
                return -1;
            }
    }
    //Modifikation von Testmaschnineninformationen (radiographic) 
    public static int updateRadiographic (RadiographicEquipment equip, Manager manager){
        ResultSet rs;
        int manager_id = 0; 
        int equip_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + DataPreparation.prepareString(equip.getName()) + "';");
            if (rs.next()) {
                equip_id = rs.getInt("id");
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                    }
                stmt.executeUpdate("UPDATE Equipment SET calibrationEndDate = '" + Common.date_toString(equip.getCalibrationEndDate()) + "' WHERE id = " + equip_id + ";");
                String query = "UPDATE RadiographicEquipment SET ir192 = " + equip.isIr192() + ", se75 = " + equip.isSe75() + ", xRay = " + equip.isxRay() +
                        ", focalSpotSize = '" + DataPreparation.prepareString(equip.getFocalSpotSize()) + "', exposureTime = '" + DataPreparation.prepareString(equip.getExposureTime())
                        + "', filmFocusDistance = '" + DataPreparation.prepareString(equip.getFilmFocusDistance()) + "', pbScreens = '" + DataPreparation.prepareString(equip.getPbScreens()) 
                        + "', filters = '" + DataPreparation.prepareString(equip.getFilters()) + "' WHERE Equipment_id = " + equip_id + ";";
                System.out.println(query);
                stmt.executeUpdate(query);
                rs = stmt.executeQuery("SELECT id FROM LastModification WHERE Element_id = " + equip_id + " AND type = " + DatabaseManagement.getEquipment_type() + ";");
                if (rs.next()){
                    int last_id = rs.getInt("id");
                    stmt.executeUpdate("DELETE FROM LastModification WHERE id = " + last_id + ";");
                }
                stmt.executeUpdate("INSERT INTO LastModification (Type, Manager_id, Element_id, date) VALUES (" + DatabaseManagement.getEquipment_type() + ", " + manager_id 
                            + ", " + equip_id + ", NOW());");
                return 1;
            }
            else {
                return 0;
            }
        }
            catch (SQLException e) {
                System.out.println("updateRadiographic " + e);
                return -1;
            }
    }  
    
    public static ArrayList<Equipment> equipments (int start, int limit, int type, Manager manager) {
        ArrayList<Equipment> res = new ArrayList();
        Equipment temp;
        ResultSet rs;
        int i = 0;
        while (i < limit) {
            try {
                if (type == 1) {
                    rs = getEquipments(i);
                }
                else if (type == 2) {
                    rs = getAddedEquipments(i, manager);
                }
                else {
                    rs = getEditedEquipments(i, manager);
                }
                
                if (rs.next()) {
                    temp = new Equipment(rs.getString("name"), rs.getDate("calibrationEndDate"));
                    temp.setType(rs.getInt("type"));
                    i += rs.getInt("id");
                    System.out.println(temp.getName());
                    res.add(temp);
                }
                else {
                    break;
                }
            } catch (SQLException ex) {
                System.out.println("equipments " + ex);
                
            }
        }
        return res;
    }
    
    //Abfragen von allen Testmaschinen
    public static ResultSet getEquipments (int biggerThan) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id, name, calibrationEndDate, type FROM Equipment WHERE id > " + biggerThan + " LIMIT 1;");
            return rs;
        }
        catch (SQLException e){
            System.out.println("getEquipments " + e);
            return rs;
        }
    }
    //Abfragen von allen Testmaschinen, die von einem bestimmten Manager eingefügt wurden
    public static ResultSet getAddedEquipments (int biggerThan, Manager manager) {
        ResultSet rs = null;
        int manager_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()){
                manager_id = rs.getInt("id");
                }            
            rs = stmt.executeQuery("SELECT id, name, calibrationEndDate, type FROM Equipment WHERE Manager_id = " + manager_id + " AND  id > " + biggerThan + " LIMIT 1;");
            return rs;
        }
        catch (SQLException e){
            System.out.println("getAddedEquipments " + e);
            return rs;
        }
    }    
    //Abfragen von allen Testmaschinen, die von einem bestimmten Manager modifiziert wurden
    public static ResultSet getEditedEquipments (int biggerThan, Manager manager) {
        ResultSet rs = null;
        int manager_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()){
                manager_id = rs.getInt("id");
                }            
            rs = stmt.executeQuery("SELECT E.id, E.name, E.calibrationEndDate, E.type FROM Equipment E JOIN LastModification L ON E.id = L.Element_id WHERE L.Manager_id = " + manager_id 
                    + " AND L.type = " + DatabaseManagement.getEquipment_type() + " AND E.id > " + biggerThan + " LIMIT 1;");
                return rs;
        }
        catch (SQLException e){
            System.out.println("getEditiedEquipments " + e);
            return rs;
        }
    }        
    //Abfragen von Testmaschineninformationen (magnetic)
    public static ResultSet getMagneticEquipment (String name){
        ResultSet rs = null;
        int equip_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + DataPreparation.prepareString(name) + "';");
                if (rs.next()){
                    equip_id = rs.getInt("id");
                    System.out.println("I got it " +  equip_id);
                    }         
            rs = stmt.executeQuery("SELECT E.id, E.name, E.calibrationEndDate, M.poles_Distance, M.MPCarrier, M.MagTechnic, M.UVIntensity, M.lightDistance FROM"
                    + " Equipment E JOIN MagneticEquipment M ON E.id = M.Equipment_id WHERE E.id = " + equip_id + ";");
            if (rs.next()) {
                return rs;
            }
                return rs;
        }
        catch (SQLException e){
            System.out.println("getMagneticEquipment " + e);
            return rs;
        }
    }
    //Abfragen von Testmaschineninformationen (radiographic)
    public static ResultSet getRadiographicEquipment (String name){
        ResultSet rs = null;
        int equip_id = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + DataPreparation.prepareString(name) + "';");
                if (rs.next()){
                    equip_id = rs.getInt("id");
                    }            
            rs = stmt.executeQuery("SELECT E.id, E.name, E.calibrationEndDate, R.ir192, R.se75, R.xRay, R.focalSpotSize, R.exposureTime, R.filmFocusDistance, R.pbScreens, R.filters"
                    + " FROM Equipment E JOIN RadiographicEquipment R ON E.id = R.Equipment_id WHERE E.id = " + equip_id + ";");
            if (rs.next()) {
                return rs;
            }   
        }
        catch (SQLException e){
            System.out.println("getRadiographicEquipment " + e);
            return rs;
        }
        return rs;
    }
}
