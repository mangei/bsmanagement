package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWJXList;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class GroupManagementView {

    private GroupManagementPresentationModel model;
    private JButton bNewGroup;
    private JButton bEditGroup;
    private JButton bRemoveGroup;
    private CWJXList liGroups;
//    private CWJXList liCustomers;
    private CustomerSelectorView customerSelectorView;

    public GroupManagementView(GroupManagementPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        bNewGroup =     CWComponentFactory.createButton(model.getNewGroupAction());
        bEditGroup =    CWComponentFactory.createButton(model.getEditGroupAction());
        bRemoveGroup =  CWComponentFactory.createButton(model.getRemoveGroupAction());
        liGroups =      CWComponentFactory.createList(model.getGroupSelection(), "Keine Gruppen vorhanden");
//        liCustomers =   CWComponentFactory.createList(model.getCustomerSelection(), "Bitte Gruppe auswählen");
        customerSelectorView = new CustomerSelectorView(model.getCustomerSelectorPresentationModel());
    }

    private void initEventHandling() {
//        liGroups.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            public void valueChanged(ListSelectionEvent e) {
//                if(liGroups.getSelectionModel().isSelectionEmpty()) {
//                    liCustomers.setEmptyText("Bitte Gruppe auswählen");
//                } else if(liCustomers.getModel().getSize() == 0) {
//                    liCustomers.setEmptyText("Keine Kunden in dieser Gruppe vorhanden");
//                }
//            }
//        });
    }

    public JPanel buildPanel() {
        initComponents();

        JViewPanel panel = new JViewPanel(model.getHeaderInfo());

        JButtonPanel buttonPanel = panel.getButtonPanel();
        buttonPanel.add(bNewGroup);
        buttonPanel.add(bEditGroup);
        buttonPanel.add(bRemoveGroup);

        FormLayout layout = new FormLayout(
                "170dlu, 4dlu, pref:grow",
                "fill:pref:grow"
        );

        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());
        CellConstraints cc = new CellConstraints();

        builder.add(liGroups, cc.xy(1,1));
//        builder.add(liCustomers, cc.xy(3,1));
        builder.add(customerSelectorView.buildPanel(), cc.xy(3,1));

        initEventHandling();

        return panel;
    }
}
