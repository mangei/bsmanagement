/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author tom
 */
public class CustomerPrintView extends CWView{

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CustomerPrintPresentationModel model;
    private CWButton bBack;
    private JRViewer viewer;

    public CustomerPrintView(CustomerPrintPresentationModel m) {
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
                " ",
                "cw/coursemanagementmodul/images/back.png"));

        viewer = new JRViewer(model.getJasperPrint());

        componentContainer.addComponent(bBack);
    }

    private void initEventHandling() {

    }

    private void buildView() {
        //this.setHeaderInfo(model.getHeaderInfo());
        this.getButtonPanel().add(bBack);
        this.getContentPanel().add(viewer);
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }

}
