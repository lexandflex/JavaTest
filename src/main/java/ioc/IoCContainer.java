package ioc;

import java.util.HashMap;
import java.util.Map;

public class IoCContainer implements AutoCloseable {
	private static Map<Class<?>, Class<?>> dependencyInversionMap = new HashMap<>();
	private static Map<Class<?>, Factory<?>> factories = new HashMap<>();

	private Map<Class<?>, Object> cache = new HashMap<>();
	private DIContainer dic = new DIContainer(this);

	public <T> T get(Class<T> key) throws IoCException {
		return get(key, null);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> key, String additionalKey) throws IoCException {
		T object = (T)cache.get(key);
		if(object == null) {
			try {
				Class<?> value = dependencyInversionMap.get(key);
				if(value != null) {
					object = (T)value.newInstance();
				} else {
					Factory<?> factory = factories.get(key);
					if(factory != null) {
						object = (T)factory.get(additionalKey);
					}
				}
				if(object != null) {
					cache.put(key, object);
					dic.injectDependencies(object);
				}
			} catch(InstantiationException | IllegalAccessException e) {
				throw new IoCException(e);
			}
		}
		return object;
	}

	public static void registerClass(String abstraction, String implemetation) throws IoCException {
		try {
			Class<?> actualImplemetation = Class.forName(implemetation);
			Class<?> actualAbstraction = Class.forName(abstraction);
			dependencyInversionMap.put(actualAbstraction, actualImplemetation);
		} catch(ClassNotFoundException e) {
			throw new IoCException(e);
		}
	}

	public static void registerFactory(String abstraction, String factory) throws IoCException {
		try {
			Class<?> actualAbstraction = Class.forName(abstraction);
			Class<?> actualFactory = Class.forName(factory);
			factories.put(actualAbstraction, (Factory<?>)actualFactory.newInstance());
		} catch(ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new IoCException(e);
		}
	}

	@Override
	public void close() {
		for(Object object : cache.values()) {
			if(object instanceof AutoCloseable) {
				try { ((AutoCloseable)object).close(); } catch(Exception e) {}
			}
		}
	}
}