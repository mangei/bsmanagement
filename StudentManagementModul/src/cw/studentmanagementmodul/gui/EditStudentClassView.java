package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.studentmanagementmodul.pojo.StudentClass;

/**
 *
 * @author ManuelG
 */
public class EditStudentClassView extends CWView
{

    private EditStudentClassPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWTextField tfName;
    private CWComboBox cbOrganisationUnit;
    private CWComboBox cbNextStudentClass;

    private CWButton bSave;
//    private CWButton bReset;
    private CWButton bCancel;
    private CWButton bSaveCancel;

    public EditStudentClassView(EditStudentClassPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        bSave       = CWComponentFactory.createButton(model.getSaveButtonAction());
//        bReset      = CWComponentFactory.createButton(model.getResetButtonAction());
        bCancel     = CWComponentFactory.createButton(model.getCancelButtonAction());
        bSaveCancel = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        
        tfName              = CWComponentFactory.createTextField(model.getBufferedModel(StudentClass.PROPERTYNAME_NAME), false);
        cbOrganisationUnit  = CWComponentFactory.createComboBox(model.getSelectionOrganisationUnit());
        cbNextStudentClass  = CWComponentFactory.createComboBox(model.getSelectionStudentClass());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(bCancel)
//                .addComponent(bReset)
                .addComponent(bSave)
                .addComponent(bSaveCancel)
                .addComponent(tfName)
                .addComponent(cbNextStudentClass)
                .addComponent(cbOrganisationUnit);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {
        
        this.setHeaderInfo(model.getHeaderInfo());

        CWButtonPanel buttonPanel = this.getButtonPanel();
        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
//        buttonPanel.add(bReset);
        buttonPanel.add(bCancel);

        FormLayout layout = new FormLayout("right:pref, 4dlu, pref:grow", "pref, 4dlu, pref, 4dlu, pref");
        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        CellConstraints cc = new CellConstraints();
        
        builder.addLabel("Name:", cc.xy(1, 1));
        builder.add(tfName, cc.xy(3, 1));
        builder.addLabel("Bereich:", cc.xy(1, 3));
        builder.add(cbOrganisationUnit, cc.xy(3, 3));
        builder.addLabel("Nächste Klasse:", cc.xy(1, 5));
        builder.add(cbNextStudentClass, cc.xy(3, 5));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}
