package People;

public class CommissionEmployee extends SalariedEmployee {
	protected double salesTotal;
	protected double commissionPercentage;
	
	public CommissionEmployee(String firstName, String lastName, String birthday, int id, String jobTitle, String company, 
			double annualSalary, double salesTotal, double commissionPercentage) {
		super(firstName, lastName, birthday, id, jobTitle, company, annualSalary);
		this.salesTotal = salesTotal;
		this.commissionPercentage = commissionPercentage;
		this.annualSalary = annualSalary + (salesTotal * commissionPercentage);
	}

	public double getSalesTotal() {
		return salesTotal;
	}

	public void setSalesTotal(double salesTotal) {
		this.salesTotal = salesTotal;
	}

	public double getCommissionPercentage() {
		return commissionPercentage;
	}

	public void setCommissionPercentage(double commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
	}
}
