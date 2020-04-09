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
    
    static public void loginVerification (String username, String password) {
        PersonManagement person = new PersonManagement();
        int res = person.login(username, password);
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        if (res == 0) {
            JOptionPane.showMessageDialog(dialog, "Girdiğiniz kullanıcı adı veya şifre yanlıştır!", "Yanlış Giriş Bilgileri", JOptionPane.PLAIN_MESSAGE);
        }
        else if (res == -1) {
            JOptionPane.showMessageDialog(dialog, "Veri tabanina bağlanamadı!, Lütfen tekrar deneyin.", "Bağlantı Kesildi", JOptionPane.PLAIN_MESSAGE);
        }
        else {
            //hier
        }
    }
    
}
