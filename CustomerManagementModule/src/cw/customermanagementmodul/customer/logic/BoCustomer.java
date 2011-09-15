package cw.customermanagementmodul.customer.logic;

import javax.persistence.EntityManager;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.logic.CWBoPersistence;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.customermanagementmodul.customer.gui.CustomerChooserPresentationModel;
import cw.customermanagementmodul.customer.gui.CustomerChooserView;
import cw.customermanagementmodul.customer.persistence.Customer;
import cw.customermanagementmodul.customer.persistence.PMCustomer;

/**
 * Business logic for customer
 * 
 * @author Manuel Geier
 */
public class BoCustomer extends CWBoPersistence<Customer> {
	
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
	
	/**
	 * Opens an dialog to select a customer
	 * @return customer
	 */
	public synchronized static Customer selectCustomer(final EntityManager entityManager) {
		final ValueModel customerId = new ValueHolder();
		Customer customer = null;
		
		GUIManager.getInstance().lockMenu();
        GUIManager.setLoadingScreenText("Kundenselector wird geladen...");
        GUIManager.setLoadingScreenVisible(true);
        
        final Thread t = Thread.currentThread();
		
		final CustomerChooserPresentationModel model = new CustomerChooserPresentationModel();
		CustomerChooserView view = new CustomerChooserView(model);
		
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.OK_BUTTON) {
                	Customer customer = model.getSelectedCustomer();
                	if(customer != null) {
                		customerId.setValue(customer.getId());
                	}
                    model.getSelectedCustomer();
                }
                if (evt.getType() == ButtonEvent.OK_BUTTON || evt.getType() == ButtonEvent.CANCEL_BUTTON) {

                	model.removeButtonListener(this);
                    GUIManager.changeToPreviousView();
                    GUIManager.getInstance().unlockMenu();
                    
                    synchronized (t) {
	                    t.notify();
                    }
                    
                }
            }
        });

        GUIManager.changeViewTo(view, true);
        GUIManager.setLoadingScreenVisible(false);
		
        synchronized(t) {
	        try {
				t.wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
        }
		        
		if(customerId.getValue() != null) {
			customer = PMCustomer.getInstance().get((Long) customerId.getValue(), entityManager);
		}
		
		return customer;
	}
}
