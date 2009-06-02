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
import java.awt.BorderLayout;
import javax.swing.JPanel;
import cw.roommanagementmodul.pojo.Bereich;
import cw.roommanagementmodul.pojo.manager.BereichManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Dominik
 */
public class EditBereichView extends CWView
{

    private EditBereichPresentationModel model;
    private CWButton bSave;
    private CWButton bCancel;
    private CWButton bSaveCancel;
    private CWLabel lName;
    private CWTextField tfName;
    private CWLabel lParentBereich;
    private CWComboBox parentComboBox;
    private CWComponentFactory.CWComponentContainer componentContainer;

    public EditBereichView(EditBereichPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        lName = CWComponentFactory.createLabel("Name: ");
        lParentBereich = CWComponentFactory.createLabel("Übergeordneter Bereich: ");

        //ComboBox
        parentComboBox = CWComponentFactory.createComboBox(model.getBereichList());

        BereichManager bManager= BereichManager.getInstance();
        //bManager.refreshBereich(model.getVaterBereich());
        if (model.getVaterBereich() != null && model.getHeaderText().equals("Bereich erstellen")) {
            if (model.getVaterBereich().getZimmerList() == null || model.getVaterBereich().getZimmerList().size() == 0) {
                parentComboBox.setSelectedItem(model.getVaterBereich());
                model.getUnsaved().setValue(false);
            } else {
                JOptionPane.showMessageDialog(null, "Im ausgewählten Bereich kann kein untergeordneter Bereich hinzugefügt werden," +
                        " weil sich in diesem Bereich bereits Zimmer befinden!");
                parentComboBox.setSelectedItem(model.getBereichManager().getRoot());
                model.getUnsaved().setValue(false);
            }
        }

        if (model.getBereichList().getList().size() == 1 && model.getHeaderText().equals("Bereich erstellen")) {
            parentComboBox.setSelectedItem(model.getBereichManager().getRoot());
            model.getUnsaved().setValue(false);
        }
        if(model.getVaterBereich()==null && model.getHeaderText().equals("Bereich erstellen")&& model.getBereichList().getList().size() > 1){
            parentComboBox.setSelectedItem(model.getBereichManager().getRoot());
            model.getUnsaved().setValue(false);
        }


        tfName = CWComponentFactory.createTextField(model.getBufferedModel(Bereich.PROPERTYNAME_NAME), false);

        bSave = CWComponentFactory.createButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schließen");

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(lName)
                .addComponent(lParentBereich)
                .addComponent(parentComboBox)
                .addComponent(tfName)
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

        CWView panel = CWComponentFactory.createView();
        JPanel contentPanel = panel.getContentPanel();

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


        this.getContentPanel().add(panel, BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
}
