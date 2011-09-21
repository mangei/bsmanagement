package cw.roommanagementmodul.persistence;

import java.beans.PropertyChangeSupport;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;

import com.jgoodies.binding.beans.Model;

import cw.boardingschoolmanagement.persistence.AnnotatedClass;
import cw.boardingschoolmanagement.persistence.CWPersistence;

/**
 * @author Jeitler Dominik
 */
public class GebuehrenLauf 
	extends CWPersistence
{
    public final static String ENTITY_NAME 	= "gebuehren_lauf";
    public final static String TABLE_NAME 	= "gebuehren_lauf";

    private int id;
    private String art;
    private GregorianCalendar abrMonat;
    private int stornoLauf;
    private GregorianCalendar cpuDate;
    
    private GebuehrenLauf() {
        super(null);
    }

    GebuehrenLauf(EntityManager entityManager) {
    	super(entityManager);
    }
    
    public GregorianCalendar getAbrMonat() {
        return abrMonat;
    }

    public void setAbrMonat(GregorianCalendar abrMonat) {
        this.abrMonat = abrMonat;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public GregorianCalendar getCpuDate() {
        return cpuDate;
    }

    public void setCpuDate(GregorianCalendar cpuDate) {
        this.cpuDate = cpuDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStornoLauf() {
        return stornoLauf;
    }

    public void setStornoLauf(int stornoLauf) {
        this.stornoLauf = stornoLauf;
    }



    @Override
    public boolean equals(Object obj) {
        if (this.getId() == ((GebuehrenLauf) obj).getId()) {
            return true;
        } else {
            return false;
        }
    }
}
