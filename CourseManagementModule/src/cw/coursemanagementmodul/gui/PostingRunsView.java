package cw.coursemanagementmodul.gui;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import cw.boardingschoolmanagement.gui.renderer.DateTimeTableCellRenderer;

/**
 *
 * @author André Salmhofer
 */
public class PostingRunsView extends CWView
{
    private PostingRunsPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;

    //Definieren der Objekte in der oberen Leiste
    private CWButton backButton;
    private CWButton stornoButton;
    private CWButton detailButton;
    //********************************************

    //Tabelle zur Darstellung der angelegten Kurse
    private CWTable runTable;
    //********************************************

    public PostingRunsView(PostingRunsPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){
        backButton    = CWComponentFactory.createButton(model.getBackButtonAction());
        stornoButton    = CWComponentFactory.createButton(model.getStornoAction());
        detailButton    = CWComponentFactory.createButton(model.getDetailAction());

        backButton.setToolTipText(CWComponentFactory.createToolTip(
                "Zurueck",
                "Hier kehren Sie wieder in den Kurs-Gebuehrenlauf zurueck!",
                "cw/coursemanagementmodul/images/back.png"));
        stornoButton.setToolTipText(CWComponentFactory.createToolTip(
                "Stornieren",
                "Hier wird der selektierte Gebuehrenlauf storniert!",
                "cw/coursemanagementmodul/images/posting_delete.png"));
        detailButton.setToolTipText(CWComponentFactory.createToolTip(
                "Detailansicht",
                "Hier erhalten Sie eine detailierte Detailansicht zum selektierten Gebuehrenlauf!",
                "cw/coursemanagementmodul/images/lupe.png"));
        
        runTable = CWComponentFactory.createTable("Es wurden noch keine Kurslaeufe erzeugt!");
        runTable.setModel(model.createPostingRunTableModel(model.getPostingRunList()));
        runTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getPostingRunList().getSelectionIndexHolder(),
                        runTable)));

        runTable.getColumns(true).get(2).setCellRenderer(new DateTimeTableCellRenderer());

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(backButton)
                .addComponent(runTable)
                .addComponent(stornoButton)
                .addComponent(detailButton);

    }
    //**************************************************************************


    private void initEventHandling() {
    }

    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurueck
    //**************************************************************************
    private void buildView(){

        this.setHeaderInfo(model.getHeaderInfo());

        this.getButtonPanel().add(backButton);
        this.getButtonPanel().add(stornoButton);
        this.getButtonPanel().add(detailButton);

        this.getContentPanel().add(new JScrollPane(runTable), BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
    //**************************************************************************
}
