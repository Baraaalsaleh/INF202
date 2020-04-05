/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Baraa
 */
public class EquipmentManagement extends DatabaseManagement{
    //Einfügen von Testmaschine Magnetic
    public int insertMagnetic (MagneticEquipment equip, Manager manager){
        Connection con = this.connect();
        ResultSet rs;
        int manager_id = 0; 
        int equip_id = 0;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + equip.getName() + "';");
            if (rs.next() == false) {
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                    }
                stmt.executeUpdate("INSERT INTO Equipment (Manager_id, name, type) VALUES (" + manager_id + ", '" + equip.getName() + "', " + this.getMagnetic_type() + ");");
                rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + equip.getName() + "';");
                if (rs.next()){
                    equip_id = rs.getInt("id");
                    }
                stmt.executeUpdate("INSERT INTO MagneticEquipment (Equipment_id, poles_Distance, MPCarrier, MagTechnic, UVIntensity, lightDistance) VALUES ("
                        + equip_id + ", " + equip.getPolesDistance() + ", '" + equip.getMPCarrier() + "', '" + equip.getMagTechnic() + "', " + equip.getUVIntensity() 
                        + ", " + equip.getLightDistance() + ");");
                return 1;
            }
            else {
                return 0;
            }
        }
            catch (SQLException e) {
                return -1;
            }
    }
    //Einfügen von Testmaschine Radiographic
    public int insertRadiographic (RadiographicEquipment equip, Manager manager){
        Connection con = this.connect();
        ResultSet rs;
        int manager_id = 0; 
        int equip_id = 0;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + equip.getName() + "';");
            if (rs.next() == false) {
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                    }
                stmt.executeUpdate("INSERT INTO Equipment (Manager_id, name, type) VALUES (" + manager_id + ", '" + equip.getName() + "', " + this.getRadiographic_type() + ");");
                rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + equip.getName() + "';");
                if (rs.next()){
                    equip_id = rs.getInt("id");
                    }
                stmt.executeUpdate("INSERT INTO RadiographicEquipment (Equipment_id, ir192, se75, xRay, focalSpotSize, exposureTime, filmFocusDistance, pbScreens, filters) \n" +
                "VALUES (" + equip_id + ", " + equip.isIr192() + ", " + equip.isSe75() + ", " + equip.isxRay() + ", '" + equip.getFocalSpotSize() + "', '" + equip.getExposureTime() + 
                "', '" + equip.getFilmFocusDistance() + "', '" + equip.getPbScreens() + "', '" + equip.getFilters() + "');");
                return 1;
            }
            else {
                return 0;
            }
        }
            catch (SQLException e) {
                return -1;
            }
    }
    //Löschen einer Testmaschine
    public int deleteEquipment (Equipment equip){
        Connection con = this.connect();
        ResultSet rs;
        int equip_id = 0;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + equip.getName() + "';");
            if (rs.next()) {
                rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + equip.getName() + "';");
                if (rs.next()){
                    equip_id = rs.getInt("id");
                    }
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
                return -1;
            }
    }
    //Modifikation von Testmaschnineninformationen (magnetic) 
    public int updateMagnetic (MagneticEquipment equip, Manager manager){
        Connection con = this.connect();
        ResultSet rs;
        int manager_id = 0; 
        int equip_id = 0;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + equip.getName() + "';");
            if (rs.next()) {
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                    }
                rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + equip.getName() + "';");
                if (rs.next()){
                    equip_id = rs.getInt("id");
                    }
                stmt.executeUpdate("UPDATE MagneticEquipment SET poles_Distance = " + equip.getPolesDistance() + ", MPCarrier = '" + equip.getMPCarrier() + "', MagTechnic = '" + 
                        equip.getMagTechnic() + "', UVIntensity = " + equip.getUVIntensity() + ", lightDistance = " + equip.getLightDistance() + " WHERE Equipment_id = " 
                        + equip_id + ";");
                
                rs = stmt.executeQuery("SELECT id FROM LastModification WHERE Element_id = " + equip_id + " AND type = " + this.getEquipment_type() + ";");
                if (rs.next()){
                    int last_id = rs.getInt("id");
                    stmt.executeUpdate("DELETE FROM LastModification WHERE id = " + last_id + ";");
                    stmt.executeUpdate("INSERT INTO LastModification (Type, Manager_id, Element_id, date) VALUES (" + this.getEquipment_type() + ", " + manager_id + ", " + equip_id 
                            + ", NOW();");
                }
                return 1;
            }
            else {
                return 0;
            }
        }
            catch (SQLException e) {
                return -1;
            }
    }
    //Modifikation von Testmaschnineninformationen (radiographic) 
    public int updateRadiographic (RadiographicEquipment equip, Manager manager){
        Connection con = this.connect();
        ResultSet rs;
        int manager_id = 0; 
        int equip_id = 0;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + equip.getName() + "';");
            if (rs.next()) {
                rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
                if (rs.next()){
                    manager_id = rs.getInt("id");
                    }
                rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + equip.getName() + "';");
                if (rs.next()){
                    equip_id = rs.getInt("id");
                    }
                stmt.executeUpdate("UPDATE RadiographicEquipment SET ir192 = " + equip.isIr192() + ", se75 = " + equip.isSe75() + ", xRay = " + equip.isxRay() +
                        ", focalSpotSize = '" + equip.getFocalSpotSize() + "', exposureTime = '" + equip.getExposureTime() + "', filmFocusDistance = '" 
                        + equip.getFilmFocusDistance() +
                        "', pbScreens = '" + equip.getPbScreens() + "', filters = '" + equip.getFilters() + " WHERE Equipment_id = " + equip_id + ";");
                rs = stmt.executeQuery("SELECT id FROM LastModification WHERE Element_id = " + equip_id + " AND type = " + this.getEquipment_type() + ";");
                if (rs.next()){
                    int last_id = rs.getInt("id");
                    stmt.executeUpdate("DELETE FROM LastModification WHERE id = " + last_id + ";");
                    stmt.executeUpdate("INSERT INTO LastModification (Type, Manager_id, Element_id, date) VALUES (" + this.getEquipment_type() + ", " + manager_id 
                            + ", " + equip_id + ", NOW();");
                }
                return 1;
            }
            else {
                return 0;
            }
        }
            catch (SQLException e) {
                return -1;
            }
    }  
    //Abfragen von allen Testmaschinen
    public ResultSet getEquipments (int limit) {
        Connection con = this.connect();
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT name, type FROM Equipment LIMIT " + limit + ";");
            if (rs.next()) {
                return rs;
            }
        }
        catch (SQLException e){
            return rs;
        }
        return rs;
    }
    //Abfragen von allen Testmaschinen, die von einem bestimmten Manager eingefügt wurden
    public ResultSet getAddedEquipments (int limit, Manager manager) {
        Connection con = this.connect();
        ResultSet rs = null;
        int manager_id = 0;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()){
                manager_id = rs.getInt("id");
                }            
            rs = stmt.executeQuery("SELECT name, type FROM Equipment WHERE Manager_id = " + manager_id + " LIMIT " + limit + ";");
            if (rs.next()) {
                return rs;
            }
        }
        catch (SQLException e){
            return rs;
        }
        return rs;
    }    
    //Abfragen von allen Testmaschinen, die von einem bestimmten Manager modifiziert wurden
    public ResultSet getEditedEquipments (int limit, Manager manager) {
        Connection con = this.connect();
        ResultSet rs = null;
        int manager_id = 0;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Person WHERE PersonalNr = " + manager.getPersonalNr() + ";");
            if (rs.next()){
                manager_id = rs.getInt("id");
                }            
            rs = stmt.executeQuery("SELECT E.name, E.type FROM Equipment E JOIN LastModification L ON E.id = L.Element_id WHERE L.Manager_id = " + manager_id 
                    + " AND L.type = " + this.getEquipment_type() + " LIMIT " + limit + ";");
            if (rs.next()) {
                return rs;
            }
        }
        catch (SQLException e){
            return rs;
        }
        return rs;
    }        
    //Abfragen von Testmaschineninformationen (magnetic)
    public ResultSet getMagneticEquipment (String name){
        Connection con = this.connect();
        ResultSet rs = null;
        int equip_id = 0;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + name + "';");
                if (rs.next()){
                    equip_id = rs.getInt("id");
                    }            
            rs = stmt.executeQuery("SELECT E.name, M.poles_Distance, M.MPCarrier, M.MagTechnic, M.UVIntensity, M.lightDistance, P.name as adder_name, P.lastname as adder_lastname, "
                    + "Per.name as changer_name, Per.lastname as changer_lastname, L.date FROM Equipment E JOIN MagneticEquipment M ON E.id = M.Equipment_id JOIN Person P ON "
                    + "E.Manager_id = P.id JOIN LastModification L ON L.Element_id = E.id JOIN Person Per ON Per.id = L.Manager_id WHERE L.type = " + this.getEquipment_type() 
                    + " AND E.id = " + equip_id + " AND E.type = " + this.getMagnetic_type() + ";");
            if (rs.next()) {
                return rs;
            }   
        }
        catch (SQLException e){
            return rs;
        }
        return rs;
    }
    //Abfragen von Testmaschineninformationen (radiographic)
    public ResultSet getRadiographicEquipment (String name){
        Connection con = this.connect();
        ResultSet rs = null;
        int equip_id = 0;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Equipment WHERE name = '" + name + "';");
                if (rs.next()){
                    equip_id = rs.getInt("id");
                    }            
            rs = stmt.executeQuery("SELECT E.name, R.ir192, R.se75, R.xRay, R.focalSpotSize, R.exposureTime, R.filmFocusDistance, R.pbScreens, R.filters, P.name as adder_name, "
                    + "P.lastname as adder_lastname, Per.name as changer_name, Per.lastname as changer_lastname, L.date FROM Equipment E JOIN RadiographicEquipment R ON"
                    + " E.id = R.Equipment_id JOIN Person P ON E.Manager_id = P.id JOIN LastModification L ON L.Element_id = E.id JOIN Person Per ON Per.id = L.Manager_id "
                    + "WHERE L.type = " + this.getEquipment_type() + " AND E.id = " + equip_id + " AND E.type = " + this.getRadiographic_type() + ";");
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
