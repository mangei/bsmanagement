package cw.coursemanagementmodul.gui;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;

/**
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class ActivityView extends CWView
{
    private ActivityPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //Definieren der Objekte in der oberen Leiste
    private CWButton newButton;
    private CWButton editButton;
    private CWButton deleteButton;
    //********************************************
    
    //Tabelle zur Darstellung der angelegten Kurse
    private CWTable activityTable;
    //********************************************
    
    public ActivityView(ActivityPresentationModel model) {
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
                "Hier koennen Sie einen neue Aktivitaet hinzufuegen!",
                "cw/coursemanagementmodul/images/activity_add.png"));
        editButton.setToolTipText(CWComponentFactory.createToolTip(
                "Bearbeiten",
                "Hier koennen Sie eine selektierte Aktivitaet aendern!",
                "cw/coursemanagementmodul/images/activity_edit.png"));
        deleteButton.setToolTipText(CWComponentFactory.createToolTip(
                "Loeschen",
                "Hier koennen Sie eine selektierte Aktivitaet loeschen!",
                "cw/coursemanagementmodul/images/activity_delete.png"));
        
        activityTable = CWComponentFactory.createTable("Keine Aktivitaeten vorhanden!");
        activityTable.setModel(model.createActivityTableModel(model.getActivitySelection()));
        activityTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getActivitySelection().getSelectionIndexHolder(),
                        activityTable)));

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(newButton)
                .addComponent(editButton)
                .addComponent(deleteButton)
                .addComponent(activityTable);
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
        
        this.getContentPanel().add(new JScrollPane(activityTable), BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
    //**************************************************************************
}
