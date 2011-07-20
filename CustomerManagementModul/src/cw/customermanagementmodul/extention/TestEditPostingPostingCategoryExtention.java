package cw.customermanagementmodul.extention;

import cw.customermanagementmodul.extention.point.EditPostingPostingCategoryExtentionPoint;
import cw.customermanagementmodul.gui.EditPostingPresentationModel;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class TestEditPostingPostingCategoryExtention implements EditPostingPostingCategoryExtentionPoint {

    public void initPresentationModel(EditPostingPresentationModel editPostingModel) {
        
    }

    public JComponent getView() {
        JPanel panel = new JPanel();

        panel.add(new JLabel("Test"));

        return panel;
    }

    public void save() {
    }

    public List<String> validate() {
        return null;
    }

    public String getKey() {
        return "test";
    }

    public void dispose() {
        
    }

}
