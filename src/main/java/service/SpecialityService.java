package service;

import java.util.List;

import dao.DaoException;
import domain.Speciality;

public interface SpecialityService {
	List<Speciality> findAll() throws DaoException;
}
