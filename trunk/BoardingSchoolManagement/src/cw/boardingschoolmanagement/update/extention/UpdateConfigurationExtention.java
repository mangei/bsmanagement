package cw.boardingschoolmanagement.update.extention;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.Icon;

import com.jgoodies.validation.ValidationResult;

import cw.boardingschoolmanagement.extention.point.IConfigurationExtentionPoint;
import cw.boardingschoolmanagement.gui.CWIEditPresentationModel;
import cw.boardingschoolmanagement.gui.CWIPresentationModel;
import cw.boardingschoolmanagement.gui.ConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.ConfigurationView;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.update.gui.UpdateConfigurationPresentationModel;
import cw.boardingschoolmanagement.update.gui.UpdateConfigurationView;

/**
 *
 * @author ManuelG
 */
public class UpdateConfigurationExtention
        implements IConfigurationExtentionPoint<ConfigurationView, ConfigurationPresentationModel> {

    private UpdateConfigurationPresentationModel model;
    private UpdateConfigurationView view;

    public String getButtonName() {
        return model.getHeaderInfo().getHeaderText();
    }

    public Icon getButtonIcon() {
        return model.getHeaderInfo().getIcon();
    }

	@Override
	public CWIEditPresentationModel getModel() {
		return model;
	}

	@Override
	public void initPresentationModel(ConfigurationPresentationModel baseModel) {
		model = new UpdateConfigurationPresentationModel(baseModel);
	}

	@Override
	public Class<?> getExtentionClass() {
		return null;
	}

	@Override
	public void initView(ConfigurationView baseView) {
		view = new UpdateConfigurationView(model);
	}

	@Override
	public CWView getView() {
		return view;
	}

	@Override
	public List<Class<?>> getExtentionClassList() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(ConfigurationView.class);
		list.add(ConfigurationPresentationModel.class);
		return list;
	}

}
