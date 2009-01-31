/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.customermanagementmodul.pojo.Customer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import cw.roommanagementmodul.pojo.Bewohner;

/**
 *
 * @author Dominik
 */
public class DetailBewohnerView {

    private DetailBewohnerPresentationModel model;
    private JButton bBack;
    private JLabel lAnrede;
    private JLabel lVorname;
    private JLabel lVorname2;
    private JLabel lNachname;
    private JLabel lGeburtsdatum;
    private JLabel lStrasse;
    private JLabel lPlz;
    private JLabel lOrt;
    private JLabel lBundesland;
    private JLabel lStaat;
    private JLabel lMobiltelefon;
    private JLabel lFestnetztelefon;
    private JLabel lFax;
    private JLabel lEmail;
    private JLabel lBemerkung;
    
    private JLabel lZimmer;
    private JLabel lBereich;
    private JLabel lVon;
    private JLabel lBis;

    public DetailBewohnerView(DetailBewohnerPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {

        bBack = new JButton(model.getBackAction());
        bBack.setText("Zurück");

        Bewohner b = model.getBewohner();
        Customer c = b.getCustomer();

        this.lAnrede = new JLabel(c.getTitle());
        this.lNachname = new JLabel(c.getSurname());
        this.lVorname = new JLabel(c.getForename());
        this.lVorname2 = new JLabel(c.getForename2());
        if (c.getPostOfficeNumber() != null) {
            lGeburtsdatum = new JLabel(c.getBirthday().toString());
        } else {
            lGeburtsdatum = new JLabel("");
        }
        lStrasse = new JLabel(c.getStreet());
        if (c.getPostOfficeNumber() != null) {
            lPlz = new JLabel(c.getPostOfficeNumber().toString());
        } else {
            lPlz = new JLabel("");
        }
        lOrt = new JLabel(c.getCity());
        lBundesland = new JLabel(c.getProvince());
        lStaat = new JLabel(c.getCountry());
        lMobiltelefon = new JLabel(c.getMobilephone());
        lFestnetztelefon = new JLabel(c.getLandlinephone());
        lFax = new JLabel(c.getFax());
        lEmail = new JLabel(c.getEmail());
        lBemerkung = new JLabel(c.getComment());


        //Zimmer und Bereich Label setzen und auf NULL abfragen
        if(b.getZimmer()!=null){
            lZimmer= new JLabel(b.getZimmer().toString());
            if(b.getZimmer().getBereich()!=null){
                lBereich= new JLabel(b.getZimmer().getBereich().toString());
            }else{
                lBereich = new JLabel("-");
            }
        }else{
            lZimmer = new JLabel("INAKTIV");
            lZimmer.setFont(new Font("Arial",Font.BOLD,11));
            lZimmer.setForeground(Color.RED);
            lBereich = new JLabel("-");
        }
        
        if(b.getVon()!=null){
            lVon = new JLabel(b.getVon().toString());
        }else { lVon = new JLabel("");}
         if(b.getBis()!=null){
            lBis = new JLabel(b.getBis().toString());
        }else { lBis = new JLabel("");}
        
    }

    public JPanel buildPanel() {

        initComponents();

        JViewPanel mainPanel = new JViewPanel();

        mainPanel.setHeaderInfo(new HeaderInfo(model.getHeaderText()));
        JButtonPanel buttonPanel = mainPanel.getButtonPanel();


        buttonPanel.add(bBack);
        JViewPanel panel = new JViewPanel();

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, 50dlu:grow, 4dlu, right:pref, 4dlu, 50dlu:grow",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref,4dlu, pref,4dlu, pref,4dlu, pref");

        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());

        CellConstraints cc = new CellConstraints();
        builder.addSeparator("Allgemein:", cc.xyw(1, 1, 7));
        builder.addLabel("Anrede:", cc.xy(1, 3));
        builder.add(lAnrede, cc.xy(3, 3));
        builder.addLabel("Vorname:", cc.xy(1, 5));
        builder.add(lVorname, cc.xy(3, 5));
        builder.addLabel("2. Vorname:", cc.xy(5, 5));
        builder.add(lVorname2, cc.xy(7, 5));
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


        mainPanel.getContentPanel().add(panel, BorderLayout.CENTER);

        //  mainPanel.getContentPanel().add(tabs, BorderLayout.CENTER);
        return mainPanel;
    }
}
