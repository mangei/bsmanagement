package cw.accountmanagementmodul.app;

import cw.accountmanagementmodul.gui.PostingCategoryManagementPresentationModel;
import cw.accountmanagementmodul.gui.PostingCategoryManagementView;
import cw.accountmanagementmodul.pojo.Account;
import cw.accountmanagementmodul.pojo.Posting;
import cw.accountmanagementmodul.pojo.PostingCategory;
import cw.accountmanagementmodul.pojo.manager.AccountManager;
import cw.accountmanagementmodul.pojo.manager.PostingCategoryManager;
import cw.accountmanagementmodul.pojo.manager.PostingManager;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.app.CascadeEvent;
import cw.boardingschoolmanagement.app.CascadeListener;
import cw.boardingschoolmanagement.gui.component.CWMenuPanel;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.MenuManager;
import cw.boardingschoolmanagement.interfaces.Modul;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.manager.CustomerManager;
import java.awt.event.ActionEvent;
import java.util.List;
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
                "Kategorien", CWUtils.loadIcon("cw/customermanagementmodul/images/posting_category.png")) {

            {
                putValue(Action.SHORT_DESCRIPTION, "Buchungskategorien verwalten");
            }

            public void actionPerformed(ActionEvent e) {
                GUIManager.setLoadingScreenText("Kategorien werden geladen...");
                GUIManager.setLoadingScreenVisible(true);

                PostingCategoryManagementPresentationModel model = new PostingCategoryManagementPresentationModel();
                PostingCategoryManagementView view = new PostingCategoryManagementView(model);

                GUIManager.changeView(view);
                GUIManager.setLoadingScreenVisible(false);
            }
        }), "posting");


        // Wenn eine Buchungskategorie gelöscht wird, die Buchungskategorie der Buchungen
        // die diese Buchungskategorie hatten auf null setzen.
        PostingCategoryManager.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                PostingCategory accountingCategory = (PostingCategory) evt.getSource();
                List<Posting> accountings = PostingManager.getInstance().getAll(accountingCategory);
                for (int i = 0, l = accountings.size(); i < l; i++) {
                    accountings.get(i).setPostingCategory(null);
                }
            }
        });

        // Wenn ein Kunden gelöscht wird, alle dazugehörigen Buchungen löschen.
        CustomerManager.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                Customer customer = (Customer) evt.getSource();
                List<Posting> postings = PostingManager.getInstance().getAll(customer);
                
                for(Posting posting : postings) {
                    PostingManager.getInstance().delete(posting);
                }
                
            }
        });

        // Wenn ein Kunde gelöscht wird, das dazugehörige Konto löschen.
        CustomerManager.getInstance().addCascadeListener(new CascadeListener() {
            public void deleteAction(CascadeEvent evt) {
                Customer customer = (Customer) evt.getSource();

                Account account = AccountManager.getInstance().get(customer);

                AccountManager.getInstance().delete(account);
            }
        });


        // Wenn das Konto gelöscht wird, alle dazugehörigen Buchungen und
        // Buchungsgruppen löschen.

        // Wenn das Konto gelöscht wird, alle dazugehörigen Kautionen löschen.

        // Wenn das Konto gelöscht wird, alle dazugehörigen Rechnungen löschen.

        // Wenn eine Rechnug gelöscht wird, alle dazugehörigen RechnungensPosten löschen.
        // Prüfen ob die Buchung noch vorhanden ist, da sie schon gelöscht worden sein könnte,
        // wenn ein Konto gelöscht wird.

        // Wenn ein Buchung gelöscht wird, diese aus den Buchungsläufen entfernen.

        

    }
}
