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

    private Icon studentClassIcon = CWUtils.loadIcon("cw/studentmanagementmodul/images/studentClass.png");
    private Icon organisationUnitIcon = CWUtils.loadIcon("cw/studentmanagementmodul/images/box.png");
    private Icon worldIcon = CWUtils.loadIcon("cw/studentmanagementmodul/images/world.png");
    private Icon noClassIcon = CWUtils.loadIcon("cw/studentmanagementmodul/images/studentClass_inactive.png");

    public StudentClassTreeCellRenderer() {
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object obj = node.getUserObject();
        if (obj instanceof OrganisationUnit) {
            setIcon(organisationUnitIcon);
            setText(((OrganisationUnit)obj).getName());
        } else if (obj instanceof StudentClass) {
            setIcon(studentClassIcon);
            setText(((StudentClass)obj).getName());
        } else if(obj instanceof String) {
            if(((String)obj).equals("noClass")) {
                setIcon(noClassIcon);
                setText("Keine Klasse");
            } else if(((String)obj).equals("world")) {
                setIcon(worldIcon);
                setText("Welt");
            }
        }
        return this;
    }
}
