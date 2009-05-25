package cw.customermanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JButton;
import org.jdesktop.swingx.JXList;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author ManuelG
 */
public class GroupEditCustomerView extends CWView
{

    private GroupEditCustomerPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JXList liCustomerGroups;
    private JXList liGroups;
    private JButton bAdd;
    private JButton bRemove;

    public GroupEditCustomerView(GroupEditCustomerPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        liCustomerGroups    = CWComponentFactory.createList(model.getSelectionCustomerGroups(), "Keine Gruppen vorhanden");
        liGroups            = CWComponentFactory.createList(model.getSelectionGroups(), "Keine Gruppen vorhanden");
        bAdd                = CWComponentFactory.createButton(model.getAddGroupAction());
        bRemove             = CWComponentFactory.createButton(model.getRemoveGroupAction());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(bAdd)
                .addComponent(bRemove)
                .addComponent(liCustomerGroups)
                .addComponent(liGroups);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
        this.setName("Gruppen");

        FormLayout layout = new FormLayout(
                "200dlu, 4dlu, pref, 4dlu, 200dlu",
                "pref:grow, pref, 4dlu, pref, pref:grow"
        );

        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        CellConstraints cc = new CellConstraints();

        builder.add(CWComponentFactory.createViewPanel("Aktive Gruppen", liCustomerGroups), cc.xywh(1, 1, 1, 5));
        builder.add(bAdd,               cc.xy(3, 2));
        builder.add(bRemove,            cc.xy(3, 4));
        builder.add(CWComponentFactory.createViewPanel("Andere Gruppen", liGroups), cc.xywh(5, 1, 1, 5));
    }

    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }

}
