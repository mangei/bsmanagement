/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.PresentationModel;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.manager.GUIManager;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import cw.roommanagementmodul.pojo.manager.BereichManager;
import cw.roommanagementmodul.pojo.Bereich;
import javax.swing.JOptionPane;

/**
 *
 * @author Dominik
 */
public class BereichPresentationModel extends PresentationModel<BereichManager> {

    private Bereich selectedBereich;
    private DefaultMutableTreeNode selectedNode;
    private BereichManager bereichManager;
    private TreeSelectionListener bereichListener;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootTree;
    private ButtonListenerSupport support;
    private Action newAction;
    private Action editAction;
    private Action deleteAction;
    private Action backAction;
    private String headerText;

    public BereichPresentationModel(BereichManager bereichManager) {
        super(bereichManager);
        selectedBereich = null;
        this.bereichManager = bereichManager;
        initModels();
        this.initEventHandling();

    }

    BereichPresentationModel(BereichManager bereichManager, String header) {
        super(bereichManager);
        selectedBereich = null;
        this.bereichManager = bereichManager;
        this.headerText = header;
        initModels();
        this.initEventHandling();
    }

    private void initModels() {

        support = new ButtonListenerSupport();
        newAction = new NewAction();
        editAction = new EditAction();
        editAction.setEnabled(false);
        deleteAction = new DeleteAction();
        deleteAction.setEnabled(false);
        backAction = new BackAction();
        bereichListener = new BereichTreeListener();

        Bereich internatBereich;
        if (bereichManager.existRoot() == false) {
            internatBereich = new Bereich("Internat", null);
            bereichManager.save(internatBereich);
            selectedBereich = internatBereich;
        } else {
            internatBereich = bereichManager.getRoot();
            selectedBereich = internatBereich;
        }

        rootTree = new DefaultMutableTreeNode(internatBereich, true);
        treeModel = new DefaultTreeModel(rootTree);
    }

    private void initEventHandling() {
//        getZimmerSelection().addPropertyChangeListener(
//                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
//                new SelectionEmptyHandler());
    }

    public Action getNewAction() {
        return newAction;
    }

    public Action getEditAction() {
        return editAction;
    }

    public Action getDeleteAction() {
        return deleteAction;
    }

    public DefaultMutableTreeNode getRootTree() {
        return rootTree;
    }

    public void setRootTree(DefaultMutableTreeNode rootTree) {
        this.rootTree = rootTree;
    }

    public Bereich getSelectedBereich() {
        return selectedBereich;
    }

