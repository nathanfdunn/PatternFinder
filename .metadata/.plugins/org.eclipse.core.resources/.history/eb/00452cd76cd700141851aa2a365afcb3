package patternDetection;


public class SimpleClause {
	public String quantID;
	public Behavior behavior;
	
	public SimpleClause(String quantID, Behavior behavior) {
		this.quantID = quantID;
		this.behavior = behavior;
	}

	public boolean matches(SimpleToken t){
		return quantID.equals(t.quantIdentity) && behavior == t.behavior;
	}
	
	public String toString(){
		return quantID + behavior;
	}
}
