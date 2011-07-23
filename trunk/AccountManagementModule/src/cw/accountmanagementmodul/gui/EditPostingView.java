package cw.accountmanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWCurrencyTextField;
import cw.boardingschoolmanagement.gui.component.CWDateChooser;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.accountmanagementmodul.pojo.AccountPosting;
import java.beans.PropertyChangeListener;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditPostingView extends CWView
{

    private EditPostingPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWTextField tfName;
    private CWCurrencyTextField tfAmount;
    private CWDateChooser dcPostingEntryDate;
    
    private CWButton bCancel;
    private CWButton bSave;

    private PropertyChangeListener postingCategoryChangeListener;

    public EditPostingView(EditPostingPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        tfName                  = CWComponentFactory.createTextField(model.getBufferedModel(AccountPosting.PROPERTYNAME_NAME),false);
        tfAmount                = CWComponentFactory.createCurrencyTextField(model.getBufferedModel(AccountPosting.PROPERTYNAME_AMOUNT));
        dcPostingEntryDate      = CWComponentFactory.createDateChooser(model.getBufferedModel(AccountPosting.PROPERTYNAME_POSTINGENTRYDATE));

        bCancel     = CWComponentFactory.createButton(model.getCancelAction());
        bSave       = CWComponentFactory.createButton(model.getSaveAction());

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(tfName)
                .addComponent(tfAmount)
                .addComponent(dcPostingEntryDate)
                .addComponent(bCancel)
                .addComponent(bSave);
    }

    private void initEventHandling() {
    }

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
        CWButtonPanel buttonPanel = this.getButtonPanel();

        buttonPanel.add(bSave);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, fill:pref",
                "pref, 4dlu, pref, 4dlu, pref");
        
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();
        builder.addLabel("Bezeichnung:",        cc.xy(1, 1));
        builder.add(tfName,                     cc.xy(3, 1));
        builder.addLabel("Betrag: € ",          cc.xy(1, 3));
        builder.add(tfAmount,                   cc.xy(3, 3));
        builder.addLabel("Eingangsdatum:",      cc.xy(1, 5));
        builder.add(dcPostingEntryDate,         cc.xy(3, 5));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}
