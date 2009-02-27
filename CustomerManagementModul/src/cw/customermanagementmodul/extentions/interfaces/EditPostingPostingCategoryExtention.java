package cw.customermanagementmodul.extentions.interfaces;

import cw.boardingschoolmanagement.extentions.interfaces.Extention;
import cw.customermanagementmodul.gui.EditPostingPresentationModel;
import javax.swing.JComponent;
import java.util.List;

/**
 *
 * @author Manuel Geier
 */
public interface EditPostingPostingCategoryExtention extends Extention {

    public void initPresentationModel(EditPostingPresentationModel editPostingModel);

    public JComponent getView();
    
    public void save();

    /**
     * checks before the save-method if the content the user entered is validate
     * if there are any errors, return a list of Strings with it otherwise return
     * null or an empty list.
     * @return validate
     */
    public List<String> validate();

    public String getKey();

    public void dispose();

}
