package cw.tweetmodule.extention;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import cw.boardingschoolmanagement.extention.point.IConfigurationExtentionPoint;
import cw.boardingschoolmanagement.gui.CWIEditPresentationModel;
import cw.boardingschoolmanagement.gui.ConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.ConfigurationView;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.tweetmodule.gui.TweetConfigurationPresentationModel;
import cw.tweetmodule.gui.TweetConfigurationView;

/**
 *  
 * Configuration extention for tweet module
 *
 * @author Manuel Geier
 */
public class TweetConfigurationExtention
	implements IConfigurationExtentionPoint<ConfigurationView, ConfigurationPresentationModel>{

    private TweetConfigurationPresentationModel model;
    private TweetConfigurationView view;

    public String getButtonName() {
        return "Tweet";
    }

    public Icon getButtonIcon() {
        return model.getHeaderInfo().getIcon();
    }

	@Override
	public CWIEditPresentationModel getModel() {
		return model;
	}

	@Override
	public Class<?> getExtentionClass() {
		return null;
	}

	@Override
	public void initView(ConfigurationView baseView) {
		view = new TweetConfigurationView(model);
	}

	@Override
	public CWView<?> getView() {
		return view;
	}

	@Override
	public List<Class<?>> getExtentionClassList() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(ConfigurationView.class);
		list.add(ConfigurationPresentationModel.class);
		return list;
	}

	@Override
	public void initPresentationModel(ConfigurationPresentationModel baseModel) {
		model = new TweetConfigurationPresentationModel(baseModel);
	}

}
