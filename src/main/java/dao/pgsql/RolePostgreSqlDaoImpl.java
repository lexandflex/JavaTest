package dao.pgsql;

import dao.DaoException;
import dao.RoleDao;
import domain.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RolePostgreSqlDaoImpl extends BasePostgreSqlDaoImpl<Role> implements RoleDao {
	@Override
	protected Long createRaw(Role role) throws DaoException {
		//INSERT INTO "role"(name) VALUES (:name)
		String sql = "INSERT INTO \"role\"(name) VALUES (?)";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, role.getName());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			resultSet.next();
			Long id = resultSet.getLong(1);
			role.setId(id);
			return id;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { resultSet.close(); } catch(Exception e) {}
			try { statement.close(); } catch(Exception e) {}
		}
	}

	@Override
	protected Role readRaw(Long id) throws DaoException {
		//SELECT name FROM "role" WHERE id=:id
		String sql = "SELECT name FROM \"role\" WHERE id = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			Role role = null;
			if(resultSet.next()) {
				role = new Role();
				role.setId(id);
				role.setName(resultSet.getString("name"));
			}
			return role;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { resultSet.close(); } catch(Exception e) {}
			try { statement.close(); } catch(Exception e) {}
		}
	}

	@Override
	public List<Role> readAll() throws DaoException {
		//SELECT id, name FROM "role"
		String sql = "SELECT id, name FROM \"role\"";
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
			List<Role> roles = new ArrayList<>();
			Role role = null;
			while(resultSet.next()) {
				role = new Role();
				role.setId(resultSet.getLong("id"));
				role.setName(resultSet.getString("name"));
			
				roles.add(role);
			}
			return roles;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { resultSet.close(); } catch(Exception e) {}
			try { statement.close(); } catch(Exception e) {}
		}
	}

	@Override
	protected void updateRaw(Role role) throws DaoException {
		//UPDATE "role" SET name=:name WHERE id=:id
		String sql = "UPDATE \"role\" SET name = ? WHERE id = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setString(1, role.getName());
			statement.setLong(2, role.getId());			
			statement.executeUpdate();
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { statement.close(); } catch(Exception e) {}
		}
	}

	@Override
	protected String getTableName() {
		return "\"role\"";
	}
}