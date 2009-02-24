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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.roommanagementmodul.pojo.GebuehrZuordnung;

/**
 *
 * @author Dominik
 */
public class GebBewohnerView {

    private GebBewohnerPresentationModel model;
    public JLabel lGebuehr;
    public JLabel lVon;
    public JLabel lBis;
    public JLabel lAnmerkung;
    public JComboBox cbGebuehr;
    public JTextField tfAnmerkung;

    public JButton bSave;
    public JButton bCancel;
    public JButton bSaveCancel;

    public GebBewohnerView(GebBewohnerPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        lGebuehr = new JLabel("Gebühr: ");
        lVon = new JLabel("Von: ");
        lBis = new JLabel("Bis: ");
        lAnmerkung = new JLabel("Anmerkung: ");

        tfAnmerkung = CWComponentFactory.createTextField(model.getBufferedModel(GebuehrZuordnung.PROPERTYNAME_ANMERKUNG), false);

        bSave = new JButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = new JButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = new JButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schließen");


        cbGebuehr = new JComboBox(model.createGebuehrComboModel(model.getGebuehrList()));
    }

    public JComponent buildPanel() {
        initComponents();
        initEventHandling();

        JViewPanel panel = new JViewPanel(model.getHeaderInfo());
        //panel.setName("Zimmer");
        
        JButtonPanel buttonPanel = panel.getButtonPanel();

        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);
        JPanel contentPanel = panel.getContentPanel();


        FormLayout layout = new FormLayout("right:pref, 4dlu, 50dlu:grow, 4dlu, right:pref, 4dlu, 50dlu:grow",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

        contentPanel.setLayout(layout);

        CellConstraints cc = new CellConstraints();
        //builder.addSeparator("Allgemein:", cc.xyw(1, 1, 7));
        contentPanel.add(lGebuehr, cc.xy(1, 5));
        contentPanel.add(lVon, cc.xy(1, 7));
        contentPanel.add(lBis, cc.xy(1, 9));
        contentPanel.add(lAnmerkung, cc.xy(1, 11));

        contentPanel.add(cbGebuehr, cc.xy(3, 5));
        contentPanel.add(model.getDcVon(), cc.xy(3, 7));
        contentPanel.add(model.getDcBis(), cc.xy(3, 9));
        contentPanel.add(tfAnmerkung, cc.xy(3, 11));


        return panel;

    }

    private void initEventHandling() {
    }
}
