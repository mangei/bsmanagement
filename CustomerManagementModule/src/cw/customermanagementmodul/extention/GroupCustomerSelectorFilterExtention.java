package cw.customermanagementmodul.extention;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.extention.point.CustomerSelectorFilterExtentionPoint;
import cw.customermanagementmodul.gui.GroupCustomerSelectorFilterExtentionPresentationModel;
import cw.customermanagementmodul.gui.GroupCustomerSelectorFilterExtentionView;
import cw.customermanagementmodul.persistence.Customer;
import cw.customermanagementmodul.persistence.Group;
import cw.customermanagementmodul.persistence.PMGroup;



/**
 *
 * @author ManuelG
 */
public class GroupCustomerSelectorFilterExtention
        implements CustomerSelectorFilterExtentionPoint {

    private GroupCustomerSelectorFilterExtentionPresentationModel model;
    private GroupCustomerSelectorFilterExtentionView view;
    private ValueModel change;
    private EntityManager entityManager;

    private ListSelectionListener changeListener;

    public void init(final ValueModel change, EntityManager entityManager) {
        this.change = change;
        this.entityManager = entityManager;

        model = new GroupCustomerSelectorFilterExtentionPresentationModel(entityManager);
        view = new GroupCustomerSelectorFilterExtentionView(model);
    }

    public void initEventHandling() {
        model.getGroupSelectionModel().addListSelectionListener(changeListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting() == false) {
                    change.setValue(true);
                }
            }
        });
    }

    public List<Customer> filter(List<Customer> costumers) {

        // Die Erweiterung die eine Aenderung feststellt, meldet dies an die Tabelle
        // diese ruft dann die filter-methode dieser Erweiterungen auf
        // Danach wird die Tabelle aktualisiert

        List<Group> groups = new ArrayList<Group>();


        // TODO Pruefen ob das so stimmt mit dem Gruppen auswaehlen


                                     //////////
                                     //      //
                                     //      //
                                     //      //
                                 //  //      //  //
                                  ///         ///
                                     ///    ///
                                        ///


        // If there is no selection or 'All' is selected
        if (model.getGroupSelectionModel().isSelectionEmpty() || model.getGroupSelectionModel().isSelectedIndex(0)) {

            return costumers;
            
        } else {

            // Get the bound of the selection
            int iMin = model.getGroupSelectionModel().getMinSelectionIndex();
            int iMax = model.getGroupSelectionModel().getMaxSelectionIndex();

            for (int i = iMin; i <= iMax; i++) {
                if (model.getGroupSelectionModel().isSelectedIndex(i)) {
                    groups.add((Group)model.getGroupSelection().getElementAt(i));
               }
            }
        }


        // Choose the costumers

        Iterator<Group> itGroups;
        Iterator<Customer> itCustomers = costumers.iterator();
        List<Customer> newCostumers = new ArrayList<Customer>();
        Customer customer;
        Group group;

        System.out.println("anz: " + costumers.size());

        // Check all costumers
        while (itCustomers.hasNext()) {

            // Get one costumer to check
            customer = itCustomers.next();

            // Iterate throu all selected groups
            itGroups = groups.iterator();
            
            boolean contains = false;

            // Check the selected groups
            while (itGroups.hasNext()) {

                // Get one group
                group = itGroups.next();

                // Check if the costumer, has the group
                if(PMGroup.getInstance().getAllGroupsByCustomer(customer, entityManager).contains(group)) {
                    contains = true;
                    break;
                }
            }

            // If the costumer, isn't in one of the selected groups
            if(contains) {
                System.out.println("add: " + customer.getForename());
                newCostumers.add(customer);
            }
        }

        return newCostumers;
    }

    public CWPanel getView() {
        return view;
    }

    public void dispose() {
        view.dispose();
        changeListener = null;
    }

    public String getFilterName() {
        return "Gruppe(n)";
    }
}
