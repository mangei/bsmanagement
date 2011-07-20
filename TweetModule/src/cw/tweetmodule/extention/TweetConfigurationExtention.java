package cw.tweetmodule.extention;

import cw.boardingschoolmanagement.extention.point.ConfigurationExtentionPoint;
import cw.boardingschoolmanagement.gui.ConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.tweetmodule.gui.TweetConfigurationPresentationModel;
import cw.tweetmodule.gui.TweetConfigurationView;
import java.util.List;
import javax.swing.Icon;

/**
 *  
 *
 *
 * @author ManuelG
 */
public class TweetConfigurationExtention implements ConfigurationExtentionPoint {

    private TweetConfigurationPresentationModel model;
    private TweetConfigurationView view;

    /**
     * Läd die Unternehmensdaten aus der Configdatei und speichert diese in
     * das zuständige POJO(BusinessData).
     *
     * @param configurationModel
     */
    public void initPresentationModel(ConfigurationPresentationModel configurationModel) {
        model = new TweetConfigurationPresentationModel(configurationModel);
        view = new TweetConfigurationView(model);
    }

    public CWPanel getView() {
        return view;
    }

    public Object getModel() {
        return model;
    }

    /**
     *  Speichert die Unternehmensdaten des BusinessData-POJOs ind die Config-Datei
     *
     */
    public void save() {
        model.save();
    }

    public List<String> validate() {
        return model.validate();
    }

    public void dispose() {
        view.dispose();
    }

    public int priority() {
        return 0;
    }

    public String getButtonName() {
        return "Tweet";
    }

    public Icon getButtonIcon() {
        return model.getHeaderInfo().getIcon();
    }

}
