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
import java.awt.Dimension;
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
public class GebLaufView {

    private GebLaufPresentationModel model;

    private JLabel lLaufart;
    private JLabel lBetiebsart;
    private JLabel lAbrMonat;
    private JLabel lMonat;
    private JLabel lJahr;
    private JRadioButton rNormal;
    private JRadioButton rStorno;
    private JRadioButton rTestlauf;
    private JRadioButton rEchtlauf;

    private JButton startButton;

    private JTextField jahrField;

    private JComboBox monatComboBox;

    private ButtonGroup laufartGroup;
    private ButtonGroup betriebsartGroup;

    public GebLaufView(GebLaufPresentationModel model){
        this.model=model;
    }

    public void initComponents() {

        startButton= new JButton(model.getStart());
        startButton.setText("Start");

        rNormal = new JRadioButton(model.getNormalLauf());
        rNormal.setText("Normal");
        rStorno = new JRadioButton(model.getStornoLauf());
        rStorno.setText("Storno");
        laufartGroup = new ButtonGroup();

        lLaufart= new JLabel("Laufart:");
        lBetiebsart= new JLabel("Betriebsart:");
        lAbrMonat=new JLabel("Abr-Monat: ");
        lMonat= new JLabel("Monat: ");

        laufartGroup.add(rNormal);
        laufartGroup.add(rStorno);
        rNormal.setSelected(true);

        rTestlauf = new JRadioButton(model.getEchtLauf());
        rTestlauf.setText("Testlauf");
        rEchtlauf = new JRadioButton(model.getTestLauf());
        rEchtlauf.setText("Echtlauf");
        betriebsartGroup= new ButtonGroup();
        betriebsartGroup.add(rTestlauf);
        betriebsartGroup.add(rEchtlauf);
        rTestlauf.setSelected(true);

        lJahr=new JLabel("Jahr: ");
        NumberFormat format =NumberFormat.getNumberInstance();
        format.setGroupingUsed(false);
        format.setMaximumIntegerDigits(4);
        
        jahrField= new JTextField();
        jahrField.setDocument(model.getYearDocument());
        GregorianCalendar gc= new GregorianCalendar();
        jahrField.setText(""+gc.get(Calendar.YEAR));


        monatComboBox= new JComboBox(model.getMonatCbModel());


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
        FormLayout layout = new FormLayout("10dlu, pref, 4dlu, pref,4dlu,pref,20dlu,pref",
                "pref, 4dlu, pref ,10dlu,pref, 4dlu,pref, 10dlu, pref,4dlu, pref");

        contentPanel.setLayout(layout);

        CellConstraints cc = new CellConstraints();
        PanelBuilder builder = new PanelBuilder(layout,contentPanel);

        builder.addSeparator("Laufart:",    cc.xyw(1, 1, 8));
        builder.add(lLaufart, cc.xyw(1, 1, 4));
        builder.add(rNormal, cc.xy(2, 3));
        builder.add(rStorno, cc.xy(4, 3));

        builder.addSeparator("Betiebsart:", cc.xyw(1, 5, 8));
        builder.add(rTestlauf, cc.xy(2, 7));
        builder.add(rEchtlauf, cc.xy(4, 7));

        builder.addSeparator("Abrechnungs-Datum:", cc.xyw(1, 9,8));
        builder.add(lMonat, cc.xyw(2, 11,3));
        builder.add(monatComboBox, cc.xy(4, 11));
        builder.add(lJahr, cc.xy(6, 11));
        jahrField.setPreferredSize(new Dimension(35,20));
        builder.add(jahrField, cc.xy(8, 11));
        
        return mainPanel;
    }
}
