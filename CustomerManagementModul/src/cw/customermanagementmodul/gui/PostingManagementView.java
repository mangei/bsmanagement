package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWJXTable;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.renderer.DateTimeTableCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Font;

/**
 *
 * @author Manuel Geier
 */
public class PostingManagementView
{
    private JButton bNew;
    private JButton bEdit;
    private JButton bCancel;
    private JButton bDelete;
    private JButton bManagePostingCategories;

    private CWJXTable tPostings;

    private JLabel lLiabilities;
    private JLabel lAssets;
    private JLabel lSaldo;
    
    private PostingManagementPresentationModel model;
    
    public PostingManagementView(PostingManagementPresentationModel model) {
        this.model = model;
    }
    
    private void initComponents() {
        bNew    = CWComponentFactory.createButton(model.getNewAction());
        bEdit   = CWComponentFactory.createButton(model.getEditAction());
        bCancel = CWComponentFactory.createButton(model.getCancelAction());
        bDelete = CWComponentFactory.createButton(model.getDeleteAction());
        bManagePostingCategories = CWComponentFactory.createButton(model.getManagePostingCategoriesAction());
        
        tPostings = CWComponentFactory.createTable("Keine Buchungen vorhanden");
        tPostings.setModel(model.createPostingTableModel(model.getPostingSelection()));
        tPostings.setSelectionModel(new SingleListSelectionAdapter(
                model.getPostingSelection().getSelectionIndexHolder()));
        tPostings.getColumnModel().getColumn(4).setCellRenderer(new DateTimeTableCellRenderer(true));
        tPostings.getColumnModel().getColumn(5).setCellRenderer(new DateTimeTableCellRenderer());

        lLiabilities = CWComponentFactory.createLabel(model.getLiabilitiesValue());
        lLiabilities.setFont(lLiabilities.getFont().deriveFont(Font.BOLD));
        lAssets = CWComponentFactory.createLabel(model.getAssetsValue());
        lAssets.setFont(lAssets.getFont().deriveFont(Font.BOLD));
        lSaldo = CWComponentFactory.createLabel(model.getSaldoValue());
        lSaldo.setFont(lSaldo.getFont().deriveFont(Font.BOLD));
    }
    
    private void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();
        
        JViewPanel panel = new JViewPanel();
        panel.setName("Buchungen");

        panel.getButtonPanel().add(bNew);
        panel.getButtonPanel().add(bEdit);
        panel.getButtonPanel().add(bCancel);
        panel.getButtonPanel().add(bDelete);
        panel.getButtonPanel().add(bManagePostingCategories);

        FormLayout layout = new FormLayout(
                "4dlu, pref, 4dlu, pref, 20dlu, pref, 4dlu, pref, 20dlu, pref, 4dlu, pref:grow",
                "fill:pref:grow, 4dlu, pref"
        );
        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());
        CellConstraints cc = new CellConstraints();
        
        builder.add(new JScrollPane(tPostings), cc.xyw(1, 1, 12));
        builder.addLabel("Soll:", cc.xy(2, 3));
        builder.add(lLiabilities, cc.xy(4, 3));
        builder.addLabel("Haben:", cc.xy(6, 3));
        builder.add(lAssets, cc.xy(8, 3));
        builder.addLabel("Saldo:", cc.xy(10, 3));
        builder.add(lSaldo, cc.xy(12, 3));

        // Buttons am Anfang deaktivieren
        bEdit.setEnabled(false);
        bDelete.setEnabled(false);
        
        return panel;
    }

}
