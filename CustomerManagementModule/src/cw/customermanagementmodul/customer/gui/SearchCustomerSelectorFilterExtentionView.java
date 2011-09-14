package cw.customermanagementmodul.customer.gui;

import javax.swing.BorderFactory;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 * @author CreativeWorkers.at
 */
public class SearchCustomerSelectorFilterExtentionView
	extends CWView<SearchCustomerSelectorFilterExtentionPresentationModel>
{

    private CWTextField tfSearch;
    private CWButton bClear;

    public SearchCustomerSelectorFilterExtentionView(SearchCustomerSelectorFilterExtentionPresentationModel model) {
        super(model);
    }

    public void initComponents() {
        tfSearch = CWComponentFactory.createTextField(getModel().getSearchModel());
        bClear = CWComponentFactory.createButton(getModel().getClearAction());
        bClear.setIcon(CWUtils.loadIcon("cw/customermanagementmodul/images/clear.png"));
        bClear.setRolloverIcon(CWUtils.loadIcon("cw/customermanagementmodul/images/clear_hover.png"));
        bClear.setContentAreaFilled(false);
        bClear.setFocusPainted(false);
        bClear.setBorderPainted(false);

        getComponentContainer()
                .addComponent(tfSearch)
                .addComponent(bClear);
    }

    public void buildView() {
    	super.buildView();
    	
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
        super.dispose();
    }
}
