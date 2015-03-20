package starfleet;

import java.util.List;
import java.util.Set;

public class Fighter extends blank_spaceship {
	
	static int ship_count = 0;

	private List<Weapon> weapon_array;
	
	public Fighter(String name, int commissionYear, float maximalSpeed,Set<CrewMember> crewMembers, List<Weapon> weaponArray){
		super(name, commissionYear, maximalSpeed, crewMembers);
		this.weapon_array = weaponArray;
		if(this.getClass().getSimpleName().equals("Fighter")){
			ship_count++;
		}
	}
	
	public List<Weapon> getWeaponArray(){
		return this.weapon_array;
	}
	
	@Override
	public int getFirePower() {
		if(this.weapon_array == null){
			return 10;
		}
		else{
			int output = 10;
			for(Weapon i:this.weapon_array){
				output = output + i.getWeaponPower();
			}
			return output;
		}
	}
	
	@Override
	public int getAnnualMaintenanceCost() {
		if(this.weapon_array == null){
			return (int) (2500 + this.getMaximalSpeed()*1000);
		}
		else{
			int output = (int) (2500 + this.getMaximalSpeed()*1000);
			for(Weapon i:this.weapon_array){
				output = output + i.getWeaponMaintenance();
			}
		return output;
		}
	}
	
	@Override
	public boolean isFighter() {
		return true;
	}
	
	public int getShipNumber(){
		return ship_count;
	}
	
	public String toString(){
		String output = super.toString();
		if(this.getFirePower() != 10){
			output = output + "\n" + "\t" + "WeaponArray=" + this.weapon_array;
		}
		return output;
	}
}
