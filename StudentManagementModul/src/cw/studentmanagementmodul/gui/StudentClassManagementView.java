package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.studentmanagementmodul.gui.renderer.StudentClassTreeCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.jdesktop.swingx.JXTree;
import cw.studentmanagementmodul.pojo.OrganisationUnit;
import cw.studentmanagementmodul.pojo.StudentClass;

/**
 *
 * @author ManuelG
 */
public class StudentClassManagementView {

    private StudentClassManagementPresentationModel model;
    private JXTree trStudentClass;
    private JButton bNewOrganisationUnt;
    private JButton bEditOrganisationUnt;
    private JButton bRemoveOrganisationUnt;
    private JButton bNewStudentClass;
    private JButton bEditStudentClass;
    private JButton bRemoveStudentClass;
    private JButton bMoveUpStudentClasses;
    private JPopupMenu popupStudentClassTreeRoot;
    private JPopupMenu popupStudentClassTreeOrganisationUnit;
    private JPopupMenu popupStudentClassTreeStudentClass;
    private JButton bTreeExpand;
    private JButton bTreeCollapse;

    public StudentClassManagementView(StudentClassManagementPresentationModel model) {
        this.model = model;
    }

    public void initModels() {
        bNewOrganisationUnt     = CWComponentFactory.createButton(model.getNewOrganisationUnitAction());
        bEditOrganisationUnt    = CWComponentFactory.createButton(model.getEditOrganisationUnitAction());
        bRemoveOrganisationUnt  = CWComponentFactory.createButton(model.getRemoveOrganisationUnitAction());
        bNewStudentClass        = CWComponentFactory.createButton(model.getNewStudentClassAction());
        bEditStudentClass       = CWComponentFactory.createButton(model.getEditStudentClassAction());
        bRemoveStudentClass     = CWComponentFactory.createButton(model.getRemoveStudentClassAction());
        bMoveUpStudentClasses   = CWComponentFactory.createButton(model.getMoveUpStudentClassAction());
        trStudentClass          = CWComponentFactory.createTree(model.getStudentClassTreeModel());
        trStudentClass.setSelectionModel(model.getStudentClassTreeSelectionModel());
        trStudentClass.setCellRenderer(new StudentClassTreeCellRenderer());
        trStudentClass.setShowsRootHandles(true);

        popupStudentClassTreeRoot = new JPopupMenu();
        popupStudentClassTreeRoot.add(new JMenuItem(model.getNewOrganisationUnitAction()));

        popupStudentClassTreeOrganisationUnit = new JPopupMenu();
        popupStudentClassTreeOrganisationUnit.add(new JMenuItem(model.getNewOrganisationUnitAction()));
        popupStudentClassTreeOrganisationUnit.add(new JMenuItem(model.getEditOrganisationUnitAction()));
        popupStudentClassTreeOrganisationUnit.add(new JMenuItem(model.getRemoveOrganisationUnitAction()));
        popupStudentClassTreeOrganisationUnit.add(new JSeparator());
        popupStudentClassTreeOrganisationUnit.add(new JMenuItem(model.getNewStudentClassAction()));

        popupStudentClassTreeStudentClass = new JPopupMenu();
        popupStudentClassTreeStudentClass.add(new JMenuItem(model.getEditStudentClassAction()));
        popupStudentClassTreeStudentClass.add(new JMenuItem(model.getRemoveStudentClassAction()));
        popupStudentClassTreeStudentClass.add(new JSeparator());
        popupStudentClassTreeStudentClass.add(new JMenuItem(model.getMoveUpStudentClassAction()));

        trStudentClass.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
//                int rowheight = trStudentClass.getRowHeight();
//                rowheight = ((StudentClassTreeCellRenderer)((DelegatingRenderer)trStudentClass.getCellRenderer()).getDelegateRenderer()).getHeight();
//                int rows = trStudentClass.getRowCount();
//                System.out.println("height: " + trStudentClass.getHeight());
//                int rowsheight = rowheight * rows;
//                System.out.println("rowheight: "+ rowheight+" rows: "+ rows+" rowsheight: "+ rowsheight+" y: " + e.getY());
//                System.out.println("pref: " + trStudentClass.getPreferredSize());
//                if(e.getY() < rowsheight && e.getButton() == MouseEvent.BUTTON3) {
                if(e.getY() < trStudentClass.getPreferredSize().getHeight() && e.getButton() == MouseEvent.BUTTON3) {
                    TreePath path = trStudentClass.getClosestPathForLocation(e.getX(), e.getY());
                    trStudentClass.getSelectionModel().setSelectionPath(path);
                    Object object = ((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject();
                    System.out.println("1");
                    if(object instanceof OrganisationUnit) {
                        System.out.println("2");
                        popupStudentClassTreeOrganisationUnit.show(trStudentClass, e.getX(), e.getY());
                    } else if(object instanceof StudentClass) {
                        System.out.println("3");
                        popupStudentClassTreeStudentClass.show(trStudentClass, e.getX(), e.getY());
                    } else {
                        System.out.println("4");
                        popupStudentClassTreeRoot.show(trStudentClass, e.getX(), e.getY());
                    }
                }
            }
        });
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
    }

    public void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initModels();
        
        JViewPanel panel = new JViewPanel(model.getHeaderText());

        panel.getButtonPanel().add(bNewOrganisationUnt);
        panel.getButtonPanel().add(bEditOrganisationUnt);
        panel.getButtonPanel().add(bRemoveOrganisationUnt);
        panel.getButtonPanel().add(bNewStudentClass);
        panel.getButtonPanel().add(bEditStudentClass);
        panel.getButtonPanel().add(bRemoveStudentClass);
        panel.getButtonPanel().add(bMoveUpStudentClasses);

        FormLayout layout = new FormLayout(
                "pref:grow, 1dlu, pref",
                "pref, 1dlu, top:pref:grow"
        );

        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());
        CellConstraints cc = new CellConstraints();

        builder.add(CWComponentFactory.createScrollPane(trStudentClass), cc.xywh(1, 1, 1, 3));
        builder.add(bTreeExpand, cc.xy(3, 1));
        builder.add(bTreeCollapse, cc.xy(3, 3));

        initEventHandling();
        
        return panel;
    }

}
