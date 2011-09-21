package cw.roommanagementmodul.gui;

import javax.swing.JOptionPane;

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
import cw.roommanagementmodul.persistence.Bereich;
import cw.roommanagementmodul.persistence.PMBereich;

/**
 *
 * @author Dominik
 */
public class EditBereichView
	extends CWView<EditBereichPresentationModel>
{

    private CWButton bSave;
    private CWButton bCancel;
    private CWButton bSaveCancel;
    private CWLabel lName;
    private CWTextField tfName;
    private CWLabel lParentBereich;
    private CWComboBox parentComboBox;

    public EditBereichView(EditBereichPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        lName = CWComponentFactory.createLabel("Name: ");
        lParentBereich = CWComponentFactory.createLabel("Uebergeordneter Bereich: ");

        //ComboBox
        parentComboBox = CWComponentFactory.createComboBox(getModel().getBereichList());

        PMBereich bManager= PMBereich.getInstance();
        //bManager.refreshBereich(model.getVaterBereich());
        if (getModel().getVaterBereich() != null && getModel().getBereich().getId()==null) {
            if (getModel().getVaterBereich().getZimmerList() == null || getModel().getVaterBereich().getZimmerList().size() == 0) {
                parentComboBox.setSelectedItem(getModel().getVaterBereich());
                getModel().getUnsaved().setValue(false);
            } else {
                JOptionPane.showMessageDialog(null, "Im ausgewaehlten Bereich kann kein untergeordneter Bereich hinzugefuegt werden," +
                        " weil sich in diesem Bereich bereits Zimmer befinden!");
                parentComboBox.setSelectedItem(getModel().getBereichManager().getRoot(getModel().getEntityManager()));
                getModel().getUnsaved().setValue(false);
            }
        }

        if (getModel().getBereichList().getList().size() == 1 && getModel().getBereich().getId()==null) {
            parentComboBox.setSelectedItem(getModel().getBereichManager().getRoot(getModel().getEntityManager()));
            getModel().getUnsaved().setValue(false);
        }
        if(getModel().getVaterBereich()==null && getModel().getBereich().getId()==null&& getModel().getBereichList().getList().size() > 1){
            parentComboBox.setSelectedItem(getModel().getBereichManager().getRoot(getModel().getEntityManager()));
            getModel().getUnsaved().setValue(false);
        }


        tfName = CWComponentFactory.createTextField(getModel().getBufferedModel(Bereich.PROPERTYNAME_NAME), false);

        bSave = CWComponentFactory.createButton(getModel().getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(getModel().getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(getModel().getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schliessen");

        getComponentContainer()
                .addComponent(lName)
                .addComponent(lParentBereich)
                .addComponent(parentComboBox)
                .addComponent(tfName)
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
//        builder.addSeparator("Allgemein:", cc.xyw(1, 1, 7));
        contentPanel.add(lName, cc.xy(1, 3));
        contentPanel.add(lParentBereich, cc.xy(1, 5));

        contentPanel.add(tfName, cc.xy(3, 3));
        contentPanel.add(parentComboBox, cc.xy(3, 5));

//        builder.addSeparator(lBemerkung.getText(), cc.xyw(1, 29, 7));


        addToContentPanel(contentPanel);
    }

    @Override
    public void dispose() {
    	
    	super.dispose();
    }
}
