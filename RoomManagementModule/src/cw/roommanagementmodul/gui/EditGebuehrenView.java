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
import cw.roommanagementmodul.persistence.Gebuehr;

/**
 *
 * @author Dominik
 */
public class EditGebuehrenView
	extends CWView<EditGebuehrenPresentationModel>
{

    private CWLabel lGebuehrenName;
    private CWLabel lKategorie;
    private CWTextField tfGebuehrenName;
    private CWComboBox cbKategorie;
    private CWButton bSave;
    private CWButton bCancel;
    private CWButton bSaveCancel;

    public EditGebuehrenView(EditGebuehrenPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        lGebuehrenName = CWComponentFactory.createLabel("Gebuehren Name: ");
        lKategorie = CWComponentFactory.createLabel("Kategorie: ");

        tfGebuehrenName = CWComponentFactory.createTextField(getModel().getBufferedModel(Gebuehr.PROPERTYNAME_NAME),false);

        bSave = CWComponentFactory.createButton(getModel().getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(getModel().getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(getModel().getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schliessen");

        cbKategorie = CWComponentFactory.createComboBox(getModel().getGebKatList());

        getComponentContainer()
                .addComponent(bSave)
                .addComponent(bCancel)
                .addComponent(bSaveCancel)
                .addComponent(cbKategorie)
                .addComponent(lGebuehrenName)
                .addComponent(lKategorie)
                .addComponent(tfGebuehrenName);
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
        contentPanel.add(lGebuehrenName, cc.xy(1, 3));
        contentPanel.add(lKategorie, cc.xy(1, 5));

        contentPanel.add(tfGebuehrenName, cc.xy(3, 3));
        contentPanel.add(cbKategorie, cc.xy(3, 5));
        
        addToContentPanel(contentPanel);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
