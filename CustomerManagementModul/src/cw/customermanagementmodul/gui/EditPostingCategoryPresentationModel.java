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
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import cw.customermanagementmodul.pojo.Posting;
import cw.customermanagementmodul.pojo.PostingCategory;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditPostingCategoryPresentationModel
        extends PresentationModel<PostingCategory> {

    private PostingCategory postingCategory;
    private ValueModel unsaved;
    
    private Action resetButtonAction;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    
    private ButtonListenerSupport support;
    
    public EditPostingCategoryPresentationModel(PostingCategory postingCategory) {
        super(postingCategory);
        this.postingCategory = postingCategory;

        support = new ButtonListenerSupport();
        
        initModels();
        initEventHandling();
    }
    
    public void initModels() {
        support = new ButtonListenerSupport();
        
        saveButtonAction = new SaveAction("Speichern", CWUtils.loadIcon("cw/customermanagementmodul/images/disk_16.png"));
        resetButtonAction = new ResetAction("Zurücksetzen", CWUtils.loadIcon("cw/customermanagementmodul/images/arrow_rotate_anticlockwise.png"));
        cancelButtonAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/customermanagementmodul/images/cancel.png"));
        saveCancelButtonAction = new SaveCancelAction("Speichern u. Schließen", CWUtils.loadIcon("cw/customermanagementmodul/images/save_cancel.png"));

        getBufferedModel(PostingCategory.PROPERTYNAME_KEY).addValueChangeListener(new SaveListener());
    }
    
    public void initEventHandling() {
        unsaved = new ValueHolder();
        unsaved.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if((Boolean)evt.getNewValue() == true) {
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

    private class SaveAction
            extends AbstractAction {

        public SaveAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            save();
            unsaved.setValue(false);
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
        }
    }
    
    private class ResetAction
            extends AbstractAction {

        public ResetAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            int i = JOptionPane.showConfirmDialog(null, "Wollen Sie alle Änderungen verwerfen?");
            if(i == JOptionPane.OK_OPTION) {
                reset();
                unsaved.setValue(false);
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.RESET_BUTTON));
            }
        }
    }
    
    private class CancelAction
            extends AbstractAction {

        public CancelAction(String name, Icon icon) {
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
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));
            }
        }
    }
    
    private class SaveCancelAction
            extends AbstractAction {
        
        public SaveCancelAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            save();
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
        }
    }
    
    public void save() {
        getBufferedModel(Posting.PROPERTYNAME_POSTINGDATE).setValue(new Date());
        triggerCommit();
    }
    
    public void reset() {
        triggerFlush();
    }
}