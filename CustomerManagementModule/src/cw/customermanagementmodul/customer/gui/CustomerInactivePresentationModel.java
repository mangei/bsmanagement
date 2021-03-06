package cw.customermanagementmodul.customer.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.persistence.EntityManager;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWEntityManager;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.customermanagementmodul.customer.images.ImageDefinitionCustomer;
import cw.customermanagementmodul.customer.logic.BoCustomer;
import cw.customermanagementmodul.customer.persistence.Customer;
import cw.customermanagementmodul.customer.persistence.PMCustomer;

/**
 * @author Manuel Geier (CreativeWorkers)
 */
public class CustomerInactivePresentationModel
	extends CWPresentationModel {

    private Action activateAction;
    private Action deleteAction;
    public static String BUTTON_OWN_ACTIVE = "activeButton";
    private CustomerSelectorPresentationModel customerSelectorPresentationModel;
    private CWHeaderInfo headerInfo;
    private ButtonListenerSupport buttonListenerSupport;
    private SelectionHandler selectionHandler;

    public CustomerInactivePresentationModel(EntityManager entityManager) {
    	super(entityManager);
        initModels();
        initEventHandling();
    }

    public void initModels() {
        buttonListenerSupport = new ButtonListenerSupport();

        activateAction = new ActivateAction("Reaktivieren", CWUtils.loadIcon(ImageDefinitionCustomer.CUSTOMER_ACTIVE_GO));
        deleteAction = new DeleteAction("Loeschen", CWUtils.loadIcon(ImageDefinitionCustomer.CUSTOMER_REMOVE));

        customerSelectorPresentationModel = new CustomerSelectorPresentationModel(
                PMCustomer.getInstance().getAllInactive(getEntityManager()),
                "cw.customerboardingmanagement.CustomerInactiveView.customerTableState",
                getEntityManager());

        headerInfo = new CWHeaderInfo(
                "Inaktive Kunden",
                "Hier sehen Sie alle inaktiven Kunden die sich noch im System befinden.",
                CWUtils.loadIcon(ImageDefinitionCustomer.CUSTOMER_INACTIVES),
                CWUtils.loadIcon(ImageDefinitionCustomer.CUSTOMER_INACTIVES));
    }

    private void initEventHandling() {
        customerSelectorPresentationModel.getCustomerDataModel().addValueChangeListener(selectionHandler = new SelectionHandler());
        updateActionEnablement();
    }

    public void release() {
        customerSelectorPresentationModel.getCustomerDataModel().removeValueChangeListener(selectionHandler);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////
    private class ActivateAction
            extends AbstractAction {

        private ActivateAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {

            Customer customer = customerSelectorPresentationModel.getCustomerDataModel().getSelection();
            BoCustomer boCustomer  = customer.getTypedAdapter(BoCustomer.class);
            boCustomer.activate();
            CWEntityManager.commit(getEntityManager());
            
            customerSelectorPresentationModel.getCustomerDataModel().remove(customer);

            GUIManager.getStatusbar().setTextAndFadeOut("Kunde wurde wieder aktiviert.");

            buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.CUSTOM_BUTTON, BUTTON_OWN_ACTIVE));
        }
    }

    private class DeleteAction
            extends AbstractAction {

        private DeleteAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {

            GUIManager.setLoadingScreenText("Kunden loeschen...");
            GUIManager.setLoadingScreenVisible(true);

            Customer customer = customerSelectorPresentationModel.getCustomerDataModel().getSelection();

            int i = JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich den ausgewaehlten Kunden loeschen?", "Kunden loeschen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (i == JOptionPane.OK_OPTION) {

                customerSelectorPresentationModel.getCustomerDataModel().remove(customer);
                
                BoCustomer boCustomer = customer.getTypedAdapter(BoCustomer.class);
                boCustomer.remove();
                CWEntityManager.commit(getEntityManager());
            	
                String statusBarText;
                String forename = customer.getForename();
                String surname = customer.getSurname();
                statusBarText = "'" + forename + " " + surname + "' wurde geloescht.";

                GUIManager.getStatusbar().setTextAndFadeOut(statusBarText);
            }

            GUIManager.setLoadingScreenVisible(false);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////
    public Action getActivateAction() {
        return activateAction;
    }

    public Action getDeleteAction() {
        return deleteAction;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public CustomerSelectorPresentationModel getCustomerSelectorPresentationModel() {
        return customerSelectorPresentationModel;
    }

    public void removeButtonListener(ButtonListener listener) {
        buttonListenerSupport.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        buttonListenerSupport.addButtonListener(listener);
    }

    // Event Handling *********************************************************
    private void updateActionEnablement() {
        boolean hasSelection = !customerSelectorPresentationModel.getCustomerDataModel().isSelectionEmpty();

        activateAction.setEnabled(hasSelection);
        deleteAction.setEnabled(hasSelection);
    }

    private class SelectionHandler implements PropertyChangeListener, ListSelectionListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateActionEnablement();
        }

        public void valueChanged(ListSelectionEvent e) {
            updateActionEnablement();
        }
    }
}
