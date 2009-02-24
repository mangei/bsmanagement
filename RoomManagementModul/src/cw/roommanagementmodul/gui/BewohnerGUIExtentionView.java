/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import cw.roommanagementmodul.pojo.Bewohner;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;

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
    private JLabel lbBewohner;
    private JComboBox cbBereich;
    private JComboBox cbZimmer;
    private JComboBox cbKaution;
    private JComboBox cbKautionStatus;
    private JDateChooser dcEinzugsdatum;
    private JDateChooser dcAuszugsdatum;
    private JCheckBox boxBewohner;

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
        lbBewohner = new JLabel("Bewohner");


        //cbBereich = new JComboBox(model.createBereichComboModel(model.getBereichList()));
        cbBereich = CWComponentFactory.createComboBox(model.getBereichList());
        //cbZimmer = new JComboBox(model.createZimmerComboModel(model.getZimmerList()));
        cbZimmer = CWComponentFactory.createComboBox(model.getZimmerList());

        boxBewohner = CWComponentFactory.createCheckBox(model.getCheckBoxModel(), "Bewohner");
        boxBewohner.addItemListener(new BewohnerItemListener());

//        cbKaution = new JComboBox(model.createKautionComboModel(model.getKautionList()));
//        cbKaution.addItemListener(new ItemListener(){
//
//            public void itemStateChanged(ItemEvent e) {
//                cbKautionStatus.setEnabled(true);
//            }
//
//        });
//
//        cbKautionStatus = new JComboBox();
//        cbKautionStatus.addItem("Keine Kaution");
//        cbKautionStatus.addItem("Nicht eingezahlt");
//        cbKautionStatus.addItem("Eingezahlt");
//        cbKautionStatus.addItem("Zurück gezahlt");
//        if(model.getBewohner().getKaution()==null){
//            //cbKautionStatus.setEnabled(false);
//        }else{
//            switch(model.getBewohner().getKautionStatus()){
//                case Bewohner.EINGEZAHLT: cbKautionStatus.setSelectedIndex(2);
//                break;
//                case Bewohner.KEINE_KAUTION: cbKautionStatus.setSelectedIndex(0);
//                break;
//                case Bewohner.NICHT_EINGEZAHLT: cbKautionStatus.setSelectedIndex(1);
//                break;
//                case Bewohner.ZURUECK_GEZAHLT: cbKautionStatus.setSelectedIndex(3);
//                break;
//            }
//        }
//        cbKautionStatus.addItemListener(model.getKautionListener());

        dcEinzugsdatum = CWComponentFactory.createDateChooser(model.getBufferedModel(Bewohner.PROPERTYNAME_VON));
        dcAuszugsdatum = CWComponentFactory.createDateChooser(model.getBufferedModel(Bewohner.PROPERTYNAME_BIS));


        if (model.getCheckBoxState() != ItemEvent.SELECTED) {
            cbBereich.setEnabled(false);
            cbZimmer.setEnabled(false);
            dcEinzugsdatum.setEnabled(false);
            dcAuszugsdatum.setEnabled(false);
            this.lbBereich.setEnabled(false);
            this.lbZimmer.setEnabled(false);
            this.lbAuszDat.setEnabled(false);
            this.lbEinzDat.setEnabled(false);
        }

    }

    public JComponent buildPanel() {
        initComponents();
        initEventHandling();

        JViewPanel panel = new JViewPanel();
        panel.setName("Zimmer");

        FormLayout layout = new FormLayout("right:pref, 4dlu, 50dlu:grow, 4dlu, right:pref, 4dlu, 50dlu:grow",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

        panel.getContentPanel().setLayout(layout);

        CellConstraints cc = new CellConstraints();
        //builder.addSeparator("Allgemein:", cc.xyw(1, 1, 7));

        panel.getContentPanel().add(boxBewohner, cc.xy(1, 3));
        panel.getContentPanel().add(lbBereich, cc.xy(1, 5));
        panel.getContentPanel().add(lbZimmer, cc.xy(1, 7));
        panel.getContentPanel().add(lbEinzDat, cc.xy(1, 9));
        panel.getContentPanel().add(lbAuszDat, cc.xy(1, 11));
        //panel.add(lbKaution, cc.xy(1, 13));
        //panel.add(lbKautionStatus, cc.xy(1, 15));

        panel.getContentPanel().add(cbBereich, cc.xy(3, 5));
        panel.getContentPanel().add(cbZimmer, cc.xy(3, 7));
        panel.getContentPanel().add(dcEinzugsdatum, cc.xy(3, 9));
        panel.getContentPanel().add(dcAuszugsdatum, cc.xy(3, 11));
//        panel.add(cbKaution, cc.xy(3, 13));
//        panel.add(cbKautionStatus, cc.xy(3, 15));


        panel.setHeaderInfo(model.getHeaderInfo());
        return panel;

    }

    public class BewohnerItemListener implements ItemListener {

        public void itemStateChanged(ItemEvent e) {

            if (e.getStateChange() == ItemEvent.DESELECTED) {
                cbBereich.setEnabled(false);
                cbZimmer.setEnabled(false);
                dcEinzugsdatum.setEnabled(false);
                dcAuszugsdatum.setEnabled(false);
                lbBereich.setEnabled(false);
                lbZimmer.setEnabled(false);
                lbAuszDat.setEnabled(false);
                lbEinzDat.setEnabled(false);
                model.getCheckBoxModel().setValue(false);
            }
            if (e.getStateChange() == ItemEvent.SELECTED) {
                cbBereich.setEnabled(true);
                cbZimmer.setEnabled(true);
                dcEinzugsdatum.setEnabled(true);
                dcAuszugsdatum.setEnabled(true);
                lbBereich.setEnabled(true);
                lbZimmer.setEnabled(true);
                lbAuszDat.setEnabled(true);
                lbEinzDat.setEnabled(true);
                model.getCheckBoxModel().setValue(true);
            }

        }
    }

    private void initEventHandling() {
    }
}
