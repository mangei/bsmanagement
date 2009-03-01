package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class CustomerHomeExtentionView
    implements Disposable
{

    private CustomerHomeExtentionPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel panel;
    private JLabel lSizeCustomers;

    public CustomerHomeExtentionView(CustomerHomeExtentionPresentationModel model) {
        this.model = model;
    }
    
    private void initComponents() {
        lSizeCustomers = CWComponentFactory.createLabel(model.getSizeCustomersValueModel());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(lSizeCustomers);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initComponents();

        panel  = new JViewPanel("Kundeninformationen");

        FormLayout layout = new FormLayout(
                "pref",
                "pref"
        );
        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());

        CellConstraints cc = new CellConstraints();
        builder.add(lSizeCustomers, cc.xy(1, 1));

        panel.addDisposableListener(this);

        initEventHandling();

        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);

        componentContainer.dispose();

        model.dispose();
    }

}