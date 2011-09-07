package cw.boardingschoolmanagement.app.adaptable;

public class IAdapterSupport
	implements IAdaptable {

	public IAdaptable getAdapter(Class adaptableClass) {
		return IAdapterManager.getAdapter(this, adaptableClass);
	}
}
