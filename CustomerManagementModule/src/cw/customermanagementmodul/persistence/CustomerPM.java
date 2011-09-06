package cw.customermanagementmodul.persistence;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import cw.boardingschoolmanagement.pojo.manager.AbstractPersistenceManager;

/**
 * Manages Customers
 * 
 * @author Manuel Geier
 */
public class CustomerPM	
	extends AbstractPersistenceManager<Customer> {

	private static CustomerPM instance;
    private static Logger logger = Logger.getLogger(CustomerPM.class.getName());
    
    /**
     * Private constructor; class is a singleton
     */
    private CustomerPM() {} 
    
    /**
     * Return an instance of CustumerManager
     * @return CustomerManager
     */
    public static CustomerPM getInstance() {
    	if(instance == null) {
    		instance = new CustomerPM();
    	}
    	return instance;
    }
    
    public Customer create(EntityManager entityManager) {
    	Customer customer = new Customer(entityManager);
    	entityManager.getTransaction().begin();
    	entityManager.persist(customer);
    	entityManager.getTransaction().commit();
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
        return entityManager.createQuery(
        		"FROM " + 
        			Customer.ENTITY_NAME + 
        		" ORDER BY " +
        			Customer.PROPERTYNAME_SURNAME)
        .getResultList();
    }

    public List<Customer> getAllActive(EntityManager entityManager) {
        return entityManager.createQuery(
        		"FROM " + 
        			Customer.ENTITY_NAME +
        		" WHERE " + 
        			Customer.PROPERTYNAME_ACTIVE + "=true " +
        		" ORDER BY " + 
        			Customer.PROPERTYNAME_SURNAME)
        .getResultList();
    }

    public List<Customer> getAllInactive(EntityManager entityManager) {
        return entityManager.createQuery(
        		"FROM " + 
        			Customer.ENTITY_NAME + 
        		" WHERE " + 
        			Customer.PROPERTYNAME_ACTIVE + "=false" +
        		" ORDER BY " + 
        			Customer.PROPERTYNAME_SURNAME)
        .getResultList();
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
