package cw.customermanagementmodul.persistence;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import cw.boardingschoolmanagement.persistence.AbstractPersistenceManager;

/**
 * Manages Guardian
 * 
 * @author Manuel Geier
 */
public class PMGuardian	
	extends AbstractPersistenceManager<Guardian> {

	private static PMGuardian instance;
    private static Logger logger = Logger.getLogger(PMGuardian.class.getName());
    
    /**
     * Private constructor; class is a singleton
     */
    private PMGuardian() {
    	super(Guardian.class);
    } 
    
    /**
     * Return an instance of CustumerManager
     * @return CustomerManager
     */
    public static PMGuardian getInstance() {
    	if(instance == null) {
    		instance = new PMGuardian();
    	}
    	return instance;
    }
    
    public Guardian create(Customer customer, EntityManager entityManager) {
    	Guardian guardian = new Guardian(entityManager);
    	guardian.setCustomer(customer);
    	entityManager.persist(guardian);
    	return guardian;
    }

    public int countAll(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery(
        		"SELECT " +
        			"COUNT(*)" +
        		" FROM " + 
        			Guardian.ENTITY_NAME)
        .getResultList().iterator().next() ).intValue();
    }

    public List<Guardian> getAll(EntityManager entityManager) {
        return setEntityManager(entityManager.createQuery(
        		"FROM " + 
        			Guardian.ENTITY_NAME)
        .getResultList(), entityManager);
    }
    
    public Guardian getGuardianForCustomer(Long customerId, EntityManager entityManager) {
    	try {
    		return setEntityManager((Guardian) entityManager.createQuery(
            		"SELECT " + 
            			"g" + 
        			" FROM " + 
            			Guardian.ENTITY_NAME + " g" +
            		" WHERE " +
            			"g.customer is not null" + 
            		" AND " + 
            			"g.customer.id = " + customerId)
            .getSingleResult(), entityManager);
    		
    	} catch(NoResultException e) {
    		return null;
    	}
    }

}
