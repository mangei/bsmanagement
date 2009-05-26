package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import java.awt.Dimension;
import javax.swing.JScrollPane;

/**
 *
 * @author André Salmhofer
 */
public class CourseParticipantForPostingView extends CWPanel
{
    private CourseParticipantForPostingPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;

    //*********************Komponenten definieren*******************************
    private CWTable courseTable;
    private CWTable activityTable;
    private CWTable subjectTable;

    private CWButton vorschlagButton;

    //**************************************************************************

    public CourseParticipantForPostingView(CourseParticipantForPostingPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    //**************************************************************************
    //Initialisieren der oben definierten Komponenten
    //Bei den meisten Komponenten kann man den PropertyName als Paramenter
    //mitgeben. Bei Datum-Komponenten ist dies jedoch nicht möglich. Hierzu
    //wird die Methode connect() von der Klasse PropertyConnector benötigt.
    //**************************************************************************
    private void initComponents(){
        courseTable = CWComponentFactory.createTable("Kein Kurs zugewiesen!");
        courseTable.setPreferredScrollableViewportSize(new Dimension(10, 70));
        courseTable.setModel(model.createCourseTableModel(model.getCourseSelection()));
        courseTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getCourseSelection().getSelectionIndexHolder(),
                        courseTable)));

        activityTable = CWComponentFactory.createTable("Keine Aktivität zugewiesen!");
        activityTable.setPreferredScrollableViewportSize(new Dimension(10, 30));
        activityTable.setModel(model.createActivityTableModel(model.getActivitySelection()));//TODO-mit ValueModel
        activityTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getActivitySelection().getSelectionIndexHolder(),
                        activityTable)));

        subjectTable = CWComponentFactory.createTable("Kein Gegenstand zugewiesen!");
        subjectTable.setPreferredScrollableViewportSize(new Dimension(10, 30));
        subjectTable.setModel(model.createSubjectTableModel(model.getSubjectSelection()));
        subjectTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getSubjectSelection().getSelectionIndexHolder(),
                        subjectTable)));

        vorschlagButton = CWComponentFactory.createButton(model.getVorschlagAction());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(courseTable)
                .addComponent(activityTable)
                .addComponent(subjectTable)
                .addComponent(vorschlagButton);

        
    }
    //**************************************************************************

    
    private void initEventHandling() {

    }

    //**************************************************************************
    //Diese Methode gibt die Maske des EditPanels in Form einse JPanels zurück
    //**************************************************************************
    private void buildView(){
        FormLayout layout = new FormLayout("fill:pref:grow, 15dlu, fill:pref:grow, 4dlu, pref",
                "pref, 4dlu, fill:pref:grow, 4dlu, pref, 4dlu, pref, 4dlu, pref");
        CellConstraints cc = new CellConstraints();

        PanelBuilder panelBuilder = new PanelBuilder(layout, this);
        
        panelBuilder.addSeparator("Ferienkurse", cc.xyw(1, 1, 3));
        panelBuilder.add(new JScrollPane(courseTable), cc.xyw(1, 3, 3));
        panelBuilder.addSeparator("Aktivitäten", cc.xy(1, 5));
        panelBuilder.add(new JScrollPane(activityTable), cc.xy(1, 7));
        panelBuilder.addSeparator("Gegenstände", cc.xy(3, 5));
        panelBuilder.add(new JScrollPane(subjectTable), cc.xy(3, 7));
        panelBuilder.add(vorschlagButton, cc.xy(1, 9));
        panelBuilder.setOpaque(false);
    }

    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
    //**************************************************************************
}
