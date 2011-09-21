package cw.roommanagementmodul.persistence;

import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jgoodies.binding.beans.Model;

import cw.boardingschoolmanagement.persistence.AnnotatedClass;
import cw.boardingschoolmanagement.persistence.CWPersistence;

/**
 * @author Jeitler Dominik
 */
@Entity
public class Kaution 
	extends CWPersistence
{
    
    public final static String ENTITY_NAME 	= "kaution";
    public final static String TABLE_NAME 	= "kaution";
    public final static String PROPERTYNAME_ID 		= "id";
    public final static String PROPERTYNAME_NAME 	= "name";
    public final static String PROPERTYNAME_BETRAG 	= "betrag";

    private Long id;
    private String name;
    private double betrag;
    
    public final static DecimalFormat numberFormat=new DecimalFormat("#0.00");

    private Kaution() {
        super(null);
    }

    Kaution(EntityManager entityManager) {
    	super(entityManager);
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
    	double old = this.betrag;
        this.betrag = betrag;
        firePropertyChange(PROPERTYNAME_BETRAG, old, betrag);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
    	Long old = this.id;
        this.id = id;
        firePropertyChange(PROPERTYNAME_ID, old, id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    	String old = name;
        this.name = name;
        firePropertyChange(PROPERTYNAME_NAME, old, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Kaution other = (Kaution) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return this.name + " € " + numberFormat.format(this.betrag);
    }
}
