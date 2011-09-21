package cw.boardingschoolmanagement.extention.point;

import java.util.List;

import cw.boardingschoolmanagement.extention.CWIExtention;
import cw.boardingschoolmanagement.gui.model.CWDataField;

/**
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public interface CWIDataModelExtentionPoint extends CWIExtention {

	public Class<?> getBaseClass();
    public List<CWDataField> getFieldList();
	
}
