/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.coursemanagementmodul.gui;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ListModel;
import javax.swing.table.TableModel;
import cw.coursemanagementmodul.pojo.CoursePosting;
import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.manager.CoursePostingManager;
import cw.coursemanagementmodul.pojo.manager.CourseManager;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;
import cw.customermanagementmodul.pojo.Posting;
import cw.customermanagementmodul.pojo.manager.PostingManager;
import javax.swing.JOptionPane;

/**
 *
 * @author André Salmhofer
 */
public class CoursePostingPresentationModel extends PresentationModel<CoursePosting>{
    
    //Definieren der Objekte in der oberen Leiste
    private Action postingAction;
    private Action normalRadioButtonAction;
    private Action testRadioButtonAction;
    //********************************************

    //Liste der Kurse
    private SelectionInList<CourseParticipant> coursePartSelection;
    private SelectionInList<Course> courseSelection;
    //**********************************************

    private PresentationModel<Posting> postingPresentationModel;

    private HeaderInfo headerInfo;

    private Course selectedCourse;
    private boolean isEchtlauf = false;

    public CoursePostingPresentationModel(CoursePosting coursePosting) {
        super(coursePosting);
        initModels();
        initEventHandling();

        headerInfo = new HeaderInfo(
                "Kursbuchungen",
                "Sie befinden sich im Kursbuchungsbereich. Hier können Sie Kursbuchungen tätigen",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/accounting.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/accounting.png"));
    }

    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels() {
        //Anlegen der Aktionen, für die Buttons
        postingAction = new PostingAction("Buchen");
        normalRadioButtonAction = new NormalRadioButtonAction("Echtlauf");
        testRadioButtonAction = new TestRadioButtonAction("Testlauf");

        coursePartSelection = new SelectionInList<CourseParticipant>();
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

                headerInfo.setHeaderText("Kursbuchungen des Kurses " + selectedCourse.getName());
                headerInfo.setDescription("<html>Von " +
                        selectedCourse.getBeginDate() + " bis " + selectedCourse.getEndDate()
                        + "<br/>Preis pro Kurs: " + selectedCourse.getPrice() +
                        "<br/> Anzahl der Kursteilnehmer: " + list.size() + "</html>");

                System.out.println("SELECTED COURSE = " + selectedCourse.getName());
            }
        });

        postingPresentationModel = new PresentationModel<Posting>(getBufferedModel(CoursePosting.PROPERTYNAME_POSTING));
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

    private class PostingAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/accounting.png"));
        }

        public void actionPerformed(ActionEvent e) {
            if(isEchtlauf){
                doPosting();
                JOptionPane.showMessageDialog(null, "Kursteilnehmer wurden gebucht!");
            }
            else{
                JOptionPane.showMessageDialog(null, "Testlauf!");
            }
        }

        private PostingAction(String string) {
            super(string);
        }
    }

    private class NormalRadioButtonAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/accounting.png"));
        }

        public void actionPerformed(ActionEvent e) {
            isEchtlauf = true;
        }

        private NormalRadioButtonAction(String string) {
            super(string);
        }
    }

    private class TestRadioButtonAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/accounting.png"));
        }

        public void actionPerformed(ActionEvent e) {
            isEchtlauf = false;
        }

        private TestRadioButtonAction(String string) {
            super(string);
        }
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
                    return "Gesamtbetrag";
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
            switch (columnIndex) {
                case 0:
                    return coursePart.getCostumer().getTitle();
                case 1:
                    return coursePart.getCostumer().getForename();
                case 2:
                    return coursePart.getCostumer().getSurname();
                case 3:
                    return coursePart.getCostumer().getStreet();
                case 4:
                    return coursePart.getCostumer().getPostOfficeNumber();
                case 5:
                    return coursePart.getCostumer().getCity();
                case 6:
                    return selectedCourse.getPrice();
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

    public Action getPostingAction() {
        return postingAction;
    }

    public void doPosting(){
        for(int i = 0; i < coursePartSelection.getSize(); i++){
            for(int j = 0; j < coursePartSelection.getList().get(i).getCourseList().size(); j++){
                if(coursePartSelection.getList().get(i).getCourseList().get(j).getCourse().getId()
                        == selectedCourse.getId()){
                    Posting a = new Posting();
                    a.setAmount(coursePartSelection.getList().get(i).getCourseList().get(j).getCourse().getPrice());
                    a.setCustomer(coursePartSelection.getList().get(i).getCostumer());
                    a.setDescription(coursePartSelection.getList().get(i).getCourseList().get(j).getCourse().getName());
                    a.setLiabilitiesAssets(true);
                    PostingManager.getInstance().save(a);

                    CoursePosting coursePosting = new CoursePosting();
                    coursePosting.setPosting(a);
                    coursePosting.setCourseAddition(coursePartSelection.getList().get(i).getCourseList().get(j));
                    CoursePostingManager.getInstance().save(coursePosting);
                }
            }
        }
    }

    public void save() {
        postingPresentationModel.triggerCommit();
        triggerCommit();
    }

    public void reset() {
        postingPresentationModel.triggerFlush();
        triggerFlush();
    }

    public PresentationModel<Posting> getPostingPresentationModel() {
        return postingPresentationModel;
    }
    
    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public Action getNormalRadioButtonAction(){
        return normalRadioButtonAction;
    }

    public Action getTestRadioButtonAction() {
        return testRadioButtonAction;
    }
}
