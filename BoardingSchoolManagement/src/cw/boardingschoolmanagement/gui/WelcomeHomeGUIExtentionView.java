package cw.boardingschoolmanagement.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import java.awt.event.ComponentAdapter;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class WelcomeHomeGUIExtentionView {

    private WelcomeHomeGUIExtentionPresentationModel model;

    private JLabel lWelcomeMessage;
    private JLabel lTimeMessage;

    public WelcomeHomeGUIExtentionView(WelcomeHomeGUIExtentionPresentationModel model) {
        this.model = model;
    }
    
    private void initComponents() {
        lWelcomeMessage = CWComponentFactory.createLabel(model.getWelcomeMessageValueModel());
        lTimeMessage = CWComponentFactory.createLabel(model.getTimeMessageValueModel());
    }

    private void initEventHandling() {

    }

    public JPanel buildPanel() {
        initComponents();

        JViewPanel panel  = new JViewPanel("Willkommen") {

            @Override
            protected void finalize() throws Throwable {


                super.finalize();
            }
        };

        FormLayout layout = new FormLayout(
                "fill:pref:grow",
                "pref, 4dlu, pref"
        );
        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());

        CellConstraints cc = new CellConstraints();
        builder.add(lWelcomeMessage, cc.xy(1, 1));
        builder.add(lTimeMessage, cc.xy(1, 3));

        initEventHandling();

        return panel;
    }

}