package test.dao;

import java.util.List;

import test.Utility;
import dao.DaoException;
import dao.SpecialityDao;
import dao.fake.SpecialityDaoFakeImpl;
import domain.Speciality;

public class SpecialityDaoReadAllTest {
	public static void main(String[] args) throws DaoException {
		SpecialityDao specialityDao = new SpecialityDaoFakeImpl();
		List<Speciality> specialities = specialityDao.readAll();
		System.out.println("Список всех специальностей");
		System.out.println("===================");
		for(Speciality speciality : specialities) {
			System.out.println(Utility.toString(speciality));
		}
	}
}