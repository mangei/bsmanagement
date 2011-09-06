package cw.coursemanagementmodul.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWCurrencyTextField;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWTextArea;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.coursemanagementmodul.pojo.Activity;

/**
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class EditActivityView extends CWView
{
    private EditActivityPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    
    //*********************Komponenten definieren*******************************
    private CWLabel nameLabel;
    private CWLabel descLabel;
    private CWLabel amountLabel;
    private CWTextField nameTextField;
    private CWCurrencyTextField amountTextField;
    private CWTextArea descTextField;
    
    private CWButton saveButton;
    private CWButton saveAndCloseButton;
    private CWButton cancelButton;

    //**************************************************************************
    
    public EditActivityView(EditActivityPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }
    
    private void initComponents(){
        nameLabel = CWComponentFactory.createLabel("Aktivität-Name");
        descLabel = CWComponentFactory.createLabel("Beschreibung");
        amountLabel = CWComponentFactory.createLabel("Preis");
        
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
                "Hier wird die Aktivität gespeichert und anschließend in die Aktivitätsuebersicht gewechselt!",
                "cw/coursemanagementmodul/images/save_cancel.png"));
        cancelButton.setToolTipText(CWComponentFactory.createToolTip(
                "Abbrechen",
                "Hier kehren Sie zur Aktivitätsuebersicht zurueck!",
                "cw/coursemanagementmodul/images/cancel.png"));
        
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeToLastView();
            }
        });

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(amountTextField)
                .addComponent(cancelButton)
                .addComponent(descLabel)
                .addComponent(descTextField)
                .addComponent(nameLabel)
                .addComponent(nameTextField)
                .addComponent(amountLabel)
                .addComponent(saveAndCloseButton)
                .addComponent(saveButton);

        
    }

    private void initEventHandling() {

    }
    
    //**************************************************************************
    //Diese Methode gibt die Maske des EditPanels in Form einse JPanels zurueck
    //**************************************************************************
    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());

        CWButtonPanel buttonPanel = this.getButtonPanel();
        
        buttonPanel.add(saveButton);
        buttonPanel.add(saveAndCloseButton);
        buttonPanel.add(cancelButton);
        
        FormLayout layout = new FormLayout("pref, 4dlu, 200dlu, 4dlu, min",
                "pref, 2dlu, pref, 2dlu, pref, 2dlu, pref"); // rows

        layout.setRowGroups(new int[][]{{1, 3, 5}});
        
        CellConstraints cc = new CellConstraints();
        this.getContentPanel().setLayout(layout);
        
        this.getContentPanel().add(nameLabel, cc.xy (1, 1));
        this.getContentPanel().add(nameTextField, cc.xy(3, 1));
        this.getContentPanel().add(descLabel, cc.xy(1, 3));
        this.getContentPanel().add(descTextField, cc.xy(3, 3));
        this.getContentPanel().add(amountLabel, cc.xy(1, 5));
        this.getContentPanel().add(amountTextField, cc.xy(3, 5));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();
        model.dispose();
    }
    //**************************************************************************
}
