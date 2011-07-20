package cw.boardingschoolmanagement.extention.point;

import cw.boardingschoolmanagement.interfaces.Extention;
import cw.boardingschoolmanagement.gui.ConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.interfaces.Priority;
import java.util.List;
import javax.swing.Icon;

/**
 *
 * @author Manuel Geier
 */
public interface ConfigurationExtentionPoint
        extends Extention, Priority{

    public void initPresentationModel(ConfigurationPresentationModel configurationModel);

    public CWPanel getView();

    public void save();

    public List<String> validate();

    public void dispose();

    public int priority();

    public String getButtonName();

    public Icon getButtonIcon();
}
