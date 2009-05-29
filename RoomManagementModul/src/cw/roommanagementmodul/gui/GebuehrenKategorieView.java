/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author Dominik
 */
public class GebuehrenKategorieView implements Disposable{

    private GebuehrenKategoriePresentationModel model;
    private JButton bNew;
    private JButton bEdit;
    private JButton bDelete;
    private JButton bBack;
    private JList lGebuehrenKat;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel mainPanel;
    
    public GebuehrenKategorieView(GebuehrenKategoriePresentationModel m) {
        this.model = m;
    }

    private void initComponents() {

        
        bNew = CWComponentFactory.createButton(model.getNewAction());
        bNew.setText("Neu");
        bEdit = CWComponentFactory.createButton(model.getEditAction());
        bEdit.setText("Bearbeiten");
        bDelete = CWComponentFactory.createButton(model.getDeleteAction());
        bDelete.setText("Löschen");
        bBack= CWComponentFactory.createButton(model.getBackAction());
        bBack.setText("Zurück");
        
        lGebuehrenKat= CWComponentFactory.createList(model.getGebuehrenKatSelection());

        componentContainer=CWComponentFactory.createCWComponentContainer()
                .addComponent(bNew)
                .addComponent(bEdit)
                .addComponent(bDelete)
                .addComponent(bBack)
                .addComponent(lGebuehrenKat);

    }
     private void initEventHandling() {
        lGebuehrenKat.addMouseListener(model.getDoubleClickHandler());
    }
    
    
    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        mainPanel = new JViewPanel(model.getHeaderInfo());
        mainPanel.getButtonPanel().add(bNew);
        mainPanel.getButtonPanel().add(bEdit);
        mainPanel.getButtonPanel().add(bDelete);
        mainPanel.getButtonPanel().add(bBack);

        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        mainPanel.getTopPanel().setLayout(layout);
        CellConstraints cc = new CellConstraints();


        mainPanel.getContentPanel().add(lGebuehrenKat, BorderLayout.CENTER);
        mainPanel.addDisposableListener(this);
        return mainPanel;
    }

    public void dispose() {
        lGebuehrenKat.removeMouseListener(model.getDoubleClickHandler());
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }

}
