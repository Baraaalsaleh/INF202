//Baraa Alsaleh, 19050800
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.Resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Baraa
 */
public class Person {
    private String name = "";
    private String lastname = "";
    private String gender = "";
    private String address = "";
    private String email = "";
    private long tcNr = 0;
    private long telephone = 0;
    private long personalNr = 0;
    private Date birthDate = new Date();
    private int status = 0;

    public Person(String name, String lastname, String gender, String address, String email, long TCNr, long telephone, long personalNr, Date birthDate) {
        this.name = name;
        this.lastname = lastname;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.tcNr = TCNr;
        this.telephone = telephone;
        this.personalNr = personalNr;
        this.birthDate = birthDate;
    }
    
    
    public Person (ResultSet rs) {
        try {
            this.name = rs.getString("name");
            this.lastname = rs.getString("lastname");
            this.gender = rs.getString("gender");
            this.address = rs.getString("address");
            this.email = rs.getString("email");
            this.tcNr = rs.getLong("TCNr");
            this.telephone = rs.getLong("telephone");
            this.personalNr = rs.getLong("PersonalNr");
            String[] date = rs.getString("birthdate").split("-");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            this.birthDate = new Date(year-1900, month-1, day);
            this.status = rs.getInt("status");
        }
        catch (SQLException e){
            System.out.println("Person " + e);
        }
    }
    
    public Person (String name, String lastname, long pnr) {
        this.name = name;
        this.lastname = lastname;
        this.personalNr = pnr;
    }

    public Person() {
        
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

    public long getTcNr() {
        return tcNr;
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

    public int getStatus() {
        return status;
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

    public void setTcNr(long tcNr) {
        this.tcNr = tcNr;
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
        if (this.tcNr != other.tcNr) {
            return false;
        }
        if (this.telephone != other.telephone) {
            return false;
        }
        if (this.personalNr != other.personalNr) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.lastname, other.lastname)) {
            return false;
        }
        if (!Objects.equals(this.gender, other.gender)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.birthDate, other.birthDate)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", lastname=" + lastname + ", gender=" + gender + ", address=" + address + ", email=" + email + ", tcNr=" + tcNr + ", telephone=" + telephone + ", personalNr=" + personalNr + ", birthDate=" + birthDate + ", status=" + status + '}';
    }    
}
