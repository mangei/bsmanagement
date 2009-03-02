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
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.boardingschoolmanagement.manager.ModulManager;
import cw.customermanagementmodul.extentions.interfaces.EditReversePostingPostingCategoryExtention;
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
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComponent;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditReversePostingPresentationModel
    implements Disposable
{

    private PresentationModel<Posting> postingPresentationModel;
    private PresentationModel<Posting> reversePostingPresentationModel;

    private Posting reversePosting;
    private Posting oldPosting;
    private SelectionInList<PostingCategory> postingCategorySelection;
    private ValueModel unsaved;
    private HeaderInfo headerInfo;

    private Action cancelButtonAction;
    private Action saveCancelButtonAction;

    private List<EditReversePostingPostingCategoryExtention> editReversePostingPostingCategoryExtentions;
    private HashMap<String,EditReversePostingPostingCategoryExtention> editPostingPostingCategoryExtentionsKeyMap;

    private ButtonListenerSupport support;

    private PropertyChangeListener unsavedListener;
    
    public EditReversePostingPresentationModel(Posting oldPosting, Posting reversePosting, HeaderInfo headerInfo) {
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

        reversePostingPresentationModel.getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE).addValueChangeListener(new SaveListener());
        reversePostingPresentationModel.getBufferedModel(Posting.PROPERTYNAME_AMOUNT).addValueChangeListener(new SaveListener());
        reversePostingPresentationModel.getBufferedModel(Posting.PROPERTYNAME_DESCRIPTION).addValueChangeListener(new SaveListener());
        reversePostingPresentationModel.getBufferedModel(Posting.PROPERTYNAME_LIABILITIESASSETS).addValueChangeListener(new SaveListener());
        reversePostingPresentationModel.getBufferedModel(Posting.PROPERTYNAME_CATEGORY).addValueChangeListener(new SaveListener());

        editReversePostingPostingCategoryExtentions = getExtentions();
        editPostingPostingCategoryExtentionsKeyMap = new HashMap<String, EditReversePostingPostingCategoryExtention>();

        // Initialize the extentions
        for(EditReversePostingPostingCategoryExtention ex : editReversePostingPostingCategoryExtentions) {
            ex.initPresentationModel(this);
            editPostingPostingCategoryExtentionsKeyMap.put(ex.getKey(), ex);
        }
    }
    
    public void initEventHandling() {
        unsaved = new ValueHolder();
        unsaved.addValueChangeListener(unsavedListener = new PropertyChangeListener() {
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

    public void dispose() {
        for(EditReversePostingPostingCategoryExtention ex : editReversePostingPostingCategoryExtentions) {
            ex.initPresentationModel(this);
            editPostingPostingCategoryExtentionsKeyMap.put(ex.getKey(), ex);
        }

        unsaved.removeValueChangeListener(unsavedListener);
    }

    private List<EditReversePostingPostingCategoryExtention> getExtentions() {
        if(editReversePostingPostingCategoryExtentions == null) {
            editReversePostingPostingCategoryExtentions = (List<EditReversePostingPostingCategoryExtention>) ModulManager.getExtentions(EditReversePostingPostingCategoryExtention.class);
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

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public JComponent getPostingCategoryExtentionComponent() {
        PostingCategory selection = postingCategorySelection.getSelection();
        if(postingCategorySelection.hasSelection()
                && selection != null
                && selection.getKey() != null
                && !selection.getKey().isEmpty()) {
            EditReversePostingPostingCategoryExtention get = editPostingPostingCategoryExtentionsKeyMap.get(selection.getKey());
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

        for(EditReversePostingPostingCategoryExtention ex : getExtentions()) {
            List<String> validate = ex.validate();
            if(validate != null && validate.size() > 0) {
                valid = false;
                errorMessages.addAll(validate);
            }
        }

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

        for(EditReversePostingPostingCategoryExtention ex : getExtentions()) {
            ex.save();
        }

        return true;
    }
    
}
