package cw.accountmanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;

/**
 *
 * @author CreativeWorkers.at
 */
public class AccountOverviewAccountManagementView extends CWView
{

    private AccountOverviewAccountManagementPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    
    public AccountOverviewAccountManagementView(AccountOverviewAccountManagementPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {

        componentContainer = CWComponentFactory.createComponentContainer()
                ;
    }

    private void initEventHandling() {
    }

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
        CWButtonPanel buttonPanel = this.getButtonPanel();

        FormLayout layout = new FormLayout(
                "",
                "");
        
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();
//        builder.add(dcPostingEntryDate,         cc.xy(3, 10));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}
