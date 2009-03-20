package cw.boardingschoolmanagement.extentions.interfaces;

import cw.boardingschoolmanagement.gui.ConfigurationPresentationModel;
import cw.boardingschoolmanagement.interfaces.Priority;
import javax.swing.JComponent;
import java.util.List;
import javax.swing.Icon;

/**
 *
 * @author Manuel Geier
 */
public interface ConfigurationExtention
        extends Extention, Priority{

    public void initPresentationModel(ConfigurationPresentationModel configurationModel);

    public JComponent getView();

    public Object getModel();
    
    public void save();

    public List<String> validate();

    public void dispose();

    public int priority();

    public String getButtonName();

    public Icon getButtonIcon();
}
