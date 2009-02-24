/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
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
import cw.roommanagementmodul.pojo.Zimmer;
import cw.roommanagementmodul.pojo.manager.ZimmerManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Dominik
 */
public class BereichPresentationModel {

    private Bereich selectedBereich;
    private Zimmer selectedZimmer;
    private DefaultMutableTreeNode selectedNode;
    private BereichManager bereichManager;
    private ZimmerManager zimmerManager;
    private TreeSelectionListener bereichListener;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootTree;
    private ButtonListenerSupport support;
    private Action newAction;
    private Action editAction;
    private Action deleteAction;
    private String headerText;
    private Action newZimmerAction;
    private Action editZimmerAction;
    private Action deleteZimmerAction;
    private Action viewTabelleAction;
    private BereichPresentationModel bereichModel;
    private HeaderInfo headerInfo;

    public BereichPresentationModel(BereichManager bereichManager) {
        selectedBereich = null;
        this.bereichManager = bereichManager;
        initModels();
        this.initEventHandling();

    }

    public BereichPresentationModel(BereichManager bereichManager, HeaderInfo header) {
        selectedBereich = null;
        this.bereichManager = bereichManager;
        this.zimmerManager = ZimmerManager.getInstance();
        this.headerText = header.getHeaderText();
        this.headerInfo=header;
        bereichModel=this;
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
        
        setViewTabelleAction(new ViewTabelleAction());
        setNewZimmerAction(new NewZimmerAction());
        setEditZimmerAction(new EditZimmerAction());
        setDeleteZimmerAction(new DeleteZimmerAction());
        bereichListener = new BereichTreeListener();
        getEditZimmerAction().setEnabled(false);
        getDeleteZimmerAction().setEnabled(false);

        Bereich internatBereich;
        if (bereichManager.existRoot() == false) {
            internatBereich = new Bereich("Internat", null);
            bereichManager.save(internatBereich);
            selectedBereich = internatBereich;
        } else {
            internatBereich = bereichManager.getRoot();
            selectedBereich = internatBereich;
        }

        setRootTree(new DefaultMutableTreeNode(internatBereich, true));
        treeModel = new DefaultTreeModel(getRootTree());
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


    public String getHeaderText() {
        return headerText;
    }

    /**
     * @return the newZimmerAction
     */
    public Action getNewZimmerAction() {
        return newZimmerAction;
    }

    /**
     * @param newZimmerAction the newZimmerAction to set
     */
    public void setNewZimmerAction(Action newZimmerAction) {
        this.newZimmerAction = newZimmerAction;
    }

    /**
     * @return the editZimmerAction
     */
    public Action getEditZimmerAction() {
        return editZimmerAction;
    }

    /**
     * @param editZimmerAction the editZimmerAction to set
     */
    public void setEditZimmerAction(Action editZimmerAction) {
        this.editZimmerAction = editZimmerAction;
    }

    /**
     * @return the deleteZimmerAction
     */
    public Action getDeleteZimmerAction() {
        return deleteZimmerAction;
    }

    /**
     * @param deleteZimmerAction the deleteZimmerAction to set
     */
    public void setDeleteZimmerAction(Action deleteZimmerAction) {
        this.deleteZimmerAction = deleteZimmerAction;
    }

    /**
     * @return the viewTabelleAction
     */
    public Action getViewTabelleAction() {
        return viewTabelleAction;
    }

    /**
     * @param viewTabelleAction the viewTabelleAction to set
     */
    public void setViewTabelleAction(Action viewTabelleAction) {
        this.viewTabelleAction = viewTabelleAction;
    }

    /**
     * @return the headerInfo
     */
    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    /**
     * @param headerInfo the headerInfo to set
     */
    public void setHeaderInfo(HeaderInfo headerInfo) {
        this.headerInfo = headerInfo;
    }

    private class NewAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/box_add.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final Bereich b = new Bereich();
            final EditBereichPresentationModel model = new EditBereichPresentationModel(b, new HeaderInfo("Bereich erstellen","Hier können Sie einen neuen Bereich erstellen"), selectedBereich);
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
            bereichManager.refreshBereich(b);
            if (b.getParentBereich() != null && checkZimmer(b) == false) {
                DefaultMutableTreeNode node = searchTreeNode(treeModel, b);
                treeModel.removeNodeFromParent(node);

                bereichManager.delete(b);
                selectedBereich=(Bereich)rootTree.getUserObject();
            } else {
                if (b.getParentBereich() != null) {
                    JOptionPane.showMessageDialog(null, "Löschen nicht möglich! \nEs befinden sich Zimmer in dieser Bereichsstruktur.");
                } else {
                    JOptionPane.showMessageDialog(null, "Internat kann nicht gelöscht werden.");
                }


            }


        }
    }

    private boolean checkZimmer(Bereich b) {

        boolean check;
        if (b.getZimmerList() != null && b.getZimmerList().size() > 0) {
            return true;
        }
        if (b.getChildBereichList() != null) {
            for (int i = 0; i < b.getChildBereichList().size(); i++) {
                check = checkZimmer(b.getChildBereichList().get(i));
                if (check == true) {
                    return check;
                }
            }
        }
        return false;
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
        final EditBereichPresentationModel model = new EditBereichPresentationModel(b, new HeaderInfo("Bereich bearbeiten","Hier können Sie einen bestehenden Bereich bearbeiten"), null);
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
            if (node != null && node.getUserObject() instanceof Bereich) {
                selectedNode = node;
                setSelectedBereich((Bereich) node.getUserObject());
                selectedZimmer = null;

                //Buttons umschalten:
                getEditAction().setEnabled(true);
                getDeleteAction().setEnabled(true);

                getEditZimmerAction().setEnabled(false);
                getDeleteZimmerAction().setEnabled(false);
            }
            if (node != null && node.getUserObject() instanceof Zimmer) {
                selectedZimmer = (Zimmer) node.getUserObject();
                selectedNode = node;
                selectedBereich = null;

                //Buttons umschalten
                getEditAction().setEnabled(false);
                getDeleteAction().setEnabled(false);
                getEditZimmerAction().setEnabled(true);
                getDeleteZimmerAction().setEnabled(true);
            }
            if (node == null) {
                setSelectedBereich(null);
                selectedZimmer = null;
                getEditAction().setEnabled(false);
                getDeleteAction().setEnabled(false);
                return;
            }

        }
    }

    public List<Bereich> initTree(DefaultMutableTreeNode rootTree) {
        Bereich rootBereich = (Bereich) rootTree.getUserObject();
        bereichManager.refreshBereich(rootBereich);
        List<Bereich> childList = rootBereich.getChildBereichList();
        DefaultMutableTreeNode node;
        if (childList != null && childList.size() != 0) {
            for (int i = 0; i < childList.size(); i++) {
                node = new DefaultMutableTreeNode(childList.get(i), true);
                rootTree.add(node);
                initTree(node);
            }
        } else {
            if (rootBereich.getZimmerList() != null || rootBereich.getZimmerList().size() != 0) {
                for (int i = 0; i < rootBereich.getZimmerList().size(); i++) {
                    rootTree.add(new DefaultMutableTreeNode(rootBereich.getZimmerList().get(i), true));
                }
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

    private DefaultMutableTreeNode searchZimmerTreeNode(TreeModel treeModel, Zimmer z) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();

        if (root.getUserObject() instanceof Zimmer && root.getUserObject().equals(z)) {
            return root;
        }
        DefaultMutableTreeNode parentNode = searchZimmerTreeNode(root.children(), z);
        return parentNode;
    }

    private DefaultMutableTreeNode searchZimmerTreeNode(Enumeration children, Zimmer z) {

        while (children.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) children.nextElement();
            if (node.getUserObject() instanceof Zimmer && node.getUserObject().equals(z)) {
                return node;
            } else {
                node = searchZimmerTreeNode(node.children(), z);
            }
            if (node != null) {
                return node;
            }
        }

        return null;
    }

    private DefaultMutableTreeNode searchTreeNode(
            Enumeration children, Bereich parentBereich) {

        while (children.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) children.nextElement();
            if (node.getUserObject() instanceof Bereich) {
                if (node.getUserObject().equals(parentBereich)) {
                    return node;
                } else {
                    node = searchTreeNode(node.children(), parentBereich);
                }

                if (node != null) {
                    return node;
                }

            }

        }
        return null;
    }

    private class NewZimmerAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/door_add.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final Zimmer z = new Zimmer();
            final EditZimmerPresentationModel model = new EditZimmerPresentationModel(z, new HeaderInfo("Zimmer erstellen","Hier können Sie ein neues Zimmer erstellen"),selectedBereich);
            final EditZimmerView editView = new EditZimmerView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        zimmerManager.save(z);
                        //TREE erweitern
                        DefaultMutableTreeNode bereichNode = searchTreeNode(treeModel, z.getBereich());
                        treeModel.insertNodeInto(new DefaultMutableTreeNode(z), bereichNode, bereichNode.getChildCount());
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);

                        

                        GUIManager.changeToLastView();
                        GUIManager.getStatusbar().setTextAndFadeOut("Zimmer wurde erstellt.");
                    }
                }
            });
            GUIManager.changeView(editView.buildPanel(), true);
        }
    }

    private class EditZimmerAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/door_edit.png"));
        }

        public void actionPerformed(ActionEvent e) {
            editZimmerSelectedItem(e);
        }
    }

    private class DeleteZimmerAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/door_remove.png"));
        }

        public void actionPerformed(ActionEvent e) {
            Zimmer z = selectedZimmer;
            if (z.getBewohnerList() == null || z.getBewohnerList().size() == 0) {
                DefaultMutableTreeNode bereichNode = searchTreeNode(treeModel, z.getBereich());
                Enumeration children = bereichNode.children();
                DefaultMutableTreeNode node = null;
                while (children.hasMoreElements()) {
                    node = (DefaultMutableTreeNode) children.nextElement();
                    if (node.getUserObject().equals(z)) {
                        break;
                    }
                }
                if (node != null) {
                    treeModel.removeNodeFromParent(node);
                    zimmerManager.delete(z);
                }

            } else {
                Object[] options = {"Ok"};
                JOptionPane.showOptionDialog(null, "Zimmer " + z.getName() + " enthält Bewohner!", "Warnung", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            }

        }
    }

    private void editZimmerSelectedItem(EventObject e) {
        final Zimmer z = selectedZimmer;
        final EditZimmerPresentationModel model = new EditZimmerPresentationModel(z, new HeaderInfo("Zimmer bearbeiten","Hier können Sie ein bestehndes Zimmer bearbeiten"));
        final EditZimmerView editView = new EditZimmerView(model);
        model.addButtonListener(new ButtonListener() {

            public void buttonPressed(ButtonEvent evt) {
                if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    zimmerManager.save(z);

                    //Alten Node entfernen:
                    DefaultMutableTreeNode zimmerNode = searchZimmerTreeNode(treeModel, z);
                    if (zimmerNode != null) {
                        treeModel.removeNodeFromParent(zimmerNode);
                    }

                    //Neuen Node hinzufügen:
                    DefaultMutableTreeNode bereichNode = searchTreeNode(treeModel, z.getBereich());
                    //initTree(zimmerNode);
                    treeModel.insertNodeInto(new DefaultMutableTreeNode(z), bereichNode, bereichNode.getChildCount());

                    GUIManager.getStatusbar().setTextAndFadeOut("Zimmer wurde aktualisiert.");
                }

                if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    model.removeButtonListener(this);
                    GUIManager.changeToLastView();
                }

            }
        });
        GUIManager.changeView(editView.buildPanel(), true);
    }

    private class ViewTabelleAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/door.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final ZimmerPresentationModel model = new ZimmerPresentationModel(ZimmerManager.getInstance(), new HeaderInfo("Zimmer Verwaltung","Übersicht aller Zimmer"),bereichModel);
            final ZimmerView zimmerView = new ZimmerView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        GUIManager.getStatusbar().setTextAndFadeOut("Zimmer wurden aktualisiert.");
                    }

                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        GUIManager.changeToLastView();
                    }
                    
                }
            });
            GUIManager.changeView(zimmerView.buildPanel(), true);
        }
    }
}


