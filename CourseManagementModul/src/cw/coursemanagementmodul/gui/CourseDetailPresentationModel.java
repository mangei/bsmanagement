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
import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;

/**
 *
 * @author André Salmhofer
 */
public class CourseDetailPresentationModel{
    //Definieren der Objekte in der oberen Leiste
    private Action cancelAction;
    private Action printAction;
    //********************************************
    
    //Liste der Kurse
    private SelectionInList<CourseParticipant> coursePartSelection;
    //**********************************************
    
    private Course selectedCourse;
    
    public CourseDetailPresentationModel(Course selectedCourse) {
        initModels();
        initEventHandling();
        this.selectedCourse = selectedCourse;
    }
    
    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels() {
        //Anlegen der Aktionen, für die Buttons
        cancelAction = new CancelAction("Abbrechen");
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
        coursePartSelection.addPropertyChangeListener(
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

    public SelectionInList<CourseParticipant> getCourosePartSelectoin() {
        return coursePartSelection;
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
    private class CoursePartTableModel extends AbstractTableAdapter<CourseParticipant> {

        private ListModel listModel;

        public CoursePartTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Vorname";
                case 1:
                    return "Nachname";
                case 2:
                    return "Kurse";
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
            String string = "";
            switch (columnIndex) {
                case 0:
                   return coursePart.getCostumer().getForename();
                case 1:
                    return coursePart.getCostumer().getSurname();
                case 2:

                    return coursePart.getCourseList().size();
                default:
                    return "";
            }
        }
    }
    //**************************************************************************
    
    
    
    private void updateActionEnablement() {
    }

    public SelectionInList<CourseParticipant> getCoursePartSelection() {
        SelectionInList<CourseParticipant> newList = new SelectionInList<CourseParticipant>();
        for(int i = 0; i < coursePartSelection.getList().size(); i++){
            for(int j = 0; j < coursePartSelection.getList().get(i).getCourseList().size(); j++){
                if(coursePartSelection.getList().get(i).getCourseList().get(j).getId() == selectedCourse.getId()){
                    newList.getList().add(coursePartSelection.getList().get(i));
                    System.out.println("******************NEW LIST ADD" + newList.getList().size());
                }
            }
        }
        return newList;
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }
    
    
}
