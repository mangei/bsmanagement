package cw.roommanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.roommanagementmodul.persistence.GebuehrenKategorie;

/**
 *
 * @author Dominik
 */
public class EditGebuehrenKategorieView
	extends CWView<EditGebuehrenKategoriePresentationModel>
{

    private CWButton bSave;
    private CWButton bCancel;
    private CWButton bSaveCancel;
    private CWLabel lKatName;
    private CWTextField tfKatName;

    public EditGebuehrenKategorieView(EditGebuehrenKategoriePresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        lKatName = CWComponentFactory.createLabel("Bezeichnung: ");

        tfKatName = CWComponentFactory.createTextField(getModel().getBufferedModel(GebuehrenKategorie.PROPERTYNAME_NAME), false);

        bSave = CWComponentFactory.createButton(getModel().getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(getModel().getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(getModel().getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schliessen");

        getComponentContainer()
                .addComponent(lKatName)
                .addComponent(tfKatName)
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
        contentPanel.add(lKatName, cc.xy(1, 3));
        contentPanel.add(tfKatName, cc.xy(3, 3));
        
        addToContentPanel(contentPanel);
    }

    @Override
    public void dispose() {
    	super.dispose();
    }
}
