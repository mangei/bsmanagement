
package cw.roommanagementmodul.gui;

import net.sf.jasperreports.swing.JRViewer;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Dominik
 */
public class PrintZimmerView extends CWView {

    private PrintZimmerPresentationModel model;
    private CWButton bBack;
    private JRViewer viewer;
    private CWComponentFactory.CWComponentContainer componentContainer;

    public PrintZimmerView(PrintZimmerPresentationModel m) {
        this.model = m;
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
