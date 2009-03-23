/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Posting;
import cw.roommanagementmodul.pojo.Bewohner;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Dominik
 */
public class StornoResultView implements Disposable {

    private StornoResultPresentationModel model;
    private JButton bBack;
    private JButton bPrint;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel mainPanel;

    public StornoResultView(StornoResultPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {
        bBack = CWComponentFactory.createButton(model.getBackAction());
        bBack.setText("Zurück");
        bPrint = CWComponentFactory.createButton(model.getPrintAction());
        bPrint.setText("Drucken");
        componentContainer = CWComponentFactory.createCWComponentContainer();
    }

    public JPanel buildPanel() {

        initComponents();

        mainPanel = new JViewPanel(model.getHeaderInfo());
        mainPanel.getButtonPanel().add(bBack);
        mainPanel.getButtonPanel().add(bPrint);

        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        mainPanel.getTopPanel().setLayout(layout);

        JPanel contentPanel = new JPanel();

        StringBuffer row = new StringBuffer("pref, 6dlu");
        for (int i = 0; i < model.getBewohner().size(); i++) {
            row.append(",pref, 6dlu");
        }
        for(int i=0;i<model.getCustomerNoBewohner().size();i++){
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

        //Falls ein Bewohner gelöscht wurde, aber der Customer noch vorhanden ist wird der Storno trotzdem durchgeführt
        List<Customer> customerList = model.getCustomerNoBewohner();
        for (int i = 0; i < customerList.size(); i++) {
            bewohnerPanel = createKundePanel(customerList.get(i), model.getCustomerNoBewohnerMap().get(customerList.get(i)));
            builder.add(bewohnerPanel, cc.xy(1, j));
            j = j + 2;
        }

        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setPreferredSize(new Dimension(10, 10));
        mainPanel.getContentPanel().add(scroll);

        componentContainer = CWComponentFactory.createCWComponentContainer().addComponent(bBack);

        mainPanel.addDisposableListener(this);
        return mainPanel;
    }

    public JViewPanel createBewohnerPanel(Bewohner b, List<Posting> postingList) {


        JViewPanel panel = new JViewPanel();
        panel.setHeaderInfo(new HeaderInfo("" + b.getCustomer().getSurname() + " " + b.getCustomer().getForename() + "     Zimmer: " + b.getZimmer().getName() + "     Bereich: " + b.getZimmer().getBereich()));

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

        componentContainer.addComponent(panel);
        return panel;
    }

    public JViewPanel createKundePanel(Customer c, List<Posting> postingList) {


        JViewPanel panel = new JViewPanel();
        panel.setHeaderInfo(new HeaderInfo("Kunde: " + c.getSurname() + " " + c.getForename()));

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

        componentContainer.addComponent(panel);
        return panel;
    }

    public void dispose() {
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
}
