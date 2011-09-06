package cw.coursemanagementmodul.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.coursemanagementmodul.pojo.Activity;
import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.Subject;
import cw.coursemanagementmodul.pojo.manager.ActivityManager;
import cw.coursemanagementmodul.pojo.manager.CourseManager;
import cw.coursemanagementmodul.pojo.manager.SubjectManager;

/**
 *
 * @author André Salmhofer
 */
public class CourseChooserPresentationModel
{
    
    //Definieren der Objekte in der oberen Leiste
    private Action addButtonAction;
    private Action cancelButtonAction;
    //********************************************
    
    //Liste der Kurse
    private SelectionInList<Course> courseSelection;
    //**********************************************

//    private CheckBoxListSelectionModel activitySelectionModel;
    private ListSelectionModel activitySelectionModel;
    private DefaultListModel activityModel;

//    private CheckBoxListSelectionModel subjectSelectionModel;
    private ListSelectionModel subjectSelectionModel;
    private DefaultListModel subjectModel;
    
    private Course courseItem;
    
    private ButtonListenerSupport support;
    private ButtonEvent buttonEvent;
    
    private ValueModel nameVM;
    private ValueModel vonVM;
    private ValueModel bisVM;
    private ValueModel priceVM;

    private CWHeaderInfo headerInfo;

    private SelectionHandler selectionHandler;
    
    public CourseChooserPresentationModel() {
        initModels();
        initEventHandling();
    }
    
    //**************************************************************************
    //Initialisieren der Objekte
    //**************************************************************************
    public void initModels() {
        buttonEvent = new ButtonEvent(ButtonEvent.OK_BUTTON);

        headerInfo = new CWHeaderInfo(
                "Alle Ferienkurse",
                "<html>Sie befinden sich im Kurs-Auswahlbereich.<br/>Hier können Sie dem Kursteilnehmer" +
                " einen Kurs mit beliebig vielen Aktivitäten und Gegenständen zuweisen!</html>",
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"),
                CWUtils.loadIcon("cw/coursemanagementmodul/images/course.png"));

        //Anlegen der Aktionen, fuer die Buttons
        addButtonAction = new AddAction("Hinzufuegen");
        cancelButtonAction = new CancelButtonAction("Schließen");
        courseSelection = new SelectionInList<Course>(CourseManager.getInstance().getAll());
        support = new ButtonListenerSupport();
        
        //----------------------------------------------------------------------
        nameVM = new ValueHolder();
        vonVM = new ValueHolder();
        bisVM = new ValueHolder();
        priceVM = new ValueHolder();
        //----------------------------------------------------------------------

//        activitySelectionModel = new CheckBoxListSelectionModel();
        activitySelectionModel = new DefaultListSelectionModel();
        activitySelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        activityModel = new DefaultListModel();
        List<Activity> activityList = ActivityManager.getInstance().getAll();
        for (int i=0, l=activityList.size(); i<l; i++) {
            activityModel.addElement(activityList.get(i));
        }

//        subjectSelectionModel = new CheckBoxListSelectionModel();
        subjectSelectionModel = new DefaultListSelectionModel();
        subjectSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        subjectModel = new DefaultListModel();
        List<Subject> subjectList = SubjectManager.getInstance().getAll();
        for (int i=0, l=subjectList.size(); i<l; i++) {
            subjectModel.addElement(subjectList.get(i));
        }
    }
    //**************************************************************************

    private void initEventHandling() {
        courseSelection.addValueChangeListener(selectionHandler = new SelectionHandler());

        updateCourseLabels();
        updateActionEnablement();
    }

    public void dispose() {
        courseSelection.removeValueChangeListener(selectionHandler);
        courseSelection.release();
    }
    
    //**************************************************************************
    //Methode, die ein CourseTableModel erzeugt.
    //Die private Klasse befindet sich in der gleichen Datei
    //**************************************************************************
    TableModel createCourseTableModel(ListModel listModel) {
        return new CourseTableModel(listModel);
    }
    //**************************************************************************
     

    private class SelectionHandler implements PropertyChangeListener{
        public void propertyChange(PropertyChangeEvent evt) {
                updateCourseLabels();
                updateActionEnablement();
            }
    }
    
