package cw.coursemanagementmodul.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import cw.coursemanagementmodul.pojo.Subject;

/**
 *
 * @author André Salmhofer
 */
public class EditSubjectView extends CWView
{
    private EditSubjectPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //*********************Komponenten definieren*******************************
    private CWLabel nameLabel;
    private CWTextField nameTextField;
    
    private CWButton saveButton;
    private CWButton saveAndCloseButton;
    private CWButton cancelButton;

    //**************************************************************************
    
    public EditSubjectView(EditSubjectPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }
    
    private void initComponents(){
        nameLabel = CWComponentFactory.createLabel("Gegenstand:");
        
        nameTextField = CWComponentFactory.createTextField(model.getBufferedModel(Subject.PROPERTYNAME_NAME), false);

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

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(cancelButton)
                .addComponent(nameLabel)
                .addComponent(nameTextField)
                .addComponent(saveAndCloseButton)
                .addComponent(saveButton);
        
    }

    private void initEventHandling() {

    }

    //**************************************************************************
    //Diese Methode gibt die Maske des EditPanels in Form einse JPanels zurück
    //**************************************************************************
    private void buildView(){

        this.setHeaderInfo(model.getHeaderInfo());
        
        CWButtonPanel buttonPanel = this.getButtonPanel();
        
        buttonPanel.add(saveButton);
        buttonPanel.add(saveAndCloseButton);
        buttonPanel.add(cancelButton);
        
        FormLayout layout = new FormLayout("pref, 4dlu, 200dlu, 4dlu, min",
                "pref, 2dlu, pref"); // rows
        
        CellConstraints cc = new CellConstraints();
        this.getContentPanel().setLayout(layout);
        
        this.getContentPanel().add(nameLabel, cc.xy (1, 1));
        this.getContentPanel().add(nameTextField, cc.xy(3, 1));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
    //**************************************************************************
}
