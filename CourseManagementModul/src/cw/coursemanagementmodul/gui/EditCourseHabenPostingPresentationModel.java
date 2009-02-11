package cw.coursemanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.coursemanagementmodul.pojo.Activity;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.Subject;
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
import javax.swing.ListModel;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCourseHabenPostingPresentationModel
        extends PresentationModel<Posting> {

    private Posting posting;
    private SelectionInList<PostingCategory> postingCategorySelection;
    private ValueModel unsaved;
    
    private Action resetButtonAction;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    
    private ButtonListenerSupport support;

    private HeaderInfo headerInfo;

    private CourseAddition courseAddition;
    
    public EditCourseHabenPostingPresentationModel(Posting posting) {
        super(posting);
        this.posting = posting;

        support = new ButtonListenerSupport();
        
        initModels();
        initEventHandling();

        headerInfo = new HeaderInfo(
                "Kurs bearbeiten",
                "<html>Sie befinden sich im Kursbuchungsbereich!<br/>"
                + "Hier können Sie eine Habenbuchung tätigen!<html>",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/posting.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/posting.png"));
    }
    
    public void initModels() {
        support = new ButtonListenerSupport();
        
        saveButtonAction = new SaveAction("Speichern", CWUtils.loadIcon("cw/customermanagementmodul/images/disk_16.png"));
        resetButtonAction = new ResetAction("Zurücksetzen", CWUtils.loadIcon("cw/customermanagementmodul/images/arrow_rotate_anticlockwise.png"));
        cancelButtonAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/customermanagementmodul/images/cancel.png"));
        saveCancelButtonAction = new SaveCancelAction("Speichern u. Schließen", CWUtils.loadIcon("cw/customermanagementmodul/images/save_cancel.png"));

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

    public SelectionInList<PostingCategory> getPostingCategorySelection() {
        return postingCategorySelection;
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

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public String getHabenPrice(){
        return courseAddition.getCourse().getPrice() + "";
    }

    public void setCourseAddition(CourseAddition courseAddition) {
        this.courseAddition = courseAddition;
    }

    public CourseAddition getCourseAddition() {
        return courseAddition;
    }

    public SelectionInList<Activity> getActivitySelection(){
        return new SelectionInList<Activity>(courseAddition.getActivities());
    }

    public SelectionInList<Subject> getSubjectSelection(){
        return new SelectionInList<Subject>(courseAddition.getSubjects());
    }

    //**************************************************************************
    //CourseTableModel, das die Art der Anzeige von Kursen regelt.
    //**************************************************************************
    private class ActivityTableModel extends AbstractTableAdapter<Activity> {

        private ListModel listModel;

        public ActivityTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Aktivitätsname";
                case 1:
                    return "Beschreibung";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Activity activity = (Activity) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return activity.getName();
                case 1:
                    return activity.getDescription();
                default:
                    return "";
            }
        }
    }
    //**************************************************************************

    //**************************************************************************
    //CourseTableModel, das die Art der Anzeige von Kursen regelt.
    //**************************************************************************
    private class SubjectTableModel extends AbstractTableAdapter<Subject> {

        private ListModel listModel;

        public SubjectTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Kursgegenstand";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Subject subject = (Subject) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return subject.getName();
                default:
                    return "";
            }
        }
    }
    //**************************************************************************

    ActivityTableModel createActivityTableModel(SelectionInList<Activity> activitySelection) {
        return new ActivityTableModel(activitySelection);
    }

    SubjectTableModel createSubjectTableModel(SelectionInList<Subject> subjectSelection) {
        return new SubjectTableModel(subjectSelection);
    }

    public Posting getPosting() {
        return posting;
    }

    public String getDescription(){
        String string = getCourseAddition().getCourse().getName()
                + " - " + getPosting().getCustomer().getForename() + " "
                + getPosting().getCustomer().getSurname() + "-"
                + getPosting().getCustomer().getId();
        return string;
    }
    public double getCoursePrice(){
        return (double)(getCourseAddition().getCourse().getPrice());
    }
}
