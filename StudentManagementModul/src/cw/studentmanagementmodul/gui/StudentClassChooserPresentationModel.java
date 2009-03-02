package cw.studentmanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import cw.studentmanagementmodul.pojo.OrganisationUnit;
import cw.studentmanagementmodul.pojo.StudentClass;
import cw.studentmanagementmodul.pojo.manager.OrganisationUnitManager;
import cw.studentmanagementmodul.pojo.manager.StudentClassManager;
import javax.swing.Icon;

/**
 *
 * @author ManuelG
 */
public class StudentClassChooserPresentationModel
    implements Disposable
{

    private String headerText;
    private StudentClass selectedStudentClass;
    private Action okAction;
    private Action cancelAction;
    private DefaultTreeModel studentClassTreeModel;
    private DefaultTreeSelectionModel studentClassTreeSelectionModel;
    private DefaultTreeCellRenderer studentClassTreeCellRenderer;
    private DefaultMutableTreeNode studentClassRootTreeNode;
    private HashMap<Object, DefaultMutableTreeNode> studentClassTreeNodeMap;
    private ButtonListenerSupport buttonListenerSupport;

    private TreeSelectionListener treeSelectionListener;

    public StudentClassChooserPresentationModel(String headerText) {
        this(null, headerText);
    }

    public StudentClassChooserPresentationModel(StudentClass selectedStudentClass, String headerText) {
        this.headerText = headerText;
        this.selectedStudentClass = selectedStudentClass;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        studentClassTreeNodeMap = new HashMap<Object, DefaultMutableTreeNode>();
        okAction = new OkAction("Auswählen", CWUtils.loadIcon("cw/studentmanagementmodul/images/accept.png"));
        cancelAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/studentmanagementmodul/images/cancel.png"));

        studentClassRootTreeNode = new DefaultMutableTreeNode("Welt", true);
        studentClassTreeNodeMap.put(null, studentClassRootTreeNode);
        studentClassTreeModel = new DefaultTreeModel(studentClassRootTreeNode);
        studentClassTreeSelectionModel = new DefaultTreeSelectionModel();
        buildStudentClassTree();

        buttonListenerSupport = new ButtonListenerSupport();
    }

    public void initEventHandling() {
        studentClassTreeSelectionModel.addTreeSelectionListener(treeSelectionListener = new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) {
                if (!studentClassTreeSelectionModel.isSelectionEmpty()) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) studentClassTreeSelectionModel.getSelectionPath().getLastPathComponent();
                    Object object = node.getUserObject();
                    if (object instanceof StudentClass) {
                        selectedStudentClass = (StudentClass) object;
                        okAction.setEnabled(true);
                    } else {
//                        studentClassTreeSelectionModel.setSelectionPath(e.getOldLeadSelectionPath());
                        selectedStudentClass = null;
                        okAction.setEnabled(false);
                    }
                }
            }
        });

        TreePath selectedPath = new TreePath(studentClassTreeNodeMap.get(selectedStudentClass).getPath());
        studentClassTreeSelectionModel.setSelectionPath(selectedPath);
    }

    public void dispose() {
        studentClassTreeSelectionModel.removeTreeSelectionListener(treeSelectionListener);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Useful methods
    ////////////////////////////////////////////////////////////////////////////
    private void buildStudentClassTree() {

        // Clear the old tree
        for (int i = 0, l = studentClassRootTreeNode.getChildCount(); i < l; i++) {
            studentClassRootTreeNode.remove(0);
        }

        // Load root organisationUnits and studentClasses
        OrganisationUnit organisationUnit;
        MutableTreeNode node;
        List<OrganisationUnit> roots = OrganisationUnitManager.getInstance().getRoots();

        // add the first organisationUnits
        for (int i = 0, l = roots.size(); i < l; i++) {
            organisationUnit = roots.get(i);
            node = createTreeNode(organisationUnit);
            studentClassRootTreeNode.insert(node, i);
            buildStudentClassTree(node, organisationUnit);
        }
    }

    private void buildStudentClassTree(MutableTreeNode node, OrganisationUnit organisationUnit) {
        List<OrganisationUnit> children = organisationUnit.getChildren();
        OrganisationUnit child;
        MutableTreeNode node2;

        // Append the under-organisationUnits
        for (int i = 0, l = children.size(); i < l; i++) {
            child = children.get(i);
            node2 = createTreeNode(child);
            node.insert(node2, i);
            buildStudentClassTree(node2, child);
        }

        // add the classes
        List<StudentClass> studentClasses = StudentClassManager.getInstance().getAll(organisationUnit);
        StudentClass studentClass;
        for (int i = 0, l = studentClasses.size(); i < l; i++) {
            studentClass = studentClasses.get(i);
            node2 = createTreeNode(studentClass);
            node.insert(node2, i);
        }
    }

    private MutableTreeNode createTreeNode(Object obj) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(obj, true);
        studentClassTreeNodeMap.put(obj, node);
        return node;
    }

    private MutableTreeNode getParentNode(OrganisationUnit organisationUnit) {
        return studentClassTreeNodeMap.get(organisationUnit.getParent());
    }

    private MutableTreeNode getParentNode(StudentClass studentClass) {
        return studentClassTreeNodeMap.get(studentClass.getOrganisationUnit());
    }

    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////
    private class OkAction extends AbstractAction {

        public OkAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.OK_BUTTON));
        }
    }

    private class CancelAction extends AbstractAction {

        public CancelAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            buttonListenerSupport.fireButtonPressed(new ButtonEvent(ButtonEvent.CANCEL_BUTTON));
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    // ButtonListenerSupport
    ////////////////////////////////////////////////////////////////////////////
    public void removeButtonListener(ButtonListener listener) {
        buttonListenerSupport.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        buttonListenerSupport.addButtonListener(listener);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////
    public Action getCancelAction() {
        return cancelAction;
    }

    public String getHeaderText() {
        return headerText;
    }

    public Action getOkAction() {
        return okAction;
    }

    public DefaultTreeCellRenderer getStudentClassTreeCellRenderer() {
        return studentClassTreeCellRenderer;
    }

    public DefaultTreeModel getStudentClassTreeModel() {
        return studentClassTreeModel;
    }

    public DefaultTreeSelectionModel getStudentClassTreeSelectionModel() {
        return studentClassTreeSelectionModel;
    }

    public StudentClass getSelectedStudentClass() {
        return selectedStudentClass;
    }
}
