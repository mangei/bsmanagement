package cw.boardingschoolmanagement.gui.component;

import java.util.Hashtable;
import java.util.Vector;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import org.jdesktop.swingx.JXTree;

/**
 *
 * @author ManuelG
 */
public class CWTree extends JXTree {

    CWTree(TreeModel newModel) {
        super(newModel);
    }

    CWTree(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
    }

    CWTree(TreeNode root) {
        super(root);
    }

    CWTree(Hashtable value) {
        super(value);
    }

    CWTree(Vector value) {
        super(value);
    }

    CWTree(Object[] value) {
        super(value);
    }

    CWTree() {
    }

}
