package cw.roommanagementmodul.gui;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;

import javax.swing.ButtonGroup;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWIntegerTextField;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWRadioButton;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Dominik
 */
public class GebLaufView
	extends CWView<GebLaufPresentationModel>
	implements ItemListener {

    private CWLabel lLaufart;
    private CWLabel lBetiebsart;
    private CWLabel lAbrMonat;
    private CWLabel lMonat;
    private CWLabel lJahr;
    private CWLabel lGebLauf;
    private CWRadioButton rNormal;
    private CWRadioButton rStorno;
    private CWRadioButton rTestlauf;
    private CWRadioButton rEchtlauf;
    private CWButton startButton;
    private CWIntegerTextField jahrField;
    private CWComboBox monatComboBox;
    private CWComboBox gebLaufComboBox;
    private ButtonGroup laufartGroup;
    private ButtonGroup betriebsartGroup;

    public GebLaufView(GebLaufPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();

        startButton = CWComponentFactory.createButton(getModel().getStart());
        startButton.setText("Start");

        rNormal = CWComponentFactory.createRadioButton(getModel().getNormalLauf());
        rNormal.setText("Normal");
        rNormal.setSelected(true);
        rStorno = CWComponentFactory.createRadioButton(getModel().getStornoLauf());
        rStorno.setText("Storno");
        laufartGroup = new ButtonGroup();

        lLaufart = CWComponentFactory.createLabel("Laufart:");
        lBetiebsart = CWComponentFactory.createLabel("Betriebsart:");
        lAbrMonat = CWComponentFactory.createLabel("Abr-Monat: ");
        lMonat = CWComponentFactory.createLabel("Monat: ");
        lGebLauf = CWComponentFactory.createLabel("Gebuehren Lauf: ");

        laufartGroup.add(rNormal);
        laufartGroup.add(rStorno);

        // Test und Echtlauf vertauscht
        rTestlauf = CWComponentFactory.createRadioButton(getModel().getTestLauf());
        rTestlauf.setText("Testlauf");
        rEchtlauf = CWComponentFactory.createRadioButton(getModel().getEchtLauf());
        rEchtlauf.setText("Echtlauf");
        betriebsartGroup = new ButtonGroup();
        betriebsartGroup.add(rTestlauf);
        betriebsartGroup.add(rEchtlauf);
        rTestlauf.setSelected(true);

        lJahr = CWComponentFactory.createLabel("Jahr: ");
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setGroupingUsed(false);
        format.setMaximumIntegerDigits(4);

        jahrField = CWComponentFactory.createIntegerTextField(getModel().getYearFocus(),4);

//        GregorianCalendar gc = new GregorianCalendar();
//        jahrField.setText("" + gc.get(Calendar.YEAR));


        monatComboBox = CWComponentFactory.createComboBox(getModel().getMonatCbModel());
        gebLaufComboBox = CWComponentFactory.createComboBox(getModel().getGebLaufList());

        rNormal.addItemListener(this);
        rStorno.addItemListener(this);

        lGebLauf.setEnabled(false);
        gebLaufComboBox.setEnabled(false);

        getComponentContainer()
        	.addComponent(lLaufart)
        	.addComponent(lBetiebsart)
        	.addComponent(lAbrMonat)
        	.addComponent(lGebLauf)
        	.addComponent(lMonat)
        	.addComponent(lJahr)
        	.addComponent(rNormal)
        	.addComponent(rStorno)
        	.addComponent(startButton)
        	.addComponent(rTestlauf)
        	.addComponent(rEchtlauf)
        	.addComponent(jahrField)
        	.addComponent(gebLaufComboBox)
        	.addComponent(monatComboBox);
    }

    public void buildView() {
    	super.buildView();

        this.setHeaderInfo(getModel().getHeaderInfo());
        CWButtonPanel buttonPanel = this.getButtonPanel();

        buttonPanel.add(startButton);

        CWPanel contentPanel = CWComponentFactory.createPanel();

        /**
         * Boxes
         */
        FormLayout layout = new FormLayout("10dlu, pref, 4dlu, pref,10dlu,pref,4dlu,pref",
                "pref, 4dlu, pref ,4dlu,pref, 4dlu,pref, 40dlu, pref,4dlu, pref,25dlu,pref,4dlu,pref");

        contentPanel.setLayout(layout);

        CellConstraints cc = new CellConstraints();
        PanelBuilder builder = new PanelBuilder(layout, contentPanel);

        builder.addSeparator("Laufart:", cc.xyw(1, 1, 8));
        builder.add(lLaufart, cc.xyw(1, 1, 4));
        builder.add(rNormal, cc.xy(2, 3));
        builder.add(rStorno, cc.xy(4, 3));

        builder.addSeparator("Betiebsart:", cc.xyw(1, 5, 8));
        builder.add(rTestlauf, cc.xy(2, 7));
        builder.add(rEchtlauf, cc.xy(4, 7));

        builder.addSeparator("Abrechnungs-Datum:", cc.xyw(1, 9, 8));
        builder.add(lMonat, cc.xyw(2, 11, 3));
        builder.add(monatComboBox, cc.xy(4, 11));
        builder.add(lJahr, cc.xy(6, 11));
        jahrField.setPreferredSize(new Dimension(35, 20));
        builder.add(jahrField, cc.xy(8, 11));


        builder.addSeparator("Storno-Lauf:", cc.xyw(1, 13, 8));
        builder.add(lGebLauf, cc.xy(2, 15));
        builder.add(gebLaufComboBox, cc.xy(4, 15));

        addToContentPanel(contentPanel);
    }

    public void itemStateChanged(ItemEvent e) {

        if (e.getStateChange() == ItemEvent.SELECTED) {

            //Unbedingt Ueberarbeiten:
            //Je nachdem ob Storno aktiviert oder deaktiviert ist sollen die
            //dazu gehoerigen Komponenten enabled oder disabled werden
            if ((getModel().getStornoInt() + 1) % 2 == 0) {
                lMonat.setEnabled(false);
                monatComboBox.setEnabled(false);
                lJahr.setEnabled(false);
                jahrField.setEnabled(false);

                lGebLauf.setEnabled(true);
                gebLaufComboBox.setEnabled(true);
            } else {
                lMonat.setEnabled(true);
                monatComboBox.setEnabled(true);
                lJahr.setEnabled(true);
                jahrField.setEnabled(true);

                lGebLauf.setEnabled(false);
                gebLaufComboBox.setEnabled(false);
            }
        }
    }

    @Override
    public void dispose() {
        rNormal.removeItemListener(this);
        rStorno.removeItemListener(this);
        
        super.dispose();
    }
}
