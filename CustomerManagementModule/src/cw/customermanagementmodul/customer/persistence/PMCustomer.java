package cw.customermanagementmodul.customer.persistence;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import cw.boardingschoolmanagement.persistence.CWPersistenceManager;

/**
 * Manages Customers
 * 
 * @author Manuel Geier
 */
public class PMCustomer	
	extends CWPersistenceManager<Customer> {

	private static PMCustomer instance;
    private static Logger logger = Logger.getLogger(PMCustomer.class.getName());
    
    /**
     * Private constructor; class is a singleton
     */
    private PMCustomer() {
    	super(Customer.class);
    } 
    
    /**
     * Return an instance of PMCustomer
     * @return PMCustomer
     */
    public static PMCustomer getInstance() {
    	if(instance == null) {
    		instance = new PMCustomer();
    	}
    	return instance;
    }
    
    public Customer create(EntityManager entityManager) {
    	Customer customer = new Customer(entityManager);
    	entityManager.persist(customer);
    	return customer;
    }

    public int countAll(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery(
        		"SELECT " +
        			"COUNT(*)" +
        		" FROM " + 
        			Customer.ENTITY_NAME)
        .getResultList().iterator().next() ).intValue();
    }

    public int countActive(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery(
        		"SELECT " +
        			"COUNT(*)" +
        		" FROM " + 
        			Customer.ENTITY_NAME + 
        		" WHERE " + 
        			Customer.PROPERTYNAME_ACTIVE + "=true")
        .getResultList().iterator().next() ).intValue();
    }

    public int countInactive(EntityManager entityManager) {
        return ( (Long) entityManager.createQuery(
        		"SELECT " +
        			"COUNT(*)" +
        		" FROM " + 
        			Customer.ENTITY_NAME + 
        		" WHERE " + 
        			Customer.PROPERTYNAME_ACTIVE + "=false")
        .getResultList().iterator().next() ).intValue();
    }

    public List<Customer> getAll(EntityManager entityManager) {
        return setEntityManager(entityManager.createQuery(
        		"FROM " + 
        			Customer.ENTITY_NAME + 
        		" ORDER BY " +
        			Customer.PROPERTYNAME_SURNAME)
        .getResultList(), entityManager);
    }

    public List<Customer> getAllActive(EntityManager entityManager) {
        return setEntityManager(entityManager.createQuery(
        		"FROM " + 
        			Customer.ENTITY_NAME +
        		" WHERE " + 
        			Customer.PROPERTYNAME_ACTIVE + "=true " +
        		" ORDER BY " + 
        			Customer.PROPERTYNAME_SURNAME)
        .getResultList(), entityManager);
    }

    public List<Customer> getAllInactive(EntityManager entityManager) {
        return setEntityManager(entityManager.createQuery(
        		"FROM " + 
        			Customer.ENTITY_NAME + 
        		" WHERE " + 
        			Customer.PROPERTYNAME_ACTIVE + "=false" +
        		" ORDER BY " + 
        			Customer.PROPERTYNAME_SURNAME)
        .getResultList(), entityManager);
    }

    public List<String> getList(String attribute, EntityManager entityManager) {
        return entityManager.createQuery(
        		"SELECT " +
        			"str("+attribute+")" +
        		" FROM " + 
        			Customer.ENTITY_NAME + 
        		" WHERE " + 
        			attribute + " IS NOT NULL")
        .getResultList();
    }

    public String getResult(String attribute1, String value1, String attribute2, EntityManager entityManager) {
        try {
            return (String) entityManager.createQuery("SELECT str(" + attribute2 + ") FROM " + Customer.ENTITY_NAME + " WHERE " + attribute1 + " IS NOT NULL AND " + attribute1 + "='" + value1 + "'").setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
