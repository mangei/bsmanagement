package cw.studentmanagementmodul.gui;

import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import javax.swing.event.ListSelectionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.ListSelectionListener;
import cw.studentmanagementmodul.pojo.Student;
import cw.studentmanagementmodul.pojo.StudentClass;
import cw.studentmanagementmodul.pojo.manager.StudentManager;
import javax.swing.ListModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * @author CreativeWorkers.at
 */
public class StudentsOverviewPresentationModel
    implements Disposable
{

    private SelectionInList<Student> studentSelection;

    private StudentClass studentClass;

    private HeaderInfo headerInfo;

    public StudentsOverviewPresentationModel(StudentClass studentClass) {
        this.studentClass = studentClass;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        studentSelection = new SelectionInList<Student>(StudentManager.getInstance().getAllActive(studentClass));

        headerInfo = new HeaderInfo(
                "Schüler anzeigen",
                "<html>Hier haben Sie einen Überblick über alle Schüler der Klasse '<b>" + studentClass.getName() + "</b>'.<br>Anzahl: <b>" + studentSelection.getSize() + " Schüler</b></html>",
                CWUtils.loadIcon("cw/studentmanagementmodul/images/student.png"),
                CWUtils.loadIcon("cw/studentmanagementmodul/images/student.png")
        );
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public void dispose() {
        // Nothing to do
    }

    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public SelectionInList<Student> getStudentSelection() {
        return studentSelection;
    }

    public TableModel createStudentTableModel(ListModel listModel) {
        return new StudentTableModel(listModel);
    }

    public static class StudentTableModel extends AbstractTableModel {

        private ListModel listModel;

        public StudentTableModel(ListModel listModel) {
            this.listModel = listModel;
        }


        public int getRowCount() {
            return listModel.getSize();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Anrede";
                case 1:
                    return "Vorname";
                case 2:
                    return "2. Vorname";
                case 3:
                    return "Nachname";
                default:
                    return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                default:
                    return String.class;
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Student s = (Student) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    if(s.getCustomer().getGender()) {
                        return "Herr";
                    }
                    return "Frau";
                case 1:
                    return s.getCustomer().getForename();
                case 2:
                    return s.getCustomer().getForename2();
                case 3:
                    return s.getCustomer().getSurname();
                default:
                    return "";
            }
        }

    }
}
