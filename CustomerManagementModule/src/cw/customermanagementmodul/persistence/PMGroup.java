package cw.customermanagementmodul.persistence;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.pojo.manager.AbstractPersistenceManager;

/**
 * Manages Groups
 * 
 * @author Manuel Geier
 */
public class PMGroup extends AbstractPersistenceManager<Group> {

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
    	entityManager.getTransaction().begin();
		entityManager.persist(group);
    	entityManager.getTransaction().commit();
		return group;
	}
    
    public int countAll(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery("SELECT COUNT(*) FROM " + Group.ENTITY_NAME).getResultList().iterator().next() ).intValue();
    }

	public List<Group> getAll(EntityManager entityManager) {
		return entityManager.createQuery(
				"FROM " + 
					Group.ENTITY_NAME
		).getResultList();
	}

	public List<Group> getAllGroupsByCustomer(Customer customer, EntityManager entityManager) {
		return entityManager.createQuery(
				"FROM " +
					Group.ENTITY_NAME /*+ 
				" WHERE " +
					Group.PROPERTYNAME_CUSTOMERS
					// TODO Not finished*/
		).getResultList();
	}
}
