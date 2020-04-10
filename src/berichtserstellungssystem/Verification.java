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
public class Verification {
    final private static int nameLength = 20;
    final private static int passwordLength = 20;    
    
    
    public static boolean verifyName (String string) {
        if (string.length() <= nameLength && string.length() > 3){
            if (string.contains("/") || string.contains("*") || string.contains("+") || string.contains("\\") || string.contains("%") || string.contains("$") || string.contains("'") || string.contains("!") || string.contains("@") || string.contains("#") 
            || string.contains("^") || string.contains("&") || string.contains("(") || string.contains(")") || string.contains("_") || string.contains("=") || string.contains("-") || string.contains(";") || string.contains("?") || string.contains(":") ||
            string.contains("\"") || string.contains(".") || string.contains("<") || string.contains(">") || string.contains("[") || string.contains("]") || string.contains("{") || string.contains("}") || string.contains("~") || string.contains("|") || 
            string.contains("0") || string.contains("1") || string.contains("2") || string.contains("3") || string.contains("4") || string.contains("5") || string.contains("6") || string.contains("7") || string.contains("8") || string.contains("9")) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }
    
    public static boolean verifyUsername (String string) {
        if (string.length() <= nameLength && string.length() > 3){
            if (string.contains("/") || string.contains("*") || string.contains("+") || string.contains("\\") || string.contains("%") || string.contains("$") || string.contains("'") || string.contains("!") || string.contains("@") || string.contains("#") 
            || string.contains("^") || string.contains("&") || string.contains("(") || string.contains(")") || string.contains("_") || string.contains("=") || string.contains("-") || string.contains(";") || string.contains("?") || string.contains(":") ||
            string.contains("\"") || string.contains(".") || string.contains("<") || string.contains(">") || string.contains("[") || string.contains("]") || string.contains("{") || string.contains("}") || string.contains("~") || string.contains("|") ||
                    string.contains(" ")) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }
    
    public static boolean verifyEmail (String string){
        if (string.length() <= nameLength && string.length() > 3){
            if (string.contains("/") || string.contains("*") || string.contains("+") || string.contains("\\") || string.contains("%") || string.contains("$") || string.contains("'") || string.contains("!") || string.contains("@") || string.contains("#") 
            || string.contains("^") || string.contains("&") || string.contains("(") || string.contains(")") || string.contains("_") || string.contains("=") || string.contains("-") || string.contains(";") || string.contains("?") || string.contains(":") ||
            string.contains("\"") || string.contains(".") || string.contains("<") || string.contains(">") || string.contains("[") || string.contains("]") || string.contains("{") || string.contains("}") || string.contains("~") || string.contains("|") ||
                    string.contains(" ")) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }
    public static boolean verifyPassword (String string) {
        if (string.length() <= 32 && string.length() > 7){
            if (string.contains("'") || string.contains(" ") || string.contains("\"")){
                return false;
            }
            else {
                    return true;
                }
            }
        else {
            return false;
        }
    }    
}
