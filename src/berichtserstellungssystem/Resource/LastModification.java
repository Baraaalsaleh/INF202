/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.Resource;

import java.util.Date;

/**
 *
 * @author Baraa
 */
public class LastModification {
    private Date modificationDate = null;
    private Manager modificator = null;
    
    public LastModification(Date modificationDate, Manager modificator) {
        this.modificationDate = modificationDate;
        this.modificator = modificator;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public void setModificator(Manager modificator) {
        this.modificator = modificator;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public Manager getModificator() {
        return modificator;
    }
    
    
}
