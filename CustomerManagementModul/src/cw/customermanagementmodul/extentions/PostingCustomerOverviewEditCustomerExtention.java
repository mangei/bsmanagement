package cw.customermanagementmodul.extentions;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWJLabel;
import cw.boardingschoolmanagement.gui.component.CWJPanel;
import cw.customermanagementmodul.extentions.interfaces.CustomerOverviewEditCustomerExtention;
import cw.customermanagementmodul.gui.CustomerOverviewEditCustomerPresentationModel;
import cw.customermanagementmodul.gui.PostingManagementEditCustomerPresentationModel;
import java.text.NumberFormat;
import javax.swing.JComponent;

/**
 *
 * @author Manuel Geier
 */
public class PostingCustomerOverviewEditCustomerExtention
        implements CustomerOverviewEditCustomerExtention {

    private CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel;

    private CWJLabel lSaldo;
    private CWJLabel lAssets;
    private CWJLabel lLiabilities;

    public void initPresentationModel(CustomerOverviewEditCustomerPresentationModel customerOverviewEditCustomerPresentationModel) {
        this.customerOverviewEditCustomerPresentationModel = customerOverviewEditCustomerPresentationModel;
    }
    
    public JComponent getView() {
        CWJPanel panel = CWComponentFactory.createPanel();

        lSaldo = CWComponentFactory.createLabel(
                ((PostingManagementEditCustomerPresentationModel)
                    ((PostingEditCustomerTabExtention)customerOverviewEditCustomerPresentationModel
                        .getEditCustomerPresentationModel()
                        .getExtention(PostingEditCustomerTabExtention.class))
                        .getModel()).getTotalSaldoValue(),
                        NumberFormat.getCurrencyInstance()
                    );
        lAssets = CWComponentFactory.createLabel(
                ((PostingManagementEditCustomerPresentationModel)
                    ((PostingEditCustomerTabExtention)customerOverviewEditCustomerPresentationModel
                        .getEditCustomerPresentationModel()
                        .getExtention(PostingEditCustomerTabExtention.class))
                        .getModel()).getTotalAssetsValue(),
                        NumberFormat.getCurrencyInstance()
                    );
        lLiabilities = CWComponentFactory.createLabel(
                ((PostingManagementEditCustomerPresentationModel)
                    ((PostingEditCustomerTabExtention)customerOverviewEditCustomerPresentationModel
                        .getEditCustomerPresentationModel()
                        .getExtention(PostingEditCustomerTabExtention.class))
                        .getModel()).getTotalLiabilitiesValue(),
                        NumberFormat.getCurrencyInstance()
                    );

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
        
    }

    public int priority() {
        return 0;
    }

}
