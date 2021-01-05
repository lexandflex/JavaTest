package dao.pgsql;

import dao.DaoException;
import dao.UserDao;
import domain.Role;
import domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserPostgreSqlDaoImpl extends BasePostgreSqlDaoImpl<User> implements UserDao {
	@Override
	protected Long createRaw(User user) throws DaoException {
		//INSERT INTO "user"(login, password, role_id) VALUES (:login, :password, :role_id)
		String sql = "INSERT INTO \"user\"(login, password, role_id) VALUES (?, ?, ?)";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getPassword());
			statement.setLong(3, user.getRole().getId());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			resultSet.next();
			Long id = resultSet.getLong(1);
			user.setId(id);
			return id;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { resultSet.close(); } catch(Exception e) {}
			try { statement.close(); } catch(Exception e) {}
		}
	}

	@Override
	protected User readRaw(Long id) throws DaoException {
		//SELECT id, login, password, role_id FROM "user" WHERE id=:id
		String sql = "SELECT login, password, role_id FROM \"user\" WHERE id = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			User user = null;
			if(resultSet.next()) {
				user = new User();
				user.setId(id);
				user.setLogin(resultSet.getString("login"));
				user.setPassword(resultSet.getString("password"));
				
				user.setRole(new Role());
				user.getRole().setId(resultSet.getLong("role_id"));
			}
			return user;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { resultSet.close(); } catch(Exception e) {}
			try { statement.close(); } catch(Exception e) {}
		}
	}

	@Override
	public List<User> readAll() throws DaoException {
		//SELECT id, login, password, role_id FROM "user"
		String sql = "SELECT \"user\".id AS user_id, \"user\".login, \"user\".password, \"role\".id AS role_id, \"role\".name AS role_name "
				+ "FROM \"user\" JOIN \"role\" ON role_id = \"role\".id";
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
			List<User> users = new ArrayList<>();
			User user = null;
			while(resultSet.next()) {
				user = new User();
				user.setId(resultSet.getLong("user_id"));
				user.setLogin(resultSet.getString("login"));
				user.setPassword(resultSet.getString("password"));
				
				user.setRole(new Role());
				user.getRole().setId(resultSet.getLong("role_id"));
				user.getRole().setName(resultSet.getString("role_name"));
				
				users.add(user);
			}
			return users;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { resultSet.close(); } catch(Exception e) {}
			try { statement.close(); } catch(Exception e) {}
		}
	}
	
	@Override
	public User find(String login, String password) throws DaoException {
		//SELECT "user".id AS "user_id", "role".id AS "role_id", "role".name FROM "user" 
		//JOIN "role" ON "role_id" = "role".id WHERE "login" = ? AND "password" = ?;
		String sql = "SELECT \"user\".id AS user_id, \"role\".id AS role_id, \"role\".name AS role_name FROM \"user\" "
        		+ "JOIN \"role\" ON role_id = \"role\".id WHERE login = ? AND password = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            
            User user = null;
            if(resultSet.next()) {
            	user = new User();
				user.setId(resultSet.getLong("user_id"));
				user.setLogin(login);
				user.setPassword(password);
				
				user.setRole(new Role());
				user.getRole().setId(resultSet.getLong("role_id"));
				user.getRole().setName(resultSet.getString("role_name"));
            }
            return user;
        } catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { resultSet.close(); } catch(Exception e) {}
			try { statement.close(); } catch(Exception e) {}
		}
    }

	@Override
	protected void updateRaw(User user) throws DaoException {
		//UPDATE "user" SET login=:login, password=:password, role_id=:role_id WHERE id=:id
		String sql = "UPDATE \"user\" SET login = ?, password = ?, role_id = ? WHERE id = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getPassword());			
			statement.setLong(3, user.getRole().getId());
			statement.setLong(4, user.getId());			
			statement.executeUpdate();
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { statement.close(); } catch(Exception e) {}
		}
	}

	@Override
	protected String getTableName() {
		return "\"user\"";
	}
}
