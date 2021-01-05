package test.dao;

import java.util.List;

import test.Utility;
import dao.DoctorDao;
import dao.DaoException;
import dao.fake.DoctorDaoFakeImpl;
import domain.Doctor;

public class DoctorDaoReadBySpecialityTest {
	private static void output(DoctorDao doctorDao, Long specialityId) throws DaoException {
		List<Doctor> doctors = doctorDao.readBySpeciality(specialityId);
		System.out.printf("Список всех докторов специальности с идентификатором [%d]\n", specialityId);
		System.out.println("================================================");
		for(Doctor doctor : doctors) {
			System.out.println(Utility.toString(doctor));

		}
		System.out.println();
	}

	public static void main(String[] args) throws DaoException {
		DoctorDao doctorDao = new DoctorDaoFakeImpl();
		output(doctorDao, 1L);
		output(doctorDao, 2L);
	}
}
