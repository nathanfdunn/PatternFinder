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
		return quantID + behavior.toChar();
	}
	
	public boolean equals(SimpleClause c){
		return quantID.equals(c.quantID) && behavior.equals(c.behavior);
	}
}
