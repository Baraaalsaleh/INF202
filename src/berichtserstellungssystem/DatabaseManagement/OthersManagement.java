/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.DatabaseManagement;

import berichtserstellungssystem.Resource.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
                int id = 0;
                rs = stmt.executeQuery("SELECT MAX(id) as id FROM project;");
                if (rs.next()) {
                    id = rs.getInt("id");
                }
                stmt.executeUpdate("INSERT INTO Project (id, project) VALUES (" + (id+1) + ", '" + name + "')");
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
    
    public static boolean checkProjectName(String name) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM Project WHERE project = '" + name + "';");
            return !rs.next();
        } catch (SQLException ex) {
            System.out.println("insertProject " + ex);
            return false;
        }
    }
    
    public static int insertSurfaceCondition(String name) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM SurfaceCondition WHERE surfaceCondition = '" + name + "';");
            if (!rs.next()){
                int id = 0;
                rs = stmt.executeQuery("SELECT MAX(id) as id FROM SurfaceCondition;");
                if (rs.next()) {
                    id = rs.getInt("id");
                }
                stmt.executeUpdate("INSERT INTO SurfaceCondition (id, surfaceCondition) VALUES (" + (id+1) + ", '" + name + "')");
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
    
    public static boolean checkSurfaceCondition(String name) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM SurfaceCondition WHERE surfaceCondition = '" + name + "';");
            return !rs.next();
        } catch (SQLException ex) {
            System.out.println("insertSurfaceCondition " + ex);
            return false;
        }
    }
    
    public static int insertStageOfExamination(String name) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM StageOfExamination WHERE stageOfExamination = '" + name + "';");
            if (!rs.next()){
                int id = 0;
                rs = stmt.executeQuery("SELECT MAX(id) as id FROM StageOfExamination;");
                if (rs.next()) {
                    id = rs.getInt("id");
                }
                stmt.executeUpdate("INSERT INTO StageOfExamination (id, stageOfExamination) VALUES (" + (id+1) + ", '" + name + "')");
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
    
    public static boolean checkStageOfExamination(String name) {
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT id FROM StageOfExamination WHERE stageOfExamination = '" + name + "';");
            return !rs.next();
        } catch (SQLException ex) {
            System.out.println("insertStageOfExamination " + ex);
            return false;
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
    
    public static ArrayList<String> projects (int start, int limit) {
        ArrayList<String> res = new ArrayList();
        ResultSet rs = null;
        int count = 0;
        String temp = "";
        try {
            stmt = con.createStatement();
            while (count < limit) {
                rs = stmt.executeQuery("SELECT * FROM Project WHERE id > " + start + ";");
                if (rs.next()) {
                    temp = rs.getString("project");
                    start = rs.getInt("id");
                    res.add(temp);
                }
                else {
                    break;
                }
                count++;
            }
        } catch (SQLException e) {
            System.out.println("projects " + e);
        }
        return res;
    }
    
    public static ArrayList<String> surfaceConditions (int start, int limit) {
        ArrayList<String> res = new ArrayList();
        ResultSet rs = null;
        int count = 0;
        String temp = "";
        try {
            stmt = con.createStatement();
            while (count < limit) {
                rs = stmt.executeQuery("SELECT * FROM SurfaceCondition WHERE id > " + start + ";");
                if (rs.next()) {
                    temp = rs.getString("surfaceCondition");
                    start = rs.getInt("id");
                    res.add(temp);
                }
                else {
                    break;
                }
                count++;
            }
        } catch (SQLException e) {
            System.out.println("surfaceConditions " + e);
        }
        return res;
    }
    
    public static ArrayList<String> stageOfExaminations (int start, int limit) {
        ArrayList<String> res = new ArrayList();
        ResultSet rs = null;
        int count = 0;
        String temp = "";
        try {
            stmt = con.createStatement();
            while (count < limit) {
                rs = stmt.executeQuery("SELECT * FROM StageOfExamination WHERE id > " + start + ";");
                if (rs.next()) {
                    temp = rs.getString("stageOfExamination");
                    start = rs.getInt("id");
                    res.add(temp);
                }
                else {
                    break;
                }
                count++;
            }
        } catch (SQLException e) {
            System.out.println("stageOfExaminations " + e);
        }
        return res;
    }
}
