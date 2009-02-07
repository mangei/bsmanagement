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
            private CustomerManagementPresentationModel model;
            private CustomerManagementView view;
            private JPanel panel;

            public void actionPerformed(ActionEvent e) {
                if (panel == null) {

                    GUIManager.setLoadingScreenText("Kunden werden geladen...");
                    GUIManager.setLoadingScreenVisible(true);

                    new Thread(new Runnable() {

                        public void run() {
//                            if (model == null) {
                                model = new CustomerManagementPresentationModel();
//                            }
//                            if (view == null) {
                                view = new CustomerManagementView(model);
//                            }
//                            if (panel == null) {
                                panel = view.buildPanel();
//                            }

                            GUIManager.changeView(panel);
                            GUIManager.setLoadingScreenVisible(false);
                        }
                    }).start();
                } else {
                    GUIManager.changeView(panel);
                }
            }
        }), "manage");

        sideMenu.addItem(new JButton(new AbstractAction(
                "Gruppen", CWUtils.loadIcon("cw/customermanagementmodul/images/group.png")) {

            {
                putValue(Action.SHORT_DESCRIPTION, "Gruppen verwalten");
            }
            private GroupManagementPresentationModel model;
            private GroupManagementView view;
            private JPanel panel;

            public void actionPerformed(ActionEvent e) {
                if (panel == null) {

                    GUIManager.setLoadingScreenText("Gruppen werden geladen...");
                    GUIManager.setLoadingScreenVisible(true);

                    new Thread(new Runnable() {

                        public void run() {
//                            if (model == null) {
                                model = new GroupManagementPresentationModel("Gruppen verwalten");
//                            }
//                            if (view == null) {
                                view = new GroupManagementView(model);
//                            }
//                            if (panel == null) {
                                panel = view.buildPanel();
//                            }

                            GUIManager.changeView(panel);
                            GUIManager.setLoadingScreenVisible(false);
                        }
                    }).start();
                } else {
                    GUIManager.changeView(panel);
                }
            }
        }), "manage");

        sideMenu.addCategory("Buchungen", "posting", 10);
        sideMenu.addItem(new JButton(new AbstractAction(
                "Kategorien", CWUtils.loadIcon("cw/customermanagementmodul/images/posting_category.png")) {

            {
                putValue(Action.SHORT_DESCRIPTION, "Buchungskategorien verwalten");
            }
            private PostingCategoryManagementPresentationModel model;
            private PostingCategoryManagementView view;
            private JPanel panel;

            public void actionPerformed(ActionEvent e) {
                if (panel == null) {

                    GUIManager.setLoadingScreenText("Kategorien werden geladen...");
                    GUIManager.setLoadingScreenVisible(true);

                    new Thread(new Runnable() {

                        public void run() {
//                            if (model == null) {
                                model = new PostingCategoryManagementPresentationModel();
//                            }
//                            if (view == null) {
                                view = new PostingCategoryManagementView(model);
//                            }
//                            if (panel == null) {
                                panel = view.buildPanel();
//                            }

                            GUIManager.changeView(panel);
                            GUIManager.setLoadingScreenVisible(false);
                        }
                    }).start();
                } else {
                    GUIManager.changeView(panel);
                }
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
