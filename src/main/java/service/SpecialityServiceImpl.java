package service;

import java.util.List;

import dao.DaoException;
import dao.SpecialityDao;
import domain.Speciality;

public class SpecialityServiceImpl implements SpecialityService {
	private SpecialityDao specialityDao;

	@Override
	public List<Speciality> findAll() throws DaoException {
		return specialityDao.readAll();
	}

	public void setSpecialityDao(SpecialityDao specialityDao) {
		this.specialityDao = specialityDao;
	}
}
