package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWJXList;
import cw.boardingschoolmanagement.gui.component.CWJXTable;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import cw.customermanagementmodul.pojo.Accounting;

/**
 *
 * @author Manuel Geier
 */
public class AccountingManagementCustomerGUIExtentionView
{
    private JButton bNew;
    private JButton bEdit;
    private JButton bCancel;
    private JButton bDelete;

    private CWJXTable tAccountings;
    private CWJXList liAccountings;
    
    private AccountingManagementCustomerGUIExtentionPresentationModel model;
    
    public AccountingManagementCustomerGUIExtentionView(AccountingManagementCustomerGUIExtentionPresentationModel model) {
        this.model = model;
    }
    
    private void initComponents() {
        bNew    = CWComponentFactory.createButton(model.getNewButtonAction());
        bEdit   = CWComponentFactory.createButton(model.getEditButtonAction());
        bCancel = CWComponentFactory.createButton(model.getCancelButtonAction());
        bDelete = CWComponentFactory.createButton(model.getDeleteButtonAction());
        
        tAccountings = CWComponentFactory.createTable("Keine Buchungen vorhanden");
        tAccountings.setModel(model.createBuchungTableModel(model.getAccountingSelection()));
        tAccountings.setSelectionModel(new SingleListSelectionAdapter(
                model.getAccountingSelection().getSelectionIndexHolder()));

        liAccountings = CWComponentFactory.createList(model.getAccountingSelection(), "Keine Buchungen vorhanden");
        liAccountings.setCellRenderer(new AccountingListRenderer());
    }
    
    private void initEventHandling() {
//        tAccountings.addMouseListener(model.getDoubleClickHandler());
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
        
        panel.getContentPanel().add(new JScrollPane(tAccountings), BorderLayout.CENTER);
        
        // Buttons am Anfang deaktivieren
        bEdit.setEnabled(false);
        bDelete.setEnabled(false);
        
        return panel;
    }

    private class AccountingListRenderer extends JPanel implements ListCellRenderer {

        private JLabel lDescription;
        private JLabel lAmount;

        public AccountingListRenderer() {
            lDescription = new JLabel();
            lAmount = new JLabel();
            lAmount.setForeground(Color.GRAY);

            FormLayout layout = new FormLayout(
                    "pref:grow, right:pref",
                    "pref"
            );
            PanelBuilder builder = new PanelBuilder(layout, this);
            CellConstraints cc = new CellConstraints();

            builder.add(lDescription, cc.xy(1, 1));
            builder.add(lAmount, cc.xy(2, 1));
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Accounting a = (Accounting) value;

            lDescription.setText(a.getDescription());
            lAmount.setText(Double.toString(a.getAmount()) + " €");

            return this;
        }
        
    }
}
