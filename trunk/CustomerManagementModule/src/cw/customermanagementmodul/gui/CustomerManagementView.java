package cw.customermanagementmodul.gui;

import java.awt.BorderLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 * @author CreativeWorkers.at
 */
public class CustomerManagementView extends CWView
{

    private CustomerManagementPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWButton bNew;
    private CWButton bEdit;
    private CWButton bDelete;
    private CWButton bInactive;
    private CWButton bViewInactives;
    private CWButton bPrint;
    private CustomerSelectorView customerSelectorView;

    public CustomerManagementView(CustomerManagementPresentationModel m) {
        model = m;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        bNew        = CWComponentFactory.createButton(model.getNewAction());
        bEdit       = CWComponentFactory.createButton(model.getEditAction());
        bDelete     = CWComponentFactory.createButton(model.getDeleteAction());
        bInactive   = CWComponentFactory.createButton(model.getInactiveAction());
        bViewInactives   = CWComponentFactory.createButton(model.getViewInactivesAction());
        bPrint = CWComponentFactory.createButton(model.getPrintAction());

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(bNew)
                .addComponent(bEdit)
                .addComponent(bDelete)
                .addComponent(bInactive)
                .addComponent(bViewInactives)
                .addComponent(bPrint);

        customerSelectorView = new CustomerSelectorView(model.getCustomerSelectorPresentationModel());

        bNew.setToolTipText(CWComponentFactory.createToolTip(
                "Neu",
                "Hier können Sie einen neuen Kunden hinzufuegen.",
                "cw/customermanagementmodul/images/user_add.png"));
        bEdit.setToolTipText(CWComponentFactory.createToolTip(
                "Bearbeiten",
                "Bearbeiten Sie einen vorhanden Kunden.",
                "cw/customermanagementmodul/images/user_edit.png"));
        bDelete.setToolTipText(CWComponentFactory.createToolTip(
                "Löschen",
                "Löschen Sie einen vorhandenen Kunden.",
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
        this.setHeaderInfo(model.getHeaderInfo());

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

        this.getContentPanel().setLayout(new BorderLayout());
        this.getContentPanel().add(customerSelectorView, BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        customerSelectorView.dispose();
        
        componentContainer.dispose();

        model.dispose();
    }

}
