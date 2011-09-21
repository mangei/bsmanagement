package cw.roommanagementmodul.persistence;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import cw.boardingschoolmanagement.persistence.CWPersistence;
import cw.customermanagementmodul.customer.persistence.Customer;


/**
 *
 * @author Dominik Jeitler
 */

@Entity
public class Bewohner
	extends CWPersistence
{
	
    public final static String ENTITY_NAME 	= "bewohner";
    public final static String TABLE_NAME 	= "bewohner";
    public final static String PROPERTYNAME_ID              = "id";
    public final static String PROPERTYNAME_COSTUMER        = "costumer";
    public final static String PROPERTYNAME_ZIMMER          = "zimmer";
    public final static String PROPERTYNAME_VON             = "von";
    public final static String PROPERTYNAME_BIS             = "bis";
    public final static String PROPERTYNAME_KAUTION         = "kaution";
    public final static String PROPERTYNAME_KAUTIONSTATUS   = "kaution_status";
    public final static String PROPERTYNAME_ACTIVE          = "active";
    
    private Long id             = null;
    private Customer customer   = null;
    private Zimmer zimmer       = null;
    private Kaution kaution     = null;
    private int kautionStatus   = 0;
    private Date von            = null;
    private Date bis            = null;
    private boolean active      = true;

    // TODO create an enum out of that
    public final static int NICHT_EINGEZAHLT    = 0;
    public final static int EINGEZAHLT          = 1;
    public final static int ZURUECK_GEZAHLT     = 2;
    public final static int EINGEZOGEN          = 3;
    
    private Bewohner() {
    	super(null);
    }
    
    Bewohner(EntityManager entityManager) {
    	super(entityManager);
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getId() == ((Bewohner)obj).getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Temporal(TemporalType.DATE)
    public Date getBis() {
        return bis;
    }

    public void setBis(Date bis) {
    	Date old = this.bis;
        this.bis = bis;
        firePropertyChange(PROPERTYNAME_BIS, old, bis);
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
    	Long old = this.id;
        this.id = id;
        firePropertyChange(PROPERTYNAME_ID, old, id);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
    	boolean old = this.active;
        this.active = active;
        firePropertyChange(PROPERTYNAME_ACTIVE, old, active);
    }

    @OneToOne(cascade=javax.persistence.CascadeType.ALL)
    @OnDelete(action=OnDeleteAction.CASCADE)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
    	Customer old = this.customer;
        this.customer = customer;
        firePropertyChange(PROPERTYNAME_COSTUMER, old, customer);
    }

    @Temporal(TemporalType.DATE)
    public Date getVon() {
        return von;
    }

    public void setVon(Date von) {
        Date old = this.von;
        this.von = von;
        firePropertyChange(PROPERTYNAME_VON, old, von);
    }

    @ManyToOne
    public Zimmer getZimmer() {
        return zimmer;
    }

    public void setZimmer(Zimmer zimmer) {
    	Zimmer old = this.zimmer;
        this.zimmer = zimmer;
        firePropertyChange(PROPERTYNAME_ZIMMER, old, zimmer);
    }
   
    /**
     * @return the kaution
     */
     @ManyToOne
    public Kaution getKaution() {
        return kaution;
    }

    /**
     * @param kaution the kaution to set
     */
    public void setKaution(Kaution kaution) {
    	Kaution old = this.kaution;
        this.kaution = kaution;
        firePropertyChange(PROPERTYNAME_KAUTION, old, kaution);
    }

    /**
     * @return the kautionStatus
     */
    public int getKautionStatus() {
        return kautionStatus;
    }

    /**
     * @param kautionStatus the kautionStatus to set
     */
    public void setKautionStatus(int kautionStatus) {
    	int old = this.kautionStatus;
        this.kautionStatus = kautionStatus;
        firePropertyChange(PROPERTYNAME_KAUTIONSTATUS, old, kautionStatus);
    }
}
