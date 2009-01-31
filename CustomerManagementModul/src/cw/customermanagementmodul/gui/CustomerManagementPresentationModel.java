package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
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
import java.util.EventObject;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import cw.customermanagementmodul.pojo.Customer;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * @author CreativeWorkers.at
 */
public class CustomerManagementPresentationModel {

    private Action newAction;
    private Action editAction;
    private Action deleteAction;

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

        customerSelectorPresentationModel = new CustomerSelectorPresentationModel(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        headerInfo = new HeaderInfo(
                "Kunden verwalten",
                "Sie befinden sich Kundenverwaltungsbereich. Hier haben Sie einen Überblick über alle Kunden.<br>test",
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png")
        );
    }

    private void initEventHandling() {
        customerSelectorPresentationModel.addListSelectionListener(new SelectionHandler());
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

            new Thread(new Runnable() {

                public void run() {

                    final Customer c = new Customer();
                    final EditCustomerPresentationModel model = new EditCustomerPresentationModel(c, new HeaderInfo("Kunden erstellen"));
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

            GUIManager.setLoadingScreenText("Kunden löschen...");
            GUIManager.setLoadingScreenVisible(true);

            new Thread(new Runnable() {

                public void run() {
                    List<Customer> customers = customerSelectorPresentationModel.getSelectedCustomers();

                    int i = JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich die ausgewählten Kunden löschen?", "Kunden löschen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (i == JOptionPane.OK_OPTION) {

                        String statusBarText;
                        if(customers.size() == 1) {
                            String forename = customers.get(0).getForename();
                            String surname = customers.get(0).getSurname();
                            statusBarText = "'" + forename + " " + surname +  "' wurde gelöscht.";
                        } else {
                            statusBarText = customers.size() + " Kunden wurden gelöscht.";
                        }

                        customerSelectorPresentationModel.remove(customers);
                        CustomerManager.getInstance().delete(customers);

                        GUIManager.getStatusbar().setTextAndFadeOut(statusBarText);
                    }

                    GUIManager.setLoadingScreenVisible(false);
                }
            }).start();
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

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public CustomerSelectorPresentationModel getCustomerSelectorPresentationModel() {
        return customerSelectorPresentationModel;
    }

    public void editSelectedItem(EventObject e) {
        GUIManager.getInstance().lockMenu();
        GUIManager.setLoadingScreenText("Kunde wird geladen...");
        GUIManager.setLoadingScreenVisible(true);

        new Thread(new Runnable() {

            public void run() {

                final Customer c = customerSelectorPresentationModel.getSelectedCustomer();
                final EditCustomerPresentationModel model = new EditCustomerPresentationModel(c, new HeaderInfo("Kunde bearbeiten"));
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
        }).start();
    }




    // Event Handling *********************************************************
    private void updateActionEnablement() {
        boolean hasSelection = !customerSelectorPresentationModel.isSelectionEmpty();

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
