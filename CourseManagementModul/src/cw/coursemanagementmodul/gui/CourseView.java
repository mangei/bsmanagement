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
import cw.boardingschoolmanagement.gui.renderer.DateTimeTableCellRenderer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

/**
 *
 * @author André Salmhofer
 */
public class CourseView{
    private CoursePresentationModel model;
    
    //Definieren der Objekte in der oberen Leiste
    private JButton newButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton detailButton;
    //********************************************
    
    //Tabelle zur Darstellung der angelegten Kurse
    private JXTable courseTable;
    private JTextField searchTextField;
    //********************************************
    
    public CourseView(CoursePresentationModel model) {
        this.model = model;
    }
    
    private void initEventHandling() {
        courseTable.addMouseListener(model.getDoubleClickHandler());
    }
    
    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){
        newButton    = CWComponentFactory.createButton(model.getNewButtonAction());
        editButton   = CWComponentFactory.createButton(model.getEditButtonAction());
        deleteButton = CWComponentFactory.createButton(model.getDeleteButtonAction());
        detailButton = CWComponentFactory.createButton(model.getDetailButtonAction());
        
        courseTable = new JXTable();
        courseTable.setColumnControlVisible(true);
        courseTable.setAutoCreateRowSorter(true);
        courseTable.setPreferredScrollableViewportSize(new Dimension(10,10));
        courseTable.setHighlighters(HighlighterFactory.createSimpleStriping());
        
        courseTable.setModel(model.createCourseTableModel(model.getCourseSelection()));
        courseTable.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getCourseSelection().getSelectionIndexHolder()));
        courseTable.getColumn(1).setCellRenderer(new DateTimeTableCellRenderer(true));
        courseTable.getColumn(2).setCellRenderer(new DateTimeTableCellRenderer(true));
        
        searchTextField = new JTextField();
    }
    //**************************************************************************
    
    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        initEventHandling();
        JViewPanel panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());
        
        panel.getButtonPanel().add(newButton);
        panel.getButtonPanel().add(editButton);
        panel.getButtonPanel().add(deleteButton);
        panel.getButtonPanel().add(detailButton);
        
        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref","pref");
        panel.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();
        
        panel.getTopPanel().add(searchTextField, cc.xy(3, 1));
        
        panel.getContentPanel().add(new JScrollPane(courseTable), BorderLayout.CENTER);
        
        return panel;
    }
    //********************************************
}
