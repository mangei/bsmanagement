package cw.studentmanagementmodul.student.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.studentmanagementmodul.organisationunit.persistence.OrganisationUnit;
import cw.studentmanagementmodul.organisationunit.persistence.PMOrganisationUnit;
import cw.studentmanagementmodul.student.persistence.PMStudentClass;
import cw.studentmanagementmodul.student.persistence.StudentClass;

/**
 *
 * @author ManuelG
 */
public class EditStudentClassPresentationModel 
        extends CWEditPresentationModel<StudentClass>
{

    private ButtonListenerSupport buttonListenerSupport;
    private ValueModel unsaved;
    private CWHeaderInfo headerInfo;

    private SelectionInList<OrganisationUnit> selectionOrganisationUnit;
    private SelectionInList<StudentClass> selectionStudentClass;

//    private Action resetButtonAction;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;

    private PropertyChangeListener unsavedListener;
    private SaveListener saveListener;
    private PropertyConnector organisationUnitPropertyConnector;
    private PropertyConnector nextStudentClassPropertyConnector;
    
    public EditStudentClassPresentationModel(StudentClass studentClass, CWHeaderInfo headerInfo) {
        super(studentClass);
        
        this.headerInfo = headerInfo;
        
        initModels();
        initEventHandling();
    }

    private void initModels() {
        unsaved = new ValueHolder();
        buttonListenerSupport = new ButtonListenerSupport();

        saveButtonAction = new SaveAction("Speichern", CWUtils.loadIcon("cw/boardingschoolmanagement/images/save.png"));
//        resetButtonAction = new ResetAction("Zuruecksetzen", CWUtils.loadIcon("cw/boardingschoolmanagement/images/arrow_rotate_anticlockwise.png"));
        cancelButtonAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/boardingschoolmanagement/images/cancel.png"));
        saveCancelButtonAction = new SaveCancelAction("Speichern u. Schließen", CWUtils.loadIcon("cw/boardingschoolmanagement/images/save_cancel.png"));

        List<OrganisationUnit> organisationUnitList = PMOrganisationUnit.getInstance().getAll();
        selectionOrganisationUnit = new SelectionInList<OrganisationUnit>(organisationUnitList);
        selectionOrganisationUnit.setSelection(getBean().getOrganisationUnit());

        List<StudentClass> studentClassList = PMStudentClass.getInstance().getAll();
        studentClassList.add(0, null);
        // Remove self
        studentClassList.remove(getBean());
        selectionStudentClass = new SelectionInList<StudentClass>(studentClassList);
        selectionStudentClass.setSelection(getBean().getNextStudentClass());
    }

    private void initEventHandling() {
        organisationUnitPropertyConnector = PropertyConnector.connect(getBufferedModel(StudentClass.PROPERTYNAME_ORGANISATIONUNIT), "value",  selectionOrganisationUnit, "selection");
//        organisationUnitPropertyConnector.updateProperty2();
        nextStudentClassPropertyConnector = PropertyConnector.connect(getBufferedModel(StudentClass.PROPERTYNAME_NEXTSTUDENTCLASS), "value",  selectionStudentClass, "selection");
//        organisationUnitPropertyConnector.updateProperty2();

        // Already done above
//        PropertyConnector.connectAndUpdate(getBufferedModel(StudentClass.PROPERTYNAME_ORGANISATIONUNIT), selectionOrganisationUnit, "selection");
//        PropertyConnector.connectAndUpdate(getBufferedModel(StudentClass.PROPERTYNAME_NEXTSTUDENTCLASS), selectionStudentClass, "selection");

        saveListener = new SaveListener();
        getBufferedModel(StudentClass.PROPERTYNAME_NAME).addValueChangeListener(saveListener);
        getBufferedModel(StudentClass.PROPERTYNAME_ORGANISATIONUNIT).addValueChangeListener(saveListener);
        getBufferedModel(StudentClass.PROPERTYNAME_NEXTSTUDENTCLASS).addValueChangeListener(saveListener);

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
        nextStudentClassPropertyConnector.release();

        getBufferedModel(StudentClass.PROPERTYNAME_NAME).removeValueChangeListener(saveListener);
        getBufferedModel(StudentClass.PROPERTYNAME_ORGANISATIONUNIT).removeValueChangeListener(saveListener);
        getBufferedModel(StudentClass.PROPERTYNAME_NEXTSTUDENTCLASS).removeValueChangeListener(saveListener);

        unsaved.removeValueChangeListener(unsavedListener);
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
//            int i = JOptionPane.showConfirmDialog(null, "Wollen Sie alle Aenderungen verwerfen?");
//            if(i == JOptionPane.OK_OPTION) {
//                triggerFlush();
//                unsaved.setValue(false);
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
               i = JOptionPane.showOptionDialog(null, "Daten wurden geaendert. Wollen Sie die Aenderungen speichern?", "Speichern", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null,  options, options[0] );
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

        // remove from the studentclass list of the old organisationunit
        if(getBean().getOrganisationUnit() != null) {
            getBean().getOrganisationUnit().getStudentClasses().remove(getBean());
        }

        triggerCommit();

        // Add to the studentclasses list of the organisationunit
        if(getBean().getOrganisationUnit() != null) {
            getBean().getOrganisationUnit().getStudentClasses().add(getBean());
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

    public SelectionInList<StudentClass> getSelectionStudentClass() {
        return selectionStudentClass;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

}