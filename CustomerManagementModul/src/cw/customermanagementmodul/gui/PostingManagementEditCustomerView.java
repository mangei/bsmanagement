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
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

/**
 *
 * @author Manuel Geier
 */
public class PostingManagementEditCustomerView
        implements Disposable
{
    private PostingManagementEditCustomerPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel panel;
    private JButton bNew;
    private JButton bReversePosting;
    private JButton bBalancePosting;
    private JButton bManagePostingCategories;

    private JComboBox cbFilterYear;
    private JComboBox cbFilterMonth;
    private JComboBox cbFilterPostingCategory;

    private CWJXTable tPostings;

    private JLabel lLiabilities;
    private JLabel lAssets;
    private JLabel lSaldo;
    private JLabel lTotalLiabilities;
    private JLabel lTotalAssets;
    private JLabel lTotalSaldo;

    private JPanel pFilteredValues;

    private PropertyChangeListener filterActiveListener;

    public PostingManagementEditCustomerView(PostingManagementEditCustomerPresentationModel model) {
        this.model = model;
    }
    
    private void initComponents() {
        bNew    = CWComponentFactory.createButton(model.getNewAction());
        bReversePosting = CWComponentFactory.createButton(model.getReversePostingAction());
        bBalancePosting = CWComponentFactory.createButton(model.getBalancePostingAction());
//        bDelete = CWComponentFactory.createButton(model.getDeleteAction());
        bManagePostingCategories = CWComponentFactory.createButton(model.getManagePostingCategoriesAction());

        cbFilterYear    = CWComponentFactory.createComboBox(model.getFilterYearSelection());
        cbFilterMonth   = CWComponentFactory.createComboBox(model.getFilterMonthSelection());
        cbFilterPostingCategory   = CWComponentFactory.createComboBox(model.getFilterPostingCategorySelection());

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

        lLiabilities = CWComponentFactory.createLabel(model.getLiabilitiesValue(), NumberFormat.getCurrencyInstance());
        lAssets = CWComponentFactory.createLabel(model.getAssetsValue(), NumberFormat.getCurrencyInstance());
        lSaldo = CWComponentFactory.createLabel(model.getSaldoValue(), NumberFormat.getCurrencyInstance());
        lTotalLiabilities = CWComponentFactory.createLabel(model.getTotalLiabilitiesValue(), NumberFormat.getCurrencyInstance());
        lTotalAssets = CWComponentFactory.createLabel(model.getTotalAssetsValue(), NumberFormat.getCurrencyInstance());
        lTotalSaldo = CWComponentFactory.createLabel(model.getTotalSaldoValue(), NumberFormat.getCurrencyInstance());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(bNew)
                .addComponent(bReversePosting)
                .addComponent(bBalancePosting)
                .addComponent(bManagePostingCategories)
                .addComponent(cbFilterYear)
                .addComponent(cbFilterMonth)
                .addComponent(cbFilterPostingCategory)
                .addComponent(lLiabilities)
                .addComponent(lAssets)
                .addComponent(lSaldo)
                .addComponent(lTotalLiabilities)
                .addComponent(lTotalAssets)
                .addComponent(lTotalSaldo);
    }
    
    private void initEventHandling() {
        model.getFilterActive().addValueChangeListener(filterActiveListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                pFilteredValues.setVisible((Boolean)evt.getNewValue());
            }
        });
        pFilteredValues.setVisible(false);
    }

    public JPanel buildPanel() {
        initComponents();
        
        panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());
        panel.setName("Buchungen");

        panel.getButtonPanel().add(bNew);
        panel.getButtonPanel().add(bBalancePosting);
        panel.getButtonPanel().add(bReversePosting);
//        panel.getButtonPanel().add(bDelete);
        panel.getButtonPanel().add(bManagePostingCategories);

        JPanel pFilter = new JPanel();
        pFilter.setOpaque(false);

        FormLayout layout = new FormLayout(
                "pref, 30, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 30dlu, pref, 4dlu, pref",
                "pref"
        );
        PanelBuilder builder = new PanelBuilder(layout, pFilter);
        CellConstraints cc = new CellConstraints();
        
        builder.addLabel("Filter:", cc.xy(1, 1));
        builder.addLabel("Jahr:", cc.xy(3, 1));
        builder.add(cbFilterYear, cc.xy(5, 1));
        builder.addLabel("Monat:", cc.xy(7, 1));
        builder.add(cbFilterMonth, cc.xy(9, 1));
        builder.addLabel("Kategorie:", cc.xy(11, 1));
        builder.add(cbFilterPostingCategory, cc.xy(13, 1));

        
        // filterValue Layout
        pFilteredValues = new JPanel();
        pFilteredValues.setOpaque(false);

        layout = new FormLayout(
                "4dlu, [100,pref], pref, 4dlu, pref, 10dlu, pref, 4dlu, pref, 10dlu, pref, 4dlu, pref",
                "4dlu, pref"
        );
        builder = new PanelBuilder(layout, pFilteredValues);
        cc = new CellConstraints();

        builder.addLabel("<html><b>Gefiltert:</b></html>",  cc.xy(2, 2));
        builder.addLabel("<html><b>Soll:</b></html>",       cc.xy(3, 2));
        builder.add(lLiabilities,                           cc.xy(5, 2));
        builder.addLabel("<html><b>Haben:</b></html>",      cc.xy(7, 2));
        builder.add(lAssets,                                cc.xy(9, 2));
        builder.addLabel("<html><b>Saldo:</b></html>",      cc.xy(11, 2));
        builder.add(lSaldo,                                 cc.xy(13, 2));


        // totalValue Layout
        JPanel pTotalValues = new JPanel();
        pTotalValues.setOpaque(false);

        layout = new FormLayout(
                "4dlu, [100,pref], pref, 4dlu, pref, 10dlu, pref, 4dlu, pref, 10dlu, pref, 4dlu, pref",
                "pref"
        );
        builder = new PanelBuilder(layout, pTotalValues);
        cc = new CellConstraints();

        builder.addLabel("<html><b>Gesamt:</b></html>",     cc.xy(2, 1));
        builder.addLabel("<html><b>Soll:</b></html>",       cc.xy(3, 1));
        builder.add(lTotalLiabilities,                      cc.xy(5, 1));
        builder.addLabel("<html><b>Haben:</b></html>",      cc.xy(7, 1));
        builder.add(lTotalAssets,                           cc.xy(9, 1));
        builder.addLabel("<html><b>Saldo:</b></html>",      cc.xy(11, 1));
        builder.add(lTotalSaldo,                            cc.xy(13, 1));

        
        // Main layout
        layout = new FormLayout(
                "pref:grow",
                "pref, 4dlu, fill:pref:grow, pref, 4dlu, pref"
        );
        builder = new PanelBuilder(layout, panel.getContentPanel());
        cc = new CellConstraints();
        
        builder.add(pFilter,                    cc.xy(1, 1));
        builder.add(new JScrollPane(tPostings), cc.xy(1, 3));
        builder.add(pFilteredValues,            cc.xy(1, 4));
        builder.add(pTotalValues,               cc.xy(1, 6));

        panel.addDisposableListener(this);

        initEventHandling();

        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);

        model.getFilterActive().removeValueChangeListener(filterActiveListener);

        componentContainer.dispose();

        model.dispose();
    }

}
