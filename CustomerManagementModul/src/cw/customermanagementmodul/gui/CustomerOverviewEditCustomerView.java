package cw.customermanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWView;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author CreativeWorkers.at
 */
public class CustomerOverviewEditCustomerView extends CWView
{

    private CustomerOverviewEditCustomerPresentationModel model;

    public CustomerOverviewEditCustomerView(CustomerOverviewEditCustomerPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {

    }

    private void initEventHandling() {
    }

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
        
        List<JComponent> extentionComponents = model.getExtentionComponents();

        StringBuilder rowLayoutString = new StringBuilder();
        for(int i=0, l=extentionComponents.size(); i<l; i++) {
            if(i != 0) {
                rowLayoutString.append(", ");
            }
            rowLayoutString.append("pref, 4dlu");
        }

        FormLayout layout = new FormLayout(
                "fill:pref:grow",
                rowLayoutString.toString());

        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        CellConstraints cc = new CellConstraints();

        for(int i=0, l=extentionComponents.size(); i<l; i++) {
            builder.add(extentionComponents.get(i), cc.xy(1, i*2+1));
        }
    }

    @Override
    public void dispose() {
        model.dispose();

        // Kill references
        model = null;
    }
}
