package cw.customermanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.app.CWUtils;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author CreativeWorkers.at
 */
public class SearchCustomerSelectorFilterExtentionView {

    private SearchCustomerSelectorFilterExtentionPresentationModel model;

    private JTextField tfSearch;
    private JButton bClear;

    public SearchCustomerSelectorFilterExtentionView(SearchCustomerSelectorFilterExtentionPresentationModel m) {
        model = m;
    }

    private void initComponents() {
        tfSearch = CWComponentFactory.createTextField(model.getSearchModel());
        bClear = CWComponentFactory.createButton(model.getClearAction());
        bClear.setIcon(CWUtils.loadIcon("cw/customermanagementmodul/images/clear.png"));
        bClear.setRolloverIcon(CWUtils.loadIcon("cw/customermanagementmodul/images/clear_hover.png"));
        bClear.setContentAreaFilled(false);
        bClear.setFocusPainted(false);
        bClear.setBorderPainted(false);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initComponents();

        JPanel panel = CWComponentFactory.createPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        FormLayout layout = new FormLayout(
                "pref, 4dlu, fill:pref:grow, 4dlu, pref",
                "pref");
        CellConstraints cc = new CellConstraints();

        PanelBuilder builder = new PanelBuilder(layout, panel);

        builder.addLabel("Suche:", cc.xy(1, 1));
        builder.add(tfSearch, cc.xy(3, 1));
        builder.add(bClear, cc.xy(5, 1));

        initEventHandling();
        
        return panel;
    }

}
