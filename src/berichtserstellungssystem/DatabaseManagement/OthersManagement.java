/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.DatabaseManagement;

import berichtserstellungssystem.Resource.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Baraa
 */
public class OthersManagement extends DatabaseManagement{
    
    public static int insertProject(String name) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Project WHERE project = '" + name + "';");
            if (!rs.next()){
                stmt.executeUpdate("INSERT INTO Project (project) VALUES ('" + name + "')");
                return 1;
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("insertProject " + ex);
            return -1;
        }
        
    }
    
    public static int insertSurfaceCondition(String name) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM SurfaceCondition WHERE surfaceCondition = '" + name + "';");
            if (!rs.next()){
                stmt.executeUpdate("INSERT INTO SurfaceCondition (surfaceCondition) VALUES ('" + name + "')");
                return 1;
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("insertSurfaceCondition " + ex);
            return -1;
        }
        
    }
    
    public static int insertStageOfExamination(String name) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM StageOfExamination WHERE stageOfExamination = '" + name + "';");
            if (!rs.next()){
                stmt.executeUpdate("INSERT INTO StageOfExamination (stageOfExamination) VALUES ('" + name + "')");
                return 1;
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("insertStageOfExamination " + ex);
            return -1;
        }
        
    }
    
    public static int updateProject(String oldName, String name) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Project WHERE project = '" + name + "';");
            if (!rs.next()){
                stmt.executeUpdate("UPDATE Project SET project = '" + name + "' WHERE project = '" + oldName + "';");
                return 1;
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("updateProject " + ex);
            return -1;
        }
        
    }
    
    public static int updateSurfaceCondition(String oldName, String name) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM SurfaceCondition WHERE surfaceCondition = '" + name + "';");
            if (!rs.next()){
                stmt.executeUpdate("UPDATE SurfaceCondition SET surfaceCondition = '" + name + "' WHERE surfaceCondition = '" + oldName + "';");
                return 1;
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("updateSurfaceCondition " + ex);
            return -1;
        }
        
    }
    
    public static int updateStageOfExamination(String oldName, String name) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM StageOfExamination WHERE stageOfExamination = '" + name + "';");
            if (!rs.next()){
                stmt.executeUpdate("UPDATE StageOfExamination stageOfExamination = '" + name + "' WHERE stageOfExamination = '" + oldName + "';");
                return 1;
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("updateStageOfExamination " + ex);
            return -1;
        }
        
    }
    
   public static int deleteProject(String name) {
        ResultSet rs = null;
        int id;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Project WHERE project = '" + name + "';");
            if (rs.next()){
                id = rs.getInt("id");
                stmt.executeUpdate("DELETE FROM Project WHERE id = " + id + ";");
                return 1;
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("deleteProject " + ex);
            return -1;
        }
        
    }
    
    public static int deleteSurfaceCondition(String name) {
        ResultSet rs = null;
        int id;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM SurfaceCondition WHERE surfaceCondition = '" + name + "';");
            if (rs.next()){
                id = rs.getInt("id");
                stmt.executeUpdate("DELETE FROM SurfaceCondition WHERE id = " + id + ";");
                return 1;
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("deleteSurfaceCondition " + ex);
            return -1;
        }
        
    }
    
    public static int deleteStageOfExamination(String name) {
        ResultSet rs = null;
        int id;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM StageOfExamination WHERE stageOfExamination = '" + name + "';");
            if (rs.next()){
                id = rs.getInt("id");
                stmt.executeUpdate("DELETE FROM StageOfExamination WHERE id = " + id + ";");
                return 1;
            }
            else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("deleteStageOfExamination " + ex);
            return -1;
        }
        
    }
    
    
}
