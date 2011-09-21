package cw.boardingschoolmanagement.extention.point;

import javax.persistence.EntityManager;
import javax.swing.Icon;

import com.jgoodies.validation.ValidationResult;

import cw.boardingschoolmanagement.extention.CWIExtention;
import cw.boardingschoolmanagement.gui.ConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.interfaces.Priority;

/**
 *
 * @author Manuel Geier
 */
public interface IConfigurationExtentionPoint
        extends CWIExtention, Priority{

    public void initPresentationModel(ConfigurationPresentationModel configurationModel, EntityManager entityManager);

    public CWPanel getView();

    public void save();

    public ValidationResult validate();

    public void dispose();

    public int priority();

    public String getButtonName();

    public Icon getButtonIcon();
}
