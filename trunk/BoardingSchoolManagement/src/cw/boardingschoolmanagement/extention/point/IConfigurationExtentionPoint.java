package cw.boardingschoolmanagement.extention.point;

import javax.swing.Icon;

import cw.boardingschoolmanagement.gui.CWIEditPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView;

/**
 *
 * @author Manuel Geier
 */
public interface IConfigurationExtentionPoint<TBaseView extends CWView<?>, TBasePresentationModel extends CWIEditPresentationModel>
        extends CWIEditViewExtentionPoint<TBaseView, TBasePresentationModel>{

    public String getButtonName();
    public Icon getButtonIcon();
    
}
