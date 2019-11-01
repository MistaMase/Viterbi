package People;

public abstract class Person {
	protected String firstName;
	protected String lastName;
	protected String birthday;
	
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

	public String getBirthdate() {
		return birthday;
	}

	public void setBirthdate(String birthday) {
		this.birthday = birthday;
	}
}
