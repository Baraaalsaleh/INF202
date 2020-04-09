/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem;

import berichtserstellungssystem.DatabaseManagement.PersonManagement;
import berichtserstellungssystem.Resource.*;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Baraa
 */
public class Common {
    
    static public String date_toString(Date date){
        return (date.getYear() + "-" + date.getMonth() + "-" + date.getDay());
    }
    

    
}
