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
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.CoursePosting;
import cw.coursemanagementmodul.pojo.manager.CoursePostingManager;
import cw.coursemanagementmodul.pojo.manager.ValueManager;

/**
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class DetailHistoryPresentationModel
{
    private Action backAction;
    private SelectionInList<CoursePosting> accountings;
    private CourseParticipant selectedCourseParticipant;

    private CWHeaderInfo headerInfo;
    private CWHeaderInfo headerInfoSaldo;

    public DetailHistoryPresentationModel(CourseParticipant courseParticipant) {
        System.out.println("SELECTED = " + courseParticipant.getCustomer().getForename());
        selectedCourseParticipant = courseParticipant;
        initModels();
        initEventHandling();
    }

    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels() {

        headerInfo = new CWHeaderInfo(
                "Buchungsdetailansicht fuer Ferienkurse",
                    new StringBuilder().append("<html>").append(selectedCourseParticipant.getCustomer().getTitle())
                    .append(" ").append(selectedCourseParticipant.getCustomer().getForename())
                    .append(" ").append(selectedCourseParticipant.getCustomer().getSurname()).append("<br/>")
                    .append(selectedCourseParticipant.getCustomer().getStreet()).append("<br/>")
                    .append(selectedCourseParticipant.getCustomer().getPostOfficeNumber())
                    .append(" ").append(selectedCourseParticipant.getCustomer().getCity()).append("</html>")
                    .toString(),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/detail.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/detail.png"));

        headerInfoSaldo = new CWHeaderInfo(
                "Buchungsuebersicht fuer den Kurs",
                "Sie befinden sich im Kursverwaltungsbereich. Hier koennen Sie Kurse anlegen",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"));

        backAction = new BackAction("Zurueck");
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
                    return "";
                case 1:
                    if(coursePosting.getPosting().isAssets()){//isHaben
                        return "€ " + numberFormat.format(coursePosting.getPosting().getAmount());
                    }
                    return "";
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

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public CWHeaderInfo getHeaderInfoSaldo() {
        return headerInfoSaldo;
    }
}
