package cw.customermanagementmodul.gui;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.manager.ModulManager;
import cw.customermanagementmodul.extention.point.CustomerSelectorFilterExtentionPoint;
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
public class CustomerSelectorPresentationModel
{

    private CustomerTableModel customerTableModel;
    private SelectionInList<Customer> customerSelection;
    private List<CustomerSelectorFilterExtentionPoint> filterExtentions;
    private ValueModel filterChange;
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel westPanel;
    private JPanel eastPanel;

    private ArrayList<Customer> customers;
    private boolean filtering;

    private PropertyChangeListener filterChangeListener;

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
            filterChange.addValueChangeListener(filterChangeListener = new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {

                    // If there is really a change
                    if(evt.getNewValue() == Boolean.FALSE) { return; }

                    updateFilter();
                }
            });

            for (CustomerSelectorFilterExtentionPoint ex : filterExtentions) {
                ex.initEventHandling();
            }
            
        }
    }

    private void initExtentions() {

        if(filtering) {

            // Load extentions
            filterExtentions = (List<CustomerSelectorFilterExtentionPoint>) ModulManager.getExtentions(CustomerSelectorFilterExtentionPoint.class);

            for (CustomerSelectorFilterExtentionPoint ex : filterExtentions) {

                ex.init(filterChange);

                if (ex.getPosition().equals(BorderLayout.NORTH)) {
                    northPanel = ex.getView();
                }
                if (ex.getPosition().equals(BorderLayout.SOUTH)) {
                    southPanel = ex.getView();
                }
                if (ex.getPosition().equals(BorderLayout.WEST)) {
                    westPanel = ex.getView();
                }
                if (ex.getPosition().equals(BorderLayout.EAST)) {
                    eastPanel = ex.getView();
                }
            }
        }
        else {
            filterExtentions = new ArrayList();
        }
    }

    public void dispose() {
        System.out.println("Dispose CustomerSELECTOR");
        if(filterChange != null) {
            filterChange.removeValueChangeListener(filterChangeListener);
        }
        customerSelection.release();

        for (CustomerSelectorFilterExtentionPoint ex : filterExtentions) {
            ex.dispose();
        }
        filterExtentions.clear();
    }

    public void updateFilter() {
        if(filtering) {

            List<Customer> filteredCustomers = new ArrayList<Customer>();
            filteredCustomers.addAll((ArrayList)customers.clone());

            // Filter the elements
            for (CustomerSelectorFilterExtentionPoint ex : filterExtentions) {
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

        updateFilter();
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

        updateFilter();
    }

    public void remove(Customer c) {
        int idx = customerSelection.getList().indexOf(c);
        customerSelection.getList().remove(c);
        customerSelection.fireIntervalRemoved(idx, idx);
        customerTableModel.fireTableRowsDeleted(idx, idx);

        this.customers.remove(c);

        updateFilter();
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
            return 18;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Anrede";
                case 1:
                    return "Titel";
                case 2:
                    return "Vorname";
                case 3:
                    return "Vorname2";
                case 4:
                    return "Nachname";
                case 5:
                    return "Geburtsdatum";
                case 6:
                    return "Adresse";
                case 7:
                    return "PLZ";
                case 8:
                    return "Ort";
                case 9:
                    return "Bundesland";
                case 10:
                    return "Staat";
                case 11:
                    return "Mobiltelefon";
                case 12:
                    return "Festnetztelefon";
                case 13:
                    return "Fax";
                case 14:
                    return "eMail";
                case 15:
                    return "Bemerkung";
                case 16:
                    return "Status";
                case 17:
                    return "Inaktiv seit";
                default:
                    return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return Boolean.class;
                case 5:
                    return Date.class;
                case 16:
                    return Boolean.class;
                case 17:
                    return Date.class;
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
                    return c.getGender();
                case 1:
                    return c.getTitle();
                case 2:
                    return c.getForename();
                case 3:
                    return c.getForename2();
                case 4:
                    return c.getSurname();
                case 5:
                    return c.getBirthday();
                case 6:
                    return c.getStreet();
                case 7:
                    return c.getPostOfficeNumber();
                case 8:
                    return c.getCity();
                case 9:
                    return c.getProvince();
                case 10:
                    return c.getCountry();
                case 11:
                    return c.getMobilephone();
                case 12:
                    return c.getLandlinephone();
                case 13:
                    return c.getFax();
                case 14:
                    return c.getEmail();
                case 15:
                    return c.getComment();
                case 16:
                    return c.isActive();
                case 17:
                    return c.getInactiveDate();
                default:
                    return "";
            }
        }

    }
}
