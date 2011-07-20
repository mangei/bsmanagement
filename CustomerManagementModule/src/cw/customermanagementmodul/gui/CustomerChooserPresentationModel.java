package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import cw.customermanagementmodul.pojo.Customer;

/**
 *
 * @author ManuelG
 */
public class CustomerChooserPresentationModel
{

    private List<Customer> customerList;
    private CWHeaderInfo headerInfo;
    private ButtonListenerSupport buttonListenerSupport;

    private Action okAction;
    private Action cancelAction;

    private Customer selectedCustomer;

    private CustomerSelectorPresentationModel customerSelectorPresentationModel;

    public CustomerChooserPresentationModel(CWHeaderInfo headerInfo) {
        this(headerInfo, null);
    }

    public CustomerChooserPresentationModel(CWHeaderInfo headerInfo, List<Customer> customerList) {
        this(headerInfo, customerList, null);
    }
    
    public CustomerChooserPresentationModel(CWHeaderInfo headerInfo, List<Customer> customerList, Customer selectedCustomer) {
        this.headerInfo = headerInfo;
        this.customerList = customerList;
        this.selectedCustomer = selectedCustomer;

        initModels();
        initEventHandling();
    }

    private void initModels() {

        okAction = new OkAction("Auswählen", CWUtils.loadIcon("cw/customermanagementmodul/images/user.png"));
        cancelAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/customermanagementmodul/images/cancel.png"));

        customerSelectorPresentationModel = new CustomerSelectorPresentationModel();
        
        // Select the Customer
        
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public void dispose() {
        // Nothing to do
    }

    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////

    private class OkAction extends AbstractAction {

        public OkAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.OK_BUTTON));
        }
    }

    private class CancelAction extends AbstractAction {

        public CancelAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
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
        return customerSelectorPresentationModel.getSelectedCustomer();
    }

    public CustomerSelectorPresentationModel getCustomerSelectorPresentationModel() {
        return customerSelectorPresentationModel;
    }

}
