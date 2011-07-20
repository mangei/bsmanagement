package cw.accountmanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.accountmanagementmodul.gui.model.PostingTreeTableModel;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.renderer.DateTimeTableCellRenderer;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTreeTable;

/**
 *
 * @author Manuel Geier
 */
public class PostingManagementAccountManagementView extends CWView
{
    private PostingManagementAccountManagementPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWButton bNew;
    private CWButton bEdit;
    private CWButton bReversePosting;
    private CWButton bBalancePosting;

    private CWComboBox cbFilterYear;
    private CWComboBox cbFilterMonth;

    private JXTreeTable ttPostings;

    private CWLabel lSaldo;
    private CWLabel lTotalSaldo;

    private CWPanel pFilteredValues;

    private PropertyChangeListener filterActiveListener;

    public PostingManagementAccountManagementView(PostingManagementAccountManagementPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }
    
    private void initComponents() {
        bNew    = CWComponentFactory.createButton(model.getNewAction());
        bEdit    = CWComponentFactory.createButton(model.getEditAction());
        bReversePosting = CWComponentFactory.createButton(model.getReversePostingAction());
        bBalancePosting = CWComponentFactory.createButton(model.getBalancePostingAction());
//        bDelete = CWComponentFactory.createButton(model.getDeleteAction());

        cbFilterYear    = CWComponentFactory.createComboBox(model.getFilterYearSelection());
        cbFilterMonth   = CWComponentFactory.createComboBox(model.getFilterMonthSelection());

        ttPostings = new JXTreeTable(model.getPostingTreeTableModel());
        ttPostings.setShowsRootHandles(false);
        ttPostings.getColumns(true).get(PostingTreeTableModel.COLUMN_CREATIONDATE).setCellRenderer(new DateTimeTableCellRenderer(true));

        lSaldo = CWComponentFactory.createLabel(model.getSaldoValue(), NumberFormat.getCurrencyInstance());
        lTotalSaldo = CWComponentFactory.createLabel(model.getTotalSaldoValue(), NumberFormat.getCurrencyInstance());

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(bNew)
                .addComponent(bReversePosting)
                .addComponent(bBalancePosting)
                .addComponent(cbFilterYear)
                .addComponent(cbFilterMonth)
                .addComponent(lSaldo)
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

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
        this.setName("Buchungen");

        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bBalancePosting);
        this.getButtonPanel().add(bReversePosting);
//        this.getButtonPanel().add(bDelete);

        JPanel pFilter = new JPanel();
        pFilter.setOpaque(false);

        FormLayout layout = new FormLayout(
                "pref, 30, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 30dlu, pref",
                "pref"
        );
        PanelBuilder builder = new PanelBuilder(layout, pFilter);
        CellConstraints cc = new CellConstraints();
        
        builder.addLabel("Filter:", cc.xy(1, 1));
        builder.addLabel("Jahr:", cc.xy(3, 1));
        builder.add(cbFilterYear, cc.xy(5, 1));
        builder.addLabel("Monat:", cc.xy(7, 1));
        builder.add(cbFilterMonth, cc.xy(9, 1));

        
        // filterValue Layout
        pFilteredValues = CWComponentFactory.createPanel();
        pFilteredValues.setOpaque(false);

        layout = new FormLayout(
                "4dlu, [100,pref], pref, 4dlu, pref",
                "4dlu, pref"
        );
        builder = new PanelBuilder(layout, pFilteredValues);
        cc = new CellConstraints();

        builder.addLabel("<html><b>Gefiltert:</b></html>",  cc.xy(2, 2));
        builder.addLabel("<html><b>Saldo:</b></html>",      cc.xy(3, 2));
        builder.add(lSaldo,                                 cc.xy(3, 2));


        // totalValue Layout
        JPanel pTotalValues = new JPanel();
        pTotalValues.setOpaque(false);

        layout = new FormLayout(
                "4dlu, [100,pref], pref, 4dlu, pref",
                "pref"
        );
        builder = new PanelBuilder(layout, pTotalValues);
        cc = new CellConstraints();

        builder.addLabel("<html><b>Gesamt:</b></html>",     cc.xy(2, 1));
        builder.addLabel("<html><b>Saldo:</b></html>",      cc.xy(3, 1));
        builder.add(lTotalSaldo,                            cc.xy(3, 1));

        
        // Main layout
        layout = new FormLayout(
                "pref:grow",
                "pref, 4dlu, fill:pref:grow, pref, 4dlu, pref"
        );
        builder = new PanelBuilder(layout, this.getContentPanel());
        cc = new CellConstraints();
        
        builder.add(pFilter,                    cc.xy(1, 1));
        builder.add(new JScrollPane(ttPostings), cc.xy(1, 3));
        builder.add(pFilteredValues,            cc.xy(1, 4));
        builder.add(pTotalValues,               cc.xy(1, 6));
    }

    @Override
    public void dispose() {
        model.getFilterActive().removeValueChangeListener(filterActiveListener);
        filterActiveListener = null;

        componentContainer.dispose();

        model.dispose();
    }

}
