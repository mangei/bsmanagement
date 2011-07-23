/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.coursemanagementmodul.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.accountmanagementmodul.gui.EditPostingPresentationModel;
import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.coursemanagementmodul.pojo.Activity;
import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.CoursePosting;
import cw.coursemanagementmodul.pojo.Subject;

/**
 *
 * @author André Salmhofer
 */
public class CourseParticipantForPostingPresentationModel
        extends PresentationModel<CourseParticipant>
{
    //Instanz eines Kurses
    private CourseParticipant coursePart;
    //Variable, die feststellt ob die Daten gespeichert sind oder nicht
    private ValueModel unsaved;
    private SelectionInList<CourseAddition> courseAdditionSelection;
    private SelectionInList<Activity> activitySelection;
    private SelectionInList<Subject> subjectSelection;

    private Action vorschlagAction;

    private ButtonListenerSupport support;

    private AccountPosting accountPosting;
    private SelectionInList<CoursePosting> coursePostingList;

    private EditPostingPresentationModel postingModel;

    private ValueHolder priceV;

    private SelectionHandler selectionHandler;

    //Konstruktor
    public CourseParticipantForPostingPresentationModel(CourseParticipant coursePart, ValueModel unsaved,
            EditPostingPresentationModel postingModel) {
        super(coursePart);
        this.coursePart = coursePart;
        this.unsaved = unsaved;
        this.postingModel = postingModel;
        initModels();
        initEventHandling();
    }
    //**************************************************************************


    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels() {
        accountPosting = new AccountPosting();
        accountPosting.setCustomer(coursePart.getCustomer());

        support = new ButtonListenerSupport();

        courseAdditionSelection = new SelectionInList<CourseAddition>(coursePart.getCourseList());

        activitySelection = new SelectionInList<Activity>();
        subjectSelection = new SelectionInList<Subject>();

        vorschlagAction = new VorschlagAction("Vorschlag");
    }
    //**************************************************************************

    public void initEventHandling() {
        courseAdditionSelection.addValueChangeListener(selectionHandler = new SelectionHandler());
        updateCourseModels();
        updateActionEnablement();
    }

    public void dispose() {
        courseAdditionSelection.removeValueChangeListener(selectionHandler);
        postingModel.dispose();
        coursePostingList.release();
        activitySelection.release();
        subjectSelection.release();
        release();
    }

    private class SelectionHandler implements PropertyChangeListener{
        public void propertyChange(PropertyChangeEvent evt) {
                updateCourseModels();
                updateActionEnablement();
            }
    }

    private void updateCourseModels() {
        if (courseAdditionSelection.hasSelection()) {
            activitySelection.setList(courseAdditionSelection.getSelection().getActivities());
            subjectSelection.setList(courseAdditionSelection.getSelection().getSubjects());
            vorschlagAction.setEnabled(true);

            double diff =  courseAdditionSelection.getSelection().getCourse().getPrice()
                    - courseAdditionSelection.getSelection().getAlreadyPayedAmount();
            priceV = new ValueHolder(diff);
         } else {
            vorschlagAction.setEnabled(false);
        }
    }

    private void updateActionEnablement() {
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }
    
    public SelectionInList<CourseAddition> getCourseSelection() {
        return courseAdditionSelection;
    }

    public SelectionInList<Activity> getActivitySelection() {
        return activitySelection;
    }

    public SelectionInList<Subject> getSubjectSelection() {
        return subjectSelection;
    }

    private class VorschlagAction extends AbstractAction{
          {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/vorschlag.png") );
          }

         private VorschlagAction(String name) {
            super(name);
         }

        public void actionPerformed(ActionEvent e) {
            setVoreinstellungen();
        }
    }

    //**************************************************************************
    CourseTableModel createCourseTableModel(SelectionInList<CourseAddition> courseSelection) {
        return new CourseTableModel(courseSelection);
    }

    ActivityTableModel createActivityTableModel(SelectionInList<Activity> activitySelection) {
        return new ActivityTableModel(activitySelection);
    }

    SubjectTableModel createSubjectTableModel(SelectionInList<Subject> subjectSelection) {
        return new SubjectTableModel(subjectSelection);
    }

    //**************************************************************************
    //CourseTableModel, das die Art der Anzeige von Kursen regelt.
    //**************************************************************************
    private class CourseTableModel extends AbstractTableAdapter<Course> {

        private ListModel listModel;

        public CourseTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Kursname";
                case 1:
                    return "Von";
                case 2:
                    return "Bis";
                case 3:
                    return "Preis";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            CourseAddition courseAddition = (CourseAddition) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return courseAddition.getCourse().getName();
                case 1:
                    return courseAddition.getCourse().getBeginDate();
                case 2:
                    return courseAddition.getCourse().getEndDate();
                case 3:
                    return courseAddition.getCourse().getPrice();
                default:
                    return "";
            }
        }
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
    
    private void setVoreinstellungen(){
        postingModel.getBufferedModel(AccountPosting.PROPERTYNAME_DESCRIPTION).setValue("Buchung für "
                + courseAdditionSelection.getSelection().getCourse().getName());
    }

    public Action getVorschlagAction() {
        return vorschlagAction;
    }

    public EditPostingPresentationModel getPostingModel() {
        return postingModel;
    }

    public ValueHolder getPriceV() {
        return priceV;
    }
}