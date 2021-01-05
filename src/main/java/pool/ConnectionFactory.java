package pool;

import ioc.Factory;
import ioc.IoCException;

import java.sql.Connection;

public class ConnectionFactory implements Factory<Connection> {
	@Override
	public Connection get(String key) throws IoCException {
		try {
			return ConnectionPool.getInstance().getConnection();
		} catch(PoolException e) {
			throw new IoCException(e);
		}
	}
}
