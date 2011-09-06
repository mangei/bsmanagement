package cw.customermanagementmodul.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout.Group;
import javax.swing.ListModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.boardingschoolmanagement.manager.ModulManager;
import cw.customermanagementmodul.extention.point.CustomerSelectorFilterExtentionPoint;
import cw.customermanagementmodul.persistence.Customer;

/**
 * Diese Klasse beinhaltet alle Funktionen und Methoden die für
 * die Kundenübersichtstabelle der Kundenverwaltung benötigt werden.
 *
 * @author CreativeWorkers.at
 */
public class CustomerSelectorPresentationModel
	extends CWPresentationModel {

    private CustomerTableModel customerTableModel;
    private SelectionInList<Customer> customerSelection;
    private List<CustomerSelectorFilterExtentionPoint> filterExtentions;
    private List<CustomerSelectorFilterExtentionPointHelper> filterExtentionsHelper;
    private ValueModel filterChange;
    private Action filterSwitcherAction;
    private ValueModel filterSwitcherModel;
    private Action filterChooserAction;
    private ArrayList<Customer> customers;
    private ValueModel filterEnabledModel;
    private ValueModel activeFilterCountModel;

    private PropertyChangeListener filterChangeListener;
    private HashMap<ValueModel, PropertyChangeListener> valueChangeListenerDisposeMap;

    private static final String defaultCustomerTableStateName = "cw.customerboardingmanagement.CustomerSelectorView.customerTableState";
    private String customerTableStateName = "cw.customerboardingmanagement.CustomerSelectorView.customerTableState";

    public CustomerSelectorPresentationModel(EntityManager entityManager) {
        this(null, true, defaultCustomerTableStateName, entityManager);
    }

    public CustomerSelectorPresentationModel(List<Customer> customers, String customerTableStateName, EntityManager entityManager) {
        this(customers, true, customerTableStateName, entityManager);
    }

    public CustomerSelectorPresentationModel(List<Customer> customers, EntityManager entityManager) {
        this(customers, true, defaultCustomerTableStateName, entityManager);
    }

    public CustomerSelectorPresentationModel(List<Customer> customers, boolean filtering, EntityManager entityManager) {
        this(customers, filtering, defaultCustomerTableStateName, entityManager);
    }

    public CustomerSelectorPresentationModel(List<Customer> customers, boolean filtering, String customerTableStateName, EntityManager entityManager) {
        super(entityManager);
    	this.customers = new ArrayList(customers);
        this.filterEnabledModel = new ValueHolder(filtering);
        this.customerTableStateName = customerTableStateName;

        initModels();
        initExtentions();
        initEventHandling();
    }

    public void initModels() {
        filterChange = new ValueHolder(false);
        filterExtentionsHelper = new ArrayList<CustomerSelectorFilterExtentionPointHelper>();
        valueChangeListenerDisposeMap = new HashMap<ValueModel, PropertyChangeListener>();

        if(customers != null) {
            customerSelection = new SelectionInList<Customer>((ArrayList)customers.clone());
        } else {
            customers = new ArrayList<Customer>();
            customerSelection = new SelectionInList<Customer>();
        }

//        customerTableModel = new CustomerTableModel(customerSelection);

        filterSwitcherAction = new FilterSwitcherAction("Filter ein-/ausblenden");
        filterChooserAction = new FilterChooserAction("Filter auswählen...");

        filterSwitcherModel = new ValueHolder(false);
        activeFilterCountModel = new ValueHolder(0);
    }

    private void initEventHandling() {

        if(filterEnabledModel.getValue() == Boolean.TRUE) {
        
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

        if((Boolean)filterEnabledModel.getValue()) {

            // Load extentions
            filterExtentions = (List<CustomerSelectorFilterExtentionPoint>) ModulManager.getExtentions(CustomerSelectorFilterExtentionPoint.class);

            for (CustomerSelectorFilterExtentionPoint ex : filterExtentions) {

                CustomerSelectorFilterExtentionPointHelper helper = new CustomerSelectorFilterExtentionPointHelper(ex);
                filterExtentionsHelper.add(helper);

                ex.init(filterChange);

                // Init the activeFilterCount
                PropertyChangeListener activeFilterCountListener;
                helper.getActiveModel().addValueChangeListener(activeFilterCountListener = new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent evt) {
                        if((Boolean)evt.getNewValue() == true) {
                            activeFilterCountModel.setValue(((Integer)activeFilterCountModel.getValue()) + 1);
                        } else {
                            activeFilterCountModel.setValue(((Integer)activeFilterCountModel.getValue()) - 1);
                        }
                    }
                });
                valueChangeListenerDisposeMap.put(helper.getActiveModel(), activeFilterCountListener);
                if((Boolean)helper.getActiveModel().getValue() == true) {
                    activeFilterCountModel.setValue(((Integer)activeFilterCountModel.getValue()) + 1);
                }

            }
        }
        else {
            filterExtentions = new ArrayList();
        }
    }

    public void dispose() {
        if(filterChange != null) {
            filterChange.removeValueChangeListener(filterChangeListener);
        }
        customerSelection.release();

        for (CustomerSelectorFilterExtentionPoint ex : filterExtentions) {
            ex.dispose();
        }
        filterExtentions.clear();

        Iterator<Entry<ValueModel, PropertyChangeListener>> iterator = valueChangeListenerDisposeMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Entry<ValueModel, PropertyChangeListener> next = iterator.next();
            next.getKey().removeValueChangeListener(next.getValue());
        }
    }

    public void updateFilter() {
        if((Boolean)filterEnabledModel.getValue()) {

            List<Customer> filteredCustomers = new ArrayList<Customer>();
            filteredCustomers.addAll((ArrayList)customers.clone());

            // Filter the elements, if the filter is active
            for (CustomerSelectorFilterExtentionPointHelper exHelper : filterExtentionsHelper) {
                if((Boolean) exHelper.getActiveModel().getValue() == true) {
                    filteredCustomers = exHelper.getExtention().filter(filteredCustomers);
                }
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

    public Action getFilterChooserAction() {
        return filterChooserAction;
    }

    public Action getFilterSwitcherAction() {
        return filterSwitcherAction;
    }

    public ValueModel getFilterSwitcherModel() {
        return filterSwitcherModel;
    }

    public List<CustomerSelectorFilterExtentionPointHelper> getFilterExtentionsHelper() {
        return filterExtentionsHelper;
    }

    public ValueModel getFilterEnabledModel() {
        return filterEnabledModel;
    }

    public ValueModel getActiveFilterCountModel() {
        return activeFilterCountModel;
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
                case 15:
                    return "Status";
                case 16:
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
                case 15:
                    return Boolean.class;
                case 16:
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
                case 15:
                    return c.isActive();
                case 16:
                    return c.getInactiveDate();
                default:
                    return "";
            }
        }

    }

    public class CustomerSelectorFilterExtentionPointHelper {

        private ValueModel activeModel;
        private CustomerSelectorFilterExtentionPoint extention;
        private Action activeAction;

        public CustomerSelectorFilterExtentionPointHelper(CustomerSelectorFilterExtentionPoint extention) {
            this.extention = extention;
            this.activeModel = new ValueHolder(false);
            this.activeAction = new AbstractAction(extention.getFilterName()) {
                public void actionPerformed(ActionEvent e) {
                    if((Boolean)activeModel.getValue() == true) {
                        activeModel.setValue(false);
                    } else {
                        activeModel.setValue(true);
                    }
                }
            };
        }

        public ValueModel getActiveModel() {
            return activeModel;
        }

        public Action getActiveAction() {
            return activeAction;
        }

        public CustomerSelectorFilterExtentionPoint getExtention() {
            return extention;
        }

    }

    private class FilterSwitcherAction extends AbstractAction {

        public FilterSwitcherAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            if((Boolean) filterSwitcherModel.getValue() == true) {
                filterSwitcherModel.setValue(false);
            } else {
                filterSwitcherModel.setValue(true);
            }
        }

    }

    private class FilterChooserAction extends AbstractAction {

        public FilterChooserAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
        }

    }
}
