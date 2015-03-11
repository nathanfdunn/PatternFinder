package behaviorClassification;

public class StandardFeatureExtractor extends FeatureExtractor {

	@Override
	public double[] extractFeatures(double[] values, double[] times) {
		return new double[]{
				MyMath.minZ(values),
				MyMath.maxZ(values),
				MyMath.coefVar(values),
				fisherZ(values, times),
				MyMath.skewness(values)				
		};
	}

//	@Override
//	public int numFeatures() {
//		return 5;
//	}

	@Override
	public String[] featureNames() {
		return new String[]{
				"MaxZ",
				"MinZ",
				"CV",
				"FisherZ",
				"skew"
		};
	}
	
	private static double fisherZ(double[] values, double[] times){
		if (values.length <= 3)
			throw new Error("Fisher Transformation needs n >= 4");
		double w = MyMath.fisherTrans( MyMath.corrCoef(values, times) );
		double std = 1/Math.sqrt(values.length - 3);
		
		return w/std;
	}
}
