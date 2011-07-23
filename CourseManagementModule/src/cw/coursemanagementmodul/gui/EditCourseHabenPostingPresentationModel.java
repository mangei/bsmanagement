package cw.coursemanagementmodul.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.accountmanagementmodul.pojo.manager.PostingManager;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.coursemanagementmodul.pojo.Activity;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.CoursePosting;
import cw.coursemanagementmodul.pojo.Subject;
import cw.coursemanagementmodul.pojo.manager.CoursePostingManager;

/**
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class EditCourseHabenPostingPresentationModel {

    private SelectionInList<PostingCategory> postingCategorySelection;
    private ValueModel unsaved;
    
    private Action saveButtonAction;
    private Action cancelButtonAction;
    
    private ButtonListenerSupport support;

    private CWHeaderInfo headerInfo;

    private CourseAddition courseAddition;
    private CourseParticipant coursePart;
    
    private PresentationModel postingModel;

    private SelectionHandler selectionHandler;
    
    public EditCourseHabenPostingPresentationModel(CourseParticipant coursePart, CourseAddition course) {
        this.coursePart = coursePart;
        this.courseAddition = course;

        initModels();
        initEventHandling();
    }
    
    public void initModels() {
        support = new ButtonListenerSupport();

        headerInfo = new CWHeaderInfo(
                "Kurs bearbeiten",
                "<html>Sie befinden sich im Kursbuchungsbereich!<br/>"
                + "Hier können Sie eine Habenbuchung tätigen!<html>",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/posting.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/posting.png"));

        AccountPosting accountPosting = new AccountPosting();
        accountPosting.setCustomer(coursePart.getCustomer());
        postingModel = new PresentationModel<AccountPosting>(accountPosting);

        saveButtonAction = new SaveAction("Buchen", CWUtils.loadIcon("cw/customermanagementmodul/images/posting.png"));
        cancelButtonAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/customermanagementmodul/images/cancel.png"));

        List<PostingCategory> postingCategories = PostingCategoryManager.getInstance().getAll();
        postingCategories.add(0, null);
        postingCategorySelection = new SelectionInList<PostingCategory>(postingCategories);
        postingCategorySelection.setSelection(((AccountPosting)postingModel.getBean()).getPostingCategory());

        //Setzen der Voreinstellungen
        postingModel.getBufferedModel(AccountPosting.PROPERTYNAME_DESCRIPTION).setValue(courseAddition.getCourse().getName() + " für "
                + coursePart.getCustomer().getForename() + " "
                + coursePart.getCustomer().getSurname());
        postingModel.setBufferedValue(AccountPosting.PROPERTYNAME_AMOUNT, courseAddition.getCourse().getPrice()
                + getActivitiesAmount(courseAddition));
        //****************************

        unsaved = new ValueHolder();
    }
    
    public void initEventHandling() {
        postingModel.getBufferedModel(AccountPosting.PROPERTYNAME_AMOUNT).addValueChangeListener(new SaveListener());
        postingModel.getBufferedModel(AccountPosting.PROPERTYNAME_CATEGORY).addValueChangeListener(new SaveListener());
        postingModel.getBufferedModel(AccountPosting.PROPERTYNAME_CUSTOMER).addValueChangeListener(new SaveListener());
        postingModel.getBufferedModel(AccountPosting.PROPERTYNAME_DESCRIPTION).addValueChangeListener(new SaveListener());
        postingModel.getBufferedModel(AccountPosting.PROPERTYNAME_POSTINGENTRYDATE).addValueChangeListener(new SaveListener());


        unsaved.addValueChangeListener(selectionHandler = new SelectionHandler());
        unsaved.setValue(false);
    }

    public void dispose() {
        unsaved.removeValueChangeListener(selectionHandler);
        postingCategorySelection.release();
        postingModel.release();
    }

    private class SelectionHandler implements PropertyChangeListener{
        public void propertyChange(PropertyChangeEvent evt) {
                if((Boolean)evt.getNewValue() == true) {
                    saveButtonAction.setEnabled(true);
                } else {
                    saveButtonAction.setEnabled(false);
                }
            }
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

    public Action getCancelButtonAction() {
        return cancelButtonAction;
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
    
    public void save() {
        PostingCategory cat = PostingCategoryManager.getInstance().get("Kurs-Buchung");
        
        postingModel.getBufferedModel(AccountPosting.PROPERTYNAME_POSTINGDATE).setValue(new Date());
        postingModel.getBufferedModel(AccountPosting.PROPERTYNAME_CATEGORY).setValue(cat);
        postingModel.triggerCommit();
        PostingManager.getInstance().save(((AccountPosting)postingModel.getBean()));
        
        CoursePosting coursePosting = new CoursePosting();
        coursePosting.setPosting(((AccountPosting)postingModel.getBean()));
        coursePosting.setCourseAddition(courseAddition);

        CoursePostingManager.getInstance().save(coursePosting);
    }

    public CWHeaderInfo getHeaderInfo() {
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

    public double getCoursePrice(){
        return (double)(getCourseAddition().getCourse().getPrice());
    }

    public CourseParticipant getCoursePart() {
        return coursePart;
    }

    public PresentationModel getPostingModel() {
        return postingModel;
    }

    private double getActivitiesAmount(CourseAddition courseAddition){
        double amount = 0.0;
        for(int i = 0; i < courseAddition.getActivities().size(); i++){
            amount += courseAddition.getActivities().get(i).getPrice();
        }
        return amount;
    }
}
