package cw.customermanagementmodul.logic;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.app.CWEntityManager;
import cw.boardingschoolmanagement.logic.CWBo;
import cw.customermanagementmodul.persistence.Customer;
import cw.customermanagementmodul.persistence.PMCustomer;

/**
 * Business logic for customer
 * 
 * @author Manuel Geier
 */
public class BoCustomer extends CWBo<Customer> {

	public BoCustomer(Customer customer) {
		super(customer);
	}
	
	/**
	 * Removes a customer
	 * @param customerId Long
	 */
	public static void remove(Long customerId) {
		EntityManager entityManager = CWEntityManager.createEntityManager();
		
		Customer customer = PMCustomer.getInstance().get(customerId, entityManager);
		PMCustomer.getInstance().remove(customer, entityManager);
		
		CWEntityManager.commit(entityManager);
		CWEntityManager.closeEntityManager(entityManager);
	}
	
	/**
	 * Activates a customer
	 * @param customerId Long
	 */
	public static void activate(Long customerId) {
		EntityManager entityManager = CWEntityManager.createEntityManager();
		
		Customer customer = PMCustomer.getInstance().get(customerId, entityManager);
		customer.setActive(true);
		
		CWEntityManager.commit(entityManager);
		CWEntityManager.closeEntityManager(entityManager);
	}

	/**
	 * Deactivate a customer
	 * @param customerId Long
	 */
	public static void deactivate(Long customerId) {
		EntityManager entityManager = CWEntityManager.createEntityManager();
		
		Customer customer = PMCustomer.getInstance().get(customerId, entityManager);
		customer.setActive(false);
		
		CWEntityManager.commit(entityManager);
		CWEntityManager.closeEntityManager(entityManager);
	}
}
