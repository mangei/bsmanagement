/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cw.roommanagementmodul.pojo.Bereich;
import cw.roommanagementmodul.pojo.manager.BereichManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Dominik
 */
public class EditBereichView implements Disposable{

    private EditBereichPresentationModel model;
    private JButton bSave;
    private JButton bReset;
    private JButton bCancel;
    private JButton bSaveCancel;
    private JLabel lName;
    private JTextField tfName;
    private JLabel lParentBereich;
    private JComboBox parentComboBox;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel mainPanel;

    public EditBereichView(EditBereichPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {
        lName = CWComponentFactory.createLabel("Name: ");
        lParentBereich = CWComponentFactory.createLabel("Übergeordneter Bereich: ");

        //ComboBox
        parentComboBox = CWComponentFactory.createComboBox(model.getBereichList());

        BereichManager bManager= BereichManager.getInstance();
        //bManager.refreshBereich(model.getVaterBereich());
        if (model.getVaterBereich() != null && model.getHeaderText().equals("Bereich erstellen")) {
            if (model.getVaterBereich().getZimmerList() == null || model.getVaterBereich().getZimmerList().size() == 0) {
                parentComboBox.setSelectedItem(model.getVaterBereich());
                model.getUnsaved().setValue(false);
            } else {
                JOptionPane.showMessageDialog(null, "Im ausgewählten Bereich kann kein untergeordneter Bereich hinzugefügt werden," +
                        " weil sich in diesem Bereich bereits Zimmer befinden!");
                parentComboBox.setSelectedItem(model.getBereichManager().getRoot());
                model.getUnsaved().setValue(false);
            }
        }

        if (model.getBereichList().getList().size() == 1 && model.getHeaderText().equals("Bereich erstellen")) {
            parentComboBox.setSelectedItem(model.getBereichManager().getRoot());
            model.getUnsaved().setValue(false);
        }
        if(model.getVaterBereich()==null && model.getHeaderText().equals("Bereich erstellen")&& model.getBereichList().getList().size() > 1){
            parentComboBox.setSelectedItem(model.getBereichManager().getRoot());
            model.getUnsaved().setValue(false);
        }


        tfName = CWComponentFactory.createTextField(model.getBufferedModel(Bereich.PROPERTYNAME_NAME), false);

        bSave = CWComponentFactory.createButton(model.getSaveButtonAction());
        bSave.setText("Speichern");

        bCancel = CWComponentFactory.createButton(model.getCancelButtonAction());
        bCancel.setText("Abbrechen");

        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        bSaveCancel.setText("Speichern u. Schließen");

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(lName)
                .addComponent(lParentBereich)
                .addComponent(parentComboBox)
                .addComponent(tfName)
                .addComponent(bSave)
                .addComponent(bCancel)
                .addComponent(bSaveCancel);
    }

    public JComponent buildPanel() {
        initComponents();

        mainPanel = new JViewPanel(model.getHeaderInfo());
        JButtonPanel buttonPanel = mainPanel.getButtonPanel();

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
        contentPanel.add(lName, cc.xy(1, 3));
        contentPanel.add(lParentBereich, cc.xy(1, 5));

        contentPanel.add(tfName, cc.xy(3, 3));
        contentPanel.add(parentComboBox, cc.xy(3, 5));

//        builder.addSeparator(lBemerkung.getText(), cc.xyw(1, 29, 7));


        mainPanel.getContentPanel().add(panel, BorderLayout.CENTER);

        mainPanel.addDisposableListener(this);
        return mainPanel;
    }

    public void dispose() {
        mainPanel.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
}
