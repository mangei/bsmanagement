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
import cw.roommanagementmodul.pojo.Gebuehr;

/**
 *
 * @author Dominik
 */
public class EditGebuehrenView extends CWView
{

    private EditGebuehrenPresentationModel model;
    private CWLabel lGebuehrenName;
    private CWLabel lKategorie;
    private CWTextField tfGebuehrenName;
    private CWComboBox cbKategorie;
    private CWButton bSave;
    private CWButton bCancel;
    private CWButton bSaveCancel;

    private CWComponentFactory.CWComponentContainer componentContainer;

    public EditGebuehrenView(EditGebuehrenPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        lGebuehrenName = CWComponentFactory.createLabel("Gebuehren Name: ");
        lKategorie = CWComponentFactory.createLabel("Kategorie: ");

        tfGebuehrenName = CWComponentFactory.createTextField(model.getBufferedModel(Gebuehr.PROPERTYNAME_NAME),false);

        bSave = CWComponentFactory.createButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schließen");

        cbKategorie = CWComponentFactory.createComboBox(model.getGebKatList());

        componentContainer= CWComponentFactory.createCWComponentContainer()
                .addComponent(bSave)
                .addComponent(bCancel)
                .addComponent(bSaveCancel)
                .addComponent(cbKategorie)
                .addComponent(lGebuehrenName)
                .addComponent(lKategorie)
                .addComponent(tfGebuehrenName);
    }

    private void initEventHandling() {
    }
    
     private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
        CWButtonPanel buttonPanel = this.getButtonPanel();

        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);

        JPanel contentPanel = this.getContentPanel();

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
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
}
