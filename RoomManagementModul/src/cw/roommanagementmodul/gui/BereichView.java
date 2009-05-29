package cw.roommanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.roommanagementmodul.pojo.Bereich;
import cw.roommanagementmodul.pojo.Zimmer;
import gui.component.JViewPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Dominik
 */
public class BereichView extends CWView
{

    BereichPresentationModel model;
    private JButton bNew;
    private JButton bEdit;
    private JButton bDelete;
    private JButton bZimmerNew;
    private JButton bZimmerEdit;
    private JButton bZimmerDelete;
    private JTree bereichTree;
    private JButton viewZimmerTabelle;
    private CWComponentFactory.CWComponentContainer componentContainer;

    public BereichView(BereichPresentationModel m) {
        this.model = m;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {

        bNew = CWComponentFactory.createButton(model.getNewAction());
        bNew.setText("Neuer Bereich");
        bEdit = CWComponentFactory.createButton(model.getEditAction());
        bEdit.setText("Bearbeiten");
        bDelete = CWComponentFactory.createButton(model.getDeleteAction());
        bDelete.setText("Löschen");


        bZimmerNew = CWComponentFactory.createButton(model.getNewZimmerAction());
        bZimmerNew.setText("Neues Zimmer");
        bZimmerEdit = CWComponentFactory.createButton(model.getEditZimmerAction());
        bZimmerEdit.setText("Bearbeiten");
        bZimmerDelete = CWComponentFactory.createButton(model.getDeleteZimmerAction());
        bZimmerDelete.setText("Löschen");

        viewZimmerTabelle = CWComponentFactory.createButton(model.getViewTabelleAction());
        viewZimmerTabelle.setText("Zimmer Tabelle");

        model.initTree(model.getRootTree());
        bereichTree = CWComponentFactory.createTree(model.getTreeModel());
        bereichTree.setRootVisible(true);

        bereichTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        bereichTree.addTreeSelectionListener(model.getBereichListener());
        bereichTree.setShowsRootHandles(true);

        MyRenderer renderer = new MyRenderer();
        renderer.setOpenIcon(CWUtils.loadIcon("cw/roommanagementmodul/images/box.png"));
        renderer.setClosedIcon(CWUtils.loadIcon("cw/roommanagementmodul/images/box.png"));
        renderer.setLeafIcon(CWUtils.loadIcon("cw/roommanagementmodul/images/box.png"));
        bereichTree.setCellRenderer(renderer);

        componentContainer = CWComponentFactory.createCWComponentContainer().addComponent(bNew).addComponent(bEdit).addComponent(bDelete).addComponent(bZimmerNew).addComponent(bZimmerEdit).addComponent(bZimmerDelete).addComponent(viewZimmerTabelle).addComponent(bereichTree);


    }

    private void initEventHandling() {

    }

    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());

        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bDelete);
        this.getButtonPanel().add(bZimmerNew);
        this.getButtonPanel().add(bZimmerEdit);
        this.getButtonPanel().add(bZimmerDelete);
        this.getButtonPanel().add(viewZimmerTabelle);
        this.getContentPanel().add(new JScrollPane(bereichTree), BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        bereichTree.removeTreeSelectionListener(model.getBereichListener());
        componentContainer.dispose();
        model.dispose();
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

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            if (node.getUserObject() instanceof Zimmer) {
                setIcon(CWUtils.loadIcon("cw/roommanagementmodul/images/door.png"));
                Zimmer z = (Zimmer) node.getUserObject();
                setText(z.getName());
            } else {
                Bereich b = (Bereich) node.getUserObject();
                setText(b.getName());
                if (isRoot(value)) {
                    setIcon(CWUtils.loadIcon("cw/boardingschoolmanagement/images/house.png"));

                } else {
                    setIcon(CWUtils.loadIcon("cw/roommanagementmodul/images/box.png"));
                }
            }
            return this;
        }

        protected boolean isRoot(Object value) {
            DefaultMutableTreeNode node =
                    (DefaultMutableTreeNode) value;
            Bereich bereich = (Bereich) (node.getUserObject());

            if (bereich.getParentBereich() == null) {
                return true;
            }

            return false;
        }
    }
}
