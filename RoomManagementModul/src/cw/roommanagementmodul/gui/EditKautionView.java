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
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.roommanagementmodul.pojo.Kaution;

/**
 *
 * @author Dominik
 */
public class EditKautionView {


    private EditKautionPresentationModel model;
    public JButton bSave;
    public JButton bCancel;
    public JButton bSaveCancel;
    public JLabel lName;
    public JLabel lBetrag;
    public JTextField tfName;
    public JFormattedTextField tfBetrag;

    public EditKautionView(EditKautionPresentationModel model) {
        this.model = model;
    }

     private void initComponents() {
        lName = new JLabel("Name: ");
        lBetrag = new JLabel("Betrag");

        tfName = BasicComponentFactory.createTextField(model.getBufferedModel(Kaution.PROPERTYNAME_NAME),false);
        tfBetrag = BasicComponentFactory.createFormattedTextField(model.getBufferedModel(Kaution.PROPERTYNAME_BETRAG), NumberFormat.getCurrencyInstance());

        bSave = new JButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = new JButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = new JButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern&Schließen");

    }

    public JComponent buildPanel() {
        initComponents();

        JViewPanel mainPanel = new JViewPanel();
        mainPanel.setHeaderInfo(new HeaderInfo(model.getHeaderText()));
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
        contentPanel.add(lName, cc.xy(1, 3));
        contentPanel.add(tfName, cc.xy(3, 3));
        contentPanel.add(lBetrag, cc.xy(1, 5));
        contentPanel.add(tfBetrag, cc.xy(3, 5));


        mainPanel.getContentPanel().add(panel, BorderLayout.CENTER);

        return mainPanel;
    }

}
