package cw.studentmanagementmodul.gui.renderer;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.studentmanagementmodul.pojo.OrganisationUnit;
import cw.studentmanagementmodul.pojo.StudentClass;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author ManuelG
 */
public class StudentClassTreeCellRenderer extends DefaultTreeCellRenderer {

    private Icon studentClassIcon = CWUtils.loadIcon("cw/studentmanagementmodul/images/image.png");
    private Icon organisationUnitIcon = CWUtils.loadIcon("cw/studentmanagementmodul/images/box.png");
    private Icon worldIcon = CWUtils.loadIcon("cw/studentmanagementmodul/images/world.png");

    public StudentClassTreeCellRenderer() {
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object obj = node.getUserObject();
        if (obj instanceof OrganisationUnit) {
            setIcon(organisationUnitIcon);
        } else if (obj instanceof StudentClass) {
            setIcon(studentClassIcon);
        } else {
            setIcon(worldIcon);
        }
        return this;
    }
}
