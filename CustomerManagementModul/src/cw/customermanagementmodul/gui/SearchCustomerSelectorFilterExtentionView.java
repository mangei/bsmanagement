package cw.customermanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWJPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author CreativeWorkers.at
 */
public class SearchCustomerSelectorFilterExtentionView
    implements Disposable
{

    private SearchCustomerSelectorFilterExtentionPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWJPanel panel;
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

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(tfSearch)
                .addComponent(bClear);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initComponents();

        panel = CWComponentFactory.createPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        FormLayout layout = new FormLayout(
                "pref, 4dlu, fill:pref:grow, 4dlu, pref",
                "pref");
        CellConstraints cc = new CellConstraints();

        PanelBuilder builder = new PanelBuilder(layout, panel);

        builder.addLabel("Suche:", cc.xy(1, 1));
        builder.add(tfSearch, cc.xy(3, 1));
        builder.add(bClear, cc.xy(5, 1));

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
