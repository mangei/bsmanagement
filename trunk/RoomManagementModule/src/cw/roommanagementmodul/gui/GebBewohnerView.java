package cw.roommanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;
import javax.swing.JPanel;
import cw.roommanagementmodul.pojo.GebuehrZuordnung;

/**
 *
 * @author Dominik
 */
public class GebBewohnerView extends CWView
{

    private GebBewohnerPresentationModel model;
    private CWLabel lGebuehr;
    private CWLabel lVon;
    private CWLabel lBis;
    private CWLabel lAnmerkung;
    private CWComboBox cbGebuehr;
    private CWTextField tfAnmerkung;

    private CWButton bSave;
    private CWButton bCancel;
    private CWButton bSaveCancel;

    private CWComponentFactory.CWComponentContainer componentContainer;

    public GebBewohnerView(GebBewohnerPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        lGebuehr = CWComponentFactory.createLabel("Gebuehr: ");
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
        componentContainer = CWComponentFactory.createComponentContainer()
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

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
        //this.setName("Zimmer");
        
        CWButtonPanel buttonPanel = this.getButtonPanel();

        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);
        JPanel contentPanel = this.getContentPanel();


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
    }

    private void initEventHandling() {
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
}
