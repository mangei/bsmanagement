/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWCurrencyTextField;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.BorderLayout;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.roommanagementmodul.pojo.Kaution;

/**
 *
 * @author Dominik
 */
public class EditKautionView implements Disposable{


    private EditKautionPresentationModel model;
    private JButton bSave;
    private JButton bCancel;
    private JButton bSaveCancel;
    private JLabel lName;
    private JLabel lBetrag;
    private JTextField tfName;
    private CWCurrencyTextField tfBetrag;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel mainPanel;

    public EditKautionView(EditKautionPresentationModel model) {
        this.model = model;
    }

     private void initComponents() {
        lName = CWComponentFactory.createLabel("Name: ");
        lBetrag = CWComponentFactory.createLabel("Betrag");

        tfName = CWComponentFactory.createTextField(model.getBufferedModel(Kaution.PROPERTYNAME_NAME),false);
        tfBetrag = CWComponentFactory.createCurrencyTextField(model.getBufferedModel(Kaution.PROPERTYNAME_BETRAG));

        bSave = CWComponentFactory.createButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern&Schließen");

        componentContainer=CWComponentFactory.createCWComponentContainer()
                .addComponent(lName)
                .addComponent(lBetrag)
                .addComponent(tfName)
                .addComponent(tfBetrag)
                .addComponent(bSave)
                .addComponent(bCancel)
                .addComponent(bSaveCancel);
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
        contentPanel.add(lName, cc.xy(1, 3));
        contentPanel.add(tfName, cc.xy(3, 3));
        contentPanel.add(lBetrag, cc.xy(1, 5));
        contentPanel.add(tfBetrag, cc.xy(3, 5));


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
