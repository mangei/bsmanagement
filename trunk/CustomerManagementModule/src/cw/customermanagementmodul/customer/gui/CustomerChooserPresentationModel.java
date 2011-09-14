package cw.customermanagementmodul.customer.gui;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.Action;
import javax.swing.Icon;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWAction;
import cw.boardingschoolmanagement.app.CWEntityManager;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.customermanagementmodul.customer.persistence.Customer;
import cw.customermanagementmodul.customer.persistence.PMCustomer;

/**
 *
 * @author ManuelG
 */
public class CustomerChooserPresentationModel
	extends CWPresentationModel {

    private List<Customer> customerList;
    private CWHeaderInfo headerInfo;
    private ButtonListenerSupport buttonListenerSupport = new ButtonListenerSupport();

    private Action okAction;
    private Action cancelAction;

    private CustomerSelectorPresentationModel customerSelectorPresentationModel;

    public CustomerChooserPresentationModel() {
        this(null);
    }
    
    public CustomerChooserPresentationModel(List<Customer> customerList) {
        super(CWEntityManager.createEntityManager());
        this.customerList = customerList;
        
        if(customerList == null) {
        	this.customerList = PMCustomer.getInstance().getAll(getEntityManager());
        }

        initModels();
        initEventHandling();
    }

    private void initModels() {

        okAction = new OkAction("Auswaehlen", CWUtils.loadIcon("cw/customermanagementmodul/images/user.png"));
        cancelAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/customermanagementmodul/images/cancel.png"));

        customerSelectorPresentationModel = new CustomerSelectorPresentationModel(customerList, getEntityManager());
        
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public void dispose() {
        CWEntityManager.closeEntityManager(getEntityManager());
    }

    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////

    private class OkAction extends CWAction {

        public OkAction(String name, Icon icon) {
            super(name, icon);
        }

        public void action(ActionEvent e) {
            buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.OK_BUTTON));
        }
    }

    private class CancelAction extends CWAction {

        public CancelAction(String name, Icon icon) {
            super(name, icon);
        }

        public void action(ActionEvent e) {
            buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.CANCEL_BUTTON));
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // ButtonListenerSupport
    ////////////////////////////////////////////////////////////////////////////

    public void removeButtonListener(ButtonListener listener) {
        buttonListenerSupport.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        buttonListenerSupport.addButtonListener(listener);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////

    public Action getCancelAction() {
        return cancelAction;
    }

    public Action getOkAction() {
        return okAction;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public Customer getSelectedCustomer() {
        return customerSelectorPresentationModel.getCustomerDataModel().getSelection();
    }

    public CustomerSelectorPresentationModel getCustomerSelectorPresentationModel() {
        return customerSelectorPresentationModel;
    }

}
