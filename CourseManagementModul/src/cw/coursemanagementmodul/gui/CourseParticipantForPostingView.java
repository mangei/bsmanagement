/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author André Salmhofer
 */
public class CourseParticipantForPostingView implements Disposable{
    private CourseParticipantForPostingPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;

    //*********************Komponenten definieren*******************************
    private JXTable courseTable;
    private JXTable activityTable;
    private JXTable subjectTable;

    private JButton vorschlagButton;


    private JViewPanel view;

    //**************************************************************************

    public CourseParticipantForPostingView(CourseParticipantForPostingPresentationModel model) {
        this.model = model;
    }

    //**************************************************************************
    //Initialisieren der oben definierten Komponenten
    //Bei den meisten Komponenten kann man den PropertyName als Paramenter
    //mitgeben. Bei Datum-Komponenten ist dies jedoch nicht möglich. Hierzu
    //wird die Methode connect() von der Klasse PropertyConnector benötigt.
    //**************************************************************************
    public void initComponents(){
        courseTable = CWComponentFactory.createTable("Kein Kurs zugewiesen!");
        courseTable.setPreferredScrollableViewportSize(new Dimension(10, 70));
        courseTable.setModel(model.createCourseTableModel(model.getCourseSelection()));
        courseTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getCourseSelection().getSelectionIndexHolder(),
                        courseTable)));

        activityTable = CWComponentFactory.createTable("Keine Aktivität zugewiesen!");
        activityTable.setPreferredScrollableViewportSize(new Dimension(10, 30));
        activityTable.setModel(model.createActivityTableModel(model.getActivitySelection()));//TODO-mit ValueModel
        activityTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getActivitySelection().getSelectionIndexHolder(),
                        activityTable)));

        subjectTable = CWComponentFactory.createTable("Kein Gegenstand zugewiesen!");
        subjectTable.setPreferredScrollableViewportSize(new Dimension(10, 30));
        subjectTable.setModel(model.createSubjectTableModel(model.getSubjectSelection()));
        subjectTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getSubjectSelection().getSelectionIndexHolder(),
                        subjectTable)));

        vorschlagButton = CWComponentFactory.createButton(model.getVorschlagAction());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(courseTable)
                .addComponent(activityTable)
                .addComponent(subjectTable)
                .addComponent(vorschlagButton);

        view = CWComponentFactory.createViewPanel();
    }
    //**************************************************************************

    //**************************************************************************
    //Diese Methode gibt die Maske des EditPanels in Form einse JPanels zurück
    //**************************************************************************
    public JComponent buildPanel(){
        initComponents();

        FormLayout layout = new FormLayout("fill:pref:grow, 15dlu, fill:pref:grow, 4dlu, pref",
                "pref, 4dlu, fill:pref:grow, 4dlu, pref, 4dlu, pref, 4dlu, pref");
        CellConstraints cc = new CellConstraints();

        PanelBuilder panelBuilder = new PanelBuilder(layout, view);
        
        panelBuilder.addSeparator("Ferienkurse", cc.xyw(1, 1, 3));
        panelBuilder.add(new JScrollPane(courseTable), cc.xyw(1, 3, 3));
        panelBuilder.addSeparator("Aktivitäten", cc.xy(1, 5));
        panelBuilder.add(new JScrollPane(activityTable), cc.xy(1, 7));
        panelBuilder.addSeparator("Gegenstände", cc.xy(3, 5));
        panelBuilder.add(new JScrollPane(subjectTable), cc.xy(3, 7));
        panelBuilder.add(vorschlagButton, cc.xy(1, 9));
        panelBuilder.setOpaque(false);
        view.addDisposableListener(this);
        return view;
    }

    public void dispose() {
        view.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
    //**************************************************************************
}
