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
    private ArrayList orderNrs = new ArrayList<String>();
    private ArrayList offerNrs = new ArrayList<String>();

    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList getOrderNrs() {
        return orderNrs;
    }

    public ArrayList getOfferNrs() {
        return offerNrs;
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

}
