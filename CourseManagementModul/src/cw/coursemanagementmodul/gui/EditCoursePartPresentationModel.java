/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import cw.coursemanagementmodul.pojo.Activity;
import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.Subject;
import cw.coursemanagementmodul.pojo.manager.CourseAdditionManager;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;
import cw.coursemanagementmodul.pojo.manager.ValueManager;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author André Salmhofer
 */
public class EditCoursePartPresentationModel extends PresentationModel<CourseParticipant>
implements Disposable{
    //Definieren der Objekte in der oberen Leiste

    private Action courseChooserButtonAction;
    private Action activityButtonAction;
    private Action subjectButtonAction;
    private Action removeCourseButtonAction;
    private Action removeSubjectButtonAction;
    private Action removeActivityButtonAction;
    //*******************************************
    //Instanz eines Kurses
    private CourseParticipant coursePart;
    //Variable, die feststellt ob die Daten gespeichert sind oder nicht
    private ValueModel unsaved;
    private SelectionInList<CourseAddition> courseAdditionSelection;
    private SelectionInList<Activity> activitySelection;
    private SelectionInList<Subject> subjectSelection;

    private ButtonListenerSupport support;
    
    private CourseChooserPresentationModel courseChooserModel;
    private ActivityChooserPresentationModel activityChooserModel;
    private SubjectChooserPresentationModel subjectChooserModel;

    private ValueModel nameVM;
    private ValueModel vonVM;
    private ValueModel bisVM;
    private ValueModel priceVM;
    
    private ValueModel sollVM;
    private ValueModel habenVM;
    private ValueModel saldoVM;
    
    private HeaderInfo headerInfo;

    private CourseHandler selectionHandler;
    private DataHandler dataHandler;
    private DataHandler2 dataHandler2;

    private SaveListener saveListener;

    
    //Konstruktor
    public EditCoursePartPresentationModel(CourseParticipant coursePart, ValueModel unsaved) {
        super(coursePart);
        this.coursePart = coursePart;
        this.unsaved = unsaved;
        initModels();
        initEventHandling();

        headerInfo = new HeaderInfo("Ferienkurse", coursePart.getCustomer().getTitle() + " "
                + coursePart.getCustomer().getForename() + " "
                + coursePart.getCustomer().getSurname());
    }
    //**************************************************************************

    public void initEventHandling() {
        courseAdditionSelection.addValueChangeListener(selectionHandler = new CourseHandler());
        updateCourseModels();
        updateActionEnablement();

        saveListener = new SaveListener();
        courseAdditionSelection.addValueChangeListener(saveListener);
        getBufferedModel(CourseParticipant.PROPERTYNAME_COSTUMER).addValueChangeListener(saveListener);
        getBufferedModel(CourseParticipant.PROPERTYNAME_COURSELIST).addValueChangeListener(saveListener);

        courseAdditionSelection.addListDataListener(dataHandler = new DataHandler());

        courseAdditionSelection.addListDataListener(dataHandler2 = new DataHandler2());
    }

    public void dispose() {
       courseAdditionSelection.removeValueChangeListener(selectionHandler);
       courseAdditionSelection.removeListDataListener(dataHandler);
       courseAdditionSelection.removeListDataListener(dataHandler2);
       courseAdditionSelection.removeValueChangeListener(saveListener);
       getBufferedModel(CourseParticipant.PROPERTYNAME_COSTUMER).removeValueChangeListener(saveListener);
       getBufferedModel(CourseParticipant.PROPERTYNAME_COURSELIST).removeValueChangeListener(saveListener);

       courseChooserModel.dispose();
       activityChooserModel.dispose();
       subjectChooserModel.dispose();

       release();
    }

    private class CourseHandler implements PropertyChangeListener{
        public void propertyChange(PropertyChangeEvent evt) {
                updateCourseModels();
                updateActionEnablement();
            }
    }
    private class DataHandler implements ListDataListener{

        public void intervalAdded(ListDataEvent e) {
                unsaved.setValue(true);
            }

            public void intervalRemoved(ListDataEvent e) {
                unsaved.setValue(true);
            }

            public void contentsChanged(ListDataEvent e) {
                unsaved.setValue(true);
            }
    }
    private class DataHandler2 implements ListDataListener{
        public void intervalAdded(ListDataEvent e) {
                update();
            }

            public void intervalRemoved(ListDataEvent e) {
                update();
            }

            public void contentsChanged(ListDataEvent e) {
                update();
            }

            public void update() {
                setBufferedValue(CourseParticipant.PROPERTYNAME_COURSELIST, courseAdditionSelection.getList());
            }
    }

    private void updateCourseModels() {
        if (courseAdditionSelection.hasSelection()) {
            nameVM.setValue(courseAdditionSelection.getSelection().getCourse().getName());
            vonVM.setValue(courseAdditionSelection.getSelection().getCourse().getBeginDate() + "");
            bisVM.setValue(courseAdditionSelection.getSelection().getCourse().getEndDate() + "");
            priceVM.setValue(courseAdditionSelection.getSelection().getCourse().getPrice() + "");
            
            sollVM.setValue(ValueManager.getInstance().getTotalSoll(coursePart) + "");
            habenVM.setValue(ValueManager.getInstance().getTotalHaben(coursePart) + "");
            saldoVM.setValue(ValueManager.getInstance().getTotalSaldo(coursePart) + "");
            
            activitySelection.setList(courseAdditionSelection.getSelection().getActivities());
            subjectSelection.setList(courseAdditionSelection.getSelection().getSubjects());
         } else {
            nameVM.setValue("");
            vonVM.setValue("");
            bisVM.setValue("");
            priceVM.setValue("");

            sollVM.setValue(ValueManager.getInstance().getTotalSoll(coursePart) + "");
            habenVM.setValue(ValueManager.getInstance().getTotalHaben(coursePart) + "");
            saldoVM.setValue(ValueManager.getInstance().getTotalSaldo(coursePart) + "");

            activitySelection.setList(null);
            subjectSelection.setList(null);
        }
    }

    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels() {
        courseChooserButtonAction = new CourseChooserButtonAction("Kurs hinzufügen");
        activityButtonAction = new ActivityButtonAction("Aktivität hinzufügen");
        subjectButtonAction = new SubjectButtonAction("Gegenstand hinzufügen");
        removeSubjectButtonAction = new RemoveSubjectButtonAction("Gegenstand löschen");
        removeCourseButtonAction = new RemoveButtonAction("Kurs löschen");
        removeActivityButtonAction = new RemoveActivityButtonAction("Aktivität löschen");

        courseChooserModel = new CourseChooserPresentationModel(new Course());
        activityChooserModel = new ActivityChooserPresentationModel(new Activity());
        subjectChooserModel = new SubjectChooserPresentationModel(new Subject());

        support = new ButtonListenerSupport();

        courseAdditionSelection = new SelectionInList<CourseAddition>(coursePart.getCourseList());
        activitySelection = new SelectionInList<Activity>();
        subjectSelection = new SelectionInList<Subject>();
        
        //----------------------------------------------------------------------
        nameVM = new ValueHolder();
        vonVM = new ValueHolder();
        bisVM = new ValueHolder();
        priceVM = new ValueHolder();
    //----------------------------------------------------------------------
        sollVM = new ValueHolder();
        habenVM = new ValueHolder();
        saldoVM = new ValueHolder();

        updateActionEnablement();
    }
    //**************************************************************************
    
    private void updateActionEnablement() {
        boolean hasSelection = courseAdditionSelection.hasSelection();
        activityButtonAction.setEnabled(hasSelection);
        subjectButtonAction.setEnabled(hasSelection);
        removeActivityButtonAction.setEnabled(hasSelection);
        removeSubjectButtonAction.setEnabled(hasSelection);
        removeCourseButtonAction.setEnabled(hasSelection);
    }

    //**************************************************************************
    //Getter- und Setter-Methoden der Aktionen
    //**************************************************************************
    Action getCourseChooserButtonAction() {
        return courseChooserButtonAction;
    }

    Action getActivityButtonAction() {
        return activityButtonAction;
    }

    Action getSubjectButtonAction() {
        return subjectButtonAction;
    }
    //**************************************************************************

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            unsaved.setValue(true);
        }
    }

    //**************************************************************************
    //Klasse zum Zurücksetzen eines Kurses
    //**************************************************************************
    public void reset() {
        resetCoursePart();
    }

    //**************************************************************************
    //Klasse zum Speicher eines Kurses
    //**************************************************************************
    public void save() {
        saveCoursePart();
    }
    //**************************************************************************

    private class CourseChooserButtonAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"));
        }

        public CourseChooserButtonAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.setLoadingScreenText("Formular wird geladen...");
            GUIManager.setLoadingScreenVisible(true);
            final CourseChooserView view = new CourseChooserView(courseChooserModel);
            final CourseAddition courseAddition = new CourseAddition();
            courseAddition.setCourse(courseChooserModel.getCourseItem());

            courseChooserModel.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.OK_BUTTON) {
                        //Hinzufügen des Kurses in eine CourseAddition
                        if(!courseAlreadyExists()){
                            courseAddition.setCourse(courseChooserModel.getCourseItem());
                            courseAdditionSelection.getList().add(courseAddition);
                            int index = courseAdditionSelection.getList().indexOf(courseAddition);
                            courseAdditionSelection.fireIntervalAdded(index, index);
                            //*********************************************************************
                            int min = courseChooserModel.getActivitySelectionModel().getMinSelectionIndex();
                            int max = courseChooserModel.getActivitySelectionModel().getMaxSelectionIndex();

                            System.out.println("*********MIN = " + min);
                            System.out.println("*********MAX = " + max);

                            List activityTempList = new ArrayList();
                            for(int i = min; i < max+1; i++){
                                if(courseChooserModel.getActivitySelectionModel().isSelectedIndex(i)){
                                    activityTempList.add(courseChooserModel.getActivityModel().get(i));
                                    System.out.println("*******SELECTED***********");
                                }
                            }
                            activitySelection.setList(activityTempList);
                            courseAddition.setActivities(activityTempList);

                            min = courseChooserModel.getSubjectSelection().getMinSelectionIndex();
                            max = courseChooserModel.getSubjectSelection().getMaxSelectionIndex();

                            System.out.println("*********MIN = " + min);
                            System.out.println("*********MAX = " + max);

                            List subjectTempList = new ArrayList();
                            for(int i = min; i < max+1; i++){
                                if(courseChooserModel.getSubjectSelection().isSelectedIndex(i)){
                                    subjectTempList.add(courseChooserModel.getSubjectModel().get(i));
                                    System.out.println("*******SELECTED_SUBJECT***********");
                                }
                            }
                            subjectSelection.setList(subjectTempList);
                            courseAddition.setSubjects(subjectTempList);
                            courseChooserModel.removeButtonListener(this);
                            CourseAdditionManager.getInstance().save(courseAddition);
                            GUIManager.changeToLastView();
                        }
                        else {
                            JOptionPane.showMessageDialog(null,
                                    "Dieser Kurs wurde dem Kursteilnehmer bereits zugewiesen!");
                        }
                    }
                    //***********************************
                }
            });

            GUIManager.changeView(view.buildPanel(), true);
            GUIManager.setLoadingScreenVisible(false);
        }
    }

    private class ActivityButtonAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/activity.png"));
        }

        public ActivityButtonAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            final ActivityChooserView view = new ActivityChooserView(activityChooserModel);
            
            activityChooserModel.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.OK_BUTTON) {
                        if(!activityAlreadyExists()){
                            activitySelection.getList().add(activityChooserModel.getActivityItem());
                            courseAdditionSelection.getSelection().setActivities(activitySelection.getList());
                            System.out.println("COURSE_SELECTION = " + courseAdditionSelection.getSelection().getCourse().getName());
                            GUIManager.changeToLastView();
                            activityChooserModel.removeButtonListener(this);
                        }
                        else {
                            JOptionPane.showMessageDialog(null,
                                    "Diese Aktivität wurde dem Kurs des Kursteilnehmers bereits zugewiesen!");
                        }
                    }
                }
            });
            GUIManager.changeView(view.buildPanel(), true);
        }
    }

    private class SubjectButtonAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/subject.png"));
        }

        public SubjectButtonAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            final SubjectChooserView view = new SubjectChooserView(subjectChooserModel);

            subjectChooserModel.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.OK_BUTTON) {
                        if(!subjectAlreadyExists()){
                            subjectSelection.getList().add(subjectChooserModel.getSubjectItem());
                            courseAdditionSelection.getSelection().setSubjects(subjectSelection.getList());
                            System.out.println("COURSE_SELECTION = " + courseAdditionSelection.getSelection().getCourse().getName());
                            GUIManager.changeToLastView();
                            subjectChooserModel.removeButtonListener(this);
                        }
                        else {
                            JOptionPane.showMessageDialog(null,
                                    "Dieser Kursgegenstand wurde dem Kurs des Kursteilnehmers bereits zugewiesen!");
                        }
                    }
                }
            });
            GUIManager.changeView(view.buildPanel(), true);
        }
    }

    private class RemoveButtonAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/subject.png"));
        }

        public RemoveButtonAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            int check = JOptionPane.showConfirmDialog(null, "Wollen Sie diesen Kurs ("
                    + courseAdditionSelection.getSelection().getCourse().getName() + ") "
                    + " des Kursteilnehmers " + coursePart.getCustomer().getForename()
                    + " " + coursePart.getCustomer().getSurname() + " wirklich löschen?");
            if(check == JOptionPane.OK_OPTION){
                CourseAddition cA = courseAdditionSelection.getSelection();
                courseAdditionSelection.getList().remove(cA);
                courseAdditionSelection.fireIntervalRemoved(courseAdditionSelection.getList().size(), courseAdditionSelection.getList().size()-1);
                CourseAdditionManager.getInstance().delete(cA);
            }
        }
    }

    private class RemoveSubjectButtonAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/subject.png"));
        }

        public RemoveSubjectButtonAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            int check = JOptionPane.showConfirmDialog(null, "Wollen Sie diesen Gegenstand ("
                    + subjectSelection.getSelection().getName() + ") "
                    + " für den Kurs " + courseAdditionSelection.getSelection().getCourse().getName()
                    + " wirklich löschen?");
            if(check == JOptionPane.OK_OPTION){
                Subject subject = subjectSelection.getSelection();
                subjectSelection.getList().remove(subject);
                subjectSelection.fireIntervalRemoved(subjectSelection.getList().size(), subjectSelection.getList().size()-1);
                courseAdditionSelection.getSelection().setSubjects(subjectSelection.getList());
                CourseAdditionManager.getInstance().save(courseAdditionSelection.getSelection());
            }
        }
    }

    private class RemoveActivityButtonAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/activity.png"));
        }

        public RemoveActivityButtonAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            int check = JOptionPane.showConfirmDialog(null, "Wollen Sie diese Aktivität ("
                    + activitySelection.getSelection().getName() + ") "
                    + " für den Kurs " + courseAdditionSelection.getSelection().getCourse().getName()
                    + " wirklich löschen?");
            if(check == JOptionPane.OK_OPTION){
                Activity activity = activitySelection.getSelection();
                activitySelection.getList().remove(activity);
                activitySelection.fireIntervalRemoved(activitySelection.getList().size(), activitySelection.getList().size()-1);
                courseAdditionSelection.getSelection().setActivities(activitySelection.getList());
                CourseAdditionManager.getInstance().save(courseAdditionSelection.getSelection());
            }
        }
    }
    //**************************************************************************
    //Methoden die in den oben angelegten Klassen zum
    // + Speichern
    // + Zurücksetzen
    // + Speichern & Schließen
    //dienen
    //**************************************************************************
    public void saveCoursePart() {
        triggerCommit();
        CourseParticipantManager.getInstance().save(coursePart);
    }

    public void resetCoursePart() {
        triggerFlush();
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
        private DecimalFormat numberFormat;

        public CourseTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
            numberFormat = new DecimalFormat("#0.00");
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
                    return "€ " + numberFormat.format(courseAddition.getCourse().getPrice());
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
        private DecimalFormat numberFormat;

        public ActivityTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
            numberFormat = new DecimalFormat("#0.00");
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Aktivitätsname";
                case 1:
                    return "Beschreibung";
                case 2:
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
            Activity activity = (Activity) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return activity.getName();
                case 1:
                    return activity.getDescription();
                case 2:
                    return "€ " + numberFormat.format(activity.getPrice());
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

    public ValueModel getBisVM() {
        return bisVM;
    }

    public ValueModel getNameVM() {
        return nameVM;
    }

    public ValueModel getPriceVM() {
        return priceVM;
    }

    public ValueModel getVonVM() {
        return vonVM;
    }

    public ValueModel getHabenVM() {
        return habenVM;

    }

    public ValueModel getSaldoVM() {
        return saldoVM;
    }

    public ValueModel getSollVM() {
        return sollVM;
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public boolean courseAlreadyExists(){
        boolean doCourseExist = false;
        for(int i = 0; i < coursePart.getCourseList().size(); i++){

            if(coursePart.getCourseList().get(i).getCourse().getId()
                    == courseChooserModel.getCourseItem().getId()){
                doCourseExist = true;
            }
        }
        return doCourseExist;
    }

    public boolean activityAlreadyExists(){
        boolean doActivityExist = false;
        for(int i = 0; i < coursePart.getCourseList().size(); i++){

            if(coursePart.getCourseList().get(i).getActivities().contains(
                    activityChooserModel.getActivityItem())){
                doActivityExist = true;
            }
        }
        return doActivityExist;
    }

    public boolean subjectAlreadyExists(){
        boolean doSubjectExist = false;
        for(int i = 0; i < coursePart.getCourseList().size(); i++){

            if(coursePart.getCourseList().get(i).getSubjects().contains(
                    subjectChooserModel.getSubjectItem())){
                doSubjectExist = true;
            }
        }
        return doSubjectExist;
    }

    public Action getRemoveCourseButtonAction() {
        return removeCourseButtonAction;
    }

    public Action getRemoveSubjectButtonAction() {
        return removeSubjectButtonAction;
    }

    public Action getRemoveActivityButtonAction() {
        return removeActivityButtonAction;
    }
}