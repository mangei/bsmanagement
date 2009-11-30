package cw.customermanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CalendarUtil;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.JNotNullLabel;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import cw.boardingschoolmanagement.gui.renderer.DateTimeTableCellRenderer;
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
public class CustomerSelectorView extends CWPanel
{

    private CustomerSelectorPresentationModel model;
    private CWTable tCustomers;

    public CustomerSelectorView(CustomerSelectorPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        tCustomers = CWComponentFactory.createTable(
                model.createCustomerTableModel(model.getCustomerSelection()),
                "Keine Kunden vorhanden",
                model.getCustomerTableStateName()
                );
        tCustomers.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getCustomerSelection().getSelectionIndexHolder(),
                        tCustomers)));

        tCustomers.getColumns(true).get(0).setCellRenderer(new GenderTableCellRenderer());
        tCustomers.getColumns(true).get(4).setCellRenderer(new DateTimeTableCellRenderer(CalendarUtil.DATE_FORMAT_STANDARD));
        tCustomers.getColumns(true).get(15).setCellRenderer(new ActiveCustomerTableCellRenderer());
        tCustomers.getColumns(true).get(16).setCellRenderer(new DateTimeTableCellRenderer(CalendarUtil.DATETIME_FORMAT_STANDARD));
    }

    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {
        this.setLayout(new BorderLayout());
        JPanel pCenter = new JPanel();
        pCenter.setOpaque(false);

        if (model.getNorthPanel() != null) {
            this.add(model.getNorthPanel(), BorderLayout.NORTH);
        }
        if (model.getSouthPanel() != null) {
            this.add(model.getSouthPanel(), BorderLayout.SOUTH);
        }
        this.add(pCenter, BorderLayout.CENTER);
        if (model.getWestPanel() != null) {
            this.add(model.getWestPanel(), BorderLayout.WEST);
        }
        if (model.getEastPanel() != null) {
            this.add(model.getEastPanel(), BorderLayout.EAST);
        }

        FormLayout layout = new FormLayout(
                "fill:pref:grow",
                "pref, 2dlu, fill:pref:grow");
        PanelBuilder builder = new PanelBuilder(layout, pCenter);

        CellConstraints cc = new CellConstraints();
        builder.addLabel("Kunden:", cc.xy(1, 1));
        builder.add(CWComponentFactory.createScrollPane(tCustomers), cc.xy(1, 3));
    }

    public void dispose() {
        model.dispose();
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
