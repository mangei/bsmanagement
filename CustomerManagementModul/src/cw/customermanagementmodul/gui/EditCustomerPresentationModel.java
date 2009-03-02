package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.comparator.PriorityComparator;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.boardingschoolmanagement.manager.ModulManager;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import cw.customermanagementmodul.extentions.interfaces.EditCustomerTabExtention;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Guardian;
import java.util.Collections;
import javax.swing.Icon;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCustomerPresentationModel
        extends PresentationModel<Customer>
        implements Disposable {

    private Customer customer;
    private ValueModel unsaved;
    private HeaderInfo headerInfo;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    private ButtonListenerSupport support;

    private PresentationModel<Guardian> guardianPresentationModel;

    private List<EditCustomerTabExtention> editCustomerGUITabExtentions;

    private PropertyChangeListener actionButtonListener;
    private SaveListener saveListener;

    public EditCustomerPresentationModel(Customer customer) {
        this(customer, new HeaderInfo());
    }

    public EditCustomerPresentationModel(Customer customer, HeaderInfo headerInfo) {
        super(customer);
        this.customer = customer;
        this.headerInfo = headerInfo;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        unsaved = new ValueHolder();

        guardianPresentationModel = new PresentationModel<Guardian>(getBean().getGuardian());

        // Addons initialisieren
        editCustomerGUITabExtentions = getExtentions();
        for (EditCustomerTabExtention extention : editCustomerGUITabExtentions) {
            System.out.println("Extention: " + extention.toString());
            extention.initPresentationModel(this);
        }

        saveButtonAction = new SaveAction("Speichern", CWUtils.loadIcon("cw/customermanagementmodul/images/disk_16.png"));
        cancelButtonAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/customermanagementmodul/images/cancel.png"));
        saveCancelButtonAction = new SaveCancelAction("Speichern u. Schließen", CWUtils.loadIcon("cw/customermanagementmodul/images/save_cancel.png"));

        support = new ButtonListenerSupport();

        saveListener = new SaveListener();
        getBufferedModel(Customer.PROPERTYNAME_ACTIVE).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_GENDER).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_TITLE).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_FORENAME).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_FORENAME2).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_SURNAME).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_STREET).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_CITY).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_MOBILEPHONE).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_LANDLINEPHONE).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_FAX).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_EMAIL).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_COMMENT).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_BIRTHDAY).addValueChangeListener(saveListener);

        guardianPresentationModel.getBufferedModel(Guardian.PROPERTYNAME_FORENAME).addValueChangeListener(saveListener);
        guardianPresentationModel.getBufferedModel(Guardian.PROPERTYNAME_SURNAME).addValueChangeListener(saveListener);
    }

    public void initEventHandling() {
        unsaved.addValueChangeListener(actionButtonListener = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if ((Boolean) evt.getNewValue() == true) {
                    saveButtonAction.setEnabled(true);
                    saveCancelButtonAction.setEnabled(true);
                } else {
                    saveButtonAction.setEnabled(false);
                    saveCancelButtonAction.setEnabled(false);
                }
            }
        });
        unsaved.setValue(false);
    }

    public void dispose() {

        for (EditCustomerTabExtention extention : editCustomerGUITabExtentions) {
            extention.dispose();
        }

        if(actionButtonListener != null) {
            unsaved.removeValueChangeListener(actionButtonListener);
            actionButtonListener = null;
        }
        if(saveListener != null) {
            getBufferedModel(Customer.PROPERTYNAME_ACTIVE).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_GENDER).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_TITLE).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_FORENAME).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_FORENAME2).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_SURNAME).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_STREET).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_CITY).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_MOBILEPHONE).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_LANDLINEPHONE).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_FAX).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_EMAIL).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_COMMENT).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_BIRTHDAY).removeValueChangeListener(saveListener);

            guardianPresentationModel.getBufferedModel(Guardian.PROPERTYNAME_FORENAME).removeValueChangeListener(saveListener);
            guardianPresentationModel.getBufferedModel(Guardian.PROPERTYNAME_SURNAME).removeValueChangeListener(saveListener);

            saveListener = null;
        }

        getBufferedModel(Customer.PROPERTYNAME_ACTIVE).release();
        getBufferedModel(Customer.PROPERTYNAME_GENDER).release();
        getBufferedModel(Customer.PROPERTYNAME_TITLE).release();
        getBufferedModel(Customer.PROPERTYNAME_FORENAME).release();
        getBufferedModel(Customer.PROPERTYNAME_FORENAME2).release();
        getBufferedModel(Customer.PROPERTYNAME_SURNAME).release();
        getBufferedModel(Customer.PROPERTYNAME_STREET).release();
        getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).release();
        getBufferedModel(Customer.PROPERTYNAME_CITY).release();
        getBufferedModel(Customer.PROPERTYNAME_MOBILEPHONE).release();
        getBufferedModel(Customer.PROPERTYNAME_LANDLINEPHONE).release();
        getBufferedModel(Customer.PROPERTYNAME_FAX).release();
        getBufferedModel(Customer.PROPERTYNAME_EMAIL).release();
        getBufferedModel(Customer.PROPERTYNAME_COMMENT).release();
        getBufferedModel(Customer.PROPERTYNAME_BIRTHDAY).release();
        guardianPresentationModel.getBufferedModel(Guardian.PROPERTYNAME_FORENAME).release();
        guardianPresentationModel.getBufferedModel(Guardian.PROPERTYNAME_SURNAME).release();

        PropertyChangeListener[] beanPropertyChangeListeners = super.getBeanPropertyChangeListeners();
        System.out.println("anz1: " + beanPropertyChangeListeners.length);
        for (PropertyChangeListener l : beanPropertyChangeListeners) {
            removeBeanPropertyChangeListener(l);
        }
        PropertyChangeListener[] propertyChangeListeners = super.getPropertyChangeListeners();
        System.out.println("anz2: " + propertyChangeListeners.length);
        for (PropertyChangeListener l : propertyChangeListeners) {
            removePropertyChangeListener(l);
        }
        VetoableChangeListener[] vetoableChangeListeners = super.getVetoableChangeListeners();
        System.out.println("anz3: " + vetoableChangeListeners.length);
        for (VetoableChangeListener l : vetoableChangeListeners) {
            removeVetoableChangeListener(l);
        }

        System.out.println("anz4: " + getBean().getVetoableChangeListeners().length);
        System.out.println("anz5: " + getBean().getPropertyChangeListeners().length);

        saveButtonAction = null;
        cancelButtonAction = null;
        saveCancelButtonAction = null;

        support = null;
        editCustomerGUITabExtentions.clear();
        editCustomerGUITabExtentions = null;
        customer = null;

        unsaved = null;
        release();
    }

    /**
     * Wenn sich ein Document ändert, wird saved auf false gesetzt
     */
    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