    public void setSelectedBereich(Bereich selectedBereich) {
        this.selectedBereich = selectedBereich;
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    public TreeSelectionListener getBereichListener() {
        return bereichListener;
    }

    public void setBereichListener(TreeSelectionListener bereichListener) {
        this.bereichListener = bereichListener;
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    public void setTreeModel(DefaultTreeModel treeModel) {
        this.treeModel = treeModel;
    }

    public Action getBackAction() {
        return backAction;
    }

    public String getHeaderText() {
        return headerText;
    }

    private class NewAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/box_add.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final Bereich b = new Bereich();
            final EditBereichPresentationModel model = new EditBereichPresentationModel(b, "Bereich erstellen",selectedBereich);
            final EditBereichView editView = new EditBereichView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        Long check = b.getId();
                        bereichManager.save(b);
                        bereichManager.refreshBereich(b);
                        DefaultMutableTreeNode parentNode = searchTreeNode(treeModel, b.getParentBereich());

                        if (check == null) {
                            treeModel.insertNodeInto(new DefaultMutableTreeNode(b), parentNode, parentNode.getChildCount());

                        }
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        GUIManager.changeToLastView();
                        GUIManager.getStatusbar().setTextAndFadeOut("Bereich wurde erstellt.");

                    }
                }
            });
            GUIManager.changeView(editView.buildPanel(), true);

        }
    }

    private class EditAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/box_edit.png"));
        }

        public void actionPerformed(ActionEvent e) {
            editSelectedItem(e);
        }
    }

    private class DeleteAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/box_remove.png"));
        }

        public void actionPerformed(ActionEvent e) {
            Bereich b = selectedBereich;

            if (b.getParentBereich() != null && checkZimmer(b) == false) {
                DefaultMutableTreeNode node = searchTreeNode(treeModel, b);
                treeModel.removeNodeFromParent(node);

                bereichManager.delete(b);
            }else{
                if(b.getParentBereich()!=null){
                    JOptionPane.showMessageDialog(null, "Löschen nicht möglich! \nEs befinden sich Zimmer in dieser Bereichsstruktur.");
                }else{
                    JOptionPane.showMessageDialog(null, "Internat kann nicht gelöscht werden.");
                }

                
            }


        }
    }

    private boolean checkZimmer(Bereich b) {

        boolean check;
        if(b.getZimmerList()!=null && b.getZimmerList().size() > 0){
            return true;
        }
        if (b.getChildBereichList() != null) {
            for (int i = 0; i < b.getChildBereichList().size(); i++) {
                check= checkZimmer(b.getChildBereichList().get(i));
                if(check==true){
                    return check;
                }
            }
        }
        return false;
    }

    private class BackAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/arrow_left.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.changeToLastView();  // Zur Übersicht wechseln

        }
    }

    private final class SelectionEmptyHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
        }
    }

    // Event Handling *********************************************************
    public MouseListener getDoubleClickHandler() {
        return new DoubleClickHandler();
    }

    private final class DoubleClickHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                editSelectedItem(e);
            }
        }
    }

    private void editSelectedItem(EventObject e) {
        final Bereich b = selectedBereich;
        final EditBereichPresentationModel model = new EditBereichPresentationModel(b, "Bereich bearbeiten",null);
        final EditBereichView editView = new EditBereichView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    bereichManager.save(b);

                    DefaultMutableTreeNode newParentNode = searchTreeNode(treeModel, b.getParentBereich());
                    DefaultMutableTreeNode node = searchTreeNode(treeModel, b);
                    DefaultMutableTreeNode oldParentNode = (DefaultMutableTreeNode) node.getParent();

                    Bereich parentBereich = (Bereich) oldParentNode.getUserObject();
                    treeModel.removeNodeFromParent(node);
                    parentBereich.getChildBereichList().remove(b);

                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(b);

                    //überlegen...
                    initTree(newNode);
                    treeModel.insertNodeInto(newNode, newParentNode, newParentNode.getChildCount());

                    GUIManager.getStatusbar().setTextAndFadeOut("Bereich wurde aktualisiert.");
                }
                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    model.removeButtonListener(this);
                    GUIManager.changeToLastView();

                }
            }
        });
        GUIManager.changeView(editView.buildPanel(), true);
    }

    private class BereichTreeListener implements TreeSelectionListener {

        public void valueChanged(TreeSelectionEvent e) {

            JTree tree = (JTree) e.getSource();

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) {
                setSelectedBereich(null);
                return;
            }
            selectedNode = node;
            setSelectedBereich((Bereich) node.getUserObject());
        }
    }

    public List<Bereich> initTree(DefaultMutableTreeNode rootTree) {

        Bereich rootBereich = (Bereich) rootTree.getUserObject();
        bereichManager.refreshBereich(rootBereich);
        List<Bereich> childList = rootBereich.getChildBereichList();
        DefaultMutableTreeNode node;
        if (childList != null) {
            for (int i = 0; i < childList.size(); i++) {
                node = new DefaultMutableTreeNode(childList.get(i), true);
                rootTree.add(node);
                initTree(node);
            }
        }
        return childList;
    }

    private DefaultMutableTreeNode searchTreeNode(TreeModel treeModel, Bereich parentBereich) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        if (root.getUserObject().equals(parentBereich)) {
            return root;
        }
        DefaultMutableTreeNode parentNode = searchTreeNode(root.children(), parentBereich);
        return parentNode;
    }

    private DefaultMutableTreeNode searchTreeNode(Enumeration children, Bereich parentBereich) {

        while (children.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) children.nextElement();
            if (node.getUserObject().equals(parentBereich)) {
                return node;
            } else {
                node = searchTreeNode(node.children(), parentBereich);
            }
            if (node != null) {
                return node;
            }
        }
        return null;
    }
}


