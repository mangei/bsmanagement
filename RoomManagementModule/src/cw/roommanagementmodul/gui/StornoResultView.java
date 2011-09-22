package cw.roommanagementmodul.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.customer.persistence.Customer;
import cw.roommanagementmodul.persistence.Bewohner;

/**
 *
 * @author Dominik
 */
public class StornoResultView
	extends CWView<StornoResultPresentationModel>
{

    private CWButton bBack;
    private CWButton bPrint;
    private DecimalFormat numberFormat;

    public StornoResultView(StornoResultPresentationModel model) {
        super(model);
        numberFormat = new DecimalFormat("#0.00");
    }

    public void initComponents() {
    	super.initComponents();
    	
        bBack = CWComponentFactory.createButton(getModel().getBackAction());
        bBack.setText("Zurueck");
        bPrint = CWComponentFactory.createButton(getModel().getPrintAction());
        bPrint.setText("Drucken");
        
        getComponentContainer()
        	.addComponent(bBack)
        	.addComponent(bPrint);
    }

    public void buildView() {
    	super.buildView();

        this.setHeaderInfo(getModel().getHeaderInfo());
        this.getButtonPanel().add(bBack);
        this.getButtonPanel().add(bPrint);

        FormLayout layout = new FormLayout("pref, 2dlu, 50dlu:grow, 2dlu, pref", "pref");
        this.getTopPanel().setLayout(layout);

        JPanel contentPanel = new JPanel();

        StringBuffer row = new StringBuffer("pref, 6dlu");
        for (int i = 0; i < getModel().getBewohner().size(); i++) {
            row.append(",pref, 6dlu");
        }
        for (int i = 0; i < getModel().getCustomerNoBewohner().size(); i++) {
            row.append(",pref, 6dlu");
        }

        FormLayout bewohnerLayout = new FormLayout("pref", row.toString());
        contentPanel.setLayout(bewohnerLayout);
        //contentPanel.setLayout(new GridLayout(model.getBewohnerAnzahl(), 1));

        PanelBuilder builder = new PanelBuilder(bewohnerLayout, contentPanel);
        CellConstraints cc = new CellConstraints();

        CWView bewohnerPanel=CWComponentFactory.createView(null);
        List<Bewohner> bewohnerList = getModel().getBewohner();
        int j = 1;
        for (int i = 0; i < bewohnerList.size(); i++) {
            bewohnerPanel = createBewohnerPanel(bewohnerList.get(i), getModel().getBewohnerPostingMap().get(bewohnerList.get(i)));
            builder.add(bewohnerPanel, cc.xy(1, j));
            j = j + 2;
        }

        //Falls ein Bewohner geloescht wurde, aber der Customer noch vorhanden ist wird der Storno trotzdem durchgefuehrt
        List<Customer> customerList = getModel().getCustomerNoBewohner();
        for (int i = 0; i < customerList.size(); i++) {
            bewohnerPanel = createKundePanel(customerList.get(i), getModel().getCustomerNoBewohnerMap().get(customerList.get(i)));
            builder.add(bewohnerPanel, cc.xy(1, j));
            j = j + 2;
        }

        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setPreferredSize(new Dimension(10, 10));
        addToContentPanel(scroll);
    }

    public CWView createBewohnerPanel(Bewohner b, List<AccountPosting> postingList) {

        CWView panel = CWComponentFactory.createView(null);
        panel.setHeaderInfo(new CWHeaderInfo("" + b.getCustomer().getSurname() + " " + b.getCustomer().getForename() + "     Zimmer: " + b.getZimmer().getName() + "     Bereich: " + b.getZimmer().getBereich()));

        StringBuffer row = new StringBuffer("pref, 3dlu, pref, 12dlu,pref, 3dlu, pref, 12dlu");
        for (int i = 1; i < postingList.size(); i++) {
            row.append(",pref, 3dlu, pref, 12dlu");

        }
        row.append(",pref,2dlu,pref");

        FormLayout layout = new FormLayout(
                "pref, 3dlu, pref, 16dlu, right:pref, 3dlu, pref, 16dlu, pref, 3dlu, pref,16dlu, pref, 3dlu, pref,180dlu", // columns
                row.toString());      // rows

        CellConstraints cc = new CellConstraints();
        PanelBuilder builder = new PanelBuilder(layout);

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



            storno = new JLabel("€ " + numberFormat.format(postingList.get(i).getAmount()));

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
            summeLabel = new JLabel("€ " + numberFormat.format(summe));
            summeLabel.setForeground(Color.GREEN.darker());

        } else {
            summeLabel = new JLabel("FEHLER");
            summeLabel.setForeground(Color.RED);
        }

        builder.addSeparator("", cc.xyw(1, yPos, 3));
        builder.add(sumText, cc.xy(1, yPos + 2));
        builder.add(summeLabel, cc.xy(3, yPos + 2));

        getComponentContainer()
        	.addComponent(panel);
        
        panel.addToContentPanel(builder.getPanel());
        
        return panel;
    }

    public CWView createKundePanel(Customer c, List<AccountPosting> postingList) {


        CWView panel = CWComponentFactory.createView(null);
        panel.setHeaderInfo(new CWHeaderInfo("Kunde: " + c.getSurname() + " " + c.getForename()));

        StringBuffer row = new StringBuffer("pref, 3dlu, pref, 12dlu,pref, 3dlu, pref, 12dlu");
        for (int i = 1; i < postingList.size(); i++) {
            row.append(",pref, 3dlu, pref, 12dlu");

        }
        row.append(",pref,2dlu,pref");

        FormLayout layout = new FormLayout(
                "pref, 3dlu, pref, 16dlu, right:pref, 3dlu, pref, 16dlu, pref, 3dlu, pref,16dlu, pref, 3dlu, pref,180dlu", // columns
                row.toString());      // rows

        CellConstraints cc = new CellConstraints();
        PanelBuilder builder = new PanelBuilder(layout);

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



            storno = new JLabel("€ " + numberFormat.format(postingList.get(i).getAmount()));

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
            summeLabel = new JLabel("€ " + numberFormat.format(summe));
            summeLabel.setForeground(Color.GREEN.darker());

        } else {
            summeLabel = new JLabel("FEHLER");
            summeLabel.setForeground(Color.RED);
        }

        builder.addSeparator("", cc.xyw(1, yPos, 3));
        builder.add(sumText, cc.xy(1, yPos + 2));
        builder.add(summeLabel, cc.xy(3, yPos + 2));

        getComponentContainer()
        	.addComponent(panel);
        
        panel.addToContentPanel(builder.getPanel());
        
        return panel;
    }

    @Override
    public void dispose() {
        
    	super.dispose();
    }
}
