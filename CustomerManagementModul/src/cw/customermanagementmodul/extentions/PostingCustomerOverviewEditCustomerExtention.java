package cw.customermanagementmodul.extentions;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.extentions.interfaces.CustomerOverviewEditCustomerExtention;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerPresentationModel;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.gui.PostingManagementEditCustomerPresentationModel;
import java.text.NumberFormat;

/**
 *
 * @author Manuel Geier
 */
public class PostingCustomerOverviewEditCustomerExtention
        implements CustomerOverviewEditCustomerExtention {

    private CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel;
    private CWComponentFactory.CWComponentContainer componentContainer;

    private CWLabel lSaldo;
    private CWLabel lAssets;
    private CWLabel lLiabilities;

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

        PostingManagementEditCustomerPresentationModel postingManagementModel =
                (PostingManagementEditCustomerPresentationModel) 
                    postingExtention
                        .getModel();

        System.out.println("postingManagementModel: " + postingManagementModel);

        lSaldo = CWComponentFactory.createLabel(
                postingManagementModel.getTotalSaldoValue(),
                        NumberFormat.getCurrencyInstance()
                    );
        lAssets = CWComponentFactory.createLabel(
                postingManagementModel.getTotalAssetsValue(),
                        NumberFormat.getCurrencyInstance()
                    );
        lLiabilities = CWComponentFactory.createLabel(
                postingManagementModel.getTotalLiabilitiesValue(),
                        NumberFormat.getCurrencyInstance()
                    );

        componentContainer
                .addComponent(lSaldo)
                .addComponent(lAssets)
                .addComponent(lLiabilities);

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, pref, 4dlu, right:pref, 4dlu, pref, 4dlu, right:pref, 4dlu, pref:grow",
                "pref, 4dlu, pref"
        );

        PanelBuilder builder = new PanelBuilder(layout,panel);
        CellConstraints cc = new CellConstraints();

        builder.addSeparator("Buchungen",   cc.xyw(1, 1, 11));
        builder.addLabel("<html><b>Soll:</b></html>",            cc.xy(1, 3));
        builder.add(lLiabilities,           cc.xy(3, 3));
        builder.addLabel("<html><b>Haben:</b></html>",           cc.xy(5, 3));
        builder.add(lAssets,                cc.xy(7, 3));
        builder.addLabel("<html><b>Saldo:</b></html>",           cc.xy(9, 3));
        builder.add(lSaldo,                 cc.xy(11, 3));

        return panel;
    }

    public void dispose() {
        componentContainer.dispose();
    }

    public int priority() {
        return 0;
    }

}
