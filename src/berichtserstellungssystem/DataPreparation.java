/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem;

/**
 *
 * @author Baraa
 */
public class DataPreparation {
    public static String prepareString (String string) {
        String s = "\\" + "\\" + "'";
        String s2 = "\\" + "\\" + "\"";
        String temp = string.replaceAll("'", s);
        temp = temp.replaceAll("\"", s2);
        return temp;
    }
}
