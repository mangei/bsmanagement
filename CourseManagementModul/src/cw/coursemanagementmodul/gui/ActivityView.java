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
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

/**
 *
 * @author André Salmhofer
 */
public class ActivityView {
    private ActivityPresentationModel model;
    
    //Definieren der Objekte in der oberen Leiste
    private JButton newButton;
    private JButton editButton;
    private JButton deleteButton;
    //********************************************
    
    //Tabelle zur Darstellung der angelegten Kurse
    private JXTable activityTable;
    //********************************************
    
    public ActivityView(ActivityPresentationModel model) {
        this.model = model;
    }
    
    private void initEventHandling() {
        activityTable.addMouseListener(model.getDoubleClickHandler());
    }
    
    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){
        newButton    = CWComponentFactory.createButton(model.getNewButtonAction());
        editButton   = CWComponentFactory.createButton(model.getEditButtonAction());
        deleteButton = CWComponentFactory.createButton(model.getDeleteButtonAction());
        
        activityTable = CWComponentFactory.createTable("");
        activityTable.setColumnControlVisible(true);
        activityTable.setAutoCreateRowSorter(true);
        activityTable.setPreferredScrollableViewportSize(new Dimension(10,10));
        activityTable.setHighlighters(HighlighterFactory.createSimpleStriping());
        
        activityTable.setModel(model.createActivityTableModel(model.getActivitySelection()));
        activityTable.setSelectionModel(
                new SingleListSelectionAdapter(
                model.getActivitySelection().getSelectionIndexHolder()));
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
        
        panel.getContentPanel().add(new JScrollPane(activityTable), BorderLayout.CENTER);
        
        return panel;
    }
    //**************************************************************************
}
