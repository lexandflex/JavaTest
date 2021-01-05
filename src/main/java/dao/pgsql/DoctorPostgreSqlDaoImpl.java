package dao.pgsql;

import dao.DoctorDao;
import dao.DaoException;
import domain.Doctor;
import domain.Speciality;

import javax.print.Doc;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DoctorPostgreSqlDaoImpl extends BasePostgreSqlDaoImpl<Doctor> implements DoctorDao {
	@Override
	protected Long createRaw(Doctor doctor) throws DaoException {
		String courseInsertSql = "INSERT INTO \"doctor\"(speciality_id, first_name, last_name, patronymic, birthday_date, employment_date, "
				+ "lot_number, salary) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(courseInsertSql, Statement.RETURN_GENERATED_KEYS);
			statement.setLong(1, doctor.getSpeciality().getId());
			statement.setString(2, doctor.getFirstName());
			statement.setString(3, doctor.getLastName());
			statement.setString(4, doctor.getPatronymic());
			statement.setString(5, doctor.getBirthdayDate());
			statement.setString(6, doctor.getEmploymentDate());
			statement.setInt(7, doctor.getLotNumber());
			statement.setDouble(8, doctor.getSalary());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			resultSet.next();
			Long id = resultSet.getLong(1);
			doctor.setId(id);
			return id;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { resultSet.close(); } catch(Exception e) {}
			try { statement.close(); } catch(Exception e) {}
		}
	}

	@Override
	protected Doctor readRaw(Long id) throws DaoException {
		String sql = "SELECT speciality_id, first_name, last_name, patronymic, birthday_date, employment_date, lot_number, salary "
				+ "FROM \"doctor\" WHERE id = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			Doctor doctor = null;
			if(resultSet.next()) {
				doctor = new Doctor();
				doctor.setId(id);
				
				doctor.setSpeciality(new Speciality());
				doctor.getSpeciality().setId(resultSet.getLong("speciality_id"));
				
				doctor.setFirstName(resultSet.getString("first_name"));
				doctor.setLastName(resultSet.getString("last_name"));
				doctor.setPatronymic(resultSet.getString("patronymic"));
				doctor.setBirthdayDate(resultSet.getString("birthday_date"));
				doctor.setEmploymentDate(resultSet.getString("employment_date"));
				doctor.setLotNumber(resultSet.getInt("lot_number"));
				doctor.setSalary(resultSet.getDouble("salary"));
			}
			return doctor;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { resultSet.close(); } catch(Exception e) {}
			try { statement.close(); } catch(Exception e) {}
		}
	}

	@Override
	public List<Doctor> readAll() throws DaoException {
		String sql = "SELECT speciality_id, first_name, last_name, patronymic, birthday_date, employment_date, lot_number, salary "
				+ "FROM \"doctor\"";
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
			List<Doctor> courses = new ArrayList<>();
			Doctor doctor = null;
			while(resultSet.next()) {
				doctor = new Doctor();
				doctor.setId(resultSet.getLong("id"));
				
				doctor.setSpeciality(new Speciality());
				doctor.getSpeciality().setId(resultSet.getLong("speciality_id"));
				doctor.setFirstName(resultSet.getString("first_name"));
				doctor.setLastName(resultSet.getString("last_name"));
				doctor.setPatronymic(resultSet.getString("patronymic"));
				doctor.setBirthdayDate(resultSet.getString("birthday_date"));
				doctor.setEmploymentDate(resultSet.getString("employment_date"));
				doctor.setLotNumber(resultSet.getInt("lot_number"));
				doctor.setSalary(resultSet.getDouble("salary"));
				courses.add(doctor);
			}
			return courses;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { resultSet.close(); } catch(Exception e) {}
			try { statement.close(); } catch(Exception e) {}
		}
	}
	
	@Override
	public List<Doctor> readBySpeciality(Long specialityID) throws DaoException {
		String sql = "SELECT speciality_id, first_name, last_name, patronymic, birthday_date, employment_date, lot_number, salary "
				+ "FROM \"doctor\" WHERE speciality_id = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setLong(1, specialityID);
			resultSet = statement.executeQuery();
			List<Doctor> doctors = new ArrayList<>();
			Doctor doctor = null;
			while(resultSet.next()) {
				doctor = new Doctor();
				doctor.setId(resultSet.getLong("id"));
				doctor.setSpeciality(new Speciality());
				doctor.getSpeciality().setId(specialityID);
				doctor.setFirstName(resultSet.getString("first_name"));
				doctor.setLastName(resultSet.getString("last_name"));
				doctor.setPatronymic(resultSet.getString("patronymic"));
				doctor.setBirthdayDate(resultSet.getString("birthday_date"));
				doctor.setEmploymentDate(resultSet.getString("employment_date"));
				doctor.setLotNumber(resultSet.getInt("lot_number"));
				doctor.setSalary(resultSet.getDouble("salary"));
				doctors.add(doctor);
			}
			return doctors;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { resultSet.close(); } catch(Exception e) {}
			try { statement.close(); } catch(Exception e) {}
		}
	}

	@Override
	protected void updateRaw(Doctor doctor) throws DaoException {
		String sql = "UPDATE \"doctor\" SET speciality_id = ?, first_name = ?, last_name = ?, patronymic = ?, birthday_date = ?, employment_date = ?, "
				+ "lot_number = ?, salary = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(sql);		
			
			statement.setLong(1, doctor.getSpeciality().getId());
			statement.setString(2, doctor.getFirstName());
			statement.setString(3, doctor.getLastName());
			statement.setString(4, doctor.getPatronymic());
			statement.setString(5, doctor.getBirthdayDate());
			statement.setString(6, doctor.getEmploymentDate());
			statement.setInt(7, doctor.getLotNumber());
			statement.setDouble(8, doctor.getSalary());

			statement.executeUpdate();
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { statement.close(); } catch(Exception e) {}
		}
	}

	@Override
	protected String getTableName() {
		return "\"doctor\"";
	}
}
