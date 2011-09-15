package cw.studentmanagementmodul.student.gui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWPopupMenu;
import cw.boardingschoolmanagement.gui.component.CWTree;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.studentmanagementmodul.organisationunit.persistence.OrganisationUnit;
import cw.studentmanagementmodul.student.gui.renderer.StudentClassTreeCellRenderer;
import cw.studentmanagementmodul.student.persistence.StudentClass;

/**
 *
 * @author ManuelG
 */
public class StudentClassManagementView
	extends CWView<StudentClassManagementPresentationModel>
{

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
        super(model);
    }

    public void initComponents() {
    	super.initComponents();

        bNewOrganisationUnt     = CWComponentFactory.createButton(getModel().getNewOrganisationUnitAction());
        bEditOrganisationUnt    = CWComponentFactory.createButton(getModel().getEditOrganisationUnitAction());
        bRemoveOrganisationUnt  = CWComponentFactory.createButton(getModel().getRemoveOrganisationUnitAction());
        bNewStudentClass        = CWComponentFactory.createButton(getModel().getNewStudentClassAction());
        bEditStudentClass       = CWComponentFactory.createButton(getModel().getEditStudentClassAction());
        bRemoveStudentClass     = CWComponentFactory.createButton(getModel().getRemoveStudentClassAction());
        bViewStudents           = CWComponentFactory.createButton(getModel().getViewStudentsAction());
        bMoveUpStudentClasses   = CWComponentFactory.createButton(getModel().getMoveUpStudentClassAction());
        trStudentClass          = CWComponentFactory.createTree(getModel().getStudentClassTreeModel());
        trStudentClass.setSelectionModel(getModel().getStudentClassTreeSelectionModel());
        trStudentClass.setCellRenderer(new StudentClassTreeCellRenderer());

        popupStudentClassTreeRoot = CWComponentFactory.createPopupMenu();
        popupStudentClassTreeRoot.add((JMenuItem)getComponentContainer().addComponentAndReturn(CWComponentFactory.createMenuItem(getModel().getNewOrganisationUnitAction())));

        popupStudentClassTreeOrganisationUnit = CWComponentFactory.createPopupMenu();
        popupStudentClassTreeOrganisationUnit.add((JMenuItem)getComponentContainer().addComponentAndReturn(CWComponentFactory.createMenuItem(getModel().getNewOrganisationUnitAction())));
        popupStudentClassTreeOrganisationUnit.add((JMenuItem)getComponentContainer().addComponentAndReturn(CWComponentFactory.createMenuItem(getModel().getEditOrganisationUnitAction())));
        popupStudentClassTreeOrganisationUnit.add((JMenuItem)getComponentContainer().addComponentAndReturn(CWComponentFactory.createMenuItem(getModel().getRemoveOrganisationUnitAction())));
        popupStudentClassTreeOrganisationUnit.add(new JSeparator());
        popupStudentClassTreeOrganisationUnit.add((JMenuItem)getComponentContainer().addComponentAndReturn(CWComponentFactory.createMenuItem(getModel().getNewStudentClassAction())));

        popupStudentClassTreeStudentClass = CWComponentFactory.createPopupMenu();
        popupStudentClassTreeStudentClass.add((JMenuItem)getComponentContainer().addComponentAndReturn(CWComponentFactory.createMenuItem(getModel().getEditStudentClassAction())));
        popupStudentClassTreeStudentClass.add((JMenuItem)getComponentContainer().addComponentAndReturn(CWComponentFactory.createMenuItem(getModel().getRemoveStudentClassAction())));
        popupStudentClassTreeStudentClass.add(new JSeparator());
        popupStudentClassTreeStudentClass.add((JMenuItem)getComponentContainer().addComponentAndReturn(CWComponentFactory.createMenuItem(getModel().getViewStudentsAction())));
        popupStudentClassTreeStudentClass.add((JMenuItem)getComponentContainer().addComponentAndReturn(CWComponentFactory.createMenuItem(getModel().getMoveUpStudentClassAction())));

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

    public void buildView() {
    	super.buildView();
        
        this.setHeaderInfo(getModel().getHeaderInfo());

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

        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.add(CWComponentFactory.createScrollPane(trStudentClass), cc.xywh(1, 1, 1, 3));
        builder.add(bTreeExpand, cc.xy(3, 1));
        builder.add(bTreeCollapse, cc.xy(3, 3));
        
        addToContentPanel(builder.getPanel());
    }


    @Override
    public void dispose() {
        trStudentClass.removeMouseListener(reMouseListener);

        super.dispose();
    }
}
