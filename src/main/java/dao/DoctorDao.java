package dao;

import java.util.List;

import domain.Doctor;

public interface DoctorDao extends Dao<Doctor> {
	List<Doctor> readAll() throws DaoException;
	List<Doctor> readBySpeciality(Long SpecialityId) throws DaoException;
}
