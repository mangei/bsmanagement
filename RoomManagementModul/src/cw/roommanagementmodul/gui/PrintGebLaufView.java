package cw.roommanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import net.sf.jasperreports.swing.JRViewer;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Dominik
 */
public class PrintGebLaufView extends CWView{

    private PrintGebLaufPresentationModel model;
    private CWButton bBack;
    private JRViewer viewer;
    private CWComponentFactory.CWComponentContainer componentContainer;

    public PrintGebLaufView(PrintGebLaufPresentationModel m) {
        this.model = m;
        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        bBack = CWComponentFactory.createButton(model.getBackAction());
        bBack.setText("Zurück");
        viewer = new JRViewer(model.getJasperPrint());

        componentContainer = CWComponentFactory.createCWComponentContainer().addComponent(bBack);
    }

    private void initEventHandling() {
    }

    private void buildView() {
        initComponents();

        this.setHeaderInfo(model.getHeaderInfo());
        this.getButtonPanel().add(bBack);
        this.getContentPanel().add(viewer);

    }

    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
}
