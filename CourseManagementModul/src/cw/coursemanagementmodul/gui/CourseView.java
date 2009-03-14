/*
 * Die Course View representiert das Startfenster,
 * dass angezeigt wird, wenn man auf 'Kurs' in der
 * linken Auswahlliste klickt.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.helper.JXTableSelectionConverter;
import cw.boardingschoolmanagement.gui.renderer.DateTimeTableCellRenderer;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author André Salmhofer
 */
public class CourseView implements Disposable{
    private CoursePresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //Definieren der Objekte in der oberen Leiste
    private JButton newButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton detailButton;
    //********************************************
    
    //Tabelle zur Darstellung der angelegten Kurse
    private JXTable courseTable;
    //********************************************

    private JViewPanel panel;
    
    public CourseView(CoursePresentationModel model) {
        this.model = model;
    }
    
    private void initEventHandling() {
    }
    
    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){
        newButton    = CWComponentFactory.createButton(model.getNewButtonAction());
        editButton   = CWComponentFactory.createButton(model.getEditButtonAction());
        deleteButton = CWComponentFactory.createButton(model.getDeleteButtonAction());
        detailButton = CWComponentFactory.createButton(model.getDetailButtonAction());
        
        courseTable = CWComponentFactory.createTable("Kein Kurs vorhanden!");
        
        courseTable.setModel(model.createCourseTableModel(model.getCourseSelection()));

        courseTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getCourseSelection().getSelectionIndexHolder(),
                        courseTable)));
        
        courseTable.getColumns(true).get(1).setCellRenderer(new DateTimeTableCellRenderer("dd.MM.yyyy"));
        courseTable.getColumns(true).get(2).setCellRenderer(new DateTimeTableCellRenderer("dd.MM.yyyy"));

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(courseTable)
                .addComponent(deleteButton)
                .addComponent(detailButton)
                .addComponent(editButton)
                .addComponent(newButton)
                .addComponent(editButton);

        panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());
    }
    //**************************************************************************
    
    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        initEventHandling();
        
        panel.getButtonPanel().add(newButton);
        panel.getButtonPanel().add(editButton);
        panel.getButtonPanel().add(deleteButton);
        panel.getButtonPanel().add(detailButton);
        
        FormLayout layout = new FormLayout("fill:pref:grow","fill:pref:grow");
        panel.getContentPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();
        
        panel.getContentPanel().add(new JScrollPane(courseTable), cc.xy(1, 1));
        panel.addDisposableListener(this);
        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
    //********************************************
}
