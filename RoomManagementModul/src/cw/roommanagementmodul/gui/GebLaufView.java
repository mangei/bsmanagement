/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Dominik
 */
public class GebLaufView implements ItemListener {

    private GebLaufPresentationModel model;
    private JLabel lLaufart;
    private JLabel lBetiebsart;
    private JLabel lAbrMonat;
    private JLabel lMonat;
    private JLabel lJahr;
    private JLabel lGebLauf;
    private JRadioButton rNormal;
    private JRadioButton rStorno;
    private JRadioButton rTestlauf;
    private JRadioButton rEchtlauf;
    private JButton startButton;
    private JTextField jahrField;
    private JComboBox monatComboBox;
    private JComboBox gebLaufComboBox;
    private ButtonGroup laufartGroup;
    private ButtonGroup betriebsartGroup;

    public GebLaufView(GebLaufPresentationModel model) {
        this.model = model;
    }

    public void initComponents() {

        startButton = new JButton(model.getStart());
        startButton.setText("Start");

        rNormal = CWComponentFactory.createRadioButton(model.getNormalLauf());
        rNormal.setText("Normal");
        rNormal.setSelected(true);
        rStorno = CWComponentFactory.createRadioButton(model.getStornoLauf());
        rStorno.setText("Storno");
        laufartGroup = new ButtonGroup();

        lLaufart = new JLabel("Laufart:");
        lBetiebsart = new JLabel("Betriebsart:");
        lAbrMonat = new JLabel("Abr-Monat: ");
        lMonat = new JLabel("Monat: ");
        lGebLauf = new JLabel("Gebühren Lauf: ");

        laufartGroup.add(rNormal);
        laufartGroup.add(rStorno);


        rTestlauf = CWComponentFactory.createRadioButton(model.getEchtLauf());
        rTestlauf.setText("Testlauf");
        rEchtlauf = CWComponentFactory.createRadioButton(model.getTestLauf());
        rEchtlauf.setText("Echtlauf");
        betriebsartGroup = new ButtonGroup();
        betriebsartGroup.add(rTestlauf);
        betriebsartGroup.add(rEchtlauf);
        rTestlauf.setSelected(true);

        lJahr = new JLabel("Jahr: ");
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setGroupingUsed(false);
        format.setMaximumIntegerDigits(4);

        jahrField = new JTextField();
        jahrField.setDocument(model.getYearDocument());
        GregorianCalendar gc = new GregorianCalendar();
        jahrField.setText("" + gc.get(Calendar.YEAR));


        monatComboBox = new JComboBox(model.getMonatCbModel());
        gebLaufComboBox = CWComponentFactory.createComboBox(model.getGebLaufList());

        rNormal.addItemListener(this);
        rStorno.addItemListener(this);

        lGebLauf.setEnabled(false);
        gebLaufComboBox.setEnabled(false);

    }

    public JComponent buildPanel() {
        initComponents();

        JViewPanel mainPanel = new JViewPanel();
        mainPanel.setHeaderInfo(new HeaderInfo(model.getHeaderText()));

        JButtonPanel buttonPanel = mainPanel.getButtonPanel();

        buttonPanel.add(startButton);

        JPanel contentPanel = mainPanel.getContentPanel();

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

        return mainPanel;
    }

    public void itemStateChanged(ItemEvent e) {

        if (e.getStateChange() == ItemEvent.SELECTED) {

            if ((model.getStornoInt() + 1 )% 2 == 0) {
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
}
