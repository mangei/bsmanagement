package cw.tweetmodule.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author ManuelG
 */
public class TweetConfigurationView extends CWView
{

    private TweetConfigurationPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private JTextField tfUsername;
    private JPasswordField pfPassword;

    public TweetConfigurationView(TweetConfigurationPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    public void initComponents() {
        tfUsername = CWComponentFactory.createTextField(model.getUsernameModel());
        pfPassword = CWComponentFactory.createPasswordField(model.getPasswordModel());

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(tfUsername)
                .addComponent(pfPassword);
    }

    public void initEventHandling() {
        
    }

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, pref:grow",
                "pref, 4dlu, pref"
        );
        CellConstraints cc = new CellConstraints();
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        builder.addLabel("Benutzername:",     cc.xy(1, 1));
        builder.add(tfUsername,               cc.xy(3, 1));
        builder.addLabel("Passwort:",         cc.xy(1, 3));
        builder.add(pfPassword,               cc.xy(3, 3));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}
