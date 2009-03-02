package cw.customermanagementmodul.extentions;

import com.jgoodies.binding.value.ValueModel;
import cw.customermanagementmodul.extentions.interfaces.CustomerSelectorFilterExtention;
import cw.customermanagementmodul.gui.GroupCustomerSelectorFilterExtentionPresentationModel;
import cw.customermanagementmodul.gui.GroupCustomerSelectorFilterExtentionView;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Group;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author ManuelG
 */
public class GroupCustomerSelectorFilterExtention implements CustomerSelectorFilterExtention {

    private GroupCustomerSelectorFilterExtentionPresentationModel model;
    private GroupCustomerSelectorFilterExtentionView view;
    private ValueModel change;

    private ListSelectionListener changeListener;

    public void init(final ValueModel change) {
        this.change = change;

        model = new GroupCustomerSelectorFilterExtentionPresentationModel();
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

        // Die Erweiterung die eine Änderung feststellt, meldet dies an die Tabelle
        // diese ruft dann die filter-methode dieser Erweiterungen auf
        // Danach wird die Tabelle aktualisiert

        List<Group> groups = new ArrayList<Group>();


        // TODO Prüfen ob das so stimmt mit dem Gruppen auswählen


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
        Iterator<Customer> itCostumers = costumers.iterator();
        List<Customer> newCostumers = new ArrayList<Customer>();
        Customer costumer;
        Group group;

        System.out.println("anz: " + costumers.size());

        // Check all costumers
        while (itCostumers.hasNext()) {

            // Get one costumer to check
            costumer = itCostumers.next();
            
            System.out.println("Costumer: " + costumer.getForename());

            // Iterate throu all selected groups
            itGroups = groups.iterator();
            
            boolean contains = false;

            // Check the selected groups
            while (itGroups.hasNext()) {

                // Get one group
                group = itGroups.next();


                System.out.println("Group: " + group.getName() + " + Costumer: " + costumer.getForename() + " = " + costumer.getGroups().contains(group));


                // Check if the costumer, has the group
                if(costumer.getGroups().contains(group)) {
                    contains = true;
                    break;
                }
            }

            // If the costumer, isn't in one of the selected groups
            if(contains) {
                System.out.println("add: " + costumer.getForename());
                newCostumers.add(costumer);
            }
        }

        return newCostumers;
    }

    public String getPosition() {
        return BorderLayout.EAST;
    }

    public JPanel getPanel() {
        return view.buildPanel();
    }

    public void dispose() {
        changeListener = null;
    }
}
