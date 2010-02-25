package cw.accountmanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import cw.customermanagementmodul.gui.renderer.LockTableCellRenderer;
import javax.swing.JScrollPane;

/**
 *
 * @author Manuel Geier
 */
public class PostingCategoryManagementView extends CWView
{
    private PostingCategoryManagementPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWButton bNew;
    private CWButton bEdit;
    private CWButton bDelete;
    private CWLabel lLocked;

    private CWTable tPostingsCategories;

    
    public PostingCategoryManagementView(PostingCategoryManagementPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }
    
    private void initComponents() {
        bNew    = CWComponentFactory.createButton(model.getNewAction());
        bEdit   = CWComponentFactory.createButton(model.getEditAction());
        bDelete = CWComponentFactory.createButton(model.getDeleteAction());
        
        tPostingsCategories = CWComponentFactory.createTable("Keine Kategorien vorhanden");
        tPostingsCategories.setModel(model.createPostingCategoryTableModel(model.getPostingCategorySelection()));
        tPostingsCategories.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getPostingCategorySelection().getSelectionIndexHolder(),
                        tPostingsCategories)));

        tPostingsCategories.getColumns(true).get(2).setCellRenderer(new LockTableCellRenderer());

        lLocked = CWComponentFactory.createLabel("Geperrt: Dies sind systeminterne Kategorien.", CWUtils.loadIcon("cw/customermanagementmodul/images/lock.png"));

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(bNew)
                .addComponent(bEdit)
                .addComponent(bDelete)
                .addComponent(lLocked);
    }
    
    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());

        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bDelete);

        FormLayout layout = new FormLayout(
                "fill:pref:grow",
                "fill:pref:grow, 4dlu, pref"
        );
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        CellConstraints cc = new CellConstraints();
        
        builder.add(new JScrollPane(tPostingsCategories), cc.xy(1, 1));
        builder.add(lLocked, cc.xy(1, 3));

        // Buttons am Anfang deaktivieren
        bEdit.setEnabled(false);
        bDelete.setEnabled(false);
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}
