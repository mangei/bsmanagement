package cw.customermanagementmodul.persistence;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cw.boardingschoolmanagement.persistence.CWPersistence;

/**
 * Implementation of persistence 'Group'
 *
 * @author Manuel Geier
 */
@Entity(name=Group.ENTITY_NAME)
@Table(name=Group.TABLE_NAME)
public class Group
        extends CWPersistence {

	// Properties - Constants
    public final static String ENTITY_NAME = "groups";
    public final static String TABLE_NAME = "groups";
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_NAME = "name";
    public final static String PROPERTYNAME_DELETABLE = "deletable";
    public final static String PROPERTYNAME_PARENT = "parent";
    public final static String PROPERTYNAME_CUSTOMERS = "customers";
	
    private Long id;
    private String name;
    private boolean deletable;
    private Group parent;
    private List<Customer> customers;

    public Group() {
		super(null);
	}
    
    Group(EntityManager entityManager) {
    	super(entityManager);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Group)) return false;
        if (this.getId() == ((Group) obj).getId()) {
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
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToOne
    public Group getParent() {
        return parent;
    }

    public void setParent(Group parent) {
        this.parent = parent;
    }

    @OneToMany
	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
}
