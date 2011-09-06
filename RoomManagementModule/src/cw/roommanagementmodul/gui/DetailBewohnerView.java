package cw.roommanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.persistence.model.CustomerModel;

import java.awt.Color;
import java.awt.Font;
import cw.roommanagementmodul.pojo.Bewohner;

/**
 *
 * @author Dominik
 */
public class DetailBewohnerView extends CWView
{

    private DetailBewohnerPresentationModel model;
    private CWButton bBack;
    private CWLabel lAnrede;
    private CWLabel lVorname;
    private CWLabel lNachname;
    private CWLabel lGeburtsdatum;
    private CWLabel lStrasse;
    private CWLabel lPlz;
    private CWLabel lOrt;
    private CWLabel lBundesland;
    private CWLabel lStaat;
    private CWLabel lMobiltelefon;
    private CWLabel lFestnetztelefon;
    private CWLabel lFax;
    private CWLabel lEmail;
    private CWLabel lBemerkung;
    
    private CWLabel lZimmer;
    private CWLabel lBereich;
    private CWLabel lVon;
    private CWLabel lBis;

    public DetailBewohnerView(DetailBewohnerPresentationModel m) {
        this.model = m;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {

        bBack = CWComponentFactory.createButton(model.getBackAction());
        bBack.setText("Zurueck");

        Bewohner b = model.getBewohner();
        CustomerModel c = b.getCustomer();

        this.lAnrede = CWComponentFactory.createLabel(c.getTitle());
        this.lNachname = CWComponentFactory.createLabel(c.getSurname());
        this.lVorname = CWComponentFactory.createLabel(c.getForename());
        if (c.getPostOfficeNumber() != null) {
            lGeburtsdatum = CWComponentFactory.createLabel(c.getBirthday().toString());
        } else {
            lGeburtsdatum = CWComponentFactory.createLabel("");
        }
        lStrasse = CWComponentFactory.createLabel(c.getStreet());
        if (c.getPostOfficeNumber() != null) {
            lPlz = CWComponentFactory.createLabel(c.getPostOfficeNumber().toString());
        } else {
            lPlz = CWComponentFactory.createLabel("");
        }
        lOrt = CWComponentFactory.createLabel(c.getCity());
        lBundesland = CWComponentFactory.createLabel(c.getProvince());
        lStaat = CWComponentFactory.createLabel(c.getCountry());
        lMobiltelefon = CWComponentFactory.createLabel(c.getMobilephone());
        lFestnetztelefon = CWComponentFactory.createLabel(c.getLandlinephone());
        lFax = CWComponentFactory.createLabel(c.getFax());
        lEmail = CWComponentFactory.createLabel(c.getEmail());
        lBemerkung = CWComponentFactory.createLabel(c.getComment());


        //Zimmer und Bereich Label setzen und auf NULL abfragen
        if(b.getZimmer()!=null){
            lZimmer= CWComponentFactory.createLabel(b.getZimmer().toString());
            if(b.getZimmer().getBereich()!=null){
                lBereich= CWComponentFactory.createLabel(b.getZimmer().getBereich().toString());
            }else{
                lBereich = CWComponentFactory.createLabel("-");
            }
        }else{
            lZimmer = CWComponentFactory.createLabel("INAKTIV");
            lZimmer.setFont(new Font("Arial",Font.BOLD,11));
            lZimmer.setForeground(Color.RED);
            lBereich = CWComponentFactory.createLabel("-");
        }
        
        if(b.getVon()!=null){
            lVon = CWComponentFactory.createLabel(b.getVon().toString());
        }else { lVon = CWComponentFactory.createLabel("");}
         if(b.getBis()!=null){
            lBis = CWComponentFactory.createLabel(b.getBis().toString());
        }else { lBis = CWComponentFactory.createLabel("");}
        
    }

    private void initEventHandling() {
        
    }

    private void buildView() {
        this.setHeaderInfo(new CWHeaderInfo(model.getHeaderText()));

        CWButtonPanel buttonPanel = this.getButtonPanel();

        buttonPanel.add(bBack);

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, 50dlu:grow, 4dlu, right:pref, 4dlu, 50dlu:grow",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref,4dlu, pref,4dlu, pref,4dlu, pref");

        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());

        CellConstraints cc = new CellConstraints();
        builder.addSeparator("Allgemein:", cc.xyw(1, 1, 7));
        builder.addLabel("Anrede:", cc.xy(1, 3));
        builder.add(lAnrede, cc.xy(3, 3));
        builder.addLabel("Vorname:", cc.xy(1, 5));
        builder.add(lVorname, cc.xy(3, 5));
        builder.addLabel("Nachname", cc.xy(1, 7));
        builder.add(lNachname, cc.xyw(3, 7, 5));
        builder.addLabel("Geburtsdatum:", cc.xy(1, 9));
        builder.add(lGeburtsdatum, cc.xy(3, 9));

        builder.addSeparator("Zimmer Informationen:", cc.xyw(1, 11, 7));
        builder.addLabel("Zimmer:", cc.xy(1, 13));
        builder.add(lZimmer, cc.xy(3, 13));
        builder.addLabel("Von:", cc.xy(5, 13));
        builder.add(lVon, cc.xy(7, 13));

        builder.addLabel("Bereich:", cc.xy(1, 15));
        builder.add(lBereich, cc.xy(3, 15));
        builder.addLabel("Bis:", cc.xy(5, 15));
        builder.add(lBis, cc.xy(7, 15));

        builder.addSeparator("Adresse:", cc.xyw(1, 17, 7));
        builder.addLabel("Strasse:", cc.xy(1, 19));
        builder.add(lStrasse, cc.xyw(3, 19, 5));
        builder.addLabel("PLZ:", cc.xy(1, 21));
        builder.add(lPlz, cc.xy(3, 21));
        builder.addLabel("Ort:", cc.xy(5, 21));
        builder.add(lOrt, cc.xy(7, 21));
        builder.addLabel("Bundesland:", cc.xy(1, 23));
        builder.add(lBundesland, cc.xy(3, 23));
        builder.addLabel("Staat:", cc.xy(5, 23));
        builder.add(lStaat, cc.xy(7, 23));

        builder.addSeparator("Kontakt:", cc.xyw(1, 25, 7));
        builder.addLabel("Mobiltelefon:", cc.xy(1, 27));
        builder.add(lMobiltelefon, cc.xyw(3, 27, 5));
        builder.addLabel("Festnetztelefon:", cc.xy(1, 29));
        builder.add(lFestnetztelefon, cc.xyw(3, 29, 5));
        builder.addLabel("Fax:", cc.xy(1, 31));
        builder.add(lFax, cc.xyw(3, 31, 5));
        builder.addLabel("eMail:", cc.xy(1, 33));
        builder.add(lEmail, cc.xyw(3, 33, 5));

        builder.addSeparator("Bemerkung", cc.xyw(1, 35, 7));
        builder.add(lBemerkung, cc.xyw(1, 37, 7));
    }

    @Override
    public void dispose() {
        
    }
}
