package cw.roommanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.roommanagementmodul.persistence.GebuehrZuordnung;

/**
 *
 * @author Dominik
 */
public class GebBewohnerView
	extends CWView<GebBewohnerPresentationModel>
{

    private CWLabel lGebuehr;
    private CWLabel lVon;
    private CWLabel lBis;
    private CWLabel lAnmerkung;
    private CWComboBox cbGebuehr;
    private CWTextField tfAnmerkung;

    private CWButton bSave;
    private CWButton bCancel;
    private CWButton bSaveCancel;

    public GebBewohnerView(GebBewohnerPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        lGebuehr = CWComponentFactory.createLabel("Gebuehr: ");
        lVon = CWComponentFactory.createLabel("Von: ");
        lBis = CWComponentFactory.createLabel("Bis: ");
        lAnmerkung = CWComponentFactory.createLabel("Anmerkung: ");

        tfAnmerkung = CWComponentFactory.createTextField(getModel().getBufferedModel(GebuehrZuordnung.PROPERTYNAME_ANMERKUNG), false);

        bSave = CWComponentFactory.createButton(getModel().getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(getModel().getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(getModel().getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schliessen");

        cbGebuehr = CWComponentFactory.createComboBox(getModel().getGebuehrList());
        
        getComponentContainer()
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

    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());
        //this.setName("Zimmer");
        
        CWButtonPanel buttonPanel = this.getButtonPanel();

        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);
        
        CWPanel contentPanel = CWComponentFactory.createPanel();

        FormLayout layout = new FormLayout("right:pref, 4dlu, 50dlu:grow, 4dlu, right:pref, 4dlu, 50dlu:grow",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

        contentPanel.setLayout(layout);

        CellConstraints cc = new CellConstraints();
        //builder.addSeparator("Allgemein:", cc.xyw(1, 1, 7));
        contentPanel.add(lGebuehr, cc.xy(1, 5));
        contentPanel.add(lVon, cc.xy(1, 7));
        contentPanel.add(lBis, cc.xy(1, 9));
        contentPanel.add(lAnmerkung, cc.xy(1, 11));

        // TODO move datechooser to view
        contentPanel.add(cbGebuehr, cc.xy(3, 5));
        contentPanel.add(getModel().getDcVon(), cc.xy(3, 7));
        contentPanel.add(getModel().getDcBis(), cc.xy(3, 9));
        contentPanel.add(tfAnmerkung, cc.xy(3, 11));
        
        addToContentPanel(contentPanel);
    }

    @Override
    public void dispose() {
    	
        super.dispose();
    }
}
