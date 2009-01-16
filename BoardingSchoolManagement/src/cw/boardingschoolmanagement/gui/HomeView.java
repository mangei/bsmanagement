package cw.boardingschoolmanagement.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class HomeView
{
    private HomePresentationModel model;

    public HomeView(HomePresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        // Nothing to do
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initComponents();

        JViewPanel panel = new JViewPanel("Startseite");

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
        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());

        for(int i=0,l=panels.size(); i<l; i++) {
            builder.add(panels.get(i));
            builder.nextRow();
            builder.nextRow();
        }

        initEventHandling();

        return panel;
    }
    
}
