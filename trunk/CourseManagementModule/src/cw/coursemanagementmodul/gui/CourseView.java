package cw.coursemanagementmodul.gui;

import javax.swing.JScrollPane;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import cw.boardingschoolmanagement.gui.renderer.DateTimeTableCellRenderer;

/**
 * Die Course View representiert das Startfenster,
 * dass angezeigt wird, wenn man auf 'Kurs' in der
 * linken Auswahlliste klickt.
 * 
 * @author André Salmhofer (CreativeWorkers)
 */
public class CourseView extends CWView
{
    private CoursePresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //Definieren der Objekte in der oberen Leiste
    private CWButton newButton;
    private CWButton editButton;
    private CWButton deleteButton;
    private CWButton detailButton;
    //********************************************
    
    //Tabelle zur Darstellung der angelegten Kurse
    private CWTable courseTable;
    //********************************************

    public CourseView(CoursePresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }
    
    
    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    private void initComponents(){
        newButton    = CWComponentFactory.createButton(model.getNewButtonAction());
        editButton   = CWComponentFactory.createButton(model.getEditButtonAction());
        deleteButton = CWComponentFactory.createButton(model.getDeleteButtonAction());
        detailButton = CWComponentFactory.createButton(model.getDetailButtonAction());

        newButton.setToolTipText(CWComponentFactory.createToolTip(
                "Neu",
                "Hier können Sie einen neuen Kurs hinzufuegen!",
                "cw/coursemanagementmodul/images/course_add.png"));
        editButton.setToolTipText(CWComponentFactory.createToolTip(
                "Bearbeiten",
                "Hier können Sie einen selektierten Kurs ändern!",
                "cw/coursemanagementmodul/images/course_edit.png"));
        deleteButton.setToolTipText(CWComponentFactory.createToolTip(
                "Löschen",
                "Hier können Sie einen selektierten Kurs löschen!",
                "cw/coursemanagementmodul/images/course_delete.png"));
        detailButton.setToolTipText(CWComponentFactory.createToolTip(
                "Detailansicht",
                "Hier sehen Sie alle Kursteilnehmer des selektierten Kurses!",
                "cw/coursemanagementmodul/images/detail.png"));
        
        courseTable = CWComponentFactory.createTable("Kein Kurs vorhanden!");
        
        courseTable.setModel(model.createCourseTableModel(model.getCourseSelection()));

        courseTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getCourseSelection().getSelectionIndexHolder(),
                        courseTable)));
        
        courseTable.getColumns(true).get(1).setCellRenderer(new DateTimeTableCellRenderer("dd.MM.yyyy"));
        courseTable.getColumns(true).get(2).setCellRenderer(new DateTimeTableCellRenderer("dd.MM.yyyy"));

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(courseTable)
                .addComponent(deleteButton)
                .addComponent(detailButton)
                .addComponent(editButton)
                .addComponent(newButton)
                .addComponent(editButton);
        
    }
    //**************************************************************************

    private void initEventHandling() {
    }

    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurueck
    //**************************************************************************
    private void buildView(){

        this.setHeaderInfo(model.getHeaderInfo());

        this.getButtonPanel().add(newButton);
        this.getButtonPanel().add(editButton);
        this.getButtonPanel().add(deleteButton);
        this.getButtonPanel().add(detailButton);
        
        FormLayout layout = new FormLayout("fill:pref:grow","fill:pref:grow");
        this.getContentPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();
        
        this.getContentPanel().add(new JScrollPane(courseTable), cc.xy(1, 1));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
    //********************************************
}
