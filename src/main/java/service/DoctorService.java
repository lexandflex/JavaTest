package service;

import java.util.List;

import dao.DaoException;
import domain.Doctor;

public interface DoctorService {
	List<Doctor> findBySpeciality(Long specialityId) throws DaoException;
	List<Doctor> findAll() throws DaoException;
}
