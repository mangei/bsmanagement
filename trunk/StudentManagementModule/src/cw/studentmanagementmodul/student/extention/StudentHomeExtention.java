package cw.studentmanagementmodul.student.extention;

import cw.boardingschoolmanagement.extention.point.CWIViewExtentionPoint;
import cw.boardingschoolmanagement.gui.HomeView;
import cw.studentmanagementmodul.student.gui.StudentHomeExtentionPresentationModel;
import cw.studentmanagementmodul.student.gui.StudentHomeExtentionView;

/**
 *
 * @author Manuel Geier
 */
public class StudentHomeExtention
	implements CWIViewExtentionPoint<HomeView> {

    private StudentHomeExtentionPresentationModel model;
    private StudentHomeExtentionView view;
    private HomeView baseView;

	@Override
	public Class<?> getViewExtentionClass() {
		return HomeView.class;
	}

	@Override
	public void initComponents(HomeView baseView) {
		this.baseView = baseView;
		
		model = new StudentHomeExtentionPresentationModel();
        view = new StudentHomeExtentionView(model);
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
