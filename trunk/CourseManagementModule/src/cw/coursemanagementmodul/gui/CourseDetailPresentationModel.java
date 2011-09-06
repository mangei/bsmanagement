package cw.coursemanagementmodul.gui;

import java.awt.event.ActionEvent;
import java.text.DecimalFormat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;

/**
 *
 * @author André Salmhofer
 */
public class CourseDetailPresentationModel
{
    //Definieren der Objekte in der oberen Leiste
    private Action cancelAction;
    private Action printAction;
    //********************************************
    
    //Liste der Kurse
    private SelectionInList<CourseParticipant> coursePartSelection;

    private CWHeaderInfo headerInfo;
    //**********************************************
    
    private Course selectedCourse;
    
    public CourseDetailPresentationModel(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
        initModels();
        initEventHandling();
    }
    
    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels() {
        headerInfo = new CWHeaderInfo(
                "Kurs bearbeiten",
                "Sie befinden sich im Kurs - Kursteilnehmer. Hier sehen Sie alle Kursteilnehmer des Kurses " + selectedCourse.getName() + "!",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"));

        //Anlegen der Aktionen, fuer die Buttons
        cancelAction = new CancelAction("Zurueck");
        printAction = new PrintAction("Drucken");
        coursePartSelection = new SelectionInList<CourseParticipant>(CourseParticipantManager.getInstance().getAll(selectedCourse));
        
    }
    //**************************************************************************

    private void initEventHandling() {
        updateActionEnablement();
    }

    public void dispose() {
        coursePartSelection.release();
    }

    //**************************************************************************
    //Methode, die ein CourseTableModel erzeugt.
    //Die private Klasse befindet sich in der gleichen Datei
    //**************************************************************************
    TableModel createCoursePartTableModel(SelectionInList<CourseParticipant> coursePartSelection) {
        return new CoursePartTableModel(coursePartSelection);
    }
    //**************************************************************************
     
    //**************************************************************************
    //Klasse zum Beenden des Eingabeformulars. Wechselt anschließend
    //in die letzte View
    //**************************************************************************
    private class CancelAction extends AbstractAction{
        {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/back.png") );
        }
        
        public void actionPerformed(ActionEvent e){
            GUIManager.getInstance().unlockMenu();
            GUIManager.changeToLastView();
        }
        
        private CancelAction(String string){
            super(string);
        }
    }
    
    private class PrintAction extends AbstractAction{
        {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/print.png") );
        }
        
        public void actionPerformed(ActionEvent e){
            GUIManager.changeView(new PrintCourseParticipantView(
                    new PrintCourseParticipantPresentationModel(coursePartSelection.getList(),
                    new CWHeaderInfo("Kursteilnehmerliste drucken"), selectedCourse)), true);
        }
        
        private PrintAction(String string){
            super(string);
        }
    }

    public Action getCancelAction() {
        return cancelAction;
    }

    public Action getPrintAction() {
        return printAction;
    }

    public SelectionInList<CourseParticipant> getCourosePartSelectoin() {
        return coursePartSelection;
    }
    //**************************************************************************
    
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
                    return "Betrag";
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
                    return "€ " + numberFormat.format(getPrice(coursePart, selectedCourse));
                default:
                    return "";
            }
        }
    }
    //**************************************************************************
    
    private double getPrice(CourseParticipant courseParticipant, Course course){
        double price = course.getPrice();
        CourseAddition cA = new CourseAddition();
        for(int i = 0; i < courseParticipant.getCourseList().size(); i++){
            if(courseParticipant.getCourseList().get(i).getCourse().getId() == course.getId()){
                cA = courseParticipant.getCourseList().get(i);
            }
        }
        for(int i = 0; i < cA.getActivities().size(); i++){
            price += cA.getActivities().get(i).getPrice();
        }
        return price;
    }
    
    private void updateActionEnablement() {
    }

    public SelectionInList<CourseParticipant> getCoursePartSelection() {
        return new SelectionInList<CourseParticipant>(CourseParticipantManager.getInstance().getAll(selectedCourse));
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }
}
