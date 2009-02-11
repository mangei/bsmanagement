package cw.customermanagementmodul.gui;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.manager.ModulManager;
import cw.customermanagementmodul.extentions.interfaces.CustomerSelectorFilterExtention;
import java.beans.PropertyChangeEvent;
import java.util.Date;
import java.util.List;
import javax.swing.ListModel;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Group;
import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * @author CreativeWorkers.at
 */
public class CustomerSelectorPresentationModel {

    private CustomerTableModel customerTableModel;
    private SelectionInList<Customer> customerSelection;
    private List<CustomerSelectorFilterExtention> extentions;
    private ValueModel filterChange;
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel westPanel;
    private JPanel eastPanel;

    private ArrayList<Customer> customers;
    private boolean filtering;

    private static final String defaultCustomerTableStateName = "cw.customerboardingmanagement.CustomerSelectorView.customerTableState";
    private String customerTableStateName = "cw.customerboardingmanagement.CustomerSelectorView.customerTableState";

    public CustomerSelectorPresentationModel() {
        this(null, true, defaultCustomerTableStateName);
    }

    public CustomerSelectorPresentationModel(List<Customer> customers, String customerTableStateName) {
        this(customers, true, customerTableStateName);
    }

    public CustomerSelectorPresentationModel(List<Customer> customers) {
        this(customers, true, defaultCustomerTableStateName);
    }

    public CustomerSelectorPresentationModel(List<Customer> customers, boolean filtering) {
        this(customers, filtering, defaultCustomerTableStateName);
    }

    public CustomerSelectorPresentationModel(List<Customer> customers, boolean filtering, String customerTableStateName) {
        this.customers = new ArrayList(customers);
        this.filtering = filtering;
        this.customerTableStateName = customerTableStateName;

        initModels();
        initExtentions();
        initEventHandling();
    }

    public void initModels() {
        filterChange = new ValueHolder(false);

        if(customers != null) {
            customerSelection = new SelectionInList<Customer>((ArrayList)customers.clone());
        } else {
            customers = new ArrayList<Customer>();
            customerSelection = new SelectionInList<Customer>();
        }

//        customerTableModel = new CustomerTableModel(customerSelection);
    }

    private void initEventHandling() {

        if(filtering == true) {
        
            // If a filter has changed, reload the table
            filterChange.addValueChangeListener(new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {

                    // If there is really a change
                    if(evt.getNewValue() == Boolean.FALSE) { return; }

                    List<Customer> filteredCustomers = new ArrayList<Customer>();
                    filteredCustomers.addAll((ArrayList)customers.clone());

                    System.out.println("customers: " + customers.size());

                    // Filter the elements
                    for (CustomerSelectorFilterExtention ex : extentions) {
                        filteredCustomers = ex.filter(filteredCustomers);
                    }

                    // Delete
                    int size = customerSelection.getList().size();
                    if(size > 0) {
                        customerSelection.getList().clear();
                        customerSelection.fireIntervalRemoved(0, size-1);
                        customerTableModel.fireTableRowsDeleted(0, size-1);
                    }

                    // Add
                    customerSelection.getList().addAll(filteredCustomers);
                    size = customerSelection.getList().size();
                    if(size > 0) {
                        customerSelection.fireIntervalAdded(0, size-1);
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

    public TableModel createCustomerTableModel(ListModel listModel) {
        return customerTableModel = new CustomerTableModel(listModel);
    }

    public void setCustomers(List<Customer> customers) {

        // Delete
        int size = customerSelection.getList().size();
        if(size > 0) {
            customerSelection.getList().clear();
            customerSelection.fireIntervalRemoved(0, size-1);
            customerTableModel.fireTableRowsDeleted(0, size-1);
        }

        // Add
        if(customers != null) {
            customerSelection.getList().addAll(customers);
            size = customerSelection.getList().size();
            if(size > 0) {
                customerSelection.fireIntervalAdded(0, size-1);
                customerTableModel.fireTableRowsInserted(0, size-1);
            }
            this.customers = new ArrayList<Customer>(customers);
        } else {
            this.customers = new ArrayList<Customer>();
        }

    }

    ////////////////////////////////////////////////////////////////////////////
    // Methods
    ////////////////////////////////////////////////////////////////////////////
    public void add(Customer c) {
        customerSelection.getList().add(c);

        int idx = customerSelection.getList().indexOf(c);
        customerSelection.fireIntervalAdded(idx, idx);
        customerTableModel.fireTableRowsInserted(idx, idx);

        this.customers.add(c);
    }

    public void remove(Customer c) {
        int idx = customerSelection.getList().indexOf(c);
        customerSelection.getList().remove(c);
        customerSelection.fireIntervalRemoved(idx, idx);
        customerTableModel.fireTableRowsDeleted(idx, idx);

        this.customers.remove(c);
    }

    public void remove(List<Customer> list) {
        for(int i=0, l=list.size(); i<l; i++) {
            remove(list.get(i));
        }
    }

    public Customer getSelectedCustomer() {
        return customerSelection.getSelection();
    }

    public SelectionInList<Customer> getCustomerSelection() {
        return customerSelection;
    }

    public String getCustomerTableStateName() {
        return customerTableStateName;
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
