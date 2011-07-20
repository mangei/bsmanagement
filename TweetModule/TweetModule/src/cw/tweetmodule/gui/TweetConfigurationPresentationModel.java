package cw.tweetmodule.gui;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.ConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.PropertiesManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 *
 * @author ManuelG
 * @version 1.0
 *
 */
public class TweetConfigurationPresentationModel
{

    private ConfigurationPresentationModel configurationPresentationModel;
    private CWHeaderInfo headerInfo;
    private SaveListener saveListener;

    private ValueModel usernameModel;
    private ValueModel passwordModel;

    /**
     *Konstruktor
     *
     * @param businessData POJO
     * @param configurationPresentationModel
     */

    public TweetConfigurationPresentationModel(ConfigurationPresentationModel configurationPresentationModel) {
        this.configurationPresentationModel = configurationPresentationModel;
        
        initModels();
        initEventHandling();
    }

    private void initModels() {
        headerInfo = new CWHeaderInfo(
                "Tweet",
                "Ändern Sie hier ihre Verbindungsdaten.",
                CWUtils.loadIcon("cw/tweetmodule/images/twitter11.png"),
                CWUtils.loadIcon("cw/tweetmodule/images/twitter11.png")
        );

        usernameModel = new ValueHolder(PropertiesManager.getProperty("tweetmodule.username",""));
        passwordModel = new ValueHolder(PropertiesManager.getProperty("tweetmodule.password",""));
    }

    private void initEventHandling() {
        saveListener = new SaveListener();
        
        usernameModel.addValueChangeListener(saveListener);
        passwordModel.addValueChangeListener(saveListener);
    }

    public void dispose() {
        usernameModel.removeValueChangeListener(saveListener);
        passwordModel.removeValueChangeListener(saveListener);
    }

    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            configurationPresentationModel.setChanged(true);
        }
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public List<String> validate() {
        return null;
    }

    public void save() {
        PropertiesManager.setProperty("tweetmodule.username", (String) usernameModel.getValue());
        PropertiesManager.setProperty("tweetmodule.password", (String) passwordModel.getValue());
    }

    public ValueModel getPasswordModel() {
        return passwordModel;
    }

    public ValueModel getUsernameModel() {
        return usernameModel;
    }

}
