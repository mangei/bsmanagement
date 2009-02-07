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
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditReversePostingView {

    private EditReversePostingPresentationModel model;
    
    private JLabel lPostingDescription;
    private JLabel lPostingCategory;
    private JLabel lPostingAmount;
    private JLabel lPostingEntryDate;
    private JLabel lPostingLiabilitiesAssets;
    
    private JTextField tfReversePostingDescription;
    private JComboBox cbReversePostingCategory;
    private JLabel lReversePostingAmount;
    private JDateChooser dcReversePostingEntryDate;
    private JLabel lReversePostingLiabilitiesAssets;
    
    private JButton bSave;
    private JButton bReset;
    private JButton bCancel;
    private JButton bSaveCancel;
    

    public EditReversePostingView(EditReversePostingPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        lPostingDescription    = CWComponentFactory.createLabel(model.getPostingPresentationModel().getBufferedModel(Posting.PROPERTYNAME_DESCRIPTION));
        lPostingCategory       = CWComponentFactory.createLabel(model.getPostingCategorySelection().getSelectionHolder());
        lPostingAmount         = CWComponentFactory.createLabel(Double.toString(model.getPostingPresentationModel().getBufferedModel(Posting.PROPERTYNAME_AMOUNT).doubleValue()));
        Date postingEntryDate = (Date)model.getPostingPresentationModel().getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE).getValue();
        String postingEntryDateString = (postingEntryDate == null) ? "" : postingEntryDate.toString();
        lPostingEntryDate      = CWComponentFactory.createLabel(postingEntryDateString);
        lPostingLiabilitiesAssets = CWComponentFactory.createLabel(model.getPostingPresentationModel().getBufferedModel(Posting.PROPERTYNAME_LIABILITIESASSETS).booleanValue() == true ? "Soll" : "Haben");
        
        tfReversePostingDescription    = CWComponentFactory.createTextField(model.getBufferedModel(Posting.PROPERTYNAME_DESCRIPTION),false);
        cbReversePostingCategory       = CWComponentFactory.createComboBox(model.getPostingCategorySelection());
        lReversePostingAmount          = CWComponentFactory.createLabel(Double.toString(model.getBufferedModel(Posting.PROPERTYNAME_AMOUNT).doubleValue()));
        dcReversePostingEntryDate      = CWComponentFactory.createDateChooser(model.getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE));

        lReversePostingLiabilitiesAssets = CWComponentFactory.createLabel(model.getBufferedModel(Posting.PROPERTYNAME_LIABILITIESASSETS).booleanValue() == true ? "Soll" : "Haben");

        bSave       = CWComponentFactory.createButton(model.getSaveButtonAction());
        bReset      = CWComponentFactory.createButton(model.getResetButtonAction());
        bCancel     = CWComponentFactory.createButton(model.getCancelButtonAction());
        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());

//        lPostingDescription.setEnabled(false);
//        lPostingCategory.setEnabled(false);
//        lPostingAmount.setEnabled(false);
//        lPostingEntryDate.setEnabled(false);
//        lPostingLiabilitiesAssets.setEnabled(false);

//        lReversePostingAmount.setEnabled(false);
//        lReversePostingLiabilitiesAssets.setEnabled(false);
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
                "10dlu, left:pref, 4dlu, pref, 4dlu, left:200dlu",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 20dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");
        
        PanelBuilder builder = new PanelBuilder(layout,panel.getContentPanel());
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();

        builder.addSeparator("<html><b>Alte Buchung</b></html>",    cc.xyw(1, 1, 6));

        builder.addLabel("Bezeichnung:",        cc.xy(2, 3));
        builder.add(lPostingDescription,        cc.xyw(4, 3, 3));
        builder.addLabel("Kategorie:",          cc.xy(2, 5));
        builder.add(lPostingCategory,           cc.xyw(4, 5, 3));
        builder.addLabel("Betrag:",             cc.xy(2, 7));
        builder.add(lPostingAmount,             cc.xy(4, 7));
        builder.addLabel("€",                   cc.xy(6, 7));
        builder.addLabel("Art:",                cc.xy(2, 9));
        builder.add(lPostingLiabilitiesAssets,  cc.xyw(4, 9, 3));
        builder.addLabel("Eingangsdatum:",      cc.xy(2, 11));
        builder.add(lPostingEntryDate,          cc.xy(4, 11));

        builder.addSeparator("<html><b>Stornobuchung</b></html>",   cc.xyw(1, 13, 6));

        builder.addLabel("Bezeichnung:",        cc.xy(2, 15));
        builder.add(tfReversePostingDescription,cc.xyw(4, 15, 3));
        builder.addLabel("Kategorie:",          cc.xy(2, 17));
        builder.add(cbReversePostingCategory,   cc.xyw(4, 17, 3));
        builder.addLabel("Betrag:",             cc.xy(2, 19));
        builder.add(lReversePostingAmount,     cc.xy(4, 19));
        builder.addLabel("€",                   cc.xy(6, 19));
        builder.addLabel("Art:",                cc.xy(2, 21));
        builder.add(lReversePostingLiabilitiesAssets, cc.xyw(4, 21, 3));
        builder.addLabel("Eingangsdatum:",      cc.xy(2, 23));
        builder.add(dcReversePostingEntryDate,  cc.xy(4, 23));

        initEventHandling();

        return panel;
    }
}
