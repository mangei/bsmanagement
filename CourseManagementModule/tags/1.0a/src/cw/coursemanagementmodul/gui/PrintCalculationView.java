package cw.coursemanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import net.sf.jasperreports.view.JRViewer;

/**
 *
 * @author André Salmhofer
 */
public class PrintCalculationView extends CWView
{

    private CWComponentFactory.CWComponentContainer componentContainer;
    private PrintCalculationPresentationModel model;
    private CWButton bBack;
    private JRViewer viewer;

    public PrintCalculationView(PrintCalculationPresentationModel m) {
        this.model = m;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        componentContainer = CWComponentFactory.createComponentContainer();

        bBack = CWComponentFactory.createButton(model.getBackAction());
        bBack.setText("Zurück");

        bBack.setToolTipText(CWComponentFactory.createToolTip(
                "Zurück",
                "Hier kehren Sie in die Kurshistorie zurück!",
                "cw/coursemanagementmodul/images/back.png"));

        viewer = new JRViewer(model.getJasperPrint());

        componentContainer.addComponent(bBack);
    }

    private void initEventHandling() {

    }

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
        this.getButtonPanel().add(bBack);
        this.getContentPanel().add(viewer);
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
}
