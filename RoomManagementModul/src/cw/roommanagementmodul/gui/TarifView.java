package cw.roommanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import cw.roommanagementmodul.component.DateTimeTableCellRenderer;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWTable;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

/**
 *
 * @author Dominik
 */
public class TarifView extends CWView {

    private TarifPresentationModel model;
    private CWButton bNew;
    private CWButton bDelete;
    private CWButton bEdit;
    private CWButton bBack;
    private CWTable tTarif;
    private CWComponentFactory.CWComponentContainer componentContainer;

    public TarifView(TarifPresentationModel m) {
        this.model = m;
        initComponents();
        buildView();
        initEventHandling();
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
        tTarif = CWComponentFactory.createTable(model.createZuordnungTableModel(model.getTarifSelection()), "kein Tarif vorhanden", tarifTableStateName);

        tTarif.setSelectionModel(new SingleListSelectionAdapter(new CWTableSelectionConverter(
                model.getTarifSelection().getSelectionIndexHolder(),
                tTarif)));

        tTarif.getColumnModel().getColumn(0).setCellRenderer(new DateTimeTableCellRenderer(true));
        tTarif.getColumnModel().getColumn(1).setCellRenderer(new DateTimeTableCellRenderer(true));

        componentContainer = CWComponentFactory.createComponentContainer().addComponent(bNew).addComponent(bDelete).addComponent(bEdit).addComponent(bBack).addComponent(tTarif);
    }

    private void initEventHandling() {
        tTarif.addMouseListener(model.getDoubleClickHandler());
    }

    private void buildView() {

        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bDelete);
        this.getButtonPanel().add(bBack);

        this.getContentPanel().add(new JScrollPane(tTarif), BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        tTarif.removeMouseListener(model.getDoubleClickHandler());
        componentContainer.dispose();
        model.dispose();
    }
}
