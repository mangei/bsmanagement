package cw.customermanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWJXTable;
import cw.boardingschoolmanagement.gui.component.JNotNullLabel;
import cw.boardingschoolmanagement.gui.helper.JXTableSelectionConverter;
import cw.customermanagementmodul.gui.renderer.ActiveCustomerTableCellRenderer;
import cw.customermanagementmodul.gui.renderer.GenderTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import cw.customermanagementmodul.pojo.Customer;
import java.awt.BorderLayout;

/**
 * @author CreativeWorkers.at
 */
public class CustomerSelectorView {

    private CustomerSelectorPresentationModel model;
    private CWJXTable tCustomers;

    public CustomerSelectorView(CustomerSelectorPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        tCustomers = CWComponentFactory.createTable(
                model.createCustomerTableModel(model.getCustomerSelection()),
                "Keine Kunden vorhanden",
                model.getCustomerTableStateName()
                );
        tCustomers.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getCustomerSelection().getSelectionIndexHolder(),
                        tCustomers)));

        tCustomers.getColumns(true).get(0).setCellRenderer(new GenderTableCellRenderer());
        tCustomers.getColumns(true).get(16).setCellRenderer(new ActiveCustomerTableCellRenderer());
    }

    private void initEventHandling() {
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JPanel pCenter = new JPanel();
        pCenter.setOpaque(false);

        if (model.getNorthPanel() != null) {
            panel.add(model.getNorthPanel(), BorderLayout.NORTH);
        }
        if (model.getSouthPanel() != null) {
            panel.add(model.getSouthPanel(), BorderLayout.SOUTH);
        }
        panel.add(pCenter, BorderLayout.CENTER);
        if (model.getWestPanel() != null) {
            panel.add(model.getWestPanel(), BorderLayout.WEST);
        }
        if (model.getEastPanel() != null) {
            panel.add(model.getEastPanel(), BorderLayout.EAST);
        }

        FormLayout layout = new FormLayout(
                "fill:pref:grow",
                "pref, 2dlu, fill:pref:grow");
        PanelBuilder builder = new PanelBuilder(layout, pCenter);

        CellConstraints cc = new CellConstraints();
        builder.addLabel("Kunden:", cc.xy(1, 1));
        builder.add(CWComponentFactory.createScrollPane(tCustomers), cc.xy(1, 3));

        return panel;
    }

    private class CustomerTableCellRenderer extends JPanel implements TableCellRenderer {

        private JNotNullLabel lName;
        private JNotNullLabel lAddress;
        private JNotNullLabel lCity;
        private JNotNullLabel lProvince;
        private JNotNullLabel lCountry;
        private JPanel pCity;

        public CustomerTableCellRenderer() {
            lName = new JNotNullLabel();
            lName.setFont(lName.getFont().deriveFont(Font.BOLD));
            lAddress = new JNotNullLabel();
            lCity = new JNotNullLabel();
            lProvince = new JNotNullLabel();
            lProvince.setForeground(Color.GRAY);
            lCountry = new JNotNullLabel();
            lCountry.setForeground(Color.GRAY);

            pCity = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pCity.add(lCity);
            pCity.add(lProvince);
            pCity.add(lCountry);

            FormLayout layout = new FormLayout(
                    "pref, pref, pref:grow",
                    "pref, pref, pref");
            PanelBuilder builder = new PanelBuilder(layout, this);
            CellConstraints cc = new CellConstraints();

            builder.add(lName, cc.xyw(1, 1, 3));
            builder.add(lAddress, cc.xyw(1, 2, 3));
//            builder.add(pCity, cc.xy(1, 3));
            builder.add(lCity, cc.xy(1, 3));
            builder.add(lProvince, cc.xy(2, 3));
            builder.add(lCountry, cc.xy(3, 3));

            setOpaque(false);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Customer c = (Customer) value;

            lName.setText(c.getForename() + " " + c.getSurname());
            lAddress.setText(c.getStreet());
            lCity.setText(c.getPostOfficeNumber() + " " + c.getCity());
            lProvince.setText(", " + c.getProvince());
            lCountry.setText(", " + c.getCountry());

            System.out.println(table.getRowHeight() + "+++++++++++++++++++++ height: " + this.getComponentCount() + "   " + this.getPreferredSize().getHeight());
            table.setRowHeight(row, (int) getPreferredSize().getHeight());

            return this;
        }
    }
}