    private void updateCourseLabels(){
        if(courseSelection.hasSelection()){
            nameVM.setValue(courseSelection.getSelection().getName());
            vonVM.setValue(courseSelection.getSelection().getBeginDate());
            bisVM.setValue(courseSelection.getSelection().getEndDate());
            priceVM.setValue(courseSelection.getSelection().getPrice());
        }
        else{
            nameVM.setValue("");
            vonVM.setValue(new Date());
            bisVM.setValue(new Date());
            priceVM.setValue(0.0);
        }
    }

    private void updateActionEnablement() {
        boolean hasSelection = courseSelection.hasSelection();
        addButtonAction.setEnabled(hasSelection);
    }
    
    //**************************************************************************
    //Private Klasse, die Events behandelt, die das Neuanlegen
    //von Kursen beinhaltet
    //**************************************************************************
    private class AddAction extends AbstractAction{
          {
            putValue( Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/course_add.png") );
          }
         
         private AddAction(String name) {
            super(name);
         }

        public void actionPerformed(ActionEvent e) {
            courseItem = courseSelection.getSelection();
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
    
    public SelectionInList<Course> getCourseSelection(){
        return courseSelection;
    }
    
    //**************************************************************************
    //CourseTableModel, das die Art der Anzeige von Kursen regelt.
    //**************************************************************************
    private class CourseTableModel extends AbstractTableAdapter<Course> {

        private ListModel listModel;
        private DecimalFormat numberFormat;

        public CourseTableModel(ListModel listModel) {
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
                    return "Kursname";
                case 1:
                    return "Von";
                case 2:
                    return "Bis";
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
            Course course = (Course) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return course.getName();
                case 1:
                    return course.getBeginDate();
                case 2:
                    return course.getEndDate();
                case 3:
                    return "€ " + numberFormat.format(course.getPrice());
                default:
                    return "";
            }
        }
    }

    public Course getCourseItem() {
        return courseItem;
    }

    public void setCourseItem(Course courseItem) {
        this.courseItem = courseItem;
    }

    public ValueModel getBisVM() {
        return bisVM;
    }

    public ValueModel getNameVM() {
        return nameVM;
    }

    public ValueModel getPriceVM() {
        return priceVM;
    }

    public ValueModel getVonVM() {
        return vonVM;
    }
    //**************************************************************************
    
    private class ActivityListCellRenderer implements ListCellRenderer{
        private JPanel panel;
        private JLabel name;
        private JLabel desc;
        private CellConstraints cc;
        private FormLayout layout;
        private JSeparator sep;

        public ActivityListCellRenderer(){
            panel = new JPanel();
            name = new JLabel();
            name.setFont(name.getFont().deriveFont(15f));
            desc = new JLabel();
            layout = new FormLayout("pref", "pref, pref, 2dlu, pref");
            cc = new CellConstraints();
            sep = new JSeparator();

            panel.setLayout(layout);
            panel.add(name, cc.xy(1, 1));
            panel.add(sep,  cc.xy(1, 2));
            panel.add(desc, cc.xy(1, 4));
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            name.setText(((Activity)value).getName());
            desc.setText(((Activity)value).getDescription());
            return panel;
        }
    }

    ActivityListCellRenderer createActivityListCellRenderer(){
        return new ActivityListCellRenderer();
    }

     private class SubjectListCellRenderer implements ListCellRenderer{
        private JPanel panel;
        private JLabel name;
        private CellConstraints cc;
        private FormLayout layout;

        public SubjectListCellRenderer(){
            panel = new JPanel();
            name = new JLabel();
            layout = new FormLayout("pref", "pref");
            cc = new CellConstraints();

            panel.setLayout(layout);
            panel.add(name, cc.xy(1, 1));
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            name.setText(((Subject)value).getName());
            return panel;
        }
    }

    SubjectListCellRenderer createSubjectListCellRenderer(){
        return new SubjectListCellRenderer();
    }

    public ListSelectionModel getActivitySelectionModel() {
        return activitySelectionModel;
    }

    public DefaultListModel getActivityModel() {
        return activityModel;
    }

    public ListSelectionModel getSubjectSelection() {
        return subjectSelectionModel;
    }

    public DefaultListModel getSubjectModel() {
        return subjectModel;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }
}
