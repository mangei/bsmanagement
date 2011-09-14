package cw.boardingschoolmanagement.gui;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class HomeView
	extends CWView<HomePresentationModel>
{

    public HomeView(HomePresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    }

    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(new CWHeaderInfo(
                "Startseite",
                "<html>Sie befinden sich auf der Startseite.</html>",
                CWUtils.loadIcon("cw/boardingschoolmanagement/images/home_32.png"),
                CWUtils.loadIcon("cw/boardingschoolmanagement/images/home_16.png")
        ));

    }

    @Override
    public void dispose() {
    	super.dispose();
    }
}
