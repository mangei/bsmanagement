package cw.boardingschoolmanagement.app.adaptable;

public class ITypedAdapterSupport
	extends IAdapterSupport
		implements ITypedAdaptable {
	
	public <T> T getTypedAdapter(Class<T> adaptableClass) {
		return (T) getAdapter(adaptableClass);
	}
}
