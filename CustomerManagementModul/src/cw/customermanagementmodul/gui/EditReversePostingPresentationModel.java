package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
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
public class EditReversePostingPresentationModel
        extends PresentationModel<Posting> {

    private PresentationModel<Posting> postingPresentationModel;
//    private PresentationModel<Posting> reversePostingPresentationModel;

    private Posting reversePosting;
    private Posting oldPosting;
    private SelectionInList<PostingCategory> postingCategorySelection;
    private ValueModel unsaved;
    
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;

    private List<EditPostingPostingCategoryExtention> editPostingPostingCategoryExtentions;
    private HashMap<String,EditPostingPostingCategoryExtention> editPostingPostingCategoryExtentionsKeyMap;

    private ButtonListenerSupport support;
    
    public EditReversePostingPresentationModel(Posting oldPosting, Posting reversePosting) {
        super(reversePosting);
        this.reversePosting = reversePosting;
        this.oldPosting = oldPosting;

        support = new ButtonListenerSupport();
        
        initModels();
        initEventHandling();
    }
    
    public void initModels() {
        support = new ButtonListenerSupport();
        
        cancelButtonAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/customermanagementmodul/images/cancel.png"));
        saveCancelButtonAction = new SaveCancelAction("Speichern u. Schließen", CWUtils.loadIcon("cw/customermanagementmodul/images/save_cancel.png"));

        List<PostingCategory> postingCategories = PostingCategoryManager.getInstance().getAll();
        postingCategories.add(0, null);
        postingCategorySelection = new SelectionInList<PostingCategory>(postingCategories);
        if(getBean().getPostingCategory() != null) {
            postingCategorySelection.setSelection(getBean().getPostingCategory());
        } else {
            postingCategorySelection.setSelectionIndex(0);
        }

        postingPresentationModel = new PresentationModel<Posting>(oldPosting);
//        reversePostingPresentationModel = new PresentationModel<Posting>((Posting)getBufferedModel(ReversePosting.PROPERTYNAME_REVERSEPOSTING).getValue());

        getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE).addValueChangeListener(new SaveListener());
        getBufferedModel(Posting.PROPERTYNAME_AMOUNT).addValueChangeListener(new SaveListener());
        getBufferedModel(Posting.PROPERTYNAME_DESCRIPTION).addValueChangeListener(new SaveListener());
        getBufferedModel(Posting.PROPERTYNAME_LIABILITIESASSETS).addValueChangeListener(new SaveListener());
        getBufferedModel(Posting.PROPERTYNAME_CATEGORY).addValueChangeListener(new SaveListener());

        
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
        unsaved.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if((Boolean)evt.getNewValue() == true) {
                    saveCancelButtonAction.setEnabled(true);
                } else {
                    saveCancelButtonAction.setEnabled(false);
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
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    public SelectionInList<PostingCategory> getPostingCategorySelection() {
        return postingCategorySelection;
    }
    
    public Action getCancelButtonAction() {
        return cancelButtonAction;
    }

    public Action getSaveCancelButtonAction() {
        return saveCancelButtonAction;
    }

    public PresentationModel<Posting> getPostingPresentationModel() {
        return postingPresentationModel;
    }


    public JComponent getPostingCategoryExtentionComponent() {
        PostingCategory selection = postingCategorySelection.getSelection();
        if(postingCategorySelection.hasSelection()
                && selection.getKey() != null
                && !selection.getKey().isEmpty()) {
            EditPostingPostingCategoryExtention get = editPostingPostingCategoryExtentionsKeyMap.get(selection.getKey());
            if(get != null) {
                return get.getView();
            }
        }
        return null;
    }
    
    private class CancelAction
            extends AbstractAction {

        public CancelAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            int i = JOptionPane.OK_OPTION;
            if((Boolean)unsaved.getValue() == true) {
               i = JOptionPane.showConfirmDialog(null, "Wollen Sie wirlich abbrechen?", "Abbrechen", JOptionPane.OK_CANCEL_OPTION);
            }
            if(i == JOptionPane.OK_OPTION) {
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
