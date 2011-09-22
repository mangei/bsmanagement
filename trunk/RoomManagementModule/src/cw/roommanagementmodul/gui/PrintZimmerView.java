
package cw.roommanagementmodul.gui;

import net.sf.jasperreports.swing.JRViewer;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Dominik
 */
public class PrintZimmerView
	extends CWView<PrintZimmerPresentationModel>
{

    private CWButton bBack;
    private JRViewer viewer;

    public PrintZimmerView(PrintZimmerPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        bBack = CWComponentFactory.createButton(getModel().getBackAction());
        bBack.setText("Zurueck");
        viewer = new JRViewer(getModel().getJasperPrint());

        getComponentContainer()
        	.addComponent(bBack);
    }

    public void buildView() {
    	super.buildView();

        this.setHeaderInfo(getModel().getHeaderInfo());
        this.getButtonPanel().add(bBack);
        addToContentPanel(viewer);

    }

    public void dispose() {
        super.dispose();
    }
}
