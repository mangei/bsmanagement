package cw.boardingschoolmanagement.extention;

import cw.boardingschoolmanagement.extention.point.CWIViewExtentionPoint;
import cw.boardingschoolmanagement.gui.CWIPresentationModel;
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
    	CWIViewExtentionPoint<HomeView>
    {

    private WelcomeHomeExtentionPresentationModel model;
    private WelcomeHomeExtentionView view;
    private HomeView baseView;
    
	@Override
	public Class<?> getExtentionClass() {
		return HomeView.class;
	}
	
	public void init(HomeView baseView) {
		this.baseView = baseView;
		
		model = new WelcomeHomeExtentionPresentationModel(baseView.getModel().getEntityManager());
        view = new WelcomeHomeExtentionView(model, baseView);
	}

	@Override
	public CWView<?> getView() {
		return view;
	}
	
	
}
