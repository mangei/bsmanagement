package cw.boardingschoolmanagement.app.adaptable;

public class ITypedAdapterSupport
	extends IAdapterSupport{
	
	public <T> T getTypedAdapter(IAdaptable baseObj, Class<T> adaptableClass) {
		return (T) getAdapter(baseObj, adaptableClass);
	}
}
