package cw.customermanagementmodul.group.gui;

import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.customermanagementmodul.group.persistence.Group;
import cw.customermanagementmodul.group.persistence.PMGroup;

/**
 * @author CreativeWorkers.at
 */
public class GroupCustomerSelectorFilterExtentionPresentationModel
 	extends CWPresentationModel {

    private DefaultListModel groupSelection;
    private DefaultListSelectionModel groupSelectionModel;

    private ListSelectionListener groupSelectionListener;

    public GroupCustomerSelectorFilterExtentionPresentationModel(EntityManager entityManager) {
    	super(entityManager);
        initModels();
        initEventHandling();
    }

    public void initModels() {
        groupSelection = new DefaultListModel();
        List<Group> groupList = PMGroup.getInstance().getAll(getEntityManager());
        for (int i = 0, l = groupList.size(); i < l; i++) {
            groupSelection.addElement(groupList.get(i));
        }

        groupSelectionModel = new DefaultListSelectionModel();
        groupSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        groupSelection.add(0, "Alle");
        groupSelectionModel.setSelectionInterval(0, 0);
    }

    private void initEventHandling() {

        groupSelectionModel.addListSelectionListener(groupSelectionListener = new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {

//                // Get the number of customers
//                int size = customerListModel.size();
//
//                // If there are customers in the list, clear the list
//                if (size > 0) {
//                    customerListModel.clear();
//                    try {
//                        customerTableModel.fireTableRowsDeleted(0, size - 1);
//                    } catch (java.lang.IndexOutOfBoundsException e1) {
//                        // FIX IT, This should no happen
//                    }
//                }

                // If there is no selection or 'All' is selected
                if (groupSelectionModel.isSelectionEmpty() || groupSelectionModel.isSelectedIndex(0)) {

//                    customerListModel.addAll(CustomerManager.getCustomers());

                    // If there is no selection, select 'All'
                    if (groupSelectionModel.isSelectionEmpty()) {
                        groupSelectionModel.setSelectionInterval(0, 0);
                    }

                } else {
//
//                    // Get the bound of the selection
//                    int iMin = groupSelectionModel.getMinSelectionIndex();
//                    int iMax = groupSelectionModel.getMaxSelectionIndex();
//
//                    // If there is no selection, stop it
//                    if ((iMin < 0) || (iMax < 0)) {
//                        return;
//                    }
//
//                    Group g;
//                    Customer c;
//                    List<Customer> customers;
//                    for (int i = iMin; i <= iMax; i++) {
//                        if (groupSelectionModel.isSelectedIndex(i)) {
//                            g = (Group) groupSelection.get(i);
//                            customers = g.getCustomers();
//
//                            for (int j = 0, l = customers.size(); j < l; j++) {
//                                c = customers.get(j);
//                                if (!customerListModel.contains(c)) {
//                                    customerListModel.add(c);
//                                }
//                            }
//                        }
//                    }
                }
//
//                size = customerListModel.size();
//                if (size > 0) {
//                    customerTableModel.fireTableRowsInserted(0, size - 1);
//                }
            }
        });

 
    }

    public void dispose() {
        groupSelectionModel.removeListSelectionListener(groupSelectionListener);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////


    
    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////

    public ListModel getGroupSelection() {
        return groupSelection;
    }

    public DefaultListSelectionModel getGroupSelectionModel() {
        return groupSelectionModel;
    }


    ////////////////////////////////////////////////////////////////////////////
    // Other classes
    ////////////////////////////////////////////////////////////////////////////

    
}
