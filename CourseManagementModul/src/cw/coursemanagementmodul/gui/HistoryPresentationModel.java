/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;
import javax.swing.table.TableModel;
import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.manager.CourseManager;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;
import cw.coursemanagementmodul.pojo.manager.ValueManager;
import java.text.DecimalFormat;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author André Salmhofer
 */
public class HistoryPresentationModel implements Disposable{
    //Definieren der Objekte in der oberen Leiste
    private Action detailAction;
    private Action printAction;
    private Action chartAction;
    //********************************************

    //Liste der Kurse
    private SelectionInList<CourseParticipant> coursePartSelection;
    private SelectionInList<Course> courseSelection;
    //**********************************************
    private Course selectedCourse;

    private HeaderInfo headerInfoComboBox;

    private IntervallHandler intervallHandler;
    private CoursePartSelectionHandler coursePartSelectionHandler;

    private ValueModel courseSollValueHolder;
    private ValueModel courseHabenValueHolder;
    private ValueModel courseSaldoValueHolder;
    private ValueModel sizeValueHolder;

    private ValueModel nameVM;
    private ValueModel vonVM;
    private ValueModel bisVM;
    private ValueModel priceVM;

    private double sollValue;
    private double habenValue;
    private double saldoValue;
    
    public HistoryPresentationModel() {
        initModels();
        initEventHandling();

        headerInfoComboBox = headerInfoComboBox = new HeaderInfo(
                "Buchungshistorie",
                "<html>Sie befinden sich im Kursbuchungsbereich. Hier können Sie die Historie der Kursbuchungen betrachten!<br/>Wählen Sie bitte zunächst den Kurs aus!</html>",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/courseHistory.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/courseHistory.png"));
    }

    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels() {
        //Anlegen der Aktionen, für die Buttons
        detailAction = new DetailAction("Anzeigen");
        printAction = new PrintAction("Drucken");
        chartAction = new ChartAction("Chart");
        
        coursePartSelection = new SelectionInList<CourseParticipant>();
        courseSelection = new SelectionInList<Course>(CourseManager.getInstance().getAll());
        
        updateActionEnablement();

        courseSollValueHolder = new ValueHolder();
        courseHabenValueHolder = new ValueHolder();
        courseSaldoValueHolder = new ValueHolder();
        sizeValueHolder = new ValueHolder();

        //----------------------------------------------------------------------
        nameVM = new ValueHolder();
        vonVM = new ValueHolder();
        bisVM = new ValueHolder();
        priceVM = new ValueHolder();
        
        updateElements();
    }

    public void dispose() {
        courseSelection.removeValueChangeListener(intervallHandler);
        coursePartSelection.removeValueChangeListener(coursePartSelectionHandler);
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
                updateElements();
                updateActionEnablement();
        }
    }

    private class CoursePartSelectionHandler implements PropertyChangeListener{
        public void propertyChange(PropertyChangeEvent evt) {
                updateActionEnablement();
            }
    }
    //**************************************************************************

    private void updateElements(){
        if(courseSelection.hasSelection()){
            calculateTotalValue();
            courseSollValueHolder.setValue(sollValue);
            courseHabenValueHolder.setValue(habenValue);
            courseSaldoValueHolder.setValue(saldoValue);
            nameVM.setValue(selectedCourse.getName());
            vonVM.setValue(selectedCourse.getBeginDate());
            bisVM.setValue(selectedCourse.getEndDate());
            priceVM.setValue(selectedCourse.getPrice());
            sizeValueHolder.setValue(coursePartSelection.getSize() + "");
        }
        else{
            courseSollValueHolder.setValue(0.0);
            courseHabenValueHolder.setValue(0.0);
            courseSaldoValueHolder.setValue(0.0);
            sizeValueHolder.setValue("");

            nameVM.setValue("");
            vonVM.setValue(new Date());
            bisVM.setValue(new Date());
            priceVM.setValue(0.0);
        }
    }

    //**************************************************************************
    //Methode, die ein CourseTableModel erzeugt.
    //Die private Klasse befindet sich in der gleichen Datei
    //**************************************************************************
    TableModel createCoursePartTableModel(SelectionInList<CourseParticipant> coursePartSelection) {
        return new CoursePartTableModel(coursePartSelection);
    }
    //**************************************************************************

    private void initEventHandling() {
        coursePartSelection.addValueChangeListener(coursePartSelectionHandler = new CoursePartSelectionHandler());
        courseSelection.addValueChangeListener(intervallHandler = new IntervallHandler());
        updateActionEnablement();
        updateElements();
    }
    
