package patternDetection;

public class SimpleToken {
	public String quantIdentity;
	public Behavior behavior;
	public int time;
	
	//TODO implement fully qualified model
	
	public SimpleToken(String quantIdentity, Behavior behavior, int time) {
		this.quantIdentity = quantIdentity;
		this.behavior = behavior;
		this.time = time;
	}
	
	public String toString(){
		return "("+quantIdentity + behavior + ", t="+time+")";
	}
	
}
