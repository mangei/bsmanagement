package cw.accountmanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWRadioButton;

/**
 * @author CreativeWorkers.at
 */
public class AccountCustomerSelectorFilterExtentionView extends CWPanel
{

    private AccountCustomerSelectorFilterExtentionPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWRadioButton rbNoMatter;
    private CWRadioButton rbBalanced;
    private CWRadioButton rbNotBalanced;

    public AccountCustomerSelectorFilterExtentionView(AccountCustomerSelectorFilterExtentionPresentationModel m) {
        model = m;
        
        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        rbNoMatter      = CWComponentFactory.createRadioButton(model.getNoMatterAction(), true);
        rbBalanced      = CWComponentFactory.createRadioButton(model.getBalancedAction());
        rbNotBalanced   = CWComponentFactory.createRadioButton(model.getNotBalancedAction());

        CWComponentFactory.createButtonGroup(rbNoMatter, rbBalanced, rbNotBalanced);

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(rbNoMatter)
                .addComponent(rbNotBalanced)
                .addComponent(rbBalanced);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {
        FormLayout layout = new FormLayout(
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref",
                "pref");
        CellConstraints cc = new CellConstraints();

        PanelBuilder builder = new PanelBuilder(layout, this);

        builder.addLabel("Konto:",      cc.xy(1, 1));
        builder.add(rbNoMatter,         cc.xy(3, 1));
        builder.add(rbBalanced,         cc.xy(5, 1));
        builder.add(rbNotBalanced,      cc.xy(7, 1));
    }

    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}
