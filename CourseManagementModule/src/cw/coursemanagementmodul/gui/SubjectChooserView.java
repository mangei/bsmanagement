package cw.coursemanagementmodul.gui;

import javax.swing.JScrollPane;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;

/**
 * Die Course View representiert das Startfenster,
 * dass angezeigt wird, wenn man auf 'Kurs' in der
 * linken Auswahlliste klickt.
 * 
 * @author André Salmhofer
 */
public class SubjectChooserView extends CWView
{
    private SubjectChooserPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;

    //Definieren der Objekte in der oberen Leiste
    private CWButton addButton;
    private CWButton cancelButton;
    //********************************************

    //Tabelle zur Darstellung der angelegten Kurse
    private CWTable subjectTable;
    //********************************************

    public SubjectChooserView(SubjectChooserPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    private void initComponents(){
        addButton = CWComponentFactory.createButton(model.getAddButtonAction());
        cancelButton = CWComponentFactory.createButton(model.getCancelButtonAction());

        addButton.setToolTipText(CWComponentFactory.createToolTip(
                "Hinzufügen",
                "Hier können Sie den selektierten Kursgegenstand zum Kurs hinzufügen!",
                "cw/coursemanagementmodul/images/subject_add.png"));

        cancelButton.setToolTipText(CWComponentFactory.createToolTip(
                "Schließen",
                "Hier kehren Sie zur Ferienkursübersicht zurück!",
                "cw/coursemanagementmodul/images/cancel.png"));

        subjectTable = CWComponentFactory.createTable("Es wurden noch keine Gegenstände angelegt!");
        subjectTable.setModel(model.createSubjectTableModel(model.getSubjectSelection()));
        subjectTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getSubjectSelection().getSelectionIndexHolder(),
                        subjectTable)));

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(addButton)
                .addComponent(cancelButton)
                .addComponent(subjectTable);

    }
    //**************************************************************************

    private void initEventHandling() {
    }

    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    private void buildView(){

        this.setHeaderInfo(model.getHeaderInfo());

        this.getButtonPanel().add(addButton);
        this.getButtonPanel().add(cancelButton);

        FormLayout mainLayout = new FormLayout("pref:grow", "pref, 4dlu, fill:pref:grow");

        CellConstraints cc = new CellConstraints();

        PanelBuilder panelBuilder = new PanelBuilder(mainLayout, this.getContentPanel());

        panelBuilder.addSeparator("Kursgegenstandstabelle", cc.xy(1, 1));
        panelBuilder.add(new JScrollPane(subjectTable), cc.xy(1, 3));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        //model.dispose();
    }
    //********************************************
}
