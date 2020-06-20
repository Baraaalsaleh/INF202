//Baraa Alsaleh, 19050800
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.Resource;

import java.util.ArrayList;

/**
 *
 * @author Baraa
 */
public class Customer {
    private String name;
    private String address;
    private ArrayList<String> orderNrs = new ArrayList();
    private ArrayList<String> offerNrs = new ArrayList();
    private LastModification lastChange = null;
    private Manager adder = new Manager();

    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Customer() {
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<String> getOrderNrs() {
        return orderNrs;
    }

    public ArrayList<String> getOfferNrs() {
        return offerNrs;
    }
    
    public LastModification getLastChange() {
        return lastChange;
    }

    public Manager getAdder() {
        return adder;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }    

    public void addOrderNrs(String orderNr) {
        this.orderNrs.add(orderNr);
    }

    public void addOfferNrs(String offerNr) {
        this.offerNrs.add(offerNr);
    }
      
     public void delOrderNrs(String orderNr) {
        this.orderNrs.remove(orderNr);
    }

    public void delOfferNrs(String offerNr) {
        this.offerNrs.remove(offerNr);
    }

    public void setOrderNrs(ArrayList orderNrs) {
        this.orderNrs = orderNrs;
    }

    public void setOfferNrs(ArrayList offerNrs) {
        this.offerNrs = offerNrs;
    }
    
    public void setLastChange(LastModification lastChange) {
        this.lastChange = lastChange;
    }

    public void setAdder(Manager adder) {
        this.adder = adder;
    }
}
