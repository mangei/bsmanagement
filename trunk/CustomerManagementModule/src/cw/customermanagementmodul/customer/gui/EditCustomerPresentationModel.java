package cw.customermanagementmodul.customer.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWEntityManager;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.comparator.PriorityComparator;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.boardingschoolmanagement.gui.CWErrorMessage;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.ModuleManager;
import cw.customermanagementmodul.customer.extention.point.EditCustomerTabExtentionPoint;
import cw.customermanagementmodul.customer.logic.BoCustomer;
import cw.customermanagementmodul.customer.persistence.Customer;
import cw.customermanagementmodul.customer.persistence.PMCustomer;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCustomerPresentationModel
        extends CWEditPresentationModel<Customer>
{

    private ValueModel unsaved;
    private CWHeaderInfo headerInfo;
    private Action saveAction;
    private Action cancelAction;
    private ButtonListenerSupport support;
    private List<EditCustomerTabExtentionPoint> editCustomerGUITabExtentions;
    private PropertyChangeListener actionButtonListener;
    private SaveListener saveListener;
    
    public EditCustomerPresentationModel() {
        this(null);
    }
    
    public EditCustomerPresentationModel(Long customerId) {
        super(CWEntityManager.createEntityManager(), EditCustomerView.class);

        if(customerId == null) {
        	setBean(PMCustomer.getInstance().create(getEntityManager()));
        	setMode(Mode.NEW);
        } else {
        	setBean(PMCustomer.getInstance().get(customerId, getEntityManager()));
        	setMode(Mode.EDIT);
        }
        
        initModels();
        initEventHandling();
    }

    public void initModels() {
        unsaved = new ValueHolder();

//        // initialize extentions
//        editCustomerGUITabExtentions = getExtentions();
//        for (EditCustomerTabExtentionPoint extention : editCustomerGUITabExtentions) {
//            extention.initPresentationModel(this, getEntityManager());
//        }

        if(isNewMode()) {
        	headerInfo =
                new CWHeaderInfo(
                "Neuer Kunde",
                "Bearbeiten sie hier alle Kundeninformationen.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/user_add.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/user_add.png"));
        } else if(isEditMode()) {
        	headerInfo =
                new CWHeaderInfo(
                "Kunden bearbeiten",
                "Bearbeiten sie hier alle Kundeninformationen.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/user_edit.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/user_edit.png"));
        }
        
        // top button actions
        saveAction = new SaveAction("Speichern", CWUtils.loadIcon("cw/customermanagementmodul/images/disk_16.png"));
        cancelAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/customermanagementmodul/images/cancel.png"));

        support = new ButtonListenerSupport();
    }

    public void initEventHandling() {
        saveListener = new SaveListener();
        getBufferedModel(Customer.PROPERTYNAME_ACTIVE).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_GENDER).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_TITLE).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_FORENAME).addValueChangeListener(saveListener);
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

        unsaved.addValueChangeListener(actionButtonListener = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if ((Boolean) evt.getNewValue() == true) {
                    saveAction.setEnabled(true);
                } else {
                    saveAction.setEnabled(false);
                }
            }
        });
        unsaved.setValue(false);
    }

    public void dispose() {
//
//        for (EditCustomerTabExtentionPoint extention : editCustomerGUITabExtentions) {
//            extention.dispose();
//        }
//        editCustomerGUITabExtentions.clear();

        unsaved.removeValueChangeListener(actionButtonListener);
        actionButtonListener = null;

        if (saveListener != null) {
            getBufferedModel(Customer.PROPERTYNAME_ACTIVE).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_GENDER).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_TITLE).removeValueChangeListener(saveListener);
            getBufferedModel(Customer.PROPERTYNAME_FORENAME).removeValueChangeListener(saveListener);
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
        }

        getBufferedModel(Customer.PROPERTYNAME_ACTIVE).release();
        getBufferedModel(Customer.PROPERTYNAME_GENDER).release();
        getBufferedModel(Customer.PROPERTYNAME_TITLE).release();
        getBufferedModel(Customer.PROPERTYNAME_FORENAME).release();
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

    }

    /**
     * Wenn sich ein Document aendert, wird saved auf false gesetzt
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

//    public List<JComponent> getExtentionComponents() {
//        List<JComponent> comps = new ArrayList<JComponent>();
//        for (EditCustomerTabExtentionPoint ex : editCustomerGUITabExtentions) {
//            comps.add((ex).getView());
//        }
//        return comps;
//    }

    public List<EditCustomerTabExtentionPoint> getExtentions() {
        if (editCustomerGUITabExtentions == null) {
            editCustomerGUITabExtentions = (List<EditCustomerTabExtentionPoint>) ModuleManager.getExtentions(EditCustomerTabExtentionPoint.class);

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

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
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
            if(isValid()) {
            	save();
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
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
                i = JOptionPane.showOptionDialog(null, "Daten wurden geaendert. Wollen Sie die Aenderungen speichern?", "Speichern", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            }
            if (i == 0) {

                // If the save-method doesn't worked, because of an error, do nothing
                if (isValid()) {
                	save();
                    return;
                }
            }
            if (i == 0 || i == 1) {
            	cancel();
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));
            }
        }
    }
    
    public boolean validate(List<CWErrorMessage> errorMessages) {
    	
    	// validate
    	
    	
        
        validateExtentions(errorMessages);
    	
    	return !hasErrorMessages();
    }
    
    public void cancel() {
        
        cancelExtentions();

        if(isNewMode()) {
        	
        	// remove the customer, if we cancel a new one
        	BoCustomer boCustomer = getBean().getTypedAdapter(BoCustomer.class);
        	boCustomer.remove();
        	CWEntityManager.commit(getEntityManager());
        	
        } else if(isEditMode()) {
        	
	        // revert all changes
	        CWEntityManager.rollback(getEntityManager());
	        
        }

    	CWEntityManager.closeEntityManager(getEntityManager());
    }

    public void save() {
    	super.save();

        // Alle Werte in das Objekt schreiben
        triggerCommit();
        
        saveExtentions();

        // Commit all changes
        getEntityManager().getTransaction().commit();
        CWEntityManager.closeEntityManager(getEntityManager());
        
        unsaved.setValue(false);
    }

    public ValueModel getUnsaved() {
        return unsaved;
    }
}
