package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import java.awt.BorderLayout;
import javax.swing.JButton;

/**
 * @author CreativeWorkers.at
 */
public class PostingManagementView extends CWView
    
{

    private PostingManagementPresentationModel model;
    private JButton bNew;
    private JButton bEdit;
    private JButton bDelete;
    private JButton bInactive;
    private JButton bViewInactives;
    private CustomerSelectorView customerSelectorView;

    public PostingManagementView(PostingManagementPresentationModel m) {
        model = m;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
//        bNew        = CWComponentFactory.createButton(model.getNewAction());
//        bEdit       = CWComponentFactory.createButton(model.getEditAction());
//        bDelete     = CWComponentFactory.createButton(model.getDeleteAction());
//        bInactive   = CWComponentFactory.createButton(model.getInactiveAction());
//        bViewInactives   = CWComponentFactory.createButton(model.getViewInactivesAction());
//
//        customerSelectorView = new CustomerSelectorView(model.getCustomerSelectorPresentationModel());
//
//        bNew.setToolTipText(CWComponentFactory.createToolTip(
//                "Neu",
//                "Hier können Sie einen neuen Kunden hinzufügen.",
//                "cw/customermanagementmodul/images/user_add.png"));
//        bEdit.setToolTipText(CWComponentFactory.createToolTip(
//                "Bearbeiten",
//                "Bearbeiten Sie einen vorhanden Kunden.",
//                "cw/customermanagementmodul/images/user_edit.png"));
//        bDelete.setToolTipText(CWComponentFactory.createToolTip(
//                "Löschen",
//                "Löschen Sie einen vorhandenen Kunden.",
//                "cw/customermanagementmodul/images/user_delete.png"));
//        bInactive.setToolTipText(CWComponentFactory.createToolTip(
//                "Inaktiv setzen",
//                "Setzen Sie einen vorhanden Kunden inaktiv.",
//                "cw/customermanagementmodul/images/user_inactive_go.png"));
//        bViewInactives.setToolTipText(CWComponentFactory.createToolTip(
//                "Inaktive anzeigen",
//                "Zeigen Sie alle inaktiven Kunden ein.",
//                "cw/customermanagementmodul/images/user_inactives.png"));
    }

    private void initEventHandling() {
        // Nothing to do
    }

   private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());

//        panel.getButtonPanel().add(bNew);
//        panel.getButtonPanel().add(bEdit);
//        panel.getButtonPanel().add(bDelete);
//        panel.getButtonPanel().add(bInactive);
//        panel.getButtonPanel().add(bViewInactives);

        this.getContentPanel().setLayout(new BorderLayout());
        this.getContentPanel().add(CWComponentFactory.createLabel("In Arbeit... :D"), BorderLayout.CENTER);
    }

}
