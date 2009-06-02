package cw.roommanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWCurrencyTextField;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;
import javax.swing.JPanel;
import cw.roommanagementmodul.pojo.Kaution;

/**
 *
 * @author Dominik
 */
public class EditKautionView extends CWView
{

    private EditKautionPresentationModel model;
    private CWButton bSave;
    private CWButton bCancel;
    private CWButton bSaveCancel;
    private CWLabel lName;
    private CWLabel lBetrag;
    private CWTextField tfName;
    private CWCurrencyTextField tfBetrag;

    private CWComponentFactory.CWComponentContainer componentContainer;

    public EditKautionView(EditKautionPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        lName = CWComponentFactory.createLabel("Name: ");
        lBetrag = CWComponentFactory.createLabel("Betrag");

        tfName = CWComponentFactory.createTextField(model.getBufferedModel(Kaution.PROPERTYNAME_NAME),false);
        tfBetrag = CWComponentFactory.createCurrencyTextField(model.getBufferedModel(Kaution.PROPERTYNAME_BETRAG));

        bSave = CWComponentFactory.createButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern&Schließen");

        componentContainer=CWComponentFactory.createCWComponentContainer()
                .addComponent(lName)
                .addComponent(lBetrag)
                .addComponent(tfName)
                .addComponent(tfBetrag)
                .addComponent(bSave)
                .addComponent(bCancel)
                .addComponent(bSaveCancel);
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
        contentPanel.add(lName, cc.xy(1, 3));
        contentPanel.add(tfName, cc.xy(3, 3));
        contentPanel.add(lBetrag, cc.xy(1, 5));
        contentPanel.add(tfBetrag, cc.xy(3, 5));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }

}
