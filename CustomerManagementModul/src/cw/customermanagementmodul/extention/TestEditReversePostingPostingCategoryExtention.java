package cw.customermanagementmodul.extention;

import cw.customermanagementmodul.extention.point.EditReversePostingPostingCategoryExtentionPoint;
import cw.customermanagementmodul.gui.EditReversePostingPresentationModel;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ManuelG
 */
public class TestEditReversePostingPostingCategoryExtention implements EditReversePostingPostingCategoryExtentionPoint {

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

    public void dispose() {

    }

}
