package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import cw.customermanagementmodul.pojo.Group;
import javax.swing.Icon;

/**
 *
 * @author ManuelG
 */
public class EditGroupPresentationModel
    extends PresentationModel<Group>
{

    private Action saveButtonAction;
    private Action cancelButtonAction;

    private CWHeaderInfo headerInfo;
    private ValueModel unsaved;
    private ButtonListenerSupport support;

    private SaveListener saveListener;
    private PropertyChangeListener unsavedListener;

    public EditGroupPresentationModel(Group group, CWHeaderInfo headerInfo) {
        super(group);
        this.headerInfo = headerInfo;

        initModels();
        initEventHandling();
    }

    private void initModels() {
        unsaved = new ValueHolder();
        support = new ButtonListenerSupport();

        saveButtonAction = new SaveAction("Speichern", CWUtils.loadIcon("cw/customermanagementmodul/images/disk_16.png"));
        cancelButtonAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/customermanagementmodul/images/cancel.png"));
    }

    private void initEventHandling() {

        saveListener = new SaveListener();
        getBufferedModel(Group.PROPERTYNAME_NAME).addValueChangeListener(saveListener);

        unsaved.addValueChangeListener(unsavedListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if((Boolean)evt.getNewValue() == true) {
                    saveButtonAction.setEnabled(true);
                } else {
                    saveButtonAction.setEnabled(false);
                }
            }
        });
        unsaved.setValue(false);
    }

    public void dispose() {
        getBufferedModel(Group.PROPERTYNAME_NAME).removeValueChangeListener(saveListener);
        
        unsaved.removeValueChangeListener(unsavedListener);

        release();
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
            triggerCommit();
            unsaved.setValue(false);
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
        }
    }

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
                triggerCommit();
            }
            if(i == 0 || i == 1) {
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Button Listener Support
    ////////////////////////////////////////////////////////////////////////////

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    
    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////

    public Action getCancelButtonAction() {
        return cancelButtonAction;
    }

    public Action getSaveButtonAction() {
        return saveButtonAction;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

}
