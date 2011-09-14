package cw.customermanagementmodul.customer.logic;

import java.lang.reflect.InvocationTargetException;

import javax.persistence.EntityManager;
import javax.swing.SwingUtilities;

import com.jgoodies.forms.util.Utilities;

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
	public static Customer selectCustomer(final EntityManager entityManager) {
		Long customerId;
		Customer customer = null;
		
		GUIManager.getInstance().lockMenu();
        GUIManager.setLoadingScreenText("Kundenselector wird geladen...");
        GUIManager.setLoadingScreenVisible(true);
		
        class MyRunnable implements Runnable {
			
        	public Long customerId;
        	
			public void run() {
				
				final CustomerChooserPresentationModel model = new CustomerChooserPresentationModel();
				CustomerChooserView view = new CustomerChooserView(model);
				
		        model.addButtonListener(new ButtonListener() {

		            public void buttonPressed(ButtonEvent evt) {
		                if (evt.getType() == ButtonEvent.OK_BUTTON) {
		                	Customer customer = model.getSelectedCustomer();
		                	if(customer != null) {
		                		customerId = customer.getId();
		                	}
		                    model.getSelectedCustomer();
		                }
		                if (evt.getType() == ButtonEvent.OK_BUTTON || evt.getType() == ButtonEvent.CANCEL_BUTTON) {

		                	model.removeButtonListener(this);
		                    GUIManager.changeToPreviousView();
		                    GUIManager.getInstance().unlockMenu();
//		                    DoThread.this.interrupt();
		                }
		            }
		        });

		        GUIManager.changeViewTo(view, true);
		        GUIManager.setLoadingScreenVisible(false);
				
//				while(!isInterrupted()) {
//					try {
//						sleep(100);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
			}
		};
		
//		DoThread thread = new DoThread();
//		
//		thread.start();
//		try {
//			thread.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		MyRunnable r = new MyRunnable();
		
		try {
			SwingUtilities.invokeAndWait(r);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		customerId = r.customerId;
		
		if(customerId != null) {
			customer = PMCustomer.getInstance().get(customerId, entityManager);
		}
		
		return customer;
	}
}
