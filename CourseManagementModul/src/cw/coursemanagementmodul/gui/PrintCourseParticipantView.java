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
public class PrintCourseParticipantView {

    private PrintCourseParticipantPresentationModel model;
    private JButton bBack;
    private JRViewer viewer;

       public PrintCourseParticipantView(PrintCourseParticipantPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {
        bBack = CWComponentFactory.createButton(model.getBackAction());
        bBack.setText("Zurück");

        bBack.setToolTipText(CWComponentFactory.createToolTip(
                "Zurück",
                "Hier kehren Sie zur Kursdetailansicht zurück!",
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
