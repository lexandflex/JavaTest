package domain;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Doctor extends Entity {
    private Speciality speciality;
	private String firstName;
	private String lastName;
	private String patronymic;
	private String birthdayDate;
	private String employmentDate;
	private Integer lotNumber;
	private Double salary;
	
	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getBirthdayDate() { return birthdayDate; }

	public void setBirthdayDate(String birthdayDate) {this.birthdayDate = birthdayDate;}

	public String  getEmploymentDate() {return employmentDate;}

	public void setEmploymentDate(String employmentDate) {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		Date dateOne = null;
		Date dateTwo = new Date();
		try {
			dateOne = format.parse(getBirthdayDate());
			dateTwo = format.parse(format.format(dateTwo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		long difference = dateTwo.getTime() - dateOne.getTime();
		int days = (int) (difference / ( 24 * 60 * 60 * 1000));
		if(days >= 7300){
			this.employmentDate = employmentDate;
		}else{
			System.out.println("Возраст специалиста " + getLastName() +" " + getFirstName() + " менее 20 лет !");
			this.employmentDate = null;
		}

	}

	public Integer getLotNumber() {return lotNumber;}

	public void setLotNumber(Integer lotNumber) {
		if(!this.speciality.getIsNarrowSpecialist()){
			this.lotNumber = lotNumber;
		}else{
			System.out.println(this.lastName + " " + this.firstName + " является узким специалистом, поэтому ему нельзя установить Номер участка!");
			this.lotNumber = null;
		}
		}

	public Double getSalary() {return salary;}

	public void setSalary(Double salary) {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		Date dateOne = null;
		Date dateTwo = new Date();
		try {
			dateOne = format.parse(this.employmentDate);
			dateTwo = format.parse(format.format(dateTwo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		long difference = dateTwo.getTime() - dateOne.getTime();
		int days = (int) (difference / ( 24 * 60 * 60 * 1000));
		if(days >= 1825 && days < 3650){
			this.salary = salary * 1.05;
			getSpeciality().setSalaryExpenses(salary * 1.05);
		}else {
				if( days >= 3650 && days< 7300){
					getSpeciality().setSalaryExpenses(salary * 1.1);
					this.salary = salary * 1.1; }
				else{
					if(days >= 7300 && days <12775){
						getSpeciality().setSalaryExpenses(salary * 1.15);
						this.salary = salary * 1.15;}
				else{
					if(days >= 12775){
						getSpeciality().setSalaryExpenses(salary * 1.2);
						this.salary = salary * 1.2;
				}else{
						getSpeciality().setSalaryExpenses(salary);
						this.salary = salary;
				}
				}
			}
		}
	}

}
