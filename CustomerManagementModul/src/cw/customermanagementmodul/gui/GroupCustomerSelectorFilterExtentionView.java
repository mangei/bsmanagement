package cw.customermanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWJPanel;
import cw.boardingschoolmanagement.gui.component.CWJXList;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * @author CreativeWorkers.at
 */
public class GroupCustomerSelectorFilterExtentionView
    implements Disposable
{

    private GroupCustomerSelectorFilterExtentionPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWJPanel panel;
    private CWJXList liGroup;

    public GroupCustomerSelectorFilterExtentionView(GroupCustomerSelectorFilterExtentionPresentationModel m) {
        model = m;
    }

    private void initComponents() {
        liGroup = CWComponentFactory.createList(model.getGroupSelection());
        liGroup.setSelectionModel(model.getGroupSelectionModel());
        liGroup.setPreferredSize(new Dimension(200, 0));

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(liGroup);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initComponents();

        panel = CWComponentFactory.createPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        FormLayout layout = new FormLayout(
                "pref",
                "pref, 4dlu, fill:pref:grow");
        CellConstraints cc = new CellConstraints();

        PanelBuilder builder = new PanelBuilder(layout, panel);

        builder.addLabel("Gruppen:", cc.xy(1, 1));
        builder.add(liGroup, cc.xy(1, 3));

        panel.addDisposableListener(this);

        initEventHandling();
        
        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);

        componentContainer.dispose();

        model.dispose();
    }
}
