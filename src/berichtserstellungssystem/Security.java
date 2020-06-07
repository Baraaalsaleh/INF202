/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author Baraa
 */
public class Security {
    static String algorithm = "SHA-512";
    private final static char[] hexArray = "ACEGIKMOQSUWYZTL".toCharArray();
    //Diese Klasse ist da nur für spätere Änderungen, falls die eingegebenen Daten bearbeitet werden sollte, befor sie in der Datenbank gespeichert werden
    public static String prepareString (String string) {
        
        return string;
    }

    public static String generateHash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.reset();
            byte[] hash = digest.digest(data.getBytes());
            
            String hashCode = bytesToStringHex(hash);
            
            System.out.println(hashCode);
            
            return hashCode;
        } catch(NoSuchAlgorithmException e) {
            System.out.println(algorithm + " does not exist!");
        }
        return null;
    }
    
    public static String bytesToStringHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] &0xFF;
            hexChars[i*2] = hexArray[v >>> 4];
            hexChars[i*2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


}
