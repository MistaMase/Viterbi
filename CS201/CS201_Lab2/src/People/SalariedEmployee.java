package People;

public class SalariedEmployee extends Employee {
	protected double annualSalary;

	public SalariedEmployee(String firstName, String lastName, String birthday, int id, String jobTitle, String company, double annualSalary){
		setFirstName(firstName);
		setLastName(lastName);
		setBirthdate(birthday);
		setEmployeeID(id);
		setJobTitle(jobTitle);
		setCompany(company);
		this.annualSalary = annualSalary;
		
	}
	
	public void setAnnualSalary(double annualSalary) {
		this.annualSalary = annualSalary;
	}

	@Override
	public double getAnnualSalary() {
		return annualSalary;
	}

}
