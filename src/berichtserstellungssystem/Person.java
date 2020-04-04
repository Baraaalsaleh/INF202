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
    private long TCNr;
    private long telephone;
    private long personalNr;
    private Date birthDate;

    public Person(String name, String lastname, String username, String password, String gender, String address, String email, long TCNr, long telephone, long personalNr, Date birthDate) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.TCNr = TCNr;
        this.telephone = telephone;
        this.personalNr = personalNr;
        this.birthDate = birthDate;
    }

    
    
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

    public long getTCNr() {
        return TCNr;
    }

    public long getTelephone() {
        return telephone;
    }

    public long getPersonalNr() {
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

    public void setTCNr(long TCNr) {
        this.TCNr = TCNr;
    }

    public void setTelephone(long telephone) {
        this.telephone = telephone;
    }

    public void setPersonalNr(long personalNr) {
        this.personalNr = personalNr;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String date_toString(Date date){
        return (date.getYear() + "-" + date.getMonth() + "-" + date.getDay());
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
        if (this.TCNr != other.TCNr) {
            return false;
        }
        return true;
    }

    
   
    
    
    
    
}
