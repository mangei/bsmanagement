package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWJXTable;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.helper.JXTableSelectionConverter;
import cw.customermanagementmodul.gui.renderer.LockTableCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Manuel Geier
 */
public class PostingCategoryManagementView
{
    private JButton bNew;
    private JButton bEdit;
    private JButton bDelete;
    private JLabel lLocked;

    private CWJXTable tPostingsCategories;

    private PostingCategoryManagementPresentationModel model;
    
    public PostingCategoryManagementView(PostingCategoryManagementPresentationModel model) {
        this.model = model;
    }
    
    private void initComponents() {
        bNew    = CWComponentFactory.createButton(model.getNewAction());
        bEdit   = CWComponentFactory.createButton(model.getEditAction());
        bDelete = CWComponentFactory.createButton(model.getDeleteAction());
        
        tPostingsCategories = CWComponentFactory.createTable("Keine Kategorien vorhanden");
        tPostingsCategories.setModel(model.createPostingCategoryTableModel(model.getPostingCategorySelection()));
        tPostingsCategories.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getPostingCategorySelection().getSelectionIndexHolder(),
                        tPostingsCategories)));

        tPostingsCategories.getColumns(true).get(2).setCellRenderer(new LockTableCellRenderer());

        lLocked = CWComponentFactory.createLabel("Geperrt: Dies sind systeminterne Kategorien.", CWUtils.loadIcon("cw/customermanagementmodul/images/lock.png"));

    }
    
    private void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();
        
        JViewPanel panel = new JViewPanel(model.getHeaderInfo());

        panel.getButtonPanel().add(bNew);
        panel.getButtonPanel().add(bEdit);
        panel.getButtonPanel().add(bDelete);

        FormLayout layout = new FormLayout(
                "fill:pref:grow",
                "fill:pref:grow, 4dlu, pref"
        );
        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());
        CellConstraints cc = new CellConstraints();
        
        builder.add(new JScrollPane(tPostingsCategories), cc.xy(1, 1));
        builder.add(lLocked, cc.xy(1, 3));

        // Buttons am Anfang deaktivieren
        bEdit.setEnabled(false);
        bDelete.setEnabled(false);
        
        return panel;
    }

}
