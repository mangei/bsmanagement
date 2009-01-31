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

/**
 *
 * @author CreativeWorkers.at
 */
public class EditPostingView {

    private EditPostingPresentationModel model;
    
    private JTextField tfDescription;
    private JTextField tfValue;
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
//        tfValue                = CWComponentFactory.createFormattedTextField(model.getBufferedModel(Accounting.PROPERTYNAME_AMOUNT), NumberFormat.getNumberInstance());
//        tfValue.setHorizontalAlignment(JTextField.RIGHT);
        tfValue                 = CWComponentFactory.createCurrencyTextField(model.getBufferedModel(Posting.PROPERTYNAME_AMOUNT));
        dcPostingEntryDate      = CWComponentFactory.createDateChooser(model.getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE));

        pLiabilitiesAssets = CWComponentFactory.createTrueFalsePanel(
                model.getBufferedModel(Posting.PROPERTYNAME_LIABILITIESASSETS),
                "Soll",
                "Haben",
                (Boolean)(model.getModel(Posting.PROPERTYNAME_LIABILITIESASSETS).getValue())
        );

        bSave       = new JButton(model.getSaveButtonAction());
        bReset      = new JButton(model.getResetButtonAction());
        bCancel     = new JButton(model.getCancelButtonAction());
        bSaveCancel = new JButton(model.getSaveCancelButtonAction());
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
                "right:pref, 4dlu, pref, 4dlu, 50dlu:grow:right",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");
        
        PanelBuilder builder = new PanelBuilder(layout,panel.getContentPanel());
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();
        builder.addLabel("Bezeichnung:", cc.xy(1, 1));
        builder.add(tfDescription, cc.xyw(3, 1, 3));
        builder.addLabel("Betrag:", cc.xy(1, 3));
        builder.add(tfValue, cc.xy(3, 3));
        builder.addLabel("€", cc.xy(5, 3));
        builder.addLabel("Art:", cc.xy(1, 5));
        builder.add(pLiabilitiesAssets, cc.xyw(3, 5, 3));
        builder.addLabel("Eingangsdatum:", cc.xy(1, 7));
        builder.add(dcPostingEntryDate, cc.xy(3, 7));

        initEventHandling();

        return panel;
    }
}
