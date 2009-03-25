/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
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
public class BewohnerGUIExtentionView implements Disposable {

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
    private JDateChooser dcEinzugsdatum;
    private JDateChooser dcAuszugsdatum;
    private JCheckBox boxBewohner;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel mainPanel;
    private ItemListener bewohnerItemListener;
    private JComboBox cbKaution;
    private JComboBox cbKautionStatus;


    public BewohnerGUIExtentionView(BewohnerGUIExtentionPresentationModel model) {
        this.model = model;
    }

    public void initComponents() {

        lbBereich = CWComponentFactory.createLabel("Bereich: ");
        lbZimmer = CWComponentFactory.createLabel("Zimmer");
        lbEinzDat = CWComponentFactory.createLabel("Einzugsdatum: ");
        lbAuszDat = CWComponentFactory.createLabel("Auszugsdatum: ");
        lbKaution = CWComponentFactory.createLabel("Kaution: ");
        lbKautionStatus = CWComponentFactory.createLabel("Kaution Status: ");
        lbBewohner = CWComponentFactory.createLabel("Bewohner");

        cbBereich = CWComponentFactory.createComboBox(model.getBereichList());
        cbZimmer = CWComponentFactory.createComboBox(model.getZimmerList());
        cbKaution=CWComponentFactory.createComboBox(model.getKautionList());
        cbKaution.addItemListener(new ItemListener(){

            public void itemStateChanged(ItemEvent e) {
                if(model.getBewohner().getKaution()!=null){
                    cbKautionStatus.setEnabled(true);
                }else{
                    cbKautionStatus.setEnabled(false);
                }
            }
        });
        boxBewohner = CWComponentFactory.createCheckBox(model.getCheckBoxModel(), "Bewohner");
        bewohnerItemListener=new BewohnerItemListener();
        boxBewohner.addItemListener(bewohnerItemListener);
        dcEinzugsdatum = CWComponentFactory.createDateChooser(model.getBufferedModel(Bewohner.PROPERTYNAME_VON));
        dcAuszugsdatum = CWComponentFactory.createDateChooser(model.getBufferedModel(Bewohner.PROPERTYNAME_BIS));

        cbKautionStatus = CWComponentFactory.createComboBox(model.getKautionStadi());


        if(model.getBewohner().getKaution()==null){
            cbKautionStatus.setEnabled(false);
            cbKautionStatus.setSelectedIndex(0);
        }else{
            switch(model.getBewohner().getKautionStatus()){
                case Bewohner.NICHT_EINGEZAHLT: cbKautionStatus.setSelectedIndex(0);
                break;
                case Bewohner.EINGEZAHLT: cbKautionStatus.setSelectedIndex(1);
                break;
                case Bewohner.ZURUECK_GEZAHLT: cbKautionStatus.setSelectedIndex(2);
                break;
                case Bewohner.EINGEZOGEN: cbKautionStatus.setSelectedIndex(3);
                break;

            }
        }
        cbKautionStatus.addItemListener(model.getKautionListener());


        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(lbBereich)
                .addComponent(lbZimmer)
                .addComponent(lbEinzDat)
                .addComponent(lbAuszDat)
                .addComponent(lbKaution)
                .addComponent(lbKautionStatus)
                .addComponent(lbBewohner)
                .addComponent(cbBereich)
                .addComponent(cbZimmer)
                .addComponent(boxBewohner)
                .addComponent(dcEinzugsdatum)
                .addComponent(cbKaution)
                .addComponent(lbKaution)
                .addComponent(dcAuszugsdatum);


        if (model.getCheckBoxState() != ItemEvent.SELECTED) {
            cbBereich.setEnabled(false);
            cbZimmer.setEnabled(false);
            dcEinzugsdatum.setEnabled(false);
            dcAuszugsdatum.setEnabled(false);
            this.lbBereich.setEnabled(false);
            this.lbZimmer.setEnabled(false);
            this.lbAuszDat.setEnabled(false);
            this.lbEinzDat.setEnabled(false);
            this.lbKautionStatus.setEnabled(false);
            this.cbKautionStatus.setEnabled(false);
            this.lbKaution.setEnabled(false);
            this.cbKaution.setEnabled(false);
        }

    }

    public JComponent buildPanel() {
        initComponents();
        initEventHandling();

        mainPanel = new JViewPanel();
        mainPanel.setName("Zimmer");

        FormLayout layout = new FormLayout("right:pref, 4dlu, 50dlu:grow, 4dlu, right:pref, 4dlu, 50dlu:grow",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 20dlu, pref, 4dlu, pref, 4dlu, pref");

        mainPanel.getContentPanel().setLayout(layout);

        CellConstraints cc = new CellConstraints();
        //builder.addSeparator("Allgemein:", cc.xyw(1, 1, 7));

        mainPanel.getContentPanel().add(boxBewohner, cc.xy(1, 3));
        mainPanel.getContentPanel().add(lbBereich, cc.xy(1, 5));
        mainPanel.getContentPanel().add(lbZimmer, cc.xy(1, 7));
        mainPanel.getContentPanel().add(lbEinzDat, cc.xy(1, 9));
        mainPanel.getContentPanel().add(lbAuszDat, cc.xy(1, 11));
        mainPanel.getContentPanel().add(lbKaution, cc.xy(1, 13));
        mainPanel.getContentPanel().add(lbKautionStatus, cc.xy(1, 15));

        mainPanel.getContentPanel().add(cbBereich, cc.xy(3, 5));
        mainPanel.getContentPanel().add(cbZimmer, cc.xy(3, 7));
        mainPanel.getContentPanel().add(dcEinzugsdatum, cc.xy(3, 9));
        mainPanel.getContentPanel().add(dcAuszugsdatum, cc.xy(3, 11));
        mainPanel.getContentPanel().add(cbKaution, cc.xy(3, 13));
        mainPanel.getContentPanel().add(cbKautionStatus, cc.xy(3, 15));


        mainPanel.setHeaderInfo(model.getHeaderInfo());
        mainPanel.addDisposableListener(this);
        return mainPanel;

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
                lbKautionStatus.setEnabled(false);
                lbKaution.setEnabled(false);
                cbKaution.setEnabled(false);
                cbKautionStatus.setEnabled(false);
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
                lbKautionStatus.setEnabled(true);
                lbKaution.setEnabled(true);
                cbKaution.setEnabled(true);
                if(model.getBewohner().getKaution()!=null){
                    cbKautionStatus.setEnabled(true);
                }
            }

        }
    }

    public void dispose() {
        boxBewohner.removeItemListener(bewohnerItemListener);
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }

    private void initEventHandling() {
    }
}
