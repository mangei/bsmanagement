package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import cw.studentmanagementmodul.pojo.OrganisationUnit;
import cw.studentmanagementmodul.pojo.manager.OrganisationUnitManager;
import javax.swing.Icon;

/**
 *
 * @author ManuelG
 */
public class EditOrganisationUnitPresentationModel 
        extends PresentationModel<OrganisationUnit>
{

    private ButtonListenerSupport buttonListenerSupport;
    private ValueModel unsaved;
    private CWHeaderInfo headerInfo;

//    private Action resetButtonAction;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    
    private SelectionInList<OrganisationUnit> selectionOrganisationUnit;

    private PropertyChangeListener unsavedListener;
    private SaveListener saveListener;
    private PropertyConnector organisationUnitPropertyConnector;
    
    public EditOrganisationUnitPresentationModel(OrganisationUnit organisationUnit, CWHeaderInfo headerInfo) {
        super(organisationUnit);
        
        this.headerInfo = headerInfo;
        
        initModels();
        initEventHandling();
    }

    private void initModels() {
        unsaved = new ValueHolder();
        buttonListenerSupport = new ButtonListenerSupport();

        saveButtonAction = new SaveAction("Speichern", CWUtils.loadIcon("cw/boardingschoolmanagement/images/save.png"));
//        resetButtonAction = new ResetAction("Zurücksetzen", CWUtils.loadIcon("cw/boardingschoolmanagement/images/arrow_rotate_anticlockwise.png"));
        cancelButtonAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/boardingschoolmanagement/images/cancel.png"));
        saveCancelButtonAction = new SaveCancelAction("Speichern u. Schließen", CWUtils.loadIcon("cw/boardingschoolmanagement/images/save_cancel.png"));

        // Load all organisationUnits
        List<OrganisationUnit> organisationUnitList = OrganisationUnitManager.getInstance().getAll();
        // Add the null object, if there is no parent
        organisationUnitList.add(0, null);
        // Remove self
        organisationUnitList.remove(getBean());
        // Remove children
        removeChildren(organisationUnitList, getBean());

        selectionOrganisationUnit = new SelectionInList<OrganisationUnit>(organisationUnitList);
        selectionOrganisationUnit.setSelection(getBean().getParent());
    }

    private void initEventHandling() {

        organisationUnitPropertyConnector = PropertyConnector.connect(getBufferedModel(OrganisationUnit.PROPERTYNAME_PARENT), "value",  selectionOrganisationUnit, "selection");
//        organisationUnitPropertyConnector.updateProperty2();

        // Already done above
//        PropertyConnector.connectAndUpdate(getBufferedModel(OrganisationUnit.PROPERTYNAME_PARENT), selectionOrganisationUnit, "selection");

        saveListener = new SaveListener();
        getBufferedModel(OrganisationUnit.PROPERTYNAME_NAME).addValueChangeListener(saveListener);
        getBufferedModel(OrganisationUnit.PROPERTYNAME_PARENT).addValueChangeListener(saveListener);

        unsaved.addValueChangeListener(unsavedListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if((Boolean)evt.getNewValue() == true) {
                    saveButtonAction.setEnabled(true);
//                    resetButtonAction.setEnabled(true);
                    saveCancelButtonAction.setEnabled(true);
                } else {
                    saveButtonAction.setEnabled(false);
//                    resetButtonAction.setEnabled(false);
                    saveCancelButtonAction.setEnabled(false);
                }
            }
        });
        unsaved.setValue(false);
    }

    public void dispose() {
        organisationUnitPropertyConnector.release();

        getBufferedModel(OrganisationUnit.PROPERTYNAME_NAME).removeValueChangeListener(saveListener);
        getBufferedModel(OrganisationUnit.PROPERTYNAME_PARENT).removeValueChangeListener(saveListener);

        unsaved.removeValueChangeListener(unsavedListener);

        release();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Useful methods
    ////////////////////////////////////////////////////////////////////////////

    private void removeChildren(List<OrganisationUnit> list, OrganisationUnit org) {
        if(org == null) {
            return;
        }

        OrganisationUnit child;
        for(int i=0,l=org.getChildren().size(); i<l; i++) {

            // Get the child
            child = org.getChildren().get(i);

            // Remove the children of the child
            removeChildren(list, child);

            // Remove the child
            list.remove(child);

        }
    }
    

    ////////////////////////////////////////////////////////////////////////////
    // Useful classes
    ////////////////////////////////////////////////////////////////////////////

    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            unsaved.setValue(true);
        }
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
            save();
            buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
        }
    }

//    private class ResetAction
//            extends AbstractAction {
//
//        private ResetAction(String name, Icon icon) {
//            super(name, icon);
//        }
//
//        public void actionPerformed(ActionEvent e) {
//            int i = JOptionPane.showConfirmDialog(null, "Wollen Sie alle Änderungen verwerfen?");
//            if(i == JOptionPane.OK_OPTION) {
//                triggerFlush();
//                buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.RESET_BUTTON));
//            }
//        }
//    }

    private class CancelAction
            extends AbstractAction {

        private CancelAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            int i = 1;
            if((Boolean)unsaved.getValue() == true) {
                Object[] options = { "Speichern", "Nicht Speichern", "Abbrechen" };
               i = JOptionPane.showOptionDialog(null, "Daten wurden geändert. Wollen Sie die Änderungen speichern?", "Speichern", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null,  options, options[0] );
            }
            if(i == 0) {
                save();
            }
            if(i == 0 || i == 1) {
                buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));
            }
        }
    }

    private class SaveCancelAction
            extends AbstractAction {

        private SaveCancelAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            save();
            buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
        }
    }

    public void save() {

        // Remove from old parent
        if(getBean().getParent() != null) {
            getBean().getParent().getChildren().remove(getBean());
        }

        triggerCommit();

        // Add to new parent
        if(getBean().getParent() != null) {
            getBean().getParent().getChildren().add(getBean());
        }

        unsaved.setValue(false);
    }

    ////////////////////////////////////////////////////////////////////////////
    // ButtonListenerSupport
    ////////////////////////////////////////////////////////////////////////////

    public void removeButtonListener(ButtonListener listener) {
        buttonListenerSupport.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        buttonListenerSupport.addButtonListener(listener);
    }

    
    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////

    public Action getCancelButtonAction() {
        return cancelButtonAction;
    }

//    public Action getResetButtonAction() {
//        return resetButtonAction;
//    }

    public Action getSaveButtonAction() {
        return saveButtonAction;
    }

    public Action getSaveCancelButtonAction() {
        return saveCancelButtonAction;
    }

    public SelectionInList<OrganisationUnit> getSelectionOrganisationUnit() {
        return selectionOrganisationUnit;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

}
