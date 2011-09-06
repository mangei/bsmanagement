package cw.customermanagementmodul.persistence;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.app.CWPersistenceImpl;

/**
 * Implementation of persistence 'Guardian'
 *
 * @author Manuel Geier
 */
class GuardianImpl
        extends CWPersistenceImpl
        implements Guardian {

    private Long id;
    private boolean legitimate;
    private Customer customer;
    private Customer guardian;

    GuardianImpl(EntityManager entityManager) {
    	super(entityManager);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof GuardianImpl)) return false;
        if (this.getId() == ((GuardianImpl) obj).getId()) {
            return true;
        } else {
            return false;
        }
    }

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
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Customer getGuardian() {
		return guardian;
	}

	public void setGuardian(Customer guardian) {
		this.guardian = guardian;
	}

}
