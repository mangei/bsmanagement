package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import javax.swing.event.ListSelectionEvent;
import cw.customermanagementmodul.pojo.manager.CustomerManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.ListSelectionListener;
import cw.customermanagementmodul.pojo.Customer;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * @author CreativeWorkers.at
 */
public class CustomerManagementPresentationModel {

    private Action newAction;
    private Action editAction;
    private Action deleteAction;
    private Action inactiveAction;

    private CustomerSelectorPresentationModel customerSelectorPresentationModel;

    private HeaderInfo headerInfo;

    public CustomerManagementPresentationModel() {
        initModels();
        initEventHandling();
    }

    public void initModels() {
        newAction = new NewAction("Neu", CWUtils.loadIcon("cw/customermanagementmodul/images/user_add.png"));
        editAction = new EditAction("Bearbeiten", CWUtils.loadIcon("cw/customermanagementmodul/images/user_edit.png"));
        deleteAction = new DeleteAction("Löschen", CWUtils.loadIcon("cw/customermanagementmodul/images/user_delete.png"));
        inactiveAction = new InactiveAction("Inaktive", CWUtils.loadIcon("cw/customermanagementmodul/images/user_delete.png"));

        customerSelectorPresentationModel = new CustomerSelectorPresentationModel(
                CustomerManager.getInstance().getAllActive(),
                "cw.customerboardingmanagement.CustomerManangementView.customerTableState"
                );
        
        headerInfo = new HeaderInfo(
                "Kunden verwalten",
                "Sie befinden sich Kundenverwaltungsbereich. Hier haben Sie einen Überblick über alle Kunden.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png")
        );
    }

    private void initEventHandling() {
        customerSelectorPresentationModel.getCustomerSelection().addValueChangeListener(new SelectionHandler());
        updateActionEnablement();
    }


    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////
    private class NewAction
            extends AbstractAction {

        private NewAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().lockMenu();
            GUIManager.setLoadingScreenText("Formular wird geladen...");
            GUIManager.setLoadingScreenVisible(true);

            final Customer c = new Customer();
            final EditCustomerPresentationModel model = new EditCustomerPresentationModel(
                    c,
                    new HeaderInfo(
                        "Kunden erstellen",
                        "Bearbeiten sie hier alle Informationen über Ihren Kunden.",
                        CWUtils.loadIcon("cw/customermanagementmodul/images/user_add.png"),
                        CWUtils.loadIcon("cw/customermanagementmodul/images/user_add.png")
            ));
            final EditCustomerView editView = new EditCustomerView(model);
            model.addButtonListener(new ButtonListener() {

                boolean customerAlreadyCreated = false;

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        CustomerManager.getInstance().save(c);
                        if (customerAlreadyCreated) {
                            GUIManager.getStatusbar().setTextAndFadeOut("Kunde wurde aktualisiert.");
                        } else {
                            GUIManager.getStatusbar().setTextAndFadeOut("Kunde wurde erstellt.");
                            customerAlreadyCreated = true;
                            customerSelectorPresentationModel.add(c);
                        }
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        GUIManager.changeToLastView();
                        GUIManager.getInstance().unlockMenu();
                    }
                }
            });

            GUIManager.changeView(editView.buildPanel(), true);
            GUIManager.setLoadingScreenVisible(false);

        }
    }

    private class EditAction
            extends AbstractAction {

        private EditAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            Customer c = customerSelectorPresentationModel.getSelectedCustomer();
            editCustomer(c);
        }
    }

    private class DeleteAction
            extends AbstractAction {

        private DeleteAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {

            GUIManager.setLoadingScreenText("Kunden löschen...");
            GUIManager.setLoadingScreenVisible(true);

            Customer customer = customerSelectorPresentationModel.getSelectedCustomer();

            int i = JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich den ausgewählten Kunden löschen?", "Kunden löschen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (i == JOptionPane.OK_OPTION) {

                String statusBarText;
                String forename = customer.getForename();
                String surname = customer.getSurname();
                statusBarText = "'" + forename + " " + surname +  "' wurde gelöscht.";

                customerSelectorPresentationModel.remove(customer);
                CustomerManager.getInstance().delete(customer);

                GUIManager.getStatusbar().setTextAndFadeOut(statusBarText);
            }

            GUIManager.setLoadingScreenVisible(false);
        }
    }

    private class InactiveAction
            extends AbstractAction {

        private InactiveAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {

            GUIManager.setLoadingScreenText("Inaktive Kunden werden geladen...");
            GUIManager.setLoadingScreenVisible(true);

            final CustomerInactivePresentationModel model = new CustomerInactivePresentationModel();
            final CustomerInactiveView view = new CustomerInactiveView(model);

            GUIManager.changeView(CWComponentFactory.createBackPanel(view.buildPanel()).getPanel(), true);

            GUIManager.setLoadingScreenVisible(false);

        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////

    public Action getNewAction() {
        return newAction;
    }

    public Action getDeleteAction() {
        return deleteAction;
    }

    public Action getEditAction() {
        return editAction;
    }

    public Action getInactiveAction() {
        return inactiveAction;
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public CustomerSelectorPresentationModel getCustomerSelectorPresentationModel() {
        return customerSelectorPresentationModel;
    }

    public static void editCustomer(final Customer c) {
        GUIManager.getInstance().lockMenu();
        GUIManager.setLoadingScreenText("Kunde wird geladen...");
        GUIManager.setLoadingScreenVisible(true);

        final EditCustomerPresentationModel model = new EditCustomerPresentationModel(
                c,
                new HeaderInfo(
                    "Kunden bearbeiten",
                    "Bearbeiten sie hier alle Informationen über Ihren Kunden.",
                    CWUtils.loadIcon("cw/customermanagementmodul/images/user_edit.png"),
                    CWUtils.loadIcon("cw/customermanagementmodul/images/user_edit.png")
        ));
        final EditCustomerView editView = new EditCustomerView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    CustomerManager.getInstance().save(c);
                    GUIManager.getStatusbar().setTextAndFadeOut("Kunde wurde aktualisiert.");
                }
                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    model.removeButtonListener(this);
                    GUIManager.changeToLastView();
                    GUIManager.getInstance().unlockMenu();
                }
            }
        });
        GUIManager.changeView(editView.buildPanel(), true);
        GUIManager.setLoadingScreenVisible(false);
    }




    // Event Handling *********************************************************
    private void updateActionEnablement() {
        boolean hasSelection = !customerSelectorPresentationModel.getCustomerSelection().isSelectionEmpty();

        editAction.setEnabled(hasSelection);
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
