package cw.accountmanagementmodul.gui.renderer;

import cw.accountmanagementmodul.pojo.AbstractPosting;
import cw.accountmanagementmodul.pojo.Posting;
import cw.accountmanagementmodul.pojo.PostingGroup;
import cw.boardingschoolmanagement.app.CWUtils;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author ManuelG
 */
public class PostingTreeTableRenderer
        implements TreeCellRenderer {

    private JLabel label;

    private Icon postingIcon = CWUtils.loadIcon("cw/accountmanagementmodul/images/posting.png");
    private Icon postingGroupIcon = CWUtils.loadIcon("cw/accountmanagementmodul/images/postinggroup.png");

    public PostingTreeTableRenderer() {
        label = new JLabel();
    }

    public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean selected,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {

        if(value instanceof AbstractPosting) {
            label.setText(((AbstractPosting)value).getName());
        } else {
            label.setText("");
        }

        if(value instanceof Posting) {
            label.setIcon(postingIcon);
        } else if(value instanceof PostingGroup) {
            label.setIcon(postingGroupIcon);
        } else {
            label.setIcon(null);
        }

        return label;
    }

}
