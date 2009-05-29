/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.roommanagementmodul.pojo.Bereich;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.roommanagementmodul.pojo.Zimmer;
import java.util.List;

/**
 *
 * @author Dominik
 */
public class EditZimmerView implements Disposable{

    private EditZimmerPresentationModel model;
    public JLabel lZimmerName;
    public JLabel lBettenAnzahl;
    public JLabel lBereich;
    public JTextField tfZimmerName;
    public JTextField tfBettenAnzahl;
    public JComboBox cbBereich;
    public JButton bSave;
    public JButton bCancel;
    public JButton bSaveCancel;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel mainPanel;

    public EditZimmerView(EditZimmerPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        lZimmerName = CWComponentFactory.createLabel("Zimmer Name: ");
        lBettenAnzahl = CWComponentFactory.createLabel("Betten Anzahl: ");
        lBereich = CWComponentFactory.createLabel("Bereich: ");

        //tfZimmerName = new JTextField(model.getBufferedModel(Zimmer.PROPERTYNAME_NAME));
        //tfBettenAnzahl = new JTextField(model.getBufferedModel(Zimmer.PROPERTYNAME_ANZBETTEN));
        tfZimmerName = CWComponentFactory.createTextField(model.getBufferedModel(Zimmer.PROPERTYNAME_NAME), false);
        tfBettenAnzahl = CWComponentFactory.createTextField(model.getBufferedModel(Zimmer.PROPERTYNAME_ANZBETTEN), false);
        tfBettenAnzahl.setDocument(model.getDigitDocument());

        bSave = CWComponentFactory.createButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schließen");


        cbBereich = CWComponentFactory.createComboBox(model.getBereichList());
        //cbBereich = new JComboBox(model.createParentBereichComboModel(model.getBereichList()));

        if (model.getZimmer().getBereich() == null) {
            List<Bereich> leafList=model.getBereichManager().getBereichLeaf();
            cbBereich.setSelectedItem(leafList.get(0));
            model.getUnsaved().setValue(false);
        }

        componentContainer= CWComponentFactory.createCWComponentContainer()
                .addComponent(bSave)
                .addComponent(bCancel)
                .addComponent(bSaveCancel)
                .addComponent(cbBereich)
                .addComponent(lZimmerName)
                .addComponent(lBettenAnzahl)
                .addComponent(lBereich)
                .addComponent(tfZimmerName)
                .addComponent(tfBettenAnzahl);
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
//        builder.addSeparator("Allgemein:", cc.xyw(1, 1, 7));
        contentPanel.add(lZimmerName, cc.xy(1, 3));
        contentPanel.add(lBettenAnzahl, cc.xy(1, 5));
        contentPanel.add(lBereich, cc.xy(1, 7));

        contentPanel.add(tfZimmerName, cc.xy(3, 3));
        contentPanel.add(tfBettenAnzahl, cc.xy(3, 5));
        contentPanel.add(cbBereich, cc.xy(3, 7));

//        builder.addSeparator(lBemerkung.getText(), cc.xyw(1, 29, 7));


        mainPanel.getContentPanel().add(panel, BorderLayout.CENTER);

        mainPanel.addDisposableListener(this);
        return mainPanel;
    }

    public void dispose(){
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
}
