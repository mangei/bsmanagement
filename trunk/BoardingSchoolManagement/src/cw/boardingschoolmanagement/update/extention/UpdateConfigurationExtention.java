package cw.boardingschoolmanagement.update.extention;

import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.Icon;

import cw.boardingschoolmanagement.extention.point.ConfigurationExtentionPoint;
import cw.boardingschoolmanagement.gui.ConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.update.gui.UpdateConfigurationPresentationModel;
import cw.boardingschoolmanagement.update.gui.UpdateConfigurationView;

/**
 *
 * @author ManuelG
 */
public class UpdateConfigurationExtention
        implements ConfigurationExtentionPoint {

    private UpdateConfigurationPresentationModel model;
    private UpdateConfigurationView view;

    public void initPresentationModel(ConfigurationPresentationModel configurationModel, EntityManager entityManager) {
        model = new UpdateConfigurationPresentationModel(configurationModel);
        view = new UpdateConfigurationView(model);
    }

    public CWPanel getView() {
        return view;
    }

    public void save() {
    }

    public List<String> validate() {
        return null;
    }

    public void dispose() {
    }

    public int priority() {
        return 0100;
    }

    public String getButtonName() {
        return model.getHeaderInfo().getHeaderText();
    }

    public Icon getButtonIcon() {
        return model.getHeaderInfo().getIcon();
    }

}
