package People;

public class HourlyEmployee extends Employee {
	protected double hourlyRate;
	protected double numberHoursPerWeek;
	
	public HourlyEmployee(String firstName, String lastName, String birthday, int id, String jobTitle, String company, 
			double hourlyRate, double numberHoursPerWeek) {
		setFirstName(firstName);
		setLastName(lastName);
		setBirthdate(birthday);
		setEmployeeID(id);
		setJobTitle(jobTitle);
		setCompany(company);
		this.hourlyRate = hourlyRate;
		this.numberHoursPerWeek = numberHoursPerWeek;	
	}
	
	
	public double getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public double getNumberHoursPerWeek() {
		return numberHoursPerWeek;
	}

	public void setNumberHoursPerWeek(double numberHoursPerWeek) {
		this.numberHoursPerWeek = numberHoursPerWeek;
	}

	@Override
	public double getAnnualSalary() {
		return hourlyRate * numberHoursPerWeek * 52;
	}
}
