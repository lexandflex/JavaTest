package ioc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DIContainer {
	private static Map<Class<?>, Map<Class<?>, Method>> dependencyInjectionMap = new HashMap<>();

	private IoCContainer ioc;

	public DIContainer(IoCContainer ioc) {
		this.ioc = ioc;
	}

	public void injectDependencies(Object object) throws IoCException {
		Map<Class<?>, Method> dependencies = dependencyInjectionMap.get(object.getClass());
		if(dependencies != null) {
			try {
				for(Map.Entry<Class<?>, Method> entry : dependencies.entrySet()) {
					Class<?> dependency = entry.getKey();
					Method injector = entry.getValue();
					injector.invoke(object, ioc.get(dependency));
				}
			} catch(IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				throw new IoCException(e);
			}
		}
	}

	public static void registerClass(String implemetation, Map<String, String> dependencies) throws IoCException {
		try {
			Class<?> actualImplemetation = Class.forName(implemetation);
			Map<Class<?>, Method> actualDependencies = new HashMap<>();
			dependencyInjectionMap.put(actualImplemetation, actualDependencies);
			for(Map.Entry<String, String> entry : dependencies.entrySet()) {
				Class<?> dependency = Class.forName(entry.getKey());
				String injectorName = entry.getValue();
				Method injector = actualImplemetation.getMethod(injectorName, dependency);
				actualDependencies.put(dependency, injector);
			}
		} catch(ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			throw new IoCException(e);
		}
	}
}