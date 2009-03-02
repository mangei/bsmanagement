package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author CreativeWorkers.at
 */
public class CustomerManagementView
    implements Disposable
{

    private CustomerManagementPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JButton bNew;
    private JButton bEdit;
    private JButton bDelete;
    private JButton bInactive;
    private JButton bViewInactives;
    private JViewPanel panel;
    private CustomerSelectorView customerSelectorView;

    public CustomerManagementView(CustomerManagementPresentationModel m) {
        model = m;
    }

    private void initComponents() {
        bNew        = CWComponentFactory.createButton(model.getNewAction());
        bEdit       = CWComponentFactory.createButton(model.getEditAction());
        bDelete     = CWComponentFactory.createButton(model.getDeleteAction());
        bInactive   = CWComponentFactory.createButton(model.getInactiveAction());
        bViewInactives   = CWComponentFactory.createButton(model.getViewInactivesAction());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(bNew)
                .addComponent(bEdit)
                .addComponent(bDelete)
                .addComponent(bInactive)
                .addComponent(bViewInactives);

        customerSelectorView = new CustomerSelectorView(model.getCustomerSelectorPresentationModel());

        bNew.setToolTipText(CWComponentFactory.createToolTip(
                "Neu",
                "Hier können Sie einen neuen Kunden hinzufügen.",
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

    public JPanel buildPanel() {
        initComponents();

        panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());

        panel.getButtonPanel().add(bNew);
        panel.getButtonPanel().add(bEdit);
        panel.getButtonPanel().add(bDelete);
        panel.getButtonPanel().add(bInactive);
        panel.getButtonPanel().add(bViewInactives);

        panel.getContentPanel().setLayout(new BorderLayout());
        panel.getContentPanel().add(customerSelectorView.buildPanel(), BorderLayout.CENTER);

        panel.addDisposableListener(this);

        initEventHandling();

        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);

        customerSelectorView.dispose();
        
        componentContainer.dispose();

        model.dispose();
    }

}
