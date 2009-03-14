/*
 * Die Course View representiert das Startfenster,
 * dass angezeigt wird, wenn man auf 'Kurs' in der
 * linken Auswahlliste klickt.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.helper.JXTableSelectionConverter;
import cw.boardingschoolmanagement.interfaces.Disposable;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author André Salmhofer
 */
public class ActivityChooserView implements Disposable{
    private ActivityChooserPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;

    //Definieren der Objekte in der oberen Leiste
    private JButton addButton;
    private JButton cancelButton;
    //********************************************
    
    //Tabelle zur Darstellung der angelegten Kurse
    private JXTable activityTable;
    //********************************************

    private JViewPanel mainPanel;

    public ActivityChooserView(ActivityChooserPresentationModel model) {
        this.model = model;
        initEventHandling();
    }
    
    private void initEventHandling() {
    }

    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){
        mainPanel = CWComponentFactory.createViewPanel(model.getHeaderInfo());
        addButton = CWComponentFactory.createButton(model.getAddButtonAction());
        cancelButton = CWComponentFactory.createButton(model.getCancelButtonAction());

        activityTable = CWComponentFactory.createTable("Es wurden noch keine Aktivitäten angelegt!");
        activityTable.setModel(model.createActivityTableModel(model.getActivitySelection()));
        activityTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getActivitySelection().getSelectionIndexHolder(),
                        activityTable)));

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(addButton)
                .addComponent(cancelButton)
                .addComponent(activityTable);
    }
    //**************************************************************************

    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        initEventHandling();

        mainPanel.getButtonPanel().add(addButton);
        mainPanel.getButtonPanel().add(cancelButton);

        FormLayout mainLayout = new FormLayout("pref:grow", "pref, 4dlu, fill:pref:grow");

        mainPanel.getContentPanel().setLayout(mainLayout);

        CellConstraints cc = new CellConstraints();

        PanelBuilder panelBuilder = new PanelBuilder(mainLayout, mainPanel.getContentPanel());
        panelBuilder.addSeparator("Aktivitätstabelle", cc.xy(1, 1));
        panelBuilder.add(new JScrollPane(activityTable), cc.xy(1, 3));
        mainPanel.addDisposableListener(this);
        return mainPanel;
    }

    public void dispose() {
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
    //********************************************
}
