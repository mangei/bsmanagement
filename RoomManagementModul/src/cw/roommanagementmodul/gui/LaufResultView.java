/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.roommanagementmodul.geblauf.GebTarifSelection;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import cw.roommanagementmodul.pojo.Bewohner;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 *
 * @author Dominik
 */
public class LaufResultView {

    private LaufResultPresentationModel model;
    private JButton bBack;

    public LaufResultView(LaufResultPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {


        bBack = new JButton(model.getBackAction());
        bBack.setText("Zurück");
    }

    public JPanel buildPanel() {
        initComponents();

        boolean warningNoGebuehr=false;
        JViewPanel panel = new JViewPanel();
        panel.setHeaderInfo(new HeaderInfo(model.getHeaderText()));
        panel.getButtonPanel().add(bBack);

        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        panel.getTopPanel().setLayout(layout);

        //JPanel contentPanel = panel.getContentPanel();
        JPanel contentPanel = new JPanel();

        StringBuffer row = new StringBuffer("pref, 6dlu");
        for (int i = 1; i < model.getBewohnerAnzahl(); i++) {
            row.append(",pref, 6dlu");
        }
        FormLayout bewohnerLayout = new FormLayout("pref", row.toString());
        contentPanel.setLayout(bewohnerLayout);
        //contentPanel.setLayout(new GridLayout(model.getBewohnerAnzahl(), 1));

        PanelBuilder builder = new PanelBuilder(bewohnerLayout, contentPanel);
        CellConstraints cc = new CellConstraints();

        JViewPanel bewohnerPanel;
        List<Bewohner> bewohnerList = model.getBewohner();
        int j = 1;
        for (int i = 0; i < bewohnerList.size(); i++) {
            bewohnerPanel = createBewohnerPanel(bewohnerList.get(i), model.getTarifSelection().get(bewohnerList.get(i)));
            if (bewohnerPanel != null) {
                builder.add(bewohnerPanel, cc.xy(1, j));
                j = j + 2;
            }else{
                warningNoGebuehr=true;
            }

        }

        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setPreferredSize(new Dimension(10, 10));
        panel.getContentPanel().add(scroll);
        
        if(warningNoGebuehr){
            JOptionPane.showMessageDialog(null, "Es sind Bewohner vorhanden die keine Gebühr zugewießen bekommen haben.","Warunung",JOptionPane.INFORMATION_MESSAGE);
        }


        return panel;
    }

    public JViewPanel createBewohnerPanel(Bewohner b, List<GebTarifSelection> tarifSelectionList) {

//        System.out.println("Bewohner: --------------------------");
//        System.out.println(b.getCostumer().getSurname());
//        for(int i=0;i<tarifSelectionList.size();i++){
//            System.out.println("TarifSelection:--------------------------");
//            System.out.println(tarifSelectionList.get(i).getTarif());
//            System.out.println(tarifSelectionList.get(i).getGebuehr().getGebuehr());
//            System.out.println(tarifSelectionList.get(i).isWarning());
//            System.out.println(tarifSelectionList.get(i).isNoTarifError());
//            System.out.println(tarifSelectionList.get(i).isMoreTarifError());
//            System.out.println("Ende Selection--------------------------");
//        }
//        System.out.println("Ende Bewohner");


        JViewPanel panel = new JViewPanel();
        panel.setHeaderInfo(new HeaderInfo("" + b.getCustomer().getSurname() + " " + b.getCustomer().getForename() + "     Zimmer: " + b.getZimmer().getName() + "     Bereich: " + b.getZimmer().getBereich()));

        StringBuffer row = new StringBuffer("pref, 3dlu, pref, 12dlu,pref, 3dlu, pref, 12dlu");
        for (int i = 1; i < tarifSelectionList.size(); i++) {
            row.append(",pref, 3dlu, pref, 12dlu");

        }
        row.append(",pref,2dlu,pref");

        FormLayout layout = new FormLayout(
                "pref, 3dlu, pref, 16dlu, right:pref, 3dlu, pref, 16dlu, pref, 3dlu, pref,16dlu, pref, 3dlu, pref,300dlu", // columns
                row.toString());      // rows

        CellConstraints cc = new CellConstraints();
        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());

        JLabel lTarif = new JLabel("Tarif ");
        lTarif.setFont(new Font("Arial", Font.BOLD, 12));
        JLabel lGebuehr = new JLabel("Gebühr ");
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
                    tarif = new JLabel("" + tarifSelectionList.get(i).getTarif().getTarif());
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
                error = new JLabel("WARNUNG: keine Gebühr vorhanden");
                summeCheck = true;
                return null;
            }

            //if (tarifSelectionList.get(i).isWarning() == false) {
            //builder.addSeparator("Laufart:", cc.xyw(1, 1, 8));

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
            summeLabel = new JLabel("" + summe);
            summeLabel.setForeground(Color.GREEN.darker());

        } else {
            summeLabel = new JLabel("FEHLER");
            summeLabel.setForeground(Color.RED);
        }

        builder.addSeparator("", cc.xyw(1, yPos, 3));
        builder.add(sumText, cc.xy(1, yPos + 2));
        builder.add(summeLabel, cc.xy(3, yPos + 2));


        return panel;
    }
}
