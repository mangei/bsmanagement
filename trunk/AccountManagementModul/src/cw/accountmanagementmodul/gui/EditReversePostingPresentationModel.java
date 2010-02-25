package cw.accountmanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.ModulManager;
import cw.accountmanagementmodul.extention.point.EditReversePostingPostingCategoryExtentionPoint;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import cw.accountmanagementmodul.pojo.Posting;
import cw.accountmanagementmodul.pojo.PostingCategory;
import cw.accountmanagementmodul.pojo.manager.PostingCategoryManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComponent;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditReversePostingPresentationModel
{

    private PresentationModel<Posting> postingPresentationModel;
    private PresentationModel<Posting> reversePostingPresentationModel;

    private Posting reversePosting;
    private Posting oldPosting;
    private SelectionInList<PostingCategory> postingCategorySelection;
    private ValueModel unsaved;
    private CWHeaderInfo headerInfo;

    private Action cancelButtonAction;
    private Action saveCancelButtonAction;

    private List<EditReversePostingPostingCategoryExtentionPoint> editReversePostingPostingCategoryExtentions;
    private HashMap<String,EditReversePostingPostingCategoryExtentionPoint> editPostingPostingCategoryExtentionsKeyMap;

    private ButtonListenerSupport support;

    private SaveListener saveListener;
//    private PropertyChangeListener unsavedListener;
    
    public EditReversePostingPresentationModel(Posting oldPosting, Posting reversePosting, CWHeaderInfo headerInfo) {
        this.reversePosting = reversePosting;
        this.oldPosting = oldPosting;
        this.headerInfo = headerInfo;
        
        initModels();
        initEventHandling();
    }
    
    public void initModels() {
        support = new ButtonListenerSupport();
        
        cancelButtonAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/customermanagementmodul/images/cancel.png"));
        saveCancelButtonAction = new SaveCancelAction("Buchen", CWUtils.loadIcon("cw/customermanagementmodul/images/posting_lightning.png"));

        List<PostingCategory> postingCategories = PostingCategoryManager.getInstance().getAll();
        postingCategories.add(0, null);
        postingCategorySelection = new SelectionInList<PostingCategory>(postingCategories);
        if(reversePosting.getPostingCategory() != null) {
            postingCategorySelection.setSelection(reversePosting.getPostingCategory());
        } else {
            postingCategorySelection.setSelectionIndex(0);
        }

        postingPresentationModel = new PresentationModel<Posting>(oldPosting);
        reversePostingPresentationModel = new PresentationModel<Posting>(reversePosting);

        saveListener = new SaveListener();
        reversePostingPresentationModel.getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE).addValueChangeListener(saveListener);
        reversePostingPresentationModel.getBufferedModel(Posting.PROPERTYNAME_AMOUNT).addValueChangeListener(saveListener);
        reversePostingPresentationModel.getBufferedModel(Posting.PROPERTYNAME_DESCRIPTION).addValueChangeListener(saveListener);
        reversePostingPresentationModel.getBufferedModel(Posting.PROPERTYNAME_LIABILITIESASSETS).addValueChangeListener(saveListener);
        reversePostingPresentationModel.getBufferedModel(Posting.PROPERTYNAME_CATEGORY).addValueChangeListener(saveListener);

        editReversePostingPostingCategoryExtentions = getExtentions();
        editPostingPostingCategoryExtentionsKeyMap = new HashMap<String, EditReversePostingPostingCategoryExtentionPoint>();

        // Load the extentions
        for(EditReversePostingPostingCategoryExtentionPoint ex : editReversePostingPostingCategoryExtentions) {
//            ex.initPresentationModel(this);
            editPostingPostingCategoryExtentionsKeyMap.put(ex.getKey(), ex);
        }
        // Initialize the selected Extention
        if(oldPosting.getPostingCategory() != null) {
            EditReversePostingPostingCategoryExtentionPoint ex = editPostingPostingCategoryExtentionsKeyMap.get(oldPosting.getPostingCategory().getKey());
            if(ex != null) {
                ex.initPresentationModel(this);
            }
        }
    }
    
    public void initEventHandling() {
        unsaved = new ValueHolder();
//        unsaved.addValueChangeListener(unsavedListener = new PropertyChangeListener() {
//            public void propertyChange(PropertyChangeEvent evt) {
//                if((Boolean)evt.getNewValue() == true) {
//                    saveCancelButtonAction.setEnabled(true);
//                } else {
//                    saveCancelButtonAction.setEnabled(false);
//                }
//            }
//        });
        unsaved.setValue(false);
        saveCancelButtonAction.setEnabled(true);
    }

    public void dispose() {
//        for(EditReversePostingPostingCategoryExtention ex : editReversePostingPostingCategoryExtentions) {
//            ex.initPresentationModel(this);
//            editPostingPostingCategoryExtentionsKeyMap.put(ex.getKey(), ex);
//        }

        reversePostingPresentationModel.getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE).removeValueChangeListener(saveListener);
        reversePostingPresentationModel.getBufferedModel(Posting.PROPERTYNAME_AMOUNT).removeValueChangeListener(saveListener);
        reversePostingPresentationModel.getBufferedModel(Posting.PROPERTYNAME_DESCRIPTION).removeValueChangeListener(saveListener);
        reversePostingPresentationModel.getBufferedModel(Posting.PROPERTYNAME_LIABILITIESASSETS).removeValueChangeListener(saveListener);
        reversePostingPresentationModel.getBufferedModel(Posting.PROPERTYNAME_CATEGORY).removeValueChangeListener(saveListener);


        if(postingCategorySelection.hasSelection() && postingCategorySelection.getSelectionIndex() != 0) {
            EditReversePostingPostingCategoryExtentionPoint ex = editPostingPostingCategoryExtentionsKeyMap.get(postingCategorySelection.getSelection().getKey());
            if(ex != null) {
                ex.dispose();
            }
        }

