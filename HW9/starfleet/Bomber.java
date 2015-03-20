package starfleet;

import java.util.List;
import java.util.Set;

public class Bomber extends Fighter{

	
	private int tech;
	
	private static int ship_count = 0;
	
	public Bomber(String name, int commissionYear, float maximalSpeed,Set<CrewMember> crewMembers, List<Weapon> weaponArray, int numberOfTechnicians){
		super(name, commissionYear, maximalSpeed, crewMembers, weaponArray);
		this.tech =numberOfTechnicians;
		ship_count++;
	}
	
	public int getNumberOfTechnicians(){
		return this.tech;
	}
	
	@Override
	public int getAnnualMaintenanceCost(){
		int output = 0;
		for(Weapon i : this.getWeaponArray()){
			output = output + i.getWeaponMaintenance();
		}
		output = (int) (output * (0.1 * this.tech) + 6000);
		return  output;
	}
	
	@Override
	public String toString(){
		String output = super.toString();
		output = output + "\n" + "\t" + "NumberOfTechnicians=" +this.tech;
		return output;
	}
	
	@Override
	public boolean isFighter() {
		return true;
	}
	
	public int getShipNumber(){
		return ship_count;
	}
}
