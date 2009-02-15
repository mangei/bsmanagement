/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.roommanagementmodul.pojo.Bereich;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.roommanagementmodul.pojo.Zimmer;
import java.util.List;

/**
 *
 * @author Dominik
 */
public class EditZimmerView {

    private EditZimmerPresentationModel model;
    public JLabel lZimmerName;
    public JLabel lBettenAnzahl;
    public JLabel lBereich;
    public JTextField tfZimmerName;
    public JTextField tfBettenAnzahl;
    public JComboBox cbBereich;
    public JButton bSave;
    public JButton bReset;
    public JButton bCancel;
    public JButton bSaveCancel;

    public EditZimmerView(EditZimmerPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        lZimmerName = new JLabel("Zimmer Name: ");
        lBettenAnzahl = new JLabel("Betten Anzahl: ");
        lBereich = new JLabel("Bereich: ");

        //tfZimmerName = new JTextField(model.getBufferedModel(Zimmer.PROPERTYNAME_NAME));
        //tfBettenAnzahl = new JTextField(model.getBufferedModel(Zimmer.PROPERTYNAME_ANZBETTEN));
        tfZimmerName = CWComponentFactory.createTextField(model.getBufferedModel(Zimmer.PROPERTYNAME_NAME), false);
        tfBettenAnzahl = CWComponentFactory.createTextField(model.getBufferedModel(Zimmer.PROPERTYNAME_ANZBETTEN), false);

        bSave = new JButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = new JButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = new JButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern&Schließen");

        bReset = new JButton(model.getResetButtonAction());
        bReset.setText("Zurücksetzen");

        cbBereich = new JComboBox(model.createParentBereichComboModel(model.getBereichList()));

        if (model.getZimmer().getBereich() == null) {
            List<Bereich> leafList=model.getBereichManager().getBereichLeaf();
            cbBereich.setSelectedItem(leafList.get(0));
            model.getUnsaved().setValue(false);
        }


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
//        builder.addSeparator("Allgemein:", cc.xyw(1, 1, 7));
        contentPanel.add(lZimmerName, cc.xy(1, 3));
        contentPanel.add(lBettenAnzahl, cc.xy(1, 5));
        contentPanel.add(lBereich, cc.xy(1, 7));

        contentPanel.add(tfZimmerName, cc.xy(3, 3));
        contentPanel.add(tfBettenAnzahl, cc.xy(3, 5));
        contentPanel.add(cbBereich, cc.xy(3, 7));

//        builder.addSeparator(lBemerkung.getText(), cc.xyw(1, 29, 7));


        mainPanel.getContentPanel().add(panel, BorderLayout.CENTER);

        return mainPanel;
    }
}
