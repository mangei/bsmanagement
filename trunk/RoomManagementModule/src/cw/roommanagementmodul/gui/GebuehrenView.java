package cw.roommanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWTable;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

/**
 *
 * @author Dominik
 */
public class GebuehrenView extends CWView {

    private GebuehrenPresentationModel model;
    private CWButton bNew;
    private CWButton bEdit;
    private CWButton bDelete;
    private CWButton bKategorie;
    private CWButton bTarif;
    private CWTable tGebuehr;
    private CWComponentFactory.CWComponentContainer componentContainer;

    public GebuehrenView(GebuehrenPresentationModel m) {
        this.model = m;
        initComponents();
        buildView();
        initEventHandling();
        
    }

    private void initComponents() {

        bNew = CWComponentFactory.createButton(model.getNewAction());
        bNew.setText("Neu");
        bEdit = CWComponentFactory.createButton(model.getEditAction());
        bEdit.setText("Bearbeiten");
        bDelete = CWComponentFactory.createButton(model.getDeleteAction());
        bDelete.setText("Löschen");
        bKategorie = CWComponentFactory.createButton(model.getKategorieAction());
        bKategorie.setText("Kategorien");
        bTarif = CWComponentFactory.createButton(model.getTarifAction());
        bTarif.setText("Tarif");


        String gebuehrenTableStateName = "cw.roommanagementmodul.GebuehrenView.gebuehrenTableState";
        tGebuehr = CWComponentFactory.createTable(model.createGebuehrenTableModel(model.getGebuehrenSelection()), "keine Gebuehren vorhanden", gebuehrenTableStateName);

        tGebuehr.setSelectionModel(new SingleListSelectionAdapter(new CWTableSelectionConverter(
                model.getGebuehrenSelection().getSelectionIndexHolder(),
                tGebuehr)));

        componentContainer = CWComponentFactory.createComponentContainer().addComponent(bNew).addComponent(bEdit).addComponent(bDelete).addComponent(bKategorie).addComponent(bTarif).addComponent(tGebuehr);
    }

    private void initEventHandling() {
        tGebuehr.addMouseListener(model.getDoubleClickHandler());
    }

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());

        this.getButtonPanel().add(bTarif);
        this.getButtonPanel().add(bKategorie);
        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bDelete);

        this.getContentPanel().add(new JScrollPane(tGebuehr), BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
}
