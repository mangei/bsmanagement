package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import javax.swing.event.ListSelectionEvent;
import cw.customermanagementmodul.pojo.manager.CustomerManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import cw.customermanagementmodul.pojo.Customer;
import javax.swing.Icon;

/**
 * @author CreativeWorkers.at
 */
public class CustomerManagementPresentationModel {

    private Action newAction;
    private Action editAction;
    private Action deleteAction;
    private String headerText;

    private CustomerSelectorPresentationModel customerSelectorPresentationModel;

    public CustomerManagementPresentationModel() {
        this("");
    }

    public CustomerManagementPresentationModel(String headerText) {
        this.headerText = headerText;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        newAction = new NewAction("Neu", CWUtils.loadIcon("cw/customermanagementmodul/images/user_add.png"));
        editAction = new EditAction("Bearbeiten", CWUtils.loadIcon("cw/customermanagementmodul/images/user_edit.png"));
        deleteAction = new DeleteAction("Löschen", CWUtils.loadIcon("cw/customermanagementmodul/images/user_delete.png"));

        customerSelectorPresentationModel = new CustomerSelectorPresentationModel(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    }

    private void initEventHandling() {
        customerSelectorPresentationModel.addListSelectionListener(new SelectionHandler());
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
            GUIManager.setLoadingScreenText("Formular wird geladen...");
            GUIManager.setLoadingScreenVisible(true);

            new Thread(new Runnable() {

                public void run() {

                    final Customer c = new Customer();
                    final EditCustomerPresentationModel model = new EditCustomerPresentationModel(c, "Kunde erstellen");
                    final EditCustomerView editView = new EditCustomerView(model);
                    model.addButtonListener(new ButtonListener() {

                        boolean customerAlreadyCreated = false;

                        public void buttonPressed(ButtonEvent evt) {
                            if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                                CustomerManager.saveCustomer(c);
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
                            }
                        }
                    });

                    GUIManager.changeView(editView.buildPanel(), true);
                    GUIManager.setLoadingScreenVisible(false);

                }
            }).start();

        }
    }

    private class EditAction
            extends AbstractAction {

        private EditAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            editSelectedItem(e);
        }
    }

    private class DeleteAction
            extends AbstractAction {

        private DeleteAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {

            GUIManager.setLoadingScreenText("Kunde wird gelöscht...");
            GUIManager.setLoadingScreenVisible(true);

            new Thread(new Runnable() {

                public void run() {
                    Customer c = customerSelectorPresentationModel.getSelectedCustomer();

                    String forename = c.getForename();
                    String surname = c.getSurname();

                    customerSelectorPresentationModel.remove(c);
                    CustomerManager.removeCustomer(c);

                    GUIManager.setLoadingScreenVisible(false);
                    GUIManager.getStatusbar().setTextAndFadeOut("'" + forename + " " + surname + "' wurde gelöscht.");
                }
            }).start();
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////

    public String getHeaderText() {
        return headerText;
    }

    public Action getNewAction() {
        return newAction;
    }

    public Action getDeleteAction() {
        return deleteAction;
    }

    public Action getEditAction() {
        return editAction;
    }

    public CustomerSelectorPresentationModel getCustomerSelectorPresentationModel() {
        return customerSelectorPresentationModel;
    }

    public void editSelectedItem(EventObject e) {
        GUIManager.setLoadingScreenText("Kunde wird geladen...");
        GUIManager.setLoadingScreenVisible(true);

        new Thread(new Runnable() {

            public void run() {

                final Customer c = customerSelectorPresentationModel.getSelectedCustomer();
                final EditCustomerPresentationModel model = new EditCustomerPresentationModel(c, "Kunde bearbeiten");
                final EditCustomerView editView = new EditCustomerView(model);
                model.addButtonListener(new ButtonListener() {

                    public void buttonPressed(ButtonEvent evt) {
                        if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                            CustomerManager.saveCustomer(c);
                            GUIManager.getStatusbar().setTextAndFadeOut("Kunde wurde aktualisiert.");
                        }
                        if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                            model.removeButtonListener(this);
                            GUIManager.changeToLastView();
                        }
                    }
                });
                GUIManager.changeView(editView.buildPanel(), true);
                GUIManager.setLoadingScreenVisible(false);

            }
        }).start();
    }




    // Event Handling *********************************************************
    private void updateActionEnablement() {
        boolean hasSelection = !customerSelectorPresentationModel.isSelectionEmpty();

        System.out.println("hasSelection: " + hasSelection);
        System.out.println("mode: " + customerSelectorPresentationModel.getSelectionMode());
        switch(customerSelectorPresentationModel.getSelectionMode()) {
            case ListSelectionModel.SINGLE_SELECTION: System.out.println("SINGLE");
            case ListSelectionModel.MULTIPLE_INTERVAL_SELECTION: System.out.println("MULTIPLE");
            case ListSelectionModel.SINGLE_INTERVAL_SELECTION: System.out.println("SINGLE_INTERVAL");
        }

        if(!hasSelection) {
            editAction.setEnabled(false);
            deleteAction.setEnabled(false);
        } else if (customerSelectorPresentationModel.getSelectionMode() == ListSelectionModel.SINGLE_SELECTION) {
            editAction.setEnabled(hasSelection);
            deleteAction.setEnabled(hasSelection);
        } else if (customerSelectorPresentationModel.getSelectionMode() == ListSelectionModel.MULTIPLE_INTERVAL_SELECTION) {
            if(customerSelectorPresentationModel.getSelectedCount() == 1) {
                editAction.setEnabled(true);
                deleteAction.setEnabled(true);
            } else {
                editAction.setEnabled(false);
                deleteAction.setEnabled(true);
            }
        }

    }

    private final class SelectionHandler implements PropertyChangeListener, ListSelectionListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateActionEnablement();
        }

        public void valueChanged(ListSelectionEvent e) {
            updateActionEnablement();
        }
    }
}
