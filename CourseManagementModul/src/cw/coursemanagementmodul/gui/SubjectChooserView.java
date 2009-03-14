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
public class SubjectChooserView implements Disposable{
    private SubjectChooserPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;

    //Definieren der Objekte in der oberen Leiste
    private JButton addButton;
    private JButton cancelButton;
    //********************************************

    //Tabelle zur Darstellung der angelegten Kurse
    private JXTable subjectTable;
    //********************************************

    private JViewPanel mainPanel;

    public SubjectChooserView(SubjectChooserPresentationModel model) {
        this.model = model;
        initEventHandling();
    }

    private void initEventHandling() {
    }

    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){
        addButton = CWComponentFactory.createButton(model.getAddButtonAction());
        cancelButton = CWComponentFactory.createButton(model.getCancelButtonAction());

        subjectTable = CWComponentFactory.createTable("Es wurden noch keine Gegenstände angelegt!");
        subjectTable.setModel(model.createSubjectTableModel(model.getSubjectSelection()));
        subjectTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getSubjectSelection().getSelectionIndexHolder(),
                        subjectTable)));

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(addButton)
                .addComponent(cancelButton)
                .addComponent(subjectTable);

        mainPanel = CWComponentFactory.createViewPanel(model.getHeaderInfo());
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

        panelBuilder.addSeparator("Kursgegenstandstabelle", cc.xy(1, 1));
        panelBuilder.add(new JScrollPane(subjectTable), cc.xy(1, 3));
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
