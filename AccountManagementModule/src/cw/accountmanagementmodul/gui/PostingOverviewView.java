package cw.accountmanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.accountmanagementmodul.gui.renderer.PostingTreeTableRenderer;
import cw.boardingschoolmanagement.gui.component.CWView;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTreeTable;

/**
 *
 * @author Manuel Geier
 */
public class PostingOverviewView extends CWView
{
    private PostingOverviewPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;

    private JXTreeTable ttPostings;

    public PostingOverviewView(PostingOverviewPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }
    
    private void initComponents() {

        ttPostings = new JXTreeTable(model.getPostingTreeTableModel());
        ttPostings.setRootVisible(true);
        ttPostings.setTreeCellRenderer(new PostingTreeTableRenderer());

        componentContainer = CWComponentFactory.createComponentContainer()
                ;
    }
    
    private void initEventHandling() {
    }

    private void buildView() {
        this.setHeaderInfo(new CWHeaderInfo("Buchungsuebersicht"));
        this.setName("Buchungsuebersicht");

        // Main layout
        FormLayout layout = new FormLayout(
                "pref:grow",
                "fill:pref:grow"
        );
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        CellConstraints cc = new CellConstraints();
        
        builder.add(new JScrollPane(ttPostings), cc.xy(1, 1));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }

}
