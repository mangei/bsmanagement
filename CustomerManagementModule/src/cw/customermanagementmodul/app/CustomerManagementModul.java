package cw.customermanagementmodul.app;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWMenuPanel;
import cw.boardingschoolmanagement.interfaces.Modul;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.MenuManager;
import cw.boardingschoolmanagement.perstistence.CascadeEvent;
import cw.boardingschoolmanagement.perstistence.CascadeListener;
import cw.customermanagementmodul.gui.CustomerManagementPresentationModel;
import cw.customermanagementmodul.gui.CustomerManagementView;
import cw.customermanagementmodul.gui.GroupManagementPresentationModel;
import cw.customermanagementmodul.gui.GroupManagementView;
import cw.customermanagementmodul.persistence.Customer;
import cw.customermanagementmodul.persistence.CustomerPM;
import cw.customermanagementmodul.persistence.Group;
import cw.customermanagementmodul.persistence.GroupPM;

/**
 * The CostumerManagement Modul
 * @author CreativeWorkers.at
 */
public class CustomerManagementModul
        implements Modul {

    public CustomerManagementModul() {
    }

    public void init() {
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

                GUIManager.changeView(view);
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

                CWUtils.showDialog(view);
//                GUIManager.changeView(view);
                GUIManager.setLoadingScreenVisible(false);
            }
        }), "manage");

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

        // Wenn ein Kunde geloescht wird, diesen Kunden aus den Kundenlisten der Gruppen loeschen.
        CustomerPM.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                Customer customer = (Customer) evt.getSource();
                
                List<Group> groups = GroupPM.getInstance().getAllGroupsByCustomer(customer, evt.getEntityManager());

                for(Group group : groups) {
                    group.getCustomers().remove(customer);
                }

            }
        });

    }
}
