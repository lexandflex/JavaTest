package test;

import domain.Doctor;
import domain.Speciality;

public class Utility {
	public static String toString(Speciality speciality) {
		return String.format("[%d] %s %d", speciality.getId(), speciality.getName(), speciality.getDoctorsCount());
	}

	public static String toString(Doctor doctor) {
		return String.format("[%d]  %s. Фамилия - %s", doctor.getId(), !doctor.getSpeciality().getIsNarrowSpecialist()? "Специальность - "+ toString(doctor.getSpeciality())+ ". " + "Номер участка - " + doctor.getLotNumber()   : "Специальность - "+ toString(doctor.getSpeciality()) , doctor.getLastName());
	}
}