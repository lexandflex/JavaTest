package dao.pgsql;

import dao.DaoException;
import dao.SpecialityDao;
import domain.Speciality;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SpecialityPostgreSqlDaoImpl extends BasePostgreSqlDaoImpl<Speciality> implements SpecialityDao {
	@Override
	protected Long createRaw(Speciality speciality) throws DaoException {
		String sql = "INSERT INTO \"speciality\"(name, is_narrow_specialist, doctors_count, "
				+ "wage_rate, salary_expenses) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, speciality.getName());
			statement.setBoolean(2, speciality.getIsNarrowSpecialist());
			statement.setInt(3, speciality.getDoctorsCount());
			statement.setDouble(4, speciality.getWageRate());
			statement.setDouble(5, speciality.getSalaryExpenses());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			resultSet.next();
			Long id = resultSet.getLong(1);
			speciality.setId(id);
			return id;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { resultSet.close(); } catch(Exception e) {}
			try { statement.close(); } catch(Exception e) {}
		}
	}

	@Override
	protected Speciality readRaw(Long id) throws DaoException {
		String sql = "SELECT name, is_narrow_specialist, doctors_count, wage_rate, salary_expenses "
				+ "FROM \"speciality\" WHERE id = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			Speciality speciality = null;
			if(resultSet.next()) {
				speciality = new Speciality();
				speciality.setId(id);
				speciality.setName(resultSet.getString("name"));
				speciality.setIsNarrowSpecialist(resultSet.getBoolean("is_narrow_specialist"));
				speciality.setDoctorsCount(resultSet.getInt("doctors_count"));
				speciality.setWageRate(resultSet.getDouble("wage_rate"));
				speciality.setSalaryExpenses(resultSet.getDouble("salary_expenses"));

			}
			return speciality;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { resultSet.close(); } catch(Exception e) {}
			try { statement.close(); } catch(Exception e) {}
		}
	}

	@Override
	public List<Speciality> readAll() throws DaoException {
		String sql = "SELECT name, is_narrow_specialist, doctors_count, wage_rate, salary_expenses "
				+ "FROM \"speciality\"";
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
			List<Speciality> specialities = new ArrayList<>();
			Speciality speciality = null;
			while(resultSet.next()) {
				speciality = new Speciality();
				speciality.setId(resultSet.getLong("id"));
				speciality.setName(resultSet.getString("name"));
				speciality.setIsNarrowSpecialist(resultSet.getBoolean("is_narrow_specialist"));
				speciality.setDoctorsCount(resultSet.getInt("doctors_count"));
				speciality.setWageRate(resultSet.getDouble("wage_rate"));
				speciality.setSalaryExpenses(resultSet.getDouble("salary_expenses"));
				specialities.add(speciality);
			}
			return specialities;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { resultSet.close(); } catch(Exception e) {}
			try { statement.close(); } catch(Exception e) {}
		}
	}

	@Override
	protected void updateRaw(Speciality speciality) throws DaoException {
		String sql = "UPDATE \"speciality\" SET name = ?, is_narrow_specialist = ?, doctors_count = ?, wage_rate = ?"
				+ "salary_expenses = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);
			
			statement.setString(1, speciality.getName());
			statement.setBoolean(2, speciality.getIsNarrowSpecialist());
			statement.setInt(3, speciality.getDoctorsCount());
			statement.setDouble(4, speciality.getWageRate());
			statement.setDouble(5, speciality.getSalaryExpenses());
			statement.setLong(9, speciality.getId());
			statement.executeUpdate();
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { statement.close(); } catch(Exception e) {}
		}
	}

	@Override
	protected String getTableName() {
		return "\"speciality\"";
	}
}
