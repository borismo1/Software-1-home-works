package starfleet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;

public class StarfleetManager {

	/**
	 * Returns a list containing string representation of all fleet ships, sorted in ascending order by
	 * the ship names.
	 */
	public static List<String> getShipDescriptionsSortedByShipName (List<Spaceship> fleet) {
		Map<String,String> sortedmap = new TreeMap<String, String>();
		for(Spaceship i:fleet){
			sortedmap.put(i.getName(), i.toString());
		}
		return new ArrayList<String>(sortedmap.values());
	}

	/**
	 * Returns a map containing ship type names as keys (the class name) and the number of instances created for each type as values
	 */
	public static Map<String, Integer> getInstanceNumberPerClass (List<Spaceship> fleet) {
		Map<String,Integer> output = new HashMap<>();
		for(Spaceship i:fleet){
			output.put(i.getClass().getSimpleName(), i.getShipNumber());
		}
		return output;
	}


	/**
	 * Returns the total annual maintenance cost of the fleet (which is the sum of maintenance costs of all the fleet's ships)
	 */
	public static int getTotalMaintenanceCost (List<Spaceship> fleet) {
		int output =0;
		for(Spaceship i:fleet){
			output = output + i.getAnnualMaintenanceCost();
		}
		return output;
	}

	/**
	 * Returns the total fire power of the fleet (which is the sum of fire power of all the fleet's ships)
	 */
	public static int getTotalFleetFirePower (List<Spaceship> fleet) {
		int output =0;
		for(Spaceship i:fleet){
			output = output + i.getFirePower();
		}
		return output;
	}

	/**
	 * Returns a set containing the names of all the fleet's weapons installed on any ship, without repetitions
	 */
	public static Set<String> getFleetWeaponNames (List<Spaceship> fleet) {
		Set<String> output = new HashSet<>();
		for(Spaceship i:fleet){
			if(i.isFighter() && i.getFirePower() != 10){
				for(Weapon j:((Fighter) i).getWeaponArray()){
					output.add(j.getWeaponName());
				}
			}
		}
		return output;
	}

	/*
	 * Returns the total number of crew-members serving on board of the given fleet's ships.
	 */
	public static int getTotalNumberOfFleetCrewMembers(List<Spaceship> fleet) {
		int output =0;
		for(Spaceship i:fleet){
			output = output + i.getCrewMembers().size();
		}
		return output;
	}

	/*
	 * Returns the average age of all officers serving on board of the given fleet's ships.
	 */
	public static float getAverageAgeOfFleetOfficers(List<Spaceship> fleet) {
		float output = 0;
		int counter =0;
		for(Spaceship i:fleet){
			for(CrewMember j:i.getCrewMembers()){
				if(j.isOfficer()){
					output = output + j.getAge();
					counter++;
				}
			}
		}
		output = output / counter;
		return output;
	}

	/*
	 * Returns a map mapping the highest ranking officer on each ship (as keys), to his ship (as values).
	 */
	public static Map<Officer, Spaceship> getHighestRankingOfficerPerShip(List<Spaceship> fleet) {
		Map<Officer,Spaceship> output = new HashMap<>();
		for(Spaceship i:fleet){
			output.put(commandingOfficer(getOfficers(i.getCrewMembers())), i);
		}
		return output;
	}
	
	public static Set<Officer> getOfficers(Set<CrewMember> crew){
		Set<Officer> output = new HashSet<>();
		for(CrewMember i:crew){
			if(i.isOfficer()){
				output.add((Officer) i);
			}
		}
		return output;
	}
	
	public static Officer commandingOfficer(Set<Officer> crew){
		OfficerRank highestrank = OfficerRank.Ensign;
		Officer output = null;
		for(Officer i:crew){
			if(i.getRank().ordinal() >= highestrank.ordinal()){
				output = i;
				highestrank = i.getRank();
			}
		}
		return output;
	}

	/*
	 * Returns a List of entries representing ranks and their occurrences.
	 * Each entry represents a pair composed of an officer rank, and the number of its occurrences among starfleet personnel.
	 * The returned list is sorted descendingly based on the number of occurrences.
	 */
	public static List<Map.Entry<OfficerRank, Integer>> getOfficerRanksSortedByPopularity(List<Spaceship> fleet) {
		Map<OfficerRank, Integer> temp = new HashMap<>();
		for(Spaceship i:fleet){
			for(Officer j:getOfficers(i.getCrewMembers())){
				if(temp.containsKey(j.getRank())){
					temp.put(j.getRank(), temp.get(j.getRank()) + 1);
				}
				else{
					temp.put(j.getRank(), 1);
				}
			}
		}
		List<Map.Entry<OfficerRank, Integer>> output =  new ArrayList<Map.Entry<OfficerRank, Integer>>(temp.entrySet());
		output.sort(rank_pop);
		return output;
	}

	
	public static final Comparator<Map.Entry<OfficerRank, Integer>> rank_pop = new Comparator<Map.Entry<OfficerRank,Integer>>(){
		public int compare(Map.Entry<OfficerRank, Integer> rank1,Map.Entry<OfficerRank, Integer> rank2){
			return rank2.getValue() - rank1.getValue();
		}
	};
	
	
}
