package cw.customermanagementmodul.gui;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.gui.model.ExtendedListModel;
import cw.boardingschoolmanagement.gui.model.ExtendedListSelectionModel;
import cw.boardingschoolmanagement.manager.ModulManager;
import cw.customermanagementmodul.extentions.interfaces.CustomerSelectorFilterExtention;
import java.beans.PropertyChangeEvent;
import javax.swing.Action;
import javax.swing.table.TableModel;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Group;
import cw.customermanagementmodul.pojo.manager.CustomerManager;
import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;
import org.jdesktop.swingx.JXTable;

/**
 * @author CreativeWorkers.at
 */
public class CustomerSelectorPresentationModel {

    private Action sucheButtonAction;
    private CustomerTableModel customerTableModel;
    private ExtendedListModel customerListModel;
    private ExtendedListSelectionModel customerSelectionModel;
    private List<CustomerSelectorFilterExtention> extentions;
    private ValueModel filterChange;
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel westPanel;
    private JPanel eastPanel;

    private List<Customer> customers;
    private int selectionMode;
    private boolean filtering;

    private static final String defaultCustomerTableStateName = "cw.customerboardingmanagement.CustomerSelectorView.customerTableState";
    private String customerTableStateName = "cw.customerboardingmanagement.CustomerSelectorView.customerTableState";

    public CustomerSelectorPresentationModel() {
        this(null, ListSelectionModel.SINGLE_SELECTION, true, defaultCustomerTableStateName);
    }

    public CustomerSelectorPresentationModel(int selectionMode) {
        this(null, selectionMode, true, defaultCustomerTableStateName);
    }

    public CustomerSelectorPresentationModel(int selectionMode, String customerTableStateName) {
        this(null, selectionMode, true, customerTableStateName);
    }

    public CustomerSelectorPresentationModel(List<Customer> customers) {
        this(customers, ListSelectionModel.SINGLE_SELECTION, true, defaultCustomerTableStateName);
    }

    public CustomerSelectorPresentationModel(List<Customer> customers, boolean filtering) {
        this(customers, ListSelectionModel.SINGLE_SELECTION, filtering, defaultCustomerTableStateName);
    }

    public CustomerSelectorPresentationModel(List<Customer> customers, boolean filtering, String customerTableStateName) {
        this(customers, ListSelectionModel.SINGLE_SELECTION, filtering, customerTableStateName);
    }

    public CustomerSelectorPresentationModel(List<Customer> customers, int selectionMode, boolean filtering, String customerTableStateName) {
        this.customers = customers;
        this.selectionMode = selectionMode;
        this.filtering = filtering;
        this.customerTableStateName = customerTableStateName;

        initModels();
        initExtentions();
        initEventHandling();
    }

    public void initModels() {
        filterChange = new ValueHolder(false);

        customerListModel = new ExtendedListModel();

        if(customers != null) {
            customerListModel.addAll(customers);
        } else {
            customerListModel.addAll(CustomerManager.getInstance().getAll());
        }

        customerTableModel = new CustomerTableModel(customerListModel);

        customerSelectionModel = new ExtendedListSelectionModel();
        customerSelectionModel.setSelectionMode(selectionMode);
    }

