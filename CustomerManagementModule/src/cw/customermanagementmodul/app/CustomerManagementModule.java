package cw.customermanagementmodul.app;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.app.adaptable.IAdapterManager;
import cw.boardingschoolmanagement.app.adaptable.IAdapterRequestFactory;
import cw.boardingschoolmanagement.gui.component.CWMenuPanel;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.MenuManager;
import cw.boardingschoolmanagement.module.Module;
import cw.boardingschoolmanagement.persistence.CascadeEvent;
import cw.boardingschoolmanagement.persistence.CascadeListener;
import cw.customermanagementmodul.customer.gui.CustomerManagementPresentationModel;
import cw.customermanagementmodul.customer.gui.CustomerManagementView;
import cw.customermanagementmodul.customer.logic.BoCustomer;
import cw.customermanagementmodul.customer.persistence.Customer;
import cw.customermanagementmodul.customer.persistence.PMCustomer;
import cw.customermanagementmodul.group.gui.GroupManagementPresentationModel;
import cw.customermanagementmodul.group.gui.GroupManagementView;
import cw.customermanagementmodul.group.logic.BoGroup;
import cw.customermanagementmodul.group.persistence.Group;
import cw.customermanagementmodul.group.persistence.PMGroup;
import cw.customermanagementmodul.guardian.logic.BoGuardian;
import cw.customermanagementmodul.guardian.persistence.Guardian;
import cw.customermanagementmodul.guardian.persistence.PMGuardian;

/**
 * The costumer management module
 * @author Manuel Geier
 */
public class CustomerManagementModule
        implements Module {

    public CustomerManagementModule() {
    }

    /**
     * Initates the module
     */
    public void init() {
    	
    	/**
    	 * Mapping
    	 */
    	
    	IAdapterManager.registerAdapter(
    			Customer.class, 
    			IAdapterRequestFactory.createFactory(
    					BoCustomer.class));
    	
    	IAdapterManager.registerAdapter(
    			Group.class, 
    			IAdapterRequestFactory.createFactory(
    					BoGroup.class));
    	
    	IAdapterManager.registerAdapter(
    			BoCustomer.class, 
    			IAdapterRequestFactory.createFactory(
    					BoGuardian.class));
    	
    	
    	/**
    	 * Navigation
    	 */
    	
        CWMenuPanel sideMenu = MenuManager.getSideMenu();

        sideMenu.addCategory("Verwaltung", "manage", 10);
        sideMenu.addItem(new JButton(new AbstractAction(
                "Kunden", CWUtils.loadIcon("cw/customermanagementmodul/images/user.png")) {

            {
                putValue(Action.SHORT_DESCRIPTION, "Kunden verwalten");
            }

            public void actionPerformed(ActionEvent e) {

                GUIManager.setLoadingScreenText("Kunden werden geladen...");
                GUIManager.setLoadingScreenVisible(true);

                CustomerManagementPresentationModel model = new CustomerManagementPresentationModel();
                CustomerManagementView view = new CustomerManagementView(model);

                GUIManager.changeViewTo(view);
                GUIManager.setLoadingScreenVisible(false);

                model = null;

//                model = new CustomerManagementPresentationModel();
//                view = new CustomerManagementView(model);
//                panel = view.buildPanel();
//
//                final JDialog d = new JDialog(GUIManager.getInstance().getMainFrame(), true);
//                d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//                d.setTitle(panel.getName());
//                d.add(panel);
//                d.pack();
//                CWUtils.centerWindow(d, GUIManager.getInstance().getMainFrame());
//                d.setVisible(true);
//                d.dispose();

            }
        }), "manage");

        sideMenu.addItem(new JButton(new AbstractAction(
                "Gruppen", CWUtils.loadIcon("cw/customermanagementmodul/images/group.png")) {

            {
                putValue(Action.SHORT_DESCRIPTION, "Gruppen verwalten");
            }

            public void actionPerformed(ActionEvent e) {
                GUIManager.setLoadingScreenText("Gruppen werden geladen...");
                GUIManager.setLoadingScreenVisible(true);

                GroupManagementPresentationModel model = new GroupManagementPresentationModel();
                GroupManagementView view = new GroupManagementView(model);

//                CWUtils.showDialog(view);
                GUIManager.changeViewTo(view);
                GUIManager.setLoadingScreenVisible(false);
            }
        }), "manage");

        
        /**
         * Cascade
         */
        
//        // Wenn eine Gruppe geloescht wird, diese Gruppe aus der Gruppenliste der Kunden loeschen.
//        GroupPM.getInstance().addCascadeListener(new CascadeListener() {
//            public void deleteAction(CascadeEvent evt) {
//                Group group = (Group) evt.getSource();
//                List<Customer> customers = CustomerPM.getInstance().getAll(group);
//                for(int i=0, l=customers.size(); i<l; i++) {
//                    customers.get(i).getGroups().remove(group);
//                }
//            }
//        });

        // If we remove a customer, we also need to remove this customer out of the customerlist of the groups it is in.
        PMCustomer.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                Customer customer = (Customer) evt.getSource();
                
                List<Group> groups = PMGroup.getInstance().getAllForCustomer(customer.getId(), customer.getEntityManager());

                for(Group group : groups) {
                    group.getCustomers().remove(customer);
                }
            }
        });
        
     // If we remove a customer, also remove Guardian
        PMCustomer.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                Customer customer = (Customer) evt.getSource();
                
                Guardian guardian = PMGuardian.getInstance().getGuardianForCustomer(customer.getId(), customer.getEntityManager());

        		PMGuardian.getInstance().remove(guardian);
            }
        });

    }
}
