/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;
import javax.swing.table.TableModel;
import cw.coursemanagementmodul.pojo.Subject;
import cw.coursemanagementmodul.pojo.manager.SubjectManager;

/**
 *
 * @author André Salmhofer
 */
public class SubjectChooserPresentationModel extends PresentationModel<Subject>{

    //Definieren der Objekte in der oberen Leiste
    private Action addButtonAction;
    private Action cancelButtonAction;
    //********************************************

    //Liste der Kurse
    private SelectionInList<Subject> subjectSelection;
    //**********************************************

    private Subject subjectItem;

    private ButtonListenerSupport support;
    private ButtonEvent buttonEvent;

    private HeaderInfo headerInfo;

    public SubjectChooserPresentationModel(Subject subject) {
        super(subject);
        initModels();
        initEventHandling();
        buttonEvent = new ButtonEvent(ButtonEvent.OK_BUTTON);
    }

    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels() {
        //Anlegen der Aktionen, für die Buttons
        addButtonAction = new AddAction("Hinzufügen");
        cancelButtonAction = new CancelButtonAction("Schließen");
        subjectSelection = new SelectionInList<Subject>(SubjectManager.getInstance().getAll());
        support = new ButtonListenerSupport();

        headerInfo = new HeaderInfo(
                "Kursgegenstand hinzufügen!",
                "Hier können Sie Gegenstände zum markierten Kurs hinzufügen!",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/subject.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/subject.png"));
    }
    //**************************************************************************

    //**************************************************************************
    //Methode, die ein CourseTableModel erzeugt.
    //Die private Klasse befindet sich in der gleichen Datei
    //**************************************************************************
    TableModel createSubjectTableModel(SelectionInList<Subject> subjectSelection) {
        return new SubjectTableModel(subjectSelection);
    }
    //**************************************************************************


    private void initEventHandling() {
        subjectSelection.addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());

    }

    //**************************************************************************
    //Private Klasse, die Events behandelt, die das Neuanlegen
    //von Kursen beinhaltet
    //**************************************************************************
    private class AddAction extends AbstractAction{
          {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/subject_add.png") );
          }

         private AddAction(String name) {
            super(name);
         }

        public void actionPerformed(ActionEvent e) {
            subjectItem = subjectSelection.getSelection();
            support.fireButtonPressed(buttonEvent);
        }
    }
    //**************************************************************************

    //**************************************************************************
    //Klasse zum Beenden des Eingabeformulars. Wechselt anschließend
    //in die letzte View
    //**************************************************************************
    private class CancelButtonAction extends AbstractAction{
        {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/cancel.png") );
        }

        public void actionPerformed(ActionEvent e){
            GUIManager.changeToLastView();
        }

        private CancelButtonAction(String string){
            super(string);
        }
    }
    //**************************************************************************

    //**************************************************************************
    //Getter.- und Setter-Methoden
    //**************************************************************************
    public Action getAddButtonAction() {
        return addButtonAction;
    }

    public void setAddButtonAction(Action newButtonAction) {
        this.addButtonAction = newButtonAction;
    }

    public Action getCancelButtonAction() {
        return cancelButtonAction;
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }
    //**************************************************************************



    private final class SelectionEmptyHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            //updateActionEnablement();
        }
    }

    public SelectionInList<Subject> getSubjectSelection(){
        return subjectSelection;
    }

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
                    return "Gegenstandsname";
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

    public Subject getSubjectItem() {
        return subjectItem;
    }

    public void setSubjectItem(Subject subjectItem) {
        this.subjectItem = subjectItem;
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }
    //**************************************************************************
}
