package cw.studentmanagementmodul.student.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.studentmanagementmodul.student.persistence.StudentClass;

/**
 *
 * @author ManuelG
 */
public class EditStudentClassView
	extends CWView<EditStudentClassPresentationModel>
{

    private CWTextField tfName;
    private CWComboBox cbOrganisationUnit;
    private CWComboBox cbNextStudentClass;

    private CWButton bSave;
//    private CWButton bReset;
    private CWButton bCancel;
    private CWButton bSaveCancel;

    public EditStudentClassView(EditStudentClassPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        bSave       = CWComponentFactory.createButton(getModel().getSaveButtonAction());
//        bReset      = CWComponentFactory.createButton(getModel().getResetButtonAction());
        bCancel     = CWComponentFactory.createButton(getModel().getCancelButtonAction());
        bSaveCancel = CWComponentFactory.createButton(getModel().getSaveCancelButtonAction());
        
        tfName              = CWComponentFactory.createTextField(getModel().getBufferedModel(StudentClass.PROPERTYNAME_NAME), false);
        cbOrganisationUnit  = CWComponentFactory.createComboBox(getModel().getSelectionOrganisationUnit());
        cbNextStudentClass  = CWComponentFactory.createComboBox(getModel().getSelectionStudentClass());

        getComponentContainer()
                .addComponent(bCancel)
//                .addComponent(bReset)
                .addComponent(bSave)
                .addComponent(bSaveCancel)
                .addComponent(tfName)
                .addComponent(cbNextStudentClass)
                .addComponent(cbOrganisationUnit);
    }

    public void buildView() {
    	super.buildView();
        
        this.setHeaderInfo(getModel().getHeaderInfo());

        CWButtonPanel buttonPanel = this.getButtonPanel();
        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
//        buttonPanel.add(bReset);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout("right:pref, 4dlu, pref:grow", "pref, 4dlu, pref, 4dlu, pref");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        
        builder.addLabel("Name:", cc.xy(1, 1));
        builder.add(tfName, cc.xy(3, 1));
        builder.addLabel("Bereich:", cc.xy(1, 3));
        builder.add(cbOrganisationUnit, cc.xy(3, 3));
        builder.addLabel("Naechste Klasse:", cc.xy(1, 5));
        builder.add(cbNextStudentClass, cc.xy(3, 5));
        
        addToContentPanel(builder.getPanel());
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
