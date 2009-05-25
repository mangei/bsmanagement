package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import javax.swing.event.ListSelectionEvent;
import cw.customermanagementmodul.pojo.manager.CustomerManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.pojo.PresentationModelProperties;
import cw.customermanagementmodul.extentions.EditCustomerEditCustomerTabExtention;
import cw.customermanagementmodul.extentions.interfaces.CustomerOverviewEditCustomerExtention;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.ListSelectionListener;
import cw.customermanagementmodul.pojo.Customer;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * @author CreativeWorkers.at
 */
public class CustomerManagementPresentationModel
{

    private Action newAction;
    private Action editAction;
    private Action deleteAction;
    private Action inactiveAction;
    private Action viewInactivesAction;

    private CustomerSelectorPresentationModel customerSelectorPresentationModel;

    private CWHeaderInfo headerInfo;
    
    private SelectionHandler selectionHandler;

    public CustomerManagementPresentationModel() {
        initModels();
        initEventHandling();
    }

    public void initModels() {
        newAction = new NewAction("Neu", CWUtils.loadIcon("cw/customermanagementmodul/images/user_add.png"));
        editAction = new EditAction("Bearbeiten", CWUtils.loadIcon("cw/customermanagementmodul/images/user_edit.png"));
        deleteAction = new DeleteAction("Löschen", CWUtils.loadIcon("cw/customermanagementmodul/images/user_delete.png"));
        inactiveAction = new InactivesAction("Inaktiv setzen", CWUtils.loadIcon("cw/customermanagementmodul/images/user_inactive_go.png"));
        viewInactivesAction = new ViewInactivesAction("Inaktive anzeigen", CWUtils.loadIcon("cw/customermanagementmodul/images/user_inactives.png"));

        customerSelectorPresentationModel = new CustomerSelectorPresentationModel(
                CustomerManager.getInstance().getAllActive(),
                "cw.customerboardingmanagement.CustomerManangementView.customerTableState"
                );
        
        headerInfo = new CWHeaderInfo(
                "Kunden verwalten",
                "Sie befinden sich Kundenverwaltungsbereich. Hier haben Sie einen Überblick über alle Kunden.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png")
        );
    }

    private void initEventHandling() {
        customerSelectorPresentationModel.getCustomerSelection().addValueChangeListener(selectionHandler = new SelectionHandler());
        updateActionEnablement();
    }

    public void dispose() {
        customerSelectorPresentationModel.getCustomerSelection().removeValueChangeListener(selectionHandler);

        // Kill references
        newAction = null;
        editAction = null;
        deleteAction = null;
        inactiveAction = null;
        viewInactivesAction = null;
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
            PresentationModelProperties p = new PresentationModelProperties();
            p.put("customer", c);
            p.put(PresentationModelProperties.HEADERINFO,
                    new CWHeaderInfo(
                        "Kunden erstellen",
                        "Bearbeiten sie hier alle Informationen über Ihren Kunden.",
                        CWUtils.loadIcon("cw/customermanagementmodul/images/user_add.png"),
                        CWUtils.loadIcon("cw/customermanagementmodul/images/user_add.png")
            ));
            p.put("activeExtention", EditCustomerEditCustomerTabExtention.class);
            final EditCustomerPresentationModel model = new EditCustomerPresentationModel(p);
            final EditCustomerView editView = new EditCustomerView(model);

            final PropertyChangeListener activeListener = new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    if((Boolean)evt.getNewValue() == true) {
                        System.out.println("add");
                        customerSelectorPresentationModel.add(c);
                    } else {
                        System.out.println("remove");
                        customerSelectorPresentationModel.remove(c);
                    }
                }
            };

            model.addButtonListener(new ButtonListener() {

                boolean customerAlreadyCreated = false;

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        CustomerManager.getInstance().save(c);
                        if (customerAlreadyCreated) {
                            GUIManager.getStatusbar().setTextAndFadeOut("Kunde wurde aktualisiert.");

                            // Check if the customer is active or not
                            c.addPropertyChangeListener(Customer.PROPERTYNAME_ACTIVE, activeListener);

                        } else {
                            GUIManager.getStatusbar().setTextAndFadeOut("Kunde wurde erstellt.");
                            customerAlreadyCreated = true;
                            if(c.isActive()) {
                                customerSelectorPresentationModel.add(c);
                            }
                        }
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        c.removePropertyChangeListener(Customer.PROPERTYNAME_ACTIVE, activeListener);
                        GUIManager.changeToLastView();
                        GUIManager.getInstance().unlockMenu();
                    }
                }
            });

