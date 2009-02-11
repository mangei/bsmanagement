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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;
import javax.swing.table.TableModel;
import cw.coursemanagementmodul.pojo.CoursePosting;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.manager.CoursePostingManager;
import cw.coursemanagementmodul.pojo.manager.ValueManager;

/**
 *
 * @author André Salmhofer
 */
public class DetailHistoryPresentationModel {
    private Action backAction;
    private SelectionInList<CoursePosting> accountings;
    private CourseParticipant selectedCourseParticipant;

    private HeaderInfo headerInfo;
    private HeaderInfo headerInfoSaldo;

    public DetailHistoryPresentationModel(CourseParticipant courseParticipant) {
        System.out.println("SELECTED = " + courseParticipant.getCostumer().getForename());
        selectedCourseParticipant = courseParticipant;
        initModels();
        initEventHandling();

        headerInfo = new  HeaderInfo(
                "Buchungsdetailansicht für Ferienkurse",
                    "<html>" + courseParticipant.getCostumer().getTitle()
                    + " " + courseParticipant.getCostumer().getForename()
                    + " " + courseParticipant.getCostumer().getSurname() + "<br/>"
                    + courseParticipant.getCostumer().getStreet() + "<br/>"
                    + courseParticipant.getCostumer().getPostOfficeNumber()
                    + " " + courseParticipant.getCostumer().getCity() + "</html>",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/detail.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/detail.png"));

        headerInfoSaldo = new HeaderInfo(
                "Buchungsübersicht für den Kurs",
                "Sie befinden sich im Kursverwaltungsbereich. Hier können Sie Kurse anlegen",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"));
    }

    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels() {
        backAction = new BackAction("Zurück");
        accountings = new SelectionInList<CoursePosting>();
        setPostingsOfOneCourseParticipant();
    }

    //**************************************************************************

    //**************************************************************************
    //Methode, die ein CourseTableModel erzeugt.
    //Die private Klasse befindet sich in der gleichen Datei
    //**************************************************************************
    TableModel createCoursePostingTableModel(SelectionInList<CoursePosting> coursePosting) {
        return new CoursePostingTableModel(coursePosting);
    }

    private void setPostingsOfOneCourseParticipant() {
        SelectionInList<CoursePosting> cA = new SelectionInList<CoursePosting>(CoursePostingManager.getInstance().getAll());
        for(int i = 0; i < cA.getList().size(); i++){
            if(cA.getList().get(i).getPosting().getCustomer().equals(selectedCourseParticipant.getCostumer())){
                accountings.getList().add(cA.getList().get(i));
            }
        }
        System.out.println("ANZAHL DER ACCOUNTINGS = " + accountings.getList().size());
    }
    //**************************************************************************

    private void initEventHandling() {
        
    }
    
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
    private class CoursePostingTableModel extends AbstractTableAdapter<CoursePosting> {

        private ListModel listModel;

        public CoursePostingTableModel(ListModel listModel) {
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
                    return "Soll";
                case 1:
                    return "Haben";
                case 2:
                    return "Beschreibung";
                default:
                    return "";
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            CoursePosting coursePosting = (CoursePosting) listModel.getElementAt(rowIndex);
            int i = 0;
            String string = "";
            switch (columnIndex) {
                case 0:
                    if(coursePosting.getPosting().isLiabilities()){//isSoll
                        return coursePosting.getPosting().getAmount() + "";
                    }
                    else return "";
                case 1:
                    if(coursePosting.getPosting().isAssets()){//isHaben
                        return coursePosting.getPosting().getAmount() + "";
                    }
                    else return "";
                case 2:
                    return coursePosting.getPosting().getDescription();
                default:
                    return "";
            }
        }
    }
    //**************************************************************************

    private void updateActionEnablement() {
    }

    private class BackAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/back.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.changeToLastView();
        }

        private BackAction(String string) {
            super(string);
        }
    }

    Action getBackAction() {
        return backAction;
    }

    public SelectionInList<CoursePosting> getPostings() {
        return accountings;
    }

    public CourseParticipant getSelectedCourseParticipant() {
        return selectedCourseParticipant;
    }

    public double getTotalSoll(){
       return ValueManager.getTotalSoll(selectedCourseParticipant);
    }

    public double getTotalHaben(){
       return ValueManager.getTotalHaben(selectedCourseParticipant);
    }

    public double getTotalSaldo(){
       return ValueManager.getTotalSaldo(selectedCourseParticipant);
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public HeaderInfo getHeaderInfoSaldo() {
        return headerInfoSaldo;
    }
}
