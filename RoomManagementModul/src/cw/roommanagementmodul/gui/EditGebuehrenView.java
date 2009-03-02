/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.roommanagementmodul.pojo.Gebuehr;

/**
 *
 * @author Dominik
 */
public class EditGebuehrenView implements Disposable{

    private EditGebuehrenPresentationModel model;
    private JLabel lGebuehrenName;
    private JLabel lKategorie;
    private JTextField tfGebuehrenName;
    private JComboBox cbKategorie;
    private JButton bSave;
    private JButton bCancel;
    private JButton bSaveCancel;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel mainPanel;

    public EditGebuehrenView(EditGebuehrenPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        lGebuehrenName = CWComponentFactory.createLabel("Gebuehren Name: ");
        lKategorie = CWComponentFactory.createLabel("Kategorie: ");

        tfGebuehrenName = CWComponentFactory.createTextField(model.getBufferedModel(Gebuehr.PROPERTYNAME_NAME),false);

        bSave = CWComponentFactory.createButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schließen");

        cbKategorie = CWComponentFactory.createComboBox(model.getGebKatList());

        componentContainer= CWComponentFactory.createCWComponentContainer()
                .addComponent(bSave)
                .addComponent(bCancel)
                .addComponent(bSaveCancel)
                .addComponent(cbKategorie)
                .addComponent(lGebuehrenName)
                .addComponent(lKategorie)
                .addComponent(tfGebuehrenName);


    }
    
     public JComponent buildPanel() {
        initComponents();

        mainPanel = new JViewPanel(model.getHeaderInfo());
        JButtonPanel buttonPanel = mainPanel.getButtonPanel();

        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);

        JViewPanel panel = new JViewPanel();
        JPanel contentPanel = panel.getContentPanel();

        /**
         * Boxes
         */
        FormLayout layout = new FormLayout("right:pref, 4dlu, 50dlu:grow, 4dlu, right:pref, 4dlu, 50dlu:grow",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

        contentPanel.setLayout(layout);

        CellConstraints cc = new CellConstraints();
        contentPanel.add(lGebuehrenName, cc.xy(1, 3));
        contentPanel.add(lKategorie, cc.xy(1, 5));

        contentPanel.add(tfGebuehrenName, cc.xy(3, 3));
        contentPanel.add(cbKategorie, cc.xy(3, 5));



        mainPanel.getContentPanel().add(panel, BorderLayout.CENTER);
        mainPanel.addDisposableListener(this);
        return mainPanel;
    }

    public void dispose() {
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
}
