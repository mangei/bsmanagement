package cw.customermanagementmodul.customer.gui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.swing.Action;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.app.CWAction;
import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.boardingschoolmanagement.gui.model.CWDataModel;
import cw.boardingschoolmanagement.manager.ModuleManager;
import cw.customermanagementmodul.customer.extention.point.CustomerSelectorFilterExtentionPoint;
import cw.customermanagementmodul.customer.gui.model.CustomerDataModel;
import cw.customermanagementmodul.customer.persistence.Customer;

/**
 * Diese Klasse beinhaltet alle Funktionen und Methoden die fuer
 * die Kundenuebersichtstabelle der Kundenverwaltung benoetigt werden.
 *
 * @author CreativeWorkers.at
 */
public class CustomerSelectorPresentationModel
	extends CWPresentationModel {

    private CustomerDataModel customerTableModel;
//    private SelectionInList<Customer> customerDataModel;
    private CWDataModel<Customer> customerDataModel;
    private List<CustomerSelectorFilterExtentionPoint> filterExtentions;
    private List<CustomerSelectorFilterExtentionPointHelper> filterExtentionsHelper;
    private ValueModel filterChange;
    private CWAction filterSwitcherAction;
    private ValueModel filterSwitcherModel;
    private CWAction filterChooserAction;
    private ArrayList<Customer> customers;
    private ValueModel filterEnabledModel;
    private ValueModel activeFilterCountModel;

    private PropertyChangeListener filterChangeListener;
    private HashMap<ValueModel, PropertyChangeListener> valueChangeListenerDisposeMap;

    private static final String defaultCustomerTableStateName = "cw.customerboardingmanagement.CustomerSelectorView.customerTableState";
    private String customerTableStateName = "cw.customerboardingmanagement.CustomerSelectorView.customerTableState";

    public CustomerSelectorPresentationModel(EntityManager entityManager) {
        this(new ArrayList<Customer>(), true, defaultCustomerTableStateName, entityManager);
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
    	this.customers = new ArrayList<Customer>(customers);
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
            customerDataModel = new CWDataModel<Customer>((List<Customer>)customers.clone(), Customer.class);
        } else {
            customers = new ArrayList<Customer>();
            customerDataModel = new CWDataModel<Customer>(customers, Customer.class);
        }

        filterSwitcherAction = new FilterSwitcherAction("Filter ein-/ausblenden");
        filterChooserAction = new FilterChooserAction("Filter auswaehlen...");

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
            filterExtentions = (List<CustomerSelectorFilterExtentionPoint>) ModuleManager.getExtentions(CustomerSelectorFilterExtentionPoint.class);

            for (CustomerSelectorFilterExtentionPoint ex : filterExtentions) {

                CustomerSelectorFilterExtentionPointHelper helper = new CustomerSelectorFilterExtentionPointHelper(ex);
                filterExtentionsHelper.add(helper);

                ex.init(filterChange, getEntityManager());

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

    public void release() {
        if(filterChange != null) {
            filterChange.removeValueChangeListener(filterChangeListener);
        }
//        customerDataModel.release();

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
            filteredCustomers.addAll((List)customers.clone());

            // Filter the elements, if the filter is active
            for (CustomerSelectorFilterExtentionPointHelper exHelper : filterExtentionsHelper) {
                if((Boolean) exHelper.getActiveModel().getValue() == true) {
                    filteredCustomers = exHelper.getExtention().filter(filteredCustomers);
                }
            }

            // Delete
            int size = customerDataModel.getSize();
            if(size > 0) {
                customerDataModel.clear();
//                customerDataModel.fireIntervalRemoved(0, size-1);
//                customerTableModel.fireTableRowsDeleted(0, size-1);
            }

            // Add
            customerDataModel.addAll(filteredCustomers);
            size = customerDataModel.getSize();
            if(size > 0) {
//                customerDataModel.fireIntervalAdded(0, size-1);
//                customerTableModel.fireTableRowsInserted(0, size-1);
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

//    public TableModel createCustomerTableModel(CWDataModel<Customer> listModel) {
//    	return customerTableModel = new CustomerDataModel(listModel);
//    }

    public void setCustomers(List<Customer> customers) {

        // Delete
        int size = customerDataModel.getSize();
        if(size > 0) {
            customerDataModel.clear();
//            customerDataModel.fireIntervalRemoved(0, size-1);
//            customerTableModel.fireTableRowsDeleted(0, size-1);
        }

        // Add
        if(customers != null) {
            customerDataModel.addAll(customers);
            size = customerDataModel.getSize();
            if(size > 0) {
//                customerDataModel.fireIntervalAdded(0, size-1);
//                customerTableModel.fireTableRowsInserted(0, size-1);
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
//    public void add(Customer c) {
//        customerDataModel.add(c);
//
//        int idx = customerDataModel.getList().indexOf(c);
////        customerDataModel.fireIntervalAdded(idx, idx);
////        customerTableModel.fireTableRowsInserted(idx, idx);
//
//        this.customers.add(c);
//
//        updateFilter();
//    }
//
//    public void remove(Customer c) {
//        int idx = customerDataModel.getList().indexOf(c);
//        customerDataModel.getList().remove(c);
////        customerDataModel.fireIntervalRemoved(idx, idx);
////        customerTableModel.fireTableRowsDeleted(idx, idx);
//
//        this.customers.remove(c);
//
//        updateFilter();
//    }
//
//    public void remove(List<Customer> list) {
//        for(int i=0, l=list.size(); i<l; i++) {
//            remove(list.get(i));
//        }
//    }

    public CWDataModel<Customer> getCustomerDataModel() {
        return customerDataModel;
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


    public class CustomerSelectorFilterExtentionPointHelper {

        private ValueModel activeModel;
        private CustomerSelectorFilterExtentionPoint extention;
        private CWAction activeAction;

        public CustomerSelectorFilterExtentionPointHelper(CustomerSelectorFilterExtentionPoint extention) {
            this.extention = extention;
            this.activeModel = new ValueHolder(false);
            this.activeAction = new CWAction(extention.getFilterName()) {
                public void action(ActionEvent e) {
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

    private class FilterSwitcherAction extends CWAction {

        public FilterSwitcherAction(String name) {
            super(name);
        }

        public void action(ActionEvent e) {
            if((Boolean) filterSwitcherModel.getValue() == true) {
                filterSwitcherModel.setValue(false);
            } else {
                filterSwitcherModel.setValue(true);
            }
        }

    }

    private class FilterChooserAction extends CWAction {

        public FilterChooserAction(String name) {
            super(name);
        }

        public void action(ActionEvent e) {
        }

    }
}
