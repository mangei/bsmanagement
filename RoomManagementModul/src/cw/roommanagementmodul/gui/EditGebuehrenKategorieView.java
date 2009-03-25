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
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.roommanagementmodul.pojo.GebuehrenKategorie;

/**
 *
 * @author Dominik
 */
public class EditGebuehrenKategorieView implements Disposable{

    private EditGebuehrenKategoriePresentationModel model;
    private JButton bSave;
    private JButton bCancel;
    private JButton bSaveCancel;
    private JLabel lKatName;
    private JTextField tfKatName;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel mainPanel;

    public EditGebuehrenKategorieView(EditGebuehrenKategoriePresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        lKatName = new JLabel("Bezeichnung: ");

        tfKatName = BasicComponentFactory.createTextField(model.getBufferedModel(GebuehrenKategorie.PROPERTYNAME_NAME), false);

        bSave = new JButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = new JButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = new JButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schließen");

        componentContainer = CWComponentFactory.createCWComponentContainer().addComponent(lKatName).addComponent(tfKatName).addComponent(bSave).addComponent(bCancel).addComponent(bSaveCancel);


    }

    public JComponent buildPanel() {
        initComponents();

        JViewPanel mainPanel = new JViewPanel(model.getHeaderInfo());
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
        contentPanel.add(lKatName, cc.xy(1, 3));
        contentPanel.add(tfKatName, cc.xy(3, 3));


        mainPanel.getContentPanel().add(panel, BorderLayout.CENTER);

        return mainPanel;
    }

    public void dispose() {
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
}
