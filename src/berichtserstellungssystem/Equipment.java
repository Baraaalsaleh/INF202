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
abstract public class Equipment {
    private String name;

    public Equipment(String name) {
        this.name = name;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    
}
