/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWCurrencyTextField;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import cw.roommanagementmodul.pojo.Tarif;
import java.util.Date;

/**
 *
 * @author Dominik
 */
public class EditTarifView implements Disposable{

    private EditTarifPresentationModel model;
    private JButton bSave;
    private JButton bCancel;
    private JButton bSaveCancel;
    private JLabel lAb;
    private JLabel lBis;
    private JLabel lTarif;
    private CWCurrencyTextField tfTarif;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel mainPanel;

    public EditTarifView(EditTarifPresentationModel model) {
        this.model = model;
    }

    public void initComponents() {

        bSave = CWComponentFactory.createButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schließen");


        lAb = CWComponentFactory.createLabel("Von: ");
        lBis = CWComponentFactory.createLabel("Bis: ");
        lTarif = CWComponentFactory.createLabel("Tarif: ");

        tfTarif = CWComponentFactory.createCurrencyTextField(model.getBufferedModel(Tarif.PROPERTYNAME_TARIF));

        if (model.getHeaderText().equals("Tarif erstellen")) {
            Date vonDate = model.getVonDate();
            if (vonDate != null) {
                model.getDcVon().setDate(vonDate);
                model.getUnsaved().setValue(false);
            }
        }
        if(model.getHeaderText().equals("Tarif bearbeiten")){
            model.setOldVon(model.getDcVon().getDate());
            model.setOldBis(model.getDcBis().getDate());

        }
        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(lAb)
                .addComponent(lBis)
                .addComponent(lTarif)
                .addComponent(bSave)
                .addComponent(bCancel)
                .addComponent(bSaveCancel)
                .addComponent(model.getDcBis())
                .addComponent(model.getDcVon())
                .addComponent(tfTarif);



    }

    public JComponent buildPanel() {
        initComponents();

        mainPanel = new JViewPanel(model.getHeaderInfo());
        CWButtonPanel buttonPanel = mainPanel.getButtonPanel();

        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);

        JViewPanel panel = new JViewPanel();
        JPanel contentPanel = panel.getContentPanel();

        /**
         * Boxes
         */
        FormLayout layout = new FormLayout("right:pref, 4dlu, 50dlu:grow, 4dlu, right:pref, 4dlu, 50dlu:grow",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

        contentPanel.setLayout(layout);

        CellConstraints cc = new CellConstraints();
        contentPanel.add(lAb, cc.xy(1, 3));
        contentPanel.add(lBis, cc.xy(1, 5));
        contentPanel.add(lTarif, cc.xy(1, 7));

        contentPanel.add(model.getDcVon(), cc.xy(3, 3));
        contentPanel.add(model.getDcBis(), cc.xy(3, 5));
        contentPanel.add(tfTarif, cc.xy(3, 7));

        mainPanel.getContentPanel().add(panel, BorderLayout.CENTER);
        mainPanel.addDisposableListener(this);
        return mainPanel;

    }

    private void initEventHandling() {
        // throw new UnsupportedOperationException("Not yet implemented");
    }

    public void dispose() {
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }

    public class UpdateDocument implements DocumentListener {

        public void insertUpdate(DocumentEvent e) {
//            try {
//                tfTarif.commitEdit();
//            } catch (ParseException ex) {
//                Logger.getLogger(EditTarifView.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }

        public void removeUpdate(DocumentEvent e) {
        }

        public void changedUpdate(DocumentEvent e) {
        }
    }
}
