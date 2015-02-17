package patternDetection;


public class Clause {
	public String quantID;
	public Behavior behavior;
	
	public Clause(String quantID, Behavior behavior) {
		this.quantID = quantID;
		this.behavior = behavior;
	}

	public boolean matches(Token t){
		return quantID.equals(t.quantIdentity) && behavior == t.behavior;
	}
	
	public String toString(){
		return quantID + behavior;
	}
}
