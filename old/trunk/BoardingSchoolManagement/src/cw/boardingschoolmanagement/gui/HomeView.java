package cw.boardingschoolmanagement.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class HomeView extends CWView
{
    private HomePresentationModel model;

    public HomeView(HomePresentationModel model) {
        this.model = model;

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

        List<JPanel> panels = model.getExtentionPanels();
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
        PanelBuilder builder = new PanelBuilder(layout, getContentPanel());

        for(int i=0,l=panels.size(); i<l; i++) {
            builder.add(panels.get(i));
            builder.nextRow();
            builder.nextRow();
        }
    }

    @Override
    public void dispose() {
        model.dispose();
    }
}
