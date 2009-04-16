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
import cw.boardingschoolmanagement.gui.helper.JXTableSelectionConverter;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author André Salmhofer
 */
public class SubjectView implements Disposable{
    private SubjectPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //Definieren der Objekte in der oberen Leiste
    private JButton newButton;
    private JButton editButton;
    private JButton deleteButton;
    //********************************************
    
    //Tabelle zur Darstellung der angelegten Kursteilnehmer
    private JXTable subjectTable;
    //********************************************

    private JViewPanel panel;
    
    public SubjectView(SubjectPresentationModel model) {
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

        newButton.setToolTipText(CWComponentFactory.createToolTip(
                "Neu",
                "Hier können Sie einen neuen Kursgegenstand hinzufügen!",
                "cw/coursemanagementmodul/images/subject_add.png"));
        editButton.setToolTipText(CWComponentFactory.createToolTip(
                "Bearbeiten",
                "Hier können Sie einen selektierten Kursgegenstand ändern!",
                "cw/coursemanagementmodul/images/subject_edit.png"));
        deleteButton.setToolTipText(CWComponentFactory.createToolTip(
                "Löschen",
                "Hier können Sie einen selektierten Kursgegenstand löschen!",
                "cw/coursemanagementmodul/images/subject_delete.png"));
        
        subjectTable = CWComponentFactory.createTable("Keine Gegenstände vorhanden!");
        subjectTable.setModel(model.createSubjectTableModel(model.getSubjectSelection()));
        subjectTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getSubjectSelection().getSelectionIndexHolder(),
                        subjectTable)));

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(deleteButton)
                .addComponent(editButton)
                .addComponent(newButton)
                .addComponent(subjectTable);

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
        
        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref","pref");
        panel.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();
        
        panel.getContentPanel().add(new JScrollPane(subjectTable), BorderLayout.CENTER);
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
