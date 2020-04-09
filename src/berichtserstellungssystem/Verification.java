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
    
    
    public static boolean verifyName (String name) {
        if (name.length() <= nameLength){
            if (name.contains("/") || name.contains("*") || name.contains("+") || name.contains("\\") || name.contains("%") || name.contains("$") || name.contains("'") || name.contains("!") || name.contains("@") || name.contains("#") 
            || name.contains("^") || name.contains("&") || name.contains("(") || name.contains(")") || name.contains("_") || name.contains("=") || name.contains("-") || name.contains(";") || name.contains("?") || name.contains(":") ||
            name.contains("\"") || name.contains(".") || name.contains("<") || name.contains(">") || name.contains("[") || name.contains("]") || name.contains("{") || name.contains("}") || name.contains("~") || name.contains("|") || 
            name.contains("0") || name.contains("1") || name.contains("2") || name.contains("3") || name.contains("4") || name.contains("5") || name.contains("6") || name.contains("7") || name.contains("8") || name.contains("9")) {
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
    
    public static boolean verifyEmail (String name) {
        if (name.length() >= 7){
            if (name.contains("/") || name.contains("*") || name.contains("+") || name.contains("\\") || name.contains("%") || name.contains("$") || name.contains("'") || name.contains("!") || name.contains("#") 
            || name.contains("^") || name.contains("&") || name.contains("(") || name.contains(")") || name.contains("=") || name.contains(";") || name.contains("?") || name.contains(":") ||
            name.contains("\"") || name.contains("<") || name.contains(">") || name.contains("[") || name.contains("]") || name.contains("{") || name.contains("}") || name.contains("~") || name.contains("|")) {
                return false;
            }
            else {
                if (name.contains("@") && name.charAt(0) != '@' && name.charAt(name.length() - 1) != '@') {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        else {
            return false;
        }
    }    
}
