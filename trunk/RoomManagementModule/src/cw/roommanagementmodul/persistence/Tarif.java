package cw.roommanagementmodul.persistence;

import java.beans.PropertyChangeSupport;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jgoodies.binding.beans.Model;

import cw.boardingschoolmanagement.persistence.AnnotatedClass;
import cw.boardingschoolmanagement.persistence.CWPersistence;

/**
 * @author Jeitler Dominik
 */
@Entity
public class Tarif 
	extends CWPersistence
{
    
    public final static String ENTITY_NAME 	= "tarif";
    public final static String TABLE_NAME 	= "tarif";
    public final static String PROPERTYNAME_ID 				= "id";
    public final static String PROPERTYNAME_AB 				= "ab";
    public final static String PROPERTYNAME_BIS 			= "bis";
    public final static String PROPERTYNAME_GEBUEHR 		= "gebuehr";
    public final static String PROPERTYNAME_TARIF			= "tarif";
    public final static String PROPERTYNAME_ACTIVE 			= "active";
    public final static String PROPERTYNAME_LASTACCOUNTED 	= "last_accounted";

    private Long id;
    private Date ab;
    private Date bis;
    private Gebuehr gebuehr;
    private double tarif;
    private boolean active;
    private Date lastAccounted;
    
    private Tarif() {
        super(null);
    }

    Tarif(EntityManager entityManager) {
    	super(entityManager);
    }

    @Temporal(TemporalType.DATE)
    public Date getAb() {
        return ab;
    }

    public void setAb(Date ab) {
        this.ab = ab;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Temporal(TemporalType.DATE)
    public Date getBis() {
        return bis;
    }

    public void setBis(Date bis) {
        this.bis = bis;
    }

    @ManyToOne
    public Gebuehr getGebuehr() {
        return gebuehr;
    }

    public void setGebuehr(Gebuehr gebuehr) {
        this.gebuehr = gebuehr;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.DATE)
    public Date getLastAccounted() {
        return lastAccounted;
    }

    public void setLastAccounted(Date lastAccounted) {
        this.lastAccounted = lastAccounted;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getId() == ((Tarif) obj).getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return this.gebuehr.toString() + "  " + this.tarif;

    }
}
