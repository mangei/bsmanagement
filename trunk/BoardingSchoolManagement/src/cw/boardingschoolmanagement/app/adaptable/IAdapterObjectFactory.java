package cw.boardingschoolmanagement.app.adaptable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class IAdapterObjectFactory extends IAdapterFactory {

	private Map<IAdaptable,IAdaptable> objectMap;
	
	private IAdapterObjectFactory(Class adaptableClass) {
		super(adaptableClass);
		objectMap = new HashMap<IAdaptable, IAdaptable>();
	}
	
	static public IAdapterObjectFactory createFactory(Class adaptableClass) {
		return new IAdapterObjectFactory(adaptableClass);
	}
	
	public IAdaptable createAdaptableObject(IAdaptable basisObject) {
		IAdaptable adaptableObject = null;
		
		adaptableObject = objectMap.get(basisObject);
		
		if(adaptableObject == null) {
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
			
			if(adaptableObject != null) {
				objectMap.put(basisObject, adaptableObject);
			}
		}
		
		return adaptableObject;
	}
}
