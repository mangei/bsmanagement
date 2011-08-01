package cw.boardingschoolmanagement.app.adaptable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class IAdapterRequestFactory extends IAdapterFactory {
	
	private IAdapterRequestFactory(Class adaptableClass) {
		super(adaptableClass);
	}
	
	static public IAdapterRequestFactory createFactory(Class adaptableClass) {
		return new IAdapterRequestFactory(adaptableClass);
	}
	
	public IAdaptable createAdaptableObject(IAdaptable basisObject) {
		IAdaptable adaptableObject = null;
		
		Constructor constructor = null;
		try {
			constructor = adaptableClass.getConstructor(basisObject.getClass());
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		}
		
		if(constructor != null) {
			try {
				adaptableObject = (IAdaptable) constructor.newInstance(basisObject);
			} catch (IllegalArgumentException e) {
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		} else {
			try {
				adaptableObject = (IAdaptable) adaptableClass.newInstance();
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		}
		
		return adaptableObject;
	}
}
