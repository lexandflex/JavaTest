package dao;

import java.util.List;

import domain.Speciality;

public interface SpecialityDao extends Dao<Speciality> {
	List<Speciality> readAll() throws DaoException;
}
