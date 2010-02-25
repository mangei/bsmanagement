package cw.accountmanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWCurrencyTextField;
import cw.boardingschoolmanagement.gui.component.CWDateChooser;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.accountmanagementmodul.pojo.Posting;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditReversePostingView extends CWView
{

    private EditReversePostingPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWLabel lPostingDescription;
    private CWLabel lPostingCategory;
    private CWLabel lPostingAmount;
    private CWLabel lPostingEntryDate;
    private CWLabel lPostingLiabilitiesAssets;
    
    private CWTextField tfReversePostingDescription;
    private CWComboBox cbReversePostingCategory;
    private CWCurrencyTextField tfReversePostingAmount;
    private CWDateChooser dcReversePostingEntryDate;
    private CWLabel lReversePostingLiabilitiesAssets;
    private CWLabel lLocked;
    
    private CWPanel pPostingCategoryExtention;
    
    private CWButton bCancel;
    private CWButton bSaveCancel;

    private PropertyChangeListener postingCategoryChangeListener;

    public EditReversePostingView(EditReversePostingPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        lPostingDescription    = CWComponentFactory.createLabel(model.getPostingPresentationModel().getBufferedModel(Posting.PROPERTYNAME_DESCRIPTION));
        lPostingCategory       = CWComponentFactory.createLabel(model.getPostingCategorySelection().getSelection() != null ? model.getPostingCategorySelection().getSelection().getName() : "");
        lPostingAmount         = CWComponentFactory.createLabel(Double.toString(model.getPostingPresentationModel().getBufferedModel(Posting.PROPERTYNAME_AMOUNT).doubleValue()));
        lPostingEntryDate      = CWComponentFactory.createLabelDate(model.getPostingPresentationModel().getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE));
        lPostingLiabilitiesAssets = CWComponentFactory.createLabelBoolean(model.getPostingPresentationModel().getBufferedModel(Posting.PROPERTYNAME_LIABILITIESASSETS), "Soll", "Haben");
        
        tfReversePostingDescription    = CWComponentFactory.createTextField(model.getReversePostingPresentationModel().getBufferedModel(Posting.PROPERTYNAME_DESCRIPTION),false);
        cbReversePostingCategory       = CWComponentFactory.createComboBox(model.getPostingCategorySelection());
        tfReversePostingAmount          = CWComponentFactory.createCurrencyTextField(model.getReversePostingPresentationModel().getBufferedModel(Posting.PROPERTYNAME_AMOUNT));
        dcReversePostingEntryDate      = CWComponentFactory.createDateChooser(model.getReversePostingPresentationModel().getBufferedModel(Posting.PROPERTYNAME_POSTINGENTRYDATE));

        lReversePostingLiabilitiesAssets = CWComponentFactory.createLabel(model.getReversePostingPresentationModel().getBufferedModel(Posting.PROPERTYNAME_LIABILITIESASSETS).booleanValue() == true ? "Soll" : "Haben");

        lLocked = CWComponentFactory.createLabel("Gesperrt", CWUtils.loadIcon("cw/customermanagementmodul/images/lock.png"));
        lLocked.setToolTipText(CWComponentFactory.createToolTip(
                "Gesperrt",
                "Diese Kategorie ist für Sie gesperrt<br>und kann nicht geändert werden.<br>Grund: Es handelt sich um eine<br>automatische Buchung.",
                "cw/customermanagementmodul/images/lock.png"
        ));
        lLocked.setVisible(false);

        bCancel     = CWComponentFactory.createButton(model.getCancelButtonAction());
        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());

        cbReversePostingCategory.setEnabled(false);

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(lPostingDescription)
                .addComponent(lPostingCategory)
                .addComponent(lPostingAmount)
                .addComponent(lPostingEntryDate)
                .addComponent(lPostingLiabilitiesAssets)
                .addComponent(tfReversePostingDescription)
                .addComponent(cbReversePostingCategory)
                .addComponent(tfReversePostingAmount)
                .addComponent(dcReversePostingEntryDate)
                .addComponent(lReversePostingLiabilitiesAssets)
                .addComponent(lLocked)
                .addComponent(bSaveCancel)
                .addComponent(bCancel);

        pPostingCategoryExtention = CWComponentFactory.createPanel();
        FormLayout pPostingCategoryExtentionLayout = new FormLayout(
                "fill:pref:grow:",
                "4dlu, fill:pref"
        );
        pPostingCategoryExtention.setLayout(pPostingCategoryExtentionLayout);
    }

    private void initEventHandling() {
        model.getPostingCategorySelection().addValueChangeListener(postingCategoryChangeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                // Change the extention component
                changePostingCategoryExtentionComponent();
            }
        });
        changePostingCategoryExtentionComponent();
    }

    private void changePostingCategoryExtentionComponent() {
        pPostingCategoryExtention.removeAll();
        JComponent comp = model.getPostingCategoryExtentionComponent();
        if(comp != null) {
            CellConstraints cc = new CellConstraints();
            pPostingCategoryExtention.add(comp, cc.xy(1, 2));
        }
        this.validate();
    }
    
    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
        CWButtonPanel buttonPanel = this.getButtonPanel();

        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "10dlu, left:pref, 4dlu, fill:pref, 4dlu, left:200dlu, 4dlu, pref",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 20dlu, pref, 4dlu, pref, 4dlu, pref, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");
        
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();

        builder.addSeparator("<html><b>Alte Buchung</b></html>",    cc.xyw(1, 1, 6));

        builder.addLabel("Bezeichnung:",        cc.xy(2, 3));
        builder.add(lPostingDescription,        cc.xyw(4, 3, 3));
        builder.addLabel("Kategorie:",          cc.xy(2, 5));
        builder.add(lPostingCategory,           cc.xyw(4, 5, 3));
        builder.addLabel("Betrag:",             cc.xy(2, 7));
        builder.add(lPostingAmount,             cc.xy(4, 7, CellConstraints.RIGHT, CellConstraints.CENTER));
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
        builder.add(lLocked,                    cc.xy(8, 17));
        builder.add(pPostingCategoryExtention,  cc.xyw(4, 18, 3));
        builder.addLabel("Betrag:",             cc.xy(2, 20));
        builder.add(tfReversePostingAmount,      cc.xy(4, 20, CellConstraints.RIGHT, CellConstraints.CENTER));
        builder.addLabel("€",                   cc.xy(6, 20));
        builder.addLabel("Art:",                cc.xy(2, 22));
        builder.add(lReversePostingLiabilitiesAssets, cc.xyw(4, 22, 3));
        builder.addLabel("Eingangsdatum:",      cc.xy(2, 24));
        builder.add(dcReversePostingEntryDate,  cc.xy(4, 24));
    }

    @Override
    public void dispose() {
        model.getPostingCategorySelection().removeValueChangeListener(postingCategoryChangeListener);

        componentContainer.dispose();

        model.dispose();
    }
}
