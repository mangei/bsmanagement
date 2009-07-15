package cw.roommanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;
import javax.swing.JPanel;
import cw.roommanagementmodul.pojo.GebuehrenKategorie;

/**
 *
 * @author Dominik
 */
public class EditGebuehrenKategorieView extends CWView
{

    private EditGebuehrenKategoriePresentationModel model;
    private CWButton bSave;
    private CWButton bCancel;
    private CWButton bSaveCancel;
    private CWLabel lKatName;
    private CWTextField tfKatName;
    private CWComponentFactory.CWComponentContainer componentContainer;

    public EditGebuehrenKategorieView(EditGebuehrenKategoriePresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        lKatName = CWComponentFactory.createLabel("Bezeichnung: ");

        tfKatName = CWComponentFactory.createTextField(model.getBufferedModel(GebuehrenKategorie.PROPERTYNAME_NAME), false);

        bSave = CWComponentFactory.createButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schließen");

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(lKatName)
                .addComponent(tfKatName)
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
        contentPanel.add(lKatName, cc.xy(1, 3));
        contentPanel.add(tfKatName, cc.xy(3, 3));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
}
