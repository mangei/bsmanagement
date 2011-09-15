package cw.boardingschoolmanagement.extention;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.Icon;

import cw.boardingschoolmanagement.extention.point.IConfigurationExtentionPoint;
import cw.boardingschoolmanagement.gui.ConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.GeneralConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.GeneralConfigurationView;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.PropertiesManager;

/**
 *
 * @author ManuelG
 */
public class GeneralConfigurationExtention
        implements IConfigurationExtentionPoint {

    private GeneralConfigurationPresentationModel model;
    private GeneralConfigurationView view;
    private HashMap generalConfigruationMap;

    public void initPresentationModel(ConfigurationPresentationModel configurationModel, EntityManager entityManager) {
        generalConfigruationMap = new HashMap();
        generalConfigruationMap.put("pathPanelActive", Boolean.parseBoolean(PropertiesManager.getProperty("configuration.general.pathPanelActive")));
        generalConfigruationMap.put("pathPanelPosition", PropertiesManager.getProperty("configuration.general.pathPanelPosition"));

        model = new GeneralConfigurationPresentationModel(generalConfigruationMap, configurationModel);
        view = new GeneralConfigurationView(model);
    }

    public CWPanel getView() {
        return view;
    }

    public void save() {
        GUIManager.getInstance().getPathPanel().setPosition(model.getPathPanelPosition());
        GUIManager.getInstance().setPathPanelVisible((Boolean)model.getPathPanelActiveModel().getValue());

        PropertiesManager.setProperty("configuration.general.pathPanelActive", model.getPathPanelActiveModel().getValue().toString());
        PropertiesManager.setProperty("configuration.general.pathPanelPosition", model.getPathPanelPosition().name());
    }

    public List<String> validate() {
        return null;
    }

    public void dispose() {
    }

    public int priority() {
        return 1000;
    }

    public String getButtonName() {
        return "Allgemein";
    }

    public Icon getButtonIcon() {
        return model.getHeaderInfo().getIcon();
    }

}
