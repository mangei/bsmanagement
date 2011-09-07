package cw.accountmanagementmodul.app;

import cw.accountmanagementmodul.gui.PostingOverviewPresentationModel;
import cw.accountmanagementmodul.gui.PostingOverviewView;
import cw.accountmanagementmodul.pojo.Account;
import cw.accountmanagementmodul.pojo.manager.AccountManager;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWMenuPanel;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.MenuManager;
import cw.boardingschoolmanagement.persistence.CascadeEvent;
import cw.boardingschoolmanagement.persistence.CascadeListener;
import cw.boardingschoolmanagement.interfaces.Modul;
import cw.customermanagementmodul.persistence.PMCustomer;
import cw.customermanagementmodul.persistence.model.CustomerModel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

/**
 * The CostumerManagement Modul
 * @author CreativeWorkers.at
 */
public class AccountManagementModul
        implements Modul {

    public AccountManagementModul() {
    }

    public void init() {
        CWMenuPanel sideMenu = MenuManager.getSideMenu();

        sideMenu.addCategory("Buchungen", "posting", 10);
        sideMenu.addItem(new JButton(new AbstractAction(
                "Buchungen", CWUtils.loadIcon("cw/accountmanagementmodul/images/posting.png")) {

            {
                putValue(Action.SHORT_DESCRIPTION, "Buchungen einsehen");
            }

            public void actionPerformed(ActionEvent e) {
                GUIManager.setLoadingScreenText("Buchungen werden geladen...");
                GUIManager.setLoadingScreenVisible(true);

                PostingOverviewPresentationModel model = new PostingOverviewPresentationModel();
                PostingOverviewView view = new PostingOverviewView(model);

                GUIManager.changeView(view);
                GUIManager.setLoadingScreenVisible(false);
            }
        }), "posting");

        sideMenu.addItem(new JButton(new AbstractAction(
                "Buchungslaeufe", CWUtils.loadIcon("cw/accountmanagementmodul/images/postingcycle.png")) {

            {
                putValue(Action.SHORT_DESCRIPTION, "Buchungslaeufe verwalten");
            }

            public void actionPerformed(ActionEvent e) {
                GUIManager.setLoadingScreenText("Buchungslaeufe werden geladen...");
                GUIManager.setLoadingScreenVisible(true);

                PostingOverviewPresentationModel model = new PostingOverviewPresentationModel();
                PostingOverviewView view = new PostingOverviewView(model);

                CWUtils.showDialog(view);
//                GUIManager.changeView(view);
                GUIManager.setLoadingScreenVisible(false);
            }
        }), "posting");

        sideMenu.addItem(new JButton(new AbstractAction(
                "Rechnungen", CWUtils.loadIcon("cw/accountmanagementmodul/images/invoice.png")) {

            {
                putValue(Action.SHORT_DESCRIPTION, "Rechnungen verwalten");
            }

            public void actionPerformed(ActionEvent e) {
                GUIManager.setLoadingScreenText("Rechnungen werden geladen...");
                GUIManager.setLoadingScreenVisible(true);
//
//                PostingCategoryManagementPresentationModel model = new PostingCategoryManagementPresentationModel();
//                PostingCategoryManagementView view = new PostingCategoryManagementView(model);
//
//                GUIManager.changeView(view);
                GUIManager.setLoadingScreenVisible(false);
            }
        }), "posting");

        sideMenu.addItem(new JButton(new AbstractAction(
                "Kautionen", CWUtils.loadIcon("cw/accountmanagementmodul/images/bailment.png")) {

            {
                putValue(Action.SHORT_DESCRIPTION, "Kautionen verwalten");
            }

            public void actionPerformed(ActionEvent e) {
                GUIManager.setLoadingScreenText("Kautionen werden geladen...");
                GUIManager.setLoadingScreenVisible(true);

//                PostingCategoryManagementPresentationModel model = new PostingCategoryManagementPresentationModel();
//                PostingCategoryManagementView view = new PostingCategoryManagementView(model);
//
//                GUIManager.changeView(view);
                GUIManager.setLoadingScreenVisible(false);
            }
        }), "posting");

        // Nicht mehr notwendig
//        // Wenn eine Buchungskategorie geloescht wird, die Buchungskategorie der Buchungen
//        // die diese Buchungskategorie hatten auf null setzen.
//        PostingCategoryManager.getInstance().addCascadeListener(new CascadeListener() {
//            public void deleteAction(CascadeEvent evt) {
//                PostingCategory accountingCategory = (PostingCategory) evt.getSource();
//                List<Posting> accountings = PostingManager.getInstance().getAll(accountingCategory);
//                for (int i = 0, l = accountings.size(); i < l; i++) {
//                    accountings.get(i).setPostingCategory(null);
//                }
//            }
//        });

//        // Wenn ein Kunden geloescht wird, alle dazugehoerigen Buchungen loeschen.
//        CustomerManager.getInstance().addCascadeListener(new CascadeListener() {
//            public void deleteAction(CascadeEvent evt) {
//                Customer customer = (Customer) evt.getSource();
//                List<Posting> postings = PostingManager.getInstance().getAll(customer);
//
//                for(Posting posting : postings) {
//                    PostingManager.getInstance().delete(posting);
//                }
//
//            }
//        });

        // Wenn ein Kunde geloescht wird, das dazugehoerige Konto loeschen.
        PMCustomer.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                CustomerModel customer = (CustomerModel) evt.getSource();

                Account account = AccountManager.getInstance().get(customer);

                AccountManager.getInstance().delete(account);
            }
        });


        // Wenn das Konto geloescht wird, alle dazugehoerigen Buchungen und
        // Buchungsgruppen loeschen.

        // Wenn das Konto geloescht wird, alle dazugehoerigen Kautionen loeschen.

        // Wenn das Konto geloescht wird, alle dazugehoerigen Rechnungen loeschen.

        // Wenn eine Rechnug geloescht wird, alle dazugehoerigen RechnungensPosten loeschen.
        // Pruefen ob die Buchung noch vorhanden ist, da sie schon geloescht worden sein koennte,
        // wenn ein Konto geloescht wird.

        // Wenn ein Buchung geloescht wird, diese aus den Buchungslaeufen entfernen.

        

    }
}
