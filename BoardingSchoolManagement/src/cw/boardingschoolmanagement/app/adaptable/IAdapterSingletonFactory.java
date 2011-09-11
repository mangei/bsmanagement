package cw.boardingschoolmanagement.app.adaptable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class IAdapterSingletonFactory extends IAdapterFactory {

	private Map<Class,IAdaptable> objectMap;
	
	private IAdapterSingletonFactory(Class adaptableClass) {
		super(adaptableClass);
		objectMap = new HashMap<Class, IAdaptable>();
	}
	
	static public IAdapterSingletonFactory createFactory(Class adaptableClass) {
		return new IAdapterSingletonFactory(adaptableClass);
	}
	
	public IAdaptable createAdaptableObject(IAdaptable basisObject) {
		IAdaptable adaptableObject = null;
		
		adaptableObject = objectMap.get(basisObject.getClass());
		
		if(adaptableObject == null) {
			Constructor constructor = null;
			try {
				constructor = adaptableClass.getConstructor(basisObject.getClass());
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			
			if(constructor != null) {
				try {
					adaptableObject = (IAdaptable) constructor.newInstance(basisObject);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} else {
				try {
					adaptableObject = (IAdaptable) adaptableClass.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			
			if(adaptableObject != null) {
				objectMap.put(basisObject.getClass(), adaptableObject);
			}
		}
		
		return adaptableObject;
	}
}
