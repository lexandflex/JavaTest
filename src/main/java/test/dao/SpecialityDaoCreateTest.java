package test.dao;

import java.util.List;

import test.Utility;
import dao.DaoException;
import dao.SpecialityDao;
import dao.fake.SpecialityDaoFakeImpl;
import domain.Speciality;

public class SpecialityDaoCreateTest {
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
		
		Speciality speciality = new Speciality();
		speciality.setId((long) (specialities.size() + 1));
		speciality.setName("therapist");
		speciality.setDoctorsCount(1);
		speciality.setIsNarrowSpecialist(false);
		speciality.setSalaryExpenses(55689.22);
		speciality.setWageRate(0.2);

		specialityDao.create(speciality);
		
		output(specialityDao);
	}
}