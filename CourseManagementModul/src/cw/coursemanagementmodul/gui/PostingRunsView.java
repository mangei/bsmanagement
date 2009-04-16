/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.helper.JXTableSelectionConverter;
import cw.boardingschoolmanagement.gui.renderer.DateTimeTableCellRenderer;
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
public class PostingRunsView implements Disposable{
    private PostingRunsPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;

    //Definieren der Objekte in der oberen Leiste
    private JButton backButton;
    private JButton stornoButton;
    private JButton detailButton;
    //********************************************

    //Tabelle zur Darstellung der angelegten Kurse
    private JXTable runTable;
    //********************************************

    private JViewPanel panel;

    public PostingRunsView(PostingRunsPresentationModel model) {
        this.model = model;
    }

    private void initEventHandling() {
    }

    //******************************************************************
    //In dieser Methode werden alle oben definierten Objekte instanziert
    //******************************************************************
    public void initComponents(){
        backButton    = CWComponentFactory.createButton(model.getBackButtonAction());
        stornoButton    = CWComponentFactory.createButton(model.getStornoAction());
        detailButton    = CWComponentFactory.createButton(model.getDetailAction());

        backButton.setToolTipText(CWComponentFactory.createToolTip(
                "Zurück",
                "Hier kehren Sie wieder in den Kurs-Gebührenlauf zurück!",
                "cw/coursemanagementmodul/images/back.png"));
        stornoButton.setToolTipText(CWComponentFactory.createToolTip(
                "Stornieren",
                "Hier wird der selektierte Gebührenlauf storniert!",
                "cw/coursemanagementmodul/images/posting_delete.png"));
        detailButton.setToolTipText(CWComponentFactory.createToolTip(
                "Detailansicht",
                "Hier erhalten Sie eine detailierte Detailansicht zum selektierten Gebührenlauf!",
                "cw/coursemanagementmodul/images/lupe.png"));
        
        runTable = CWComponentFactory.createTable("Es wurden noch keine Kursläufe erzeugt!");
        runTable.setModel(model.createPostingRunTableModel(model.getPostingRunList()));
        runTable.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getPostingRunList().getSelectionIndexHolder(),
                        runTable)));

        runTable.getColumns(true).get(2).setCellRenderer(new DateTimeTableCellRenderer());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(backButton)
                .addComponent(runTable)
                .addComponent(stornoButton)
                .addComponent(detailButton);

        panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());
    }
    //**************************************************************************


    //**************************************************************************
    //Diese Methode gibt die Maske des StartPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        initEventHandling();

        panel.getButtonPanel().add(backButton);
        panel.getButtonPanel().add(stornoButton);
        panel.getButtonPanel().add(detailButton);

        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref","pref");
        panel.getTopPanel().setLayout(layout);

        panel.getContentPanel().add(new JScrollPane(runTable), BorderLayout.CENTER);
        panel.addDisposableListener(this);
        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
    //**************************************************************************
}
