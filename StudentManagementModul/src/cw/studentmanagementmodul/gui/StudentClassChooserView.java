package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWTree;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.studentmanagementmodul.gui.renderer.StudentClassTreeCellRenderer;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author ManuelG
 */
public class StudentClassChooserView extends CWView
{

    private StudentClassChooserPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWTree trStudentClass;
    private CWButton bOk;
    private CWButton bNoClass;
    private CWButton bCancel;
    private CWButton bTreeExpand;
    private CWButton bTreeCollapse;

    public StudentClassChooserView(StudentClassChooserPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        bOk             = CWComponentFactory.createButton(model.getOkAction());
        bNoClass        = CWComponentFactory.createButton(model.getNoClassAction());
        bCancel         = CWComponentFactory.createButton(model.getCancelAction());
        trStudentClass  = CWComponentFactory.createTree(model.getStudentClassTreeModel());
        trStudentClass.setSelectionModel(model.getStudentClassTreeSelectionModel());
        trStudentClass.setCellRenderer(model.getStudentClassTreeCellRenderer());
        trStudentClass.setCellRenderer(new StudentClassTreeCellRenderer());
        
        bTreeExpand = CWComponentFactory.createButton(new AbstractAction("", CWUtils.loadIcon("cw/studentmanagementmodul/images/tree_expand.png")) {
            public void actionPerformed(ActionEvent e) {
                trStudentClass.expandAll();
            }
        });
        bTreeCollapse = CWComponentFactory.createButton(new AbstractAction("", CWUtils.loadIcon("cw/studentmanagementmodul/images/tree_collapse.png")) {
            public void actionPerformed(ActionEvent e) {
                trStudentClass.collapseAll();
            }
        });

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(bOk)
                .addComponent(bNoClass)
                .addComponent(bCancel)
                .addComponent(bTreeCollapse)
                .addComponent(bTreeExpand);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {

        this.setHeaderInfo(model.getHeaderInfo());

        this.getButtonPanel().add(bOk);
        this.getButtonPanel().add(bCancel);
        this.getButtonPanel().add(bNoClass);

        FormLayout layout = new FormLayout(
                "pref:grow, 1dlu, pref",
                "pref, 1dlu, top:pref:grow"
        );

        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        CellConstraints cc = new CellConstraints();

        builder.add(trStudentClass, cc.xywh(1, 1, 1, 3));
        builder.add(bTreeExpand, cc.xy(3, 1));
        builder.add(bTreeCollapse, cc.xy(3, 3));
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}
