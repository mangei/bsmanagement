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
import cw.roommanagementmodul.component.DateTimeTableCellRenderer;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Dominik
 */
public class TarifView implements Disposable{

    private TarifPresentationModel model;
    private JButton bNew;
    private JButton bDelete;
    private JButton bEdit;
    private JButton bBack;
    private JXTable tTarif;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel mainPanel;

    public TarifView(TarifPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {


        bNew = CWComponentFactory.createButton(model.getNewAction());
        bNew.setText("Neu");
        bDelete = CWComponentFactory.createButton(model.getDeleteAction());
        bDelete.setText("Löschen");
        bEdit = CWComponentFactory.createButton(model.getEditAction());
        bEdit.setText("Bearbeiten");
        bBack = CWComponentFactory.createButton(model.getBackAction());
        bBack.setText("Zurück");


        String tarifTableStateName = "cw.roommanagementmodul.TarifView.tarifTableState";
        tTarif = CWComponentFactory.createTable(model.createZuordnungTableModel(model.getTarifSelection()), "kein Tarif vorhanden",tarifTableStateName);

        tTarif.setSelectionModel(new SingleListSelectionAdapter(new JXTableSelectionConverter(
                model.getTarifSelection().getSelectionIndexHolder(),
                tTarif)));

        tTarif.getColumnModel().getColumn(0).setCellRenderer(new DateTimeTableCellRenderer(true));
        tTarif.getColumnModel().getColumn(1).setCellRenderer(new DateTimeTableCellRenderer(true));

        componentContainer=CWComponentFactory.createCWComponentContainer()
                .addComponent(bNew)
                .addComponent(bDelete)
                .addComponent(bEdit)
                .addComponent(bBack)
                .addComponent(tTarif);
    }

    private void initEventHandling() {
        tTarif.addMouseListener(model.getDoubleClickHandler());
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        mainPanel = new JViewPanel(model.getHeaderInfo());

        mainPanel.getButtonPanel().add(bNew);
        mainPanel.getButtonPanel().add(bEdit);
        mainPanel.getButtonPanel().add(bDelete);
        mainPanel.getButtonPanel().add(bBack);


        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        mainPanel.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();

        mainPanel.getContentPanel().add(new JScrollPane(tTarif), BorderLayout.CENTER);
        mainPanel.addDisposableListener(this);
        return mainPanel;
    }

    public void dispose() {
        tTarif.removeMouseListener(model.getDoubleClickHandler());
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
}
