package cw.roommanagementmodul.app;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.app.CascadeEvent;
import cw.boardingschoolmanagement.app.CascadeListener;
import cw.boardingschoolmanagement.app.HibernateUtil;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Modul;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.MenuManager;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Posting;
import cw.customermanagementmodul.pojo.PostingCategory;
import cw.customermanagementmodul.pojo.manager.CustomerManager;
import cw.customermanagementmodul.pojo.manager.PostingCategoryManager;
import cw.customermanagementmodul.pojo.manager.PostingManager;
import cw.roommanagementmodul.gui.BereichPresentationModel;
import cw.roommanagementmodul.gui.BereichView;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import cw.roommanagementmodul.pojo.manager.BewohnerManager;
import cw.roommanagementmodul.pojo.manager.GebuehrenManager;
import cw.roommanagementmodul.gui.BewohnerPresentationModel;
import cw.roommanagementmodul.gui.BewohnerView;
import cw.roommanagementmodul.gui.GebLaufPresentationModel;
import cw.roommanagementmodul.gui.GebLaufView;
import cw.roommanagementmodul.gui.GebuehrenPresentationModel;
import cw.roommanagementmodul.gui.GebuehrenView;
import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.BuchungsLaufZuordnung;
import cw.roommanagementmodul.pojo.Gebuehr;
import cw.roommanagementmodul.pojo.GebuehrenKategorie;
import cw.roommanagementmodul.pojo.manager.BereichManager;
import cw.roommanagementmodul.pojo.manager.BuchungsLaufZuordnungManager;
import cw.roommanagementmodul.pojo.manager.GebuehrZuordnungManager;
import cw.roommanagementmodul.pojo.manager.GebuehrenKatManager;

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
        System.out.println("hihohi");
        MenuManager.getSideMenu().addCategory("ZIMMER VERWALTUNG", "bewohner", 2);

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


                bewohnerManager = BewohnerManager.getInstance();

                GUIManager.changeView(new BewohnerView(new BewohnerPresentationModel(bewohnerManager, new HeaderInfo("Bewohner Verwaltung", "Übersicht aller Bewohner", CWUtils.loadIcon("cw/roommanagementmodul/images/user_orange.png"), CWUtils.loadIcon("cw/roommanagementmodul/images/user_orange.png")))).buildPanel());
                GUIManager.setLoadingScreenVisible(false);

            }
        }), "bewohner");


        MenuManager.getSideMenu().addItem(new JButton(new AbstractAction(
                "Bereiche") {

            {
                putValue(Action.SHORT_DESCRIPTION, "Zimmer verwalten");
                putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/door.png"));
//                    putValue( Action.LARGE_ICON_KEY, IconManager.getIcon("user", 32) );
            }
            BereichManager bereichManager;

            public void actionPerformed(ActionEvent e) {

                GUIManager.setLoadingScreenText("Zimmer werden geladen...");
                GUIManager.setLoadingScreenVisible(true);



                bereichManager = BereichManager.getInstance();

                GUIManager.changeView(new BereichView(new BereichPresentationModel(bereichManager, new HeaderInfo("Bereich Verwaltung", "Hier können Sie anhand des Baumes die Zimmer und Bereiche bearbeiten", CWUtils.loadIcon("cw/roommanagementmodul/images/door.png"), CWUtils.loadIcon("cw/roommanagementmodul/images/door.png")))).buildPanel());
                GUIManager.setLoadingScreenVisible(false);


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


                gebuehrenManager = GebuehrenManager.getInstance();

                GUIManager.changeView(new GebuehrenView(new GebuehrenPresentationModel(gebuehrenManager, new HeaderInfo("Gebühren Verwaltung", "Hier können Sie die Gebühren, Kategorien und Tarife verwalten", CWUtils.loadIcon("cw/roommanagementmodul/images/money.png"), CWUtils.loadIcon("cw/roommanagementmodul/images/money.png")))).buildPanel());
                GUIManager.setLoadingScreenVisible(false);


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
                final GebLaufSelection gebLauf = new GebLaufSelection();



                GUIManager.changeView(new GebLaufView(new GebLaufPresentationModel(gebLauf, new HeaderInfo("Gebühren Lauf", "Hier können Sie denn Gebühren oder Storno Lauf durchführen", CWUtils.loadIcon("cw/roommanagementmodul/images/cog_go.png"), CWUtils.loadIcon("cw/roommanagementmodul/images/cog_go.png")))).buildPanel());
                GUIManager.setLoadingScreenVisible(false);

            }
        }), "bewohner");

        CustomerManager.getInstance().addCascadeListener(new CascadeListener() {

            public void deleteAction(CascadeEvent evt) {
                Customer c = (Customer) evt.getObject();
                BewohnerManager bewManager = BewohnerManager.getInstance();

                Bewohner b = bewManager.getBewohner(c);

                if (b != null) {
                    bewManager.delete(b);
                }

            }
        });

        PostingManager.getInstance().addCascadeListener(new CascadeListener() {

            public void deleteAction(CascadeEvent evt) {
                Posting p = (Posting) evt.getObject();
                BuchungsLaufZuordnungManager blzManager = BuchungsLaufZuordnungManager.getInstance();


                BuchungsLaufZuordnung blz = blzManager.getBuchungsLaufZuordnung(p);

                if (blz != null) {
                    blzManager.delete(blz);
                }
            }
        });

        BewohnerManager.getInstance().addCascadeListener(new CascadeListener() {

            public void deleteAction(CascadeEvent evt) {
                Bewohner b = (Bewohner) evt.getObject();
                GebuehrZuordnungManager gebZuordnungManager = GebuehrZuordnungManager.getInstance();
                //TODO überlegen
                gebZuordnungManager.removeGebuehrZuordnung(b);
            }
        });

        GebuehrenManager.getInstance().addCascadeListener(new CascadeListener() {

            public void deleteAction(CascadeEvent evt) {
                Gebuehr g = (Gebuehr) evt.getObject();
                GebuehrZuordnungManager gebZuordnungManager = GebuehrZuordnungManager.getInstance();
                gebZuordnungManager.removeGebuehrZuordnung(g);

                BuchungsLaufZuordnungManager blzManager = BuchungsLaufZuordnungManager.getInstance();
                List<BuchungsLaufZuordnung> blzList = blzManager.getBuchungsLaufZuordnung(g);
                for (int i = 0; i < blzList.size(); i++) {
                    blzList.get(i).setGebuehr(null);
                }
            }
        });

        GebuehrenKatManager.getInstance().addCascadeListener(new CascadeListener() {

            public void deleteAction(CascadeEvent evt) {

                GebuehrenKategorie k = (GebuehrenKategorie) evt.getObject();
                GebuehrenManager gebManager = GebuehrenManager.getInstance();
                List<Gebuehr> gebList = gebManager.getGebuehr(k);

                for (int i = 0; i < gebList.size(); i++) {
                    gebList.get(i).setGebKat(null);
                }
            }
        });

        if (PostingCategoryManager.getInstance().get("zimmer") == null) {
            PostingCategory category = new PostingCategory();
            category.setName("Zimmer");
            category.setKey("zimmer");
            PostingCategoryManager.getInstance().save(category);
        }
    }

    public Class getBaseClass() {
        return Customer.class;
    }

    public List<Class> getDependencies() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
