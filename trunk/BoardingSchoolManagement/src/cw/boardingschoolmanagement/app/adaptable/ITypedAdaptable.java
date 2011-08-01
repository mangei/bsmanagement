package cw.boardingschoolmanagement.app.adaptable;

public interface ITypedAdaptable extends IAdaptable {

	public <T> T getTypedAdapter(Class<T> adaptableClass);
	
}
