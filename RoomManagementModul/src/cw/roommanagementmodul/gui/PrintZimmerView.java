/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.JViewPanel;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.sf.jasperreports.swing.JRViewer;



/**
 *
 * @author Dominik
 */
public class PrintZimmerView {

    private PrintZimmerPresentationModel model;
    private JButton bBack;
    private JRViewer viewer;

       public PrintZimmerView(PrintZimmerPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {
        bBack = new JButton(model.getBackAction());
        bBack.setText("Zurück");
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
