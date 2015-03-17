package behaviorClassification;

/**
 * A class to find the relevant features of a chunk of time series data
 * @author nathandunn
 *
 */
public abstract class FeatureExtractor {

	public abstract double[] extractFeatures(double[] values, double[] times);
	
	public double[] extractFeatures(Chunk chunk){
		return extractFeatures(chunk.getVals(), chunk.getTimes());
	}
	
	public int numFeatures(){
		return featureNames().length;
	}

	public abstract String[] featureNames();
	
	//TODO make non-static?
	/**
	 * Determines if the specified chunk has enough information in it for
	 *  features to be meaningful
	 * @param c
	 * @return
	 */
	public final static boolean isFeaturizable(Chunk c){
		final int minLength = 5;
		return c.getLength() >= minLength;
	}
	
}