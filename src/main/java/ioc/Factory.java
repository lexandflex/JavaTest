package ioc;

public interface Factory<T> {
	T get(String key) throws IoCException;
}