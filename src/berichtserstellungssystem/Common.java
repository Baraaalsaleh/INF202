//Baraa Alsaleh, 19050800
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
    //Diese Funktion nimmt die Jahr, Monat und Taginformationen eines java.util.Date und gibt sie als ein String der Form "JJJJ-MM-TT"
    public static String date_toString(Date date){
        if (date != null) {
            return ((date.getYear()+1900) + "-" + (date.getMonth()+1) + "-" + date.getDate());
        }
        return "";
    }
    
    //Diese Funktion nimmt die Jahr, Monat und Taginformationen eines java.util.Date und gibt sie als ein String der Form "TT-MM-JJJJ"
    public static String date_toStringReverse(Date date, String s){
        if (date != null) {
            return (date.getDate()+ s + (date.getMonth()+1) + s + (date.getYear()+1900));
        }
        return "";
    }
    
    //Diese Funktion testet die Gültigkeit eines Datum
    private static Date makeItDate (String[] temp) {
        Date date = new Date();
        try {
                int day = Integer.parseInt(temp[0]);
                int month = Integer.parseInt(temp[1]) - 1;
                int year = Integer.parseInt(temp[2])-1900;
                if (year > 0 && year < 200) {
                    if (month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11){
                        if (day > 0 && day < 32) {
                            date.setDate(day);
                            date.setMonth(month);
                            date.setYear(year);
                            return date;
                        }
                        else {
                            return null;
                        }
                    }
                    else if (month == 3 || month == 5 || month == 8 || month == 10) {
                        if (day > 0 && day < 31) {
                            date.setDate(day);
                            date.setMonth(month);
                            date.setYear(year);
                            return date;
                        }
                        else {
                            return null;
                        }
                    }
                    else if (month == 1) {
                        if (year%4 == 0) {
                            if (day > 0 && day < 30) {
                            date.setDate(day);
                            date.setMonth(month);
                            date.setYear(year);
                            return date;
                            }
                            else {
                                return null;
                            }
                        }
                        else {
                            if (day > 0 && day < 29) {
                                date.setDate(day);
                                date.setMonth(month);
                                date.setYear(year);
                                return date;
                                }
                            else {
                                return null;
                            }
                        }
                    }
                    else {
                        return null;
                    }
                }
                else {
                    return null;
                }
        }
        catch (NumberFormatException e) {
            return null;
        }
    }
    
    //Diese Funktion versucht ein java.util.Date aus einem String der Form "TT-MM-JJJJ" oder "TT/MM/JJJJ" zu machen
    public static Date string_toDate(String s){
        Date date = new Date();
        s = s.replace(".", "-");
        s = s.replace("/", "-");
        s = s.replace("\\", "-");
        s = s.replace(",", "-");
        String[] temp = s.split("-");
        if (temp.length == 3) {
            return makeItDate(temp);
        }
        else {
            return null;
        }
    }
    
    //Schreiben von Daten in eine Datei
    public static void writeUsingBufferedWriter(String data, int noOfLines) {
        File file = new File("Data/BufferedWriter.txt");
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

    //Lesen von Daten aus einer Datei
    public static String readUsingBufferReader (){
        String data = "";
        try {
            File file = new File("Data/BufferedWriter.txt"); 
            BufferedReader br = new BufferedReader(new FileReader(file)); 
            String st; 
            while ((st = br.readLine()) != null) 
            data += st;
            System.out.println(data);
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
        catch (IOException e) {
            System.out.println(e);
        }
        return data;
    }
    
    //Diese Methode macht den ersten Buchstaben Groß
    public static String makeFirstLetterCapital (String s) {
        String a = s.charAt(0) + " ";
        a = a.toUpperCase();
        char[] temp = s.toCharArray();
        temp[0] = a.charAt(0);
        s = "";
        for (char c : temp){
            s += c;
        }
        return s;
    }
    
    
}
