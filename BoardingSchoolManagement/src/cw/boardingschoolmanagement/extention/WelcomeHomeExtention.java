package cw.boardingschoolmanagement.extention;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.extention.point.CWViewExtentionPoint;
import cw.boardingschoolmanagement.gui.HomeView;
import cw.boardingschoolmanagement.gui.WelcomeHomeExtentionPresentationModel;
import cw.boardingschoolmanagement.gui.WelcomeHomeExtentionView;

/**
 *
 * @author Manuel Geier
 */
public class WelcomeHomeExtention
    implements CWViewExtentionPoint<HomeView> {

    private WelcomeHomeExtentionPresentationModel model;
    private WelcomeHomeExtentionView view;
    private HomeView baseView;
    
	@Override
	public Class<?> getViewExtentionClass() {
		return HomeView.class;
	}
	
	@Override
	public void initComponents(HomeView baseView) {
		this.baseView = baseView;
		
		model = new WelcomeHomeExtentionPresentationModel(baseView.getModel().getEntityManager());
        view = new WelcomeHomeExtentionView(model);
        view.initComponents();
	}
	
	@Override
	public void buildView() {
		view.buildView();
		baseView.addToContentPanel(view);
	}
	
	@Override
	public void dispose() {
		view.dispose();
	}
}
