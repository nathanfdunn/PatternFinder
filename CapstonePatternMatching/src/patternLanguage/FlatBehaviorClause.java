package patternLanguage;


public class FlatBehaviorClause extends BehaviorClause {

	@Override
	public boolean matches(BehaviorToken bt) {
		return bt.getType() == BehaviorType.FLAT;
	}
	
	public String toString() {
		return "-";
	}
}
