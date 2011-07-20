package cw.studentmanagementmodul.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWComboBox;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWList;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author CreativeWorkers.at
 */
public class StudentCustomerSelectorFilterExtentionView extends CWPanel
{

    private StudentCustomerSelectorFilterExtentionPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWRadioButton rbNoMatter;
    private CWRadioButton rbInactive;
    private CWRadioButton rbActive;
    private CWComboBox cbStudentClass;

    public StudentCustomerSelectorFilterExtentionView(StudentCustomerSelectorFilterExtentionPresentationModel m) {
        model = m;
        
        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        rbNoMatter  = CWComponentFactory.createRadioButton(model.getNoMatterAction(), true);
        rbInactive  = CWComponentFactory.createRadioButton(model.getInactiveAction());
        rbActive    = CWComponentFactory.createRadioButton(model.getActiveAction());
        cbStudentClass = CWComponentFactory.createComboBox(model.getStudentClassSelection());

        CWComponentFactory.createButtonGroup(rbNoMatter, rbActive, rbInactive);

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(rbNoMatter)
                .addComponent(rbInactive)
                .addComponent(rbActive)
                .addComponent(cbStudentClass);
    }

    private void initEventHandling() {
        rbActive.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                cbStudentClass.setEnabled(rbActive.isSelected());
            }
        });
        cbStudentClass.setEnabled(false);
    }

    private void buildView() {
        FormLayout layout = new FormLayout(
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref",
                "pref");
        CellConstraints cc = new CellConstraints();

        PanelBuilder builder = new PanelBuilder(layout, this);

        builder.addLabel("Schüler:",    cc.xy(1, 1));
        builder.add(rbNoMatter,         cc.xy(3, 1));
        builder.add(rbInactive,         cc.xy(5, 1));
        builder.add(rbActive,           cc.xy(7, 1));
        builder.add(cbStudentClass,      cc.xy(9, 1));
    }

    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}
