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
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.ModulManager;
import cw.customermanagementmodul.extention.point.EditPostingPostingCategoryExtentionPoint;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import cw.customermanagementmodul.pojo.Posting;
import cw.customermanagementmodul.pojo.PostingCategory;
import cw.customermanagementmodul.pojo.manager.PostingCategoryManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
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
    private CWHeaderInfo headerInfo;
    private Action cancelAction;
    private Action saveCancelAction;
    private List<EditPostingPostingCategoryExtentionPoint> editPostingPostingCategoryExtentions;
    private HashMap<String, EditPostingPostingCategoryExtentionPoint> editPostingPostingCategoryExtentionsKeyMap;
    private ButtonListenerSupport buttonListenerSupport;
    private SaveListener saveListener;
    private PropertyChangeListener unsavedListener;
    private PropertyConnector postingCategoryConnector;

    public EditPostingPresentationModel(Posting posting, CWHeaderInfo headerInfo) {
        this(posting, false, headerInfo);
    }

    public EditPostingPresentationModel(Posting posting, boolean editMode, CWHeaderInfo headerInfo) {
        super(posting);
        this.posting = posting;
        this.headerInfo = headerInfo;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        buttonListenerSupport = new ButtonListenerSupport();

        cancelAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/customermanagementmodul/images/cancel.png"));
        saveCancelAction = new SaveCancelAction("Buchen", CWUtils.loadIcon("cw/customermanagementmodul/images/posting_lightning.png"));

        List<PostingCategory> postingCategories = PostingCategoryManager.getInstance().getAll();
        postingCategories.add(0, null);
        postingCategorySelection = new SelectionInList<PostingCategory>(postingCategories);
        postingCategorySelection.setSelectionIndex(0);

        editPostingPostingCategoryExtentions = getExtentions();
        editPostingPostingCategoryExtentionsKeyMap = new HashMap<String, EditPostingPostingCategoryExtentionPoint>();

        // Initialize the extentions
        for (EditPostingPostingCategoryExtentionPoint ex : editPostingPostingCategoryExtentions) {
            ex.initPresentationModel(this);
            editPostingPostingCategoryExtentionsKeyMap.put(ex.getKey(), ex);
        }
    }

    public void initEventHandling() {
        unsaved = new ValueHolder();

        saveListener = new SaveListener();
        getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE).addValueChangeListener(saveListener);
        getBufferedModel(Posting.PROPERTYNAME_AMOUNT).addValueChangeListener(saveListener);
        getBufferedModel(Posting.PROPERTYNAME_DESCRIPTION).addValueChangeListener(saveListener);
        getBufferedModel(Posting.PROPERTYNAME_LIABILITIESASSETS).addValueChangeListener(saveListener);
        getBufferedModel(Posting.PROPERTYNAME_CATEGORY).addValueChangeListener(saveListener);

        postingCategoryConnector = PropertyConnector.connect(getBufferedModel(Posting.PROPERTYNAME_CATEGORY), "value", postingCategorySelection, "selection");
        postingCategoryConnector.updateProperty2();

        unsaved.addValueChangeListener(unsavedListener = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if ((Boolean) evt.getNewValue() == true) {
                    saveCancelAction.setEnabled(true);
                } else {
                    saveCancelAction.setEnabled(false);
                }
            }
        });
        unsaved.setValue(false);
    }

    public void dispose() {
        getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE).removeValueChangeListener(saveListener);
        getBufferedModel(Posting.PROPERTYNAME_AMOUNT).removeValueChangeListener(saveListener);
        getBufferedModel(Posting.PROPERTYNAME_DESCRIPTION).removeValueChangeListener(saveListener);
        getBufferedModel(Posting.PROPERTYNAME_LIABILITIESASSETS).removeValueChangeListener(saveListener);
        getBufferedModel(Posting.PROPERTYNAME_CATEGORY).removeValueChangeListener(saveListener);

        // Initialize the extentions
        for(EditPostingPostingCategoryExtentionPoint ex : editPostingPostingCategoryExtentions) {
            ex.dispose();
        }

        unsaved.removeValueChangeListener(unsavedListener);

        postingCategoryConnector.release();
        release();
    }

    private List<EditPostingPostingCategoryExtentionPoint> getExtentions() {
        if (editPostingPostingCategoryExtentions == null) {
            editPostingPostingCategoryExtentions = (List<EditPostingPostingCategoryExtentionPoint>) ModulManager.getExtentions(EditPostingPostingCategoryExtentionPoint.class);
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

    public Action getCancelAction() {
        return cancelAction;
    }

    public Action getSaveCancelAction() {
        return saveCancelAction;
    }

    public ValueModel getUnsaved() {
        return unsaved;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public JComponent getPostingCategoryExtentionComponent() {
        PostingCategory selection = postingCategorySelection.getSelection();
        if (postingCategorySelection.hasSelection() && selection.getKey() != null && !selection.getKey().isEmpty()) {
            EditPostingPostingCategoryExtentionPoint get = editPostingPostingCategoryExtentionsKeyMap.get(selection.getKey());
            if (get != null) {
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
            if ((Boolean) unsaved.getValue() == true) {
                i = JOptionPane.showConfirmDialog(null, "Wollen Sie wirlich abbrechen?", "Abbrechen", JOptionPane.OK_CANCEL_OPTION);
            }
            if (i == JOptionPane.OK_OPTION) {
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

            if (!save()) {
                return;
            }
            buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
        }
    }

    public boolean save() {
        getBufferedModel(Posting.PROPERTYNAME_POSTINGDATE).setValue(new Date());
        if (postingCategorySelection.getSelectionIndex() == 0) {
            getBufferedModel(Posting.PROPERTYNAME_CATEGORY).setValue(null);
        }

        boolean valid = true;
        List<String> errorMessages = new ArrayList<String>();

//        for(EditPostingPostingCategoryExtention ex : getExtentions()) {
        if (postingCategorySelection.hasSelection() && (postingCategorySelection.getSelectionIndex() != 0)) {
//            List<String> validate = null;
//            try {
//                validate = editPostingPostingCategoryExtentionsKeyMap.get(postingCategorySelection.getSelection().getKey()).validate();
//            } catch(Exception ex) {
//                System.out.println(ex.getClass().toString());
//            }
//            System.out.println(validate);
//                if(validate != null && validate.size() > 0) {
//                    System.out.println("if 2 ok");
//                    valid = false;
//                    errorMessages.addAll(validate);
//                }
        }
//        }

        if (!valid) {

            StringBuffer buffer = new StringBuffer("<html>");

            for (String message : errorMessages) {
                buffer.append(message);
                buffer.append("<br>");
            }

            buffer.append("</html>");

            JOptionPane.showMessageDialog(null, buffer.toString(), "Fehler und Warnungen", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        triggerCommit();

        // Save the selected extention
        if (postingCategorySelection.hasSelection() && postingCategorySelection.getSelectionIndex() != 0) {
        // editPostingPostingCategoryExtentionsKeyMap.get(postingCategorySelection.getSelection().getKey()).save();
        String s = postingCategorySelection.getSelection().getKey();
        editPostingPostingCategoryExtentionsKeyMap.get(s);

        }
//        for (EditPostingPostingCategoryExtentionPoint ex : getExtentions()) {
//            ex.save();
//        }
//
        unsaved.setValue(false);
        return true;
    }
}
