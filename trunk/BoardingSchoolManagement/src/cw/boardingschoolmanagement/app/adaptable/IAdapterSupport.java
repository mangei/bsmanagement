package cw.boardingschoolmanagement.app.adaptable;

public class IAdapterSupport {

	public IAdaptable getAdapter(IAdaptable baseObj, Class adaptableClass) {
		return IAdapterManager.getAdapter(baseObj, adaptableClass);
	}
}