//            JPanel panel = editView.buildPanel();
//            final JDialog d = new JDialog(GUIManager.getInstance().getMainFrame(), true);
//            d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//            d.setTitle(panel.getName());
//            d.add(panel);
//            d.pack();
//            CWUtils.centerWindow(d, GUIManager.getInstance().getMainFrame());
//            d.setVisible(true);
//            d.dispose();

            GUIManager.changeView(editView, true);
            GUIManager.setLoadingScreenVisible(false);

        }
    }

    private class EditAction
            extends AbstractAction {

        private EditAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            final Customer c = customerSelectorPresentationModel.getSelectedCustomer();
            final EditCustomerPresentationModel model = editCustomer(c);

            final PropertyChangeListener activeListener = new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {

                    if((Boolean)evt.getNewValue() == true) {
                        customerSelectorPresentationModel.add(c);
                    } else {
                        customerSelectorPresentationModel.remove(c);
                    }
                }
            };

            model.addButtonListener(new ButtonListener() {
                public void buttonPressed(ButtonEvent evt) {
                    if(evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON || evt.getType() == ButtonEvent.EXIT_BUTTON) {
                        c.removePropertyChangeListener(Customer.PROPERTYNAME_ACTIVE, activeListener);
                        model.removeButtonListener(this);
                    }
                }
            });

            c.addPropertyChangeListener(Customer.PROPERTYNAME_ACTIVE, activeListener);
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

            String[] options = {"Inaktiv setzen", "Löschen", "Abbrechen"};

            int i = JOptionPane.showOptionDialog(
                    null, 
                    "Wollen Sie wirklich den ausgewählten Kunden löschen oder nur deaktivieren?",
                    "Kunden löschen",
                    JOptionPane.OK_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[2]);

            if (i == 1) {

                String statusBarText;
                String forename = customer.getForename();
                String surname = customer.getSurname();
                statusBarText = "'" + forename + " " + surname +  "' wurde gelöscht.";

                customerSelectorPresentationModel.remove(customer);
                CustomerManager.getInstance().delete(customer);

                GUIManager.getStatusbar().setTextAndFadeOut(statusBarText);
            } else if (i == 0) {
                customer.setActive(false);
                customerSelectorPresentationModel.remove(customer);
                CustomerManager.getInstance().save(customer);
            }

            GUIManager.setLoadingScreenVisible(false);
        }
    }

    private class InactivesAction
            extends AbstractAction {

        private InactivesAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {

            Customer c = customerSelectorPresentationModel.getSelectedCustomer();
            c.setActive(false);
            customerSelectorPresentationModel.remove(c);
            CustomerManager.getInstance().save(c);

            GUIManager.getStatusbar().setTextAndFadeOut("Kunde wurde deaktiviert.");
            
        }
    }

    private class ViewInactivesAction
            extends AbstractAction {

        private ViewInactivesAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {

            GUIManager.setLoadingScreenText("Inaktive Kunden werden geladen...");
            GUIManager.setLoadingScreenVisible(true);

            final CustomerInactivePresentationModel model = new CustomerInactivePresentationModel();
            final CustomerInactiveView view = new CustomerInactiveView(model);

            model.addButtonListener(new ButtonListener() {
                public void buttonPressed(ButtonEvent evt) {
                    customerSelectorPresentationModel.setCustomers(CustomerManager.getInstance().getAllActive());
                }
            });

            GUIManager.changeView(CWComponentFactory.createBackView(view), true);

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

    public Action getViewInactivesAction() {
        return viewInactivesAction;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public CustomerSelectorPresentationModel getCustomerSelectorPresentationModel() {
        return customerSelectorPresentationModel;
    }

    public static EditCustomerPresentationModel editCustomer(final Customer c) {
        GUIManager.getInstance().lockMenu();
        GUIManager.setLoadingScreenText("Kunde wird geladen...");
        GUIManager.setLoadingScreenVisible(true);


        PresentationModelProperties p = new PresentationModelProperties();
        p.put("customer", c);
        p.put(PresentationModelProperties.HEADERINFO,
                new CWHeaderInfo(
                    "Kunden bearbeiten",
                    "Bearbeiten sie hier alle Informationen über Ihren Kunden.",
                    CWUtils.loadIcon("cw/customermanagementmodul/images/user_edit.png"),
                    CWUtils.loadIcon("cw/customermanagementmodul/images/user_edit.png")
        ));
        p.put("activeExtention", CustomerOverviewEditCustomerExtention.class);
        final EditCustomerPresentationModel model = new EditCustomerPresentationModel(p);
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

        GUIManager.changeView(editView, true);
        GUIManager.setLoadingScreenVisible(false);

        return model;
    }


    // Event Handling *********************************************************
    private void updateActionEnablement() {
        boolean hasSelection = !customerSelectorPresentationModel.getCustomerSelection().isSelectionEmpty();

        editAction.setEnabled(hasSelection);
        deleteAction.setEnabled(hasSelection);
        inactiveAction.setEnabled(hasSelection);
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
