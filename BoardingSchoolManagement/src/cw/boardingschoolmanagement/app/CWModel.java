package cw.boardingschoolmanagement.app;

import com.jgoodies.binding.beans.Model;

import cw.boardingschoolmanagement.app.adaptable.IAdaptable;
import cw.boardingschoolmanagement.app.adaptable.IAdapterManager;
import cw.boardingschoolmanagement.app.adaptable.ITypedAdaptable;

/**
 * 
 * @author manuel.geier
 *
 */
public class CWModel extends Model
	implements ITypedAdaptable {

	@Override
	public IAdaptable getAdapter(Class adaptableClass) {
		return IAdapterManager.getAdapter(this, adaptableClass);
	}
	
	@Override
	public <T> T getTypedAdapter(Class<T> adaptableClass) {
		return (T) getAdapter(adaptableClass);
	}

}