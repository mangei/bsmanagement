package cw.customermanagementmodul.extentions;

import cw.customermanagementmodul.extentions.interfaces.EditReversePostingPostingCategoryExtention;
import cw.customermanagementmodul.gui.EditReversePostingPresentationModel;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class TestEditReversePostingPostingCategoryExtention implements EditReversePostingPostingCategoryExtention {

    public void initPresentationModel(EditReversePostingPresentationModel editPostingModel) {
        
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
