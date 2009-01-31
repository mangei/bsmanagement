package cw.roommanagementmodul.app;


import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.interfaces.Modul;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.MenuManager;
import cw.customermanagementmodul.pojo.Customer;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import cw.roommanagementmodul.pojo.manager.BewohnerManager;
import cw.roommanagementmodul.pojo.manager.GebuehrenManager;
import cw.roommanagementmodul.pojo.manager.ZimmerManager;
import cw.roommanagementmodul.gui.BewohnerPresentationModel;
import cw.roommanagementmodul.gui.BewohnerView;
import cw.roommanagementmodul.gui.GebLaufPresentationModel;
import cw.roommanagementmodul.gui.GebLaufView;
import cw.roommanagementmodul.gui.GebuehrenPresentationModel;
import cw.roommanagementmodul.gui.GebuehrenView;
import cw.roommanagementmodul.gui.ZimmerPresentationModel;
import cw.roommanagementmodul.gui.ZimmerView;
import cw.roommanagementmodul.pojo.Bewohner;

/**
 * @author Jeitler Dominik
 */
public class ZimmerModul implements Modul {

    private Customer customer;
    private Bewohner bewohner;

    public String getModulName() {
        return "zimmer";
    }

    public Modul createModul(Object obj) {
//        Costumer c = (Costumer)obj;
//        ZimmerModul modul = new ZimmerModul();
//        modul.setCostumer(c);
//        BewohnerCollection bewohnerCollection = (BewohnerCollection)CollectionManager.getCollection(BewohnerCollection.class);
//        modul.setBewohner(bewohnerCollection.getBewohner(this.getCostumer()));
//        return modul;
        return null;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Bewohner getBewohner() {
        return bewohner;
    }

    public void setBewohner(Bewohner bewohner) {
        this.bewohner = bewohner;
    }

    public void init() {
        MenuManager.getSideMenu().addCategory("BEWOHNER", "bewohner", 2);

        MenuManager.getSideMenu().addItem(new JButton(new AbstractAction(
                "Bewohner") {

            {
                putValue(Action.SHORT_DESCRIPTION, "Bewohner verwalten");
                putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/user_orange.png"));
//                    putValue( Action.LARGE_ICON_KEY, IconManager.getIcon("user", 32) );
            }
            BewohnerManager bewohnerManager;

            public void actionPerformed(ActionEvent e) {

                GUIManager.setLoadingScreenText("Bewohner werden geladen...");
                GUIManager.setLoadingScreenVisible(true);

                new Thread(new Runnable() {

                    public void run() {
                        bewohnerManager = BewohnerManager.getInstance();

                        GUIManager.changeView(new BewohnerView(new BewohnerPresentationModel(bewohnerManager,"Bewohner verwalten")).buildPanel());
                        GUIManager.setLoadingScreenVisible(false);

                    }
                }).start();
            }
        }), "bewohner");


        MenuManager.getSideMenu().addItem(new JButton(new AbstractAction(
                "Zimmer") {

            {
                putValue(Action.SHORT_DESCRIPTION, "Zimmer verwalten");
                putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/door.png"));
//                    putValue( Action.LARGE_ICON_KEY, IconManager.getIcon("user", 32) );
            }
            ZimmerManager zimmerManager;

            public void actionPerformed(ActionEvent e) {

                GUIManager.setLoadingScreenText("Zimmer werden geladen...");
                GUIManager.setLoadingScreenVisible(true);

                new Thread(new Runnable() {

                    public void run() {
                        zimmerManager = ZimmerManager.getInstance();

                        GUIManager.changeView(new ZimmerView(new ZimmerPresentationModel(zimmerManager, "Zimmer verwalten")).buildPanel());
                        GUIManager.setLoadingScreenVisible(false);

                    }
                }).start();
            }
        }), "bewohner");


        MenuManager.getSideMenu().addItem(new JButton(new AbstractAction(
                "Gebühren") {

            {
                putValue(Action.SHORT_DESCRIPTION, "Gebühren verwalten");
                putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/money.png"));
//                    putValue( Action.LARGE_ICON_KEY, IconManager.getIcon("user", 32) );
            }
            GebuehrenManager gebuehrenManager;

            public void actionPerformed(ActionEvent e) {

                GUIManager.setLoadingScreenText("Gebühren werden geladen...");
                GUIManager.setLoadingScreenVisible(true);

                new Thread(new Runnable() {

                    public void run() {
                        gebuehrenManager = GebuehrenManager.getInstance();

                        GUIManager.changeView(new GebuehrenView(new GebuehrenPresentationModel(gebuehrenManager, "Gebühren verwalten")).buildPanel());
                        GUIManager.setLoadingScreenVisible(false);

                    }
                }).start();
            }
        }), "bewohner");

          MenuManager.getSideMenu().addItem(new JButton(new AbstractAction(
                "Lauf") {

            {
                putValue(Action.SHORT_DESCRIPTION, "Gebühren Lauf");
                putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/cog_go.png"));
//                    putValue( Action.LARGE_ICON_KEY, IconManager.getIcon("user", 32) );
            }


            public void actionPerformed(ActionEvent e) {

                GUIManager.setLoadingScreenText("Lauf wird geladen...");
                GUIManager.setLoadingScreenVisible(true);
                final GebLaufSelection gebLauf= new GebLaufSelection();

                new Thread(new Runnable() {

                    public void run() {

                        GUIManager.changeView(new GebLaufView(new GebLaufPresentationModel(gebLauf, "Gebühren Lauf")).buildPanel());
                        GUIManager.setLoadingScreenVisible(false);

                    }
                }).start();
            }
        }), "bewohner");

    }

    public Class getBaseClass() {
        return Customer.class;
    }



    public List<Class> getDependencies() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
