package starfleet;


public class Officer extends Crewman {
	
	private OfficerRank dudes_rank;

	public Officer(String name, int age, int seniority,OfficerRank rank) {
		super(name, age, seniority);
		this.dudes_rank = rank;
	}
	
	public OfficerRank getRank(){
		return this.dudes_rank;
	}
	
	@Override
	public boolean isOfficer() {
		return true;
	}

}
