package patternLanguage;


public class SteadyBehaviorClause extends BehaviorClause{

	private int sign;	//1 for increase, -1 for decrease
	
	public SteadyBehaviorClause( int sign){
		this.sign=sign;
	}
	
	@Override
	public boolean matches(BehaviorToken bt) {
		if (bt.getType() != BehaviorType.STEADY)
			return false;
		
		SteadyBehavior sb = (SteadyBehavior)bt;
		
		if (Math.round( (float)sb.getSign() ) == sign){
			return true;
		}
		return false;
	}
	
	public String toString() {
		return sign>0? "/" : "\\";
	}
	
}
