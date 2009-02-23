package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.manager.ModulManager;
import cw.customermanagementmodul.extentions.interfaces.EditPostingPostingCategoryExtention;
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
import java.util.HashMap;
import javax.swing.JComponent;

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
    
    private Action saveAction;
    private Action cancelAction;
    private Action saveCancelAction;
    private Action reversePostingAction;

    private List<EditPostingPostingCategoryExtention> editPostingPostingCategoryExtentions;
    private HashMap<String,EditPostingPostingCategoryExtention> editPostingPostingCategoryExtentionsKeyMap;
    
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
        cancelAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/customermanagementmodul/images/cancel.png"));
        saveCancelAction = new SaveCancelAction("Speichern u. Schließen", CWUtils.loadIcon("cw/customermanagementmodul/images/save_cancel.png"));
        reversePostingAction = new ReversePostingAction("Stornieren", CWUtils.loadIcon("cw/customermanagementmodul/images/posting_delete.png"));

        List<PostingCategory> postingCategories = PostingCategoryManager.getInstance().getAll();
        postingCategories.add(0, new PostingCategory("Keine"));
        postingCategorySelection = new SelectionInList<PostingCategory>(postingCategories);
        postingCategorySelection.setSelectionIndex(0);

        editPostingPostingCategoryExtentions = getExtentions();
        editPostingPostingCategoryExtentionsKeyMap = new HashMap<String, EditPostingPostingCategoryExtention>();

        // Initialize the extentions
        for(EditPostingPostingCategoryExtention ex : editPostingPostingCategoryExtentions) {
            ex.initPresentationModel(this);
            editPostingPostingCategoryExtentionsKeyMap.put(ex.getKey(), ex);
        }
    }
    
    public void initEventHandling() {
        unsaved = new ValueHolder();

        SaveListener saveListener = new SaveListener();
        getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE).addValueChangeListener(saveListener);
        getBufferedModel(Posting.PROPERTYNAME_AMOUNT).addValueChangeListener(saveListener);
        getBufferedModel(Posting.PROPERTYNAME_DESCRIPTION).addValueChangeListener(saveListener);
        getBufferedModel(Posting.PROPERTYNAME_LIABILITIESASSETS).addValueChangeListener(saveListener);
        getBufferedModel(Posting.PROPERTYNAME_CATEGORY).addValueChangeListener(saveListener);
        
        PropertyConnector.connectAndUpdate(getBufferedModel(Posting.PROPERTYNAME_CATEGORY), postingCategorySelection, "selection");
        
        unsaved.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if((Boolean)evt.getNewValue() == true) {
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

    private List<EditPostingPostingCategoryExtention> getExtentions() {
        if(editPostingPostingCategoryExtentions == null) {
            editPostingPostingCategoryExtentions = (List<EditPostingPostingCategoryExtention>) ModulManager.getExtentions(EditPostingPostingCategoryExtention.class);
        }
        return editPostingPostingCategoryExtentions;
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

    public JComponent getPostingCategoryExtentionComponent() {
        PostingCategory selection = postingCategorySelection.getSelection();
        if(postingCategorySelection.hasSelection()
                && selection.getKey() != null && !selection.getKey().isEmpty()) {
            return editPostingPostingCategoryExtentionsKeyMap.get(selection.getKey()).getView();
        }
        return null;
    }

    private class SaveAction
            extends AbstractAction {

        public SaveAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            if(!save()) return;
            buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
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

    public boolean save() {
        getBufferedModel(Posting.PROPERTYNAME_POSTINGDATE).setValue(new Date());
        if(postingCategorySelection.getSelectionIndex() == 0) {
            getBufferedModel(Posting.PROPERTYNAME_CATEGORY).setValue(null);
        }

//        for(EditPostingPostingCategoryExtention ex : getExtentions()) {
//            ex.validate();
//        }

        triggerCommit();
        unsaved.setValue(false);
        editMode.setValue(true);

        return true;
    }
    
}
