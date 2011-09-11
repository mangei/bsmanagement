package cw.boardingschoolmanagement.gui;

import java.util.List;

import javax.swing.JPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.FormLayout;

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

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        // Nothing to do
    }

    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {
        this.setHeaderInfo(new CWHeaderInfo(
                "Startseite",
                "<html>Sie befinden sich auf der Startseite.</html>",
                CWUtils.loadIcon("cw/boardingschoolmanagement/images/home_32.png"),
                CWUtils.loadIcon("cw/boardingschoolmanagement/images/home_16.png")
        ));

        List<JPanel> panels = getModel().getExtentionPanels();
        StringBuilder rows = new StringBuilder();
        for(int i=0,l=panels.size(); i<l; i++) {
            if(i != 0) {
                rows.append(", ");
            }
            rows.append("pref, 8dlu");
        }

        FormLayout layout = new FormLayout(
                "fill:pref:grow",
                rows.toString()
        );
        PanelBuilder builder = new PanelBuilder(layout);

        for(int i=0,l=panels.size(); i<l; i++) {
            builder.add(panels.get(i));
            builder.nextRow();
            builder.nextRow();
        }
        
        addToContentPanel(builder.getPanel());
    }

    @Override
    public void dispose() {
    	getModel().dispose();
    }
}
