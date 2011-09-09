package cw.customermanagementmodul.logic;

import cw.boardingschoolmanagement.logic.CWBo;
import cw.customermanagementmodul.persistence.Customer;
import cw.customermanagementmodul.persistence.PMCustomer;

/**
 * Business logic for customer
 * 
 * @author Manuel Geier
 */
public class BoCustomer extends CWBo<Customer> {
	
	/**
	 * Basic constructor
	 * @param customer Customer
	 */
	public BoCustomer(Customer customer) {
		super(customer);
	}
	
	/**
	 * Returns the persistence
	 */
	public Customer getPersistence() {
		return (Customer) getBaseClass();
	}
	
	/**
	 * Removes the customer which is adapted from
	 */
	public void remove() {
		PMCustomer.getInstance().remove(getPersistence());
	}
	
	/**
	 * Activates the customer
	 */
	public void activate() {
		getPersistence().setActive(true);
	}
	
	/**
	 * Deactivates the customer
	 */
	public void deactivate() {
		getPersistence().setActive(false);
	}
}