    private void initEventHandling() {

        if(filtering == true) {
        
            // If a filter has changed, reload the table
            filterChange.addValueChangeListener(new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {

                    // If there is really a change
                    if(evt.getNewValue() == Boolean.FALSE) { return; }

                    List<Customer> customers = new ArrayList<Customer>();
                    customers.addAll(CustomerManager.getInstance().getAll());

                    // Filter the elements
                    for (CustomerSelectorFilterExtention ex : extentions) {
                        customers = ex.filter(customers);
                    }

                    // Delete
                    int size = customerListModel.size();
                    if(size > 0) {
                        customerListModel.removeAllElements();
                        customerTableModel.fireTableRowsDeleted(0, size-1);
                    }

                    // Add
                    customerListModel.addAll(customers);
                    size = customerListModel.size();
                    if(size > 0) {
                        customerTableModel.fireTableRowsInserted(0, size-1);
                    }

                    filterChange.setValue(false);
                }
            });

            for (CustomerSelectorFilterExtention ex : extentions) {
                ex.initEventHandling();
            }
            
        }
    }

    private void initExtentions() {

        if(filtering) {

            // Load extentions
            extentions = (List<CustomerSelectorFilterExtention>) ModulManager.getExtentions(CustomerSelectorFilterExtention.class);

            for (CustomerSelectorFilterExtention ex : extentions) {

                ex.init(filterChange);

                if (ex.getPosition().equals(BorderLayout.NORTH)) {
                    northPanel = ex.getPanel();
                }
                if (ex.getPosition().equals(BorderLayout.SOUTH)) {
                    southPanel = ex.getPanel();
                }
                if (ex.getPosition().equals(BorderLayout.WEST)) {
                    westPanel = ex.getPanel();
                }
                if (ex.getPosition().equals(BorderLayout.EAST)) {
                    eastPanel = ex.getPanel();
                }
            }
            
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////
    public TableModel getCustomerTableModel(ListModel listModel) {
        return new CustomerTableModel(listModel);
    }

    public Action getSucheButtonAction() {
        return sucheButtonAction;
    }

    public CustomerTableModel getCustomerTableModel() {
        return customerTableModel;
    }

    public DefaultListSelectionModel createCustomerSelectionModel(JXTable table) {
        ExtendedListSelectionModel newCustomerSelectionModel = new ExtendedListSelectionModel(table);
        newCustomerSelectionModel.setSelectionMode(selectionMode);

        // Transfer the old listeners to the new one
        for(ListSelectionListener l : customerSelectionModel.getListSelectionListeners()) {
            newCustomerSelectionModel.addListSelectionListener(l);
        }

        customerSelectionModel = newCustomerSelectionModel;

        return newCustomerSelectionModel;
    }

    public int getSelectionMode() {
        return selectionMode;
    }


    public void setCustomers(List<Customer> customers) {

        System.out.println("size: " + customers.size());

        // Delete
        int size = customerListModel.size();
        if(size > 0) {
            customerListModel.removeAllElements();
            customerTableModel.fireTableRowsDeleted(0, size-1);
        }

        // Add
        customerListModel.addAll(customers);
        size = customerListModel.size();
        if(size > 0) {
            customerTableModel.fireTableRowsInserted(0, size-1);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Methods
    ////////////////////////////////////////////////////////////////////////////
    public void add(Customer c) {
        customerListModel.add(c);
    }

    public void remove(Customer c) {
        customerListModel.remove(c);
        customerSelectionModel.setSelectionInterval(-1, -1);
    }

    public void remove(List<Customer> list) {
        for(int i=0, l=list.size(); i<l; i++) {
            remove(list.get(i));
        }
    }

    public void remove(int idx) {
        customerListModel.remove(idx);
    }

    public Customer getSelectedCustomer() {
        if (customerSelectionModel.isSelectionEmpty()) {
            return null;
        }

        return (Customer) customerListModel.get(customerSelectionModel.getSelectedIndex());
    }

    public List<Customer> getSelectedCustomers() {
        List<Customer> list = new ArrayList<Customer>();

        int[] indizes = customerSelectionModel.getSelectedIndizes();

        for (int i = 0; i < indizes.length; i++) {
            list.add((Customer) customerListModel.get(indizes[i]));
        }

        return list;
    }

    public boolean isSelectionEmpty() {
        return customerSelectionModel.isSelectionEmpty();
    }

    public int getSelectedCount() {
        return customerSelectionModel.getSelectedCount();
    }

    public String getCustomerTableStateName() {
        return customerTableStateName;
    }

    public void removeListSelectionListener(ListSelectionListener l) {
        customerSelectionModel.removeListSelectionListener(l);
    }

    public void addListSelectionListener(ListSelectionListener l) {
        customerSelectionModel.addListSelectionListener(l);
    }

    public JPanel getNorthPanel() {
        return northPanel;
    }

    public JPanel getSouthPanel() {
        return southPanel;
    }

    public JPanel getWestPanel() {
        return westPanel;
    }

    public JPanel getEastPanel() {
        return eastPanel;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Other classes
    ////////////////////////////////////////////////////////////////////////////
    public class NoGroup extends Group {

        private String name;

        public NoGroup(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class CustomerTableModel extends AbstractTableModel {

        private ListModel listModel;

        public CustomerTableModel(ListModel listModel) {
            this.listModel = listModel;
        }


        public int getRowCount() {
            return listModel.getSize();
        }

        @Override
        public int getColumnCount() {
            return 15;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Anrede";
                case 1:
                    return "Vorname";
                case 2:
                    return "Vorname2";
                case 3:
                    return "Nachname";
                case 4:
                    return "Geburtsdatum";
                case 5:
                    return "Adresse";
                case 6:
                    return "PLZ";
                case 7:
                    return "Ort";
                case 8:
                    return "Bundesland";
                case 9:
                    return "Staat";
                case 10:
                    return "Mobiltelefon";
                case 11:
                    return "Festnetztelefon";
                case 12:
                    return "Fax";
                case 13:
                    return "eMail";
                case 14:
                    return "Bemerkung";
                default:
                    return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 4:
                    return Date.class;
                case 6:
                    return Integer.class;
                default:
                    return String.class;
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Customer c = (Customer) listModel.getElementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return c.getTitle();
                case 1:
                    return c.getForename();
                case 2:
                    return c.getForename2();
                case 3:
                    return c.getSurname();
                case 4:
                    return c.getBirthday();
                case 5:
                    return c.getStreet();
                case 6:
                    return c.getPostOfficeNumber();
                case 7:
                    return c.getCity();
                case 8:
                    return c.getProvince();
                case 9:
                    return c.getCountry();
                case 10:
                    return c.getMobilephone();
                case 11:
                    return c.getLandlinephone();
                case 12:
                    return c.getFax();
                case 13:
                    return c.getEmail();
                case 14:
                    return c.getComment();
                default:
                    return "";
            }
        }

    }
}
