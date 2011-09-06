
package cw.roommanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.component.CWButton;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Dominik
 */
public class PrintStornoView extends CWView {

    private PrintStornoPresentationModel model;
    private CWButton bBack;
    private JRViewer viewer;
    private CWComponentFactory.CWComponentContainer componentContainer;

    public PrintStornoView(PrintStornoPresentationModel model) {
        this.model = model;
        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        bBack = CWComponentFactory.createButton(model.getBackAction());
        bBack.setText("Zurueck");
        viewer = new JRViewer(model.getJasperPrint());

        componentContainer = CWComponentFactory.createComponentContainer().addComponent(bBack);
    }

    private void initEventHandling() {
    }

    private void buildView() {

        this.setHeaderInfo(model.getHeaderInfo());
        this.getButtonPanel().add(bBack);
        this.getContentPanel().add(viewer);
    }

    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
}
