package cw.customermanagementmodul.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.app.CalendarUtil;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWCheckBoxMenuItem;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWPopupMenu;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.JNotNullLabel;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import cw.boardingschoolmanagement.gui.renderer.DateTimeTableCellRenderer;
import cw.boardingschoolmanagement.gui.ui.CWButtonPanelButtonUI;
import cw.boardingschoolmanagement.gui.ui.ColoredPanelUI;
import cw.customermanagementmodul.gui.CustomerSelectorPresentationModel.CustomerSelectorFilterExtentionPointHelper;
import cw.customermanagementmodul.gui.renderer.ActiveCustomerTableCellRenderer;
import cw.customermanagementmodul.gui.renderer.GenderTableCellRenderer;
import cw.customermanagementmodul.persistence.Customer;

/**
 * Diese Klasse ist die grafsiche Darstellung der Kundenuebersichtstabelle in
 * Kundenverwaltung.
 *
 * @author Manuel Geier
 */
public class CustomerSelectorView extends CWPanel
{

    private static final Icon enabledFilterPanelIcon = CWUtils.loadIcon("cw/boardingschoolmanagement/images/panel_arrow_down.png");
    private static final Icon disabledFilterPanelIcon = CWUtils.loadIcon("cw/boardingschoolmanagement/images/panel_arrow_right.png");
    private static final Icon filterEnabledIcon = CWUtils.loadIcon("cw/boardingschoolmanagement/images/filter_active.png");
    private static final Icon filterDisabledIcon = CWUtils.loadIcon("cw/boardingschoolmanagement/images/filter.png");

    private CustomerSelectorPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWTable tCustomers;
    private JPanel pFilterHeader;
    private JPanel pFilter;
    private CWButton bFilterSwitcher;
    private CWButton bFilterChooser;
    private CWPopupMenu popFilterChooser;

    private HashMap<ValueModel, PropertyChangeListener> valueChangeListenerDisposeMap;

    private ActionListener filterChooserActionListener;
    private PropertyChangeListener filterSwitcherChangeListener;
    private PropertyChangeListener filterSwitcherIconChangeListener;
    private PropertyChangeListener filterEnabledChangeListener;
    private PropertyChangeListener filterSwitcherPanelCountChangeListener;

    public CustomerSelectorView(CustomerSelectorPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        valueChangeListenerDisposeMap = new HashMap<ValueModel, PropertyChangeListener>();

        pFilterHeader = CWComponentFactory.createPanel();
        pFilterHeader.setUI(new ColoredPanelUI(ColoredPanelUI.ONLY_STD_BORDER_COLOR_STYLE));
        pFilter = CWComponentFactory.createPanel();
        pFilter.setUI(new ColoredPanelUI(ColoredPanelUI.ONLY_STD_BORDER_COLOR_STYLE));

        bFilterSwitcher = CWComponentFactory.createButton(model.getFilterSwitcherAction());
        bFilterSwitcher.setHorizontalAlignment(SwingConstants.LEFT);
        bFilterSwitcher.setUI(new CWButtonPanelButtonUI());
        bFilterChooser = CWComponentFactory.createButton(model.getFilterChooserAction());
        bFilterChooser.setUI(new CWButtonPanelButtonUI());
        bFilterChooser.setIcon(filterDisabledIcon);
        popFilterChooser = CWComponentFactory.createPopupMenu();

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

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(bFilterSwitcher)
                .addComponent(bFilterChooser)
                .addComponent(popFilterChooser)
                .addComponent(tCustomers);

        // Build the filter popup
        List<CustomerSelectorFilterExtentionPointHelper> filterExtentionsHelper = model.getFilterExtentionsHelper();
        for(int i=0, l=filterExtentionsHelper.size(); i<l; i++) {

            // get the extentionhelper
            final CustomerSelectorFilterExtentionPointHelper ex = filterExtentionsHelper.get(i);

            // create the CheckBoxMenuItem with the action
            final CWCheckBoxMenuItem cMenuItem = CWComponentFactory.createCheckBoxMenuItem(ex.getActiveAction());
            PropertyChangeListener activeChangeListener;
            ex.getActiveModel().addValueChangeListener(activeChangeListener = new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    cMenuItem.setSelected((Boolean) ex.getActiveModel().getValue());
                }
            });
            cMenuItem.setSelected((Boolean) ex.getActiveModel().getValue());
            valueChangeListenerDisposeMap.put(ex.getActiveModel(), activeChangeListener);


