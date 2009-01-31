/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.roommanagementmodul.pojo.Bereich;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Dominik
 */
public class BereichView {

    BereichPresentationModel model;
    private JButton bNew;
    private JButton bEdit;
    private JButton bDelete;
    private JButton bBack;
    private JTree bereichTree;

    public BereichView(BereichPresentationModel m) {
        this.model = m;
    }

    private void initComponents() {

        bNew = new JButton(model.getNewAction());
        bNew.setText("Neu");
        bEdit = new JButton(model.getEditAction());
        bEdit.setText("Bearbeiten");
        bDelete = new JButton(model.getDeleteAction());
        bDelete.setText("Löschen");
        bBack = new JButton(model.getBackAction());
        bBack.setText("Zurück");


        System.out.println("init");
        model.initTree(model.getRootTree());
        bereichTree = new JTree(model.getTreeModel());

        bereichTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        bereichTree.addTreeSelectionListener(model.getBereichListener());

        MyRenderer renderer = new MyRenderer();
        renderer.setOpenIcon(CWUtils.loadIcon("cw/roommanagementmodul/images/box.png"));
        renderer.setClosedIcon(CWUtils.loadIcon("cw/roommanagementmodul/images/box.png"));
        renderer.setLeafIcon(CWUtils.loadIcon("cw/roommanagementmodul/images/box.png"));
        bereichTree.setCellRenderer(renderer);
        bereichTree.getSelectionModel().addTreeSelectionListener(new MyTreeSelectionListener());

    }

    private void initEventHandling() {
    }

    public JPanel buildPanel() {
        JViewPanel panel = new JViewPanel();
        panel.setHeaderInfo(new HeaderInfo(model.getHeaderText()));
        this.initComponents();

        panel.getButtonPanel().add(bNew);
        panel.getButtonPanel().add(bEdit);
        panel.getButtonPanel().add(bDelete);
        panel.getButtonPanel().add(bBack);
        panel.getContentPanel().add(bereichTree, BorderLayout.CENTER);


        return panel;
    }
    
    class MyRenderer extends DefaultTreeCellRenderer {
    

    public MyRenderer() {
        
    }

        @Override
    public Component getTreeCellRendererComponent(
                        JTree tree,
                        Object value,
                        boolean sel,
                        boolean expanded,
                        boolean leaf,
                        int row,
                        boolean hasFocus) {

        super.getTreeCellRendererComponent(
                        tree, value, sel,
                        expanded, leaf, row,
                        hasFocus);
        if (isRoot(value)) {
            setIcon(CWUtils.loadIcon("cw/boardingschoolmanagement/images/house.png"));
            
        } else {
            setIcon(CWUtils.loadIcon("cw/roommanagementmodul/images/box.png"));
        } 

        return this;
    }

    protected boolean isRoot(Object value) {
        DefaultMutableTreeNode node =
                (DefaultMutableTreeNode)value;
        Bereich bereich =(Bereich)(node.getUserObject());
                
        if (bereich.getParentBereich()==null) {
            return true;
        }

        return false;
    }
}
    class MyTreeSelectionListener implements TreeSelectionListener{

        public void valueChanged(TreeSelectionEvent e) {
            if(e.getNewLeadSelectionPath()==null){
                model.getEditAction().setEnabled(false);
            model.getDeleteAction().setEnabled(false);
            }else{
                model.getEditAction().setEnabled(true);
            model.getDeleteAction().setEnabled(true);
            }
            
        }

    }

}
