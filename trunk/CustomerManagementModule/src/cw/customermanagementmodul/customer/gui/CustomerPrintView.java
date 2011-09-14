package cw.customermanagementmodul.customer.gui;

import net.sf.jasperreports.swing.JRViewer;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author tom
 */
public class CustomerPrintView
	extends CWView<CustomerPrintPresentationModel>
{

    private CWButton bBack;
    private JRViewer viewer;

    public CustomerPrintView(CustomerPrintPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
   
        bBack = CWComponentFactory.createButton(getModel().getBackAction());
        bBack.setText("Zurueck");

        bBack.setToolTipText(CWComponentFactory.createToolTip(
                "Zurueck",
                " ",
                "cw/coursemanagementmodul/images/back.png"));

        viewer = new JRViewer(getModel().getJasperPrint());

        getComponentContainer()
        	.addComponent(bBack);
    }

    public void buildView() {
    	super.buildView();
    	
        //this.setHeaderInfo(model.getHeaderInfo());
        this.getButtonPanel().add(bBack);
        addToContentPanel(viewer, true);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
