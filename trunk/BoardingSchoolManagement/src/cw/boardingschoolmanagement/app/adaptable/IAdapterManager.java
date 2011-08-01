package cw.boardingschoolmanagement.app.adaptable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IAdapterManager {

	private static Map<Class,List<IAdapterFactory>> adaptableMap;
	
	static {
		adaptableMap = new HashMap<Class, List<IAdapterFactory>>();
	}
	
	public static void registerAdapter(Class basisClass, IAdapterFactory factory) {
		
		if(getAdapterFactory(basisClass, factory.getAdaptableClass()) == null) {
			List<IAdapterFactory> factories = adaptableMap.get(basisClass);
			if(factories == null) {
				factories = new ArrayList<IAdapterFactory>();
				adaptableMap.put(basisClass, factories);
			}
			factories.add(factory);
		}
		
	}
	
	public static IAdaptable getAdapter(IAdaptable basisObject, Class adaptableClass) {
		IAdaptable adaptableObject = null;
		
		IAdapterFactory factory = getAdapterFactory(basisObject.getClass(), adaptableClass);
		if(factory != null) {
			adaptableObject = factory.createAdaptableObject(basisObject);
		}
		
		return adaptableObject;
	}
	
	protected static IAdapterFactory getAdapterFactory(Class basisClass, Class adaptableClass) {
		IAdapterFactory iAdapterFactory = null;
		
		List<IAdapterFactory> factories = adaptableMap.get(basisClass);
		if(factories != null) {
			for(IAdapterFactory factory: factories) {
				if(factory.getAdaptableClass().equals(adaptableClass)) {
					iAdapterFactory = factory;
					break;
				}
			}
		}
		
		return iAdapterFactory;
	}
	
}
