package cw.coursemanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.sf.jasperreports.view.JRViewer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author André Salmhofer
 */
public class PrintPostingView {

    private PrintPostingRunPresentationModel model;
    private JButton bBack;
    private JRViewer viewer;

       public PrintPostingView(PrintPostingRunPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {
        bBack = CWComponentFactory.createButton(model.getBackAction());
        bBack.setText("Zurück");

        bBack.setToolTipText(CWComponentFactory.createToolTip(
                "Zurück",
                "Hier kehren Sie in zur Gebührenlauf-Detailansicht zurück!",
                "cw/coursemanagementmodul/images/back.png"));

        viewer = new JRViewer(model.getJasperPrint());
    }

    public JPanel buildPanel() {
        initComponents();
        JViewPanel panel = new JViewPanel(model.getHeaderInfo());
        panel.getButtonPanel().add(bBack);
        panel.getContentPanel().add(viewer);
        return panel;
    }

}