//        unsaved.removeValueChangeListener(unsavedListener);
    }

    private List<EditReversePostingPostingCategoryExtentionPoint> getExtentions() {
        if(editReversePostingPostingCategoryExtentions == null) {
            editReversePostingPostingCategoryExtentions = (List<EditReversePostingPostingCategoryExtentionPoint>) ModulManager.getExtentions(EditReversePostingPostingCategoryExtentionPoint.class);
        }
        return editReversePostingPostingCategoryExtentions;
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

    public PresentationModel<Posting> getReversePostingPresentationModel() {
        return reversePostingPresentationModel;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public JComponent getPostingCategoryExtentionComponent() {
        PostingCategory selection = postingCategorySelection.getSelection();
        if(postingCategorySelection.hasSelection()
                && selection != null
                && selection.getKey() != null
                && !selection.getKey().isEmpty()) {
            EditReversePostingPostingCategoryExtentionPoint get = editPostingPostingCategoryExtentionsKeyMap.get(selection.getKey());
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
            if(!save()) {
                return;
            }
            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
        }
    }
    
    public boolean save() {
        reversePostingPresentationModel.getBufferedModel(Posting.PROPERTYNAME_POSTINGDATE).setValue(new Date());
        
        boolean valid = true;
        List<String> errorMessages = new ArrayList<String>();

//        for(EditReversePostingPostingCategoryExtention ex : getExtentions()) {
//            List<String> validate = ex.validate();
//            if(validate != null && validate.size() > 0) {
//                valid = false;
//                errorMessages.addAll(validate);
//            }
            
            if(postingCategorySelection.hasSelection() && postingCategorySelection.getSelectionIndex() != 0) {
                EditReversePostingPostingCategoryExtentionPoint ex = editPostingPostingCategoryExtentionsKeyMap.get(postingCategorySelection.getSelection().getKey());
                if(ex != null) {
                    List<String> validate = ex.validate();
                    if(validate != null && validate.size() > 0) {
                        valid = false;
                        errorMessages.addAll(validate);
                    }
                }
            }

//        }

        if(!valid) {

            StringBuffer buffer = new StringBuffer("<html>");

            for(String message : errorMessages) {
                buffer.append(message);
                buffer.append("<br>");
            }

            buffer.append("</html>");

            JOptionPane.showMessageDialog(null, buffer.toString(), "Fehler und Warnungen", JOptionPane.ERROR_MESSAGE);

            return false;
        }
        
        reversePostingPresentationModel.triggerCommit();
        unsaved.setValue(false);

        // Save the selected extention
        if(postingCategorySelection.hasSelection() && postingCategorySelection.getSelectionIndex() != 0) {
            EditReversePostingPostingCategoryExtentionPoint ex = editPostingPostingCategoryExtentionsKeyMap.get(postingCategorySelection.getSelection().getKey());
            if(ex != null) {
                ex.save();
            }
        }
        
//        for(EditReversePostingPostingCategoryExtention ex : getExtentions()) {
//            ex.save();
//        }

        return true;
    }
    
}
