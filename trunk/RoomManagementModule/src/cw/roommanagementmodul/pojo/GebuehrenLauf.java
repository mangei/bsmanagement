package cw.roommanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;

import cw.boardingschoolmanagement.persistence.AnnotatedClass;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.GregorianCalendar;

/**
 * @author Jeitler Dominik
 */
public class GebuehrenLauf extends Model implements AnnotatedClass {

    private int id;
    private String art;
    private GregorianCalendar abrMonat;
    private int stornoLauf;
    private GregorianCalendar cpuDate;
    protected transient PropertyChangeSupport support;

    public GebuehrenLauf(int id, String art, GregorianCalendar abrMonat, int stornoLauf, GregorianCalendar cpuDate) {
        this.id = id;
        this.art = art;
        this.abrMonat = abrMonat;
        this.stornoLauf = stornoLauf;
        this.cpuDate = cpuDate;
    }

    public GebuehrenLauf() {
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
