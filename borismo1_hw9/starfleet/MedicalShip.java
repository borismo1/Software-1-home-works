package starfleet;

import java.util.Set;


public class MedicalShip extends blank_spaceship{
	
	private int med;
	
	private static int ship_count = 0;
	
	public MedicalShip(String name, int commissionYear, float maximalSpeed,Set<CrewMember> crewMembers, int numberOfMedicalClinics){
		super(name, commissionYear, maximalSpeed, crewMembers);
		this.med = numberOfMedicalClinics;
		ship_count++;
	}
	
	public int getNumberOfMedicalClinics(){
		return this.med;
	}
	
	@Override
	public int getAnnualMaintenanceCost() {
		return 8000 + this.med*3000;
	}
	
	public String toString(){
		String output = super.toString();
		output = output + "\n" + "\t" + "NumberOfMedicalClinics=" + this.med;
		return output;
	}
	
	public int getShipNumber(){
		return ship_count;
	}
}
