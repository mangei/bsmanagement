package cw.customermanagementmodul.gui;

import java.awt.BorderLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 * @author CreativeWorkers.at
 */
public class CustomerManagementView
	extends CWView<CustomerManagementPresentationModel>
{

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWButton bNew;
    private CWButton bEdit;
    private CWButton bDelete;
    private CWButton bInactive;
    private CWButton bViewInactives;
    private CWButton bPrint;
    private CustomerSelectorView customerSelectorView;

    public CustomerManagementView(CustomerManagementPresentationModel model) {
    	super(model);

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        bNew        = CWComponentFactory.createButton(getModel().getNewAction());
        bEdit       = CWComponentFactory.createButton(getModel().getEditAction());
        bDelete     = CWComponentFactory.createButton(getModel().getDeleteAction());
        bInactive   = CWComponentFactory.createButton(getModel().getInactiveAction());
        bViewInactives   = CWComponentFactory.createButton(getModel().getViewInactivesAction());
        bPrint = CWComponentFactory.createButton(getModel().getPrintAction());

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(bNew)
                .addComponent(bEdit)
                .addComponent(bDelete)
                .addComponent(bInactive)
                .addComponent(bViewInactives)
                .addComponent(bPrint);

        customerSelectorView = new CustomerSelectorView(getModel().getCustomerSelectorPresentationModel());

        bNew.setToolTipText(CWComponentFactory.createToolTip(
                "Neu",
                "Hier koennen Sie einen neuen Kunden hinzufuegen.",
                "cw/customermanagementmodul/images/user_add.png"));
        bEdit.setToolTipText(CWComponentFactory.createToolTip(
                "Bearbeiten",
                "Bearbeiten Sie einen vorhanden Kunden.",
                "cw/customermanagementmodul/images/user_edit.png"));
        bDelete.setToolTipText(CWComponentFactory.createToolTip(
                "Loeschen",
                "Loeschen Sie einen vorhandenen Kunden.",
                "cw/customermanagementmodul/images/user_delete.png"));
        bInactive.setToolTipText(CWComponentFactory.createToolTip(
                "Inaktiv setzen",
                "Setzen Sie einen vorhanden Kunden inaktiv.",
                "cw/customermanagementmodul/images/user_inactive_go.png"));
        bViewInactives.setToolTipText(CWComponentFactory.createToolTip(
                "Inaktive anzeigen",
                "Zeigen Sie alle inaktiven Kunden ein.",
                "cw/customermanagementmodul/images/user_inactives.png"));
    }

    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {
        this.setHeaderInfo(getModel().getHeaderInfo());

        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bDelete);
        this.getButtonPanel().add(bInactive);
        this.getButtonPanel(CWView.ButtonPanelPosition.RIGHT).add(bViewInactives);
        this.getButtonPanel(CWView.ButtonPanelPosition.RIGHT).add(bPrint);

//        panel.getButtonPanel().add(new JButton(new AbstractAction("Show Postings") {
//
//            public void actionPerformed(ActionEvent e) {
//                Customer c = model.getCustomerSelectorPresentationModel().getSelectedCustomer();
//                List<Posting> all = PostingManager.getInstance().getAll(c);
//                Posting p;
//                for(int i=0,l=all.size(); i<l; i++) {
//                    p = all.get(i);
//                    System.out.println(p.getDescription());
//                    while(p.getPreviousPosting() != null) {
//                        p = p.getPreviousPosting();
//                        System.out.println(" -> " + p.getDescription());
//                    }
//                }
//            }
//        }));
        
        addToContentPanel(customerSelectorView, true);
    }

    @Override
    public void dispose() {
        customerSelectorView.dispose();
        
        componentContainer.dispose();

        getModel().dispose();
    }

}
