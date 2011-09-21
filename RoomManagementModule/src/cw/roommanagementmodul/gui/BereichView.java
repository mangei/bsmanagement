package cw.roommanagementmodul.gui;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTree;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.roommanagementmodul.persistence.Bereich;
import cw.roommanagementmodul.persistence.Zimmer;

/**
 *
 * @author Dominik
 */
public class BereichView
	extends CWView<BereichPresentationModel>
{

    private CWButton bNew;
    private CWButton bEdit;
    private CWButton bDelete;
    private CWButton bZimmerNew;
    private CWButton bZimmerEdit;
    private CWButton bZimmerDelete;
    private CWTree bereichTree;
    private CWButton viewZimmerTabelle;

    public BereichView(BereichPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();

        bNew = CWComponentFactory.createButton(getModel().getNewAction());
        bNew.setText("Neuer Bereich");
        bEdit = CWComponentFactory.createButton(getModel().getEditAction());
        bEdit.setText("Bearbeiten");
        bDelete = CWComponentFactory.createButton(getModel().getDeleteAction());
        bDelete.setText("Loeschen");

        bZimmerNew = CWComponentFactory.createButton(getModel().getNewZimmerAction());
        bZimmerNew.setText("Neues Zimmer");
        bZimmerEdit = CWComponentFactory.createButton(getModel().getEditZimmerAction());
        bZimmerEdit.setText("Bearbeiten");
        bZimmerDelete = CWComponentFactory.createButton(getModel().getDeleteZimmerAction());
        bZimmerDelete.setText("Loeschen");

        viewZimmerTabelle = CWComponentFactory.createButton(getModel().getViewTabelleAction());
        viewZimmerTabelle.setText("Zimmer Tabelle");

        getModel().initTree(getModel().getRootTree());
        bereichTree = CWComponentFactory.createTree(getModel().getTreeModel());
        bereichTree.setRootVisible(true);

        bereichTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        bereichTree.addTreeSelectionListener(getModel().getBereichListener());
        bereichTree.setShowsRootHandles(true);

        MyRenderer renderer = new MyRenderer();
        renderer.setOpenIcon(CWUtils.loadIcon("cw/roommanagementmodul/images/box.png"));
        renderer.setClosedIcon(CWUtils.loadIcon("cw/roommanagementmodul/images/box.png"));
        renderer.setLeafIcon(CWUtils.loadIcon("cw/roommanagementmodul/images/box.png"));
        bereichTree.setCellRenderer(renderer);

        getComponentContainer()
        	.addComponent(bNew)
        	.addComponent(bEdit)
        	.addComponent(bDelete)
        	.addComponent(bZimmerNew)
        	.addComponent(bZimmerEdit)
        	.addComponent(bZimmerDelete)
        	.addComponent(viewZimmerTabelle)
        	.addComponent(bereichTree);
    }

    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());

        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bDelete);
        this.getButtonPanel().add(bZimmerNew);
        this.getButtonPanel().add(bZimmerEdit);
        this.getButtonPanel().add(bZimmerDelete);
        this.getButtonPanel().add(viewZimmerTabelle);
        
        addToContentPanel(new JScrollPane(bereichTree), true);
    }

    @Override
    public void dispose() {
        bereichTree.removeTreeSelectionListener(getModel().getBereichListener());
        
        super.dispose();
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
