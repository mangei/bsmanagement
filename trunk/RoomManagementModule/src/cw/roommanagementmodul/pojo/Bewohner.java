package cw.roommanagementmodul.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.jgoodies.binding.beans.Model;

import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import cw.customermanagementmodul.pojo.Customer;


/**
 *
 * @author Dominik Jeitler
 */

@Entity
public class Bewohner
        extends Model
        implements AnnotatedClass
{
    
    private Long id             = null;
    private Customer customer   = null;
    private Zimmer zimmer       = null;
    private Kaution kaution     = null;
    private int kautionStatus   = 0;
    private Date von            = null;
    private Date bis            = null;
    private boolean active      = true;

    public final static int NICHT_EINGEZAHLT    = 0;
    public final static int EINGEZAHLT          = 1;
    public final static int ZURUECK_GEZAHLT     = 2;
    public final static int EINGEZOGEN          = 3;

    public final static String PROPERTYNAME_ID              = "id";
    public final static String PROPERTYNAME_COSTUMER        = "costumer";
    public final static String PROPERTYNAME_ZIMMER          = "zimmer";
    public final static String PROPERTYNAME_VON             = "von";
    public final static String PROPERTYNAME_BIS             = "bis";
    public final static String PROPERTYNAME_KAUTION         = "kaution";
    public final static String PROPERTYNAME_KAUTIONSTATUS   = "kautionStatus";
    public final static String PROPERTYNAME_ACTIVE          = "active";
    
    
    public Bewohner(){
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
        this.bis = bis;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @OneToOne(cascade=javax.persistence.CascadeType.ALL)
    @OnDelete(action=OnDeleteAction.CASCADE)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        this.zimmer = zimmer;
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
        this.kaution = kaution;
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
        this.kautionStatus = kautionStatus;
    }
}
