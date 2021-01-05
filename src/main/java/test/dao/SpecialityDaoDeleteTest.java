package test.dao;

import java.util.List;

import test.Utility;
import dao.DaoException;
import dao.SpecialityDao;
import dao.fake.SpecialityDaoFakeImpl;
import domain.Speciality;

public class SpecialityDaoDeleteTest {
	private static void output(SpecialityDao specialityDao) throws DaoException {
		List<Speciality> specialities = specialityDao.readAll();
		System.out.println("Список всех специальностей");
		System.out.println("===================");
		for(Speciality speciality : specialities) {
			System.out.println(Utility.toString(speciality));
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws DaoException {
		SpecialityDao specialityDao = new SpecialityDaoFakeImpl();
		output(specialityDao);
		List<Speciality> specialities = specialityDao.readAll();
		specialityDao.delete(2L);
		
		output(specialityDao);
	}
}
