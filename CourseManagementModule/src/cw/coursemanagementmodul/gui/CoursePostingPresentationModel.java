package cw.coursemanagementmodul.gui;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.accountmanagementmodul.pojo.manager.PostingManager;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.CoursePosting;
import cw.coursemanagementmodul.pojo.PostingRun;
import cw.coursemanagementmodul.pojo.manager.CourseManager;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;
import cw.coursemanagementmodul.pojo.manager.CoursePostingManager;
import cw.coursemanagementmodul.pojo.manager.PostingRunManager;

/**
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class CoursePostingPresentationModel
        extends PresentationModel<CoursePosting>
{
    
    //Definieren der Objekte in der oberen Leiste
    private Action postingAction;
    private Action normalRadioButtonAction;
    private Action testRadioButtonAction;
    private Action runsAction;
    //********************************************

    //Liste der Kurse
    private SelectionInList<CourseParticipant> coursePartSelection;
    private SelectionInList<Course> courseSelection;
    //**********************************************

    private PresentationModel<AccountPosting> postingPresentationModel;

    private CWHeaderInfo headerInfo;

    private Course selectedCourse;
    private boolean isEchtlauf = false;

    private ValueModel nameVM;
    private ValueModel vonVM;
    private ValueModel bisVM;
    private ValueModel priceVM;

    private SelectionHandler selectionHandler;
    private IntervallHandler intervallHandler;

    public CoursePostingPresentationModel(CoursePosting coursePosting) {
        super(coursePosting);
        initModels();
        initEventHandling();
    }

    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels() {
        headerInfo = new CWHeaderInfo(
                "Kursbuchungen",
                "Sie befinden sich im Kursbuchungsbereich. Hier koennen Sie Kursbuchungen taetigen",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/accounting.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/accounting.png"));
        //Anlegen der Aktionen, fuer die Buttons
        postingAction = new PostingAction("Buchen");
        normalRadioButtonAction = new NormalRadioButtonAction("Echtlauf");
        testRadioButtonAction = new TestRadioButtonAction("Testlauf");
        runsAction = new RunsAction("Gebuehrenlaeufe");

        coursePartSelection = new SelectionInList<CourseParticipant>();
        courseSelection = new SelectionInList<Course>(CourseManager.getInstance().getAll());

        courseSelection.addValueChangeListener(intervallHandler = new IntervallHandler());

        postingPresentationModel = new PresentationModel<AccountPosting>(getBufferedModel(CoursePosting.PROPERTYNAME_POSTING));

        //----------------------------------------------------------------------
        nameVM = new ValueHolder();
        vonVM = new ValueHolder();
        bisVM = new ValueHolder();
        priceVM = new ValueHolder();
        //----------------------------------------------------------------------
    }
    //**************************************************************************


    private void initEventHandling() {
        courseSelection.addValueChangeListener(selectionHandler = new SelectionHandler());
        updateCourseLabels();
        updateActionEnablement();
    }

    public void dispose() {
        courseSelection.removeValueChangeListener(selectionHandler);
        courseSelection.removeValueChangeListener(intervallHandler);
        coursePartSelection.release();
        courseSelection.release();
        release();
    }

    private void updateCourseLabels(){
        if(courseSelection.hasSelection()){
            nameVM.setValue(courseSelection.getSelection().getName());
            vonVM.setValue(courseSelection.getSelection().getBeginDate());
            bisVM.setValue(courseSelection.getSelection().getEndDate());
            priceVM.setValue(courseSelection.getSelection().getPrice());
        }
        else{
            nameVM.setValue("");
            vonVM.setValue(new Date());
            bisVM.setValue(new Date());
            priceVM.setValue(0.0);
        }
    }

    private void updateActionEnablement() {
        boolean hasSelection = courseSelection.hasSelection();
        postingAction.setEnabled(hasSelection);
    }

    //**************************************************************************
    //Methode, die ein CourseTableModel erzeugt.
    //Die private Klasse befindet sich in der gleichen Datei
    //**************************************************************************
    TableModel createCoursePartTableModel(SelectionInList<CourseParticipant> coursePartSelection) {
        return new CoursePartTableModel(coursePartSelection);
    }
    //**************************************************************************


    private class SelectionHandler implements PropertyChangeListener{
        public void propertyChange(PropertyChangeEvent evt) {
                updateCourseLabels();
                updateActionEnablement();
            }
    }

    private class IntervallHandler implements PropertyChangeListener{
        public void propertyChange(PropertyChangeEvent evt) {
                selectedCourse = courseSelection.getSelection();

                if (coursePartSelection.getList().size() != 0) {
                    coursePartSelection.getList().clear();
                    coursePartSelection.fireIntervalRemoved(0, getCoursePartSelection().getList().size() - 1);
                }

                List<CourseParticipant> list = CourseParticipantManager.getInstance().getAll(selectedCourse);
                coursePartSelection.setList(list);
                if (coursePartSelection.getList().size() != 0) {
                    coursePartSelection.fireIntervalAdded(0, list.size()-1);
                }
            }
    }

    private class PostingAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/start.png"));
        }

        public void actionPerformed(ActionEvent e) {
            if(isEchtlauf){
                doPosting();
                JOptionPane.showMessageDialog(null, "Kursteilnehmer wurden gebucht!");
            }
            else{
                GUIManager.getInstance().lockMenu();
                GUIManager.changeView(new TestRunView(new TestRunPresentationModel(
                        coursePartSelection, selectedCourse)), true);
            }
        }

        private PostingAction(String string) {
            super(string);
        }
    }

    private class NormalRadioButtonAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/accounting.png"));
        }

        public void actionPerformed(ActionEvent e) {
            isEchtlauf = true;
        }

        private NormalRadioButtonAction(String string) {
            super(string);
        }
    }

    private class TestRadioButtonAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/accounting.png"));
        }

        public void actionPerformed(ActionEvent e) {
            isEchtlauf = false;
        }

        private TestRadioButtonAction(String string) {
            super(string);
        }
    }

    //**************************************************************************

    private class RunsAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/postingRuns.png"));
        }

        private RunsAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().lockMenu();
            GUIManager.changeView(new PostingRunsView(new PostingRunsPresentationModel()), true);
        }
    }
    
    //**************************************************************************
    //CourseTableModel, das die Art der Anzeige von Kursen regelt.
    //**************************************************************************
    private class CoursePartTableModel extends AbstractTableAdapter<CourseParticipant> {

        private ListModel listModel;
        private DecimalFormat numberFormat;

        public CoursePartTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
            numberFormat = new DecimalFormat("#0.00");
        }

        @Override
        public int getColumnCount() {
            return 7;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Anrede";
                case 1:
                    return "Vorname";
                case 2:
                    return "Nachname";
                case 3:
                    return "Adresse";
                case 4:
                    return "PLZ";
                case 5:
                    return "Ort";
                case 6:
                    return "Vorgeschriebener Betrag";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            CourseParticipant coursePart = (CourseParticipant) listModel.getElementAt(rowIndex);
            int i = 0;
            switch (columnIndex) {
                case 0:
                    return coursePart.getCustomer().getTitle();
                case 1:
                    return coursePart.getCustomer().getForename();
                case 2:
                    return coursePart.getCustomer().getSurname();
                case 3:
                    return coursePart.getCustomer().getStreet();
                case 4:
                    return coursePart.getCustomer().getPostOfficeNumber();
                case 5:
                    return coursePart.getCustomer().getCity();
                case 6:
                    return "€ " + numberFormat.format(getPrice(coursePart));
                default:
                    return "";
            }
        }
    }
    //**************************************************************************

    public SelectionInList<CourseParticipant> getCoursePartSelection() {
        return coursePartSelection;
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public SelectionInList<Course> getCourseSelection() {
        return courseSelection;
    }

    public Action getPostingAction() {
        return postingAction;
    }

    public void doPosting(){
        ArrayList<CoursePosting> postingList = new ArrayList<CoursePosting>();
        PostingRun run = new PostingRun();
        
        run.setName(selectedCourse.toString());
        PostingCategory cat = PostingCategoryManager.getInstance().get("Kurs-Buchung");

        for(int i = 0; i < coursePartSelection.getSize(); i++){
            for(int j = 0; j < coursePartSelection.getList().get(i).getCourseList().size(); j++){
                if(coursePartSelection.getList().get(i).getCourseList().get(j).getCourse().getId()
                        == selectedCourse.getId()){
                    AccountPosting accountPosting = new AccountPosting();
                    accountPosting.setAmount(getPrice(coursePartSelection.getList().get(i).getCourseList().get(j)));
                    accountPosting.setCustomer(coursePartSelection.getList().get(i).getCustomer());
                    accountPosting.setDescription(coursePartSelection.getList().get(i).getCourseList().get(j).getCourse().getName());
                    accountPosting.setPostingDate(new Date());
                    accountPosting.setLiabilitiesAssets(true);
                    accountPosting.setPostingCategory(cat);
                    PostingManager.getInstance().save(accountPosting);

                    CoursePosting coursePosting = new CoursePosting();
                    coursePosting.setPosting(accountPosting);
                    coursePosting.setCourseAddition(coursePartSelection.getList().get(i).getCourseList().get(j));
                    CoursePostingManager.getInstance().save(coursePosting);

                    postingList.add(coursePosting);
                }
            }
        }
        run.setCoursePostings(postingList);
        PostingRunManager.getInstance().save(run);
    }

    private double getPrice(CourseAddition courseAddition){
        double price = courseAddition.getCourse().getPrice();
        for(int i = 0; i < courseAddition.getActivities().size(); i++){
            price += courseAddition.getActivities().get(i).getPrice();
        }
        return price;
    }

    private double getPrice(CourseParticipant courseParticipant){
        double price = selectedCourse.getPrice();

        CourseAddition courseAddition = new CourseAddition();

        for(int i = 0; i < courseParticipant.getCourseList().size(); i++){
            if(courseParticipant.getCourseList().get(i).getCourse().getId() == selectedCourse.getId()){
                courseAddition = courseParticipant.getCourseList().get(i);
            }
        }

        for(int i = 0; i < courseAddition.getActivities().size(); i++){
            price += courseAddition.getActivities().get(i).getPrice();
        }
        return price;
    }

    public void save() {
        postingPresentationModel.triggerCommit();
        triggerCommit();
    }

    public void reset() {
        postingPresentationModel.triggerFlush();
        triggerFlush();
    }

    public PresentationModel<AccountPosting> getPostingPresentationModel() {
        return postingPresentationModel;
    }
    
    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public Action getNormalRadioButtonAction(){
        return normalRadioButtonAction;
    }

    public Action getTestRadioButtonAction() {
        return testRadioButtonAction;
    }

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

    public Action getRunsAction() {
        return runsAction;
    }
}
