package cw.roommanagementmodul.gui;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;

/**
 *
 * @author Dominik
 */
public class GebuehrenView
	extends CWView<GebuehrenPresentationModel>
{

    private CWButton bNew;
    private CWButton bEdit;
    private CWButton bDelete;
    private CWButton bKategorie;
    private CWButton bTarif;
    private CWTable tGebuehr;

    public GebuehrenView(GebuehrenPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();

        bNew = CWComponentFactory.createButton(getModel().getNewAction());
        bNew.setText("Neu");
        bEdit = CWComponentFactory.createButton(getModel().getEditAction());
        bEdit.setText("Bearbeiten");
        bDelete = CWComponentFactory.createButton(getModel().getDeleteAction());
        bDelete.setText("Loeschen");
        bKategorie = CWComponentFactory.createButton(getModel().getKategorieAction());
        bKategorie.setText("Kategorien");
        bTarif = CWComponentFactory.createButton(getModel().getTarifAction());
        bTarif.setText("Tarif");


        String gebuehrenTableStateName = "cw.roommanagementmodul.GebuehrenView.gebuehrenTableState";
        tGebuehr = CWComponentFactory.createTable(getModel().createGebuehrenTableModel(getModel().getGebuehrenSelection()), "keine Gebuehren vorhanden", gebuehrenTableStateName);

        tGebuehr.setSelectionModel(new SingleListSelectionAdapter(new CWTableSelectionConverter(
        		getModel().getGebuehrenSelection().getSelectionIndexHolder(),
                tGebuehr)));

        getComponentContainer()
        	.addComponent(bNew)
        	.addComponent(bEdit)
        	.addComponent(bDelete)
        	.addComponent(bKategorie)
        	.addComponent(bTarif)
        	.addComponent(tGebuehr);
        
        initEventHandling();
    }

    private void initEventHandling() {
        tGebuehr.addMouseListener(getModel().getDoubleClickHandler());
    }

    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());

        this.getButtonPanel().add(bTarif);
        this.getButtonPanel().add(bKategorie);
        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bDelete);

        addToContentPanel(new JScrollPane(tGebuehr));
    }

    @Override
    public void dispose() {
        
    	super.dispose();
    }
}
