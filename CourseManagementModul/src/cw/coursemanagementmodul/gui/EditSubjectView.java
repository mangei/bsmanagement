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
public class EditSubjectView {
    private EditSubjectPresentationModel model;
    
    //*********************Komponenten definieren*******************************
    private JLabel nameLabel;
    private JTextField nameTextField;
    
    private JButton saveButton;
    private JButton saveAndCloseButton;
    private JButton rollbackButton;
    private JButton cancelButton;
    //**************************************************************************
    
    public EditSubjectView(EditSubjectPresentationModel model) {
        this.model = model;
    }
    
    public void initComponents(){
        nameLabel = CWComponentFactory.createLabel("Aktivität-Name");
        
        nameTextField = BasicComponentFactory.createTextField(model.getBufferedModel(Subject.PROPERTYNAME_NAME), false);
        
        saveButton = CWComponentFactory.createButton(model.getSaveButtonAction());
        saveAndCloseButton = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        rollbackButton = CWComponentFactory.createButton(model.getResetButtonAction());
        cancelButton = CWComponentFactory.createButton(model.getCancelButtonAction());
        
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeToLastView();
            }
        });
    }
    
    //**************************************************************************
    //Diese Methode gibt die Maske des EditPanels in Form einse JPanels zurück
    //**************************************************************************
    public JPanel buildPanel(){
        initComponents();
        
        JViewPanel view = CWComponentFactory.createViewPanel(model.getHeaderInfo());
        
        JButtonPanel buttonPanel = view.getButtonPanel();
        
        buttonPanel.add(saveButton);
        buttonPanel.add(saveAndCloseButton);
        buttonPanel.add(rollbackButton);
        buttonPanel.add(cancelButton);
        
        FormLayout layout = new FormLayout("pref, 4dlu, 200dlu:grow, 4dlu, min",
                "pref, 2dlu, pref"); // rows
        
        CellConstraints cc = new CellConstraints();
        view.getContentPanel().setLayout(layout);
        
        view.getContentPanel().add(nameLabel, cc.xy (1, 1));
        view.getContentPanel().add(nameTextField, cc.xy(3, 1));
        
        return view;
    }
    //**************************************************************************
}
