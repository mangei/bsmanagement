package cw.customermanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import javax.swing.BorderFactory;

/**
 * @author CreativeWorkers.at
 */
public class SearchCustomerSelectorFilterExtentionView extends CWPanel
{

    private SearchCustomerSelectorFilterExtentionPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWTextField tfSearch;
    private CWButton bClear;

    public SearchCustomerSelectorFilterExtentionView(SearchCustomerSelectorFilterExtentionPresentationModel m) {
        model = m;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        tfSearch = CWComponentFactory.createTextField(model.getSearchModel());
        bClear = CWComponentFactory.createButton(model.getClearAction());
        bClear.setIcon(CWUtils.loadIcon("cw/customermanagementmodul/images/clear.png"));
        bClear.setRolloverIcon(CWUtils.loadIcon("cw/customermanagementmodul/images/clear_hover.png"));
        bClear.setContentAreaFilled(false);
        bClear.setFocusPainted(false);
        bClear.setBorderPainted(false);

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(tfSearch)
                .addComponent(bClear);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        FormLayout layout = new FormLayout(
                "pref, 4dlu, fill:pref:grow, 4dlu, pref",
                "pref");
        CellConstraints cc = new CellConstraints();

        PanelBuilder builder = new PanelBuilder(layout, this);

        builder.addLabel("Suche:", cc.xy(1, 1));
        builder.add(tfSearch, cc.xy(3, 1));
        builder.add(bClear, cc.xy(5, 1));
    }

    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}
