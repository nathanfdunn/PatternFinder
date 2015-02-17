package patternLanguage;

public class SteadyBehavior extends BehaviorToken{

	private double r;		//value of correlation coefficient
	private double slope;	
	
	public SteadyBehavior( double r){
		this.r = r;
	}
	
	@Override
	public BehaviorType getType() {
		return BehaviorType.STEADY;
	}
	
	/**
	 * 
	 * @return 1.0 if there is a steady increase or -1.0 if there
	 * 				is a steady decrease
	 */
	public double getSign(){
		return Math.signum(r);
	}

	@Override
	public String toString() {
		return r>0? "/" : "\\";
	}

}
