package domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Speciality extends Entity {
	private String name;
	private Boolean isNarrowSpecialist;
	private Integer doctorsCount;
    private Double wageRate;
    private Double salaryExpenses =0.0;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsNarrowSpecialist() {
		return isNarrowSpecialist;
	}

	public void setIsNarrowSpecialist(Boolean isNarrowSpecialist) {
		this.isNarrowSpecialist = isNarrowSpecialist;
	}

	public Integer getDoctorsCount() {
		return doctorsCount;
	}

	public void setDoctorsCount(Integer doctorsCount) {
		this.doctorsCount = doctorsCount;
	}

	public Double getWageRate() {
		return wageRate;
	}

	public void setWageRate(Double wageRate) {
		this.wageRate = wageRate;
	}

	public Double getSalaryExpenses() {
		return salaryExpenses;
	}

	public void setSalaryExpenses(Double salaryExpenses) {
		this.salaryExpenses += salaryExpenses;
	}

}
