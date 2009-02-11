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
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

/**
 *
 * @author André Salmhofer
 */
public class SubjectView {
    private SubjectPresentationModel model;
    
    //Definieren der Objekte in der oberen Leiste
    private JButton newButton;
    private JButton editButton;
    private JButton deleteButton;
    //********************************************
    
    //Tabelle zur Darstellung der angelegten Kursteilnehmer
    private JXTable subjectTable;
    //********************************************
    
    public SubjectView(SubjectPresentationModel model) {
        this.model = model;
    }
    
    private void initEventHandling() {
        subjectTable.addMouseListener(model.getDoubleClickHandler());
    }
    
    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){
        newButton    = CWComponentFactory.createButton(model.getNewButtonAction());
        editButton   = CWComponentFactory.createButton(model.getEditButtonAction());
        deleteButton = CWComponentFactory.createButton(model.getDeleteButtonAction());
        
        subjectTable = CWComponentFactory.createTable("");
        subjectTable.setColumnControlVisible(true);
        subjectTable.setAutoCreateRowSorter(true);
        subjectTable.setPreferredScrollableViewportSize(new Dimension(10,10));
        subjectTable.setHighlighters(HighlighterFactory.createSimpleStriping());
        
        subjectTable.setModel(model.createSubjectTableModel(model.getSubjectSelection()));
        subjectTable.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getSubjectSelection().getSelectionIndexHolder()));
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
        
        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref","pref");
        panel.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();
        
        panel.getContentPanel().add(new JScrollPane(subjectTable), BorderLayout.CENTER);
        
        return panel;
    }
    //********************************************
}
