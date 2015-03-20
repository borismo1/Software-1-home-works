package starfleet;


public class Crewman implements CrewMember{
	
	private String dudes_name;
	
	private int dudes_age;
	
	private int dudes_seniority;

	public Crewman(String name,int age,int seniority){
		this.dudes_name = name;
		this.dudes_age = age;
		this.dudes_seniority = seniority;
	}
	
	@Override
	public String getName() {
		return this.dudes_name;
	}

	@Override
	public int getAge() {
		return this.dudes_age;
	}

	@Override
	public int getYearsInService() {
		return this.dudes_seniority;
	}

	@Override
	public boolean isOfficer() {
		return false;
	}

}
