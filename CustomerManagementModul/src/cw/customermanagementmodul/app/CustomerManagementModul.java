package cw.customermanagementmodul.app;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.app.CascadeEvent;
import  cw.boardingschoolmanagement.app.CascadeListener;
import cw.boardingschoolmanagement.gui.component.JMenuPanel;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.MenuManager;
import cw.customermanagementmodul.gui.CustomerManagementPresentationModel;
import cw.customermanagementmodul.gui.CustomerManagementView;
import cw.customermanagementmodul.gui.GroupManagementPresentationModel;
import cw.customermanagementmodul.gui.GroupManagementView;
import cw.customermanagementmodul.pojo.Posting;
import cw.customermanagementmodul.pojo.PostingCategory;
import cw.customermanagementmodul.pojo.manager.PostingCategoryManager;
import cw.customermanagementmodul.pojo.manager.PostingManager;
import cw.boardingschoolmanagement.interfaces.Modul;
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
implements Modul
{

    public CustomerManagementModul() {
    }

    public void init() {
        JMenuPanel sideMenu = MenuManager.getSideMenu();

        sideMenu.addCategory("Verwaltung", "manage", 10);
        sideMenu.addItem(new JButton(new AbstractAction(
                "Kunden", CWUtils.loadIcon("cw/customermanagementmodul/images/user.png")) {

            {
                putValue(Action.SHORT_DESCRIPTION, "Kunden verwalten");
            }

            public void actionPerformed(ActionEvent e) {
                GUIManager.setLoadingScreenText("Kunden werden geladen...");
                GUIManager.setLoadingScreenVisible(true);

                new Thread(new Runnable() {
                    public void run() {
                        GUIManager.changeView(new CustomerManagementView(new CustomerManagementPresentationModel("Kunden verwalten")).buildPanel());
                        GUIManager.setLoadingScreenVisible(false);
                    }
                }).start();
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

                new Thread(new Runnable() {

                    public void run() {
                        GUIManager.changeView(new GroupManagementView(new GroupManagementPresentationModel("Gruppen verwalten")).buildPanel());
                        GUIManager.setLoadingScreenVisible(false);
                    }
                }).start();
            }
        }), "manage");

        PostingCategoryManager.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                PostingCategory accountingCategory = (PostingCategory) evt.getObject();
                List<Posting> accountings = PostingManager.getInstance().getAll(accountingCategory);
                for(int i=0, l=accountings.size(); i<l; i++) {
                    accountings.get(i).setCategory(null);
                }
            }
        });

//        GroupManager.getInstance().addCascadeListener(new CascadeListener() {
//            public void deleteAction(CascadeEvent evt) {
//                Group group = (Group) evt.getObject();
//                List<Customer> customer = CustomerManager.getInstance().getAll(group);
//                for(int i=0, l=customer.size(); i<l; i++) {
//                    customer.get(i).setCategory(null);
//                }
//            }
//        });
    }

    public List<Class> getDependencies() {
        return null;
    }
}
