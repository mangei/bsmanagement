package cw.customermanagementmodul.group.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWEntityManager;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.boardingschoolmanagement.gui.CWErrorMessage;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.customermanagementmodul.group.logic.BoGroup;
import cw.customermanagementmodul.group.persistence.Group;
import cw.customermanagementmodul.group.persistence.PMGroup;

/**
 *
 * @author ManuelG
 */
public class EditGroupPresentationModel
    extends CWEditPresentationModel<Group>
{

    private Action saveButtonAction;
    private Action cancelButtonAction;

    private CWHeaderInfo headerInfo;
    private ValueModel unsaved;
    private ButtonListenerSupport support;

    private SaveListener saveListener;
    private PropertyChangeListener unsavedListener;

    public EditGroupPresentationModel() {
		this(null);
	}
    
    public EditGroupPresentationModel(Long groupId) {
        super(CWEntityManager.createEntityManager(), EditGroupView.class);

        if(groupId == null) {
        	setBean(PMGroup.getInstance().create(getEntityManager()));
        	setMode(Mode.NEW);
        } else {
        	setBean(PMGroup.getInstance().get(groupId, getEntityManager()));
        	setMode(Mode.EDIT);
        }
        
        initModels();
        initEventHandling();
    }

    private void initModels() {
        unsaved = new ValueHolder();
        support = new ButtonListenerSupport();

        if(isNewMode()) {
        	headerInfo = new CWHeaderInfo("Gruppe erstellen");
        } else if(isEditMode()) {
        	headerInfo = new CWHeaderInfo("Gruppe bearbeiten");
        }
        
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
        
        CWEntityManager.closeEntityManager(getEntityManager());
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
            if((Boolean)unsaved.getValue() == true) {
                Object[] options = { "Speichern", "Nicht Speichern", "Abbrechen" };
               i = JOptionPane.showOptionDialog(null, "Daten wurden geaendert. Wollen Sie die Aenderungen speichern?", "Speichern", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null,  options, options[0] );
            }
            if(i == 0) {
                save();
            }
            if(i == 0 || i == 1) {
            	cancel();
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

	public boolean validate(List<CWErrorMessage> errorMessages) {
		
		validateExtentions(errorMessages);
		
		return !hasErrorMessages();
	}

	public void save() {
		triggerCommit();

		saveExtentions();
		
		CWEntityManager.commit(getEntityManager());

        unsaved.setValue(false);
	}

	public void cancel() {
		
		cancelExtentions();
		
		if(isNewMode()) {
        	
        	BoGroup boGroup = getBean().getTypedAdapter(BoGroup.class);
        	boGroup.remove();
        	CWEntityManager.commit(getEntityManager());
			
        } else if(isEditMode()) {
        	
        	CWEntityManager.rollback(getEntityManager());
        	
        }
		
		CWEntityManager.closeEntityManager(getEntityManager());
	}

}
