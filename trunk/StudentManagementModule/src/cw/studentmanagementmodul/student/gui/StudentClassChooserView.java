package cw.studentmanagementmodul.student.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTree;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.studentmanagementmodul.student.gui.renderer.StudentClassTreeCellRenderer;

/**
 *
 * @author ManuelG
 */
public class StudentClassChooserView
	extends CWView<StudentClassChooserPresentationModel>
{

    private CWTree trStudentClass;
    private CWButton bOk;
//    private CWButton bNoClass;
    private CWButton bCancel;
    private CWButton bTreeExpand;
    private CWButton bTreeCollapse;

    public StudentClassChooserView(StudentClassChooserPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        bOk             = CWComponentFactory.createButton(getModel().getOkAction());
//        bNoClass        = CWComponentFactory.createButton(getModel().getNoClassAction());
        bCancel         = CWComponentFactory.createButton(getModel().getCancelAction());
        trStudentClass  = CWComponentFactory.createTree(getModel().getStudentClassTreeModel());
        trStudentClass.setSelectionModel(getModel().getStudentClassTreeSelectionModel());
        trStudentClass.setCellRenderer(getModel().getStudentClassTreeCellRenderer());
        trStudentClass.setCellRenderer(new StudentClassTreeCellRenderer());
        trStudentClass.setRootVisible(false);
        
        bTreeExpand = CWComponentFactory.createButton(new AbstractAction("", CWUtils.loadIcon("cw/boardingschoolmanagement/images/tree_expand.png")) {
            public void actionPerformed(ActionEvent e) {
                trStudentClass.expandAll();
            }
        });
        bTreeCollapse = CWComponentFactory.createButton(new AbstractAction("", CWUtils.loadIcon("cw/boardingschoolmanagement/images/tree_collapse.png")) {
            public void actionPerformed(ActionEvent e) {
                trStudentClass.collapseAll();
            }
        });

        getComponentContainer()
                .addComponent(bOk)
//                .addComponent(bNoClass)
                .addComponent(bCancel)
                .addComponent(bTreeCollapse)
                .addComponent(bTreeExpand);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public void buildView() {
    	super.buildView();

        this.setHeaderInfo(getModel().getHeaderInfo());

        this.getButtonPanel().add(bOk);
        this.getButtonPanel().add(bCancel);
//        this.getButtonPanel().add(bNoClass);

        FormLayout layout = new FormLayout(
                "pref:grow, 1dlu, pref",
                "pref, 1dlu, top:pref:grow"
        );

        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.add(trStudentClass, cc.xywh(1, 1, 1, 3));
        builder.add(bTreeExpand, cc.xy(3, 1));
        builder.add(bTreeCollapse, cc.xy(3, 3));
        
        addToContentPanel(builder.getPanel());
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
