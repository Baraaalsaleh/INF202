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
public class Verification {
    final private static int nameLength = 20;
    final private static int passwordLength = 20;    
    
    
    public static boolean verifyName (String string) {
        if (string.length() <= nameLength && string.length() >= 3){
            if (string.contains("/") || string.contains("*") || string.contains("+") || string.contains("\\") || string.contains("%") || string.contains("$") || string.contains("'") || string.contains("!") || string.contains("@") || string.contains("#") 
            || string.contains("^") || string.contains("&") || string.contains("(") || string.contains(")") || string.contains("_") || string.contains("=") || string.contains("-") || string.contains(";") || string.contains("?") || string.contains(":") ||
            string.contains("\"") || string.contains(".") || string.contains("<") || string.contains(">") || string.contains("[") || string.contains("]") || string.contains("{") || string.contains("}") || string.contains("~") || string.contains("|") || 
            string.contains("0") || string.contains("1") || string.contains("2") || string.contains("3") || string.contains("4") || string.contains("5") || string.contains("6") || string.contains("7") || string.contains("8") || string.contains("9")) {
                System.out.println("Wrong Name and Lastname");
                return false;
            }
            else {
                return true;
            }
        }
        else {
            System.out.println("Wrong Name and Lastname");
            return false;
        }
    }
    
    public static boolean justEnglish (String string) {
        if (string.toLowerCase().contains("ş") || string.toLowerCase().contains("ğ") || string.toLowerCase().contains("ü") || string.toLowerCase().contains("ç") || string.toLowerCase().contains("ö") || string.contains("İ")) {
            return false;
        }
        else {
            return true;
        }
    }
    
    public static boolean verifyUsername (String string) {
        if (string.length() <= nameLength && string.length() > 3){
            if (string.contains("/") || string.contains("*") || string.contains("+") || string.contains("\\") || string.contains("%") || string.contains("$") || string.contains("'") || string.contains("!") || string.contains("@") || string.contains("#") 
            || string.contains("^") || string.contains("&") || string.contains("(") || string.contains(")") || string.contains("_") || string.contains("=") || string.contains("-") || string.contains(";") || string.contains("?") || string.contains(":") ||
            string.contains("\"") || string.contains(".") || string.contains("<") || string.contains(">") || string.contains("[") || string.contains("]") || string.contains("{") || string.contains("}") || string.contains("~") || string.contains("|") ||
                    string.contains(" ")) {
                System.out.println("Wrong Username");
                return false;
            }
            else if (justEnglish(string)){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            System.out.println("Wrong Username");
            return false;
        }
    }
    
    public static boolean verifyEmail (String string){
        if (string.length() <= 64 && string.length() > 3){
            if (string.contains("/") || string.contains("*") || string.contains("+") || string.contains("\\") || string.contains("%") || string.contains("$") || string.contains("'") || string.contains("!") || string.contains("#") 
            || string.contains("^") || string.contains("&") || string.contains("(") || string.contains(")") || string.contains("=") || string.contains(";") || string.contains("?") || string.contains(":") ||
            string.contains("\"") || string.contains("<") || string.contains(">") || string.contains("[") || string.contains("]") || string.contains("{") || string.contains("}") || string.contains("~") || string.contains("|") ||
                    string.contains(" ")) {
                
                System.out.println("Wrong Email");
                return false;
            }
            else if (justEnglish(string) && string.contains("@") && string.contains(".")){
                    return true;
                }
            else {
                System.out.println("Wrong Email");
                return false;
            }
        }
        else {
            System.out.println("Wrong Email");
            return false;
        }
    }
    
    public static boolean verifyPassword (String string) {
        if (string.length() <= 32 && string.length() > 7){
            if (string.contains("'") || string.contains(" ") || string.contains("\"")){
                System.out.println("Wrong Password");
                return false;
            }
            else if (justEnglish(string)){
                    return true;
                }
            else {
                return false;
            }
            }
        else {
            System.out.println("Wrong Password");
            return false;
        }
    }
    
    public static boolean verifyTelephoneNumber (String string) {
        if (string.length() > 6 && string.length() < 20) {
            return isNumber(string);
        }
        else {
            System.out.println("Wrong Length (Telephone number)");
            return false;
        }
    }
    
    public static boolean verifyTCnumber (String string) {
        if (string.length() == 11) {
            return isNumber(string);
        }
        else {
            System.out.println("Wrong Length (TC)");
            return false;
        }
    }
    
    public static boolean isNumber (String string) {
            try {
                Long.parseLong(string);
                return true;
            }
            catch (NumberFormatException e){
                System.out.println("NOT Number");
                return false;
            }
    }
    
    public static boolean verifyDate (String string) {
        Date test = Common.string_toDate(string);
        if (test == null) {
            System.out.println("Wrong Date");
            return false;
        }
        else {
            return true;
        }
    }
}
