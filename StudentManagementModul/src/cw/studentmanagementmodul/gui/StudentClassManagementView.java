package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWPopupMenu;
import cw.boardingschoolmanagement.gui.component.CWTree;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.studentmanagementmodul.gui.renderer.StudentClassTreeCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.JSeparator;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import cw.studentmanagementmodul.pojo.OrganisationUnit;
import cw.studentmanagementmodul.pojo.StudentClass;
import javax.swing.JMenuItem;

/**
 *
 * @author ManuelG
 */
public class StudentClassManagementView extends CWView
{

    private StudentClassManagementPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWTree trStudentClass;
    private CWButton bNewOrganisationUnt;
    private CWButton bEditOrganisationUnt;
    private CWButton bRemoveOrganisationUnt;
    private CWButton bNewStudentClass;
    private CWButton bEditStudentClass;
    private CWButton bRemoveStudentClass;
    private CWButton bViewStudents;
    private CWButton bMoveUpStudentClasses;
    private CWPopupMenu popupStudentClassTreeRoot;
    private CWPopupMenu popupStudentClassTreeOrganisationUnit;
    private CWPopupMenu popupStudentClassTreeStudentClass;
    private CWButton bTreeExpand;
    private CWButton bTreeCollapse;

    private MouseAdapter reMouseListener;

    public StudentClassManagementView(StudentClassManagementPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    public void initComponents() {
        componentContainer = CWComponentFactory.createComponentContainer();

        bNewOrganisationUnt     = CWComponentFactory.createButton(model.getNewOrganisationUnitAction());
        bEditOrganisationUnt    = CWComponentFactory.createButton(model.getEditOrganisationUnitAction());
        bRemoveOrganisationUnt  = CWComponentFactory.createButton(model.getRemoveOrganisationUnitAction());
        bNewStudentClass        = CWComponentFactory.createButton(model.getNewStudentClassAction());
        bEditStudentClass       = CWComponentFactory.createButton(model.getEditStudentClassAction());
        bRemoveStudentClass     = CWComponentFactory.createButton(model.getRemoveStudentClassAction());
        bViewStudents           = CWComponentFactory.createButton(model.getViewStudentsAction());
        bMoveUpStudentClasses   = CWComponentFactory.createButton(model.getMoveUpStudentClassAction());
        trStudentClass          = CWComponentFactory.createTree(model.getStudentClassTreeModel());
        trStudentClass.setSelectionModel(model.getStudentClassTreeSelectionModel());
        trStudentClass.setCellRenderer(new StudentClassTreeCellRenderer());

        popupStudentClassTreeRoot = CWComponentFactory.createPopupMenu();
        popupStudentClassTreeRoot.add((JMenuItem)componentContainer.addComponentAndReturn(CWComponentFactory.createMenuItem(model.getNewOrganisationUnitAction())));

        popupStudentClassTreeOrganisationUnit = CWComponentFactory.createPopupMenu();
        popupStudentClassTreeOrganisationUnit.add((JMenuItem)componentContainer.addComponentAndReturn(CWComponentFactory.createMenuItem(model.getNewOrganisationUnitAction())));
        popupStudentClassTreeOrganisationUnit.add((JMenuItem)componentContainer.addComponentAndReturn(CWComponentFactory.createMenuItem(model.getEditOrganisationUnitAction())));
        popupStudentClassTreeOrganisationUnit.add((JMenuItem)componentContainer.addComponentAndReturn(CWComponentFactory.createMenuItem(model.getRemoveOrganisationUnitAction())));
        popupStudentClassTreeOrganisationUnit.add(new JSeparator());
        popupStudentClassTreeOrganisationUnit.add((JMenuItem)componentContainer.addComponentAndReturn(CWComponentFactory.createMenuItem(model.getNewStudentClassAction())));

        popupStudentClassTreeStudentClass = CWComponentFactory.createPopupMenu();
        popupStudentClassTreeStudentClass.add((JMenuItem)componentContainer.addComponentAndReturn(CWComponentFactory.createMenuItem(model.getEditStudentClassAction())));
        popupStudentClassTreeStudentClass.add((JMenuItem)componentContainer.addComponentAndReturn(CWComponentFactory.createMenuItem(model.getRemoveStudentClassAction())));
        popupStudentClassTreeStudentClass.add(new JSeparator());
        popupStudentClassTreeStudentClass.add((JMenuItem)componentContainer.addComponentAndReturn(CWComponentFactory.createMenuItem(model.getViewStudentsAction())));
        popupStudentClassTreeStudentClass.add((JMenuItem)componentContainer.addComponentAndReturn(CWComponentFactory.createMenuItem(model.getMoveUpStudentClassAction())));

        trStudentClass.addMouseListener(reMouseListener = new MouseAdapter() {
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

        componentContainer
                .addComponent(bEditOrganisationUnt)
                .addComponent(bEditStudentClass)
                .addComponent(bMoveUpStudentClasses)
                .addComponent(bNewOrganisationUnt)
                .addComponent(bNewStudentClass)
                .addComponent(bRemoveOrganisationUnt)
                .addComponent(bRemoveStudentClass)
                .addComponent(bTreeCollapse)
                .addComponent(bTreeExpand)
                .addComponent(bViewStudents);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {
        
        this.setHeaderInfo(model.getHeaderInfo());

        this.getButtonPanel().add(bNewOrganisationUnt);
        this.getButtonPanel().add(bEditOrganisationUnt);
        this.getButtonPanel().add(bRemoveOrganisationUnt);
        this.getButtonPanel().add(bNewStudentClass);
        this.getButtonPanel().add(bEditStudentClass);
        this.getButtonPanel().add(bRemoveStudentClass);
        this.getButtonPanel().add(bViewStudents);
        this.getButtonPanel().add(bMoveUpStudentClasses);

        FormLayout layout = new FormLayout(
                "pref:grow, 1dlu, pref",
                "pref, 1dlu, top:pref:grow"
        );

        PanelBuilder builder = new PanelBuilder(layout, this.getContentPanel());
        CellConstraints cc = new CellConstraints();

        builder.add(CWComponentFactory.createScrollPane(trStudentClass), cc.xywh(1, 1, 1, 3));
        builder.add(bTreeExpand, cc.xy(3, 1));
        builder.add(bTreeCollapse, cc.xy(3, 3));
    }


    @Override
    public void dispose() {
        trStudentClass.removeMouseListener(reMouseListener);

        componentContainer.dispose();

        model.dispose();
    }
}
