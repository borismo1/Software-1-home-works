package starfleet;

import java.util.Set;

public class TransportShip extends blank_spaceship{

	private int cargo;
	
	private int passenger;
	
	private static int ship_count = 0;
	
	public TransportShip(String name, int commissionYear, float maximalSpeed,Set<CrewMember> crewMembers, int cargoCapacity, int passengerCapacity){
		super(name, commissionYear, maximalSpeed, crewMembers);
		this.cargo = cargoCapacity;
		this.passenger = passengerCapacity;
		ship_count++;
	}
	
	public int getCargoCapacity(){
		return this.cargo;
	}
	
	public int getPassengerCapacity(){
		return this.passenger;
	}
	
	public String toString(){
		String output = super.toString();
		output = output + "\n" + "\t" + "CargoCapacity=" + this.cargo;
		output = output + "\n" + "\t" + "PassengerCapacity=" + this.passenger;
		return output;
	}
	
	@Override
	public int getAnnualMaintenanceCost() {
		return 3000 + 5 * this.cargo + 3*this.passenger;
	}
	
	public int getShipNumber(){
		return ship_count;
	}

}
