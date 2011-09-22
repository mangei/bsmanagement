package cw.tweetmodule.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.validation.ValidationResult;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.boardingschoolmanagement.gui.ConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.PropertiesManager;
import cw.tweetmodule.images.ImageDefinitionTweet;

/**
 *
 * @author ManuelG
 * @version 1.0
 *
 */
public class TweetConfigurationPresentationModel
	extends CWEditPresentationModel
{

    private ConfigurationPresentationModel configurationPresentationModel;
    private CWHeaderInfo headerInfo;
    private SaveListener saveListener;

    private ValueModel activeModel;
    private ValueModel usernameModel;
    private ValueModel passwordModel;

    /**
     *Konstruktor
     *
     * @param businessData POJO
     * @param configurationPresentationModel
     */
    public TweetConfigurationPresentationModel(ConfigurationPresentationModel configurationPresentationModel) {
    	super(null, null);
        this.configurationPresentationModel = configurationPresentationModel;
        
        initModels();
        initEventHandling();
    }

    private void initModels() {
        headerInfo = new CWHeaderInfo(
                "Tweet",
                "Aendern Sie hier ihre Verbindungsdaten.",
                CWUtils.loadIcon(ImageDefinitionTweet.TWITTER),
                CWUtils.loadIcon(ImageDefinitionTweet.TWITTER)
        );

        activeModel = new ValueHolder(Boolean.parseBoolean(PropertiesManager.getProperty("tweetmodule.active","false")));
        usernameModel = new ValueHolder(PropertiesManager.getProperty("tweetmodule.username",""));
        passwordModel = new ValueHolder(PropertiesManager.getProperty("tweetmodule.password",""));
    }

    private void initEventHandling() {
        saveListener = new SaveListener();

        activeModel.addValueChangeListener(saveListener);
        usernameModel.addValueChangeListener(saveListener);
        passwordModel.addValueChangeListener(saveListener);
    }

    public void dispose() {
        activeModel.removeValueChangeListener(saveListener);
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

    public ValidationResult validate() {
    	ValidationResult validationResult = new ValidationResult();
    	
        return validationResult;
    }

    public void save() {
    	super.save();
    	PropertiesManager.setProperty("tweetmodule.active", (String) activeModel.getValue().toString());
        PropertiesManager.setProperty("tweetmodule.username", (String) usernameModel.getValue());
        PropertiesManager.setProperty("tweetmodule.password", (String) passwordModel.getValue());
        PropertiesManager.saveProperties();
    }

    public ValueModel getPasswordModel() {
        return passwordModel;
    }

    public ValueModel getUsernameModel() {
        return usernameModel;
    }
    
    public ValueModel getActiveModel() {
		return activeModel;
	}

}
