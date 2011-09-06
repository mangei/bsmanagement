package cw.customermanagementmodul.persistence;

import java.util.List;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.app.CWPersistenceImpl;

/**
 * Implementation of persistence 'Group'
 *
 * @author Manuel Geier
 */
class GroupImpl
        extends CWPersistenceImpl
        implements Group {

    private Long id;
    private String name;
    private boolean deletable;
    private Group parent;
    private List<Customer> customers;

    GroupImpl(EntityManager entityManager) {
    	super(entityManager);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof GroupImpl)) return false;
        if (this.getId() == ((GroupImpl) obj).getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getParent() {
        return parent;
    }

    public void setParent(Group parent) {
        this.parent = parent;
    }

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
}
