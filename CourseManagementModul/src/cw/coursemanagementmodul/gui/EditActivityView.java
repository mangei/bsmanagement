/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import cw.coursemanagementmodul.pojo.Activity;

/**
 *
 * @author André Salmhofer
 */
public class EditActivityView implements Disposable{
    private EditActivityPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //*********************Komponenten definieren*******************************
    private JLabel nameLabel;
    private JLabel descLabel;
    private JTextField nameTextField;
    private JTextField amountTextField;
    private JTextArea descTextField;
    
    private JButton saveButton;
    private JButton saveAndCloseButton;
    private JButton cancelButton;

    private JViewPanel view;
    //**************************************************************************
    
    public EditActivityView(EditActivityPresentationModel model) {
        this.model = model;
    }
    
    public void initComponents(){
        nameLabel = CWComponentFactory.createLabel("Aktivität-Name");
        descLabel = CWComponentFactory.createLabel("Beschreibung");
        
        nameTextField = CWComponentFactory.createTextField(model.getBufferedModel(Activity.PROPERTYNAME_NAME), false);
        descTextField = CWComponentFactory.createTextArea(model.getBufferedModel(Activity.PROPERTYNAME_DESCRIPTION), false);
        amountTextField = CWComponentFactory.createCurrencyTextField(model.getBufferedModel(Activity.PROPERTYNAME_PRICE));

        saveButton = CWComponentFactory.createButton(model.getSaveButtonAction());
        saveAndCloseButton = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        cancelButton = CWComponentFactory.createButton(model.getCancelButtonAction());

        saveButton.setToolTipText(CWComponentFactory.createToolTip(
                "Speichern",
                "Hier wird die Aktivität gespeichert!",
                "cw/coursemanagementmodul/images/save.png"));
        saveAndCloseButton.setToolTipText(CWComponentFactory.createToolTip(
                "Speichern u. Schließen",
                "Hier wird die Aktivität gespeichert und anschließend in die Aktivitätsübersicht gewechselt!",
                "cw/coursemanagementmodul/images/save_cancel.png"));
        cancelButton.setToolTipText(CWComponentFactory.createToolTip(
                "Abbrechen",
                "Hier kehren Sie zur Aktivitätsübersicht zurück!",
                "cw/coursemanagementmodul/images/cancel.png"));
        
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeToLastView();
            }
        });

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(amountTextField)
                .addComponent(cancelButton)
                .addComponent(descLabel)
                .addComponent(descTextField)
                .addComponent(nameLabel)
                .addComponent(nameTextField)
                .addComponent(saveAndCloseButton)
                .addComponent(saveButton);

        view = CWComponentFactory.createViewPanel(model.getHeaderInfo());
    }
    
    //**************************************************************************
    //Diese Methode gibt die Maske des EditPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        
        JButtonPanel buttonPanel = view.getButtonPanel();
        
        buttonPanel.add(saveButton);
        buttonPanel.add(saveAndCloseButton);
        buttonPanel.add(cancelButton);
        
        FormLayout layout = new FormLayout("pref, 4dlu, 200dlu, 4dlu, min",
                "pref, 2dlu, pref, 2dlu, pref, 2dlu, pref"); // rows

        layout.setRowGroups(new int[][]{{1, 3, 5}});
        
        CellConstraints cc = new CellConstraints();
        view.getContentPanel().setLayout(layout);
        
        view.getContentPanel().add(nameLabel, cc.xy (1, 1));
        view.getContentPanel().add(nameTextField, cc.xy(3, 1));
        view.getContentPanel().add(descLabel, cc.xy(1, 3));
        view.getContentPanel().add(descTextField, cc.xy(3, 3));
        view.getContentPanel().add(new JLabel("Preis"), cc.xy(1, 5));
        view.getContentPanel().add(amountTextField, cc.xy(3, 5));
        view.addDisposableListener(this);
        return view;
    }

    public void dispose() {
        view.removeDisposableListener(this);
        componentContainer.dispose();
        model.dispose();
    }
    //**************************************************************************
}
