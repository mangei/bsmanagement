package cw.boardingschoolmanagement.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWView;
import javax.swing.JLabel;

/**
 *
 * @author ManuelG
 */
public class WelcomeHomeGUIExtentionView extends CWView
{

    private WelcomeHomeGUIExtentionPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JLabel lWelcomeMessage;
    private JLabel lTimeMessage;

    public WelcomeHomeGUIExtentionView(WelcomeHomeGUIExtentionPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }
    
    private void initComponents() {
        lWelcomeMessage = CWComponentFactory.createLabel(model.getWelcomeMessageValueModel());
        lTimeMessage = CWComponentFactory.createLabel(model.getTimeMessageValueModel());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(lWelcomeMessage)
                .addComponent(lTimeMessage);
    }

    private void initEventHandling() {

    }

    private void buildView() {
        this.setHeaderInfo(new CWHeaderInfo(
                "Willkommen"
        ));

        FormLayout layout = new FormLayout(
                "fill:pref:grow",
                "pref, 4dlu, pref"
        );
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());

        CellConstraints cc = new CellConstraints();
        builder.add(lWelcomeMessage, cc.xy(1, 1));
        builder.add(lTimeMessage, cc.xy(1, 3));
    }

    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}