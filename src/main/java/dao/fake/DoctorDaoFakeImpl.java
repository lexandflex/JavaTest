package dao.fake;

import java.sql.Date;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import dao.DoctorDao;
import domain.Doctor;
import domain.Speciality;



public class DoctorDaoFakeImpl implements DoctorDao {
	private static Map<Long, Doctor> doctors = new ConcurrentHashMap<>();
	

	/*
	 * «аполн€ем хранилище в пам€ти тестовыми данными
	 */


	private static final SpecialityDaoFakeImpl spec = new SpecialityDaoFakeImpl();

	static {
		Doctor doctor = new Doctor();
		doctor.setId(1L);
		doctor.setFirstName("Oleg");
		doctor.setLastName("Nazarenko");
		doctor.setPatronymic("Sergeevich");
		doctor.setSpeciality(spec.read(1L));
		doctor.getSpeciality().setIsNarrowSpecialist(true);

		doctor.setBirthdayDate("20.11.1999");
		doctor.setEmploymentDate("12.07.2010");
		doctor.setSalary(850.00);
		doctors.put(doctor.getId(), doctor);

		doctor = new Doctor();
		doctor.setId(2L);
		doctor.setFirstName("Olga");
		doctor.setLastName("Malaschenko");
		doctor.setPatronymic("Anatolievna");
		doctor.setSpeciality(spec.read(2L));
		doctor.getSpeciality().setIsNarrowSpecialist(false);
		doctor.setLotNumber(4);
		doctor.setBirthdayDate("12.04.1985");
		doctor.setEmploymentDate("23.09.2015");
		doctor.setSalary(1350.00);
		doctors.put(doctor.getId(), doctor);
	}

	@Override
	public Long create(Doctor doctor) {
		Long id = 0L;
		if(!doctors.isEmpty()) {
			id = Collections.max(doctors.keySet());
		}
		doctor.setId(++id);
		doctors.put(id, doctor);
		return id;
	}

	@Override
	public Doctor read(Long id) {
		return doctors.get(id);
	}

	@Override
	public void update(Doctor doctor) {
		doctors.put(doctor.getId(), doctor);
	}

	@Override
	public void delete(Long id) {
		doctors.remove(id);
	}

	@Override
	public List<Doctor> readBySpeciality(Long specialityId) {
		List<Doctor> result = new ArrayList<>();
		for(Doctor doctor : doctors.values()) {
			if((specialityId == null && doctor.getSpeciality() == null) || (specialityId != null && doctor.getSpeciality() != null && specialityId.equals(doctor.getSpeciality().getId()))) {
				result.add(doctor);
			}
		}
		return result;
	}
	
	@Override
	public List<Doctor> readAll() {
		return new ArrayList<>(doctors.values());
	}
}