            // add the item to the popupmenu
            popFilterChooser.add(cMenuItem);

            // add the item to the containier
            componentContainer.addComponent(cMenuItem);
        }
    }

    private void initEventHandling() {
        // Shows the popmenu for the filters
        bFilterChooser.addActionListener(filterChooserActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFilterPopup();
            }
        });

        // Changes the visibility of the active filters. If there are no filters active, show the popup with the filters
        model.getFilterSwitcherModel().addValueChangeListener(filterSwitcherChangeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                // If the filter gets opened and there are filters active, open it
                if((Boolean)evt.getNewValue() == true && (Integer)model.getActiveFilterCountModel().getValue() > 0) {
                    pFilter.setVisible((Boolean) model.getFilterSwitcherModel().getValue());
                } else {
                    // If the filter gets opened and there are no filters active, show the popup
                    if((Boolean)evt.getNewValue() == true && (Integer)model.getActiveFilterCountModel().getValue() == 0) {
                        showFilterPopup();
                        model.getFilterSwitcherModel().setValue(false);
                    } else {
                        pFilter.setVisible((Boolean) model.getFilterSwitcherModel().getValue());
                    }
                }
            }
        });
        pFilter.setVisible((Boolean) model.getFilterSwitcherModel().getValue());

        // Hide the filter panel if there is no filter active and activate the filter, if the first filter is added
        // Change the icon of the filter if there are some active
        model.getActiveFilterCountModel().addValueChangeListener(filterSwitcherPanelCountChangeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                // Hide the panel if the last filter is removed
                if((Integer)model.getActiveFilterCountModel().getValue() == 0 && (Boolean)model.getFilterSwitcherModel().getValue() == true) {
                    model.getFilterSwitcherModel().setValue(false);
                }
                // If the first filter is added and the panel is not shown, show it
                if((Boolean)model.getFilterSwitcherModel().getValue() == false && (Integer) evt.getOldValue() == 0 && (Integer) evt.getNewValue() == 1) {
                    model.getFilterSwitcherModel().setValue(true);
//                    pFilter.setVisible(true);
                }

                // Change the icon if there are some filters enabled
                if((Integer) evt.getNewValue() > 0) {
                    bFilterChooser.setIcon(filterEnabledIcon);
                } else {
                    bFilterChooser.setIcon(filterDisabledIcon);
                }
            }
        });

        // Changes the icon of the filter panel
        model.getFilterSwitcherModel().addValueChangeListener(filterSwitcherIconChangeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if((Boolean) model.getFilterSwitcherModel().getValue() == true){
                    bFilterSwitcher.setIcon(enabledFilterPanelIcon);
                } else {
                    bFilterSwitcher.setIcon(disabledFilterPanelIcon);
                }
            }
        });
        if((Boolean) model.getFilterSwitcherModel().getValue() == true){
            bFilterSwitcher.setIcon(enabledFilterPanelIcon);
        } else {
            bFilterSwitcher.setIcon(disabledFilterPanelIcon);
        }

        // Hide the filter panel if the filter is disabled
        model.getFilterEnabledModel().addValueChangeListener(filterEnabledChangeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                pFilterHeader.setVisible((Boolean)model.getFilterEnabledModel().getValue());
                if(!pFilterHeader.isVisible()) {
                    pFilter.setVisible((Boolean)model.getFilterEnabledModel().getValue());
                }
            }
        });
        pFilterHeader.setVisible((Boolean)model.getFilterEnabledModel().getValue());
        if(!pFilterHeader.isVisible()) {
            pFilter.setVisible((Boolean)model.getFilterEnabledModel().getValue());
        }
    }

    private void buildView() {
        this.setLayout(new BorderLayout());

        // Build the filter
        StringBuilder filterRowBuilder = new StringBuilder();
        List<CustomerSelectorFilterExtentionPointHelper> filterExtentionsHelper = model.getFilterExtentionsHelper();

        for(int i=0, l=filterExtentionsHelper.size(); i<l; i++) {
            if(i == 0) {
                filterRowBuilder.append("pref");
            } else {
                filterRowBuilder.append(", 2dlu, pref");
            }
        }

        CellConstraints cc = new CellConstraints();

        // Create the header panel of the filter
        FormLayout filterHeaderLayout = new FormLayout(
                "fill:pref:grow, pref",
                "pref");
        PanelBuilder filterHeaderBuilder = new PanelBuilder(filterHeaderLayout, pFilterHeader);

        filterHeaderBuilder.add(bFilterSwitcher, cc.xy(1, 1));
        filterHeaderBuilder.add(bFilterChooser, cc.xy(2, 1));


        // Create the filter panel
        FormLayout filterLayout = new FormLayout(
                "fill:pref:grow",
                filterRowBuilder.toString());
        PanelBuilder filterBuilder = new PanelBuilder(filterLayout, pFilter);
        
        for(int i=0, l=filterExtentionsHelper.size(); i<l; i++) {

            // get the extentionHelper
            final CustomerSelectorFilterExtentionPointHelper exHelper = filterExtentionsHelper.get(i);

            // get the view
            final CWPanel view = exHelper.getExtention().getView();

            // Create the filter panel for this filter
            final CWPanel pFilterEx = CWComponentFactory.createPanel();

            // add the visibility action to the panel for this filter
            PropertyChangeListener activeChangeListener;
            exHelper.getActiveModel().addValueChangeListener(activeChangeListener = new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    pFilterEx.setVisible((Boolean) exHelper.getActiveModel().getValue());
                }
            });
            pFilterEx.setVisible((Boolean) exHelper.getActiveModel().getValue());
            valueChangeListenerDisposeMap.put(exHelper.getActiveModel(), activeChangeListener);

            // Add the disable button
            CWButton disableButton = CWComponentFactory.createButton(new AbstractAction("", CWUtils.loadIcon("cw/boardingschoolmanagement/images/filter_remove.png")) {
                public void actionPerformed(ActionEvent e) {
                    exHelper.getActiveModel().setValue(false);
                }
            });
            componentContainer.addComponent(disableButton);

            // Create the filter panel
            FormLayout filterExLayout = new FormLayout(
                    "2dlu, fill:pref:grow, 4dlu, pref, 2dlu",
                    "pref");
            PanelBuilder filterExBuilder = new PanelBuilder(filterExLayout, pFilterEx);

            // add the view and the remove button to the builder
            filterExBuilder.add(view, cc.xy(2, 1 ) );
            filterExBuilder.add(disableButton, cc.xy(4, 1 ) );

            // Add the filter panel to the main filter panel
            filterBuilder.add(pFilterEx, cc.xy(1, i*2+1 ) );
        }


        // Create the main layout
        FormLayout layout = new FormLayout(
                "fill:pref:grow",
                "pref, pref, 2dlu, pref, 2dlu, fill:pref:grow");
        PanelBuilder builder = new PanelBuilder(layout);

        builder.add(pFilterHeader, cc.xy(1, 1));
        builder.add(pFilter, cc.xy(1, 2));
        builder.addLabel("Kunden:", cc.xy(1, 4));
        builder.add(CWComponentFactory.createScrollPane(tCustomers), cc.xy(1, 6));

        JPanel p;
        p = builder.getPanel();
        p.setOpaque(false);

        this.add(p, BorderLayout.CENTER);
    }

    public void dispose() {
        bFilterChooser.removeActionListener(filterChooserActionListener);
        model.getFilterSwitcherModel().removeValueChangeListener(filterSwitcherChangeListener);
        model.getFilterSwitcherModel().removeValueChangeListener(filterSwitcherIconChangeListener);
        model.getFilterChooserAction().removePropertyChangeListener(filterEnabledChangeListener);
        model.getActiveFilterCountModel().removeValueChangeListener(filterSwitcherPanelCountChangeListener);

        Iterator<Entry<ValueModel, PropertyChangeListener>> iterator = valueChangeListenerDisposeMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Entry<ValueModel, PropertyChangeListener> next = iterator.next();
            next.getKey().removeValueChangeListener(next.getValue());
        }

        componentContainer.dispose();

        model.dispose();
    }

    private void showFilterPopup() {
        popFilterChooser.show(bFilterChooser, 0, bFilterChooser.getHeight());
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

            table.setRowHeight(row, (int) getPreferredSize().getHeight());

            return this;
        }
    }
}
