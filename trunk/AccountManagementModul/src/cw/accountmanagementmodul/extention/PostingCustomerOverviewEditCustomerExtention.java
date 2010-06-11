package cw.accountmanagementmodul.extention;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.accountmanagementmodul.gui.PostingManagementAccountManagementPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.extention.point.CustomerOverviewEditCustomerExtentionPoint;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerPresentationModel;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import java.text.NumberFormat;

/**
 *
 * @author Manuel Geier
 */
public class PostingCustomerOverviewEditCustomerExtention
        implements CustomerOverviewEditCustomerExtentionPoint {

    private CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel;
    private CWComponentFactory.CWComponentContainer componentContainer;

    private CWLabel lSaldo;

    public void initPresentationModel(CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel) {
        this.customerOverviewEditCustomerPresentationModel = customerOverviewEditCustomerPresentationModel;
        componentContainer = CWComponentFactory.createComponentContainer();

    }
    
    public CWPanel getView() {
        CWPanel panel = CWComponentFactory.createPanel();

        EditCustomerPresentationModel editCustomerPresentationModel =
                customerOverviewEditCustomerPresentationModel
                    .getEditCustomerPresentationModel();

        PostingEditCustomerTabExtention postingExtention =
                (PostingEditCustomerTabExtention)
                    editCustomerPresentationModel
                        .getExtention(PostingEditCustomerTabExtention.class);

        PostingManagementAccountManagementPresentationModel postingManagementModel =
                (PostingManagementAccountManagementPresentationModel)
                    postingExtention
                        .getModel();

        System.out.println("postingManagementModel: " + postingManagementModel);

        lSaldo = CWComponentFactory.createLabel(
                postingManagementModel.getTotalSaldoValue(),
                        NumberFormat.getCurrencyInstance()
                    );

        componentContainer
                .addComponent(lSaldo);

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, pref:grow",
                "pref, 4dlu, pref"
        );

        PanelBuilder builder = new PanelBuilder(layout,panel);
        CellConstraints cc = new CellConstraints();

        builder.addSeparator("Buchungen",                   cc.xyw(1, 1, 3));
        builder.addLabel("<html><b>Saldo:</b></html>",      cc.xy(1, 3));
        builder.add(lSaldo,                                 cc.xy(3, 3));

        return panel;
    }

    public void dispose() {
        componentContainer.dispose();
    }

    public int priority() {
        return 0;
    }

}
