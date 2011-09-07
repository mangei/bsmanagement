package cw.customermanagementmodul.gui;

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
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.customermanagementmodul.logic.BoCustomer;
import cw.customermanagementmodul.persistence.Customer;
import cw.customermanagementmodul.persistence.PMCustomer;

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

        activateAction = new ActivateAction("Aktivieren", CWUtils.loadIcon("cw/customermanagementmodul/images/user_active_go.png"));
        deleteAction = new DeleteAction("Loeschen", CWUtils.loadIcon("cw/customermanagementmodul/images/user_delete.png"));

        customerSelectorPresentationModel = new CustomerSelectorPresentationModel(
                PMCustomer.getInstance().getAllInactive(getEntityManager()),
                "cw.customerboardingmanagement.CustomerInactiveView.customerTableState",
                getEntityManager());

        headerInfo = new CWHeaderInfo(
                "Inaktive Kunden",
                "Hier sehen Sie alle inaktiven Kunden die sich noch im System befinden.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/user_inactives.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/user_inactives.png"));
    }

    private void initEventHandling() {
        customerSelectorPresentationModel.getCustomerSelection().addValueChangeListener(selectionHandler = new SelectionHandler());
        updateActionEnablement();
    }

    public void dispose() {
        customerSelectorPresentationModel.getCustomerSelection().removeValueChangeListener(selectionHandler);
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

            Customer customer = customerSelectorPresentationModel.getSelectedCustomer();
            
            BoCustomer.activate(customer.getId());
            
            customerSelectorPresentationModel.remove(customer);

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

            Customer customer = customerSelectorPresentationModel.getSelectedCustomer();

            int i = JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich den ausgewaehlten Kunden loeschen?", "Kunden loeschen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (i == JOptionPane.OK_OPTION) {

                customerSelectorPresentationModel.remove(customer);
                
                BoCustomer.remove(customer.getId());
            	
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
        boolean hasSelection = !customerSelectorPresentationModel.getCustomerSelection().isSelectionEmpty();

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
