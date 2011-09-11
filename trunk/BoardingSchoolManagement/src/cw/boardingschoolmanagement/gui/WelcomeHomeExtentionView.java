package cw.boardingschoolmanagement.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Manuel Geier
 */
public class WelcomeHomeExtentionView
	extends CWView<WelcomeHomeExtentionPresentationModel>
{
    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWLabel lWelcomeMessage;
    private CWLabel lTimeMessage;

    public WelcomeHomeExtentionView(WelcomeHomeExtentionPresentationModel model) {
        super(model);

        initComponents();
        buildView();
        initEventHandling();
    }
    
    private void initComponents() {
        lWelcomeMessage = CWComponentFactory.createLabel(getModel().getWelcomeMessageValueModel());
        lTimeMessage = CWComponentFactory.createLabel(getModel().getTimeMessageValueModel());

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
        PanelBuilder builder = new PanelBuilder(layout);

        CellConstraints cc = new CellConstraints();
        builder.add(lWelcomeMessage, cc.xy(1, 1));
        builder.add(lTimeMessage, cc.xy(1, 3));
        
        addToContentPanel(builder.getPanel());
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        getModel().dispose();
    }
}