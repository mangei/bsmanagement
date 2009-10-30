package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.comparator.PriorityComparator;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.ModulManager;
import cw.boardingschoolmanagement.pojo.PresentationModelProperties;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import cw.customermanagementmodul.extention.point.EditCustomerTabExtentionPoint;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Guardian;
import cw.customermanagementmodul.pojo.manager.CustomerManager;
import java.util.Collections;
import javax.swing.Icon;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCustomerPresentationModel
        extends PresentationModel<Customer>
{

    private PresentationModelProperties properties;
    private Customer customer;
    private ValueModel unsaved;
    private CWHeaderInfo headerInfo;
    private Action saveAction;
    private Action cancelAction;
    private Action saveCancelAction;
    private ButtonListenerSupport support;
    private PresentationModel<Guardian> guardianPresentationModel;
    private List<EditCustomerTabExtentionPoint> editCustomerGUITabExtentions;
    private PropertyChangeListener actionButtonListener;
    private SaveListener saveListener;

    public EditCustomerPresentationModel(PresentationModelProperties properties) {
        super((Customer)properties.get("customer"));
        this.customer = (Customer)properties.get("customer");
        this.headerInfo = properties.getHeaderInfo();
        this.properties = properties;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        unsaved = new ValueHolder();

        guardianPresentationModel = new PresentationModel<Guardian>(getBean().getGuardian());

        // Init Extentions
        editCustomerGUITabExtentions = getExtentions();
        for (EditCustomerTabExtentionPoint extention : editCustomerGUITabExtentions) {
            extention.initPresentationModel(this);
        }

        saveAction = new SaveAction("Speichern", CWUtils.loadIcon("cw/customermanagementmodul/images/disk_16.png"));
        cancelAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/customermanagementmodul/images/cancel.png"));
        saveCancelAction = new SaveCancelAction("Speichern u. Schließen", CWUtils.loadIcon("cw/customermanagementmodul/images/save_cancel.png"));

        support = new ButtonListenerSupport();
    }

    public void initEventHandling() {
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

        guardianPresentationModel.getBufferedModel(Guardian.PROPERTYNAME_ACTIVE).addValueChangeListener(saveListener);
        guardianPresentationModel.getBufferedModel(Guardian.PROPERTYNAME_TITLE).addValueChangeListener(saveListener);
        guardianPresentationModel.getBufferedModel(Guardian.PROPERTYNAME_GENDER).addValueChangeListener(saveListener);
        guardianPresentationModel.getBufferedModel(Guardian.PROPERTYNAME_FORENAME).addValueChangeListener(saveListener);
        guardianPresentationModel.getBufferedModel(Guardian.PROPERTYNAME_SURNAME).addValueChangeListener(saveListener);

        unsaved.addValueChangeListener(actionButtonListener = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if ((Boolean) evt.getNewValue() == true) {
                    saveAction.setEnabled(true);
                    saveCancelAction.setEnabled(true);
                } else {
                    saveAction.setEnabled(false);
                    saveCancelAction.setEnabled(false);
                }
            }
        });
        unsaved.setValue(false);
    }

    public void dispose() {

        for (EditCustomerTabExtentionPoint extention : editCustomerGUITabExtentions) {
            extention.dispose();
        }
        editCustomerGUITabExtentions.clear();

        unsaved.removeValueChangeListener(actionButtonListener);
        actionButtonListener = null;

        if (saveListener != null) {
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
        guardianPresentationModel.release();

//        PropertyChangeListener[] beanPropertyChangeListeners = super.getBeanPropertyChangeListeners();
//        for (PropertyChangeListener l : beanPropertyChangeListeners) {
//            removeBeanPropertyChangeListener(l);
//        }
//        PropertyChangeListener[] propertyChangeListeners = super.getPropertyChangeListeners();
//        for (PropertyChangeListener l : propertyChangeListeners) {
//            removePropertyChangeListener(l);
//        }
//        VetoableChangeListener[] vetoableChangeListeners = super.getVetoableChangeListeners();
//        for (VetoableChangeListener l : vetoableChangeListeners) {
//            removeVetoableChangeListener(l);
//        }

//        editCustomerGUITabExtentions.clear();

        // Kill references
//        properties = null;
//        customer = null;
//        unsaved = null;
//        headerInfo = null;
//        saveAction = null;
//        cancelAction = null;
//        saveCancelAction = null;
//        support = null;
//        guardianPresentationModel = null;
//        editCustomerGUITabExtentions = null;
//        actionButtonListener = null;
//        saveListener = null;

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
        for (EditCustomerTabExtentionPoint ex : editCustomerGUITabExtentions) {
            comps.add((ex).getView());
        }
        return comps;
    }

    public List<EditCustomerTabExtentionPoint> getExtentions() {
        if (editCustomerGUITabExtentions == null) {
            editCustomerGUITabExtentions = (List<EditCustomerTabExtentionPoint>) ModulManager.getExtentions(EditCustomerTabExtentionPoint.class);

            Collections.sort(editCustomerGUITabExtentions, new PriorityComparator());

//            for(EditCustomerTabExtention ex: editCustomerGUITabExtentions) {
//                System.out.println(ex);
//            }
//
//            System.out.println("----BEFORE-----");
//
//            // Order the extentions
//            for(int i=0, l=editCustomerGUITabExtentions.size(); i<l; i++) {
//                for(int j=i+1, k=editCustomerGUITabExtentions.size(); j<k; j++) {
//                    System.out.println("CHECK " + "(" + i + " " + j + ") -> " + editCustomerGUITabExtentions.get(i).priority() + " < " + editCustomerGUITabExtentions.get(j).priority());
//                    System.out.println("  ex1: " + editCustomerGUITabExtentions.get(i) + " -> " + editCustomerGUITabExtentions.get(i).priority());
//                    System.out.println("  ex2: " + editCustomerGUITabExtentions.get(j) + " -> " + editCustomerGUITabExtentions.get(j).priority());
//                    if(editCustomerGUITabExtentions.get(i).priority() > editCustomerGUITabExtentions.get(j).priority()) {
//                        System.out.println(" SWAP " + "(" + i + " " + j + ") -> " + editCustomerGUITabExtentions.get(i).priority() + " < " + editCustomerGUITabExtentions.get(j).priority());
//                        System.out.println("   ex1: " + editCustomerGUITabExtentions.get(i));
//                        System.out.println("   ex2: " + editCustomerGUITabExtentions.get(j));
//                        Collections.swap(editCustomerGUITabExtentions, i, j);
//                        break;
//                    }
//                }
//            }
//
//            System.out.println("-----AFTER-----");
//
//            for(EditCustomerTabExtention ex: editCustomerGUITabExtentions) {
//                System.out.println(ex);
//            }

        }
        return editCustomerGUITabExtentions;
    }

    public EditCustomerTabExtentionPoint getExtention(Class extentionClass) {

        for (EditCustomerTabExtentionPoint ex : editCustomerGUITabExtentions) {
            if (extentionClass.isInstance(ex)) {
                return ex;
            }
        }

        return null;
    }

    public PresentationModelProperties getProperties() {
        return properties;
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    public Action getSaveAction() {
        return saveAction;
    }

    public Action getCancelAction() {
        return cancelAction;
    }

    public Action getSaveCancelAction() {
        return saveCancelAction;
    }

    public CWHeaderInfo getHeaderInfo() {
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
            if (save()) {
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
                if (!save()) {
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
            if (!save()) {
                return;
            }

            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
        }
    }

    public boolean save() {
        boolean valid = true;

        List<EditCustomerTabExtentionPoint> extentions = getExtentions();
        List<String> errorMessages = new ArrayList<String>();
        for (EditCustomerTabExtentionPoint extention : extentions) {
            List<String> errorList = extention.validate();
            if (errorList != null) {
                if (errorList.size() > 0) {
                    valid = false;
                    errorMessages.addAll(errorList);
                }
            }
        }

        if (!valid) {

            StringBuffer buffer = new StringBuffer("<html>");

            for (String message : errorMessages) {
                buffer.append(message);
                buffer.append("<br>");
            }

            buffer.append("</html>");

            JOptionPane.showMessageDialog(null, buffer.toString(), "Fehler und Warnungen", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        // Alle Werte in das Objekt schreiben
        triggerCommit();
        guardianPresentationModel.triggerCommit();

        // Den Kunden speichern (Guardian wird automatisch mittels Hibernate-Cascade mitgespeichert)
        CustomerManager.getInstance().save(getBean());

        // Die Erweiterungen speichern lassen
        for (EditCustomerTabExtentionPoint extention : extentions) {
            extention.save();
        }

        unsaved.setValue(false);

        return true;
    }

    public ValueModel getUnsaved() {
        return unsaved;
    }
}
