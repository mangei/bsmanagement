/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.helper.JXTableSelectionConverter;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Dominik
 */
public class ZimmerView implements Disposable {

    private ZimmerPresentationModel model;
    private JButton bNew;
    private JButton bEdit;
    private JButton bDelete;
    private JButton bBack;
    private JButton bPrint;
    private JXTable tZimmer;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel mainPanel;

    public ZimmerView(ZimmerPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {


        bNew = CWComponentFactory.createButton(model.getNewAction());
        bNew.setText("Neu");
        bEdit = CWComponentFactory.createButton(model.getEditAction());
        bEdit.setText("Bearbeiten");
        bDelete = CWComponentFactory.createButton(model.getDeleteAction());
        bDelete.setText("Löschen");

        bBack = CWComponentFactory.createButton(model.getBackAction());
        bBack.setText("Zurück");
        bPrint = CWComponentFactory.createButton(model.getPrintAction());
        bPrint.setText("Drucken");


        String zimmerTableStateName = "cw.roommanagementmodul.ZimmerView.zimmerTableState";
        tZimmer = CWComponentFactory.createTable(model.createZimmerTableModel(model.getZimmerSelection()), "keine Zimmer vorhanden", zimmerTableStateName);


        tZimmer.setSelectionModel(new SingleListSelectionAdapter(new JXTableSelectionConverter(
                model.getZimmerSelection().getSelectionIndexHolder(),
                tZimmer)));

        componentContainer= CWComponentFactory.createCWComponentContainer();
        componentContainer.addComponent(bNew)
                .addComponent(bEdit)
                .addComponent(bDelete)
                .addComponent(bBack)
                .addComponent(bPrint)
                .addComponent(tZimmer);
    }

    private void initEventHandling() {
        tZimmer.addMouseListener(model.getDoubleClickHandler());
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        mainPanel = new JViewPanel(model.getHeaderInfo());
        mainPanel.getButtonPanel().add(bNew);
        mainPanel.getButtonPanel().add(bEdit);
        mainPanel.getButtonPanel().add(bDelete);
        mainPanel.getButtonPanel().add(bPrint);
        mainPanel.getButtonPanel().add(bBack);

        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        mainPanel.getTopPanel().setLayout(layout);
        

        mainPanel.getContentPanel().add(new JScrollPane(tZimmer), BorderLayout.CENTER);

        mainPanel.addDisposableListener(this);
        return mainPanel;
    }

    public void dispose() {
        tZimmer.removeMouseListener(model.getDoubleClickHandler());
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
}
