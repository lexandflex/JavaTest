package dao.fake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dao.SpecialityDao;
import domain.Speciality;

public class SpecialityDaoFakeImpl implements SpecialityDao {
	public static Map<Long, Speciality> specialities = new ConcurrentHashMap<>();

	/*
	 * «аполн€ем хранилище в пам€ти тестовыми данными
	 */
	static {
		Speciality speciality = new Speciality();
		speciality.setId(1L);
		speciality.setName("dentist");
		speciality.setDoctorsCount(5);
		speciality.setIsNarrowSpecialist(true);
		speciality.setWageRate(0.3);
		specialities.put(speciality.getId(), speciality);

		speciality = new Speciality();
		speciality.setId(2L);
		speciality.setName("surgeon");
		speciality.setDoctorsCount(8);
		speciality.setIsNarrowSpecialist(false);
		speciality.setWageRate(0.4);
		specialities.put(speciality.getId(), speciality);
	}

	@Override
	public Long create(Speciality speciality) {
		Long id = 0L;
		if(!specialities.isEmpty()) {
			id = Collections.max(specialities.keySet());
		}
		speciality.setId(++id);
		specialities.put(id, speciality);
		return id;
	}

	@Override
	public Speciality read(Long id) {
		return specialities.get(id);
	}

	@Override
	public void update(Speciality speciality) {
		specialities.put(speciality.getId(), speciality);
	}

	@Override
	public void delete(Long id) {
		specialities.remove(id);
	}

	@Override
	public List<Speciality> readAll() {
		return new ArrayList<>(specialities.values());
	}
}