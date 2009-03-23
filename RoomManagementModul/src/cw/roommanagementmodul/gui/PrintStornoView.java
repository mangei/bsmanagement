/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Dominik
 */
public class PrintStornoView implements Disposable{

    private PrintStornoPresentationModel model;
    private JButton bBack;
    private JRViewer viewer;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel mainPanel;


    public PrintStornoView(PrintStornoPresentationModel model){
        this.model=model;
    }

     private void initComponents() {
        bBack = CWComponentFactory.createButton(model.getBackAction());
        bBack.setText("Zurück");
        viewer = new JRViewer(model.getJasperPrint());

        componentContainer = CWComponentFactory.createCWComponentContainer().addComponent(bBack);
    }

    public JPanel buildPanel() {
        initComponents();

        mainPanel = new JViewPanel(model.getHeaderInfo());
        mainPanel.getButtonPanel().add(bBack);

        mainPanel.getContentPanel().add(viewer);

        mainPanel.addDisposableListener(this);
        return mainPanel;
    }

    public void dispose() {
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
}
