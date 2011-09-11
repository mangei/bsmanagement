package cw.customermanagementmodul.persistence;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import cw.boardingschoolmanagement.persistence.CWPersistence;

/**
 * Implementation of persistence 'Guardian'
 *
 * @author Manuel Geier
 */
@Entity(name=Guardian.ENTITY_NAME)
@Table(name=Guardian.TABLE_NAME)
public class Guardian
        extends CWPersistence {

	// Properties - Constants
    public final static String ENTITY_NAME = "guardian";
    public final static String TABLE_NAME = "guardian";
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_LEGITIMATE = "legitimate";
    public final static String PROPERTYNAME_CUSTOMER = "customer";
    public final static String PROPERTYNAME_GUARDIAN = "guardian";
	
    private Long id;
    private boolean legitimate;
    private Customer customer;
    private Customer guardian;

    public Guardian() {
		super(null);
	}
    
    Guardian(EntityManager entityManager) {
    	super(entityManager);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Guardian)) return false;
        if (this.getId() == ((Guardian) obj).getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public boolean isLegitimate() {
		return legitimate;
	}

	public void setLegitimate(boolean legitimate) {
		this.legitimate = legitimate;
	}

    @OneToOne
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

    @OneToOne
	public Customer getGuardian() {
		return guardian;
	}

	public void setGuardian(Customer guardian) {
		this.guardian = guardian;
	}

}
