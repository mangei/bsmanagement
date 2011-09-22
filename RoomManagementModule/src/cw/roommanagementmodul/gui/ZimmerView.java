
package cw.roommanagementmodul.gui;

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
public class ZimmerView
	extends CWView<ZimmerPresentationModel>
{

    private CWButton bNew;
    private CWButton bEdit;
    private CWButton bDelete;
    private CWButton bBack;
    private CWButton bPrint;
    private CWTable tZimmer;

    public ZimmerView(ZimmerPresentationModel model) {
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

        bBack = CWComponentFactory.createButton(getModel().getBackAction());
        bBack.setText("Zurueck");
        bPrint = CWComponentFactory.createButton(getModel().getPrintAction());
        bPrint.setText("Drucken");


        String zimmerTableStateName = "cw.roommanagementmodul.ZimmerView.zimmerTableState";
        tZimmer = CWComponentFactory.createTable(getModel().createZimmerTableModel(getModel().getZimmerSelection()), "keine Zimmer vorhanden", zimmerTableStateName);


        tZimmer.setSelectionModel(new SingleListSelectionAdapter(new CWTableSelectionConverter(
        		getModel().getZimmerSelection().getSelectionIndexHolder(),
                tZimmer)));

        getComponentContainer()
        		.addComponent(bNew)
                .addComponent(bEdit)
                .addComponent(bDelete)
                .addComponent(bBack)
                .addComponent(bPrint)
                .addComponent(tZimmer);
        
        initEventHandling();
    }

    private void initEventHandling() {
        tZimmer.addMouseListener(getModel().getDoubleClickHandler());
    }

    public void buildView() {
    	super.buildView();

        this.setHeaderInfo(getModel().getHeaderInfo());
        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bDelete);
        this.getButtonPanel().add(bPrint);
        this.getButtonPanel().add(bBack);
        
        addToContentPanel(new JScrollPane(tZimmer));
    }

    @Override
    public void dispose() {
        tZimmer.removeMouseListener(getModel().getDoubleClickHandler());
        
        super.dispose();
    }
}
