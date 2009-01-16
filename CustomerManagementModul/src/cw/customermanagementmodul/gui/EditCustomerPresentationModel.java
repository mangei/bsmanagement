package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import cw.boardingschoolmanagement.manager.ModulManager;
import cw.customermanagementmodul.extentions.interfaces.EditCustomerGUITabExtention;
import cw.customermanagementmodul.pojo.Customer;
import javax.swing.Icon;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCustomerPresentationModel
        extends PresentationModel<Customer> {

    private Customer customer;
    private ValueModel unsaved;
    private String headerText;
    private Action resetButtonAction;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    private ButtonListenerSupport support;

    public EditCustomerPresentationModel(Customer customer) {
        this(customer, "");
    }

    public EditCustomerPresentationModel(Customer customer, String headerText) {
        super(customer);
        this.customer = customer;
        this.headerText = headerText;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        unsaved = new ValueHolder();

        // Addons initialisieren
        List<EditCustomerGUITabExtention> extentions = getExtentions();
        for (EditCustomerGUITabExtention extention : extentions) {
            System.out.println("Extention: " + extention.toString());
            extention.initPresentationModel(customer, unsaved);
        }

        saveButtonAction = new SaveAction("Speichern", CWUtils.loadIcon("cw/customermanagementmodul/images/disk_16.png"));
        resetButtonAction = new ResetAction("Zurücksetzen", CWUtils.loadIcon("cw/customermanagementmodul/images/arrow_rotate_anticlockwise.png"));
        cancelButtonAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/customermanagementmodul/images/cancel.png"));
        saveCancelButtonAction = new SaveCancelAction("Speichern u. Schließen", CWUtils.loadIcon("cw/customermanagementmodul/images/save_cancel.png"));

        support = new ButtonListenerSupport();

//        PropertyConnector.connectAndUpdate(
//                    unsaved,
//                    saveButtonAction,
//                    ComponentValueModel.PROPERTYNAME_ENABLED);
//        PropertyConnector.connectAndUpdate(
//                    unsaved,
//                    resetButtonAction,
//                    ComponentValueModel.PROPERTYNAME_ENABLED);
//        PropertyConnector.connectAndUpdate(
//                    unsaved,
//                    saveCancelButtonAction,
//                    ComponentValueModel.PROPERTYNAME_ENABLED);

        getBufferedModel(Customer.PROPERTYNAME_TITLE).addValueChangeListener(new SaveListener());
        getBufferedModel(Customer.PROPERTYNAME_FORENAME).addValueChangeListener(new SaveListener());
        getBufferedModel(Customer.PROPERTYNAME_FORENAME2).addValueChangeListener(new SaveListener());
        getBufferedModel(Customer.PROPERTYNAME_SURNAME).addValueChangeListener(new SaveListener());
        getBufferedModel(Customer.PROPERTYNAME_STREET).addValueChangeListener(new SaveListener());
        getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).addValueChangeListener(new SaveListener());
        getBufferedModel(Customer.PROPERTYNAME_CITY).addValueChangeListener(new SaveListener());
        getBufferedModel(Customer.PROPERTYNAME_MOBILEPHONE).addValueChangeListener(new SaveListener());
        getBufferedModel(Customer.PROPERTYNAME_LANDLINEPHONE).addValueChangeListener(new SaveListener());
        getBufferedModel(Customer.PROPERTYNAME_FAX).addValueChangeListener(new SaveListener());
        getBufferedModel(Customer.PROPERTYNAME_EMAIL).addValueChangeListener(new SaveListener());
        getBufferedModel(Customer.PROPERTYNAME_COMMENT).addValueChangeListener(new SaveListener());
        getBufferedModel(Customer.PROPERTYNAME_BIRTHDAY).addValueChangeListener(new SaveListener());
    }

    public void initEventHandling() {
        unsaved.addValueChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if ((Boolean) evt.getNewValue() == true) {
                    saveButtonAction.setEnabled(true);
                    resetButtonAction.setEnabled(true);
                    saveCancelButtonAction.setEnabled(true);
                } else {
                    saveButtonAction.setEnabled(false);
                    resetButtonAction.setEnabled(false);
                    saveCancelButtonAction.setEnabled(false);
                }
            }
        });
        unsaved.setValue(false);
    }

    /**
     * Wenn sich ein Document ändert, wird saved auf false gesetzt
     */
    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            unsaved.setValue(true);
        }
    }

    public List<JComponent> getExtentionComponents() {
        List<JComponent> comps = new ArrayList<JComponent>();
        List<EditCustomerGUITabExtention> aList = (List<EditCustomerGUITabExtention>) ModulManager.getExtentions(EditCustomerGUITabExtention.class);
        for (EditCustomerGUITabExtention ex : aList) {
            comps.add((ex).getView());
        }
        return comps;
    }

    public List<EditCustomerGUITabExtention> getExtentions() {
        List<EditCustomerGUITabExtention> addons = new ArrayList<EditCustomerGUITabExtention>();
        List<EditCustomerGUITabExtention> aList = (List<EditCustomerGUITabExtention>) ModulManager.getExtentions(EditCustomerGUITabExtention.class);
        for (EditCustomerGUITabExtention a : aList) {
            addons.add(a);
        }
        return addons;
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    public Action getSaveButtonAction() {
        return saveButtonAction;
    }

    public Action getResetButtonAction() {
        return resetButtonAction;
    }

    public Action getCancelButtonAction() {
        return cancelButtonAction;
    }

    public Action getSaveCancelButtonAction() {
        return saveCancelButtonAction;
    }

    public String getHeaderText() {
        return headerText;
    }

    private class SaveAction
            extends AbstractAction {

        private SaveAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
//            saveCustomer();
            triggerCommit();
            unsaved.setValue(false);

            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));

            List<EditCustomerGUITabExtention> addons = getExtentions();
            for (EditCustomerGUITabExtention addon : addons) {
                addon.save();
            }
        }
    }

    private class ResetAction
            extends AbstractAction {

        private ResetAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            int i = JOptionPane.showConfirmDialog(null, "Wollen Sie alle Änderungen verwerfen?");
            if (i == JOptionPane.OK_OPTION) {
                resetCustomer();
                unsaved.setValue(false);
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.RESET_BUTTON));
            }
        }
    }

    private class CancelAction
            extends AbstractAction {

        private CancelAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            int i = 1;
            if ((Boolean) unsaved.getValue() == true) {
                Object[] options = {"Speichern", "Nicht Speichern", "Abbrechen"};
                i = JOptionPane.showOptionDialog(null, "Daten wurden geändert. Wollen Sie die Änderungen speichern?", "Speichern", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            }
            if (i == 0) {
                saveCustomer();
            }
            if (i == 0 || i == 1) {
//                GUIManager.lastView();  // Zur Übersicht wechseln
//                GUIManager.removeView(); // Diese View nicht merken
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));
            }
        }
    }

    private class SaveCancelAction
            extends AbstractAction {

        private SaveCancelAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
//            saveCustomer();
            triggerCommit();

            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));

            List<EditCustomerGUITabExtention> addons = getExtentions();
            for (EditCustomerGUITabExtention addon : addons) {
                addon.save();
            }
        }
    }

    public void saveCustomer() {

        triggerCommit();

        List<EditCustomerGUITabExtention> addons = getExtentions();
        for (EditCustomerGUITabExtention addon : addons) {
            addon.save();
        }
    }

    public void resetCustomer() {

        triggerFlush();

        List<EditCustomerGUITabExtention> addons = getExtentions();
        for (EditCustomerGUITabExtention addon : addons) {
            addon.reset();
        }
    }

    public ValueModel getUnsaved() {
        return unsaved;
    }
}
