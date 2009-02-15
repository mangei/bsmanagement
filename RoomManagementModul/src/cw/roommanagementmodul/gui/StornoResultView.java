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
import cw.customermanagementmodul.pojo.Posting;
import cw.roommanagementmodul.pojo.Bewohner;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Dominik
 */
public class StornoResultView {

    private StornoResultPresentationModel model;
    private JButton bBack;

    public StornoResultView(StornoResultPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {
        bBack = new JButton(model.getBackAction());
        bBack.setText("Zurück");
    }

    public JPanel buildPanel() {

        initComponents();

        JViewPanel panel = new JViewPanel();
        panel.setHeaderInfo(new HeaderInfo(model.getHeaderText()));
        panel.getButtonPanel().add(bBack);

        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        panel.getTopPanel().setLayout(layout);

        JPanel contentPanel = panel.getContentPanel();

        StringBuffer row = new StringBuffer("pref, 6dlu");
        for (int i = 1; i < model.getBewohner().size(); i++) {
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
            bewohnerPanel = createBewohnerPanel(bewohnerList.get(i), model.getBewohnerPostingMap().get(bewohnerList.get(i)));
            builder.add(bewohnerPanel, cc.xy(1, j));
            j = j + 2;
        }


        return panel;
    }

    public JViewPanel createBewohnerPanel(Bewohner b, List<Posting> postingList) {


        JViewPanel panel = new JViewPanel();
        panel.setHeaderInfo(new HeaderInfo("" + b.getCustomer().getSurname() + " " + b.getCustomer().getForename() + "  Zimmer: " + b.getZimmer().getName()));

        StringBuffer row = new StringBuffer("pref, 3dlu, pref, 12dlu,pref, 3dlu, pref, 12dlu");
        for (int i = 1; i < postingList.size(); i++) {
            row.append(",pref, 3dlu, pref, 12dlu");

        }
        row.append(",pref,2dlu,pref");

        FormLayout layout = new FormLayout(
                "pref, 3dlu, pref, 16dlu, right:pref, 3dlu, pref, 16dlu, pref, 3dlu, pref,16dlu, pref, 3dlu, pref,180dlu", // columns
                row.toString());      // rows

        CellConstraints cc = new CellConstraints();
        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());

        JLabel lTarif = new JLabel("Storno ");
        lTarif.setFont(new Font("Arial", Font.BOLD, 12));
        JLabel lGebuehr = new JLabel("Bezeichnung ");
        lGebuehr.setFont(new Font("Arial", Font.BOLD, 12));

        builder.add(lTarif, cc.xy(3, 1));
        builder.add(lGebuehr, cc.xy(5, 1));


        int yPos = 5;
        double summe = 0;
        boolean summeCheck = false;
        for (int i = 0; i < postingList.size(); i++) {
            JLabel error = null, storno = null, bezeichnung = null;



            storno = new JLabel("" + postingList.get(i).getAmount());

            bezeichnung = new JLabel(postingList.get(i).getDescription());

            summe = summe + postingList.get(i).getAmount();

            builder.add(storno, cc.xy(3, yPos));

            builder.add(bezeichnung, cc.xy(5, yPos));

            if (error != null) {
                error.setForeground(Color.RED);
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
