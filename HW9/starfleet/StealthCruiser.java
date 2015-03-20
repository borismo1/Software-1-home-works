package starfleet;

import java.util.List;
import java.util.Set;

public class StealthCruiser extends Fighter{
	
	private static int ship_count = 0;
	
	public StealthCruiser(String name, int commissionYear, float maximalSpeed,Set<CrewMember> crewMembers, List<Weapon> weaponArray) {
		super(name, commissionYear, maximalSpeed, crewMembers, weaponArray);
		ship_count++;
	}
	
	public StealthCruiser(String name, int commissionYear, float maximalSpeed,Set<CrewMember> crewMembers){
		super(name, commissionYear, maximalSpeed, crewMembers, null);
		ship_count++;
	}
	
	public int getAnnualMaintenanceCost(){
		int output = super.getAnnualMaintenanceCost();
		output = output + 100 * getShipNumber();
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
