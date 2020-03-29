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
    private String username;
    private String password;
    private String gender;
    private String address;
    private String email;
    private int[] TCNr = new int[11];
    private int[] telephone = new int[15];
    private int[] personalNr = new int[8];
    private Date birthDate = new Date();

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
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
        return personalNr;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public void setPersonalNr(int[] personalNr) {
        this.personalNr = personalNr;
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
