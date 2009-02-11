/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.manager.CourseManager;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;

/**
 *
 * @author André Salmhofer
 */
public class HistoryPresentationModel {
    //Definieren der Objekte in der oberen Leiste

    private Action cancelAction;
    private Action printAction;
    private Action accountingAction;
    private Action detailAction;
    //********************************************

    //Liste der Kurse
    private SelectionInList<CourseParticipant> coursePartSelection;
    private SelectionInList<Course> courseSelection;
    //**********************************************
    private Course selectedCourse;

    private HeaderInfo headerInfoComboBox;
    
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
        cancelAction = new CancelAction("Abbrechen");
        printAction = new PrintAction("Drucken");
        detailAction = new DetailAction("Anzeigen");
        
        coursePartSelection = new SelectionInList<CourseParticipant>(CourseParticipantManager.getInstance().getAll());
        courseSelection = new SelectionInList<Course>(CourseManager.getInstance().getAll());
        
        updateActionEnablement();

        courseSelection.addValueChangeListener(new PropertyChangeListener() {

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
                
                System.out.println("SELECTED COURSE = " + selectedCourse.getName());
            }
        });
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
    private class CancelAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/cancel.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.changeToLastView();
        }

        private CancelAction(String string) {
            super(string);
        }
    }

    private class PrintAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/print.png"));
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("*******PRINT*******");
        }

        private PrintAction(String string) {
            super(string);
        }
    }

    private class DetailAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/lupe.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.changeView(new DetailHistoryView(new DetailHistoryPresentationModel(coursePartSelection.getSelection())).buildPanel(), true);
        }

        private DetailAction(String string) {
            super(string);
        }
    }

    public Action getCancelAction() {
        return cancelAction;
    }

    public Action getPrintAction() {
        return printAction;
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
        return coursePartSelection;
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public SelectionInList<Course> getCourseSelection() {
        return courseSelection;
    }

    public Action getAccountingAction() {
        return accountingAction;
    }

    public Action getDetailAction() {
        return detailAction;
    }

    //**************************************************************************
    //Event Handling
    //**************************************************************************
    public MouseListener getDoubleClickHandler() {
        return new DoubleClickHandler();
    }

    private final class DoubleClickHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                GUIManager.changeView(new DetailHistoryView(new DetailHistoryPresentationModel(coursePartSelection.getSelection())).buildPanel(), true);
            }
        }
    }

    public HeaderInfo getHeaderInfoComboBox() {
        return headerInfoComboBox;
    }
}
