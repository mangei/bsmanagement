package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.studentmanagementmodul.gui.renderer.StudentClassTreeCellRenderer;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXTree;

/**
 *
 * @author ManuelG
 */
public class StudentClassChooserView
    implements Disposable
{

    private StudentClassChooserPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel panel;
    private JXTree trStudentClass;
    private JButton bOk;
    private JButton bCancel;
    private JButton bTreeExpand;
    private JButton bTreeCollapse;

    public StudentClassChooserView(StudentClassChooserPresentationModel model) {
        this.model = model;
    }

    public void initModels() {
        bOk             = CWComponentFactory.createButton(model.getOkAction());
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

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(bOk)
                .addComponent(bCancel)
                .addComponent(bTreeCollapse)
                .addComponent(bTreeExpand);
    }

    public void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initModels();

        panel = new JViewPanel(model.getHeaderText());

        panel.getButtonPanel().add(bOk);
        panel.getButtonPanel().add(bCancel);

        FormLayout layout = new FormLayout(
                "pref:grow, 1dlu, pref",
                "pref, 1dlu, top:pref:grow"
        );

        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());
        CellConstraints cc = new CellConstraints();

        builder.add(trStudentClass, cc.xywh(1, 1, 1, 3));
        builder.add(bTreeExpand, cc.xy(3, 1));
        builder.add(bTreeCollapse, cc.xy(3, 3));

        panel.addDisposableListener(this);

        initEventHandling();

        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);

        componentContainer.dispose();

        model.dispose();
    }
}
