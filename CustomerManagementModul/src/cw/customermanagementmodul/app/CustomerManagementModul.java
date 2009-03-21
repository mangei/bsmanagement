package cw.customermanagementmodul.app;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.app.CascadeEvent;
import cw.boardingschoolmanagement.app.CascadeListener;
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
import cw.customermanagementmodul.gui.PostingCategoryManagementPresentationModel;
import cw.customermanagementmodul.gui.PostingCategoryManagementView;
import cw.customermanagementmodul.gui.PostingManagement2PresentationModel;
import cw.customermanagementmodul.gui.PostingManagementView;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Group;
import cw.customermanagementmodul.pojo.manager.CustomerManager;
import cw.customermanagementmodul.pojo.manager.GroupManager;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The CostumerManagement Modul
 * @author CreativeWorkers.at
 */
public class CustomerManagementModul
        implements Modul {

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

                CustomerManagementPresentationModel model = new CustomerManagementPresentationModel();
                CustomerManagementView view = new CustomerManagementView(model);
                JPanel panel = view.buildPanel();

                GUIManager.changeView(panel);
                GUIManager.setLoadingScreenVisible(false);

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
                JPanel panel = view.buildPanel();

                GUIManager.changeView(panel);
                GUIManager.setLoadingScreenVisible(false);
            }
        }), "manage");

        sideMenu.addCategory("Buchungen", "posting", 10);
        sideMenu.addItem(new JButton(new AbstractAction(
                "Übersicht", CWUtils.loadIcon("cw/customermanagementmodul/images/posting.png")) {

            {
                putValue(Action.SHORT_DESCRIPTION, "Buchungsübersicht");
            }

            public void actionPerformed(ActionEvent e) {
                GUIManager.setLoadingScreenText("Buchungen werden geladen...");
                GUIManager.setLoadingScreenVisible(true);

                PostingManagement2PresentationModel model = new PostingManagement2PresentationModel();
                PostingManagementView view = new PostingManagementView(model);
                JPanel panel = view.buildPanel();

                GUIManager.changeView(panel);
                GUIManager.setLoadingScreenVisible(false);
            }
        }), "posting");
        sideMenu.addItem(new JButton(new AbstractAction(
                "Kategorien", CWUtils.loadIcon("cw/customermanagementmodul/images/posting_category.png")) {

            {
                putValue(Action.SHORT_DESCRIPTION, "Buchungskategorien verwalten");
            }

            public void actionPerformed(ActionEvent e) {
                GUIManager.setLoadingScreenText("Kategorien werden geladen...");
                GUIManager.setLoadingScreenVisible(true);

                PostingCategoryManagementPresentationModel model = new PostingCategoryManagementPresentationModel();
                PostingCategoryManagementView view = new PostingCategoryManagementView(model);
                JPanel panel = view.buildPanel();

                GUIManager.changeView(panel);
                GUIManager.setLoadingScreenVisible(false);
            }
        }), "posting");


        PostingCategoryManager.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                PostingCategory accountingCategory = (PostingCategory) evt.getObject();
                List<Posting> accountings = PostingManager.getInstance().getAll(accountingCategory);
                for (int i = 0, l = accountings.size(); i < l; i++) {
                    accountings.get(i).setPostingCategory(null);
                }
            }
        });

        GroupManager.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                Group group = (Group) evt.getObject();
                List<Customer> customers = CustomerManager.getInstance().getAll(group);
                for(int i=0, l=customers.size(); i<l; i++) {
                    customers.get(i).getGroups().remove(group);
                }
            }
        });

        CustomerManager.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                Customer customer = (Customer) evt.getObject();
                List<Posting> postings = PostingManager.getInstance().getAll(customer);
                PostingManager.getInstance().delete(postings);
            }
        });

        if(PostingCategoryManager.getInstance().get("test") == null) {
            PostingCategory category = new PostingCategory();
            category.setName("Test");
            category.setKey("test");
            PostingCategoryManager.getInstance().save(category);
        }
    }
}