    private class DetailAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/lupe.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().lockMenu();
            GUIManager.changeView(new DetailHistoryView(new DetailHistoryPresentationModel(coursePartSelection.getSelection())).buildPanel(), true);
        }

        private DetailAction(String string) {
            super(string);
        }
    }

    private class PrintAction extends AbstractAction{
        {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/print.png") );
        }

        public void actionPerformed(ActionEvent e){
            GUIManager.getInstance().lockMenu();
            GUIManager.changeView(new PrintCalculationView(
                    new PrintCalculationPresentationModel(coursePartSelection.getList(),
                    new HeaderInfo("Kursteilnehmerliste drucken"), selectedCourse)).buildPanel(), true);
        }

        private PrintAction(String string){
            super(string);
        }
    }

    private class ChartAction extends AbstractAction{
        {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/print.png") );
        }

        public void actionPerformed(ActionEvent e){
            
        }

        private ChartAction(String string){
            super(string);
        }
    }
    //**************************************************************************
    
    //**************************************************************************
    //CourseTableModel, das die Art der Anzeige von Kursen regelt.
    //**************************************************************************
    private class CoursePartTableModel extends AbstractTableModel {

        private ListModel listModel;
        private DecimalFormat numberFormat;

        public CoursePartTableModel(ListModel listModel) {
            this.listModel = listModel;
            numberFormat = new DecimalFormat("#0.00");
        }

        @Override
        public int getColumnCount() {
            return 8;
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
                    return "Betrag";
                case 7:
                    return "Status";
                default:
                    return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 7:
                    return Integer.class;
                default:
                    return String.class;
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            CourseParticipant coursePart = (CourseParticipant) listModel.getElementAt(rowIndex);
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
                    return "€ " + numberFormat.format(ValueManager.getInstance().getTotalSoll(selectedCourse, coursePart));
                case 7:
                    return getCorrectPosted(coursePart);
                default:
                    return "";
            }
        }

        public int getRowCount() {
            return listModel.getSize();
        }
    }
    //**************************************************************************
    
    private void updateActionEnablement() {
        boolean hasSelection = coursePartSelection.hasSelection();
        detailAction.setEnabled(hasSelection);
        boolean hasCourseSelection = courseSelection.hasSelection();
        printAction.setEnabled(hasCourseSelection);
    }

    public SelectionInList<CourseParticipant> getCoursePartSelection() {
        return coursePartSelection;
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public SelectionInList<Course> getCourseSelection() {
        return courseSelection;
    }

    public Action getDetailAction() {
        return detailAction;
    }

    public HeaderInfo getHeaderInfoComboBox() {
        return headerInfoComboBox;
    }

    private void calculateTotalValue(){
        sollValue = 0.0;
        habenValue = 0.0;
        saldoValue = 0.0;
        for(int i = 0; i < coursePartSelection.getSize(); i++){
            sollValue += ValueManager.getInstance().getTotalSoll(selectedCourse, coursePartSelection.getList().get(i));
            habenValue += ValueManager.getInstance().getTotalHaben(selectedCourse, coursePartSelection.getList().get(i));
            saldoValue += ValueManager.getInstance().getTotalSaldo(selectedCourse, coursePartSelection.getList().get(i));
        }
    }

    public ValueModel getCourseSaldoValueHolder() {
        return courseSaldoValueHolder;
    }

    public ValueModel getCourseSollValueHolder() {
        return courseSollValueHolder;
    }

    public ValueModel getCourseHabenValueHolder() {
        return courseHabenValueHolder;
    }

    public ValueModel getSizeValueHolder() {
        return sizeValueHolder;
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

    public Integer getCorrectPosted(CourseParticipant courseParticipant){
        CourseAddition courseAddition = new CourseAddition();

        //CourseAddition suchen
        for(int i = 0; i < courseParticipant.getCourseList().size(); i++){
            if(courseParticipant.getCourseList().get(i).getCourse().getId() == selectedCourse.getId()){
                courseAddition = courseParticipant.getCourseList().get(i);
            }
        }
        //*************************

        Date warningDate = new Date();
        Date currentDate = new Date();
        warningDate.setMonth(courseAddition.getCourse().getEndDate().getMonth()+1);
        
        if(currentDate.after(warningDate) && ValueManager.getInstance().getTotalSaldo(courseAddition) != 0.0){
            return -1;
        }

        if(ValueManager.getInstance().getTotalSaldo(courseAddition) != 0.0){
            return 1;
        }
        else{
            return 0;
        }
    }

    public Action getPrintAction() {
        return printAction;
    }

    public Action getChartAction() {
        return chartAction;
    }
}
