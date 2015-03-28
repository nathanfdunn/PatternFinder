package patternDetection;

public class EvaluationObject implements Comparable<EvaluationObject>{
	private Pattern p;
	private double accuracy;
	private double rarity;
	private double power;
	private MatchDataObject data;
	
	private EvaluationSettings settings;
	
	
	public EvaluationObject( MatchDataObject mdo ){
		this(mdo, EvaluationSettings.DEFAULT);
	}
	
	
	public EvaluationObject( MatchDataObject mdo, double alpha, double beta, int padding ){
		this(mdo, new EvaluationSettings(alpha, beta, padding));
	}
	
	
	public static EvaluationObject evaluate(TokenStream ts, Pattern p, EvaluationSettings settings){
		MatchDataObject mdo = new MatchDataObject(p, ts);
		return new EvaluationObject(mdo, settings);
	}
	
	public static EvaluationObject evaluate(TokenStream ts, Pattern p){
		MatchDataObject mdo = new MatchDataObject(p, ts);
		return new EvaluationObject(mdo);
	}
	
	
	public static double evaluatePower(TokenStream ts, Pattern p, EvaluationSettings settings){
		return evaluate(ts, p, settings).getPower();
	}
	
	public static double evaluatePower(TokenStream ts, Pattern p){
		return evaluate(ts, p).getPower();
	}
	
	
	
	public EvaluationObject( MatchDataObject mdo, EvaluationSettings settings){
		
		final double alpha = settings.getAlpha();
		final double beta = settings.getBeta();
		final double padding = settings.getPadding();
		
		this.data = mdo;
		p = mdo.getPattern();
		int numMatches = mdo.getMatches().size();
		int numAntiMatches = mdo.getAntiMatches().size();
		
		//Uses Agresti-Caull method
		this.accuracy = ((double)numMatches + padding) / 
				(numMatches + numAntiMatches + 2*padding);
		
//		this.rarity = mdo.getLags().calculateRarity(p.time.getWidth());
		calculateRarity();
		if (this.rarity == 0.0)
			this.power = -1;
		else
			this.power = Math.pow(accuracy, alpha) / Math.pow(rarity, beta);
	}
	
	/**
	 * Uses a binomial model to compute how likely it is that there 
	 *  would be at least one occurrence of the successor clause 
	 *  within the time window of the string
	 */
	private void calculateRarity(){		
		int streamLen = data.getTokenStream().length();
		int intervalWidth = this.p.time.getWidth() + 1;		//add 1 because [0,0] covers one unit
		double p = (double)data.getSuccessors().size() / streamLen;
		this.rarity = 1 - Math.pow( 1-p, intervalWidth);
	}
	
	@Override
	public int compareTo(EvaluationObject arg0) {
		return Double.compare(this.power, arg0.power);
	}
	
	@Override
	public String toString(){
		return String.format("%s (%d, %d, %d, %d)(%f, %f, %f)", 
				p, getNumMatches(), getNumAntiMatches(), data.getPrecursors().size(),
				data.getSuccessors().size(), accuracy, rarity, power);
	}
	
	public Pattern getPattern() {
		return p;
	}
	public double getAccuracy() {
		return accuracy;
	}
	public double getRarity() {
		return rarity;
	}
	public double getPower() {
		return power;
	}
	
	public int getNumMatches(){
		return data.getMatches().size();
	}
	
	public int getNumAntiMatches(){
		return data.getAntiMatches().size();
	}
	
	
	public static class EvaluationSettings{
		private double alpha;
		private double beta;
		private double padding;
		
		public static final double DEFAULT_ALPHA = 1;
		public static final double DEFAULT_BETA = 0.5;
		public static final double DEFAULT_PADDING = 4;
		
		public static final EvaluationSettings DEFAULT = new EvaluationSettings();
		
		public EvaluationSettings(){
			this.alpha = DEFAULT_ALPHA;
			this.beta = DEFAULT_BETA;
			this.padding = DEFAULT_PADDING;
		}
		
		public EvaluationSettings(double alpha, double beta, int padding) {
			this.alpha = alpha;
			this.beta = beta;
			this.padding = padding;
		}
		
		public double getAlpha() {
			return alpha;
		}
		public double getBeta() {
			return beta;
		}
		public double getPadding() {
			return padding;
		}
		
	}
	
}
