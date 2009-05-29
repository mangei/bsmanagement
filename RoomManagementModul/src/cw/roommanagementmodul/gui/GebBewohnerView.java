/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
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
public class GebBewohnerView implements Disposable{

    private GebBewohnerPresentationModel model;
    private JLabel lGebuehr;
    private JLabel lVon;
    private JLabel lBis;
    private JLabel lAnmerkung;
    private JComboBox cbGebuehr;
    private JTextField tfAnmerkung;

    private JButton bSave;
    private JButton bCancel;
    private JButton bSaveCancel;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel mainPanel;

    public GebBewohnerView(GebBewohnerPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        lGebuehr = CWComponentFactory.createLabel("Gebühr: ");
        lVon = CWComponentFactory.createLabel("Von: ");
        lBis = CWComponentFactory.createLabel("Bis: ");
        lAnmerkung = CWComponentFactory.createLabel("Anmerkung: ");

        tfAnmerkung = CWComponentFactory.createTextField(model.getBufferedModel(GebuehrZuordnung.PROPERTYNAME_ANMERKUNG), false);

        bSave = CWComponentFactory.createButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schließen");


        cbGebuehr = CWComponentFactory.createComboBox(model.getGebuehrList());
        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(lGebuehr)
                .addComponent(lVon)
                .addComponent(lBis)
                .addComponent(lAnmerkung)
                .addComponent(tfAnmerkung)
                .addComponent(bSave)
                .addComponent(bCancel)
                .addComponent(bSaveCancel)
                .addComponent(cbGebuehr);
    }

    public JComponent buildPanel() {
        initComponents();
        initEventHandling();

        mainPanel = new JViewPanel(model.getHeaderInfo());
        //panel.setName("Zimmer");
        
        CWButtonPanel buttonPanel = mainPanel.getButtonPanel();

        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);
        JPanel contentPanel = mainPanel.getContentPanel();


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

        mainPanel.addDisposableListener(this);
        return mainPanel;
    }

    private void initEventHandling() {
    }

    public void dispose() {
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
}
