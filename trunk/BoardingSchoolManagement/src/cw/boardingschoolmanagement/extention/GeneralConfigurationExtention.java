package cw.boardingschoolmanagement.extention;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Icon;

import cw.boardingschoolmanagement.extention.point.IConfigurationExtentionPoint;
import cw.boardingschoolmanagement.gui.CWIEditPresentationModel;
import cw.boardingschoolmanagement.gui.ConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.ConfigurationView;
import cw.boardingschoolmanagement.gui.GeneralConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.GeneralConfigurationView;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.PropertiesManager;

/**
 *
 * @author ManuelG
 */
public class GeneralConfigurationExtention
        implements IConfigurationExtentionPoint<ConfigurationView, ConfigurationPresentationModel> {

    private GeneralConfigurationPresentationModel model;
    private GeneralConfigurationView view;
    private HashMap generalConfigruationMap;

    public String getButtonName() {
        return "Allgemein";
    }

    public Icon getButtonIcon() {
        return model.getHeaderInfo().getIcon();
    }

	@Override
	public void initView(ConfigurationView baseView) {
		view = new GeneralConfigurationView(model);
	}

	@Override
	public CWView getView() {
		return view;
	}

	@Override
	public void initPresentationModel(ConfigurationPresentationModel configurationModel) {
		generalConfigruationMap = new HashMap();
        generalConfigruationMap.put("pathPanelActive", Boolean.parseBoolean(PropertiesManager.getProperty("configuration.general.pathPanelActive")));
        generalConfigruationMap.put("pathPanelPosition", PropertiesManager.getProperty("configuration.general.pathPanelPosition"));

        model = new GeneralConfigurationPresentationModel(generalConfigruationMap, configurationModel);
        
	}

	@Override
	public Class<?> getExtentionClass() {
		return null;
	}

	@Override
	public List<Class<?>> getExtentionClassList() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(ConfigurationView.class);
		list.add(ConfigurationPresentationModel.class);
		return list;
	}

	@Override
	public CWIEditPresentationModel getModel() {
		return model;
	}

}
