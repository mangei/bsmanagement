package cw.boardingschoolmanagement.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Manuel Geier
 */
public class WelcomeHomeExtentionView
	extends CWView<WelcomeHomeExtentionPresentationModel>
{
    private CWLabel lWelcomeMessage;
    private CWLabel lTimeMessage;
    private HomeView baseView;

    public WelcomeHomeExtentionView(WelcomeHomeExtentionPresentationModel model, HomeView baseView) {
        super(model);
        
        this.baseView = baseView;
    }
    
    public void initComponents() {
    	super.initComponents();
    	
        lWelcomeMessage = CWComponentFactory.createLabel(getModel().getWelcomeMessageValueModel());
        lTimeMessage = CWComponentFactory.createLabel(getModel().getTimeMessageValueModel());

        getComponentContainer()
                .addComponent(lWelcomeMessage)
                .addComponent(lTimeMessage);
    }
    
    public void buildView() {
    	super.buildView();
    	
//        this.setHeaderInfo(new CWHeaderInfo(
//                "Willkommen"
//        ));

        FormLayout layout = new FormLayout(
                "fill:pref:grow",
                "pref, 4dlu, pref"
        );
        PanelBuilder builder = new PanelBuilder(layout);

        CellConstraints cc = new CellConstraints();
        builder.add(lWelcomeMessage, cc.xy(1, 1));
        builder.add(lTimeMessage, cc.xy(1, 3));

        this.addToContentPanel(builder.getPanel());
        
        baseView.addToContentPanel(this);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}