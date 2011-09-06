package cw.customermanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWList;
import cw.boardingschoolmanagement.gui.component.CWPanel;

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
        liGroup.setLayoutOrientation(CWList.VERTICAL_WRAP);
//        liGroup.setPreferredSize(new Dimension(200, 20));
        liGroup.setVisibleRowCount(1);


        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(liGroup);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {
        FormLayout layout = new FormLayout(
                "pref, 4dlu, fill:pref:grow",
                "pref");
        CellConstraints cc = new CellConstraints();

        PanelBuilder builder = new PanelBuilder(layout, this);

        builder.addLabel("Gruppen:", cc.xy(1, 1));
        builder.add(liGroup, cc.xy(3, 1));
    }

    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}
