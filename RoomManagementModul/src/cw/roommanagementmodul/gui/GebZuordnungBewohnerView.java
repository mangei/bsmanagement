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
public class GebZuordnungBewohnerView extends CWView{

    private GebZuordnungBewohnerPresentationModel model;
    private CWButton bNew;
    private CWButton bDelete;
    private CWButton bEdit;
    private CWButton bBack;
    private CWTable tZuordnung;
    private CWComponentFactory.CWComponentContainer componentContainer;

    public GebZuordnungBewohnerView(GebZuordnungBewohnerPresentationModel m) {
        this.model = m;
        initComponents();
        buildView();
        initEventHandling();

    }

    private void initComponents() {

        bNew = CWComponentFactory.createButton(model.getNewAction());
        bNew.setText("Neue Gebühr");
        bDelete = CWComponentFactory.createButton(model.getDeleteAction());
        bDelete.setText("Löschen");
        bEdit = CWComponentFactory.createButton(model.getEditAction());
        bEdit.setText("Bearbeiten");
        bBack = CWComponentFactory.createButton(model.getBackAction());
        bBack.setText("Zurück");


        String zuordnungenTableStateName = "cw.roommanagementmodul.GebZuordnunglBewohnerView.zuordnungTableState";
        tZuordnung = CWComponentFactory.createTable(model.createZuordnungTableModel(model.getGebuehrZuordnungSelection()), "keine Gebühr Zuordnungen vorhanden",zuordnungenTableStateName);


        tZuordnung.setSelectionModel(new SingleListSelectionAdapter(new CWTableSelectionConverter(
                model.getGebuehrZuordnungSelection().getSelectionIndexHolder(),
                tZuordnung)));

        tZuordnung.getColumnModel().getColumn(1).setCellRenderer(new DateTimeTableCellRenderer(true));
        tZuordnung.getColumnModel().getColumn(2).setCellRenderer(new DateTimeTableCellRenderer(true));

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(bNew)
                .addComponent(bDelete)
                .addComponent(bEdit)
                .addComponent(bBack)
                .addComponent(tZuordnung);
    }

    private void initEventHandling() {
        tZuordnung.addMouseListener(model.getDoubleClickHandler());
    }

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());

        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bDelete);
        this.getButtonPanel().add(bBack);

        this.getContentPanel().add(new JScrollPane(tZuordnung), BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        tZuordnung.removeMouseListener(model.getDoubleClickHandler());
        componentContainer.dispose();
        model.dispose();
    }
}
