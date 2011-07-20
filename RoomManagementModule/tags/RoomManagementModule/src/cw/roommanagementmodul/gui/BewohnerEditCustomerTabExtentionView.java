package cw.roommanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWCheckBox;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWDateChooser;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.roommanagementmodul.pojo.Bewohner;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JPanel;

/**
 *
 * @author Dominik
 */
public class BewohnerEditCustomerTabExtentionView extends CWView
{

    BewohnerEditCustomerTabExtentionPresentationModel model;
    private CWLabel lbBereich;
    private CWLabel lbZimmer;
    private CWLabel lbEinzDat;
    private CWLabel lbAuszDat;
    private CWLabel lbKaution;
    private CWLabel lbKautionStatus;
    private CWLabel lbBewohner;
    private CWComboBox cbBereich;
    private CWComboBox cbZimmer;
    private CWDateChooser dcEinzugsdatum;
    private CWDateChooser dcAuszugsdatum;
    private CWCheckBox boxBewohner;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private ItemListener bewohnerItemListener;
    private CWComboBox cbKaution;
    private CWComboBox cbKautionStatus;


    public BewohnerEditCustomerTabExtentionView(BewohnerEditCustomerTabExtentionPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
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
        boxBewohner.setSelected(false);
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


        componentContainer = CWComponentFactory.createComponentContainer()
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

    private void initEventHandling() {
        
    }

    private void buildView() {
        
        this.setHeaderInfo(model.getHeaderInfo());
        this.setName("Zimmer");

        FormLayout layout = new FormLayout("right:pref, 4dlu, 50dlu:grow, 4dlu, right:pref, 4dlu, 50dlu:grow",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 20dlu, pref, 4dlu, pref, 4dlu, pref");

        this.getContentPanel().setLayout(layout);

        CellConstraints cc = new CellConstraints();
        //builder.addSeparator("Allgemein:", cc.xyw(1, 1, 7));

        JPanel contentPanel = this.getContentPanel();
        contentPanel.add(boxBewohner, cc.xy(1, 3));
        contentPanel.add(lbBereich, cc.xy(1, 5));
        contentPanel.add(lbZimmer, cc.xy(1, 7));
        contentPanel.add(lbEinzDat, cc.xy(1, 9));
        contentPanel.add(lbAuszDat, cc.xy(1, 11));
        contentPanel.add(lbKaution, cc.xy(1, 13));
        contentPanel.add(lbKautionStatus, cc.xy(1, 15));

        contentPanel.add(cbBereich, cc.xy(3, 5));
        contentPanel.add(cbZimmer, cc.xy(3, 7));
        contentPanel.add(dcEinzugsdatum, cc.xy(3, 9));
        contentPanel.add(dcAuszugsdatum, cc.xy(3, 11));
        contentPanel.add(cbKaution, cc.xy(3, 13));
        contentPanel.add(cbKautionStatus, cc.xy(3, 15));

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

    @Override
    public void dispose() {
        boxBewohner.removeItemListener(bewohnerItemListener);
        componentContainer.dispose();
        model.dispose();
    }

}
