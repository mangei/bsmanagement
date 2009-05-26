package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

/**
 *
 * @author André Salmhofer
 */
public class SubjectView extends CWView
{
    private SubjectPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //Definieren der Objekte in der oberen Leiste
    private CWButton newButton;
    private CWButton editButton;
    private CWButton deleteButton;
    //********************************************
    
    //Tabelle zur Darstellung der angelegten Kursteilnehmer
    private CWTable subjectTable;
    //********************************************

    public SubjectView(SubjectPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
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
                    new CWTableSelectionConverter(
                        model.getSubjectSelection().getSelectionIndexHolder(),
                        subjectTable)));

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(deleteButton)
                .addComponent(editButton)
                .addComponent(newButton)
                .addComponent(subjectTable);

        
    }

    private void initEventHandling() {
    }

    private void buildView(){

        this.setHeaderInfo(model.getHeaderInfo());
        
        this.getButtonPanel().add(newButton);
        this.getButtonPanel().add(editButton);
        this.getButtonPanel().add(deleteButton);
        
        this.getContentPanel().add(new JScrollPane(subjectTable), BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
    //********************************************
}
