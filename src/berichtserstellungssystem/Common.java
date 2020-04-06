/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem;

import java.util.Date;

/**
 *
 * @author Baraa
 */
public class Common {
    
    public String date_toString(Date date){
        return (date.getYear() + "-" + date.getMonth() + "-" + date.getDay());
    }    
    
}
