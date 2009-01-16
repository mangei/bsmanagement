package cw.customermanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWJXList;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * @author CreativeWorkers.at
 */
public class GroupCustomerSelectorFilterExtentionView {

    private GroupCustomerSelectorFilterExtentionPresentationModel model;
    private CWJXList liGroup;

    public GroupCustomerSelectorFilterExtentionView(GroupCustomerSelectorFilterExtentionPresentationModel m) {
        model = m;
    }

    private void initComponents() {
        liGroup = CWComponentFactory.createList(model.getGroupSelection());
        liGroup.setSelectionModel(model.getGroupSelectionModel());
        liGroup.setPreferredSize(new Dimension(200, 0));
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initComponents();

        JPanel panel = CWComponentFactory.createPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        FormLayout layout = new FormLayout(
                "pref",
                "pref, 4dlu, fill:pref:grow");
        CellConstraints cc = new CellConstraints();

        PanelBuilder builder = new PanelBuilder(layout, panel);

        builder.addLabel("Gruppen:", cc.xy(1, 1));
        builder.add(liGroup, cc.xy(1, 3));

        initEventHandling();
        
        return panel;
    }

}
