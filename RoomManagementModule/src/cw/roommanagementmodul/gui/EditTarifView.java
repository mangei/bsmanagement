package cw.roommanagementmodul.gui;

import java.util.Date;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWCurrencyTextField;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.roommanagementmodul.persistence.Tarif;

/**
 *
 * @author Dominik
 */
public class EditTarifView
	extends CWView<EditTarifPresentationModel>
{

	private CWButton bSave;
    private CWButton bCancel;
    private CWButton bSaveCancel;
    private CWLabel lAb;
    private CWLabel lBis;
    private CWLabel lTarif;
    private CWCurrencyTextField tfTarif;

    public EditTarifView(EditTarifPresentationModel model) {
    	super(model);
    }

    public void initComponents() {
    	super.initComponents();

        bSave = CWComponentFactory.createButton(getModel().getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(getModel().getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(getModel().getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schliessen");

        lAb = CWComponentFactory.createLabel("Von: ");
        lBis = CWComponentFactory.createLabel("Bis: ");
        lTarif = CWComponentFactory.createLabel("Tarif: ");

        tfTarif = CWComponentFactory.createCurrencyTextField(getModel().getBufferedModel(Tarif.PROPERTYNAME_TARIF));

        // TODO move datechooser to view
        if (getModel().getTarif().getId()==null) {
            Date vonDate = getModel().getVonDate();
            if (vonDate != null) {
            	getModel().getDcVon().setDate(vonDate);
                getModel().getUnsaved().setValue(false);
            }
        }
        if(getModel().getTarif().getId()!=null){
        	getModel().setOldVon(getModel().getDcVon().getDate());
        	getModel().setOldBis(getModel().getDcBis().getDate());

        }
       
        getComponentContainer()
                .addComponent(lAb)
                .addComponent(lBis)
                .addComponent(lTarif)
                .addComponent(bSave)
                .addComponent(bCancel)
                .addComponent(bSaveCancel)
                .addComponent(getModel().getDcBis())
                .addComponent(getModel().getDcVon())
                .addComponent(tfTarif);



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
        contentPanel.add(lAb, cc.xy(1, 3));
        contentPanel.add(lBis, cc.xy(1, 5));
        contentPanel.add(lTarif, cc.xy(1, 7));

        contentPanel.add(getModel().getDcVon(), cc.xy(3, 3));
        contentPanel.add(getModel().getDcBis(), cc.xy(3, 5));
        contentPanel.add(tfTarif, cc.xy(3, 7));
        
        addToContentPanel(contentPanel);
    }

    @Override
    public void dispose() {
        
    	super.dispose();
    }
}
