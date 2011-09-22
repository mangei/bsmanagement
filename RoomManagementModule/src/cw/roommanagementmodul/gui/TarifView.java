package cw.roommanagementmodul.gui;

import javax.swing.JScrollPane;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import cw.boardingschoolmanagement.gui.renderer.DateTimeDataFieldRenderer;

/**
 *
 * @author Dominik
 */
public class TarifView
	extends CWView<TarifPresentationModel>
{

    private CWButton bNew;
    private CWButton bDelete;
    private CWButton bEdit;
    private CWButton bBack;
    private CWTable tTarif;

    public TarifView(TarifPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();

        bNew = CWComponentFactory.createButton(getModel().getNewAction());
        bNew.setText("Neu");
        bDelete = CWComponentFactory.createButton(getModel().getDeleteAction());
        bDelete.setText("Loeschen");
        bEdit = CWComponentFactory.createButton(getModel().getEditAction());
        bEdit.setText("Bearbeiten");
        bBack = CWComponentFactory.createButton(getModel().getBackAction());
        bBack.setText("Zurueck");


        String tarifTableStateName = "cw.roommanagementmodul.TarifView.tarifTableState";
        tTarif = CWComponentFactory.createTable(getModel().createZuordnungTableModel(getModel().getTarifSelection()), "kein Tarif vorhanden", tarifTableStateName);

        tTarif.setSelectionModel(new SingleListSelectionAdapter(new CWTableSelectionConverter(
        		getModel().getTarifSelection().getSelectionIndexHolder(),
                tTarif)));

        tTarif.getColumnModel().getColumn(0).setCellRenderer(new DateTimeDataFieldRenderer(true));
        tTarif.getColumnModel().getColumn(1).setCellRenderer(new DateTimeDataFieldRenderer(true));

        getComponentContainer()
        	.addComponent(bNew)
        	.addComponent(bDelete)
        	.addComponent(bEdit)
        	.addComponent(bBack)
        	.addComponent(tTarif);
        
        initEventHandling();
    }

    private void initEventHandling() {
        tTarif.addMouseListener(getModel().getDoubleClickHandler());
    }

    public void buildView() {
    	super.buildView();

        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bDelete);
        this.getButtonPanel().add(bBack);

        addToContentPanel(new JScrollPane(tTarif));
    }

    @Override
    public void dispose() {
        tTarif.removeMouseListener(getModel().getDoubleClickHandler());
        
        super.dispose();
    }
}
