package starfleet;

public class Weapon {

	private String name;
	
	private int firepower;
	
	private int maintenance;
	
	public Weapon(String name, int firePower, int annualMaintenanceCost){
		this.name = name;
		this.firepower = firePower;
		this.maintenance = annualMaintenanceCost;
	}
	
	public String getWeaponName(){
		return this.name;
	}
	
	public int getWeaponPower(){
		return this.firepower;
	}
	
	public int getWeaponMaintenance(){
		return this.maintenance;
	}
	
	public String toString(){
			String output = this.getClass().getSimpleName();
			output = output + " [name=" + this.name;
			output = output + ", firePower=" +this.getWeaponPower();
			output = output +  ", annualMaintenanceCost=" + this.maintenance + "]";
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
		Weapon other = (Weapon) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
}
