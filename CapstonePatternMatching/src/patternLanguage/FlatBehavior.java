package patternLanguage;

public class FlatBehavior extends BehaviorToken{

	
	public FlatBehavior(){
		
	}
	
	@Override
	public BehaviorType getType() {
		return BehaviorType.FLAT;
	}

	@Override
	public String toString() {
		return "-";
	}
	
}
