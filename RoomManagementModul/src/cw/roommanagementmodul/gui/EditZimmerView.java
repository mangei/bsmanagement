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
import cw.roommanagementmodul.pojo.Bereich;
import javax.swing.JPanel;
import cw.roommanagementmodul.pojo.Zimmer;
import java.util.List;

/**
 *
 * @author Dominik
 */
public class EditZimmerView extends CWView
{

    private EditZimmerPresentationModel model;
    private CWLabel lZimmerName;
    private CWLabel lBettenAnzahl;
    private CWLabel lBereich;
    private CWTextField tfZimmerName;
    private CWTextField tfBettenAnzahl;
    private CWComboBox cbBereich;
    private CWButton bSave;
    private CWButton bCancel;
    private CWButton bSaveCancel;
    private CWComponentFactory.CWComponentContainer componentContainer;

    public EditZimmerView(EditZimmerPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        lZimmerName = CWComponentFactory.createLabel("Zimmer Name: ");
        lBettenAnzahl = CWComponentFactory.createLabel("Betten Anzahl: ");
        lBereich = CWComponentFactory.createLabel("Bereich: ");

        //tfZimmerName = new JTextField(model.getBufferedModel(Zimmer.PROPERTYNAME_NAME));
        //tfBettenAnzahl = new JTextField(model.getBufferedModel(Zimmer.PROPERTYNAME_ANZBETTEN));
        tfZimmerName = CWComponentFactory.createTextField(model.getBufferedModel(Zimmer.PROPERTYNAME_NAME), false);
        tfBettenAnzahl = CWComponentFactory.createTextField(model.getBufferedModel(Zimmer.PROPERTYNAME_ANZBETTEN), false);
        tfBettenAnzahl.setDocument(model.getDigitDocument());

        bSave = CWComponentFactory.createButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schließen");


        cbBereich = CWComponentFactory.createComboBox(model.getBereichList());
        //cbBereich = new JComboBox(model.createParentBereichComboModel(model.getBereichList()));

        if (model.getZimmer().getBereich() == null) {
            List<Bereich> leafList=model.getBereichManager().getBereichLeaf();
            cbBereich.setSelectedItem(leafList.get(0));
            model.getUnsaved().setValue(false);
        }

        componentContainer= CWComponentFactory.createCWComponentContainer()
                .addComponent(bSave)
                .addComponent(bCancel)
                .addComponent(bSaveCancel)
                .addComponent(cbBereich)
                .addComponent(lZimmerName)
                .addComponent(lBettenAnzahl)
                .addComponent(lBereich)
                .addComponent(tfZimmerName)
                .addComponent(tfBettenAnzahl);
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
//        builder.addSeparator("Allgemein:", cc.xyw(1, 1, 7));
        contentPanel.add(lZimmerName, cc.xy(1, 3));
        contentPanel.add(lBettenAnzahl, cc.xy(1, 5));
        contentPanel.add(lBereich, cc.xy(1, 7));

        contentPanel.add(tfZimmerName, cc.xy(3, 3));
        contentPanel.add(tfBettenAnzahl, cc.xy(3, 5));
        contentPanel.add(cbBereich, cc.xy(3, 7));

//        builder.addSeparator(lBemerkung.getText(), cc.xyw(1, 29, 7));
    }

    @Override
    public void dispose(){
        componentContainer.dispose();
        model.dispose();
    }
}
