package starfleet;
import java.util.Set;


public interface Spaceship {

	public String getName();
	
	public int getCommissionYear();
	
	public float getMaximalSpeed();
	
	public int getFirePower();
	
	public Set<CrewMember> getCrewMembers();
	
	public int getAnnualMaintenanceCost();
	
	public boolean isFighter();
	
	public int getShipNumber();
}
