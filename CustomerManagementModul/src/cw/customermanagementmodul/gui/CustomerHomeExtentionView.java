package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class CustomerHomeExtentionView {

    private CustomerHomeExtentionPresentationModel model;

    private JLabel lSizeCustomers;

    public CustomerHomeExtentionView(CustomerHomeExtentionPresentationModel model) {
        this.model = model;
    }
    
    private void initComponents() {
        lSizeCustomers = CWComponentFactory.createLabel(model.getSizeCustomersValueModel());
    }

    private void initEventHandling() {

    }

    public JPanel buildPanel() {
        initComponents();

        JViewPanel panel  = new JViewPanel("Kundeninformationen");

        FormLayout layout = new FormLayout(
                "pref",
                "pref"
        );
        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());

        CellConstraints cc = new CellConstraints();
        builder.add(lSizeCustomers, cc.xy(1, 1));

        initEventHandling();

        return panel;
    }

}