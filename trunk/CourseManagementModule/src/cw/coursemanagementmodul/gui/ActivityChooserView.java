/*
 * Die Course View representiert das Startfenster,
 * dass angezeigt wird, wenn man auf 'Kurs' in der
 * linken Auswahlliste klickt.
 */

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
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class ActivityChooserView extends CWView

{
    private ActivityChooserPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;

    //Definieren der Objekte in der oberen Leiste
    private CWButton addButton;
    private CWButton cancelButton;
    //********************************************
    
    //Tabelle zur Darstellung der angelegten Kurse
    private CWTable activityTable;
    //********************************************

    public ActivityChooserView(ActivityChooserPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }
    
    private void initEventHandling() {
    }

    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    private void initComponents(){
        addButton = CWComponentFactory.createButton(model.getAddButtonAction());
        cancelButton = CWComponentFactory.createButton(model.getCancelButtonAction());

        addButton.setToolTipText(CWComponentFactory.createToolTip(
                "Hinzufuegen",
                "Hier koennen Sie die selektierte Aktivitaet zum Kurs hinzufuegen!",
                "cw/coursemanagementmodul/images/activity_add.png"));

        cancelButton.setToolTipText(CWComponentFactory.createToolTip(
                "Schließen",
                "Hier kehren Sie zur Ferienkursuebersicht zurueck!",
                "cw/coursemanagementmodul/images/cancel.png"));

        activityTable = CWComponentFactory.createTable("Es wurden noch keine Aktivitaeten angelegt!");
        activityTable.setModel(model.createActivityTableModel(model.getActivitySelection()));
        activityTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getActivitySelection().getSelectionIndexHolder(),
                        activityTable)));

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(addButton)
                .addComponent(cancelButton)
                .addComponent(activityTable);
    }
    //**************************************************************************

    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurueck
    //**************************************************************************
    private void buildView(){

        this.setHeaderInfo(model.getHeaderInfo());

        this.getButtonPanel().add(addButton);
        this.getButtonPanel().add(cancelButton);

        FormLayout mainLayout = new FormLayout("pref:grow", "pref, 4dlu, fill:pref:grow");

        CellConstraints cc = new CellConstraints();

        PanelBuilder panelBuilder = new PanelBuilder(mainLayout, this.getContentPanel());
        panelBuilder.addSeparator("Aktivitaetstabelle", cc.xy(1, 1));
        panelBuilder.add(new JScrollPane(activityTable), cc.xy(1, 3));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
    //********************************************
}
