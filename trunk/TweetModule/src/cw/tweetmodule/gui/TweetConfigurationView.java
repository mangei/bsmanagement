package cw.tweetmodule.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWCheckBox;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWPasswordField;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Manuel Geier
 */
public class TweetConfigurationView
	extends CWView<TweetConfigurationPresentationModel>
{

	private CWCheckBox cbActive;
    private CWTextField tfUsername;
    private CWPasswordField pfPassword;
    private ItemListener cbActiveItemListener;

    public TweetConfigurationView(TweetConfigurationPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	cbActive = CWComponentFactory.createCheckBox(getModel().getActiveModel(), "Tweets aktivieren");
        tfUsername = CWComponentFactory.createTextField(getModel().getUsernameModel());
        pfPassword = CWComponentFactory.createPasswordField(getModel().getPasswordModel());

        getComponentContainer()
        		.addComponent(cbActive)
                .addComponent(tfUsername)
                .addComponent(pfPassword);
        
        initEventhandling();
    }
    
    private void initEventhandling() {
    	cbActive.addItemListener(cbActiveItemListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
		    	updateFields();
			}
    	});
    	updateFields();
    }
    
    private void updateFields() {
    	tfUsername.setEnabled(cbActive.isSelected());
    	pfPassword.setEnabled(cbActive.isSelected());
    }

    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, pref:grow",
                "pref, 4dlu, pref, 4dlu, pref"
        );
        CellConstraints cc = new CellConstraints();
        PanelBuilder builder = new PanelBuilder(layout);
        builder.add(cbActive,     			  cc.xyw(1, 1, 3));
        builder.addLabel("Benutzername:",     cc.xy(1, 3));
        builder.add(tfUsername,               cc.xy(3, 3));
        builder.addLabel("Passwort:",         cc.xy(1, 5));
        builder.add(pfPassword,               cc.xy(3, 5));
        
        addToContentPanel(builder.getPanel());
    }

    @Override
    public void dispose() {
    	cbActive.removeItemListener(cbActiveItemListener);
        super.dispose();
    }
}
