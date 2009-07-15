package cw.customermanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWList;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import java.awt.Dimension;
import javax.swing.BorderFactory;

/**
 * @author CreativeWorkers.at
 */
public class GroupCustomerSelectorFilterExtentionView extends CWPanel
{

    private GroupCustomerSelectorFilterExtentionPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWList liGroup;

    public GroupCustomerSelectorFilterExtentionView(GroupCustomerSelectorFilterExtentionPresentationModel m) {
        model = m;
        
        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        liGroup = CWComponentFactory.createList(model.getGroupSelection());
        liGroup.setSelectionModel(model.getGroupSelectionModel());
        liGroup.setPreferredSize(new Dimension(200, 0));

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(liGroup);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {
        this.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        FormLayout layout = new FormLayout(
                "pref",
                "pref, 4dlu, fill:pref:grow");
        CellConstraints cc = new CellConstraints();

        PanelBuilder builder = new PanelBuilder(layout, this);

        builder.addLabel("Gruppen:", cc.xy(1, 1));
        builder.add(liGroup, cc.xy(1, 3));
    }

    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}
