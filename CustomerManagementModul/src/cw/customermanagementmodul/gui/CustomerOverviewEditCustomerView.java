package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author CreativeWorkers.at
 */
public class CustomerOverviewEditCustomerView
    implements Disposable {

    private CustomerOverviewEditCustomerPresentationModel model;

    private JViewPanel mainPanel;

    public CustomerOverviewEditCustomerView(CustomerOverviewEditCustomerPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {

    }

    private void initEvents() {
    }

    public JPanel buildPanel() {
        initComponents();
        
        mainPanel = CWComponentFactory.createViewPanel(model.getHeaderInfo());
        
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

        PanelBuilder builder = new PanelBuilder(layout,mainPanel.getContentPanel());
        CellConstraints cc = new CellConstraints();

        for(int i=0, l=extentionComponents.size(); i<l; i++) {
            builder.add(extentionComponents.get(i), cc.xy(l, i*2+1));
        }

        initEvents();

        mainPanel.addDisposableListener(this);

        return mainPanel;
    }

    public void dispose() {
        mainPanel.removeDisposableListener(this);

        model.dispose();
    }
}
