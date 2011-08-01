package cw.boardingschoolmanagement.app.adaptable;


public abstract class IAdapterFactory {

	protected Class adaptableClass;
	
	protected IAdapterFactory(Class adaptableClass) {
		this.adaptableClass = adaptableClass;
	}
	
	protected Class getAdaptableClass() {
		return adaptableClass;
	}
	
	public abstract IAdaptable createAdaptableObject(IAdaptable basisObject);
}
