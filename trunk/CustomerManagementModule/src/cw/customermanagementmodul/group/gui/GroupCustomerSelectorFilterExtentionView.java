package cw.customermanagementmodul.group.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWList;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 * @author CreativeWorkers.at
 */
public class GroupCustomerSelectorFilterExtentionView
	extends CWView<GroupCustomerSelectorFilterExtentionPresentationModel>
{
	
    private CWList liGroup;

    public GroupCustomerSelectorFilterExtentionView(GroupCustomerSelectorFilterExtentionPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        liGroup = CWComponentFactory.createList(getModel().getGroupSelection());
        liGroup.setSelectionModel(getModel().getGroupSelectionModel());
        liGroup.setLayoutOrientation(CWList.VERTICAL_WRAP);
//        liGroup.setPreferredSize(new Dimension(200, 20));
        liGroup.setVisibleRowCount(1);


        getComponentContainer()
                .addComponent(liGroup);
    }

    public void buildView() {
    	super.buildView();
    	
        FormLayout layout = new FormLayout(
                "pref, 4dlu, fill:pref:grow",
                "pref");
        CellConstraints cc = new CellConstraints();

        PanelBuilder builder = new PanelBuilder(layout, this);

        builder.addLabel("Gruppen:", cc.xy(1, 1));
        builder.add(liGroup, cc.xy(3, 1));
    }

    public void dispose() {
        super.dispose();
    }
}
