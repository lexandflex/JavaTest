package dao;

import domain.Role;

import java.util.List;

public interface RoleDao extends Dao<Role> {
	List<Role> readAll() throws DaoException;
}
