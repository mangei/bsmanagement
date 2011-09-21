package cw.boardingschoolmanagement.extention;

import java.util.ArrayList;
import java.util.List;

import cw.boardingschoolmanagement.extention.point.CWIViewExtentionPoint;
import cw.boardingschoolmanagement.gui.HomePresentationModel;
import cw.boardingschoolmanagement.gui.HomeView;
import cw.boardingschoolmanagement.gui.WelcomeHomeExtentionPresentationModel;
import cw.boardingschoolmanagement.gui.WelcomeHomeExtentionView;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Manuel Geier
 */
public class WelcomeHomeExtention
    implements 
    	CWIViewExtentionPoint<HomeView, HomePresentationModel>
    {

    private WelcomeHomeExtentionPresentationModel model;
    private WelcomeHomeExtentionView view;
    
	@Override
	public Class<?> getExtentionClass() {
		return null;
	}
	
	@Override
	public List<Class<?>> getExtentionClassList() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(HomeView.class);
		list.add(HomePresentationModel.class);
		return list;
	}

	@Override
	public CWView<?> getView() {
		return view;
	}

	@Override
	public void initView(HomeView baseView) {
        view = new WelcomeHomeExtentionView(model, baseView);
	}

	@Override
	public void initPresentationModel(HomePresentationModel baseModel) {
		model = new WelcomeHomeExtentionPresentationModel(baseModel.getEntityManager());
	}
	
}
