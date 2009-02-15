package cw.customermanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXList;
import cw.boardingschoolmanagement.app.CWComponentFactory;

/**
 *
 * @author ManuelG
 */
public class GroupEditCustomerTabGUIExtentionView {

    private GroupEditCustomerTabGUIExtentionPresentationModel model;
    private JXList liCustomerGroups;
    private JXList liGroups;
    private JButton bAdd;
    private JButton bRemove;

    public GroupEditCustomerTabGUIExtentionView(GroupEditCustomerTabGUIExtentionPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        liCustomerGroups    = CWComponentFactory.createList(model.getSelectionCustomerGroups(), "Keine Gruppen vorhanden");
        liGroups            = CWComponentFactory.createList(model.getSelectionGroups(), "Keine Gruppen vorhanden");
        bAdd                = CWComponentFactory.createButton(model.getAddGroupAction());
        bRemove             = CWComponentFactory.createButton(model.getRemoveGroupAction());
    }

    private void initEventHandling() {
        
    }

    public JPanel buildPanel() {
        initComponents();

        JViewPanel panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());
        panel.setName("Gruppen");

        FormLayout layout = new FormLayout(
                "200dlu, 4dlu, pref, 4dlu, 200dlu",
                "pref:grow, pref, 4dlu, pref, pref:grow"
        );

        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());
        CellConstraints cc = new CellConstraints();

        builder.add(CWComponentFactory.createViewPanel("Aktive Gruppen", liCustomerGroups), cc.xywh(1, 1, 1, 5));
        builder.add(bAdd,               cc.xy(3, 2));
        builder.add(bRemove,            cc.xy(3, 4));
        builder.add(CWComponentFactory.createViewPanel("Andere Gruppen", liGroups), cc.xywh(5, 1, 1, 5));

        initEventHandling();

        return panel;
    }

}
