/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

/**
 *
 * @author André Salmhofer
 */
public class CoursePartView {
    private CoursePartPresentationModel model;
    
    //Definieren der Objekte in der oberen Leiste
    private JButton newButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton detailButton;
    //********************************************
    
    //Tabelle zur Darstellung der angelegten Kursteilnehmer
    private JXTable coursePartTable;
    private JTextField searchTextField;
    //********************************************
    
    public CoursePartView(CoursePartPresentationModel model) {
        this.model = model;
    }
    
    private void initEventHandling() {
        coursePartTable.addMouseListener(model.getDoubleClickHandler());
    }
    
    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){
        newButton    = CWComponentFactory.createButton(model.getNewButtonAction());
        editButton   = CWComponentFactory.createButton(model.getEditButtonAction());
        deleteButton = CWComponentFactory.createButton(model.getDeleteButtonAction());
        detailButton = CWComponentFactory.createButton(model.getDetailAction());
        
        coursePartTable = CWComponentFactory.createTable("");
        coursePartTable.setColumnControlVisible(true);
        coursePartTable.setAutoCreateRowSorter(true);
        coursePartTable.setPreferredScrollableViewportSize(new Dimension(10,10));
        coursePartTable.setHighlighters(HighlighterFactory.createSimpleStriping());
        
        coursePartTable.setModel(model.createCoursePartTableModel(model.getCoursePartSelection()));
        coursePartTable.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getCoursePartSelection().getSelectionIndexHolder()));
        
        searchTextField = CWComponentFactory.createTextField(null);
    }
    //**************************************************************************
    
    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        initEventHandling();
        JViewPanel panel = CWComponentFactory.createViewPanel();
        
        panel.getButtonPanel().add(newButton);
        panel.getButtonPanel().add(editButton);
        panel.getButtonPanel().add(deleteButton);
        panel.getButtonPanel().add(detailButton);
        
        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref","pref");
        panel.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();
        
        panel.getTopPanel().add(searchTextField, cc.xy(3, 1));
        
        panel.getContentPanel().add(CWComponentFactory.createScrollPane(coursePartTable), BorderLayout.CENTER);
        
        return panel;
    }
    //********************************************
}
