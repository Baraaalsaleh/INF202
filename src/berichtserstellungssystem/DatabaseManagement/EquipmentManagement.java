//Baraa Alsaleh, 19050800
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
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + Security.prepareString(equip.getName()) + "';");
            if (rs.next() == false) {
                int id = 0;
                rs = stmt.executeQuery("SELECT MAX(id) as id FROM Equipment;");
                if (rs.next()) {
                    id = rs.getInt("id");
                }
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                    }
                stmt.executeUpdate("INSERT INTO Equipment (id, Manager_id, name, calibrationEndDate, type) VALUES (" + (id+1) + ", " + manager_id + ", '" + Security.prepareString(equip.getName()) 
                        + "', '" + Common.date_toString(equip.getCalibrationEndDate()) + "', " + DatabaseManagement.getMAGNETIC_TYPE() + ");");
                rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + Security.prepareString(equip.getName()) + "';");
                if (rs.next()){
                    equip_id = rs.getInt("id");
                    id = 0;
                    rs = stmt.executeQuery("SELECT MAX(id) as id FROM MagneticEquipment;");
                    if (rs.next()) {
                        id = rs.getInt("id");
                    }
                    stmt.executeUpdate("INSERT INTO MagneticEquipment (id, Equipment_id, poles_Distance, MPCarrier, MagTechnic, UVIntensity, lightDistance) VALUES (" + (id+1) + ", "
                        + equip_id + ", '" + equip.getPolesDistance() + "', '" + Security.prepareString(equip.getMpCarrier()) + "', '" + Security.prepareString(equip.getMagTechnic())
                        + "', '" + equip.getUvIntensity() + "', '" + equip.getLightDistance() + "');");
                    return 1;
                    }
                else {
                    //Falls das Einfügen nicht erfolgreich war, wird die Verbindung an die Datenbank erneut und die Daten dieser Testmaschine komplett gelöscht
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
                //Falls das Einfügen nicht komplett funktionierte, wird die Verbindung an die Datenbank erneut und die Daten dieser Testmaschine komplett gelöscht
                con = DatabaseManagement.connect();
                deleteEquipment(equip.getName());
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
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + Security.prepareString(equip.getName()) + "';");
            if (rs.next() == false) {
                int id = 0;
                rs = stmt.executeQuery("SELECT MAX(id) as id FROM Equipment;");
                if (rs.next()) {
                    id = rs.getInt("id");
                }
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                    }
                stmt.executeUpdate("INSERT INTO Equipment (id, Manager_id, name, calibrationEndDate, type) VALUES (" + (id+1) + ", " + manager_id + ", '" + Security.prepareString(equip.getName()) 
                        + "', '" + Common.date_toString(equip.getCalibrationEndDate()) + "', " + DatabaseManagement.getRADIOGRAPHIC_TYPE() + ");");
                rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + equip.getName() + "';");
                if (rs.next()){
                    equip_id = rs.getInt("id");
                    }
                id = 0;
                rs = stmt.executeQuery("SELECT MAX(id) as id FROM RadiographicEquipment;");
                if (rs.next()) {
                    id = rs.getInt("id");
                }
                stmt.executeUpdate("INSERT INTO RadiographicEquipment (id, Equipment_id, ir192, se75, xRay, focalSpotSize, exposureTime, filmFocusDistance, pbScreens, filters) \n" +
                "VALUES (" + (id+1) + ", " + equip_id + ", " + equip.isIr192() + ", " + equip.isSe75() + ", " + equip.isxRay() + ", '" + Security.prepareString(equip.getFocalSpotSize())
                        + "', '" + Security.prepareString(equip.getExposureTime()) + "', '" + Security.prepareString(equip.getFilmFocusDistance()) + "', '" 
                        + Security.prepareString(equip.getPbScreens()) + "', '" + Security.prepareString(equip.getFilters()) + "');");
                return 1;
            }
            else {
                return 0;
            }
        }
            catch (SQLException e) {
                System.out.println("insertRadiographic " + e);
                //Falls das Einfügen nicht komplett funktionierte, wird die Verbindung an die Datenbank erneut und die Daten dieser Testmaschine komplett gelöscht
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
                stmt.executeUpdate("DELETE FROM MagneticEquipment  WHERE Equipment_id = " + equip_id + ";");
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
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + Security.prepareString(equip.getName()) + "';");
            if (rs.next()) {
                equip_id = rs.getInt("id");
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                    }
                stmt.executeUpdate("UPDATE Equipment SET calibrationEndDate = '" + Common.date_toString(equip.getCalibrationEndDate()) + "' WHERE id = " + equip_id + ";");
                stmt.executeUpdate("UPDATE MagneticEquipment SET poles_Distance = '" + equip.getPolesDistance() + "', MPCarrier = '" + Security.prepareString(equip.getMpCarrier())
                        + "', MagTechnic = '" + Security.prepareString(equip.getMagTechnic()) + "', UVIntensity = '" + equip.getUvIntensity() + "', lightDistance = '" + equip.getLightDistance() + "' WHERE Equipment_id = " 
                        + equip_id + ";");
                rs = stmt.executeQuery("SELECT id FROM LastModification WHERE Element_id = " + equip_id + " AND type = " + DatabaseManagement.getEQUIPMENT_TYPE() + ";");
                if (rs.next()){
                    int last_id = rs.getInt("id");
                    stmt.executeUpdate("DELETE FROM LastModification WHERE id = " + last_id + ";");
                }
                int id = 0;
                rs = stmt.executeQuery("SELECT MAX(id) as id FROM LastModification;");
                if (rs.next()) {
                    id = rs.getInt("id");
                }
                stmt.executeUpdate("INSERT INTO LastModification (id, Type, Manager_id, Element_id, date) VALUES (" + (id+1) + ", " + DatabaseManagement.getEQUIPMENT_TYPE() + ", " + manager_id + ", " + equip_id 
                            + ", NOW());");
                
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
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + Security.prepareString(equip.getName()) + "';");
            if (rs.next()) {
                equip_id = rs.getInt("id");
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                    }
                stmt.executeUpdate("UPDATE Equipment SET calibrationEndDate = '" + Common.date_toString(equip.getCalibrationEndDate()) + "' WHERE id = " + equip_id + ";");
                String query = "UPDATE RadiographicEquipment SET ir192 = " + equip.isIr192() + ", se75 = " + equip.isSe75() + ", xRay = " + equip.isxRay() +
                        ", focalSpotSize = '" + Security.prepareString(equip.getFocalSpotSize()) + "', exposureTime = '" + Security.prepareString(equip.getExposureTime())
                        + "', filmFocusDistance = '" + Security.prepareString(equip.getFilmFocusDistance()) + "', pbScreens = '" + Security.prepareString(equip.getPbScreens()) 
                        + "', filters = '" + Security.prepareString(equip.getFilters()) + "' WHERE Equipment_id = " + equip_id + ";";
                System.out.println(query);
                stmt.executeUpdate(query);
                rs = stmt.executeQuery("SELECT id FROM LastModification WHERE Element_id = " + equip_id + " AND type = " + DatabaseManagement.getEQUIPMENT_TYPE() + ";");
                if (rs.next()){
                    int last_id = rs.getInt("id");
                    stmt.executeUpdate("DELETE FROM LastModification WHERE id = " + last_id + ";");
                }
                int id = 0;
                rs = stmt.executeQuery("SELECT MAX(id) as id FROM LastModification;");
                if (rs.next()) {
                    id = rs.getInt("id");
                }
                stmt.executeUpdate("INSERT INTO LastModification (id, Type, Manager_id, Element_id, date) VALUES (" + (id+1) + ", " + DatabaseManagement.getEQUIPMENT_TYPE() + ", " + manager_id 
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
        int count = 0;
        int i = start;
        while (count < limit) {
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
                    int id = rs.getInt("id");
                    Manager man = null;
                    if (type == 1 || type == 3) {
                        int manager_id = rs.getInt("Manager_id");
                        man = new Manager(PersonManagement.getManagerById(manager_id));
                        temp.setAdder(man);
                    }
                    if (type == 1 || type == 2) {
                        temp.setLastChange(OthersManagement.getLastModiInfos(DatabaseManagement.getEQUIPMENT_TYPE(), id));
                    }
                    i += (id-i);
                    res.add(temp);
                    count++;
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
            rs = stmt.executeQuery("SELECT id, name, calibrationEndDate, type, Manager_id FROM Equipment WHERE id > " + biggerThan + " LIMIT 1;");
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
            rs = stmt.executeQuery("SELECT E.id, E.name, E.calibrationEndDate, E.type, E.Manager_id FROM Equipment E JOIN LastModification L ON E.id = L.Element_id WHERE (L.Manager_id = " + manager_id 
                    + " AND L.type = " + DatabaseManagement.getEQUIPMENT_TYPE() + " AND E.id > " + biggerThan + ") LIMIT 1;");
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
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + Security.prepareString(name) + "';");
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
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + Security.prepareString(name) + "';");
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
