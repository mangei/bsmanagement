package cw.roommanagementmodul.gui;

import java.util.List;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWIntegerTextField;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.roommanagementmodul.persistence.Bereich;
import cw.roommanagementmodul.persistence.Zimmer;

/**
 *
 * @author Dominik
 */
public class EditZimmerView
	extends CWView<EditZimmerPresentationModel>
{

    private CWLabel lZimmerName;
    private CWLabel lBettenAnzahl;
    private CWLabel lBereich;
    private CWTextField tfZimmerName;
//    private CWTextField tfBettenAnzahl;
//    private CWIntegerTextField tfBettenAnzahl;
    private CWIntegerTextField tfBettenAnzahl;
    private CWComboBox cbBereich;
    private CWButton bSave;
    private CWButton bCancel;
    private CWButton bSaveCancel;

    public EditZimmerView(EditZimmerPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        lZimmerName = CWComponentFactory.createLabel("Zimmer Name: ");
        lBettenAnzahl = CWComponentFactory.createLabel("Betten Anzahl: ");
        lBereich = CWComponentFactory.createLabel("Bereich: ");

        //tfZimmerName = new JTextField(model.getBufferedModel(Zimmer.PROPERTYNAME_NAME));
        //tfBettenAnzahl = new JTextField(model.getBufferedModel(Zimmer.PROPERTYNAME_ANZBETTEN));
        tfZimmerName = CWComponentFactory.createTextField(getModel().getBufferedModel(Zimmer.PROPERTYNAME_NAME), false);
//        tfBettenAnzahl = CWComponentFactory.createTextField(model.getBufferedModel(Zimmer.PROPERTYNAME_ANZBETTEN), false);
//        tfBettenAnzahl.setDocument(model.getDigitDocument());
          tfBettenAnzahl = CWComponentFactory.createIntegerTextField(getModel().getBufferedModel(Zimmer.PROPERTYNAME_ANZBETTEN));


        bSave = CWComponentFactory.createButton(getModel().getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(getModel().getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(getModel().getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schliessen");


        cbBereich = CWComponentFactory.createComboBox(getModel().getBereichList());
        //cbBereich = new JComboBox(model.createParentBereichComboModel(model.getBereichList()));

        if (getModel().getZimmer().getBereich() == null) {
            List<Bereich> leafList=getModel().getBereichManager().getBereichLeaf(getModel().getEntityManager());
            cbBereich.setSelectedItem(leafList.get(0));
            getModel().getUnsaved().setValue(false);
        }

        getComponentContainer()
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
//        builder.addSeparator("Allgemein:", cc.xyw(1, 1, 7));
        contentPanel.add(lZimmerName, cc.xy(1, 3));
        contentPanel.add(lBettenAnzahl, cc.xy(1, 5));
        contentPanel.add(lBereich, cc.xy(1, 7));

        contentPanel.add(tfZimmerName, cc.xy(3, 3));
        contentPanel.add(tfBettenAnzahl, cc.xy(3, 5));
        contentPanel.add(cbBereich, cc.xy(3, 7));

//        builder.addSeparator(lBemerkung.getText(), cc.xyw(1, 29, 7));
        
        addToContentPanel(contentPanel);
    }

    @Override
    public void dispose(){
        
    	super.dispose();
    }
}
