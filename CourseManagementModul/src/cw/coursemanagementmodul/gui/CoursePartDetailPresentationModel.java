/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;
import javax.swing.table.TableModel;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.manager.CourseAdditionManager;

/**
 *
 * @author André Salmhofer
 */
public class CoursePartDetailPresentationModel{
    //Definieren der Objekte in der oberen Leiste
    private Action cancelAction;
    private Action printAction;
    //********************************************
    
    //Liste der Kurse
    private SelectionInList<CourseAddition> courseSelection;
    //**********************************************
    
    private CourseParticipant selectedCourseParticipant;
    
    public CoursePartDetailPresentationModel(CourseParticipant selectedCourseParticipant) {
        initModels();
        initEventHandling();
        this.selectedCourseParticipant = selectedCourseParticipant;
    }
    
    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels() {
        //Anlegen der Aktionen, für die Buttons
        cancelAction = new CancelAction("Abbrechen");
        printAction = new PrintAction("Drucken");
        
        courseSelection = new SelectionInList<CourseAddition>(CourseAdditionManager.getInstance().getAll());
        updateActionEnablement();
    }
    //**************************************************************************
    
    //**************************************************************************
    //Methode, die ein CourseTableModel erzeugt.
    //Die private Klasse befindet sich in der gleichen Datei
    //**************************************************************************
    TableModel createCoursePartTableModel(SelectionInList<CourseAddition> courseSelection) {
        return new CourseTableModel(courseSelection);
    }
    //**************************************************************************
     
    
    private void initEventHandling() {
        courseSelection.addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());
    }
    
    //**************************************************************************
    //Klasse zum Beenden des Eingabeformulars. Wechselt anschließend
    //in die letzte View
    //**************************************************************************
    private class CancelAction extends AbstractAction{
        {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/cancel.png") );
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
            System.out.println("*******PRINT*******");
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

    public SelectionInList<CourseAddition> getCourseSelection() {
        return courseSelection;
    }
    //**************************************************************************
    
    //**************************************************************************
    //Getter.- und Setter-Methoden
    //**************************************************************************
    
    
    private final class SelectionEmptyHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateActionEnablement();
        }
    }
    
    //**************************************************************************
    //CourseTableModel, das die Art der Anzeige von Kursen regelt.
    //**************************************************************************
    private class CourseTableModel extends AbstractTableAdapter<CourseParticipant> {

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
                    return "Kursbeginn";
                case 2:
                    return "Kursende";
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
            CourseAddition course = (CourseAddition) listModel.getElementAt(rowIndex);
            int i = 0;
            String string = "";
            switch (columnIndex) {
                case 0:
                   return course.getCourse().getName();
                case 1:
                    return course.getCourse().getBeginDate() + "";
                case 2:
                    return course.getCourse().getEndDate() + "";
                case 3:
                    return course.getCourse().getPrice();
                default:
                    return "";
            }
        }
    }
    //**************************************************************************

    private void updateActionEnablement() {
    }

    public SelectionInList<CourseAddition> getCoursePartSelection() {
        return new SelectionInList<CourseAddition>(selectedCourseParticipant.getCourseList());
    }

    public CourseParticipant getSelectedCourseParticipant() {
        return selectedCourseParticipant;
    }
    
    
}
