package patternLanguage;

public class ExtremeBehavior extends BehaviorToken{

	private double zScore;
	
	public ExtremeBehavior(double zScore){
		this.zScore = zScore;
	}
	
	@Override
	public BehaviorType getType() {
		return BehaviorType.SPIKE;
	}
	
	/**
	 * 
	 * @return	1.0 if it is a positive spike
	 * 			-1.0 if it is a negative spike (dip)
	 */
	public double getSign(){
		return Math.signum(zScore);
	}
	
	public String toString(){
		return zScore > 0 ? "^" : "v";
	}

}
