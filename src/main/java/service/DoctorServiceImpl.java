package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.SpecialityDao;
import dao.DoctorDao;
import dao.DaoException;
import domain.Speciality;
import domain.Doctor;

public class DoctorServiceImpl implements DoctorService {
	private SpecialityDao specialityDao;
	private DoctorDao doctorDao;

	@Override
	public List<Doctor> findBySpeciality(Long specialityId) throws DaoException {
		List<Doctor> doctors = doctorDao.readBySpeciality(specialityId);
		Map<Long, Speciality> specialities = new HashMap<>();
		for(Doctor doctor : doctors) {
			Speciality speciality = doctor.getSpeciality();
			if(speciality != null) {
				Long id = speciality.getId();
				speciality = specialities.get(id);
				if(speciality == null) {
					speciality = specialityDao.read(id);
					specialities.put(id, speciality);
				}
				doctor.setSpeciality(speciality);
			}
		}
		return doctors;
	}
	
	@Override
	public List<Doctor> findAll() throws DaoException {
		return doctorDao.readAll();
	}

	public void setSpecialityDao(SpecialityDao specialityDao) {
		this.specialityDao = specialityDao;
	}

	public void setDoctorDao(DoctorDao doctorDao) {
		this.doctorDao = doctorDao;
	}
}
