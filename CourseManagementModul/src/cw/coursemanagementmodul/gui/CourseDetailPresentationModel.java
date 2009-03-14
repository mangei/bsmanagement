/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;
import javax.swing.table.TableModel;
import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;
import java.text.DecimalFormat;

/**
 *
 * @author André Salmhofer
 */
public class CourseDetailPresentationModel implements Disposable{
    //Definieren der Objekte in der oberen Leiste
    private Action cancelAction;
    private Action printAction;
    //********************************************
    
    //Liste der Kurse
    private SelectionInList<CourseParticipant> coursePartSelection;

    private HeaderInfo headerInfo;
    //**********************************************
    
    private Course selectedCourse;
    
    public CourseDetailPresentationModel(Course selectedCourse) {
        initModels();
        initEventHandling();
        this.selectedCourse = selectedCourse;

        headerInfo = new HeaderInfo(
                "Kurs bearbeiten",
                "Sie befinden sich im Kurs - Kursteilnehmer. Hier sehen Sie alle Kursteilnehmer des Kurses " + selectedCourse.getName() + "!",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"));
    }
    
    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels() {
        //Anlegen der Aktionen, für die Buttons
        cancelAction = new CancelAction("Zurück");
        printAction = new PrintAction("Drucken");
        
        coursePartSelection = new SelectionInList<CourseParticipant>(CourseParticipantManager.getInstance().getAll());
        updateActionEnablement();
    }
    //**************************************************************************
    
    //**************************************************************************
    //Methode, die ein CourseTableModel erzeugt.
    //Die private Klasse befindet sich in der gleichen Datei
    //**************************************************************************
    TableModel createCoursePartTableModel(SelectionInList<CourseParticipant> coursePartSelection) {
        return new CoursePartTableModel(coursePartSelection);
    }
    //**************************************************************************
     
    
    private void initEventHandling() {
    }

    public void dispose() {

    }
    
    //**************************************************************************
    //Klasse zum Beenden des Eingabeformulars. Wechselt anschließend
    //in die letzte View
    //**************************************************************************
    private class CancelAction extends AbstractAction{
        {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/back.png") );
        }
        
        public void actionPerformed(ActionEvent e){
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
                    new HeaderInfo("Kursteilnehmerliste drucken"), selectedCourse)).buildPanel(), true);
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
                    return "€ " + numberFormat.format(selectedCourse.getPrice());
                default:
                    return "";
            }
        }
    }
    //**************************************************************************
    
    
    
    private void updateActionEnablement() {
    }

    public SelectionInList<CourseParticipant> getCoursePartSelection() {
        return new SelectionInList<CourseParticipant>(CourseParticipantManager.getInstance().getAll(selectedCourse));
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }
}
