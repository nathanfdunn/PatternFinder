package patternLanguage;


public class ExtremeBehaviorClause extends BehaviorClause{

	private int sign;	//1 for spike, -1 for dip
	
	
	public ExtremeBehaviorClause(int sign){
		this.sign=sign;
	}
	
	@Override
	public boolean matches(BehaviorToken bt) {
		if (bt.getType() != BehaviorType.SPIKE)
			return false;
		
		ExtremeBehavior eb = (ExtremeBehavior)bt;
		
		if (Math.round( (float)eb.getSign() ) == sign){
			return true;
		}
		return false;
	}
	
	public String toString(){
		return sign > 0 ? "^" : "v";
	}

}
