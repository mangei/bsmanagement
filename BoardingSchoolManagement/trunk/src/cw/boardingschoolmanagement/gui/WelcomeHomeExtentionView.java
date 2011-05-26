package cw.boardingschoolmanagement.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author ManuelG
 */
public class WelcomeHomeExtentionView extends CWView
{

    private WelcomeHomeExtentionPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWLabel lWelcomeMessage;
    private CWLabel lTimeMessage;

    public WelcomeHomeExtentionView(WelcomeHomeExtentionPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }
    
    private void initComponents() {
        lWelcomeMessage = CWComponentFactory.createLabel(model.getWelcomeMessageValueModel());
        lTimeMessage = CWComponentFactory.createLabel(model.getTimeMessageValueModel());

        componentContainer = CWComponentFactory.createComponentContainer()
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

    @Override
    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}