package cw.roommanagementmodul.app;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.accountmanagementmodul.pojo.manager.PostingManager;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.MenuManager;
import cw.boardingschoolmanagement.module.Module;
import cw.boardingschoolmanagement.persistence.CascadeEvent;
import cw.boardingschoolmanagement.persistence.CascadeListener;
import cw.customermanagementmodul.customer.persistence.Customer;
import cw.customermanagementmodul.customer.persistence.PMCustomer;
import cw.roommanagementmodul.gui.BereichPresentationModel;
import cw.roommanagementmodul.gui.BereichView;
import cw.roommanagementmodul.gui.BewohnerPresentationModel;
import cw.roommanagementmodul.gui.BewohnerView;
import cw.roommanagementmodul.gui.GebLaufPresentationModel;
import cw.roommanagementmodul.gui.GebLaufView;
import cw.roommanagementmodul.gui.GebuehrenPresentationModel;
import cw.roommanagementmodul.gui.GebuehrenView;
import cw.roommanagementmodul.images.ImageDefinitionRoom;
import cw.roommanagementmodul.persistence.Bewohner;
import cw.roommanagementmodul.persistence.BuchungsLaufZuordnung;
import cw.roommanagementmodul.persistence.Gebuehr;
import cw.roommanagementmodul.persistence.GebuehrenKategorie;
import cw.roommanagementmodul.persistence.PMBereich;
import cw.roommanagementmodul.persistence.PMBewohner;
import cw.roommanagementmodul.persistence.PMBuchungsLaufZuordnung;
import cw.roommanagementmodul.persistence.PMGebuehr;
import cw.roommanagementmodul.persistence.PMGebuehrZuordnung;
import cw.roommanagementmodul.persistence.PMGebuehrenKat;

/**
 * @author Jeitler Dominik
 */
public class ZimmerModul implements Module {

    private Customer customer;
    private Bewohner bewohner;

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
        MenuManager.getSideMenu().addCategory("ZIMMER VERWALTUNG", "bewohner", 2);

        MenuManager.getSideMenu().addItem(new JButton(new AbstractAction(
                "Bewohner") {

            {
                putValue(Action.SHORT_DESCRIPTION, "Bewohner verwalten");
                putValue(Action.SMALL_ICON, CWUtils.loadIcon(ImageDefinitionRoom.BEWOHNER));
            }
            PMBewohner bewohnerManager;

            public void actionPerformed(ActionEvent e) {

                GUIManager.setLoadingScreenText("Bewohner werden geladen...");
                GUIManager.setLoadingScreenVisible(true);


                bewohnerManager = PMBewohner.getInstance();

                GUIManager.changeViewTo(new BewohnerView(new BewohnerPresentationModel(bewohnerManager, new CWHeaderInfo("Bewohner Verwaltung", "Uebersicht aller Bewohner", CWUtils.loadIcon("cw/roommanagementmodul/images/user_orange.png"), CWUtils.loadIcon("cw/roommanagementmodul/images/user_orange.png")))));
                GUIManager.setLoadingScreenVisible(false);

            }
        }), "bewohner");


        MenuManager.getSideMenu().addItem(new JButton(new AbstractAction(
                "Bereiche") {

            {
                putValue(Action.SHORT_DESCRIPTION, "Zimmer verwalten");
                putValue(Action.SMALL_ICON, CWUtils.loadIcon(ImageDefinitionRoom.ZIMMER));
            }
            PMBereich bereichManager;

            public void actionPerformed(ActionEvent e) {

                GUIManager.setLoadingScreenText("Zimmer werden geladen...");
                GUIManager.setLoadingScreenVisible(true);



                bereichManager = PMBereich.getInstance();

                GUIManager.changeViewTo(new BereichView(new BereichPresentationModel(bereichManager, new CWHeaderInfo("Bereich Verwaltung", "Hier koennen Sie anhand des Baumes die Zimmer und Bereiche bearbeiten", CWUtils.loadIcon("cw/roommanagementmodul/images/door.png"), CWUtils.loadIcon("cw/roommanagementmodul/images/door.png")))));
                GUIManager.setLoadingScreenVisible(false);


            }
        }), "bewohner");


