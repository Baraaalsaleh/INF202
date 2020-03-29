/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem;

import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author Baraa
 */
abstract public class Person {
    private String name;
    private String lastname;
    private String geschlecht;
    private String adress;
    private String email;
    private int[] TCNr = new int[11];
    private int[] telephone = new int[15];
    private int[] PersonalNr = new int[8];
    private Date birthDate = new Date();

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public String getAdress() {
        return adress;
    }

    public String getEmail() {
        return email;
    }

    public int[] getTCNr() {
        return TCNr;
    }

    public int[] getTelephone() {
        return telephone;
    }

    public int[] getPersonalNr() {
        return PersonalNr;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTCNr(int[] TCNr) {
        this.TCNr = TCNr;
    }

    public void setTelephone(int[] telephone) {
        this.telephone = telephone;
    }

    public void setPersonalNr(int[] PersonalNr) {
        this.PersonalNr = PersonalNr;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (!Arrays.equals(this.TCNr, other.TCNr)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
