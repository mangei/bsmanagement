package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.extentions.interfaces.EditPostingPostingCategoryExtention;
import cw.customermanagementmodul.gui.EditPostingPresentationModel;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class TestEditPostingPostingCategoryExtention implements EditPostingPostingCategoryExtention {

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

}
