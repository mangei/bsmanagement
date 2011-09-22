package cw.roommanagementmodul.gui;

import java.awt.BorderLayout;

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
public class GebZuordnungBewohnerView
	extends CWView<GebZuordnungBewohnerPresentationModel>
{

    private CWButton bNew;
    private CWButton bDelete;
    private CWButton bEdit;
    private CWButton bBack;
    private CWTable tZuordnung;

    public GebZuordnungBewohnerView(GebZuordnungBewohnerPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();

        bNew = CWComponentFactory.createButton(getModel().getNewAction());
        bNew.setText("Neue Gebuehr");
        bDelete = CWComponentFactory.createButton(getModel().getDeleteAction());
        bDelete.setText("Loeschen");
        bEdit = CWComponentFactory.createButton(getModel().getEditAction());
        bEdit.setText("Bearbeiten");
        bBack = CWComponentFactory.createButton(getModel().getBackAction());
        bBack.setText("Zurueck");


        String zuordnungenTableStateName = "cw.roommanagementmodul.GebZuordnunglBewohnerView.zuordnungTableState";
        tZuordnung = CWComponentFactory.createTable(getModel().createZuordnungTableModel(getModel().getGebuehrZuordnungSelection()), "keine Gebuehr Zuordnungen vorhanden",zuordnungenTableStateName);


        tZuordnung.setSelectionModel(new SingleListSelectionAdapter(new CWTableSelectionConverter(
        		getModel().getGebuehrZuordnungSelection().getSelectionIndexHolder(),
                tZuordnung)));

        tZuordnung.getColumnModel().getColumn(1).setCellRenderer(new DateTimeDataFieldRenderer(true));
        tZuordnung.getColumnModel().getColumn(2).setCellRenderer(new DateTimeDataFieldRenderer(true));

        getComponentContainer()
                .addComponent(bNew)
                .addComponent(bDelete)
                .addComponent(bEdit)
                .addComponent(bBack)
                .addComponent(tZuordnung);
        
        initEventHandling();
    }

    private void initEventHandling() {
        tZuordnung.addMouseListener(getModel().getDoubleClickHandler());
    }

    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());

        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bDelete);
        this.getButtonPanel().add(bBack);

        addToContentPanel(new JScrollPane(tZuordnung));
    }

    @Override
    public void dispose() {
        tZuordnung.removeMouseListener(getModel().getDoubleClickHandler());
        
        super.dispose();
    }
}
