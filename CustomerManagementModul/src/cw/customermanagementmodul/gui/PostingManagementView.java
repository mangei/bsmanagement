package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWJXTable;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.helper.JXTableSelectionConverter;
import cw.boardingschoolmanagement.gui.renderer.DateTimeTableCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JComboBox;

/**
 *
 * @author Manuel Geier
 */
public class PostingManagementView
{
    private JButton bNew;
    private JButton bEdit;
    private JButton bReversePosting;
//    private JButton bDelete;
    private JButton bManagePostingCategories;

    private JComboBox cbFilterYear;
    private JComboBox cbFilterMonth;

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
//        bEdit   = CWComponentFactory.createButton(model.getEditAction());
        bReversePosting = CWComponentFactory.createButton(model.getReversePostingAction());
//        bDelete = CWComponentFactory.createButton(model.getDeleteAction());
        bManagePostingCategories = CWComponentFactory.createButton(model.getManagePostingCategoriesAction());

        cbFilterYear    = CWComponentFactory.createComboBox(model.getFilterYearSelection());
        cbFilterMonth   = CWComponentFactory.createComboBox(model.getFilterMonthSelection());

        tPostings = CWComponentFactory.createTable(
                model.createPostingTableModel(model.getPostingSelection()),
                "Keine Buchungen vorhanden",
                "cw.customerboardingmanagement.PostingManagementView.postingTableState"
                );
        tPostings.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getPostingSelection().getSelectionIndexHolder(),
                        tPostings)));
        tPostings.getColumns(true).get(4).setCellRenderer(new DateTimeTableCellRenderer(true));
        tPostings.getColumns(true).get(5).setCellRenderer(new DateTimeTableCellRenderer());

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
        
        JViewPanel panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());
        panel.setName("Buchungen");

        panel.getButtonPanel().add(bNew);
        panel.getButtonPanel().add(bEdit);
        panel.getButtonPanel().add(bReversePosting);
//        panel.getButtonPanel().add(bDelete);
        panel.getButtonPanel().add(bManagePostingCategories);

        JPanel pFilter = new JPanel();
        pFilter.setOpaque(false);

        FormLayout layout = new FormLayout(
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref",
                "pref"
        );
        PanelBuilder builder = new PanelBuilder(layout, pFilter);
        CellConstraints cc = new CellConstraints();
        
        builder.addLabel("Filter:", cc.xy(1, 1));
        builder.addLabel("Jahr:", cc.xy(3, 1));
        builder.add(cbFilterYear, cc.xy(5, 1));
        builder.addLabel("Monat:", cc.xy(7, 1));
        builder.add(cbFilterMonth, cc.xy(9, 1));


        layout = new FormLayout(
                "4dlu, pref, 4dlu, pref, 20dlu, pref, 4dlu, pref, 20dlu, pref, 4dlu, pref:grow",
                "pref, 4dlu, fill:pref:grow, 4dlu, pref"
        );
        builder = new PanelBuilder(layout, panel.getContentPanel());
        cc = new CellConstraints();
        
        builder.add(pFilter, cc.xyw(1, 1, 12));
        builder.add(new JScrollPane(tPostings), cc.xyw(1, 3, 12));
        builder.addLabel("Soll:", cc.xy(2, 5));
        builder.add(lLiabilities, cc.xy(4, 5));
        builder.addLabel("Haben:", cc.xy(6, 5));
        builder.add(lAssets, cc.xy(8, 5));
        builder.addLabel("Saldo:", cc.xy(10, 5));
        builder.add(lSaldo, cc.xy(12, 5));

        return panel;
    }

}
