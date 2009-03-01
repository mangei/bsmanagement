package cw.boardingschoolmanagement.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class HomeView
    implements Disposable
{
    private HomePresentationModel model;

    private JViewPanel panel;

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

        panel = new JViewPanel(new HeaderInfo(
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
        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());

        for(int i=0,l=panels.size(); i<l; i++) {
            builder.add(panels.get(i));
            builder.nextRow();
            builder.nextRow();
        }

        panel.addDisposableListener(this);

        initEventHandling();

        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);

        model.dispose();
    }
}
