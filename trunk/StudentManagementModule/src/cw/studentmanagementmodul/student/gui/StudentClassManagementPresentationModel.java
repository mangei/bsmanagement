package cw.studentmanagementmodul.student.gui;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.studentmanagementmodul.organisationunit.gui.DeleteOrganisationUnitPresentationModel;
import cw.studentmanagementmodul.organisationunit.gui.DeleteOrganisationUnitView;
import cw.studentmanagementmodul.organisationunit.gui.EditOrganisationUnitPresentationModel;
import cw.studentmanagementmodul.organisationunit.gui.EditOrganisationUnitView;
import cw.studentmanagementmodul.organisationunit.persistence.OrganisationUnit;
import cw.studentmanagementmodul.organisationunit.persistence.PMOrganisationUnit;
import cw.studentmanagementmodul.student.persistence.Student;
import cw.studentmanagementmodul.student.persistence.StudentClass;
import cw.studentmanagementmodul.student.persistence.PMStudentClass;
import cw.studentmanagementmodul.student.persistence.PMStudent;

/**
 *
 * @author Manuel Geier
 */
public class StudentClassManagementPresentationModel
	extends CWPresentationModel
{

    private CWHeaderInfo headerInfo;
    private Action newOrganisationUnitAction;
    private Action editOrganisationUnitAction;
    private Action removeOrganisationUnitAction;
    private Action newStudentClassAction;
    private Action editStudentClassAction;
    private Action removeStudentClassAction;
    private Action viewStudentsAction;
    private Action moveUpStudentClassAction;
    private DefaultTreeModel studentClassTreeModel;
    private DefaultTreeSelectionModel studentClassTreeSelectionModel;
    private DefaultMutableTreeNode studentClassRootTreeNode;
    private HashMap<Object, MutableTreeNode> studentClassTreeNodeMap;

    private ActionTreeChangeListener actionTreeChangeListener;

    public StudentClassManagementPresentationModel() {
        initModels();
        initEventHandling();
    }

    private void initModels() {

        headerInfo = new CWHeaderInfo(
                "Klassenverwaltung",
                "Verwalten Sie ihre Klassen in einer beliebigen Struktur mit Hilfe von Bereichen. Lassen Sie sich die Schueler ein Klasse anzeigen und Schueler automatisch in die naechste Klasse aufsteigen lassen.",
                CWUtils.loadIcon("cw/studentmanagementmodul/images/studentClass.png"),
                CWUtils.loadIcon("cw/studentmanagementmodul/images/studentClass.png")
        );

        studentClassTreeNodeMap = new HashMap<Object, MutableTreeNode>();
        newOrganisationUnitAction = new NewOrganisationUnitAction("Bereich erstellen", CWUtils.loadIcon("cw/studentmanagementmodul/images/organisationUnit_add.png"));
        editOrganisationUnitAction = new EditOrganisationUnitAction("Bereich bearbeiten", CWUtils.loadIcon("cw/studentmanagementmodul/images/organisationUnit_edit.png"));
        removeOrganisationUnitAction = new RemoveOrganisationUnitAction("Bereich loeschen", CWUtils.loadIcon("cw/studentmanagementmodul/images/organisationUnit_remove.png"));
        newStudentClassAction = new NewStudentClassAction("Klasse erstellen", CWUtils.loadIcon("cw/studentmanagementmodul/images/studentClass_add.png"));
        editStudentClassAction = new EditStudentClassAction("Klasse bearbeiten", CWUtils.loadIcon("cw/studentmanagementmodul/images/studentClass_edit.png"));
        removeStudentClassAction = new RemoveStudentClassAction("Klasse loeschen", CWUtils.loadIcon("cw/studentmanagementmodul/images/studentClass_remove.png"));
        viewStudentsAction = new ViewStudentsActionAction("Schueler anzeigen", CWUtils.loadIcon("cw/studentmanagementmodul/images/student.png"));
        moveUpStudentClassAction = new MoveUpStudentClassAction("Schueler aufsteigen", CWUtils.loadIcon("cw/studentmanagementmodul/images/studentClass_up.png"));

        studentClassRootTreeNode = new DefaultMutableTreeNode("world", true);
        studentClassTreeNodeMap.put(null, studentClassRootTreeNode);
        studentClassTreeModel = new DefaultTreeModel(studentClassRootTreeNode);
        studentClassTreeSelectionModel = new DefaultTreeSelectionModel();
        buildStudentClassTree();
    }

    public void initEventHandling() {
        actionTreeChangeListener = new ActionTreeChangeListener();
        actionTreeChangeListener.updateActions();
        studentClassTreeSelectionModel.addTreeSelectionListener(actionTreeChangeListener);
        studentClassTreeModel.addTreeModelListener(actionTreeChangeListener);
    }

    public void dispose() {
        studentClassTreeSelectionModel.removeTreeSelectionListener(actionTreeChangeListener);
        studentClassTreeModel.removeTreeModelListener(actionTreeChangeListener);
    }

    //////////////////////////////////////////////////////////////////////////
    // Useful methods
    //////////////////////////////////////////////////////////////////////////
    private void buildStudentClassTree() {

        // Clear the old tree
        for (int i = 0, l = studentClassRootTreeNode.getChildCount(); i < l; i++) {
            studentClassRootTreeNode.remove(0);
        }

        // Load root organisationUnits and studentClasses
        OrganisationUnit organisationUnit;
        MutableTreeNode node;
        List<OrganisationUnit> roots = PMOrganisationUnit.getInstance().getRoots();

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
        List<StudentClass> studentClasses = PMStudentClass.getInstance().getAll(organisationUnit);
        StudentClass studentClass;
        for (int i = 0, l = studentClasses.size(); i < l; i++) {
            studentClass = studentClasses.get(i);
            node2 = createTreeNode(studentClass);
            node.insert(node2, i);
        }
    }

    private MutableTreeNode createTreeNode(Object obj) {
        MutableTreeNode node = new DefaultMutableTreeNode(obj, true);
        studentClassTreeNodeMap.put(obj, node);
        return node;
    }

    private MutableTreeNode getNode(OrganisationUnit organisationUnit) {
        return studentClassTreeNodeMap.get(organisationUnit);
    }

    private MutableTreeNode getNode(StudentClass studentClass) {
        return studentClassTreeNodeMap.get(studentClass);
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
    private class NewOrganisationUnitAction extends AbstractAction {

        public NewOrganisationUnitAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().lockMenu();
            GUIManager.setLoadingScreenText("Formular wird geladen...");
            GUIManager.setLoadingScreenVisible(true);

            final OrganisationUnit organisationUnit = new OrganisationUnit();

            // Automaticly select the parent of the new OrganisationUnit
            if (!studentClassTreeSelectionModel.isSelectionEmpty()) {
                Object parent = ((DefaultMutableTreeNode) studentClassTreeSelectionModel.getSelectionPath().getLastPathComponent()).getUserObject();
                if (parent instanceof OrganisationUnit) {
                    organisationUnit.setParent((OrganisationUnit) parent);
                }
            }

            final EditOrganisationUnitPresentationModel model = new EditOrganisationUnitPresentationModel(organisationUnit, new CWHeaderInfo("Bereich erstellen"));
            final EditOrganisationUnitView editView = new EditOrganisationUnitView(model);

            model.addButtonListener(new ButtonListener() {


                // If the parent changes
                PropertyChangeListener organisationUnitChanged;
                boolean customerAlreadyCreated = false;

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        PMOrganisationUnit.getInstance().save(organisationUnit);
                        if (customerAlreadyCreated) {
                            GUIManager.getStatusbar().setTextAndFadeOut("Bereich wurde aktualisiert.");
                        } else {
                            GUIManager.getStatusbar().setTextAndFadeOut("Bereich wurde erstellt.");
                            studentClassTreeModel.insertNodeInto(createTreeNode(organisationUnit), getParentNode(organisationUnit), getParentNode(organisationUnit).getChildCount());
                            customerAlreadyCreated = true;

                            // It the parent changes after the save, then change it in the tree
                            organisationUnit.addPropertyChangeListener(OrganisationUnit.PROPERTYNAME_PARENT, organisationUnitChanged = new PropertyChangeListener() {

                                public void propertyChange(PropertyChangeEvent evt) {
                                    MutableTreeNode node = getNode(organisationUnit);
                                    studentClassTreeModel.removeNodeFromParent(node);
                                    studentClassTreeModel.insertNodeInto(node, getParentNode(organisationUnit), getParentNode(organisationUnit).getChildCount());
                                }
                            });
                        }
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        // Remove Listeners
                        model.removeButtonListener(this);
                        organisationUnit.removePropertyChangeListener(organisationUnitChanged);
                        GUIManager.changeToPreviousView();
                        GUIManager.getInstance().unlockMenu();
                    }
                }
            });

            GUIManager.changeViewTo(editView, true);
            GUIManager.setLoadingScreenVisible(false);

        }
    }

    private class EditOrganisationUnitAction extends AbstractAction {

        public EditOrganisationUnitAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().lockMenu();
            GUIManager.setLoadingScreenText("Bereich wird geladen...");
            GUIManager.setLoadingScreenVisible(true);

            final DefaultMutableTreeNode node = (DefaultMutableTreeNode) studentClassTreeSelectionModel.getSelectionPath().getLastPathComponent();
            final OrganisationUnit organisationUnit = (OrganisationUnit) node.getUserObject();

            // Wenn sie die bereiche aendern, das element verschieben
            final PropertyChangeListener organisationUnitChanged;
            organisationUnit.addPropertyChangeListener(OrganisationUnit.PROPERTYNAME_PARENT, organisationUnitChanged = new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    studentClassTreeModel.removeNodeFromParent(node);
                    studentClassTreeModel.insertNodeInto(node, getParentNode(organisationUnit), getParentNode(organisationUnit).getChildCount());
                }
            });

            final EditOrganisationUnitPresentationModel model = new EditOrganisationUnitPresentationModel(organisationUnit, new CWHeaderInfo("Bereich erstellen"));
            final EditOrganisationUnitView editView = new EditOrganisationUnitView(model);

            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        PMOrganisationUnit.getInstance().save(organisationUnit);
                        GUIManager.getStatusbar().setTextAndFadeOut("Bereich wurde aktualisiert.");
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        // Remove Listeners
                        model.removeButtonListener(this);
                        organisationUnit.removePropertyChangeListener(organisationUnitChanged);
                        GUIManager.changeToPreviousView();
                        GUIManager.getInstance().unlockMenu();
                    }
                }
            });

            GUIManager.changeViewTo(editView, true);
            GUIManager.setLoadingScreenVisible(false);
        }
    }

    private class RemoveOrganisationUnitAction extends AbstractAction {

        public RemoveOrganisationUnitAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            // Automaticly select the parent of the new OrganisationUnit
            if (!studentClassTreeSelectionModel.isSelectionEmpty()) {
                Object object = ((DefaultMutableTreeNode) studentClassTreeSelectionModel.getSelectionPath().getLastPathComponent()).getUserObject();
                if (object instanceof OrganisationUnit) {
                    final OrganisationUnit organisationUnit = (OrganisationUnit) object;
                    final DeleteOrganisationUnitPresentationModel model = new DeleteOrganisationUnitPresentationModel(organisationUnit);
                    final DeleteOrganisationUnitView view = new DeleteOrganisationUnitView(model);

                    final JDialog d = new JDialog(GUIManager.getInstance().getMainFrame(), true);
                    d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    d.setTitle(view.getName());
                    d.add(view);
                    d.pack();
                    d.setResizable(false);
                    CWUtils.centerWindow(d, GUIManager.getInstance().getMainFrame());

                    final WindowListener windowListener;
                    d.addWindowListener(windowListener = new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            d.removeWindowListener(this);
                            view.dispose();
                            d.dispose();
                        }
                    });

                    // Add the Listener
                    model.addButtonListener(new ButtonListener() {
                        public void buttonPressed(ButtonEvent evt) {
                            if(evt.getType() == ButtonEvent.OK_BUTTON) {

                                // Seleted organisatoinUnitNode
                                MutableTreeNode organisationUnitNode = getNode(organisationUnit);

                                if(model.getChoice() == DeleteOrganisationUnitPresentationModel.DELETE_ALL) {
                                    // Delete the object and all his children

                                    PMOrganisationUnit.getInstance().delete(organisationUnit);
                                    studentClassTreeModel.removeNodeFromParent(organisationUnitNode);


                                } else if(model.getChoice() == DeleteOrganisationUnitPresentationModel.MOVE_ALL) {
                                    // Move the child objects to the new position and delete the object

                                    // Get the new OrganisationUnit Object
                                    OrganisationUnit newOrganisationUnit = model.getOrganisationUnitSelection().getSelection();

                                    // Get new OrganisationUnit Node, which the other ones should move
                                    MutableTreeNode newOrganisationUnitNode = getNode(newOrganisationUnit);

                                    // Change all children of the old one
                                    DefaultMutableTreeNode childNode;
                                    Object childObject;
                                    for(int i=organisationUnitNode.getChildCount()-1; i>=0; i--) {

                                        // Get the Childnode
                                        childNode = (DefaultMutableTreeNode) organisationUnitNode.getChildAt(i);

                                        // Remove from the old one
                                        studentClassTreeModel.removeNodeFromParent(childNode);

                                        // Add to the new one
                                        studentClassTreeModel.insertNodeInto(childNode, newOrganisationUnitNode, newOrganisationUnitNode.getChildCount());

                                        // Change the object
                                        childObject = childNode.getUserObject();
                                        if(childObject instanceof OrganisationUnit) {
                                            ((OrganisationUnit)childObject).setParent(newOrganisationUnit);
                                            PMOrganisationUnit.getInstance().save(((OrganisationUnit)childObject));
                                        } else if(childObject instanceof StudentClass) {
                                            ((StudentClass)childObject).setOrganisationUnit(organisationUnit);
                                            PMStudentClass.getInstance().save((StudentClass)childObject);
                                        }
                                    }

                                    // Remove the old one
                                    studentClassTreeModel.removeNodeFromParent(organisationUnitNode);
                                    PMOrganisationUnit.getInstance().delete(organisationUnit);
                                }

                                d.setVisible(false);
                            }

                            d.removeWindowListener(windowListener);
                            view.dispose();
                            d.dispose();
                        }
                    });

                    // Set it visible
                    d.setVisible(true);

                }
            }
        }
    }

    private class NewStudentClassAction extends AbstractAction {

        public NewStudentClassAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().lockMenu();
            GUIManager.setLoadingScreenText("Formular wird geladen...");
            GUIManager.setLoadingScreenVisible(true);

            final StudentClass studentClass = new StudentClass();

            // Automaticly choose the right OrganisationUnit
            if (!studentClassTreeSelectionModel.isSelectionEmpty()) {
                Object parent = ((DefaultMutableTreeNode) studentClassTreeSelectionModel.getSelectionPath().getLastPathComponent()).getUserObject();
                if (parent instanceof OrganisationUnit) {
                    studentClass.setOrganisationUnit((OrganisationUnit) parent);
                } else {

                    // Automaticlly select the first OrganisationUnit if no OrganisationUnit is selected
                    studentClass.setOrganisationUnit(PMOrganisationUnit.getInstance().getAll(0,1).get(0));
                }
            } else {

                // Automaticlly select the first OrganisationUnit if no OrganisationUnit is selected
                studentClass.setOrganisationUnit(PMOrganisationUnit.getInstance().getAll(0,1).get(0));
            }

            final EditStudentClassPresentationModel model = new EditStudentClassPresentationModel(studentClass, new CWHeaderInfo("Klasse erstellen"));
            final EditStudentClassView editView = new EditStudentClassView(model);
            model.addButtonListener(new ButtonListener() {

                // If the parent changes
                PropertyChangeListener organisationUnitChanged;
                boolean customerAlreadyCreated = false;

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        PMStudentClass.getInstance().save(studentClass);
                        if (customerAlreadyCreated) {
                            GUIManager.getStatusbar().setTextAndFadeOut("Klasse wurde aktualisiert.");
                        } else {
                            GUIManager.getStatusbar().setTextAndFadeOut("Klasse wurde erstellt.");
                            studentClassTreeModel.insertNodeInto(createTreeNode(studentClass), getParentNode(studentClass), getParentNode(studentClass).getChildCount());
                            customerAlreadyCreated = true;

                            // It the parent changes after the save, then change it in the tree
                            studentClass.addPropertyChangeListener(StudentClass.PROPERTYNAME_ORGANISATIONUNIT, organisationUnitChanged = new PropertyChangeListener() {

                                public void propertyChange(PropertyChangeEvent evt) {
                                    MutableTreeNode node = getNode(studentClass);
                                    studentClassTreeModel.removeNodeFromParent(node);
                                    studentClassTreeModel.insertNodeInto(node, getParentNode(studentClass), getParentNode(studentClass).getChildCount());
                                }
                            });
                        }
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {

                        // Remove Listeners
                        model.removeButtonListener(this);
                        studentClass.removePropertyChangeListener(organisationUnitChanged);
                        GUIManager.changeToPreviousView();
                        GUIManager.getInstance().unlockMenu();
                    }
                }
            });

            GUIManager.changeViewTo(editView, true);
            GUIManager.setLoadingScreenVisible(false);

        }
    }

    private class EditStudentClassAction extends AbstractAction {

        public EditStudentClassAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().lockMenu();
            GUIManager.setLoadingScreenText("Klasse wird geladen...");
            GUIManager.setLoadingScreenVisible(true);

            final DefaultMutableTreeNode node = (DefaultMutableTreeNode) studentClassTreeSelectionModel.getSelectionPath().getLastPathComponent();
            final StudentClass studentClass = (StudentClass) node.getUserObject();

            // Automaticly choose the right OrganisationUnit
            if (!studentClassTreeSelectionModel.isSelectionEmpty()) {
                Object parent = ((DefaultMutableTreeNode) studentClassTreeSelectionModel.getSelectionPath().getLastPathComponent()).getUserObject();
                if (parent instanceof OrganisationUnit) {
                    studentClass.setOrganisationUnit((OrganisationUnit) parent);
                }
            }

            // Wenn sie die bereiche aendern, das element verschieben
            final PropertyChangeListener organisationUnitChanged;
            studentClass.addPropertyChangeListener(StudentClass.PROPERTYNAME_ORGANISATIONUNIT, organisationUnitChanged = new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    studentClassTreeModel.removeNodeFromParent(node);
                    studentClassTreeModel.insertNodeInto(node, getNode(studentClass.getOrganisationUnit()), getNode(studentClass.getOrganisationUnit()).getChildCount());
                }
            });

            final EditStudentClassPresentationModel model = new EditStudentClassPresentationModel(studentClass, new CWHeaderInfo("Klasse erstellen"));
            final EditStudentClassView editView = new EditStudentClassView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        PMStudentClass.getInstance().save(studentClass);
                        GUIManager.getStatusbar().setTextAndFadeOut("Klasse wurde aktualisiert.");
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        // Remove Listeners
                        model.removeButtonListener(this);
                        studentClass.removePropertyChangeListener(organisationUnitChanged);
                        GUIManager.changeToPreviousView();
                        GUIManager.getInstance().unlockMenu();
                    }
                }
            });

            GUIManager.changeViewTo(editView, true);
            GUIManager.setLoadingScreenVisible(false);

        }
    }

    private class RemoveStudentClassAction extends AbstractAction {

        public RemoveStudentClassAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.setLoadingScreenText("Klasse loeschen...");
            GUIManager.setLoadingScreenVisible(true);

            if (!studentClassTreeSelectionModel.isSelectionEmpty()) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) studentClassTreeSelectionModel.getSelectionPath().getLastPathComponent();
                Object object = node.getUserObject();
                if (object instanceof StudentClass) {

                    int i = JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich die Klasse loeschen, dabei verlassen alle Schueler die Klasse.", "Klasse loeschen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (i == JOptionPane.OK_OPTION) {
                        GUIManager.setLoadingScreenText("Klasse wird geloescht...");

                        studentClassTreeModel.removeNodeFromParent(node);
                        PMStudentClass.getInstance().delete((StudentClass) object);

                    }
                }
                GUIManager.setLoadingScreenVisible(false);
            }
        }
    }

    private class ViewStudentsActionAction extends AbstractAction {

        public ViewStudentsActionAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.setLoadingScreenText("Schueler anzeigen...");
            GUIManager.setLoadingScreenVisible(true);

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) studentClassTreeSelectionModel.getSelectionPath().getLastPathComponent();

            final StudentClass studentClass = (StudentClass) node.getUserObject();
            final StudentsOverviewPresentationModel model = new StudentsOverviewPresentationModel(studentClass);
            StudentsOverviewView view = new StudentsOverviewView(model);

            JDialog d = new JDialog(GUIManager.getInstance().getMainFrame(), true);
            d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            d.setTitle(view.getName());
            d.add(view);
            d.pack();
            CWUtils.centerWindow(d, GUIManager.getInstance().getMainFrame());
            d.setVisible(true);

            d.dispose();
            d = null;

            view.dispose();

            GUIManager.setLoadingScreenVisible(false);
        }
    }

    private class MoveUpStudentClassAction extends AbstractAction {

        public MoveUpStudentClassAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.setLoadingScreenText("Schueler in die naechste Klasse aufsteigen lassen...");
            GUIManager.setLoadingScreenVisible(true);

            int i = JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich alle Schueler aufsteigen lassen? Einige Schueler verlassen dabei die Klasse.", "Schueler aufsteigen lassen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (i == JOptionPane.OK_OPTION) {
                GUIManager.setLoadingScreenText("Schueler steigen in die naechste Klasse auf...");

                List<Student> list = PMStudent.getInstance().getAll();
                Student student;
                for (int j = 0, l = list.size(); j < l; j++) {
                    student = list.get(j);
                    if (student.getStudentClass() != null) {
                        if (student.getStudentClass().getNextStudentClass() != null) {
                            student.setStudentClass(student.getStudentClass().getNextStudentClass());
                        } else {
                            student.setStudentClass(null);
                        }
                        PMStudent.getInstance().save(student);
                    }
                }

                
            }

            GUIManager.setLoadingScreenVisible(false);
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    // Other classes
    ////////////////////////////////////////////////////////////////////////////
    private class ActionTreeChangeListener implements TreeSelectionListener, TreeModelListener {

        public void valueChanged(TreeSelectionEvent e) {
            updateActions();
        }

        public void treeNodesChanged(TreeModelEvent e) {
            updateActions();
        }

        public void treeNodesInserted(TreeModelEvent e) {
            updateActions();
        }

        public void treeNodesRemoved(TreeModelEvent e) {
           updateActions();
        }

        public void treeStructureChanged(TreeModelEvent e) {
            updateActions();
        }

        public void updateActions() {
            if (studentClassTreeSelectionModel.isSelectionEmpty()) {
                editOrganisationUnitAction.setEnabled(false);
                removeOrganisationUnitAction.setEnabled(false);

                editStudentClassAction.setEnabled(false);
                removeStudentClassAction.setEnabled(false);
                viewStudentsAction.setEnabled(false);
            } else {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) studentClassTreeSelectionModel.getSelectionPath().getLastPathComponent();
                Object object = node.getUserObject();

                if (object instanceof OrganisationUnit) {
                    editOrganisationUnitAction.setEnabled(true);
                    removeOrganisationUnitAction.setEnabled(true);

                    editStudentClassAction.setEnabled(false);
                    removeStudentClassAction.setEnabled(false);
                    viewStudentsAction.setEnabled(false);
                } else if (object instanceof StudentClass) {
                    editOrganisationUnitAction.setEnabled(false);
                    removeOrganisationUnitAction.setEnabled(false);

                    editStudentClassAction.setEnabled(true);
                    removeStudentClassAction.setEnabled(true);
                    viewStudentsAction.setEnabled(true);
                } else {
                    editOrganisationUnitAction.setEnabled(false);
                    removeOrganisationUnitAction.setEnabled(false);

                    editStudentClassAction.setEnabled(false);
                    removeStudentClassAction.setEnabled(false);
                    viewStudentsAction.setEnabled(false);
                }
            }

            // Only enable the New StudentClass function, if there is already a OrganisationUnit
            newStudentClassAction.setEnabled(PMOrganisationUnit.getInstance().size() != 0);

            // Only enable the MoveUpStudentClass function, if there are StudentClasses
            moveUpStudentClassAction.setEnabled(PMStudentClass.getInstance().size() != 0);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Getter methods for the model
    ////////////////////////////////////////////////////////////////////////////
    public Action getNewOrganisationUnitAction() {
        return newOrganisationUnitAction;
    }

    public Action getEditOrganisationUnitAction() {
        return editOrganisationUnitAction;
    }

    public Action getRemoveOrganisationUnitAction() {
        return removeOrganisationUnitAction;
    }

    public Action getNewStudentClassAction() {
        return newStudentClassAction;
    }

    public Action getEditStudentClassAction() {
        return editStudentClassAction;
    }

    public Action getRemoveStudentClassAction() {
        return removeStudentClassAction;
    }

    public Action getMoveUpStudentClassAction() {
        return moveUpStudentClassAction;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public TreeModel getStudentClassTreeModel() {
        return studentClassTreeModel;
    }

    public TreeSelectionModel getStudentClassTreeSelectionModel() {
        return studentClassTreeSelectionModel;
    }

    public Action getViewStudentsAction() {
        return viewStudentsAction;
    }

}