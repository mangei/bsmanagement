package cw.accountmanagementmodul.extention.point;

import cw.accountmanagementmodul.gui.EditReversePostingPresentationModel;
import cw.boardingschoolmanagement.interfaces.Extention;
import javax.swing.JComponent;
import java.util.List;

/**
 *
 * @author Manuel Geier
 */
public interface EditReversePostingPostingCategoryExtentionPoint extends Extention {

    public void initPresentationModel(EditReversePostingPresentationModel editReversePostingModel);

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
