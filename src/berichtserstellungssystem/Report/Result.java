//Baraa Alsaleh, 19050800
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.Report;

/**
 *
 * @author Baraa
 */
public class Result {
    private int serialNo;

    public Result(int serialNo) {
        this.serialNo = serialNo;
    }
    
    public Result () {
        
    }
    
    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public int getSerialNo() {
        return serialNo;
    }
}
