package starfleet;

import java.util.Set;

public class blank_spaceship implements Spaceship{

	private String name;
	
	private int commissionyear;
	
	private float maximalspeed;
	
	private final int defaultfirepower = 10;
	
	private Set<CrewMember> crew;
	
	
	
	public blank_spaceship(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers) {
		this.name = name;
		this.commissionyear = commissionYear;
		this.maximalspeed = maximalSpeed;
		this.crew = crewMembers;
	}
	
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getCommissionYear() {
		return this.commissionyear;
	}

	@Override
	public float getMaximalSpeed() {
		return this.maximalspeed;
	}

	@Override
	public int getFirePower() {
		return this.defaultfirepower;
	}

	@Override
	public Set<CrewMember> getCrewMembers() {
		return this.crew;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		return 0;
	}
	
	@Override
	public String toString(){
		String output = this.getClass().getSimpleName();
		output = output + "\n" + "\t" + "Name=" + this.name;
		output = output + "\n" + "\t" + "CommissionYear=" +this.commissionyear;
		output = output + "\n" + "\t" + "MaximalSpeed=" + this.getMaximalSpeed();
		output = output + "\n" + "\t" + "FirePower=" + this.getFirePower();
		output = output + "\n" + "\t" + "CrewMembers=" + this.getCrewMembers().size();
		output = output + "\n" + "\t" + "AnnualMaintenanceCost=" + this.getAnnualMaintenanceCost();
		return output;
		}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		blank_spaceship other = (blank_spaceship) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	@Override
	public boolean isFighter() {
		return false;
	}


	@Override
	public int getShipNumber() {
		return 0;
	}

}
