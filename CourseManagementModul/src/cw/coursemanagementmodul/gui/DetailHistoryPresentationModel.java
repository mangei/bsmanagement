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
import cw.coursemanagementmodul.pojo.CoursePosting;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.manager.CoursePostingManager;
import cw.coursemanagementmodul.pojo.manager.ValueManager;
import java.text.DecimalFormat;

/**
 *
 * @author André Salmhofer
 */
public class DetailHistoryPresentationModel implements Disposable{
    private Action backAction;
    private SelectionInList<CoursePosting> accountings;
    private CourseParticipant selectedCourseParticipant;

    private HeaderInfo headerInfo;
    private HeaderInfo headerInfoSaldo;

    public DetailHistoryPresentationModel(CourseParticipant courseParticipant) {
        System.out.println("SELECTED = " + courseParticipant.getCustomer().getForename());
        selectedCourseParticipant = courseParticipant;
        initModels();
        initEventHandling();

        headerInfo = new  HeaderInfo(
                "Buchungsdetailansicht für Ferienkurse",
                    "<html>" + courseParticipant.getCustomer().getTitle()
                    + " " + courseParticipant.getCustomer().getForename()
                    + " " + courseParticipant.getCustomer().getSurname() + "<br/>"
                    + courseParticipant.getCustomer().getStreet() + "<br/>"
                    + courseParticipant.getCustomer().getPostOfficeNumber()
                    + " " + courseParticipant.getCustomer().getCity() + "</html>",
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
            if(cA.getList().get(i).getPosting().getCustomer().equals(selectedCourseParticipant.getCustomer())){
                accountings.getList().add(cA.getList().get(i));
            }
        }
        System.out.println("ANZAHL DER ACCOUNTINGS = " + accountings.getList().size());
    }
    //**************************************************************************

    private void initEventHandling() {
        
    }

    public void dispose() {
        
    }

    //**************************************************************************
    //CourseTableModel, das die Art der Anzeige von Kursen regelt.
    //**************************************************************************
    private class CoursePostingTableModel extends AbstractTableAdapter<CoursePosting> {

        private ListModel listModel;
        private DecimalFormat numberFormat;

        public CoursePostingTableModel(ListModel listModel) {
            super(listModel);
            this.listModel = listModel;
            numberFormat = new DecimalFormat("#0.00");
        }

        @Override
        public int getColumnCount() {
            return 4;
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
                case 3:
                    return "Buchungsdatum";
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
            switch (columnIndex) {
                case 0:
                    if(coursePosting.getPosting().isLiabilities()){//isSoll
                        return "€ " + numberFormat.format(coursePosting.getPosting().getAmount());
                    }
                    else return "";
                case 1:
                    if(coursePosting.getPosting().isAssets()){//isHaben
                        return "€ " + numberFormat.format(coursePosting.getPosting().getAmount());
                    }
                    else return "";
                case 2:
                    return coursePosting.getPosting().getDescription();
                case 3:
                    return coursePosting.getPosting().getPostingDate();
                default:
                    return "";
            }
        }
    }
    
    private class BackAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/back.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().unlockMenu();
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
       return ValueManager.getInstance().getTotalSoll(selectedCourseParticipant);
    }

    public double getTotalHaben(){
       return ValueManager.getInstance().getTotalHaben(selectedCourseParticipant);
    }

    public double getTotalSaldo(){
       return ValueManager.getInstance().getTotalSaldo(selectedCourseParticipant);
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public HeaderInfo getHeaderInfoSaldo() {
        return headerInfoSaldo;
    }
}
