/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import java.awt.BorderLayout;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
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
public class EditTarifView {

    private EditTarifPresentationModel model;
    public JButton bSave;
    public JButton bReset;
    public JButton bCancel;
    public JButton bSaveCancel;
    public JLabel lAb;
    public JLabel lBis;
    public JLabel lTarif;
    public JFormattedTextField tfTarif;
    

    public EditTarifView(EditTarifPresentationModel model) {
        this.model = model;
    }

    public void initComponents() {

        bSave = new JButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = new JButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = new JButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern&Schließen");

        bReset = new JButton(model.getResetButtonAction());
        bReset.setText("Zurücksetzten");


        lAb = new JLabel("Von: ");
        lBis = new JLabel("Bis: ");
        lTarif = new JLabel("Tarif: ");

        tfTarif = BasicComponentFactory.createFormattedTextField(model.getBufferedModel(Tarif.PROPERTYNAME_TARIF), NumberFormat.getCurrencyInstance());
        tfTarif.getDocument().addDocumentListener(new UpdateDocument());

        model.getDcVon().getJCalendar().setDecorationBordersVisible(false);
        model.getDcVon().getJCalendar().getDayChooser().setDecorationBackgroundVisible(false);
        model.getDcVon().getJCalendar().getDayChooser().setWeekOfYearVisible(false);
        PropertyConnector.connectAndUpdate(model.getBufferedModel(Tarif.PROPERTYNAME_AB), model.getDcVon(), "date");

        Date vonDate = model.getVonDate();
        if (vonDate != null) {
            model.getDcVon().setDate(vonDate);
            //model.getDcVon().setEnabled(false);
        }
        if(model.getHeaderText().equals("Edit")){

            

        }
        
        model.getDcBis().getJCalendar().setDecorationBordersVisible(false);
        model.getDcBis().getJCalendar().getDayChooser().setDecorationBackgroundVisible(false);
        model.getDcBis().getJCalendar().getDayChooser().setWeekOfYearVisible(false);
        PropertyConnector.connectAndUpdate(model.getBufferedModel(Tarif.PROPERTYNAME_BIS), model.getDcBis(), "date");
    }



    public JComponent buildPanel() {
        initComponents();

        JViewPanel mainPanel = new JViewPanel();
        mainPanel.setHeaderInfo(new HeaderInfo(model.getHeaderText()));
        JButtonPanel buttonPanel = mainPanel.getButtonPanel();

        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bReset);
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

        return mainPanel;

    }

    private void initEventHandling() {
        // throw new UnsupportedOperationException("Not yet implemented");
    }

    public class UpdateDocument implements DocumentListener {

        public void insertUpdate(DocumentEvent e) {
            try {
                tfTarif.commitEdit();
            } catch (ParseException ex) {
                Logger.getLogger(EditTarifView.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        public void removeUpdate(DocumentEvent e) {
        }

        public void changedUpdate(DocumentEvent e) {
        }
    }
}