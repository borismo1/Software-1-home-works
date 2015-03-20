package starfleet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class StarfleetManagerTester {

	static int crewId=0; //used for generating unique names for crew members

	public static void main(String[] args) {

		System.out.println("*** STARFLEET COMMAND OFFICIAL REPORT ***\n");

		List<Spaceship> fleet = generateStarfleet(); //Generates fleet objects

		System.out.println("Fleet ships sorted by ship name:");
		for (String shipDescription : StarfleetManager.getShipDescriptionsSortedByShipName(fleet)) {
			System.out.println(shipDescription);
		}

		System.out.println();
		System.out.println("Ship counts by type:");
		for (Map.Entry<String,Integer> entry : StarfleetManager.getInstanceNumberPerClass(fleet).entrySet()) {
			System.out.println("\t" + entry.getValue() + "\t" + entry.getKey());
		}

		System.out.println();
		System.out.println("Weapon types:");
		for (String  weaponName: StarfleetManager.getFleetWeaponNames(fleet)) {
			System.out.println("\t" + weaponName);
		}

		System.out.println();
		System.out.println("Highest ranking officer per ship:");
		Map<Officer,Spaceship> officersToShipsMap = StarfleetManager.getHighestRankingOfficerPerShip(fleet); 
		for (Officer officer : officersToShipsMap.keySet())
			System.out.println("\t" + officer.getRank() + "\t" + officer.getName() + "\t" +  officersToShipsMap.get(officer) .getName());


		System.out.println();
		System.out.println("Officer ranks sorted by popularity:");
		for (Map.Entry<OfficerRank, Integer>  rankCountPair: StarfleetManager.getOfficerRanksSortedByPopularity(fleet)) {
			System.out.println("\t" + rankCountPair.getValue() + "\t" + rankCountPair.getKey());
		}

		System.out.printf("\nFleet Totals:\n");
		System.out.printf("\tTotal fire power:\t\t\t%d\n",  StarfleetManager.getTotalFleetFirePower(fleet));
		System.out.printf("\tTotal fleet crew members:\t\t\t%d\n", StarfleetManager.getTotalNumberOfFleetCrewMembers(fleet));
		System.out.printf("\tAverage age of fleet officers:\t\t\t%.2f\n", StarfleetManager.getAverageAgeOfFleetOfficers(fleet));
		System.out.printf("\tTotal annual maintenance cost:\t\t\t%d\n", StarfleetManager.getTotalMaintenanceCost(fleet));
	}

	// Generates a list of spaceship objects with synthesized data
	private static List<Spaceship> generateStarfleet() {
		List<Spaceship> fleet = new ArrayList<>();

		fleet.add(new MedicalShip("USS Pasteur", 2451,7.2f, generateCrew (14,64), 11));
		fleet.add(new MedicalShip("USS Fleming", 2532,4.5f, generateCrew (15,31), 5));

		fleet.add(new TransportShip("USS Astral Queen", 2396, 5.1f, generateCrew (9,14), 2000,5000));
		fleet.add(new TransportShip("USS Lantree", 24571, 5.1f, generateCrew (4,5), 3000,10000));	

		List<Weapon> weapons = new ArrayList<Weapon>();
		weapons.add(new Weapon("Laser Cannons",10,100));
		weapons.add(new Weapon("Quantum Torpedoes",120,200));
		weapons.add(new Weapon("TAU Phasers",150,280));
		fleet.add(new Fighter("USS Defiant",2423,6f, generateCrew (22,117), weapons));

		weapons = new ArrayList<Weapon>();
		weapons.add(new Weapon("Laser Cannons",10,100));
		weapons.add(new Weapon("Evaporator",30,300));
		fleet.add(new StealthCruiser("USS Andromeda",2478,9.2f, generateCrew (1,2), weapons));

		fleet.add(new StealthCruiser("USS Riker",2419,9f, generateCrew (1,3)));
		fleet.add(new StealthCruiser("USS Amsterdamer",2419,9f, generateCrew (1,1)));

		weapons = new ArrayList<Weapon>();
		weapons.add(new Weapon("Laser Cannons",10,100));
		weapons.add(new Weapon("Photon Torpedoes",100,200));
		fleet.add(new Bomber("USS Enterprise",2416,9.9f, generateCrew (24,212), weapons,5));

		return fleet;
	}

	// Generates a set containing crew-member objects containing synthesized data
	private static HashSet<CrewMember> generateCrew (int numberOfOfficers, int numberOfCrewmen) {

		HashSet<CrewMember> crew = new HashSet<>();

		//Generating Officers
		for (int i=0; i< numberOfOfficers-1; i++) {
			crewId++;
			String name = generateName();
			Integer age = generateAge();
			Integer yearsInService = generateYearsInService();
			OfficerRank rank = generateRank();

			crew.add(new Officer(name,age,yearsInService,rank));
		}

		// Adding one captain
		crewId++;
		crew.add(new Officer(generateName(),generateAge(),generateYearsInService(),OfficerRank.Captain));

		//Generating Cremen
		for (int i=0; i< numberOfCrewmen; i++) {
			crewId++;
			String name = generateName();
			Integer age = generateAge();
			Integer yearsInService = generateYearsInService();

			crew.add(new Crewman(name,age,yearsInService));
		}

		return crew;
	}

	private static String generateName() {
		final String[] nameRepository = new String[]{"James", "Riker", "Kathryn", "Picard", "Archer", "Sisko","Troi","Beverly", "FitzRoy", "Sparrow", "Nemo", "America","Data" };
		return  nameRepository[(crewId % (nameRepository.length))] + " #" +crewId;
	}

	private static Integer generateAge() {	
		final Integer[] ageRepository = new Integer[]{31, 47, 27, 23, 57, 104, 28, 19, 35, 64};
		return ageRepository[crewId % (ageRepository.length)];
	}

	private static Integer generateYearsInService() {	
		final Integer[] yearsRepository =  new Integer[]{7, 2, 14, 4, 6, 32, 16, 12, 2, 1, 17, 5};
		return yearsRepository[crewId % (yearsRepository.length)];
	}

	private static OfficerRank generateRank() {	
		final OfficerRank[] ranksRepository =  new OfficerRank[]{OfficerRank.Ensign, OfficerRank.Ensign, OfficerRank.Commander, OfficerRank.Lieutenant, OfficerRank.Ensign, OfficerRank.LieutenantCommander,  OfficerRank.Lieutenant,};
		return ranksRepository[crewId % (ranksRepository.length)];
	}


}
