/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import cw.roommanagementmodul.pojo.Bewohner;

/**
 *
 * @author Dominik
 */
public class BewohnerGUIExtentionView {

    BewohnerGUIExtentionPresentationModel model;
    
    private JLabel lbBereich;
    private JLabel lbZimmer;
    private JLabel lbEinzDat;
    private JLabel lbAuszDat;
    private JLabel lbKaution;
    private JLabel lbKautionStatus;
    
    private JComboBox cbBereich;
    private JComboBox cbZimmer;
    private JComboBox cbKaution;
    private JComboBox cbKautionStatus;
    private JDateChooser dcEinzugsdatum;
    private JDateChooser dcAuszugsdatum;

    public BewohnerGUIExtentionView(BewohnerGUIExtentionPresentationModel model) {
        this.model = model;
    }

    public void initComponents() {

        lbBereich = new JLabel("Bereich: ");
        lbZimmer = new JLabel("Zimmer");
        lbEinzDat = new JLabel("Einzugsdatum: ");
        lbAuszDat = new JLabel("Auszugsdatum: ");
        lbKaution = new JLabel("Kaution: ");
        lbKautionStatus = new JLabel("Kaution Status: ");
        
        //cbBereich = new JComboBox(model.createParentBereichComboModel(model.getBereichList()));
        
        cbBereich = new JComboBox(model.createBereichComboModel(model.getBereichList()));
        cbZimmer = new JComboBox(model.createZimmerComboModel(model.getZimmerList()));
        cbKaution = new JComboBox(model.createKautionComboModel(model.getKautionList()));
        cbKaution.addItemListener(new ItemListener(){

            public void itemStateChanged(ItemEvent e) {
                cbKautionStatus.setEnabled(true);
            }
            
        });

        cbKautionStatus = new JComboBox();
        cbKautionStatus.addItem("Keine Kaution");
        cbKautionStatus.addItem("Nicht eingezahlt");
        cbKautionStatus.addItem("Eingezahlt");
        cbKautionStatus.addItem("Zurück gezahlt");
        if(model.getBewohner().getKaution()==null){
            cbKautionStatus.setEnabled(false);
        }else{
            switch(model.getBewohner().getKautionStatus()){
                case Bewohner.EINGEZAHLT: cbKautionStatus.setSelectedIndex(2);
                break;
                case Bewohner.KEINE_KAUTION: cbKautionStatus.setSelectedIndex(0);
                break;
                case Bewohner.NICHT_EINGEZAHLT: cbKautionStatus.setSelectedIndex(1);
                break;
                case Bewohner.ZURUECK_GEZAHLT: cbKautionStatus.setSelectedIndex(3);
                break;
            }
        }
        cbKautionStatus.addItemListener(model.getKautionListener());

        dcEinzugsdatum = new JDateChooser();
        dcEinzugsdatum.getJCalendar().setDecorationBordersVisible(false);
        dcEinzugsdatum.getJCalendar().getDayChooser().setDecorationBackgroundVisible(false);
        dcEinzugsdatum.getJCalendar().getDayChooser().setWeekOfYearVisible(false);
        PropertyConnector.connectAndUpdate(model.getBufferedModel(Bewohner.PROPERTYNAME_VON), dcEinzugsdatum, "date");

        dcAuszugsdatum = new JDateChooser();
        dcAuszugsdatum.getJCalendar().setDecorationBordersVisible(false);
        dcAuszugsdatum.getJCalendar().getDayChooser().setDecorationBackgroundVisible(false);
        dcAuszugsdatum.getJCalendar().getDayChooser().setWeekOfYearVisible(false);
        PropertyConnector.connectAndUpdate(model.getBufferedModel(Bewohner.PROPERTYNAME_BIS), dcAuszugsdatum, "date");
    }


    public JComponent buildPanel() {
        initComponents();
        initEventHandling();

        JViewPanel panel = new JViewPanel();
        panel.setName("Zimmer");

        FormLayout layout = new FormLayout("right:pref, 4dlu, 50dlu:grow, 4dlu, right:pref, 4dlu, 50dlu:grow",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

        panel.setLayout(layout);

        CellConstraints cc = new CellConstraints();
        //builder.addSeparator("Allgemein:", cc.xyw(1, 1, 7));
        panel.add(lbBereich, cc.xy(1, 3));
        panel.add(lbZimmer, cc.xy(1, 5));
        panel.add(lbEinzDat, cc.xy(1, 7));
        panel.add(lbAuszDat, cc.xy(1, 9));
        panel.add(lbKaution, cc.xy(1, 13));
        panel.add(lbKautionStatus, cc.xy(1, 15));

        panel.add(cbBereich, cc.xy(3, 3));
        panel.add(cbZimmer, cc.xy(3, 5));
        panel.add(dcEinzugsdatum, cc.xy(3, 7));
        panel.add(dcAuszugsdatum, cc.xy(3, 9));
        panel.add(cbKaution, cc.xy(3, 13));
        panel.add(cbKautionStatus, cc.xy(3, 15));



        return panel;

    }

    private void initEventHandling() {
    }
}
