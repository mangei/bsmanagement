package cw.roommanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWCurrencyTextField;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.roommanagementmodul.persistence.Kaution;

/**
 *
 * @author Dominik
 */
public class EditKautionView
	extends CWView<EditKautionPresentationModel>
{

    private CWButton bSave;
    private CWButton bCancel;
    private CWButton bSaveCancel;
    private CWLabel lName;
    private CWLabel lBetrag;
    private CWTextField tfName;
    private CWCurrencyTextField tfBetrag;

    public EditKautionView(EditKautionPresentationModel model) {
    	super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        lName = CWComponentFactory.createLabel("Name: ");
        lBetrag = CWComponentFactory.createLabel("Betrag");

        tfName = CWComponentFactory.createTextField(getModel().getBufferedModel(Kaution.PROPERTYNAME_NAME),false);
        tfBetrag = CWComponentFactory.createCurrencyTextField(getModel().getBufferedModel(Kaution.PROPERTYNAME_BETRAG));

        bSave = CWComponentFactory.createButton(getModel().getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(getModel().getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(getModel().getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern&Schliessen");

        getComponentContainer()
                .addComponent(lName)
                .addComponent(lBetrag)
                .addComponent(tfName)
                .addComponent(tfBetrag)
                .addComponent(bSave)
                .addComponent(bCancel)
                .addComponent(bSaveCancel);
    }

    public void buildView() {
    	super.buildView();

        this.setHeaderInfo(getModel().getHeaderInfo());
        CWButtonPanel buttonPanel = this.getButtonPanel();

        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);

        CWPanel contentPanel = CWComponentFactory.createPanel();

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
        
        addToContentPanel(contentPanel);
    }

    @Override
    public void dispose() {
    	
        super.dispose();
    }

}
