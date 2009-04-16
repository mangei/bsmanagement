/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

import com.jgoodies.binding.adapter.BasicComponentFactory;
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
import javax.swing.JTextField;
import cw.coursemanagementmodul.pojo.Subject;

/**
 *
 * @author André Salmhofer
 */
public class EditSubjectView implements Disposable{
    private EditSubjectPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //*********************Komponenten definieren*******************************
    private JLabel nameLabel;
    private JTextField nameTextField;
    
    private JButton saveButton;
    private JButton saveAndCloseButton;
    private JButton cancelButton;

    private JViewPanel view;
    //**************************************************************************
    
    public EditSubjectView(EditSubjectPresentationModel model) {
        this.model = model;
    }
    
    public void initComponents(){
        nameLabel = CWComponentFactory.createLabel("Gegenstand:");
        
        nameTextField = BasicComponentFactory.createTextField(model.getBufferedModel(Subject.PROPERTYNAME_NAME), false);

        saveButton = CWComponentFactory.createButton(model.getSaveButtonAction());
        saveAndCloseButton = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        cancelButton = CWComponentFactory.createButton(model.getCancelButtonAction());

        saveButton.setToolTipText(CWComponentFactory.createToolTip(
                "Speichern",
                "Hier wird der Kursgegenstand gespeichert!",
                "cw/coursemanagementmodul/images/save.png"));
        saveAndCloseButton.setToolTipText(CWComponentFactory.createToolTip(
                "Speichern u. Schließen",
                "Hier wird der Kursgegenstand gespeichert und anschließend in die Gegenstandsübersicht gewechselt!",
                "cw/coursemanagementmodul/images/save_cancel.png"));
        cancelButton.setToolTipText(CWComponentFactory.createToolTip(
                "Abbrechen",
                "Hier kehren Sie zur Kursgegenstandsübersicht zurück!",
                "cw/coursemanagementmodul/images/cancel.png"));
        
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeToLastView();
            }
        });

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(cancelButton)
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
                "pref, 2dlu, pref"); // rows
        
        CellConstraints cc = new CellConstraints();
        view.getContentPanel().setLayout(layout);
        
        view.getContentPanel().add(nameLabel, cc.xy (1, 1));
        view.getContentPanel().add(nameTextField, cc.xy(3, 1));
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
