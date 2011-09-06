package cw.customermanagementmodul.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cw.boardingschoolmanagement.app.CWPersistence;

/**
 * 
 *
 * @author Manuel Geier
 */
@Entity(name=Guardian.ENTITY_NAME)
@Table(name=Guardian.TABLE_NAME)
public interface Guardian
        extends CWPersistence {

    // Properties - Constants
    public final static String ENTITY_NAME = "guardian";
    public final static String TABLE_NAME = "guardian";
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_LEGITIMATE = "legitimate";
    public final static String PROPERTYNAME_CUSTOMER = "customer";
    public final static String PROPERTYNAME_GUARDIAN = "guardian";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId();
    public void setId(Long id);
    
    public boolean isLegitimate();
    public void setLegitimate(boolean legitimate);
    
    @OneToMany
    public Customer getCustomer();
    public void setCustomer(Customer customer);

    @OneToMany
    public Customer getGuardian();
    public void setGuardian(Customer customer);
}
