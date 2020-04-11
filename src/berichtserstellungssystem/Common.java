/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem;

import berichtserstellungssystem.DatabaseManagement.PersonManagement;
import berichtserstellungssystem.Resource.*;
import java.io.*;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Baraa
 */
public class Common {
    
    public static String date_toString(Date date){
        return ((date.getYear()+1900) + "-" + (date.getMonth()+1) + "-" + date.getDate());
    }
    
    public static void writeUsingBufferedWriter(String data, int noOfLines) {
        File file = new File("C:/Users/Baraa/Desktop/BufferedWriter.txt");
        FileWriter fr = null;
        BufferedWriter br = null;
        String dataWithNewLine=data+System.getProperty("line.separator");
        try{
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            for(int i = noOfLines; i>0; i--){
                br.write(dataWithNewLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readUsingBufferReader () throws FileNotFoundException, IOException{
        File file = new File("C:/Users/Baraa/Desktop/BufferedWriter.txt"); 
        BufferedReader br = new BufferedReader(new FileReader(file)); 
        String st; 
        String data = "";
        while ((st = br.readLine()) != null) 
        data += st;
        
        return data;
        }
    
}
