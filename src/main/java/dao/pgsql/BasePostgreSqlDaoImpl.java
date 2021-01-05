package dao.pgsql;

import dao.Dao;
import dao.DaoException;
import domain.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

abstract public class BasePostgreSqlDaoImpl<T extends Entity> implements Dao<T> {
	private Connection connection;
	private Map<Long, T> cache = new HashMap<>();

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public final T read(Long id) throws DaoException {
		T entity = cache.get(id);
		if(entity == null) {
			entity = readRaw(id);
			cache.put(id, entity);
		}
		return entity;
	}

	@Override
	public final Long create(T entity) throws DaoException {
		Long id = createRaw(entity);
		cache.clear();
		return id;
	}

	@Override
	public final void update(T entity) throws DaoException {
		updateRaw(entity);
		cache.clear();
	}

	@Override
	public final void delete(Long id) throws DaoException {
		String sql = String.format("DELETE FROM %s WHERE id = ?", getTableName());
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			statement.executeUpdate();
			cache.clear();
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { statement.close(); } catch(Exception e) {}
		}
	}

	abstract protected String getTableName();

	abstract protected T readRaw(Long id) throws DaoException;

	abstract protected Long createRaw(T entity) throws DaoException;

	abstract protected void updateRaw(T entity) throws DaoException;
}
