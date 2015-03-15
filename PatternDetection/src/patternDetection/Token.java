package patternDetection;

public class Token {
	public String quantIdentity;
	public Behavior behavior;
	public int time;
	
	//TODO implement fully qualified model
	
	public Token(String quantIdentity, Behavior behavior, int time) {
		this.quantIdentity = quantIdentity;
		this.behavior = behavior;
		this.time = time;
	}
	
	public String toString(){
		return "("+quantIdentity + behavior + ", t="+time+")";
	}
	
}
