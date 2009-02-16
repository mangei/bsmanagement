package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import cw.customermanagementmodul.pojo.Posting;
import cw.customermanagementmodul.pojo.PostingCategory;
import cw.customermanagementmodul.pojo.manager.PostingCategoryManager;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditPostingPresentationModel
        extends PresentationModel<Posting> {

    private Posting posting;
    private SelectionInList<PostingCategory> postingCategorySelection;
    private ValueModel unsaved;
    private ValueModel editMode;
    private HeaderInfo headerInfo;
    
    private Action resetAction;
    private Action saveAction;
    private Action cancelAction;
    private Action saveCancelAction;
    private Action reversePostingAction;
    
    private ButtonListenerSupport buttonListenerSupport;
    
    public EditPostingPresentationModel(Posting posting, HeaderInfo headerInfo) {
        this(posting, false, headerInfo);
    }

    public EditPostingPresentationModel(Posting posting, boolean editMode, HeaderInfo headerInfo) {
        super(posting);
        this.posting = posting;
        this.editMode = new ValueHolder(editMode);
        this.headerInfo = headerInfo;

        buttonListenerSupport = new ButtonListenerSupport();
        
        initModels();
        initEventHandling();
    }
    
    public void initModels() {
        buttonListenerSupport = new ButtonListenerSupport();
        
        saveAction = new SaveAction("Speichern", CWUtils.loadIcon("cw/customermanagementmodul/images/disk_16.png"));
        resetAction = new ResetAction("Zurücksetzen", CWUtils.loadIcon("cw/customermanagementmodul/images/arrow_rotate_anticlockwise.png"));
        cancelAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/customermanagementmodul/images/cancel.png"));
        saveCancelAction = new SaveCancelAction("Speichern u. Schließen", CWUtils.loadIcon("cw/customermanagementmodul/images/save_cancel.png"));
        reversePostingAction = new ReversePostingAction("Stornieren", CWUtils.loadIcon("cw/customermanagementmodul/images/money_delete.png"));

        List<PostingCategory> postingCategories = PostingCategoryManager.getInstance().getAll();
        postingCategories.add(0, null);
        postingCategorySelection = new SelectionInList<PostingCategory>(postingCategories);
        postingCategorySelection.setSelection(getBean().getPostingCategory());

        getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE).addValueChangeListener(new SaveListener());
        getBufferedModel(Posting.PROPERTYNAME_AMOUNT).addValueChangeListener(new SaveListener());
        getBufferedModel(Posting.PROPERTYNAME_DESCRIPTION).addValueChangeListener(new SaveListener());
        getBufferedModel(Posting.PROPERTYNAME_LIABILITIESASSETS).addValueChangeListener(new SaveListener());
        getBufferedModel(Posting.PROPERTYNAME_CATEGORY).addValueChangeListener(new SaveListener());
    }
    
    public void initEventHandling() {
        unsaved = new ValueHolder();
        unsaved.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if((Boolean)evt.getNewValue() == true) {
                    saveAction.setEnabled(true);
                    resetAction.setEnabled(true);
                    saveCancelAction.setEnabled(true);
                } else {
                    saveAction.setEnabled(false);
                    resetAction.setEnabled(false);
                    saveCancelAction.setEnabled(false);
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
        buttonListenerSupport.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        buttonListenerSupport.addButtonListener(listener);
    }

    public SelectionInList<PostingCategory> getPostingCategorySelection() {
        return postingCategorySelection;
    }
    
    public Action getSaveAction() {
        return saveAction;
    }

    public Action getResetAction() {
        return resetAction;
    }

    public Action getCancelAction() {
        return cancelAction;
    }

    public Action getSaveCancelAction() {
        return saveCancelAction;
    }

    public Action getReversePostingAction() {
        return reversePostingAction;
    }

    public ValueModel getUnsaved() {
        return unsaved;
    }

    public ValueModel getEditMode() {
        return editMode;
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    private class SaveAction
            extends AbstractAction {

        public SaveAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            save();
            buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
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
                buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.RESET_BUTTON));
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
                buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));
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
            buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
        }
    }

    private class ReversePostingAction
            extends AbstractAction {

        public ReversePostingAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.OWN_BUTTON, "reversePostingButton"));
        }
    }

    public void save() {
        getBufferedModel(Posting.PROPERTYNAME_POSTINGDATE).setValue(new Date());
        triggerCommit();
        unsaved.setValue(false);
        editMode.setValue(true);
    }
    
    public void reset() {
        triggerFlush();
    }
}
