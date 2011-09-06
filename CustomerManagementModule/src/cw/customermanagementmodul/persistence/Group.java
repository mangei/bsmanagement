package cw.customermanagementmodul.persistence;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cw.boardingschoolmanagement.app.CWPersistence;

/**
 *
 * @author ManuelG
 */
@Entity(name=Group.ENTITY_NAME)
@Table(name=Group.TABLE_NAME)
public interface Group
        extends CWPersistence {

    // Properties - Constants
    public final static String ENTITY_NAME = "groups";
    public final static String TABLE_NAME = "groups";
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_NAME = "name";
    public final static String PROPERTYNAME_DELETABLE = "deletable";
    public final static String PROPERTYNAME_PARENT = "parent";
    public final static String PROPERTYNAME_CUSTOMERS = "customers";

    public boolean isDeletable();
    public void setDeletable(boolean deletable);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId();
    public void setId(Long id);

    public String getName();
    public void setName(String name);

    @ManyToOne
    public Group getParent();
    public void setParent(Group parent);

    @OneToMany
    public List<Customer> getCustomers();
    public void setCustomers(List<Customer> customers);
}