        MenuManager.getSideMenu().addItem(new JButton(new AbstractAction(
                "Gebuehren") {

            {
                putValue(Action.SHORT_DESCRIPTION, "Gebuehren verwalten");
                putValue(Action.SMALL_ICON, CWUtils.loadIcon(ImageDefinitionRoom.GEBUEHR));
            }
            PMGebuehr gebuehrenManager;

            public void actionPerformed(ActionEvent e) {

                GUIManager.setLoadingScreenText("Gebuehren werden geladen...");
                GUIManager.setLoadingScreenVisible(true);


                gebuehrenManager = PMGebuehr.getInstance();

                GUIManager.changeViewTo(new GebuehrenView(new GebuehrenPresentationModel(gebuehrenManager, new CWHeaderInfo("Gebuehren Verwaltung", "Hier koennen Sie die Gebuehren, Kategorien und Tarife verwalten", CWUtils.loadIcon("cw/roommanagementmodul/images/money.png"), CWUtils.loadIcon("cw/roommanagementmodul/images/money.png")))));
                GUIManager.setLoadingScreenVisible(false);


            }
        }), "bewohner");

        MenuManager.getSideMenu().addItem(new JButton(new AbstractAction(
                "Lauf") {

            {
                putValue(Action.SHORT_DESCRIPTION, "Gebuehren Lauf");
                putValue(Action.SMALL_ICON, CWUtils.loadIcon(ImageDefinitionRoom.GEBUEHREN_LAUF));
//                    putValue( Action.LARGE_ICON_KEY, IconManager.getIcon("user", 32) );
            }

            public void actionPerformed(ActionEvent e) {

                GUIManager.setLoadingScreenText("Lauf wird geladen...");
                GUIManager.setLoadingScreenVisible(true);
                final GebLaufSelection gebLauf = new GebLaufSelection();



                GUIManager.changeViewTo(new GebLaufView(new GebLaufPresentationModel(gebLauf, new CWHeaderInfo("Gebuehren Lauf", "Hier koennen Sie denn Gebuehren oder Storno Lauf durchfuehren", CWUtils.loadIcon("cw/roommanagementmodul/images/cog_go.png"), CWUtils.loadIcon("cw/roommanagementmodul/images/cog_go.png")))));
                GUIManager.setLoadingScreenVisible(false);

            }
        }), "bewohner");

        PMCustomer.getInstance().addCascadeListener(new CascadeListener() {

            public void deleteAction(CascadeEvent evt) {
                Customer c = (Customer) evt.getSource();
                PMBewohner bewManager = PMBewohner.getInstance();

                Bewohner b = bewManager.getBewohner(c);

                if (b != null) {
                    bewManager.delete(b);
                }

            }
        });

        PostingManager.getInstance().addCascadeListener(new CascadeListener() {

            public void deleteAction(CascadeEvent evt) {
                AccountPosting p = (AccountPosting) evt.getSource();
                PMBuchungsLaufZuordnung blzManager = PMBuchungsLaufZuordnung.getInstance();


                BuchungsLaufZuordnung blz = blzManager.getBuchungsLaufZuordnung(p);

                if (blz != null) {
                    blzManager.delete(blz);
                }
            }
        });

        PMBewohner.getInstance().addCascadeListener(new CascadeListener() {

            public void deleteAction(CascadeEvent evt) {
                Bewohner b = (Bewohner) evt.getSource();
                PMGebuehrZuordnung gebZuordnungManager = PMGebuehrZuordnung.getInstance();
                //TODO ueberlegen
                gebZuordnungManager.removeGebuehrZuordnung(b);
            }
        });

        PMGebuehr.getInstance().addCascadeListener(new CascadeListener() {

            public void deleteAction(CascadeEvent evt) {
                Gebuehr g = (Gebuehr) evt.getSource();
                PMGebuehrZuordnung gebZuordnungManager = PMGebuehrZuordnung.getInstance();
                gebZuordnungManager.removeGebuehrZuordnung(g);

                PMBuchungsLaufZuordnung blzManager = PMBuchungsLaufZuordnung.getInstance();
                List<BuchungsLaufZuordnung> blzList = blzManager.getBuchungsLaufZuordnung(g);
                for (int i = 0; i < blzList.size(); i++) {
                    blzList.get(i).setGebuehr(null);
                }
            }
        });

        PMGebuehrenKat.getInstance().addCascadeListener(new CascadeListener() {

            public void deleteAction(CascadeEvent evt) {

                GebuehrenKategorie k = (GebuehrenKategorie) evt.getSource();
                PMGebuehr gebManager = PMGebuehr.getInstance();
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
        return CustomerModel.class;
    }

    public List<Class> getDependencies() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