//            setChanged(true);
            unsaved.setValue(true);
        }
    }

    public List<JComponent> getExtentionComponents() {
        List<JComponent> comps = new ArrayList<JComponent>();
        for (EditCustomerTabExtention ex : editCustomerGUITabExtentions) {
            comps.add((ex).getView());
        }
        return comps;
    }

    public List<EditCustomerTabExtention> getExtentions() {
        if(editCustomerGUITabExtentions == null) {
            editCustomerGUITabExtentions = (List<EditCustomerTabExtention>) ModulManager.getExtentions(EditCustomerTabExtention.class);
            Collections.sort(editCustomerGUITabExtentions, new PriorityComparator());
        }
        return editCustomerGUITabExtentions;
    }

    public EditCustomerTabExtention getExtention(EditCustomerTabExtention extention) {

        for(EditCustomerTabExtention ex : editCustomerGUITabExtentions) {
            if(extention.getClass().isInstance(ex)) {
                return ex;
            }
        }

        return null;
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

    public Action getCancelButtonAction() {
        return cancelButtonAction;
    }

    public Action getSaveCancelButtonAction() {
        return saveCancelButtonAction;
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public PresentationModel<Guardian> getGuardianPresentationModel() {
        return guardianPresentationModel;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////

    private class SaveAction
            extends AbstractAction {

        private SaveAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {

            // Fire only when the save-method worked correct
            if(save()) {
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
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

                // If the save-method doesn't worked, because of an error, to nothing
                if(!save()) {
                    return;
                }
            }
            if (i == 0 || i == 1) {
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
            if(!save()) {
                return;
            }

            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
        }
    }

    public boolean save() {
        boolean valid = true;

        List<EditCustomerTabExtention> extentions = getExtentions();
        List<String> errorMessages = new ArrayList<String>();
        for (EditCustomerTabExtention extention : extentions) {
            List<String> errorList = extention.validate();
            if(errorList != null) {
                if(errorList.size() > 0) {
                    valid = false;
                    errorMessages.addAll(errorList);
                }
            }
        }

        if(!valid) {

            StringBuffer buffer = new StringBuffer("<html>");

            for(String message : errorMessages) {
                buffer.append(message);
                buffer.append("<br>");
            }

            buffer.append("</html>");

            JOptionPane.showMessageDialog(null, buffer.toString(), "Fehler und Warnungen", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        triggerCommit();

        for (EditCustomerTabExtention extention : extentions) {
            extention.save();
        }

        unsaved.setValue(false);

        return true;
    }

    public ValueModel getUnsaved() {
        return unsaved;
    }

}
