package cw.customermanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWList;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author ManuelG
 */
public class GroupManagementView extends CWView
{

    private GroupManagementPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWButton bNewGroup;
    private CWButton bEditGroup;
    private CWButton bRemoveGroup;
    private CWList liGroups;
    private CustomerSelectorView customerSelectorView;

    public GroupManagementView(GroupManagementPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        bNewGroup =     CWComponentFactory.createButton(model.getNewGroupAction());
        bEditGroup =    CWComponentFactory.createButton(model.getEditGroupAction());
        bRemoveGroup =  CWComponentFactory.createButton(model.getRemoveGroupAction());
        liGroups =      CWComponentFactory.createList(model.getGroupSelection(), "Keine Gruppen vorhanden");

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(bNewGroup)
                .addComponent(bEditGroup)
                .addComponent(bRemoveGroup);

        customerSelectorView = new CustomerSelectorView(model.getCustomerSelectorPresentationModel());
    }

    private void initEventHandling() {
//        liGroups.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            public void valueChanged(ListSelectionEvent e) {
//                if(liGroups.getSelectionModel().isSelectionEmpty()) {
//                    liCustomers.setEmptyText("Bitte Gruppe auswaehlen");
//                } else if(liCustomers.getModel().getSize() == 0) {
//                    liCustomers.setEmptyText("Keine Kunden in dieser Gruppe vorhanden");
//                }
//            }
//        });
    }

    public void buildView() {

        this.setHeaderInfo(model.getHeaderInfo());

        CWButtonPanel buttonPanel = getButtonPanel();
        buttonPanel.add(bNewGroup);
        buttonPanel.add(bEditGroup);
        buttonPanel.add(bRemoveGroup);

        FormLayout layout = new FormLayout(
                "170dlu, 4dlu, pref:grow",
                "fill:pref:grow"
        );

        PanelBuilder builder = new PanelBuilder(layout, getContentPanel());
        CellConstraints cc = new CellConstraints();

        builder.add(liGroups, cc.xy(1,1));
//        builder.add(liCustomers, cc.xy(3,1));
        builder.add(customerSelectorView, cc.xy(3,1));
    }

    @Override
    public void dispose() {

        customerSelectorView.dispose();

        componentContainer.dispose();

        model.dispose();
    }
}
