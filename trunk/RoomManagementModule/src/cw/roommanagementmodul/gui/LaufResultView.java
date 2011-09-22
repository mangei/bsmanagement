package cw.roommanagementmodul.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.roommanagementmodul.geblauf.GebTarifSelection;
import cw.roommanagementmodul.persistence.Bewohner;

/**
 *
 * @author Dominik
 */
public class LaufResultView
	extends CWView<LaufResultPresentationModel>
{

    private CWButton bBack;
    private CWButton bPrint;
    private DecimalFormat numberFormat;

    public LaufResultView(LaufResultPresentationModel model) {
        super(model);
        numberFormat = new DecimalFormat("#0.00");
    }

    public void initComponents() {
    	super.initComponents();

        bPrint = CWComponentFactory.createButton(getModel().getPrintAction());
        bPrint.setText("Drucken");
        bBack = CWComponentFactory.createButton(getModel().getBackAction());
        bBack.setText("Zurueck");

        getComponentContainer()
        	.addComponent(bPrint)
        	.addComponent(bBack);

    }

    public void buildView() {
    	super.buildView();

        boolean warningNoGebuehr = false;
        this.setHeaderInfo(getModel().getHeaderInfo());
        this.getButtonPanel().add(bPrint);
        this.getButtonPanel().add(bBack);

        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        this.getTopPanel().setLayout(layout);

        //JPanel contentPanel = panel.getContentPanel();
        JPanel contentPanel = new JPanel();

        StringBuffer row = new StringBuffer("pref, 6dlu");
        for (int i = 1; i < getModel().getBewohnerAnzahl(); i++) {
            row.append(",pref, 6dlu");
        }
        FormLayout bewohnerLayout = new FormLayout("pref", row.toString());
        contentPanel.setLayout(bewohnerLayout);
        //contentPanel.setLayout(new GridLayout(model.getBewohnerAnzahl(), 1));

        PanelBuilder builder = new PanelBuilder(bewohnerLayout, contentPanel);
        CellConstraints cc = new CellConstraints();

        CWView bewohnerPanel = CWComponentFactory.createView(null);
        List<Bewohner> bewohnerList = getModel().getBewohner();
        int j = 1;
        for (int i = 0; i < bewohnerList.size(); i++) {
            bewohnerPanel = createBewohnerPanel(bewohnerList.get(i), getModel().getTarifSelection().get(bewohnerList.get(i)));
            if (bewohnerPanel != null) {
                builder.add(bewohnerPanel, cc.xy(1, j));
                j = j + 2;
            } else {
                warningNoGebuehr = true;
            }

        }

        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setPreferredSize(new Dimension(10, 10));
        
        addToContentPanel(scroll);

        if (warningNoGebuehr) {
            JOptionPane.showMessageDialog(null, "Es sind Bewohner vorhanden die keine Gebuehr fuer dieses Datum zugewiessen bekommen haben.", "Warunung", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public CWView createBewohnerPanel(Bewohner b, List<GebTarifSelection> tarifSelectionList) {


        CWView panel = CWComponentFactory.createView(null);
        panel.setHeaderInfo(new CWHeaderInfo("" + b.getCustomer().getSurname() + " " + b.getCustomer().getForename() + "     Zimmer: " + b.getZimmer().getName() + "     Bereich: " + b.getZimmer().getBereich()));

        StringBuffer row = new StringBuffer("pref, 3dlu, pref, 12dlu,pref, 3dlu, pref, 12dlu");
        for (int i = 1; i < tarifSelectionList.size(); i++) {
            row.append(",pref, 3dlu, pref, 12dlu");

        }
        row.append(",pref,2dlu,pref");

        FormLayout layout = new FormLayout(
                "pref, 3dlu, pref, 16dlu, right:pref, 3dlu, pref, 16dlu, pref, 3dlu, pref,16dlu, pref, 3dlu, pref,300dlu", // columns
                row.toString());      // rows

        CellConstraints cc = new CellConstraints();
        PanelBuilder builder = new PanelBuilder(layout);

        JLabel lTarif = new JLabel("Tarif ");
        lTarif.setFont(new Font("Arial", Font.BOLD, 12));
        JLabel lGebuehr = new JLabel("Gebuehr ");
        lGebuehr.setFont(new Font("Arial", Font.BOLD, 12));
        JLabel lKategorie = new JLabel("Kategorie ");
        lKategorie.setFont(new Font("Arial", Font.BOLD, 12));
        JLabel lAnmerkung = new JLabel("Anmerkung ");
        lAnmerkung.setFont(new Font("Arial", Font.BOLD, 12));
        builder.add(lTarif, cc.xy(3, 1));
        builder.add(lGebuehr, cc.xy(7, 1));
        builder.add(lKategorie, cc.xy(11, 1));
        builder.add(lAnmerkung, cc.xy(15, 1));


        int yPos = 5;
        double summe = 0;
        boolean summeCheck = false;
        for (int i = 0; i < tarifSelectionList.size(); i++) {
            JLabel error = null, tarif = null, gebuehr = null, kategorie = null, anmerkung = null;


            if (tarifSelectionList.get(i).isWarning() == false) {
                gebuehr = new JLabel(tarifSelectionList.get(i).getGebuehr().getGebuehr().getName());
                if (tarifSelectionList.get(i).getTarif() != null) {
                    tarif = new JLabel("€ " + numberFormat.format(tarifSelectionList.get(i).getTarif().getTarif()));
                    summe = summe + tarifSelectionList.get(i).getTarif().getTarif();

                }


                if (tarifSelectionList.get(i).getGebuehr().getGebuehr().getGebKat() != null) {
                    kategorie = new JLabel(tarifSelectionList.get(i).getGebuehr().getGebuehr().getGebKat().getName());
                }
                anmerkung = new JLabel(tarifSelectionList.get(i).getGebuehr().getAnmerkung());
            }


            if (tarifSelectionList.get(i).isMoreTarifError()) {
                error = new JLabel("mehrere Tarife selektiert!");
                tarif = new JLabel("FEHLER");
                summeCheck = true;
            }

            if (tarifSelectionList.get(i).isNoTarifError()) {
                error = new JLabel("kein Tarif selektiert!");
                tarif = new JLabel("FEHLER");
                summeCheck = true;
            }
            if (tarifSelectionList.get(i).isWarning()) {
                error = new JLabel("WARNUNG: keine Gebuehr vorhanden");
                summeCheck = true;
                return null;
            }


            if (tarif != null) {
                builder.add(tarif, cc.xy(3, yPos));
            }

            if (gebuehr != null) {
                builder.add(gebuehr, cc.xy(7, yPos));
            }

            if (kategorie != null) {
                builder.add(kategorie, cc.xy(11, yPos));
            }

            if (anmerkung != null) {
                builder.add(anmerkung, cc.xy(15, yPos));

            }

            if (error != null) {
                error.setForeground(Color.RED);
                if (tarif != null) {
                    tarif.setForeground(Color.RED);
                }
                builder.add(error, cc.xyw(1, yPos + 2, 15));
            }
            yPos = yPos + 4;
        }

        JLabel summeLabel = null;
        JLabel sumText = new JLabel("Summe: ");
        sumText.setFont(new Font("Arial", Font.BOLD, 12));
        if (summeCheck == false) {
            summeLabel = new JLabel("€ " + numberFormat.format(summe));
            summeLabel.setForeground(Color.GREEN.darker());

        } else {
            summeLabel = new JLabel("FEHLER");
            summeLabel.setForeground(Color.RED);
            getModel().getPrintAction().setEnabled(false);
        }

        builder.addSeparator("", cc.xyw(1, yPos, 3));
        builder.add(sumText, cc.xy(1, yPos + 2));
        builder.add(summeLabel, cc.xy(3, yPos + 2));

        panel.addToContentPanel(builder.getPanel());
        
        getComponentContainer()
        	.addComponent(panel);
        
        return panel;
    }

    public void dispose() {
        
    	super.dispose();
    }
}
