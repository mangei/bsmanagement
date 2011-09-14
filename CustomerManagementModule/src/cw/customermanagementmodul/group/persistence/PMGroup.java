package cw.customermanagementmodul.group.persistence;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.persistence.CWPersistenceManager;
import cw.customermanagementmodul.customer.persistence.Customer;

/**
 * Manages Groups
 * 
 * @author Manuel Geier
 */
public class PMGroup extends CWPersistenceManager<Group> {

    private static PMGroup instance;
    private static Logger logger = Logger.getLogger(PMGroup.class.getName());
    
    private PMGroup() {
    	super(Group.class);
    }
    
    public static PMGroup getInstance() {
        if(instance == null) {
            instance = new PMGroup();
        }
        return instance;
    }

	public Group create(EntityManager entityManager) {
		Group group = new Group(entityManager);
		entityManager.persist(group);
		return group;
	}
    
    public int countAll(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery("SELECT COUNT(*) FROM " + Group.ENTITY_NAME).getResultList().iterator().next() ).intValue();
    }

	public List<Group> getAll(EntityManager entityManager) {
		return setEntityManager(entityManager.createQuery(
				"FROM " + 
					Group.ENTITY_NAME
		).getResultList(), entityManager);
	}

	public List<Group> getAllGroupsByCustomer(Customer customer, EntityManager entityManager) {
		return setEntityManager(entityManager.createQuery(
				"FROM " +
					Group.ENTITY_NAME /*+ 
				" WHERE " +
					Group.PROPERTYNAME_CUSTOMERS
					// TODO Not finished*/
		).getResultList(), entityManager);
	}
}
