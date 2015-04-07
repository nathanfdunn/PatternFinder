package behaviorClassification;

public class SimplifiedFeatureExtractor extends FeatureExtractor{

	@Override
	public double[] extractFeatures(double[] values, double[] times) {
		return new double[]{
				MyMath.coefVar(values),
				StandardFeatureExtractor.fisherZ(values, times),
				MyMath.skewness(values)	
		};
	}

	@Override
	public String[] featureNames() {
		return new String[]{
				"CV",
				"FisherZ",
				"skew"
		};
	}

}
