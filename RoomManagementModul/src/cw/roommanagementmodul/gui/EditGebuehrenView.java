/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
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
public class EditGebuehrenView {

    private EditGebuehrenPresentationModel model;
    public JLabel lGebuehrenName;
    public JLabel lKategorie;
    public JTextField tfGebuehrenName;
    public JComboBox cbKategorie;
    public JButton bSave;
    public JButton bReset;
    public JButton bCancel;
    public JButton bSaveCancel;

    public EditGebuehrenView(EditGebuehrenPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        lGebuehrenName = new JLabel("Gebuehren Name: ");
        lKategorie = new JLabel("Kategorie: ");

        tfGebuehrenName = BasicComponentFactory.createTextField(model.getBufferedModel(Gebuehr.PROPERTYNAME_NAME),false);

        bSave = new JButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = new JButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = new JButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern&Schließen");

        bReset = new JButton(model.getResetButtonAction());
        bReset.setText("Zurücksetzen");

        cbKategorie = new JComboBox(model.createKategorieComboModel(model.getGebKatList()));
    }
    
     public JComponent buildPanel() {
        initComponents();

        JViewPanel mainPanel = new JViewPanel();
        mainPanel.setHeaderInfo(new HeaderInfo(model.getHeaderText()));
        JButtonPanel buttonPanel = mainPanel.getButtonPanel();

        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bReset);
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

        return mainPanel;
    }
}
