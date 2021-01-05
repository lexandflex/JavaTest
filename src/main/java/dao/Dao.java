package dao;

import domain.Entity;

public interface Dao<E extends Entity> {
	Long create(E entity) throws DaoException;

	E read(Long id) throws DaoException;

	void update(E entity) throws DaoException;

	void delete(Long id) throws DaoException;
}
