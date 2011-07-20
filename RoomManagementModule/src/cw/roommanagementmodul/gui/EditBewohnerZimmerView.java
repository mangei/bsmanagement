package cw.roommanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWDateChooser;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWView;
import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JPanel;
import cw.roommanagementmodul.pojo.Bewohner;

/**
 *
 * @author Dominik
 */
public class EditBewohnerZimmerView extends CWView
{

    EditBewohnerZimmerPresentationModel model;
    private CWLabel lbBereich;
    private CWLabel lbZimmer;
    private CWLabel lbEinzDat;
    private CWLabel lbAuszDat;
    private CWLabel lbKaution;
    private CWLabel lbKautionStatus;
    private CWButton bSave;
    private CWButton bCancel;
    private CWButton bSaveCancel;
    private CWComboBox cbBereich;
    private CWComboBox cbZimmer;
    private CWComboBox cbKaution;
    private CWComboBox cbKautionStatus;
    private CWDateChooser dcEinzugsdatum;
    private CWDateChooser dcAuszugsdatum;
    // CWTODO create an ComponentContainer

    public EditBewohnerZimmerView(EditBewohnerZimmerPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    public void initComponents() {

        bSave = CWComponentFactory.createButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schließen");

        lbBereich = CWComponentFactory.createLabel("Bereich: ");
        lbZimmer = CWComponentFactory.createLabel("Zimmer");
        lbEinzDat = CWComponentFactory.createLabel("Einzugsdatum: ");
        lbAuszDat = CWComponentFactory.createLabel("Auszugsdatum: ");
        lbKaution = CWComponentFactory.createLabel("Kaution: ");
        lbKautionStatus = CWComponentFactory.createLabel("Kaution Status: ");

        cbKaution = CWComponentFactory.createComboBox(model.getKautionList());
        cbKaution.addItemListener(new ItemListener(){

            public void itemStateChanged(ItemEvent e) {
                cbKautionStatus.setEnabled(true);
            }

        });

        cbKautionStatus = CWComponentFactory.createComboBox(model.getKautionStatusSelection());

        //cbBereich = new JComboBox(model.createParentBereichComboModel(model.getBereichList()));
        cbBereich = CWComponentFactory.createComboBox(model.getBereichList());
        cbZimmer = CWComponentFactory.createComboBox(model.getZimmerList());

        dcEinzugsdatum = CWComponentFactory.createDateChooser(model.getBufferedModel(Bewohner.PROPERTYNAME_VON));
        dcAuszugsdatum = CWComponentFactory.createDateChooser(model.getBufferedModel(Bewohner.PROPERTYNAME_BIS));
    }

    private void initEventHandling() {
        if(model.getBewohner().getKaution()==null){
            cbKautionStatus.setEnabled(false);
        }
    }

    private void buildView() {
        this.setHeaderInfo(new CWHeaderInfo("Bewohner: " + model.getHeaderText()));
        CWButtonPanel buttonPanel = this.getButtonPanel();

        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);

        JPanel panel = this.getContentPanel();

        //panel.setName("Zimmer");

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

        this.getContentPanel().add(panel, BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        
        model.dispose();
    }

}