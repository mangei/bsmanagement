package cw.customermanagementmodul.group.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWList;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.customer.gui.CustomerSelectorView;

/**
 *
 * @author Manuel Geier
 */
public class GroupManagementView
	extends CWView<GroupManagementPresentationModel>
{

    private CWButton bNewGroup;
    private CWButton bEditGroup;
    private CWButton bRemoveGroup;
    private CWList liGroups;
    private CustomerSelectorView customerSelectorView;

    public GroupManagementView(GroupManagementPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        bNewGroup =     CWComponentFactory.createButton(getModel().getNewGroupAction());
        bEditGroup =    CWComponentFactory.createButton(getModel().getEditGroupAction());
        bRemoveGroup =  CWComponentFactory.createButton(getModel().getRemoveGroupAction());
        liGroups =      CWComponentFactory.createList(getModel().getGroupSelection(), "Keine Gruppen vorhanden");
        
        customerSelectorView = new CustomerSelectorView(getModel().getCustomerSelectorPresentationModel());
        customerSelectorView.initComponents();
        
        getComponentContainer()
                .addComponent(bNewGroup)
                .addComponent(bEditGroup)
                .addComponent(bRemoveGroup)
                .addComponent(customerSelectorView);
       
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
    	super.buildView();
    	
    	customerSelectorView.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());

        CWButtonPanel buttonPanel = getButtonPanel();
        buttonPanel.add(bNewGroup);
        buttonPanel.add(bEditGroup);
        buttonPanel.add(bRemoveGroup);

        FormLayout layout = new FormLayout(
                "170dlu, 4dlu, pref:grow",
                "fill:pref:grow"
        );

        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.add(liGroups, cc.xy(1,1));
//        builder.add(liCustomers, cc.xy(3,1));
        builder.add(customerSelectorView, cc.xy(3,1));
        
        addToContentPanel(builder.getPanel(), true);
    }

    @Override
    public void dispose() {
    	super.dispose();
    }
}
