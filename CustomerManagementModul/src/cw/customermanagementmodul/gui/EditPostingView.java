package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.customermanagementmodul.pojo.Posting;
import javax.swing.JComboBox;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditPostingView {

    private EditPostingPresentationModel model;
    
    private JTextField tfDescription;
    private JComboBox cbPostingCategory;
    private JTextField tfAmount;
    private JDateChooser dcPostingEntryDate;
    private JPanel pLiabilitiesAssets;
    
    private JButton bSave;
    private JButton bReset;
    private JButton bCancel;
    private JButton bSaveCancel;
    

    public EditPostingView(EditPostingPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        tfDescription           = CWComponentFactory.createTextField(model.getBufferedModel(Posting.PROPERTYNAME_DESCRIPTION),false);
        cbPostingCategory       = CWComponentFactory.createComboBox(model.getPostingCategorySelection());
//        tfValue                = CWComponentFactory.createFormattedTextField(model.getBufferedModel(Accounting.PROPERTYNAME_AMOUNT), NumberFormat.getNumberInstance());
//        tfValue.setHorizontalAlignment(JTextField.RIGHT);
        tfAmount                = CWComponentFactory.createCurrencyTextField(model.getBufferedModel(Posting.PROPERTYNAME_AMOUNT));
        dcPostingEntryDate      = CWComponentFactory.createDateChooser(model.getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE));

        pLiabilitiesAssets = CWComponentFactory.createTrueFalsePanel(
                model.getBufferedModel(Posting.PROPERTYNAME_LIABILITIESASSETS),
                "Soll",
                "Haben",
                (Boolean)(model.getModel(Posting.PROPERTYNAME_LIABILITIESASSETS).getValue())
        );

        bSave       = CWComponentFactory.createButton(model.getSaveButtonAction());
        bReset      = CWComponentFactory.createButton(model.getResetButtonAction());
        bCancel     = CWComponentFactory.createButton(model.getCancelButtonAction());
        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());

        tfAmount.setEnabled(!model.isEditMode());
        pLiabilitiesAssets.setEnabled(!model.isEditMode());
    }

    private void initEventHandling() {
        // Nothing to do
    }
    
    public JPanel buildPanel() {
        initComponents();
        
        JViewPanel panel = new JViewPanel();
        JButtonPanel buttonPanel = panel.getButtonPanel();
        
        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bReset);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, pref, 4dlu, left:200dlu",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");
        
        PanelBuilder builder = new PanelBuilder(layout,panel.getContentPanel());
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();
        builder.addLabel("Bezeichnung:",        cc.xy(1, 1));
        builder.add(tfDescription,              cc.xyw(3, 1, 3));
        builder.addLabel("Kategorie:",          cc.xy(1, 3));
        builder.add(cbPostingCategory,          cc.xyw(3, 3, 3));
        builder.addLabel("Betrag:",             cc.xy(1, 5));
        builder.add(tfAmount,                    cc.xy(3, 5));
        builder.addLabel("€",                   cc.xy(5, 5));
        builder.addLabel("Art:",                cc.xy(1, 7));
        builder.add(pLiabilitiesAssets,         cc.xyw(3, 7, 3));
        builder.addLabel("Eingangsdatum:",      cc.xy(1, 9));
        builder.add(dcPostingEntryDate,         cc.xy(3, 9));

        initEventHandling();

        return panel;
    }
}
