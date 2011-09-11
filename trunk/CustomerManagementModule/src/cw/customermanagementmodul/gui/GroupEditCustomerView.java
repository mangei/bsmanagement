package cw.customermanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWList;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Manuel Geier
 */
public class GroupEditCustomerView
	extends CWView<GroupEditCustomerPresentationModel>
{
    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWList liCustomerGroups;
    private CWList liGroups;
    private CWButton bAdd;
    private CWButton bRemove;

    public GroupEditCustomerView(GroupEditCustomerPresentationModel model) {
        super(model);

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        liCustomerGroups    = CWComponentFactory.createList(getModel().getSelectionCustomerGroups(), "Keine Gruppen vorhanden");
        liGroups            = CWComponentFactory.createList(getModel().getSelectionGroups(), "Keine Gruppen vorhanden");
        bAdd                = CWComponentFactory.createButton(getModel().getAddGroupAction());
        bRemove             = CWComponentFactory.createButton(getModel().getRemoveGroupAction());

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(bAdd)
                .addComponent(bRemove)
                .addComponent(liCustomerGroups)
                .addComponent(liGroups);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {
        this.setHeaderInfo(getModel().getHeaderInfo());
        this.setName("Gruppen");

        FormLayout layout = new FormLayout(
                "200dlu, 4dlu, pref, 4dlu, 200dlu",
                "pref:grow, pref, 4dlu, pref, pref:grow"
        );

        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.add(CWComponentFactory.createView(null, "Aktive Gruppen", liCustomerGroups), cc.xywh(1, 1, 1, 5));
        builder.add(bAdd,               cc.xy(3, 2));
        builder.add(bRemove,            cc.xy(3, 4));
        builder.add(CWComponentFactory.createView(null, "Andere Gruppen", liGroups), cc.xywh(5, 1, 1, 5));
    
        addToContentPanel(builder.getPanel(), true);
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        getModel().dispose();
    }

}
