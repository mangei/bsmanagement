package cw.customermanagementmodul.customer.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWAction;
import cw.boardingschoolmanagement.app.CWEntityManager;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.pojo.PresentationModelProperties;
import cw.customermanagementmodul.customer.extention.EditCustomerEditCustomerTabExtention;
import cw.customermanagementmodul.customer.logic.BoCustomer;
import cw.customermanagementmodul.customer.persistence.Customer;
import cw.customermanagementmodul.customer.persistence.PMCustomer;

/**
 * 
 * 
 * @author Manuel Geier
 * 
 */
public class CustomerManagementPresentationModel
	extends CWPresentationModel {

    private CWAction newAction;
    private CWAction editAction;
    private CWAction deleteAction;
    private CWAction inactiveAction;
    private CWAction viewInactivesAction;
    private CWAction printAction;
    private CustomerSelectorPresentationModel customerSelectorPresentationModel;
    private CWHeaderInfo headerInfo;
    private SelectionHandler selectionHandler;

    public CustomerManagementPresentationModel() {
    	super(CWEntityManager.createEntityManager());
        initModels();
        initEventHandling();
    }

    public void initModels() {
        newAction = new NewAction("Neu", CWUtils.loadIcon("cw/customermanagementmodul/images/user_add.png"));
        editAction = new EditAction("Bearbeiten", CWUtils.loadIcon("cw/customermanagementmodul/images/user_edit.png"));
        deleteAction = new DeleteAction("Loeschen", CWUtils.loadIcon("cw/customermanagementmodul/images/user_delete.png"));
        inactiveAction = new InactivesAction("Inaktiv setzen", CWUtils.loadIcon("cw/customermanagementmodul/images/user_inactive_go.png"));
        viewInactivesAction = new ViewInactivesAction("Inaktive anzeigen", CWUtils.loadIcon("cw/customermanagementmodul/images/user_inactives.png"));
        printAction = new PrintAction("Drucken", CWUtils.loadIcon("cw/coursemanagementmodul/images/print.png"));

        customerSelectorPresentationModel = new CustomerSelectorPresentationModel(
                PMCustomer.getInstance().getAllActive(getEntityManager()),
                "cw.customerboardingmanagement.CustomerManangementView.customerTableState",
                getEntityManager()
        );

        headerInfo = new CWHeaderInfo(
                "Kunden verwalten",
                "Sie befinden sich Kundenverwaltungsbereich. Hier haben Sie einen Ueberblick ueber alle Kunden.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png")
        );
    }

    private void initEventHandling() {
        customerSelectorPresentationModel.getCustomerDataModel().addValueChangeListener(selectionHandler = new SelectionHandler());
        updateActionEnablement();
    }

    public void release() {
        customerSelectorPresentationModel.getCustomerDataModel().removeValueChangeListener(selectionHandler);

        // Kill references
        newAction = null;
        editAction = null;
        deleteAction = null;
        inactiveAction = null;
        viewInactivesAction = null;
        
        CWEntityManager.closeEntityManager(getEntityManager());
    }

    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////
    private class NewAction
            extends CWAction {

        private NewAction(String name, Icon icon) {
            super(name, icon);
        }

        public void action(ActionEvent e) {
            GUIManager.getInstance().lockMenu();
            GUIManager.setLoadingScreenText("Formular wird geladen...");
            GUIManager.setLoadingScreenVisible(true);

            //final Customer c = CustomerPM.getInstance().create(getEntityManager());
            PresentationModelProperties p = new PresentationModelProperties();
            
            p.put(PresentationModelProperties.HEADERINFO,
                    new CWHeaderInfo(
                    "Kunden erstellen",
                    "Bearbeiten sie hier alle Informationen ueber Ihren Kunden.",
                    CWUtils.loadIcon("cw/customermanagementmodul/images/user_add.png"),
                    CWUtils.loadIcon("cw/customermanagementmodul/images/user_add.png")));
            p.put("activeExtention", EditCustomerEditCustomerTabExtention.class);

            final EditCustomerPresentationModel model = new EditCustomerPresentationModel();
            final EditCustomerView editView = new EditCustomerView(model);

            final Customer c = model.getBean();
            
            final PropertyChangeListener activeListener = new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    if ((Boolean) evt.getNewValue() == true) {
                        customerSelectorPresentationModel.getCustomerDataModel().add(c);
                    } else {
                        customerSelectorPresentationModel.getCustomerDataModel().remove(c);
                    }
                }
            };

            model.addButtonListener(new ButtonListener() {

                boolean customerAlreadyCreated = false;

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {

                        if (customerAlreadyCreated) {
                            GUIManager.getStatusbar().setTextAndFadeOut("Kunde wurde aktualisiert.");

                            // Check if the customer is active or not
                            c.addPropertyChangeListener(Customer.PROPERTYNAME_ACTIVE, activeListener);

                        } else {
                            GUIManager.getStatusbar().setTextAndFadeOut("Kunde wurde erstellt.");
                            customerAlreadyCreated = true;
                            if (c.isActive()) {
                                customerSelectorPresentationModel.getCustomerDataModel().add(c);
                            }
                        }
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        c.removePropertyChangeListener(Customer.PROPERTYNAME_ACTIVE, activeListener);
                        GUIManager.changeToPreviousView();
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

            GUIManager.changeViewTo(editView, true);
            GUIManager.setLoadingScreenVisible(false);

        }
    }

    private class EditAction
            extends CWAction {

        private EditAction(String name, Icon icon) {
            super(name, icon);
        }

        public void action(ActionEvent e) {
            final Customer c = customerSelectorPresentationModel.getCustomerDataModel().getSelection();
            final EditCustomerPresentationModel model = editCustomer(c.getId());

            final PropertyChangeListener activeListener = new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {

                    if ((Boolean) evt.getNewValue() == true) {
                        customerSelectorPresentationModel.getCustomerDataModel().add(c);
                    } else {
                        customerSelectorPresentationModel.getCustomerDataModel().remove(c);
                    }
                }
            };

            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON || evt.getType() == ButtonEvent.EXIT_BUTTON) {
                        c.removePropertyChangeListener(Customer.PROPERTYNAME_ACTIVE, activeListener);
                        model.removeButtonListener(this);
                    }
                }
            });

            c.addPropertyChangeListener(Customer.PROPERTYNAME_ACTIVE, activeListener);
        }
    }

    private class DeleteAction
            extends CWAction {

        private DeleteAction(String name, Icon icon) {
            super(name, icon);
        }

        public void action(ActionEvent e) {

            GUIManager.setLoadingScreenText("Kunden loeschen...");
            GUIManager.setLoadingScreenVisible(true);

            Customer customer = customerSelectorPresentationModel.getCustomerDataModel().getSelection();

            String[] options = {"Inaktiv setzen", "Loeschen", "Abbrechen"};

            int i = JOptionPane.showOptionDialog(
                    null,
                    "Wollen Sie wirklich den ausgewaehlten Kunden loeschen oder nur deaktivieren?",
                    "Kunden loeschen",
                    JOptionPane.OK_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[2]);

            if (i == 1) {

            	customerSelectorPresentationModel.getCustomerDataModel().remove(customer);
            	
            	// Remove entity
            	BoCustomer boCustomer = customer.getTypedAdapter(BoCustomer.class);
            	boCustomer.remove();
           	 	CWEntityManager.commit(getEntityManager());
            	
                String statusBarText;
                String forename = customer.getForename();
                String surname = customer.getSurname();
                statusBarText = "'" + forename + " " + surname + "' wurde geloescht.";

                GUIManager.getStatusbar().setTextAndFadeOut(statusBarText);
            } else if (i == 0) {

                customerSelectorPresentationModel.getCustomerDataModel().remove(customer);
                
                BoCustomer boCustomer = customer.getTypedAdapter(BoCustomer.class);
                boCustomer.deactivate();
                CWEntityManager.commit(getEntityManager());
            }

            GUIManager.setLoadingScreenVisible(false);
        }
    }

    /**
     * Innere Klasse die zum inaktive setzen der Kunden benoetigt wird.
     */
    private class InactivesAction
            extends CWAction {

        private InactivesAction(String name, Icon icon) {
            super(name, icon);
        }

        /**
         * Liest den momentan markierten Kunden aus der View, setzt diesen
         * Inaktiv und entfernt ihn aus der Anzeige fuer aktive Kunden.
         * Speicher den inaktiv gesetzten Kunden wieder in die Datenbank;
         *
         * @param e ActionEvent
         */
        public void action(ActionEvent e) {

            Customer c = customerSelectorPresentationModel.getCustomerDataModel().getSelection();
            customerSelectorPresentationModel.getCustomerDataModel().remove(c);

            BoCustomer boCustomer = c.getTypedAdapter(BoCustomer.class);
            boCustomer.deactivate();
            CWEntityManager.commit(getEntityManager());

            GUIManager.getStatusbar().setTextAndFadeOut("Kunde wurde deaktiviert.");
        }
    }

    private class ViewInactivesAction
            extends CWAction {

        private ViewInactivesAction(String name, Icon icon) {
            super(name, icon);
        }

        public void action(ActionEvent e) {

            GUIManager.setLoadingScreenText("Inaktive Kunden werden geladen...");
            GUIManager.setLoadingScreenVisible(true);

            final CustomerInactivePresentationModel model = new CustomerInactivePresentationModel(getEntityManager());
            final CustomerInactiveView view = new CustomerInactiveView(model);

            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    customerSelectorPresentationModel.setCustomers(PMCustomer.getInstance().getAllActive(getEntityManager()));
                }
            });

            GUIManager.changeViewTo(CWComponentFactory.createBackView(view), true);

            GUIManager.setLoadingScreenVisible(false);

        }
    }
    
    private class PrintAction extends CWAction {
        {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/print.png") );
        }

        private PrintAction(String name, Icon icon) {
            super(name, icon);
        }

        public void action(ActionEvent e) {

            GUIManager.getInstance().lockMenu();
            GUIManager.changeViewTo(new CustomerPrintView(
                    new CustomerPrintPresentationModel(
                    		PMCustomer.getInstance().getAllActive(getEntityManager()), 
                    		null, 
                    		getEntityManager())
                    ),
                    true
            );
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
    
    public Action getPrintAction() {
        return printAction;
    }

    public CustomerSelectorPresentationModel getCustomerSelectorPresentationModel() {
        return customerSelectorPresentationModel;
    }
    
    public static EditCustomerPresentationModel editCustomer(Long customerId) {
        GUIManager.getInstance().lockMenu();
        GUIManager.setLoadingScreenText("Kunde wird geladen...");
        GUIManager.setLoadingScreenVisible(true);

        final EditCustomerPresentationModel model = new EditCustomerPresentationModel(customerId);
        final EditCustomerView editView = new EditCustomerView(model);

        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    GUIManager.getStatusbar().setTextAndFadeOut("Kunde wurde aktualisiert.");
                }
                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    model.removeButtonListener(this);
                    GUIManager.changeToPreviousView();
                    GUIManager.getInstance().unlockMenu();
                }
            }
        });

        GUIManager.changeViewTo(editView, true);
        GUIManager.setLoadingScreenVisible(false);

        return model;
    }


    // Event Handling *********************************************************
    private void updateActionEnablement() {

        boolean hasSelection = !customerSelectorPresentationModel.getCustomerDataModel().isSelectionEmpty();

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
