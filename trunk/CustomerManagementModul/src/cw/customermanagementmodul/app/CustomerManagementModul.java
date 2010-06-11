package cw.customermanagementmodul.app;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.app.CascadeEvent;
import cw.boardingschoolmanagement.app.CascadeListener;
import cw.boardingschoolmanagement.gui.component.CWMenuPanel;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.MenuManager;
import cw.customermanagementmodul.gui.CustomerManagementPresentationModel;
import cw.customermanagementmodul.gui.CustomerManagementView;
import cw.customermanagementmodul.gui.GroupManagementPresentationModel;
import cw.customermanagementmodul.gui.GroupManagementView;
import cw.boardingschoolmanagement.interfaces.Modul;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Group;
import cw.customermanagementmodul.pojo.manager.CustomerManager;
import cw.customermanagementmodul.pojo.manager.GroupManager;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

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

        // Wenn eine Gruppe gelöscht wird, diese Gruppe aus der Gruppenliste der Kunden löschen.
        GroupManager.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                Group group = (Group) evt.getSource();
                List<Customer> customers = CustomerManager.getInstance().getAll(group);
                for(int i=0, l=customers.size(); i<l; i++) {
                    customers.get(i).getGroups().remove(group);
                }
            }
        });

        // Wenn ein Kunde gelöscht wird, diesen Kunden aus den Kundenlisten der Gruppen löschen.
        CustomerManager.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                Customer customer = (Customer) evt.getSource();
                List<Group> groups = customer.getGroups();

                for(Group group : groups) {
                    group.getCustomers().remove(customer);
                }

            }
        });

    }
}
